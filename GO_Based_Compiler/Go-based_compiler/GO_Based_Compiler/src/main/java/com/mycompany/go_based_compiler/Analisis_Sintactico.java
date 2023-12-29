package com.mycompany.go_based_compiler;

public class Analisis_Sintactico {
    
    boolean errorEncontrado = false;
    
    public Analisis_Sintactico(){
         
    }
    
    public Nodo AnalizarSintaxis(Nodo Nodo_Actual){
        while(Nodo_Actual.Siguiente != null){
            
            if(Nodo_Actual.Token  == 222){ // Token de la palabra reservada 'package'
                
                Nodo_Actual = Nodo_Actual.Siguiente;
                
                if(Nodo_Actual.Token  == 100){ // Token para identificadores
                    
                    Nodo_Actual = Nodo_Actual.Siguiente;
                    
                    if (Nodo_Actual.Token  == 117){ // Token del ;
                        
                        Nodo_Actual = Sintaxis_de_Imports(Nodo_Actual); 
                        Nodo_Actual = Nodo_Actual.Siguiente;
                                                
                        if(Nodo_Actual.Token  == 213){ // Token de la palabra reservada 'func'
                        
                            Nodo_Actual = Nodo_Actual.Siguiente;

                            if(Nodo_Actual.Token  == 100){ // Token para identificadores

                                Nodo_Actual = Nodo_Actual.Siguiente;

                                if(Nodo_Actual.Token  == 121){ // Token para inicio de paréntesis

                                    Nodo_Actual = Nodo_Actual.Siguiente;

                                    if(Nodo_Actual.Token  == 122){ // Token para cierre de paréntesis

                                        Nodo_Actual = Nodo_Actual.Siguiente;

                                        if(Nodo_Actual.Token  == 125){ // Token para inicio de llaves
                                            
                                            Nodo_Actual = Contenido_de_las_Funciones(Nodo_Actual);
                                                                                                                                    
                                            if(Nodo_Actual.Token  == 126){ // Token para cierre de llaves
                                                
                                                if(Nodo_Actual.Siguiente != null){ // Tokens de la palabra reservada 'func' y 'type'
                                                    
                                                    Nodo_Actual = Declaracion_de_Funciones_y_Estructuras(Nodo_Actual);
                                                    break;
                                                    
                                                } else {
                                                    
                                                    System.out.println("\nAnálisis sintáctico terminado con éxito.");
                                                    break;
                                                }                                                                                              
                                            } else{
                                                
                                                System.out.println("Se espera un cierre de llaves en la línea " + Nodo_Actual.Linea);
                                                errorEncontrado = true;
                                                System.exit(0);
                                            }
                                        } else {

                                            System.out.println("Se espera un inicio de llaves en la línea " + Nodo_Actual.Linea);
                                            errorEncontrado = true;
                                            System.exit(0);
                                        }
                                    } else {

                                        System.out.println("Se espera un cierre de paréntesis en la línea " + Nodo_Actual.Linea);
                                        errorEncontrado = true;
                                        System.exit(0);
                                    }
                                } else {

                                    System.out.println("Se espera un inicio de paréntesis en la línea " + Nodo_Actual.Linea);
                                    errorEncontrado = true;
                                    System.exit(0);
                                }
                            } else {

                                System.out.println("Se espera un identificador en la línea " + Nodo_Actual.Linea);
                                errorEncontrado = true;
                                System.exit(0);
                            }
                        } else {

                            System.out.println("Se espera la palabra 'func' en la línea " + Nodo_Actual.Linea);
                            errorEncontrado = true;
                            System.exit(0);
                        }
                    } else {
                        
                        System.out.println("Se espera un ; en la línea " + Nodo_Actual.Linea);
                        errorEncontrado = true;
                        System.exit(0);
                    }
                } else {
                    
                    System.out.println("Se espera un identificador en la línea " + Nodo_Actual.Linea);
                    errorEncontrado = true;
                    System.exit(0);
                }
            } else {
                
                System.out.println("Se espera la palabra 'package' en la línea " + Nodo_Actual.Linea);
                errorEncontrado = true;
                System.exit(0);
            }
        }   
        
        return Nodo_Actual;
    }

    public Nodo Sintaxis_de_Imports (Nodo ImportarPaquetes) {
        while(ImportarPaquetes.Siguiente.Token == 217){ // Token de la palabra reservada 'import'
            
            ImportarPaquetes = ImportarPaquetes.Siguiente;
            
            if(ImportarPaquetes.Siguiente.Token == 121){ // Token de inicio de paréntesis
                
                ImportarPaquetes = ImportarPaquetes.Siguiente;
                
                if(ImportarPaquetes.Siguiente.Token == 123){ // Token de strings
                    
                    ImportarPaquetes = ImportarPaquetes.Siguiente;
                    
                    if(ImportarPaquetes.Siguiente.Token == 122){ // Token de cierre de paréntesis
                        
                        ImportarPaquetes = ImportarPaquetes.Siguiente;
                        
                        if(ImportarPaquetes.Siguiente.Token == 117){ // Token del ;
                        
                            ImportarPaquetes = ImportarPaquetes.Siguiente;

                        } else {

                            System.out.println("Se espera un ; en la línea " + ImportarPaquetes.Linea);
                            errorEncontrado = true;
                            System.exit(0);
                        }
                    } else {
                        
                        System.out.println("Se espera un cierre de paréntesis en la línea " + ImportarPaquetes.Linea);
                        errorEncontrado = true;
                        System.exit(0);
                    }
                } else {
                    
                    System.out.println("Se espera una cadena de caracteres en la línea " + ImportarPaquetes.Linea);
                    errorEncontrado = true;
                    System.exit(0);
                }
            } else {
                
                System.out.println("Se espera un inicio de paréntesis en la línea " + ImportarPaquetes.Linea);
                errorEncontrado = true;
                System.exit(0);
            }
        }
        
        return ImportarPaquetes;
    }
    
    public Nodo Contenido_de_las_Funciones(Nodo FunctionContent){
        
        FunctionContent = FunctionContent.Siguiente;
        
        while(FunctionContent != null && FunctionContent.Siguiente != null && FunctionContent.Siguiente.Token != 126){ // Token de cierre de llaves
            
            switch(FunctionContent.Token){
                case 100: // Token del identificador
                    
                    FunctionContent = Uso_de_Identificadores(FunctionContent);
                    break;
                
                case 126: // Token de cierre de llaves
                    
                    return FunctionContent;
                    
                case 212: // Token de la palabra reservada 'for
                    
                    FunctionContent = Declaracion_de_Bucle(FunctionContent);
                    break;
                    
                case 216: // Token de la palabra reservada 'if'
                    
                    FunctionContent = Declaracion_condicional(FunctionContent);
                    break;
                
                case 233: // Token de la palabra reservada 'var'
                    
                    FunctionContent = Inicializar_variables(FunctionContent);
                    break;
                   
                default:
                    
                    FunctionContent = null;
                    System.out.println("Error inesperado en la línea " + FunctionContent.Linea);
                    errorEncontrado = true;
                    System.exit(0);
            }
        }
        
        return FunctionContent;
    }

    public Nodo Inicializar_variables(Nodo VariableInitializator) {
        while(VariableInitializator.Siguiente.Token != 126){ // Token de cierre de llaves
            
            VariableInitializator = VariableInitializator.Siguiente;
            
            if(VariableInitializator.Token == 100){ // Token para identificadores

                VariableInitializator = Tipo_de_dato(VariableInitializator);
                VariableInitializator = VariableInitializator.Siguiente;

                if(VariableInitializator.Token == 124){ // Token del :=

                    VariableInitializator = Valores_de_Salida(VariableInitializator);
                    VariableInitializator = VariableInitializator.Siguiente;
                        
                    if(VariableInitializator.Token == 117){ // Token del ;

                        VariableInitializator = VariableInitializator.Siguiente;
                        break;

                    } else {

                        System.out.println("Se espera un ; en la línea " + VariableInitializator.Linea);
                        errorEncontrado = true;
                        System.exit(0);
                    }
                } 
                
                if(VariableInitializator.Token == 117){ // Token del ;

                    VariableInitializator = VariableInitializator.Siguiente;
                    break;

                } else {

                    System.out.println("Se espera un := o un ; en la línea " + VariableInitializator.Linea);
                    errorEncontrado = true;
                    System.exit(0);
                }
            } else {

                System.out.println("Se espera un identificador en la línea " + VariableInitializator.Linea);
                errorEncontrado = true;
                System.exit(0);
            }
        }
        
        return VariableInitializator;
    }
    
    public Nodo Tipo_de_dato(Nodo DataType){
        
        DataType = DataType.Siguiente;
        
        while(DataType != null && DataType.Siguiente.Token != 124 && DataType.Siguiente.Token != 117){ // Tokens del := y del ;
            
            switch(DataType.Token){
                case 218: // Token de la palabra reservada 'int'
                    
                    return DataType;
                
                case 211: // Token de la palabra reservada 'float'
                    
                    return DataType;
                    
                case 228: // Token de la palabra reservada 'string'
                    
                    return DataType;
                    
                 case 200: // Token de la palabra reservada 'bool'
                    
                    return DataType;
                    
                default:
                    
                    DataType = null;
                    System.out.println("Se espera una asignación de tipo en la línea " + DataType.Linea);
                    errorEncontrado = true;
                    System.exit(0);
            }
        }
        
        return DataType;
    }
    
    public Nodo Entrada_de_datos(Nodo Entrada_Datos) {
        while(Entrada_Datos.Siguiente.Token != 126){ // Token de cierre de llaves
            
            Entrada_Datos = Entrada_Datos.Siguiente;
            
            if(Entrada_Datos.Token == 121){ // Token para el inicio de paréntesis
                
                Entrada_Datos = Entrada_Datos.Siguiente;
                
                if(Entrada_Datos.Token == 100){ // Token de los identificadores y los strings
                    
                    Entrada_Datos = Entrada_Datos.Siguiente;
                    
                    if(Entrada_Datos.Token == 122){ // Token para el cierre de paréntesis
                    
                        Entrada_Datos = Entrada_Datos.Siguiente;
                        
                        if(Entrada_Datos.Token  == 117){ // Token del ;

                            Entrada_Datos = Entrada_Datos.Siguiente;
                            break;

                        } else {

                            System.out.println("Se espera un ; en la línea " + Entrada_Datos.Linea);
                            errorEncontrado = true;
                            System.exit(0);
                        }
                    } else {

                        System.out.println("Se espera un cierre de paréntesis en la línea " + Entrada_Datos.Linea);
                        errorEncontrado = true;
                        System.exit(0);
                    }
                } else {

                    System.out.println("Se espera un identificador en la línea " + Entrada_Datos.Linea);
                    errorEncontrado = true;
                    System.exit(0);
                }
            } else {
                                
                System.out.println("Se espera un inicio de paréntesis en la línea " + Entrada_Datos.Linea);
                errorEncontrado = true;
                System.exit(0);
            }
        }
        
        return Entrada_Datos;
    }
    
    public Nodo Salida_de_datos(Nodo Salida_Datos) {
        while(Salida_Datos.Siguiente.Token != 126){ // Token de cierre de llaves
                        
            Salida_Datos = Salida_Datos.Siguiente;
            
            if(Salida_Datos.Token == 121){ // Topen de inicio de paréntesis
                                
                if(Salida_Datos.Siguiente.Token == 100 || Salida_Datos.Siguiente.Token == 123){ // Token de los identificadores y los strings
                                                
                    Salida_Datos = Valores_de_Salida(Salida_Datos);
                    Salida_Datos = Salida_Datos.Siguiente;

                } else {

                    Salida_Datos = Salida_Datos.Siguiente;
                }
                
                if(Salida_Datos.Token  == 122){ // Token para cierre de paréntesis

                    Salida_Datos = Salida_Datos.Siguiente;
                    
                    if(Salida_Datos.Token  == 117){ // Token del ;

                        Salida_Datos = Salida_Datos.Siguiente;
                        break;

                    } else {

                        System.out.println("Se espera un ; en la línea " + Salida_Datos.Linea);
                        errorEncontrado = true;
                        System.exit(0);
                    }
                } else {

                    System.out.println("Se espera un cierre de paréntesis en la línea " + Salida_Datos.Linea);
                    errorEncontrado = true;
                    System.exit(0);
                }
            } else {
                
                System.out.println("Se espera un inicio de paréntesis en la línea " + Salida_Datos.Linea);
                errorEncontrado = true;
                System.exit(0);
            }
        }
        
        return Salida_Datos;
    }
    
    public Nodo Valores_de_Salida(Nodo OutputValues){
        
        OutputValues = OutputValues.Siguiente;
        
        while(OutputValues != null && OutputValues.Siguiente.Token != 122){ // Token del cierre de paréntesis
            
            switch(OutputValues.Token){
                case 100: // Token de los identificadores
                    
                    return OutputValues;
                
                case 101: // Token de los números enteros
                    
                    return OutputValues;
                    
                case 102: // Token de los números decimales
                    
                    return OutputValues;
                
                case 123: // Token de los strings
                    
                    return OutputValues;
                    
                case 210: // Token del valor booleano 'false'
                    
                    return OutputValues;
                
                case 231: // Token del valor booleano 'true'
                    
                    return OutputValues;
                 
                default:
                    
                    OutputValues = null;
                    System.out.println("Se espera un valor válido en la línea " + OutputValues.Linea);
                    errorEncontrado = true;
                    System.exit(0);
            }
        }
        
        return OutputValues;
    }
    
    public Nodo Declaracion_condicional(Nodo ConditionalStatement){
        while(ConditionalStatement.Siguiente.Token != 126){ // Token de la palabra reservada 'if'
            
            ConditionalStatement = ConditionalStatement.Siguiente;
            
            if(ConditionalStatement.Token == 121){ // Token de inicio de paréntesis
                                
                if(ConditionalStatement.Siguiente.Token == 116){ // Token de !
                    
                    ConditionalStatement = ConditionalStatement.Siguiente;
                    
                }
                
                ConditionalStatement = Valores_de_Salida(ConditionalStatement);
                
                // Parte de la declaración condicional sin operador condicional
                if(ConditionalStatement.Siguiente.Token == 122){ // Token de cierre de paréntesis
                    
                    ConditionalStatement = ConditionalStatement.Siguiente; 
                    
                    if(ConditionalStatement.Siguiente.Token == 125){ // Token de inicio de llaves

                        ConditionalStatement = ConditionalStatement.Siguiente;
                        ConditionalStatement = Contenido_de_las_Funciones(ConditionalStatement);

                        if(ConditionalStatement.Token == 126){ // Token de cierre de llaves

                            ConditionalStatement = ConditionalStatement.Siguiente;

                            if(ConditionalStatement.Token == 208){ // Token de la palabra reservada 'else'

                                ConditionalStatement = ConditionalStatement.Siguiente;

                                if(ConditionalStatement.Token == 125){ // Token de inicio de llaves

                                    ConditionalStatement = Contenido_de_las_Funciones(ConditionalStatement);

                                    if(ConditionalStatement.Token == 126){ // Token de cierre de llaves

                                        ConditionalStatement = ConditionalStatement.Siguiente;
                                        break;

                                    } else {

                                        System.out.println("Se espera un cierre de llaves en la línea " + ConditionalStatement.Linea);
                                        errorEncontrado = true;
                                        System.exit(0);
                                    }
                                } else {

                                    System.out.println("Se espera un inicio de llaves en la línea " + ConditionalStatement.Linea);
                                    errorEncontrado = true;
                                    System.exit(0);
                                }
                            } else {

                                break;
                            }
                        } else {

                            System.out.println("Se espera un cierre de llaves en la línea " + ConditionalStatement.Linea);
                            errorEncontrado = true;
                            System.exit(0);
                        }
                    } else {

                        System.out.println("Se espera un inicio de llaves en la línea " + ConditionalStatement.Linea);
                        errorEncontrado = true;
                        System.exit(0);
                    }
                    
                // Parte de la declaración condicional con operador condicional
                } else { 
                    
                    ConditionalStatement = Operadores_Condicionales_y_Logicos(ConditionalStatement);
                    ConditionalStatement = Valores_de_Salida(ConditionalStatement);
                    
                    if(ConditionalStatement.Siguiente.Token == 122){ // Token de cierre de paréntesis
                    
                        ConditionalStatement = ConditionalStatement.Siguiente;

                        if(ConditionalStatement.Siguiente.Token == 125){ // Token de inicio de llaves

                            ConditionalStatement = ConditionalStatement.Siguiente;
                            ConditionalStatement = Contenido_de_las_Funciones(ConditionalStatement);

                            if(ConditionalStatement.Token == 126){ // Token de cierre de llaves
                                
                                ConditionalStatement = ConditionalStatement.Siguiente;
                                
                                if(ConditionalStatement.Token == 208){ // Token de la palabra reservada 'else'

                                    ConditionalStatement = ConditionalStatement.Siguiente;
                                    
                                    if(ConditionalStatement.Token == 125){ // Token de inicio de llaves

                                        ConditionalStatement = Contenido_de_las_Funciones(ConditionalStatement);
                                        
                                        if(ConditionalStatement.Token == 126){ // Token de cierre de llaves

                                            ConditionalStatement = ConditionalStatement.Siguiente;
                                            break;

                                        } else {

                                            System.out.println("Se espera un cierre de llaves en la línea " + ConditionalStatement.Linea);
                                            errorEncontrado = true;
                                            System.exit(0);
                                        }
                                    } else {

                                        System.out.println("Se espera un inicio de llaves en la línea " + ConditionalStatement.Linea);
                                        errorEncontrado = true;
                                        System.exit(0);
                                    }
                                } else {
                                    
                                    break;
                                }
                            } else {

                                System.out.println("Se espera un cierre de llaves en la línea " + ConditionalStatement.Linea);
                                errorEncontrado = true;
                                System.exit(0);
                            }
                        } else {

                            System.out.println("Se espera un inicio de llaves en la línea " + ConditionalStatement.Linea);
                            errorEncontrado = true;
                            System.exit(0);
                        }
                    } else {
                        
                        ConditionalStatement = Operadores_Condicionales_y_Logicos(ConditionalStatement);
                        ConditionalStatement = Valores_de_Salida(ConditionalStatement);
                        
                        if(ConditionalStatement.Siguiente.Token == 122){ // Token de cierre de paréntesis
                    
                            ConditionalStatement = ConditionalStatement.Siguiente;

                            if(ConditionalStatement.Siguiente.Token == 125){ // Token de inicio de llaves

                                ConditionalStatement = ConditionalStatement.Siguiente;
                                ConditionalStatement = Contenido_de_las_Funciones(ConditionalStatement);

                                if(ConditionalStatement.Token == 126){ // Token de cierre de llaves

                                    ConditionalStatement = ConditionalStatement.Siguiente;

                                    if(ConditionalStatement.Token == 208){ // Token de la palabra reservada 'else'

                                        ConditionalStatement = ConditionalStatement.Siguiente;

                                        if(ConditionalStatement.Token == 125){ // Token de inicio de llaves

                                            ConditionalStatement = Contenido_de_las_Funciones(ConditionalStatement);

                                            if(ConditionalStatement.Token == 126){ // Token de cierre de llaves

                                                ConditionalStatement = ConditionalStatement.Siguiente;
                                                break;

                                            } else {

                                                System.out.println("Se espera un cierre de llaves en la línea " + ConditionalStatement.Linea);
                                                errorEncontrado = true;
                                                System.exit(0);
                                            }
                                        } else {

                                            System.out.println("Se espera un inicio de llaves en la línea " + ConditionalStatement.Linea);
                                            errorEncontrado = true;
                                            System.exit(0);
                                        }
                                    } else {

                                        break;
                                    }
                                } else {

                                    System.out.println("Se espera un cierre de llaves en la línea " + ConditionalStatement.Linea);
                                    errorEncontrado = true;
                                    System.exit(0);
                                }
                            } else {

                                System.out.println("Se espera un inicio de llaves en la línea " + ConditionalStatement.Linea);
                                errorEncontrado = true;
                                System.exit(0);
                            }
                        } else {
                            
                            ConditionalStatement = Operadores_Condicionales_y_Logicos(ConditionalStatement);
                            ConditionalStatement = Valores_de_Salida(ConditionalStatement);

                            if(ConditionalStatement.Siguiente.Token == 122){ // Token de cierre de paréntesis
                    
                                ConditionalStatement = ConditionalStatement.Siguiente;

                                if(ConditionalStatement.Siguiente.Token == 125){ // Token de inicio de llaves

                                    ConditionalStatement = ConditionalStatement.Siguiente;
                                    ConditionalStatement = Contenido_de_las_Funciones(ConditionalStatement);

                                    if(ConditionalStatement.Token == 126){ // Token de cierre de llaves

                                        ConditionalStatement = ConditionalStatement.Siguiente;

                                        if(ConditionalStatement.Token == 208){ // Token de la palabra reservada 'else'

                                            ConditionalStatement = ConditionalStatement.Siguiente;

                                            if(ConditionalStatement.Token == 125){ // Token de inicio de llaves

                                                ConditionalStatement = Contenido_de_las_Funciones(ConditionalStatement);

                                                if(ConditionalStatement.Token == 126){ // Token de cierre de llaves

                                                    ConditionalStatement = ConditionalStatement.Siguiente;
                                                    break;

                                                } else {

                                                    System.out.println("Se espera un cierre de llaves en la línea " + ConditionalStatement.Linea);
                                                    errorEncontrado = true;
                                                    System.exit(0);
                                                }
                                            } else {

                                                System.out.println("Se espera un inicio de llaves en la línea " + ConditionalStatement.Linea);
                                                errorEncontrado = true;
                                                System.exit(0);
                                            }
                                        } else {

                                            break;
                                        }
                                    } else {

                                        System.out.println("Se espera un cierre de llaves en la línea " + ConditionalStatement.Linea);
                                        errorEncontrado = true;
                                        System.exit(0);
                                    }
                                } else {

                                    System.out.println("Se espera un inicio de llaves en la línea " + ConditionalStatement.Linea);
                                    errorEncontrado = true;
                                    System.exit(0);
                                }
                            } else {

                                System.out.println("Se espera un cierre de paréntesis en la línea " + ConditionalStatement.Linea);
                                errorEncontrado = true;
                                System.exit(0);
                            }
                        }
                    }
                }
            } else {
                
                System.out.println("Se espera un inicio de paréntesis en la línea " + ConditionalStatement.Linea);
                errorEncontrado = true;
                System.exit(0);
            }
        }        
        
        return ConditionalStatement;
    }
    
    public Nodo Operadores_Condicionales_y_Logicos(Nodo ConditionalAndLogicalOps){
        
        ConditionalAndLogicalOps = ConditionalAndLogicalOps.Siguiente;
        
        while(ConditionalAndLogicalOps != null && ConditionalAndLogicalOps.Siguiente.Token != 122){ // Token del cierre de paréntesis
            
            switch(ConditionalAndLogicalOps.Token){
                case 108: // Token de <
                    
                    return ConditionalAndLogicalOps;
                
                case 109: // Token de >
                    
                    return ConditionalAndLogicalOps;
                    
                case 110: // Token de <=
                    
                    return ConditionalAndLogicalOps;
                
                case 111: // Token de >=
                    
                    return ConditionalAndLogicalOps;
                    
                case 112: // Token de ==
                    
                    return ConditionalAndLogicalOps;
                
                case 113: // Token de !=
                    
                    return ConditionalAndLogicalOps;
                 
                case 114: // Token de &&
                    
                    return ConditionalAndLogicalOps;
                
                case 115: // Token de ||
                    
                    return ConditionalAndLogicalOps;
                default:
                    
                    ConditionalAndLogicalOps = null;
                    System.out.println("Se espera un operador condicional o lógico válido en la línea " + ConditionalAndLogicalOps.Linea);
                    errorEncontrado = true;
                    System.exit(0);
            }
        }
        
        return ConditionalAndLogicalOps;
    }
    
    public Nodo Declaracion_de_Bucle(Nodo LoopStatement){
        while(LoopStatement.Siguiente.Token != 126){ // Token de cierre de paréntesis
            
            LoopStatement = LoopStatement.Siguiente;
            
            if(LoopStatement.Token == 121){ // Token de inicio de paréntesis
                
                LoopStatement = LoopStatement.Siguiente;
                
                if(LoopStatement.Token == 100){ // Token del identificador
                
                    LoopStatement = LoopStatement.Siguiente;
                    
                    if(LoopStatement.Token == 124){ // Token del :=
                        
                        LoopStatement = LoopStatement.Siguiente;
                        
                        if(LoopStatement.Token == 101){ // Token del número entero
                        
                            LoopStatement = LoopStatement.Siguiente;
                            
                            if(LoopStatement.Token == 117){ // Token del ;
                        
                                LoopStatement = LoopStatement.Siguiente;
                                
                                if(LoopStatement.Token == 100){ // Token del identificador
                        
                                    LoopStatement = Operadores_Condicionales_y_Logicos(LoopStatement);
                                    LoopStatement = LoopStatement.Siguiente;
                                    
                                    if(LoopStatement.Token == 101){ // Token del identificador
                        
                                        LoopStatement = LoopStatement.Siguiente;
                                        
                                        if(LoopStatement.Token == 117){ // Token del ;
                        
                                            LoopStatement = LoopStatement.Siguiente;
                                            
                                            if(LoopStatement.Token == 100){ // Token del identificador
                        
                                                LoopStatement = LoopStatement.Siguiente;
                                                
                                                if(LoopStatement.Token == 124){ // Token del :=
                        
                                                    LoopStatement = LoopStatement.Siguiente;
                                                    
                                                    if(LoopStatement.Token == 100){ // Token del :=
                        
                                                        LoopStatement = Operadores_Aritmeticos(LoopStatement);
                                                        LoopStatement = LoopStatement.Siguiente;
                                                        
                                                        if(LoopStatement.Token == 101){ // Token del :=
                        
                                                            LoopStatement = LoopStatement.Siguiente;
                                                            
                                                            if(LoopStatement.Token == 122){ // Token de cierre de paréntesis
                        
                                                                LoopStatement = LoopStatement.Siguiente;
                                                                
                                                                if(LoopStatement.Token == 125){ // Token de inicio de llaves
                        
                                                                    LoopStatement = Contenido_de_las_Funciones(LoopStatement);
                                                                    
                                                                    if(LoopStatement.Token == 126){ // Token de cierre de llaves
                        
                                                                        LoopStatement = LoopStatement.Siguiente;
                                                                        break;

                                                                    } else {

                                                                        System.out.println("Se espera un inicio de llaves en la línea " + LoopStatement.Linea);
                                                                        errorEncontrado = true;
                                                                        System.exit(0);
                                                                    }
                                                                } else {

                                                                    System.out.println("Se espera un inicio de llaves en la línea " + LoopStatement.Linea);
                                                                    errorEncontrado = true;
                                                                    System.exit(0);
                                                                }
                                                            } else {

                                                                System.out.println("Se espera un cierre de paréntesis en la línea " + LoopStatement.Linea);
                                                                errorEncontrado = true;
                                                                System.exit(0);
                                                            }
                                                        } else {

                                                            System.out.println("Se espera un número entero en la línea " + LoopStatement.Linea);
                                                            errorEncontrado = true;
                                                            System.exit(0);
                                                        }
                                                    } else {

                                                        System.out.println("Se espera un identificador en la línea " + LoopStatement.Linea);
                                                        errorEncontrado = true;
                                                        System.exit(0);
                                                    }
                                                } else {

                                                    System.out.println("Se espera un := en la línea " + LoopStatement.Linea);
                                                    errorEncontrado = true;
                                                    System.exit(0);
                                                }
                                            } else {

                                                System.out.println("Se espera un identificador en la línea " + LoopStatement.Linea);
                                                errorEncontrado = true;
                                                System.exit(0);
                                            }
                                        } else {

                                            System.out.println("Se espera un ; en la línea " + LoopStatement.Linea);
                                            errorEncontrado = true;
                                            System.exit(0);
                                        }
                                    } else {

                                        System.out.println("Se espera un número entero en la línea " + LoopStatement.Linea);
                                        errorEncontrado = true;
                                        System.exit(0);
                                    }
                                } else{

                                    System.out.println("Se espera un identificador en la línea " + LoopStatement.Linea);
                                    errorEncontrado = true;
                                    System.exit(0);
                                }
                            } else {

                                System.out.println("Se espera un ; en la línea " + LoopStatement.Linea);
                                errorEncontrado = true;
                                System.exit(0);
                            }
                        } else {

                            System.out.println("Se espera un número enteroen la línea " + LoopStatement.Linea);
                            errorEncontrado = true;
                            System.exit(0);
                        }
                    } else {
                        
                        System.out.println("Se espera un :=en la línea " + LoopStatement.Linea);
                        errorEncontrado = true;
                        System.exit(0);
                    }
                } else {

                    System.out.println("Se espera un identificadoren la línea " + LoopStatement.Linea);
                    errorEncontrado = true;
                    System.exit(0);
                }
            } else {
                
                System.out.println("Se espera un inicio de paréntesisen la línea " + LoopStatement.Linea);
                errorEncontrado = true;
                System.exit(0);
            }
        }
        
        return LoopStatement;
    }
    
    public Nodo Operadores_Aritmeticos(Nodo ArithmeticOps){
        
        ArithmeticOps = ArithmeticOps.Siguiente;
        
        while(ArithmeticOps != null && ArithmeticOps.Siguiente.Token != 122){ // Token del cierre de paréntesis
            
            switch(ArithmeticOps.Token){
                case 103: // Token de suma
                    
                    return ArithmeticOps;
                
                case 104: // Token de resta
                    
                    return ArithmeticOps;
                    
                case 105: // Token de multiplicación
                    
                    return ArithmeticOps;
                
                case 106: // Token de división
                    
                    return ArithmeticOps;
                    
                case 107: // Token de exponenciación
                    
                    return ArithmeticOps;
                    
                default:
                    
                    ArithmeticOps = null;
                    System.out.println("Se espera un operador aritmético válido en la línea " + ArithmeticOps.Linea);
                    errorEncontrado = true;
                    System.exit(0);
            }
        }
        
        return ArithmeticOps;
    }
    
    public Nodo Arithmetic_Operations(Nodo OperacionesAritmeticas){
        while(OperacionesAritmeticas.Siguiente.Token != 117){ // Token del ;
            
            if(OperacionesAritmeticas.Siguiente.Token != 210 && OperacionesAritmeticas.Siguiente.Token != 231){ // Tokens de las palabras reservadas 'false' y 'true'
                    
                OperacionesAritmeticas = Valores_de_Salida(OperacionesAritmeticas);
                
                if(OperacionesAritmeticas.Siguiente.Token == 125){ // Token de inicio de llaves
                    
                    OperacionesAritmeticas = OperacionesAritmeticas.Siguiente;
                    OperacionesAritmeticas = Invocar_Estructuras(OperacionesAritmeticas);
                    break;
                    
                } else {
                    
                    OperacionesAritmeticas = Operadores_Aritmeticos(OperacionesAritmeticas);

                    if(OperacionesAritmeticas.Siguiente.Token != 210 && OperacionesAritmeticas.Siguiente.Token != 231){ // Tokens de las palabras reservadas 'false' y 'true'

                        OperacionesAritmeticas = Valores_de_Salida(OperacionesAritmeticas);
                        OperacionesAritmeticas = OperacionesAritmeticas.Siguiente;

                        if(OperacionesAritmeticas.Token == 117){ // Token del ;

                            OperacionesAritmeticas = OperacionesAritmeticas.Siguiente;
                            break;

                        } else {

                            System.out.println("Se espera un ; en la línea " + OperacionesAritmeticas.Linea);
                            errorEncontrado = true;
                            System.exit(0);
                        }
                    } else {

                        System.out.println("No se permiten valores booleanos en la línea " + OperacionesAritmeticas.Linea);
                        errorEncontrado = true;
                        System.exit(0);
                    }                    
                }
            } else {

                System.out.println("No se permiten valores booleanos en la línea " + OperacionesAritmeticas.Linea);
                errorEncontrado = true;
                System.exit(0);
            }
        }
        
        return OperacionesAritmeticas;
    }
    
    public Nodo Declaracion_de_Funciones_y_Estructuras(Nodo FuncAndStructDeclarations){
        
        FuncAndStructDeclarations = FuncAndStructDeclarations.Siguiente;
        
        while(FuncAndStructDeclarations != null && FuncAndStructDeclarations.Siguiente != null && FuncAndStructDeclarations.Siguiente.Token != 126){
            
            switch(FuncAndStructDeclarations.Token){
                case 213: // Token de la palabra reservada 'func'
                    
                    FuncAndStructDeclarations = Declaracion_de_Funciones(FuncAndStructDeclarations);
                    break;
                
                case 232: // Token de la palabra reservada 'type'
                    
                    FuncAndStructDeclarations = Declaracion_de_Estructuras(FuncAndStructDeclarations);
                    break;
                    
                default: 
                    
                    FuncAndStructDeclarations = null;
                    System.out.println("Se espera una declaración de función o de estructura en la línea " + FuncAndStructDeclarations.Linea);
                    errorEncontrado = true;
                    System.exit(0);
            }
        }
        
        return FuncAndStructDeclarations;
    }
    
    public Nodo Declaracion_de_Funciones(Nodo FuncDeclaration){
        while(FuncDeclaration.Siguiente != null){ // Token de cierre de llaves
            
            FuncDeclaration = FuncDeclaration.Siguiente;
            
            if(FuncDeclaration.Token == 100){ // Token del identificador
                
                FuncDeclaration = FuncDeclaration.Siguiente;
                
                if(FuncDeclaration.Token == 121){ // Token de inicio de paréntesis

                    FuncDeclaration = FuncDeclaration.Siguiente;
                    
                    if(FuncDeclaration.Token == 122){ // Token de cierre de paréntesis

                        FuncDeclaration = FuncDeclaration.Siguiente;
                        
                        if(FuncDeclaration.Token == 125){ // Token de inicio de llaves
                            
                            FuncDeclaration = Contenido_de_las_Funciones(FuncDeclaration);
                            
                            if(FuncDeclaration.Token  == 126){ // Token de cierre de llaves
                                                
                                if(FuncDeclaration.Siguiente != null){ // Tokens de la palabra reservada 'func' y 'type'

                                    FuncDeclaration = Declaracion_de_Funciones_y_Estructuras(FuncDeclaration);
                                    break;

                                } else {

                                    System.out.println("\nAnálisis sintáctico terminado con éxito.");
                                    break;
                                }                                                                                              
                            } else{

                                System.out.println("Se espera un cierre de llaves en la línea " + FuncDeclaration.Linea);
                                errorEncontrado = true;
                                System.exit(0);
                            }
                        } else {

                            System.out.println("Se espera un inicio de llaves en la línea " + FuncDeclaration.Linea);
                            errorEncontrado = true;
                            System.exit(0);
                        }
                    } 
                    
                    if(FuncDeclaration.Token == 100){ // Token del identificador
                        
                        FuncDeclaration = Tipo_de_dato(FuncDeclaration);
                        FuncDeclaration = FuncDeclaration.Siguiente;
                        
                        if(FuncDeclaration.Token == 118){ // Token de ,
                            
                            FuncDeclaration = FuncDeclaration.Siguiente;
                            
                            if(FuncDeclaration.Token == 100){ // Token del identificador
                                
                                FuncDeclaration = Tipo_de_dato(FuncDeclaration);
                                FuncDeclaration = FuncDeclaration.Siguiente;
                                
                                if(FuncDeclaration.Token == 118){ // Token de ,
                                    
                                    FuncDeclaration = FuncDeclaration.Siguiente;
                                    
                                    if(FuncDeclaration.Token == 100){ // Token del identificador
                                        
                                        FuncDeclaration = Tipo_de_dato(FuncDeclaration);
                                        FuncDeclaration = FuncDeclaration.Siguiente;
                                        
                                        if(FuncDeclaration.Token == 122){ // Token de cierre de paréntesis

                                            FuncDeclaration = FuncDeclaration.Siguiente;

                                            if(FuncDeclaration.Token == 125){ // Token de inicio de llaves
                                                
                                                FuncDeclaration = Contenido_de_las_Funciones(FuncDeclaration);

                                                if(FuncDeclaration.Token  == 126){ // Token de cierre de llaves

                                                    if(FuncDeclaration.Siguiente != null){ // Tokens de la palabra reservada 'func' y 'type'

                                                        FuncDeclaration = Declaracion_de_Funciones_y_Estructuras(FuncDeclaration);
                                                        break;

                                                    } else {

                                                        System.out.println("\nAnálisis sintáctico terminado con éxito.");
                                                        break;
                                                    }                                                                                              
                                                } else{

                                                    System.out.println("Se espera un cierre de llaves en la línea " + FuncDeclaration.Linea);
                                                    errorEncontrado = true;
                                                    System.exit(0);
                                                }
                                            } else {

                                                System.out.println("Se espera un inicio de llaves en la línea " + FuncDeclaration.Linea);
                                                errorEncontrado = true;
                                                System.exit(0);
                                            }
                                        } else {

                                            System.out.println("Se espera un cierre de paréntesis en la línea " + FuncDeclaration.Linea);
                                            errorEncontrado = true;
                                            System.exit(0);
                                        }
                                    } else {
                                        
                                        System.out.println("Se espera un identificador en la línea " + FuncDeclaration.Linea);
                                        errorEncontrado = true;
                                        System.exit(0);
                                    }
                                }
                                
                                if(FuncDeclaration.Token == 122){ // Token de cierre de paréntesis

                                    FuncDeclaration = FuncDeclaration.Siguiente;

                                    if(FuncDeclaration.Token == 125){ // Token de inicio de llaves
                                        
                                        FuncDeclaration = Contenido_de_las_Funciones(FuncDeclaration);

                                        if(FuncDeclaration.Token  == 126){ // Token de cierre de llaves

                                            if(FuncDeclaration.Siguiente != null){ // Tokens de la palabra reservada 'func' y 'type'

                                                FuncDeclaration = Declaracion_de_Funciones_y_Estructuras(FuncDeclaration);
                                                break;

                                            } else {

                                                System.out.println("\nAnálisis sintáctico terminado con éxito.");
                                                break;
                                            }                                                                                              
                                        } else{

                                            System.out.println("Se espera un cierre de llaves en la línea " + FuncDeclaration.Linea);
                                            errorEncontrado = true;
                                            System.exit(0);
                                        }
                                    } else {

                                        System.out.println("Se espera un inicio de llaves en la línea " + FuncDeclaration.Linea);
                                        errorEncontrado = true;
                                        System.exit(0);
                                    }
                                } else {

                                    System.out.println("Se espera un cierre de paréntesis en la línea " + FuncDeclaration.Linea);
                                    errorEncontrado = true;
                                    System.exit(0);
                                }
                            } else {
                                
                                System.out.println("Se espera un identificador en la línea " + FuncDeclaration.Linea);
                                errorEncontrado = true;
                                System.exit(0);
                            }
                        } 
                        
                        if(FuncDeclaration.Token == 122){ // Token de cierre de paréntesis
                            
                            FuncDeclaration = FuncDeclaration.Siguiente;

                            if(FuncDeclaration.Token == 125){ // Token de inicio de llaves
                                
                                FuncDeclaration = Contenido_de_las_Funciones(FuncDeclaration);

                                if(FuncDeclaration.Token  == 126){ // Token de cierre de llaves

                                    if(FuncDeclaration.Siguiente != null){ // Tokens de la palabra reservada 'func' y 'type'

                                        FuncDeclaration = Declaracion_de_Funciones_y_Estructuras(FuncDeclaration);
                                        break;

                                    } else {

                                        System.out.println("\nAnálisis sintáctico terminado con éxito.");
                                        break;
                                    }                                                                                              
                                } else{

                                    System.out.println("Se espera un cierre de llaves en la línea " + FuncDeclaration.Linea);
                                    errorEncontrado = true;
                                    System.exit(0);
                                }
                            } else {

                                System.out.println("Se espera un inicio de llaves en la línea " + FuncDeclaration.Linea);
                                errorEncontrado = true;
                                System.exit(0);
                            }
                        } else {

                            System.out.println("Se espera un cierre de paréntesis en la línea " + FuncDeclaration.Linea);
                            errorEncontrado = true;
                            System.exit(0);
                        }
                    } else {

                        System.out.println("Se espera un identificador o un cierre de paréntesis en la línea " + FuncDeclaration.Linea);
                        errorEncontrado = true;
                        System.exit(0);
                    }
                } else {

                    System.out.println("Se espera un inicio de paréntesis en la línea " + FuncDeclaration.Linea);
                    errorEncontrado = true;
                    System.exit(0);
                }
            } else {
                
                System.out.println("Se espera un identificador en la línea " + FuncDeclaration.Linea);
                errorEncontrado = true;
                System.exit(0);
            }
        }
        
        return FuncDeclaration;
    }
    
    public Nodo Declaracion_de_Estructuras(Nodo StructDeclaration){
        while(StructDeclaration.Siguiente != null){ // Token de cierre de llaves
            
            StructDeclaration = StructDeclaration.Siguiente;
            
            if(StructDeclaration.Token  == 100){ // Token del identificador
                
                StructDeclaration = StructDeclaration.Siguiente;
                
                if (StructDeclaration.Token  == 229){ // Token de la palabra reservada 'struct'
                    
                    StructDeclaration = StructDeclaration.Siguiente;
                    
                    if (StructDeclaration.Token  == 125){ // Token de inicio de llaves
                    
                        StructDeclaration = StructDeclaration.Siguiente;
                        
                        if(StructDeclaration.Token  == 100){ // Token del identificador
                            
                            StructDeclaration = Tipo_de_dato(StructDeclaration);
                            StructDeclaration = StructDeclaration.Siguiente;
                            
                            if(StructDeclaration.Token  == 117){ // Token del ;
                                
                                StructDeclaration = StructDeclaration.Siguiente;
                                
                                if(StructDeclaration.Token == 100){ // Token del identificador
                                    
                                    StructDeclaration = Tipo_de_dato(StructDeclaration);
                                    StructDeclaration = StructDeclaration.Siguiente;
                                    
                                    if(StructDeclaration.Token  == 117){ // Token del ;
                                        
                                        StructDeclaration = StructDeclaration.Siguiente;
                                        
                                        if(StructDeclaration.Token == 100){ // Token del identificador
                                            
                                            StructDeclaration = Tipo_de_dato(StructDeclaration);
                                            StructDeclaration = StructDeclaration.Siguiente;
                                            
                                            if(StructDeclaration.Token  == 117){ // Token del ;
                                                
                                                StructDeclaration = StructDeclaration.Siguiente;
                                                
                                                if(StructDeclaration.Token == 100){ // Token del identificador
                                                    
                                                    StructDeclaration = Tipo_de_dato(StructDeclaration);
                                                    StructDeclaration = StructDeclaration.Siguiente;
                                                    
                                                    if(StructDeclaration.Token  == 117){ // Token del ;
                                                        
                                                        StructDeclaration = StructDeclaration.Siguiente;
                                                        
                                                        if(StructDeclaration.Token  == 126){ // Token de cierre de llaves

                                                            if(StructDeclaration.Siguiente != null){ // Tokens de la palabra reservada 'func' y 'type'

                                                                StructDeclaration = Declaracion_de_Funciones_y_Estructuras(StructDeclaration);
                                                                break;

                                                            } else {

                                                                System.out.println("\nAnálisis sintáctico terminado con éxito.");
                                                                break;
                                                            }                                                                                              
                                                        } else{

                                                            System.out.println("Se espera un cierre de llaves o un identificador en la línea " + StructDeclaration.Linea);
                                                            errorEncontrado = true;
                                                            System.exit(0);
                                                        }
                                                    } else {
                                                        
                                                        System.out.println("Se espera un ; en la línea " + StructDeclaration.Linea);
                                                        errorEncontrado = true;
                                                        System.exit(0);
                                                    }
                                                } 
                                                
                                                if(StructDeclaration.Token  == 126){ // Token de cierre de llaves

                                                    if(StructDeclaration.Siguiente != null){ // Tokens de la palabra reservada 'func' y 'type'

                                                        StructDeclaration = Declaracion_de_Funciones_y_Estructuras(StructDeclaration);
                                                        break;

                                                    } else {

                                                        System.out.println("\nAnálisis sintáctico terminado con éxito.");
                                                        break;
                                                    }                                                                                              
                                                } else{

                                                    System.out.println("Se espera un cierre de llaves o un identificador en la línea " + StructDeclaration.Linea);
                                                    errorEncontrado = true;
                                                    System.exit(0);
                                                }
                                            } else {
                                                
                                                System.out.println("Se espera un ; en la línea " + StructDeclaration.Linea);
                                                errorEncontrado = true;
                                                System.exit(0);
                                            }
                                        } 
                                        
                                        if(StructDeclaration.Token  == 126){ // Token de cierre de llaves

                                            if(StructDeclaration.Siguiente != null){ // Tokens de la palabra reservada 'func' y 'type'

                                                StructDeclaration = Declaracion_de_Funciones_y_Estructuras(StructDeclaration);
                                                break;

                                            } else {

                                                System.out.println("\nAnálisis sintáctico terminado con éxito.");
                                                break;
                                            }                                                                                              
                                        } else{

                                            System.out.println("Se espera un cierre de llaves o un identificador en la línea " + StructDeclaration.Linea);
                                            errorEncontrado = true;
                                            System.exit(0);
                                        }
                                    } else {
                                
                                        System.out.println("Se espera un ; en la línea " + StructDeclaration.Linea);
                                        errorEncontrado = true;
                                        System.exit(0);
                                    }
                                }
                                
                                if(StructDeclaration.Token  == 126){ // Token de cierre de llaves

                                    if(StructDeclaration.Siguiente != null){ // Tokens de la palabra reservada 'func' y 'type'

                                        StructDeclaration = Declaracion_de_Funciones_y_Estructuras(StructDeclaration);
                                        break;

                                    } else {

                                        System.out.println("\nAnálisis sintáctico terminado con éxito.");
                                        break;
                                    }                                                                                              
                                } else{

                                    System.out.println("Se espera un cierre de llaves o un identificador en la línea " + StructDeclaration.Linea);
                                    errorEncontrado = true;
                                    System.exit(0);
                                }
                            } else {
                                
                                System.out.println("Se espera un ; en la línea " + StructDeclaration.Linea);
                                errorEncontrado = true;
                                System.exit(0);
                            }
                        } 
                        
                        if(StructDeclaration.Token  == 126){ // Token de cierre de llaves

                            if(StructDeclaration.Siguiente != null){ // Tokens de la palabra reservada 'func' y 'type'

                                StructDeclaration = Declaracion_de_Funciones_y_Estructuras(StructDeclaration);
                                break;

                            } else {

                                System.out.println("\nAnálisis sintáctico terminado con éxito.");
                                break;
                            }                                                                                              
                        } else{

                            System.out.println("Se espera un cierre de llaves o un identificador en la línea " + StructDeclaration.Linea);
                            errorEncontrado = true;
                            System.exit(0);
                        }
                    } else {

                        System.out.println("Se espera un inicio de llaves en la línea " + StructDeclaration.Linea);
                        errorEncontrado = true;
                        System.exit(0);
                    }
                } else {
                    
                    System.out.println("Se espera la palabra 'struct' en la línea " + StructDeclaration.Linea);
                    errorEncontrado = true;
                    System.exit(0);
                }
            } else {
                
                System.out.println("Se espera un identificador en la línea " + StructDeclaration.Linea);
                errorEncontrado = true;
                System.exit(0);
            }
        }
        
        return StructDeclaration;
    }
    
    public Nodo Uso_de_Identificadores(Nodo IdUses){
        
        IdUses = IdUses.Siguiente;
        
        while(IdUses != null && IdUses.Siguiente != null && IdUses.Siguiente.Token != 126){
            
            if(IdUses.Token == 124){ // Token del :=
                
                IdUses = Arithmetic_Operations(IdUses);
                break;
                
            } 
            
            if(IdUses.Token == 121){ // Token de inicio de paréntesis
                
                IdUses = Invocar_Funciones(IdUses);
                break;
                
            }
            
            if(IdUses.Token == 120){ // Token de .
                
                IdUses = IdUses.Siguiente;
                
                if(IdUses.Token == 223){ // Token de la palabra reservada 'println'
                
                    IdUses = Salida_de_datos(IdUses);
                    break;

                } 
                
                if(IdUses.Token == 226){
                
                    IdUses = Entrada_de_datos(IdUses);
                    break;

                } else {

                    System.out.println("Se espera las palabras 'print' o 'scan' en la línea " + IdUses.Linea);
                    errorEncontrado = true;
                    System.exit(0);
                }
                
            } else {
                
                System.out.println("Se espera un :=, un inicio de paréntesis o un . en la línea " + IdUses.Linea);
                errorEncontrado = true;
                System.exit(0);
            }
        }
        
        return IdUses;
    }
    
    public Nodo Invocar_Estructuras(Nodo InvokeStructs){
        while(InvokeStructs.Siguiente.Token != 126){ // Token de cierre de llaves
            
            if(InvokeStructs.Siguiente.Token != 126){ // Token de cierre de llaves
                
                InvokeStructs = Valores_de_Salida(InvokeStructs);
                InvokeStructs = InvokeStructs.Siguiente;
                
                if(InvokeStructs.Token == 118){ // Token de ,
                    
                    InvokeStructs = Valores_de_Salida(InvokeStructs);
                    InvokeStructs = InvokeStructs.Siguiente;
                    
                    if(InvokeStructs.Token == 118){ // Token de ,
                    
                        InvokeStructs = Valores_de_Salida(InvokeStructs);
                        InvokeStructs = InvokeStructs.Siguiente;

                        if(InvokeStructs.Token == 118){ // Token de ,
                    
                            InvokeStructs = Valores_de_Salida(InvokeStructs);
                            InvokeStructs = InvokeStructs.Siguiente;

                            if(InvokeStructs.Token == 126){ // Token de cierre de llaves

                                InvokeStructs = InvokeStructs.Siguiente;
                                break;
                                
                            } else {

                                System.out.println("Se espera un cierre de llaves en la línea " + InvokeStructs.Linea);
                                errorEncontrado = true;
                                System.exit(0);
                            }
                        }
                        
                        if(InvokeStructs.Token == 126){ // Token de cierre de llaves

                            InvokeStructs = InvokeStructs.Siguiente;
                            break;
                            
                        } else {

                            System.out.println("Se espera una , o un cierre de paréntesis en la línea " + InvokeStructs.Linea);
                            errorEncontrado = true;
                            System.exit(0);
                        }
                    }
                    
                    if(InvokeStructs.Token == 126){ // Token de cierre de llaves
                    
                        InvokeStructs = InvokeStructs.Siguiente;
                        break;

                    } else {

                        System.out.println("Se espera una , o un cierre de paréntesis en la línea " + InvokeStructs.Linea);
                        errorEncontrado = true;
                        System.exit(0);
                    }
                }
                
                if(InvokeStructs.Token == 126){ // Token de cierre de llaves
                    
                    InvokeStructs = InvokeStructs.Siguiente;
                    break;
                    
                } else {
                    
                    System.out.println("Se espera una , o un cierre de paréntesis en la línea " + InvokeStructs.Linea);
                    errorEncontrado = true;
                    System.exit(0);
                }
            } else {
                
                InvokeStructs = InvokeStructs.Siguiente;
                
                if(InvokeStructs.Token == 126){ // Token del ;

                    InvokeStructs = InvokeStructs.Siguiente;
                    break;

                } else {

                    System.out.println("Se espera un ; en la línea " + InvokeStructs.Linea);
                    errorEncontrado = true;
                    System.exit(0);
                }
            }
        }
        
        return InvokeStructs;
    }
    
    public Nodo Invocar_Funciones(Nodo InvokeFuncs){
        while(InvokeFuncs.Siguiente.Token != 117){ // Token del ;
            
            if(InvokeFuncs.Siguiente.Token != 122){ // Token de cierre de paréntesis
                
                InvokeFuncs = Valores_de_Salida(InvokeFuncs);
                InvokeFuncs = InvokeFuncs.Siguiente;
                
                if(InvokeFuncs.Token == 118){ // Token de ,
                    
                    InvokeFuncs = Valores_de_Salida(InvokeFuncs);
                    InvokeFuncs = InvokeFuncs.Siguiente;
                    
                    if(InvokeFuncs.Token == 118){ // Token de ,
                    
                        InvokeFuncs = Valores_de_Salida(InvokeFuncs);
                        InvokeFuncs = InvokeFuncs.Siguiente;

                        if(InvokeFuncs.Token == 122){ // Token de cierre de llaves

                            InvokeFuncs = InvokeFuncs.Siguiente;

                            if(InvokeFuncs.Token == 117){ // Token del ;

                                InvokeFuncs = InvokeFuncs.Siguiente;
                                break;

                            } else {

                                System.out.println("Se espera un ; en la línea " + InvokeFuncs.Linea);
                                errorEncontrado = true;
                                System.exit(0);
                            }
                        } else {

                            System.out.println("Se espera un cierre de paréntesis en la línea " + InvokeFuncs.Linea);
                            errorEncontrado = true;
                            System.exit(0);
                        }
                    }
                    
                    if(InvokeFuncs.Token == 122){ // Token de cierre de llaves

                        InvokeFuncs = InvokeFuncs.Siguiente;

                        if(InvokeFuncs.Token == 117){ // Token del ;

                            InvokeFuncs = InvokeFuncs.Siguiente;
                            break;

                        } else {

                            System.out.println("Se espera un ; en la línea " + InvokeFuncs.Linea);
                            errorEncontrado = true;
                            System.exit(0);
                        }
                    } else {

                        System.out.println("Se espera una , o un cierre de paréntesis en la línea " + InvokeFuncs.Linea);
                        errorEncontrado = true;
                        System.exit(0);
                    }
                }
                
                if(InvokeFuncs.Token == 122){ // Token de cierre de llaves

                    InvokeFuncs = InvokeFuncs.Siguiente;
                    
                    if(InvokeFuncs.Token == 117){ // Token del ;

                        InvokeFuncs = InvokeFuncs.Siguiente;
                        break;

                    } else {

                        System.out.println("Se espera un ; en la línea " + InvokeFuncs.Linea);
                        errorEncontrado = true;
                        System.exit(0);
                    }
                } else {

                    System.out.println("Se espera una , o un cierre de paréntesis en la línea " + InvokeFuncs.Linea);
                    errorEncontrado = true;
                    System.exit(0);
                }
            } else {
                
                InvokeFuncs = InvokeFuncs.Siguiente;
                
                if(InvokeFuncs.Token == 122){ // Token de cierre de llaves

                    InvokeFuncs = InvokeFuncs.Siguiente;
                    
                    if(InvokeFuncs.Token == 117){ // Token del ;

                        InvokeFuncs = InvokeFuncs.Siguiente;
                        break;

                    } else {

                        System.out.println("Se espera un ; en la línea " + InvokeFuncs.Linea);
                        errorEncontrado = true;
                        System.exit(0);
                    }
                } else {

                    System.out.println("Se espera un cierre de paréntesis en la línea " + InvokeFuncs.Linea);
                    errorEncontrado = true;
                    System.exit(0);
                }
            }
        }
        
        return InvokeFuncs;
    }
}
/*
    public Nodo Correspondencia_de_datos(Nodo DataMatching, Nodo DataMatcher){
                
        DataMatcher = Tipo_de_dato(DataMatcher);
        
        DataMatching = DataMatching.Siguiente;
        
        System.out.println(DataMatching.Token);
        
        while(DataMatcher != null && DataMatching.Siguiente.Token != 117){ // Token del ;
            
            // Se pregunta si el token de valor es de tipo número entero cuando se escogió previamente el tipo de dato 'int'
            if(DataMatcher.Token == 218 && DataMatching.Token == 101){
                
                DataMatching = Inicializar_variables(DataMatching);
                break;
                
            // Se pregunta si el token de valor es de tipo número decimal cuando se escogió previamente el tipo de dato 'float'
            } else if(DataMatcher.Token == 211 && DataMatching.Token == 102){
                
                DataMatching = Inicializar_variables(DataMatching);
                break;
               
            // Se pregunta si el token de valor es de tipo cadena de caracteres cuando se escogió previamente el tipo de dato 'string'
            } else if(DataMatcher.Token == 227 && DataMatching.Token == 123){
                
                DataMatching = Inicializar_variables(DataMatching);
                break;
              
            // Se pregunta si el token de valor es de tipo verdadero o falso cuando se escogió previamente el tipo de dato 'bool'
            } else if(DataMatcher.Token == 200 && (DataMatching.Token == 230 || DataMatching.Token == 210)){
                
                DataMatching = Inicializar_variables(DataMatching);
                break;
                
            } else{
                
                System.out.println("Los valores ingresados no coinciden con el tipo de datos");
                errorEncontrado = true;
                break;
            }
        }
        
        return DataMatching;
    }
*/