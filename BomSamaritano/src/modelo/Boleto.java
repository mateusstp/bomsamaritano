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
public class Boleto {

    private Endereco EnderecoBoleto;
    private String Responsavel;
    private String ContatoResponsavel;
    private String VencimentoBoleto;
    private String MesmoEnderecoInterno;

    public Boleto() {
        Responsavel = " ";
        ContatoResponsavel = " ";
        VencimentoBoleto = " ";
        MesmoEnderecoInterno = " ";
        EnderecoBoleto = new Endereco();
    }

    /**
     * @return the EnderecoBoleto
     */
    public Endereco getEnderecoBoleto() {
        return EnderecoBoleto;
    }

    /**
     * @param EnderecoBoleto the EnderecoBoleto to set
     */
    public void setEnderecoBoleto(Endereco EnderecoBoleto) {
        this.EnderecoBoleto = EnderecoBoleto;
    }

    /**
     * @return the Responsavel
     */
    public String getResponsavel() {
        return Responsavel;
    }

    /**
     * @param Responsavel the Responsavel to set
     */
    public void setResponsavel(String Responsavel) {
        this.Responsavel = Responsavel;
    }

    /**
     * @return the ContatoResponsavel
     */
    public String getContatoResponsavel() {
        return ContatoResponsavel;
    }

    /**
     * @param ContatoResponsavel the ContatoResponsavel to set
     */
    public void setContatoResponsavel(String ContatoResponsavel) {
        this.ContatoResponsavel = ContatoResponsavel;
    }

    /**
     * @return the VencimentoBoleto
     */
    public String getVencimentoBoleto() {
        return VencimentoBoleto;
    }

    /**
     * @param VencimentoBoleto the VencimentoBoleto to set
     */
    public void setVencimentoBoleto(String VencimentoBoleto) {
        this.VencimentoBoleto = VencimentoBoleto;
    }

    /**
     * @return the MesmoEnderecoInterno
     */
    public String getMesmoEnderecoInterno() {
        return MesmoEnderecoInterno;
    }

    /**
     * @param MesmoEnderecoInterno the MesmoEnderecoInterno to set
     */
    public void setMesmoEnderecoInterno(String MesmoEnderecoInterno) {
        this.MesmoEnderecoInterno = MesmoEnderecoInterno;
    }
}
