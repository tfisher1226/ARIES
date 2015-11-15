package aries.codegen;

import org.apache.tools.ant.types.FilterSet;
import org.apache.tools.ant.types.FilterSetCollection;

import aries.generation.model.ModelAnnotation;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelConstructor;
import aries.generation.model.ModelField;
import aries.generation.model.ModelInterface;
import aries.generation.model.ModelOperation;


public interface BeanGenerator {

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

	public String generateClassSignature(ModelClass modelClass) throws Exception;
	
	
	/*
	 * Field generation
	 * ----------------
	 */
	
	public String generateStaticFields(ModelClass modelClass) throws Exception;

	public String generateStaticMethods(ModelClass modelClass) throws Exception;

	public String generateInstanceFields(ModelClass modelClass) throws Exception;
	
	public String generateField(ModelField modelField) throws Exception;

	
	/*
	 * Method generation
	 * -----------------
	 */
	
	public String generateInstanceMethods(ModelClass modelClass) throws Exception;
	

	/*
	 * Annotation generation
	 * ---------------------
	 */

	public String generateClassAnnotations(ModelClass modelClass) throws Exception;

	public String generateInterfaceAnnotations(ModelInterface modelInterface) throws Exception;

	public String generateFieldAnnotations(ModelField modelField) throws Exception;

	public String generateOperationAnnotations(ModelOperation modelOperation) throws Exception;

	public String generateAnnotation(ModelAnnotation modelAnnotation) throws Exception;

	public String generateModifiers(ModelField modelField) throws Exception;


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
	
	public String generateGetterMethod(ModelField modelField) throws Exception;

	public String generateSetterMethod(ModelField modelField) throws Exception;
	

	/*
	 * Operation generation
	 * --------------------
	 */

//	public String generateEqualsMethod(ModelClass modelClass) throws Exception;

//	public String generateHashCodeMethod(ModelClass modelClass) throws Exception;

}
