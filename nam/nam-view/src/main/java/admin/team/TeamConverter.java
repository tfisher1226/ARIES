package admin.team;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import admin.Team;
import admin.util.TeamUtil;


@FacesConverter(value = "teamConverter", forClass = Team.class)
public class TeamConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		TeamListManager teamListManager = BeanContext.getFromSession("teamListManager");
		Team team = teamListManager.getRecordByName(value);
		return team;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Team team = (Team) value;
		return TeamUtil.toString(team);
	}
	
}
