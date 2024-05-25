package screen;

import javax.swing.*;

import db.JsonDatabase;
import models.Chamados;
import models.Denunciante;
import models.Atendente;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class ChamadosScreen extends JPanel {
    private JFrame frame;
    private JsonDatabase db;
    private Atendente atendente;

    public ChamadosScreen(JFrame frame, Atendente atendente) {
        this.frame = frame;
        this.atendente = atendente;
        this.db = new JsonDatabase();  // Inicializar a instância do banco de dados
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        // Título da tela
        JLabel labelTitulo = new JLabel("Gerenciamento de Chamados");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        labelTitulo.setForeground(new Color(60, 63, 65));
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        labelTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(labelTitulo, BorderLayout.NORTH);

        // Painel para exibir os chamados
        JPanel chamadosPanel = new JPanel();
        chamadosPanel.setLayout(new BoxLayout(chamadosPanel, BoxLayout.Y_AXIS));
        chamadosPanel.setBackground(new Color(245, 245, 245));
        chamadosPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        try {
            // Ler os chamados do arquivo JSON
            List<Chamados> chamadosList = db.readChamados();

            for (Chamados chamado : chamadosList) {
                JPanel chamadoPanel = new JPanel(new BorderLayout());
                chamadoPanel.setBackground(new Color(230, 230, 230));
                chamadoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                chamadoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

                JLabel descricaoLabel = new JLabel( " -    Descrição:" + chamado.getDescricao());
                descricaoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
                descricaoLabel.setForeground(new Color(60, 63, 65));
                chamadoPanel.add(descricaoLabel, BorderLayout.CENTER);

                Denunciante denunciante = db.buscarDenunciantePorId(chamado.getDenuncianteId());
                JLabel denuncianteLabel = new JLabel("Denunciante: " + denunciante.getNome());
                denuncianteLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                denuncianteLabel.setForeground(new Color(60, 63, 65));
                chamadoPanel.add(denuncianteLabel, BorderLayout.WEST);

                JLabel atendenteLabel = new JLabel("     Atendente: " + atendente.getNome());
                atendenteLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                atendenteLabel.setForeground(new Color(60, 63, 65));
                chamadoPanel.add(atendenteLabel, BorderLayout.EAST);

                JButton updateButton = new JButton(chamado.getStatus() == Chamados.Status.ABERTO ? "Encerrar" : "Abrir");
                styleButton(updateButton);
                updateButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (chamado.getStatus() == Chamados.Status.ABERTO) {
                            chamado.setStatus(Chamados.Status.ENCERRADO);
                            updateButton.setText("Abrir");
                        } else {
                            chamado.setStatus(Chamados.Status.ABERTO);
                            updateButton.setText("Encerrar");
                        }
                        // Atualizar o status do chamado no banco de dados
                        try {
                            db.updateChamado(chamado);
                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    }
                });
                chamadoPanel.add(updateButton, BorderLayout.SOUTH);

                chamadosPanel.add(chamadoPanel);
                chamadosPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Erro ao ler os chamados: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        JScrollPane scrollPane = new JScrollPane(chamadosPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        // Menu suspenso para acessar outras telas
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Telas");
        JMenuItem itemDenunciantes = new JMenuItem("Denunciantes");
        JMenuItem itemRegistrarChamado = new JMenuItem("Registrar Chamado");
        JMenuItem itemRegistroDenunciante = new JMenuItem("Registro Denunciante");

        itemDenunciantes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abrir tela de Denunciantes
                List<Denunciante> denunciantes = db.readDenunciantes();
                JFrame denunciantesFrame = new JFrame("Denunciantes");
                denunciantesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                denunciantesFrame.getContentPane().add(new Denunciantes(denunciantesFrame, denunciantes));
                denunciantesFrame.pack();
                denunciantesFrame.setLocationRelativeTo(frame);
                denunciantesFrame.setVisible(true);
            }
        });

        itemRegistrarChamado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abrir tela de Registrar Chamado
                JFrame registrarChamadoFrame = new JFrame("Registrar Chamado");
                registrarChamadoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                registrarChamadoFrame.getContentPane().add(new RegistrarChamado(registrarChamadoFrame, null));
                registrarChamadoFrame.pack();
                registrarChamadoFrame.setLocationRelativeTo(frame);
                registrarChamadoFrame.setVisible(true);
            }
        });

        itemRegistroDenunciante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abrir tela de Registro de Denunciante
                JFrame registroDenuncianteFrame = new JFrame("Registro Denunciante");
                registroDenuncianteFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                registroDenuncianteFrame.getContentPane().add(new RegistroDenunciante(registroDenuncianteFrame, null));
                registroDenuncianteFrame.pack();
                registroDenuncianteFrame.setLocationRelativeTo(frame);
                registroDenuncianteFrame.setVisible(true);
            }
        });

        menu.add(itemDenunciantes);
        menu.add(itemRegistrarChamado);
        menu.add(itemRegistroDenunciante);
        menuBar.add(menu);

        frame.setJMenuBar(menuBar);
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(100, 149, 237));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 1),
        BorderFactory.createEmptyBorder(10, 25, 10, 25)
));
}
}
               
