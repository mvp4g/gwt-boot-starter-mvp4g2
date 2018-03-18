package de.gishmo.gwt.gwtbootstartermvp4g2.shared.model;

@SuppressWarnings("serial")
public class GeneratorException
  extends Exception {

  public GeneratorException() {
    super();
  }

  public GeneratorException(String message) {
    super(message);
  }

  public GeneratorException(String message,
                            Throwable cause) {
    super(message,
          cause);
  }

  public GeneratorException(Throwable cause) {
    super(cause);
  }

  public GeneratorException(String message,
                            Throwable cause,
                            boolean enableSuppression,
                            boolean writableStackTrace) {
    super(message,
          cause,
          enableSuppression,
          writableStackTrace);
  }
}
