package org.aries.emf;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.xsd.ecore.XSDEcoreBuilder;


public class Xsd2Ecore {

	public static void main(String[] args) {
		Xsd2Ecore x2e = new Xsd2Ecore();
		x2e.go("/workspace/ARIES3/nam-model/source/main/resources/schema/model-0.0.1.xsd", "/workspace/ARIES3/nam-model/source/main/resources/schema/model-0.0.1.xmi");
	}


	public void go(String sourcename, String targetname) {

		System.out.println("Starting");

		XSDEcoreBuilder xsdEcoreBuilder = new XSDEcoreBuilder();
		ResourceSet resourceSet = new ResourceSetImpl();
		Collection eCorePackages = xsdEcoreBuilder.generate(URI.createFileURI(sourcename));

		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		Resource resource = resourceSet.createResource(URI.createFileURI(targetname));

		for (Iterator iter = eCorePackages.iterator(); iter.hasNext();) {
			EPackage element = (EPackage) iter.next();
			resource.getContents().add(element);
		}

		try {
			resource.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Finished");

	}

}
