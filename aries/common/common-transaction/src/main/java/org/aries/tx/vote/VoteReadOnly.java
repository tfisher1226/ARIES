package org.aries.tx.vote;


/**
 * The inferior votes that it has done no work that requires to be involved
 * any further in the two-phase protocol. For example, it has not updated
 * any data. This can then be used by the coordinator to optimise the
 * subsequent phase of the protocol (if any).
 *
 * WARNING: this should be used with care.
 */
public class VoteReadOnly implements Vote {

	public VoteReadOnly() {
	}

	public boolean equals (Object object) {
		if (object == null)
			return false;
		if (object instanceof VoteReadOnly)
			return true;
		return false;
	}

	public String toString() {
		return "VoteReadOnly";
	}

}
