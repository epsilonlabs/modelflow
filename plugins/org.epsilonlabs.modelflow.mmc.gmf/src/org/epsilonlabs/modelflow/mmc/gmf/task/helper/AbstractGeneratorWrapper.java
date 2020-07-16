package org.epsilonlabs.modelflow.mmc.gmf.task.helper;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.codegen.util.CodeGenUtil;
import org.eclipse.emf.codegen.util.CodeGenUtil.EclipseUtil;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticException;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.common.UnexpectedBehaviourException;
import org.eclipse.gmf.common.codegen.ImportAssistant;
import org.eclipse.gmf.internal.common.codegen.BinaryEmitter;
import org.eclipse.gmf.internal.common.codegen.Conversions;
import org.eclipse.gmf.internal.common.codegen.ImportUtil;
import org.eclipse.gmf.internal.common.codegen.JavaClassEmitter;
import org.eclipse.gmf.internal.common.codegen.Messages;
import org.eclipse.gmf.internal.common.codegen.OrganizeImportsPostprocessor;
import org.eclipse.gmf.internal.common.codegen.TextEmitter;
import org.eclipse.gmf.internal.common.codegen.TextMerger;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IImportDeclaration;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.TextEdit;

@SuppressWarnings({ "restriction", "deprecation" })
public abstract class AbstractGeneratorWrapper implements Runnable {

	protected CodeFormatter myCodeFormatter;
	protected OrganizeImportsPostprocessor myImportsPostprocessor;
	protected final List<IStatus> myExceptions;
	protected IStatus myRunStatus = Status.CANCEL_STATUS;
	protected TextMerger myMerger;
	protected final static boolean isToRestoreExistingImports = true;
	protected IProgressMonitor myProgress = new NullProgressMonitor();

	protected IPackageFragmentRoot myDestRoot;
	protected IProject myDestProject;

	protected abstract void customRun() throws InterruptedException, UnexpectedBehaviourException;

	protected abstract void setupProgressMonitor();


	
	public AbstractGeneratorWrapper() {
		myExceptions = new LinkedList<>();
	}

	public void run(IProgressMonitor progress) throws InterruptedException {
		setProgressMonitor(progress);
		clearExceptionsList();
		doRun();
	}

	@Override
	public void run() {
		clearExceptionsList();
		try {
			doRun();
		} catch (InterruptedException ex) {
			myRunStatus = new Status(IStatus.CANCEL,getClass().getName(), 0, Messages.interrupted, ex);
		}
	}

	/**
	 * Provides information about success/failures during {@link #run()}
	 * 
	 * @return state of the generator run, or CANCEL if generator was not yet run.
	 */
	public IStatus getRunStatus() {
		return myRunStatus;
	}

	/**
	 * Optionally, specify progressMonitor to use. Should be called prior to
	 * {@link #run()}
	 * 
	 * @param progress
	 */
	public void setProgressMonitor(IProgressMonitor progress) {
		myProgress = progress;
	}

	protected final void handleException(CoreException ex) {
		handleException(ex.getStatus());
	}

	protected final void handleException(IStatus status) {
		myExceptions.add(status);
	}

	protected final void handleException(Throwable ex) {
		if (ex instanceof DiagnosticException) {
			// unwind invocation target exceptions as much as possible
			final Diagnostic diagnostic = ((DiagnosticException) ex).getDiagnostic();
			if (diagnostic.getException() instanceof InvocationTargetException) {
				Throwable originalEx = ((InvocationTargetException) diagnostic.getException()).getCause();
				handleException(newStatus(originalEx));
			} else {
				handleException(BasicDiagnostic.toIStatus(diagnostic));
			}
		} else {
			handleException(newStatus(ex));
		}
	}

	/**
	 * by default, process as ordinary exception
	 */
	protected void handleUnexpected(UnexpectedBehaviourException ex) {
		handleException(ex);
	}

	protected static IStatus newStatus(Throwable ex) {
		return newStatus(IStatus.ERROR, ex);
	}

	protected static IStatus newStatus(int severity, Throwable ex) {
		final String msg = ex.getMessage() == null ? ex.getClass().getName() : ex.getMessage();
		return new Status(severity, AbstractGeneratorWrapper.class.getCanonicalName(), 0, Messages.bind(Messages.exception, msg), ex);
	}

	protected final IProject getDestProject() {
		return myDestProject;
	}

	protected final IProgressMonitor getProgress() {
		return myProgress;
	}

	/**
	 * @param task  optional string to be shown in the progress dialog
	 * @param total estimation of number of activities to happen
	 */
	protected final void setupProgressMonitor(String task, int total) {
		if (myProgress == null) {
			myProgress = new NullProgressMonitor();
			return;
			// no need to set it up
		}
		myProgress.beginTask(task == null ? Messages.start : task, total);
	}

	protected final IProgressMonitor getNextStepMonitor() throws InterruptedException {
		if (myProgress.isCanceled()) {
			throw new InterruptedException();
		}
		return new SubProgressMonitor(myProgress, 1);
	}

	/**
	 * @see #initializeEditorProject(String, IPath, List)
	 */
	protected final void initializeEditorProject(String pluginId, IPath projectLocation)
			throws UnexpectedBehaviourException, InterruptedException {
		initializeEditorProject(pluginId, projectLocation, Collections.<IProject>emptyList());
	}

	/**
	 * Delegates to {@link #initializeEditorProject(IPath, IPath, List)}, using
	 * plug-in id as workspace project name and 'src' as Java sources location.
	 * 
	 * @param pluginId           both name of workspace project and plug-in id
	 * @param projectLocation    {@link IPath} to folder where <code>.project</code>
	 *                           file would reside. Use <code>null</code> to use
	 *                           default workspace location.
	 * @param referencedProjects collection of {@link IProject}
	 * 
	 */
	protected final void initializeEditorProject(String pluginId, IPath projectLocation,
			List<IProject> referencedProjects) throws UnexpectedBehaviourException, InterruptedException {
		// not sure if there's any reason to get project's name via IProject (not use
		// pluginId directly), this is just how it was done from 1.1.
		IProject p = ResourcesPlugin.getWorkspace().getRoot().getProject(pluginId);
		initializeEditorProject(new Path('/' + p.getName() + "/src"), projectLocation, referencedProjects);
	}

	/**
	 * @param javaSource         workspace absolute path to java source folder of
	 *                           the generated project, e.g.
	 *                           '/org.sample.aaa/sources'.
	 * @param projectLocation    {@link IPath} to folder where <code>.project</code>
	 *                           file would reside. Use <code>null</code> to use
	 *                           default workspace location.
	 * @param referencedProjects collection of {@link IProject}
	 * @throws UnexpectedBehaviourException something goes really wrong
	 * @throws InterruptedException         user canceled operation
	 */
	protected final void initializeEditorProject(IPath javaSource, IPath projectLocation,
			List<IProject> referencedProjects) throws UnexpectedBehaviourException, InterruptedException {
		myDestProject = ResourcesPlugin.getWorkspace().getRoot().getProject(javaSource.segment(0));
		final int style = org.eclipse.emf.codegen.ecore.Generator.EMF_PLUGIN_PROJECT_STYLE;
		// pluginVariables is NOT used when style is EMF_PLUGIN_PROJECT_STYLE
		final List<?> pluginVariables = null;
		final IProgressMonitor pm = getNextStepMonitor();
		setProgressTaskName(Messages.initproject);

		org.eclipse.emf.codegen.ecore.Generator.createEMFProject(javaSource, projectLocation, referencedProjects, pm,
				style, pluginVariables);

		try {
			final IJavaProject jp = JavaCore.create(myDestProject);
			myDestRoot = jp.findPackageFragmentRoot(javaSource);
			// createEMFProject doesn't create source entry in case project exists and has
			// some classpath entries already,
			// though the folder gets created.
			if (myDestRoot == null) {
				IClasspathEntry[] oldCP = jp.getRawClasspath();
				IClasspathEntry[] newCP = new IClasspathEntry[oldCP.length + 1];
				System.arraycopy(oldCP, 0, newCP, 0, oldCP.length);
				newCP[oldCP.length] = JavaCore.newSourceEntry(javaSource);
				jp.setRawClasspath(newCP, new NullProgressMonitor());
				myDestRoot = jp.findPackageFragmentRoot(javaSource);
			}
		} catch (JavaModelException ex) {
			throw new UnexpectedBehaviourException(ex.getMessage());
		}
		if (myDestRoot == null) {
			throw new UnexpectedBehaviourException("no source root can be found");
		}
	}

	/**
	 * Generate ordinary file.
	 * 
	 * @param emitter  template to use
	 * @param filePath - project-relative path to file, e.g. META-INF/MANIFEST.MF
	 * @param param    TODO
	 * @throws InterruptedException
	 */
	protected final void doGenerateFile(TextEmitter emitter, IPath filePath, Object... param)
			throws InterruptedException {
		assert !myDestProject.getName().equals(filePath.segment(0));
		IProgressMonitor pm = getNextStepMonitor();
		try {
			setProgressTaskName(filePath.lastSegment());
			pm.beginTask(null, 5);
			IPath containerPath = myDestProject.getFullPath().append(filePath.removeLastSegments(1));
			EclipseUtil.findOrCreateContainer(containerPath, false, (IPath) null, new SubProgressMonitor(pm, 1));
			String genText = emitter.generate(new SubProgressMonitor(pm, 1), param);
			IFile f = myDestProject.getFile(filePath);
			newFile(f);
			final boolean propertyFile = "properties".equals(filePath.getFileExtension());
			String charset = propertyFile ? "ISO-8859-1" : "UTF-8";
			if (propertyFile) {
				genText = Conversions.escapeUnicode(genText);
			}
			String oldText = null;
			if (f.exists()) {
				oldText = getFileContents(f); 
			}
			if (oldText != null) {
				genText = mergePlainText(oldText, genText, f, new SubProgressMonitor(pm, 1));
				if (!oldText.equals(genText)) {
					f.setContents(new ByteArrayInputStream(genText.getBytes(charset)), true, true,
							new SubProgressMonitor(pm, 1));
				} else {
					pm.worked(1);
				}
			} else {
				f.create(new ByteArrayInputStream(genText.getBytes(charset)), true, new SubProgressMonitor(pm, 2));
			}
		} catch (InvocationTargetException ex) {
			handleException(ex.getCause());
		} catch (UnexpectedBehaviourException ex) {
			handleUnexpected(ex);
		} catch (CoreException ex) {
			handleException(ex);
		} catch (UnsupportedEncodingException ex) {
			handleException(ex);
		} finally {
			pm.done();
		}
	}
	
	protected String rule;
	protected Set<File> files = new HashSet<File>();
	
	protected void newFile(IFile f){
		newFile(f.getFullPath());
	}
	protected void newFile(IPath f){
		this.files.add(f.toFile());
	}
	public Set<File> getFiles() {
		return files;
	}

	/**
	 * Inspired by GenBaseImpl.EclipseUtil.findOrCreateContainer Although later
	 * (with EMF API adopting Platform changes) we might need to return URI here
	 * 
	 * @return path suitable for IProjectDescription, or <code>null</code> to
	 *         indicate use of default
	 */
	protected final IPath guessNewProjectLocation(Path examplaryProjectPath, String newProjectName) {
		assert newProjectName != null;
		try {
			if (ResourcesPlugin.getWorkspace().getRoot().getProject(newProjectName).exists()) {
				// just use whatever already specified.
				// Returned value doesn't make sense in this case -
				// oee.codegen.ecore.Generator#EclipseHelper#createEMFProject doesn't use it
				// then.
				return null;
			}
			if (examplaryProjectPath == null || !examplaryProjectPath.isAbsolute()) {
				return null;
			}
			IProject p = ResourcesPlugin.getWorkspace().getRoot().getProject(examplaryProjectPath.segment(0));
			if (!p.exists()) {
				return null;
			}
			java.net.URI locationURI = p.getDescription().getLocationURI();
			// org.eclipse.core.internal.utils.FileUtil#toPath
			if (locationURI == null) {
				return null;
			}
			if (locationURI.getScheme() != null && !"file".equals(locationURI.getScheme())) {
				return null;
			}
			return new Path(locationURI.getSchemeSpecificPart()).removeLastSegments(1).append(newProjectName);
		} catch (CoreException ex) {
			handleException(newStatus(IStatus.WARNING, ex));
			return null;
		}
	}

	protected final ImportAssistant createImportAssistant(String packageName, String className) {
		return new ImportUtil(packageName, className, myDestRoot);
	}

	protected final void doGenerate(JavaClassEmitter emitter, Object... input)
			throws InterruptedException, UnexpectedBehaviourException {
		if (emitter != null) {
			doGenerateJavaClass(emitter, emitter.getQualifiedClassName(input), input);
		}
	}

	protected final void doGenerateJavaClass(TextEmitter emitter, String qualifiedClassName, Object... input)
			throws InterruptedException {
		if (emitter != null) {
			doGenerateJavaClass(emitter, CodeGenUtil.getPackageName(qualifiedClassName),
					CodeGenUtil.getSimpleClassName(qualifiedClassName), input);
		}
	}

	/**
	 * NOTE: potential problem - packageName and className should match those
	 * specified in the template. Besides, getQualifiedXXX helpers in diagram
	 * GenModel should also correctly return qualified class names.
	 */
	protected final void doGenerateJavaClass(TextEmitter emitter, String packageName, String className, Object... input)
			throws InterruptedException {
		IProgressMonitor pm = getNextStepMonitor();
		setProgressTaskName(className);
		pm.beginTask(null, 7);
		if (emitter == null) {
			pm.done();
			return;
		}
		try {
			String genText = emitter.generate(new SubProgressMonitor(pm, 2), input);
			IPackageFragment pf = myDestRoot.createPackageFragment(packageName, true, new SubProgressMonitor(pm, 1));
			ICompilationUnit cu = pf.getCompilationUnit(className + ".java"); //$NON-NLS-1$
			newFile(cu.getPath());
			if (cu.exists()) {
				ICompilationUnit workingCopy = null;
				try {
					workingCopy = cu.getWorkingCopy(new SubProgressMonitor(pm, 1));
					final String oldContents = workingCopy.getSource();
					IImportDeclaration[] declaredImports = workingCopy.getImports();
					workingCopy.getBuffer().setContents(genText);
					workingCopy.reconcile(ICompilationUnit.NO_AST, false, null, null);
					try {
						// Since we do organizeImports prior to merge, we must ensure imports added
						// manually are known to OrganizeImportsProcessor
						String[] declaredImportsAsStrings = new String[declaredImports.length];
						for (int i = 0; i < declaredImports.length; i++) {
							declaredImportsAsStrings[i] = declaredImports[i].getElementName();
						}
						getImportsPostrocessor().organizeImports(workingCopy, declaredImportsAsStrings,
								new SubProgressMonitor(pm, 1));
					} catch (CoreException e) {
						workingCopy.commitWorkingCopy(true, new SubProgressMonitor(pm, 1)); // save to investigate
																							// contents
						throw e;
					}
					genText = mergeJavaCode(oldContents, workingCopy.getSource(), new SubProgressMonitor(pm, 1));
					genText = formatCode(genText);
					if (!genText.equals(oldContents)) {
						workingCopy.getBuffer().setContents(genText);
						workingCopy.reconcile(ICompilationUnit.NO_AST, false, null, null);
						workingCopy.commitWorkingCopy(true, new SubProgressMonitor(pm, 1));
					} else {
						// discard changes - would happen in finally, nothing else to do
						pm.worked(1);
					}
				} finally {
					workingCopy.discardWorkingCopy();
				}
			} else {
				cu = pf.createCompilationUnit(cu.getElementName(), genText, true, new SubProgressMonitor(pm, 1));
				getImportsPostrocessor().organizeImports(cu, null, new SubProgressMonitor(pm, 1));
				String newContents = formatCode(cu.getSource());
				cu.getBuffer().setContents(newContents);
				cu.save(new SubProgressMonitor(pm, 2), true);
			}
		} catch (NullPointerException ex) {
			handleException(ex);
		} catch (InvocationTargetException ex) {
			handleException(ex.getCause());
		} catch (UnexpectedBehaviourException ex) {
			handleUnexpected(ex);
		} catch (CoreException ex) {
			handleException(ex);
		} finally {
			pm.done();
		}
	}

	protected final void doGenerateBinaryFile(BinaryEmitter emitter, Path outputPath, Object[] params)
			throws InterruptedException, UnexpectedBehaviourException {
		IProgressMonitor pm = getNextStepMonitor();
		setProgressTaskName(outputPath.lastSegment());
		IFile f = getDestProject().getFile(outputPath);
		newFile(f);
		if (f.exists()) {
			// Follow EMF's policy and do not overwrite file if exists
			return;
		}
		try {
			pm.beginTask(null, 4);
			IPath containerPath = getDestProject().getFullPath().append(outputPath.removeLastSegments(1));
			EclipseUtil.findOrCreateContainer(containerPath, false, (IPath) null, new SubProgressMonitor(pm, 1));
			byte[] contents = emitter.generate(new SubProgressMonitor(pm, 1), params);
			f.create(new ByteArrayInputStream(contents), true, new SubProgressMonitor(pm, 1));
			f.getParent().refreshLocal(IResource.DEPTH_ONE, new SubProgressMonitor(pm, 1));
		} catch (InvocationTargetException ex) {
			handleException(ex.getCause());
		} catch (CoreException ex) {
			handleException(ex);
		} finally {
			pm.done();
		}
	}

	protected String mergeJavaCode(String oldContents, String generatedText, IProgressMonitor pm)
			throws JavaModelException {
		pm.beginTask(Messages.merge, 1);
		try {
			return getMergeService().mergeJava(oldContents, generatedText);
		} finally {
			pm.done();
		}
	}

	protected String mergePlainText(String oldText, String genText, IFile oldRes, IProgressMonitor pm) {
		pm.beginTask(Messages.merge, 1);
		try {
			return getMergeService().process(oldRes.getName(), oldText, genText);
		} finally {
			pm.done();
		}
	}

	protected TextMerger getMergeService() {
		if (myMerger == null) {
			myMerger = createMergeService();
			assert myMerger != null;
		}
		return myMerger;
	}

	/**
	 * By default, provides facility that doesn't perform any merge at all.
	 * 
	 * @return facility to perform merges, should never return null.
	 */
	protected TextMerger createMergeService() {
		return new TextMerger();
	}

	protected void setProgressTaskName(String text) {
		myProgress.subTask(text);
	}

	protected final String formatCode(String text) {
		IDocument doc = new Document(text);
		TextEdit edit = getCodeFormatter().format(CodeFormatter.K_COMPILATION_UNIT, doc.get(), 0, doc.get().length(), 0,
				null);

		try {
			// check if text formatted successfully
			if (edit != null) {
				edit.apply(doc);
				text = doc.get();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return text;
	}

	protected void doRun() throws InterruptedException {
		try {
			setupProgressMonitor();
			ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {

				@Override
				public void run(IProgressMonitor monitor) throws CoreException {
					try {
						customRun();
						myRunStatus = getExceptionsStatus();
						// XXX consider catching CCE and provide "programming error"
						// to help users with their templates
					} catch (NullPointerException ex) {
						myRunStatus = newStatus(0, ex);
					} catch (UnexpectedBehaviourException ex) {
						myRunStatus = newStatus(0, ex);
					} catch (InterruptedException ex) {
						myRunStatus = newStatus(0, ex);
					}
				}
			}, null);
			if (myRunStatus.getSeverity() == IStatus.CANCEL
					&& myRunStatus.getException() instanceof InterruptedException) {
				throw (InterruptedException) myRunStatus.getException();
			}
		} catch (CoreException ex) {
			myRunStatus = ex.getStatus();
		} finally {
			getProgress().done();
			clearExceptionsList();
		}
	}

	protected CodeFormatter getCodeFormatter() {
		if (myCodeFormatter == null) {
			myCodeFormatter = ToolFactory.createCodeFormatter(null);
		}
		return myCodeFormatter;
	}

	protected OrganizeImportsPostprocessor getImportsPostrocessor() {
		if (myImportsPostprocessor == null) {
			myImportsPostprocessor = new OrganizeImportsPostprocessor(isToRestoreExistingImports);
		}
		return myImportsPostprocessor;
	}

	protected final void clearExceptionsList() {
		myExceptions.clear();
	}

	protected final IStatus getExceptionsStatus() {
		if (myExceptions == null || myExceptions.isEmpty()) {
			return Status.OK_STATUS;
		} else {
			IStatus[] s = myExceptions.toArray(new IStatus[myExceptions.size()]);
			return new MultiStatus(AbstractGeneratorWrapper.class.getCanonicalName(), 0, s, Messages.problems, null);
		}
	}

	public static final class Counter {

		protected final HashMap<EClass, Integer> myCounters = new HashMap<EClass, Integer>();
		protected final HashMap<EClass, Integer> myCache = new HashMap<EClass, Integer>();
		protected final Integer CACHE_MISS = new Integer(0);

		public Counter() {
		}

		public void registerFactor(EClass eClass, int count) {
			myCounters.put(eClass, count);
		}

		public int getTotal(EObject from) {
			int total = process(from);
			for (Iterator<EObject> it = from.eAllContents(); it.hasNext();) {
				total += process(it.next());
			}
			return total;
		}

		protected int process(EObject next) {
			final EClass nextKey = next.eClass();
			Integer cachedValue = checkCached(nextKey);
			if (cachedValue != null) {
				return cachedValue;
			}
			LinkedList<EClass> checkQueue = new LinkedList<EClass>();
			checkQueue.add(nextKey);
			do {
				EClass key = checkQueue.removeFirst();
				if (myCounters.containsKey(key)) {
					final Integer value = myCounters.get(key);
					cache(nextKey, value);
					return value;
				} else {
					// add immeditate superclasses to check first
					checkQueue.addAll(key.getESuperTypes());
				}
			} while (!checkQueue.isEmpty());
			cache(nextKey, CACHE_MISS);
			return 0;
		}

		protected Integer checkCached(EClass nextKey) {
			return myCache.get(nextKey);
		}

		protected void cache(EClass nextKey, Integer value) {
			myCache.put(nextKey, value);
		}
	}
	
	public static String getFileContents(IFile file) {
		StringBuffer contents = new StringBuffer();
		char[] buffer = new char[1024];
		int count;
		try {
			Reader in = new InputStreamReader(file.getContents(true), file.getCharset());
			try {
				while ((count = in.read(buffer)) > 0) {
					contents.append(buffer, 0, count);
				}
			} finally {
				in.close();
			}
		} catch (CoreException ce) {
			ce.printStackTrace();
			return null;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		}
		return contents.toString();
	}
}
