package org.epsilonlabs.modelflow.mmc.emf.task.hasher;

import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.codegen.ecore.CodeGenEcorePlugin;
import org.eclipse.emf.codegen.jet.JETCompiler;
import org.eclipse.emf.codegen.merge.java.JControlModel;
import org.eclipse.emf.codegen.merge.java.JMerger;
import org.eclipse.emf.codegen.merge.java.facade.FacadeHelper;
import org.eclipse.emf.codegen.merge.java.facade.ast.ASTFacadeHelper;
import org.eclipse.emf.codegen.util.CodeGenUtil;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.formatter.DefaultCodeFormatterConstants;

public class CodegenOutputUtil {
	public static final String JDK = "jdk";
	public static final String OPTIONS = "options";
	public static final String FILES = "files";
	public static final String DYNAMIC = "dynamic";
	public static final String TEMPLATE_DIR = "tempDir";
	public static final String FORMATTING = "codeFormatting";

	protected Map<String, String> options;
	protected String jdkComplianceLevel;
	protected Set<String> files;
	protected String templateDir;
	protected Boolean dynamic = false;
	protected Boolean codeFormatting = false;
	protected JControlModel jControlModel;

	public CodegenOutputUtil(Map<String, String> options, String jdk, Set<String> files, String templateDir,
			Boolean dynamic, Boolean codeFormatting) {
		this.options = options;
		this.jdkComplianceLevel = jdk;
		this.files = files;
		this.templateDir = templateDir;
		if (dynamic != null) {
			this.dynamic = dynamic;
		}
		if (codeFormatting != null) {
			this.codeFormatting = codeFormatting;
		}
	}

	public Set<String> getFiles() {
		return files;
	}

	public Boolean isCodeFormatting() {
		return codeFormatting;
	}

	public String getJDK() {
		return jdkComplianceLevel;
	}

	public Boolean isDynamic() {
		return dynamic;
	}

	public String getTemplateDir() {
		return templateDir;
	}

	public Map<String, String> getOptions() {
		return options;
	}

	/** From GenModelGeneratorAdapterFactory */
	protected String[] getDefaultTemplatePath() {
		String[] result = null;
		String staticLocation = CodeGenEcorePlugin.INSTANCE.getBaseURL().toString() + "templates";

		if (dynamic && templateDir != null) {
			result = new String[2];
			result[0] = templateDir.indexOf(':') == -1 ? URI.createPlatformResourceURI(templateDir, true).toString()
					: templateDir;
			result[1] = staticLocation;
		} else {
			result = new String[1];
			result[0] = staticLocation;
		}
		return result;
	}

	public void reset() {
		jControlModel.getFacadeHelper().reset();
	}

	public static final boolean IS_ECLIPSE_RUNNING;
	static {
		boolean result = false;
		try {
			result = Platform.isRunning();
		} catch (Throwable exception) {
			// Assume that we aren't running.
		}
		IS_ECLIPSE_RUNNING = result;
	}

	public JMerger getMerger() {
		jControlModel = new JControlModel();
		FacadeHelper facadeHelper = CodeGenUtil.instantiateFacadeHelper(ASTFacadeHelper.class.getName());
		facadeHelper.setCompilerCompliance(jdkComplianceLevel);
		if (codeFormatting) {
			jControlModel.setLeadingTabReplacement(null);
			jControlModel.setConvertToStandardBraceStyle(false);
		} else {
			String tabSize = options.get(DefaultCodeFormatterConstants.FORMATTER_TAB_SIZE);
			String braceStyle = options
					.get(DefaultCodeFormatterConstants.FORMATTER_BRACE_POSITION_FOR_TYPE_DECLARATION);
			String tabCharacter = options.get(DefaultCodeFormatterConstants.FORMATTER_TAB_CHAR);
			if (JavaCore.TAB.equals(tabCharacter)) {
				jControlModel.setLeadingTabReplacement("\t");
			} else {
				String spaces = "";
				for (int i = Integer.parseInt(tabSize); i > 0; --i) {
					spaces += " ";
				}
				jControlModel.setLeadingTabReplacement(spaces);
			}
			jControlModel.setConvertToStandardBraceStyle(DefaultCodeFormatterConstants.END_OF_LINE.equals(braceStyle));
		}
		String location = JETCompiler.find(getDefaultTemplatePath(), "emf-merge.xml");
		jControlModel.initialize(facadeHelper, location);
		return new JMerger(jControlModel);
	}
}