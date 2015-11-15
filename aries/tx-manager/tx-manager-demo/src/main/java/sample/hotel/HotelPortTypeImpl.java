/*
 * HotelServiceAT.java
 * Copyright (c) 2003, 2004 Arjuna Technologies Ltd
 * $Id: HotelServiceAT.java,v 1.3 2004/12/01 16:26:44 kconner Exp $
 */

package sample.hotel;

import javax.annotation.Resource;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.xml.ws.WebServiceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sample.AbstractAction;
import sample.restaurant.RestaurantClient;


@WebService(name = "HotelPortType", 
		portName = "HotelPortType",
		serviceName = "HotelService",
        wsdlLocation = "/wsdl/sample-hotel-service.wsdl",
        targetNamespace = "http://www.aries.com/demo/Hotel")
@SOAPBinding(style=SOAPBinding.Style.RPC)
@HandlerChain(file = "/jaxws-handlers-inbound.xml")
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class HotelPortTypeImpl extends AbstractAction implements HotelPortType {

	private static Log log = LogFactory.getLog(HotelPortType.class);
	
	private static final long ROOM_ID = 1L;
	
	@Resource
	private WebServiceContext webServiceContext;

	@PersistenceContext
	protected EntityManager entityManager;


    @WebMethod
    public void bookRoom(
            @WebParam(name = "numberOfPeople", partName = "numberOfPeople")
            int numberOfPeople) {
    	
		//MessageContext messageContext = webServiceContext.getMessageContext();
		//Header header = Header.getHeader(messageContext);

        //String transactionId = header.getInstanceIdentifier().getInstanceIdentifier();
        //log.info("["+transactionId+"] Request received for number of people: "+numberOfPeople);

        //HotelProcessor processor = new HotelProcessorImpl();
		//processor.bookRoom(transactionId, numberOfPeople);

		//TheatreClient.INSTANCE.bookRooms(6, 1);

		if (entityManager == null)
			entityManager = createEntityManager("hotel");
        entityManager.joinTransaction();

        HotelRoomEntity entity = getHotelRoomEntity();
        entity.setNumberOfPeople(numberOfPeople);
        
		RestaurantClient restaurantClient = new RestaurantClient("localhost", 8082);
		restaurantClient.bookSeats(6);

        //entityManager.persist(entity);
    }

	protected HotelRoomEntity getHotelRoomEntity() {
		HotelRoomEntity entity = entityManager.find(HotelRoomEntity.class, ROOM_ID);
		if (entity == null) {
			entity = new HotelRoomEntity();
			entityManager.persist(entity);
		}
		return entity;
	}

	protected static EntityManager createEntityManager(String persistenceUnit) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnit);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		return entityManager;
	}

}
