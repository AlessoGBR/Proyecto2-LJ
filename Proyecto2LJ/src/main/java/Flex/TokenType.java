/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Flex;

/**
 *
 * @author alesso
 */
public enum TokenType {
    // Keywords
    CREATE,
    DATABASE,
    TABLE,
    KEY,
    NULL,
    PRIMARY,
    UNIQUE,
    FOREIGN,
    REFERENCES,
    ALTER,
    ADD,
    COLUMN,
    TYPE,
    DROP,
    CONSTRAINT,
    IF,
    EXISTS,
    CASCADE,
    ON,
    DELETE,
    SET,
    UPDATE,
    INSERT,
    INTO,
    VALUES,
    SELECT,
    FROM,
    WHERE,
    AS,
    GROUP,
    ORDER,
    BY,
    ASC,
    DESC,
    LIMIT,
    JOIN,

    // Data Types
    INTEGER_TYPE,
    BIGINT_TYPE,
    VARCHAR_TYPE,
    DECIMAL_TYPE,
    DATE_TYPE,
    TEXT_TYPE,
    BOOLEAN_TYPE,
    SERIAL_TYPE,
    CADENA,
    TIPO_DATO,
    FUNCION_AGREGACION,
    LOGICO,

    // Constants
    ENTERO,
    DECIMAL,
    ARITMETICO,
    SIGNO,
    ID,
    RELACIONAL,
    FECHA,
    STRING,
    BOOLEAN,

    // Functions
    AGGREGATE_FUNCTION,  // SUM, AVG, COUNT, MAX, MIN

    // Operators and Symbols
    SIGN,                // Parentheses, commas, semicolons, etc.
    ARITHMETIC_OPERATOR, // +, -, *, /
    RELATIONAL_OPERATOR, // <, >, <=, >=
    LOGICAL_OPERATOR,    // AND, OR, NOT

    // Identifiers and Comments
    IDENTIFIER,          // e.g., variable names, table names
    COMMENT,

    ERROR,// SQL single-line comments

    // End of File
    EOF
}
