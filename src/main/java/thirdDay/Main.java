package thirdDay;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Echo и Pomodoro bot
 * С использованием библиотеки org.telegram:telegrambots:6.0.1
 */
public class Main {

    private static final ConcurrentHashMap<Pomodoro.Timer, Long> userTimers = new ConcurrentHashMap<>();

    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        var pomodoroBot = new Pomodoro();
        botsApi.registerBot(new Pomodoro());
        new Thread(() -> {
            try {
                pomodoroBot.run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).run();
    }

    static class Pomodoro extends TelegramLongPollingBot {

        enum TimerType {
            WORK,
            BREAK
        }

        static record Timer(Instant time, TimerType timerType) {}

        public Pomodoro() {
            super();

        }

        @Override
        public String getBotUsername() {
            return "Pomodoro bot";
        }

        @Override
        public String getBotToken() {
            return "5566383705:AAGHcGSNwC1Ibx3E3VIq-439lJzMv0e0AC8";
        }

        @Override
        public void onUpdateReceived(Update update) {
            if (update.hasMessage() && update.getMessage().hasText()) {
                var args = update.getMessage().getText().split(" ");
                var userId = update.getMessage().getChatId();
                if (args.length >= 1) {
                    var workTime = Instant.now().plus(Long.parseLong(args[0]), ChronoUnit.MINUTES);
                    userTimers.put(new Timer(workTime, TimerType.WORK), userId);
                    if (args.length == 2) {
                        var breakTime = workTime.plus(Long.parseLong(args[1]), ChronoUnit.MINUTES);
                        userTimers.put(new Timer(breakTime, TimerType.BREAK), userId);
                    }
                }

                sendMsg(update.getMessage().getChatId(), "Time to hard work!");
            }
        }

        private void sendMsg(Long chatId, String msgStr) {
            SendMessage msg = new SendMessage(chatId.toString(), msgStr);
            try {
                execute(msg);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }


        public void run() throws InterruptedException {
            while (true) {
                System.out.printf("Number of user's timers = %d\n", userTimers.size());
                userTimers.forEach((time, userId) -> {
                    System.out.printf("Check userId = %d, userTime = %s, now = %s\n", userId, time.toString(), Instant.now());
                    if (Instant.now().isAfter(time.time)) {
                        userTimers.remove(time);
                        switch (time.timerType) {
                            case WORK -> sendMsg(userId, "It's time to rest!");
                            case BREAK -> sendMsg(userId, "Timer work ended.");
                        }
                    }
                });
                Thread.sleep(1000);
            }
        }
    }

    static class EchoBot extends TelegramLongPollingBot {

        @Override
        public String getBotUsername() {
            return "Echo bot";
        }

        @Override
        public String getBotToken() {
            return "5566383705:AAGHcGSNwC1Ibx3E3VIq-439lJzMv0e0AC8";
        }

        @Override
        public void onUpdateReceived(Update update) {
            if (update.hasMessage() && update.getMessage().hasText()) {
                SendMessage msg = new SendMessage(update.getMessage().getChatId().toString(),
                        update.getMessage().getText());
                try {
                    execute(msg);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
