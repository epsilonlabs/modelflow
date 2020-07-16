package org.epsilonlabs.gradle.artifact.internal;

import org.eclipse.epsilon.eol.models.IModel;
import org.epsilonlabs.gradle.artifact.EmfModel;
import org.epsilonlabs.gradle.artifact.Model;
import org.epsilonlabs.gradle.artifact.RuntimeModel;
import org.gradle.api.Project;

public class EmfIModelFactory implements Factory {
	
	@Override
	public IModel get(Model _model, RuntimeModel _runtime, Project p) {
		/*assert (_model.getName().equals(_runtime.getName()));
		assert (_model.getClass().equals(EmfModel.class));
		assert (_runtime.getClass().equals(EmfRuntimeModel.class));
		
		EmfModel model = (EmfModel) _model;
		EmfRuntimeModel runtime = (EmfRuntimeModel) _runtime;
		
		org.eclipse.epsilon.emc.emf.EmfModel m = new org.eclipse.epsilon.emc.emf.EmfModel();
		m.getAliases().add(runtime.getAlias());
		m.setCachingEnabled(runtime.getIsCachingEnabled());
		m.setReadOnLoad(runtime.getReadOnLoad());
		m.setStoredOnDisposal(runtime.getStoreOnDisposal());
		m.setName(model.getName());
		m.setExpand(model.getIsExpand());
		m.setReuseUnmodifiedFileBasedMetamodels(model.getIsReuseUnmodifiedFileBasedMetamodels());
		m.setModelFile(model.getModelFile().getAbsolutePath());
		m.setMetamodelFile(model.getMetamodelFile().getAbsolutePath());
		
		// FIXME FileCollection for metamodels
		//List<String> metamodels = model.getMetamodelFiles().getFiles().stream().map(f -> f.getAbsolutePath()).collect(Collectors.toList());
		//m.setMetamodelFiles(metamodels);
				
		return m;*/
		
		// FIXME FileCollection for metamodels
		//List<String> metamodels = model.getMetamodelFiles().getFiles().stream().map(f -> f.getAbsolutePath()).collect(Collectors.toList());
		//m.setMetamodelFiles(metamodels);
				
		//return m;
		return null;		
	}

	@Override
	public IModel get(Model _model, Project _project) {
		//System.out.println("Yai");
		EmfModel model = (EmfModel) _model;
		
		org.eclipse.epsilon.emc.emf.EmfModel m = new org.eclipse.epsilon.emc.emf.EmfModel();
		m.setName(model.getName());
	
		m.setExpand(model.getIsExpand());
		m.setReuseUnmodifiedFileBasedMetamodels(model.getIsReuseUnmodifiedFileBasedMetamodels());
		m.setModelFile(model.getModelFile().getAbsolutePath());
		m.setMetamodelFile(model.getMetamodelFile().getAbsolutePath());
		/*System.out.println("IN FACTORY");
		System.out.println(m.getName());
		System.out.println(m.getModelFile());
		System.out.println(m.getMetamodelFiles());*/
		return m;
	}
	
}