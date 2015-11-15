package aries.codegen;

import org.apache.tools.ant.types.FilterSet;
import org.apache.tools.ant.types.FilterSetCollection;

import aries.generation.model.ModelAnnotation;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelConstructor;
import aries.generation.model.ModelInterface;
import aries.generation.model.ModelOperation;


public interface FileGenerator {

	/*
	 * Class initialization
	 * --------------------
	 */
	
	public void initializeClass(ModelClass modelClass) throws Exception;


	/*
	 * File generation
	 * ---------------
	 */
	
	public void generateFile(FilterSet filterSet) throws Exception;

	public void generateFile(FilterSetCollection filters) throws Exception;

	public void generateFile(String content) throws Exception;

	
	/*
	 * Package generation
	 * ------------------
	 */
	
	public String generatePackageDeclaration(ModelClass modelClass) throws Exception;

	
	/*
	 * Import list generation
	 * ----------------------
	 */
	
	public String generateImportedPackages(ModelClass modelClass) throws Exception;
	
	
	/*
	 * Class declaration generation
	 * ----------------------------
	 */

	public String generateClassDeclaration(ModelClass modelClass) throws Exception;
	
	public String generateClassJavadoc(ModelClass modelClass) throws Exception;

	public String generateClassName(ModelClass modelClass) throws Exception;
	
	
	/*
	 * Field generation
	 * ----------------
	 */
	
	public String generateStaticFields(ModelClass modelClass) throws Exception;

	public String generateStaticMethods(ModelClass modelClass) throws Exception;

	public String generateInstanceFields(ModelClass modelClass) throws Exception;
	
	public String generateField(ModelAttribute modelAttribute) throws Exception;

	
	/*
	 * Method generation
	 * -----------------
	 */
	
	public String generateInstanceMethods(ModelClass modelClass) throws Exception;
	

	/*
	 * Annotation generation
	 * ---------------------
	 */

	public String generateAnnotations(ModelClass modelClass) throws Exception;

	public String generateAnnotations(ModelInterface modelInterface) throws Exception;

	public String generateAnnotations(ModelAttribute modelAttribute) throws Exception;

	public String generateAnnotations(ModelOperation modelOperation) throws Exception;

	public String generateAnnotation(ModelAnnotation modelAnnotation) throws Exception;

	public String generateModifiers(ModelAttribute modelAttribute) throws Exception;


	/*
	 * Constructor generation
	 * ----------------------
	 */

	public String generateConstructors(ModelClass modelClass) throws Exception;
	
	public String generateConstructor(ModelClass modelClass, ModelConstructor modelConstructor) throws Exception;
	
	
	/*
	 * Accessor generation
	 * -------------------
	 */
	
	public String generateAccessorMethods(ModelClass modelClass) throws Exception;
	
	public String generateGetterMethod(ModelAttribute modelAttribute) throws Exception;

	public String generateSetterMethod(ModelAttribute modelAttribute) throws Exception;
	

	/*
	 * Operation generation
	 * --------------------
	 */

//	public String generateEqualsMethod(ModelClass modelClass) throws Exception;

//	public String generateHashCodeMethod(ModelClass modelClass) throws Exception;

}
