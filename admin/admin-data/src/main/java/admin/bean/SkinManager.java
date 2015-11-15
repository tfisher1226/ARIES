package admin.bean;

import java.util.List;

import admin.Skin;


public interface SkinManager {
	
	public void initialize();
	
	public void clearContext();
	
	public List<Skin> getAllSkinRecords();
	
	public Skin getSkinRecordById(Long id);
	
	public Skin getSkinRecordByName(String name);
	
	public List<Skin> getSkinRecordsByPage(int pageIndex, int pageSize);
	
	public Long addSkinRecord(Skin skin);
	
	public void saveSkinRecord(Skin skin);
	
	public void removeAllSkinRecords();
	
	public void removeSkinRecord(Skin skin);
	
	public void removeSkinRecord(Long id);
	
	public void importSkinRecords();
	
}
