package translation.calculator.gild.com;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayDeque;

public class Lexer {

    private ArrayDeque<Lexeme> lexemes;

    public Lexer(BufferedReader reader) {
        lexemes = new ArrayDeque<>();
        try {
            getLexemes(reader);
        } catch (IOException e) {
            System.out.println("Read error");
        }
    }

    public Lexeme getCurrentLexeme() {
        return lexemes.pop();
    }

    public boolean queueIsEmpty() {
        return lexemes.isEmpty();
    }

    private void getLexemes(BufferedReader reader) throws IOException {
        int c;
        StringBuilder lexemeText = new StringBuilder();
        boolean wasNumber = false;

        while(((c = reader.read()) != '\n')||(lexemeText.length() != 0)) {
                char currentSymbol = (char) c;

                if (Character.isDigit(currentSymbol)) {
                    wasNumber = true;
                    lexemeText = (lexemeText == null ? new StringBuilder("null") : lexemeText).append(String.valueOf(currentSymbol));

                    continue;
                }

                if(lexemeText.length() != 0) {
                    lexemes.add(new Lexeme(LexemeType.NUMBER, lexemeText.toString()));
                    lexemeText = new StringBuilder();
                    //continue;
                }

                switch(currentSymbol) {
                    case ('+'):
                        lexemes.add(new Lexeme(LexemeType.SUM, "+"));
                        continue;
                    case ('-'):
                        if (wasNumber) {
                            lexemes.add(new Lexeme(LexemeType.SUBTRACTION, "-"));
                            wasNumber = false;
                        } else {
                            lexemes.add(new Lexeme(LexemeType.UNARY_MINUS, "-"));
                        }
                        continue;
                    case ('*'):
                        lexemes.add(new Lexeme(LexemeType.MULTIPLICATION, "*"));
                        continue;
                    case ('/'):
                    case (':'):
                        lexemes.add(new Lexeme(LexemeType.DIVISION, "/"));
                        continue;
                    case ('^'):
                        lexemes.add(new Lexeme(LexemeType.POWER, "^"));
                        continue;
                    case ('('):
                        lexemes.add(new Lexeme(LexemeType.LEFT_BRACKET, "("));
                        continue;
                    case (')'):
                        lexemes.add(new Lexeme(LexemeType.RIGHT_BRACKET, ")"));
                        continue;
                    case (' '):
                        continue;
                    case ('\n'):
                        break;
                    default:
                        System.out.println("Insert error");
                        System.exit(-1);
                }
        }
    }


    class Lexeme {
        LexemeType type;
        String lexemeText;

        public Lexeme(LexemeType type, String lexemeText) {
            this.type = type;
            this.lexemeText = lexemeText;
        }
    }

    enum LexemeType {
        NUMBER,
        SUM, //+
        SUBTRACTION, //-
        MULTIPLICATION, //*
        DIVISION, // /
        POWER, //^
        UNARY_MINUS, // unary -
        LEFT_BRACKET, // (
        RIGHT_BRACKET, // )
        ERROR
    }
}
