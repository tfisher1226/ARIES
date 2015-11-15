package nam.model.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;

import nam.model.Deployment;


public class DeploymentUtil extends BaseUtil {
	
	public static Object getKey(Deployment deployment) {
		return deployment.getDomain() + "." + deployment.getName();
	}
	
	public static String getLabel(Deployment deployment) {
		return deployment.getDomain() + "." + deployment.getName();
	}
	
	public static boolean isEmpty(Deployment deployment) {
		if (deployment == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Deployment> deploymentList) {
		if (deploymentList == null  || deploymentList.size() == 0)
			return true;
		Iterator<Deployment> iterator = deploymentList.iterator();
		while (iterator.hasNext()) {
			Deployment deployment = iterator.next();
			if (!isEmpty(deployment))
				return false;
		}
		return true;
	}
	
	public static String toString(Deployment deployment) {
		if (isEmpty(deployment))
			return "Deployment: [uninitialized] "+deployment.toString();
		String text = deployment.toString();
		return text;
	}
	
	public static String toString(Collection<Deployment> deploymentList) {
		if (isEmpty(deploymentList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Deployment> iterator = deploymentList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Deployment deployment = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(deployment);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Deployment create() {
		Deployment deployment = new Deployment();
		initialize(deployment);
		return deployment;
	}
	
	public static void initialize(Deployment deployment) {
		//nothing for now
	}
	
	public static boolean validate(Deployment deployment) {
		if (deployment == null)
			return false;
		Validator validator = Validator.getValidator();
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Deployment> deploymentList) {
		Validator validator = Validator.getValidator();
		Iterator<Deployment> iterator = deploymentList.iterator();
		while (iterator.hasNext()) {
			Deployment deployment = iterator.next();
			//TODO break or accumulate?
			validate(deployment);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Deployment> deploymentList) {
		Collections.sort(deploymentList, createDeploymentComparator());
	}
	
	public static Collection<Deployment> sortRecords(Collection<Deployment> deploymentCollection) {
		List<Deployment> list = new ArrayList<Deployment>(deploymentCollection);
		Collections.sort(list, createDeploymentComparator());
		return list;
	}
	
	public static Comparator<Deployment> createDeploymentComparator() {
		return new Comparator<Deployment>() {
			public int compare(Deployment deployment1, Deployment deployment2) {
				Object key1 = getKey(deployment1);
				Object key2 = getKey(deployment2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Deployment clone(Deployment deployment) {
		if (deployment == null)
			return null;
		Deployment clone = create();
		clone.setName(ObjectUtil.clone(deployment.getName()));
		clone.setLabel(ObjectUtil.clone(deployment.getLabel()));
		clone.setDomain(ObjectUtil.clone(deployment.getDomain()));
		clone.setVersion(ObjectUtil.clone(deployment.getVersion()));
		clone.setUrl(ObjectUtil.clone(deployment.getUrl()));
		return clone;
	}
	
}
