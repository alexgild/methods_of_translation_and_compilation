package translation.calculator.gild.com;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        System.out.println("Enter a string to calculated:");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        Lexer lexer = new Lexer(in);
        Parser parser = new Parser(lexer);
        System.out.println("Answer: " + parser.parseExpression());

    }
}
