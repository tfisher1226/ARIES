package nam.model.cache;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.ui.manager.AbstractElementHelper;

import nam.model.Cache;
import nam.model.util.CacheUtil;


@SessionScoped
@Named("cacheHelper")
public class CacheHelper extends AbstractElementHelper<Cache> implements Serializable {
	
	@Override
	public boolean isEmpty(Cache cache) {
		return CacheUtil.isEmpty(cache);
	}
	
	@Override
	public String toString(Cache cache) {
		return CacheUtil.toString(cache);
	}
	
	@Override
	public String toString(Collection<Cache> cacheList) {
		return CacheUtil.toString(cacheList);
	}
	
	@Override
	public boolean validate(Cache cache) {
		return CacheUtil.validate(cache);
	}
	
	@Override
	public boolean validate(Collection<Cache> cacheList) {
		return CacheUtil.validate(cacheList);
	}
	
}
