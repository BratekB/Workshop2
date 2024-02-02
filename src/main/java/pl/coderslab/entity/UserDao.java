package pl.coderslab.entity;

import com.mysql.cj.protocol.Resultset;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.Arrays;

public class UserDao {
    private static final String CREATE_USER_QUERY =
            "INSERT INTO users (username,email,password) VALUES (?,?,?);";
    private static final String READ_USER_QUERY =
            "SELECT username,email,password FROM users where id=?";
    private static final String UPDATE_USER_QUERY =
            "UPDATE users SET username=?,email=?,password=? where id=?";
    private static final String DELETE_USER_QUERY =
            "DELETE FROM users WHERE id=?";
    private static final String FINDALL_USER_QUERY =
            "SELECT id,username,email,password FROM users";
    private static final String DELETEALL_USER_QUERY =
            "DELETE FROM users";

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public User create(User user) {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement st = conn.prepareStatement(CREATE_USER_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            st.setString(1, user.getUserName());
            st.setString(2, user.getEmail());
            st.setString(3, hashPassword(user.getPassword()));
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getInt(1));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User read(int id) {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement st = conn.prepareStatement(READ_USER_QUERY);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setId(id);
                u.setUserName(rs.getString("userName"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(User user) {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement st = conn.prepareStatement(UPDATE_USER_QUERY);
            st.setString(1, user.getUserName());
            st.setString(2, user.getEmail());
            st.setString(3, hashPassword(user.getPassword()));
            st.setInt(4, user.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void delete(int id) {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement st = conn.prepareStatement(DELETE_USER_QUERY);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private User[] addToArray(User u, User[] users) {
        User[] tmpUsers = Arrays.copyOf(users, users.length + 1);
        tmpUsers[users.length] = u;
        return tmpUsers;
    }

    public User[] findAll() {
        try (Connection conn = DBUtil.getConnection()) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(FINDALL_USER_QUERY);
            User[] users = new User[0];
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setUserName(rs.getString("userName"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                users = addToArray(u,users);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void deleteAll(){
        try(Connection conn = DBUtil.getConnection()){
            Statement st = conn.createStatement();
            st.executeUpdate(DELETEALL_USER_QUERY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static int lastId(){
        String sql1= """
                SELECT `AUTO_INCREMENT` FROM INFORMATION_SCHEMA.TABLES
                WHERE TABLE_SCHEMA = 'DatabaseName' AND TABLE_NAME = 'users';
                """;
        try(Connection conn = DBUtil.getConnection()){
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql1);
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
