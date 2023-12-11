package org.example.run;

import lombok.SneakyThrows;
import org.example.bot.myBot.MyBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot (new MyBot("6412231038:AAFS2aF2uZEeziecOvVpMQ"));
        System.out.println(".....");
    }
}