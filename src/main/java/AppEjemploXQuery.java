
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
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

      Collection collection = null;

      // get the collection
      collection = DatabaseManager.getCollection(URI, USER, PASSWORD);
      collection.setProperty(OutputKeys.INDENT, "no");

      printIterator(xQuery01(collection));
      printIterator(xQuery02(collection));
      printIterator(xQuery03(collection));
      printIterator(xQuery04(collection));
      printIterator(xQuery05(collection));
      printIterator(xQuery06(collection));


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

  // Obtiene todos los empleados.
  public static ResourceIterator xQuery01(Collection collection) throws XMLDBException {
    System.out.println();
    System.out.println("xQuery01");
    System.out.println("========");

    XPathQueryService xPathQueryService = (XPathQueryService) collection.getService("XPathQueryService", "1.0");
    ResourceSet result = xPathQueryService.query("for $emp in /EMPLEADOS/EMP_ROW return $emp");

    ResourceIterator iterator = result.getIterator();

    while(iterator.hasMoreResources()){
      Resource r = iterator.nextResource();
      System.out.println((String)r.getContent());
    }

    return iterator;
  }

  // Obtiene el apellido de todos los empleados.
  public static ResourceIterator xQuery02(Collection collection) throws XMLDBException {
    System.out.println();
    System.out.println("xQuery02");
    System.out.println("========");

    XPathQueryService xPathQueryService = (XPathQueryService) collection.getService("XPathQueryService", "1.0");
    ResourceSet result = xPathQueryService.query("for $emp in /EMPLEADOS/EMP_ROW return $emp/APELLIDO");

    ResourceIterator iterator = result.getIterator();

    while(iterator.hasMoreResources()){
      Resource r = iterator.nextResource();
      System.out.println((String)r.getContent());
    }

    return iterator;
  }

  // Obtiene el apellido y el oficio de los empleados en un nuevo nodo.
  public static ResourceIterator xQuery03(Collection collection) throws XMLDBException {
    System.out.println();
    System.out.println("xQuery03");
    System.out.println("========");

    XPathQueryService xPathQueryService = (XPathQueryService) collection.getService("XPathQueryService", "1.0");
    ResourceSet result = xPathQueryService.query("for $emp in /EMPLEADOS/EMP_ROW " +
        " let $nom := $emp/APELLIDO, $ofi := $emp/OFICIO " +
        " order by $emp/OFICIO " +
        " return <APE_OFI>{concat($nom, ' ', $ofi)}</APE_OFI> ");

    ResourceIterator iterator = result.getIterator();

    while(iterator.hasMoreResources()){
      Resource r = iterator.nextResource();
      System.out.println((String)r.getContent());
    }

    return iterator;
  }

  // Obtiene los nodos apellido y el oficio de los empleados en un nuevo nodo.
  public static ResourceIterator xQuery04(Collection collection) throws XMLDBException {
    System.out.println();
    System.out.println("xQuery04");
    System.out.println("========");

    XPathQueryService xPathQueryService = (XPathQueryService) collection.getService("XPathQueryService", "1.0");
    ResourceSet result = xPathQueryService.query("for $emp in /EMPLEADOS/EMP_ROW " +
        " let $nom := $emp/APELLIDO, $ofi := $emp/OFICIO " +
        " order by $emp/OFICIO " +
        " return <APE_OFI>{($nom, ' ', $ofi)}</APE_OFI> ");

    ResourceIterator iterator = result.getIterator();

    while(iterator.hasMoreResources()){
      Resource r = iterator.nextResource();
      System.out.println((String)r.getContent());
    }

    return iterator;
  }

  // Obtiene los nodos apellido y el oficio de los empleados en un nuevo nodo.
  public static ResourceIterator xQuery05(Collection collection) throws XMLDBException {
    System.out.println();
    System.out.println("xQuery05");
    System.out.println("========");

    XPathQueryService xPathQueryService = (XPathQueryService) collection.getService("XPathQueryService", "1.0");
    ResourceSet result = xPathQueryService.query("for $emp in /EMPLEADOS/EMP_ROW " +
        " order by $emp/APELLIDO " +
        " return " +
        " if ($emp/OFICIO='DIRECTOR') " +
        " then " +
        " <DIRECTOR>{$emp/APELLIDO/text()}</DIRECTOR> " +
        " else " +
        " <EMPLE>{data($emp/APELLIDO)}</EMPLE> ");

    ResourceIterator iterator = result.getIterator();

    while(iterator.hasMoreResources()){
      Resource r = iterator.nextResource();
      System.out.println((String)r.getContent());
    }

    return iterator;
  }

  // Obtiene el número de empleados que tiene cada departamento y la media del salario redondeada
  public static ResourceIterator xQuery06(Collection collection) throws XMLDBException {
    System.out.println();
    System.out.println("xQueryXX");
    System.out.println("========");

    XPathQueryService xPathQueryService = (XPathQueryService) collection.getService("XPathQueryService", "1.0");
    ResourceSet result = xPathQueryService.query("for $dep in distinct-values(/EMPLEADOS/EMP_ROW/DEPT_NO)" +
        "let $cu := count(/EMPLEADOS/EMP_ROW[DEPT_NO=$dep])" +
        "let $sala := round(avg(/EMPLEADOS/EMP_ROW[DEPT_NO=$dep]/SALARIO))" +
        "return concat('Departamento: ', $dep, '. Num empleados = ', $cu, '. Media salario = ', $sala)");

    ResourceIterator iterator = result.getIterator();

    return iterator;
  }

  // Muestra por pantalla lo que haya en el iterador.
  public static void printIterator(ResourceIterator iterator) throws XMLDBException {
    while(iterator.hasMoreResources()){
      Resource r = iterator.nextResource();
      System.out.println((String)r.getContent());
    }
  }

}
