public class Pessoa {
    private String sigla;
    private int id;
    private boolean activated;


    public Pessoa(int id, String sigla, boolean activated) {
        this.id = id;
        this.sigla = sigla;
        this.activated = activated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }
}
