package com.mycompany.go_based_compiler;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GO_Based_Compiler {

    public static void main(String[] args) {
        
        String Archivo_a_compilar = Seleccionar_Archivo();
        
        Analisis_Lexico Lex_Analysis = new Analisis_Lexico();
        
        if(!Lex_Analysis.errorEncontrado){
            
            System.out.println("--------------------------------------------");
            System.out.println("Análisis léxico.");
            System.out.println("--------------------------------------------\n");
            
            Nodo Lista_de_Nodos = Lex_Analysis.AnalizarCodigo(Archivo_a_compilar);
            
            System.out.println("\nAnálisis léxico terminado con éxito.");
        
            Analisis_Sintactico Syntax_Analysis = new Analisis_Sintactico();
            
            System.out.println("Procediendo a inicializar el análisis sintáctico.\n");
            
            if(!Syntax_Analysis.errorEncontrado){
                
                System.out.println("--------------------------------------------");
                System.out.println("Análisis sintáctico.");
                System.out.println("--------------------------------------------\n");
                
                Nodo Lista_Nodos_Sintactico = Syntax_Analysis.AnalizarSintaxis(Lista_de_Nodos);
            }
        }   
    }
    
    // Método para seleccionar archivos de texto a través de una ventana de selección de archivos
    private static String Seleccionar_Archivo() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter Filtro_de_archivos = new FileNameExtensionFilter("Archivos de texto (.txt)", "txt");
        fileChooser.setFileFilter(Filtro_de_archivos);

        int Seleccion = fileChooser.showOpenDialog(null);

        if (Seleccion == JFileChooser.APPROVE_OPTION) {
            File Archivo_seleccionado = fileChooser.getSelectedFile();

            if (Archivo_seleccionado.getName().endsWith(".txt")) {
                
                return Archivo_seleccionado.getAbsolutePath();
                
            } else {
                
                // El archivo seleccionado no es un archivo de texto, cancelar todo
                System.out.println("Error: El archivo seleccionado no es un archivo de texto. Cancelando.");
                System.exit(0);
                return null;
            }
        } else {
            
            // El usuario canceló la selección
            System.exit(0);
            return null;
        }
    }
}
