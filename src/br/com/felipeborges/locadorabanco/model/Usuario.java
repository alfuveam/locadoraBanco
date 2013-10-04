
package br.com.felipeborges.locadorabanco.model;

import java.util.Date;

public class Usuario {
    private String nome;
    private String login;
    private String senha;
    private String cpf;
    private String telefone;
    private Date dataNascimento;
    private String sexo; 
    private int codigo;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Usuario() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    @Override
    public String toString() {
        return "Usuario{" + "nome=" + nome + ", login=" + login + ", senha=" + senha + ", cpf=" + cpf + ", telefone=" + telefone + ", dataNascimento=" + dataNascimento + ", sexo=" + sexo + '}';
    }

    
}