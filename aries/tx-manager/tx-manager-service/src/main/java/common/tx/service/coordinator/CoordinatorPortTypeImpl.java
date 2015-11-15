package common.tx.service.coordinator;

import javax.annotation.Resource;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.jws.HandlerChain;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Action;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.soap.Addressing;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.tx.service.coordinator.CoordinatorManager;
import org.aries.tx.util.Task;
import org.aries.tx.util.TaskManager;
import org.xmlsoap.schemas.soap.envelope.Fault;

import common.tx.CoordinationConstants;
import common.tx.handler.service.ContextFactoryMapper;
import common.tx.model.Header;
import common.tx.model.Notification;


@Startup
@Stateless
@Addressing(required=false)
@WebService(name = "CoordinatorPortType", 
		portName = "CoordinatorPortType",
		serviceName = "CoordinatorService",
		wsdlLocation = "/META-INF/wsdl/wsat-coordinator-binding.wsdl",
		targetNamespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@HandlerChain(file="/jaxws-handlers-server-twoway.xml")
public class CoordinatorPortTypeImpl implements CoordinatorPortType {

	private static Log log = LogFactory.getLog(CoordinatorPortTypeImpl.class);
	
	@Resource
	private WebServiceContext webServiceCtx;

	
	@WebMethod(operationName = "GetStatus", action = "http://docs.oasis-open.org/ws-tx/wsat/2006/06/GetStatus")
	@WebResult(name = "Status", targetNamespace = "http://docs.oasis-open.org/ws-tx/wscoor/2006/06", partName = "status")
	@Action(input="http://docs.oasis-open.org/ws-tx/wsat/2006/06/TransactionId", output="http://docs.oasis-open.org/ws-tx/wsat/2006/06/Status")
	public int getStatus(
		@WebParam(name = "TransactionId", targetNamespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06", partName = "transactionId") 
		String transactionId) {
		
		String coordinationType = CoordinationConstants.WSAT_PROTOCOL;
		ContextFactoryImple contextFactory = (ContextFactoryImple) ContextFactoryMapper.getMapper().getContextFactory(coordinationType);
		CoordinatorManager coordinatorManager = contextFactory.getCoordinatorManager();
		int status = coordinatorManager.getStatus(transactionId);
		return status;
	}

	@Oneway
	@WebMethod(operationName = "PreparedOperation", action = "http://docs.oasis-open.org/ws-tx/wsat/2006/06/Prepared")
	@Action(input="http://docs.oasis-open.org/ws-tx/wsat/2006/06/Prepared")
	public void preparedOperation(
		@WebParam(name = "Prepared", targetNamespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06", partName = "parameters") 
		Notification notification) {
		
		//boolean isSecure = false;
		MessageContext messageContext = webServiceCtx.getMessageContext();
		final Header header = Header.getHeader(messageContext);
		log.info("preparedOperation: "+header.getInstanceIdentifier());
		final Notification packet = notification;

		TaskManager.getManager().queueTask(new Task() {
			public void executeTask() {
				CoordinatorProcessorImpl.getProcessor().prepared(header, packet);
			}
		});
	}

	@Oneway
	@WebMethod(operationName = "ReadOnlyOperation", action = "http://docs.oasis-open.org/ws-tx/wsat/2006/06/ReadOnly")
	@Action(input="http://docs.oasis-open.org/ws-tx/wsat/2006/06/ReadOnly")
	public void readOnlyOperation(
		@WebParam(name = "ReadOnly", targetNamespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06", partName = "parameters") 
		Notification notification) {
		
		//boolean isSecure = false;
		MessageContext messageContext = webServiceCtx.getMessageContext();
		final Header header = Header.getHeader(messageContext);
		log.info("readOnlyOperation: "+header.getInstanceIdentifier());
		final Notification packet = notification;

		TaskManager.getManager().queueTask(new Task() {
			public void executeTask() {
				CoordinatorProcessorImpl.getProcessor().readOnly(header, packet);
			}
		});
	}

	@Oneway
	@WebMethod(operationName = "CommittedOperation", action = "http://docs.oasis-open.org/ws-tx/wsat/2006/06/Committed")
	@Action(input="http://docs.oasis-open.org/ws-tx/wsat/2006/06/Committed")
	public void committedOperation(
		@WebParam(name = "Committed", targetNamespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06", partName = "parameters") 
		Notification notification) {
		
		//boolean isSecure = false;
		MessageContext messageContext = webServiceCtx.getMessageContext();
		final Header header = Header.getHeader(messageContext);
		log.info("committedOperation: "+header.getInstanceIdentifier());
		final Notification packet = notification;

		TaskManager.getManager().queueTask(new Task() {
			public void executeTask() {
				CoordinatorProcessorImpl.getProcessor().committed(header, packet);
			}
		});
	}

	@Oneway
	@WebMethod(operationName = "AbortedOperation", action = "http://docs.oasis-open.org/ws-tx/wsat/2006/06/Aborted")
	@Action(input="http://docs.oasis-open.org/ws-tx/wsat/2006/06/Aborted")
	public void abortedOperation(
		@WebParam(name = "Aborted", targetNamespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06", partName = "parameters") 
		Notification notification) {
		
		//boolean isSecure = false;
		MessageContext messageContext = webServiceCtx.getMessageContext();
		final Header header = Header.getHeader(messageContext);
		log.info("abortedOperation: "+header.getInstanceIdentifier());
		final Notification packet = notification;

		TaskManager.getManager().queueTask(new Task() {
			public void executeTask() {
				CoordinatorProcessorImpl.getProcessor().aborted(header, packet);
			}
		});
	}

	@Oneway
	@WebMethod(operationName = "Fault", action = "http://docs.oasis-open.org/ws-tx/wsat/2006/06/fault")
	@Action(input="http://docs.oasis-open.org/ws-tx/wsat/2006/06/fault")
	public void fault(
			@WebParam(name = "Fault", targetNamespace = "http://schemas.xmlsoap.org/soap/envelope/", partName = "parameters") 
			Fault fault) {
		
		//boolean isSecure = false;
		MessageContext messageContext = webServiceCtx.getMessageContext();
		final Header header = Header.getHeader(messageContext);
		log.info("fault: "+header.getInstanceIdentifier());
		//final Fault packet = FaultUtil.convertToFault(fault);
		final Fault packet = fault;

		TaskManager.getManager().queueTask(new Task() {
			public void executeTask() {
				CoordinatorProcessorImpl.getProcessor().fault(header, packet);
			}
		});
	}

}
