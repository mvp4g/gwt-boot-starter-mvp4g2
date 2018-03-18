package de.gishmo.gwt.gwtbootstartermvp4g2.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import de.gishmo.gwt.gwtbootstartermvp4g2.client.service.ProjectService;
import de.gishmo.gwt.gwtbootstartermvp4g2.server.generator.PomGenerator;
import de.gishmo.gwt.gwtbootstartermvp4g2.server.generator.SourceGenerator;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.GeneratorException;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ProjectServiceImpl
  extends RemoteServiceServlet
  implements ProjectService {

  @Override
  public synchronized String generate(Mvp4g2GeneraterParms model)
    throws GeneratorException {
    // create folder in tempDirectory
    String tmpDirPath = System.getProperty("java.io.tmpdir");
    // create projectFolder
    String projectFolder = tmpDirPath + model.getArtefactId();
    // exists? -> delete
    if ((new File(projectFolder)).exists()) {
      deleteFolder(new File(projectFolder));
    }
    // create ...
    if (!(new File(projectFolder).mkdirs())) {
      return "ERROR: creation of project folder failed!";
    }
    // create Java sources (must run first, because this creates the project strucutre)
    SourceGenerator sourceGenerator = new SourceGenerator(model,
                                                          projectFolder);
    sourceGenerator.generate();
    // create XML sources
    PomGenerator pomGenerator = new PomGenerator(model,
                                                 projectFolder);
    // zip the content
    this.zipIt(projectFolder);
    // delete tmp folder
    deleteFolder(new File(projectFolder));
    return "etst";
  }

  private void zipIt(String projectFolder) {
    List<String> fileList = new ArrayList<>();
    generateFileList(projectFolder,
                     fileList,
                     new File(projectFolder));
    byte[] buffer = new byte[1024];
    try {
      FileOutputStream fos = new FileOutputStream(projectFolder + ".zip");
      ZipOutputStream zos = new ZipOutputStream(fos);
      for (String file : fileList) {
        ZipEntry zipEntry = new ZipEntry(file);
        zos.putNextEntry(zipEntry);
        FileInputStream in = new FileInputStream(projectFolder + File.separator + file);
        int len;
        while ((len = in.read(buffer)) > 0) {
          zos.write(buffer,
                    0,
                    len);
        }
        in.close();
      }
      zos.closeEntry();
      zos.close();
    } catch (FileNotFoundException e) {
      // TODO throw exception
      e.printStackTrace();
    } catch (IOException e) {
      // TODO throw exception
      e.printStackTrace();
    }
  }

  private void generateFileList(String sourceFolder,
                                List<String> fileList,
                                File node) {
    //add file only
    if (node.isFile()) {
      fileList.add(generateZipEntry(sourceFolder,
                                    node.getAbsoluteFile()
                                        .toString()));
    }
    if (node.isDirectory()) {
      String[] subNote = node.list();
      for (String filename : subNote) {
        generateFileList(sourceFolder,
                         fileList,
                         new File(node,
                                  filename));
      }
    }
  }

  private String generateZipEntry(String sourceFolder,
                                  String file) {
    return file.substring(sourceFolder.length() + 1,
                          file.length());
  }

  private void deleteFolder(File folder) {
    File[] files = folder.listFiles();
    if (files != null) { //some JVMs return null for empty dirs
      Arrays.stream(files)
            .forEach(file -> {
              if (file.isDirectory()) {
                deleteFolder(file);
              } else {
                file.delete();
              }
            });
    }
    folder.delete();
  }
}

