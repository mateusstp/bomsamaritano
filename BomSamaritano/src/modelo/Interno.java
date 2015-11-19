/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.swing.ImageIcon;

/**
 *
 * @author Mateus
 */
public class Interno {

    //Atributos
    private int Codigo;
    private int Numero;
    private String Nome;
    private String Idade;
    private Date DataNascimento;
    private String Sexo;
    private String Naturaliade;
    private String ComQuemMoraAtualmente;
    private String RG;
    private Boleto BoletoInterno;
    private String CPF;
    private String EstadoCivil;
    private String numeroCTPS;
    private String PIS;
    private String naturalidade;
    //
    private TituloEleitoral tituloEleitoral;
    //mudar para imagem
    private String fotoAntes;
    private String fotoDepois;
    /// private ImageIcon fotoAntes;
    //private ImageIcon fotoDepois;

    private Endereco endereco;
    //private Endereco enderecoBol;
    //private Endereco enderecoBoleto;
    private ArrayList<FamiliaSocial> familiaSocial;
    private Dependencia dependencia;
    private Profissao profissao;
    private Processo processo;
    private Questionario questionario;
    //private Relacionamento relacionamento;
    private Saude saude;
    private SituacaoEconomica situacaoEconomica;
    private TratamentosAnteriores tratamentosAnteriores;
    private ArrayList receptores;
//    private String Responsavel;

    public Interno() {
        receptores = new ArrayList();
        dependencia = new Dependencia();
        endereco = new Endereco();
        //enderecoBol = new Endereco();
        familiaSocial = new ArrayList<FamiliaSocial>();
        processo = new Processo();
        profissao = new Profissao();
        questionario = new Questionario();
        saude = new Saude();
        tituloEleitoral = new TituloEleitoral();
        tratamentosAnteriores = new TratamentosAnteriores();
        situacaoEconomica = new SituacaoEconomica();
        BoletoInterno = new Boleto();
        Sexo = "M";
        Codigo = 0;
        Numero = 0;
        fotoAntes = null;
        fotoDepois = null;               
    }

    public void inicializaModelo() {

        Codigo = 0;
        Numero = 0;
        Nome = "teste";
        Idade = "0";
        DataNascimento = null;
        setNaturaliade("teste");
        RG = "teste";
        CPF = "teste";
        EstadoCivil = "Solteito";
        numeroCTPS = "teste";
        PIS = "teste";
        tituloEleitoral.setNumero("numero");
        tituloEleitoral.setSecao("secao");
        tituloEleitoral.setZona("zona");

    }

    public void verificaModelo() {
        //if(Codigo)Codigo=0;

        if (Nome == null) {
            Nome = "";
        }
        //private int Idade;
        if (DataNascimento == null) {
            //DataNascimento = "";
        }
        if (Sexo == null) {
            Sexo = "";
        }
        if (Naturaliade == null) {
            Naturaliade = "";
        }
        if (ComQuemMoraAtualmente == null) {
            ComQuemMoraAtualmente = "";
        }
        if (RG == null) {
            RG = "";
        }
        if (CPF == null) {
            CPF = "";
        }
        if (EstadoCivil == null) {
            EstadoCivil = "";
        }
        if (numeroCTPS == null) {
            numeroCTPS = "";
        }
        if (PIS == null) {
            PIS = "";
        }
        if (naturalidade == null) {
            naturalidade = "";
        }
        if (fotoAntes == null) {
            fotoAntes = "";
        }
        if (fotoAntes == null) {
            fotoAntes = "";
        }

        verificaBoleto();
        verificaTituloEleitoral();
        verificaEndereco();
        verificaFamiliaSocial();
        verificaDependencia();
        verificaProfissao();
        verificaProcesso();
        verificaQuestionario();
        verificaSaude();
        verificaSituacaoEconomica();
        verificaTratamentosAnteriores();
        
    }
    
    public void verificaTratamentosAnteriores(){
        if(tratamentosAnteriores.getClinica()==null)tratamentosAnteriores.setClinica("");
       // if(tratamentosAnteriores.getDataEntrada()==null)tratamentosAnteriores.setClinica("");
        //if(tratamentosAnteriores.getDataSaida()==null)tratamentosAnteriores.setClinica("");
        if(tratamentosAnteriores.getMotivoSaida()==null)tratamentosAnteriores.setClinica("");
    }
    public void verificaSituacaoEconomica (){
        if(situacaoEconomica.getAjudaFinanceira()==null)situacaoEconomica.setAjudaFinanceira("");
        if(situacaoEconomica.getRendaFamilia()==null)situacaoEconomica.setAjudaFinanceira("");
        if(situacaoEconomica.getRendaFamiliaSeparados()==null)situacaoEconomica.setAjudaFinanceira("");
        if(situacaoEconomica.getRendaPessoal()==null)situacaoEconomica.setAjudaFinanceira("");
    }
    public void verificaSaude(){
        if(saude.getAlimentacao()==null)saude.setAlimentacao("");
        if(saude.getSono()==null)saude.setSono("");
        if(saude.getMedicacao()==null)saude.setMedicacao("");
        if(saude.getExamesEspecificos()==null)saude.setExamesEspecificos("");
        if(saude.getDesmaiosConvulcoes()==null)saude.setDesmaiosConvulcoes("");
        if(saude.getAutoExterminio()==null)saude.setAutoExterminio("");
        if(saude.getAlucinacaoDelirios()==null)saude.setAlucinacaoDelirios("");
        
    }
    
    public void   verificaQuestionario(){
        if(questionario.getConhecimentoColonia()==null)questionario.setConhecimentoColonia("");
        if(questionario.getSexualidade()==null)questionario.setSexualidade("");
        if(questionario.getRelacionamentoSocial()==null)questionario.setRelacionamentoSocial("");
        if(questionario.getProcessoJustica()==null)questionario.setProcessoJustica("");
        if(questionario.getPossuiRelacionamentoAfetivo()==null)questionario.setPossuiRelacionamentoAfetivo("");
        if(questionario.getObservacoes()==null)questionario.setObservacoes("");
        if(questionario.getMotivoUsarDrogas()==null)questionario.setMotivoUsarDrogas("");
        if(questionario.getGrupoApoio()==null)questionario.setGrupoApoio("");
        if(questionario.getExpectativaTratamento()==null)questionario.setExpectativaTratamento("");
    }
    
    public void verificaProcesso(){
        /*if(processo.getDataUm()==null)processo.setDataUm("");
        if(processo.getDataDois()==null)processo.setDataDois("");
        if(processo.getDataSaida()==null)processo.setDataSaida("");
        if(processo.getDataInternamento()==null)processo.setDataInternamento("");*/
        if(processo.getEntrevistadoraUm()==null)processo.setEntrevistadoraUm("");
        if(processo.getEntrevistadoraDois()==null)processo.setEntrevistadoraDois("");
        if(processo.getEntrevistadoraInternamento()==null)processo.setEntrevistadoraInternamento("");
        if(processo.getEntrevistadoraSaida()==null)processo.setEntrevistadoraSaida("");
    }
    public void verificaProfissao(){
        if(profissao.getAfastadoPS()==null)profissao.setAfastadoPS("");
        if(profissao.getTrabalhoCA()==null)profissao.setTrabalhoCA("");
        if(profissao.getTrabalhoAtual()==null)profissao.setTrabalhoAtual("");
        if(profissao.getSalarioDesemprego()==null)profissao.setSalarioDesemprego("");
        if(profissao.getEscolaridade()==null)profissao.setEscolaridade("");
        if(profissao.getAposentado()==null)profissao.setAposentado("");
    }
    
    public void verificaDependencia(){
        if(dependencia.getAlcool()==null)dependencia.setAlcool("");
        if(dependencia.getTempoUso()==null)dependencia.setTempoUso("");
        if(dependencia.getOutrasDrogas()==null)dependencia.setOutrasDrogas("");
        if(dependencia.getMaconha()==null)dependencia.setMaconha("");
        if(dependencia.getInalantes()==null)dependencia.setInalantes("");
        if(dependencia.getDrogaUsadaAtual()==null)dependencia.setDrogaUsadaAtual("");
        if(dependencia.getCrack()==null)dependencia.setCrack("");
        if(dependencia.getComprimidos()==null)dependencia.setComprimidos("");
        if(dependencia.getCocainaI()==null)dependencia.setCocainaI("");
        if(dependencia.getCocainaA()==null)dependencia.setCocainaA("");
        if(dependencia.getCigarroTabaco()==null)dependencia.setCigarroTabaco("");
    }
    public void verificaFamiliaSocial(){
        
                Iterator<FamiliaSocial> it = familiaSocial.iterator();
        while (it.hasNext()) {
            FamiliaSocial f = it.next();
            if(f.getCasoDrogas()==null)f.setCasoDrogas("");
            if(f.getTempoCasado()==null)f.setTempoCasado("");
            if(f.getSalarioPessoal()==null)f.setSalarioPessoal("");
            if(f.getSalarioFamiliarSeparados()==null)f.setSalarioFamiliarSeparados("");
            if(f.getSalarioFamiliar()==null)f.setSalarioFamiliar("");
            if(f.getRelacionamentoFamiliar()==null)f.setRelacionamentoFamiliar("");
            if(f.getProfissao()==null)f.setProfissao("");
            if(f.getParantesco()==null)f.setParantesco("");
            if(f.getNome()==null)f.setNome("");
            if(f.getFalecido()==null)f.setFalecido("");
            if(f.getEstadoCivil()==null)f.setEstadoCivil("");
                        
        }
        
    }

    public void verificaEndereco(){
        if (endereco.getBairro() == null) {
            endereco.setBairro("");
        }
        if (endereco.getCEP() == null) {
            endereco.setCEP("");
        }
        if (endereco.getCidade() == null) {
            endereco.setCidade("");
        }
        if (endereco.getEstado() == null) {
            endereco.setEstado("");
        }
        if (endereco.getComplemento() == null) {
            endereco.setComplemento("");
        }
        if (endereco.getRua() == null) {
            endereco.setRua("");
        }
        if (endereco.getTelefone() == null) {
            endereco.setTelefone("");
        }

    }
    public void verificaTituloEleitoral() {
        if(tituloEleitoral.getNumero()==null)tituloEleitoral.setNumero("0");
        if(tituloEleitoral.getSecao()==null)tituloEleitoral.setSecao("0");
        if(tituloEleitoral.getZona()==null)tituloEleitoral.setZona("0");
    }

    public void verificaBoleto() {

        if (BoletoInterno.getContatoResponsavel() == null) {
            BoletoInterno.setContatoResponsavel("");
        }
        if (BoletoInterno.getEnderecoBoleto().getBairro() == null) {
            BoletoInterno.getEnderecoBoleto().setBairro("");
        }
        if (BoletoInterno.getEnderecoBoleto().getCEP() == null) {
            BoletoInterno.getEnderecoBoleto().setCEP("");
        }
        if (BoletoInterno.getEnderecoBoleto().getCidade() == null) {
            BoletoInterno.getEnderecoBoleto().setCidade("");
        }
        if (BoletoInterno.getEnderecoBoleto().getEstado() == null) {
            BoletoInterno.getEnderecoBoleto().setEstado("");
        }
        if (BoletoInterno.getEnderecoBoleto().getComplemento() == null) {
            BoletoInterno.getEnderecoBoleto().setComplemento("");
        }
        if (BoletoInterno.getEnderecoBoleto().getRua() == null) {
            BoletoInterno.getEnderecoBoleto().setRua("");
        }
        if (BoletoInterno.getEnderecoBoleto().getTelefone() == null) {
            BoletoInterno.getEnderecoBoleto().setTelefone("");
        }

        if (BoletoInterno.getMesmoEnderecoInterno() == null) {
            BoletoInterno.setMesmoEnderecoInterno("SIM");
        }
        if (BoletoInterno.getVencimentoBoleto() == null) {
            BoletoInterno.setVencimentoBoleto("00/00/0000");
        }

        if (BoletoInterno.getResponsavel() == null) {
            BoletoInterno.setResponsavel("");
        }

    }

    /**
     * }
     *
     * @return the Codigo
     */
    public int getCodigo() {
        return Codigo;
    }

    /**
     * @param Codigo the Codigo to set
     */
    public void setCodigo(int Codigo) {
        this.Codigo = Codigo;
    }

    /**
     * @return the Nome
     */
    public String getNome() {
        return Nome;
    }

    /**
     * @param Nome the Nome to set
     */
    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    /**
     * @return the Idade
     */
    public String getIdade() {
        return Idade;
    }

    /**
     * @param Idade the Idade to set
     */
    public void setIdade(String Idade) {
        this.Idade = Idade;
    }

    /**
     * @return the DataNascimento
     */
    public Date getDataNascimento() {
        return DataNascimento;
    }

    /**
     * @param DataNascimento the DataNascimento to set
     */
    public void setDataNascimento(Date DataNascimento) {
        
        this.DataNascimento = DataNascimento;
    }

    /**
     * @return the RG
     */
    public String getRG() {
        return RG;
    }

    /**
     * @param RG the RG to set
     */
    public void setRG(String RG) {
        this.RG = RG;
    }

    /**
     * @return the CPF
     */
    public String getCPF() {
        return CPF;
    }

    /**
     * @param CPF the CPF to set
     */
    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    /**
     * @return the EstadoCivil
     */
    public String getEstadoCivil() {
        return EstadoCivil;
    }

    /**
     * @param EstadoCivil the EstadoCivil to set
     */
    public void setEstadoCivil(String EstadoCivil) {
        this.EstadoCivil = EstadoCivil;
    }

    /**
     * @return the numeroCTPS
     */
    public String getNumeroCTPS() {
        return numeroCTPS;
    }

    /**
     * @param numeroCTPS the numeroCTPS to set
     */
    public void setNumeroCTPS(String numeroCTPS) {
        this.numeroCTPS = numeroCTPS;
    }

    /**
     * @return the PIS
     */
    public String getPIS() {
        return PIS;
    }

    /**
     * @param PIS the PIS to set
     */
    public void setPIS(String PIS) {
        this.PIS = PIS;
    }

    /**
     * @return the tituloEleitoral
     */
    public TituloEleitoral getTituloEleitoral() {
        return tituloEleitoral;
    }

    /**
     * @param tituloEleitoral the tituloEleitoral to set
     */
    public void setTituloEleitoral(TituloEleitoral tituloEleitoral) {
        this.tituloEleitoral = tituloEleitoral;
    }

    /**
     * @return the endereco
     */
    public Endereco getEndereco() {
        return endereco;
    }

    /**
     * @param endereco the endereco to set
     */
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    /**
     * @return the familiaSocial
     */
    /*    public FamiliaSocial getFamiliaSocial() {
     return familiaSocial;
     }

     /**
     * @param familiaSocial the familiaSocial to set
     */
    /*public void setFamiliaSocial(FamiliaSocial familiaSocial) {
     this.familiaSocial = familiaSocial;
     }

     /**
     * @return the denpendencia
     */
    public Dependencia getDependencia() {
        return dependencia;
    }

    /**
     * @param denpendencia the denpendencia to set
     */
    public void setDependencia(Dependencia dependencia) {
        this.dependencia = dependencia;
    }

    /**
     * @return the profissao
     */
    public Profissao getProfissao() {
        return profissao;
    }

    /**
     * @param profissao the profissao to set
     */
    public void setProfissao(Profissao profissao) {
        this.profissao = profissao;
    }

    /**
     * @return the processo
     */
    public Processo getProcesso() {
        return processo;
    }

    /**
     * @param processo the processo to set
     */
    public void setProcesso(Processo processo) {
        this.processo = processo;
    }

    /**
     * @return the questionario
     */
    public Questionario getQuestionario() {
        return questionario;
    }

    /**
     * @param questionario the questionario to set
     */
    public void setQuestionario(Questionario questionario) {
        this.questionario = questionario;
    }

    /**
     * @return the saude
     */
    public Saude getSaude() {
        return saude;
    }

    /**
     * @param saude the saude to set
     */
    public void setSaude(Saude saude) {
        this.saude = saude;
    }

    /**
     * @return the situacaoEconomica
     */
    public SituacaoEconomica getSituacaoEconomica() {
        return situacaoEconomica;
    }

    /**
     * @param situacaoEconomica the situacaoEconomica to set
     */
    public void setSituacaoEconomica(SituacaoEconomica situacaoEconomica) {
        this.situacaoEconomica = situacaoEconomica;
    }

    /**
     * @return the tratamentosAnteriores
     */
    public TratamentosAnteriores getTratamentosAnteriores() {
        return tratamentosAnteriores;
    }

    /**
     * @param tratamentosAnteriores the tratamentosAnteriores to set
     */
    public void setTratamentosAnteriores(TratamentosAnteriores tratamentosAnteriores) {
        this.tratamentosAnteriores = tratamentosAnteriores;
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
     * @return the naturalidade
     */
    public String getNaturalidade() {
        return naturalidade;
    }

    /**
     * @param naturalidade the naturalidade to set
     */
    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

    /**
     * @return the familiaSocial
     */
    public ArrayList<FamiliaSocial> getFamiliaSocial() {
        return familiaSocial;
    }

    /**
     * @param familiaSocial the familiaSocial to set
     */
    public void setFamiliaSocial(ArrayList<FamiliaSocial> familiaSocial) {
        this.familiaSocial = familiaSocial;
    }

    /**
     * @return the fotoAntes
     */
    public String getFotoAntes() {
        return fotoAntes;
    }

    /**
     * @param fotoAntes the fotoAntes to set
     */
    public void setFotoAntes(String fotoAntes) {
        this.fotoAntes = fotoAntes;
    }

    /**
     * @return the fotoDepois
     */
    public String getFotoDepois() {
        return fotoDepois;
    }

    /**
     * @param fotoDepois the fotoDepois to set
     */
    public void setFotoDepois(String fotoDepois) {
        this.fotoDepois = fotoDepois;
    }

    /**
     * @return the Sexo
     */
    public String getSexo() {
        return Sexo;
    }

    /**
     * @param Sexo the Sexo to set
     */
    public void setSexo(String Sexo) {
        this.Sexo = Sexo;
    }

    /**
     * @return the Naturaliade
     */
    public String getNaturaliade() {
        return Naturaliade;
    }

    /**
     * @param Naturaliade the Naturaliade to set
     */
    public void setNaturaliade(String Naturaliade) {
        this.Naturaliade = Naturaliade;
    }

    /**
     * @return the ComQuemMora
     */
    public String getComQuemMoraAtualmente() {
        return ComQuemMoraAtualmente;
    }

    /**
     * @param ComQuemMora the ComQuemMora to set
     */
    public void setComQuemMoraAtualmente(String ComQuemMora) {
        this.ComQuemMoraAtualmente = ComQuemMora;
    }

    /**
     * @return the BoletoInterno
     */
    public Boleto getBoletoInterno() {
        return BoletoInterno;
    }

    /**
     * @param BoletoInterno the BoletoInterno to set
     */
    public void setBoletoInterno(Boleto BoletoInterno) {
        this.BoletoInterno = BoletoInterno;
    }

    /**
     * @return the Numero
     */
    public int getNumero() {
        return Numero;
    }

    /**
     * @param Numero the Numero to set
     */
    public void setNumero(int Numero) {
        this.Numero = Numero;
    }
}
