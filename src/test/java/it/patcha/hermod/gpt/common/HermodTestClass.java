package it.patcha.hermod.gpt.common;

import it.patcha.hermod.gpt.common.util.HermodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

/**
 * Base abstract class for all application test classes.
 * The purpose is to initialize here all common attributes,
 *   or to declare here all common non-static methods.
 */
public abstract class HermodTestClass {
	protected Logger logger;

	/**
	 * Contains {@link System#lineSeparator()},
	 *   but in a shorter name variable.
	 *
	 * @see System
	 */
	public static final String NL = System.lineSeparator();

	/** Initialize the logger for instances of classes extending this abstract. */
	protected HermodTestClass() {
		this.logger = LoggerFactory.getLogger(this.getClass());
	}

	/**
	 * Returns the simple name of a class (or class of an instance),
	 *   without the need to use {@code MyClass.class.getSimpleName()}
	 *   or {@code myClass.getClass().getSimpleName()}.
	 * <p/>
	 * Note: it's not a static method, to not have to declare the
	 *   class each time it's being used, i.e: {@link HermodUtils#getSimpleName(Object)}
	 *   which is used.
	 * Instead, it is supposed to be inherited by application classes
	 *   extending this one and used directly into its code, i.e.:
	 *   {@code getClassName(MyClass.class)}.
	 *
	 * @param objectOrClass a class or an instance of a class from which
	 *   the simple name of the class have to be read
	 * @return the simple name of the class involved with the parameter
	 * @param <T> any class
	 */
	public <T> String getSimpleName(T objectOrClass) {
		return HermodUtils.getSimpleName(objectOrClass);
	}

	/**
	 * Returns the simple name of the class of this instance,
	 *   without the need to use {@code this.getClass().getSimpleName()}.
	 *
	 * @return the simple name of this instance's class
	 */
	public String getSimpleName() {
		return getSimpleName(this);
	}

	/**
	 * Returns the name of a class (or class of an instance),
	 *   without the need to use {@code MyClass.class.getName()}
	 *   or {@code myClass.getClass().getName()}.
	 * <p/>
	 * Note: it's not a static method, to not have to declare the
	 *   class each time it's being used, i.e: {@link HermodUtils#getClassName(Object)}
	 *   which is used.
	 * Instead, it is supposed to be inherited by application classes
	 *   extending this one and used directly into its code, i.e.:
	 *   {@code getClassName(MyClass.class)}.
	 *
	 * @param objectOrClass a class or an instance of a class from which
	 *   the class name have to be read
	 * @return the name of the class involved with the parameter
	 * @param <T> any class
	 */
	public <T> String getClassName(T objectOrClass) {
		return HermodUtils.getClassName(objectOrClass);
	}

	/**
	 * Returns the name of the class of this instance,
	 *   without the need to use {@code this.getClass().getName()}.
	 *
	 * @return the name of this instance's class
	 */
	public String getClassName() {
		return getClassName(this);
	}

	/**
	 * Concat {@code message.toString()} with {@code actual.toString()},
	 *   returning {@code message} + {@code actual}.
	 * </p>
	 * If {@code message} is {@code empty},
	 *   then {@code null} will be returned.
	 *
	 * @param concat what you want to concatenate to {@code message}
	 * @param message the message after which {@code actual} have to be concatenated
	 * @return {@code message} + {@code actual}, if {@code message} is not {@code empty},
	 *   otherwise {@code null}
	 * @param <T> the type of {@code actual},
	 *   {@code actual.toString()} will be concatenated
	 */
	protected <T, U> String concatToMessage(T concat, U message) {
		return !ObjectUtils.isEmpty(message) ?
				message.toString() + (
						!ObjectUtils.isEmpty(concat) ? concat : "") :
				"";
	}

}
