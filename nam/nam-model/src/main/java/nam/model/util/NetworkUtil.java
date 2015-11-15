package nam.model.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import nam.model.Master;
import nam.model.Minion;
import nam.model.Network;
import nam.model.Networks;
import nam.model.Project;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class NetworkUtil extends BaseUtil {

	public static Object getKey(Network network) {
		return network.getName();
	}
	
	public static String getLabel(Network network) {
		return network.getName();
	}

	public static boolean getLabel(Collection<Network> networkList) {
		if (networkList == null  || networkList.size() == 0)
			return true;
		Iterator<Network> iterator = networkList.iterator();
		while (iterator.hasNext()) {
			Network network = iterator.next();
			if (!isEmpty(network))
				return false;
		}
		return true;
	}
	
	public static boolean isEmpty(Network network) {
		if (network == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Network> networkList) {
		if (networkList == null  || networkList.size() == 0)
			return true;
		Iterator<Network> iterator = networkList.iterator();
		while (iterator.hasNext()) {
			Network network = iterator.next();
			if (!isEmpty(network))
				return false;
		}
		return true;
	}
	
	public static String toString(Network network) {
		if (isEmpty(network))
			return "Network: [uninitialized] "+network.toString();
		String text = network.toString();
		return text;
	}
	
	public static String toString(Collection<Network> networkList) {
		if (isEmpty(networkList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Network> iterator = networkList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Network network = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(network);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Network create() {
		Network network = new Network();
		initialize(network);
		return network;
	}
	
	public static void initialize(Network network) {
		//nothing for now
	}
	
	public static boolean validate(Network network) {
		if (network == null)
			return false;
		Validator validator = Validator.getValidator();
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Network> networkList) {
		Validator validator = Validator.getValidator();
		Iterator<Network> iterator = networkList.iterator();
		while (iterator.hasNext()) {
			Network network = iterator.next();
			//TODO break or accumulate?
			validate(network);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Network> networkList) {
		Collections.sort(networkList, createNetworkComparator());
	}
	
	public static Collection<Network> sortRecords(Collection<Network> networkCollection) {
		List<Network> list = new ArrayList<Network>(networkCollection);
		Collections.sort(list, createNetworkComparator());
		return list;
	}
	
	public static Comparator<Network> createNetworkComparator() {
		return new Comparator<Network>() {
			public int compare(Network network1, Network network2) {
				Object key1 = getKey(network1);
				Object key2 = getKey(network2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Network clone(Network network) {
		if (network == null)
			return null;
		Network clone = create();
		clone.setInitialSize(ObjectUtil.clone(network.getInitialSize()));
		clone.setMinSize(ObjectUtil.clone(network.getMinSize()));
		clone.setMaxSize(ObjectUtil.clone(network.getMaxSize()));
		return clone;
	}

	
	public static Collection<Network> getNetworks(Project project) {
		return getNetworks(project.getNetworks());
	}
	
	public static Collection<Network> getNetworks(Networks networks) {
		if (networks != null)
			return networks.getNetworks();
		return null;
	}
	
	public static boolean containsNetwork(Networks networks, Network network) {
		Collection<Network> list = getNetworks(networks);
		if (list.contains(network))
			return true;
		Iterator<Network> iterator = list.iterator();
		String networkName = network.getName();
		String networkNamespace = network.getNamespace();
		while (iterator.hasNext()) {
			Network currentNetwork = iterator.next();
			if (currentNetwork.getName().equals(networkName) &&
				currentNetwork.getNamespace().equals(networkNamespace))
					return true;
		}
		return false;
	}
	
	public static void addNetwork(Networks networks, Network network) {
		if (!containsNetwork(networks, network))
			networks.getNetworks().add(network);
	}
	
	public static void addNetworks(Networks networks, Collection<Network> networkList) {
		Iterator<Network> iterator = networkList.iterator();
		while (iterator.hasNext()) {
			Network network = iterator.next();
			addNetwork(networks, network);
		}
	}

	public static void removeNetworks(Networks networks, Networks networksToRemove) {
		Collection<Network> networkListToRemove = getNetworks(networksToRemove);
		Iterator<Network> iterator = networkListToRemove.iterator();
		while (iterator.hasNext()) {
			Network network = iterator.next();
			removeNetwork(networks, network);
		}
	}
	
	public static boolean removeNetwork(Networks networks, Network network) {
		if (containsNetwork(networks, network))
			return networks.getNetworks().remove(network);
		return false;
	}

	public static Collection<Master> getMasters(Network network) {
		return null;
	}

	public static Collection<Minion> getMinions(Network network) {
		return null;
	}
	
}
