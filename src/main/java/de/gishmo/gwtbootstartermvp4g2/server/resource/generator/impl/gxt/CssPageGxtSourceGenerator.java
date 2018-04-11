/*
 * Copyright (c) 2018 - Frank Hossfeld
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 *
 */

package de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.gxt;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.GeneratorException;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;

public class CssPageGxtSourceGenerator {

  private Mvp4g2GeneraterParms mvp4g2GeneraterParms;
  private File                 directoryWebapp;

  private CssPageGxtSourceGenerator(Builder builder) {
    super();

    this.mvp4g2GeneraterParms = builder.mvp4g2GeneraterParms;
    this.directoryWebapp = builder.directoryWebapp;
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
      Files.write(Paths.get(directoryWebapp.getPath() + File.separator + this.mvp4g2GeneraterParms.getArtefactId() + ".css"),
                  fileContent.getBytes());
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>" + Paths.get(directoryWebapp.getPath() + this.mvp4g2GeneraterParms.getArtefactId() + ".css") + "<< -> exception: " + e.getMessage());
    }
  }

  public static class Builder {

    Mvp4g2GeneraterParms mvp4g2GeneraterParms;
    File                 directoryWebapp;

    public Builder mvp4g2GeneraterParms(Mvp4g2GeneraterParms mvp4g2GeneraterParms) {
      this.mvp4g2GeneraterParms = mvp4g2GeneraterParms;
      return this;
    }

    public Builder directoryResourcesStatic(File directoryWebapp) {
      this.directoryWebapp = directoryWebapp;
      return this;
    }

    public CssPageGxtSourceGenerator build() {
      return new CssPageGxtSourceGenerator(this);
    }
  }
}
