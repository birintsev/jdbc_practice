package ua.edu.sumdu.nc;

import com.sun.org.apache.xerces.internal.util.DOMInputSource;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import ua.edu.sumdu.nc.commands.*;
import ua.edu.sumdu.nc.entities.Employee;
import ua.edu.sumdu.nc.entities.exceptions.JDBCPracticeException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class App {

    private static Logger LOGGER;

    public static void main(String[] args) {
        configureLogger();
        createDefaultProperties();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            execute(scanner.nextLine());
        }
    }

    private static void createDefaultProperties() {
        try {
            File datasource_config = new File(new File(App.class.getProtectionDomain()
                    .getCodeSource().getLocation().toURI()).getParentFile(), "datasource-config.xml");

            if (datasource_config.isFile()) {
                return;
            }

            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element datasources = document.createElement("datasources");
            Element datasource = document.createElement("datasource");
            Element connection_url = document.createElement("connection-url");
            Element driver_class = document.createElement("driver-class");
            Element password = document.createElement("password");
            Element user_name = document.createElement("user_name");
            Element source_name = document.createElement("user_name");

            connection_url.setTextContent("jdbc:oracle:thin:@localhost:1521:XE");
            driver_class.setTextContent("oracle.jdbc.driver.OracleDriver");
            user_name.setTextContent("SYSTEM");
            password.setTextContent("changeMe");
            source_name.setTextContent("changeMe");

            datasource.appendChild(connection_url);
            datasource.appendChild(driver_class);
            datasource.appendChild(user_name);
            datasource.appendChild(source_name);
            datasource.appendChild(password);
            datasources.appendChild(datasource);
            document.appendChild(datasources);

            TransformerFactory.newInstance().newTransformer().transform(
                    new DOMSource(document),
                    new StreamResult(datasource_config));
        } catch (TransformerException | URISyntaxException | ParserConfigurationException e) {
            throw new JDBCPracticeException(e);
        }
    }

    private static void configure(File datasource_config) throws FileNotFoundException {
        if (datasource_config == null || !datasource_config.isFile()) {
            throw new FileNotFoundException("The file configuration file has not been found");
        }
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(datasource_config);
            XPath xPath = XPathFactory.newInstance().newXPath();
            DOMInputSource domInputSource = new DOMInputSource(document);
            System.setProperty("connection-url",
                    xPath.evaluate("/datasources/datasource/connection-url", domInputSource));
            System.setProperty("user-name",
                    xPath.evaluate("/datasources/datasource/user-name", domInputSource));
            System.setProperty("password",
                    xPath.evaluate("/datasources/datasource/password", domInputSource));
        } catch (SAXException | IOException | ParserConfigurationException | XPathExpressionException e) {
            throw new JDBCPracticeException(e);
        }
    }

    private static void configureLogger() {
        try {
            System.setProperty("logfile", new File(new File(App.class.getProtectionDomain()
                    .getCodeSource().getLocation().toURI()).getParentFile(), "log.log").getAbsolutePath());
            PropertyConfigurator.configure(App.class.getResourceAsStream("/log4j.properties"));
            LOGGER = Logger.getLogger(App.class.getSimpleName());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private static void execute(String command) {
        String key;
        try {
            key = command.substring(0, 3);
        } catch (Exception e) {
            new PrintComands().execute();
            return;
        }
        switch (key) {
            case "-h":
                new PrintComands().execute();
                break;
            case "-sa":
                new PrintAllEmployees().execute();
                break;
            case "-se":
                printEmployee(command);
                break;
            case "-de":
                deleteEmployee(command);
                break;
            case "-ae":
                addEmployee(command);
                break;
            case "-scan":
                try {
                    configure(new File(command.split(" ").length == 2 ?
                            command.split(" ")[1]
                            : new File(
                                new File(App.class.getProtectionDomain()
                                .getCodeSource()
                                .getLocation()
                                .toURI()).getParentFile(), "datasource-config.xml").getAbsolutePath()));
                } catch (FileNotFoundException | URISyntaxException e) {
                    throw new JDBCPracticeException(e);
                }
                break;
            case "-exit":
                System.exit(0);
            default:
                if (LOGGER.isEnabledFor(Level.INFO)) {
                    LOGGER.info("Unknown command " + command);
                }
                new PrintComands().execute();
        }
    }

    private static void printEmployee(String command) {
        try {
            new PrintEmployee(parseEmployeeId(command)).execute();
        } catch (JDBCPracticeException e) {
            if (LOGGER.isEnabledFor(Level.ERROR)) {
                LOGGER.error(e);
            }
        }
    }

    private static void addEmployee(String command) {
        String[] items = command.split(" ");
        new AddEmployee(new Employee(
                items[1],
                items[2],
                Integer.parseInt(items[3]),
                Integer.parseInt(items[4]),
                Double.parseDouble(items[5]),
                Double.parseDouble(items[6]),
                LocalDate.from(DateTimeFormatter.ISO_DATE.parse(items[7]))))
                .execute();
    }

    private static void deleteEmployee(String command) {
        try {
            new DeleteEmployee(parseEmployeeId(command)).execute();
        } catch (JDBCPracticeException e) {
            if (LOGGER.isEnabledFor(Level.ERROR)) {
                LOGGER.error(e);
            }
        }
    }


    private static int parseEmployeeId(String command) {
        String empno = null;
        try {
            empno = command.split(" ")[1];
            return Integer.parseInt(empno);
        } catch (NumberFormatException e) {
            throw new JDBCPracticeException("Invalid id format " + empno, e);
        } catch (IndexOutOfBoundsException e) {
            throw new JDBCPracticeException("Unspecified employee id (empno)", e);
        }
    }
}