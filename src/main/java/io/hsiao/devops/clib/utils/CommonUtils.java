package io.hsiao.devops.clib.utils;

import io.hsiao.devops.clib.exception.Exception;
import io.hsiao.devops.clib.exception.RuntimeException;
import io.hsiao.devops.clib.logging.Logger;
import io.hsiao.devops.clib.logging.Logger.Level;
import io.hsiao.devops.clib.logging.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public final class CommonUtils {
  public static Properties loadProperties(final Class<?> object, final String name) throws Exception {
    if (object == null) {
      throw new RuntimeException("argument 'object' is null");
    }

    if (name == null) {
      throw new RuntimeException("argument 'name' is null");
    }

    final Properties props = new Properties();

    try (final InputStream ins = object.getResourceAsStream(name)) {
      if (ins == null) {
        final Exception exception = new Exception("failed to locate property file [" + name + "]");
        logger.log(Level.INFO, "failed to locate property file [" + name + "]", exception);
        throw exception;
      }

      props.load(ins);
    }
    catch (IOException ex) {
      final Exception exception = new Exception("failed to load properties");
      exception.initCause(ex);
      logger.log(Level.INFO, "failed to load properties [" + object + "] [" + name + "]", exception);
      throw exception;
    }

    return props;
  }

  public static String getProperty(final Properties props, final String name, final boolean allowEmpty) throws Exception {
    if (props == null) {
      throw new RuntimeException("argument 'props' is null");
    }

    if (name == null) {
      throw new RuntimeException("argument 'name' is null");
    }

    final String value = props.getProperty(name, "");

    if (value.isEmpty() && !allowEmpty) {
      final Exception exception = new Exception("failed to get property (property not found or may be empty) [" + name + "]");
      logger.log(Level.INFO, "failed to get property (property not found or may be empty) [" + name + "]", exception);
      throw exception;
    }

    return value;
  }

  public static String getSystemProperty(final String name, final boolean allowEmpty) throws Exception {
    if (name == null) {
      throw new RuntimeException("argument 'name' is null");
    }

    final String value = System.getProperty(name, "");

    if (value.isEmpty() && !allowEmpty) {
      final Exception exception = new Exception("failed to get system property (property not found or may be empty) [" + name + "]");
      logger.log(Level.INFO, "failed to get system property (property not found or may be empty) [" + name + "]", exception);
      throw exception;
    }

    return value;
  }

  public static <K, V> V getMapValue(final Map<K, V> map, final K name) throws Exception {
    if (map == null) {
      throw new RuntimeException("argument 'map' is null");
    }

    if (name == null) {
      throw new RuntimeException("argument 'name' is null");
    }

    if (!map.containsKey(name)) {
      final Exception exception = new Exception("failed to get map value (key not found) [" + name + "]");
      logger.log(Level.INFO, "failed to get map value (key not found) [" + name + "]", exception);
      throw exception;
    }

    return map.get(name);
  }

  @SuppressWarnings("unchecked")
  public static <T extends Throwable> void throwAs(final Throwable ex) throws T {
    throw (T) ex;
  }

  private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);
}
