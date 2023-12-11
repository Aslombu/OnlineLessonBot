package org.example.bot.myBot;

import lombok.SneakyThrows;
import org.example.bot.model.Lesson;
import org.example.bot.model.UserStatus;
import org.example.bot.model.Users;
import org.example.bot.server.LessonService;
import org.example.bot.server.UserService;
import org.example.bot.ui.Replys;
import org.example.bot.ui.Text;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.util.List;


public class MyBot extends TelegramLongPollingBot {
    public MyBot(String s){
        super(s);
    }
    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()){
            Message message = update.getMessage();
            String chatId = message.getChatId().toString();
            String text = message.getText();

            Users users = UserService.findChatId(chatId);
            if (users == null){
                if (message.hasContact()){
                    Contact contact = message.getContact();
                    Users users1 = new Users();
                    users1.setChatid(contact.getUserId().toString());
                    users1.setUsername(message.getChat().getUserName());
                    users1.setName(contact.getFirstName());
                    users1.setPhonenomer(contact.getPhoneNumber());
                    users1.setUserstatus(UserStatus.START);
                    UserService.add_users(users1);
                    execute(Replys.MainMenyu(chatId));
                }else {
                    execute(Replys.getContact(chatId));
                }
                return;
            }
            if (users.getUserstatus().equals(UserStatus.TEST) && message.hasDocument()){
                UserService.userStatus(chatId,UserStatus.KURS);
                execute(Replys.Todo_lesson(chatId));
            }

            if (text.equals("/start") || text.equals(Text.mainmenyu)){
                UserService.userStatus(chatId,UserStatus.START);
                execute(Replys.MainMenyu(chatId));
            }

            if(users.getUserstatus().equals(UserStatus.START) && text.equals(Text.Courses)){
                UserService.userStatus(chatId,UserStatus.REGESTRD);
                execute(Replys.coursesShow(chatId));
            }else if (users.getUserstatus().equals(UserStatus.START) && text.equals(Text.Trending)){
                execute(Replys.TopTrending(chatId));
            }
            else if (users.getUserstatus().equals(UserStatus.START) && text.equals(Text.Popular)){
                execute(Replys.TopCourse(chatId));
            }else if (users.getUserstatus().equals(UserStatus.START) && text.equals(Text.dasturchilar)){
                execute(Replys.Dasturchilar(chatId));
            }

            else if (users.getUserstatus().equals(UserStatus.START) && text.equals(Text.MyCourses)){
                UserService.userStatus(chatId,UserStatus.KURS);
                execute(Replys.MyCourses(chatId));
            }else if(users.getUserstatus().equals(UserStatus.KURS) && text.equals(Text.back)){
                UserService.userStatus(chatId,UserStatus.START);
                execute(Replys.MainMenyu(chatId));
            }else if (users.getUserstatus().equals(UserStatus.KURS) && (text.equals(Text.Java) || text.equals(Text.C3) || text.equals(Text.Pyton) || text.equals(Text.Flutter) || text.equals(Text.JavaScript))){

                execute(Replys.findLesson(chatId,text));

            }else if (users.getUserstatus().equals(UserStatus.REGESTRD) && (text.equals(Text.Backend) || text.equals(Text.Frontend) || text.equals(Text.Mobile))) {
              UserService.userStatus(chatId,UserStatus.MAIN);
                execute(Replys.show_corses_text(chatId,text));
            }


            else if (users.getUserstatus().equals(UserStatus.MAIN) &&(text.equals(Text.Java) || text.equals(Text.C3) || text.equals(Text.Pyton) || text.equals(Text.Flutter) || text.equals(Text.JavaScript))){
                     execute(Replys.courses_get(chatId,text));
            }

            else if (users.getUserstatus().equals(UserStatus.MAIN) && text.equals(Text.back)){
                UserService.userStatus(chatId,UserStatus.REGESTRD);
                execute(Replys.coursesShow(chatId));
            }

            else if (users.getUserstatus().equals(UserStatus.REGESTRD) && text.equals(Text.back)) {
                UserService.userStatus(chatId,UserStatus.START);
                execute(Replys.MainMenyu(chatId));

            }

            else if (users.getUserstatus().equals(UserStatus.START)){
                execute(Replys.MainMenyu(chatId));
            }
        }else if (update.hasCallbackQuery()){
            String text = update.getCallbackQuery().getData();
            String chatid = update.getCallbackQuery().getMessage().getChatId().toString();
            Users users = UserService.findChatId(chatid);
            String string = LessonService.findlessonid(text);
             if(users.getUserstatus().equals(UserStatus.TEST) && (text.equals(Text.ortga) || (text.equals(Text.Java) || text.equals(Text.C3) || text.equals(Text.Pyton) || text.equals(Text.Flutter) || text.equals(Text.JavaScript)))){
                UserService.userStatus(chatid,UserStatus.KURS);
                execute(Replys.userCourseStatus(chatid,text));
            }



            if (users.getUserstatus().equals(UserStatus.MAIN) && (text.equals(Text.Java) || text.equals(Text.Pyton) || text.equals(Text.C3) || text.equals(Text.Flutter) || text.equals(Text.JavaScript))) {
                    execute(Replys.Add_courses(chatid, text));
                }
            else if (users.getUserstatus().equals(UserStatus.KURS) &&  (text.equals(Text.Java) || text.equals(Text.Pyton) || text.equals(Text.C3) || text.equals(Text.Flutter) || text.equals(Text.JavaScript)) ){
                    execute(Replys.userCourseStatus(chatid,text));
                }
            else if(users.getUserstatus().equals(UserStatus.KURS) && text.equals(string)){
                UserService.userStatus(chatid,UserStatus.TEST);
                execute(Replys.VedioSend(chatid,string));
                }
            List<Lesson> list = LessonService.findforVedio(text);
             if (users.getUserstatus().equals(UserStatus.TEST) && text.equals(list.get(0).getId().toString())){

                execute(Replys.TestIshlash(chatid,list));
            }

            }

        }




    @Override
    public String getBotUsername() {
        return "Aslombu_academybot";
    }

}
