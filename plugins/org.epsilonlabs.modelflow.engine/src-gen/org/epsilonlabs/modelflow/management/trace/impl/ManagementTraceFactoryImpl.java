/**
 * ******************************************************************************
 * Copyright (c) 2020 The University of York.
 *  
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * ******************************************************************************
 */
package org.epsilonlabs.modelflow.management.trace.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.epsilonlabs.modelflow.management.trace.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ManagementTraceFactoryImpl extends EFactoryImpl implements ManagementTraceFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ManagementTraceFactory init() {
		try {
			ManagementTraceFactory theManagementTraceFactory = (ManagementTraceFactory)EPackage.Registry.INSTANCE.getEFactory(ManagementTracePackage.eNS_URI);
			if (theManagementTraceFactory != null) {
				return theManagementTraceFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ManagementTraceFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ManagementTraceFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case ManagementTracePackage.MANAGEMENT_TRACE: return createManagementTrace();
			case ManagementTracePackage.TASK_TRACE: return createTaskTrace();
			case ManagementTracePackage.TRACE: return createTrace();
			case ManagementTracePackage.MODEL_ELEMENT: return createModelElement();
			case ManagementTracePackage.MODEL_ELEMENT_PROPERTY: return createModelElementProperty();
			case ManagementTracePackage.FILE_ELEMENT: return createFileElement();
			case ManagementTracePackage.FILE_REGION_ELEMENT: return createFileRegionElement();
			case ManagementTracePackage.LINK: return createLink();
			case ManagementTracePackage.PROPERTY: return createProperty();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ManagementTrace createManagementTrace() {
		ManagementTraceImpl managementTrace = new ManagementTraceImpl();
		return managementTrace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TaskTrace createTaskTrace() {
		TaskTraceImpl taskTrace = new TaskTraceImpl();
		return taskTrace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Trace createTrace() {
		TraceImpl trace = new TraceImpl();
		return trace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ModelElement createModelElement() {
		ModelElementImpl modelElement = new ModelElementImpl();
		return modelElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ModelElementProperty createModelElementProperty() {
		ModelElementPropertyImpl modelElementProperty = new ModelElementPropertyImpl();
		return modelElementProperty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FileElement createFileElement() {
		FileElementImpl fileElement = new FileElementImpl();
		return fileElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FileRegionElement createFileRegionElement() {
		FileRegionElementImpl fileRegionElement = new FileRegionElementImpl();
		return fileRegionElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Link createLink() {
		LinkImpl link = new LinkImpl();
		return link;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Property createProperty() {
		PropertyImpl property = new PropertyImpl();
		return property;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ManagementTracePackage getManagementTracePackage() {
		return (ManagementTracePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ManagementTracePackage getPackage() {
		return ManagementTracePackage.eINSTANCE;
	}

} //ManagementTraceFactoryImpl
