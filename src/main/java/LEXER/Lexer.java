package LEXER;

import UTILS.Utilities;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private final Utilities u = new Utilities();
    private final List<Token> tokens = new ArrayList<>();

    private int pos = 0;
    private final char[] charText;
    private Character current_char;

    public Lexer(String input) {
        charText = input.toCharArray();
        current_char = charText[pos];
    }

    protected void advance() {
        pos++;
        if (pos > charText.length - 1)
            current_char = null;
        else
            current_char = charText[pos];
    }

    public void tokenizer(int state, String lexeme) {
        if (current_char == null){
            if(state == 4)
                tokens.add(new Token(TokenType.INTEGER, lexeme));
            if(state >= 6 && state <= 11)
                tokens.add(new Token(TokenType.STRING, lexeme));

            return;
        }

        if (lexeme == null)
            lexeme = "";

        switch (state) {
            case 0:
                if (u.validate(current_char)) {
                    if (current_char == '+')
                        tokens.add(new Token(TokenType.ADD, Character.toString(current_char)));

                    if (current_char == '-')
                        tokens.add(new Token(TokenType.SUBTRACT, Character.toString(current_char)));

                    if (current_char == '=')
                        tokens.add(new Token(TokenType.ASSIGN, Character.toString(current_char)));

                    lexeme = null;
                } else if (Character.isDigit(current_char)) {
                    if (current_char == '0') {
                        tokens.add(new Token(TokenType.ERROR, Character.toString(current_char)));
                        lexeme = null;
                    } else {
                        lexeme += Character.toString(current_char);
                        state = 4;
                    }
                } else if (Character.isLetter(current_char)) {
                    lexeme += Character.toString(current_char);
                    state = current_char == 'p' ? 8 : 6;
                } else {
                    tokens.add(new Token(TokenType.ERROR, Character.toString(current_char)));
                    lexeme = null;
                }

                advance();
                break;
            case 4:
                if (Character.isDigit(current_char)) {
                    lexeme += Character.toString(current_char);
                    advance();
                } else {
                    tokens.add(new Token(TokenType.INTEGER, lexeme));
                    state = 0;
                    lexeme = null;
                }

                break;
            case 6:
                if (Character.isLetter(current_char)) {
                    lexeme += Character.toString(current_char);
                    advance();
                } else {
                    tokens.add(new Token(TokenType.STRING, lexeme));
                    state = 0;
                    lexeme = null;
                }
                break;
            case 8:
                if (current_char == 'r') {
                    lexeme += Character.toString((current_char));
                    state = 9;
                    advance();
                } else {
                    state = 6;
                }

                break;
            case 9:
                if (current_char == 'i') {
                    lexeme += Character.toString((current_char));
                    state = 10;
                    advance();
                } else {
                    state = 6;
                }

                break;
            case 10:
                if (current_char == 'n') {
                    lexeme += Character.toString((current_char));
                    state = 11;
                    advance();
                } else {
                    state = 6;
                }

                break;
            case 11:
                if (current_char == 't') {
                    lexeme += Character.toString((current_char));
                    tokens.add(new Token(TokenType.PRINT, lexeme));
                    state = 0;
                    lexeme = null;

                    advance();
                } else {
                    state = 6;
                }

                break;
        }

        tokenizer(state, lexeme);
    }

    public void print() {
        for (Token token : tokens) {
            System.out.println("TOKEN: " + token.getType().toString() + " LEXEMA: " + token.getValue());
        }
    }

    public static void main(String[] args) {
        String input = "prinat045+62*print1-78a";
        Lexer lexico = new Lexer(input);
        lexico.tokenizer(0, null);
        lexico.print();
    }
}
