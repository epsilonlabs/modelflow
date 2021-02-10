/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.management.param.hash;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hasher {

	private static final Logger LOG = LoggerFactory.getLogger(Hasher.class);

	private static final String MD5_ALGORITHM = "MD5";

	public static Object hash(Object object) {
		byte[] bytes = new byte[0];
		if (object instanceof String) {
			bytes = ((String) object).getBytes();
			return computeHash(bytes);
		} else if (object instanceof Integer) {
			bytes = new byte[] { ((Integer) object).byteValue() };
			return computeHash(bytes);
		} else if (object instanceof Long) {
			bytes = new byte[] { ((Long) object).byteValue() };
			return computeHash(bytes);
		} else if (object instanceof Float) {
			bytes = new byte[] { ((Float) object).byteValue() };
			return computeHash(bytes);
		} else if (object instanceof Double) {
			bytes = new byte[] { ((Double) object).byteValue() };
			return computeHash(bytes);
		} else if (object instanceof Boolean) {
			bytes = new byte[] { (byte) (((boolean) object) ? 1:0) };
			return computeHash(bytes);
		} else if (object instanceof File) {
			return computeHashForFile((File) object);
		} else if (object instanceof List) {
			return ((List<?>) object).stream().map(Hasher::hash).collect(Collectors.toList());
		} else if (object instanceof Map) {
			return ((Map<?,?>) object).entrySet().stream().collect(Collectors.toMap(Entry::getKey,e->hash(e.getValue())));
		} else if (object instanceof Set) {
			return ((Set<?>) object).stream().map(Hasher::hash).collect(Collectors.toSet());
		} else if (object == null) {
			return null;
		} else {
			LOG.error("Usupported type {} for hashing", object.getClass());
		}
		return bytes;
	}
	
	public static byte[] objectBytes(byte[] bytes, Object object) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(object);
			bytes = bos.toByteArray();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return bytes;
	}

	protected static byte[] fileBytes(File file) {
		try {
			return Files.readAllBytes(file.toPath());
		} catch (IOException e) {
			return new byte[0];
		}
	}

	public static String computeHashForFile(File file) {
		try {
			byte[] bytes = Files.readAllBytes(file.toPath());
			return computeHash(bytes);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
		return null;
	}

	public static String computeHash(byte[] bytes) {
		try {
			MessageDigest md5 = MessageDigest.getInstance(MD5_ALGORITHM);
			md5.update(bytes);
			byte[] digest = md5.digest();
			return DatatypeConverter.printHexBinary(digest).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			LOG.error(e.getMessage(), e);
		}
		return null;
	}
	
	public static Set<String> computeHash(Set<File> files) {
		return files.stream().map(Hasher::computeHashForFile).collect(Collectors.toSet());
	}

}
