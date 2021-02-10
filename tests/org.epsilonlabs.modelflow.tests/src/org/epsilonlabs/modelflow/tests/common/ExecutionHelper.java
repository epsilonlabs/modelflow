/**
 * 
 */
package org.epsilonlabs.modelflow.tests.common;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.epsilonlabs.modelflow.IModelFlowModule;
import org.epsilonlabs.modelflow.ModelFlowModule;
import org.epsilonlabs.modelflow.dom.IWorkflow;
import org.epsilonlabs.modelflow.dom.impl.DomPackage;
import org.epsilonlabs.modelflow.execution.trace.ExecutionTrace;
import org.epsilonlabs.modelflow.execution.trace.ExecutionTracePackage;
import org.epsilonlabs.modelflow.management.trace.ManagementTrace;
import org.epsilonlabs.modelflow.management.trace.ManagementTracePackage;

/**
 * @author Betty Sanchez
 *
 */
public class ExecutionHelper {

	protected static final String USER_DIR= System.getProperty("user.dir");
	protected static final String TARGET = "target";
	
	protected final IModelFlowModule module;
	
	public ExecutionHelper(IModelFlowModule module) {
		this.module = module;
	}
	
	public void saveModels(String outputName) {
		ResourceSet rs = new ResourceSetImpl();		
		Registry packageRegistry = rs.getPackageRegistry();
		packageRegistry.put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
		packageRegistry.put(DomPackage.eNS_URI, DomPackage.eINSTANCE);
		packageRegistry.put(ExecutionTracePackage.eNS_URI, ExecutionTracePackage.eINSTANCE);
		packageRegistry.put(ManagementTracePackage.eNS_URI, ManagementTracePackage.eINSTANCE);
		
		Map<String, Object> extensionToFactoryMap = rs.getResourceFactoryRegistry().getExtensionToFactoryMap();
		extensionToFactoryMap.put(ModelFlowModule.EXECUITON_TRACE_EXTENSION, new XMIResourceFactoryImpl());
		extensionToFactoryMap.put(ModelFlowModule.EXTENSION, new XMIResourceFactoryImpl());
		extensionToFactoryMap.put(ModelFlowModule.MANAGEMENT_TRACE_EXTENSION, new XMIResourceFactoryImpl());
		
		ExecutionTrace executionTrace = module.getContext().getExecutionTrace();
		IWorkflow declaredWorkflow = module.getWorkflow();
		ManagementTrace managementTrace = module.getContext().getManagementTrace();
		
		String wfLocation= Paths.get(USER_DIR, TARGET, "wf", outputName + "." + ModelFlowModule.EXTENSION).toAbsolutePath().toString();
		String execLocation= Paths.get(USER_DIR, TARGET, "wf", outputName + "." + ModelFlowModule.EXECUITON_TRACE_EXTENSION).toAbsolutePath().toString();
		String mgmLocation= Paths.get(USER_DIR, TARGET, "wf", outputName + "."+ ModelFlowModule.MANAGEMENT_TRACE_EXTENSION).toAbsolutePath().toString();
		
		Resource wfR = rs.createResource(URI.createFileURI(wfLocation));
		wfR.getContents().add(declaredWorkflow);
		try {
			wfR.save(null);
		} catch (Exception e) {
			e.printStackTrace();
			fail("UNABLE TO SAVE WORKFLOW");
		}
		
		Resource mgmR = rs.createResource(URI.createFileURI(mgmLocation));
		mgmR.getContents().add(managementTrace);
		try {
			mgmR.save(null);
		} catch (Exception e) {
			e.printStackTrace();
			fail("UNABLE TO SAVE MANAGEMENT TRACE");
		}
		
		Resource traceR = rs.createResource(URI.createFileURI(execLocation));
		traceR.getContents().add(executionTrace);
		try {
			traceR.save(null);
		} catch (Exception e) {
			e.printStackTrace();
			fail("UNABLE TO SAVE EXECUTION TRACE");
		}
	}

	public void saveGraphs(String outputName){
		String dg = module.getContext().getScheduler().getDependencyGraph().toString();
		String eg = module.getContext().getScheduler().getExecutionGraph().toString();
		String dgLoc= Paths.get(USER_DIR, TARGET, "wf", outputName + "-dg.dot").toAbsolutePath().toString();
		String egLoc= Paths.get(USER_DIR, TARGET, "wf", outputName + "-eg.dot").toAbsolutePath().toString();
		try {
			FileUtils.writeStringToFile(new File(dgLoc), dg, Charset.defaultCharset());
			FileUtils.writeStringToFile(new File(egLoc), eg, Charset.defaultCharset());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
