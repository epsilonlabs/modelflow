/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.epsilon.resource.hash;

import static org.epsilonlabs.modelflow.management.param.hash.Hasher.computeHashForFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.DatatypeConverter;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;

public class EmfHashUtil {

	private static final String MD5_ALGORITHM = "MD5";

	public static void main(String[] args) throws EolModelLoadingException {
		// String modelFile = "resources/hash/single/Root.xmi";
		// String metamodel = "resources/hash/single/test.ecore";

		String metamodel = "resources/hash/fragmented/JDTAST.ecore";
		String modelFile = "resources/hash/fragmented/set0-fragmented/set0.xmi";
		testWorkflowNoExpand(modelFile, metamodel, false);
	}

	public static void test(String modelFile, String metamodel) throws EolModelLoadingException {

		/* LOAD MODEL */
		EmfModel emfModel = newModel(modelFile, metamodel, false);

		/* CHECK IN-MEMORY RESOURCE HASH */
		Resource resource = emfModel.getResource();
		String checksum = computeHashForResource(resource);
		System.out.println(checksum);

		/* LOAD MODEL */
		EmfModel emfModelExpanded = newModel(modelFile, metamodel, true);

		/* CHECK IN-MEMORY RESOURCE HASH */
		Resource resourceExpanded = emfModelExpanded.getResource();
		String checksumExpanded = computeHashForResource(resourceExpanded);
		System.out.println(checksumExpanded);

		/* CHECK FILE HASH */
		File file = new File(modelFile);
		String checksumFile = computeHashForFile(file);
		System.out.println(checksumFile);

		// Dealing with fragmented models DONE
		// TODO : expanded resources toggle

		/*Map<String, Resource> dependencies = getResourceDependencies(resource, file);

		HashSet<String> res = new HashSet<>();
		HashSet<String> files = new HashSet<>();
		dependencies.values().forEach(r -> {
			String hash = computeHashForResource(r);
			res.add(hash);
			System.out.println(">>R: " + hash);
		});

		dependencies.keySet().forEach(r -> {
			File f = Paths.get(file.getParent(), r).toFile();
			String hash = computeHashForFile(f);
			files.add(hash);
			System.out.println(">>F: " + hash);
		});
		System.out.println(files.equals(res));*/
	}
	
	public static void testWorkflowNoExpand(String modelFile, String metamodel, boolean expanded) throws EolModelLoadingException {
		// FIRST TIME EXECUTION, no trace
		Object trace = null;
		for (int execution=0; execution<2; execution++) {
			EmfModel emfModel = null ;
			if (trace == null) {
				emfModel = newModel(modelFile, metamodel, expanded);
			} else {
				Map<String, String> newTrace = hash(trace, new File(modelFile), expanded);
				System.out.println("same: "+newTrace.equals(trace));
			}
			
			if (emfModel != null) { // Model loaded
				trace = hash(emfModel.getResource(), new File(modelFile), expanded);
				// work with model
				// re-compute trace if modified
				emfModel.dispose();
			} 
		}		
	}

	protected static EmfModel newModel(String modelFile, String metamodel, boolean expand)
			throws EolModelLoadingException {
		EmfModel emfModel = new EmfModel();
		emfModel.setName("test");
		emfModel.setCachingEnabled(true);
		emfModel.setMetamodelFile(metamodel);
		emfModel.setModelFile(modelFile);
		emfModel.setExpand(expand);
		emfModel.load();
		return emfModel;
	}

	/** --------- HELPERS -------- */

	public static Map<String, String> hash(Object trace, File root, boolean expanded) {
		HashMap<String, String> res = new HashMap<>();
		res.put(URI.createFileURI(root.getAbsolutePath()).toFileString(), computeHashForFile(root));
		if (expanded && trace instanceof HashMap) {
			HashMap<?, ?> maptrace = (HashMap<?, ?>) trace;
			maptrace.keySet().forEach(r -> {
				if (r instanceof String) {
					File f = new File((String) r);
					String hash = computeHashForFile(f);
					res.put((String) r,hash);
				}
			});
		}
		return res;
	}

	public static Map<String, String> hash(Resource resource, File root, boolean expanded) {
		HashMap<String, String> res = new HashMap<>();
		res.put(URI.createFileURI(root.getAbsolutePath()).toFileString(), computeHashForFile(root));
		if (expanded) {
			Set<String> dependencies = getResourceDependencies(resource, root);
			dependencies.stream().map(File::new).forEach(r -> {
				String hash = computeHashForFile(r);
				res.put(URI.createFileURI(r.getAbsolutePath()).toFileString(), hash);
			});
		}
		return res;
	}

	public static Set<String> getResourceDependencies(Resource resource, File root) {
		final Map<EObject, Collection<Setting>> externalCrossReferencer = EcoreUtil.ExternalCrossReferencer.find(resource);
		final Map<EObject, Collection<Setting>> proxies = EcoreUtil.ProxyCrossReferencer.find(resource);
		final Map<EObject, Collection<Setting>> unresolved = EcoreUtil.UnresolvedProxyCrossReferencer.find(resource);

		Set<String> deps = new HashSet<>();
		for (Collection<Setting> settings : externalCrossReferencer.values()) {
			deps.addAll(processSettings(root, settings));
		}
		for (Collection<Setting> settings : proxies.values()) {
			deps.addAll(processSettings(root, settings));
		}
		for (Collection<Setting> settings : unresolved.values()) {
			deps.addAll(processSettings(root, settings));
		}
		return deps;
	}

	/**
	 * @param root
	 * @param settings
	 * @return
	 */
	protected static Set<String> processSettings(File root, Collection<Setting> settings) {
		return settings.stream()
			.map(setting -> {
				final EStructuralFeature eStructuralFeature = setting.getEStructuralFeature();
				final EObject eObject = setting.getEObject();
				final Object obj = eObject.eGet(eStructuralFeature);
				if (obj instanceof EObject) {
					EObject eObj = (EObject) obj;
					return eObj.eResource();
				}
				return null;
			})
			.filter(Objects::nonNull)
			.peek(System.out::println)
			.map(Resource::getURI)
			.filter(URI::isFile)
			.map(URI::toFileString)
			.map(path-> {
				final File file = new File(path);
				if (!(file.isAbsolute())) {
					return Paths.get(root.getParent(), path).toFile().getAbsolutePath();
				}
				return path;
			})
			.collect(Collectors.toSet());
	}

	private static String computeHashForResource(Resource r) {
		try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
			MessageDigest md5 = MessageDigest.getInstance(MD5_ALGORITHM);
			try (DigestOutputStream dos = new DigestOutputStream(os, md5)) {
				r.save(dos, null);
				byte[] digest = dos.getMessageDigest().digest();
				return DatatypeConverter.printHexBinary(digest).toUpperCase();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

}
