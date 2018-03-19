package de.gishmo.gwtbootstartermvp4g2.server.resource.generator;

import com.google.gwt.core.client.EntryPoint;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.GeneratorException;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;
import de.gishmo.gwt.mvp4g2.core.application.IsApplication;
import de.gishmo.gwt.mvp4g2.core.application.annotation.Application;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;

public class SourceGenerator
  extends AbstractGenerator {

  private static final String SRC_MAIN_JAVA             = "src" + File.separator + "main" + File.separator + "java";
  private static final String SRC_MAIN_RESOURCES        = "src" + File.separator + "main" + File.separator + "resources";
  private static final String SRC_MAIN_RESOURCES_STATIC = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static";
  private static final String SRC_MAIN_TEST             = "src" + File.separator + "test";

  private static final String CLIENT = "client";
  private static final String SERVER = "server";
  private static final String SHARED = "shared";

  private static final String APPLICAITON = "Application";
  private static final String EVENT_BUS   = "EventBus";
  private static final String LOADER      = "Loader";


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

    generateEntryPoint();
    generateApplication();
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

  private void generateEntryPoint()
    throws GeneratorException {
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder(this.setFirstCharacterToUperCase(model.getArtefactId()))
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(AbstractGenerator.COPYRIGHT)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .addSuperinterface(ClassName.get(EntryPoint.class))
                                        .addMethod(MethodSpec.methodBuilder("onModuleLoad")
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .addStatement("$T application = new $LImpl()",
                                                                           ClassName.get(this.clientPackageJavaConform,
                                                                                         model.getArtefactId() + SourceGenerator.APPLICAITON),
                                                                           model.getArtefactId() + SourceGenerator.APPLICAITON)
                                                             .addStatement("application.run()")
                                                             .build());
    JavaFile javaFile = JavaFile.builder(this.clientPackageJavaConform,
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(new File(directoryJava,
                                ""));
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>" + model.getArtefactId() + "<< -> exception: " + e.getMessage());
    }
  }

  private void generateApplication()
    throws GeneratorException {
    AnnotationSpec.Builder annotation = AnnotationSpec.builder(Application.class)
                                                      .addMember("eventBus",
                                                                 model.getArtefactId() + SourceGenerator.EVENT_BUS + ".class");
    if (model.isApplicationLoader()) {
      annotation.addMember("loader",
                           model.getArtefactId() + SourceGenerator.LOADER + ".class");
    }
    if (model.isHistoryOnStart()) {
      annotation.addMember("historyOnStart",
                           "true");
    }
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder(this.setFirstCharacterToUperCase(model.getArtefactId() + SourceGenerator.APPLICAITON))
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(AbstractGenerator.COPYRIGHT)
                                                             .build())
                                        .addSuperinterface(ClassName.get(IsApplication.class))
                                        .addAnnotation(annotation.build());
    JavaFile javaFile = JavaFile.builder(this.clientPackageJavaConform,
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(new File(directoryJava,
                                ""));
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>" + this.setFirstCharacterToUperCase(model.getArtefactId()) + SourceGenerator.APPLICAITON + "<< -> exception: " + e.getMessage());
    }
  }

  private String setFirstCharacterToUperCase(String value) {
    return value.substring(0,
                           1)
                .toUpperCase() + value.substring(1);
  }
}
