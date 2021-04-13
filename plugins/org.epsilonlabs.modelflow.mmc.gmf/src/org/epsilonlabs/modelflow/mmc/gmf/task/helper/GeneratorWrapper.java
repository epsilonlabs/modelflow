package org.epsilonlabs.modelflow.mmc.gmf.task.helper;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.codegen.util.CodeGenUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.gmf.codegen.gmfgen.Behaviour;
import org.eclipse.gmf.codegen.gmfgen.CustomParser;
import org.eclipse.gmf.codegen.gmfgen.ElementType;
import org.eclipse.gmf.codegen.gmfgen.ExpressionLabelParser;
import org.eclipse.gmf.codegen.gmfgen.ExternalParser;
import org.eclipse.gmf.codegen.gmfgen.FeatureLinkModelFacet;
import org.eclipse.gmf.codegen.gmfgen.GMFGenFactory;
import org.eclipse.gmf.codegen.gmfgen.GMFGenPackage;
import org.eclipse.gmf.codegen.gmfgen.GenAction;
import org.eclipse.gmf.codegen.gmfgen.GenApplication;
import org.eclipse.gmf.codegen.gmfgen.GenChildContainer;
import org.eclipse.gmf.codegen.gmfgen.GenChildLabelNode;
import org.eclipse.gmf.codegen.gmfgen.GenChildNode;
import org.eclipse.gmf.codegen.gmfgen.GenCommonBase;
import org.eclipse.gmf.codegen.gmfgen.GenCompartment;
import org.eclipse.gmf.codegen.gmfgen.GenContainerBase;
import org.eclipse.gmf.codegen.gmfgen.GenContributionItem;
import org.eclipse.gmf.codegen.gmfgen.GenContributionManager;
import org.eclipse.gmf.codegen.gmfgen.GenCustomAction;
import org.eclipse.gmf.codegen.gmfgen.GenCustomGeneratorExtension;
import org.eclipse.gmf.codegen.gmfgen.GenCustomPreferencePage;
import org.eclipse.gmf.codegen.gmfgen.GenCustomPropertyTab;
import org.eclipse.gmf.codegen.gmfgen.GenCustomTemplateInput;
import org.eclipse.gmf.codegen.gmfgen.GenDiagram;
import org.eclipse.gmf.codegen.gmfgen.GenEditorGenerator;
import org.eclipse.gmf.codegen.gmfgen.GenEditorView;
import org.eclipse.gmf.codegen.gmfgen.GenExpressionInterpreter;
import org.eclipse.gmf.codegen.gmfgen.GenExpressionProviderBase;
import org.eclipse.gmf.codegen.gmfgen.GenExpressionProviderContainer;
import org.eclipse.gmf.codegen.gmfgen.GenExternalNodeLabel;
import org.eclipse.gmf.codegen.gmfgen.GenFixedInputsTemplateInvocation;
import org.eclipse.gmf.codegen.gmfgen.GenLanguage;
import org.eclipse.gmf.codegen.gmfgen.GenLink;
import org.eclipse.gmf.codegen.gmfgen.GenLinkLabel;
import org.eclipse.gmf.codegen.gmfgen.GenNavigatorChildReference;
import org.eclipse.gmf.codegen.gmfgen.GenNode;
import org.eclipse.gmf.codegen.gmfgen.GenNodeLabel;
import org.eclipse.gmf.codegen.gmfgen.GenParserImplementation;
import org.eclipse.gmf.codegen.gmfgen.GenPreferencePage;
import org.eclipse.gmf.codegen.gmfgen.GenPropertyTab;
import org.eclipse.gmf.codegen.gmfgen.GenSharedContributionItem;
import org.eclipse.gmf.codegen.gmfgen.GenStandardPreferencePage;
import org.eclipse.gmf.codegen.gmfgen.GenTemplateInvocation;
import org.eclipse.gmf.codegen.gmfgen.GenTemplateInvocationBase;
import org.eclipse.gmf.codegen.gmfgen.GenTopLevelNode;
import org.eclipse.gmf.codegen.gmfgen.GenVisualEffect;
import org.eclipse.gmf.codegen.gmfgen.InitDiagramAction;
import org.eclipse.gmf.codegen.gmfgen.MetamodelType;
import org.eclipse.gmf.codegen.gmfgen.OpenDiagramBehaviour;
import org.eclipse.gmf.codegen.gmfgen.PredefinedEnumParser;
import org.eclipse.gmf.codegen.gmfgen.PredefinedParser;
import org.eclipse.gmf.codegen.gmfgen.SpecializationType;
import org.eclipse.gmf.codegen.gmfgen.StandardPreferencePages;
import org.eclipse.gmf.codegen.gmfgen.TypeLinkModelFacet;
import org.eclipse.gmf.codegen.util.BinaryEmitters;
import org.eclipse.gmf.codegen.util.CodegenEmitters;
import org.eclipse.gmf.common.UnexpectedBehaviourException;
import org.eclipse.gmf.internal.common.codegen.JavaClassEmitter;
import org.eclipse.gmf.internal.common.codegen.TextEmitter;
import org.eclipse.gmf.internal.common.codegen.TextMerger;
import org.eclipse.ocl.ecore.OCL;
import org.eclipse.ocl.ecore.OCL.Helper;
import org.eclipse.ocl.ecore.OCLExpression;
import org.eclipse.ocl.options.ParsingOptions;

@SuppressWarnings({ "restriction", "deprecation" })
public class GeneratorWrapper extends AbstractGeneratorWrapper  {

	protected GenEditorGenerator myEditorGen;

	protected GenDiagram myDiagram;

	protected CodegenEmitters myEmitters;

	protected BinaryEmitters myBinaryEmmiters;

	public GeneratorWrapper(GenEditorGenerator genModel, CodegenEmitters emitters) {
		this(genModel, emitters, new BinaryEmitters());
	}

	public GeneratorWrapper(GenEditorGenerator genModel, CodegenEmitters emitters, BinaryEmitters binaryEmitters) {
		myEditorGen = genModel;
		myDiagram = genModel.getDiagram();
		myEmitters = emitters;
		myBinaryEmmiters = binaryEmitters;
	}

	@Override
	public void clean(){
		super.clean();
		myEditorGen = null;
		myDiagram = null;
		myEmitters = null;
		myBinaryEmmiters = null;
	}
	
	@Override
	protected TextMerger createMergeService() {
		TextMerger service = myEmitters.createMergeService();
		if (service != null) {
			return service;
		}
		return super.createMergeService();
	}

	@Override
	protected void customRun() throws InterruptedException, UnexpectedBehaviourException {
		final Path pluginDirectory = new Path(myEditorGen.getPluginDirectory());
		initializeEditorProject(pluginDirectory, guessProjectLocation(pluginDirectory.segment(0)),
				Collections.<IProject>emptyList());

		if (myEditorGen.getModelAccess() != null) {
			final Map<String, Object> singletonMap = Collections.<String, Object>singletonMap("DynamicModelAccess", myEditorGen.getModelAccess());
			try {
				final Method m = CodegenEmitters.class.getMethod("setGlobals", Map.class);
				m.setAccessible(true);
				m.invoke(myEmitters, singletonMap);
			} catch (Exception e) {
				throw new UnexpectedBehaviourException(e);
			}
			
			generateModelAccessFacility();
		}

		// draft for messages
		generateExternalizationSupport();

		// commands
		generateReorientLinkViewCommand();

		// edit helpers
		generateBaseEditHelper();

		// parsers
		generateParsers();

		//
		// Nodes
		for (GenTopLevelNode node : myDiagram.getTopLevelNodes()) {
			generateNode(node);
		}
		for (GenChildNode node : myDiagram.getChildNodes()) {
			if (node instanceof GenChildLabelNode) {
				generateChildLabelNode((GenChildLabelNode) node);
			} else {
				generateNode(node);
			}
		}
		//
		// Compartments
		for (GenCompartment compartment : myDiagram.getCompartments()) {
			generateCompartmentEditPart(compartment);
			generateCompartmentItemSemanticEditPolicy(compartment);
			if (compartment.needsCanonicalEditPolicy()) {
				generateChildContainerCanonicalEditPolicy(compartment);
			}
		}
		//
		// Links
		for (GenLink next : myDiagram.getLinks()) {
			generateEditSupport(next);
			generateLinkEditPart(next);
			generateBehaviours(next);
			generateLinkItemSemanticEditPolicy(next);
			if (next.getModelFacet() != null
					&& (next.isIncomingCreationAllowed() || next.isOutgoingCreationAllowed())) {
				generateCreateLinkCommand(next);
			}
			if (next.getModelFacet() instanceof TypeLinkModelFacet) {
				if (next.isTargetReorientingAllowed() || next.isSourceReorientingAllowed()) {
					generateReorientLinkCommand(next);
				}
			} else if (next.getModelFacet() instanceof FeatureLinkModelFacet) {
				if (next.isTargetReorientingAllowed() || next.isSourceReorientingAllowed()) {
					generateReorientRefLinkCommand(next);
				}
			}
			for (GenLinkLabel label : next.getLabels()) {
				generateLinkLabelEditPart(label);
			}
			generateVisualEffectEditPolicies(next);
		}
		generateDiagram();
		//
		// common edit parts, edit policies and providers
		generateBaseItemSemanticEditPolicy();
		generateTextSelectionEditPolicy();
		generateTextNonResizableEditPolicy();
		generateEditPartFactory();
		generateElementInitializers();
		generateElementTypes();
		generateViewProvider();
		generateEditPartProvider();
		generateModelingAssistantProvider();
		generateIconProvider();
		generateParserProvider();
		if (myDiagram.isValidationEnabled() || myEditorGen.hasAudits()) {
			generateValidationProvider();
			generateValidateAction();
			if (myEditorGen.getApplication() == null) {
				generateMarkerNavigationProvider();
			} else {
				generateValidationMarker();
			}
			if (myDiagram.isValidationDecorators()) {
				generateValidationDecoratorProvider();
			}
		}
		if (myEditorGen.getMetrics() != null) {
			generateMetricProvider();
		}
		if (myEditorGen.getExpressionProviders() != null) {
			generateExpressionProviders();
		}

		// preferences
		generatePreferenceInitializer();
		generatePreferencePages(myDiagram.getPreferencePages());

		// editor
		generatePalette();
		generateDiagramEditorUtil();
		generateVisualIDRegistry();
		generateCreationWizard();
		generateCreationWizardPage();
		generateDeleteElementAction();
		generateDiagramEditorContextMenuProvider();
		generateEditor();
		generateActionBarContributor();
		generateMatchingStrategy();
		generateDocumentProvider();
		if (myDiagram.generateInitDiagramAction()
				|| myDiagram.generateCreateShortcutAction() /* FIXME use another condition here */) {
			generateModelElementSelectionPage();
		}
		if (myDiagram.generateInitDiagramAction() /* FIXME use another condition here */) {
			// FIXME HACK!!! until I decide how to contribute action against IFile
			InitDiagramAction fakeAction = GMFGenFactory.eINSTANCE.createInitDiagramAction();
			fakeAction.setQualifiedClassName(myDiagram.getInitDiagramFileActionQualifiedClassName());
			doGenerateJavaClass(myEmitters.getPredefinedActionEmitter(), fakeAction.getQualifiedClassName(), fakeAction,
					myEditorGen);
			generateNewDiagramFileWizard();
		}
		if (myDiagram.generateCreateShortcutAction() /* FIXME use another condition here */) {
			generateCreateShortcutDecorationsCommand();
			if (myEditorGen.getApplication() == null) {
				generateElementChooser();
			} else {
				generateShortcutCreationWizard();
			}
		}
		//
		// Updater
		generateDiagramUpdater();
		generateUpdateCommand();
		generateNodeDescriptor();
		generateLinkDescriptor();
		//
		// Navigator
		if (myEditorGen.getNavigator() != null) {
			generateNavigatorContentProvider();
			generateNavigatorLabelProvider();
			generateNavigatorLinkHelper();
			generateNavigatorSorter();
			generateNavigatorActionProvider();
			generateAbstractNavigatorItem();
			generateNavigatorGroup();
			generateNavigatorItem();
			generateNavigatorGroupIcons();
			if (myEditorGen.getDomainGenModel() != null
					&& myEditorGen.getNavigator().isGenerateDomainModelNavigator()) {
				generateDomainNavigatorContentProvider();
				generateDomainNavigatorLabelProvider();
				generateDomainNavigatorItem();
				generateURIEditorInputTester();
			}
		}
		if (myEditorGen.getPropertySheet() != null) {
			generatePropertySheetSections();
		}
		if (myDiagram.generateShortcutIcon()) {
			generateShortcutIcon();
			generateShortcutsDecoratorProvider();
			generateShortcutPropertyTester();
		}
		if (isPathInsideGenerationTarget(myDiagram.getCreationWizardIconPathX())) {
			// at the moment this may produce path that reference generated icon file, thus
			// skip generation if the path is relative
			generateDiagramIcon(myDiagram.getCreationWizardIconPathX());
		}
		if (isPathInsideGenerationTarget(myEditorGen.getEditor().getIconPathX())) {
			// at the moment this may produce path that reference generated icon file, thus
			// skip generation if the path is relative
			generateDiagramIcon(myEditorGen.getEditor().getIconPathX());
		}
		generateWizardBanner();
		generatePlugin();
		generateApplication();
		generateActions();
		generateCustomExtensions();
	}

	protected static boolean isPathInsideGenerationTarget(String path) {
		assert path != null;
		Path p = new Path(path);
		return !p.isAbsolute() && !p.segment(0).equals(".."); //$NON-NLS-1$
	}

	// Diagram itself as a diagram element - editpart, editpolicies
	protected void generateDiagram() throws UnexpectedBehaviourException, InterruptedException {
		generateBehaviours(myDiagram);
		if (myDiagram.needsCanonicalEditPolicy()) {
			generateDiagramCanonicalEditPolicy();
		}
		generateDiagramItemSemanticEditPolicy();
		generateEditSupport(myDiagram);
		generateDiagramEditPart();
		generateEditPartModelingAssistantProvider(myDiagram);
	}

	protected void generateNode(GenNode node) throws UnexpectedBehaviourException, InterruptedException {
		generateNodeItemSemanticEditPolicy(node);
		if (node.getModelFacet() != null) {
			generateCreateNodeCommand(node);
		}
		generateEditSupport(node);
		generateNodeEditPart(node);
		generateEditPartModelingAssistantProvider(node);
		generateBehaviours(node);
		if (node.needsCanonicalEditPolicy()) {
			generateChildContainerCanonicalEditPolicy(node);
		}
		if (needsGraphicalNodeEditPolicy(node)) {
			generateGraphicalNodeEditPolicy(node);
		}
		for (GenNodeLabel label : node.getLabels()) {
			if (label instanceof GenExternalNodeLabel) {
				GenExternalNodeLabel extLabel = (GenExternalNodeLabel) label;
				generateExternalNodeLabelEditPart(extLabel);
			} else {
				generateNodeLabelEditPart(label);
			}
		}
		generateVisualEffectEditPolicies(node);
	}

	protected void generateVisualEffectEditPolicies(GenCommonBase commonBase) throws InterruptedException {
		rule = "VisualEffectEditPolicies";
		for (Behaviour behaviour : commonBase.getBehaviour()) {
			if (behaviour instanceof GenVisualEffect) {
				GenVisualEffect visualEffect = (GenVisualEffect) behaviour;
				generateVisualEffectEditPolicy(visualEffect);
			}
		}
	}

	protected void generateVisualEffectEditPolicy(GenVisualEffect visualEffect) throws InterruptedException {
		rule = "VisualEffectEditPolicy";
		
		doGenerateJavaClass(myEmitters.getVisualEffectEditPolicyEmitter(),
				visualEffect.getEditPolicyQualifiedClassName(), visualEffect);
	}

	protected void generateChildLabelNode(GenChildLabelNode child)
			throws UnexpectedBehaviourException, InterruptedException {
		rule = "ChildLabelNode";
		generateNodeItemSemanticEditPolicy(child);
		if (child.getModelFacet() != null) {
			generateCreateNodeCommand(child);
		}
		generateEditSupport(child);
		generateBehaviours(child);
		generateChildNodeLabelEditPart(child);
		generateEditPartModelingAssistantProvider(child);
	}

	// commands

	protected void generateReorientLinkViewCommand() throws UnexpectedBehaviourException, InterruptedException {
		rule = "ReorientLinkViewCommand";
		for (GenNode n : myDiagram.getAllNodes()) {
			if (needsGraphicalNodeEditPolicy(n)) {
				// ReorientConnectionViewCommand is neccessary only when there's
				// GraphicalNodeEditPolicy
				// XXX consider using some general modeling facility for reused code like that
				// one (there's a bug for this)
				doGenerateJavaClass(myEmitters.getReorientLinkViewCommandEmitter(),
						myDiagram.getReorientConnectionViewCommandQualifiedClassName(), myDiagram);
				break;
			}
		}
	}

	protected void generateCreateNodeCommand(GenNode node) throws InterruptedException, UnexpectedBehaviourException {
		rule = "CreateNodeCommand";
		doGenerateJavaClass(myEmitters.getCreateNodeCommandEmitter(), node.getCreateCommandQualifiedClassName(), node);
	}

	protected void generateCreateLinkCommand(GenLink link) throws UnexpectedBehaviourException, InterruptedException {
		rule = "CreateLinkCommand";
		doGenerateJavaClass(myEmitters.getCreateLinkCommandEmitter(), link.getCreateCommandQualifiedClassName(), link);
	}

	protected void generateReorientLinkCommand(GenLink link) throws UnexpectedBehaviourException, InterruptedException {
		rule = "ReorientLinkCommand";
		doGenerateJavaClass(myEmitters.getReorientLinkCommandEmitter(), link.getReorientCommandQualifiedClassName(),
				link);
	}

	protected void generateReorientRefLinkCommand(GenLink link)
			throws UnexpectedBehaviourException, InterruptedException {
		rule = "ReorientRefLinkCommand";
		doGenerateJavaClass(myEmitters.getReorientRefLinkCommandEmitter(), link.getReorientCommandQualifiedClassName(),
				link);
	}

	protected void generateCreateShortcutDecorationsCommand()
			throws InterruptedException, UnexpectedBehaviourException {
		rule = "CreateShortcutDecorationsCommand";
		doGenerateJavaClass(myEmitters.getCreateShortcutDecorationsCommandEmitter(),
				myDiagram.getCreateShortcutDecorationsCommandQualifiedClassName(), myDiagram);
	}

	// helpers

	protected void generateBaseEditHelper() throws UnexpectedBehaviourException, InterruptedException {
		rule = "BaseEditHelper";
		doGenerateJavaClass(myEmitters.getBaseEditHelperEmitter(), myDiagram.getBaseEditHelperQualifiedClassName(),
				myDiagram);
	}

	protected void generateEditSupport(GenCommonBase diagramElement)
			throws UnexpectedBehaviourException, InterruptedException {
		rule = "EditSupport";
		ElementType genType = diagramElement.getElementType();
		if (genType.isDefinedExternally()) {
			return;
		}
		if (genType instanceof SpecializationType) {
			generateEditHelperAdvice((SpecializationType) genType);
		} else if (genType instanceof MetamodelType) {
			generateEditHelper((MetamodelType) genType);
		}
	}

	protected void generateEditHelper(MetamodelType genType) throws UnexpectedBehaviourException, InterruptedException {
		rule = "EditHelper";
		doGenerateJavaClass(myEmitters.getEditHelperEmitter(), genType.getEditHelperQualifiedClassName(), genType);
	}

	protected void generateEditHelperAdvice(SpecializationType genType)
			throws UnexpectedBehaviourException, InterruptedException {
		rule = "EditHelperAdvice";
		if (!genType.eIsSet(GMFGenPackage.eINSTANCE.getSpecializationType_EditHelperAdviceClassName())) {
			return;
		}
		doGenerateJavaClass(myEmitters.getEditHelperAdviceEmitter(), genType.getEditHelperAdviceQualifiedClassName(),
				genType);
	}

	// parts

	protected void generateDiagramEditPart() throws UnexpectedBehaviourException, InterruptedException {
		rule = "DiagramEditPart";
		doGenerateJavaClass(myEmitters.getDiagramEditPartEmitter(), myDiagram.getEditPartQualifiedClassName(),
				myDiagram);
	}

	protected void generateNodeEditPart(GenNode node) throws UnexpectedBehaviourException, InterruptedException {
		rule = "NodeEditPart";
		doGenerateJavaClass(myEmitters.getNodeEditPartEmitter(), node.getEditPartQualifiedClassName(), node);
	}

	protected void generateEditPartModelingAssistantProvider(GenContainerBase container)
			throws UnexpectedBehaviourException, InterruptedException {
		rule = "EditPartModelingAssistantProvider";
		JavaClassEmitter emitter = myEmitters.getNodeEditPartModelingAssistantProviderEmitter();
		doGenerate(emitter, container);
	}

	protected void generateNodeLabelEditPart(GenNodeLabel label)
			throws UnexpectedBehaviourException, InterruptedException {
		rule = "NodeLabelEditPart";
		doGenerateJavaClass(myEmitters.getNodeLabelEditPartEmitter(), label.getEditPartQualifiedClassName(), label);
	}

	protected void generateExternalNodeLabelEditPart(GenExternalNodeLabel label)
			throws UnexpectedBehaviourException, InterruptedException {
		rule = "ExternalNodeLabelEditPart";
		doGenerateJavaClass(myEmitters.getExternalNodeLabelEditPartEmitter(), label.getEditPartQualifiedClassName(),
				label);
	}

	protected void generateChildNodeLabelEditPart(GenChildLabelNode node)
			throws UnexpectedBehaviourException, InterruptedException {
		rule = "ChildNodeLabelEditPart";
		doGenerateJavaClass(myEmitters.getChildNodeLabelEditPartEmitter(), node.getEditPartQualifiedClassName(), node);
	}

	protected void generateCompartmentEditPart(GenCompartment compartment)
			throws UnexpectedBehaviourException, InterruptedException {
		rule = "CompartmentEditPart";
		doGenerateJavaClass(myEmitters.getCompartmentEditPartEmitter(), compartment.getEditPartQualifiedClassName(),
				compartment);
	}

	protected void generateLinkEditPart(GenLink link) throws UnexpectedBehaviourException, InterruptedException {
		rule = "LinkEditPart";
		doGenerateJavaClass(myEmitters.getLinkEditPartEmitter(), link.getEditPartQualifiedClassName(), link);
	}

	protected void generateLinkLabelEditPart(GenLinkLabel label)
			throws UnexpectedBehaviourException, InterruptedException {
		rule = "LinkLabelEditPart";
		doGenerateJavaClass(myEmitters.getLinkLabelEditPartEmitter(), label.getEditPartQualifiedClassName(), label);
	}

	protected void generateEditPartFactory() throws UnexpectedBehaviourException, InterruptedException {
		rule = "EditPartFactory";
		doGenerateJavaClass(myEmitters.getEditPartFactoryEmitter(), myDiagram.getEditPartFactoryQualifiedClassName(),
				myDiagram);
	}

	// edit policies

	protected void generateBaseItemSemanticEditPolicy() throws InterruptedException {
		rule = "BaseItemSemanticEditPolicy";
		Collection<GenCommonBase> allSemanticElements = new ArrayList<GenCommonBase>(myDiagram.getAllContainers());
		allSemanticElements.addAll(myDiagram.getLinks());
		boolean isSansDomainModel = true;
		for (Iterator<GenCommonBase> it = allSemanticElements.iterator(); it.hasNext() && isSansDomainModel;) {
			GenCommonBase nextCommonBase = it.next();
			if (!nextCommonBase.isSansDomain()) {
				isSansDomainModel = false;
			}
		}
		if (isSansDomainModel) {
			return;
		}
		doGenerateJavaClass(myEmitters.getBaseItemSemanticEditPolicyEmitter(),
				myDiagram.getBaseItemSemanticEditPolicyQualifiedClassName(), myDiagram);
	}

	/**
	 * Generate classes for behaviours specified for the diagram element. As part of
	 * its job, this method tries not to generate shared policies more than once.
	 */
	protected void generateBehaviours(GenCommonBase commonBase)
			throws UnexpectedBehaviourException, InterruptedException {
		rule = "Behaviours";
		for (OpenDiagramBehaviour behaviour : commonBase.getBehaviour(OpenDiagramBehaviour.class)) {
			if (behaviour.getSubject() == commonBase) { // extravagant way to check whether this behaviour is shared or
														// not
				generateOpenDiagramEditPolicy(behaviour);
			}
		}
	}

	protected void generateOpenDiagramEditPolicy(OpenDiagramBehaviour behaviour)
			throws UnexpectedBehaviourException, InterruptedException {
		rule = "OpenDiagramEditPolicy";
		internalGenerateJavaClass(myEmitters.getOpenDiagramEditPolicyEmitter(),
				behaviour.getEditPolicyQualifiedClassName(), behaviour);
	}

	protected void generateDiagramCanonicalEditPolicy() throws InterruptedException {
		rule = "DiagramCanonicalEditPolicy";
		internalGenerateJavaClass(myEmitters.getDiagramCanonicalEditPolicyEmitter(),
				myDiagram.getCanonicalEditPolicyQualifiedClassName(), myDiagram);
	}

	protected void generateChildContainerCanonicalEditPolicy(GenChildContainer genContainer)
			throws InterruptedException {
		rule = "ChildContainerCanonicalEditPolicy";
		doGenerateJavaClass(myEmitters.getChildContainerCanonicalEditPolicyEmitter(),
				genContainer.getCanonicalEditPolicyQualifiedClassName(), genContainer);
	}

	protected void generateDiagramItemSemanticEditPolicy() throws InterruptedException {
		rule = "DiagramItemSemanticEditPolicy";
		if (myDiagram.isSansDomain()) {
			return;
		}
		doGenerateJavaClass(myEmitters.getDiagramItemSemanticEditPolicyEmitter(),
				myDiagram.getItemSemanticEditPolicyQualifiedClassName(), myDiagram);
	}

	protected void generateCompartmentItemSemanticEditPolicy(GenCompartment genCompartment)
			throws InterruptedException {
		rule = "CompartmentItemSemanticEditPolicy";
		if (genCompartment.isSansDomain()) {
			return;
		}
		doGenerateJavaClass(myEmitters.getCompartmentItemSemanticEditPolicyEmitter(),
				genCompartment.getItemSemanticEditPolicyQualifiedClassName(), genCompartment);
	}

	protected void generateGraphicalNodeEditPolicy(GenNode genNode)
			throws UnexpectedBehaviourException, InterruptedException {
		rule = "GraphicalNodeEditPolicy";
		doGenerateJavaClass(myEmitters.getGraphicalNodeEditPolicyEmitter(),
				genNode.getGraphicalNodeEditPolicyQualifiedClassName(), genNode);
	}

	protected void generateNodeItemSemanticEditPolicy(GenNode genNode) throws InterruptedException {
		rule = "NodeItemSemanticEditPolicy";

		if (genNode.isSansDomain()) {
			return;
		}
		doGenerateJavaClass(myEmitters.getNodeItemSemanticEditPolicyEmitter(),
				genNode.getItemSemanticEditPolicyQualifiedClassName(), genNode);
	}

	protected void generateLinkItemSemanticEditPolicy(GenLink genLink) throws InterruptedException {
		rule = "LinkItemSemanticEditPolicy";
		if (genLink.isSansDomain()) {
			return;
		}
		doGenerateJavaClass(myEmitters.getLinkItemSemanticEditPolicyEmitter(),
				genLink.getItemSemanticEditPolicyQualifiedClassName(), genLink);
	}

	protected void generateTextSelectionEditPolicy() throws UnexpectedBehaviourException, InterruptedException {
		rule = "TextSelectionEditPolicy";
		doGenerateJavaClass(myEmitters.getTextSelectionEditPolicyEmitter(),
				myDiagram.getTextSelectionEditPolicyQualifiedClassName(), myDiagram);
	}

	protected void generateTextNonResizableEditPolicy() throws UnexpectedBehaviourException, InterruptedException {
		rule = "TextNonResizableEditPolicy";
		doGenerateJavaClass(myEmitters.getTextNonResizableEditPolicyEmitter(),
				myDiagram.getTextNonResizableEditPolicyQualifiedClassName(), myDiagram);
	}

	// preferences

	protected void generatePreferenceInitializer() throws UnexpectedBehaviourException, InterruptedException {
		rule = "PreferenceInitializer";
		JavaClassEmitter emitter = myEmitters.getPreferenceInitializerEmitter();
		doGenerate(emitter, myDiagram);
	}

	protected void generatePreferencePages(List<GenPreferencePage> pages)
			throws UnexpectedBehaviourException, InterruptedException {
		rule = "PreferencePages";
		for (GenPreferencePage p : pages) {
			if (p instanceof GenCustomPreferencePage) {
				if (((GenCustomPreferencePage) p).isGenerateBoilerplate()) {
					doGenerateJavaClass(myEmitters.getCustomPreferencePageEmitter(), p.getQualifiedClassName(), p);
				}
			} else if (p instanceof GenStandardPreferencePage) {
				if (((GenStandardPreferencePage) p).getKind() != StandardPreferencePages.PATHMAPS_LITERAL) {
					doGenerateJavaClass(myEmitters.getStandardPreferencePageEmitter(), p.getQualifiedClassName(), p);
				}
			} else {
				throw new UnexpectedBehaviourException("No idea how to handle GenPreferencePage subclass:" + p);
			}
			generatePreferencePages(p.getChildren());
		}
	}

	protected void generateParsers() throws UnexpectedBehaviourException, InterruptedException {
		rule = "Parsers";
		if (myEditorGen.getLabelParsers() == null) {
			return;
		}
		boolean needsAbstractParser = false;
		for (GenParserImplementation pi : myEditorGen.getLabelParsers().getImplementations()) {
			if (pi instanceof PredefinedParser) {
				needsAbstractParser = true;
				doGenerateJavaClass(myEmitters.getPredefinedParserEmitter(),
						((PredefinedParser) pi).getQualifiedClassName(), pi);
			} else if (pi instanceof PredefinedEnumParser) {
				needsAbstractParser = true;
			} else if (pi instanceof CustomParser && ((CustomParser) pi).isGenerateBoilerplate()) {
				doGenerateJavaClass(myEmitters.getCustomParserEmitter(), ((CustomParser) pi).getQualifiedName(), pi);
			} else if (pi instanceof ExpressionLabelParser) {
				doGenerateJavaClass(myEmitters.getExpressionLabelParserEmitter(),
						((ExpressionLabelParser) pi).getQualifiedClassName(), pi);
			}
		}
		if (needsAbstractParser) {
			JavaClassEmitter emitter = myEmitters.getAbstractParserEmitter();
			doGenerateJavaClass(emitter, myEmitters.getAbstractParserName(myEditorGen.getLabelParsers()),
					myEditorGen.getLabelParsers());
		}
	}

	// providers

	protected void generateParserProvider() throws UnexpectedBehaviourException, InterruptedException {
		rule = "ParserProvider";
		if (myEditorGen.getLabelParsers() != null
				&& (myEditorGen.getLabelParsers().isExtensibleViaService() || existsNonExternalParser())) {
			doGenerateJavaClass(myEmitters.getParserProviderEmitter(),
					myEditorGen.getLabelParsers().getQualifiedClassName(), myEditorGen.getLabelParsers());
		}
	}

	// if there's no other parser than external, and provider is not contributed as
	// a Service -
	// no need to generate class (only get() method would be there)
	// XXX although adopters might want to change the logic - what if they generate
	// smth reasonable?
	// or if I add sort of getDescriptionParser common access method there?
	protected boolean existsNonExternalParser() {
		for (GenParserImplementation pi : myEditorGen.getLabelParsers().getImplementations()) {
			if (false == pi instanceof ExternalParser) {
				return true;
			}
		}
		return false;
	}

	protected void generateElementInitializers() throws UnexpectedBehaviourException, InterruptedException {
		rule = "ElementInitializers";
		doGenerateJavaClass(myEmitters.getElementInitializersEmitter(),
				myDiagram.getElementInitializersPackageName() + '.' + myDiagram.getElementInitializersClassName(),
				myDiagram);
	}

	protected void generateElementTypes() throws UnexpectedBehaviourException, InterruptedException {
		rule = "ElementTypes";
		doGenerateJavaClass(myEmitters.getElementTypesEmitter(), myDiagram.getElementTypesQualifiedClassName(),
				myDiagram);
	}

	protected void generateViewProvider() throws InterruptedException {
		rule = "ViewProvider";
		doGenerateJavaClass(myEmitters.getViewProviderEmitter(), myDiagram.getNotationViewProviderQualifiedClassName(),
				myDiagram);
	}

	protected void generateEditPartProvider() throws UnexpectedBehaviourException, InterruptedException {
		rule = "EditPartProvider";
		doGenerateJavaClass(myEmitters.getEditPartProviderEmitter(), myDiagram.getProvidersPackageName(),
				myDiagram.getEditPartProviderClassName(), myDiagram);
	}

	protected void generateModelingAssistantProvider() throws UnexpectedBehaviourException, InterruptedException {
		rule = "ModelingAssistantProvider";
		doGenerateJavaClass(myEmitters.getModelingAssistantProviderEmitter(),
				myDiagram.getModelingAssistantProviderQualifiedClassName(), myDiagram);
	}

	protected void generateIconProvider() throws UnexpectedBehaviourException, InterruptedException {
		rule = "IconProvider";
		doGenerateJavaClass(myEmitters.getIconProviderEmitter(), myDiagram.getIconProviderQualifiedClassName(),
				myDiagram);
	}

	protected void generateValidationProvider() throws UnexpectedBehaviourException, InterruptedException {
		rule = "ValidationProvider";
		doGenerateJavaClass(myEmitters.getValidationProviderEmitter(),
				myDiagram.getValidationProviderQualifiedClassName(), myDiagram);
	}

	protected void generateValidationDecoratorProvider() throws UnexpectedBehaviourException, InterruptedException {
		rule = "ValidationDecoratorProvider";
		doGenerateJavaClass(myEmitters.getValidationDecoratorProviderEmitter(),
				myDiagram.getValidationDecoratorProviderQualifiedClassName(), myDiagram);
	}

	protected void generateShortcutsDecoratorProvider() throws InterruptedException {
		rule = "ShortcutsDecoratorProvider";
		doGenerateJavaClass(myEmitters.getShortcutsDecoratorProviderEmitter(),
				myDiagram.getShortcutsDecoratorProviderQualifiedClassName(), myDiagram);
	}

	protected void generateShortcutPropertyTester() throws InterruptedException {
		rule = "ShortcutPropertyTester";
		doGenerateJavaClass(myEmitters.getShortcutPropertyTesterEmitter(),
				myDiagram.getShortcutPropertyTesterQualifiedClassName(), myDiagram);
	}

	protected void generateMetricProvider() throws UnexpectedBehaviourException, InterruptedException {
		rule = "MetricProvider";
		doGenerateJavaClass(myEmitters.getMetricProviderEmitter(), myDiagram.getMetricProviderQualifiedClassName(),
				myDiagram);
	}

	protected void generateMarkerNavigationProvider() throws UnexpectedBehaviourException, InterruptedException {
		rule = "MarkerNavigationProvider";
		doGenerateJavaClass(myEmitters.getMarkerNavigationProviderEmitter(),
				myDiagram.getMarkerNavigationProviderQualifiedClassName(), myDiagram);
	}

	// editor

	protected void generateValidateAction() throws UnexpectedBehaviourException, InterruptedException {
		rule = "ValidateAction";
		JavaClassEmitter emitter = myEmitters.getValidateActionEmitter();
		doGenerate(emitter, myDiagram);
	}

	protected void generateValidationMarker() throws UnexpectedBehaviourException, InterruptedException {
		rule = "ValidationMarker";
		JavaClassEmitter emitter = myEmitters.getValidationMarkerEmitter();
		doGenerate(emitter, myDiagram);
	}

	protected void generateModelElementSelectionPage() throws UnexpectedBehaviourException, InterruptedException {
		rule = "ModelElementSelectionPage";
		JavaClassEmitter emitter = myEmitters.getModelElementSelectionPageEmitter();
		doGenerate(emitter, myDiagram);
	}

	protected void generateNewDiagramFileWizard() throws UnexpectedBehaviourException, InterruptedException {
		rule = "NewDiagramFileWizard";
		if (!myDiagram.isSynchronized()) {
			doGenerateJavaClass(myEmitters.getDiagramContentInitializerEmitter(),
					myDiagram.getDiagramContentInitializerQualifiedClassName(), myDiagram);
		}
		JavaClassEmitter emitter = myEmitters.getNewDiagramFileWizardEmitter();
		doGenerate(emitter, myDiagram);
	}

	protected void generatePalette() throws UnexpectedBehaviourException, InterruptedException {
		rule = "Palette";
		if (myDiagram.getPalette() == null) {
			return;
		}
		doGenerateJavaClass(myEmitters.getPaletteEmitter(), myDiagram.getPalette().getFactoryQualifiedClassName(),
				myDiagram.getPalette());
	}

	protected void generateDiagramEditorUtil() throws UnexpectedBehaviourException, InterruptedException {
		rule = "DiagramEditorUtil";
		doGenerateJavaClass(myEmitters.getDiagramEditorUtilEmitter(), myEditorGen.getEditor().getPackageName(),
				myDiagram.getDiagramEditorUtilClassName(), myDiagram);
	}

	protected void generateVisualIDRegistry() throws InterruptedException {
		rule = "VisualIDRegistry";
		doGenerateJavaClass(myEmitters.getVisualIDRegistryEmitter(), myDiagram.getVisualIDRegistryQualifiedClassName(),
				myDiagram);
	}

	protected void generateCreationWizard() throws UnexpectedBehaviourException, InterruptedException {
		rule = "CreationWizard";
		doGenerateJavaClass(myEmitters.getCreationWizardEmitter(), myDiagram.getCreationWizardQualifiedClassName(),
				myDiagram);
	}

	protected void generateCreationWizardPage() throws UnexpectedBehaviourException, InterruptedException {
		rule = "CreationWizardPage";
		doGenerateJavaClass(myEmitters.getCreationWizardPageEmitter(),
				myDiagram.getCreationWizardPageQualifiedClassName(), myDiagram);
	}

	protected void generateDeleteElementAction() throws UnexpectedBehaviourException, InterruptedException {
		rule = "DeleteElementAction";
		JavaClassEmitter emitter = myEmitters.getDeleteElementActionEmitter();
		doGenerate(emitter, myDiagram);
	}

	protected void generateDiagramEditorContextMenuProvider()
			throws UnexpectedBehaviourException, InterruptedException {
		rule = "DiagramEditorContextMenuProvider";
		JavaClassEmitter emitter = myEmitters.getDiagramEditorContextMenuProviderEmitter();
		doGenerate(emitter, myDiagram);
	}

	protected void generateEditor() throws InterruptedException {
		rule = "Editor";
		final GenEditorView editor = myEditorGen.getEditor();
		doGenerateJavaClass(myEmitters.getEditorEmitter(), editor.getQualifiedClassName(), editor);
	}

	protected void generateElementChooser() throws InterruptedException {
		rule = "ElementChooser";
		doGenerateJavaClass(myEmitters.getElementChooserEmitter(), myDiagram.getElementChooserQualifiedClassName(),
				myDiagram);
	}

	protected void generateShortcutCreationWizard() throws UnexpectedBehaviourException, InterruptedException {
		rule = "ShortcutCreationWizard";
		JavaClassEmitter emitter = myEmitters.getShortcutCreationWizardEmitter();
		doGenerate(emitter, myDiagram);
	}

	protected void generateDocumentProvider() throws InterruptedException {
		rule = "DocumentProvider";
		doGenerateJavaClass(myEmitters.getDocumentProviderEmitter(), myDiagram.getDocumentProviderQualifiedClassName(),
				myDiagram);
	}

	protected void generateDiagramUpdater() throws InterruptedException {
		rule = "DiagramUpdater";
		doGenerateJavaClass(myEmitters.getDiagramUpdaterEmitter(),
				myEditorGen.getDiagramUpdater().getDiagramUpdaterQualifiedClassName(), myEditorGen.getDiagramUpdater());
	}

	protected void generateUpdateCommand() throws InterruptedException {
		rule = "UpdateCommand";
		doGenerateJavaClass(myEmitters.getUpdateCommandEmitter(),
				myEditorGen.getDiagramUpdater().getUpdateCommandQualifiedClassName(), myEditorGen.getDiagramUpdater());
	}

	protected void generateNodeDescriptor() throws InterruptedException {
		rule = "NodeDescriptor";
		doGenerateJavaClass(myEmitters.getNodeDescriptorEmitter(),
				myEditorGen.getDiagramUpdater().getNodeDescriptorQualifiedClassName(), myEditorGen.getDiagramUpdater());
	}

	protected void generateLinkDescriptor() throws InterruptedException {
		rule = "LinkDescriptor";
		doGenerateJavaClass(myEmitters.getLinkDescriptorEmitter(),
				myEditorGen.getDiagramUpdater().getLinkDescriptorQualifiedClassName(), myEditorGen.getDiagramUpdater());
	}

	protected void generateActionBarContributor() throws UnexpectedBehaviourException, InterruptedException {
		rule = "ActionBarContributor";
		final GenEditorView editor = myEditorGen.getEditor();
		doGenerateJavaClass(myEmitters.getActionBarContributorEmitter(),
				editor.getActionBarContributorQualifiedClassName(), editor);
	}

	protected void generateMatchingStrategy() throws InterruptedException {
		rule = "MatchingStrategy";
		doGenerateJavaClass(myEmitters.getMatchingStrategyEmitter(), myDiagram.getMatchingStrategyQualifiedClassName(),
				myDiagram);
	}

	protected void generateNavigatorContentProvider() throws InterruptedException {
		rule = "NavigatorContentProvider";
		doGenerateJavaClass(myEmitters.getNavigatorContentProviderEmitter(),
				myEditorGen.getNavigator().getContentProviderQualifiedClassName(), myEditorGen.getNavigator());
	}

	protected void generateDomainNavigatorContentProvider() throws InterruptedException {
		rule = "DomainNavigatorContentProvider";
		doGenerateJavaClass(myEmitters.getDomainNavigatorContentProviderEmitter(),
				myEditorGen.getNavigator().getDomainContentProviderQualifiedClassName(), myEditorGen.getNavigator());
	}

	protected void generateDomainNavigatorLabelProvider() throws InterruptedException {
		rule = "DomainNavigatorLabelProvider";
		doGenerateJavaClass(myEmitters.getDomainNavigatorLabelProviderEmitter(),
				myEditorGen.getNavigator().getDomainLabelProviderQualifiedClassName(), myEditorGen.getNavigator());
	}

	protected void generateDomainNavigatorItem() throws InterruptedException {
		rule = "DomainNavigatorItem";
		doGenerateJavaClass(myEmitters.getDomainNavigatorItemEmitter(),
				myEditorGen.getNavigator().getDomainNavigatorItemQualifiedClassName(), myEditorGen.getNavigator());
	}

	protected void generateURIEditorInputTester() throws InterruptedException {
		rule = "URIEditorInputTester";
		doGenerateJavaClass(myEmitters.getURIEditorInputTesterEmitter(),
				myEditorGen.getNavigator().getUriInputTesterQualifiedClassName(), myEditorGen.getNavigator());
	}

	protected void generateNavigatorLabelProvider() throws InterruptedException {
		rule = "NavigatorLabelProvider";
		doGenerateJavaClass(myEmitters.getNavigatorLabelProviderEmitter(),
				myEditorGen.getNavigator().getLabelProviderQualifiedClassName(), myEditorGen.getNavigator());
	}

	protected void generateNavigatorLinkHelper() throws InterruptedException {
		rule = "NavigatorLinkHelper";
		doGenerateJavaClass(myEmitters.getNavigatorLinkHelperEmitter(),
				myEditorGen.getNavigator().getLinkHelperQualifiedClassName(), myEditorGen.getNavigator());
	}

	protected void generateNavigatorSorter() throws InterruptedException {
		rule = "NavigatorSorter";
		doGenerateJavaClass(myEmitters.getNavigatorSorterEmitter(),
				myEditorGen.getNavigator().getSorterQualifiedClassName(), myEditorGen.getNavigator());
	}

	protected void generateNavigatorActionProvider() throws InterruptedException {
		rule = "NavigatorActionProvider";
		doGenerateJavaClass(myEmitters.getNavigatorActionProviderEmitter(),
				myEditorGen.getNavigator().getActionProviderQualifiedClassName(), myEditorGen.getNavigator());
	}

	protected void generateAbstractNavigatorItem() throws InterruptedException {
		rule = "AbstractNavigatorItem";
		doGenerateJavaClass(myEmitters.getAbstractNavigatorItemEmitter(),
				myEditorGen.getNavigator().getAbstractNavigatorItemQualifiedClassName(), myEditorGen.getNavigator());
	}

	protected void generateNavigatorGroup() throws InterruptedException {
		rule = "NavigatorGroup";
		doGenerateJavaClass(myEmitters.getNavigatorGroupEmitter(),
				myEditorGen.getNavigator().getNavigatorGroupQualifiedClassName(), myEditorGen.getNavigator());
	}

	protected void generateNavigatorItem() throws InterruptedException {
		rule = "NavigatorItem";
		doGenerateJavaClass(myEmitters.getNavigatorItemEmitter(),
				myEditorGen.getNavigator().getNavigatorItemQualifiedClassName(), myEditorGen.getNavigator());
	}

	protected void generateNavigatorGroupIcons() throws InterruptedException, UnexpectedBehaviourException {
		rule = "NavigatorGroupIcons";
		Set<String> groupIcons = new HashSet<String>();
		for (GenNavigatorChildReference nextReference : myEditorGen.getNavigator().getChildReferences()) {
			if (nextReference.getGroupIcon() != null && nextReference.getGroupIcon().length() > 0) {
				groupIcons.add(nextReference.getGroupIcon());
			}
		}
		for (String iconPath : groupIcons) {
			generateGroupIcon(new Path(iconPath));
		}
	}
	
	// property sheet

	protected void generatePropertySheetSections() throws UnexpectedBehaviourException, InterruptedException {
		rule = "PropertySheetSections";
		if (myEditorGen.getPropertySheet().isNeedsCaption()) {
			internalGenerateJavaClass(myEmitters.getPropertySheetLabelProviderEmitter(),
					myEditorGen.getPropertySheet().getLabelProviderQualifiedClassName(),
					myEditorGen.getPropertySheet());
		}
		for (GenPropertyTab tab : myEditorGen.getPropertySheet().getTabs()) {
			if (tab instanceof GenCustomPropertyTab && ((GenCustomPropertyTab) tab).isGenerateBoilerplate()) {
				internalGenerateJavaClass(myEmitters.getPropertySectionEmitter(),
						((GenCustomPropertyTab) tab).getQualifiedClassName(), tab);
			}
		}
	}

	// expressions

	protected void generateExpressionProviders() throws UnexpectedBehaviourException, InterruptedException {
		rule = "ExpressionProviders";
		GenExpressionProviderContainer providerContainer = myEditorGen.getExpressionProviders();
		boolean needAbstractExpression = false;
		for (GenExpressionProviderBase nextProvider : providerContainer.getProviders()) {
			if (nextProvider instanceof GenExpressionInterpreter) {
				TextEmitter providerEmitter = null;
				if (GenLanguage.OCL_LITERAL.equals(nextProvider.getLanguage())) {
					providerEmitter = myEmitters.getOCLExpressionFactoryEmitter();
					needAbstractExpression = true;
				} else if (GenLanguage.REGEXP_LITERAL.equals(nextProvider.getLanguage())
						|| GenLanguage.NREGEXP_LITERAL.equals(nextProvider.getLanguage())) {
					providerEmitter = myEmitters.getRegexpExpressionFactoryEmitter();
					needAbstractExpression = true;
				}
				GenExpressionInterpreter interpreter = (GenExpressionInterpreter) nextProvider;
				if (providerEmitter != null) {
					doGenerateJavaClass(providerEmitter, interpreter.getQualifiedClassName(), interpreter);
				}
			}
		}
		if (needAbstractExpression) {
			// so that if there are only literal initializers, do not generate any extra
			// class
			doGenerateJavaClass(myEmitters.getAbstractExpressionEmitter(),
					providerContainer.getAbstractExpressionQualifiedClassName(), myDiagram);
		}
	}

	protected void generateModelAccessFacility() throws UnexpectedBehaviourException, InterruptedException {
		rule = "ModelAccessFacility";
		doGenerateJavaClass(myEmitters.getModelAccessFacilityEmitter(),
				myEditorGen.getModelAccess().getQualifiedClassName(), myEditorGen.getModelAccess());
	}

	protected void generateShortcutIcon() throws UnexpectedBehaviourException, InterruptedException {
		rule = "ShortcutIcon";
		doGenerateBinaryFile(myBinaryEmmiters.getShortcutImageEmitter(), new Path("icons/shortcut.gif"), null); //$NON-NLS-1$
	}

	protected void generateGroupIcon(Path groupIconPath) throws InterruptedException, UnexpectedBehaviourException {
		rule = "GroupIcon";
		doGenerateBinaryFile(myBinaryEmmiters.getGroupIconEmitter(), groupIconPath, null);
	}

	protected void generateDiagramIcon(String path) throws UnexpectedBehaviourException, InterruptedException {
		rule = "DiagramIcon";
		// use genModel.prefix if available to match colors of model icons and diagram
		// icons
		// @see GenPackageImpl#generateEditor - it passes prefix to ModelGIFEmitter
		Object[] args = new Object[] {
				myDiagram.getDomainDiagramElement() == null ? myEditorGen.getDiagramFileExtension()
						: myDiagram.getDomainDiagramElement().getGenPackage().getPrefix() };
		doGenerateBinaryFile(myBinaryEmmiters.getDiagramIconEmitter(), new Path(path), args);
	}

	protected void generateWizardBanner() throws UnexpectedBehaviourException, InterruptedException {
		rule = "WizardBanner";
		String stem = myDiagram.getDomainDiagramElement() == null ? "" //$NON-NLS-1$
				: myDiagram.getDomainDiagramElement().getGenPackage().getPrefix();
		// @see GenPackageImpl#generateEditor - it passes prefix to
		// ModelWizardGIFEmitter
		Object[] args = new Object[] { stem.length() == 0 ? myEditorGen.getDiagramFileExtension() : stem };
		doGenerateBinaryFile(myBinaryEmmiters.getWizardBannerImageEmitter(),
				new Path("icons/wizban/New" + stem + "Wizard.gif"), args); //$NON-NLS-1$ //$NON-NLS-2$
	}

	protected void generateExternalizationSupport() throws UnexpectedBehaviourException, InterruptedException {
		rule = "ExternalizationSupport";
		String packageName = myEditorGen.getEditor().getPackageName();
		String messagesClassName = "Messages"; //$NON-NLS-1$
		doGenerateJavaClass(myEmitters.getExternalizeEmitter(), packageName, messagesClassName,
				new Object[] { myEditorGen });
		doGenerateFile(myEmitters.getMessagesEmitter(), new Path(messagesClassName.toLowerCase() + ".properties"), //$NON-NLS-1$
				new Object[] { myEditorGen });
	}

	// plugin

	protected void generatePlugin() throws UnexpectedBehaviourException, InterruptedException {
		rule = "Plugin";
		generateActivator();
		generateBundleManifest();
		generatePluginXml();
		generatePluginProperties();
		generateBuildProperties();
		generateOptionsFile();
	}

	protected void generateActivator() throws UnexpectedBehaviourException, InterruptedException {
		rule = "Activator";
		doGenerateJavaClass(myEmitters.getActivatorEmitter(), myEditorGen.getPlugin().getActivatorQualifiedClassName(),
				myEditorGen.getPlugin());
	}

	protected void generateBundleManifest() throws UnexpectedBehaviourException, InterruptedException {
		rule = "BundleManifest";
		doGenerateFile(myEmitters.getBundleManifestEmitter(), new Path("META-INF/MANIFEST.MF"), //$NON-NLS-1$
				new Object[] { myDiagram.getEditorGen().getPlugin() });
	}

	protected void generatePluginXml() throws UnexpectedBehaviourException, InterruptedException {
		rule = "PluginXml";
		doGenerateFile(myEmitters.getPluginXmlEmitter(), new Path("plugin.xml"), //$NON-NLS-1$
				new Object[] { myDiagram.getEditorGen().getPlugin() });
	}

	protected void generatePluginProperties() throws UnexpectedBehaviourException, InterruptedException {
		rule = "PluginProperties";
		doGenerateFile(myEmitters.getPluginPropertiesEmitter(), new Path("plugin.properties"), //$NON-NLS-1$
				new Object[] { myDiagram.getEditorGen().getPlugin() });
	}

	protected void generateBuildProperties() throws UnexpectedBehaviourException, InterruptedException {
		rule = "BuildProperties";
		doGenerateFile(myEmitters.getBuildPropertiesEmitter(), new Path("build.properties"), //$NON-NLS-1$
				new Object[] { myEditorGen.getPlugin() });
	}

	protected void generateOptionsFile() throws InterruptedException, UnexpectedBehaviourException {
		rule = "OptionsFile";
		doGenerateFile(myEmitters.getOptionsFileEmitter(), new Path(".options"), //$NON-NLS-1$
				new Object[] { myEditorGen.getPlugin() });
	}

	// application

	protected void generateApplication() throws UnexpectedBehaviourException, InterruptedException {
		GenApplication application = myEditorGen.getApplication();
		if (application != null) {
			generateApplication(application);
			generateActionBarAdvisor(application);
			generatePerspective(application);
			generateWorkbenchAdvisor(application);
			generateWorkbenchWindowAdvisor(application);
			generateWizardNewFileCreationPage(application);
		}
	}

	protected void generateApplication(GenApplication application)
			throws UnexpectedBehaviourException, InterruptedException {
		doGenerateJavaClass(myEmitters.getApplicationEmitter(), application.getQualifiedClassName(), application);
	}

	protected void generateActionBarAdvisor(GenApplication application)
			throws UnexpectedBehaviourException, InterruptedException {
		doGenerateJavaClass(myEmitters.getActionBarAdvisorEmitter(),
				application.getActionBarAdvisorQualifiedClassName(), application);
	}

	protected void generatePerspective(GenApplication application)
			throws UnexpectedBehaviourException, InterruptedException {
		doGenerateJavaClass(myEmitters.getPerspectiveEmitter(), application.getPerspectiveQualifiedClassName(),
				application);
	}

	protected void generateWorkbenchAdvisor(GenApplication application)
			throws UnexpectedBehaviourException, InterruptedException {
		doGenerateJavaClass(myEmitters.getWorkbenchAdvisorEmitter(),
				application.getWorkbenchAdvisorQualifiedClassName(), application);
	}

	protected void generateWorkbenchWindowAdvisor(GenApplication application)
			throws UnexpectedBehaviourException, InterruptedException {
		doGenerateJavaClass(myEmitters.getWorkbenchWindowAdvisorEmitter(),
				application.getWorkbenchWindowAdvisorQualifiedClassName(), application);
	}

	protected void generateWizardNewFileCreationPage(GenApplication application)
			throws UnexpectedBehaviourException, InterruptedException {
		doGenerateJavaClass(myEmitters.getWizardNewFileCreationPageEmitter(), application.getPackageName(),
				"WizardNewFileCreationPage", application); //$NON-NLS-1$
	}

	// actions
	protected void generateActions() throws UnexpectedBehaviourException, InterruptedException {
		HashSet<GenContributionItem> processedItems = new HashSet<GenContributionItem>();
		for (GenContributionManager m : myEditorGen.getContextMenus()) {
			LinkedList<GenContributionItem> items = new LinkedList<GenContributionItem>(m.getItems());
			while (!items.isEmpty()) {
				GenContributionItem ci = items.removeFirst();
				if (ci instanceof GenCustomAction && ((GenCustomAction) ci).isGenerateBoilerplate()
						&& !processedItems.contains(ci)) {
					doGenerateJavaClass(myEmitters.getCustomActionEmitter(),
							((GenCustomAction) ci).getQualifiedClassName(), ci);
					processedItems.add(ci);
				} else if (ci instanceof GenContributionManager) {
					items.addAll(((GenContributionManager) ci).getItems());
				} else if (ci instanceof GenSharedContributionItem) {
					items.addLast(((GenSharedContributionItem) ci).getActualItem());
				} else if (ci instanceof GenAction) {
					doGenerateJavaClass(myEmitters.getPredefinedActionEmitter(),
							((GenAction) ci).getQualifiedClassName(), ci);
					processedItems.add(ci);
				}
			}
		}
	}

	protected void generateCustomExtensions() throws UnexpectedBehaviourException, InterruptedException {
		if (myEditorGen.getExtensions().isEmpty()) {
			return;
		}
		List<GenTemplateInvocationBase> unresolvedInvocations = new ArrayList<GenTemplateInvocationBase>();
		Map<GenTemplateInvocationBase, Collection<EObject>> boundInputs = bindInvocationsToInputs(
				unresolvedInvocations);
		for (Map.Entry<GenTemplateInvocationBase, Collection<EObject>> nextEntry : boundInputs.entrySet()) {
			GenTemplateInvocationBase invocation = nextEntry.getKey();
			Collection<EObject> oclInputs = nextEntry.getValue();
			if (oclInputs == null || oclInputs.isEmpty()) {
				unresolvedInvocations.add(invocation);
				continue;
			}
			generateTemplateInvocation(invocation, oclInputs);
		}

		if (!unresolvedInvocations.isEmpty()) {
			throw new UnexpectedBehaviourException(
					"There were custom templates invocations with unresolved inputs: " + unresolvedInvocations);
		}
	}

	protected void generateTemplateInvocation(GenTemplateInvocationBase invocation, Collection<EObject> oclInput)
			throws UnexpectedBehaviourException, InterruptedException {
		String primaryTemplateFQN = invocation.getTemplateFqn();
		if (primaryTemplateFQN == null) {
			handleException(new NullPointerException("Invocation without templateFQN: " + invocation));
			return;
		}

		JavaClassEmitter primaryEmitter = myEmitters.createFullTemplateInvocation(primaryTemplateFQN);
		TextEmitter fqnEmitter = myEmitters.getQualifiedClassNameEmitterForPrimaryTemplate(primaryTemplateFQN);

		Object[] templateInputs;
		if (invocation instanceof GenFixedInputsTemplateInvocation) {
			templateInputs = oclInput.toArray();
		} else if (invocation instanceof GenTemplateInvocation) {
			templateInputs = computeTemplateInputs((GenTemplateInvocation) invocation, oclInput);
		} else {
			throw new UnexpectedBehaviourException("Unknown invocation type: " + invocation);
		}
		for (Object nextTemplateInput : templateInputs) {
			String nextFqn;
			try {
				nextFqn = fqnEmitter.generate(new NullProgressMonitor(), new Object[] { nextTemplateInput });
			} catch (Exception e) {
				handleException(new UnexpectedBehaviourException(//
						"Error computing FQN for invocation " + invocation + //
								" on " + nextTemplateInput,
						e));
				continue;
			}
			if (nextFqn != null) {
				doGenerateJavaClass(primaryEmitter, nextFqn, nextTemplateInput);
			}
		}
	}

	protected Object[] computeTemplateInputs(GenTemplateInvocation invocation, Collection<EObject> oclInputs)
			throws UnexpectedBehaviourException {
		String oclExpressionText = invocation.getOclExpression();
		if (oclExpressionText == null || oclExpressionText.trim().length() == 0
				|| "self".equals(oclExpressionText.trim())) {
			return oclInputs.toArray();
		}

		List<Object> results = new ArrayList<Object>();
		OCL ocl = OCL.newInstance();
		ParsingOptions.setOption(ocl.getEnvironment(), ParsingOptions.implicitRootClass(ocl.getEnvironment()),
				EcorePackage.eINSTANCE.getEObject());
		for (EObject nextOclInput : oclInputs) {
			try {
				Helper oclHelper = ocl.createOCLHelper();
				oclHelper.setContext(nextOclInput.eClass());
				OCLExpression oclExpression = oclHelper.createQuery(oclExpressionText);
				Object oclResult = ocl.evaluate(nextOclInput, oclExpression);
				if (oclResult instanceof Collection<?>) {
					results.addAll((Collection<?>) oclResult);
				} else {
					results.add(oclResult);
				}
			} catch (Exception e) {
				throw new UnexpectedBehaviourException(
						"Can't evaluate OCL " + oclExpressionText + " for context: " + nextOclInput, e);
			}
		}
		return results.toArray();
	}

	protected Map<GenCustomTemplateInput, Collection<EObject>> resolveCustomTemplateInputs() {
		Map<GenCustomTemplateInput, Collection<EObject>> resolvedInputs = new HashMap<GenCustomTemplateInput, Collection<EObject>>();
		for (GenCustomGeneratorExtension nextExtension : myEditorGen.getExtensions()) {
			EObject nextInput = nextExtension.getRootInput();
			if (nextInput == null) {
				nextInput = myEditorGen;
			}
			resolvedInputs.put(nextExtension, Collections.singletonList(nextInput));
		}
		for (GenCustomGeneratorExtension nextExtension : myEditorGen.getExtensions()) {
			for (GenTemplateInvocationBase nextInvocation : nextExtension.getInvocations()) {
				if (nextInvocation instanceof GenFixedInputsTemplateInvocation) {
					GenFixedInputsTemplateInvocation hasFixedInputs = (GenFixedInputsTemplateInvocation) nextInvocation;
					Collection<EObject> nextInputs;
					if (hasFixedInputs.getFixedInputs().isEmpty()) {
						nextInputs = resolvedInputs.get(hasFixedInputs.getExtension());
					} else {
						nextInputs = new ArrayList<EObject>(hasFixedInputs.getFixedInputs());
					}
					resolvedInputs.put(hasFixedInputs, nextInputs);
				}
			}
		}
		return resolvedInputs;
	}

	protected Map<GenTemplateInvocationBase, Collection<EObject>> bindInvocationsToInputs(
			List<GenTemplateInvocationBase> unresolvedInvocations) {
		Map<GenCustomTemplateInput, Collection<EObject>> resolvedInputs = resolveCustomTemplateInputs();
		Map<GenTemplateInvocationBase, Collection<EObject>> result = new LinkedHashMap<GenTemplateInvocationBase, Collection<EObject>>();
		for (GenCustomGeneratorExtension extension : myEditorGen.getExtensions()) {
			for (GenTemplateInvocationBase nextInvocation : extension.getInvocations()) {
				if (nextInvocation instanceof GenFixedInputsTemplateInvocation) {
					GenFixedInputsTemplateInvocation nextImpl = (GenFixedInputsTemplateInvocation) nextInvocation;
					if (resolvedInputs.containsKey(nextImpl)) {
						result.put(nextInvocation, resolvedInputs.get(nextImpl));
					} else {
						unresolvedInvocations.add(nextImpl);
					}
				} else if (nextInvocation instanceof GenTemplateInvocation) {
					GenTemplateInvocation nextImpl = (GenTemplateInvocation) nextInvocation;
					List<EObject> combinedInputs = new ArrayList<EObject>();

					Collection<? extends GenCustomTemplateInput> inputsFromModel = nextImpl.getInputs();
					if (inputsFromModel.isEmpty()) {
						inputsFromModel = Collections.singletonList(nextImpl.getExtension());
					}
					boolean hasUnresolvedInputRef = false;
					for (GenCustomTemplateInput nextInputRef : inputsFromModel) {
						Collection<EObject> nextResolvedInput = resolvedInputs.get(nextInputRef);
						if (nextResolvedInput == null) {
							hasUnresolvedInputRef = true;
							break;
						}
						combinedInputs.addAll(nextResolvedInput);
					}
					if (hasUnresolvedInputRef || combinedInputs.isEmpty()) {
						unresolvedInvocations.add(nextImpl);
					} else {
						result.put(nextInvocation, combinedInputs);
					}
				}
			}
		}
		return result;
	}

	// util

	protected void internalGenerateJavaClass(TextEmitter emitter, String qualifiedName, EObject input)
			throws InterruptedException {
		doGenerateJavaClass(emitter, CodeGenUtil.getPackageName(qualifiedName),
				CodeGenUtil.getSimpleClassName(qualifiedName), input);
	}

	protected IPath guessProjectLocation(String projectName) {
		if (myEditorGen.getDomainGenModel() == null) {
			return null;
		}
		Path modelProjectPath = new Path(myEditorGen.getDomainGenModel().getModelDirectory());
		return guessNewProjectLocation(modelProjectPath, projectName);
	}

	@Override
	protected void setupProgressMonitor() {
		Counter c = new Counter();
		c.registerFactor(GMFGenPackage.eINSTANCE.getGenNode(), 7);
		c.registerFactor(GMFGenPackage.eINSTANCE.getGenChildLabelNode(), 5);
		c.registerFactor(GMFGenPackage.eINSTANCE.getGenLink(), 6);
		c.registerFactor(GMFGenPackage.eINSTANCE.getGenLinkLabel(), 2);
		c.registerFactor(GMFGenPackage.eINSTANCE.getGenCompartment(), 3);
		c.registerFactor(GMFGenPackage.eINSTANCE.getGenDiagram(), 30);
		c.registerFactor(GMFGenPackage.eINSTANCE.getGenEditorGenerator(), 2); // i18n=2
		c.registerFactor(GMFGenPackage.eINSTANCE.getGenPlugin(), 6);
		c.registerFactor(GMFGenPackage.eINSTANCE.getGenNavigator(), 3);
		c.registerFactor(GMFGenPackage.eINSTANCE.getGenNavigatorChildReference(), 1);
		c.registerFactor(GMFGenPackage.eINSTANCE.getGenCustomPropertyTab(), 1);
		c.registerFactor(GMFGenPackage.eINSTANCE.getBehaviour(), 1);
		c.registerFactor(GMFGenPackage.eINSTANCE.getGenMetricContainer(), 1);
		c.registerFactor(GMFGenPackage.eINSTANCE.getGenExpressionProviderContainer(), 1);
		c.registerFactor(GMFGenPackage.eINSTANCE.getPalette(), 1);
		setupProgressMonitor(null, c.getTotal(myEditorGen));
	}

	protected static boolean needsGraphicalNodeEditPolicy(GenNode node) {
		return node.getModelFacet() != null && !node.getReorientedIncomingLinks().isEmpty();
	}

}
