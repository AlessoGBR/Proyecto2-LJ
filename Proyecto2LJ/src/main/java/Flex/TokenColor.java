/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Flex;

import static Flex.TokenType.INTEGER_TYPE;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

/**
 *
 * @author alesso
 */
public class TokenColor {

    private final JTextPane txtCodigo;
    private final StyledDocument doc;

    public TokenColor(JTextPane txtCodigo) {
        this.txtCodigo = txtCodigo;
        this.doc = txtCodigo.getStyledDocument();

        // Definir estilos para diferentes tipos de tokens
        defineStyles();
    }

    private void defineStyles() {
        Style defaultStyle = txtCodigo.addStyle("default", null);
        StyleConstants.setForeground(defaultStyle, Color.BLACK);

        Style sqlKeywordStyle = txtCodigo.addStyle("sqlKeyword", null);
        StyleConstants.setForeground(sqlKeywordStyle, Color.ORANGE);

        Style identifierStyle = txtCodigo.addStyle("identifier", null);
        StyleConstants.setForeground(identifierStyle, new Color(128, 0, 128));

        Style numberStyle = txtCodigo.addStyle("numero", null);
        StyleConstants.setForeground(numberStyle, Color.BLUE);

        Style fechaStyle = txtCodigo.addStyle("fecha", null);
        StyleConstants.setForeground(fechaStyle, Color.YELLOW);

        Style cadenaStyle = txtCodigo.addStyle("cadena", null);
        StyleConstants.setForeground(cadenaStyle, Color.GREEN);

        Style idStyle = txtCodigo.addStyle("id", null);
        StyleConstants.setForeground(idStyle, new Color(255, 0, 255));

        Style booleanStyle = txtCodigo.addStyle("boolean", null);
        StyleConstants.setForeground(booleanStyle, Color.BLUE);

        Style signosStyle = txtCodigo.addStyle("signos", null);
        StyleConstants.setForeground(signosStyle, Color.BLACK);

        Style logicalStyle = txtCodigo.addStyle("logico", null);
        StyleConstants.setForeground(logicalStyle, Color.ORANGE);

        Style commentStyle = txtCodigo.addStyle("comment", null);
        StyleConstants.setForeground(commentStyle, Color.GRAY);
    }

    public void applyStyleToToken(String lexeme, TokenType tokenType) {
        Style style = determineStyle(tokenType);

        try {
            // Separar el token en líneas si contiene saltos de línea
            String[] lines = lexeme.split("\n");
            for (int i = 0; i < lines.length; i++) {
                // Insertar cada línea con el estilo correspondiente
                doc.insertString(doc.getLength(), lines[i], style);

                // Insertar un salto de línea al final de cada línea, excepto la última
                if (i < lines.length - 1) {
                    doc.insertString(doc.getLength(), "\n", txtCodigo.getStyle("default"));
                }
            }
            // Agregar un espacio después del token si no contiene salto de línea
            if (!lexeme.contains("\n")) {
                doc.insertString(doc.getLength(), " ", style);
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    // Método para determinar el estilo según el tipo de token
    private Style determineStyle(TokenType tokenType) {
        switch (tokenType) {
            case CREATE:
            case DATABASE:
            case TABLE:
            case KEY:
            case NULL:
            case PRIMARY:
            case UNIQUE:
            case FOREIGN:
            case REFERENCES:
            case ALTER:
            case ADD:
            case COLUMN:
            case TYPE:
            case DROP:
            case CONSTRAINT:
            case IF:
            case EXISTS:
            case CASCADE:
            case ON:
            case DELETE:
            case SET:
            case UPDATE:
            case INSERT:
            case INTO:
            case VALUES:
            case SELECT:
            case FROM:
            case WHERE:
            case AS:
            case GROUP:
            case ORDER:
            case BY:
            case ASC:
            case DESC:
            case LIMIT:
            case JOIN:
                return txtCodigo.getStyle("sqlKeyword");
            case INTEGER_TYPE:
            case BIGINT_TYPE:
            case VARCHAR_TYPE:
            case DECIMAL_TYPE:
            case NUMERIC_TYPE:
            case DATE_TYPE:
            case TEXT_TYPE:
            case BOOLEAN_TYPE:
            case SERIAL_TYPE:
                return txtCodigo.getStyle("identifier");
            case ENTERO:
            case DECIMAL:
                return txtCodigo.getStyle("numero");
            case FECHA:
                return txtCodigo.getStyle("fecha");
            case ID:
                return txtCodigo.getStyle("id");
            case CADENA:
                return txtCodigo.getStyle("cadena");
            case BOOLEAN:
            case AGGREGATE_FUNCTION:
            case SUM:
            case AVG:
            case COUNT:
            case MIN:
            case MAX:
                return txtCodigo.getStyle("boolean");
            case SIGNO:
            case RIGHT_PAREN:
            case LEFT_PAREN:
            case COMMA:
            case SEMICOLON:
            case EQUAL:
            case ARITHMETIC_OPERATOR:
            case RELATIONAL_OPERATOR:
            case SUMA:
            case RESTA:
            case DIVICION:
            case MULTIPLICACION:
            case MAYOR:
            case MENOR:
            case MAYOR_IGUAL:
            case MENOR_IGUAL:
                return txtCodigo.getStyle("signos");
            case LOGICAL_OPERATOR:
            case AND:
            case OR:
            case NOT:
                return txtCodigo.getStyle("logico");
            case COMMENT:
                return txtCodigo.getStyle("comment");
            default:
                return txtCodigo.getStyle("default");
        }
    }
}
