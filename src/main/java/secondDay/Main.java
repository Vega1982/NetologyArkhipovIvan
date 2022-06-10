package secondDay;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class Main {


    public static int test = 0;

    public static void main(String[] args) throws InterruptedException {

        // Ожидаем: -w 1 -b 1 -count 1 -m 2
        System.out.println("Ehi Pomodoro. Напиши пожалуйста команду");
        String[] cmd = new Scanner(System.in).nextLine().split(" ");

        // переменная для длительности работы
        int work = 50;
        // переменная для длительности отдыха
        int breake = 10;
        // переменные для прогресс-бара
        int sizebreak = 30;
        int sizework = 30;
        // признак вызова помощи в программе
        int help = 0;
        // счётчик ходов
        int count = 1;
        // ограничитель, чтобы умножитель не был больше чем кол-во ходов (это мне имеет смысла потому-что)
        int multiCap = 1;

        // основной цикл парсинга
        for (int i = 0; i < cmd.length; i++) {
            switch (cmd[i]) {
                case "--help" -> {
                    helpPrinter();
                    help = 1;
                }
                case "-w" -> work = Integer.parseInt(cmd[++i]);
                case "-b" -> breake = Integer.parseInt(cmd[++i]);
                case "-count" -> count = Integer.parseInt(cmd[++i]);
                case "-m" -> multiCap = Integer.parseInt(cmd[++i]);
            }
        }

        // снижаем умножитель до ко-ва ходов
        if (multiCap > count) {
            multiCap = count;
        }

        // очновной цикл работы приложения
        if (help == 0) {
            long startTime = System.currentTimeMillis();
            for (int i = 1; i <= count; i++) {

                // увеличиваем умножитель с каждым ходом
                if (i <= multiCap) {
                    work = work * i;
                }
                // вызываем метод прогресс-бара
                timer(work, breake, sizebreak, sizework);

            }
            long endTime = System.currentTimeMillis();
            // выводим кол-во прошедшего времени
            System.out.println("Pomodor таймер истек: " + (endTime - startTime) / (1000 * 60) + " min");
        }

    }

    // вызываем прогресс-барЫ
    public static void timer(int work, int breake, int sizebreak, int sizework) throws InterruptedException {

        printProgress("Work Progress::  ", work, sizework);

        printProgress("Break Progress:: ", breake, sizebreak);
    }

    // метод прогре--бара из инета )
    private static void printProgress(String process, int time, int size) throws InterruptedException {
        int length;
        int rep;
        length = 60 * time / size;
        rep = 60 * time / length;
        int stretchb = size / (3 * time);
        for (int i = 1; i <= rep; i++) {
            double x = i;
            x = 1.0 / 3.0 * x;
            x *= 10;
            x = Math.round(x);
            x /= 10;
            double w = time * stretchb;
            double percent = (x / w) * 1000;
            x /= stretchb;
            x *= 10;
            x = Math.round(x);
            x /= 10;
            percent = Math.round(percent);
            percent /= 10;
            System.out.print(process + percent + "% " + (" ").repeat(5 - (String.valueOf(percent).length())) + "[" + ("#").repeat(i) + ("-").repeat(rep - i) + "]    ( " + x + "min / " + time + "min )" + "\r");
            if (test == 0) {
                TimeUnit.SECONDS.sleep(length);
            }
        }
        System.out.println();
    }

    // метод вывода помощи
    public static void helpPrinter() {
        System.out.println(
                "\n\nPomodoro - сделай свое время более эффективным\n");
        System.out.println(
                "	-w <time>: время работы, сколько хочешь работать.\n");
        System.out.println(
                "	-b <time>: время отдыха, сколько хочешь отдыхать.\n");
        System.out.println(
                "	-count <count>: количество итераций.\n");
        System.out.println(
                "	--help: меню помощи.\n");
    }

}
