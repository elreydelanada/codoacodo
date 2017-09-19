/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author alumno
 */
public class SQLError {
    private SQLError(){}
    
    public static String getErrorByCode(int code){
        switch(code){
            case 1045:
                return "SQL: Contraseña incorrecta.";                
            
            case 1049:
                return "SQL: Base no existe.";                
                
            default:
                return "SQL " + code +": Error desconocido.";
        }
    }
}
