package org.example.bot.server;

import lombok.Data;
import lombok.SneakyThrows;
import org.example.bot.dburl.Dburl;
import org.example.bot.model.Popular;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CourseService {
    @SneakyThrows
    public static ResultSet showCourses(String chatId) {
        Connection connection = Dburl.getConnection();
        Statement statement = connection.createStatement();
        String str = "select * from user_course where user_id = '%s';";
        ResultSet resultSet = statement.executeQuery(String.format(str,chatId));
        return resultSet;

    }

    @SneakyThrows
    public static Boolean isStartCourse(String chatId, String text) {
        Connection connection = Dburl.getConnection();
        Statement statement = connection.createStatement();
        String str = "select * from user_course where user_id = '%s' and course_name = '%s';";
        ResultSet resultSet = statement.executeQuery(String.format(str,chatId,text));

        if (resultSet.next()){
         return   (resultSet.getBoolean("start"));
        }
       return false;


    }

    @SneakyThrows
    public static void StatusChange(String text, String chatid) {

        Connection connection = Dburl.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("update user_course set start = ? where course_name = ? and user_id=? ;");
        preparedStatement.setBoolean(1,true);
        preparedStatement.setString(2,text);
        preparedStatement.setString(3,chatid);
        preparedStatement.executeUpdate();
        preparedStatement.close();

    }

    @SneakyThrows
    public static List<Popular> CourseSonList() {
        List<Popular>list = new ArrayList<>();
        Connection connection = Dburl.getConnection();
        Statement statement = connection.createStatement();
        /*
       SELECT
	customer_id,
	SUM (amount)
FROM
	payment
GROUP BY
	customer_id;*/
        String str = "select course_name ,count(course_name)  from  user_course  group by course_name";
        ResultSet resultSet = statement.executeQuery(str);
        while (resultSet.next()) {
            Popular popular = new Popular();
            popular.setName(resultSet.getString("course_name"));
            popular.setHowMany(resultSet.getInt("count"));
            list.add(popular);
        }
        return list;

    }

    @SneakyThrows
    public static List<Popular> CourseTrending() {
        List<Popular>list = new ArrayList<>();
        Connection connection = Dburl.getConnection();
        Statement statement = connection.createStatement();

        String str = "select course_name,count(course_name) from user_course where course_name is  not null and creat >= CURRENT_DATE - INTERVAL '1 month' and creat<= CURRENT_DATE group by course_name;";

        ResultSet resultSet = statement.executeQuery(str);
        while (resultSet.next()){
            Popular popular = new Popular();
            popular.setName(resultSet.getString("course_name"));
            popular.setHowMany(resultSet.getInt("count"));
            list.add(popular);
        }

        return list;
    }
}
