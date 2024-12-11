package com.mycompany.go_based_compiler;

public class Analisis_Semantico {

    private final SistemaDeTipos sistemaDeTipos;

    boolean errorEncontrado = false;

    public Analisis_Semantico() {
        sistemaDeTipos = new SistemaDeTipos(); // Inicializa el sistema de tipos
    }

    public void analizarSemantica(Nodo raiz) {
        Nodo nodoActual = raiz;

        while (nodoActual != null) {
            switch (nodoActual.Token) {
                case 233: // Token para la palabra reservada 'var'
                    nodoActual = inicializarVariable(nodoActual);
                    break;

                case 100: // Identificador (posiblemente usado)
                    validarUsoIdentificador(nodoActual);
                    break;

                // Agrega más casos según tus necesidades
                default:
                    break;
            }

            nodoActual = nodoActual.Siguiente;
        }
    }

    private Nodo inicializarVariable(Nodo nodoActual) {
        nodoActual = nodoActual.Siguiente;

        if (nodoActual.Token == 100) { // Identificador
            String nombreVariable = nodoActual.Lexema;

            nodoActual = nodoActual.Siguiente;
            String tipo = obtenerTipoDesdeToken(nodoActual.Token);

            if (tipo == null) {
                System.out.println("Error: Tipo inválido en la línea " + nodoActual.Linea);
                errorEncontrado = true;
                return null;
            }

            try {
                sistemaDeTipos.registrarVariable(nombreVariable, tipo);
            } catch (IllegalStateException e) {
                System.out.println("Error: " + e.getMessage() + " Línea: " + nodoActual.Linea);
                errorEncontrado = true;
                return null;
            }

            nodoActual = nodoActual.Siguiente;

            if (nodoActual.Token == 124) { // Token del ':='
                nodoActual = nodoActual.Siguiente;
                String tipoValor = obtenerTipoDesdeToken(nodoActual.Token);

                if (!sistemaDeTipos.sonTiposCompatibles(tipo, tipoValor)) {
                    System.out.println("Error: Tipos incompatibles en la línea " + nodoActual.Linea);
                    errorEncontrado = true;
                    return null;
                }
            }

            if (nodoActual.Siguiente.Token == 117) { // Token del ';'
                return nodoActual.Siguiente;
            } else {
                System.out.println("Error: Se esperaba ';' en la línea " + nodoActual.Linea);
                errorEncontrado = true;
                return null;
            }
        } else {
            System.out.println("Error: Se esperaba un identificador en la línea " + nodoActual.Linea);
            errorEncontrado = true;
            return null;
        }
    }

    private void validarUsoIdentificador(Nodo nodoActual) {
        String nombreVariable = nodoActual.Lexema;

        if (!sistemaDeTipos.variableDefinida(nombreVariable)) {
            System.out.println("Error: Variable no definida '" + nombreVariable + "' en la línea " + nodoActual.Linea);
            System.exit(0);
        }
    }

    private String obtenerTipoDesdeToken(int token) {
        switch (token) {
            case 218:
                return "int";
            case 211:
                return "float";
            case 200:
                return "bool";
            case 228:
                return "string";
            case 101:
                return "int"; // Literales enteros
            case 102:
                return "float"; // Literales flotantes
            case 231:
                return "bool"; // true
            case 210:
                return "bool"; // false
            case 123:
                return "string"; // Literales de cadena
            default:
                return null;
        }
    }
}
