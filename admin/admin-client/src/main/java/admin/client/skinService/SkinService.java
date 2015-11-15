package admin.client.skinService;

import java.util.List;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import admin.Skin;


@WebService(name = "skinService", targetNamespace = "http://admin")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface SkinService {
	
	public String ID = "admin.skinService";
	
	public List<Skin> getSkinList();
	
	public Skin getSkinById(Long id);
	
	public Skin getSkinByName(String name);
	
	public Long addSkin(Skin skin);
	
	public void saveSkin(Skin skin);
	
	public void deleteSkin(Skin skin);
	
}
