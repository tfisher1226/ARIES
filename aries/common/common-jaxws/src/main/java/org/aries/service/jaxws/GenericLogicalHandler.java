package org.aries.service.jaxws;

import javax.xml.ws.handler.LogicalHandler;
import javax.xml.ws.handler.LogicalMessageContext;


/**
 * A generic jaxws logical handler
 *
 */
public class GenericLogicalHandler<C extends LogicalMessageContext> extends GenericHandler implements LogicalHandler {
}
