package de.gishmo.gwt.gwtbootstartermvp4g2.shared.model;

public class Parameter {

  private String name;
  private String type;

  public Parameter() {
    this("",
         null);
  }

  public Parameter(String name,
                   String type) {
    this.name = name;
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
