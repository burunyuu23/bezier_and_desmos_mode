package ru.vsu.edu.shlyikov_d_g.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.script.*;

public class Parser {
    // a*x^b
    static Element x;
    static Element y;
    static Element b;

    public static Element[] parsing(String str){
        x = new Element(1, 1);
        y = new Element(1, 1);
        b = new Element(0, 1);

        boolean flagY = false;
        boolean flagX= false;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == 'y') {
                flagY = true;
                if (i != 0) {
                    String num = "";
                    for (int j = 0; j < i; j++) {
                        num = num.concat(String.valueOf(str.charAt(j)));
                    }
                    if (num.equals("-")) y.setA(-1);
                    else y.setA(Integer.parseInt(num));
                }
                if (str.indexOf('^') != -1 && (str.indexOf('x') != str.indexOf('^')-1)) {
                    String num = "";
                    for (int j = str.indexOf('^') + 1; j < str.indexOf(' '); j++) {
                        num = num.concat(String.valueOf(str.charAt(j)));
                    }
                    if (!num.equals("")) y.setB(Integer.parseInt(num));
                }
            }
            else if (c == 'x'){
                flagX = true;
                    String num = "";
                    for (int j = str.indexOf('=')+2; j < i; j++) {
                        num = num.concat(String.valueOf(str.charAt(j)));
                }
                    if (num.equals("-")) x.setA(-1);
                    else if (!num.equals("")) x.setA(Integer.parseInt(num));
                    if (str.indexOf('^') != -1 && (str.indexOf('y') != str.lastIndexOf('^')-1)) {
                        num = "";
                        int j = i+2;
                        while(j < str.length() && str.charAt(j) != ' '){
                            num = num.concat(String.valueOf(str.charAt(j)));
                            j++;
                        }
                        i = j;
                        if (!num.equals("")) x.setB(Integer.parseInt(num));
                    }
            }
            else if ((c == '-' || c == '+') && flagX && flagY){
                String num = "";
                for (int j = i; j < str.length(); j++) {
                    if (str.charAt(j) != ' ') {
                        num = num.concat(String.valueOf(str.charAt(j)));
                    }
                }
                    if (!num.equals("")) b.setA(Integer.parseInt(num));
                break;
            }
        }

        return new Element[]{y,x,b};
    }

    public static void parse(String string){
        List<String> allMatches = new ArrayList<>();
        Matcher m = Pattern.compile("-?\\w*x(\\^?\\w+)?")
                .matcher(string);
        while (m.find()) {
            allMatches.add(m.group());
        }
        System.out.println(allMatches);
    }


}
