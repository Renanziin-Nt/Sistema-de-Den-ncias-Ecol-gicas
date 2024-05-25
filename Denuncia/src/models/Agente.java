package models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Agente extends Usuario {
  public Agente(int id, String nome, int id_central, String email, String senha) {
        this.setId(id);
        this.setNome(nome);
        this.setCargo(Cargo.AGENTE); // Definindo o cargo como ATENDENTE
        this.setId_central(id_central);
        this.setSenha(senha);
        this.setEmail(email);
        this.setDataCriacao(LocalDate.now());
        this.setHoraCriacao(LocalTime.now());
    }
    
  
}
