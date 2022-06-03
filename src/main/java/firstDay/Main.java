package firstDay;

import java.text.DecimalFormat;
import java.util.Scanner;

public class Main {

    // задаём переменню для слова
    static String word;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Игра Угадай слово.");
        System.out.println("Введите слово для отгадывания: ");
        word = input.nextLine();
        // переводим слово в нижний регистр
        word = word.toLowerCase();
        //System.out.println("Проверка. Вы ввели слово: " + word);

        int lengthWord = word.length();

        // проверка на пустое слово
        while (lengthWord == 0) {
            System.out.println("Слово должно быть из 1 или более букв. Повторите ввод: ");
            word = input.nextLine();
            // переводим слово в нижний регистр
            word = word.toLowerCase();
            lengthWord = word.length();
        }
        System.out.println("В загаданном слове: " + lengthWord + " букв.");


        // задаём прочерки на неизвестных буквах
        String maskWord = "-".repeat(lengthWord);
        System.out.println(maskWord);

        // Переменная для счётчика ходов
        int counterValue = 0;

        // Массив введённых букв для проверки повторений
        StringBuilder lastChars = new StringBuilder();

        do {
            System.out.println("Введите букву: ");
            // Получаем от игрока букву, переводим в нижний регистр
            char c = input.next().toLowerCase().charAt(0);

            // Проверяем, нет ли повторения ввода букв. Просим ввода уникальной буквы
            while (repeaterChecker(lastChars, c)) {
                System.out.println("Вы повторяетесь, уважаемый(ая)! Введите другую букву.");
                c = input.next().toLowerCase().charAt(0);
            }
            // добавляем введённую букву в массив ранее введённых
            lastChars.append(c);

            // Инкрементируем число ходов
            counterValue++;


            // Если буква есть в слове
            if (word.indexOf(c) >= 0) {
                System.out.println("Удача");

                // Перебираем слово, заменяет маску на букву от игрока при совпадении
                for (char elem : word.toCharArray()) {
                    if (elem == c) {
                        maskWord = replaceMaskLetter(c, maskWord);
                    }
                }
                System.out.println(maskWord);
            } else {
                System.out.println("Промах, поробуй еще раз");
                System.out.println(maskWord);
            }
        } while (maskWord.contains("-"));
        System.out.println("Поздравляем! Вы выиграли!");
        System.out.println("Букв в загаданном слове: " + word.length());
        System.out.println("Вы затратили попыток: " + counterValue);
        // выведем % эффективности и приз

        effCounter(counterValue, word.length());
    }

    // Метод для вписывания букв в слово-маску

    /**
     * @param c        буква от игрока
     * @param maskWord слово из прочерков (и) найденными буквами
     * @return маска с найденными буквами
     */
    public static String replaceMaskLetter(char c, String maskWord) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == c) {
                stringBuilder.append(c);
            } else if (maskWord.charAt(i) != '-') {
                stringBuilder.append(maskWord.charAt(i));
            } else {
                stringBuilder.append("-");
            }
        }
        return stringBuilder.toString();
    }

    // Метод для проверки повтора введённой буквы
    // Сравнивает ввод пользователя со всеми предыдущими введёнными буквами
    // Если есть совпадение - возвращает true, иначе false

    /**
     * @param lastChars предыдущая введённая игроком буква
     * @param c         текущая введённая игроком буква
     * @return результат проверки
     */
    public static boolean repeaterChecker(StringBuilder lastChars, char c) {
        //String tempString = lastChars.toString();
        if (lastChars.indexOf(String.valueOf(c)) == -1) {
            return false;
        }
        return true;
    }

    // метод для подсчёта эффективности

    /**
     * @param counterValue кол-во ходов
     * @param worldLength  длина слова
     */
    public static void effCounter(int counterValue, int worldLength) {
        double eff = (((double) worldLength / (double) counterValue) * 100.00D);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String result = decimalFormat.format(eff);
        System.out.println("Ваша эффективность: " + result + "%");
        // определим подарок
        if (eff >= 100) {
            System.out.println("Ваш приз - Автомобиль!");
        } else if (eff > 50 & eff < 100) {
            System.out.println("Ваш приз - Миллион!");
        } else {
            System.out.println("Ваш приз - Тыква!");
        }
    }
}
