/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.Date;

/**
 *
 * @author Mateus
 */
public class Processo {

    
    private String entrevistadoraUm;
    private String entrevistadoraDois;
    private String entrevistadoraInternamento;
    private String entrevistadoraSaida;
    private Date dataUm;
    private Date dataDois;
    private Date dataInternamento;
    private Date dataSaida;
    private String status;

    /**
     * @return the idProcesso
     */
    public Processo() {
        
        entrevistadoraUm = "";
        entrevistadoraDois = "";
        entrevistadoraInternamento = "";
        entrevistadoraSaida = "";
        dataUm = null;
        dataDois = null;
        dataSaida = null;
        dataInternamento = null;
        status = "";
    }
    
    
    

    /**
     * @return the entrevistadoraUm
     */
    public String getEntrevistadoraUm() {
        return entrevistadoraUm;
    }

    /**
     * @param entrevistadoraUm the entrevistadoraUm to set
     */
    public void setEntrevistadoraUm(String entrevistadoraUm) {
        this.entrevistadoraUm = entrevistadoraUm;
    }

    /**
     * @return the entrevistadoraDois
     */
    public String getEntrevistadoraDois() {
        return entrevistadoraDois;
    }

    /**
     * @param entrevistadoraDois the entrevistadoraDois to set
     */
    public void setEntrevistadoraDois(String entrevistadoraDois) {
        this.entrevistadoraDois = entrevistadoraDois;
    }

    /**
     * @return the entrevistadoraInternamento
     */
    public String getEntrevistadoraInternamento() {
        return entrevistadoraInternamento;
    }

    /**
     * @param entrevistadoraInternamento the entrevistadoraInternamento to set
     */
    public void setEntrevistadoraInternamento(String entrevistadoraInternamento) {
        this.entrevistadoraInternamento = entrevistadoraInternamento;
    }

    /**
     * @return the entrevistadoraSaida
     */
    public String getEntrevistadoraSaida() {
        return entrevistadoraSaida;
    }

    /**
     * @param entrevistadoraSaida the entrevistadoraSaida to set
     */
    public void setEntrevistadoraSaida(String entrevistadoraSaida) {
        this.entrevistadoraSaida = entrevistadoraSaida;
    }

    /**
     * @return the dataUm
     */
    public Date getDataUm() {
        return dataUm;
    }

    /**
     * @param dataUm the dataUm to set
     */
    public void setDataUm(Date dataUm) {
        this.dataUm = dataUm;
    }

    /**
     * @return the dataDois
     */
    public Date getDataDois() {
        return dataDois;
    }

    /**
     * @param dataDois the dataDois to set
     */
    public void setDataDois(Date dataDois) {
        this.dataDois = dataDois;
    }

    /**
     * @return the dataInternamento
     */
    public Date getDataInternamento() {
        return dataInternamento;
    }

    /**
     * @param dataInternamento the dataInternamento to set
     */
    public void setDataInternamento(Date dataInternamento) {
        this.dataInternamento = dataInternamento;
    }

    /**
     * @return the dataSaida
     */
    public Date getDataSaida() {
        return dataSaida;
    }

    /**
     * @param dataSaida the dataSaida to set
     */
    public void setDataSaida(Date dataSaida) {
        this.dataSaida = dataSaida;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
}