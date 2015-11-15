package nam.model.network;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.Network;
import nam.model.util.NetworkUtil;


public class NetworkListObject extends AbstractListObject<Network> implements Comparable<NetworkListObject>, Serializable {
	
	private Network network;
	
	
	public NetworkListObject(Network network) {
		this.network = network;
	}
	
	
	public Network getNetwork() {
		return network;
	}
	
	@Override
	public Object getKey() {
		return getKey(network);
	}
	
	public Object getKey(Network network) {
		return NetworkUtil.getKey(network);
	}
	
	@Override
	public String getLabel() {
		return getLabel(network);
	}
	
	public String getLabel(Network network) {
		return NetworkUtil.getLabel(network);
	}
	
	@Override
	public String toString() {
		return toString(network);
	}
	
	@Override
	public String toString(Network network) {
		return NetworkUtil.toString(network);
	}
	
	@Override
	public int compareTo(NetworkListObject other) {
		Object thisKey = getKey(this.network);
		Object otherKey = getKey(other.network);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		NetworkListObject other = (NetworkListObject) object;
		Object thisKey = NetworkUtil.getKey(this.network);
		Object otherKey = NetworkUtil.getKey(other.network);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
