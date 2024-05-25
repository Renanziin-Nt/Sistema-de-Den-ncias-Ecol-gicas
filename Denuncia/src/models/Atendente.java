package models;

import screen.Login;
import db.JsonDatabase;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Atendente extends Usuario {
    private JsonDatabase dbDenunciantes;
    private JsonDatabase dbChamados;

    public Atendente(int id, String nome, int id_central, String senha, String email, JsonDatabase dbDenunciantes, JsonDatabase dbChamados) {
        this.setId(id);
        this.setNome(nome);
        this.setCargo(Cargo.ATENDENTE); // Definindo o cargo como ATENDENTE
        this.setId_central(id_central);
        this.setSenha(senha);
        this.setEmail(email);
        this.setDataCriacao(LocalDate.now());
        this.setHoraCriacao(LocalTime.now());
        this.dbDenunciantes = dbDenunciantes;
        this.dbChamados = dbChamados;
    }

    // Método para registrar um denunciante
    public void registrarDenunciante(String nome, String telefone, String estado, int atendenteId, String atendente) {
        List<Denunciante> denunciantes = dbDenunciantes.readDenunciantes();

        int novoId = denunciantes.size() + 1; // ID é o tamanho atual + 1
        Denunciante novoDenunciante = new Denunciante(novoId, nome, telefone, estado, atendenteId, atendente);

        dbDenunciantes.addDenunciante(novoDenunciante);
        System.out.println("Denunciante registrado com sucesso!");
    }

    // Método para registrar um chamado
    public void registrarChamado(String localizacao, String descricao, int denuncianteId, String atendente) {
        List<Denunciante> denunciantes = dbDenunciantes.readDenunciantes();
        Denunciante denunciante = buscarDenunciantePorId(denunciantes, denuncianteId);

        if (denunciante == null) {
            System.out.println("Denunciante não encontrado.");
            return;
        }

        List<Chamados> chamados = dbChamados.readChamados();
        Chamados novoChamado = new Chamados();
        novoChamado.setId(chamados.size() + 1); // ID é o tamanho atual + 1
        novoChamado.setLocalizacao(localizacao);
        novoChamado.setDescricao(descricao);
        novoChamado.setStatus(Chamados.Status.ABERTO); // Usando o método setStatus com enum Status
        novoChamado.setDenuncianteId(denunciante.getId());
        novoChamado.setDenuncianteNome(denunciante.getNome());
       /*  novoChamado.setAtendenteId(AtendenteId); */ // Definindo o ID do atendente
        novoChamado.setAtendenteNome(atendente); // Definindo o nome do atendente

        dbChamados.addChamado(novoChamado);
        System.out.println("Chamado registrado com sucesso!");
    }

    // Método para buscar um denunciante por ID
    public Denunciante buscarDenunciantePorId(List<Denunciante> denunciantes, int id) {
        for (Denunciante denunciante : denunciantes) {
            if (denunciante.getId() == id) {
                return denunciante;
            }
        }
        return null;
    }
}
