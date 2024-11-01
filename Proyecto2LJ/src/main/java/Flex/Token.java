/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Flex;

/**
 *
 * @author alesso
 */
public class Token {

    private String lexema;
    private TokenType type;
    private String modificacion;
    private int fila;
    private int columna;

    public Token(String value, TokenType type, int fila, int columna) {
        this.lexema = value;
        this.type = type;
        this.fila = fila;
        this.columna = columna;
    }

    public TokenType getType() {
        return type;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public String getModificacion() {
        return modificacion;
    }

    public void setModificacion(String modificacion) {
        this.modificacion = modificacion;
    }

    @Override
    public String toString() {
        return "Token{" + "tipoToken=" + type + ", fila=" + fila + ", columna=" + columna + ", lexema=" + lexema + '}';
    }
}
