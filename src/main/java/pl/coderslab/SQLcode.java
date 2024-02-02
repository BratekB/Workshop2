package pl.coderslab;

public class SQLcode {

    public static final String SQL1= """
            CREATE DATABASE workshop2 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
            use workshop2;
            """;
    public static final String SQL2= """
            CREATE TABLE users
            (
                id       int(11) primary key not null auto_increment,
                email    varchar(255) unique default null,
                username varchar(255)        default null,
                password varchar(60)         default null
            );
            """;
}
