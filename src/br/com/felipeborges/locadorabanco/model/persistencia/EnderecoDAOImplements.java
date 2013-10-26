package br.com.felipeborges.locadorabanco.model.persistencia;

import br.com.felipeborges.locadorabanco.model.Endereco;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Felipe Borges Tomaz
 */
public class EnderecoDAOImplements implements EnderecoDAO {

    private static final String INSERT = "insert into endereco (rua) values (?);";
    @Override
    public int salvar(Endereco e) {
        if(e.getCodigo() == 0){
            return inserir(e);
        }
        return -1;
    }

    private int inserir(Endereco e) {
        int status = -1;
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = ConnectionFactory.getConnection();
            pstm = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, e.getRua());
            pstm.execute();
            try(ResultSet rs = pstm.getGeneratedKeys()){
                rs.next();
                status = rs.getInt(1);
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir um endereço: " + ex.getMessage());
        //Ele vai fechar a conexão com o banco de dados
        } finally {
            try {
                ConnectionFactory.closeConnection(con, pstm);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar" + ex.getMessage());
            }
        }
        return status;
    }
}
