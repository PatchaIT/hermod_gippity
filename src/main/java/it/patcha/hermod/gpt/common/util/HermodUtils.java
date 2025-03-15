package it.patcha.hermod.gpt.common.util;

import it.patcha.hermod.gpt.common.error.HermodException;
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
import java.util.List;

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
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(connectionFactoryUrl);

		//factory.setTrustAllPackages(true); // uncomment and replace for debug purposes only
		factory.setTrustedPackages(List.of(
				"java.lang",
				"java.util",
				"it.patcha.hermod.gpt"
		));

		return factory;
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
	 * @throws HermodException if something goes wrong
	 */
	public static String readFile(String filePath) throws HermodException {
		return readFile(new File(filePath));
	}

	/**
	 * Reads the file and returns its content as {@code String}.
	 *
	 * @param file the File object made from the path where
	 *   the file to open is located
	 * @return the file content as {@code String}
	 * @throws HermodException if something goes wrong
	 */
	public static String readFile(File file) throws HermodException {
		if (file == null || StringUtils.isEmpty(file.getPath()))
			return null;

		try {
			return new String(Files.readAllBytes(file.toPath()));

		} catch (IOException e) {
			throw new HermodException(
					String.format(IO01.getMessage(), file.getPath()), IO01.getCode(), e);
		}
	}

}
