package models;

public class Denunciante {
    private int id;
    private String nome;
    private String telefone;
    private String estado;
    private int atendenteId;
    private String atendenteNome;

    // Construtor padrão necessário para a desserialização
    public Denunciante() {
    }

    // Construtor completo
    public Denunciante(int id, String nome, String telefone, String estado, int atendenteId, String atendenteNome) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.estado = estado;
        this.atendenteId = atendenteId;
        this.atendenteNome = atendenteNome;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getAtendenteId() {
        return atendenteId;
    }

    public void setAtendenteId(int atendenteId) {
        this.atendenteId = atendenteId;
    }

    public String getAtendenteNome() {
        return atendenteNome;
    }

    public void setAtendenteNome(String atendenteNome) {
        this.atendenteNome = atendenteNome;
    }
}
