package screen;

import models.Denunciante;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Denunciantes extends JPanel {
    private JFrame frame;
    private List<Denunciante> denunciantes;

    public Denunciantes (JFrame frame, List<Denunciante> denunciantes) {
        this.frame = frame;
        this.denunciantes = denunciantes;
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        JLabel labelTitulo = new JLabel("Denunciantes Registrados");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        labelTitulo.setForeground(new Color(60, 63, 65));
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        labelTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(labelTitulo, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Nome", "Telefone", "Estado"};
        Object[][] data = new Object[denunciantes.size()][4];
        for (int i = 0; i < denunciantes.size(); i++) {
            Denunciante denunciante = denunciantes.get(i);
            data[i][0] = denunciante.getId();
            data[i][1] = denunciante.getNome();
            data[i][2] = denunciante.getTelefone();
            data[i][3] = denunciante.getEstado();
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        add(scrollPane, BorderLayout.CENTER);
    }
}
