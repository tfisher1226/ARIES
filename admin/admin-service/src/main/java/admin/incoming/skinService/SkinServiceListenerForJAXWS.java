package admin.incoming.skinService;

import static javax.ejb.TransactionAttributeType.REQUIRED;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.transaction.TransactionSynchronizationRegistry;
import javax.xml.ws.WebServiceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.tx.module.Bootstrapper;
import org.aries.util.ExceptionUtil;

import admin.Skin;


@Remote(SkinService.class)
@Stateless(name = "SkinService")
@WebService(name = "skinService", serviceName = "skinServiceService", portName = "skinService", targetNamespace = "http://admin")
@HandlerChain(file = "/jaxws-handlers-service-oneway.xml")
public class SkinServiceListenerForJAXWS implements SkinService {
	
	private static final Log log = LogFactory.getLog(SkinServiceListenerForJAXWS.class);
	
	@Inject
	private SkinServiceHandler skinServiceHandler;
	
	@Resource
	private WebServiceContext webServiceContext;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	@Override
	@WebMethod
	@WebResult(name = "skinList")
	@TransactionAttribute(REQUIRED)
	public List<Skin> getSkinList() {
		if (!Bootstrapper.isInitialized("admin-service"))
			return null;
		
		try {
			List<Skin> skinList = skinServiceHandler.getSkinList();
			Assert.notNull(skinList, "SkinList must exist");
			return skinList;
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod
	@WebResult(name = "skin")
	@TransactionAttribute(REQUIRED)
	public Skin getSkinById(Long id) {
		if (!Bootstrapper.isInitialized("admin.service"))
			return null;
		
		try {
			Skin skin = skinServiceHandler.getSkinById(id);
			Assert.notNull(skin, "Skin must exist");
			return skin;
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod
	@WebResult(name = "skin")
	@TransactionAttribute(REQUIRED)
	public Skin getSkinByName(String name) {
		if (!Bootstrapper.isInitialized("admin.service"))
			return null;
		
		try {
			Skin skin = skinServiceHandler.getSkinByName(name);
			Assert.notNull(skin, "Skin must exist");
			return skin;
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod
	@WebResult(name = "id")
	@TransactionAttribute(REQUIRED)
	public Long addSkin(Skin skin) {
		if (!Bootstrapper.isInitialized("admin.service"))
			return null;
		
		try {
			Long id = skinServiceHandler.addSkin(skin);
			Assert.notNull(id, "Id must exist");
			return id;
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod
	@TransactionAttribute(REQUIRED)
	public void saveSkin(Skin skin) {
		if (!Bootstrapper.isInitialized("admin.service"))
			return;
		
		try {
			skinServiceHandler.saveSkin(skin);
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod
	@TransactionAttribute(REQUIRED)
	public void deleteSkin(Skin skin) {
		if (!Bootstrapper.isInitialized("admin.service"))
			return;
		
		try {
			skinServiceHandler.deleteSkin(skin);
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
