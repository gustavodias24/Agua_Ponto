package benicio.solutions.guaponto.model;

public class AguaModel {
    String dia;
    int quantidadeAgua;

    public AguaModel() {
    }

    public AguaModel(String dia, int quantidadeAgua) {
        this.dia = dia;
        this.quantidadeAgua = quantidadeAgua;
    }


    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public int getQuantidadeAgua() {
        return quantidadeAgua;
    }

    public void setQuantidadeAgua(int quantidadeAgua) {
        this.quantidadeAgua = quantidadeAgua;
    }
}
