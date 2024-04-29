package benicio.solutions.guaponto.model;

public class RotinaModel {

    int mlIngerido, usuarioId;
    String ingestao;

    public RotinaModel() {
    }

    public String getIngestao() {
        return ingestao;
    }

    public void setIngestao(String ingestao) {
        this.ingestao = ingestao;
    }

    public int getMlIngerido() {
        return mlIngerido;
    }

    public void setMlIngerido(int mlIngerido) {
        this.mlIngerido = mlIngerido;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }
}
