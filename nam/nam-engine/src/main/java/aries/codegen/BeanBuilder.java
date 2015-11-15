package aries.codegen;

import nam.model.Element;
import aries.generation.model.ModelClass;


public interface BeanBuilder {

	/*
	 * Class initialization
	 * --------------------
	 */
	
	public void initializeClass(ModelClass modelClass, Element element) throws Exception;


	/*
	 * Operation generation
	 * --------------------
	 */

//	public String generateEqualsMethod(ModelClass modelClass) throws Exception;

//	public String generateHashCodeMethod(ModelClass modelClass) throws Exception;

}
