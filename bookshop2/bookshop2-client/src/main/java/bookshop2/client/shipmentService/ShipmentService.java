package bookshop2.client.shipmentService;

import java.util.List;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import bookshop2.Shipment;


@WebService(name = "shipmentService", targetNamespace = "http://bookshop2")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface ShipmentService {
	
	public String ID = "bookshop2.shipmentService";
	
	public List<Shipment> getAllShipmentRecords();
	
	public Shipment getShipmentRecordById(Long id);
	
	public List<Shipment> getShipmentRecordsByPage(int pageIndex, int pageSize);
	
	public Long addShipmentRecord(Shipment shipment);
	
	public void saveShipmentRecord(Shipment shipment);
	
	public void removeAllShipmentRecords();
	
	public void removeShipmentRecord(Shipment shipment);
	
	public void importShipmentRecords();
	
}
