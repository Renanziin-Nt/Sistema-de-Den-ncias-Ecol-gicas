package screen;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import db.JsonDatabase;
import models.Atendente;
import models.Chamados;
import models.Chamados.Status;
import models.Denunciante;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class ChamadosScreen extends JPanel {
    private JFrame frame;
    private JsonDatabase db;
    private Atendente atendente;
    private JPanel chamadosPanel;

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
        chamadosPanel = new JPanel();
        chamadosPanel.setLayout(new BoxLayout(chamadosPanel, BoxLayout.Y_AXIS));
        chamadosPanel.setBackground(new Color(245, 245, 245));
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
                registrarChamadoFrame.getContentPane().add(new RegistrarChamado(registrarChamadoFrame, atendente));
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
                registroDenuncianteFrame.getContentPane().add(new RegistroDenunciante(registroDenuncianteFrame, atendente));
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

        // Botão para atualizar a lista de chamados
        JButton refreshButton = new JButton("Atualizar");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshChamados();
            }
        });
        add(refreshButton, BorderLayout.SOUTH);

        // Carregar os chamados inicialmente
        refreshChamados();
    }

    private void refreshChamados() {
        chamadosPanel.removeAll(); // Limpar o painel atual

        try {
            // Ler os chamados do arquivo JSON
            List<Chamados> chamadosList = db.readChamados();

            for (Chamados chamado : chamadosList) {
                JPanel chamadoPanel = new JPanel(new BorderLayout());
                chamadoPanel.setBackground(new Color(230, 230, 230));
                chamadoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                
                JPanel chamadoContentPanel = new JPanel(new GridLayout(0, 1));
                chamadoContentPanel.setBackground(new Color(230, 230, 230));
                
                JLabel descricaoLabel = new JLabel("Descrição: " + chamado.getDescricao());
                descricaoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
                descricaoLabel.setForeground(new Color(60, 63, 65));
                descricaoLabel.setBorder(new EmptyBorder(5, 10, 5, 10));
                chamadoContentPanel.add(descricaoLabel);
                
                Denunciante denunciante = db.buscarDenunciantePorId(chamado.getDenuncianteId());
                JLabel denuncianteLabel = new JLabel("Denunciante: " + denunciante.getNome());
                denuncianteLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                denuncianteLabel.setForeground(new Color(60, 63, 65));
                denuncianteLabel.setBorder(new EmptyBorder(5, 10, 5, 10));
                chamadoContentPanel.add(denuncianteLabel);

                JLabel atendenteLabel = new JLabel("Atendente: " + chamado.getAtendenteNome());
                atendenteLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                atendenteLabel.setForeground(new Color(60, 63, 65));
                atendenteLabel.setBorder(new EmptyBorder(5, 10, 5, 10));
                chamadoContentPanel.add(atendenteLabel);

                chamadoPanel.add(chamadoContentPanel, BorderLayout.CENTER);
                
                // Botões de status do chamado
                JPanel statusPanel = new JPanel();
                statusPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
                statusPanel.setBackground(new Color(230, 230, 230));
                
                JToggleButton abertoButton = new JToggleButton("Aberto");
                abertoButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        chamado.setStatus(Status.ABERTO);
                        try {
                            db.updateChamado(chamado);
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(frame, "Erro ao atualizar status do chamado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
                statusPanel.add(abertoButton);

                JToggleButton encerradoButton = new JToggleButton("Encerrado");
                encerradoButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        chamado.setStatus(Status.ENCERRADO);
                        try {
                            db.updateChamado(chamado);
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(frame, "Erro ao atualizar status do chamado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
                statusPanel.add(encerradoButton);

                ButtonGroup statusGroup = new ButtonGroup();
                statusGroup.add(abertoButton);
                statusGroup.add(encerradoButton);

                chamadoPanel.add(statusPanel, BorderLayout.SOUTH);

                // Botão para adicionar resoluções, somente se estiver aberto
                if (chamado.getStatus() == Status.ABERTO) {
                    JButton resolucaoButton = new JButton("Ver Resoluções");
                    styleButton(resolucaoButton);
                    resolucaoButton.setPreferredSize(new Dimension(120, 30)); // Tamanho menor
                    resolucaoButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            mostrarResolucoes(chamado);
                        }
                    });
                    chamadoPanel.add(resolucaoButton, BorderLayout.EAST);
                }

                chamadosPanel.add(chamadoPanel);
                chamadosPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Erro ao ler os chamados: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        // Atualizar a exibição da tela
        revalidate();
        repaint();
    }

    private void mostrarResolucoes(Chamados chamado) {
        JFrame resolucaoFrame = new JFrame("Resoluções do Chamado");
        resolucaoFrame.setSize(400, 300);
        resolucaoFrame.setLayout(new BorderLayout());

        String[] columnNames = {"Resolução"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable resolucaoTable = new JTable(tableModel);

        // Adiciona as resoluções existentes na tabela
        for (String resolucao : chamado.getResolucoes()) {
            tableModel.addRow(new Object[]{resolucao});
        }

        JScrollPane scrollPane = new JScrollPane(resolucaoTable);

        JTextField resolucaoField = new JTextField();
        JButton addResolucaoButton = new JButton("Adicionar Resolução");
        addResolucaoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String novaResolucao = resolucaoField.getText();
                if (!novaResolucao.isEmpty()) {
                    chamado.getResolucoes().add(novaResolucao);
                    tableModel.addRow(new Object[]{novaResolucao});
                    resolucaoField.setText("");
                    try {
                        db.updateChamado(chamado);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(resolucaoFrame, "Erro ao salvar resolução: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(resolucaoField, BorderLayout.CENTER);
        inputPanel.add(addResolucaoButton, BorderLayout.EAST);

        resolucaoFrame.add(scrollPane, BorderLayout.CENTER);
        resolucaoFrame.add(inputPanel, BorderLayout.SOUTH);

        resolucaoFrame.setLocationRelativeTo(frame);
        resolucaoFrame.setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(100, 149, 237));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 149, 237), 1),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
    }
}
