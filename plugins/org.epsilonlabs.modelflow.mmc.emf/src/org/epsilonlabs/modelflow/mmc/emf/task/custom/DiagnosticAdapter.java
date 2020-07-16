package org.epsilonlabs.modelflow.mmc.emf.task.custom;

import java.util.List;

import org.eclipse.emf.common.util.Diagnostic;

public class DiagnosticAdapter implements Diagnostic {
	
	protected Diagnostic diagnostic;
	protected Object object;
	protected Object projectType;
	
	public DiagnosticAdapter(Diagnostic diagnostic, Object object, Object projectType) {
		this.diagnostic = diagnostic;
		this.object = object;
		this.projectType = projectType;
	}

	@Override
	public int getSeverity() {
		return diagnostic.getSeverity();
	}

	@Override
	public String getMessage() {
		return diagnostic.getMessage();
	}

	@Override
	public String getSource() {
		return diagnostic.getSource();
	}

	@Override
	public int getCode() {
		return diagnostic.getCode();
	}

	@Override
	public Throwable getException() {
		return diagnostic.getException();
	}

	@Override
	public List<?> getData() {
		return diagnostic.getData();
	}

	@Override
	public List<Diagnostic> getChildren() {
		return diagnostic.getChildren();
	}

}
