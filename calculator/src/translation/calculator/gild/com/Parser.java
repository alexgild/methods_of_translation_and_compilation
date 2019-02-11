package translation.calculator.gild.com;

public class Parser {
    private Lexer lexer;
    boolean bracketsAreCorrect = true;
    Lexer.Lexeme currentLexeme;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    public int parseExpression() {
        //int res = 0;
        Lexer.LexemeType sign = Lexer.LexemeType.ERROR;
        int res = parseTerm();

        while(!lexer.queueIsEmpty()) {
            if(currentLexeme.type.equals(Lexer.LexemeType.SUM)) {
                sign = Lexer.LexemeType.SUM;
            } else if (currentLexeme.type.equals(Lexer.LexemeType.SUBTRACTION)) {
                sign = Lexer.LexemeType.SUBTRACTION;
            }
            int term = parseTerm();
            if(sign.equals(Lexer.LexemeType.SUM)) {
                res += term;
            }
            if (sign.equals(Lexer.LexemeType.SUBTRACTION)) {
                res -= term;
            }
            sign = Lexer.LexemeType.ERROR;
        }
        return res;
    }

  public int parseTerm() {
      Lexer.LexemeType sign = Lexer.LexemeType.ERROR;
      currentLexeme = lexer.getCurrentLexeme();
      int res = parseFactor();

      while(!(currentLexeme.type.equals(Lexer.LexemeType.SUM) || currentLexeme.type.equals(Lexer.LexemeType.SUBTRACTION) || currentLexeme.type.equals(Lexer.LexemeType.ERROR))) {
              if(currentLexeme.type.equals(Lexer.LexemeType.MULTIPLICATION)) {
                  sign = Lexer.LexemeType.MULTIPLICATION;
              } else if (currentLexeme.type.equals(Lexer.LexemeType.DIVISION)) {
                  sign = Lexer.LexemeType.DIVISION;
              }
              int factor = parseFactor();
              if(sign.equals(Lexer.LexemeType.MULTIPLICATION)) {
                  res *= factor;
              }
              if(sign.equals(Lexer.LexemeType.DIVISION)) {
                  res /= factor;
              }
              sign = Lexer.LexemeType.ERROR;
      }
      return res;
  }

    public int parseFactor() {
        int res = 1;
        int power;
        int factor = 0;

        power = parsePower();

        if(!lexer.queueIsEmpty()) {
            currentLexeme = lexer.getCurrentLexeme();

            if(currentLexeme.type.equals(Lexer.LexemeType.POWER)) {
                currentLexeme = lexer.getCurrentLexeme();
                if(currentLexeme.type.equals(Lexer.LexemeType.LEFT_BRACKET)) {
                    bracketsAreCorrect = false;
                    factor = parseExpression();
                } else if(currentLexeme.type.equals(Lexer.LexemeType.RIGHT_BRACKET)) {
                    bracketsAreCorrect = true;
                } else if (currentLexeme.type.equals(Lexer.LexemeType.NUMBER)) {
                    factor = Integer.parseInt(currentLexeme.lexemeText);
                } else {
                    System.out.println("Parse factor error!");
                    return -1;
                }

                for (int i = 0; i < factor; i++) {
                    res *= power;
                }
                return res;
            } else return power;
        } else {
            currentLexeme.type = Lexer.LexemeType.ERROR;
        }
        return power;
    }

    public int parsePower() {
        int res = 0;
        boolean isNegative = false;

        if (currentLexeme.type.equals(Lexer.LexemeType.UNARY_MINUS)) {
            isNegative = true;
        }
        res = parseAtom();
        return isNegative? -res : res;
    }

    public int parseAtom() {
        int res = 0;

        if(currentLexeme.type.equals(Lexer.LexemeType.LEFT_BRACKET)) {
                res = parseExpression();
                bracketsAreCorrect = false;
        } else if (currentLexeme.type.equals(Lexer.LexemeType.RIGHT_BRACKET)) {
            if(!bracketsAreCorrect) {
                bracketsAreCorrect = true;
            } else {
                System.out.println("Brackets error!");
            }
        } else if (currentLexeme.type.equals(Lexer.LexemeType.NUMBER)) {
            res = Integer.parseInt(currentLexeme.lexemeText);
        }
        return res;
    }
}