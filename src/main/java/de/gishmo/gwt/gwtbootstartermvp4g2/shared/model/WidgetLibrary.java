package de.gishmo.gwt.gwtbootstartermvp4g2.shared.model;

public enum WidgetLibrary {

  ELEMENTO("use Elemento widgets"),
  GWT("use GWT widgets (will not work with J2CL / GWT 3)");

  private String text;

  WidgetLibrary(String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }
}
