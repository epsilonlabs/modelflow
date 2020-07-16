package org.epsilonlabs.gradle.element;

import org.eclipse.epsilon.egl.formatter.Formatter;

public class EglDefaultFormatter {

	private Class<? extends Formatter> implementation;

	public Class<? extends Formatter> getImplementation() {
		return implementation;
	}

	public void setImplementation(Class<? extends Formatter> implementation) throws Exception {
		if (Formatter.class.isAssignableFrom(implementation)) {
			this.implementation = implementation;

		} else {
			throw new Exception("The implementation parameter must be a class that subtypes org.eclipse.epsilon.egl.formatter.Formatter.");
		}
	}
}
