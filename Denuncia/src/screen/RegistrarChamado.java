package screen;

import javax.swing.*;
import db.JsonDatabase;
import models.Chamados;
import models.Atendente;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RegistrarChamado extends JPanel {
    private JFrame frame;
    private Atendente atendente;

    public RegistrarChamado(JFrame frame, Atendente atendente) {
        this.frame = frame;
        this.atendente = atendente;
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        JLabel labelTitulo = new JLabel("Registrar Chamado");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        labelTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(labelTitulo, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        JLabel localizacaoLabel = new JLabel("Localização:");
        JTextField localizacaoField = new JTextField();
        formPanel.add(localizacaoLabel);
        formPanel.add(localizacaoField);

        JLabel descricaoLabel = new JLabel("Descrição:");
        JTextField descricaoField = new JTextField();
        formPanel.add(descricaoLabel);
        formPanel.add(descricaoField);

        JLabel denuncianteIdLabel = new JLabel("ID do Denunciante:");
        JTextField denuncianteIdField = new JTextField();
        formPanel.add(denuncianteIdLabel);
        formPanel.add(denuncianteIdField);

        JButton registrarButton = new JButton("Registrar");
        formPanel.add(new JLabel());  // Placeholder
        formPanel.add(registrarButton);

        add(formPanel, BorderLayout.CENTER);

        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String localizacao = localizacaoField.getText();
                String descricao = descricaoField.getText();
                int denuncianteId = Integer.parseInt(denuncianteIdField.getText());

                try {
                    atendente.registrarChamado(localizacao, descricao, denuncianteId);
                    JOptionPane.showMessageDialog(frame, "Chamado registrado com sucesso!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Erro ao registrar chamado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
