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
package org.aries.tx.recovery.logger;

import org.jboss.logging.*;

import javax.xml.namespace.QName;

import static org.jboss.logging.Logger.Level.*;
import static org.jboss.logging.Message.Format.*;

/**
 * i18n log messages for the wst module.
 *
 * @author Jonathan Halliday (jonathan.halliday@redhat.com) 2010-06
 */
@MessageLogger(projectCode = "ARJUNA")
public interface wstI18NLogger {

    /*
        Message IDs are unique and non-recyclable.
        Don't change the purpose of existing messages.
          (tweak the message text or params for clarification if you like).
        Allocate new messages by following instructions at the bottom of the file.
     */

	@Message(id = 43216, value = "participant {0} has no saved recovery state to recover", format = MESSAGE_FORMAT)
	public String get_recovery_participant_at_ATParticipantRecoveryRecord_restoreParticipant_1(String arg0);

	@Message(id = 43217, value = "XML stream exception restoring recovery state for participant {0}", format = MESSAGE_FORMAT)
	@LogMessage(level = WARN)
	public void warn_recovery_participant_at_ATParticipantRecoveryRecord_restoreState_1(String arg0, @Cause() Throwable arg1);

	@Message(id = 43218, value = "I/O exception saving restoring state for participant {0}", format = MESSAGE_FORMAT)
	@LogMessage(level = WARN)
	public void warn_recovery_participant_at_ATParticipantRecoveryRecord_restoreState_2(String arg0, @Cause() Throwable arg1);

	@Message(id = 43219, value = "Could not save recovery state for non-serializable durable WS-AT participant {0}", format = MESSAGE_FORMAT)
	@LogMessage(level = WARN)
	public void warn_recovery_participant_at_ATParticipantRecoveryRecord_saveState_1(String arg0);

	@Message(id = 43220, value = "XML stream exception saving recovery state for participant {0}", format = MESSAGE_FORMAT)
	@LogMessage(level = WARN)
	public void warn_recovery_participant_at_ATParticipantRecoveryRecord_saveState_2(String arg0, @Cause() Throwable arg1);

	@Message(id = 43221, value = "I/O exception saving recovery state for participant {0}", format = MESSAGE_FORMAT)
	@LogMessage(level = WARN)
	public void warn_recovery_participant_at_ATParticipantRecoveryRecord_saveState_3(String arg0, @Cause() Throwable arg1);

	@Message(id = 43222, value = "participant {0} has no saved recovery state to recover", format = MESSAGE_FORMAT)
	public String get_recovery_participant_ba_BAParticipantRecoveryRecord_restoreParticipant_1(String arg0);

	@Message(id = 43223, value = "XML stream exception restoring recovery state for participant {0}", format = MESSAGE_FORMAT)
	@LogMessage(level = WARN)
	public void warn_recovery_participant_ba_BAParticipantRecoveryRecord_restoreState_1(String arg0, @Cause() Throwable arg1);

	@Message(id = 43224, value = "I/O exception saving restoring state for participant {0}", format = MESSAGE_FORMAT)
	@LogMessage(level = WARN)
	public void warn_recovery_participant_ba_BAParticipantRecoveryRecord_restoreState_2(String arg0, @Cause() Throwable arg1);

	@Message(id = 43225, value = "Could not save recovery state for non-serializable WS-BA participant {0}", format = MESSAGE_FORMAT)
	@LogMessage(level = WARN)
	public void warn_recovery_participant_ba_BAParticipantRecoveryRecord_saveState_1(String arg0);

	@Message(id = 43226, value = "XML stream exception saving recovery state for participant {0}", format = MESSAGE_FORMAT)
	@LogMessage(level = WARN)
	public void warn_recovery_participant_ba_BAParticipantRecoveryRecord_saveState_2(String arg0, @Cause() Throwable arg1);

	@Message(id = 43227, value = "I/O exception saving recovery state for participant {0}", format = MESSAGE_FORMAT)
	@LogMessage(level = WARN)
	public void warn_recovery_participant_ba_BAParticipantRecoveryRecord_saveState_3(String arg0, @Cause() Throwable arg1);

    /*
        Allocate new messages directly above this notice.
          - id: use the next id number in numeric sequence. Don't reuse ids.
          The first two digits of the id(XXyyy) denote the module
            all message in this file should have the same prefix.
          - value: default (English) version of the log message.
          - level: according to severity semantics defined at http://docspace.corp.redhat.com/docs/DOC-30217
          Debug and trace don't get i18n. Everything else MUST be i18n.
          By convention methods with String return type have prefix get_,
            all others are log methods and have prefix <level>_
     */
}
