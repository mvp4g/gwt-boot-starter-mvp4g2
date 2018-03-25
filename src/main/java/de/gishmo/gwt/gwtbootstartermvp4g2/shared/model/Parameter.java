package de.gishmo.gwt.gwtbootstartermvp4g2.shared.model;

public class Parameter {

  private String name;
  private ClassNameModel type;

  public Parameter() {
    this("",
         null);
  }

  public Parameter(String name,
                   ClassNameModel type) {
    this.name = name;
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ClassNameModel getType() {
    return type;
  }

  public void setType(ClassNameModel type) {
    this.type = type;
  }
}
