import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import javax.xml.transform.OutputKeys;

/**
 * Esta clase se conecta a una base de datos eXist con las siguientes caracteristicas:

 * Y realiza una consulta de todos los empleados que hay.
 */
public class AppEjemploXPath {
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
      res = (XMLResource) col.getResource("empleados.xml");

      if (res == null) {
        System.out.println("document not found!");
      } else {
        System.out.println(res.getContent());
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
