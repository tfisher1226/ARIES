package admin.incoming.skinService;

import java.util.List;

import org.aries.tx.Transactional;

import admin.Skin;


public interface SkinServiceHandler extends Transactional {
	
	public List<Skin> getSkinList();
	
	public Skin getSkinById(Long id);
	
	public Skin getSkinByName(String name);
	
	public Long addSkin(Skin skin);
	
	public void saveSkin(Skin skin);
	
	public void deleteSkin(Skin skin);
	
}
