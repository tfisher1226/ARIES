package nam.model.util;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.aries.Assert;
import org.aries.util.ReflectionUtil;


public class ListUtil {

	@SuppressWarnings("unchecked")
	public static <T> T getObjectByValue(List<?> objectList, Class<?> objectClass, String methodName, String objectValue) {
		Iterator<?> iterator = objectList.iterator();
		while (iterator.hasNext()) {
			Object object = iterator.next();
			if (object.getClass().equals(objectClass)) {
				Method method = ReflectionUtil.findMethod(objectClass, methodName);
				Assert.notNull(method, "Method not found: "+methodName);
				Object result = ReflectionUtil.invokeMethod(object, method, null);
				if (result != null && result.equals(objectValue))
					return (T) object;
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> getObjectList(Collection<? extends Serializable> objects, Class<?> objectClass) {
		List<T> list = new ArrayList<T>();
		Iterator<? extends Serializable> iterator = objects.iterator();
		while (iterator.hasNext()) {
			Serializable object = iterator.next();
			if (object == null)
				System.out.println();
			if (objectClass.isAssignableFrom(object.getClass()))
				list.add((T) object);
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Set<T> getObjectSet(Collection<? extends Serializable> objects, Class<?> objectClass) {
		Set<T> set = new LinkedHashSet<T>();
		Iterator<? extends Serializable> iterator = objects.iterator();
		while (iterator.hasNext()) {
			Serializable object = iterator.next();
			if (objectClass.isAssignableFrom(object.getClass()))
				set.add((T) object);
		}
		return set;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getObject(Collection<? extends Serializable> objects, Class<?> objectClass) {
		Iterator<? extends Serializable> iterator = objects.iterator();
		while (iterator.hasNext()) {
			Serializable object = iterator.next();
			if (objectClass.isAssignableFrom(object.getClass()))
				return (T) object;
		}
		return null;
	}
}
