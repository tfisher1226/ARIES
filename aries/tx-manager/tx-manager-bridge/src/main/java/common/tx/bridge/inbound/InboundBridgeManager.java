/*
 * JBoss, Home of Professional Open Source
 * Copyright 2009, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 *
 * (C) 2007, 2009 @author JBoss Inc
 */
package common.tx.bridge.inbound;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.transaction.xa.XAException;
import javax.transaction.xa.Xid;

import org.aries.tx.TransactionContext;
import org.aries.tx.TransactionContextManager;
import org.aries.tx.TransactionManager;
import org.aries.tx.TransactionManagerFactory;

import com.arjuna.ats.arjuna.common.Uid;
import com.arjuna.ats.jta.xa.XATxConverter;
import common.tx.bridge.logging.txbridgeLogger;
import common.tx.exception.AlreadyRegisteredException;
import common.tx.exception.CannotRegisterException;
import common.tx.exception.SystemException;
import common.tx.exception.UnknownTransactionException;
import common.tx.exception.WrongStateException;


/**
 * Maintains the mapping data that relates WS-AT transactions to JTA subordinate transactions and related objects.
 *
 * The mappings are scoped to the singleton instance of this class and its lifetime.
 * This poses problems where you have more than one instance (classloading, clusters)
 * or where you need crash recovery. It short, it's rather limited.
 *
 * @author jonathan.halliday@redhat.com, 2007-04-30
 */
public class InboundBridgeManager {
	
    // maps WS-AT Tx Id to InboundBridge instance.
    private static final ConcurrentMap<String, InboundBridge> inboundBridgeMappings = new ConcurrentHashMap<String, InboundBridge>();

    /**
     * Return an InboundBridge instance that maps the current Thread's WS transaction context
     * to a JTA context. Control of the latter is provided by the returned instance.
     *
     * @return an InboundBridge corresponding to the calling Thread's current WS-AT transaction context.
     * @throws WrongStateException
     * @throws UnknownTransactionException
     * @throws com.arjuna.wst.SystemException
     * @throws AlreadyRegisteredException
     */
    public static InboundBridge getInboundBridge()
            throws UnknownTransactionException, AlreadyRegisteredException, 
            	CannotRegisterException, WrongStateException, SystemException, 
            	javax.transaction.SystemException, XAException {

    	txbridgeLogger.logger.trace("InboundBridgeManager.getInboundBridge()");
    	
        //String externalTxId = UserTransactionFactory.getUserTransaction().getTransactionId();
        TransactionContext transactionContext = TransactionContextManager.getInstance().getTransactionContext();
        String externalTxId = transactionContext.getTransactionId();
        
        if (!inboundBridgeMappings.containsKey(externalTxId)) {
            createMapping(externalTxId);
        }

        return inboundBridgeMappings.get(externalTxId);
    }

    /**
     * Return an InboundBridge instance for the specified WS-AT transaction context.
     *
     * This method exists only to allow BridgeVolatileParticipant to get tx context and may go away
     * once its no longer needed for that purpose. Therefore it should probably not be relied upon.
     *
     * @deprecated
     * @param externalTxId The WS-AT tx identifier.
     * @return
     */
    static InboundBridge getInboundBridge(String externalTxId) {
        return inboundBridgeMappings.get(externalTxId);
    }

    /**
     * Remove the mapping for the given externalTxId. This should be called for gc when the tx is finished.
     *
     * @param externalTxId The WS-AT tx identifier.
     */
    public static synchronized void removeMapping(String externalTxId) {
        txbridgeLogger.logger.trace("InboundBridgeManager.removeMapping(externalTxId="+externalTxId+")");

        if (externalTxId != null) {
            inboundBridgeMappings.remove(externalTxId);
        }
    }

    /**
     * Create the JTA transaction mapping and support objects for a given WS transaction context.
     *
     * @param externalTxId The WS-AT tx identifier.
     * @throws WrongStateException
     * @throws UnknownTransactionException
     * @throws com.arjuna.wst.SystemException
     * @throws AlreadyRegisteredException
     */
    private static synchronized void createMapping(String externalTxId)
            throws XAException, UnknownTransactionException, WrongStateException, 
            AlreadyRegisteredException, CannotRegisterException, SystemException, javax.transaction.SystemException {
        txbridgeLogger.logger.trace("InboundBridgeManager.createMapping(externalTxId="+externalTxId+")");
        if (inboundBridgeMappings.containsKey(externalTxId)) {
            return;
        }

        TransactionManager transactionManager = TransactionManagerFactory.getTransactionManager();

        // Xid for driving the subordinate,
        // shared by the bridge (thread assoc) and Participant (termination via XATerminator)
        Xid xid = XATxConverter.getXid(new Uid(), false, BridgeDurableParticipant.XARESOURCE_FORMAT_ID);

        //DurableParticipant
        BridgeDurableParticipant bridgeDurableParticipant = new BridgeDurableParticipant(externalTxId, xid);
        // construct the participantId in such as way as we can recognise it at recovery time:
        String participantId = BridgeDurableParticipant.TYPE_IDENTIFIER + new Uid().toString();
        bridgeDurableParticipant.setParticipantId(participantId);
        transactionManager.enlistForDurableTwoPhase(externalTxId, bridgeDurableParticipant);

//        //VolatileParticipant
//        BridgeVolatileParticipant bridgeVolatileParticipant = new BridgeVolatileParticipant(externalTxId, xid);
//        participantId = new Uid().toString();
//        bridgeVolatileParticipant.setParticipantId(participantId);
//        transactionManager.enlistForVolatileTwoPhase(externalTxId, bridgeVolatileParticipant);

        inboundBridgeMappings.put(externalTxId, new InboundBridge(xid));
    }

}