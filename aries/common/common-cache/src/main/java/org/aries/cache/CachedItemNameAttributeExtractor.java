package org.aries.cache;

import net.sf.ehcache.Element;
import net.sf.ehcache.search.attribute.AttributeExtractor;
import net.sf.ehcache.search.attribute.AttributeExtractorException;


/**
 * Implementing the AttributeExtractor Interface and passing it in
 * allows you to create very efficient and specific attribute extraction
 * for performance sensitive code
 */
public class CachedItemNameAttributeExtractor implements AttributeExtractor {

	private static final long serialVersionUID = 1L;

	@Override
	public Object attributeFor(Element element, String attributeName) throws AttributeExtractorException {
		CachedItem item = (CachedItem) element.getValue();
		return item.getElementName();
	}
	
}
