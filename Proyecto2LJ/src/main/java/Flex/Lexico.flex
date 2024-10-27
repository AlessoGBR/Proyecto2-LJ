package Flex;
import TokenType;
import Token;
%%

// Configuración de JFlex
%public
%class AnalizadorLexico
%char
%unicode
%line
%column
%ignorecase
%standalone

%type Token

// Expresiones regulares
PALABRA = [a-zA-Z]+
LETRA_MINUSCULA = [a-z]
LETRA_MAYUSCULA = [A-Z]
NUMERO = [0-9]
DECIMAL = [0-9]+"."[0-9]+
ESPACIO = [" "\r\t\n\f]


// Código cuando se alcanza el fin de archivo
%eofval{
    return new Token(null,TokenType.EOF, -1, -1);
%eofval}

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
    "INTEGER"|"BIGINT" { return token(yytext(), TokenType.TIPO_DATO); }
    "VARCHAR"          { return token(yytext(), TokenType.TIPO_DATO); }
    "DECIMAL"          { return token(yytext(), TokenType.TIPO_DATO); }
    "DATE"             { return token(yytext(), TokenType.TIPO_DATO); }
    "TEXT"             { return token(yytext(), TokenType.TIPO_DATO); }
    "BOOLEAN"          { return token(yytext(), TokenType.TIPO_DATO); }
    "SERIAL"           { return token(yytext(), TokenType.TIPO_DATO); }

    // Constantes y Literales
    {NUMERO}           { return token(yytext(), TokenType.ENTERO); }
    {DECIMAL}          { return token(yytext(), TokenType.DECIMAL); }
    "'"[0-9]{4}-[0-9]{2}-[0-9]{2}"'" { return token(yytext(), TokenType.FECHA); }
    "'"[^']*"'?"       { return token(yytext(), TokenType.CADENA); }

    // Identificadores
    {LETRA_MINUSCULA}({LETRA_MINUSCULA}|{NUMERO}|"_")* {
                        return token(yytext(), TokenType.ID);
                      }

    // Booleanos
    "TRUE"|"FALSE"     { return token(yytext(), TokenType.BOOLEAN); }

    // Funciones de Agregación
    "SUM"|"AVG"|"COUNT"|"MAX"|"MIN" { return token(yytext(), TokenType.FUNCION_AGREGACION); }

    // Operadores y símbolos
    "("                { return token(yytext(), TokenType.SIGNO); }
    ")"                { return token(yytext(), TokenType.SIGNO); }
    ","                { return token(yytext(), TokenType.SIGNO); }
    ";"                { return token(yytext(), TokenType.SIGNO); }
    "="                { return token(yytext(), TokenType.SIGNO); }
    "+"|"-"|"*"|"/"    { return token(yytext(), TokenType.ARITMETICO); }
    "<"|">"|"<="|">="  { return token(yytext(), TokenType.RELACIONAL); }
    "AND"|"OR"|"NOT"   { return token(yytext(), TokenType.LOGICO); }

    // Comentarios
    "--"               { yybegin(COMMENT); }
}

<COMMENT>[^\\n]+  { /* ignorar el contenido del comentario */ }
<COMMENT>\\n      { yybegin(YYINITIAL); }

<YYINITIAL> {ESPACIO} {}
