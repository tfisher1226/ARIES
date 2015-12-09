package nam.model.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import nam.model.Extensions;
import nam.model.Information;
import nam.model.Messaging;
import nam.model.Namespace;
import nam.model.Persistence;
import nam.model.Project;
import nam.model.Unit;
import nam.ui.View;

import org.aries.Assert;


public class ExtensionsUtil {

	public static Extensions newExtensions() {
		Extensions extensions = new Extensions();
		return extensions;
	}

	public static void addExtensions(Project project, Collection<? extends Serializable> extensions) {
		Iterator<? extends Serializable> iterator = extensions.iterator();
		while (iterator.hasNext()) {
			Serializable extension = iterator.next();
			if (extension instanceof Information) {
				addExtension(project, (Information) extension);
			} else if (extension instanceof Persistence) {
				addExtension(project, (Persistence) extension);
			} else if (extension instanceof Messaging) {
				addExtension(project, (Messaging) extension);
			}
		}
	}

	public static void addExtension(Project project, Information newBlock) {
		if (newBlock == null)
			return;

		List<Serializable> list = project.getExtensions().getInformationsAndMessagingsAndPersistences();
		Information matchingBlock = null;
		Iterator<Serializable> iterator = list.iterator();
		boolean found = false;
		while (iterator.hasNext()) {
			Serializable extension = iterator.next();
			if (extension instanceof Information == false)
				continue;
			Information information = (Information) extension;
			if (information.getName().equals(newBlock.getName())) {
				matchingBlock = information;
				found = true;
				break;
			}
		}
		if (found) {
			mergeInformationBlocks(matchingBlock, newBlock);
		} else {
			list.add(newBlock);
		}
	}

	public static void addExtension(Project project, Persistence newBlock) {
		if (newBlock == null)
			return;
		
		List<Serializable> list = project.getExtensions().getInformationsAndMessagingsAndPersistences();
		Iterator<Serializable> iterator = list.iterator();
		boolean found = false;
		while (iterator.hasNext()) {
			Serializable extension = iterator.next();
			if (extension instanceof Persistence == false)
				continue;

			Persistence persistence = (Persistence) extension;
			if (persistence == newBlock)
				return;

			if (persistence.getName() == null) {
				/*
				 * We will come here for any "persist" block specified in ARIEL
				 * that does not have a name yet - just adopt the name from the
				 * first Unit (in ARIEL "persist" blocks will have only one Unit).
				 */
				Collection<Unit> units = PersistenceUtil.getUnits(persistence);
				Assert.notNull(units, "Persistence units null");
				Assert.notEmpty(units, "At least one Persistence-unit must be specified");
				Unit unit = units.iterator().next();
				persistence.setName(unit.getName());
				//System.out.println();
			}
			
			if (persistence.getDomain() == null) {
				/*
				 * We will come here for any "persist" block specified in ARIEL
				 * that does not have a name yet - just adopt the name from the
				 * first Unit (in ARIEL "persist" blocks will have only one Unit).
				 */
				Collection<Unit> units = PersistenceUtil.getUnits(persistence);
				Assert.notNull(units, "Persistence units null");
				Assert.notEmpty(units, "At least one Persistence-unit must be specified");
				Unit unit = units.iterator().next();
				persistence.setName(unit.getName());
				//System.out.println();
			}
			
			if (persistence.getName().equals(newBlock.getName())) {
				mergePersistenceBlocks(persistence, newBlock);
				found = true;
				break;
			}
		}
//		if (found)
//			System.out.println();
		if (!found)
			list.add(newBlock);
	}

	public static void addExtension(Project project, Messaging newMessaging) {
		List<Serializable> list = project.getExtensions().getInformationsAndMessagingsAndPersistences();
		Iterator<Serializable> iterator = list.iterator();
		boolean found = false;
		while (iterator.hasNext()) {
			Serializable extension = iterator.next();
			if (extension instanceof Messaging == false)
				continue;
			Messaging messaging = (Messaging) extension;
			if (messaging.getName().equals(newMessaging.getName())) {
				found = true;
				break;
			}
		}
		if (!found)
			list.add(newMessaging);
	}

	public static List<Information> getInformationBlocks(Project project) {
		List<Information> list = new ArrayList<Information>();
		List<Serializable> extensions = project.getExtensions().getInformationsAndMessagingsAndPersistences();
		Iterator<Serializable> iterator = extensions.iterator();
		while (iterator.hasNext()) {
			Serializable extension = iterator.next();
			if (extension instanceof Information)
				list.add((Information) extension);
		}
		return list;
	}

	public static Information getInformationBlock(Project project, Namespace namespace) {
		List<Information> blocks = getInformationBlocks(project);
		Iterator<Information> iterator = blocks.iterator();
		while (iterator.hasNext()) {
			Information information = iterator.next();
			if (InformationUtil.containsNamespace(information, namespace)) {
				return information;
			}
		}
		return null;
	}

	/**
	 * Merges <code>Information</code> blocks.
	 * Merges into block1 everything in block2 not in block1.
	 */
	public static void mergeInformationBlocks(Information block1, Information block2) {
		if (block2.getImported())
			block1.setImported(true);
		InformationUtil.mergeImports(block1, block2);
		InformationUtil.mergeNamespaces(block1, block2);
	}


	public static List<Persistence> getPersistenceBlocks(Collection<Project> projectList) {
		List<Persistence> list = new ArrayList<Persistence>();
		Iterator<Project> iterator = projectList.iterator();
		while (iterator.hasNext()) {
			Project project = iterator.next();
			list.addAll(getPersistenceBlocks(project));
		}
		return list;
	}
	
	public static List<Persistence> getPersistenceBlocks(Project project) {
		List<Persistence> list = new ArrayList<Persistence>();
		List<Serializable> extensions = project.getExtensions().getInformationsAndMessagingsAndPersistences();
		Iterator<Serializable> iterator = extensions.iterator();
		while (iterator.hasNext()) {
			Serializable extension = iterator.next();
			if (extension instanceof Persistence)
				list.add((Persistence) extension);
		}
		return list;
	}
	
	public static Persistence getPersistenceBlock(Project project, String domain, String name) {
		List<Persistence> blocks = getPersistenceBlocks(project);
		Iterator<Persistence> iterator = blocks.iterator();
		while (iterator.hasNext()) {
			Persistence persistenceBlock = iterator.next();
			if (!persistenceBlock.getName().equals(name))
				continue;
			if (!persistenceBlock.getDomain().equals(domain))
				continue;
			return persistenceBlock;
		}
		return null;
	}

	public static Persistence getPersistenceBlock(Project project, Namespace namespace) {
		return getPersistenceBlock(project, namespace.getUri());
	}
	
	public static Persistence getPersistenceBlock(Project project, String namespace) {
		List<Persistence> blocks = getPersistenceBlocks(project);
		Iterator<Persistence> iterator = blocks.iterator();
		while (iterator.hasNext()) {
			Persistence persistenceBlock = iterator.next();
//			if (persistenceBlock.getNamespace() == null)
//				System.out.println();
			if (!persistenceBlock.getNamespace().equals(namespace))
				continue;
			return persistenceBlock;
		}
		return null;
	}
	
	/**
	 * Merges <code>Persistence</code> blocks.
	 * Merges into block1 everything in block2 not in block1.
	 */
	public static void mergePersistenceBlocks(Persistence block1, Persistence block2) {
		if (block2.getImported())
			block1.setImported(true);
		PersistenceUtil.mergeImports(block1, block2);
		PersistenceUtil.mergeAdapters(block1, block2);
		PersistenceUtil.mergeSources(block1, block2);
		PersistenceUtil.mergeUnits(block1, block2);
	}

	public static Persistence getPersistenceBlockByNamespace(Project project, String namespaceUri) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public static List<Messaging> getMessagingBlocks(Collection<Project> projectList) {
		List<Messaging> list = new ArrayList<Messaging>();
		Iterator<Project> iterator = projectList.iterator();
		while (iterator.hasNext()) {
			Project project = iterator.next();
			list.addAll(getMessagingBlocks(project));
		}
		return list;
	}
	
	public static List<Messaging> getMessagingBlocks(Project project) {
		List<Messaging> list = new ArrayList<Messaging>();
		List<Serializable> extensions = project.getExtensions().getInformationsAndMessagingsAndPersistences();
		Iterator<Serializable> iterator = extensions.iterator();
		while (iterator.hasNext()) {
			Serializable extension = iterator.next();
			if (extension instanceof Messaging)
				list.add((Messaging) extension);
		}
		return list;
	}
	
	public static Messaging getMessagingBlock(Project project, String domain, String name) {
		List<Messaging> blocks = getMessagingBlocks(project);
		Iterator<Messaging> iterator = blocks.iterator();
		while (iterator.hasNext()) {
			Messaging messagingBlock = iterator.next();
			if (!messagingBlock.getName().equals(name))
				continue;
			if (!messagingBlock.getDomain().equals(domain))
				continue;
			return messagingBlock;
		}
		return null;
	}

	public static Messaging getMessagingBlock(Project project, Namespace namespace) {
		return getMessagingBlock(project, namespace.getUri());
	}
	
	public static Messaging getMessagingBlock(Project project, String namespace) {
		List<Messaging> blocks = getMessagingBlocks(project);
		Iterator<Messaging> iterator = blocks.iterator();
		while (iterator.hasNext()) {
			Messaging messagingBlock = iterator.next();
//			if (messagingBlock.getNamespace() == null)
//				System.out.println();
			if (!messagingBlock.getNamespace().equals(namespace))
				continue;
			return messagingBlock;
		}
		return null;
	}

	
	/**
	 * Merges <code>View</code> blocks.
	 * Merges into block1 everything in block2 not in block1.
	 */
	public static void mergeViewBlocks(View block1, View block2) {
		ViewUtil.mergeImports(block1, block2);
		//TODO merge other elements
	}

	
}
