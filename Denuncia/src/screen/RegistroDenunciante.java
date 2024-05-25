package screen;

import javax.swing.*;
import models.Atendente;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistroDenunciante extends JPanel {
    private JFrame frame;
    private Atendente atendente;

    public RegistroDenunciante(JFrame frame, Atendente atendente) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        JLabel labelTitulo = new JLabel("Registro de Denunciante");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        labelTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(labelTitulo, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        JLabel nomeLabel = new JLabel("Nome:");
        JTextField nomeField = new JTextField();
        formPanel.add(nomeLabel);
        formPanel.add(nomeField);

        JLabel telefoneLabel = new JLabel("Telefone:");
        JTextField telefoneField = new JTextField();
        formPanel.add(telefoneLabel);
        formPanel.add(telefoneField);

        JLabel estadoLabel = new JLabel("Estado:");
        JTextField estadoField = new JTextField();
        formPanel.add(estadoLabel);
        formPanel.add(estadoField);

        JButton registrarButton = new JButton("Registrar");
        formPanel.add(new JLabel());  // Placeholder
        formPanel.add(registrarButton);

        add(formPanel, BorderLayout.CENTER);

        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText();
                String telefone = telefoneField.getText();
                String estado = estadoField.getText();
                atendente.registrarDenunciante(nome, telefone, estado, atendente.getId(),atendente.getNome());
                JOptionPane.showMessageDialog(frame, "Denunciante registrado com sucesso!");
            }
        });
    }
}
