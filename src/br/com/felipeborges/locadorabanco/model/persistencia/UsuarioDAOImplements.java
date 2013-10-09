package br.com.felipeborges.locadorabanco.model.persistencia;

import br.com.felipeborges.locadorabanco.model.Usuario;
import br.com.felipeborges.locadorabanco.model.persistencia.dao.UsuarioDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class UsuarioDAOImplements implements UsuarioDAO {

    private static final String INSERT = "insert into usuario (nome, login, senha, cpf, telefone, data_nascimento, sexo) values (?,?,?,?,?,?,?);";
    private static final String LIST = "select * from usuario;";
    
    @Override
    public int salve(Usuario u) {
        if (u.getCodigo() == 0) {
            return insert(u);
        }
        return -1;
    }

    public int insert(Usuario u) {
        Connection con = null;
        PreparedStatement pstm = null;
        int retorno = -1;
        try {
                con = ConnectionFactory.getConnection();
                pstm = con.prepareStatement(INSERT,Statement.RETURN_GENERATED_KEYS);
                pstm.setString(1, u.getNome());
                pstm.setString(2, u.getLogin());
                pstm.setString(3, u.getSenha());
                pstm.setString(4, u.getCpf());
                pstm.setString(5, u.getTelefone());
                pstm.setDate(6, new java.sql.Date(u.getDataNascimento().getTime()));
                pstm.setString(7, u.getSexo());
                pstm.execute();
                
                try(ResultSet rs = pstm.getGeneratedKeys()){
                    if(rs.next()){
                        retorno = rs.getInt(1);
                    }
                }
                
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao enserir:" + e);
        }finally{
            try{
                ConnectionFactory.closeConnection(con, pstm);
            }catch(SQLException ex){
            }
            return retorno;
    }
}

    @Override
        public boolean remove(int codigo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
        public List<Usuario> listAll() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Usuario> usuarios = new ArrayList<>();
        
        try {
            con = ConnectionFactory.getConnection();
            pstm = con.prepareStatement(LIST);
            
            rs = pstm.executeQuery();
            
            while(rs.next()){
                Usuario u = new Usuario();
                u.setCodigo(rs.getInt("codigo"));
                u.setCpf(rs.getString("cpf"));
                u.setDataNascimento(rs.getDate("data_nascimento"));
                u.setLogin(rs.getString("login"));
                u.setSenha(rs.getString("senha"));
                u.setSexo(rs.getString("sexo"));
                u.setTelefone(rs.getString("telefone"));
                usuarios.add(u);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar as pessoas: " + e.getMessage());
        } finally {
            try {
                ConnectionFactory.closeConnection(con, pstm, rs);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar conexão: " + e.getMessage());
            }
        }
        return usuarios;
    }

    @Override
        public Usuario listById(int codigo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }       
}
