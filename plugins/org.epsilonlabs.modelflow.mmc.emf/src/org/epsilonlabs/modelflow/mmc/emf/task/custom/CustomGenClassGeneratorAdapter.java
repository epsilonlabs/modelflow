package org.epsilonlabs.modelflow.mmc.emf.task.custom;

import org.eclipse.emf.codegen.ecore.generator.GeneratorAdapterFactory;
import org.eclipse.emf.codegen.ecore.genmodel.generator.GenClassGeneratorAdapter;
import org.eclipse.emf.codegen.jet.JETEmitter;
import org.eclipse.emf.codegen.util.GIFEmitter;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.common.util.URI;

public class CustomGenClassGeneratorAdapter extends GenClassGeneratorAdapter {

	public CustomGenClassGeneratorAdapter(GeneratorAdapterFactory generatorAdapterFactory) {
		super(generatorAdapterFactory);
	}

	@Override
	protected void generateJava(String targetPath, String packageName, String className, JETEmitter jetEmitter,
			Object[] arguments, Monitor monitor) {
		URI targetDirectory = toURI(targetPath).appendSegments(packageName.split("\\."));
		URI targetFile = targetDirectory.appendSegment(className + ".java");
		if (getGenerator() instanceof CustomGenerator) {
			((CustomGenerator) getGenerator()).addOutput(targetFile);
		}
		super.generateJava(targetPath, packageName, className, jetEmitter, arguments, monitor);
	}

	@Override
	protected void generateGIF(String targetPathName, GIFEmitter gifEmitter, String parentKey, String childKey,
			boolean overwrite, Monitor monitor) {
		URI targetFile = toURI(targetPathName);
		if (getGenerator() instanceof CustomGenerator) {
			((CustomGenerator) getGenerator()).addOutput(targetFile);
		}
		super.generateGIF(targetPathName, gifEmitter, parentKey, childKey, overwrite, monitor);
	}

	@Override
	protected void generateProperties(String targetPathName, JETEmitter jetEmitter, Object[] arguments,
			Monitor monitor) {
		URI targetFile = toURI(targetPathName);
		if (getGenerator() instanceof CustomGenerator) {
			((CustomGenerator) getGenerator()).addOutput(targetFile);
		}
		super.generateProperties(targetPathName, jetEmitter, arguments, monitor);
	}

	@Override
	protected void generateText(String targetPathName, JETEmitter jetEmitter, Object[] arguments, boolean overwrite,
			String encoding, Monitor monitor) {
		URI targetFile = toURI(targetPathName);
		if (getGenerator() instanceof CustomGenerator) {
			((CustomGenerator) getGenerator()).addOutput(targetFile);
		}
		super.generateText(targetPathName, jetEmitter, arguments, overwrite, encoding, monitor);
	}
	
}
