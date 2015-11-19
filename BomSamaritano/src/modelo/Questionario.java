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
public class Questionario {

    private String ConhecimentoColonia;
    private String expectativaTratamento;
    private String motivoUsarDrogas;
    private String processoJustica;
    private String sexualidade;
    private String possuiRelacionamentoAfetivo;
    private String grupoApoio;
    private String relacionamentoSocial;
    private String observacoes;

    public Questionario() {
        ConhecimentoColonia = "";
        expectativaTratamento = "";
        sexualidade = "";
        processoJustica = "";
        observacoes = "";
        relacionamentoSocial="";
       grupoApoio ="";
        possuiRelacionamentoAfetivo="";
        motivoUsarDrogas="";

    }

 

    /**
     * @return the ConhecimentoColonia
     */
    public String getConhecimentoColonia() {
        return ConhecimentoColonia;
    }

    /**
     * @param ConhecimentoColonia the ConhecimentoColonia to set
     */
    public void setConhecimentoColonia(String ConhecimentoColonia) {
        this.ConhecimentoColonia = ConhecimentoColonia;
    }

    /**
     * @return the expextativaTratamento
     */
    public String getExpectativaTratamento() {
        return expectativaTratamento;
    }

    /**
     * @param expectativaTratamento the expextativaTratamento to set
     */
    public void setExpectativaTratamento(String expectativaTratamento) {
        this.expectativaTratamento = expectativaTratamento;
    }



    /**
     * @return the sexualidade
     */
    public String getSexualidade() {
        return sexualidade;
    }

    /**
     * @param sexualidade the sexualidade to set
     */
    public void setSexualidade(String sexualidade) {
        this.sexualidade = sexualidade;
    }

    /**
     * @return the processoJustica
     */
    public String getProcessoJustica() {
        return processoJustica;
    }

    /**
     * @param processoJustica the processoJustica to set
     */
    public void setProcessoJustica(String processoJustica) {
        this.processoJustica = processoJustica;
    }

    /**
     * @return the observacoes
     */
    public String getObservacoes() {
        return observacoes;
    }

    /**
     * @param observacoes the observacoes to set
     */
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    /**
     * @return the motivoUsarDrogas
     */
    public String getMotivoUsarDrogas() {
        return motivoUsarDrogas;
    }

    /**
     * @param motivoUsarDrogas the motivoUsarDrogas to set
     */
    public void setMotivoUsarDrogas(String motivoUsarDrogas) {
        this.motivoUsarDrogas = motivoUsarDrogas;
    }

    /**
     * @return the possuiRelacionamentoAfetivo
     */
    public String getPossuiRelacionamentoAfetivo() {
        return possuiRelacionamentoAfetivo;
    }

    /**
     * @param possuiRelacionamentoAfetivo the possuiRelacionamentoAfetivo to set
     */
    public void setPossuiRelacionamentoAfetivo(String possuiRelacionamentoAfetivo) {
        this.possuiRelacionamentoAfetivo = possuiRelacionamentoAfetivo;
    }

    /**
     * @return the participaGrupoApoio
     */
    public String getGrupoApoio() {
        return grupoApoio;
    }

    /**
     * @param participaGrupoApoio the participaGrupoApoio to set
     */
    public void setGrupoApoio(String participaGrupoApoio) {
        this.grupoApoio = participaGrupoApoio;
    }

    /**
     * @return the relacionamentoSocial
     */
    public String getRelacionamentoSocial() {
        return relacionamentoSocial;
    }

    /**
     * @param relacionamentoSocial the relacionamentoSocial to set
     */
    public void setRelacionamentoSocial(String relacionamentoSocial) {
        this.relacionamentoSocial = relacionamentoSocial;
    }

}
