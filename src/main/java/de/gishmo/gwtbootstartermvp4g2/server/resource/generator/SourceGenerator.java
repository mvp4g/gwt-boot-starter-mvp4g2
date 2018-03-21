package de.gishmo.gwtbootstartermvp4g2.server.resource.generator;

import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.GeneratorException;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.ApplicationLoaderSourceGenerator;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.ApplicationSourceGenerator;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.EntryPointSourceGenerator;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.HostPageSourceGenerator;

import java.io.File;

public class SourceGenerator {

  private static final String SRC_MAIN_JAVA             = "src" + File.separator + "main" + File.separator + "java";
  private static final String SRC_MAIN_RESOURCES        = "src" + File.separator + "main" + File.separator + "resources";
  private static final String SRC_MAIN_RESOURCES_STATIC = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static";
  private static final String SRC_MAIN_TEST             = "src" + File.separator + "test";

  private static final String CLIENT = "client";
  private static final String SERVER = "server";
  private static final String SHARED = "shared";

  private File directoryJava;
  private File directoryResources;
  private File directoryResourcesStatic;
  private File directoryResourcesTest;

  private String clientPackage;
  private String sharedPackage;
  private String serverPackage;

  private File directoryClientPackage;
  private File directoryRootPackage;
  private File directorySharedPackage;
  private File directoryServerPackage;

  private String clientPackageJavaConform;
  private String sharedPackageJavaConform;
  private String serverPackageJavaConform;

  private Mvp4g2GeneraterParms mvp4g2GeneraterParms;
  private String               projectFolder;

  private SourceGenerator(Builder builder) {
    super();

    this.mvp4g2GeneraterParms = builder.mvp4g2GeneraterParms;
    this.projectFolder = builder.projectFolder;
  }

  public static Builder builder() {
    return new SourceGenerator.Builder();
  }

  public void generate()
    throws GeneratorException {

    createBasicStructure();
    createDataDependingStructure();

    // Hostpage ...
    HostPageSourceGenerator.builder()
                           .mvp4g2GeneraterParms(this.mvp4g2GeneraterParms)
                           .directoryResourcesStatic(this.directoryResourcesStatic)
                           .build()
                           .generate();

    // EntryPoint
    EntryPointSourceGenerator.builder()
                             .mvp4g2GeneraterParms(this.mvp4g2GeneraterParms)
                             .clientPackageJavaConform(this.clientPackageJavaConform)
                             .directoryJava(this.directoryJava)
                             .build()
                             .generate();
    // Application
    ApplicationSourceGenerator.builder()
                              .mvp4g2GeneraterParms(this.mvp4g2GeneraterParms)
                              .clientPackageJavaConform(this.clientPackageJavaConform)
                              .directoryJava(this.directoryJava)
                              .build()
                              .generate();

    // Application Loader class (if requested)
    if (mvp4g2GeneraterParms.isApplicationLoader()) {
      ApplicationLoaderSourceGenerator.builder()
                                      .mvp4g2GeneraterParms(this.mvp4g2GeneraterParms)
                                      .clientPackageJavaConform(this.clientPackageJavaConform)
                                      .directoryJava(this.directoryJava)
                                      .build()
                                      .generate();
    }
  }

  private void createBasicStructure() {
    // create Java directory
    directoryJava = new File(this.projectFolder + File.separator + SourceGenerator.SRC_MAIN_JAVA);
    directoryJava.mkdirs();
    // create Resources directory
    directoryResources = new File(this.projectFolder + File.separator + SourceGenerator.SRC_MAIN_RESOURCES);
    directoryResources.mkdirs();
    // create Resources/static directory
    directoryResourcesStatic = new File(this.projectFolder + File.separator + SourceGenerator.SRC_MAIN_RESOURCES_STATIC);
    directoryResourcesStatic.mkdirs();
    // create test directory
    directoryResourcesTest = new File(this.projectFolder + File.separator + SourceGenerator.SRC_MAIN_TEST);
    directoryResourcesTest.mkdirs();
  }

  private void createDataDependingStructure() {
    // create Java package
    String srcPackage = mvp4g2GeneraterParms.getGroupId()
                                            .replace(".",
                                                     File.separator);
    srcPackage = srcPackage + File.separator + mvp4g2GeneraterParms.getArtefactId()
                                                                   .toLowerCase();
    directoryRootPackage = new File(directoryJava,
                                    srcPackage);
    directoryRootPackage.mkdirs();

    this.clientPackage = srcPackage + File.separator + SourceGenerator.CLIENT;
    this.clientPackageJavaConform = this.clientPackage.replace(File.separator,
                                                               ".");
    directoryClientPackage = new File(directoryJava,
                                      this.clientPackage);
    directoryClientPackage.mkdirs();

    this.serverPackage = srcPackage + File.separator + SourceGenerator.SERVER;
    this.serverPackageJavaConform = this.serverPackage.replace(File.separator,
                                                               ".");
    directoryServerPackage = new File(directoryJava,
                                      this.serverPackage);
    directoryServerPackage.mkdirs();

    this.sharedPackage = srcPackage + File.separator + SourceGenerator.SHARED;
    this.sharedPackageJavaConform = this.sharedPackage.replace(File.separator,
                                                               ".");
    directorySharedPackage = new File(directoryJava,
                                      this.sharedPackage);
    directorySharedPackage.mkdirs();

    directoryRootPackage = new File(directoryResources,
                                    srcPackage);
    directoryRootPackage.mkdirs();
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

    public SourceGenerator build() {
      return new SourceGenerator(this);
    }
  }
}
