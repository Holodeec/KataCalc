import java.util.Scanner;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("На ноль операции делать нельзя");
        System.out.println("Введите выражение");
        Scanner cs = new Scanner(System.in);
        String input =cs.nextLine();

        input.replace(" ","");
        System.out.println(calc(input));
    }
    public static String calc(String line) throws Exception {
        Converter converter = new Converter();
        String[] action = {"+","-","*","/"};
        String[] ip = {"\\+","-","\\*","/"};
        String[] rom ={"I","II","III","IV","V","VI","VII","VIII","IX","X"};
        int index = -1;

        for(int i = 0; i < action.length;i++) {
        if(line.contains(action[i])) {
        index = i;
        }
        }
        if(index==-1){
            throw new Exception("Введите один из знаков +, -, *, /");
        }

        String[] input = line.split(ip[index]);

        int a = 0;
        int b = 0;
        boolean roman = false;
        try {
            a =Integer.parseInt(input[0]);
            b =Integer.parseInt(input[1]);
        }catch (Exception e) {
            roman = true;
        }

        if(roman) {
            int count = 0 ;
            for (int i = 0; i < rom.length; i++) {
            if (input[0].equals(rom[i]))  {
                count++;
            }
            if (input[1].equals(rom[i]))  {
                count++;
            }
            }
            if(count != 2 ) {
                throw new Exception("Числа должны быть в одной системе исчисления");
            }


            a = converter.romanToInt(input[0]);
            b = converter.romanToInt(input[1]);

        }
        if(a < 1 || a > 10 || b < 1 || b > 10) {
           throw new Exception("Введенные числа должны быть от 1 до 10");
        }
        int result = 0;
        switch (action[index]){
            case "+" :
               result = a+b;
                break;
            case "-" :
               result = a-b;
                break;
            case "*" :
               result = a*b;
                break;
            case "/" :
               result = a/b;
                break;
        }
        if(roman == true) {
             String text = converter.intToRoman(result);
             return text;
        } else {
            return result+"";
        }

    }

}
class Converter {
    public int romanToInt(String x) {
        TreeMap<Character, Integer> romanKey = new TreeMap<>();

        romanKey.put('I', 1);
        romanKey.put('V', 5);
        romanKey.put('X', 10);
        romanKey.put('L', 50);
        romanKey.put('C', 100);

        int leng = x.length()-1;
        char[] arr = x.toCharArray();
        int mass = 0;
        int result = romanKey.get(arr[leng]);
        for (int i = leng-1; i >= 0;i--) {
            mass = romanKey.get(arr[i]);

            if(mass<romanKey.get(arr[i+1])) {
                result-=mass;
            } else {
                result+=mass;
            }

        }
        return result;
    }
    public String intToRoman (int x) {
        TreeMap<Integer, String> arabianKey = new TreeMap<>();
        arabianKey.put(100, "C");
        arabianKey.put(50, "L");
        arabianKey.put(10, "X");
        arabianKey.put(5, "V");
        arabianKey.put(3, "III");
        arabianKey.put(2,"II");
        arabianKey.put(1, "I");

        String roman ="";
        int arabian ;
        do {
            arabian = arabianKey.floorKey(x);

            roman +=arabianKey.get(arabian);
            x -=arabian;

        }while(x != 0);
        return roman;

    }

}
