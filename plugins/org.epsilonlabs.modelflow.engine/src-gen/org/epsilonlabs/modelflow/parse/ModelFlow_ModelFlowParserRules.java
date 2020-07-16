package org.epsilonlabs.modelflow.parse;

// $ANTLR 3.1b1 ModelFlowParserRules.g 2020-07-01 10:22:20

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
    public static final int RULETYPE=84;
    public static final int T__143=143;
    public static final int T__146=146;
    public static final int MODELDECLARATIONPARAMETER=74;
    public static final int T__145=145;
    public static final int BREAKALL=40;
    public static final int T__140=140;
    public static final int T__142=142;
    public static final int VAR=49;
    public static final int MODELDECLARATIONPARAMETERS=73;
    public static final int T__141=141;
    public static final int THROW=54;
    public static final int PARAMLIST=25;
    public static final int EXPRLIST=55;
    public static final int EXPRRANGE=56;
    public static final int BREAK=39;
    public static final int ELSE=32;
    public static final int TASKRESOURCE=90;
    public static final int T__137=137;
    public static final int T__136=136;
    public static final int FORMAL=24;
    public static final int IF=31;
    public static final int MultiplicativeExpression=58;
    public static final int TYPE=66;
    public static final int T__139=139;
    public static final int T__138=138;
    public static final int T__133=133;
    public static final int T__132=132;
    public static final int T__135=135;
    public static final int T__134=134;
    public static final int T__131=131;
    public static final int NewExpression=48;
    public static final int T__130=130;
    public static final int RESLIST=91;
    public static final int CASE=36;
    public static final int Letter=16;
    public static final int LINE_COMMENT=22;
    public static final int T__129=129;
    public static final int T__126=126;
    public static final int JavaIDDigit=18;
    public static final int T__125=125;
    public static final int LAMBDAEXPR=65;
    public static final int MAP=76;
    public static final int T__128=128;
    public static final int T__127=127;
    public static final int T__166=166;
    public static final int T__165=165;
    public static final int T__168=168;
    public static final int T__167=167;
    public static final int T__162=162;
    public static final int T__161=161;
    public static final int T__164=164;
    public static final int MODELDECLARATION=69;
    public static final int T__163=163;
    public static final int EXPRESSIONINBRACKETS=60;
    public static final int T__160=160;
    public static final int TERNARY=33;
    public static final int TRANSACTION=42;
    public static final int FLOAT_TYPE_SUFFIX=7;
    public static final int ITEMSELECTOR=75;
    public static final int COMMENT=21;
    public static final int ModelElementType=46;
    public static final int IMPORT=68;
    public static final int DELETE=53;
    public static final int ARROW=11;
    public static final int T__159=159;
    public static final int T__158=158;
    public static final int T__155=155;
    public static final int SPECIAL_ASSIGNMENT=27;
    public static final int T__154=154;
    public static final int T__157=157;
    public static final int T__156=156;
    public static final int T__151=151;
    public static final int T__150=150;
    public static final int T__153=153;
    public static final int T__152=152;
    public static final int Annotation=23;
    public static final int CONTINUE=41;
    public static final int ENUMERATION_VALUE=67;
    public static final int OPERATOR=59;
    public static final int EXPONENT=6;
    public static final int STRING=14;
    public static final int T__148=148;
    public static final int DEPENDSON=92;
    public static final int T__147=147;
    public static final int T__149=149;
    public static final int T__100=100;
    public static final int NAMESPACE=70;
    public static final int COLLECTION=43;
    public static final int NEW=50;
    public static final int EXTENDS=81;
    public static final int T__102=102;
    public static final int PRE=79;
    public static final int T__101=101;
    public static final int POST=80;
    public static final int ALIAS=71;
    public static final int DRIVER=72;
    public static final int T__180=180;
    public static final int INOUTS=89;
    public static final int T__181=181;
    public static final int KEYVAL=77;
    public static final int POINT_POINT=10;
    public static final int GUARD=82;
    public static final int T__99=99;
    public static final int T__95=95;
    public static final int HELPERMETHOD=28;
    public static final int T__96=96;
    public static final int T__97=97;
    public static final int StatementBlock=29;
    public static final int T__98=98;
    public static final int T__177=177;
    public static final int T__176=176;
    public static final int T__179=179;
    public static final int T__178=178;
    public static final int ABORT=44;
    public static final int T__173=173;
    public static final int StrangeNameLiteral=15;
    public static final int T__172=172;
    public static final int T__175=175;
    public static final int PROPERTY=83;
    public static final int T__174=174;
    public static final int FOR=30;
    public static final int BLOCK=63;
    public static final int T__171=171;
    public static final int T__170=170;
    public static final int PARAMETERS=47;
    public static final int SpecialNameChar=17;
    public static final int BOOLEAN=12;
    public static final int NAME=19;
    public static final int OUTPUTS=88;
    public static final int PARAMDECLARATION=93;
    public static final int SWITCH=35;
    public static final int T__169=169;
    public static final int FeatureCall=61;
    public static final int T__122=122;
    public static final int T__121=121;
    public static final int T__124=124;
    public static final int FLOAT=4;
    public static final int T__123=123;
    public static final int T__120=120;
    public static final int NativeType=57;
    public static final int INT=8;
    public static final int ANNOTATIONBLOCK=51;
    public static final int RETURN=38;
    public static final int KEYVALLIST=78;
    public static final int FEATURECALL=64;
    public static final int CollectionType=45;
    public static final int T__119=119;
    public static final int ASSIGNMENT=26;
    public static final int T__118=118;
    public static final int T__115=115;
    public static final int WS=20;
    public static final int EOF=-1;
    public static final int T__114=114;
    public static final int T__117=117;
    public static final int T__116=116;
    public static final int T__111=111;
    public static final int T__110=110;
    public static final int T__113=113;
    public static final int T__112=112;
    public static final int MODELFLOWMODULE=94;
    public static final int EscapeSequence=13;
    public static final int EOLMODULE=62;
    public static final int INPUTS=87;
    public static final int DIGIT=5;
    public static final int EXECUTABLEANNOTATION=52;
    public static final int TASKDECLARATION=86;
    public static final int T__108=108;
    public static final int T__107=107;
    public static final int WHILE=34;
    public static final int T__109=109;
    public static final int RESOURCEDECLARATION=85;
    public static final int T__104=104;
    public static final int POINT=9;
    public static final int T__103=103;
    public static final int T__106=106;
    public static final int DEFAULT=37;
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
    // ModelFlowParserRules.g:56:1: workflowContents : ( paramDeclarationExpression | resourceDeclaration | taskDeclaration | annotationBlock | operationDeclaration );
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
            // ModelFlowParserRules.g:57:2: ( paramDeclarationExpression | resourceDeclaration | taskDeclaration | annotationBlock | operationDeclaration )
            int alt1=5;
            switch ( input.LA(1) ) {
            case 181:
                {
                alt1=1;
                }
                break;
            case 95:
                {
                alt1=2;
                }
                break;
            case 175:
                {
                alt1=3;
                }
                break;
            case Annotation:
            case 109:
                {
                alt1=4;
                }
                break;
            case 103:
            case 104:
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
                    // ModelFlowParserRules.g:58:2: paramDeclarationExpression
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_paramDeclarationExpression_in_workflowContents79);
                    paramDeclarationExpression1=paramDeclarationExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, paramDeclarationExpression1.getTree());

                    }
                    break;
                case 2 :
                    // ModelFlowParserRules.g:58:31: resourceDeclaration
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_resourceDeclaration_in_workflowContents83);
                    resourceDeclaration2=resourceDeclaration();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, resourceDeclaration2.getTree());

                    }
                    break;
                case 3 :
                    // ModelFlowParserRules.g:58:53: taskDeclaration
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_taskDeclaration_in_workflowContents87);
                    taskDeclaration3=taskDeclaration();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, taskDeclaration3.getTree());

                    }
                    break;
                case 4 :
                    // ModelFlowParserRules.g:58:71: annotationBlock
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_annotationBlock_in_workflowContents91);
                    annotationBlock4=gModelFlow.annotationBlock();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, annotationBlock4.getTree());

                    }
                    break;
                case 5 :
                    // ModelFlowParserRules.g:58:89: operationDeclaration
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_operationDeclaration_in_workflowContents95);
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
    // ModelFlowParserRules.g:61:1: resourceDeclaration : mo= 'model' NAME ruleType ( (ob= '{' ( propertyDeclaration )* cb= '}' ) | ';' ) ;
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
            // ModelFlowParserRules.g:67:2: (mo= 'model' NAME ruleType ( (ob= '{' ( propertyDeclaration )* cb= '}' ) | ';' ) )
            // ModelFlowParserRules.g:68:2: mo= 'model' NAME ruleType ( (ob= '{' ( propertyDeclaration )* cb= '}' ) | ';' )
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            mo=(Token)match(input,95,FOLLOW_95_in_resourceDeclaration117); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            mo_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(mo);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(mo_tree, root_0);
            }
            NAME6=(Token)match(input,NAME,FOLLOW_NAME_in_resourceDeclaration120); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            NAME6_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(NAME6);
            adaptor.addChild(root_0, NAME6_tree);
            }
            pushFollow(FOLLOW_ruleType_in_resourceDeclaration122);
            ruleType7=ruleType();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, ruleType7.getTree());
            // ModelFlowParserRules.g:68:28: ( (ob= '{' ( propertyDeclaration )* cb= '}' ) | ';' )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==100) ) {
                alt3=1;
            }
            else if ( (LA3_0==96) ) {
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
                    // ModelFlowParserRules.g:68:29: (ob= '{' ( propertyDeclaration )* cb= '}' )
                    {
                    // ModelFlowParserRules.g:68:29: (ob= '{' ( propertyDeclaration )* cb= '}' )
                    // ModelFlowParserRules.g:68:30: ob= '{' ( propertyDeclaration )* cb= '}'
                    {
                    ob=(Token)match(input,100,FOLLOW_100_in_resourceDeclaration128); if (state.failed) return retval;
                    // ModelFlowParserRules.g:68:38: ( propertyDeclaration )*
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
                    	    pushFollow(FOLLOW_propertyDeclaration_in_resourceDeclaration131);
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

                    cb=(Token)match(input,101,FOLLOW_101_in_resourceDeclaration136); if (state.failed) return retval;

                    }


                    }
                    break;
                case 2 :
                    // ModelFlowParserRules.g:68:70: ';'
                    {
                    char_literal9=(Token)match(input,96,FOLLOW_96_in_resourceDeclaration142); if (state.failed) return retval;
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
    // ModelFlowParserRules.g:72:1: taskDeclaration : ta= 'task' NAME ruleType ( in )? ( inout )? ( out )? ( dependsOn )? ( (ob= '{' ( guard )? ( propertyDeclaration )* cb= '}' ) | ';' ) ;
    public final ModelFlow_ModelFlowParserRules.taskDeclaration_return taskDeclaration() throws RecognitionException {
        ModelFlow_ModelFlowParserRules.taskDeclaration_return retval = new ModelFlow_ModelFlowParserRules.taskDeclaration_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token ta=null;
        Token ob=null;
        Token cb=null;
        Token NAME10=null;
        Token char_literal18=null;
        ModelFlow_ModelFlowParserRules.ruleType_return ruleType11 = null;

        ModelFlow_ModelFlowParserRules.in_return in12 = null;

        ModelFlow_ModelFlowParserRules.inout_return inout13 = null;

        ModelFlow_ModelFlowParserRules.out_return out14 = null;

        ModelFlow_ModelFlowParserRules.dependsOn_return dependsOn15 = null;

        ModelFlow_ErlParserRules.guard_return guard16 = null;

        ModelFlow_ModelFlowParserRules.propertyDeclaration_return propertyDeclaration17 = null;


        org.eclipse.epsilon.common.parse.AST ta_tree=null;
        org.eclipse.epsilon.common.parse.AST ob_tree=null;
        org.eclipse.epsilon.common.parse.AST cb_tree=null;
        org.eclipse.epsilon.common.parse.AST NAME10_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal18_tree=null;

        try {
            // ModelFlowParserRules.g:78:2: (ta= 'task' NAME ruleType ( in )? ( inout )? ( out )? ( dependsOn )? ( (ob= '{' ( guard )? ( propertyDeclaration )* cb= '}' ) | ';' ) )
            // ModelFlowParserRules.g:79:2: ta= 'task' NAME ruleType ( in )? ( inout )? ( out )? ( dependsOn )? ( (ob= '{' ( guard )? ( propertyDeclaration )* cb= '}' ) | ';' )
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            ta=(Token)match(input,175,FOLLOW_175_in_taskDeclaration169); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            ta_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(ta);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(ta_tree, root_0);
            }
            NAME10=(Token)match(input,NAME,FOLLOW_NAME_in_taskDeclaration172); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            NAME10_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(NAME10);
            adaptor.addChild(root_0, NAME10_tree);
            }
            pushFollow(FOLLOW_ruleType_in_taskDeclaration174);
            ruleType11=ruleType();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, ruleType11.getTree());
            // ModelFlowParserRules.g:79:27: ( in )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==132) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // ModelFlowParserRules.g:0:0: in
                    {
                    pushFollow(FOLLOW_in_in_taskDeclaration176);
                    in12=in();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, in12.getTree());

                    }
                    break;

            }

            // ModelFlowParserRules.g:79:31: ( inout )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==177) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // ModelFlowParserRules.g:0:0: inout
                    {
                    pushFollow(FOLLOW_inout_in_taskDeclaration179);
                    inout13=inout();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, inout13.getTree());

                    }
                    break;

            }

            // ModelFlowParserRules.g:79:38: ( out )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==178) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // ModelFlowParserRules.g:0:0: out
                    {
                    pushFollow(FOLLOW_out_in_taskDeclaration182);
                    out14=out();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, out14.getTree());

                    }
                    break;

            }

            // ModelFlowParserRules.g:79:43: ( dependsOn )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==180) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // ModelFlowParserRules.g:0:0: dependsOn
                    {
                    pushFollow(FOLLOW_dependsOn_in_taskDeclaration185);
                    dependsOn15=dependsOn();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, dependsOn15.getTree());

                    }
                    break;

            }

            // ModelFlowParserRules.g:79:54: ( (ob= '{' ( guard )? ( propertyDeclaration )* cb= '}' ) | ';' )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==100) ) {
                alt10=1;
            }
            else if ( (LA10_0==96) ) {
                alt10=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // ModelFlowParserRules.g:79:55: (ob= '{' ( guard )? ( propertyDeclaration )* cb= '}' )
                    {
                    // ModelFlowParserRules.g:79:55: (ob= '{' ( guard )? ( propertyDeclaration )* cb= '}' )
                    // ModelFlowParserRules.g:79:56: ob= '{' ( guard )? ( propertyDeclaration )* cb= '}'
                    {
                    ob=(Token)match(input,100,FOLLOW_100_in_taskDeclaration192); if (state.failed) return retval;
                    // ModelFlowParserRules.g:79:64: ( guard )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0==173) ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // ModelFlowParserRules.g:0:0: guard
                            {
                            pushFollow(FOLLOW_guard_in_taskDeclaration195);
                            guard16=gModelFlow.guard();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, guard16.getTree());

                            }
                            break;

                    }

                    // ModelFlowParserRules.g:79:71: ( propertyDeclaration )*
                    loop9:
                    do {
                        int alt9=2;
                        int LA9_0 = input.LA(1);

                        if ( (LA9_0==NAME) ) {
                            alt9=1;
                        }


                        switch (alt9) {
                    	case 1 :
                    	    // ModelFlowParserRules.g:0:0: propertyDeclaration
                    	    {
                    	    pushFollow(FOLLOW_propertyDeclaration_in_taskDeclaration198);
                    	    propertyDeclaration17=propertyDeclaration();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, propertyDeclaration17.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop9;
                        }
                    } while (true);

                    cb=(Token)match(input,101,FOLLOW_101_in_taskDeclaration203); if (state.failed) return retval;

                    }


                    }
                    break;
                case 2 :
                    // ModelFlowParserRules.g:79:103: ';'
                    {
                    char_literal18=(Token)match(input,96,FOLLOW_96_in_taskDeclaration209); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal18_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(char_literal18);
                    adaptor.addChild(root_0, char_literal18_tree);
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
    // ModelFlowParserRules.g:83:1: ruleType : ct= 'is' head= NAME ( ':' ty= NAME )? ;
    public final ModelFlow_ModelFlowParserRules.ruleType_return ruleType() throws RecognitionException {
        ModelFlow_ModelFlowParserRules.ruleType_return retval = new ModelFlow_ModelFlowParserRules.ruleType_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token ct=null;
        Token head=null;
        Token ty=null;
        Token char_literal19=null;

        org.eclipse.epsilon.common.parse.AST ct_tree=null;
        org.eclipse.epsilon.common.parse.AST head_tree=null;
        org.eclipse.epsilon.common.parse.AST ty_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal19_tree=null;

        try {
            // ModelFlowParserRules.g:87:2: (ct= 'is' head= NAME ( ':' ty= NAME )? )
            // ModelFlowParserRules.g:88:2: ct= 'is' head= NAME ( ':' ty= NAME )?
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            ct=(Token)match(input,176,FOLLOW_176_in_ruleType235); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            ct_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(ct);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(ct_tree, root_0);
            }
            head=(Token)match(input,NAME,FOLLOW_NAME_in_ruleType240); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            head_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(head);
            adaptor.addChild(root_0, head_tree);
            }
            // ModelFlowParserRules.g:88:21: ( ':' ty= NAME )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==107) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // ModelFlowParserRules.g:88:22: ':' ty= NAME
                    {
                    char_literal19=(Token)match(input,107,FOLLOW_107_in_ruleType243); if (state.failed) return retval;
                    ty=(Token)match(input,NAME,FOLLOW_NAME_in_ruleType248); if (state.failed) return retval;
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
    // ModelFlowParserRules.g:97:1: propertyDeclaration : p= NAME expressionOrStatementBlock ;
    public final ModelFlow_ModelFlowParserRules.propertyDeclaration_return propertyDeclaration() throws RecognitionException {
        ModelFlow_ModelFlowParserRules.propertyDeclaration_return retval = new ModelFlow_ModelFlowParserRules.propertyDeclaration_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token p=null;
        ModelFlow_EolParserRules.expressionOrStatementBlock_return expressionOrStatementBlock20 = null;


        org.eclipse.epsilon.common.parse.AST p_tree=null;

        try {
            // ModelFlowParserRules.g:98:2: (p= NAME expressionOrStatementBlock )
            // ModelFlowParserRules.g:99:2: p= NAME expressionOrStatementBlock
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            p=(Token)match(input,NAME,FOLLOW_NAME_in_propertyDeclaration272); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            p_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(p);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(p_tree, root_0);
            }
            pushFollow(FOLLOW_expressionOrStatementBlock_in_propertyDeclaration275);
            expressionOrStatementBlock20=gModelFlow.expressionOrStatementBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionOrStatementBlock20.getTree());
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
    // ModelFlowParserRules.g:103:1: in : i= 'in' taskResourceList ;
    public final ModelFlow_ModelFlowParserRules.in_return in() throws RecognitionException {
        ModelFlow_ModelFlowParserRules.in_return retval = new ModelFlow_ModelFlowParserRules.in_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token i=null;
        ModelFlow_ModelFlowParserRules.taskResourceList_return taskResourceList21 = null;


        org.eclipse.epsilon.common.parse.AST i_tree=null;

        try {
            // ModelFlowParserRules.g:107:2: (i= 'in' taskResourceList )
            // ModelFlowParserRules.g:108:2: i= 'in' taskResourceList
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            i=(Token)match(input,132,FOLLOW_132_in_in299); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            i_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(i);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(i_tree, root_0);
            }
            pushFollow(FOLLOW_taskResourceList_in_in302);
            taskResourceList21=taskResourceList();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, taskResourceList21.getTree());
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
    // ModelFlowParserRules.g:112:1: inout : i= 'inout' taskResourceList ;
    public final ModelFlow_ModelFlowParserRules.inout_return inout() throws RecognitionException {
        ModelFlow_ModelFlowParserRules.inout_return retval = new ModelFlow_ModelFlowParserRules.inout_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token i=null;
        ModelFlow_ModelFlowParserRules.taskResourceList_return taskResourceList22 = null;


        org.eclipse.epsilon.common.parse.AST i_tree=null;

        try {
            // ModelFlowParserRules.g:116:2: (i= 'inout' taskResourceList )
            // ModelFlowParserRules.g:117:2: i= 'inout' taskResourceList
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            i=(Token)match(input,177,FOLLOW_177_in_inout327); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            i_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(i);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(i_tree, root_0);
            }
            pushFollow(FOLLOW_taskResourceList_in_inout330);
            taskResourceList22=taskResourceList();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, taskResourceList22.getTree());
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
    // ModelFlowParserRules.g:121:1: out : i= 'out' taskResourceList ;
    public final ModelFlow_ModelFlowParserRules.out_return out() throws RecognitionException {
        ModelFlow_ModelFlowParserRules.out_return retval = new ModelFlow_ModelFlowParserRules.out_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token i=null;
        ModelFlow_ModelFlowParserRules.taskResourceList_return taskResourceList23 = null;


        org.eclipse.epsilon.common.parse.AST i_tree=null;

        try {
            // ModelFlowParserRules.g:125:2: (i= 'out' taskResourceList )
            // ModelFlowParserRules.g:126:2: i= 'out' taskResourceList
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            i=(Token)match(input,178,FOLLOW_178_in_out355); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            i_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(i);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(i_tree, root_0);
            }
            pushFollow(FOLLOW_taskResourceList_in_out358);
            taskResourceList23=taskResourceList();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, taskResourceList23.getTree());
            if ( state.backtracking==0 ) {
              i.setType(OUTPUTS);
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
    // $ANTLR end out

    public static class taskResourceList_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start taskResourceList
    // ModelFlowParserRules.g:130:1: taskResourceList : taskResource ( 'and' taskResource )* -> ^( RESLIST ( taskResource )* ) ;
    public final ModelFlow_ModelFlowParserRules.taskResourceList_return taskResourceList() throws RecognitionException {
        ModelFlow_ModelFlowParserRules.taskResourceList_return retval = new ModelFlow_ModelFlowParserRules.taskResourceList_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token string_literal25=null;
        ModelFlow_ModelFlowParserRules.taskResource_return taskResource24 = null;

        ModelFlow_ModelFlowParserRules.taskResource_return taskResource26 = null;


        org.eclipse.epsilon.common.parse.AST string_literal25_tree=null;
        RewriteRuleTokenStream stream_149=new RewriteRuleTokenStream(adaptor,"token 149");
        RewriteRuleSubtreeStream stream_taskResource=new RewriteRuleSubtreeStream(adaptor,"rule taskResource");
        try {
            // ModelFlowParserRules.g:134:2: ( taskResource ( 'and' taskResource )* -> ^( RESLIST ( taskResource )* ) )
            // ModelFlowParserRules.g:135:2: taskResource ( 'and' taskResource )*
            {
            pushFollow(FOLLOW_taskResource_in_taskResourceList383);
            taskResource24=taskResource();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_taskResource.add(taskResource24.getTree());
            // ModelFlowParserRules.g:135:15: ( 'and' taskResource )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==149) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // ModelFlowParserRules.g:135:16: 'and' taskResource
            	    {
            	    string_literal25=(Token)match(input,149,FOLLOW_149_in_taskResourceList386); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_149.add(string_literal25);

            	    pushFollow(FOLLOW_taskResource_in_taskResourceList388);
            	    taskResource26=taskResource();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_taskResource.add(taskResource26.getTree());

            	    }
            	    break;

            	default :
            	    break loop12;
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
            // 136:2: -> ^( RESLIST ( taskResource )* )
            {
                // ModelFlowParserRules.g:136:5: ^( RESLIST ( taskResource )* )
                {
                org.eclipse.epsilon.common.parse.AST root_1 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();
                root_1 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot((org.eclipse.epsilon.common.parse.AST)adaptor.create(RESLIST, "RESLIST"), root_1);

                // ModelFlowParserRules.g:136:15: ( taskResource )*
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
    // ModelFlowParserRules.g:139:1: taskResource : res= NAME ( '.' ind= NAME )? (a= 'as' NAME ( ',' NAME )* )? ;
    public final ModelFlow_ModelFlowParserRules.taskResource_return taskResource() throws RecognitionException {
        ModelFlow_ModelFlowParserRules.taskResource_return retval = new ModelFlow_ModelFlowParserRules.taskResource_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token res=null;
        Token ind=null;
        Token a=null;
        Token char_literal27=null;
        Token NAME28=null;
        Token char_literal29=null;
        Token NAME30=null;

        org.eclipse.epsilon.common.parse.AST res_tree=null;
        org.eclipse.epsilon.common.parse.AST ind_tree=null;
        org.eclipse.epsilon.common.parse.AST a_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal27_tree=null;
        org.eclipse.epsilon.common.parse.AST NAME28_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal29_tree=null;
        org.eclipse.epsilon.common.parse.AST NAME30_tree=null;

        try {
            // ModelFlowParserRules.g:143:2: (res= NAME ( '.' ind= NAME )? (a= 'as' NAME ( ',' NAME )* )? )
            // ModelFlowParserRules.g:144:2: res= NAME ( '.' ind= NAME )? (a= 'as' NAME ( ',' NAME )* )?
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            res=(Token)match(input,NAME,FOLLOW_NAME_in_taskResource422); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            res_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(res);
            adaptor.addChild(root_0, res_tree);
            }
            // ModelFlowParserRules.g:144:11: ( '.' ind= NAME )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==POINT) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // ModelFlowParserRules.g:144:12: '.' ind= NAME
                    {
                    char_literal27=(Token)match(input,POINT,FOLLOW_POINT_in_taskResource425); if (state.failed) return retval;
                    ind=(Token)match(input,NAME,FOLLOW_NAME_in_taskResource430); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ind_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(ind);
                    adaptor.addChild(root_0, ind_tree);
                    }

                    }
                    break;

            }

            // ModelFlowParserRules.g:144:28: (a= 'as' NAME ( ',' NAME )* )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==179) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // ModelFlowParserRules.g:144:29: a= 'as' NAME ( ',' NAME )*
                    {
                    a=(Token)match(input,179,FOLLOW_179_in_taskResource437); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    a_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(a);
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(a_tree, root_0);
                    }
                    NAME28=(Token)match(input,NAME,FOLLOW_NAME_in_taskResource440); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NAME28_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(NAME28);
                    adaptor.addChild(root_0, NAME28_tree);
                    }
                    // ModelFlowParserRules.g:144:42: ( ',' NAME )*
                    loop14:
                    do {
                        int alt14=2;
                        int LA14_0 = input.LA(1);

                        if ( (LA14_0==98) ) {
                            alt14=1;
                        }


                        switch (alt14) {
                    	case 1 :
                    	    // ModelFlowParserRules.g:144:43: ',' NAME
                    	    {
                    	    char_literal29=(Token)match(input,98,FOLLOW_98_in_taskResource443); if (state.failed) return retval;
                    	    NAME30=(Token)match(input,NAME,FOLLOW_NAME_in_taskResource446); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    NAME30_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(NAME30);
                    	    adaptor.addChild(root_0, NAME30_tree);
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop14;
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
    // ModelFlowParserRules.g:154:1: dependsOn : t= 'dependsOn' NAME ( ',' NAME )* ;
    public final ModelFlow_ModelFlowParserRules.dependsOn_return dependsOn() throws RecognitionException {
        ModelFlow_ModelFlowParserRules.dependsOn_return retval = new ModelFlow_ModelFlowParserRules.dependsOn_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token t=null;
        Token NAME31=null;
        Token char_literal32=null;
        Token NAME33=null;

        org.eclipse.epsilon.common.parse.AST t_tree=null;
        org.eclipse.epsilon.common.parse.AST NAME31_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal32_tree=null;
        org.eclipse.epsilon.common.parse.AST NAME33_tree=null;

        try {
            // ModelFlowParserRules.g:158:2: (t= 'dependsOn' NAME ( ',' NAME )* )
            // ModelFlowParserRules.g:159:2: t= 'dependsOn' NAME ( ',' NAME )*
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            t=(Token)match(input,180,FOLLOW_180_in_dependsOn477); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            t_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(t);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(t_tree, root_0);
            }
            NAME31=(Token)match(input,NAME,FOLLOW_NAME_in_dependsOn480); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            NAME31_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(NAME31);
            adaptor.addChild(root_0, NAME31_tree);
            }
            // ModelFlowParserRules.g:159:22: ( ',' NAME )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==98) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // ModelFlowParserRules.g:159:23: ',' NAME
            	    {
            	    char_literal32=(Token)match(input,98,FOLLOW_98_in_dependsOn483); if (state.failed) return retval;
            	    NAME33=(Token)match(input,NAME,FOLLOW_NAME_in_dependsOn486); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    NAME33_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(NAME33);
            	    adaptor.addChild(root_0, NAME33_tree);
            	    }

            	    }
            	    break;

            	default :
            	    break loop16;
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
    // ModelFlowParserRules.g:164:1: paramDeclarationExpression : p= 'param' NAME ( ':' t= typeName )? ';' ;
    public final ModelFlow_ModelFlowParserRules.paramDeclarationExpression_return paramDeclarationExpression() throws RecognitionException {
        ModelFlow_ModelFlowParserRules.paramDeclarationExpression_return retval = new ModelFlow_ModelFlowParserRules.paramDeclarationExpression_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token p=null;
        Token NAME34=null;
        Token char_literal35=null;
        Token char_literal36=null;
        ModelFlow_EolParserRules.typeName_return t = null;


        org.eclipse.epsilon.common.parse.AST p_tree=null;
        org.eclipse.epsilon.common.parse.AST NAME34_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal35_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal36_tree=null;

        try {
            // ModelFlowParserRules.g:170:2: (p= 'param' NAME ( ':' t= typeName )? ';' )
            // ModelFlowParserRules.g:171:2: p= 'param' NAME ( ':' t= typeName )? ';'
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            p=(Token)match(input,181,FOLLOW_181_in_paramDeclarationExpression513); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            p_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(p);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(p_tree, root_0);
            }
            NAME34=(Token)match(input,NAME,FOLLOW_NAME_in_paramDeclarationExpression516); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            NAME34_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(NAME34);
            adaptor.addChild(root_0, NAME34_tree);
            }
            // ModelFlowParserRules.g:171:18: ( ':' t= typeName )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==107) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // ModelFlowParserRules.g:171:19: ':' t= typeName
                    {
                    char_literal35=(Token)match(input,107,FOLLOW_107_in_paramDeclarationExpression519); if (state.failed) return retval;
                    pushFollow(FOLLOW_typeName_in_paramDeclarationExpression524);
                    t=gModelFlow.typeName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, t.getTree());

                    }
                    break;

            }

            char_literal36=(Token)match(input,96,FOLLOW_96_in_paramDeclarationExpression528); if (state.failed) return retval;

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


 

    public static final BitSet FOLLOW_paramDeclarationExpression_in_workflowContents79 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_resourceDeclaration_in_workflowContents83 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_taskDeclaration_in_workflowContents87 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_annotationBlock_in_workflowContents91 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_operationDeclaration_in_workflowContents95 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_95_in_resourceDeclaration117 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_NAME_in_resourceDeclaration120 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_ruleType_in_resourceDeclaration122 = new BitSet(new long[]{0x0000000000000000L,0x0000001100000000L});
    public static final BitSet FOLLOW_100_in_resourceDeclaration128 = new BitSet(new long[]{0x0000000000080000L,0x0000002000000000L});
    public static final BitSet FOLLOW_propertyDeclaration_in_resourceDeclaration131 = new BitSet(new long[]{0x0000000000080000L,0x0000002000000000L});
    public static final BitSet FOLLOW_101_in_resourceDeclaration136 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_96_in_resourceDeclaration142 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_175_in_taskDeclaration169 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_NAME_in_taskDeclaration172 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_ruleType_in_taskDeclaration174 = new BitSet(new long[]{0x0000000000000000L,0x0000001100000000L,0x0016000000000010L});
    public static final BitSet FOLLOW_in_in_taskDeclaration176 = new BitSet(new long[]{0x0000000000000000L,0x0000001100000000L,0x0016000000000000L});
    public static final BitSet FOLLOW_inout_in_taskDeclaration179 = new BitSet(new long[]{0x0000000000000000L,0x0000001100000000L,0x0014000000000000L});
    public static final BitSet FOLLOW_out_in_taskDeclaration182 = new BitSet(new long[]{0x0000000000000000L,0x0000001100000000L,0x0010000000000000L});
    public static final BitSet FOLLOW_dependsOn_in_taskDeclaration185 = new BitSet(new long[]{0x0000000000000000L,0x0000001100000000L});
    public static final BitSet FOLLOW_100_in_taskDeclaration192 = new BitSet(new long[]{0x0000000000080000L,0x0000002000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_guard_in_taskDeclaration195 = new BitSet(new long[]{0x0000000000080000L,0x0000002000000000L});
    public static final BitSet FOLLOW_propertyDeclaration_in_taskDeclaration198 = new BitSet(new long[]{0x0000000000080000L,0x0000002000000000L});
    public static final BitSet FOLLOW_101_in_taskDeclaration203 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_96_in_taskDeclaration209 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_176_in_ruleType235 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_NAME_in_ruleType240 = new BitSet(new long[]{0x0000000000000002L,0x0000080000000000L});
    public static final BitSet FOLLOW_107_in_ruleType243 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_NAME_in_ruleType248 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NAME_in_propertyDeclaration272 = new BitSet(new long[]{0x0000000000000000L,0x0000081000000000L});
    public static final BitSet FOLLOW_expressionOrStatementBlock_in_propertyDeclaration275 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_132_in_in299 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_taskResourceList_in_in302 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_177_in_inout327 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_taskResourceList_in_inout330 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_178_in_out355 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_taskResourceList_in_out358 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_taskResource_in_taskResourceList383 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_149_in_taskResourceList386 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_taskResource_in_taskResourceList388 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_NAME_in_taskResource422 = new BitSet(new long[]{0x0000000000000202L,0x0000000000000000L,0x0008000000000000L});
    public static final BitSet FOLLOW_POINT_in_taskResource425 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_NAME_in_taskResource430 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0008000000000000L});
    public static final BitSet FOLLOW_179_in_taskResource437 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_NAME_in_taskResource440 = new BitSet(new long[]{0x0000000000000002L,0x0000000400000000L});
    public static final BitSet FOLLOW_98_in_taskResource443 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_NAME_in_taskResource446 = new BitSet(new long[]{0x0000000000000002L,0x0000000400000000L});
    public static final BitSet FOLLOW_180_in_dependsOn477 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_NAME_in_dependsOn480 = new BitSet(new long[]{0x0000000000000002L,0x0000000400000000L});
    public static final BitSet FOLLOW_98_in_dependsOn483 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_NAME_in_dependsOn486 = new BitSet(new long[]{0x0000000000000002L,0x0000000400000000L});
    public static final BitSet FOLLOW_181_in_paramDeclarationExpression513 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_NAME_in_paramDeclarationExpression516 = new BitSet(new long[]{0x0000000000000000L,0x0000080100000000L});
    public static final BitSet FOLLOW_107_in_paramDeclarationExpression519 = new BitSet(new long[]{0x0000000000080000L,0x0FFE000000000000L});
    public static final BitSet FOLLOW_typeName_in_paramDeclarationExpression524 = new BitSet(new long[]{0x0000000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_96_in_paramDeclarationExpression528 = new BitSet(new long[]{0x0000000000000002L});

}
