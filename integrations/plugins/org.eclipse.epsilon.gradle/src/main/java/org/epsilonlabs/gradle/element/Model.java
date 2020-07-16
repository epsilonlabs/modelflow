package org.epsilonlabs.gradle.element;

import java.io.File;

import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.Optional;

public class Model{

	public String name = null;
	public String alias = null;
	public File file = null;
	
	Model(String name) {
        this.name = name;
    }

	@Input
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Input
	@Optional
	public String getAlias() {
		return alias;
	}
	
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	@InputFile
	@Optional
	public File getFile() {
		return file;
	}
	
	public void setFile(File file) {
		this.file = file;
	}

}