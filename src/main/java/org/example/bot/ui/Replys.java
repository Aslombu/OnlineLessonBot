package org.example.bot.ui;
import lombok.SneakyThrows;
import org.example.bot.dburl.Dburl;
import org.example.bot.model.Lesson;
import org.example.bot.model.Popular;
import org.example.bot.server.CourseService;
import org.example.bot.server.LessonService;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.File;
import java.io.Serializable;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

//Reply
public class Replys {
    // MainMenu
    @SneakyThrows
    public static SendMessage MainMenyu(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId,"MainMenyu");

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(Text.Courses);
        keyboardRow.add(Text.MyCourses);
        rows.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardRow.add(Text.Popular);
        keyboardRow.add(Text.Trending);
        rows.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardRow.add(Text.dasturchilar);
        rows.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(rows);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }
    public static SendMessage getContact(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Telefon raqam kirting");
        KeyboardRow keyboardRow = new KeyboardRow();
        List<KeyboardRow> list = new ArrayList<>();
        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setText("\uD83D\uDCF1 Telfon nomer");
        keyboardButton.setRequestContact(true);
        keyboardRow.add(keyboardButton);
        list.add(keyboardRow);
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setKeyboard(list);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        return sendMessage;

    }

    @SneakyThrows
    public static SendMessage coursesShow(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId,Text.Courses);
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(Text.Backend);
        keyboardRow.add(Text.Frontend);
        rows.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardRow.add(Text.Mobile);
        keyboardRow.add(Text.back);
        rows.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(rows);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;

    }


    @SneakyThrows
    public static SendMessage show_corses_text(String chatId, String text) {
        SendMessage sendMessage = new SendMessage(chatId,text);
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        List<KeyboardRow>list = new ArrayList<>();

        Connection connection = Dburl.getConnection();
        Statement statement = connection.createStatement();
        String str = "select * from courses where level = '%s'";
        ResultSet resultSet = statement.executeQuery(String.format(str,text));

        if (resultSet != null) {
//        int count = 0;
            while (resultSet.next()) {
                KeyboardRow keyboardRow = new KeyboardRow();
                keyboardRow.add(resultSet.getString("name"));
                list.add(keyboardRow);
              //  count = 1;
            }
        }else {
//        if (count == 0) {
            sendMessage.setText("profilactika");
            return sendMessage;
        }
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(Text.back);
        keyboardRow.add(Text.mainmenyu);
        list.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(list);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }

    @SneakyThrows
    public static SendPhoto courses_get(String chatId, String text) {
        String str = null;
        String str2 = null;
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>>rows = new ArrayList<>();
        List<InlineKeyboardButton>list = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();

        switch (text){
            case "Java" -> {
                str = "photo/java/Java.png";
                inlineKeyboardButton.setText("Java haqida tuliqroq ma'lumot");
               inlineKeyboardButton.setUrl("https://online.pdp.uz/roadmap/JavaBackend");
               list.add(inlineKeyboardButton);

            }
            case "Pyton" -> {str = "photo/pyton/Pyton.png";
                inlineKeyboardButton.setText("Pyton haqida tuliqroq ma'lumot");
                inlineKeyboardButton.setUrl("https://online.pdp.uz/course/python-development");
                list.add(inlineKeyboardButton);
             }
            case "C#" -> {
                str = "photo/c#/C#.png";
                inlineKeyboardButton.setText("C#  haqida tuliqroq ma'lumot");
                inlineKeyboardButton.setUrl("https://online.pdp.uz/course/c-development");
                list.add(inlineKeyboardButton);
            }
            case "JavaScript" ->{
                str = "photo/javaScript/JavaScript.png";
                inlineKeyboardButton.setText("JavaScript haqida tuliqroq ma'lumot");
                inlineKeyboardButton.setUrl("https://online.pdp.uz/course/frontend-development");
                list.add(inlineKeyboardButton);
            }
            case "Flutter" -> {
                str = "photo/flutter/Flutter.png";
                inlineKeyboardButton.setText("Flutter haqida tuliqroq ma'lumot");
                inlineKeyboardButton.setUrl("https://online.pdp.uz/roadmap/Flutter");
                list.add(inlineKeyboardButton);
            }
        }


       SendPhoto sendPhoto = new SendPhoto(chatId,new InputFile(new File(str)));







        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("add");
        inlineKeyboardButton1.setCallbackData(text);
        list.add(inlineKeyboardButton1);
        rows.add(list);
        inlineKeyboardMarkup.setKeyboard(rows);
        sendPhoto.setReplyMarkup(inlineKeyboardMarkup);
        return sendPhoto;



    }

    @SneakyThrows
    public static SendMessage Add_courses(String chatid, String text) {
        SendMessage sendMessage = new SendMessage(chatid,text);
        Connection connection = Dburl.getConnection();
        Statement statement = connection.createStatement();
        String str = "select *from user_course where user_id = '%s' and course_name = '%s'";
        ResultSet resultSet = statement.executeQuery(String.format(str,chatid,text));
        if (resultSet.next()){
            sendMessage.setText("bu kurs sizda mavjud");
            return sendMessage;
        }
        PreparedStatement preparedStatement = connection.prepareStatement("insert into user_course(course_name, user_id) values (?,?);");
        preparedStatement.setString(2,chatid);
        preparedStatement.setString(1,text);
        preparedStatement.executeUpdate();
        sendMessage.setText("qushildi");

        return sendMessage;
    }

    @SneakyThrows
    public static SendMessage MyCourses(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId,"Mycourses");

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        List<KeyboardRow>list = new ArrayList<>();
        ResultSet resultSet = CourseService.showCourses(chatId);
        int i =0;
        KeyboardRow keyboardRow = new KeyboardRow();
        while (resultSet.next()) {

            keyboardRow.add(resultSet.getString("course_name"));

            i++;
            if(i==2){

                list.add(keyboardRow);
                keyboardRow = new KeyboardRow();
                i=0;
            }

        }
        if(i<2) {
            keyboardRow.add(Text.back);
            list.add(keyboardRow);
            replyKeyboardMarkup.setKeyboard(list);
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            return sendMessage;
        }else {
            keyboardRow = new KeyboardRow();
            keyboardRow.add(Text.back);
            list.add(keyboardRow);
            replyKeyboardMarkup.setKeyboard(list);
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            return sendMessage;
        }

    }

    @SneakyThrows
    public static SendMessage findLesson(String chatId, String text) {


        Boolean startCourse = CourseService.isStartCourse(chatId, text);
        if (startCourse) {
            SendMessage sendMessage = new SendMessage(chatId, text);
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> rows = new ArrayList<>();
            List<InlineKeyboardButton> list = new ArrayList<>();

            List<Lesson> list1 = LessonService.findlessonidNumer(text);


           int j =0;
            for (int i = 1; i <= list1.size(); i++) {
                j++;
                for (int k = 1; k <= list1.size(); k++) {
                    if (i == list1.get(k-1).getNumber()) {
                        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
                        inlineKeyboardButton.setText(i + " lesson");
                        inlineKeyboardButton.setCallbackData(list1.get(k-1).getId().toString());
                        list.add(inlineKeyboardButton);


                    }


                }
                if (j == 2) {
                    rows.add(list);
                    list = new ArrayList<>();
                    j = 0;
                }

            }
            if(j<2){
                rows.add(list);
            }

            inlineKeyboardMarkup.setKeyboard(rows);
            sendMessage.setReplyMarkup(inlineKeyboardMarkup);
            return sendMessage;


        } else {
            SendMessage sendMessage = new SendMessage(chatId,(text+"  start bering"));
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>>rows = new ArrayList<>();
            List<InlineKeyboardButton> list = new ArrayList<>();
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText("start");
            inlineKeyboardButton.setCallbackData(text);
            list.add(inlineKeyboardButton);
            rows.add(list);
            inlineKeyboardMarkup.setKeyboard(rows);
            sendMessage.setReplyMarkup(inlineKeyboardMarkup);
            return sendMessage;
        }

    }

    public static SendMessage userCourseStatus(String chatid, String text) {
        SendMessage sendMessage = new SendMessage(chatid,text);

        CourseService.StatusChange(text, chatid);
            findLesson(chatid,text);
            return sendMessage;

    }

    @SneakyThrows
    public static SendVideo VedioSend(String chatid, String text) {
        List<Lesson> list1 = LessonService.findforVedio(text);

        SendVideo sendVideo = new SendVideo(chatid,new InputFile(list1.get(0).getForVedio()));

       sendVideo.setCaption((list1.get(0).getNumber()+" lesson "+ list1.get(0).getName()));
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>>rows = new ArrayList<>();
        List<InlineKeyboardButton>list = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText("Testni bajarish✅");
        inlineKeyboardButton.setCallbackData((text));

        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText(Text.back);
        inlineKeyboardButton1.setCallbackData(Text.ortga);
        list.add(inlineKeyboardButton1);

        list.add(inlineKeyboardButton);
        rows.add(list);
        inlineKeyboardMarkup.setKeyboard(rows);
        sendVideo.setReplyMarkup(inlineKeyboardMarkup);
        return sendVideo;

    }

    public static SendMessage TestIshlash(String chatid, List<Lesson> list) {
        SendMessage sendMessage = new SendMessage(chatid,"test");
        sendMessage.setText(list.get(0).getLesson_question()+"\n\nTesni ishlab txt yoki pdf yoki word filga olib junating");
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton>list1 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText(Text.back);
        inlineKeyboardButton1.setCallbackData(list.get(0).getCoursName());
        list1.add(inlineKeyboardButton1);


        rows.add(list1);
        inlineKeyboardMarkup.setKeyboard(rows);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }

    public static SendMessage Todo_lesson(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId,"Tesni bajarganingiz uchun rahmat");
        return sendMessage;

    }

    public static SendPhoto Dasturchilar(String chatId) {
        SendPhoto sendPhoto = new SendPhoto(chatId,new InputFile(new File("photo/dasturchi/dasturchi.png")));
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>>rows = new ArrayList<>();
        List<InlineKeyboardButton>list = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText("Aslombu");
        inlineKeyboardButton.setUrl("https://t.me/aslombu");
        list.add(inlineKeyboardButton);
        inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText("Yusufbek");
        inlineKeyboardButton.setUrl("https://t.me/Developer_JB");
        list.add(inlineKeyboardButton);
        rows.add(list);
        inlineKeyboardMarkup.setKeyboard(rows);
        sendPhoto.setReplyMarkup(inlineKeyboardMarkup);
        sendPhoto.setCaption("Dasturchi bilan bog'lanish link ustuga bosing ");
        sendPhoto.setParseMode("Markdown");
        return sendPhoto;
    }

    public static SendMessage TopCourse(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId,"Top course");
        String str ="";
        List<Popular> list = CourseService.CourseSonList();
        List<Popular> list1 = list.stream()
                .sorted(Comparator.comparingInt(Popular::getHowMany))
                .toList();
        if (list1.isEmpty()) {
              str= "profilaktika";
        }else if (list1.size()>=3) {

            int j = 1;
            for (int i = list1.size(); i >= 0; i--) {
                if (list1.size() - 3 < i) {
                    str += j + " ." + list1.get(i - 1).getName() + "\n";
                    j++;
                }

            }
        }else {
            int j =1;
            for (int size = list1.size()-1; size > 0; size--) {
                str += j +" ."+ list1.get(size).getName()+"\n";
            }

        }



        sendMessage.setText(str);
        return sendMessage;
    }

    public static SendMessage TopTrending(String chatId) {

        SendMessage sendMessage = new SendMessage(chatId,"Top Trending");
        String str ="";
        List<Popular> list = CourseService.CourseTrending();
        List<Popular> list1 = list.stream()
                .sorted(Comparator.comparingInt(Popular::getHowMany))
                .toList();
        if (list1.size()>=3) {
            int j = 1;
            for (int i = list1.size() - 1; i >= 0; i--) {
                if (list1.size() - 4 < i) {
                    str += j + " ." + list1.get(i).getName() + "\n";
                    j++;
                }

            }


            sendMessage.setText(str);
            return sendMessage;
        }else {
            int j = 1;
            for (int i = list1.size() - 1; i >= 0; i--) {
                str+= j++ +"  ."+ list1.get(i).getName()+"\n";

            }
            sendMessage.setText(str);
            return sendMessage;
        }

    }
}
