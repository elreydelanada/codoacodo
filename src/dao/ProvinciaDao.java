/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.Provincia;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alumno
 */
public class ProvinciaDao extends Dao{
    public LinkedList<Provincia> getAll(){
        try {
            LinkedList<Provincia> lista = new LinkedList<Provincia>();
            Connection conn = this.getConexion();
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM provincia order by descripcion");
            
            while(rs.next()){
                Provincia provincia = new Provincia();
                provincia.setId(rs.getInt("id"));
                provincia.setDescripcion(rs.getString("descripcion"));
                lista.add(provincia);
            }
            
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(ProvinciaDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
