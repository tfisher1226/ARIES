package aries.generation;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import nam.model.Information;
import nam.model.Namespace;
import nam.model.Project;
import nam.model.util.InformationUtil;
import nam.model.util.ProjectUtil;



public class AriesToJPAGenerator {

	public void generate(Project project) {
		Collection<Information> informationBlocks = ProjectUtil.getInformationBlocks(project);
		Iterator<Information> iterator = informationBlocks.iterator();
		while (iterator.hasNext()) {
			Information information = iterator.next();
			Collection<Namespace> namespaces = InformationUtil.getNamespaces(information);
			Iterator<Namespace> iterator2 = namespaces.iterator();
			while (iterator2.hasNext()) {
				Namespace namespace = iterator2.next();
				generate(namespace);
			}
		}
	}
	
	public void generate(Namespace namespace) {
	}
	
	public void generateEntityBeans(Project project) {
	}
	
}
