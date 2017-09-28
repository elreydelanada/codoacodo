/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.Departamento;
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
public class DepartamentoDao extends Dao{
    
    public LinkedList<Departamento> getByProvinciaId(int id){
        try {            
            LinkedList<Departamento> lista = new LinkedList<Departamento>();
            Connection conn = this.getConexion();
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM departamento WHERE id_provincia = "+ id +" order by descripcion");
            
            while(rs.next()){
                Departamento departamento = new Departamento();
                departamento.setId(rs.getInt("id"));
                departamento.setDescripcion(rs.getString("descripcion"));
                lista.add(departamento);
            }
            
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(ProvinciaDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Departamento getById(int id){
        try {
            Connection conn = this.getConexion();
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM departamento WHERE id = " + id);
            rs.next();
            Departamento departamento = new Departamento();
            departamento.setId(rs.getInt("id"));
            departamento.setDescripcion(rs.getString("descripcion"));
            ProvinciaDao provDao = new ProvinciaDao();
            departamento.setProvincia(provDao.getById(rs.getInt("id_provincia")));
            departamento.setId_departamento(rs.getInt("id_departamento"));
            return departamento;
        } catch (SQLException ex) {
            Logger.getLogger(DepartamentoDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }         
    }
}
