package org.example.bot.server;
import lombok.SneakyThrows;
import org.example.bot.dburl.Dburl;
import org.example.bot.model.UserStatus;
import org.example.bot.model.Users;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
public class UserService {
    @SneakyThrows
    public static Users findChatId(String chatId) {
        Connection connection = Dburl.getConnection();
        Statement statement = connection.createStatement();

        String users_find = "select * from users s where s.id = '%s';";
        ResultSet resultSet = statement.executeQuery(String.format(users_find, chatId));
        if (resultSet.next()) {
            Users users = new Users();
            users.setChatid(resultSet.getString("id"));
            users.setName(resultSet.getString("name"));
            users.setUsername(resultSet.getString("username"));
            users.setPhonenomer(resultSet.getString("phone"));
            users.setUserstatus(UserStatus.valueOf(resultSet.getString("userstatus")));
            return users;
        }else {
            return null;
        }

    }

    @SneakyThrows
    public static void add_users(Users users1) {
        Connection connection = Dburl.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into users(id,name,username,phone,userstatus)values (?,?,?,?,?);");
        preparedStatement.setString(1, users1.getChatid());
        preparedStatement.setString(2, users1.getName());
        preparedStatement.setString(3, users1.getUsername());
        preparedStatement.setString(4, users1.getPhonenomer());
        preparedStatement.setString(5, users1.getUserstatus().toString());
        preparedStatement.executeUpdate();
        preparedStatement.close();

    }

    @SneakyThrows
    public static void userStatus(String chatId, UserStatus userStatus) {
        Connection connection = Dburl.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("update users set userstatus = ? where id = ?;");
        preparedStatement.setString(2, chatId);
        preparedStatement.setString(1,userStatus.toString());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
}
