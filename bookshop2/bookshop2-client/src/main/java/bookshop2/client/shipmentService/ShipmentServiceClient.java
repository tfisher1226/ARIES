package bookshop2.client.shipmentService;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.client.AbstractDelegate;
import org.aries.util.ExceptionUtil;

import bookshop2.Shipment;


public class ShipmentServiceClient extends AbstractDelegate implements ShipmentService {
	
	private static final Log log = LogFactory.getLog(ShipmentServiceClient.class);
	
	
	@Override
	public String getDomain() {
		return "bookshop2";
	}
	
	@Override
	public String getServiceId() {
		return ShipmentService.ID;
	}
	
	@SuppressWarnings("unchecked")
	public ShipmentService getProxy() throws Exception {
		return getProxy(ShipmentService.ID);
	}
	
	@Override
	public List<Shipment> getAllShipmentRecords() {
		try {
			List<Shipment> result = getProxy().getAllShipmentRecords();
			return result;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Shipment getShipmentRecordById(Long id) {
		try {
			Shipment result = getProxy().getShipmentRecordById(id);
			return result;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Shipment> getShipmentRecordsByPage(int pageIndex, int pageSize) {
		try {
			List<Shipment> result = getProxy().getShipmentRecordsByPage(pageIndex, pageSize);
			return result;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Long addShipmentRecord(Shipment shipment) {
		try {
			Long result = getProxy().addShipmentRecord(shipment);
			return result;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void saveShipmentRecord(Shipment shipment) {
		try {
			getProxy().saveShipmentRecord(shipment);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeAllShipmentRecords() {
		try {
			getProxy().removeAllShipmentRecords();
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeShipmentRecord(Shipment shipment) {
		try {
			getProxy().removeShipmentRecord(shipment);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void importShipmentRecords() {
		try {
			getProxy().importShipmentRecords();
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
