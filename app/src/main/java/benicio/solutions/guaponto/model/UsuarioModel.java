package benicio.solutions.guaponto.model;

import java.util.ArrayList;

public class UsuarioModel {
    String email, senha, nome, sobrenome, dataNascimento, rotinaAcorda, rotinaDorme;
    double peso;
    int id, idade, mediaAgua;

    public UsuarioModel() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getRotinaAcorda() {
        return rotinaAcorda;
    }

    public void setRotinaAcorda(String rotinaAcorda) {
        this.rotinaAcorda = rotinaAcorda;
    }

    public String getRotinaDorme() {
        return rotinaDorme;
    }

    public void setRotinaDorme(String rotinaDorme) {
        this.rotinaDorme = rotinaDorme;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public int getMediaAgua() {
        return mediaAgua;
    }

    public void setMediaAgua(int mediaAgua) {
        this.mediaAgua = mediaAgua;
    }
}
