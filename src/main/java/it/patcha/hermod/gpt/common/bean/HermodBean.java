package it.patcha.hermod.gpt.common.bean;

import it.patcha.hermod.gpt.common.HermodClass;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serial;
import java.io.Serializable;

/**
 * Base abstract Bean class for the application.
 * All application beans should extend this.
 * The purpose is to respect criteria of serializability and
 *   the overriding of equals, hashCode and toString methods.
 * <p/>
 * It also includes a boolean to track the before and the after.
 */
public abstract class HermodBean extends HermodClass implements Serializable {

	@Serial
	private static final long serialVersionUID = 4390675801584271118L;

	/** Should be set true if everything gone ok. */
	private boolean successful;

	public HermodBean() {
		this.successful = false;
	}

	public boolean isSuccessful() {
		return successful;
	}

	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}

	/**
	 * Overrides {@link Object#equals(Object)} with same purpose.
	 * Will adopt {@link EqualsBuilder} for implementation.
	 *
	 * @param obj the reference object with which to compare
	 * @return {@code true} if this object is the same as the obj
	 *   argument; {@code false} otherwise
	 */
	@Override
	public abstract boolean equals(Object obj);

	/**
	 * Overrides {@link Object#hashCode()} with same purpose.
	 * Will adopt {@link HashCodeBuilder} for implementation.
	 *
	 * @return a hash code value for this object
	 */
	@Override
	public abstract int hashCode();

	/**
	 * Overrides {@link Object#toString()} with same purpose.
	 * Will adopt {@link ToStringBuilder} for implementation.
	 *
	 * @return a string representation of the object
	 */
	@Override
	public abstract String toString();

}
