package aries.codegen;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.codegen.ecore.CodeGenEcorePlugin;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jdt.internal.formatter.DefaultCodeFormatter;
import org.eclipse.jdt.internal.formatter.DefaultCodeFormatterOptions;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.TextEdit;


public class SourceCodeFormatter {

	public static IProject project;

	public static Object formatter;


	public static void initialize() {
		try {
			//project = createProject();
			//formatter = createCodeFormatter(project);
			DefaultCodeFormatterOptions options = DefaultCodeFormatterOptions.getEclipseDefaultSettings();
			options.page_width = 1000;
			formatter = new DefaultCodeFormatter(options);
		} catch (Exception e) {
			CodeGenEcorePlugin.INSTANCE.log(e);
			throw new RuntimeException(e);
		}
	}

//	public static IProject createProject() throws CoreException {
//		//IWorkspace workspace = ResourcesPlugin.getWorkspace();
//		IProgressMonitor progressMonitor = new NullProgressMonitor();
//		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
//		IProject project = root.getProject("DesiredProjectName");
//		project.create(progressMonitor);
//		project.open(progressMonitor);
//		return project;
//	}

	public static Object createCodeFormatter(IProject project) {
		IJavaProject javaProject = JavaCore.create(project);
		Map<?, ?> options = javaProject.getOptions(true);
		return ToolFactory.createCodeFormatter(options);
	}

	public static String formatCode(String contents) {
		if (formatter == null)
			initialize();
		IProgressMonitor monitor = new NullProgressMonitor();
		if (formatter instanceof CodeFormatter) {
			CodeFormatter codeFormatter = (CodeFormatter) formatter; 
			IDocument doc = new Document(contents);
			TextEdit edit = codeFormatter.format(CodeFormatter.K_COMPILATION_UNIT, doc.get(), 0, doc.get().length(), 0, null);
			if (edit != null) {
				try {
					edit.apply(doc);
					contents = doc.get();

//					ASTParser parser = ASTParser.newParser(AST.JLS3);
//					parser.setSource(contents.toCharArray());
//					CompilationUnit cu = (CompilationUnit) parser.createAST(monitor);
//					//ASTNode astNode = parser.createAST(monitor);
//					CompilationUnit astRoot = (CompilationUnit) cu.getRoot();
//					AST ast = cu.getAST();
//
//
//					ImportRewrite importsRewrite = StubUtility.createImportRewrite(astRoot, false);
//					Set<String> oldSingleImports= new HashSet<String>();
//					Set<String> oldDemandImports= new HashSet<String>();
//					List<SimpleName> typeReferences= new ArrayList<SimpleName>();
//					List<SimpleName> staticReferences= new ArrayList<SimpleName>();

//					if (!collectReferences(astRoot, typeReferences, staticReferences, oldSingleImports, oldDemandImports))
//						return null;
//					monitor.worked(1);

					//TypeReferenceProcessor processor= new TypeReferenceProcessor(oldSingleImports, oldDemandImports, astRoot, importsRewrite, fIgnoreLowerCaseNames);

					//					org.eclipse.jdt.internal.core.CompilationUnit xxx = new org.eclipse.jdt.internal.core.CompilationUnit(null, "xxx", DefaultWorkingCopyOwner.PRIMARY);
					//					System.out.println();
					//					xxx.getCompilationUnit();

					//PackageFragment pf = new PackageFragment();
					//IPackageFragmentRoot srcFolder= javaProject.getPackageFragmentRoot(folder);
					//Assert.assertTrue(srcFolder.exists()); // resource exists and is on build path
					//IPackageFragment fragment= srcFolder.createPackageFragment("x.y", true, null);					
					//ICompilationUnit icu = fragment.createCompilationUnit("X.java", contents, false, null);

					//OrganizeImportsOperation importsOperation = new OrganizeImportsOperation((ICompilationUnit) ast, (CompilationUnit) root, false, false, true, null);
					//importsOperation.run(monitor);
					//String output = importsOperation.toString();
					//System.out.println(output);

					//List imports = cu.imports();
					//IJavaElement javaElement = cu.getJavaElement();

					//					CompilationUnitEditor compilationUnitEditor = new CompilationUnitEditor();
					//					compilationUnitEditor.setSelection(javaElement);

					//					OrganizeImportsAction importsAction = new OrganizeImportsAction(compilationUnitEditor);
					//					importsAction.run();
					//					String text = importsAction.getText();

					//					IChooseImportQuery query = new IChooseImportQuery() {
					//						@Override
					//						public TypeNameMatch[] chooseImports(TypeNameMatch[][] openChoices, ISourceRange[] ranges) {
					//							throw new RuntimeException();
					//						}
					//					};

				} catch (Exception e) {
					CodeGenEcorePlugin.INSTANCE.log(e);
				}
			}
		}
		return contents;
	} 


//	private boolean collectReferences(CompilationUnit astRoot, List typeReferences, List staticReferences, Set oldSingleImports, Set oldDemandImports) {
////		if (!fAllowSyntaxErrors) {
////			IProblem[] problems= astRoot.getProblems();
////			for (int i= 0; i < problems.length; i++) {
////				IProblem curr= problems[i];
////				if (curr.isError() && (curr.getID() & IProblem.Syntax) != 0) {
////					fParsingError = problems[i];
////					return false;
////				}
////			}
////		}
//		List imports = astRoot.imports();
//		for (int i= 0; i < imports.size(); i++) {
//			ImportDeclaration curr= (ImportDeclaration) imports.get(i);
//			String id= ASTResolving.getFullName(curr.getName());
//			if (curr.isOnDemand()) {
//				oldDemandImports.add(id);
//			} else {
//				oldSingleImports.add(id);
//			}
//		}
//
//		IJavaProject project= fCompilationUnit.getJavaProject();
//		ImportReferencesCollector.collect(astRoot, project, null, typeReferences, staticReferences);
//		return true;
//	}
}
