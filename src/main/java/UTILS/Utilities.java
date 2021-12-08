package UTILS;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Properties;

public class Utilities {
    private static final URL FILE = Utilities.class.getResource("../lexer.properties");
    private Properties properties;

    public Utilities() {
        this.load();
    }

    public void load() {
        try {
            Properties prop = new Properties();
            assert FILE != null;
            InputStream in = new FileInputStream(FILE.getPath());
            prop.load(in);
            properties = prop;
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al cargar datos" + e);
        }
    }

    private String[] operators() {
        return properties.getProperty("arithmetic").split(",");
    }

    public boolean validate(char charText) {
        return Arrays.asList(operators()).contains(Character.toString(charText));
    }
}
