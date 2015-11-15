package common.tx.service.participant;

import javax.annotation.Resource;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.jws.HandlerChain;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Action;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.soap.Addressing;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.tx.participant.ParticipantProcessor;
import org.aries.tx.util.Task;
import org.aries.tx.util.TaskManager;
import org.xmlsoap.schemas.soap.envelope.Fault;

import common.tx.model.Header;
import common.tx.model.Notification;


@Startup
@Stateless
@Addressing(required=false)
@WebService(name = "ParticipantPortType", 
		serviceName = "ParticipantService",
		portName = "ParticipantPortType",
		wsdlLocation = "/META-INF/wsdl/wsat-participant-binding.wsdl",
		targetNamespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@HandlerChain(file="/jaxws-handlers-server-twoway.xml")
public class ParticipantPortTypeImpl implements ParticipantPortType {

	private static Log log = LogFactory.getLog(ParticipantPortTypeImpl.class);
	
	@Resource
	private WebServiceContext webServiceCtx;
	
	@Oneway
	@WebMethod(operationName = "PrepareOperation", action = "http://docs.oasis-open.org/ws-tx/wsat/2006/06/Prepare")
	@Action(input="http://docs.oasis-open.org/ws-tx/wsat/2006/06/Prepare")
	public void prepareOperation(
		@WebParam(name = "Prepare", targetNamespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06", partName = "parameters") Notification notification) {
		
		//boolean isSecure = false;
		log.info("prepareOperation");
		final Notification prepare = notification;
		MessageContext messageContext = webServiceCtx.getMessageContext();
		final Header header = Header.getHeader(messageContext);
		log.info("prepareOperation: "+header.getInstanceIdentifier());

        //BasicAction current = BasicAction.Current();
        //System.out.println("###################################"+current);

		TaskManager.getManager().queueTask(new Task() {
			public void executeTask() {
				ParticipantProcessor.getProcessor().prepare(header, prepare);
			}
		});
	}

	@Oneway
	@WebMethod(operationName = "CommitOperation", action = "http://docs.oasis-open.org/ws-tx/wsat/2006/06/Commit")
	@Action(input="http://docs.oasis-open.org/ws-tx/wsat/2006/06/Commit")
	public void commitOperation(
		@WebParam(name = "Commit", targetNamespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06", partName = "parameters") Notification notification) {
		
		log.info("commitOperation");
		final Notification commit = notification;
		MessageContext messageContext = webServiceCtx.getMessageContext();
		//final MAP inboundMap = AddressingHelper.inboundMap(ctx);
		final Header header = Header.getHeader(messageContext);

		TaskManager.getManager().queueTask(new Task() {
			public void executeTask() {
				ParticipantProcessor.getProcessor().commit(header, commit) ;
			}
		});
	}

	@Oneway
	@WebMethod(operationName = "RollbackOperation", action = "http://docs.oasis-open.org/ws-tx/wsat/2006/06/Rollback")
	@Action(input="http://docs.oasis-open.org/ws-tx/wsat/2006/06/Rollback")
	public void rollbackOperation(
		@WebParam(name = "Rollback", targetNamespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06", partName = "parameters") Notification notification) {
		
		log.info("rollbackOperation");
		final Notification rollback = notification;
		MessageContext messageContext = webServiceCtx.getMessageContext();
		//final MAP inboundMap = AddressingHelper.inboundMap(ctx);
		final Header header = Header.getHeader(messageContext);

		TaskManager.getManager().queueTask(new Task() {
			public void executeTask() {
				ParticipantProcessor.getProcessor().rollback(header, rollback) ;
			}
		});
	}

    @Oneway
    @WebMethod(operationName = "Fault", action = "http://docs.oasis-open.org/ws-tx/wsat/2006/06/fault")
    @Action(input="http://docs.oasis-open.org/ws-tx/wsat/2006/06/fault")
    public void fault(
            @WebParam(name = "Fault", targetNamespace = "http://schemas.xmlsoap.org/soap/envelope/", partName = "parameters") Fault fault) {

		log.info("fault");
		final Fault reference = fault;
		MessageContext messageContext = webServiceCtx.getMessageContext();
		//final MAP inboundMap = AddressingHelper.inboundMap(ctx);
		final Header header = Header.getHeader(messageContext);

		TaskManager.getManager().queueTask(new Task() {
			public void executeTask() {
				ParticipantProcessor.getProcessor().fault(header, reference) ;
			}
		});
	}
}
