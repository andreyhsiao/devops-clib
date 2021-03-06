package io.hsiao.devops.clib.exception;

@SuppressWarnings("serial")
public final class Exception extends java.lang.Exception {
  public Exception() {
    super();
  }

  public Exception(final String message) {
    super(message);
  }

  public Exception(final String message, final Throwable cause) {
    super(message, cause);
  }
}
