
package br.com.felipeborges.locadorabanco.controller;

import br.com.felipeborges.locadorabanco.model.Endereco;
import br.com.felipeborges.locadorabanco.model.persistencia.EnderecoDAO;
import br.com.felipeborges.locadorabanco.model.persistencia.EnderecoDAOImplements;

/**
 *
 * @author Felipe Borges Tomaz
 */
public class EnderecoController {
    public int salvar (Endereco e){
        EnderecoDAO dao = new EnderecoDAOImplements();
        return dao.salvar(e);
    }
}
