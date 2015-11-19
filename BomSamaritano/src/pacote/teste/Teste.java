/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pacote.teste;

import javax.swing.ImageIcon;

/**
 *
 * @author Mateus
 */
public class Teste {
     
        public static void main(String args[]) {
        String teste="";
        if(teste==""){
            System.out.println("NULLO");    
        }
            //Teste t = new Teste();
     /*   ImageIcon l1 = new ImageIcon("C:\\Users\\Mateus\\Pictures\\LifeFrame\\1.jpg","teste");
        ImageIcon l2 = new ImageIcon("C:\\Users\\Mateus\\Pictures\\LifeFrame\\1.jpg","teste");
        System.out.println(l1.getIconHeight()+"---"+l1.getIconWidth());
        ImageIcon l3 = t.createImageIcon("C:\\Users\\Mateus\\Pictures\\LifeFrame\\1.jpg","teste");
        ImageIcon l4 = t.createImageIcon("C:\\Users\\Mateus\\Pictures\\LifeFrame\\2.jpg","teste");
        ImageIcon l5 = t.createImageIcon("C:\\Users\\Mateus\\Pictures\\LifeFrame\\1.jpg","teste");*/
        
        }
    
    protected  ImageIcon createImageIcon(String path, String description) {
        
     //   java.net.URL imgURL = getClass().getResource(path);
        if (getClass().getResource(path)!=null) {
            return new ImageIcon(path, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
}
