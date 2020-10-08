package org.epsilonlabs.modelflow.execution.control;

public enum MemoryUnit {
	
	BYTES, KB, MB, GB, TB, PB, EB, ZB, YB;

	public long convert(long amount, MemoryUnit from) {
		long result = amount;
		long factor = 1024l;
		int diff = from.compareTo(this);

		if (diff > 0) { // from > to
			for (; diff != 0; diff--) {
				result *= factor;
			}
		} else if (diff < 0) { // from < to
			for (; diff != 0; diff++) {
				result /= factor;
			}

		}

		return result;
	}
}