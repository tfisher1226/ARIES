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
package common.tx.bridge.logging;

import com.arjuna.ats.arjuna.common.Uid;
import org.jboss.logging.Logger;

import static org.jboss.logging.Logger.Level.ERROR;
import static org.jboss.logging.Logger.Level.INFO;
import static org.jboss.logging.Logger.Level.WARN;

/**
 * i18n log messages for the txbridge module.
 * This class is autogenerated. Don't mess with it.
 *
 * @author Jonathan Halliday (jonathan.halliday@redhat.com) 2010-07
 */
public class txbridgeI18NLoggerImpl implements txbridgeI18NLogger
{
    private final Logger logger;

    txbridgeI18NLoggerImpl(Logger logger) {
        this.logger = logger;
    }

	public void error_ibdp_nosub(Throwable arg0) {
		logger.logv(ERROR, arg0, "ARJUNA-33001 Unable to get subordinate transaction id", (Object)null);
	}

	public void error_ibdp_norecovery(Uid arg0, Throwable arg1) {
		logger.logv(ERROR, arg1, "ARJUNA-33002 Unable to recover subordinate transaction id {0}", arg0);
	}

	public void warn_ibdp_aborted(String arg0, Throwable arg1) {
		logger.logv(WARN, arg1, "ARJUNA-33003 prepare on Xid={0} returning Aborted", arg0);
	}

	public void error_ibdp_commitfailed(String arg0, Throwable arg1) {
		logger.logv(ERROR, arg1, "ARJUNA-33004 commit on Xid={0} failed", arg0);
	}

	public void error_ibdp_rollbackfailed(String arg0, Throwable arg1) {
		logger.logv(ERROR, arg1, "ARJUNA-33005 rollback on Xid={0} failed", arg0);
	}

	public void info_ibrm_start() {
		logger.logv(INFO, "ARJUNA-33006 InboundBridgeRecoveryManager starting", (Object)null);
	}

	public void info_ibrm_stop() {
		logger.logv(INFO, "ARJUNA-33007 InboundBridgeRecoveryManager stopping", (Object)null);
	}

	public void error_ibrm_rollbackerr(String arg0, Throwable arg1) {
		logger.logv(ERROR, arg1, "ARJUNA-33008 problem rolling back orphaned subordinate tx {0}", arg0);
	}

	public void error_ibrm_scanerr(Throwable arg0) {
		logger.logv(ERROR, arg0, "ARJUNA-33009 Problem whilst scanning for in-doubt subordinate transactions", (Object)null);
	}

	public void warn_ibvp_preparefailed(String arg0, Throwable arg1) {
		logger.logv(WARN, arg1, "ARJUNA-33010 prepare on Xid={0} failed, setting RollbackOnly", arg0);
	}

	public void warn_ibvp_setrollbackfailed(Throwable arg0) {
		logger.logv(WARN, arg0, "ARJUNA-33011 setRollbackOnly failed", (Object)null);
	}

	public void warn_ibvp_stopfailed(String arg0, Throwable arg1) {
		logger.logv(WARN, arg1, "ARJUNA-33012 stop failed for Xid {0}", arg0);
	}

	public void info_obrm_start() {
		logger.logv(INFO, "ARJUNA-33013 OutboundBridgeRecoveryManager starting", (Object)null);
	}

	public void info_obrm_stop() {
		logger.logv(INFO, "ARJUNA-33014 OutboundBridgeRecoveryManager stopping", (Object)null);
	}

	public void warn_obs_unexpectedstatus(String arg0) {
		logger.logv(WARN, "ARJUNA-33015 unexpected Status {0}, treating as ROLLEDBACK", arg0);
	}

	public void error_obxar_unabletorecover(String arg0, Throwable arg1) {
		logger.logv(ERROR, arg1, "ARJUNA-33016 Unable to recover subordinate transaction id={0},", arg0);
	}

	public void error_obm_unabletoenlist(Throwable arg0) {
		logger.logv(ERROR, arg0, "ARJUNA-33017 Unable to enlist BridgeXAResource or register BridgeSynchronization", (Object)null);
	}
}
