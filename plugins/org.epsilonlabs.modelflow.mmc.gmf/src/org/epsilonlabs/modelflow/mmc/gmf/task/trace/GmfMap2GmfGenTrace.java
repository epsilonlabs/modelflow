/**
 * 
 */
package org.epsilonlabs.modelflow.mmc.gmf.task.trace;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.codegen.gmfgen.GMFGenPackage;
import org.eclipse.gmf.codegen.gmfgen.GenChildNode;
import org.eclipse.gmf.codegen.gmfgen.GenLink;
import org.eclipse.gmf.codegen.gmfgen.GenMetricRule;
import org.eclipse.gmf.codegen.gmfgen.GenTopLevelNode;
import org.eclipse.gmf.internal.bridge.History;
import org.eclipse.gmf.mappings.GMFMapPackage;
import org.eclipse.gmf.mappings.LinkMapping;
import org.eclipse.gmf.mappings.MetricRule;
import org.eclipse.gmf.mappings.NodeMapping;
import org.epsilonlabs.modelflow.dom.AbstractResource;
import org.epsilonlabs.modelflow.management.trace.ManagementTraceBuilder;
import org.epsilonlabs.modelflow.management.trace.Trace;
import org.epsilonlabs.modelflow.mmc.gmf.task.GmfMap2GmfGenTask;
import org.epsilonlabs.modelflow.mmc.gmf.task.helper.SimplifiedGmfMap2GmfGen;

/**
 * @author Betty Sanchez
 *
 */
public class GmfMap2GmfGenTrace {

	private GmfMap2GmfGenTask task;
	private SimplifiedGmfMap2GmfGen transformation;
	private List<Trace> traces = new ArrayList<>();

	public GmfMap2GmfGenTrace(GmfMap2GmfGenTask task, SimplifiedGmfMap2GmfGen transformation) {
		this.task = task;
		this.transformation = transformation;
	}

	@SuppressWarnings({ "restriction", "unchecked" })
	public GmfMap2GmfGenTrace init() {
		History trace = transformation.getTrace();
		//DiagramGenModelTransformer diagramTransformer = transformation.getDiagramTransformer();
		try {
			Map<NodeMapping, GenTopLevelNode> myTopNodeMap = (Map<NodeMapping, GenTopLevelNode>) getField(
					"myTopNodeMap", History.class, trace);
			Map<NodeMapping, Set<GenChildNode>> myNodeMap = (Map<NodeMapping, Set<GenChildNode>>) getField("myNodeMap",
					History.class, trace);
			Map<LinkMapping, GenLink> myLinkMap = (Map<LinkMapping, GenLink>) getField("myLinkMap", History.class,
					trace);
			Map<MetricRule, GenMetricRule> myMetricMap = (Map<MetricRule, GenMetricRule>) getField("myMetricMap",
					History.class, trace);

			AbstractResource sourceRes = task.getResources().get(GMFMapPackage.eNS_URI).getResource();
			AbstractResource targetRes = task.getResources().get(GMFGenPackage.eNS_URI).getResource();
			
			myTopNodeMap.entrySet().stream().map(e -> createTrace(sourceRes, targetRes, e.getKey(), e.getValue(), "topNode"))
					.collect(Collectors.toCollection(() -> traces));
			myNodeMap.entrySet().stream().map(e -> createTrace(sourceRes, targetRes, e.getKey(), e.getValue(), "node"))
					.collect(Collectors.toCollection(() -> traces));
			myLinkMap.entrySet().stream().map(e -> createTrace(sourceRes, targetRes, e.getKey(), e.getValue(), "link"))
					.collect(Collectors.toCollection(() -> traces));
			myMetricMap.entrySet().stream().map(e -> createTrace(sourceRes, targetRes, e.getKey(), e.getValue(), "metric"))
					.collect(Collectors.toCollection(() -> traces));
			
			// Object myPaletteProcessor = getField("myPaletteProcessor",
			// DiagramGenModelTransformer.class, diagramTransformer);
			// Object myNavigatorProcessor = getField("myNavigatorProcessor",
			// DiagramGenModelTransformer.class, diagramTransformer);
			// Object myPropertySheetProcessor = getField("myPropertySheetProcessor",
			// DiagramGenModelTransformer.class, diagramTransformer);

		} catch (Exception e) {
			// TODO: handle exception
		}
		return this;
	}

	protected Trace createTrace(AbstractResource sourceRes, AbstractResource targetRes, EObject key, Object value, String rule) {
		String sourceId = key.eResource().getURIFragment(key);
		if (value instanceof EObject) {			
			String targetId = ((EObject) value).eResource().getURIFragment((EObject) value);
			return new ManagementTraceBuilder()
				.addSourceModelElement(sourceId, sourceRes, "")
				.addTargetModelElement(targetId, targetRes, "")
				.managementLink("transform", rule)
				.build();
		} else if (value instanceof Collection) {
			ManagementTraceBuilder builder = new ManagementTraceBuilder()
					.addSourceModelElement(sourceId, sourceRes, "")
					.managementLink("transform", rule);
			((Collection<?>) value).forEach(e -> {
				if (value instanceof EObject) {
					String targetId = ((EObject) value).eResource().getURIFragment((EObject) value);
					builder.addTargetModelElement(targetId, targetRes, "");
				}
			});
			return builder.build();
		}
		return null;
	}

	protected Object getField(String name, Class<?> clazz, Object obj)
			throws NoSuchFieldException, IllegalAccessException {
		Field topNodeField = clazz.getDeclaredField(name);
		topNodeField.setAccessible(true);
		return topNodeField.get(obj);

	}

	/**
	 * @return
	 */
	public Collection<Trace> getTraces() {
		return traces;
	}

}
