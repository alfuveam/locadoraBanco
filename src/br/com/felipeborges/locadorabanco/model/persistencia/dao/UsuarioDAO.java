
package br.com.felipeborges.locadorabanco.model.persistencia.dao;

import br.com.felipeborges.locadorabanco.model.Usuario;
import java.util.List;

public interface UsuarioDAO {
    int salve(Usuario u);
    boolean remove (int codigo);
    List<Usuario> listAll();
    Usuario listById(int codigo);   
}
