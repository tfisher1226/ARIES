package common.tx.service.registration;

import java.util.HashMap;
import java.util.Map;


public class RegistrarMapper {
	
    private static final RegistrarMapper INSTANCE = new RegistrarMapper();

    public static RegistrarMapper getInstance() {
        return INSTANCE;
    }

    private Map<String, Registrar> registrarMap = new HashMap<String, Registrar>();


    protected RegistrarMapper() {
    }

    /**
     * Add a registrar for the specified protocol identifier.
     */
    public void addRegistrar(String protocolIdentifier, Registrar registrar) {
        synchronized (registrarMap) {
            registrarMap.put(protocolIdentifier, registrar);
        }
        registrar.install(protocolIdentifier);
    }

    /**
     * Get the registrar for the specified protocol identifier.
     */
    public Registrar getRegistrar(String protocolIdentifier) {
        synchronized (registrarMap) {
            Object localRegistrar = registrarMap.get(protocolIdentifier);
            return (Registrar) localRegistrar;
        }
    }

    /**
     * Remove the registrar for the specified protocol identifier.
     */
    public void removeRegistrar(String protocolIdentifier) {
        Object localRegistrar = null;
        synchronized (registrarMap) {
            localRegistrar = registrarMap.remove(protocolIdentifier);
        }
        if (localRegistrar != null) {
            ((Registrar) localRegistrar).uninstall(protocolIdentifier);
        }
    }

}
