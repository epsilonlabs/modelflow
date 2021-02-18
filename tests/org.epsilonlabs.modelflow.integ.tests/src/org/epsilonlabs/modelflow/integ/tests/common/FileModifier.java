/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.integ.tests.common;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.epsilonlabs.modelflow.management.param.hash.Hasher;

/**
 * @author Betty Sanchez
 *
 */
public class FileModifier {

	protected File file;

	public FileModifier(File file) {
		this.file = file;
	}
	
	public FileModifier(String file) {
		this.file = new File(file);
	}
	
	public void newLineAtEnd(String line) {
		assertTrue("File not found", file.exists());
		String hashOriginal = Hasher.computeHashForFile(file);
		String absolutePath = file.getAbsolutePath();
		try (FileWriter fw = new FileWriter(absolutePath, true)) {
			BufferedWriter out = new BufferedWriter(fw);
			out.newLine();
			out.write(line);
			out.close();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		String hashModified = Hasher.computeHashForFile(new File(absolutePath));
		assertNotEquals("hashes are the same", hashOriginal, hashModified);
	}
	
	public void append(File appendFile){
		assertTrue("File not found", file.exists());
		assertTrue("Appending file not found", appendFile.exists());
		String hashOriginal = Hasher.computeHashForFile(file);
		String absolutePath = file.getAbsolutePath();
		try (FileWriter fw = new FileWriter(absolutePath, true)) {
			BufferedWriter out = new BufferedWriter(fw);
			try (BufferedReader reader = new BufferedReader(new FileReader(appendFile))){
				String line;
				while ((line = reader.readLine()) != null) {					
					out.newLine();
					out.write(line);
				}
			}
			out.close();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		String hashModified = Hasher.computeHashForFile(new File(absolutePath));
		assertNotEquals("hashes are the same", hashOriginal, hashModified);
	}
	
	public void replaceFirst(String regex, String replacement) {
		String absolutePath = file.getAbsolutePath();
		assertTrue("File not found", file.exists());
		String hash1 = Hasher.computeHashForFile(file);
		Pattern pattern = Pattern.compile(regex);
		
		// Parse and Replace
		StringBuilder inputBuffer = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new FileReader(file))){
			String line;
			boolean found = false;
			while ((line = reader.readLine()) != null) {
				Matcher matcher = pattern.matcher(line);
				if (matcher.matches() && !found) {					
					line = matcher.replaceFirst(replacement);
					found = true;
				}
				inputBuffer.append(line + "\n");					
			}
		} catch (IOException e) {
			fail(e.getMessage());
		}
		
		// Store
		String modifiedString = inputBuffer.toString();
		try (FileOutputStream fileOut = new FileOutputStream(file)) {				
			fileOut.write(modifiedString.getBytes());
		} catch (IOException e) {
			fail(e.getMessage());
		}
		
		String hash2 = Hasher.computeHashForFile(new File(absolutePath));
		assertNotEquals("hashes are the same", hash1, hash2);
	}
	
}
