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
public class Dependencia {

    private String OutrasDrogas;
    private String Maconha;
    private String Alcool;
    private String Inalantes;
    private String CocainaA;
    private String CocainaI;
    private String Crack;
    private String CigarroTabaco;
    private String Comprimidos;
    private String DrogaUsadaAtual;
    private String TempoUso;

    public Dependencia() {
        OutrasDrogas = "";
        DrogaUsadaAtual = "";
        TempoUso = "";
        Maconha="";
        Alcool = "";
        Inalantes = "";
        CocainaA = "";
        CocainaI = "";
        Crack = "";
        CigarroTabaco = "";
        Comprimidos = "";

    }

    /**
     * @return the NomeDroga
     */
    public String getOutrasDrogas() {
        return OutrasDrogas;
    }

    /**
     * @param NomeDroga the NomeDroga to set
     */
    public void setOutrasDrogas(String Drogas) {
        this.OutrasDrogas = Drogas;
    }

    /**
     * @return the DrogaUsadaAtual
     */
    public String getDrogaUsadaAtual() {
        return DrogaUsadaAtual;
    }

    /**
     * @param DrogaUsadaAtual the DrogaUsadaAtual to set
     */
    public void setDrogaUsadaAtual(String DrogaUsadaAtual) {
        this.DrogaUsadaAtual = DrogaUsadaAtual;
    }

    /**
     * @return the TempoUso
     */
    public String getTempoUso() {
        return TempoUso;
    }

    /**
     * @param TempoUso the TempoUso to set
     */
    public void setTempoUso(String TempoUso) {
        this.TempoUso = TempoUso;
    }

    /**
     * @return the Maconha
     */
    public String getMaconha() {
        return Maconha;
    }

    /**
     * @param Maconha the Maconha to set
     */
    public void setMaconha(String Maconha) {
        this.Maconha = Maconha;
    }

    /**
     * @return the Alcool
     */
    public String getAlcool() {
        return Alcool;
    }

    /**
     * @param Alcool the Alcool to set
     */
    public void setAlcool(String Alcool) {
        this.Alcool = Alcool;
    }

    /**
     * @return the Inalantes
     */
    public String getInalantes() {
        return Inalantes;
    }

    /**
     * @param Inalantes the Inalantes to set
     */
    public void setInalantes(String Inalantes) {
        this.Inalantes = Inalantes;
    }

    /**
     * @return the CocainaA
     */
    public String getCocainaA() {
        return CocainaA;
    }

    /**
     * @param CocainaA the CocainaA to set
     */
    public void setCocainaA(String CocainaA) {
        this.CocainaA = CocainaA;
    }

    /**
     * @return the CocainaI
     */
    public String getCocainaI() {
        return CocainaI;
    }

    /**
     * @param CocainaI the CocainaI to set
     */
    public void setCocainaI(String CocainaI) {
        this.CocainaI = CocainaI;
    }

    /**
     * @return the Crack
     */
    public String getCrack() {
        return Crack;
    }

    /**
     * @param Crack the Crack to set
     */
    public void setCrack(String Crack) {
        this.Crack = Crack;
    }

    /**
     * @return the CigarroTabaco
     */
    public String getCigarroTabaco() {
        return CigarroTabaco;
    }

    /**
     * @param CigarroTabaco the CigarroTabaco to set
     */
    public void setCigarroTabaco(String CigarroTabaco) {
        this.CigarroTabaco = CigarroTabaco;
    }

    /**
     * @return the Comprimidos
     */
    public String getComprimidos() {
        return Comprimidos;
    }

    /**
     * @param Comprimidos the Comprimidos to set
     */
    public void setComprimidos(String Comprimidos) {
        this.Comprimidos = Comprimidos;
    }
}
