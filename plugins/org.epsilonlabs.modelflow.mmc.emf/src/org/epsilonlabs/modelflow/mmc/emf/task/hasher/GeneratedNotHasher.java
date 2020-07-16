package org.epsilonlabs.modelflow.mmc.emf.task.hasher;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.codegen.merge.java.JMerger;
import org.eclipse.emf.codegen.merge.java.facade.ast.ASTFacadeHelper;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl;
import org.epsilonlabs.modelflow.management.param.hash.Hasher;
import org.epsilonlabs.modelflow.management.param.hash.IHasher;

@SuppressWarnings("unchecked")
public class GeneratedNotHasher implements IHasher<CodegenOutputUtil, Map<String, Object>> {

	@Override
	public Map<String, Object> fromExecutionTrace(Map<String, Object> trace) {
		Map<String, String> result = new HashMap<String, String>();

		Map<String, String> object = (Map<String, String>) trace.get(CodegenOutputUtil.OPTIONS);
		String jdk = (String) trace.get(CodegenOutputUtil.JDK);
		HashMap<String, String> files = (HashMap<String, String>)trace.get(CodegenOutputUtil.FILES);
		String templateDir = (String) trace.get(CodegenOutputUtil.TEMPLATE_DIR);
		Boolean dynamic = (Boolean) trace.get(CodegenOutputUtil.DYNAMIC);
		Boolean formatting = (Boolean) trace.get(CodegenOutputUtil.FORMATTING);
		
		CodegenOutputUtil helper = new CodegenOutputUtil(object, jdk, files.keySet(), templateDir, dynamic, formatting); 
		
		
		for (String file : files.keySet()) {
			String pastResult = (String) files.get(file);
			if (file.endsWith(".java")) {
				JMerger jMerger = helper.getMerger();
				System.out.println(file);
				URI targetFile = URI.createFileURI(file);
				try {
					// read the current version of the file
					InputStream traceFileValue = new ExtensibleURIConverterImpl().createInputStream(targetFile);
					// set it as target
					jMerger.setTargetCompilationUnit(jMerger.createCompilationUnitForInputStream(traceFileValue, "UTF-8"));
					// extract its contents
					String targetFileContents = jMerger.getTargetCompilationUnitContents();
					// ??
					jMerger.setFixInterfaceBrace(new ASTFacadeHelper().fixInterfaceBrace());
					// use the value from the trace as previous output
					jMerger.setSourceCompilationUnit(jMerger.createCompilationUnitForContents(pastResult));
					// try merging
					jMerger.merge();

					String newContents = jMerger.getTargetCompilationUnitContents();
					boolean changed = !targetFileContents.equals(newContents);
					if (changed) {						
						System.out.println("--------CURRENT--------");
						System.out.println(Hasher.hash(targetFileContents));
						System.out.println("--------FROM TRACE--------");
						System.out.println(Hasher.hash(pastResult));
						System.out.println("--------NEW CONTENTs--------");
						System.out.println(Hasher.hash(newContents));
					}
					helper.reset();
					if (changed) {
						// put in map the new Value so that when compared in flow it notices the change
						result.put(file, newContents);
					} else {
						// put in map to return the old value so that when compared it remains the same
						result.put(file, pastResult);
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				String hash = Hasher.computeHashForFile(new File(file));
				result.put(file, pastResult);
				// plain file hash
			}
		}
		trace.put(CodegenOutputUtil.FILES, result);
		return trace;
	}

	@Override
	public Map<String, Object> fromTaskPopulatedParameter(CodegenOutputUtil taskParameterReturnType) {
		HashMap<String, String> fileHashes = new HashMap<String, String>();
		taskParameterReturnType.getFiles().forEach(f -> {
			try {
				InputStream is = new ExtensibleURIConverterImpl()
						.createInputStream(URI.createFileURI(f));
				InputStreamReader isReader = new InputStreamReader(is);
				BufferedReader reader = new BufferedReader(isReader);
				StringBuffer sb = new StringBuffer();
				String str;
				while ((str = reader.readLine()) != null) {
					sb.append(str);
				}
				fileHashes.put(f, sb.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put(CodegenOutputUtil.FILES, fileHashes);
		result.put(CodegenOutputUtil.JDK, taskParameterReturnType.getJDK());
		result.put(CodegenOutputUtil.OPTIONS, taskParameterReturnType.getOptions());
		result.put(CodegenOutputUtil.DYNAMIC, taskParameterReturnType.isDynamic());
		result.put(CodegenOutputUtil.TEMPLATE_DIR, taskParameterReturnType.getTemplateDir());
		return result;
	}

}
