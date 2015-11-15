package common.tx.service.participant;

import javax.annotation.Resource;
import javax.jws.HandlerChain;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Action;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.soap.Addressing;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import common.tx.model.Fault;
import common.tx.model.Notification;


@WebService(name = "ParticipantPortType", 
serviceName = "ParticipantService",
portName = "ParticipantPortType",
//wsdlLocation = "/WEB-INF/wsdl/wsat-participant-binding.wsdl",
targetNamespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@HandlerChain(file="/jaxws-handlers-server-twoway.xml")
@Addressing(required=true)
public class ParticipantPortTypeImpl implements ParticipantPortType {

	private static Log log = LogFactory.getLog(ParticipantPortTypeImpl.class);
	
	@Resource
	private WebServiceContext webServiceCtx;

	
	@Oneway
	@WebMethod(operationName = "PrepareOperation", action = "http://docs.oasis-open.org/ws-tx/wsat/2006/06/Prepare")
	@Action(input="http://docs.oasis-open.org/ws-tx/wsat/2006/06/Prepare")
	public void prepareOperation(@WebParam(name = "Prepare", targetNamespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06", partName = "parameters") Notification notification) {
		log.info("prepareOperation");
		
		//MessageContext ctx = webServiceCtx.getMessageContext();
		//final Notification prepare = notification;
		//final MAP inboundMap = AddressingHelper.inboundMap(ctx);
		//final AriesContext context = AriesContext.getCurrentContext(ctx);
		//log.info("prepareOperation: "+context.getInstanceIdentifier());

		//TaskManager.getInstance().queueTask(new Task() {
		//	public void executeTask() {
		//		ParticipantProcessor.getInstance().prepare(context, inboundMap, prepare);
		//	}
		//});
	}

	@Oneway
	@WebMethod(operationName = "CommitOperation", action = "http://docs.oasis-open.org/ws-tx/wsat/2006/06/Commit")
	@Action(input="http://docs.oasis-open.org/ws-tx/wsat/2006/06/Commit")
	public void commitOperation(@WebParam(name = "Commit", targetNamespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06", partName = "parameters") Notification notification) {
		log.info("commitOperation");

		//MessageContext ctx = webServiceCtx.getMessageContext();
		//final Notification commit = notification;
		//final MAP inboundMap = AddressingHelper.inboundMap(ctx);
		//final AriesContext context = AriesContext.getCurrentContext(ctx);

		//TaskManager.getInstance().queueTask(new Task() {
		//	public void executeTask() {
		//		ParticipantProcessor.getInstance().commit(context, inboundMap, commit) ;
		//	}
		//});
	}

	@Oneway
	@WebMethod(operationName = "RollbackOperation", action = "http://docs.oasis-open.org/ws-tx/wsat/2006/06/Rollback")
	@Action(input="http://docs.oasis-open.org/ws-tx/wsat/2006/06/Rollback")
	public void rollbackOperation(@WebParam(name = "Rollback", targetNamespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06", partName = "parameters") Notification notification) {
		log.info("rollbackOperation");

		//MessageContext ctx = webServiceCtx.getMessageContext();
		//final Notification rollback = notification;
		//final MAP inboundMap = AddressingHelper.inboundMap(ctx);
		//final AriesContext context = AriesContext.getCurrentContext(ctx);

		//TaskManager.getInstance().queueTask(new Task() {
		//	public void executeTask() {
		//		ParticipantProcessor.getInstance().rollback(context, inboundMap, rollback) ;
		//	}
		//});
	}

	@Oneway
	@WebMethod(operationName = "Fault", action = "http://docs.oasis-open.org/ws-tx/wsat/2006/06/fault")
	@Action(input="http://docs.oasis-open.org/ws-tx/wsat/2006/06/fault")
	public void fault(@WebParam(name = "Fault", targetNamespace = "http://schemas.xmlsoap.org/soap/envelope/", partName = "parameters") Fault fault) {
		log.info("fault");

		//MessageContext ctx = webServiceCtx.getMessageContext();
		//final MAP inboundMap = AddressingHelper.inboundMap(ctx);
		//final AriesContext context = AriesContext.getCurrentContext(ctx);
		//final Fault fault2 = FaultUtil.convertToFault(fault);

		//TaskManager.getInstance().queueTask(new Task() {
		//	public void executeTask() {
		//		ParticipantProcessor.getInstance().fault(context, inboundMap, fault2);
		//	}
		//});
	}
}
