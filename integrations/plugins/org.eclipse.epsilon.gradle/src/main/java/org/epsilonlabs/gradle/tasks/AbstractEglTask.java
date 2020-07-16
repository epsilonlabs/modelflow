package org.epsilonlabs.gradle.tasks;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.epsilon.egl.EglFileGeneratingTemplateFactory;
import org.eclipse.epsilon.egl.EglTemplateFactory;
import org.eclipse.epsilon.egl.engine.traceability.fine.trace.ModelLocation;
import org.eclipse.epsilon.egl.engine.traceability.fine.trace.Region;
import org.eclipse.epsilon.egl.engine.traceability.fine.trace.TextLocation;
import org.eclipse.epsilon.egl.engine.traceability.fine.trace.Trace;
import org.eclipse.epsilon.egl.engine.traceability.fine.trace.TraceLink;
import org.eclipse.epsilon.egl.formatter.Formatter;
import org.epsilonlabs.gradle.element.EglDefaultFormatter;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputFiles;
import org.gradle.api.tasks.TaskExecutionException;

public abstract class AbstractEglTask extends AbstractExportableModuleTask {

	protected final Property<Trace> trace;
	protected Class<? extends EglTemplateFactory> templateFactoryType;
	protected List<EglDefaultFormatter> defaultFormatterNestedElements;
	protected Set<String> generatedFiles;

	protected File outputRoot;

	@Optional
	public File getOutputRoot() {
		return outputRoot;
	}

	public void setOutputRoot(File outputRoot) {
		this.outputRoot = outputRoot;
	}
	
	@OutputFiles
	public Set<File> generatedFiles(){
		return generatedFiles.parallelStream().map(f->new File(f)).collect(Collectors.toSet());
	}
	
	public AbstractEglTask() {
		templateFactoryType = EglFileGeneratingTemplateFactory.class;
		defaultFormatterNestedElements = new LinkedList<EglDefaultFormatter>();
		trace = objectFactory.property(Trace.class);
		generatedFiles = new HashSet<String>();
	}
	
	@Optional
	public Class<? extends EglTemplateFactory> getTemplateFactoryType() {
		return templateFactoryType;
	}

	public void setTemplateFactoryType(Class<? extends EglTemplateFactory> templateFactoryType) {
		if (EglTemplateFactory.class.isAssignableFrom(templateFactoryType)) {
			this.templateFactoryType = templateFactoryType;

		} else {
			throw new TaskExecutionException(this, new Exception(
					"The templateFactoryType parameter must be class that subtypes org.eclipse.epsilon.egl.EglTemplateFactory."));
		}
	}
	
	@Override
	protected void initialize() throws Exception { }
	
	protected List<Formatter> instantiateDefaultFormatters()
			throws InstantiationException, IllegalAccessException {
		final List<Formatter> defaultFormatters = new LinkedList<Formatter>();

		for (EglDefaultFormatter defaultFormatterNestedElement : defaultFormatterNestedElements) {
			defaultFormatters.add(defaultFormatterNestedElement.getImplementation().newInstance());
		}

		return defaultFormatters;
	}

	public EglDefaultFormatter createDefaultFormatter() {
		final EglDefaultFormatter nestedElement = new EglDefaultFormatter();
		defaultFormatterNestedElements.add(nestedElement);
		return nestedElement;
	}

	@Override
	protected Collection<? extends Class<?>> getClassesForExportedModel() {
		return Arrays.asList(Trace.class, TraceLink.class, TextLocation.class, ModelLocation.class,
				Region.class);
	}

	@Override
	protected Collection<? extends Object> getObjectsForExportedModel() {
		return trace.get().getAllContents();
	}
	
}
