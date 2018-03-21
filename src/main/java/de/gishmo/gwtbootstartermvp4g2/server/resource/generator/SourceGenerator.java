package de.gishmo.gwtbootstartermvp4g2.server.resource.generator;

import java.io.File;

import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.GeneratorException;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.classes.ApplicationLoaderSourceGenerator;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.classes.ApplicationSourceGenerator;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.classes.EntryPointSourceGenerator;

public class SourceGenerator
  extends AbstractGenerator {

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

  private File directoryRootResource;

  public SourceGenerator(Mvp4g2GeneraterParms model,
                         String projectFolder) {
    super(projectFolder,
          model);
  }

  public void generate()
    throws GeneratorException {

    createBasicStructure();
    createDataDependingStructure();

    // EntryPoint
    EntryPointSourceGenerator.builder()
                             .mvp4g2GeneraterParms(this.model)
                             .clientPackageJavaConform(this.clientPackageJavaConform)
                             .directoryJava(this.directoryJava)
                             .build()
                             .generate();
    // Application
    ApplicationSourceGenerator.builder()
                              .mvp4g2GeneraterParms(this.model)
                              .clientPackageJavaConform(this.clientPackageJavaConform)
                              .directoryJava(this.directoryJava)
                              .build()
                              .generate();

    // Application Loader class (if requested)
    if (model.isApplicationLoader()) {
      ApplicationLoaderSourceGenerator.builder()
                                      .mvp4g2GeneraterParms(this.model)
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
    String srcPackage = model.getGroupId()
                             .replace(".",
                                      File.separator);
    srcPackage = srcPackage + File.separator + model.getArtefactId()
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
}
