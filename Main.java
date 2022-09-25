import java.io.IOException;
import java.util.Scanner;
import java.util.TreeMap;


public class Main {

    public static void main(String[] args) throws Exception {

        System.out.println("На ноль операции делать нельзя");
        Scanner cs = new Scanner(System.in);
        System.out.println("Введите выражение.");
        String inpu = cs.nextLine();
        inpu = inpu.replace(" ", "");
        System.out.println(calc(inpu));

    }
    public static String calc(String input) throws Exception {
        Converter converter = new Converter();
        String[] index = {"+", "-", "*", "/"};
        String[] iP = {"\\+", "-", "\\*", "/"};
        String[] roman = {"I", "II", "III", "V", "IV", "VI", "VII", "VIII", "IX", "X"};

        int action = -1;
        for (int i = 0; i < index.length; i++) {

            if (input.contains(index[i])) {
                action = i;
                break;
            }
        }

        if (action == -1) {
                throw new Exception("Введите один из знаков: +, -, *, /");
        }

        String mass[] = input.split(iP[action]);

        boolean isRoman=false;

        int a = 0;
        int b = 0;
        try {
            a = Integer.parseInt(mass[0]);
            b = Integer.parseInt(mass[1]);
        }catch (Exception e){
            isRoman = true;
        }

        if(isRoman == true) {
            int count = 0;
            for (int i = 0; i < roman.length; i++) {
                if (mass[0].equals(roman[i])) {
                    count++;
                }
                if (mass[1].equals(roman[i])) {
                    count++;
                }
            }
            if(count != 2){
                throw new Exception("Числа должны быть в одной системе счисления");
            }

            a = converter.romanToInt(mass[0]);
            b = converter.romanToInt(mass[1]);
        }

        if (a < 1 || a > 10 || b < 1 || b > 10) {
            throw new Exception("Числа должны быть в диапазоне от 1 до 10");
        }

        int result=0;
        switch (index[action]) {
            case "+":
                result = (a + b) ;
                break;
            case "-":
                result = (a - b) ;
                break;
            case "/":
                result = (a / b);
                break;
            case "*":
                result = (a * b) ;
                break;
        }
        if(isRoman==true) {
            String romanDigit = converter.intToRoman(result);
            return romanDigit;
        }else {
            return result+"";
        }
    }
}
class Converter {
    TreeMap<Character, Integer> romanKeyMap = new TreeMap<>();
    TreeMap<Integer, String> arabianKeyMap = new TreeMap<>();

    public Converter() {
        romanKeyMap.put('I', 1);
        romanKeyMap.put('V', 5);
        romanKeyMap.put('X', 10);
        romanKeyMap.put('L', 50);
        romanKeyMap.put('C', 100);

        arabianKeyMap.put(100, "C");
        arabianKeyMap.put(90, "XC");
        arabianKeyMap.put(50, "L");
        arabianKeyMap.put(40, "XL");
        arabianKeyMap.put(10, "X");
        arabianKeyMap.put(9, "IX");
        arabianKeyMap.put(5, "V");
        arabianKeyMap.put(4, "IV");
        arabianKeyMap.put(1, "I");
    }
    public String intToRoman(int number) {
        String roman = "";
        int arabianKey;
        do {
            arabianKey = arabianKeyMap.floorKey(number);
            roman += arabianKeyMap.get(arabianKey);
            number -= arabianKey;

        } while (number != 0);
        return roman;

    }
    public int romanToInt(String s) {
        int end = s.length() - 1;
        char[] arr = s.toCharArray();
        int arabian;
        int result = romanKeyMap.get(arr[end]);
        for (int i = end - 1; i >= 0; i--) {
            arabian = romanKeyMap.get(arr[i]);

            if (arabian < romanKeyMap.get(arr[i + 1])) {
                result -= arabian;
            } else {
                result += arabian;
            }
        }
        return result;

    }
}