package Flex;

%%

// Configuración de JFlex
%public
%class AnalizadorLexico
%unicode
%line
%column
%standalone

// Expresiones regulares
PALABRA = [a-zA-Z]+
IDENTIFICADOR = [a-zA-Z_][a-zA-Z_0-9]*
NUMERO = "-"?[0-9]+
DECIMAL = "-"?[0-9]+("." [0-9]+)?
ESPACIO = [" "\r\t\n\f]

// Inicialización
%init{
    yyline = 0;
    yycolumn = 0;
%init}

%{    
    private Token token(String value, TokenType type) {
      return new Token(value, type, yyline, yycolumn);
  }
%}

%eofval{
    return new Token(null, TokenType.EOF, -1, -1);
%eofval}

// Estados
%state COMMENT

%%

// Reglas de análisis

<YYINITIAL> {

    // Palabras reservadas SQL
    "CREATE"           { return token(yytext(), TokenType.CREATE); }
    "DATABASE"         { return token(yytext(), TokenType.DATABASE); }
    "TABLE"            { return token(yytext(), TokenType.TABLE); }
    "KEY"              { return token(yytext(), TokenType.KEY); }
    "NULL"             { return token(yytext(), TokenType.NULL); }
    "PRIMARY"          { return token(yytext(), TokenType.PRIMARY); }
    "UNIQUE"           { return token(yytext(), TokenType.UNIQUE); }
    "FOREIGN"          { return token(yytext(), TokenType.FOREIGN); }
    "REFERENCES"       { return token(yytext(), TokenType.REFERENCES); }
    "ALTER"            { return token(yytext(), TokenType.ALTER); }
    "ADD"              { return token(yytext(), TokenType.ADD); }
    "COLUMN"           { return token(yytext(), TokenType.COLUMN); }
    "TYPE"             { return token(yytext(), TokenType.TYPE); }
    "DROP"             { return token(yytext(), TokenType.DROP); }
    "CONSTRAINT"       { return token(yytext(), TokenType.CONSTRAINT); }
    "IF"               { return token(yytext(), TokenType.IF); }
    "EXISTS"           { return token(yytext(), TokenType.EXISTS); }
    "CASCADE"          { return token(yytext(), TokenType.CASCADE); }
    "ON"               { return token(yytext(), TokenType.ON); }
    "DELETE"           { return token(yytext(), TokenType.DELETE); }
    "SET"              { return token(yytext(), TokenType.SET); }
    "UPDATE"           { return token(yytext(), TokenType.UPDATE); }
    "INSERT"           { return token(yytext(), TokenType.INSERT); }
    "INTO"             { return token(yytext(), TokenType.INTO); }
    "VALUES"           { return token(yytext(), TokenType.VALUES); }
    "SELECT"           { return token(yytext(), TokenType.SELECT); }
    "FROM"             { return token(yytext(), TokenType.FROM); }
    "WHERE"            { return token(yytext(), TokenType.WHERE); }
    "AS"               { return token(yytext(), TokenType.AS); }
    "GROUP"            { return token(yytext(), TokenType.GROUP); }
    "ORDER"            { return token(yytext(), TokenType.ORDER); }
    "BY"               { return token(yytext(), TokenType.BY); }
    "ASC"              { return token(yytext(), TokenType.ASC); }
    "DESC"             { return token(yytext(), TokenType.DESC); }
    "LIMIT"            { return token(yytext(), TokenType.LIMIT); }
    "JOIN"             { return token(yytext(), TokenType.JOIN); }

    // Tipos de datos
    "INTEGER"          { return token(yytext(), TokenType.INTEGER_TYPE); }
    "BIGINT"           { return token(yytext(), TokenType.BIGINT_TYPE); }
    "VARCHAR"          { return token(yytext(), TokenType.VARCHAR_TYPE); }
    "DECIMAL"          { return token(yytext(), TokenType.DECIMAL_TYPE); }
    "NUMERIC"          { return token(yytext(), TokenType.NUMERIC_TYPE); }
    "DATE"             { return token(yytext(), TokenType.DATE_TYPE); }
    "TEXT"             { return token(yytext(), TokenType.TEXT_TYPE); }
    "BOOLEAN"          { return token(yytext(), TokenType.BOOLEAN_TYPE); }
    "SERIAL"           { return token(yytext(), TokenType.SERIAL_TYPE); }

    // Constantes y Literales
    {NUMERO}           { return token(yytext(), TokenType.ENTERO); }
    {DECIMAL}          { return token(yytext(), TokenType.DECIMAL); }
    "'"[0-9]{4}-[0-9]{2}-[0-9]{2}"'" { return token(yytext(), TokenType.FECHA); }
    \'([^\']*)\'       { return token(yytext(), TokenType.CADENA); }
    
    // Booleanos
    "TRUE"|"FALSE"     { return token(yytext(), TokenType.BOOLEAN); }

    // Funciones de Agregación
    "SUM"              { return token(yytext(), TokenType.SUM); }
    "AVG"              { return token(yytext(), TokenType.AVG); }
    "COUNT"            { return token(yytext(), TokenType.COUNT); }
    "MAX"              { return token(yytext(), TokenType.MAX); }
    "MIN"              { return token(yytext(), TokenType.MIN); }

    // Operadores y símbolos
    "("                { return token(yytext(), TokenType.LEFT_PAREN); }
    ")"                { return token(yytext(), TokenType.RIGHT_PAREN ); }
    ","                { return token(yytext(), TokenType.COMMA); }
    ";"                { return token(yytext(), TokenType.SEMICOLON); }
    "="                { return token(yytext(), TokenType.EQUAL); }
    "+"                { return token(yytext(), TokenType.SUMA); }
    "-"                { return token(yytext(), TokenType.RESTA); }
    "*"                { return token(yytext(), TokenType.MULTIPLICACION); }
    "/"                { return token(yytext(), TokenType.DIVICION); }
    "<"                { return token(yytext(), TokenType.MENOR); }
    ">"                { return token(yytext(), TokenType.MAYOR); }
    "<="               { return token(yytext(), TokenType.MENOR_IGUAL); }
    ">="               { return token(yytext(), TokenType.MAYOR_IGUAL); }
    "!="               { return token(yytext(), TokenType.NO_IGUALES); }
    "AND"              { return token(yytext(), TokenType.AND); }
    "OR"               { return token(yytext(), TokenType.OR); }
    "NOT"              { return token(yytext(), TokenType.NOT); }

    // Comentarios
    "--"               { yybegin(COMMENT); }

    // Identificadores
    {PALABRA}({PALABRA}|{NUMERO}|"_")* {
                        return token(yytext(), TokenType.ID);
    }
    {IDENTIFICADOR}\.{IDENTIFICADOR} {
                        return token(yytext(),TokenType.ID_PUNTO);
    }

    {ESPACIO} {}

    // Regla para manejar errores
    .                   { 
                        return token(yytext(), TokenType.ERROR);
                    }


}

<COMMENT>[^\\n]+  { /* ignorar el contenido del comentario */ }
<COMMENT>\\n      { yybegin(YYINITIAL); }
