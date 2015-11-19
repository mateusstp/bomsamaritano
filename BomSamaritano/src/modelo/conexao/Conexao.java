/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.conexao;

import java.util.ArrayList;
import java.util.Iterator;
import modelo.Observer;

/**
 *
 * @author Mateus
 */
public class Conexao {
    private String Usuario;
    private String Senha;
    private String Servidor;
    private ArrayList receptores;

    /**
     * @return the Usuario
     */
    public String getUsuario() {
        return Usuario;
    }

    /**
     * @param Usuario the Usuario to set
     */
    public void setUsuario(String Usuario) {
        this.Usuario = Usuario;
    }

    /**
     * @return the Senha
     */
    public String getSenha() {
        return Senha;
    }

    /**
     * @param Senha the Senha to set
     */
    public void setSenha(String Senha) {
        this.Senha = Senha;
    }
    
    public void registro(Observer o) {
        receptores.add(o);
        o.update();//duvida????
    }

    public void retiraRegistro(Observer o) {
        receptores.remove(o);
    }

    public void atualizaObservadores() {
        Iterator it = receptores.iterator();
        while (it.hasNext()) {
            Observer o = (Observer) it.next();
            o.update();//duvida????

        }
    }

    /**
     * @return the Servidor
     */
    public String getServidor() {
        return Servidor;
    }

    /**
     * @param Servidor the Servidor to set
     */
    public void setServidor(String Servidor) {
        this.Servidor = Servidor;
    }

}
