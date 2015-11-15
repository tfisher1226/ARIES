package bookshop2.client.shipmentService;

import java.util.List;

import org.aries.message.Message;
import org.aries.message.MessageInterceptor;
import org.aries.util.ExceptionUtil;

import bookshop2.Shipment;


@SuppressWarnings("serial")
public class ShipmentServiceInterceptor extends MessageInterceptor<ShipmentService> implements ShipmentService {
	
	@Override
	public List<Shipment> getAllShipmentRecords() {
		try {
			log.info("#### [admin]: getAllShipmentRecords() sending...");
			Message request = createMessage("getAllShipmentRecords");
			Message response = getProxy().invoke(request);
		List<Shipment> result = response.getPart("result");
			return result;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Shipment getShipmentRecordById(Long id) {
		try {
			log.info("#### [admin]: getShipmentRecordById() sending...");
			Message request = createMessage("getShipmentRecordById");
			request.addPart("id", id);
			Message response = getProxy().invoke(request);
		Shipment result = response.getPart("result");
			return result;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Shipment> getShipmentRecordsByPage(int pageIndex, int pageSize) {
		try {
			log.info("#### [admin]: getShipmentRecordsByPage() sending...");
			Message request = createMessage("getShipmentRecordsByPage");
			request.addPart("pageIndex", pageIndex);
			request.addPart("pageSize", pageSize);
			Message response = getProxy().invoke(request);
		List<Shipment> result = response.getPart("result");
			return result;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Long addShipmentRecord(Shipment shipment) {
		try {
			log.info("#### [admin]: addShipmentRecord() sending...");
			Message request = createMessage("addShipmentRecord");
			request.addPart("shipment", shipment);
			Message response = getProxy().invoke(request);
		Long result = response.getPart("result");
			return result;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void saveShipmentRecord(Shipment shipment) {
		try {
			log.info("#### [admin]: saveShipmentRecord() sending...");
			Message request = createMessage("saveShipmentRecord");
			request.addPart("shipment", shipment);
			getProxy().invoke(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeAllShipmentRecords() {
		try {
			log.info("#### [admin]: removeAllShipmentRecords() sending...");
			Message request = createMessage("removeAllShipmentRecords");
			getProxy().invoke(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeShipmentRecord(Shipment shipment) {
		try {
			log.info("#### [admin]: removeShipmentRecord() sending...");
			Message request = createMessage("removeShipmentRecord");
			request.addPart("shipment", shipment);
			getProxy().invoke(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void importShipmentRecords() {
		try {
			log.info("#### [admin]: importShipmentRecords() sending...");
			Message request = createMessage("importShipmentRecords");
			getProxy().invoke(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
