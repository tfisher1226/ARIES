package nam;

import nam.model.Namespace;
import nam.model.Project;

import org.aries.util.NameUtil;

import aries.generation.engine.GenerationContext;


public class ProjectLevelHelper {

	public static GenerationContext context;
	
	
	public static String getPackageName(Namespace namespace) {
		return getPackageName(namespace.getUri());
	}
	
	public static String getPackageName(String namespace) {
		Project project = context.getProject();
		String domain = project != null ? project.getDomain() : null;
		String lastSegment = NameUtil.getLastSegment(namespace, "/");
		String packageName = lastSegment;
		if (domain != null && !packageName.startsWith(domain))
			packageName = domain + "." + lastSegment;
		packageName = packageName.replace("-", "_");
		return packageName;
	}
	
}
