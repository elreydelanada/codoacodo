/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.Provincia;
import entities.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alumno
 */
public class UsuarioDao extends Dao {
    
    public void insert(Usuario usuario){
        try {                 
            Connection conn = this.getConexion();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO usuario(dni, nombre, apellido, id_provincia, id_departamento) VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, usuario.getDni());
            ps.setString(2, usuario.getNombre());
            ps.setString(3, usuario.getApellido());
            ps.setInt(4, usuario.getProvincia().getId());
            ps.setInt(5, usuario.getDepartamento().getId());
            ps.executeUpdate();      
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete(Usuario usuario){
        try {
            Connection conn = this.getConexion();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM usuario WHERE id = ?");            
            ps.setInt(1, usuario.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public void update(Usuario usuario){
        try {
            Connection conn = this.getConexion();            
            PreparedStatement ps = conn.prepareStatement("UPDATE usuario SET dni = ?, nombre = ?, apellido = ? WHERE id = ?");
            ps.setInt(1, usuario.getDni());
            ps.setString(2, usuario.getNombre());
            ps.setString(3, usuario.getApellido());
            ps.setInt(4, usuario.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public  LinkedList<Usuario> getAll(){
        try {
            Connection conn = this.getConexion();
            String query    = "SELECT * FROM usuario";
            Statement s     = conn.createStatement();
            ResultSet rs    = s.executeQuery(query);
            return this.getListFromResultSet(rs);                        
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }            
    }    
    
    public Usuario getById(int id){
        try {
            Connection conn = this.getConexion();
            String query    = "SELECT * FROM usuario WHERE id = " + id;
            Statement s     = conn.createStatement();
            ResultSet rs    = s.executeQuery(query);
            rs.next();
            
            Usuario usuario = new Usuario();
            usuario.setId(rs.getInt("id"));
            usuario.setDni(rs.getInt("dni"));
            usuario.setNombre(rs.getString("nombre"));
            usuario.setApellido(rs.getString("apellido"));
            ProvinciaDao provDao = new ProvinciaDao();
            usuario.setProvincia(provDao.getById(rs.getInt("id_provincia")));
            DepartamentoDao deptoDao = new DepartamentoDao();
            usuario.setDepartamento(deptoDao.getById(rs.getInt("id_departamento")));
            
            return usuario;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public  LinkedList<Usuario> getByAllFields(String valorBuscado){
        try {
            Connection conn = this.getConexion();
            String query    = "SELECT * FROM usuario";
            query += " WHERE apellido LIKE '%" + valorBuscado + "%' OR nombre LIKE '%" + valorBuscado + "%'";
            query += " OR dni LIKE '%" + valorBuscado + "%'";
            Statement s     = conn.createStatement();
            ResultSet rs    = s.executeQuery(query);
            return this.getListFromResultSet(rs);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }            
    }
    
    private LinkedList<Usuario> getListFromResultSet(ResultSet rs){        
        try {
            LinkedList<Usuario> lista = new LinkedList<Usuario>();
            while(rs.next()){
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setDni(rs.getInt("dni"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                
                ProvinciaDao provDao = new ProvinciaDao();
                Provincia prov       = provDao.getById(rs.getInt("id_provincia"));
                usuario.setProvincia(prov);
                
                DepartamentoDao dptoDao = new DepartamentoDao();
                usuario.setDepartamento(dptoDao.getById(rs.getInt("id_departamento")));
                
                lista.add(usuario);
            }
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
