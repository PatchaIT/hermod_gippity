package it.patcha.hermod.gpt.common.util;

import it.patcha.hermod.gpt.common.error.HermodException;
import it.patcha.hermod.gpt.common.error.UnformattedHermodException;
import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;
import jakarta.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static it.patcha.hermod.gpt.common.error.codes.ErrorType.IO01;

/** This class keeps application static utility methods with a general scope. */
public class HermodUtils {

	private HermodUtils() {}

	/**
	 * Returns the {@link ApplicationContext} from Spring Configuration class.
	 *
	 * @param clazz the Spring Configuration class.
	 * @return the ApplicationContext from given Spring Configuration class
	 */
	public static ApplicationContext getContext(Class<?> clazz) {
		return new AnnotationConfigApplicationContext(clazz);
	}

	/**
	 * Returns the {@link ConnectionFactory} from supplied URL.
	 *
	 * @param connectionFactoryUrl the URL for the ConnectionFactory.
	 * @return the ConnectionFactory from supplied URL
	 */
	public static ConnectionFactory getConnectionFactory(String connectionFactoryUrl) {
		return new ActiveMQConnectionFactory(connectionFactoryUrl);
	}

	/**
	 * Creates a connection to the {@code connectionFactory}.
	 *
	 * @param connectionFactory the {@link ConnectionFactory} you want to create a link to
	 * @return a {@link Connection} to the given {@code connectionFactory}
	 * @throws JMSException if the Jakarta Messaging provider fails to create the connection due to some internal error
	 */
	public static Connection createConnection(ConnectionFactory connectionFactory) throws JMSException {
		return connectionFactory.createConnection();
	}

	/**
	 * Creates a session for the {@code connection}.
	 *
	 * @param connection the {@link Connection} you want to create a session for
	 * @return a {@link Session} for the given {@code connection}
	 * @throws JMSException if the {@code connection} object fails to create a session
	 */
	public static Session createSession(Connection connection) throws JMSException {
		return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	}

	/**
	 * Applies the application's standard formatting
	 *   for error messages to an application exceptions.
	 *
	 * @param exception the exception for which the error message must be formatted
	 * @param clazz the class in which the exception is being generated
	 * @param message the exception message to format
	 * @param code the exception code
	 * @param cause the exception for which this exception is being generated, if any
	 * @return the wanted exception with formatted message
	 * @param <T> an application exception extending {@link HermodException}
	 */
	public static <T extends HermodException> T formatHermodException(
			T exception, Class<?> clazz, String message, String code, Throwable cause) {

		if (exception == null)
			return null;

		if (!StringUtils.isEmpty(message))
			message = String.format("[%s%s]%s : %s",
					clazz != null ? getSimpleName(clazz) : "Unknown class",
					cause != null ? " - " + getSimpleName(cause) : "",
					code != null ? " " + code : "",
					message);

		exception.setMessage(message);
		exception.setCode(code);
		exception.setCause(cause);

		return exception;
	}

	/**
	 * Returns an application Exception of the desired type,
	 *   by recycling data from the another Exception which will
	 *   also be used as {@code cause} for the new one.
	 * <p/>
	 * The message of Exception will be formatted based on the
	 *   application's standard format for error messages.
	 *
	 * @param oldException the source Exception from which we'll
	 *   recycle exception data and which will be used as cause
	 *   for the new one
	 * @param newExceptionClass the class for the Exception to be
	 *   returned
	 * @param clazz the class in which the exception is being generated
	 * @param code the exception code: if {@code null} the {@code code}
	 *   from the source Exception will be used, if any
	 * @return a new Exception of the desired type, with the same data
	 *   of the provided Exception, apart from the {@code cause}
	 *   which will be the source Exception itself, and the message
	 *   will receive the standard application's formatting
	 * @param <T> the class of the application Exception wanted as response
	 * @throws HermodException if unable to instantiate the wanted Exception type.
	 *   Note that the returned {@link HermodException} will contain the
	 *     data the wanted Exception should've contained.
	 *   Which means the same as original source Exception plus the message
	 *     formatting and the source Exception set as cause.
	 *   So that you can use this {@link HermodException} as replacement
	 *     of the wanted one.
	 */
	public static <T extends HermodException> T formatIntoHermodException(
			Exception oldException, Class<T> newExceptionClass, Class<?> clazz, String code) throws HermodException {
		return intoHermodException(oldException, newExceptionClass, clazz, code, true);
	}

	/**
	 * Returns an application Exception of the desired type,
	 *   by recycling data from the another Exception which will
	 *   also be used as {@code cause} for the new one.
	 * <p/>
	 * The message of Exception will be formatted based on the
	 *   application's standard format for error messages.
	 *
	 * @param oldException the source Exception from which we'll
	 *   recycle exception data and which will be used as cause
	 *   for the new one
	 * @param newExceptionClass the class for the Exception to be
	 *   returned
	 * @param clazz the class in which the exception is being generated
	 * @return a new Exception of the desired type, with the same data
	 *   of the provided Exception, apart from the {@code cause}
	 *   which will be the source Exception itself, and the message
	 *   will receive the standard application's formatting
	 * @param <T> the class of the application Exception wanted as response
	 * @throws HermodException if unable to instantiate the wanted Exception type.
	 *   Note that the returned {@link HermodException} will contain the
	 *     data the wanted Exception should've contained.
	 *   Which means the same as original source Exception plus the message
	 *     formatting and the source Exception set as cause.
	 *   So that you can use this {@link HermodException} as replacement
	 *     of the wanted one.
	 */
	public static <T extends HermodException> T formatIntoHermodException(
			Exception oldException, Class<T> newExceptionClass, Class<?> clazz) throws HermodException {
		return formatIntoHermodException(oldException, newExceptionClass, clazz, null);
	}

	/**
	 * Returns an application Exception of the desired type,
	 *   by recycling data from the another Exception which will
	 *   also be used as {@code cause} for the new one.
	 * <p/>
	 * The message of Exception will be untouched and
	 *   will not receive any further formatting.
	 *
	 * @param oldException the source Exception from which we'll
	 *   recycle exception data and which will be used as cause
	 *   for the new one
	 * @param newExceptionClass the class for the Exception to be
	 *   returned
	 * @param code the exception code: if {@code null} the {@code code}
	 *   from the source Exception will be used, if any
	 * @return a new Exception of the desired type, with the same data
	 *   of the provided Exception, apart from the {@code cause}
	 *   which will be the source Exception itself
	 * @param <T> the class of the application Exception wanted as response
	 * @throws HermodException if unable to instantiate the wanted Exception type.
	 *   Note that the returned {@link HermodException} will contain the
	 *     data the wanted Exception should've contained.
	 *   Which means the same as original source Exception plus the
	 *     source Exception set as cause.
	 *   So that you can use this {@link HermodException} as replacement
	 *     of the wanted one.
	 */
	public static <T extends HermodException> T includeIntoHermodException(
			Exception oldException, Class<T> newExceptionClass, String code) throws HermodException {
		return intoHermodException(oldException, newExceptionClass, null, code, false);
	}

	/**
	 * Returns an application Exception of the desired type,
	 *   by recycling data from the another Exception which will
	 *   also be used as {@code cause} for the new one.
	 * <p/>
	 * The message of Exception will be untouched and
	 *   will not receive any further formatting.
	 *
	 * @param oldException the source Exception from which we'll
	 *   recycle exception data and which will be used as cause
	 *   for the new one
	 * @param newExceptionClass the class for the Exception to be
	 *   returned
	 *   from the source Exception will be used, if any
	 * @return a new Exception of the desired type, with the same data
	 *   of the provided Exception, apart from the {@code cause}
	 *   which will be the source Exception itself
	 * @param <T> the class of the application Exception wanted as response
	 * @throws HermodException if unable to instantiate the wanted Exception type.
	 *   Note that the returned {@link HermodException} will contain the
	 *     data the wanted Exception should've contained.
	 *   Which means the same as original source Exception plus the
	 *     source Exception set as cause.
	 *   So that you can use this {@link HermodException} as replacement
	 *     of the wanted one.
	 */
	public static <T extends HermodException> T includeIntoHermodException(
			Exception oldException, Class<T> newExceptionClass) throws HermodException {
		return includeIntoHermodException(oldException, newExceptionClass, null);
	}

	/** Here there's core logic for some methods above, please refer to their javadoc. */
	private static <T extends HermodException> T intoHermodException(
			Exception oldException, Class<T> newExceptionClass, Class<?> clazz, String code, boolean format) throws HermodException {

		if (newExceptionClass == null)
			return null;

		String message = oldException.getMessage();
		if (code == null)
			code = oldException instanceof HermodException ? ((HermodException) oldException).getCode() : null;

		try {
			if (format)
				return formatHermodException(
						newExceptionClass.getConstructor().newInstance(),
						clazz, message, code, oldException);
			else {
				T newException = newExceptionClass.getConstructor().newInstance();
				newException.setMessage(message);
				newException.setCode(code);
				newException.setCause(oldException);
				return newException;
			}

		} catch (ReflectiveOperationException e) {
			if (format)
				throw formatHermodException(new HermodException(), clazz, message, code, oldException);
			else
				throw new HermodException(message, code, oldException);
		}
	}

	/**
	 * Returns the simple name of a class (or class of an instance),
	 *   without the need to use {@code MyClass.class.getSimpleName()}
	 *   or {@code myClass.getClass().getSimpleName()}.
	 *
	 * @param objectOrClass a class or an instance of a class from which
	 *   the simple name of the class have to be read
	 * @return the simple name of the class involved with the parameter
	 * @param <T> any class
	 */
	public static <T> String getSimpleName(T objectOrClass) {
		if (objectOrClass instanceof Class<?>)
			return ((Class<?>) objectOrClass).getSimpleName();
		else
			return objectOrClass.getClass().getSimpleName();
	}

	/**
	 * Returns the name of a class (or class of an instance),
	 *   without the need to use {@code MyClass.class.getName()}
	 *   or {@code myClass.getClass().getName()}.
	 *
	 * @param objectOrClass a class or an instance of a class from which
	 *   the class name have to be read
	 * @return the name of the class involved with the parameter
	 * @param <T> any class
	 */
	public static <T> String getClassName(T objectOrClass) {
		if (objectOrClass instanceof Class<?>)
			return ((Class<?>) objectOrClass).getName();
		else
			return objectOrClass.getClass().getName();
	}

	/**
	 * Reads the file and returns its content as {@code String}.
	 *
	 * @param filePath the path where the file to open is located
	 * @return the file content as {@code String}
	 * @throws UnformattedHermodException if something goes wrong
	 */
	public static String readFile(String filePath) throws UnformattedHermodException {
		return readFile(new File(filePath));
	}

	/**
	 * Reads the file and returns its content as {@code String}.
	 *
	 * @param file the File object made from the path where
	 *   the file to open is located
	 * @return the file content as {@code String}
	 * @throws UnformattedHermodException if something goes wrong
	 */
	public static String readFile(File file) throws UnformattedHermodException {
		if (file == null || StringUtils.isEmpty(file.getPath()))
			return null;

		try {
			return new String(Files.readAllBytes(file.toPath()));

		} catch (IOException e) {
			throw new UnformattedHermodException(
					String.format(IO01.getMessage(), file.getPath()), IO01.getCode(), e);
		}
	}

}
