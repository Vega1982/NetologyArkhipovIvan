package firstDay;

import java.text.DecimalFormat;
import java.util.Scanner;

public class Main {

    // задаём слово
    static String word = "javalove";

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);


        System.out.println("Угадай слово");
        int lengthWord = word.length();

        // задаём прочерки на неизвестных буквах
        String maskWord = "-".repeat(lengthWord);
        System.out.println(maskWord);

        // Переменная для счётчика ходов
        int counterValue = 0;

        // Массив введённых букв для проверки повторений
        StringBuilder lastChars = new StringBuilder();

        do {
            System.out.println("Введите букву");
            // Получаем от игрока букву
            char c = input.next().charAt(0);

            // Проверяем, нет ли повторения ввода букв. Просим ввода уникальной буквы
            while (repeaterChecker(lastChars, c)) {
                System.out.println("Вы повторяетесь, уважаемый(ая)! Введите другую букву.");
                c = input.next().charAt(0);
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
        effCounter(counterValue, word.length());
        System.out.println("Ваш приз: " + counterValue);

    }


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
    public static boolean repeaterChecker(StringBuilder lastChars, char c) {
        //String tempString = lastChars.toString();
        if (lastChars.indexOf(String.valueOf(c)) == -1) {
            return false;
        }
        return true;
    }

    // метод для подсчёта эффективности
    public static void effCounter(int counterValue, int worldLength) {
        double eff = ((worldLength / counterValue) * 100.00D);
        //DecimalFormat decimalFormat = new DecimalFormat( "#.###" );
        //String result = decimalFormat.format(eff);
        System.out.println("Ваша эффективность: " + eff + "%");
    }
}
