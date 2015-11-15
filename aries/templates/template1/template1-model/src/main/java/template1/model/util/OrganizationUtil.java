package template1.model.util;

import java.io.Serializable;

import org.aries.util.NameUtil;

import template1.model.Organization;
import template1.model.OrganizationLevel;


@SuppressWarnings("serial")
public class OrganizationUtil implements Serializable {

	private OrganizationUtil() {
		//prevent instantiation
	}
	
	public static OrganizationLevel getOrganizationLevel(int level) {
		if (level == OrganizationLevel.ZONE.ordinal())
            return OrganizationLevel.ZONE;
		if (level == OrganizationLevel.REGION.ordinal())
            return OrganizationLevel.REGION;
		if (level == OrganizationLevel.AREA.ordinal())
            return OrganizationLevel.AREA;
		if (level == OrganizationLevel.CHAPTER.ordinal())
            return OrganizationLevel.CHAPTER;
		if (level == OrganizationLevel.DISTRICT.ordinal())
            return OrganizationLevel.DISTRICT;
		if (level == OrganizationLevel.GROUP.ordinal())
            return OrganizationLevel.GROUP;
		return null;
	}

	public static OrganizationLevel getChildLevel(OrganizationLevel level) {
		return level != null ? getOrganizationLevel(level.ordinal() + 1) : null;
	}

	public static OrganizationLevel getParentLevel(OrganizationLevel level) {
		return level != null ? getOrganizationLevel(level.ordinal() - 1) : null;
	}


	public static String getIdFieldName(int level) {
		if (level == OrganizationLevel.ZONE.ordinal())
            return "zoneId";
		if (level == OrganizationLevel.REGION.ordinal())
            return "regionId";
		if (level == OrganizationLevel.AREA.ordinal())
            return "areaId";
		if (level == OrganizationLevel.CHAPTER.ordinal())
            return "chapterId";
		if (level == OrganizationLevel.DISTRICT.ordinal())
            return "districtId";
		if (level == OrganizationLevel.GROUP.ordinal())
            return "groupId";
		return null;
	}

	public static String getTableName(int level) {
		if (level == OrganizationLevel.ZONE.ordinal())
            return "ZoneTable";
		if (level == OrganizationLevel.REGION.ordinal())
            return "RegionTable";
		if (level == OrganizationLevel.AREA.ordinal())
            return "AreaTable";
		if (level == OrganizationLevel.CHAPTER.ordinal())
            return "ChapterTable";
		if (level == OrganizationLevel.DISTRICT.ordinal())
            return "DistrictTable";
		if (level == OrganizationLevel.GROUP.ordinal())
            return "GroupTable";
		return null;
	}

	public static String getLabel(Organization organization) {
		String level = organization.getLevel().toString();
		return organization.getName()+" "+NameUtil.capName(level.toLowerCase());
	}

	public static boolean equals(Organization organization0, Organization organization1) {
		if (organization0 == null || organization1 == null)
			return false;
		Long id0 = organization0.getId();
		Long id1 = organization1.getId();
		if (id0 == null || id1 == null)
			return organization0 == organization1;
		if (!id0.equals(id1))
			return false;
		return true;
	}

}
