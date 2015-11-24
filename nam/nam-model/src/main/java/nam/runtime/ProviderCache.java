package nam.runtime;

import java.util.HashMap;
import java.util.Map;

import nam.model.Provider;


public class ProviderCache {

	private Map<String, Provider> providersByName = new HashMap<String, Provider>();

	
	public void addProvider(Provider provider) {
		providersByName.put(provider.getName(), provider);
	}
	
	public Map<String, Provider> getProvidersByName() {
		return providersByName;
	}
	
	public Provider getProviderByName(String name) {
		Map<String, Provider> map = providersByName;
		Provider provider = map.get(name);
		return provider;
	}

}
