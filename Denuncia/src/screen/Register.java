package screen;

import db.JsonDatabase;
import models.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Register extends JPanel {
    private JFrame frame;
    private JsonDatabase db;

    public Register(JFrame frame) {
        this.frame = frame;
        this.db = new JsonDatabase();  // Inicializar a instância do banco de dados
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        JLabel labelTitulo = new JLabel("Cadastro de Usuário");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        labelTitulo.setForeground(new Color(60, 63, 65));
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        labelTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(labelTitulo, BorderLayout.NORTH);

        JPanel registerPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        registerPanel.setBackground(new Color(245, 245, 245));
        registerPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 40));
        registerPanel.add(new JLabel("Nome de Usuário:"));
        JTextField usernameField = new JTextField();
        registerPanel.add(usernameField);
        registerPanel.add(new JLabel("Senha:"));
        JPasswordField passwordField = new JPasswordField();
        registerPanel.add(passwordField);
        registerPanel.add(new JLabel("Email:"));
        JTextField emailField = new JTextField();
        registerPanel.add(emailField);
        registerPanel.add(new JLabel("ID Central:"));
        JTextField idCentralField = new JTextField();
        registerPanel.add(idCentralField);
        registerPanel.add(new JLabel("Tipo de Funcionário:"));
        JComboBox<String> cargoComboBox = new JComboBox<>(new String[]{"ATENDENTE", "AGENTE"});
        registerPanel.add(cargoComboBox);
        add(registerPanel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2, 20, 20));
        buttonsPanel.setBackground(new Color(245, 245, 245));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        JButton registerButton = new JButton("Registrar");
        styleButton(registerButton);
        buttonsPanel.add(registerButton);
        JButton backButton = new JButton("Voltar");
        styleButton(backButton);
        buttonsPanel.add(backButton);
        add(buttonsPanel, BorderLayout.SOUTH);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(new Login(frame));
                frame.revalidate();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String email = emailField.getText();
                int idCentral;
                try {
                    idCentral = Integer.parseInt(idCentralField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Por favor, insira um número válido para o ID Central.");
                    return;
                }

                // Criar um novo usuário
                Usuario usuario = new Usuario();
                usuario.setNome(username);
                usuario.setSenha(password);
                usuario.setId_central(idCentral);
                usuario.setEmail(email);
                usuario.setCargo(cargoComboBox.getSelectedItem().equals("ATENDENTE") ? Usuario.Cargo.ATENDENTE : Usuario.Cargo.AGENTE);

                // Adicionar o usuário ao banco de dados JSON
                db.addUsuario(usuario);

                // Mensagem de sucesso
                JOptionPane.showMessageDialog(frame, "Usuário registrado com sucesso!");

                // Limpar os campos
                usernameField.setText("");
                passwordField.setText("");
                emailField.setText("");
                idCentralField.setText("");
            }
        });
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(100, 149, 237));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 149, 237), 1),
                BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));
    }
}
