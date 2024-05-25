package db;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Chamados;
import models.Denunciante;
import models.Usuario;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonDatabase {
    private static final String USUARIOS_FILE_PATH = "Denuncia/src/db/funcionarios.json";
    private static final String DENUNCIANTES_FILE_PATH = "Denuncia/src/db/denunciante.json";
    private static final String CHAMADOS_FILE_PATH = "Denuncia/src/db/chamados.json";    
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public List<Usuario> readUsuarios() {
        return readFile(USUARIOS_FILE_PATH, new TypeReference<List<Usuario>>() {});
    }

    public void writeUsuarios(List<Usuario> usuarios) {
        writeFile(USUARIOS_FILE_PATH, usuarios);
    }

    public void addUsuario(Usuario usuario) {
        List<Usuario> usuarios = readUsuarios();
        usuario.setId(generateNewId(usuarios));
        usuarios.add(usuario);
        writeUsuarios(usuarios);
    }

    public List<Denunciante> readDenunciantes() {
        return readFile(DENUNCIANTES_FILE_PATH, new TypeReference<List<Denunciante>>() {});
    }

    public void writeDenunciantes(List<Denunciante> denunciantes) {
        writeFile(DENUNCIANTES_FILE_PATH, denunciantes);
    }

    public void addDenunciante(Denunciante denunciante) {
        List<Denunciante> denunciantes = readDenunciantes();
        denunciante.setId(generateNewId(denunciantes));
        denunciantes.add(denunciante);
        writeDenunciantes(denunciantes);
    }

    public List<Chamados> readChamados() {
        return readFile(CHAMADOS_FILE_PATH, new TypeReference<List<Chamados>>() {});
    }

    public void writeChamados(List<Chamados> chamados) {
        writeFile(CHAMADOS_FILE_PATH, chamados);
    }

    public void addChamado(Chamados chamado) {
        List<Chamados> chamados = readChamados();
        chamado.setId(generateNewId(chamados));
        chamados.add(chamado);
        writeChamados(chamados);
    }

    private <T> List<T> readFile(String filePath, TypeReference<List<T>> typeReference) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                return objectMapper.readValue(file, typeReference);
            } else {
                return new ArrayList<>();
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler arquivo: " + filePath, e);
        }
    }

    private <T> void writeFile(String filePath, List<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Os dados para escrever não podem ser nulos");
        }
        try {
            objectMapper.writeValue(new File(filePath), data);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao escrever arquivo: " + filePath, e);
        }
    }

    private int generateNewId(List<?> items) {
        return items.stream()
                .mapToInt(item -> {
                    if (item instanceof Usuario) {
                        return ((Usuario) item).getId();
                    } else if (item instanceof Denunciante) {
                        return ((Denunciante) item).getId();
                    } else if (item instanceof Chamados) {
                        return ((Chamados) item).getId();
                    }
                    return 0;
                })
                .max()
                .orElse(0) + 1;
    }

    public void updateChamado(Chamados chamado) throws IOException {
        List<Chamados> chamados = readChamados();
        int index = -1;
        for (int i = 0; i < chamados.size(); i++) {
            if (chamados.get(i).getId() == chamado.getId()) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            chamados.set(index, chamado);
            writeChamados(chamados);
        }
    }

    public Denunciante buscarDenunciantePorId(int id) {
        List<Denunciante> denunciantes = readDenunciantes();
        for (Denunciante denunciante : denunciantes) {
            if (denunciante.getId() == id) {
                return denunciante;
            }
        }
        return null;
    }
}
