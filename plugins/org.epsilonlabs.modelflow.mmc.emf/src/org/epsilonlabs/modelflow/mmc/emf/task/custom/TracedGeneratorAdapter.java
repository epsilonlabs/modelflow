package org.epsilonlabs.modelflow.mmc.emf.task.custom;

import java.util.Collection;

import org.eclipse.emf.codegen.ecore.generator.GeneratorAdapter;
import org.eclipse.emf.codegen.ecore.generator.GeneratorAdapterFactory;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.Monitor;

public class TracedGeneratorAdapter implements GeneratorAdapter {
	
	protected GeneratorAdapter adapter;

	public TracedGeneratorAdapter(GeneratorAdapter adapter) {
		this.adapter = adapter;
	}

	@Override
	public GeneratorAdapterFactory getAdapterFactory() {
		return adapter.getAdapterFactory();
	}

	@Override
	public void setAdapterFactory(GeneratorAdapterFactory adapterFactory) {
		adapter.setAdapterFactory(adapterFactory);
	}

	@Override
	public Collection<?> getCanGenerateChildren(Object object, Object projectType) {
		return adapter.getCanGenerateChildren(object, projectType);
	}

	@Override
	public Object getCanGenerateParent(Object object, Object projectType) {
		return adapter.getCanGenerateParent(object, projectType);
	}

	@Override
	public boolean canGenerate(Object object, Object projectType) {
		return adapter.canGenerate(object, projectType);
	}
	
	@Override
	public void dispose() {
		adapter.dispose();
	}
	
	@Override
	public Collection<?> getGenerateChildren(Object object, Object projectType) {
		return adapter.getGenerateChildren(object, projectType);
	}

	@Override
	public Object getGenerateParent(Object object, Object projectType) {
		return adapter.getGenerateParent(object, projectType);
	}

	@Override
	public Diagnostic preGenerate(Object object, Object projectType) {
		Diagnostic diagnostic = adapter.preGenerate(object, projectType);
		return new DiagnosticAdapter(diagnostic, object, projectType);
	}

	@Override
	public Diagnostic generate(Object object, Object projectType, Monitor monitor) {
		Diagnostic diagnostic = adapter.generate(object, projectType, monitor);
		return new DiagnosticAdapter(diagnostic, object, projectType);
	}

	@Override
	public Diagnostic postGenerate(Object object, Object projectType) {
		Diagnostic diagnostic = adapter.postGenerate(object, projectType);
		return new DiagnosticAdapter(diagnostic, object, projectType);
	}	
	
}