/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MVCDriver;

import DAO.BancoDAO;
import DAO.BancoDAOException;
import controlador.Controlador;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.SplashScreen;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JLabel;
import modelo.Interno;
import visao.ConexaoBanco;
import visao.Visao;

/**
 *
 * @author Mateus
 */
public class MCVDriver {

    public static void main(String args[]) throws IOException, BancoDAOException {

        Interno modelo = new Interno();
 //      modelo.setFotoAntes("D:\\BS\\4.8\\BomSamaritanov4.8\\src\\icones\\foto.jpg");
        //    modelo.setFotoDepois("D:\\BS\\4.8\\BomSamaritanov4.8\\src\\icones\\foto.jpg");
        Visao visao = new Visao(modelo);
        /*java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Visao().setVisible(true);
            }
        });*/
        
        Controlador controlador = new Controlador(visao, modelo);
        
       // JFrame janela = new JFrame("Banco MatPhanie");
        // visao.set("Banco MatPhanie");

        WindowAdapter wa = new WindowAdapter() {
            public void windowClossing(WindowEvent e) {
                System.exit(0);
            }
        };
        
        
        visao.addWindowListener(wa);
        visao.pack();
       //janela.addWindowListener(wa);
        //  janela.add(visao);
        // janela.pack();
        //   janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        visao.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension tamanhoTela = Toolkit.getDefaultToolkit().getScreenSize();

        int largura = tamanhoTela.width;
        int altura = tamanhoTela.height;
        //int largura_janela = (int)janela.getPreferredSize().getWidth();
        //  int altura_janela = (int)janela.getPreferredSize().getHeight();
        int largura_janela = (int) visao.getPreferredSize().getWidth();
        int altura_janela = (int) visao.getPreferredSize().getHeight();

        /*janela.setLocation((largura - largura_janela)/2,(altura-altura_janela)/2);
         janela.setVisible(true);*/
        visao.setLocation((largura - largura_janela) / 2, (altura - altura_janela) / 2);

        visao.setVisible(true);
    }

}
