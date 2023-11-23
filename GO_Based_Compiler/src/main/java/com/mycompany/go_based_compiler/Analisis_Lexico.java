package com.mycompany.go_based_compiler;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

class Analisis_Lexico{
    Nodo Cabeza_de_Nodo = null; // Cabeza
    
    int Estado, Caracter = 0; // Estado y Caracter
    int Columna, ValorMatrizTransicion, NumeroRenglon = 1; // Columna, Valor de matriz de transición, y Número de renglón
    
    String Lexema = ""; // Lexema
    
    boolean errorEncontrado = false; // Error encontrado durante el análisis léxico
     
    private String nombreArchivo;
    private String directorioArchivo;
    
    private String Archivo_a_compilar;
    
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
        /*12*/ { 503, 503, 503, 503, 503, 503, 503, 503, 503, 503, 503, 503, 114, 503, 503, 503, 503, 503, 503, 503, 503, 503, 503, 503, 503, 503, 503},
        /*13*/ { 504, 504, 504, 504, 504, 504, 504, 504, 504, 504, 504, 504, 504, 115, 504, 504, 504, 504, 504, 504, 504, 504, 504, 504, 504, 504, 504},
        /*14*/ { 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 124, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502},
        /*15*/ {  15,  15,  15,  15,  15,  15,  15,  15,  15,  15,  15,  15,  15,  15,  15,  15,  15,  15,  15, 123,  15, 505,  15,  15,  15,  15,  15}

    };
    
    // Palabras reservadas
    String reservedWords [][] = {
        
        //              0      1
        /* 0*/ {      "break", "200"},
        /* 1*/ {       "case", "201"},
        /* 2*/ {       "chan", "202"},
        /* 3*/ {      "const", "203"},
        /* 4*/ {   "continue", "204"},
        /* 5*/ {    "default", "205"},
        /* 6*/ {      "defer", "206"},
        /* 7*/ {       "else", "207"},
        /* 8*/ {"fallthrough", "208"},
        /* 8*/ {      "false", "209"},
        /* 9*/ {        "for", "210"},
        /*10*/ {       "func", "211"},
        /*11*/ {         "go", "212"},
        /*12*/ {       "goto", "213"},
        /*13*/ {         "if", "214"},
        /*14*/ {     "import", "215"},
        /*15*/ {  "interface", "216"},
        /*16*/ {        "map", "217"},
        /*16*/ {       "null", "218"},
        /*17*/ {    "package", "219"},
        /*18*/ {    "Println", "220"},
        /*19*/ {      "range", "221"},
        /*20*/ {     "return", "222"},
        /*21*/ {     "select", "223"},
        /*22*/ {     "struct", "224"},
        /*23*/ {     "switch", "225"},
        /*23*/ {       "true", "226"},
        /*24*/ {       "type", "227"},
        /*25*/ {        "var", "228"}
            
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
    
    RandomAccessFile Codigo_a_analizar = null;
    
    public Analisis_Lexico(){
        solicitarNombreDirectorio();
        AnalizarCodigoArray();
    }

    private void solicitarNombreDirectorio(){
        Scanner consulta = new Scanner(System.in);
        
        boolean nombreValido = false;
        boolean directorioValido = false;
        
        while(!nombreValido || !directorioValido){
            System.out.println("Por favor, ingrese el nombre del archivo (.txt): \nArchivo = ");
            this.nombreArchivo = consulta.nextLine();
            
            if(nombreArchivo.endsWith(".txt")){
                nombreValido = true;
            } else{
                System.out.println("Error: El formato del archivo no es válido. Inténtelo de nuevo.");
            }
            
            System.out.println("Por favor, ingrese el nombre del directorio de inicio de búsqueda: \nDirectorio = ");
            this.directorioArchivo = consulta.nextLine();
            
            File directorioDelArchivo = new File(directorioArchivo);
            
            if(directorioDelArchivo.isDirectory()){
                File archivo = new File(directorioArchivo, nombreArchivo);
                if (archivo.exists()){
                    directorioValido = true;
                } else{
                    System.out.println("Error: El archivo no se encontró en la dirección dada. Inténtelo de nuevo.");
                }
            } else{
                System.out.println("Error: La ruta de acceso no es un directorio válido. Inténtelo de nuevo.");
            }
        }
        
        this.Archivo_a_compilar = this.directorioArchivo + this.nombreArchivo;
    }
    
    private void AnalizarCodigo(){
        try {
            Codigo_a_analizar = new RandomAccessFile(Archivo_a_compilar, "r");
            
            System.out.println(Codigo_a_analizar.length());
            
            int CompararCaracteres = 0;
                        
            while (Caracter != -1){ // Se lee caracter por caracter mientras no sea fin del archivo, o fda
                Caracter = Codigo_a_analizar.read();
                                
                Columna = asignarNumeroColumna();
                
                ValorMatrizTransicion = MatrizTransicion[Estado][Columna];
                
                System.out.println("Símbolo: " + Caracter + ", Valor de matriz de transicion: " + ValorMatrizTransicion);
                
                CompararCaracteres++;
                
                System.out.println(CompararCaracteres);
                
                if (ValorMatrizTransicion < 100){ // Cambiar de estado
                    Estado = ValorMatrizTransicion;

                    if (Estado == 0){
                        Lexema = "";
                    }
                    else {
                        Lexema = Lexema + (char)Caracter;
                    }
                }
                else if (ValorMatrizTransicion >= 100 && ValorMatrizTransicion < 500){ // Estado final
                    if (ValorMatrizTransicion == 100){
                        ValidaSiEsPalabraReservada();
                    }

                    if (ValorMatrizTransicion == 100 || ValorMatrizTransicion == 101 || ValorMatrizTransicion == 102 || ValorMatrizTransicion == 106 || ValorMatrizTransicion == 108 || ValorMatrizTransicion == 109 || ValorMatrizTransicion == 116 || ValorMatrizTransicion == 127 || ValorMatrizTransicion == 203){
                        Codigo_a_analizar.seek(Codigo_a_analizar.getFilePointer() - 1); // Retrocede a una posición el apuntador
                    }
                    else { 
                        Lexema = Lexema + (char)Caracter;
                    }

                    InserteNodoLexico();
                    Estado = 0;
                    Lexema = "";
                } else { // Estado de error
                    ImprimeMensajeError();
                    break;
                }
            }
            
            ImprimeNodosAnalizadorLexico();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(Codigo_a_analizar != null){
                    Codigo_a_analizar.close();
                }
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void AnalizarCodigoArray(){
        try{
            //Tomamos el txt y lo convertimos en una lista de Strings, una por cada linea de código
        List<String> codigoAnalizarArchivo = Files.readAllLines(Paths.get(Archivo_a_compilar), StandardCharsets.UTF_8);
        //Las unimos todas para que sea un solo String
        String codigoAnalizarString = String.join("", codigoAnalizarArchivo);
        //Lo convertimos todo en un arreglo de tamaño fijo donde cada elemento es un caracter, incluyendo saltos de linea y la vaina
        char[] codigoAnalizar = codigoAnalizarString.toCharArray();

        //Recorremos el arreglo uno por uno, imposible leer mas caracteres de los que hay
        for(int indice = 0; indice < codigoAnalizar.length; indice++){
            //Ya que caracter es un entero, tomamos el elemento del indice correspondiente, y lo convertimos a su modo ASCII
            Caracter = (int)codigoAnalizar[indice];

            Columna = asignarNumeroColumna();

            ValorMatrizTransicion = MatrizTransicion[Estado][Columna]

            if (ValorMatrizTransicion < 100){ // Cambiar de estado
                    Estado = ValorMatrizTransicion;

                    if (Estado == 0){
                        Lexema = "";
                    }
                    else {
                        Lexema = Lexema + (char)Caracter;
                    }
                }
                else if (ValorMatrizTransicion >= 100 && ValorMatrizTransicion < 500){ // Estado final
                    if (ValorMatrizTransicion == 100){
                        ValidaSiEsPalabraReservada();
                    }

                    if (ValorMatrizTransicion == 100 || ValorMatrizTransicion == 101 || ValorMatrizTransicion == 102 || ValorMatrizTransicion == 106 || ValorMatrizTransicion == 108 || ValorMatrizTransicion == 109 || ValorMatrizTransicion == 116 || ValorMatrizTransicion == 127 || ValorMatrizTransicion == 203){
                        indice--; // Retrocede a una posición el apuntador
                    }
                    else { 
                        Lexema = Lexema + (char)Caracter;
                    }

                    InserteNodoLexico();
                    Estado = 0;
                    Lexema = "";
                } else { // Estado de error
                    ImprimeMensajeError();
                    break;
                }
            }
            
            ImprimeNodosAnalizadorLexico();
        }catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(Codigo_a_analizar != null){
                    Codigo_a_analizar.close();
                }
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        }
    }
    
    private int asignarNumeroColumna(){
        if(Character.isLetter(Caracter)){
            return 0;
        } else if(Character.isDigit(Caracter)){
            return 1;
        } else {
            switch(Caracter){
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
                    {
                        NumeroRenglon += 1;
                        return 21;
                    }
                case 9:
                    return 22;
                default:
                    return 24;
            }
        }
    }
    
    private void ImprimeNodosAnalizadorLexico(){    
        while(Cabeza_de_Nodo != null){
            System.out.println(Cabeza_de_Nodo.Lexema + " " + Cabeza_de_Nodo.Token + " " + (Cabeza_de_Nodo.Linea - 1));
            Cabeza_de_Nodo = Cabeza_de_Nodo.Siguiente;
        }
    }
    
    private void ValidaSiEsPalabraReservada(){
        for(String[] reservedWord: reservedWords){
            if(Lexema.equals(reservedWord[0])){
                ValorMatrizTransicion = Integer.parseInt(reservedWord[1]);
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
        Nodo Node = new Nodo(Lexema, ValorMatrizTransicion, NumeroRenglon);
        
        if (Cabeza_de_Nodo == null){
            Cabeza_de_Nodo = Node;
        }
        else {
            Cabeza_de_Nodo.Siguiente = Node;
            Cabeza_de_Nodo = Node;
        }
    }
}
