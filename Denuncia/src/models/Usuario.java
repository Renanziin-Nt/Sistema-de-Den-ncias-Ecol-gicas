package models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Usuario {
    private int id;
    private String nome;
    private Cargo cargo;  // Usando enum para o cargo
    private int id_central;
    private String senha;
    private LocalDate dataCriacao;
    private LocalTime horaCriacao;
    private String email;

    // Definição do enum Cargo
    public enum Cargo {
        ATENDENTE,
        AGENTE
    }

    // Getter e Setter para id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter e Setter para nome
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    // Getter e Setter para nome
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter e Setter para cargo
    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    // Getter e Setter para id_central
    public int getId_central() {
        return id_central;
    }

    public void setId_central(int id_central) {
        this.id_central = id_central;
    }

    // Getter e Setter para senha
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    // Getter e Setter para dataCriacao
    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    // Getter e Setter para horaCriacao
    public LocalTime getHoraCriacao() {
        return horaCriacao;
    }

    public void setHoraCriacao(LocalTime horaCriacao) {
        this.horaCriacao = horaCriacao;
    }
}
