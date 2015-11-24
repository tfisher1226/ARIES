package admin.skin;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import admin.Skin;
import admin.util.SkinUtil;


@SessionScoped
@Named("skinIdentificationSection")
public class SkinRecord_IdentificationSection extends AbstractWizardPage<Skin> implements Serializable {
	
	private Skin skin;
	
	
	public SkinRecord_IdentificationSection() {
		setName("Identification");
		setUrl("identification");
	}
	
	
	public Skin getSkin() {
		return skin;
	}
	
	public void setSkin(Skin skin) {
		this.skin = skin;
	}
	
	@Override
	public void initialize(Skin skin) {
		setEnabled(true);
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setPopulateVisible(true);
		setPopulateEnabled(true);
		setSkin(skin);
	}
	
	@Override
	public void validate() {
		if (skin == null) {
			validator.missing("Skin");
		} else {
		}
	}
	
}