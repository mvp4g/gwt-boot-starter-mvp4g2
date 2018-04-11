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

package de.gishmo.gwt.gwtbootstartermvp4g2.server.resource.generator;

import java.io.File;

import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.DataConstants;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.PresenterData;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.ViewCreationMethod;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.WidgetLibrary;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.HostPageSourceGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * HostPageSourceGenerator Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Apr 11, 2018</pre>
 */
public class HostPageSourceGeneratorTest {

  private static final String PROJECT_FOLDER = "/Users/hoss/Desktop/test/main/webapp";

  private File directory;
  private Mvp4g2GeneraterParms mvp4g2GeneraterParms = new Mvp4g2GeneraterParms();

  @Before
  public void before()
    throws Exception {
    mvp4g2GeneraterParms.setGroupId("com.example");
    mvp4g2GeneraterParms.setArtefactId("MyTestProject");
    mvp4g2GeneraterParms.setApplicationLoader(true);
    mvp4g2GeneraterParms.setDebug(true);
    mvp4g2GeneraterParms.setHistory(true);
    mvp4g2GeneraterParms.setHistoryOnStart(true);
    mvp4g2GeneraterParms.setGwtVersion(DataConstants.GWT_VERSION_2_8_2);
    mvp4g2GeneraterParms.setWidgetLibrary(WidgetLibrary.GWT);

    mvp4g2GeneraterParms.getPresenters()
                        .add(new PresenterData("search",
                                               "R2D2",
                                               false,
                                               true,
                                               ViewCreationMethod.VIEW_CREATION_METHOD_PRESENTER,
                                               false,
                                               true,
                                               true));
    mvp4g2GeneraterParms.getPresenters()
                        .add(new PresenterData("list",
                                               "C3P0",
                                               false,
                                               false,
                                               ViewCreationMethod.VIEW_CREATION_METHOD_FRAMEWORK,
                                               false,
                                               true,
                                               true));
    mvp4g2GeneraterParms.getPresenters()
                        .add(new PresenterData("detail",
                                               "BB8",
                                               false,
                                               false,
                                               ViewCreationMethod.VIEW_CREATION_METHOD_FRAMEWORK,
                                               true,
                                               true,
                                               true));

    directory = new File(HostPageSourceGeneratorTest.PROJECT_FOLDER);
    if (!directory.exists()) {
      directory.mkdirs();
    }
  }

  @After
  public void after()
    throws Exception {
  }

  /**
   * Method: generate()
   */
  @Test
  public void testGenerate()
    throws Exception {
    HostPageSourceGenerator.builder()
                           .mvp4g2GeneraterParms(this.mvp4g2GeneraterParms)
                           .directoryWebapp(directory)
                           .build()
                           .generate();
  }
}
