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
public class Profissao {

    private String trabalhoAtual;
    private String trabalhoCA;
    private String aposentado;
    private String afastadoPS;
    private String salarioDesemprego;
    private String escolaridade;

    public Profissao() {
        trabalhoAtual = "";
        trabalhoCA = "";
        aposentado = "";
        afastadoPS = "";
        salarioDesemprego = "";
        escolaridade = "";
    }

   
    /**
     * @return the trabalhoAtual
     */
    public String getTrabalhoAtual() {
        return trabalhoAtual;
    }

    /**
     * @param trabalhoAtual the trabalhoAtual to set
     */
    public void setTrabalhoAtual(String trabalhoAtual) {
        this.trabalhoAtual = trabalhoAtual;
    }

    /**
     * @return the trabalhoCA
     */
    public String getTrabalhoCA() {
        return trabalhoCA;
    }

    /**
     * @param trabalhoCA the trabalhoCA to set
     */
    public void setTrabalhoCA(String trabalhoCA) {
        this.trabalhoCA = trabalhoCA;
    }

    /**
     * @return the aposentado
     */
    public String getAposentado() {
        return aposentado;
    }

    /**
     * @param aposentado the aposentado to set
     */
    public void setAposentado(String aposentado) {
        this.aposentado = aposentado;
    }

    /**
     * @return the afastadoPS
     */
    public String getAfastadoPS() {
        return afastadoPS;
    }

    /**
     * @param afastadoPS the afastadoPS to set
     */
    public void setAfastadoPS(String afastadoPS) {
        this.afastadoPS = afastadoPS;
    }

    /**
     * @return the salarioDesemprego
     */
    public String getSalarioDesemprego() {
        return salarioDesemprego;
    }

    /**
     * @param salarioDesemprego the salarioDesemprego to set
     */
    public void setSalarioDesemprego(String salarioDesemprego) {
        this.salarioDesemprego = salarioDesemprego;
    }

    /**
     * @return the escolaridade
     */
    public String getEscolaridade() {
        return escolaridade;
    }

    /**
     * @param escolaridade the escolaridade to set
     */
    public void setEscolaridade(String escolaridade) {
        this.escolaridade = escolaridade;
    }

}
