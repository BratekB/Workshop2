package pl.coderslab;

import pl.coderslab.entity.User;
import pl.coderslab.entity.UserDao;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

//        System.out.println(UserDao.lastId());

//        int lastId=4;
        UserDao uD = new UserDao();
        User u1 = new User();
//        u1.setUserName("Romek");
//        u1.setEmail("rom@rom.pl");
//        u1.setPassword("Lewatywa");
        uD.create(u1);
        System.out.println(u1.toString());
//
//        User u2 = uD.read(lastId);
//        System.out.println(u2.toString());
//
//        u2.setUserName("Romo");
//        u2.setEmail("romo@rom.pl");
//        u2.setPassword("Gitara");
//        uD.update(u2);
//        User u3 = uD.read(lastId);
//        System.out.println(u3.toString());
//
//        uD.delete(lastId);
//        System.out.println(uD.read(lastId).toString());
//        User[] users = uD.findAll();
//        System.out.println(users[0]);
//        System.out.println(users[1]);
//        uD.deleteAll();
//        User[] users2 = uD.findAll();
//        System.out.println(users2[0]);
//        System.out.println(users2[1]);

    }
}