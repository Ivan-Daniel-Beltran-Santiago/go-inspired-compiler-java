package com.mycompany.go_based_compiler;

public class Nodo {
    String Lexema;
    int Token;
    int Linea;
    Nodo Siguiente = null;
    
    Nodo(String Lexema, int Token, int Linea){
        this.Lexema = Lexema;
        this.Token = Token;
        this.Linea = Linea;
    }
}
