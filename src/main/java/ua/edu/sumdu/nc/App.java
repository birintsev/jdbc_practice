package ua.edu.sumdu.nc;

import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.net.URISyntaxException;

public class App {

    public static void main( String[] args ) {
        configureLogger();

    }

    private static void configureLogger() {
        try {
            System.setProperty("logfile", new File(new File(App.class.getProtectionDomain()
                    .getCodeSource().getLocation().toURI()).getParentFile(), "log.log").getAbsolutePath());
            PropertyConfigurator.configure(App.class.getResourceAsStream("/log4j.properties"));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}