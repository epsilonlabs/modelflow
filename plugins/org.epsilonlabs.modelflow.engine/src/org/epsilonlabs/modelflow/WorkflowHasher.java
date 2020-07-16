/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.Scanner;

import org.epsilonlabs.modelflow.dom.Workflow;
import org.epsilonlabs.modelflow.management.param.hash.Hasher;

/**
 * @author Betty Sanchez
 *
 */
public class WorkflowHasher{
	
	protected final String code;
	protected final File file;
	protected final URI uri;
	protected final Workflow workflow;
	
	public WorkflowHasher(String code, File file, URI uri) {
		this.code = code;
		this.file = file;
		this.uri = uri;
		this.workflow = null;
	}
	
	public WorkflowHasher(Workflow w, File file) {
		this.workflow = w;
		this.file = file;
		this.code = null;
		this.uri = null;
	}

	protected String computeIdentifier() throws MalformedURLException, IOException {
		if (code != null) {
			return Hasher.computeHash(code.getBytes());
		} else if (file != null) {
			return Hasher.computeHashForFile(file);
		} else if (uri != null) {
			InputStream openStream = uri.toURL().openStream();
			StringBuilder sb = new StringBuilder();
			try (Scanner sc = new Scanner(openStream)) {
				while (sc.hasNext()) {
					sb.append(sc.nextLine());
				}
				return Hasher.computeHash(sb.toString().getBytes());
			} catch (Exception e) {
			}
		}
		return null;
	}

}
