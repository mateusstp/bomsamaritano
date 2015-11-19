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
public class SituacaoEconomica {

    private String rendaFamilia;
    private String rendaFamiliaSeparados;
    private String rendaPessoal;
    private String ajudaFinanceira;

    public SituacaoEconomica() {
        rendaFamilia = "";
        rendaFamiliaSeparados = "";
        rendaPessoal = "";
        ajudaFinanceira = "";
    }


    /**
     * @return the rendaFamilia
     */
    public String getRendaFamilia() {
        return rendaFamilia;
    }

    /**
     * @param rendaFamilia the rendaFamilia to set
     */
    public void setRendaFamilia(String rendaFamilia) {
        this.rendaFamilia = rendaFamilia;
    }

    /**
     * @return the rendaFamiliaSeparados
     */
    public String getRendaFamiliaSeparados() {
        return rendaFamiliaSeparados;
    }

    /**
     * @param rendaFamiliaSeparados the rendaFamiliaSeparados to set
     */
    public void setRendaFamiliaSeparados(String rendaFamiliaSeparados) {
        this.rendaFamiliaSeparados = rendaFamiliaSeparados;
    }

    /**
     * @return the rendaPessoal
     */
    public String getRendaPessoal() {
        return rendaPessoal;
    }

    /**
     * @param rendaPessoal the rendaPessoal to set
     */
    public void setRendaPessoal(String rendaPessoal) {
        this.rendaPessoal = rendaPessoal;
    }

    /**
     * @return the ajudaFinanceira
     */
    public String getAjudaFinanceira() {
        return ajudaFinanceira;
    }

    /**
     * @param ajudaFinanceira the ajudaFinanceira to set
     */
    public void setAjudaFinanceira(String ajudaFinanceira) {
        this.ajudaFinanceira = ajudaFinanceira;
    }
}
