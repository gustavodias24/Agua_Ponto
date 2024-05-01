package benicio.solutions.guaponto.model;

public class UsuarioModelToBody  {
    String email = "", senha = "", nome = "", sobrenome = "";
    String dataNascimento = "";
    String rotinaAcorda = "", rotinaDorme = "";
    double peso = 0, altura = 0;
    int idade = 0, mediaAgua = 0;

    public UsuarioModelToBody() {
    }

    public UsuarioModelToBody(String email, String senha, String nome, String sobrenome, String dataNascimento, String rotinaAcorda, String rotinaDorme, double peso, double altura, int idade, int mediaAgua) {
        this.email = email;
        this.senha = senha;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.dataNascimento = dataNascimento;
        this.rotinaAcorda = rotinaAcorda;
        this.rotinaDorme = rotinaDorme;
        this.peso = peso;
        this.altura = altura;
        this.idade = idade;
        this.mediaAgua = mediaAgua;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
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

