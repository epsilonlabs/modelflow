/**
 * 
 */
package org.epsilonlabs.modelflow.mmc.epsilon.task.trace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.models.ModelRepository;
import org.epsilonlabs.modelflow.dom.IAbstractResource;
import org.epsilonlabs.modelflow.management.resource.IModelWrapper;
import org.epsilonlabs.modelflow.management.trace.ManagementTraceBuilder;
import org.epsilonlabs.modelflow.management.trace.Trace;
import org.epsilonlabs.modelflow.mmc.epsilon.task.AbstractEpsilonTask;

/**
 * @author Betty Sanchez
 *
 */
public class RuntimeTracer {

	protected AbstractEpsilonTask task;
	protected ModelRepository modelRepository;
	protected List<IModelWrapper> resources; 
	protected Collection<Trace> traces = new ArrayList<>();
	
	public RuntimeTracer(AbstractEpsilonTask task) {
		this.task = task;
	}
	
	public void trace(Object source, Object target, String link) {
		IModel sourceModel = getModelRepository().getOwningModel(source);
		IModel targetModel = getModelRepository().getOwningModel(target);
		if (sourceModel != null && targetModel != null ) {
			String sourceId = sourceModel.getElementId(source);
			String targetId = targetModel.getElementId(target);
			Trace trace = new ManagementTraceBuilder()
				 .addSourceModelElement(sourceId, getResource(sourceModel).orElse(null), "")
				 .addTargetModelElement(targetId, getResource(targetModel).orElse(null), "")
				 .link(link)
				 .build();
			traces.add(trace);
		}
	}
	
	/**
	 * @return the traces
	 */
	public Collection<Trace> getTraces() {
		return traces;
	}
	
	protected ModelRepository getModelRepository() {
		if (this.modelRepository == null) {
			this.modelRepository = task.getModule().getContext().getModelRepository(); 
		}
		return modelRepository;
	}
	
	protected List<IModelWrapper> getResources(){
		if (this.resources == null) {
			this.resources = Arrays.asList(task.getResources());
		}
		return this.resources;
	}
	
	protected Optional<String> getResource(IModel model){
		return getResources().stream()
				.map(IModelWrapper::getResource)
				.filter(r->r.getName().equals(model.getName()))
				.map(IAbstractResource::getName)
				.findFirst();
	}
}
