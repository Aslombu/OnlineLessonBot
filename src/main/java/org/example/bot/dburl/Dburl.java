package org.example.bot.dburl;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

public class Dburl {
    public static Connection connection;

    @SneakyThrows
    public static Connection getConnection() {
        if(connection == null){
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/online", "postgres", "1117");
        }
        return connection;
    }

}
