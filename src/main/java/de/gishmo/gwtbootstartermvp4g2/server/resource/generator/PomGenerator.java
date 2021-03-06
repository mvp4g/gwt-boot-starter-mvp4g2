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

package de.gishmo.gwtbootstartermvp4g2.server.resource.generator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.GeneratorException;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.WidgetLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PomGenerator {

  private static final Logger logger = LoggerFactory.getLogger(PomGenerator.class);

  private Mvp4g2GeneraterParms mvp4g2GeneraterParms;

  private String               projectFolder;

  private PomGenerator(Builder builder) {
    super();

    this.mvp4g2GeneraterParms = builder.mvp4g2GeneraterParms;
    this.projectFolder = builder.projectFolder;
  }

  public static Builder builder() {
    return new Builder();
  }

  public void generate()
      throws GeneratorException {

    logger.debug(">>" + mvp4g2GeneraterParms.getArtefactId() + "<< start generating pom");

    StringBuilder sb = new StringBuilder();

    sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.COPYRIGHT_HTML)
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK)
      .append("<project xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                  "         xmlns=\"http://maven.apache.org/POM/4.0.0\"\n" +
                  "         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">")
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK);

    sb.append(this.generateHeadlines())
      .append(this.generateProperties())
      .append(this.generateDependencyManagement())
      .append(this.generateDependencies())
      .append(this.generateRepositories());

    sb.append("  <build>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(this.generateOutputDirectory())
      .append(GeneratorConstants.LINE_BREAK)
      .append(this.generateResources())
      .append("    <plugins>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(this.generateCompilerPlugin())
      .append(this.geenrateBuildHelperPlugin())
      .append(this.generateGwtPlugin())
      .append(this.generateWarPlugin())
      .append("    </plugins>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("    <pluginManagement>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      <plugins>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(this.generateEcliseM2E())
      .append("      </plugins>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("    </pluginManagement>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("  </build>")
      .append(GeneratorConstants.LINE_BREAK);

    sb.append("</project>");

    String pomContent = sb.toString();

    try {
      Files.write(Paths.get(new File(this.projectFolder) + File.separator + "pom.xml"),
                  pomContent.getBytes());
    } catch (IOException e) {
      logger.debug(">>" + mvp4g2GeneraterParms.getArtefactId() + "<< problems writing file",
                   e);
      throw new GeneratorException("Unable to write generated file: >>" + new File(this.projectFolder) + File.separator + "pom.xml" + "<< -> exception: " + e.getMessage());
    }

    logger.debug(">>" + mvp4g2GeneraterParms.getArtefactId() + "<< generating pom sucessfully finished");
  }

  private String generateEcliseM2E() {
    StringBuilder sb = new StringBuilder();

    sb.append("        <plugin>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(this.addAddrLine(10,
                               "groupId",
                               "org.eclipse.m2e"))
      .append(this.addAddrLine(10,
                               "artifactId",
                               "lifecycle-mapping"))
      .append(this.addAddrLine(10,
                               "version",
                               "${plugin.version.eclipse.lifecyle}"))
      .append("          <configuration>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("            <lifecycleMappingMetadata>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("              <pluginExecutions>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("                <pluginExecution>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("                  <pluginExecutionFilter>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(this.addAddrLine(20,
                               "groupId",
                               "org.apache.maven.plugins"))
      .append(this.addAddrLine(20,
                               "artifactId",
                               "maven-war-plugin"))
      .append(this.addAddrLine(20,
                               "versionRange",
                               "[3.0.0,)"))
      .append("                    <goals>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("                      <goal>exploded</goal>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("                    </goals>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("                  </pluginExecutionFilter>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("                  <action>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("                    <execute>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(this.addAddrLine(22,
                               "runOnConfiguration",
                               "true"))
      .append(this.addAddrLine(22,
                               "runOnIncremental",
                               "true"))
      .append("                    </execute>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("                  </action>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("                </pluginExecution>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("              </pluginExecutions>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("            </lifecycleMappingMetadata>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("          </configuration>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("        </plugin>")
      .append(GeneratorConstants.LINE_BREAK);

    return sb.toString();
  }

  private String generateResources() {
    StringBuilder sb = new StringBuilder();

    sb.append("    <resources>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      <resource>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("        <directory>src/main/java</directory>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("          <includes>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("            <include>**</include>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("          </includes>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      </resource>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("    </resources>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK);

    return sb.toString();
  }

  private String generateOutputDirectory() {
    StringBuilder sb = new StringBuilder();

    sb.append(addCommentLine(4,
                             "Compiled Classes"))
      .append("    <outputDirectory>${webappDirectory}/WEB-INF/classes</outputDirectory>")
      .append(GeneratorConstants.LINE_BREAK);

    return sb.toString();
  }

  private String generateRepositories() {
    StringBuilder sb = new StringBuilder();

    if (WidgetLibrary.GXT == this.mvp4g2GeneraterParms.getWidgetLibrary()) {
      sb.append("  <repositories>")
        .append(GeneratorConstants.LINE_BREAK)
        .append("    <repository>")
        .append(GeneratorConstants.LINE_BREAK)
        .append("      <id>sencha-gxt-repository</id>")
        .append(GeneratorConstants.LINE_BREAK)
        .append("      <name>sencha-gxt-repository</name>")
        .append(GeneratorConstants.LINE_BREAK)
        .append("      <!-- GPL -->")
        .append(GeneratorConstants.LINE_BREAK)
        .append("      <url>https://maven.sencha.com/repo/gxt-gpl-release</url>")
        .append(GeneratorConstants.LINE_BREAK)
        .append("    </repository>")
        .append(GeneratorConstants.LINE_BREAK)
        .append("  </repositories>")
        .append(GeneratorConstants.LINE_BREAK);
    }

    return sb.toString();
  }

  private String generateGwtPlugin() {
    StringBuilder sb = new StringBuilder();

    sb.append("      <plugin>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(this.addAddrLine(8,
                               "groupId",
                               "net.ltgt.gwt.maven"))
      .append(this.addAddrLine(8,
                               "artifactId",
                               "gwt-maven-plugin"))
      .append(this.addAddrLine(8,
                               "version",
                               "1.0-rc-9"))
      .append(this.addAddrLine(8,
                               "extensions",
                               "true"))
      //      .append("        <executions>")
      //      .append(GeneratorConstants.LINE_BREAK)
      //      .append("          <execution>")
      //      .append(GeneratorConstants.LINE_BREAK)
      //      .append("            <goals>")
      //      .append(GeneratorConstants.LINE_BREAK)
      //      .append(this.addAddrLine(14,
      //                               "goal",
      //                               "compile"))
      //      .append(this.addAddrLine(14,
      //                               "goal",
      //                               "package-app"))
      //      .append("            </goals>")
      //      .append(GeneratorConstants.LINE_BREAK)
      //      .append("          </execution>")
      //      .append(GeneratorConstants.LINE_BREAK)
      //      .append("        </executions>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("        <configuration>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(this.addAddrLine(10,
                               "classpathScope",
                               "compile"))
      .append(this.addAddrLine(10,
                               "moduleName",
                               this.mvp4g2GeneraterParms.getGroupId() +
                                   "." +
                                   GeneratorUtils.removeBadChracters(this.mvp4g2GeneraterParms.getArtefactId())
                                                 .toLowerCase() +
                                   "." +
                                   GeneratorUtils.setFirstCharacterToUpperCase(this.mvp4g2GeneraterParms.getArtefactId())))
      .append(this.addAddrLine(10,
                               "moduleShortName",
                               GeneratorUtils.removeBadChracters(this.mvp4g2GeneraterParms.getArtefactId())))
      //      .append(this.addAddrLine(10,
      //                               "modules",
      //                               this.mvp4g2GeneraterParms.getGroupId() + "." + this.mvp4g2GeneraterParms.getArtefactId()
      //                                                                                                       .toLowerCase() + "." + GeneratorUtils.setFirstCharacterToUpperCase(this.mvp4g2GeneraterParms.getArtefactId())))
      .append(this.addAddrLine(10,
                               "failOnError",
                               "true"))
      .append(this.addAddrLine(10,
                               "sourceLevel",
                               "${maven.compiler.source}"))
      .append(this.addAddrLine(10,
                               "logLevel",
                               "TRACE"))
      //      .append("          <compilerArgs>")
      //      .append(GeneratorConstants.LINE_BREAK)
      //      .append(this.addAddrLine(12,
      //                               "arg",
      //                               "-compileReport"))
      //      .append(this.addAddrLine(12,
      //                               "arg",
      //                               "-XcompilerMetrics"))
      //      .append(this.addAddrLine(12,
      //                               "arg",
      //                               "-setProperty"))
      //      .append(this.addAddrLine(12,
      //                               "arg",
      //                               "mvp4g2.logging=true"))
      //      .append("          </compilerArgs>")
      //      .append(GeneratorConstants.LINE_BREAK)
      //      .append(this.addAddrLine(10,
      //                               "skipModule",
      //                               "true"))
      //      .append(this.addAddrLine(10,
      //                               "codeServerPort",
      //                               "9876"))
      //      .append("          <devmodeArgs>")
      //      .append(GeneratorConstants.LINE_BREAK)
      //      .append(this.addAddrLine(12,
      //                               "arg",
      //                               "-port"))
      //      .append(this.addAddrLine(12,
      //                               "arg",
      //                               "8888"))
      //      .append(this.addAddrLine(12,
      //                               "arg",
      //                               "-codeServerPort"))
      //      .append(this.addAddrLine(12,
      //                               "arg",
      //                               "9876"))
      //      .append("          </devmodeArgs>")
      .append("          <startupUrls>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(this.addAddrLine(12,
                               "startupUrl",
                               GeneratorUtils.setFirstCharacterToUpperCase(this.mvp4g2GeneraterParms.getArtefactId()) + ".html"))
      .append("          </startupUrls>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(this.addAddrLine(8,
                               "devmodeWorkDir",
                               "${project.build.directory}/devModeWorkDir"))
      .append(this.addAddrLine(8,
                               "launcherDir",
                               "${project.build.directory}/${project.build.finalName}"))
      .append(this.addAddrLine(8,
                               "warDir",
                               "${project.build.directory}/${project.build.finalName}"))
      //      .append(this.addAddrLine(8,
      //                               "webappDirectory",
      //                               "${project.build.directory}/${project.build.finalName}"))
      //      .append("          <systemProperties>")
      //      .append(GeneratorConstants.LINE_BREAK)
      //      .append(this.addAddrLine(12,
      //                               "gwt.persistentunitcachedir",
      //                               "${project.build.directory}/gwt/unitCache/"))
      //      .append(this.addAddrLine(12,
      //                               "gwt.war.directory",
      //                               "${project.basedir}/${project.build.finalName}"))
      //      .append("          </systemProperties>")
      //      .append(GeneratorConstants.LINE_BREAK)
      .append("          <jvmArgs>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("            <arg>-Xms1G</arg>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("            <arg>-Xmx2G</arg>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("          </jvmArgs>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("        </configuration>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      </plugin>")
      .append(GeneratorConstants.LINE_BREAK);

    return sb.toString();
  }

  private String addAddrLine(int indent,
                             String tagName,
                             String value) {
    StringBuilder sb = new StringBuilder();

    sb.append(this.addIndent(indent))
      .append("<")
      .append(tagName)
      .append(">")
      .append(value)
      .append("</")
      .append(tagName)
      .append(">")
      .append(GeneratorConstants.LINE_BREAK);

    return sb.toString();
  }

  private String addIndent(int indent) {
    StringBuilder s = new StringBuilder();
    for (int i = 0; i < indent; i++) {
      s.append(" ");
    }
    return s.toString();
  }

  private String generateWarPlugin() {
    StringBuilder sb = new StringBuilder();

    sb.append("      <plugin>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(this.addAddrLine(8,
                               "groupId",
                               "org.apache.maven.plugins"))
      .append(this.addAddrLine(8,
                               "artifactId",
                               "maven-war-plugin"))
      .append(this.addAddrLine(8,
                               "version",
                               "${plugin.version.maven.war}"))
      .append("        <configuration>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("          <webResources>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("            <resource>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(this.addAddrLine(14,
                               "directory",
                               "${project.build.directory}/${project.build.finalName}"))
      .append("            </resource>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("          </webResources>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(this.addAddrLine(10,
                               "failOnMissingWebXml",
                               "false"))
      .append("        </configuration>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("        <executions>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("          <execution>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(this.addAddrLine(12,
                               "id",
                               "copy-war-contents-to-exploded-dir"))
      .append(this.addAddrLine(12,
                               "phase",
                               "compile"))
      .append("            <goals>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(this.addAddrLine(14,
                               "goal",
                               "exploded"))
      .append("            </goals>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("          </execution>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("        </executions>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      </plugin>")
      .append(GeneratorConstants.LINE_BREAK);

    return sb.toString();
  }

  private String geenrateBuildHelperPlugin() {
    StringBuilder sb = new StringBuilder();

    sb.append("      <plugin>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(this.addAddrLine(8,
                               "groupId",
                               "org.codehaus.mojo"))
      .append(this.addAddrLine(8,
                               "artifactId",
                               "build-helper-maven-plugin"))
      .append("        <executions>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("          <execution>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(this.addAddrLine(12,
                               "id",
                               "add-source"))
      .append(this.addAddrLine(12,
                               "phase",
                               "generate-sources"))
      .append("            <goals>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(this.addAddrLine(14,
                               "goal",
                               "add-source"))
      .append("            </goals>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("            <configuration>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("              <sources>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(this.addAddrLine(16,
                               "source",
                               "${generated.source.directory}"))
      .append("              </sources>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("            </configuration>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("          </execution>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("        </executions>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      </plugin>")
      .append(GeneratorConstants.LINE_BREAK);

    return sb.toString();
  }

  private String generateCompilerPlugin() {
    StringBuilder sb = new StringBuilder();

    sb.append("      <plugin>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(this.addAddrLine(8,
                               "groupId",
                               "org.apache.maven.plugins"))
      .append(this.addAddrLine(8,
                               "artifactId",
                               "maven-compiler-plugin"))
      .append(this.addAddrLine(8,
                               "version",
                               "${plugin.version.maven.compiler}"))
      .append("        <configuration>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(this.addAddrLine(10,
                               "source",
                               "${maven.compiler.source}"))
      .append(this.addAddrLine(10,
                               "target",
                               "${maven.compiler.target}"))
      //      .append(this.addAddrLine(10,
      //                               "showWarnings",
      //                               "true"))
      //      .append(this.addAddrLine(10,
      //                               "showDeprecation",
      //                               "true"))
      //      .append(this.addAddrLine(10,
      //                               "meminitial",
      //                               "128m"))
      //      .append(this.addAddrLine(10,
      //                               "maxmem",
      //                               "1024m"))
      //      .append(this.addAddrLine(10,
      //                               "encoding",
      //                               "${project.build.sourceEncoding}"))
      //      .append("          <compilerArgs>")
      //      .append(GeneratorConstants.LINE_BREAK)
      //      .append(this.addAddrLine(12,
      //                               "compilerArgument",
      //                               "-Xlint:all"))
      //      .append("          </compilerArgs>")
      //      .append(GeneratorConstants.LINE_BREAK)
      //      .append("        </configuration>")
      //      .append(GeneratorConstants.LINE_BREAK)
      //      .append("        <executions>")
      //      .append(GeneratorConstants.LINE_BREAK)
      //      .append("          <execution>")
      //      .append(GeneratorConstants.LINE_BREAK)
      //      .append(this.addAddrLine(12,
      //                               "id",
      //                               "process-annotations"))
      //      .append(this.addAddrLine(12,
      //                               "phase",
      //                               "generate-sources"))
      //      .append("            <goals>")
      //      .append(GeneratorConstants.LINE_BREAK)
      //      .append(this.addAddrLine(14,
      //                               "goal",
      //                               "compile"))
      //      .append("            </goals>")
      //      .append(GeneratorConstants.LINE_BREAK)
      //      .append("            <configuration>")
      //      .append(GeneratorConstants.LINE_BREAK)
      //      .append(this.addAddrLine(14,
      //                               "proc",
      //                               "only"))
      //      .append("              <annotationProcessors>")
      //      .append(GeneratorConstants.LINE_BREAK)
      //      .append(this.addAddrLine(16,
      //                               "annotationProcessor",
      //                               "com.github.mvp4g.mvp4g2.processor.Mvp4g2Processor"))
      //      .append("              </annotationProcessors>")
      //      .append(GeneratorConstants.LINE_BREAK)
      .append("        </configuration>")
      .append(GeneratorConstants.LINE_BREAK)
      //      .append("          </execution>")
      //      .append(GeneratorConstants.LINE_BREAK)
      //      .append("          <execution>")
      //      .append(GeneratorConstants.LINE_BREAK)
      //      .append(this.addAddrLine(12,
      //                               "id",
      //                               "default-compile"))
      //      .append(this.addAddrLine(12,
      //                               "phase",
      //                               "compile"))
      //      .append("            <goals>")
      //      .append(GeneratorConstants.LINE_BREAK)
      //      .append(this.addAddrLine(14,
      //                               "goal",
      //                               "compile"))
      //      .append("            </goals>")
      //      .append(GeneratorConstants.LINE_BREAK)
      //      .append("            <configuration>")
      //      .append(GeneratorConstants.LINE_BREAK)
      //      .append(this.addAddrLine(14,
      //                               "proc",
      //                               "only"))
      //      .append("              <compilerArgs>")
      //      .append(GeneratorConstants.LINE_BREAK)
      //      .append(this.addAddrLine(16,
      //                               "arg",
      //                               "-proc:none"))
      //      .append("              </compilerArgs>")
      //      .append(GeneratorConstants.LINE_BREAK)
      //      .append("            </configuration>")
      //      .append(GeneratorConstants.LINE_BREAK)
      //      .append("          </execution>")
      //      .append(GeneratorConstants.LINE_BREAK)
      //      .append("        </executions>")
      //      .append(GeneratorConstants.LINE_BREAK)
      .append("      </plugin>")
      .append(GeneratorConstants.LINE_BREAK);

    return sb.toString();
  }

  private String generateDependencyManagement() {
    StringBuilder sb = new StringBuilder();

    sb.append(this.addCommentLine(2,
                                  "GWT BOM"))
      .append("  <dependencyManagement>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("    <dependencies>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(this.addDependency(6,
                                 "com.google.gwt",
                                 "gwt",
                                 "${gwt.version}",
                                 "pom",
                                 "import"))
      .append("    </dependencies>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("  </dependencyManagement>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK);

    return sb.toString();
  }

  private String generateDependencies() {
    StringBuilder sb = new StringBuilder();

    sb.append("  <dependencies>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(this.addDependency(4,
                                 "com.google.gwt",
                                 "gwt-user",
                                 null,
                                 null,
                                 "provided"))
      .append(this.addDependency(4,
                                 "com.google.gwt",
                                 "gwt-dev",
                                 null,
                                 null,
                                 "provided"))
      .append(this.addDependency(4,
                                 "com.github.mvp4g",
                                 "mvp4g2",
                                 "${mvp4g2.version}"))
      .append(this.addDependency(4,
                                 "com.github.mvp4g",
                                 "mvp4g2-processor",
                                 "${mvp4g2.version}"));
    if (WidgetLibrary.ELEMENTO == this.mvp4g2GeneraterParms.getWidgetLibrary()) {
      sb.append(this.addDependency(4,
                                   "org.jboss.gwt.elemento",
                                   "elemento-core",
                                   "${elemento.version}"));
    }
    if (WidgetLibrary.GXT == this.mvp4g2GeneraterParms.getWidgetLibrary()) {
      sb.append(this.addDependency(4,
                                   "com.sencha.gxt",
                                   "gxt",
                                   "${gxt.version}"));
      sb.append(this.addDependency(4,
                                   "com.sencha.gxt",
                                   "gxt-theme-neptune",
                                   "${gxt.version}"));
    }
    sb.append("  </dependencies>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK);

    return sb.toString();
  }

  private String generateHeadlines() {
    StringBuilder sb = new StringBuilder();

    sb.append(this.addAddrLine(2,
                               "modelVersion",
                               "4.0.0"))
      .append(GeneratorConstants.LINE_BREAK)
      .append(this.addAddrLine(2,
                               "groupId",
                               this.mvp4g2GeneraterParms.getGroupId()))
      .append(this.addAddrLine(2,
                               "artifactId",
                               GeneratorUtils.removeBadChracters(this.mvp4g2GeneraterParms.getArtefactId())))
      .append(this.addAddrLine(2,
                               "version",
                               "1.0.0-SNAPSHOT"))
      .append(GeneratorConstants.LINE_BREAK)
      .append(this.addAddrLine(2,
                               "packaging",
                               "gwt-app"))
      .append(GeneratorConstants.LINE_BREAK)
      .append(this.addAddrLine(2,
                               "name",
                               this.mvp4g2GeneraterParms.getArtefactId() + " - Mvp4g2 Boot Starter Project"))
      .append(GeneratorConstants.LINE_BREAK)
      .append(this.addAddrLine(2,
                               "description",
                               "Mvp4g2 Boot Starter Project"))
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK);

    return sb.toString();
  }

  private String generateProperties() {
    StringBuilder sb = new StringBuilder();

    sb.append("  <properties>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(this.addCommentLine(4,
                                  "Convenience property to set the GWT version"))
      .append(this.addAddrLine(4,
                               "gwt.version",
                               this.mvp4g2GeneraterParms.getGwtVersion()))
      .append(GeneratorConstants.LINE_BREAK)
      .append(this.addCommentLine(4,
                                  "mvp4g2 version"))
      .append(this.addAddrLine(4,
                               "mvp4g2.version",
                               "1.0.1"))
      .append(GeneratorConstants.LINE_BREAK);
    if (WidgetLibrary.ELEMENTO == this.mvp4g2GeneraterParms.getWidgetLibrary()) {
      sb.append(this.addCommentLine(4,
                                    "Elemento version"))
        .append(this.addAddrLine(4,
                                 "elemento.version",
                                 "0.8.1"))
        .append(GeneratorConstants.LINE_BREAK);
    }
    if (WidgetLibrary.GXT == this.mvp4g2GeneraterParms.getWidgetLibrary()) {
      sb.append(this.addCommentLine(4,
                                    "GXT version"))
        .append(this.addAddrLine(4,
                                 "gxt.version",
                                 "4.0.0"))
        .append(GeneratorConstants.LINE_BREAK);
    }
    sb.append(this.addCommentLine(4,
                                  "plugin versions"))
      .append(this.addAddrLine(4,
                               "plugin.version.eclipse.lifecyle",
                               "1.0.0"))
      .append(this.addAddrLine(4,
                               "plugin.version.maven.compiler",
                               "3.6.1"))
      .append(this.addAddrLine(4,
                               "plugin.version.maven.gwt",
                               "1.0-rc-9"))
      .append(this.addAddrLine(4,
                               "plugin.version.maven.war",
                               "3.0.0"))
      .append(GeneratorConstants.LINE_BREAK)
      .append(this.addCommentLine(4,
                                  "GWT needs at least java 1.6"))
      .append(this.addAddrLine(4,
                               "maven.compiler.source",
                               "1.8"))
      .append(this.addAddrLine(4,
                               "maven.compiler.target",
                               "1.8"))
      .append(GeneratorConstants.LINE_BREAK)
      .append(this.addAddrLine(4,
                               "project.build.sourceEncoding",
                               "UTF-8"))
      .append(GeneratorConstants.LINE_BREAK)
      .append(this.addAddrLine(4,
                               "generated.source.directory",
                               "${project.build.directory}/generated-sources/annotations"))
      .append(GeneratorConstants.LINE_BREAK)
      .append(this.addAddrLine(4,
                               "webappDirectory",
                               "${project.build.directory}/${project.build.finalName}"))
      .append("  </properties>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK);

    return sb.toString();
  }

  private String addCommentLine(int indent,
                                String comment) {
    StringBuilder sb = new StringBuilder();

    sb.append(addIndent(indent))
      .append("<!-- ")
      .append(comment)
      .append(" -->")
      .append(GeneratorConstants.LINE_BREAK);

    return sb.toString();
  }

  private String addDependency(int indent,
                               String groupId,
                               String artifactId,
                               String version) {
    return this.addDependency(indent,
                              groupId,
                              artifactId,
                              version,
                              null,
                              null);
  }

  private String addDependency(int indent,
                               String groupId,
                               String artifactId) {
    return this.addDependency(indent,
                              groupId,
                              artifactId,
                              null,
                              null,
                              null);
  }

  private String addDependency(int indent,
                               String groupId,
                               String artifactId,
                               String version,
                               String type,
                               String scope) {
    StringBuilder sb = new StringBuilder();

    sb.append(addIndent(indent))
      .append("<dependency>")
      .append(GeneratorConstants.LINE_BREAK);
    if (groupId != null) {
      sb.append(this.addAddrLine(indent + 2,
                                 "groupId",
                                 groupId));
    }
    if (artifactId != null) {
      sb.append(this.addAddrLine(indent + 2,
                                 "artifactId",
                                 artifactId));
    }
    if (version != null) {
      sb.append(this.addAddrLine(indent + 2,
                                 "version",
                                 version));
    }
    if (type != null) {
      sb.append(this.addAddrLine(indent + 2,
                                 "type",
                                 type));
    }
    if (scope != null) {
      sb.append(this.addAddrLine(indent + 2,
                                 "scope",
                                 scope));
    }
    sb.append(addIndent(indent))
      .append("</dependency>")
      .append(GeneratorConstants.LINE_BREAK);

    return sb.toString();
  }

  public static class Builder {

    Mvp4g2GeneraterParms mvp4g2GeneraterParms;

    String               projectFolder;

    public Builder mvp4g2GeneraterParms(Mvp4g2GeneraterParms mvp4g2GeneraterParms) {
      this.mvp4g2GeneraterParms = mvp4g2GeneraterParms;
      return this;
    }

    public Builder projectFolder(String projectFolder) {
      this.projectFolder = projectFolder;
      return this;
    }

    public PomGenerator build() {
      return new PomGenerator(this);
    }
  }
}
