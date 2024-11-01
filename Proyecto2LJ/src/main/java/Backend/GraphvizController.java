/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import Flex.Token;
import Flex.TokenType;
import java.awt.List;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author alesso
 */
public class GraphvizController {

    private final ArrayList<Token> tokens;
    private String ruta;
    private StringBuilder graphContent;

    public GraphvizController(ArrayList<Token> token, String ruta) {
        this.tokens = token;
        this.ruta = ruta;
        this.graphContent = new StringBuilder();
    }

    public void generateDiagram() {
        graphContent.append("digraph G {\n");
        graphContent.append("    node [shape=record];\n");

        String tableName = "";
        ArrayList<String> columns = new ArrayList<>();
        ArrayList<String> constraints = new ArrayList<>();

        Iterator<Token> iterator = tokens.iterator();
        while (iterator.hasNext()) {
            Token token = iterator.next();
            switch (token.getType()) {
                case CREATE:
                    tableName = extractTableName(iterator);
                    break;
                case ID:
                    columns.add(extractColumn(iterator, token));
                    break;
                case CONSTRAINT:
                    constraints.add(extractConstraint(iterator, token));
                    break;
            }
        }

        graphContent.append("    ").append(tableName).append(" [label=\"{")
                .append(tableName).append("|");

        for (String column : columns) {
            graphContent.append(column).append("\\l");
        }

        for (String constraint : constraints) {
            graphContent.append(constraint).append("\\l");
        }

        graphContent.append("}\"];\n");
        graphContent.append("}");

        drawGraph();
    }

    private String extractTableName(Iterator<Token> iterator) {
        Token tableToken = iterator.next(); 
        if (tableToken.getType() == TokenType.TABLE) {
            Token tableNameToken = iterator.next();
            return tableNameToken.getLexema();
        }
        return "";
    }

    private String extractColumn(Iterator<Token> iterator, Token columnToken) {
        StringBuilder columnDesc = new StringBuilder(columnToken.getLexema());

        while (iterator.hasNext()) {
            Token nextToken = iterator.next();
            if (nextToken.getType() == TokenType.COMMA || nextToken.getType() == TokenType.RIGHT_PAREN) {
                break;
            }
            columnDesc.append(" ").append(nextToken.getLexema());
        }
        return columnDesc.toString();
    }

    private String extractConstraint(Iterator<Token> iterator, Token constraintToken) {
        StringBuilder constraintDesc = new StringBuilder("CONSTRAINT ");

        Token nameToken = iterator.next();
        constraintDesc.append(nameToken.getLexema()).append(":");

        while (iterator.hasNext()) {
            Token nextToken = iterator.next();
            constraintDesc.append(" ").append(nextToken.getLexema());
            if (nextToken.getType() == TokenType.RIGHT_PAREN) {
                break;
            }
        }
        return constraintDesc.toString();
    }

    private void drawGraph() {
        String rutaDot = ruta + "tabla_graph.dot";
        String rutaPng = ruta + "tablas_diagrama.png";

        try {
            
            dotFileMaker(graphContent.toString(), rutaDot);

            ProcessBuilder processBuilder = new ProcessBuilder("dot", "-Tpng", "-o", rutaPng, rutaDot);
            processBuilder.redirectErrorStream(true); 
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder output = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                output.append(line).append(System.lineSeparator());
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Diagrama generado exitosamente en: " + rutaPng);
            } else {
                System.err.println("Error al generar el diagrama. Código de salida: " + exitCode);
                System.err.println("Salida del proceso: " + output.toString());
            }
        } catch (IOException e) {
            System.err.println("Error de entrada/salida: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("El proceso fue interrumpido: " + e.getMessage());
            Thread.currentThread().interrupt(); 
        }
    }

    private void dotFileMaker(String contenido, String ruta) {
        try (FileWriter fileWriter = new FileWriter(ruta); PrintWriter printWriter = new PrintWriter(fileWriter)) {
            printWriter.write(contenido);
        } catch (IOException e) {
            System.err.println("Error al crear el archivo .dot: " + e.getMessage());
        }
    }

    public String getPathImagen() {
        return ruta;
    }

}
