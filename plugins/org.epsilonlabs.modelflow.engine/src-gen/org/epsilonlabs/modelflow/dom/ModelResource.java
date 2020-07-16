/**
 * ******************************************************************************
 * Copyright (c) 2020 The University of York.
 *  
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * ******************************************************************************
 */
package org.epsilonlabs.modelflow.dom;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Model Resource</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.epsilonlabs.modelflow.dom.DomPackage#getModelResource()
 * @model annotation="exeed classIcon='model' label='\nvar l = \'Model \' + self.name; \nif (self.definition.isDefined()){\r\n\tl = l +\' (\'+  self.definition.parent.shortId + \':\' + self.definition.shortId +\')\'; \r\n}\nif (self.conformsTo.isDefined()){\r\n\tl = l + \' conforms to \' + self.conformsTo.name;\n\t/*if (self.conformsTo.parent.isDefined()){\r\n\t\tl = l +\'(\' + self.conformsTo.definition.parent.shortId + \':\' + self.conformsTo.definition.shortId + \')\';\r\n\t} \052/\r\n}\nreturn l;\n'"
 * @generated
 */
public interface ModelResource extends Resource {
} // ModelResource
