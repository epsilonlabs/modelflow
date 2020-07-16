package org.epsilonlabs.gradle.tasks.trace;


import org.eclipse.epsilon.egl.engine.traceability.fine.trace.Trace;
import org.eclipse.epsilon.egl.execute.context.IEgxContext;
import org.eclipse.epsilon.eol.execute.ExecutorFactory;
import org.eclipse.epsilon.eol.execute.introspection.recording.IPropertyAccessRecorder;
import org.eclipse.epsilon.eol.execute.introspection.recording.PropertyAccessExecutionListener;
import org.eclipse.epsilon.eol.execute.introspection.recording.PropertyAccessRecorder;;

public class EgxEndToEndTraceContextAdaptor {

	public Trace adapt(IEgxContext context) {
		
		final Trace trace = new Trace();
		final TracedPropertyAccessLedger ledger = new TracedPropertyAccessLedger();
		
		final IPropertyAccessRecorder recorder = new PropertyAccessRecorder();
		PropertyAccessExecutionListener propertyListener = new PropertyAccessExecutionListener(recorder);

		EglOutputBufferPrintExecutionListener eglOutputListener = new EglOutputBufferPrintExecutionListener(recorder, ledger);
		
		ExecutorFactory executorFactory = context.getExecutorFactory();
		executorFactory.addExecutionListener(propertyListener);		
		executorFactory.addExecutionListener(eglOutputListener); // Must create the traces for output files
		
		TraceLinkCreatorExecutionListener traceLinkCreatingListener = new TraceLinkCreatorExecutionListener(trace, ledger);
		context.getTemplateFactory().getTemplateExecutionListeners().add(traceLinkCreatingListener);
		
		return trace;
	}
	
}
