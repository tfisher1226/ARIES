package org.aries.ui.dialog;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.inject.Named;


@ApplicationScoped
@Named("dialogMessageStyleFactory")
public class DialogMessageStyleFactory {

	private static final Map<Severity, DialogMessageStyle> severityToStyleMapping;
	
	static {
		severityToStyleMapping = new HashMap<Severity, DialogMessageStyle>();
		severityToStyleMapping.put(FacesMessage.SEVERITY_FATAL, DialogMessageStyle.Fatal);
		severityToStyleMapping.put(FacesMessage.SEVERITY_ERROR, DialogMessageStyle.Error);
		severityToStyleMapping.put(FacesMessage.SEVERITY_INFO, DialogMessageStyle.Info);
		severityToStyleMapping.put(FacesMessage.SEVERITY_WARN, DialogMessageStyle.Warn);
	}
	
	public DialogMessageStyleFactory() {
		//nothing for now
	}
	
	public DialogMessageStyle styleForSeverity(final Severity s) {
		return severityToStyleMapping.get(s);
	}
}
