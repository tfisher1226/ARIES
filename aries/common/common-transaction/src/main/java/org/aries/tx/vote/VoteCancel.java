package org.aries.tx.vote;


/**
 * The inferior votes that is has cancelled. The coordinator service
 * may inform the inferior of the final decision (hopefully to cancel
 * as well), but it need not.
 */
public class VoteCancel implements Vote {

	public VoteCancel() {
	}

	public boolean equals (Object object) {
		if (object == null)
			return false;
		if (object instanceof VoteCancel)
			return true;
		return false;
	}

	public String toString() {
		return "VoteCancel";
	}

}

