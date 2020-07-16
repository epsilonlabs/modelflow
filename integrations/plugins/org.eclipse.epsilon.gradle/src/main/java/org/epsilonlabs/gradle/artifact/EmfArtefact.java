package org.epsilonlabs.gradle.artifact;

public interface EmfArtefact extends ModelArtifact {

	String getExtension();

	void setExtension(String extension);

	String getClassifier();

	void setClassifier(String classifier);

}
