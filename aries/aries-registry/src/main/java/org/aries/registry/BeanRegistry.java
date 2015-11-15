package org.aries.registry;

import org.aries.bean.AccessException;
import org.aries.bean.AlreadyBoundException;
import org.aries.bean.NotBoundException;
import org.aries.bean.RegistryException;


public interface BeanRegistry {

    /**
     * Returns the remote reference bound to the specified
     * <code>name</code> in this registry.
     *
     * @param	name the name for the remote reference to look up
     *
     * @return	a reference to a remote object
     *
     * @throws	NotBoundException if <code>name</code> is not currently bound
     *
     * @throws	RegistryException if remote communication with the
     * registry failed; if exception is a <code>ServerException</code>
     * containing an <code>AccessException</code>, then the registry
     * denies the caller access to perform this operation
     *
     * @throws	AccessException if this registry is local and it denies
     * the caller access to perform this operation
     *
     * @throws	NullPointerException if <code>name</code> is <code>null</code>
     */
    public <T> T lookup(String name) throws RegistryException, NotBoundException, AccessException;

    /**
     * Binds a remote reference to the specified <code>name</code> in
     * this registry.
     *
     * @param	name the name to associate with the remote reference
     * @param	obj a reference to a remote object (usually a stub)
     *
     * @throws	AlreadyBoundException if <code>name</code> is already bound
     *
     * @throws	RegistryException if remote communication with the
     * registry failed; if exception is a <code>ServerException</code>
     * containing an <code>AccessException</code>, then the registry
     * denies the caller access to perform this operation (if
     * originating from a non-local host, for example)
     *
     * @throws	AccessException if this registry is local and it denies
     * the caller access to perform this operation
     *
     * @throws	NullPointerException if <code>name</code> is
     * <code>null</code>, or if <code>obj</code> is <code>null</code>
     */
    public <T> void bind(String name, T stub) throws RegistryException, AlreadyBoundException, AccessException;

    /**
     * Removes the binding for the specified <code>name</code> in
     * this registry.
     *
     * @param	name the name of the binding to remove
     *
     * @throws	NotBoundException if <code>name</code> is not currently bound
     *
     * @throws	RegistryException if remote communication with the
     * registry failed; if exception is a <code>ServerException</code>
     * containing an <code>AccessException</code>, then the registry
     * denies the caller access to perform this operation (if
     * originating from a non-local host, for example)
     *
     * @throws	AccessException if this registry is local and it denies
     * the caller access to perform this operation
     *
     * @throws	NullPointerException if <code>name</code> is <code>null</code>
     */
    public void unbind(String name) throws RegistryException, NotBoundException, AccessException;

    /**
     * Replaces the binding for the specified <code>name</code> in
     * this registry with the supplied remote reference.  If there is
     * an existing binding for the specified <code>name</code>, it is
     * discarded.
     *
     * @param	name the name to associate with the remote reference
     * @param	obj a reference to a remote object (usually a stub)
     *
     * @throws	RegistryException if remote communication with the
     * registry failed; if exception is a <code>ServerException</code>
     * containing an <code>AccessException</code>, then the registry
     * denies the caller access to perform this operation (if
     * originating from a non-local host, for example)
     *
     * @throws	AccessException if this registry is local and it denies
     * the caller access to perform this operation
     *
     * @throws	NullPointerException if <code>name</code> is
     * <code>null</code>, or if <code>obj</code> is <code>null</code>
     */
    public <T> void rebind(String name, T stub) throws RegistryException, AccessException;

    /**
     * Returns an array of the names bound in this registry.  The
     * array will contain a snapshot of the names bound in this
     * registry at the time of the given invocation of this method.
     *
     * @return	an array of the names bound in this registry
     *
     * @throws	RegistryException if remote communication with the
     * registry failed; if exception is a <code>ServerException</code>
     * containing an <code>AccessException</code>, then the registry
     * denies the caller access to perform this operation
     *
     * @throws	AccessException if this registry is local and it denies
     * the caller access to perform this operation
     */
    public String[] list() throws RegistryException, AccessException;
    
}
