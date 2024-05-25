package screen;

import javax.swing.*;
import db.JsonDatabase;
import models.Atendente;
import models.Usuario;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Login extends JPanel {
    private JFrame frame;
    private JsonDatabase db;

    public Login(JFrame frame) {
        this.frame = frame;
        this.db = new JsonDatabase();  // Inicializar a instância do banco de dados
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        JLabel labelTitulo = new JLabel("Login");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 36));
        labelTitulo.setForeground(new Color(60, 63, 65));
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        labelTitulo.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));
        add(labelTitulo, BorderLayout.NORTH);

        JPanel loginPanel = new JPanel(new GridLayout(4, 1, 0, 10));
        loginPanel.setBackground(new Color(240, 240, 240));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 40));
        
        JPanel usernamePanel = new JPanel(new BorderLayout());
        usernamePanel.setBackground(new Color(240, 240, 240));
        JLabel usernameLabel = new JLabel("Nome de Usuário:");
        JTextField usernameField = new JTextField();
        usernamePanel.add(usernameLabel, BorderLayout.NORTH);
        usernamePanel.add(usernameField, BorderLayout.CENTER);
        loginPanel.add(usernamePanel);

        JPanel emailPanel = new JPanel(new BorderLayout());
        emailPanel.setBackground(new Color(240, 240, 240));
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        emailPanel.add(emailLabel, BorderLayout.NORTH);
        emailPanel.add(emailField, BorderLayout.CENTER);
        loginPanel.add(emailPanel);

        JPanel cargoPanel = new JPanel(new BorderLayout());
        cargoPanel.setBackground(new Color(240, 240, 240));
        JLabel cargoLabel = new JLabel("Cargo:");
        JComboBox<String> cargoComboBox = new JComboBox<>(new String[]{"ATENDENTE", "AGENTE"});
        cargoPanel.add(cargoLabel, BorderLayout.NORTH);
        cargoPanel.add(cargoComboBox, BorderLayout.CENTER);
        loginPanel.add(cargoPanel);

        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.setBackground(new Color(240, 240, 240));
        JLabel passwordLabel = new JLabel("Senha:");
        JPasswordField passwordField = new JPasswordField();
        passwordPanel.add(passwordLabel, BorderLayout.NORTH);
        passwordPanel.add(passwordField, BorderLayout.CENTER);
        loginPanel.add(passwordPanel);

        add(loginPanel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2, 20, 20));
        buttonsPanel.setBackground(new Color(240, 240, 240));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        
        JButton loginButton = new JButton("Login");
        styleButton(loginButton);
        buttonsPanel.add(loginButton);
        
        JButton registerButton = new JButton("Cadastre-se");
        styleButton(registerButton);
        buttonsPanel.add(registerButton);
        
        add(buttonsPanel, BorderLayout.SOUTH);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(new Register(frame));
                frame.revalidate();
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String email = emailField.getText();
                String cargo = cargoComboBox.getSelectedItem().toString();
                String password = new String(passwordField.getPassword());

                // Verificar se o usuário existe no banco de dados
                List<Usuario> usuarios = db.readUsuarios();
                Usuario usuarioLogado = null;
                for (Usuario usuario : usuarios) {
                    if (usuario.getNome().equals(username) && usuario.getEmail().equals(email) && usuario.getCargo().toString().equals(cargo) && usuario.getSenha().equals(password)) {
                        usuarioLogado = usuario;
                        break;
                    }
                }

                if (usuarioLogado != null) {
                    JOptionPane.showMessageDialog(frame, "Login bem-sucedido!");
                    // Criar o objeto Atendente logado
                    Atendente atendenteLogado = new Atendente(usuarioLogado.getId(), usuarioLogado.getNome(), usuarioLogado.getId_central(), usuarioLogado.getSenha(), usuarioLogado.getEmail(), db, db);
                    // Abrir a tela de ChamadosScreen após o login bem-sucedido
                    openChamadosScreen(atendenteLogado);
                } else {
                    JOptionPane.showMessageDialog(frame, "Usuário ou senha incorretos. Por favor, tente novamente.");
                    // Limpar os campos
                    usernameField.setText("");
                    emailField.setText("");
                    passwordField.setText("");
                }
            }
        });
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(100, 149, 237));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 149, 237), 1),
                BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));
    }

    private void openChamadosScreen(Atendente atendenteLogado) {
        // Remove a tela de login
        frame.getContentPane().removeAll();
        // Cria e adiciona a tela de ChamadosScreen ao frame
        ChamadosScreen chamadosScreen = new ChamadosScreen(frame, atendenteLogado);
        frame.add(chamadosScreen, BorderLayout.CENTER);
        // Valida e atualiza o frame
        frame.revalidate();
    }
}
