package peaksoft.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import peaksoft.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {

    private static final String url = "jdbc:postgresql://localhost:5432/postgres";
    private static final String password = "postgres";

    private static final String userName = "postgres";
    // реализуйте настройку соеденения с БД

    public static Connection getconnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    url,
                    password,
                    userName
            );
            System.out.println("Malik key");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public static SessionFactory getSession(){
        Properties properties = new Properties();
        properties.put(Environment.DRIVER, "org.postgresql.Driver");
        properties.put(Environment.URL, "jdbc:postgresql://localhost:5432/postgres");
        properties.put(Environment.USER, "postgres");
        properties.put(Environment.PASS, "postgres");
        properties.put(Environment.HBM2DDL_AUTO, "delete");
        properties.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
        properties.put(Environment.SHOW_SQL, "true");

        Configuration configuration = new Configuration();
        configuration.addProperties(properties);
        configuration.addAnnotatedClass(User.class);
        return configuration.buildSessionFactory();
    }


}
