/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import Flex.Token;
import Flex.TokenType;
import static Flex.TokenType.ID;
import java.util.Iterator;
import java.util.ArrayList;

/**
 *
 * @author alesso
 */
public class AnalizadorDDL {

    private final ArrayList<Token> tokens;
    private ArrayList<Token> Error;
    private final ArrayList<Token> Tablas;
    private final ArrayList<Token> TablasModificadas;
    private final Iterator<Token> iterator;
    private int create = 0;
    private int delete = 0;
    private int update = 0;
    private int select = 0;
    private int alter = 0;
    private Token currentToken;

    public AnalizadorDDL(ArrayList<Token> tokens) {
        this.tokens = tokens;
        this.iterator = tokens.iterator();
        Error = new ArrayList<>();
        Tablas = new ArrayList<>();
        TablasModificadas = new ArrayList<>();
        advance(); // Inicializa el primer token
    }

    private void advance() {
        if (iterator.hasNext()) {
            currentToken = iterator.next();
        } else {
            currentToken = null; 
        }
    }

    public void parse() throws SyntaxException {
        if (currentToken == null) {
            throw new SyntaxException("NO HAY TOKENS PARA ANALIZAR.");
        }
        while (currentToken != null) {
            if (currentToken.getType() == null) {
                throw new SyntaxException("Token inesperado: " + currentToken.getLexema() + " En la fila: " + currentToken.getFila() + " Y columna: " + currentToken.getColumna());
            } else {
                switch (currentToken.getType()) {
                    case CREATE -> {
                        create += 1;
                        advance();
                        parseCreateStatement();
                    }
                    case ALTER -> {
                        alter += 1;
                        advance();
                        parseAlterStatement();
                    }
                    case DROP -> {
                        advance();
                        parseDropStatement();
                    }
                    case INSERT -> {
                        advance();
                        parseInsertStatement();
                    }
                    case SELECT -> {
                        select += 1;
                        advance();
                        parseSelectStatement();
                    }
                    case UPDATE -> {
                        update += 1;
                        advance();
                        parseUpdateStatement();
                    }
                    case DELETE -> {
                        delete += 1;
                        advance();
                        parseDeleteStatement();
                    }
                    case SEMICOLON ->
                        advance();
                    default ->
                        throw new SyntaxException("Token inesperado: " + currentToken.getLexema());
                }
            }
        }
    }

    private void parseCreateStatement() throws SyntaxException {
        if (currentToken == null) {
            throw new SyntaxException("Se esperaba DATABASE o TABLE después de CREATE");
        } else {
            switch (currentToken.getType()) {
                case DATABASE -> {
                    advance();
                    parseCreateDatabase();
                }
                case TABLE -> {
                    advance();
                    parseCreateTable();
                }
                default ->
                    throw new SyntaxException("Se esperaba DATABASE o TABLE después de CREATE");
            }
        }
    }

    private void parseCreateDatabase() throws SyntaxException {
        expect(TokenType.ID, "Se esperaba el nombre de la base de datos después de CREATE DATABASE");
        advance();
        expect(TokenType.SEMICOLON, "Se esperaba ';' al final de la instrucción CREATE DATABASE");
        advance();
    }

    private void parseCreateTable() throws SyntaxException {
        expect(TokenType.ID, "Se esperaba el nombre de la tabla después de CREATE TABLE");
        Tablas.add(currentToken);
        advance();
        expect(TokenType.LEFT_PAREN, "Se esperaba '(' después del nombre de la tabla");
        advance();
        parseColumnOrConstraint();
        expect(TokenType.RIGHT_PAREN, "Se esperaba ')' al final de las definiciones de columnas");
        advance();
        expect(TokenType.SEMICOLON, "Se esperaba ';' al final de la instrucción CREATE TABLE");
        advance();
    }

    private void parseColumnOrConstraint() throws SyntaxException {
        while (currentToken != null && currentToken.getType() != TokenType.RIGHT_PAREN) {
            if (currentToken.getType() == null) {
                throw new SyntaxException("Token inesperado en la definición de columna o restricción");
            }

            switch (currentToken.getType()) {
                case ID ->
                    parseColumnDefinition();
                case CONSTRAINT ->
                    parseConstraintDefinition();
                case LEFT_PAREN, DECIMAL, ENTERO, NUMERIC_TYPE -> {
                    expect(TokenType.LEFT_PAREN, "Se esperaba '(' después del tipo de dato");
                    advance();
                    expect(TokenType.ENTERO, "Se esperaba un entero después de '(' para el tipo de dato");
                    advance();
                    if (currentToken.getType() == TokenType.COMMA) {
                        advance();
                        expect(TokenType.ENTERO, "Se esperaba un entero después de ',' para la precisión");
                        advance();
                    }

                    expect(TokenType.RIGHT_PAREN, "Se esperaba ')' después de la definición del tipo de dato");
                    advance();
                }
                case NOT -> {
                    advance();
                    expect(TokenType.NULL, "Se esperaba NULL después de NOT");
                    advance();
                    if (currentToken.getType() == TokenType.COMMA) {
                        advance();
                    }
                }
                case UNIQUE ->
                    advance();
                case COMMA ->
                    advance();
                case FOREIGN -> {
                    advance();
                    expect(TokenType.KEY, "Se esperaba KEY después de FOREIGN");
                    advance();
                    expect(TokenType.LEFT_PAREN, "Se esperaba '(' después de FOREIGN KEY");
                    advance();
                    expect(TokenType.ID, "Se esperaba el nombre de la columna en la restricción FOREIGN KEY");
                    advance();
                    expect(TokenType.RIGHT_PAREN, "Se esperaba ')' después del nombre de la columna en la restricción FOREIGN KEY");
                    advance();
                    expect(TokenType.REFERENCES, "Se esperaba REFERENCES en la restricción FOREIGN KEY");
                    advance();
                    expect(TokenType.ID, "Se esperaba el nombre de la tabla referenciada después de REFERENCES");
                    advance();
                    expect(TokenType.LEFT_PAREN, "Se esperaba '(' después del nombre de la tabla referenciada");
                    advance();
                    expect(TokenType.ID, "Se esperaba el nombre de la columna en la tabla referenciada");
                    advance();
                    expect(TokenType.RIGHT_PAREN, "Se esperaba ')' después del nombre de la columna referenciada");
                    advance();
                }
                default ->
                    throw new SyntaxException("Token inesperado en la definición de columna o restricción");
            }
        }
    }

    private void parseColumnDefinition() throws SyntaxException {
        expect(TokenType.ID, "Se esperaba el nombre de la columna en la definición de columna");

        advance();
        expectDataType();
        advance();

        while (currentToken != null && (currentToken.getType() == TokenType.NOT
                || currentToken.getType() == TokenType.UNIQUE
                || currentToken.getType() == TokenType.PRIMARY
                || currentToken.getType() == TokenType.ENTERO
                || currentToken.getType() == TokenType.SERIAL_TYPE
                || currentToken.getType() == TokenType.INTEGER_TYPE
                || currentToken.getType() == TokenType.BIGINT_TYPE
                || currentToken.getType() == TokenType.VARCHAR_TYPE
                || currentToken.getType() == TokenType.DECIMAL
                || currentToken.getType() == TokenType.NUMERIC_TYPE
                || currentToken.getType() == TokenType.DATE_TYPE
                || currentToken.getType() == TokenType.TEXT_TYPE
                || currentToken.getType() == TokenType.BOOLEAN_TYPE
                || currentToken.getType() == TokenType.DECIMAL_TYPE)) {
            switch (currentToken.getType()) {
                case NOT -> {
                    advance();
                    expect(TokenType.NULL, "Se esperaba NULL después de NOT");
                    advance();
                }
                case UNIQUE ->
                    advance();
                case PRIMARY -> {
                    advance();
                    expect(TokenType.KEY, "Se esperaba KEY después de PRIMARY");
                    advance();
                    expect(TokenType.COMMA, "Se esperaba ',' después de KEY");
                    advance();
                }
                default -> {
                }
            }
        }
    }

    private void parseConstraintDefinition() throws SyntaxException {
        expect(TokenType.CONSTRAINT, "Se esperaba la palabra clave CONSTRAINT");
        advance();
        expect(TokenType.ID, "Se esperaba el nombre de la restricción después de CONSTRAINT");
        advance();

        if (null == currentToken.getType()) {
            throw new SyntaxException("Se esperaba una restricción UNIQUE o FOREIGN KEY");
        } else {
            switch (currentToken.getType()) {
                case UNIQUE -> {
                    advance();
                    expect(TokenType.LEFT_PAREN, "Se esperaba '(' después de UNIQUE");
                    advance();
                    expect(TokenType.ID, "Se esperaba el nombre de la columna en la restricción UNIQUE");
                    advance();
                    expect(TokenType.RIGHT_PAREN, "Se esperaba ')' después del nombre de la columna en la restricción UNIQUE");
                    advance();
                }
                case FOREIGN -> {
                    advance();
                    expect(TokenType.KEY, "Se esperaba KEY después de FOREIGN");
                    advance();
                    expect(TokenType.LEFT_PAREN, "Se esperaba '(' después de FOREIGN KEY");
                    advance();
                    expect(TokenType.ID, "Se esperaba el nombre de la columna en la restricción FOREIGN KEY");
                    advance();
                    expect(TokenType.RIGHT_PAREN, "Se esperaba ')' después del nombre de la columna en la restricción FOREIGN KEY");
                    advance();
                    expect(TokenType.REFERENCES, "Se esperaba REFERENCES en la restricción FOREIGN KEY");
                    advance();
                    expect(TokenType.ID, "Se esperaba el nombre de la tabla referenciada después de REFERENCES");
                    advance();
                    expect(TokenType.LEFT_PAREN, "Se esperaba '(' después del nombre de la tabla referenciada");
                    advance();
                    expect(TokenType.ID, "Se esperaba el nombre de la columna en la tabla referenciada");
                    advance();
                    expect(TokenType.RIGHT_PAREN, "Se esperaba ')' después del nombre de la columna referenciada");
                    advance();
                }
                default ->
                    throw new SyntaxException("Se esperaba una restricción UNIQUE o FOREIGN KEY");
            }
        }
    }

    private void parseAlterStatement() throws SyntaxException {
        expect(TokenType.TABLE, "Se esperaba TABLE después de ALTER");
        advance();
        expect(TokenType.ID, "Se esperaba el nombre de la tabla después de ALTER TABLE");
        currentToken.setModificacion("ALTER");
        TablasModificadas.add(currentToken);//aca para el reporte de modificacion
        advance();
        if (currentToken == null) {
            throw new SyntaxException("Token inesperado después de ALTER TABLE");
        } else {
            switch (currentToken.getType()) {
                case ADD -> {
                    advance();
                    parseAlterAddStatement();
                }
                case ALTER -> {
                    advance();
                    parseAlterColumnType();
                }
                case DROP -> {
                    advance();
                    parseAlterDropColumn();
                }
                default ->
                    throw new SyntaxException("Token inesperado después de ALTER TABLE");
            }
        }

        if (currentToken.getType() == TokenType.LEFT_PAREN) {
            advance();
            expect(TokenType.ENTERO, "Se esperaba Entero después de (");
            advance();
            expect(TokenType.COMMA, "Se esperaba , después del entero");
            advance();
            expect(TokenType.ENTERO, "Se esperaba Entero después de (");
            advance();
            expect(TokenType.RIGHT_PAREN, "Se esperaba ( despues del numero");
            advance();
        }
    }

    private void parseAlterAddStatement() throws SyntaxException {
        if (currentToken.getType() == null) {
            throw new SyntaxException("Se esperaba COLUMN o CONSTRAINT después de ALTER TABLE ADD");
        } else {
            switch (currentToken.getType()) {
                case COLUMN -> {
                    advance();
                    parseColumnDefinition();
                }
                case CONSTRAINT ->
                    parseConstraintDefinition();
                default ->
                    throw new SyntaxException("Se esperaba COLUMN o CONSTRAINT después de ALTER TABLE ADD");
            }
        }
    }

    private void parseAlterColumnType() throws SyntaxException {
        expect(TokenType.COLUMN, "Se esperaba COLUMN después de ALTER TABLE ALTER");
        advance();

        expect(TokenType.ID, "Se esperaba el nombre de la columna para alterar");
        advance();

        expect(TokenType.TYPE, "Se esperaba TYPE después del nombre de la columna");
        advance();

        if (isDataType(currentToken.getType())) {
            TokenType dataType = currentToken.getType();
            advance();

            if (currentToken.getType() == TokenType.LEFT_PAREN) {
                advance();

                expect(TokenType.ENTERO, "Se esperaba un entero para el tamaño o precisión del tipo de dato");
                advance();

                if ((dataType == TokenType.DECIMAL || dataType == TokenType.NUMERIC_TYPE) && currentToken.getType() == TokenType.COMMA) {
                    advance();

                    expect(TokenType.ENTERO, "Se esperaba un segundo entero para la precisión decimal");
                    advance();
                }

                expect(TokenType.RIGHT_PAREN, "Se esperaba ')' después de los parámetros del tipo de dato");
                advance();
            }
        } else {
            throw new SyntaxException("Tipo de dato inesperado en la declaración ALTER COLUMN TYPE");
        }
    }

    private void parseAlterDropColumn() throws SyntaxException {
        expect(TokenType.COLUMN, "Se esperaba COLUMN después de ALTER TABLE DROP");
        advance();
        expect(TokenType.ID, "Se esperaba el nombre de la columna para eliminar");
        advance();
    }

    private void parseDropStatement() throws SyntaxException {
        expect(TokenType.TABLE, "Se esperaba TABLE después de DROP");
        advance();
        if (currentToken.getType() == TokenType.IF) {
            advance();
            expect(TokenType.EXISTS, "Se esperaba EXISTS después de IF en DROP TABLE");
            advance();
        }
        expect(TokenType.ID, "Se esperaba el nombre de la tabla en DROP TABLE");
        currentToken.setModificacion("DROP");
        TablasModificadas.add(currentToken);
        advance();
        if (currentToken.getType() == TokenType.CASCADE) {
            advance();
        }
        expect(TokenType.SEMICOLON, "Se esperaba ';' al final de la declaración DROP TABLE");
        advance();
    }

    private void expect(TokenType expectedType, String errorMessage) throws SyntaxException {
        if (currentToken == null || currentToken.getType() != expectedType) {
            Error.add(currentToken);
            throw new SyntaxException(errorMessage);
        }
    }

    private void expectDataType() throws SyntaxException {
        switch (currentToken.getType()) {
            case SERIAL_TYPE, INTEGER_TYPE, BIGINT_TYPE, VARCHAR_TYPE, DECIMAL_TYPE, NUMERIC_TYPE, DATE_TYPE, TEXT_TYPE, BOOLEAN_TYPE -> {
            }
            default ->
                throw new SyntaxException("Se esperaba un tipo de dato válido");
        }
    }

    private boolean isDataType(TokenType tokenType) {
        return tokenType == TokenType.VARCHAR_TYPE
                || tokenType == TokenType.DECIMAL_TYPE
                || tokenType == TokenType.NUMERIC_TYPE
                || tokenType == TokenType.INTEGER_TYPE
                || tokenType == TokenType.SERIAL_TYPE
                || tokenType == TokenType.BOOLEAN_TYPE
                || tokenType == TokenType.DATE_TYPE;
    }

    private void parseInsertStatement() throws SyntaxException {
        expect(TokenType.INTO, "Se esperaba INTO después de INSERT");
        advance();
        expect(TokenType.ID, "Se esperaba el nombre de la tabla después de INSERT INTO");
        advance();

        if (currentToken.getType() == TokenType.LEFT_PAREN) {
            advance();
            parseColumnList();
            expect(TokenType.RIGHT_PAREN, "Se esperaba ')' después de la lista de columnas en INSERT");
            advance();
        }

        expect(TokenType.VALUES, "Se esperaba VALUES en la declaración INSERT");
        advance();
        expect(TokenType.LEFT_PAREN, "Se esperaba '(' después de VALUES");
        advance();
        parseValueList();
        expect(TokenType.RIGHT_PAREN, "Se esperaba ')' después de los valores en la declaración INSERT");
        advance();
        expect(TokenType.SEMICOLON, "Se esperaba ';' al final de la declaración INSERT");
        advance();
    }

    private void parseSelectStatement() throws SyntaxException {
        if (currentToken.getType() == TokenType.MULTIPLICACION) {
            advance();
        } else {
            parseColumnList();
        }

        if (currentToken.getType() == TokenType.AS) {
            advance();
            expect(TokenType.ID, "Se esperaba el nombre de la tabla en la declaración SELECT");
            advance();
        }

        expect(TokenType.FROM, "Se esperaba FROM en la declaración SELECT");
        advance();
        expect(TokenType.ID, "Se esperaba el nombre de la tabla en la declaración SELECT");
        advance();

        if (currentToken.getType() == TokenType.ID) {
            advance();
        }

        while (currentToken != null && currentToken.getType() == TokenType.JOIN) {
            advance();
            expect(TokenType.ID, "Se esperaba el nombre de la tabla después de JOIN");
            advance();
            if (currentToken.getType() == TokenType.ID) {
                advance();
            }
            expect(TokenType.ON, "Se esperaba ON después de la tabla unida");
            advance();
            parseCondition();

            while (currentToken != null && (currentToken.getType() == TokenType.AND || currentToken.getType() == TokenType.OR)) {
                advance();
                parseCondition();
            }
        }

        if (currentToken.getType() == TokenType.WHERE) {
            advance();
            parseCondition();
            while (currentToken != null && (currentToken.getType() == TokenType.AND || currentToken.getType() == TokenType.OR)) {
                advance();
                parseCondition();
            }
        }

        if (currentToken.getType() == TokenType.GROUP || currentToken.getType() == TokenType.ORDER) {
            advance();
            parseColumnList();
        }

        if (currentToken.getType() == TokenType.SELECT) {
            advance();
            parseColumnList();
            expect(TokenType.FROM, "Se esperaba FROM en la declaración SELECT");
            advance();
            expect(TokenType.ID, "Se esperaba el nombre de la tabla en la declaración SELECT");
            advance();
            if (currentToken.getType() == TokenType.GROUP) {
                advance();
                parseColumnList();
            }
        }

        if (currentToken.getType() == TokenType.DESC || currentToken.getType() == TokenType.ENTERO || currentToken.getType() == TokenType.DECIMAL) {
            advance();
        }

        expect(TokenType.SEMICOLON, "Se esperaba ';' al final de la declaración SELECT");
        advance();
    }

    private void parseUpdateStatement() throws SyntaxException {
        expect(TokenType.ID, "Se esperaba el nombre de la tabla después de UPDATE");
        currentToken.setModificacion("UPDATE");
        TablasModificadas.add(currentToken);
        advance();
        expect(TokenType.SET, "Se esperaba SET en la declaración UPDATE");
        advance();
        parseAssignmentList();

        if (currentToken != null && currentToken.getType() == TokenType.WHERE) {
            advance();
            parseConditionUpdate();
        }

        if (currentToken != null && currentToken.getType() == TokenType.ID) {
            advance();
            expect(TokenType.EQUAL, "Se esperaba IGUAL en la declaración UPDATE");
            advance();
        }

        while (currentToken != null && currentToken.getType() == TokenType.UPDATE) {
            advance();
            parseUpdateStatement();
        }

        expect(TokenType.SEMICOLON, "Se esperaba ';' al final de la declaración UPDATE");
        advance();
    }

    private void parseDeleteStatement() throws SyntaxException {
        expect(TokenType.FROM, "Se esperaba FROM en la declaración DELETE");
        advance();
        expect(TokenType.ID, "Se esperaba el nombre de la tabla en la declaración DELETE");
        currentToken.setModificacion("DELETE");
        TablasModificadas.add(currentToken);
        advance();

        if (currentToken.getType() == TokenType.WHERE) {
            advance();
            parseConditionDelete();
        }

        if (currentToken.getType() == TokenType.DELETE) {
            advance();
            parseDeleteStatement();
        }

        if (currentToken == null) {
            return;
        }

        expect(TokenType.SEMICOLON, "Se esperaba ';' al final de la declaración DELETE");
        advance();
    }

    private void parseColumnList() throws SyntaxException {
        if (null == currentToken.getType()) {
            throw new SyntaxException("Se esperaba el nombre de una columna o función de agregación en la lista de columnas");
        } else {
            switch (currentToken.getType()) {
                case ID ->
                    advance();
                case ID_PUNTO ->
                    advance();
                case SUM -> {
                    advance();
                    expect(TokenType.LEFT_PAREN, "Se esperaba '(' después de la función de agregación");
                    advance();
                    expect(TokenType.ID, "Se esperaba el nombre de la columna dentro de la función de agregación");
                    advance();
                    expect(TokenType.RIGHT_PAREN, "Se esperaba ')' de cierre para la función de agregación");
                    advance();
                    if (currentToken.getType() == TokenType.AS) {
                        advance();
                        expect(TokenType.ID, "Se esperaba el nombre del alias para la columna agregada");
                        advance();
                    }
                }
                case AVG -> {
                    advance();
                    expect(TokenType.LEFT_PAREN, "Se esperaba '(' después de la función de agregación");
                    advance();
                    expect(TokenType.ID, "Se esperaba el nombre de la columna dentro de la función de agregación");
                    advance();
                    expect(TokenType.RIGHT_PAREN, "Se esperaba ')' de cierre para la función de agregación");
                    advance();
                    if (currentToken.getType() == TokenType.AS) {
                        advance();
                        expect(TokenType.ID, "Se esperaba el nombre del alias para la columna agregada");
                        advance();
                    }
                }
                case COUNT -> {
                    advance();
                    expect(TokenType.LEFT_PAREN, "Se esperaba '(' después de la función de agregación");
                    advance();
                    expect(TokenType.ID, "Se esperaba el nombre de la columna dentro de la función de agregación");
                    advance();
                    expect(TokenType.RIGHT_PAREN, "Se esperaba ')' de cierre para la función de agregación");
                    advance();
                    if (currentToken.getType() == TokenType.AS) {
                        advance();
                        expect(TokenType.ID, "Se esperaba el nombre del alias para la columna agregada");
                        advance();
                    }
                }
                case MAX -> {
                    advance();
                    expect(TokenType.LEFT_PAREN, "Se esperaba '(' después de la función de agregación");
                    advance();
                    expect(TokenType.ID, "Se esperaba el nombre de la columna dentro de la función de agregación");
                    advance();
                    expect(TokenType.RIGHT_PAREN, "Se esperaba ')' de cierre para la función de agregación");
                    advance();
                    if (currentToken.getType() == TokenType.AS) {
                        advance();
                        expect(TokenType.ID, "Se esperaba el nombre del alias para la columna agregada");
                        advance();
                    }
                }
                case MIN -> {
                    advance();
                    expect(TokenType.LEFT_PAREN, "Se esperaba '(' después de la función de agregación");
                    advance();
                    expect(TokenType.ID, "Se esperaba el nombre de la columna dentro de la función de agregación");
                    advance();
                    expect(TokenType.RIGHT_PAREN, "Se esperaba ')' de cierre para la función de agregación");
                    advance();
                    if (currentToken.getType() == TokenType.AS) {
                        advance();
                        expect(TokenType.ID, "Se esperaba el nombre del alias para la columna agregada");
                        advance();
                    }
                }
                case BY -> {
                    advance();
                    expect(TokenType.ID, "Se esperaba el nombre del alias para la columna agregada");
                    advance();
                }
                case DESC ->
                    advance();
                case AS -> {
                    advance();
                    expect(TokenType.ID, "Se esperaba el nombre del alias para la columna agregada");
                    advance();
                }
                default ->
                    throw new SyntaxException("Se esperaba el nombre de una columna o función de agregación en la lista de columnas");
            }
        }

        while (currentToken.getType() == TokenType.COMMA) {
            advance();
            parseColumnList();
        }
    }

    private void parseValueList() throws SyntaxException {
        parseValue();
        while (currentToken.getType() == TokenType.COMMA) {
            advance();
            parseValue();
        }
    }

    private void parseValue() throws SyntaxException {
        if (isLiteral(currentToken)) {
            advance();
            parseArithmeticOperators();
        } else if (currentToken.getType() == TokenType.ID) {
            advance();
            parseArithmeticOperators();
        } else if (currentToken.getType() == TokenType.ID_PUNTO) {
            parseArithmeticOperators();
        } else {
            throw new SyntaxException("Se esperaba un valor (CADENA, NÚMERO o FECHA)");
        }
    }

    private boolean isLiteral(Token token) {
        return token.getType() == TokenType.CADENA
                || token.getType() == TokenType.ENTERO
                || token.getType() == TokenType.DECIMAL
                || token.getType() == TokenType.FECHA;
    }

    private void parseArithmeticOperators() throws SyntaxException {
        while (currentToken != null
                && (currentToken.getType() == TokenType.SUMA
                || currentToken.getType() == TokenType.RESTA
                || currentToken.getType() == TokenType.MULTIPLICACION
                || currentToken.getType() == TokenType.DIVICION)) {
            advance();
            if (!isLiteral(currentToken) && currentToken.getType() != TokenType.ID) {
                throw new SyntaxException("Se esperaba un valor después del operador aritmético");
            }
            advance();
        }

        if (currentToken != null
                && (currentToken.getType() == TokenType.MAYOR
                || currentToken.getType() == TokenType.MENOR
                || currentToken.getType() == TokenType.MAYOR_IGUAL
                || currentToken.getType() == TokenType.MENOR_IGUAL
                || currentToken.getType() == TokenType.EQUAL)) {
            advance();
            if (!isLiteral(currentToken) && currentToken.getType() != TokenType.ID) {
                throw new SyntaxException("Se esperaba un valor después del operador de comparación");
            }
            advance();
        }
    }

    private void parseAssignmentList() throws SyntaxException {
        expect(TokenType.ID, "Se esperaba el nombre de la columna en la asignación");
        advance();
        expect(TokenType.EQUAL, "Se esperaba '=' en la asignación");
        advance();
        parseValue();
        while (currentToken != null && currentToken.getType() == TokenType.COMMA) {
            advance();
            expect(TokenType.ID, "Se esperaba el nombre de la columna en la asignación después de ','");
            advance();
            expect(TokenType.EQUAL, "Se esperaba '=' en la asignación");
            advance();
            parseValue();
        }
    }

    private void parseCondition() throws SyntaxException {
        if (currentToken.getType() == TokenType.LEFT_PAREN) {
            advance();
            parseCondition();
        } else {
            if (currentToken.getType() == TokenType.ID_PUNTO) {
                expect(TokenType.ID_PUNTO, "Se esperaba el nombre de la columna en la condición");
                advance();
            } else {
                expect(TokenType.ID, "Se esperaba el nombre de la columna en la condición");
                advance();
            }

            if (currentToken.getType() == TokenType.EQUAL || currentToken.getType() == TokenType.MENOR
                    || currentToken.getType() == TokenType.MAYOR || currentToken.getType() == TokenType.NO_IGUALES) {
                advance();
                parseValue();
                advance();
            } else {
                throw new SyntaxException("Se esperaba un operador de comparación en la condición");
            }

            while (currentToken != null && (currentToken.getType() == TokenType.AND || currentToken.getType() == TokenType.OR)) {
                advance();
                parseCondition();
            }
        }
    }

    private void parseConditionDelete() throws SyntaxException {
        if (currentToken.getType() == TokenType.LEFT_PAREN) {
            advance();
            parseCondition();
        } else {
            if (currentToken.getType() == TokenType.ID_PUNTO) {
                expect(TokenType.ID_PUNTO, "Se esperaba el nombre de la columna en la condición");
                advance();
            } else {
                expect(TokenType.ID, "Se esperaba el nombre de la columna en la condición");
                advance();
            }

            if (currentToken.getType() == TokenType.EQUAL || currentToken.getType() == TokenType.MENOR
                    || currentToken.getType() == TokenType.MAYOR || currentToken.getType() == TokenType.NO_IGUALES) {
                advance();
                parseValue();
            } else {
                throw new SyntaxException("Se esperaba un operador de comparación en la condición");
            }

            while (currentToken != null && (currentToken.getType() == TokenType.AND || currentToken.getType() == TokenType.OR)) {
                advance();
                parseCondition();
            }
        }
    }

    private void parseConditionUpdate() throws SyntaxException {
        if (currentToken.getType() == TokenType.LEFT_PAREN) {
            advance();
            parseCondition();
        } else {
            expect(TokenType.ID, "Se esperaba el nombre de la columna en la condición");
            advance();

            if (currentToken.getType() == TokenType.EQUAL || currentToken.getType() == TokenType.MENOR
                    || currentToken.getType() == TokenType.MAYOR || currentToken.getType() == TokenType.NO_IGUALES) {
                advance();
                parseValue();
            } else {
                throw new SyntaxException("Se esperaba un operador de comparación en la condición");
            }

            while (currentToken != null && (currentToken.getType() == TokenType.AND || currentToken.getType() == TokenType.OR)) {
                advance();
                parseConditionUpdate();
            }
        }
    }

    public ArrayList<Token> getError() {
        return Error;
    }

    public void setError(ArrayList<Token> Error) {
        this.Error = Error;
    }

    public ArrayList<Token> getTablas() {
        return Tablas;
    }

    public ArrayList<Token> getTablasModificadas() {
        return TablasModificadas;
    }

    public int getCreate() {
        return create;
    }

    public int getDelete() {
        return delete;
    }

    public int getUpdate() {
        return update;
    }

    public int getSelect() {
        return select;
    }

    public int getAlter() {
        return alter;
    }

}
