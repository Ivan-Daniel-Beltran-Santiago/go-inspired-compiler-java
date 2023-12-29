package com.mycompany.go_based_compiler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

class Analisis_Lexico{
    //Declaración de variables
    Nodo Cabeza_de_Nodo = null; // Cabeza, es decir, final del nodo
    Nodo Inicio_Nodo = null; // Inicio del nodo, utilidad para imprimir 
    
    int Estado, Caracter = 0; // Estado y Caracter
    int Columna, ValorMatrizTransicion, NumeroRenglon = 1; // Columna, Valor de matriz de transición, y Número de renglón
    
    String Lexema = ""; // Lexema
    
    boolean errorEncontrado = false; // Error encontrado durante el análisis léxico
    
    // Matriz de transición
    int MatrizTransicion [][] = {
        
            //     l    d    .    +    -    *    ^    /    <    >    =    !    &    |    ;    ,    :    (    )    "   eb   nl  tab  fda   oc    {    }
            //     0    1    2    3    4    5    6    7    8    9   10   11   12   13   14   15   16   17   18   19   20   21   22   23   24   25   26
        /* 0*/ {   1,   2, 120, 103, 104, 105, 107,   5,   8,   9,  10,  11,  12,  13, 117, 118,  14, 121, 122,  15,   0,   0,   0,   0, 506, 125, 126},
        /* 1*/ {   1,   1, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100},
        /* 2*/ { 101,   2,   3, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101},
        /* 3*/ { 501,   4, 501, 501, 501, 501, 501, 501, 501, 501, 501, 501, 501, 501, 501, 501, 501, 501, 501, 501, 501, 501, 501, 501, 501, 501, 501},
        /* 4*/ { 102,   4, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102},
        /* 5*/ { 106, 106, 106, 106, 106,   6, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106},
        /* 6*/ {   6,   6,   6,   6,   6,   7,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6, 500,   6,   6,   6},
        /* 7*/ {   6,   6,   6,   6,   6,   6,   6,   0,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
        /* 8*/ { 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 110, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108},
        /* 9*/ { 109, 109, 109, 109, 109, 109, 109, 109, 109, 109, 111, 109, 109, 109, 109, 109, 109, 109, 109, 109, 109, 109, 109, 109, 109, 109, 109},
        /*10*/ { 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 112, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502},
        /*11*/ { 116, 116, 116, 116, 116, 116, 116, 116, 116, 116, 113, 116, 116, 116, 116, 116, 116, 116, 116, 116, 116, 116, 116, 116, 116, 116, 116},
        /*12*/ {   1, 503, 503, 503, 503, 503, 503, 503, 503, 503, 503, 503, 114, 503, 503, 503, 503, 503, 503, 503, 503, 503, 503, 503, 503, 503, 503},
        /*13*/ { 504, 504, 504, 504, 504, 504, 504, 504, 504, 504, 504, 504, 504, 115, 504, 504, 504, 504, 504, 504, 504, 504, 504, 504, 504, 504, 504},
        /*14*/ { 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 124, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502},
        /*15*/ {  15,  15,  15,  15,  15,  15,  15,  15,  15,  15,  15,  15,  15,  15,  15,  15,  15,  15,  15, 123,  15, 505,  15,  15,  15,  15,  15}
    };
    
    // Palabras reservadas
    String palabrasReservadas [][] = {
        
        //              0      1
        /* 0*/ {       "bool", "200"},
        /* 1*/ {      "break", "201"},
        /* 2*/ {       "case", "202"},
        /* 3*/ {       "chan", "203"},
        /* 4*/ {      "const", "204"},
        /* 5*/ {   "continue", "205"},
        /* 6*/ {    "default", "206"},
        /* 7*/ {      "defer", "207"},
        /* 8*/ {       "else", "208"},
        /* 9*/ {"fallthrough", "209"},
        /*10*/ {      "false", "210"},
        /*11*/ {      "float", "211"},
        /*12*/ {        "for", "212"},
        /*13*/ {       "func", "213"},
        /*14*/ {         "go", "214"},
        /*15*/ {       "goto", "215"},
        /*16*/ {         "if", "216"},
        /*17*/ {     "import", "217"},
        /*18*/ {        "int", "218"},
        /*19*/ {  "interface", "219"},
        /*20*/ {        "map", "220"},
        /*21*/ {       "null", "221"},
        /*22*/ {    "package", "222"},
        /*23*/ {    "println", "223"},
        /*24*/ {      "range", "224"},
        /*25*/ {     "return", "225"},
        /*26*/ {       "scan", "226"},
        /*27*/ {     "select", "227"},
        /*28*/ {     "string", "228"},
        /*29*/ {     "struct", "229"},
        /*30*/ {     "switch", "230"},
        /*31*/ {       "true", "231"},
        /*32*/ {       "type", "232"},
        /*33*/ {        "var", "233"}
    };
    
    // Errores
    String Errores [][] = {
        
        //                            0      1
        /* 0*/ {   "Faltó cerrar comentario", "500"},
        /* 1*/ {        "Número mal formado", "501"},
        /* 2*/ {        "Se espera un igual", "502"},
        /* 3*/ {            "Se espera un &", "503"},
        /* 4*/ {            "Se espera un |", "504"},
        /* 5*/ {"Se espera cierre de cadena", "505"},
        /* 6*/ {         "Símbolo no válido", "506"}
    };
    
    public Analisis_Lexico(){
        
    }
    
    public  Nodo AnalizarCodigo(String Archivo_a_compilar){
        try{
            // Aquí tomamos el .txt y lo convertimos en una lista de Strings, una por cada linea de código
            List<String> Codigo_a_Analizar = Files.readAllLines(Paths.get(Archivo_a_compilar), StandardCharsets.UTF_8);
            // Aquí unimos todas las listas de Strings para que sea un solo String
            String String_del_codigo_a_analizar = String.join(System.lineSeparator(), Codigo_a_Analizar);
            // Aquí convertimos todo en un arreglo de tamaño fijo donde cada elemento es un caracter, incluyendo saltos de linea
            char[] Analizar_codigo = String_del_codigo_a_analizar.toCharArray();

            // Aquí recorremos el arreglo uno por uno, imposible leer mas caracteres de los que hay
            for(int Indice_del_codigo_a_analizar = 0; Indice_del_codigo_a_analizar < Analizar_codigo.length; Indice_del_codigo_a_analizar++){
                // Ya que caracter es un entero, aquí tomamos el elemento del indice correspondiente, y lo convertimos a su modo ASCII
                Caracter = (int)Analizar_codigo[Indice_del_codigo_a_analizar];

                // Aquí llamamos a la clase asignarNumeroColumna()
                Columna = asignarNumeroColumna(Analizar_codigo[Indice_del_codigo_a_analizar]);

                ValorMatrizTransicion = MatrizTransicion[Estado][Columna];

                if (ValorMatrizTransicion < 100){ // Cambiar de estado
                    
                        Estado = ValorMatrizTransicion;

                        if (Estado == 0){
                            
                            Lexema = "";
                            
                        }
                        else {
                           
                            Lexema = Lexema + Analizar_codigo[Indice_del_codigo_a_analizar];
                        }
                        
                        if(Caracter == 10) {
                            
                            NumeroRenglon += 1;
                            
                        }
                        
                } else if (ValorMatrizTransicion >= 100 && ValorMatrizTransicion < 500){ // Estado final
                    
                    if (ValorMatrizTransicion == 100){
                        
                        // Aquí llamamos a la clase ValidaSiEsPalabraReservada()
                        ValidaSiEsPalabraReservada();
                        
                    }

                    if (ValorMatrizTransicion == 100 || ValorMatrizTransicion == 101 || ValorMatrizTransicion == 102 || ValorMatrizTransicion == 106 || ValorMatrizTransicion == 108 || ValorMatrizTransicion == 109 || ValorMatrizTransicion == 116 || ValorMatrizTransicion == 127 || ValorMatrizTransicion >= 200){
                        
                        Indice_del_codigo_a_analizar--; // Retrocede a una posición el apuntador
                        
                    }
                    else { 
                        
                        Lexema = Lexema + Analizar_codigo[Indice_del_codigo_a_analizar];
                    }
                    
                    // Aquí llamamos la clase InserteNodoLexico()
                    InserteNodoLexico();
                    
                    if(Caracter == 10) {
                        
                        System.out.println("Salto de linea");
                        NumeroRenglon += 1;
                        
                    }
                    
                    Estado = 0;
                    Lexema = "";
                    
                } else { // Estado de error
                    //Aquí llamamos a la clase ImprimeMensajeError()
                    ImprimeMensajeError();
                    
                    // Se cierra el análisis léxico del código tan pronto como encuentre un error, dejando a las partes restantes sin analizar
                    System.exit(0);
                }
            }

            //Aquí llamamos a la clase ImprimeNodosAnalizadorLexico()
            ImprimeNodosAnalizadorLexico();
            return Inicio_Nodo;
            
        } catch (IOException e) {
            
            System.out.println(e.getMessage());
            return Inicio_Nodo;
            
        }
    }

    // Aquí asignamos un número de columna a analizar para que podamos encontrar un valor de la matriz de transición según el caracter analizado
    private int asignarNumeroColumna(char Caracter_de_entrada){
        
        if(Character.isLetter(Caracter_de_entrada)) return 0;
        
        if(Character.isDigit(Caracter_de_entrada)) return 1;
        
            switch(Caracter_de_entrada){
                case '.':
                    
                    return 2;
                    
                case '+':
                    
                    return 3;
                    
                case '-':
                    
                    return 4;
                    
                case '*':
                    
                    return 5;
                    
                case '^':
                    
                    return 6;
                    
                case '/':
                    
                    return 7;
                    
                case '<':
                    
                    return 8;
                    
                case '>':
                    
                    return 9;
                    
                case '=':
                    
                    return 10;
                    
                case '!':
                    
                    return 11;
                    
                case '&':
                    
                    return 12;
                    
                case '|':
                    
                    return 13;
                    
                case ';':
                    
                    return 14;
                    
                case ',':
                    
                    return 15;
                    
                case ':':
                    
                    return 16;
                    
                case '(':
                    
                    return 17;
                    
                case ')':
                    
                    return 18;
                    
                case '"':
                    
                    return 19;
                    
                case ' ':
                    
                    return 20;
                    
                case '{':
                    
                    return 25;
                    
                case '}':
                    
                    return 26;
                    
                case 10:
                    
                    return 21;
                    
                case 9:
                    
                    return 22;
                    
                case 3:
                    
                    return 23;
                    
                default:
                    
                    return 24;
                    
            }
    }
    
    private void ImprimeNodosAnalizadorLexico(){
        
        Nodo Nodo_Actual = Inicio_Nodo;
        
        while(Nodo_Actual != null){
            
            System.out.println("Lexema: " + Nodo_Actual.Lexema + " | Token: " + Nodo_Actual.Token + " | Linea: " + Nodo_Actual.Linea);
            Nodo_Actual = Nodo_Actual.Siguiente;
            
        }
    }
    
    private void ValidaSiEsPalabraReservada(){
        for(String[] palabraReservada: palabrasReservadas){
            
            if(Lexema.equals(palabraReservada[0])){
                
                ValorMatrizTransicion = Integer.parseInt(palabraReservada[1]);
                
            }
            
        }
    }
    
    private void ImprimeMensajeError(){
        if(Caracter != 1 && ValorMatrizTransicion >= 500){
            
            for(String[] Error: Errores){
                
                if(ValorMatrizTransicion == Integer.parseInt(Error[1])){
                    
                    System.out.println("El error encontrado es: " + Error[0] + " error " + Error[1] + " en caracter " + (char)Caracter + " en el renglón " + (NumeroRenglon - 1));
                
                }
            }
            
            errorEncontrado = true;
        }
    }
    
    private void InserteNodoLexico(){
        
        // Aquí llamamos a la clase Nodo
        Nodo Node = new Nodo(Lexema, ValorMatrizTransicion, NumeroRenglon);
        
        if (Cabeza_de_Nodo == null){
            
            Inicio_Nodo = Cabeza_de_Nodo = Node;
            
        }
        else {
            
            Cabeza_de_Nodo.Siguiente = Node;
            Cabeza_de_Nodo = Node;
        }
    }
}