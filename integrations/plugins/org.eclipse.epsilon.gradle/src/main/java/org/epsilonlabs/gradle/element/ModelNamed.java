/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.epsilonlabs.gradle.element;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.gradle.api.Named;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;

public class ModelNamed implements Named, Serializable {
	
	private static final long serialVersionUID = 4222744253840395528L;
	
	public String name;
	public String as;
	public List<String> aliases = new ArrayList<>();
	
	public ModelNamed(String name) {
		this.name = name;
	}
	
	@Override
	@Input
	public String getName() {
		return this.name;
	}	
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Optional
	@Input
	public List<String> getAliases() {
		return aliases;
	}
	
	public void setAliases(List<String> aliases) {
		this.aliases = aliases;
	}
	
	public void setAliases(String alias) {
		this.aliases.add(alias);
	}
	
	@Optional
	@Input
	public String getAs() {
		return as;
	}

	public void setAs(String as) {
		this.as = as;
	}
	
	@Override
	public String toString() {
		return "ModelNamed [name=" + name + ", as=" + as + ", aliases=" + aliases + "]";
	}

	public boolean isValid() {
		return name != null;
	}
	
}
