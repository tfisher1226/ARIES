package org.aries.tx.vote;


/**
 * The inferior votes that is can confirm. The coordinator service
 * should ultimately inform the participant of the final outcome.
 */
public class VoteConfirm implements Vote {

	public VoteConfirm() {
	}

	public boolean equals (Object object) {
		if (object == null)
			return false;
		if (object instanceof VoteConfirm)
			return true;
		return false;
	}

	public String toString () {
		return "VoteConfirm";
	}

}
