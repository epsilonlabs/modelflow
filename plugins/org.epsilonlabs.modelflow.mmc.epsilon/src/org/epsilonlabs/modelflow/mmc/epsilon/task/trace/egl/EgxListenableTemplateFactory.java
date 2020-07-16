/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.epsilon.task.trace.egl;

import java.io.IOException;
import java.net.URISyntaxException;

import org.eclipse.epsilon.egl.EglFileGeneratingTemplate;
import org.eclipse.epsilon.egl.EglFileGeneratingTemplateFactory;
import org.eclipse.epsilon.egl.EglTemplate;
import org.eclipse.epsilon.egl.execute.context.IEglContext;
import org.eclipse.epsilon.egl.spec.EglTemplateSpecification;
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
			delegate.getExecutorFactory().getExecutionListeners().stream().forEach(l-> 
				ctx.getExecutorFactory().addExecutionListener(l)
			);
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