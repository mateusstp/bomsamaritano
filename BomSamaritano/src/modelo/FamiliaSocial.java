/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;

/**
 *
 * @author Mateus
 */
public class FamiliaSocial {

    //transformar em um array list
    private int id;
    private String nome;
    private String parantesco;
    private String tempoCasado;
    private String idade;
    private String EstadoCivil;
    private String profissao;
    private String casoDrogas;
    private String relacionamentoFamiliar;
    private String falecido;
//    private String CPF;
    private String salarioPessoal;
    private String salarioFamiliar;
    private String salarioFamiliarSeparados;

    
    public FamiliaSocial(String n,String i,String p, String c,String t,String e, String pr,String r,String f  ) {
        
        nome = n;
        idade = i;
        parantesco = p;
        casoDrogas = c;
        tempoCasado =t;
        EstadoCivil = e;
        profissao = pr;
        relacionamentoFamiliar = r;
        falecido = f;
        salarioPessoal = "";
        salarioFamiliar = "";
        salarioFamiliarSeparados = "";
    }    

//Acho que esta faltando a variavel "RelacionemtnoFamiliar" (shibill)
    public FamiliaSocial() {
        parantesco = "";
        nome = "";
        casoDrogas = "";
        idade = "";
        profissao = "";
//        responsavel = "";
//        CPF = "";
        EstadoCivil = "";
        relacionamentoFamiliar = "";
        tempoCasado = "";
        salarioPessoal = "";
        salarioFamiliar = "";
        salarioFamiliarSeparados = "";
    }

    public void adiconaPergunta() {

    }

    public void RemovePergunta() {

    }

    public String getEstadoCivil() {
        return EstadoCivil;
    }

    public void setEstadoCivil(String EstadoCivil) {
        this.EstadoCivil = EstadoCivil;
    }



    /**
     * @return the parantesco
     */
    public String getParantesco() {
        return parantesco;
    }

    /**
     * @param parantesco the parantesco to set
     */
    public void setParantesco(String parantesco) {
        this.parantesco = parantesco;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    

    /**
     * @return the idade
     */
    public String getIdade() {
        return idade;
    }

    /**
     * @param idade the idade to set
     */
    public void setIdade(String idade) {
        this.idade = idade;
    }

    /**
     * @return the profissao
     */
    public String getProfissao() {
        return profissao;
    }

    /**
     * @param profissao the profissao to set
     */
    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    
  

    /**
     * @return the salarioPessoal
     */
    public String getSalarioPessoal() {
        return salarioPessoal;
    }

    /**
     * @param salarioPessoal the salarioPessoal to set
     */
    public void setSalarioPessoal(String salarioPessoal) {
        this.salarioPessoal = salarioPessoal;
    }

    /**
     * @return the salarioFamiliar
     */
    public String getSalarioFamiliar() {
        return salarioFamiliar;
    }

    /**
     * @param salarioFamiliar the salarioFamiliar to set
     */
    public void setSalarioFamiliar(String salarioFamiliar) {
        this.salarioFamiliar = salarioFamiliar;
    }

    /**
     * @return the relacionamentoFamiliar
     */
    public String getRelacionamentoFamiliar() {
        return relacionamentoFamiliar;
    }

    /**
     * @param relacionamentoFamiliar the relacionamentoFamiliar to set
     */
    public void setRelacionamentoFamiliar(String relacionamentoFamiliar) {
        this.relacionamentoFamiliar = relacionamentoFamiliar;
    }

    /**
     * @return the salarioFamiliarSeparados
     */
    public String getSalarioFamiliarSeparados() {
        return salarioFamiliarSeparados;
    }

    /**
     * @param salarioFamiliarSeparados the salarioFamiliarSeparados to set
     */
    public void setSalarioFamiliarSeparados(String salarioFamiliarSeparados) {
        this.salarioFamiliarSeparados = salarioFamiliarSeparados;
    }

    /**
     * @return the tempoCasado
     */
    public String getTempoCasado() {
        return tempoCasado;
    }

    /**
     * @param tempoCasado the tempoCasado to set
     */
    public void setTempoCasado(String tempoCasado) {
        this.tempoCasado = tempoCasado;
    }

    /**
     * @return the casoDrogas
     */
    public String getCasoDrogas() {
        return casoDrogas;
    }

    /**
     * @param casoDrogas the casoDrogas to set
     */
    public void setCasoDrogas(String casoDrogas) {
        this.casoDrogas = casoDrogas;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the falecido
     */
    public String getFalecido() {
        return falecido;
    }

    /**
     * @param falecido the falecido to set
     */
    public void setFalecido(String falecido) {
        this.falecido = falecido;
    }
}
