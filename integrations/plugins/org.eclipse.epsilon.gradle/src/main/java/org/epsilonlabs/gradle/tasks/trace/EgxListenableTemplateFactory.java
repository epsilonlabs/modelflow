package org.epsilonlabs.gradle.tasks.trace;

import java.io.IOException;
import java.net.URISyntaxException;

import org.eclipse.epsilon.egl.EglFileGeneratingTemplate;
import org.eclipse.epsilon.egl.EglFileGeneratingTemplateFactory;
import org.eclipse.epsilon.egl.EglTemplate;
import org.eclipse.epsilon.egl.execute.context.IEglContext;
import org.eclipse.epsilon.egl.spec.EglTemplateSpecification;
import org.eclipse.epsilon.eol.execute.StackTraceManager;
import org.eclipse.epsilon.eol.execute.context.IEolContext;

public class EgxListenableTemplateFactory extends EglFileGeneratingTemplateFactory {
	
	public EgxListenableTemplateFactory() {
		super();
	}
	
	public EgxListenableTemplateFactory(IEglContext context) {
		super(context);
	}
	
	@Override
	protected IEglContext getContextForNewTemplate() {
		IEglContext ctx = super.getContextForNewTemplate();
		IEolContext delegate = ctx.getDelegate();
		if (delegate != null) {
			delegate.getExecutorFactory().getExecutionListeners().stream().forEach(l-> {
				if (!(l instanceof StackTraceManager)) {					
					ctx.getExecutorFactory().addExecutionListener(l);
				}
			});
		}
		return ctx;
	}
	
	@Override
	protected EglTemplate createTemplate(EglTemplateSpecification spec) throws Exception {
		return new EglFileGeneratingTemplate(spec, getContextForNewTemplate(), getOutputRootOrRoot(), outputRootPath) {
			@Override
			protected void writeNewContentsIfDifferentFromExistingContents() throws URISyntaxException, IOException {
				if (isOverwriteUnchangedFiles() || !getNewContents().equals(getExistingContents())) {
					write();
					addMessage(getPositiveMessage() + getTargetName());
				} else {
					addMessage("Content unchanged for " + getTargetName());
					write();
					//setCurrentOutputFile(template.addOutputFile(getTargetName(), UriUtil.fileToUri(getTarget())));
					/*
					if (getOutputMode().equals("MERGE")) {
						for (LocatedRegion pr : module.getContext().getPartitioner().partition(getNewContents()).getLocatedRegions()) {
							getCurrentOutputFile().addProtectedRegion(pr.getId(), pr.isEnabled(), pr.getOffset());
						}
					}*/
				}
			}
			
			
		
		};
	}
	/*

	public class InternalTemplate extends EglFileGeneratingTemplate{
		
		public InternalTemplate(EglTemplateSpecification spec, IEglContext context, URI outputRoot, String outputRootPath) throws Exception {
			super(spec, context, outputRoot, outputRootPath);
			this.template = super.template {
			
			public Template getTemplate(){
				Template newTemplate = new Template();
				Template oldTemplate = getTemplate();
				oldTemplate.getChildren().forEach(c->newTemplate.add(c));
				return newTemplate;
			}
			};
		}
		
	}	*/	
	
}