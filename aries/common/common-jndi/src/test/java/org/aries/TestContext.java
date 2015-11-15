package org.aries;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.naming.Binding;
import javax.naming.CompositeName;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameClassPair;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.OperationNotSupportedException;


public class TestContext implements Context {

	private Map<String, Object> values = new HashMap<String, Object>();
	
	
	public TestContext() throws NamingException {
		//do nothing
	}

	@Override
	public Object lookup(Name name) throws NamingException {
		return values.get(name.toString());
	}

	@Override
	public Object lookup(String name) throws NamingException {
		return values.get(name);
	}

	@Override
	public void bind(Name name, Object value) throws NamingException {
		values.put(name.toString(), value);
	}

	@Override
	public void bind(String name, Object value) throws NamingException {
		values.put(name, value);
	}

	@Override
	public void rebind(Name name, Object value) throws NamingException {
		values.put(name.toString(), value);
	}

	@Override
	public void rebind(String name, Object value) throws NamingException {
		values.put(name, value);
	}

	@Override
	public void unbind(Name name) throws NamingException {
		values.remove(name.toString());
	}

	@Override
	public void unbind(String name) throws NamingException {
		values.remove(name);
	}

	@Override
	public void rename(Name oldName, Name newName) throws NamingException {
		Object value = values.remove(oldName.toString());
		values.put(newName.toString(), value);
	}

	@Override
	public void rename(String oldName, String newName) throws NamingException {
		Object value = values.remove(oldName);
		values.put(newName, value);
	}

	@Override
	public NamingEnumeration<NameClassPair> list(Name name) throws NamingException {
		return null;
	}

	@Override
	public NamingEnumeration<NameClassPair> list(String name) throws NamingException {
		return null;
	}

	@Override
	public NamingEnumeration<Binding> listBindings(Name name) throws NamingException {
		return null;
	}

	@Override
	public NamingEnumeration<Binding> listBindings(String name) throws NamingException {
		return null;
	}

	@Override
	public void destroySubcontext(Name name) throws NamingException {
		throw new OperationNotSupportedException();
	}

	@Override
	public void destroySubcontext(String name) throws NamingException {
		throw new OperationNotSupportedException();
	}

	@Override
	public Context createSubcontext(Name name) throws NamingException {
		throw new OperationNotSupportedException();
	}

	@Override
	public Context createSubcontext(String name) throws NamingException {
		throw new OperationNotSupportedException();
	}

	@Override
	public Object lookupLink(Name name) throws NamingException {
		return null;
	}

	@Override
	public Object lookupLink(String name) throws NamingException {
		return null;
	}

	@Override
	public NameParser getNameParser(Name name) throws NamingException {
		return new NameParser() {
			public Name parse(String name) throws NamingException {
				return null;
			}
		};
	}

	@Override
	public NameParser getNameParser(String name) throws NamingException {
		return new NameParser() {
			public Name parse(String name) throws NamingException {
				return new CompositeName(name);
			}
		};
	}

	@Override
	public Name composeName(Name name, Name prefix) throws NamingException {
		throw new OperationNotSupportedException();
	}

	@Override
	public String composeName(String name, String prefix) throws NamingException {
		throw new OperationNotSupportedException();
	}

	@Override
	public Object addToEnvironment(String propName, Object propVal) throws NamingException {
		return null;
	}

	@Override
	public Object removeFromEnvironment(String propName) throws NamingException {
		return null;
	}

	@Override
	public Hashtable<?, ?> getEnvironment() throws NamingException {
		return null;
	}

	@Override
	public void close() throws NamingException {
		values.clear();
	}

	@Override
	public String getNameInNamespace() throws NamingException {
		return null;
	}

}
