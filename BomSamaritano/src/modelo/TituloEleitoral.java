/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author Mateus
 */
public class TituloEleitoral {

    private String Zona;
    private String Secao;
    private String Numero;

    public TituloEleitoral() {
        Zona = "";
        Secao = "";
        Numero = "";
    }

    /**
     * @return the Zona
     */
    public String getZona() {
        return Zona;
    }

    /**
     * @param Zona the Zona to set
     */
    public void setZona(String Zona) {
        this.Zona = Zona;
    }

    /**
     * @return the Secao
     */
    public String getSecao() {
        return Secao;
    }

    /**
     * @param Secao the Secao to set
     */
    public void setSecao(String Secao) {
        this.Secao = Secao;
    }

    /**
     * @return the Numero
     */
    public String getNumero() {
        return Numero;
    }

    /**
     * @param Numero the Numero to set
     */
    public void setNumero(String Numero) {
        this.Numero = Numero;
    }
}
