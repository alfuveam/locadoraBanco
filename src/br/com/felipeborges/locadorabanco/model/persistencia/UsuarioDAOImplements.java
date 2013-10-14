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
    private static final String REMOVE = "delete from usuario where codigo = ?;";
    private static final String UPDATE = "update usuario set nome = ?, login = ?, senha = ?, cpf =?, telefone = ?, data_nascimento = ?, sexo = ? where codigo =?;";
    private static final String LISTBYID = "select * from usuario where codigo = ?;";
    private static final String LISTBYNOME = "select * from usuario where nome like ?;";
    
    @Override
    public int salve(Usuario u) {
        if (u.getCodigo() == 0) {
            return insert(u);
        }else{
            return update(u);
        }
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
            boolean status = false;
            Connection con = null;
            PreparedStatement pstm = null;
            try{
               con =  ConnectionFactory.getConnection();
                pstm = con.prepareStatement(REMOVE);
                pstm.setInt(1, codigo);
                pstm.execute();
                status = true;
                
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,"Erro ao excluir " + e.getMessage());
            }finally{
                try{
                    ConnectionFactory.closeConnection(con, pstm);
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null, "Erro ao fechar a conexão" + e.getMessage());
                }
            }
            return status;
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
                u.setNome(rs.getString("nome"));
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
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            Usuario u = new Usuario();
            try{
                con = ConnectionFactory.getConnection();
                pstm = con.prepareStatement(LISTBYID);
                pstm.setInt(1, codigo);
                rs = pstm.executeQuery();
                while(rs.next()){
                    u.setCodigo(rs.getInt("codigo"));
                    u.setNome(rs.getString("nome"));
                    u.setLogin(rs.getString("login"));
                    u.setSenha(rs.getString("senha"));
                    u.setCpf(rs.getString("cpf"));
                    u.setTelefone(rs.getString("telefone"));
                    u.setDataNascimento(rs.getDate("data_nascimento"));
                    u.setSexo(rs.getString("sexo"));
                    
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Erro ao listar o usuário" + e.getMessage());
            }finally{
                try{
                    ConnectionFactory.closeConnection(con, pstm, rs);
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null,"Erro ao fechar a conexão"+ e.getMessage());
                }
                
            }
            return u;
    }       
    
    private int update(Usuario u){
        Connection con = null;
        PreparedStatement pstm = null;
        int retorno = -1;
        try{
            con = ConnectionFactory.getConnection();
            pstm = con.prepareStatement(UPDATE);
            pstm.setString(1, u.getNome());
            pstm.setString(2, u.getLogin());
            pstm.setString(3, u.getSenha());
            pstm.setString(4, u.getCpf());
            pstm.setString(5, u.getTelefone());
            pstm.setDate(6, new java.sql.Date(u.getDataNascimento().getTime()));
            pstm.setString(7, u.getSexo());
            pstm.setInt(8, u.getCodigo());
            pstm.execute();
            retorno = u.getCodigo();
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Erro ao editar os dados do usuário " + e.getMessage());
        }finally{
            try{
                ConnectionFactory.closeConnection(con, pstm);
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,"Erro ao fechar a conexão");
            }
        }
        return retorno;
    }

    @Override
    public List<Usuario> listByNome(String nome) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Usuario> usuarios = new ArrayList<>();
        try{
            con = ConnectionFactory.getConnection();
            pstm = con.prepareStatement(LISTBYNOME);
            pstm.setString(1, "%" +nome+ "%");
            rs = pstm.executeQuery();
            while(rs.next()){
                Usuario u = new Usuario();
                u.setCodigo(rs.getInt("codigo"));
                u.setNome(rs.getString("nome"));
                u.setCpf(rs.getString("cpf"));;
                u.setDataNascimento(rs.getDate("data_nascimento"));
                u.setLogin(rs.getString("login"));
                u.setSenha(rs.getString("senha"));
                u.setTelefone(rs.getString("telefone"));
                u.setSexo(rs.getString("sexo"));
                usuarios.add(u);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Erro ao pesquisar usuário" + e.getMessage());
        }finally{
            try{
                ConnectionFactory.closeConnection(con, pstm, rs);
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,"Erro ao fechar a conexão"+e.getMessage());
            }
        }
        return usuarios;
    }
}
