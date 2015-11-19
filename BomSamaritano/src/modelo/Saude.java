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
public class Saude {

    private String sono;
    private String alimentacao;
    private String alucinacaoDelirios;
    private String medicacao;
    private String autoExterminio;
    private String examesEspecificos;
    private String desmaiosConvulcoes;

    public Saude() {
        sono = "";
        alimentacao = "";
        alucinacaoDelirios = "";
        medicacao = "";
        autoExterminio = "";
        examesEspecificos = "";
        desmaiosConvulcoes = "";
    }


    public String getSono() {
        return sono;
    }

    public void setSono(String sono) {
        this.sono = sono;
    }


    public String getAlimentacao() {
        return alimentacao;
    }

    public void setAlimentacao(String alimentacao) {
        this.alimentacao = alimentacao;
    }

    public String getAlucinacaoDelirios() {
        return alucinacaoDelirios;
    }

    public void setAlucinacaoDelirios(String alucinacaoDelirios) {
        this.alucinacaoDelirios = alucinacaoDelirios;
    }

    public String getMedicacao() {
        return medicacao;
    }

    public void setMedicacao(String medicacao) {
        this.medicacao = medicacao;
    }

    public String getAutoExterminio() {
        return autoExterminio;
    }

    public void setAutoExterminio(String autoExterminio) {
        this.autoExterminio = autoExterminio;
    }

    public String getExamesEspecificos() {
        return examesEspecificos;
    }

    public void setExamesEspecificos(String examesEspecificos) {
        this.examesEspecificos = examesEspecificos;
    }

    /**
     * @return the desmaiosConvulcoes
     */
    public String getDesmaiosConvulcoes() {
        return desmaiosConvulcoes;
    }

    /**
     * @param desmaiosConvulcoes the desmaiosConvulcoes to set
     */
    public void setDesmaiosConvulcoes(String desmaiosConvulcoes) {
        this.desmaiosConvulcoes = desmaiosConvulcoes;
    }

}
