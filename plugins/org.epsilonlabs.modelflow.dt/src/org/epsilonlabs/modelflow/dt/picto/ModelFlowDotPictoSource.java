/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dt.picto;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IPath;
import org.eclipse.epsilon.common.module.ModuleMarker;
import org.eclipse.epsilon.picto.ViewTree;
import org.eclipse.epsilon.picto.source.GraphvizSource;
import org.eclipse.ui.IEditorPart;
import org.epsilonlabs.modelflow.IModelFlowModule;
import org.epsilonlabs.modelflow.dt.ModelFlowPlugin;
import org.epsilonlabs.modelflow.dt.editor.ModelFlowEditor;
import org.epsilonlabs.modelflow.exception.MFDependencyGraphException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.graph.DependencyGraph;
import org.epsilonlabs.modelflow.execution.graph.IDependencyGraph;

/**
 * @author Betty Sanchez
 *
 */
public class ModelFlowDotPictoSource extends GraphvizSource {

	protected Map<File, IDependencyGraph> graphs = new HashMap<>();
	
	@Override
	public String getFileExtension() {
		return "mflow";
	}

	@Override
	public String getFormat() {
		return "graphviz-dot";
	}

	@Override
	public boolean supportsEditorType(IEditorPart editorPart) {
		boolean ok = editorPart instanceof ModelFlowEditor;
		if (ok) {
			ModelFlowEditor editor = (ModelFlowEditor) editorPart;
			editor.addModuleParsedListener( 
				(innerEditor, module) -> {
					IModelFlowModule mfModule = ((IModelFlowModule) module);
					if (mfModule.getWorkflow() == null) {
						mfModule.getCompilationContext().getMarkers().clear();
						mfModule.compile();
					}
					boolean noParseProblems = mfModule.getParseProblems().isEmpty();
					boolean noCompilationErrors = mfModule.getCompilationContext().getMarkers().stream()
							.filter(m->m.getSeverity().equals(ModuleMarker.Severity.Error))
							.collect(Collectors.toList())
							.isEmpty();
					if (noParseProblems && noCompilationErrors) {						
						IDependencyGraph dg = null;				
						try {
							IModelFlowContext ctx = mfModule.getContext();
							ctx.setModule(mfModule);					
							dg= new DependencyGraph().build(ctx);
						} catch (MFDependencyGraphException e) {
							ModelFlowPlugin.error(e.getMessage());	
						}
						graphs.put(mfModule.getFile(),dg); 
					} else {
						graphs.put(mfModule.getFile(),null);
					}
				}
			);
		}
		return ok;
	}

	@Override
	public ViewTree getViewTree(IEditorPart editor) throws Exception {
		IPath iFile = waitForPath(editor);
		if (iFile == null )
			return createEmptyViewTree();
		File modelFile = new File(iFile.toOSString());
		IDependencyGraph dg = graphs.get(modelFile);
		if (dg == null) {
			return createEmptyViewTree();
		}
		return new ViewTree(dg.toString(), getFormat());
	}

}
