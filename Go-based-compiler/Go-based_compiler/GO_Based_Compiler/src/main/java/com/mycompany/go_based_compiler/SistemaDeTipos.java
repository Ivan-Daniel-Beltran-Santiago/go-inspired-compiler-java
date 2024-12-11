package com.mycompany.go_based_compiler;

import java.util.HashMap;

public class SistemaDeTipos {

    private final HashMap<String, String> tablaDeSimbolos;

    public SistemaDeTipos() {
        // Tabla para almacenar variables y sus tipos
        tablaDeSimbolos = new HashMap<>();
    }

    public void registrarVariable(String nombre, String tipo) {
        System.out.println("Intentando registrar variable: " + nombre + " de tipo: " + tipo);

        if (tablaDeSimbolos.containsKey(nombre)) {
            throw new IllegalStateException("Error: La variable '" + nombre + "' ya fue declarada.");
        }
        if (!esTipoValido(tipo)) {
            throw new IllegalArgumentException("Tipo no válido: " + tipo);
        }
        tablaDeSimbolos.put(nombre, tipo);
    }

    // Validar si una variable existe
    public boolean variableDefinida(String nombre) {
        return tablaDeSimbolos.containsKey(nombre);
    }

    // Obtener el tipo de una variable
    public String obtenerTipo(String nombre) {
        if (!variableDefinida(nombre)) {
            throw new IllegalArgumentException("Variable no definida: " + nombre);
        }
        return tablaDeSimbolos.get(nombre);
    }

    // Verificar compatibilidad de tipos
    public boolean sonTiposCompatibles(String tipo1, String tipo2) {
        // Ejemplo: en Go, int y float pueden no ser compatibles sin conversión
        if (tipo1.equals(tipo2)) {
            return true;
        }
        return (tipo1.equals("float") && tipo2.equals("int"))
                || (tipo1.equals("int") && tipo2.equals("float"));
    }

    // Verificar si el tipo es válido
    private boolean esTipoValido(String tipo) {
        return tipo.equals("int") || tipo.equals("float") || tipo.equals("bool") || tipo.equals("string");
    }
}
