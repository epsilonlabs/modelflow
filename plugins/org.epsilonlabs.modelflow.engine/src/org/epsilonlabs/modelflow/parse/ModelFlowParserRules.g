/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Betty Sanchez - ModelFlow grammar extension
 * -----------------------------------------------------------------------------
 * ANTLR 3 License
 * [The "BSD licence"]
 * Copyright (c) 2005-2008 Terence Parr
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *   derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
parser grammar ModelFlowParserRules;
options {backtrack=true; output=AST;}

tokens {
	PROPERTY;
	RULETYPE;
	RESOURCEDECLARATION;
	TASKDECLARATION;
	INPUTS;
	OUTPUTS;
	INOUTS;
	TASKRESOURCE;
	RESLIST;
	DEPENDSON;
	PARAMDECLARATION;
}

workflowContents
	:  
	paramDeclarationExpression | resourceDeclaration | taskDeclaration | annotationBlock | operationDeclaration
	;
	
resourceDeclaration
	@after {
		$tree.getExtraTokens().add($mo);
		$tree.getExtraTokens().add($ob);
		$tree.getExtraTokens().add($cb);
	}
	:	
	mo='model'^ NAME ruleType ((ob='{'! propertyDeclaration* cb='}'!) | ';')!
	{$mo.setType(RESOURCEDECLARATION);}
	;
	
taskDeclaration
	@after {
		$tree.getExtraTokens().add($ta);
		$tree.getExtraTokens().add($ob);
		$tree.getExtraTokens().add($cb);
	}
	:	
	ta='task'^ NAME ruleType in? inout? out? dependsOn? ((ob='{'! guard? propertyDeclaration* cb='}'!) | ';')!
	{$ta.setType(TASKDECLARATION);}
	;
	
ruleType
	@after {
		$tree.getExtraTokens().add($ct);
	}
	:
	ct='is'^ head=NAME (':'! ty=NAME)? 
	{
		if ($ty != null){
			$head.tree.token.setText($head.tree.token.getText() + ":" + ty.getText() );
		}
		$ct.setType(RULETYPE);
	}
	;		
	
propertyDeclaration
	:	
	p=NAME^ expressionOrStatementBlock
	{$p.setType(PROPERTY);}
	;
	
in
	@after {
		$tree.getExtraTokens().add($i);
	}
	:
	i='in'^ taskResourceList
	{$i.setType(INPUTS);}
	;	
	
inout
	@after {
		$tree.getExtraTokens().add($i);
	}
	:
	i='inout'^ taskResourceList
	{$i.setType(INOUTS);}
	;	
	
out
	@after {
		$tree.getExtraTokens().add($i);
	}
	:
	i='out'^ taskResourceList
	{$i.setType(OUTPUTS);}
	;		
	
taskResourceList
	@after {
		$tree.setImaginary(true);
	}
	:	
	taskResource ('and' taskResource)*
	-> ^(RESLIST taskResource*)
	;	
	
taskResource
	@after {
		$tree.getExtraTokens().add($a);
	}
	:
	res=NAME ('.'! ind=NAME)? (a='as'^ NAME (','! NAME)*)?
	{
		if ($ind != null){
			$res.setText($res.getText() + "." + ind.getText() );
		}
		$res.setType(TASKRESOURCE);	
	}
	;
	
		
dependsOn
	@after {
		$tree.getExtraTokens().add($t);
	}
	:
	t='dependsOn'^ NAME (','! NAME)*
	{$t.setType(DEPENDSON);}
	;


paramDeclarationExpression
	@after {
		String txt = p.getText();
		$tree.getToken().setText(txt);
		$tree.getToken().setType(PARAMDECLARATION);
	}
	:	
	p='param'^ NAME (':'! t=typeName)? ';'!
	;
