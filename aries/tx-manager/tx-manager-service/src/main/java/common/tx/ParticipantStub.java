package common.tx;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.wsaddressing.W3CEndpointReference;

import org.aries.tx.Participant;
import org.aries.tx.State;
import org.aries.tx.util.SOAPUtil;

import com.arjuna.ats.arjuna.state.InputObjectState;
import com.arjuna.ats.arjuna.state.OutputObjectState;
import common.tx.exception.SystemCommunicationException;
import common.tx.exception.SystemException;
import common.tx.exception.WrongStateException;
import common.tx.logging.WSTLogger;
import common.tx.service.coordinator.CoordinatorEngineImpl;
import common.tx.service.coordinator.CoordinatorProcessor;
import common.tx.util.StreamHelper;
import common.tx.vote.Aborted;
import common.tx.vote.Prepared;
import common.tx.vote.ReadOnly;
import common.tx.vote.Vote;


public class ParticipantStub implements Participant {

	private static final QName QNAME_TWO_PC_PARTICIPANT = new QName("twoPCParticipant");

	private String transactionId;
	
	private String coordinatorId;

	private String participantId;

	private CoordinatorEngineImpl coordinatorEngine;


	public ParticipantStub(String transactionId, String coordinatorId, String participantId, boolean durable, W3CEndpointReference participantReference) throws Exception {
		// id will be supplied as null during recovery in which case we can delay creation
		// of the coordinator until restore_state is called
		this.transactionId = transactionId;
		this.coordinatorId = coordinatorId;
		this.participantId = participantId;
		if (coordinatorId != null) {
			coordinatorEngine = new CoordinatorEngineImpl(transactionId, coordinatorId, participantId, durable, participantReference);
		}
	}

	public String getParticipantId() {
		return participantId;
	}

	public String getCoordinatorId() {
		return coordinatorId;
	}

	public Vote prepare() throws WrongStateException, SystemException {
		/*
		 * null - aborted or read only
		 * Active - illegal state
		 * Preparing - no answer
		 * Prepared - illegal state
		 * PreparedSuccess - prepared
		 * Committing - illegal state
		 * Aborting - aborting
		 */
		State state = coordinatorEngine.prepare();
		if (state == State.STATE_PREPARED_SUCCESS) {
			return new Prepared();
		} else if (state == State.STATE_ABORTING) {
			return new Aborted();
		} else if (state == null) {
			if (coordinatorEngine.isReadOnly()) {
				return new ReadOnly();
			} else {
				return new Aborted();
			}
		} else if (state == State.STATE_PREPARING) {
			// typically means no response from the remote end.
			// throw a comm exception to distinguish this case from the
			// one where the remote end itself threw a SystemException.

			throw new SystemCommunicationException();
		}
		else
		{
			throw new WrongStateException();
		}
	}

	public void commit()
	throws WrongStateException, SystemException
	{
		/*
		 * null - committed
		 * Active - illegal state
		 * Preparing - illegal state
		 * Prepared - illegal state
		 * PreparedSuccess - illegal state
		 * Committing - no response
		 * Aborting - illegal state
		 */
		final State state = coordinatorEngine.commit();
		if (state != null)
		{
			if (state == State.STATE_COMMITTING)
			{
				// typically means no response from the remote end.
				// throw a comm exception to distinguish this case from the
				// one where the remote end itself threw a SystemException.
				throw new SystemCommunicationException();
			}
			else
			{
				throw new WrongStateException();
			}
		}
	}

	public void rollback() throws WrongStateException, SystemException {
		/*
		 * null - aborted
		 * Active - illegal state
		 * Preparing - illegal state
		 * Prepared - illegal state
		 * PreparedSuccess - illegal state
		 * Committing - illegal state
		 * Aborting - no response
		 */
		final State state = coordinatorEngine.rollback();
		if (state != null) {
			if (state == State.STATE_ABORTING)
				throw new SystemCommunicationException();
			throw new WrongStateException();
		}
	}

	public boolean saveState(OutputObjectState oos) {
		try {
			oos.packString(coordinatorEngine.getCoordinatorId());
			oos.packBoolean(coordinatorEngine.isDurable());
			State state = coordinatorEngine.getState();
			
			// participants in the heuristic list may get saved in any state
			if (state == State.STATE_ACTIVE) {
				oos.packInt(0);
			} else if (state == State.STATE_PREPARING) {
				oos.packInt(1);
			} else if (state == State.STATE_PREPARED ||
					state == State.STATE_PREPARED_SUCCESS) {
				oos.packInt(2);
			} else if (state == State.STATE_ABORTING) {
				oos.packInt(3);
			} else { // COMMITTING or none
				oos.packInt(4);
			}

			// n.b. just use toString() for the endpoint -- it uses the writeTo() method which calls a suitable marshaller
			final StringWriter sw = new StringWriter();
			final XMLStreamWriter writer = SOAPUtil.getXMLStreamWriter(sw);
			StreamHelper.writeStartElement(writer, QNAME_TWO_PC_PARTICIPANT);
			String eprefText = coordinatorEngine.getParticipant().toString();
			writer.writeCData(eprefText);
			StreamHelper.writeEndElement(writer, null, null);
			writer.close();
			sw.close();

			String tmp = writer.toString();
			String swString = sw.toString();
			oos.packString(swString);
			return true;
			
		} catch (final Throwable th) {
			WSTLogger.i18NLogger.error_wst11_stub_ParticipantStub_1(th);
			return false;
		}
	}

	public boolean restoreState(final InputObjectState ios) {
		State state;
		
		try {
			String coordinatorId = ios.unpackString();
			boolean durable = ios.unpackBoolean();
			int stateTag = ios.unpackInt();
			switch (stateTag) {
			case 0:
				state = State.STATE_ACTIVE;
				break;
			case 1:
				state = State.STATE_PREPARING;
				break;
			case 2:
				state = State.STATE_PREPARED_SUCCESS;
				break;
			case 3:
				state = State.STATE_ABORTING;
				break;
			default:
				state = State.STATE_COMMITTING;
				break;
			}
			
			final String eprValue = ios.unpackString();

			// this should successfully reverse the save process
			final XMLStreamReader reader = SOAPUtil.getXMLStreamReader(new StringReader(eprValue));
			StreamHelper.checkNextStartTag(reader, QNAME_TWO_PC_PARTICIPANT);
			String eprefText = reader.getElementText();
			StreamSource source = new StreamSource(new StringReader(eprefText));
			final W3CEndpointReference endpointReference = new W3CEndpointReference(source);
			// if we already have a coordinator from a previous recovery scan or because
			// we had a heuristic outcoe then reuse it with luck it will have been committed
			// or aborted between the last scan and this one
			// note that whatever happens it will not have been removed from the table
			// because it is marked as recovered
			coordinatorEngine = (CoordinatorEngineImpl) CoordinatorProcessor.getProcessor().getCoordinator(coordinatorId);
			if (coordinatorEngine == null) {
				// no entry found so recreate one with the saved state
				coordinatorEngine = new CoordinatorEngineImpl(transactionId, coordinatorId, participantId, durable, endpointReference, true, state);
			}
			
			return true;
			
		} catch (Throwable th) {
			WSTLogger.i18NLogger.error_wst11_stub_ParticipantStub_2(th);
			return false;
		}
	}

	public void unknown() throws SystemException {
		error();
	}

	public void error() throws SystemException {
		try {
			rollback();
		} catch (WrongStateException wse) {
			//ignore for now
		} 
	}

}
