package org.epsilonlabs.modelflow.parse;

// $ANTLR 3.1b1 ModelFlowParserRules.g 2021-02-10 11:25:27

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.tree.*;

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
public class ModelFlow_ModelFlowParserRules extends org.eclipse.epsilon.common.parse.EpsilonParser {
    public static final int T__144=144;
    public static final int RULETYPE=88;
    public static final int T__143=143;
    public static final int T__146=146;
    public static final int MODELDECLARATIONPARAMETER=78;
    public static final int T__145=145;
    public static final int BREAKALL=44;
    public static final int T__140=140;
    public static final int T__142=142;
    public static final int VAR=53;
    public static final int MODELDECLARATIONPARAMETERS=77;
    public static final int T__141=141;
    public static final int THROW=58;
    public static final int SpecialTypeName=19;
    public static final int PARAMLIST=29;
    public static final int EXPRLIST=59;
    public static final int EXPRRANGE=60;
    public static final int BREAK=43;
    public static final int ELSE=36;
    public static final int TASKRESOURCE=94;
    public static final int T__137=137;
    public static final int T__136=136;
    public static final int FORMAL=28;
    public static final int IF=35;
    public static final int MultiplicativeExpression=62;
    public static final int TYPE=70;
    public static final int T__139=139;
    public static final int T__138=138;
    public static final int T__133=133;
    public static final int T__132=132;
    public static final int T__135=135;
    public static final int T__134=134;
    public static final int T__131=131;
    public static final int NewExpression=52;
    public static final int T__130=130;
    public static final int RESLIST=95;
    public static final int CASE=40;
    public static final int Letter=20;
    public static final int LINE_COMMENT=26;
    public static final int T__129=129;
    public static final int T__126=126;
    public static final int JavaIDDigit=22;
    public static final int T__125=125;
    public static final int LAMBDAEXPR=69;
    public static final int MAP=80;
    public static final int T__128=128;
    public static final int T__127=127;
    public static final int T__166=166;
    public static final int T__165=165;
    public static final int T__168=168;
    public static final int T__167=167;
    public static final int T__162=162;
    public static final int T__161=161;
    public static final int T__164=164;
    public static final int MODELDECLARATION=73;
    public static final int T__163=163;
    public static final int EXPRESSIONINBRACKETS=64;
    public static final int T__160=160;
    public static final int TERNARY=37;
    public static final int TRANSACTION=46;
    public static final int FLOAT_TYPE_SUFFIX=7;
    public static final int FOREACH=98;
    public static final int ITEMSELECTOR=79;
    public static final int COMMENT=25;
    public static final int ModelElementType=50;
    public static final int IMPORT=72;
    public static final int DELETE=57;
    public static final int ARROW=11;
    public static final int MapTypeName=18;
    public static final int T__159=159;
    public static final int T__158=158;
    public static final int T__155=155;
    public static final int SPECIAL_ASSIGNMENT=31;
    public static final int T__154=154;
    public static final int T__157=157;
    public static final int T__156=156;
    public static final int T__151=151;
    public static final int T__150=150;
    public static final int T__153=153;
    public static final int T__152=152;
    public static final int Annotation=27;
    public static final int CONTINUE=45;
    public static final int ENUMERATION_VALUE=71;
    public static final int OPERATOR=63;
    public static final int EXPONENT=6;
    public static final int STRING=15;
    public static final int T__148=148;
    public static final int DEPENDSON=96;
    public static final int T__147=147;
    public static final int T__149=149;
    public static final int T__100=100;
    public static final int NAMESPACE=74;
    public static final int COLLECTION=47;
    public static final int NEW=54;
    public static final int EXTENDS=85;
    public static final int T__102=102;
    public static final int PRE=83;
    public static final int T__101=101;
    public static final int POST=84;
    public static final int ALIAS=75;
    public static final int DRIVER=76;
    public static final int INOUTS=93;
    public static final int KEYVAL=81;
    public static final int POINT_POINT=10;
    public static final int GUARD=86;
    public static final int HELPERMETHOD=32;
    public static final int StatementBlock=33;
    public static final int T__177=177;
    public static final int T__176=176;
    public static final int T__179=179;
    public static final int T__178=178;
    public static final int ABORT=48;
    public static final int T__173=173;
    public static final int StrangeNameLiteral=16;
    public static final int T__172=172;
    public static final int T__175=175;
    public static final int PROPERTY=87;
    public static final int T__174=174;
    public static final int FOR=34;
    public static final int BLOCK=67;
    public static final int T__171=171;
    public static final int T__170=170;
    public static final int PARAMETERS=51;
    public static final int SpecialNameChar=21;
    public static final int BOOLEAN=13;
    public static final int NAME=23;
    public static final int OUTPUTS=92;
    public static final int PARAMDECLARATION=97;
    public static final int SWITCH=39;
    public static final int T__169=169;
    public static final int FeatureCall=65;
    public static final int T__122=122;
    public static final int T__121=121;
    public static final int T__124=124;
    public static final int FLOAT=4;
    public static final int T__123=123;
    public static final int T__120=120;
    public static final int NativeType=61;
    public static final int INT=8;
    public static final int ANNOTATIONBLOCK=55;
    public static final int RETURN=42;
    public static final int KEYVALLIST=82;
    public static final int FEATURECALL=68;
    public static final int CollectionType=49;
    public static final int T__119=119;
    public static final int ASSIGNMENT=30;
    public static final int T__118=118;
    public static final int T__115=115;
    public static final int WS=24;
    public static final int EOF=-1;
    public static final int T__114=114;
    public static final int T__117=117;
    public static final int T__116=116;
    public static final int T__111=111;
    public static final int T__110=110;
    public static final int T__113=113;
    public static final int T__112=112;
    public static final int MODELFLOWMODULE=99;
    public static final int EscapeSequence=14;
    public static final int EOLMODULE=66;
    public static final int INPUTS=91;
    public static final int CollectionTypeName=17;
    public static final int DIGIT=5;
    public static final int EXECUTABLEANNOTATION=56;
    public static final int TASKDECLARATION=90;
    public static final int T__108=108;
    public static final int T__107=107;
    public static final int WHILE=38;
    public static final int T__109=109;
    public static final int NAVIGATION=12;
    public static final int RESOURCEDECLARATION=89;
    public static final int T__104=104;
    public static final int POINT=9;
    public static final int T__103=103;
    public static final int T__106=106;
    public static final int DEFAULT=41;
    public static final int T__105=105;

    // delegates
    // delegators
    public ModelFlowParser gModelFlow;


        public ModelFlow_ModelFlowParserRules(TokenStream input, ModelFlowParser gModelFlow) {
            this(input, new RecognizerSharedState(), gModelFlow);
        }
        public ModelFlow_ModelFlowParserRules(TokenStream input, RecognizerSharedState state, ModelFlowParser gModelFlow) {
            super(input, state);
            this.gModelFlow = gModelFlow;
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return ModelFlowParser.tokenNames; }
    public String getGrammarFileName() { return "ModelFlowParserRules.g"; }


    public static class workflowContents_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start workflowContents
    // ModelFlowParserRules.g:57:1: workflowContents : ( paramDeclarationExpression | resourceDeclaration | taskDeclaration | annotationBlock | operationDeclaration );
    public final ModelFlow_ModelFlowParserRules.workflowContents_return workflowContents() throws RecognitionException {
        ModelFlow_ModelFlowParserRules.workflowContents_return retval = new ModelFlow_ModelFlowParserRules.workflowContents_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        ModelFlow_ModelFlowParserRules.paramDeclarationExpression_return paramDeclarationExpression1 = null;

        ModelFlow_ModelFlowParserRules.resourceDeclaration_return resourceDeclaration2 = null;

        ModelFlow_ModelFlowParserRules.taskDeclaration_return taskDeclaration3 = null;

        ModelFlow_EolParserRules.annotationBlock_return annotationBlock4 = null;

        ModelFlow_EolParserRules.operationDeclaration_return operationDeclaration5 = null;



        try {
            // ModelFlowParserRules.g:58:2: ( paramDeclarationExpression | resourceDeclaration | taskDeclaration | annotationBlock | operationDeclaration )
            int alt1=5;
            switch ( input.LA(1) ) {
            case 179:
                {
                alt1=1;
                }
                break;
            case 100:
                {
                alt1=2;
                }
                break;
            case 172:
                {
                alt1=3;
                }
                break;
            case Annotation:
            case 114:
                {
                alt1=4;
                }
                break;
            case 108:
            case 109:
                {
                alt1=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }

            switch (alt1) {
                case 1 :
                    // ModelFlowParserRules.g:59:2: paramDeclarationExpression
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_paramDeclarationExpression_in_workflowContents83);
                    paramDeclarationExpression1=paramDeclarationExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, paramDeclarationExpression1.getTree());

                    }
                    break;
                case 2 :
                    // ModelFlowParserRules.g:59:31: resourceDeclaration
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_resourceDeclaration_in_workflowContents87);
                    resourceDeclaration2=resourceDeclaration();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, resourceDeclaration2.getTree());

                    }
                    break;
                case 3 :
                    // ModelFlowParserRules.g:59:53: taskDeclaration
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_taskDeclaration_in_workflowContents91);
                    taskDeclaration3=taskDeclaration();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, taskDeclaration3.getTree());

                    }
                    break;
                case 4 :
                    // ModelFlowParserRules.g:59:71: annotationBlock
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_annotationBlock_in_workflowContents95);
                    annotationBlock4=gModelFlow.annotationBlock();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, annotationBlock4.getTree());

                    }
                    break;
                case 5 :
                    // ModelFlowParserRules.g:59:89: operationDeclaration
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_operationDeclaration_in_workflowContents99);
                    operationDeclaration5=gModelFlow.operationDeclaration();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, operationDeclaration5.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end workflowContents

    public static class resourceDeclaration_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start resourceDeclaration
    // ModelFlowParserRules.g:62:1: resourceDeclaration : mo= 'model' NAME ruleType ( (ob= '{' ( propertyDeclaration )* cb= '}' ) | ';' ) ;
    public final ModelFlow_ModelFlowParserRules.resourceDeclaration_return resourceDeclaration() throws RecognitionException {
        ModelFlow_ModelFlowParserRules.resourceDeclaration_return retval = new ModelFlow_ModelFlowParserRules.resourceDeclaration_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token mo=null;
        Token ob=null;
        Token cb=null;
        Token NAME6=null;
        Token char_literal9=null;
        ModelFlow_ModelFlowParserRules.ruleType_return ruleType7 = null;

        ModelFlow_ModelFlowParserRules.propertyDeclaration_return propertyDeclaration8 = null;


        org.eclipse.epsilon.common.parse.AST mo_tree=null;
        org.eclipse.epsilon.common.parse.AST ob_tree=null;
        org.eclipse.epsilon.common.parse.AST cb_tree=null;
        org.eclipse.epsilon.common.parse.AST NAME6_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal9_tree=null;

        try {
            // ModelFlowParserRules.g:68:2: (mo= 'model' NAME ruleType ( (ob= '{' ( propertyDeclaration )* cb= '}' ) | ';' ) )
            // ModelFlowParserRules.g:69:2: mo= 'model' NAME ruleType ( (ob= '{' ( propertyDeclaration )* cb= '}' ) | ';' )
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            mo=(Token)match(input,100,FOLLOW_100_in_resourceDeclaration121); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            mo_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(mo);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(mo_tree, root_0);
            }
            NAME6=(Token)match(input,NAME,FOLLOW_NAME_in_resourceDeclaration124); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            NAME6_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(NAME6);
            adaptor.addChild(root_0, NAME6_tree);
            }
            pushFollow(FOLLOW_ruleType_in_resourceDeclaration126);
            ruleType7=ruleType();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, ruleType7.getTree());
            // ModelFlowParserRules.g:69:28: ( (ob= '{' ( propertyDeclaration )* cb= '}' ) | ';' )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==105) ) {
                alt3=1;
            }
            else if ( (LA3_0==101) ) {
                alt3=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // ModelFlowParserRules.g:69:29: (ob= '{' ( propertyDeclaration )* cb= '}' )
                    {
                    // ModelFlowParserRules.g:69:29: (ob= '{' ( propertyDeclaration )* cb= '}' )
                    // ModelFlowParserRules.g:69:30: ob= '{' ( propertyDeclaration )* cb= '}'
                    {
                    ob=(Token)match(input,105,FOLLOW_105_in_resourceDeclaration132); if (state.failed) return retval;
                    // ModelFlowParserRules.g:69:38: ( propertyDeclaration )*
                    loop2:
                    do {
                        int alt2=2;
                        int LA2_0 = input.LA(1);

                        if ( (LA2_0==NAME) ) {
                            alt2=1;
                        }


                        switch (alt2) {
                    	case 1 :
                    	    // ModelFlowParserRules.g:0:0: propertyDeclaration
                    	    {
                    	    pushFollow(FOLLOW_propertyDeclaration_in_resourceDeclaration135);
                    	    propertyDeclaration8=propertyDeclaration();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, propertyDeclaration8.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop2;
                        }
                    } while (true);

                    cb=(Token)match(input,106,FOLLOW_106_in_resourceDeclaration140); if (state.failed) return retval;

                    }


                    }
                    break;
                case 2 :
                    // ModelFlowParserRules.g:69:70: ';'
                    {
                    char_literal9=(Token)match(input,101,FOLLOW_101_in_resourceDeclaration146); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal9_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(char_literal9);
                    adaptor.addChild(root_0, char_literal9_tree);
                    }

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
              mo.setType(RESOURCEDECLARATION);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(mo);
              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(ob);
              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(cb);
              	
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end resourceDeclaration

    public static class taskDeclaration_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start taskDeclaration
    // ModelFlowParserRules.g:73:1: taskDeclaration : ta= 'task' NAME ruleType ( in )? ( inout )? ( out )? ( dependsOn )? ( forEach )? ( (ob= '{' ( guard )? ( propertyDeclaration )* cb= '}' ) | ';' ) ;
    public final ModelFlow_ModelFlowParserRules.taskDeclaration_return taskDeclaration() throws RecognitionException {
        ModelFlow_ModelFlowParserRules.taskDeclaration_return retval = new ModelFlow_ModelFlowParserRules.taskDeclaration_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token ta=null;
        Token ob=null;
        Token cb=null;
        Token NAME10=null;
        Token char_literal19=null;
        ModelFlow_ModelFlowParserRules.ruleType_return ruleType11 = null;

        ModelFlow_ModelFlowParserRules.in_return in12 = null;

        ModelFlow_ModelFlowParserRules.inout_return inout13 = null;

        ModelFlow_ModelFlowParserRules.out_return out14 = null;

        ModelFlow_ModelFlowParserRules.dependsOn_return dependsOn15 = null;

        ModelFlow_ModelFlowParserRules.forEach_return forEach16 = null;

        ModelFlow_ErlParserRules.guard_return guard17 = null;

        ModelFlow_ModelFlowParserRules.propertyDeclaration_return propertyDeclaration18 = null;


        org.eclipse.epsilon.common.parse.AST ta_tree=null;
        org.eclipse.epsilon.common.parse.AST ob_tree=null;
        org.eclipse.epsilon.common.parse.AST cb_tree=null;
        org.eclipse.epsilon.common.parse.AST NAME10_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal19_tree=null;

        try {
            // ModelFlowParserRules.g:79:2: (ta= 'task' NAME ruleType ( in )? ( inout )? ( out )? ( dependsOn )? ( forEach )? ( (ob= '{' ( guard )? ( propertyDeclaration )* cb= '}' ) | ';' ) )
            // ModelFlowParserRules.g:80:2: ta= 'task' NAME ruleType ( in )? ( inout )? ( out )? ( dependsOn )? ( forEach )? ( (ob= '{' ( guard )? ( propertyDeclaration )* cb= '}' ) | ';' )
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            ta=(Token)match(input,172,FOLLOW_172_in_taskDeclaration173); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            ta_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(ta);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(ta_tree, root_0);
            }
            NAME10=(Token)match(input,NAME,FOLLOW_NAME_in_taskDeclaration176); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            NAME10_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(NAME10);
            adaptor.addChild(root_0, NAME10_tree);
            }
            pushFollow(FOLLOW_ruleType_in_taskDeclaration178);
            ruleType11=ruleType();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, ruleType11.getTree());
            // ModelFlowParserRules.g:80:27: ( in )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==126) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // ModelFlowParserRules.g:0:0: in
                    {
                    pushFollow(FOLLOW_in_in_taskDeclaration180);
                    in12=in();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, in12.getTree());

                    }
                    break;

            }

            // ModelFlowParserRules.g:80:31: ( inout )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==174) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // ModelFlowParserRules.g:0:0: inout
                    {
                    pushFollow(FOLLOW_inout_in_taskDeclaration183);
                    inout13=inout();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, inout13.getTree());

                    }
                    break;

            }

            // ModelFlowParserRules.g:80:38: ( out )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==175) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // ModelFlowParserRules.g:0:0: out
                    {
                    pushFollow(FOLLOW_out_in_taskDeclaration186);
                    out14=out();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, out14.getTree());

                    }
                    break;

            }

            // ModelFlowParserRules.g:80:43: ( dependsOn )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==178) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // ModelFlowParserRules.g:0:0: dependsOn
                    {
                    pushFollow(FOLLOW_dependsOn_in_taskDeclaration189);
                    dependsOn15=dependsOn();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, dependsOn15.getTree());

                    }
                    break;

            }

            // ModelFlowParserRules.g:80:54: ( forEach )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==176) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // ModelFlowParserRules.g:0:0: forEach
                    {
                    pushFollow(FOLLOW_forEach_in_taskDeclaration192);
                    forEach16=forEach();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, forEach16.getTree());

                    }
                    break;

            }

            // ModelFlowParserRules.g:80:63: ( (ob= '{' ( guard )? ( propertyDeclaration )* cb= '}' ) | ';' )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==105) ) {
                alt11=1;
            }
            else if ( (LA11_0==101) ) {
                alt11=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // ModelFlowParserRules.g:80:64: (ob= '{' ( guard )? ( propertyDeclaration )* cb= '}' )
                    {
                    // ModelFlowParserRules.g:80:64: (ob= '{' ( guard )? ( propertyDeclaration )* cb= '}' )
                    // ModelFlowParserRules.g:80:65: ob= '{' ( guard )? ( propertyDeclaration )* cb= '}'
                    {
                    ob=(Token)match(input,105,FOLLOW_105_in_taskDeclaration199); if (state.failed) return retval;
                    // ModelFlowParserRules.g:80:73: ( guard )?
                    int alt9=2;
                    int LA9_0 = input.LA(1);

                    if ( (LA9_0==170) ) {
                        alt9=1;
                    }
                    switch (alt9) {
                        case 1 :
                            // ModelFlowParserRules.g:0:0: guard
                            {
                            pushFollow(FOLLOW_guard_in_taskDeclaration202);
                            guard17=gModelFlow.guard();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, guard17.getTree());

                            }
                            break;

                    }

                    // ModelFlowParserRules.g:80:80: ( propertyDeclaration )*
                    loop10:
                    do {
                        int alt10=2;
                        int LA10_0 = input.LA(1);

                        if ( (LA10_0==NAME) ) {
                            alt10=1;
                        }


                        switch (alt10) {
                    	case 1 :
                    	    // ModelFlowParserRules.g:0:0: propertyDeclaration
                    	    {
                    	    pushFollow(FOLLOW_propertyDeclaration_in_taskDeclaration205);
                    	    propertyDeclaration18=propertyDeclaration();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, propertyDeclaration18.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop10;
                        }
                    } while (true);

                    cb=(Token)match(input,106,FOLLOW_106_in_taskDeclaration210); if (state.failed) return retval;

                    }


                    }
                    break;
                case 2 :
                    // ModelFlowParserRules.g:80:112: ';'
                    {
                    char_literal19=(Token)match(input,101,FOLLOW_101_in_taskDeclaration216); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal19_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(char_literal19);
                    adaptor.addChild(root_0, char_literal19_tree);
                    }

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
              ta.setType(TASKDECLARATION);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(ta);
              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(ob);
              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(cb);
              	
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end taskDeclaration

    public static class ruleType_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start ruleType
    // ModelFlowParserRules.g:84:1: ruleType : ct= 'is' head= NAME ( ':' ty= NAME )? ;
    public final ModelFlow_ModelFlowParserRules.ruleType_return ruleType() throws RecognitionException {
        ModelFlow_ModelFlowParserRules.ruleType_return retval = new ModelFlow_ModelFlowParserRules.ruleType_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token ct=null;
        Token head=null;
        Token ty=null;
        Token char_literal20=null;

        org.eclipse.epsilon.common.parse.AST ct_tree=null;
        org.eclipse.epsilon.common.parse.AST head_tree=null;
        org.eclipse.epsilon.common.parse.AST ty_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal20_tree=null;

        try {
            // ModelFlowParserRules.g:88:2: (ct= 'is' head= NAME ( ':' ty= NAME )? )
            // ModelFlowParserRules.g:89:2: ct= 'is' head= NAME ( ':' ty= NAME )?
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            ct=(Token)match(input,173,FOLLOW_173_in_ruleType242); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            ct_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(ct);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(ct_tree, root_0);
            }
            head=(Token)match(input,NAME,FOLLOW_NAME_in_ruleType247); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            head_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(head);
            adaptor.addChild(root_0, head_tree);
            }
            // ModelFlowParserRules.g:89:21: ( ':' ty= NAME )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==112) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // ModelFlowParserRules.g:89:22: ':' ty= NAME
                    {
                    char_literal20=(Token)match(input,112,FOLLOW_112_in_ruleType250); if (state.failed) return retval;
                    ty=(Token)match(input,NAME,FOLLOW_NAME_in_ruleType255); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ty_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(ty);
                    adaptor.addChild(root_0, ty_tree);
                    }

                    }
                    break;

            }

            if ( state.backtracking==0 ) {

              		if (ty != null){
              			head_tree.token.setText(head_tree.token.getText() + ":" + ty.getText() );
              		}
              		ct.setType(RULETYPE);
              	
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(ct);
              	
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end ruleType

    public static class propertyDeclaration_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start propertyDeclaration
    // ModelFlowParserRules.g:98:1: propertyDeclaration : p= NAME expressionOrStatementBlock ;
    public final ModelFlow_ModelFlowParserRules.propertyDeclaration_return propertyDeclaration() throws RecognitionException {
        ModelFlow_ModelFlowParserRules.propertyDeclaration_return retval = new ModelFlow_ModelFlowParserRules.propertyDeclaration_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token p=null;
        ModelFlow_EolParserRules.expressionOrStatementBlock_return expressionOrStatementBlock21 = null;


        org.eclipse.epsilon.common.parse.AST p_tree=null;

        try {
            // ModelFlowParserRules.g:99:2: (p= NAME expressionOrStatementBlock )
            // ModelFlowParserRules.g:100:2: p= NAME expressionOrStatementBlock
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            p=(Token)match(input,NAME,FOLLOW_NAME_in_propertyDeclaration279); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            p_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(p);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(p_tree, root_0);
            }
            pushFollow(FOLLOW_expressionOrStatementBlock_in_propertyDeclaration282);
            expressionOrStatementBlock21=gModelFlow.expressionOrStatementBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionOrStatementBlock21.getTree());
            if ( state.backtracking==0 ) {
              p.setType(PROPERTY);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end propertyDeclaration

    public static class in_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start in
    // ModelFlowParserRules.g:104:1: in : i= 'in' taskResourceList ;
    public final ModelFlow_ModelFlowParserRules.in_return in() throws RecognitionException {
        ModelFlow_ModelFlowParserRules.in_return retval = new ModelFlow_ModelFlowParserRules.in_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token i=null;
        ModelFlow_ModelFlowParserRules.taskResourceList_return taskResourceList22 = null;


        org.eclipse.epsilon.common.parse.AST i_tree=null;

        try {
            // ModelFlowParserRules.g:108:2: (i= 'in' taskResourceList )
            // ModelFlowParserRules.g:109:2: i= 'in' taskResourceList
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            i=(Token)match(input,126,FOLLOW_126_in_in306); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            i_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(i);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(i_tree, root_0);
            }
            pushFollow(FOLLOW_taskResourceList_in_in309);
            taskResourceList22=taskResourceList();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, taskResourceList22.getTree());
            if ( state.backtracking==0 ) {
              i.setType(INPUTS);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(i);
              	
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end in

    public static class inout_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start inout
    // ModelFlowParserRules.g:113:1: inout : i= 'inout' taskResourceList ;
    public final ModelFlow_ModelFlowParserRules.inout_return inout() throws RecognitionException {
        ModelFlow_ModelFlowParserRules.inout_return retval = new ModelFlow_ModelFlowParserRules.inout_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token i=null;
        ModelFlow_ModelFlowParserRules.taskResourceList_return taskResourceList23 = null;


        org.eclipse.epsilon.common.parse.AST i_tree=null;

        try {
            // ModelFlowParserRules.g:117:2: (i= 'inout' taskResourceList )
            // ModelFlowParserRules.g:118:2: i= 'inout' taskResourceList
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            i=(Token)match(input,174,FOLLOW_174_in_inout334); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            i_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(i);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(i_tree, root_0);
            }
            pushFollow(FOLLOW_taskResourceList_in_inout337);
            taskResourceList23=taskResourceList();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, taskResourceList23.getTree());
            if ( state.backtracking==0 ) {
              i.setType(INOUTS);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(i);
              	
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end inout

    public static class out_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start out
    // ModelFlowParserRules.g:122:1: out : o= 'out' taskResourceList ;
    public final ModelFlow_ModelFlowParserRules.out_return out() throws RecognitionException {
        ModelFlow_ModelFlowParserRules.out_return retval = new ModelFlow_ModelFlowParserRules.out_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token o=null;
        ModelFlow_ModelFlowParserRules.taskResourceList_return taskResourceList24 = null;


        org.eclipse.epsilon.common.parse.AST o_tree=null;

        try {
            // ModelFlowParserRules.g:126:2: (o= 'out' taskResourceList )
            // ModelFlowParserRules.g:127:2: o= 'out' taskResourceList
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            o=(Token)match(input,175,FOLLOW_175_in_out362); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            o_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(o);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(o_tree, root_0);
            }
            pushFollow(FOLLOW_taskResourceList_in_out365);
            taskResourceList24=taskResourceList();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, taskResourceList24.getTree());
            if ( state.backtracking==0 ) {
              o.setType(OUTPUTS);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(o);
              	
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end out

    public static class forEach_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start forEach
    // ModelFlowParserRules.g:131:1: forEach : fe= 'forEach' formalParameter ( 'as' expressionOrStatementBlock )? 'in' expressionOrStatementBlock ;
    public final ModelFlow_ModelFlowParserRules.forEach_return forEach() throws RecognitionException {
        ModelFlow_ModelFlowParserRules.forEach_return retval = new ModelFlow_ModelFlowParserRules.forEach_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token fe=null;
        Token string_literal26=null;
        Token string_literal28=null;
        ModelFlow_EolParserRules.formalParameter_return formalParameter25 = null;

        ModelFlow_EolParserRules.expressionOrStatementBlock_return expressionOrStatementBlock27 = null;

        ModelFlow_EolParserRules.expressionOrStatementBlock_return expressionOrStatementBlock29 = null;


        org.eclipse.epsilon.common.parse.AST fe_tree=null;
        org.eclipse.epsilon.common.parse.AST string_literal26_tree=null;
        org.eclipse.epsilon.common.parse.AST string_literal28_tree=null;

        try {
            // ModelFlowParserRules.g:135:2: (fe= 'forEach' formalParameter ( 'as' expressionOrStatementBlock )? 'in' expressionOrStatementBlock )
            // ModelFlowParserRules.g:136:2: fe= 'forEach' formalParameter ( 'as' expressionOrStatementBlock )? 'in' expressionOrStatementBlock
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            fe=(Token)match(input,176,FOLLOW_176_in_forEach390); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            fe_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(fe);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(fe_tree, root_0);
            }
            pushFollow(FOLLOW_formalParameter_in_forEach393);
            formalParameter25=gModelFlow.formalParameter();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameter25.getTree());
            // ModelFlowParserRules.g:136:32: ( 'as' expressionOrStatementBlock )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==177) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // ModelFlowParserRules.g:136:33: 'as' expressionOrStatementBlock
                    {
                    string_literal26=(Token)match(input,177,FOLLOW_177_in_forEach396); if (state.failed) return retval;
                    pushFollow(FOLLOW_expressionOrStatementBlock_in_forEach399);
                    expressionOrStatementBlock27=gModelFlow.expressionOrStatementBlock();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionOrStatementBlock27.getTree());

                    }
                    break;

            }

            string_literal28=(Token)match(input,126,FOLLOW_126_in_forEach403); if (state.failed) return retval;
            pushFollow(FOLLOW_expressionOrStatementBlock_in_forEach406);
            expressionOrStatementBlock29=gModelFlow.expressionOrStatementBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionOrStatementBlock29.getTree());
            if ( state.backtracking==0 ) {
              fe.setType(FOREACH);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(fe);
              	
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end forEach

    public static class taskResourceList_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start taskResourceList
    // ModelFlowParserRules.g:140:1: taskResourceList : taskResource ( 'and' taskResource )* -> ^( RESLIST ( taskResource )* ) ;
    public final ModelFlow_ModelFlowParserRules.taskResourceList_return taskResourceList() throws RecognitionException {
        ModelFlow_ModelFlowParserRules.taskResourceList_return retval = new ModelFlow_ModelFlowParserRules.taskResourceList_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token string_literal31=null;
        ModelFlow_ModelFlowParserRules.taskResource_return taskResource30 = null;

        ModelFlow_ModelFlowParserRules.taskResource_return taskResource32 = null;


        org.eclipse.epsilon.common.parse.AST string_literal31_tree=null;
        RewriteRuleTokenStream stream_144=new RewriteRuleTokenStream(adaptor,"token 144");
        RewriteRuleSubtreeStream stream_taskResource=new RewriteRuleSubtreeStream(adaptor,"rule taskResource");
        try {
            // ModelFlowParserRules.g:144:2: ( taskResource ( 'and' taskResource )* -> ^( RESLIST ( taskResource )* ) )
            // ModelFlowParserRules.g:145:2: taskResource ( 'and' taskResource )*
            {
            pushFollow(FOLLOW_taskResource_in_taskResourceList429);
            taskResource30=taskResource();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_taskResource.add(taskResource30.getTree());
            // ModelFlowParserRules.g:145:15: ( 'and' taskResource )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==144) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // ModelFlowParserRules.g:145:16: 'and' taskResource
            	    {
            	    string_literal31=(Token)match(input,144,FOLLOW_144_in_taskResourceList432); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_144.add(string_literal31);

            	    pushFollow(FOLLOW_taskResource_in_taskResourceList434);
            	    taskResource32=taskResource();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_taskResource.add(taskResource32.getTree());

            	    }
            	    break;

            	default :
            	    break loop14;
                }
            } while (true);



            // AST REWRITE
            // elements: taskResource
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();
            // 146:2: -> ^( RESLIST ( taskResource )* )
            {
                // ModelFlowParserRules.g:146:5: ^( RESLIST ( taskResource )* )
                {
                org.eclipse.epsilon.common.parse.AST root_1 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();
                root_1 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot((org.eclipse.epsilon.common.parse.AST)adaptor.create(RESLIST, "RESLIST"), root_1);

                // ModelFlowParserRules.g:146:15: ( taskResource )*
                while ( stream_taskResource.hasNext() ) {
                    adaptor.addChild(root_1, stream_taskResource.nextTree());

                }
                stream_taskResource.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

              		((org.eclipse.epsilon.common.parse.AST)retval.tree).setImaginary(true);
              	
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end taskResourceList

    public static class taskResource_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start taskResource
    // ModelFlowParserRules.g:149:1: taskResource : res= NAME ( '.' ind= NAME )? (a= 'as' NAME ( ',' NAME )* )? ;
    public final ModelFlow_ModelFlowParserRules.taskResource_return taskResource() throws RecognitionException {
        ModelFlow_ModelFlowParserRules.taskResource_return retval = new ModelFlow_ModelFlowParserRules.taskResource_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token res=null;
        Token ind=null;
        Token a=null;
        Token char_literal33=null;
        Token NAME34=null;
        Token char_literal35=null;
        Token NAME36=null;

        org.eclipse.epsilon.common.parse.AST res_tree=null;
        org.eclipse.epsilon.common.parse.AST ind_tree=null;
        org.eclipse.epsilon.common.parse.AST a_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal33_tree=null;
        org.eclipse.epsilon.common.parse.AST NAME34_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal35_tree=null;
        org.eclipse.epsilon.common.parse.AST NAME36_tree=null;

        try {
            // ModelFlowParserRules.g:153:2: (res= NAME ( '.' ind= NAME )? (a= 'as' NAME ( ',' NAME )* )? )
            // ModelFlowParserRules.g:154:2: res= NAME ( '.' ind= NAME )? (a= 'as' NAME ( ',' NAME )* )?
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            res=(Token)match(input,NAME,FOLLOW_NAME_in_taskResource468); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            res_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(res);
            adaptor.addChild(root_0, res_tree);
            }
            // ModelFlowParserRules.g:154:11: ( '.' ind= NAME )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==POINT) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // ModelFlowParserRules.g:154:12: '.' ind= NAME
                    {
                    char_literal33=(Token)match(input,POINT,FOLLOW_POINT_in_taskResource471); if (state.failed) return retval;
                    ind=(Token)match(input,NAME,FOLLOW_NAME_in_taskResource476); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ind_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(ind);
                    adaptor.addChild(root_0, ind_tree);
                    }

                    }
                    break;

            }

            // ModelFlowParserRules.g:154:28: (a= 'as' NAME ( ',' NAME )* )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==177) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // ModelFlowParserRules.g:154:29: a= 'as' NAME ( ',' NAME )*
                    {
                    a=(Token)match(input,177,FOLLOW_177_in_taskResource483); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    a_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(a);
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(a_tree, root_0);
                    }
                    NAME34=(Token)match(input,NAME,FOLLOW_NAME_in_taskResource486); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NAME34_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(NAME34);
                    adaptor.addChild(root_0, NAME34_tree);
                    }
                    // ModelFlowParserRules.g:154:42: ( ',' NAME )*
                    loop16:
                    do {
                        int alt16=2;
                        int LA16_0 = input.LA(1);

                        if ( (LA16_0==103) ) {
                            alt16=1;
                        }


                        switch (alt16) {
                    	case 1 :
                    	    // ModelFlowParserRules.g:154:43: ',' NAME
                    	    {
                    	    char_literal35=(Token)match(input,103,FOLLOW_103_in_taskResource489); if (state.failed) return retval;
                    	    NAME36=(Token)match(input,NAME,FOLLOW_NAME_in_taskResource492); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    NAME36_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(NAME36);
                    	    adaptor.addChild(root_0, NAME36_tree);
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop16;
                        }
                    } while (true);


                    }
                    break;

            }

            if ( state.backtracking==0 ) {

              		if (ind != null){
              			res.setText(res.getText() + "." + ind.getText() );
              		}
              		res.setType(TASKRESOURCE);	
              	
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(a);
              	
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end taskResource

    public static class dependsOn_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start dependsOn
    // ModelFlowParserRules.g:163:1: dependsOn : t= 'dependsOn' NAME ( ',' NAME )* ;
    public final ModelFlow_ModelFlowParserRules.dependsOn_return dependsOn() throws RecognitionException {
        ModelFlow_ModelFlowParserRules.dependsOn_return retval = new ModelFlow_ModelFlowParserRules.dependsOn_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token t=null;
        Token NAME37=null;
        Token char_literal38=null;
        Token NAME39=null;

        org.eclipse.epsilon.common.parse.AST t_tree=null;
        org.eclipse.epsilon.common.parse.AST NAME37_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal38_tree=null;
        org.eclipse.epsilon.common.parse.AST NAME39_tree=null;

        try {
            // ModelFlowParserRules.g:167:2: (t= 'dependsOn' NAME ( ',' NAME )* )
            // ModelFlowParserRules.g:168:2: t= 'dependsOn' NAME ( ',' NAME )*
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            t=(Token)match(input,178,FOLLOW_178_in_dependsOn521); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            t_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(t);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(t_tree, root_0);
            }
            NAME37=(Token)match(input,NAME,FOLLOW_NAME_in_dependsOn524); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            NAME37_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(NAME37);
            adaptor.addChild(root_0, NAME37_tree);
            }
            // ModelFlowParserRules.g:168:22: ( ',' NAME )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( (LA18_0==103) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // ModelFlowParserRules.g:168:23: ',' NAME
            	    {
            	    char_literal38=(Token)match(input,103,FOLLOW_103_in_dependsOn527); if (state.failed) return retval;
            	    NAME39=(Token)match(input,NAME,FOLLOW_NAME_in_dependsOn530); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    NAME39_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(NAME39);
            	    adaptor.addChild(root_0, NAME39_tree);
            	    }

            	    }
            	    break;

            	default :
            	    break loop18;
                }
            } while (true);

            if ( state.backtracking==0 ) {
              t.setType(DEPENDSON);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(t);
              	
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end dependsOn

    public static class paramDeclarationExpression_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start paramDeclarationExpression
    // ModelFlowParserRules.g:173:1: paramDeclarationExpression : p= 'param' NAME ( ':' t= typeName )? ';' ;
    public final ModelFlow_ModelFlowParserRules.paramDeclarationExpression_return paramDeclarationExpression() throws RecognitionException {
        ModelFlow_ModelFlowParserRules.paramDeclarationExpression_return retval = new ModelFlow_ModelFlowParserRules.paramDeclarationExpression_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token p=null;
        Token NAME40=null;
        Token char_literal41=null;
        Token char_literal42=null;
        ModelFlow_EolParserRules.typeName_return t = null;


        org.eclipse.epsilon.common.parse.AST p_tree=null;
        org.eclipse.epsilon.common.parse.AST NAME40_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal41_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal42_tree=null;

        try {
            // ModelFlowParserRules.g:179:2: (p= 'param' NAME ( ':' t= typeName )? ';' )
            // ModelFlowParserRules.g:180:2: p= 'param' NAME ( ':' t= typeName )? ';'
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            p=(Token)match(input,179,FOLLOW_179_in_paramDeclarationExpression557); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            p_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(p);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(p_tree, root_0);
            }
            NAME40=(Token)match(input,NAME,FOLLOW_NAME_in_paramDeclarationExpression560); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            NAME40_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(NAME40);
            adaptor.addChild(root_0, NAME40_tree);
            }
            // ModelFlowParserRules.g:180:18: ( ':' t= typeName )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==112) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // ModelFlowParserRules.g:180:19: ':' t= typeName
                    {
                    char_literal41=(Token)match(input,112,FOLLOW_112_in_paramDeclarationExpression563); if (state.failed) return retval;
                    pushFollow(FOLLOW_typeName_in_paramDeclarationExpression568);
                    t=gModelFlow.typeName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, t.getTree());

                    }
                    break;

            }

            char_literal42=(Token)match(input,101,FOLLOW_101_in_paramDeclarationExpression572); if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

              		String txt = p.getText();
              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getToken().setText(txt);
              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getToken().setType(PARAMDECLARATION);
              	
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end paramDeclarationExpression

    // Delegated rules


 

    public static final BitSet FOLLOW_paramDeclarationExpression_in_workflowContents83 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_resourceDeclaration_in_workflowContents87 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_taskDeclaration_in_workflowContents91 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_annotationBlock_in_workflowContents95 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_operationDeclaration_in_workflowContents99 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_100_in_resourceDeclaration121 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_NAME_in_resourceDeclaration124 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_ruleType_in_resourceDeclaration126 = new BitSet(new long[]{0x0000000000000000L,0x0000022000000000L});
    public static final BitSet FOLLOW_105_in_resourceDeclaration132 = new BitSet(new long[]{0x0000000000800000L,0x0000040000000000L});
    public static final BitSet FOLLOW_propertyDeclaration_in_resourceDeclaration135 = new BitSet(new long[]{0x0000000000800000L,0x0000040000000000L});
    public static final BitSet FOLLOW_106_in_resourceDeclaration140 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_101_in_resourceDeclaration146 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_172_in_taskDeclaration173 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_NAME_in_taskDeclaration176 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_ruleType_in_taskDeclaration178 = new BitSet(new long[]{0x0000000000000000L,0x4000022000000000L,0x0005C00000000000L});
    public static final BitSet FOLLOW_in_in_taskDeclaration180 = new BitSet(new long[]{0x0000000000000000L,0x0000022000000000L,0x0005C00000000000L});
    public static final BitSet FOLLOW_inout_in_taskDeclaration183 = new BitSet(new long[]{0x0000000000000000L,0x0000022000000000L,0x0005800000000000L});
    public static final BitSet FOLLOW_out_in_taskDeclaration186 = new BitSet(new long[]{0x0000000000000000L,0x0000022000000000L,0x0005000000000000L});
    public static final BitSet FOLLOW_dependsOn_in_taskDeclaration189 = new BitSet(new long[]{0x0000000000000000L,0x0000022000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_forEach_in_taskDeclaration192 = new BitSet(new long[]{0x0000000000000000L,0x0000022000000000L});
    public static final BitSet FOLLOW_105_in_taskDeclaration199 = new BitSet(new long[]{0x0000000000800000L,0x0000040000000000L,0x0000040000000000L});
    public static final BitSet FOLLOW_guard_in_taskDeclaration202 = new BitSet(new long[]{0x0000000000800000L,0x0000040000000000L});
    public static final BitSet FOLLOW_propertyDeclaration_in_taskDeclaration205 = new BitSet(new long[]{0x0000000000800000L,0x0000040000000000L});
    public static final BitSet FOLLOW_106_in_taskDeclaration210 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_101_in_taskDeclaration216 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_173_in_ruleType242 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_NAME_in_ruleType247 = new BitSet(new long[]{0x0000000000000002L,0x0001000000000000L});
    public static final BitSet FOLLOW_112_in_ruleType250 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_NAME_in_ruleType255 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NAME_in_propertyDeclaration279 = new BitSet(new long[]{0x0000000000000000L,0x0001020000000000L});
    public static final BitSet FOLLOW_expressionOrStatementBlock_in_propertyDeclaration282 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_126_in_in306 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_taskResourceList_in_in309 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_174_in_inout334 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_taskResourceList_in_inout337 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_175_in_out362 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_taskResourceList_in_out365 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_176_in_forEach390 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_formalParameter_in_forEach393 = new BitSet(new long[]{0x0000000000000000L,0x4000000000000000L,0x0002000000000000L});
    public static final BitSet FOLLOW_177_in_forEach396 = new BitSet(new long[]{0x0000000000000000L,0x0001020000000000L});
    public static final BitSet FOLLOW_expressionOrStatementBlock_in_forEach399 = new BitSet(new long[]{0x0000000000000000L,0x4000000000000000L});
    public static final BitSet FOLLOW_126_in_forEach403 = new BitSet(new long[]{0x0000000000000000L,0x0001020000000000L});
    public static final BitSet FOLLOW_expressionOrStatementBlock_in_forEach406 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_taskResource_in_taskResourceList429 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_144_in_taskResourceList432 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_taskResource_in_taskResourceList434 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_NAME_in_taskResource468 = new BitSet(new long[]{0x0000000000000202L,0x0000000000000000L,0x0002000000000000L});
    public static final BitSet FOLLOW_POINT_in_taskResource471 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_NAME_in_taskResource476 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0002000000000000L});
    public static final BitSet FOLLOW_177_in_taskResource483 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_NAME_in_taskResource486 = new BitSet(new long[]{0x0000000000000002L,0x0000008000000000L});
    public static final BitSet FOLLOW_103_in_taskResource489 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_NAME_in_taskResource492 = new BitSet(new long[]{0x0000000000000002L,0x0000008000000000L});
    public static final BitSet FOLLOW_178_in_dependsOn521 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_NAME_in_dependsOn524 = new BitSet(new long[]{0x0000000000000002L,0x0000008000000000L});
    public static final BitSet FOLLOW_103_in_dependsOn527 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_NAME_in_dependsOn530 = new BitSet(new long[]{0x0000000000000002L,0x0000008000000000L});
    public static final BitSet FOLLOW_179_in_paramDeclarationExpression557 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_NAME_in_paramDeclarationExpression560 = new BitSet(new long[]{0x0000000000000000L,0x0001002000000000L});
    public static final BitSet FOLLOW_112_in_paramDeclarationExpression563 = new BitSet(new long[]{0x00000000008E0000L});
    public static final BitSet FOLLOW_typeName_in_paramDeclarationExpression568 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_101_in_paramDeclarationExpression572 = new BitSet(new long[]{0x0000000000000002L});

}
