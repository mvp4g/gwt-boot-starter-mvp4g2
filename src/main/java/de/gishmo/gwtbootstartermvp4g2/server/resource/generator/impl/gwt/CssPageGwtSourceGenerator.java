package de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.gwt;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.GeneratorException;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;

public class CssPageGwtSourceGenerator {

  private Mvp4g2GeneraterParms mvp4g2GeneraterParms;
  private File                 directoryResourcesStatic;

  private CssPageGwtSourceGenerator(Builder builder) {
    super();

    this.mvp4g2GeneraterParms = builder.mvp4g2GeneraterParms;
    this.directoryResourcesStatic = builder.directoryResourcesStatic;
  }

  public static Builder builder() {
    return new Builder();
  }

  public void generate()
    throws GeneratorException {
    this.generateCssFile();
  }

  private void generateCssFile()
    throws GeneratorException {

    StringBuilder sb = new StringBuilder();

    String fileContent = sb.toString();

    try {
      Files.write(Paths.get(directoryResourcesStatic.getPath() + File.separator + this.mvp4g2GeneraterParms.getArtefactId() + ".css"),
                  fileContent.getBytes());
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>" + Paths.get(directoryResourcesStatic.getPath() + this.mvp4g2GeneraterParms.getArtefactId() + ".css") + "<< -> exception: " + e.getMessage());
    }
  }

  public static class Builder {

    Mvp4g2GeneraterParms mvp4g2GeneraterParms;
    File                 directoryResourcesStatic;

    public Builder mvp4g2GeneraterParms(Mvp4g2GeneraterParms mvp4g2GeneraterParms) {
      this.mvp4g2GeneraterParms = mvp4g2GeneraterParms;
      return this;
    }

    public Builder directoryResourcesStatic(File directoryResourcesStatic) {
      this.directoryResourcesStatic = directoryResourcesStatic;
      return this;
    }

    public CssPageGwtSourceGenerator build() {
      return new CssPageGwtSourceGenerator(this);
    }
  }
}
