package models;

import java.time.LocalDate;

public class Chamados {
    private int id;
    private String localizacao;
    private String descricao;
    private Status status;
    private int denuncianteId;
    private String denuncianteNome;
    private int atendenteId;
    private String atendenteNome;

    public enum Status {
        ABERTO,
        ENCERRADO
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getDenuncianteId() {
        return denuncianteId;
    }

    public void setDenuncianteId(int denuncianteId) {
        this.denuncianteId = denuncianteId;
    }

    public String getDenuncianteNome() {
        return denuncianteNome;
    }

    public void setDenuncianteNome(String denuncianteNome) {
        this.denuncianteNome = denuncianteNome;
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
