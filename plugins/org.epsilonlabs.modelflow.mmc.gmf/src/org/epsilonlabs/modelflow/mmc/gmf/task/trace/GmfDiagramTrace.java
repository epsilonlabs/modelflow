/**
 * 
 */
package org.epsilonlabs.modelflow.mmc.gmf.task.trace;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.epsilonlabs.modelflow.management.trace.ManagementTraceBuilder;
import org.epsilonlabs.modelflow.management.trace.Trace;

/**
 * @author Betty Sanchez
 *
 */
public class GmfDiagramTrace {

	protected Object[] sources;
	protected String label;
	protected List<File> outputs = new ArrayList<>();
	protected String resource;
	protected final Path base = ResourcesPlugin.getWorkspace().getRoot().getRawLocation().toFile().toPath();
	
	public void setSources(Object[] sources) {
		this.sources = sources;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public List<File> getOutputs() {
		return outputs;
	}
	public void addOutput(File output) {
		this.outputs.add(output);
	}

	public GmfDiagramTrace setResourceName(String resource){
		this.resource = resource;
		return this;
	}
	
	public Trace getTrace(){
		ManagementTraceBuilder builder = new ManagementTraceBuilder();
		
		if (sources != null && sources.length > 0) {			
			// Sources
			Arrays.asList(sources).forEach(s->{
				if (s instanceof EObject) {
					EObject eObject = (EObject) s;
					Resource eResource = eObject.eResource();
					if (eResource != null) {
						String id = eResource.getURIFragment(eObject);
						builder.addSourceModelElement(id, resource, null);
					} else {
						//FIXME check why are we getting here
						builder.addSourceModelElement("!Invalid", resource, null);
					}
				}
			});
			// Targets
			
			outputs.forEach(f->builder.addTargetElement(base.resolve(f.toString().substring(1)).toFile().getAbsolutePath(), -1, -1));
			
			// Link
			builder.link(label);
			
			final Trace trace = builder.build();
			outputs = null;
			sources = null;
			resource = null;
			return trace;
		}
		return null;
	}
	
}
