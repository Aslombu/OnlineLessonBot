package org.example.bot.server;

import lombok.SneakyThrows;
import org.example.bot.dburl.Dburl;
import org.example.bot.model.Lesson;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class LessonService {
    @SneakyThrows
    public static String findlessonid(String text) {
        List<Lesson>list = new ArrayList<>();
        Connection connection = Dburl.getConnection();
        Statement statement = connection.createStatement();
        String str ="select * from lessons where id = '%s';";
        return text;
    }

    @SneakyThrows
    public static ResultSet findcoursByLesson(String text) {
        Connection connection = Dburl.getConnection();
        Statement statement = connection.createStatement();
        String str = "select * from lessons where course_name = '%s';";
        ResultSet resultSet = statement.executeQuery(String.format(str,text));
        return resultSet;
    }

    @SneakyThrows
    public static List<Lesson> findlessonidNumer(String text) {
        List<Lesson> list = new ArrayList<>();
        Connection connection = Dburl.getConnection();
        Statement statement = connection.createStatement();
        String str = "select * from lessons where course_name = '%s' order by number;";
        ResultSet resultSet = statement.executeQuery(String.format(str,text));
        while (resultSet.next()) {

            Lesson lesson = new Lesson();
            lesson.setId(resultSet.getObject("id", UUID.class));
            lesson.setNumber(resultSet.getInt("number"));
            lesson.setForVedio(resultSet.getString("for_vedio"));


            list.add(lesson);
        }
        return list;

    }

    @SneakyThrows
    public static List<Lesson> findforVedio(String text) {
        List<Lesson>list = new ArrayList<>();
        Connection connection = Dburl.getConnection();
        Statement statement = connection.createStatement();
        String str = "select * from lessons where id = '%s';";
        ResultSet resultSet = statement.executeQuery(String.format(str,text));
      while (resultSet.next()){
          Lesson lesson = new Lesson();
          lesson.setId(UUID.fromString(text));
          lesson.setForVedio(resultSet.getString("for_vedio"));
          lesson.setName(resultSet.getString("name"));
          lesson.setNumber(resultSet.getInt("number"));
          lesson.setLesson_question(resultSet.getString("lesson_question"));
          lesson.setCoursName(resultSet.getString("course_name"));
          list.add(lesson);
      }
      return list;

    }
}
