package LEXER;

public enum TokenType {
    INTEGER("^[+|-]?[0-9]+$"),
    ADD("^[+]$"),
    SUBTRACT("^[-]$"),
    MULTIPLY("^[*]$"),
    DIVIDE("^[/]$"),
    ASSIGN("^[=]$"),
    RESERVED("^ENTERO$"),
    VARIABLE("^[A-z]+[0-9]*$"),
    EOL("^[;]$");

    public final String pattern;
    TokenType(String pattern) {
        this.pattern = pattern;
    }
}
