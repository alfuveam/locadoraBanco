
package br.com.felipeborges.locadorabanco.controller;

import br.com.felipeborges.locadorabanco.model.Usuario;
import br.com.felipeborges.locadorabanco.model.persistencia.UsuarioDAOImplements;
import br.com.felipeborges.locadorabanco.model.persistencia.dao.UsuarioDAO;

public class UsuarioController {
    public int salvar(Usuario u){
        UsuarioDAO dao = new UsuarioDAOImplements();
        return dao.salve(u);
    }
}
