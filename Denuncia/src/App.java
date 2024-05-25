

import screen.Login;


import javax.swing.*;


public class App {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Sistema de Chamados");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null); // Centralizar a janela na tela

        // Adiciona a tela de login ao frame principal
        frame.setContentPane(new Login(frame));

        frame.setVisible(true);
    }
}
