package test.de.gishmo.gwtbootstartermvp4g2.server.resource;


import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.DataConstants;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.PresenterData;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.ViewCreationMethod;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.WidgetLibrary;
import de.gishmo.gwtbootstartermvp4g2.server.resource.ProjectService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * ProjectService Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Apr 7, 2018</pre>
 */
public class ProjectServiceTest {

  @Before
  public void before()
    throws Exception {
  }

  @After
  public void after()
    throws Exception {
  }

  /**
   * Method: generate(@RequestBody Mvp4g2GeneraterParms model)
   */
  @Test
  public void testGenerateGwt()
    throws Exception {

    Mvp4g2GeneraterParms mvp4g2GeneraterParms = new Mvp4g2GeneraterParms();

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


//TODO: Test goes here... 
  }

  /**
   * Method: generate(@RequestBody Mvp4g2GeneraterParms model)
   */
  @Test
  public void testGenerateElemento()
    throws Exception {

    Mvp4g2GeneraterParms mvp4g2GeneraterParms = new Mvp4g2GeneraterParms();

    mvp4g2GeneraterParms.setGroupId("com.example");
    mvp4g2GeneraterParms.setArtefactId("MyTestProject");
    mvp4g2GeneraterParms.setApplicationLoader(true);
    mvp4g2GeneraterParms.setDebug(true);
    mvp4g2GeneraterParms.setHistory(true);
    mvp4g2GeneraterParms.setHistoryOnStart(true);
    mvp4g2GeneraterParms.setGwtVersion(DataConstants.GWT_VERSION_2_8_2);
    mvp4g2GeneraterParms.setWidgetLibrary(WidgetLibrary.ELEMENTO);

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

    ProjectService projectService = new ProjectService();
    projectService.generate(mvp4g2GeneraterParms);
  }


  /**
   * Method: zipIt(String projectFolder)
   */
  @Test
  public void testZipIt()
    throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = ProjectService.getClass().getMethod("zipIt", String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
  }

  /**
   * Method: generateFileList(String sourceFolder, List<String> fileList, File node)
   */
  @Test
  public void testGenerateFileList()
    throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = ProjectService.getClass().getMethod("generateFileList", String.class, List<String>.class, File.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
  }

  /**
   * Method: generateZipEntry(String sourceFolder, String file)
   */
  @Test
  public void testGenerateZipEntry()
    throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = ProjectService.getClass().getMethod("generateZipEntry", String.class, String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
  }

  /**
   * Method: deleteFolder(File folder)
   */
  @Test
  public void testDeleteFolder()
    throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = ProjectService.getClass().getMethod("deleteFolder", File.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
  }

} 
