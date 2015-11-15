/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc. and/or its affiliates,
 * and individual contributors as indicated by the @author tags.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 *
 * (C) 2010,
 * @author JBoss, by Red Hat.
 */
package common.tx.bridge.inbound.service;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.transaction.TransactionManager;

import org.jboss.logging.Logger;

import common.tx.bridge.inbound.utility.TestSynchronization;
import common.tx.bridge.inbound.utility.TestXAResource;


/**
 * Implementation of a web service used by txbridge test cases.
 *
 * @author Jonathan Halliday (jonathan.halliday@redhat.com) 2010-01
 */
@Stateless
@WebService(targetNamespace = "http://client.inbound.tests.txbridge.jbossts.jboss.org/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
@HandlerChain(file = "/jaxws-handlers-server.xml") // relative path from the class file
@TransactionAttribute(TransactionAttributeType.MANDATORY) // default is REQUIRED
public class TestServiceImpl {
	
    private static Logger log = Logger.getLogger(TestServiceImpl.class);

    @WebMethod
    public void doNothing() {
        log.trace("doNothing()");
    }

    @WebMethod(exclude = true)
    public void enlistSynchronization(int count) {
        TransactionManager tm = com.arjuna.ats.jta.TransactionManager.transactionManager();
        try {
            for (int i = 0; i < count; i++) {
                TestSynchronization testSynchronization = new TestSynchronization();
                tm.getTransaction().registerSynchronization(testSynchronization);
            }
        } catch (Exception e) {
            log.error("could not enlist", e);
        }
    }

    @WebMethod(exclude = true)
    public void enlistXAResource(int count) {
        TransactionManager tm = com.arjuna.ats.jta.TransactionManager.transactionManager();
        try {
            for (int i = 0; i < count; i++) {
                TestXAResource testXAResource = new TestXAResource();
                tm.getTransaction().enlistResource(testXAResource);
            }
        } catch (Exception e) {
            log.error("could not enlist", e);
        }
    }
}