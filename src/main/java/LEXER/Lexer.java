package LEXER;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    private final List<Token> tokens = new ArrayList<>();
    private final StringTokenizer lines;

    public Lexer(String input) {
        lines = new StringTokenizer(input, ";", true);
    }

    public List<Token> tokenizer() {
        while(lines.hasMoreElements()){
            StringTokenizer words = new StringTokenizer(lines.nextToken());
            while(words.hasMoreElements()){
                String word = words.nextToken();
                boolean matched = false;

                for (TokenType type :
                        TokenType.values()) {
                    Matcher matcher = Pattern.compile(type.pattern).matcher(word);
                    if(matcher.find()) {
                        tokens.add(new Token(type, word));
                        matched = true;
                        break;
                    }
                }

                if(!matched) {
                    tokens.add(new Token(null, word));
                    throw new RuntimeException("INVALID TOKEN: "+ word);
                }
            }
        }
        return tokens;
    }

    public static void main(String[] args) {
        String input = """
                ENTERO num1 = -4;
                ENTERO num2 = +5;
                ENTERO resultado = a + b;
                ENTERO DIV = +4 / 2;
                ENTERO Mul1 = 3 * -9;
        """;

        Lexer lexico = new Lexer(input);
        List<Token> tokens = lexico.tokenizer();
        for (Token token : tokens) {
            System.out.println("TOKEN: " + token.getType().toString() + " LEXEMA: " + token.getValue());
        }
    }
}
