/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dt.editor;

import java.util.Collections;
import java.util.List;

import org.eclipse.epsilon.common.dt.editor.AbstractModuleEditorScanner;
import org.eclipse.epsilon.common.dt.util.EclipseUtil;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public class ModelFlowModuleEditorScanner extends AbstractModuleEditorScanner {

	protected Color taskParamColor;

	protected List<String> taskParams = Collections.emptyList();

	public ModelFlowModuleEditorScanner(final ModelFlowEditor editor) {
		super(editor);
		
		initializeTaskParameters();
		initialiseLocalColours();
	
		taskParams.forEach(param -> 
			((WordRule)fRules[10]).addWord(param, new Token(new TextAttribute(taskParamColor, null, SWT.ITALIC)))
		);

	}
	
	protected void initializeTaskParameters(){
		//taskParams = editor.getModuleParsedListeners();	
	}
	
	protected void initialiseLocalColours() {
		if (EclipseUtil.isDarkThemeEnabled()) {			
			taskParamColor = new Color(Display.getCurrent(), new RGB(191, 127, 36));
		} else {
			taskParamColor = new Color(Display.getCurrent(), new RGB(191, 127, 36));
		}
	}

	public Color getTaskParamColor() {
		return taskParamColor;
	}
	
	public void setTaskParamColor(Color color) {
		this.taskParamColor = color;
	}
	
}
