
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XPathQueryService;

import javax.xml.transform.OutputKeys;

public class AppEjemploXQuery {
  private static String URI = "xmldb:exist://localhost:8080/exist/xmlrpc/db/ColeccionPruebas";
  private static String USER = "admin";
  private static String PASSWORD = "admin";

  public static void main(String[] args) {
    final String driver = "org.exist.xmldb.DatabaseImpl";

    // initialize database driver
    Class cl = null;
    try {
      cl = Class.forName(driver);
      Database database = (Database) cl.newInstance();
      database.setProperty("create-database", "true");
      DatabaseManager.registerDatabase(database);

      Collection col = null;
      XMLResource res = null;

      // get the collection
      col = DatabaseManager.getCollection(URI, USER, PASSWORD);
      col.setProperty(OutputKeys.INDENT, "no");

      XPathQueryService xPathQueryService = (XPathQueryService) col.getService("XPathQueryService", "1.0");
      ResourceSet result = xPathQueryService.query("for $emp in /EMPLEADOS/EMP_ROW return $emp/APELLIDO");

      ResourceIterator iterator = result.getIterator();

      while(iterator.hasMoreResources()){
        Resource r = iterator.nextResource();
        System.out.println((String)r.getContent());
      }

    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (XMLDBException e) {
      e.printStackTrace();
    }
  }
}
