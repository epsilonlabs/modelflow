/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.epsilonlabs.modelflow.dom.impl.DomPackageImpl;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.trace.ExecutionTrace;
import org.epsilonlabs.modelflow.execution.trace.ExecutionTracePackage;
import org.epsilonlabs.modelflow.execution.trace.impl.ExecutionTraceFactoryImpl;
import org.epsilonlabs.modelflow.management.trace.ManagementTrace;
import org.epsilonlabs.modelflow.management.trace.ManagementTracePackage;
import org.epsilonlabs.modelflow.management.trace.impl.ManagementTraceFactoryImpl;

/**
 * @author Betty Sanchez
 *
 */
public class ModulePersistenceHelper {

	protected final IModelFlowModule module;
	protected ResourceSet rs;
	
	public ModulePersistenceHelper(IModelFlowModule module){
		this.module = module;
	}
	
	protected ResourceSet getResourceSet() {
		if (rs == null) {
			rs = new ResourceSetImpl();		
			Registry packageRegistry = rs.getPackageRegistry();
			packageRegistry.put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
			packageRegistry.put(DomPackageImpl.eNS_URI, DomPackageImpl.eINSTANCE);
			packageRegistry.put(ExecutionTracePackage.eNS_URI, ExecutionTracePackage.eINSTANCE);
			packageRegistry.put(ManagementTracePackage.eNS_URI, ManagementTracePackage.eINSTANCE);
			
			Map<String, Object> extensionToFactoryMap = rs.getResourceFactoryRegistry().getExtensionToFactoryMap();
			extensionToFactoryMap.put(ModelFlowModule.MANAGEMENT_TRACE_EXTENSION, new XMIResourceFactoryImpl());
			extensionToFactoryMap.put(ModelFlowModule.EXECUITON_TRACE_EXTENSION, new XMIResourceFactoryImpl());
		}
		return rs;
	}
	
	public void prepareEndToEndTrace() throws EolRuntimeException {
		IModelFlowConfiguration config = module.getConfiguration();
		ManagementTrace managementTrace = null;
		if (module.getContext().isEndToEndTracing()) {
			IModelFlowContext ctx = module.getContext();
			if (config.isSaveEndToEndTraces() ) {
				File file = config.getEndToEndTraceFile();
				if (file != null && file.exists()) {
					URI fileUri = URI.createFileURI(file.getAbsolutePath());
					Resource traceResource = getResourceSet().createResource(fileUri);
					try {
						traceResource.load(null);
						managementTrace = (ManagementTrace) traceResource.getContents().get(0);
						
					} catch (Exception e) {
						ctx.getErrorStream().println("Unable to load end-to-end trace.%n");
						ctx.getErrorStream().println(e.getMessage());
						throw new EolRuntimeException(e);
					}
				} 
			} 
			if (managementTrace == null) {
				managementTrace  = ManagementTraceFactoryImpl.eINSTANCE.createManagementTrace();
			}
			ctx.setManagementTrace(managementTrace);
		}
	}
	
	public void updateEndToEndTrace() throws EolRuntimeException {
		IModelFlowConfiguration config = module.getConfiguration();

		if (module.getContext().isEndToEndTracing() && config.isSaveEndToEndTraces()) {
			URI fileUri = null;
			if (config.getEndToEndTraceFile() != null) {
				fileUri = URI.createFileURI(config.getEndToEndTraceFile().getAbsolutePath());
			} else {
				// TODO propose a location
			}
			if (fileUri !=null) {
				Resource traceResource = getResourceSet().createResource(fileUri);
				IModelFlowContext ctx = module.getContext();
				ManagementTrace managementTrace = ctx.getManagementTrace();
				traceResource.getContents().add(managementTrace);				
				try {
					Map<String, String> options = new HashMap<>();
					options.put(XMLResource.OPTION_PROCESS_DANGLING_HREF, XMLResource.OPTION_PROCESS_DANGLING_HREF_RECORD);
					traceResource.save(options);
					String msg = String.format("End-to-End Trace saved in: %s", fileUri.toFileString());
					ctx.getOutputStream().println(msg);
				} catch (Exception e) {
					ctx.getErrorStream().println("Unable to save end-to-end trace.");
					ctx.getErrorStream().println(e.getMessage());
					throw new EolRuntimeException(e);
				} 
			}
		}
	}
	
	public void prepareExecutionTrace() throws EolRuntimeException {
		IModelFlowConfiguration config = module.getConfiguration();
		IModelFlowContext ctx = module.getContext();
		ExecutionTrace executionTrace = ctx.getExecutionTrace();
		File file = config.getExecutionTraceFile();
		if (file != null && file.exists()) {
			URI fileUri = URI.createFileURI(file.getAbsolutePath());
			Resource traceResource = getResourceSet().createResource(fileUri);
			try {
				traceResource.load(null);
				executionTrace = (ExecutionTrace) traceResource.getContents().get(0);
			} catch (Exception e) {
				ctx.getWarningStream().println("Unable to load execution trace. Creating new one.%n");
			}
		} 
		if (executionTrace == null) {
			executionTrace = ExecutionTraceFactoryImpl.eINSTANCE.createExecutionTrace();
		}
		ctx.setExecutionTrace(executionTrace);
	}
	
	public void updateExecutionTrace() throws EolRuntimeException {
		IModelFlowConfiguration config = module.getConfiguration();
		IModelFlowContext ctx = module.getContext();

		File file = config.getExecutionTraceFile();
		if (file != null) {
			URI fileUri = URI.createFileURI(file.getAbsolutePath());
			Resource traceResource = getResourceSet().createResource(fileUri);
			
			ExecutionTrace executionTrace = ctx.getExecutionTrace();
			traceResource.getContents().add(executionTrace);	
			
			try {
				Map<String, String> options = new HashMap<>();
				options.put(XMLResource.OPTION_PROCESS_DANGLING_HREF, XMLResource.OPTION_PROCESS_DANGLING_HREF_RECORD);
				traceResource.save(options);
				String msg = String.format("Execution trace saved in: %s", fileUri.toFileString());
				ctx.getOutputStream().println(msg);				
			} catch (Exception e) {
				ctx.getErrorStream().println("Unable to save execution trace.");
				ctx.getErrorStream().println(e.getMessage());
				throw new EolRuntimeException(e);
			}
		}
		
	}
}
