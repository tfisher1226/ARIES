package nam.service.src.test.java;

import nam.ProjectLevelHelper;
import nam.model.util.TypeUtil;

import org.aries.util.NameUtil;

public class FixtureUtil {

	public static String getFixtureNameFromPackageName(String packageName) {
		String localPart = NameUtil.getLocalPart(packageName);
		String camelCase = NameUtil.toCamelCase(localPart);
		return NameUtil.capName(camelCase) + "Fixture";

		//String[] parts = localPart.split("\\.");
		//if (parts.length == 1)
		//	return NameUtil.capName(NameUtil.toCamelCase(localPart)) + "Fixture";
		//StringBuffer buf = new StringBuffer();
		//for (String part : parts)
		//	buf.append(NameUtil.toCamelCase(part));
		//String fixtureName = NameUtil.capName(buf.toString());
		//return fixtureName + "Fixture";
	}
	
	public static String getFixtureNameFromType(String type) {
		String namespace = TypeUtil.getNamespace(type);
		String packageName = ProjectLevelHelper.getPackageName(namespace);
		String fixtureName = getFixtureNameFromPackageName(packageName);
		return fixtureName;
	}

}
