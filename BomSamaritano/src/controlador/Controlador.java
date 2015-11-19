/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DAO.*;
import Relatorios.Relatorios;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.table.DefaultTableModel;
import modelo.FamiliaSocial;
import modelo.Interno;
import visao.SeletorArquivos;
import visao.Visao;

/**
 *
 * @author Mateus
 */
public class Controlador implements ActionListener {
/**versao agira*/
    private Visao visao;
    private Interno modelo;
    private SeletorArquivos seletorfoto;
    private BancoDAO bancoDAO;
    ImageIcon logo;
    private Relatorios relatorios;

    public Controlador(Visao visao, Interno modelo) {
        this.visao = visao;
        this.modelo = modelo;
        relatorios = new Relatorios();
    }

    public void actionPerformed(ActionEvent e) {
        //To change body of generated methods, choose Tools | Templates.
        String comando = e.getActionCommand();
        float zero = 0;
        if (comando.equals(visao.getNOVO())) {
            // visao.testeVisao();
            visao.habilitarAbaPrincipal();
            visao.habilitaTodosCampos();
            visao.desabilitaNovo();
            visao.desabilitaExcluir();
            visao.desabilitaPesquisar();

            //visao.desabilitaFechar();
            visao.habilitaBotoes();
            visao.desabilitaBotoesFamilia();
            visao.desabilitaCampos(visao.getjPFamiliaeSocial());
            visao.limpaTodosCampos();
          //  visao.ini(); //linha para teste
            visao.setDiretorio_FOTO_ANTES(visao.getDiretorioFotoPadrao());
            visao.setDiretorio_FOTO_DEPOIS(visao.getDiretorioFotoPadrao());
            //visao.setarCamposInteiros();

            visao.getjRBEnderecoBoletoNao().setSelected(true);
        } else if (comando.equals(visao.getCADASTRAR())) {
            try {
                modelo = new Interno();
                FamiliaSocial fs = new FamiliaSocial();

                bancoDAO = new BancoDAO(modelo);
                atualizaModelo();
                /* if (atualizaModelo() == 1) {
                 return;
                 }*/
                //modelo.getFamiliaSocial().add(fs);
                modelo.verificaModelo();
                bancoDAO.salvar();
                visao.msgCadastroEfetuado();
                visao.limpaTodosCampos();
                visao.desabilitaTodosCampos();
                visao.habilitaNovo();
                visao.habilitaPesquisar();
                visao.desabilitaBotoes();
            } catch (BancoDAOException ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (comando.equals(visao.getALTERAR())) {
            try {
                modelo = new Interno();
                atualizaModelo();
                /*if (atualizaModelo() == 1) {
                 return;
                 }*/
                // atualizaIDFamiliaAlterar();
                bancoDAO = new BancoDAO(modelo);
                modelo.verificaModelo();
                bancoDAO.excluirFamiliares(visao.getFamiliarExcluido());
                bancoDAO.atualizar(modelo.getCodigo());
            } catch (BancoDAOException ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }

            /*try {
             cadastroAbaInterno();
             } catch (BancoDAOException ex) {
             Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
             }
             try {
             cadastroAbaDependencia();
             } catch (BancoDAOException ex) {
             Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
             }
             try {
             cadastroAbaQuestionario();
             } catch (BancoDAOException ex) {
             Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
             }
             try {
             cadastroAbaOutrasInformacoes();
             } catch (BancoDAOException ex) {
             Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
             }
             try {
             cadastroAbaFamilia();
             } catch (BancoDAOException ex) {
             Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
             }*/
            visao.msgAlteracaoEfetuada();
            visao.limpaTodosCampos();
            visao.desabilitaTodosCampos();
            visao.desabilitaAlterar();
            //visao.habilitaNovo();
            //1visao.desabilitaBotoes();
            visao.limpaTabela(visao.getjTPesquisa());
            visao.desabilitaCancelar();
            visao.desabilitaExcluir();
            visao.habilitaNovo();
            visao.habilitaPesquisar();

        } else if (comando.equals(visao.getCANCELAR())) {
            visao.msgBotaoCancelar();
            visao.limpaTodosCampos();
            visao.desabilitaTodosCampos();
            visao.habilitaNovo();
            //visao.habilitarAbaPesquisa();
            visao.limpaTabela(visao.getjTPesquisa());
            //visao.habilitaPesquisar();

        } else if (comando.equals(visao.getPESQUISAR())) {
            visao.limpaTodosCampos();
            visao.getjRBNome().setSelected(true);
            visao.getjTFProcurar().requestFocus();
            visao.desabilitaTodosCampos();
            visao.habilitarAbaPesquisa();
            visao.habilitaCamposPesquisa();
            visao.desabilitaNovo();
            visao.desabilitaCadastrar();
            visao.desabilitaBotoesAbaPesquisar();

        } else if (comando.equals(visao.getENDERECOSIM())) {
            visao.desabilitaCampos(visao.getjPEnderecoBoleto());
            //pegando tudo do Panel endereço
            visao.getjFTRuaBoleto().setText(visao.getjFTRua().getText());
            visao.getjFTNumeroBoleto().setText(visao.getjFTNumero().getText());
            visao.getjFTBairroBoleto().setText(visao.getjFTBairro().getText());
            visao.getjFTComplementoBoleto().setText(visao.getjFTComplemento().getText());
            visao.getjFTCEPBoleto().setText(visao.getjFTCEP().getText());
            visao.getjFTTelefoneBoleto().setText(visao.getjFTTelefone().getText());
            visao.getjFTCidadeBoleto().setText(visao.getjFTCidade().getText());
            visao.getjCBUFBoleto().setSelectedItem((String) visao.getjCBUF().getSelectedItem());
        } else if (comando.equals(visao.getENDERECONAO())) {
            visao.habilitaCampos(visao.getjPEnderecoBoleto());
            //pegando tudo do Panel endereço
            /*visao.getjFTRuaBoleto().setText(visao.getjFTRua().getText());
             visao.getjFTNumeroBoleto().setText(visao.getjFTNumero().getText());
             visao.getjFTBairroBoleto().setText(visao.getjFTBairro().getText());
             visao.getjFTComplementoBoleto().setText(visao.getjFTComplemento().getText());
             visao.getjFTCEPBoleto().setText(visao.getjFTCEP().getText());
             visao.getjFTTelefoneBoleto().setText(visao.getjFTTelefone().getText());
             visao.getjFTCidadeBoleto().setText(visao.getjFTCidade().getText());
             visao.getjCBUFBoleto().setSelectedItem((String) visao.getjCBUF().getSelectedItem());*/
        } else if (comando.equals(visao.getFECHAR())) {
            visao.msgFechar();

        } else if (comando.equals(visao.getEXCLUIR())) {
            if (visao.getjTPesquisa().getSelectedRowCount() > 1) {
                visao.msgExcluirApenasUm();
                //visao.
                return;
            }
            if (visao.msgDesjaExcluir() == 0) {
                modelo = new Interno();
                try {
                    modelo = visao.getInterno().get(visao.getjTPesquisa().getSelectedRow());
                    bancoDAO = new BancoDAO(modelo);
                    modelo.verificaModelo();
                    bancoDAO.excluir(modelo.getCodigo());
                } catch (BancoDAOException ex) {
                    Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            visao.limpaTodosCampos();
            visao.removeLinhaPesquisa(visao.getjTPesquisa().getSelectedRow());
            visao.desabilitaAlterar();
            visao.desabilitaCancelar();
            visao.desabilitaExcluir();
            visao.habilitaNovo();
            visao.habilitaPesquisar();
            visao.limpaTabela(visao.getjTPesquisa());
            visao.habilitarAbaPrincipal();
            visao.desabilitaTodosCampos();

        } else if (comando.equals(visao.getALTERAR_FOTO_ANTES())) {
            seletorfoto = new SeletorArquivos();
            int returnVal = seletorfoto.getjFCSeletorFoto().showOpenDialog(seletorfoto.getjFCSeletorFoto());

            if (returnVal == JFileChooser.APPROVE_OPTION) {

                File file = seletorfoto.getjFCSeletorFoto().getSelectedFile();
                ImageIcon im = new ImageIcon(file.getPath());
                visao.setDiretorio_FOTO_ANTES(file.getPath());
                visao.getjLFotoAntes().setIcon(visao.redimensinarImagem(file.getPath(), visao.getjLFotoAntes().getWidth() - 3, visao.getjLFotoAntes().getHeight() - 3));
            } else {
                //JOptionPane.showMessageDialog(null,"Erro ao procurar a foto.");
            }

        } else if (comando.equals(visao.getALTERAR_FOTO_DEPOIS())) {

            seletorfoto = new SeletorArquivos();
            int returnVal = seletorfoto.getjFCSeletorFoto().showOpenDialog(seletorfoto.getjFCSeletorFoto());

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = seletorfoto.getjFCSeletorFoto().getSelectedFile();
                visao.setDiretorio_FOTO_DEPOIS(file.getPath());
                ImageIcon im = new ImageIcon(file.getPath());
                visao.getjLFotoDepois().setIcon(visao.redimensinarImagem(file.getPath(), visao.getjLFotoDepois().getWidth() - 3, visao.getjLFotoDepois().getHeight() - 3));
                // visao.getjLFotoDepois().setIcon(visao.redimensinarImagem(im, visao.getjLFotoDepois().getWidth(), visao.getjLFotoDepois().getHeight()));
            } else {
                //JOptionPane.showMessageDialog(null,"Erro ao procurar a foto.");
            }

        } else if (comando.equals(visao.getPROCURAR())) {
            try {
                visao.limpaTabela(visao.getjTPesquisa());

                try {
                    bancoDAO = new BancoDAO(modelo);
                } catch (BancoDAOException ex) {
                    Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (visao.getjRBCPF().isSelected()) {
                    if (visao.getjTFProcurar().getText().length() < 1) {
                        visao.msgBuscaInvalida();
                        visao.habilitaNovo();
                        visao.habilitaPesquisar();
                        visao.desabilitaAlterar();
                        visao.desabilitaCancelar();
                        visao.desabilitaExcluir();
                        return;

                    }
                    //Interno interno = bancoDAO.pesquisarID(visao.getjTFProcurar().getText());
                    Interno interno = bancoDAO.pesquisarCPF(visao.getjTFProcurar().getText());
                } else if (visao.getjRBNome().isSelected()) {
                    try {

                        String teste = visao.getjTFProcurar().getText();
                        if (teste.length() < 1) {
                            visao.msgBuscaInvalida();
                            visao.habilitaNovo();
                            visao.habilitaPesquisar();
                            visao.desabilitaAlterar();
                            visao.desabilitaCancelar();
                            visao.desabilitaExcluir();
                            return;
                        }

                        ArrayList<Interno> interno = bancoDAO.pesquisar(teste);

                        if (interno == null) {
                            visao.msgResgistroNaoEncontrado();
                            visao.habilitarAbaPesquisa();
                            return;
                        }
                        visao.habilitaAlterar();
                        visao.habilitaExcluir();
                        visao.habilitaCancelar();
                        visao.desabilitaNovo();
                        visao.addLinhaPesquisa(interno);
                    } catch (BancoDAOException ex) {
                        Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                // visao.getjTFProcurar().setText("");
                /*visao.limpaTabela(visao.getjTPesquisa());
                 try {
                 bancoDAO = new BancoDAO(modelo);
                 } catch (BancoDAOException ex) {
                 Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                 }
                 visao.habilitaAlterar();
                 visao.habilitaExcluir();
                 visao.habilitaCancelar();
                 visao.desabilitaNovo();
                
                 if (visao.getjRBCpf().isSelected()) {
                 try {
                 //tem que fazer o metodo pesquisarCPF e pesquisar retornar um vertor de internos
                 if(visao.getjTFProcurar().getText().length()<1){
                 visao.msgBuscaInvalida();
                 visao.habilitaNovo();
                 visao.habilitaPesquisar();
                 visao.desabilitaAlterar();
                 visao.desabilitaCancelar();
                 visao.desabilitaExcluir();
                 return;
                
                 }
                 Interno interno = bancoDAO.pesquisarCPF(visao.getjTFProcurar().getText());
                 //visao.addLinhaPesquisa(interno);
                 } catch (BancoDAOException ex) {
                 Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                 }
                 } else if (visao.getjRBNome().isSelected()) {
                 try {
                
                 String teste = visao.getjTFProcurar().getText();
                 if(teste.length()<1){
                 visao.msgBuscaInvalida();
                 visao.habilitaNovo();
                 visao.habilitaPesquisar();
                 visao.desabilitaAlterar();
                 visao.desabilitaCancelar();
                 visao.desabilitaExcluir();
                 return;
                 }
                 ArrayList<Interno> interno = bancoDAO.pesquisar(teste);
                
                 if (interno == null) {
                 visao.msgResgistroNaoEncontrado();
                 visao.habilitarAbaPesquisa();
                 return;
                 }
                 visao.addLinhaPesquisa(interno);
                 } catch (BancoDAOException ex) {
                 Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                 }
                 }*/
            } catch (BancoDAOException ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (comando.equals(visao.getNOVOF())) {
            visao.habilitaCampos(visao.getjPFamiliaeSocial());
            visao.desabilitaNovoFamilia();
            visao.habilitaBotoesFamilia();
            visao.limpaCampos(visao.getjPFamiliaeSocial());
            //visao.iniFamilia();

        } else if (comando.equals(visao.getCADASTRARF())) {
            visao.desabilitaBotoesFamilia();
            visao.addLinhaFamiliaCadastrarF();
            visao.limpaCampos(visao.getjPFamiliaeSocial());
            visao.habilitaNovoFamilia();
            //visao.habilitaAlterarFamilia();
            // visao.habilitaExcluirFamilia();
            visao.desabilitaCampos(visao.getjPFamiliaeSocial());

        } else if (comando.equals(visao.getEXCLUIRF())) {
            if (visao.msgDesjaExcluir() == 1) {
                return;
            }
            visao.desabilitaAlterarFamilia();
            visao.desabilitaExcluirFamilia();
            visao.removeLinhaFamilia();
            visao.limpaCampos(visao.getjPFamiliaeSocial());
            visao.desabilitaCampos(visao.getjPFamiliaeSocial());
            visao.desabilitaCancelarFamilia();

        } else if (comando.equals(visao.getALTERARF())) {
            visao.desabilitaAlterarFamilia();
            visao.desabilitaExcluirFamilia();
            visao.alteraLinhaFamilia();
            visao.limpaCampos(visao.getjPFamiliaeSocial());
            visao.desabilitaCampos(visao.getjPFamiliaeSocial());
            visao.desabilitaCancelarFamilia();

        } else if (comando.equals(visao.getCANCELARF())) {
            visao.desabilitaCampos(visao.getjPFamiliaeSocial());
            visao.limpaCampos(visao.getjPFamiliaeSocial());
            //visao.limpaTabela(visao.getjTFamilia());

            visao.desabilitaBotoesFamilia();
            visao.habilitaNovoFamilia();
        } else if (comando.equals(visao.getBANCODADOS())) {
            visao.visualizaConexaoBanco();
        } else if (comando.equals(visao.getATUALIZAR())) {

            try {
                procurarTodos();
            } catch (BancoDAOException ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (comando.equals(visao.getRELATORIO())) {
           if(visao.getjRBRelatorioPeriodo().isSelected()){
            String consulta = 
                    "SELECT  interno.`Numero` AS interno_Numero,"
                        + "interno.`Nome` AS interno_Nome,"
                        + "interno.`Idade` AS interno_Idade,"
                        + "dependencia.`Maconha` AS dependencia_Maconha,"
                        + "dependencia.`Alcool` AS dependencia_Alcool,"
                        + "dependencia.`CocainaA` AS dependencia_CocainaA,"
                        + "dependencia.`CocainaI` AS dependencia_CocainaI,"
                        + "dependencia.`Crack` AS dependencia_Crack,"
                        + "dependencia.`CigarroTabaco` AS dependencia_CigarroTabaco,"
                        + "dependencia.`OutrasDrogas` AS dependencia_OutrasDrogas,"
                        + "endereco.`Cidade` AS endereco_Cidade,"
                        + "processo.`DataEntrevistaInternamento` AS processo_DataEntrevistaInternamento,"
                        + "processo.`DataEntrevistaSaida` AS processo_DataEntrevistaSaida,"
                        + "processo.`Status` AS processo_Status"
                    + " FROM "
                        + "`interno` interno INNER JOIN `dependencia` dependencia ON interno.`key_int` = dependencia.`key_int`"
                        + " INNER JOIN `endereco` endereco ON interno.`key_end` = endereco.`key_end`"
                        + " INNER JOIN `processo` processo ON interno.`key_int` = processo.`key_int`"
                    + " WHERE " 
                        +"processo.`DataEntrevistaInternamento` BETWEEN '"+ visao.convertDateToStringYYYYMMDD(visao.getjDCInicio().getDate())+"' AND '"+visao.convertDateToStringYYYYMMDD(visao.getjDCFim().getDate())+"'"
                    + " ORDER BY "
                         + "interno_Numero ASC";
            relatorios.gerarRalarotioPeriodo(consulta);
           }
        }
    }

    //atualiza o id do familiar quanndo alterar e precissonado
   /* public void atualizaIDFamiliaAlterar() {
     Interno m = visao.getInterno().get(visao.getjTPesquisa().getSelectedRow());
     //Iterator i = modelo.getFamiliaSocial().iterator();
     int i = 0;
     for (Iterator it = m.getFamiliaSocial().iterator(); it.hasNext(); i++) {

     FamiliaSocial f = (FamiliaSocial) it.next();
     //FamiliaSocial fi = (FamiliaSocial)i.next();

     modelo.getFamiliaSocial().get(i).setId(f.getId());

     }
     }*/
    public void cadastroAbaInterno() {
        //bancoDAO = new BancoDAO(modelo);
        //pegando tudo do Panel Dados Pessoais
        int linha = visao.getjTPesquisa().getSelectedRow();
        if (linha >= 0) {///usado para quando não tem ninguem cadastrado 

            int codigo = visao.getInterno().get(linha).getCodigo();
            modelo.setCodigo(codigo);
        }

        modelo.setNumero(Integer.parseInt(visao.getjTFID().getText()));
        modelo.setNome(visao.getjFTNome().getText());
        //modelo.setDataNascimento(visao.convertDateToString(visao.getjDCDataNascimento().getDate()));
        modelo.setDataNascimento(visao.getjDCDataNascimento().getDate());
        modelo.setIdade(visao.getjFTIdade().getText());
        modelo.setEstadoCivil((String) visao.getjCBEstadoCivil().getSelectedItem());

        if (visao.getjRBMasculino().isSelected()) {
            modelo.setSexo("M");
        } else if (visao.getjRBFeminino().isSelected()) {
            modelo.setSexo("F");
        }
        modelo.setComQuemMoraAtualmente(visao.getjTFComQuemMoraAtualmente().getText());
        modelo.setNaturalidade(visao.getjFTNaturalidade().getText());

        //pegando tudo do Panel Documentação
        modelo.setCPF(visao.getjFTCPF().getText());
        modelo.getTituloEleitoral().setNumero(visao.getjFTTituloEleitoral().getText());
        modelo.getTituloEleitoral().setSecao(visao.getjFTSecao().getText());
        modelo.getTituloEleitoral().setZona(visao.getjFTZona().getText());
        modelo.setRG(visao.getjFTRG().getText());
        modelo.setPIS(visao.getjFTPIS().getText());
        modelo.setNumeroCTPS(visao.getjFTCarteiradeTrabalho().getText());

        //pegando tudo do Panel endereço
        modelo.getEndereco().setRua(visao.getjFTRua().getText());
        modelo.getEndereco().setNumero(visao.getjFTNumero().getText());
        modelo.getEndereco().setBairro(visao.getjFTBairro().getText());
        modelo.getEndereco().setComplemento(visao.getjFTComplemento().getText());
        modelo.getEndereco().setCEP(visao.getjFTCEP().getText());
        modelo.getEndereco().setTelefone(visao.getjFTTelefone().getText());
        modelo.getEndereco().setCidade(visao.getjFTCidade().getText());
        modelo.getEndereco().setEstado((String) visao.getjCBUF().getSelectedItem());

        //pegando tudo do Panel Foto
        if (visao.getDiretorio_FOTO_ANTES() == null) {
            modelo.setFotoAntes(visao.getDiretorioFotoPadrao());
        } else {
            modelo.setFotoAntes(visao.getDiretorio_FOTO_ANTES());
        }

        if (visao.getDiretorio_FOTO_DEPOIS() == null) {
            modelo.setFotoDepois(visao.getDiretorioFotoPadrao());
        } else {
            modelo.setFotoDepois(visao.getDiretorio_FOTO_DEPOIS());
        }

    }

    public void cadastroAbaDependencia() {
        //bancoDAO = new BancoDAO(modelo);
        //pega tudo do Panel saude
        modelo.getSaude().setSono(visao.getjTASono().getText());
        modelo.getSaude().setAlucinacaoDelirios(visao.getjTAAlucinacaoDelirio().getText());
        modelo.getSaude().setAlimentacao(visao.getjTAAlimentacao().getText());
        modelo.getSaude().setDesmaiosConvulcoes(visao.getjTADemaisConvulsoes().getText());
        modelo.getSaude().setMedicacao(visao.getjTAMedicacao().getText());
        modelo.getSaude().setAutoExterminio(visao.getjTAAutoextermino().getText());
        modelo.getSaude().setExamesEspecificos(visao.getjTAExamesEspecificos().getText());

        //pega tudo do Panel Dependência
        //Como pegar os tipos de marcar ou os outras drogas.
        //modelo.getDependencia().setDrogas(visao.getDrogasUsadas());
        modelo.getDependencia().setOutrasDrogas(visao.getjFTOutrasDrogas().getText());
        modelo.getDependencia().setMaconha(visao.getjFTMaconha().getText());
        modelo.getDependencia().setAlcool(visao.getjFTAlcool().getText());
        modelo.getDependencia().setInalantes(visao.getjFTInalantes().getText());
        modelo.getDependencia().setCocainaA(visao.getjFTCocainaA().getText());
        modelo.getDependencia().setCocainaI(visao.getjFTCocainaI().getText());
        modelo.getDependencia().setComprimidos(visao.getjFTComprimido().getText());
        modelo.getDependencia().setCigarroTabaco(visao.getjFTCigarroTabaco().getText());
        modelo.getDependencia().setCrack(visao.getjFTCrack().getText());
        modelo.getDependencia().setDrogaUsadaAtual(visao.getjTAUsadaAtualmente().getText());
        modelo.getDependencia().setTempoUso(visao.getjTATempodeUso().getText());
        //bancoDAO.salvar();
    }

    public void cadastroAbaQuestionario() {
        //bancoDAO = new BancoDAO(modelo);
        //pega tudo do Panel Questionário
        modelo.getQuestionario().setConhecimentoColonia(visao.getjTAConhecimentoColonia().getText());
        modelo.getQuestionario().setSexualidade(visao.getjTASexualidade().getText());
        modelo.getQuestionario().setExpectativaTratamento(visao.getjTAExpectativaTratamento().getText());
        modelo.getQuestionario().setProcessoJustica(visao.getjTAProcessosnaJustica().getText());
        modelo.getQuestionario().setMotivoUsarDrogas(visao.getjTAMotivoLevouDrogas().getText());
        modelo.getQuestionario().setPossuiRelacionamentoAfetivo(visao.getjTARelacionamentoAfetivo().getText());
        modelo.getQuestionario().setGrupoApoio(visao.getjTAGrupoApoio().getText());
        modelo.getQuestionario().setRelacionamentoSocial(visao.getjTARelacionamentoSocial().getText());
        //possui relacionamento afetivo? motivo que o levou a esper/ relacionamento social.

        //pega tudo do Panel tratamentos anteriores
        modelo.getTratamentosAnteriores().setClinica(visao.getjFTClinica().getText());
        //modelo.getTratamentosAnteriores().setDataEntrada(visao.convertDateToString( visao.getjDCDataEntradaClinica().getDate()));
        //modelo.getTratamentosAnteriores().setDataSaida( visao.convertDateToString(visao.getjDCDataSaidaClinica().getDate()));
        modelo.getTratamentosAnteriores().setDataEntrada(visao.getjDCDataEntradaClinica().getDate());
        modelo.getTratamentosAnteriores().setDataSaida(visao.getjDCDataSaidaClinica().getDate());
        modelo.getTratamentosAnteriores().setMotivoSaida(visao.getjTAMotivoSaida().getText());
        //      modelo.getTratamentosAnteriores().setAbordagemTerapeutica(visao.getjTAAbordagemTerapeutica().getText());
//        modelo.getTratamentosAnteriores().setEndereco(visao.getjTALocal().getText());
        //bancoDAO.salvar();
    }

    public void cadastroAbaFamilia() {

        FamiliaSocial fs;
        for (int k = 0; k < visao.getjTFamilia().getRowCount(); k++) {
            String dados[] = visao.retornaLinhaFamilia(k);

            fs = new FamiliaSocial(dados[0], dados[1], dados[2], dados[3], dados[4], dados[5], dados[6], dados[7], dados[8]);
            if (dados[9] != null) {
                fs.setId(Integer.parseInt(dados[9]));
            }
            modelo.getFamiliaSocial().add(fs);
        }
    }

    public void cadastroAbaOutrasInformacoes() {
        //bancoDAO = new BancoDAO(modelo);
        //Panel Renda
        modelo.getSituacaoEconomica().setRendaPessoal(visao.getjFTRendaPessoal().getText());
        modelo.getSituacaoEconomica().setRendaFamilia(visao.getjFTRendaFamiliar().getText());
        modelo.getSituacaoEconomica().setRendaFamiliaSeparados(visao.getjFTRendaFamiliarSeparado().getText());
        modelo.getSituacaoEconomica().setAjudaFinanceira(visao.getjFTAjudaFinanceira().getText());
//Panel Profissao
        modelo.getProfissao().setTrabalhoAtual(visao.getjFTTrabalhoAtual().getText());
        modelo.getProfissao().setAfastadoPS(visao.getjFTAfastamentoPrevidenciaSocial().getText());
        modelo.getProfissao().setSalarioDesemprego(visao.getjFTSalarioDesemprego().getText());
        modelo.getProfissao().setTrabalhoCA(visao.getjFTCarteiraAssinada().getText());
        modelo.getProfissao().setAposentado(visao.getjFTAposentadoria().getText());
        modelo.getProfissao().setEscolaridade((String) visao.getjCBEscolaridade().getSelectedItem());
        //Panel Observaçao
        modelo.getQuestionario().setObservacoes(visao.getjTAObservacoes().getText());
        //bancoDAO.salvar();
        //pegando tudo do Panel processo

        //Processo de Internamento
        /*CORIGIR modelo.getProcesso().setTipo((String) visao.getjCBTipo().getSelectedItem());*/
        modelo.getProcesso().setEntrevistadoraUm(visao.getjFTEntrevistadoraUm().getText());
        modelo.getProcesso().setEntrevistadoraDois(visao.getjFTEntrevistadoraDois().getText());
        modelo.getProcesso().setEntrevistadoraInternamento(visao.getjFTEntrevistadoraInternamento().getText());
        modelo.getProcesso().setEntrevistadoraSaida(visao.getjFTEntrevistadoraSaida().getText());
        /*modelo.getProcesso().setDataUm(visao.convertDateToString(visao.getjDCEntrevistaUm().getDate()));
         modelo.getProcesso().setDataDois(visao.convertDateToString(visao.getjDCEntrevistaDois().getDate()));
         modelo.getProcesso().setDataInternamento(visao.convertDateToString(visao.getjDCEntrevistaInternamento().getDate()));
         modelo.getProcesso().setDataSaida(visao.convertDateToString(visao.getjDCEntrevistaSaida().getDate()));*/
        modelo.getProcesso().setDataUm(visao.getjDCEntrevistaUm().getDate());
        modelo.getProcesso().setDataDois(visao.getjDCEntrevistaDois().getDate());
        modelo.getProcesso().setDataInternamento(visao.getjDCEntrevistaInternamento().getDate());
        modelo.getProcesso().setDataSaida(visao.getjDCEntrevistaSaida().getDate());
        modelo.getProcesso().setStatus((String) visao.getjCBStatus().getSelectedItem());
    }

    public void cadastroAbaDadosBoleto() {
        modelo.getBoletoInterno().setResponsavel(visao.getjFTNomeResponsavel().getText());
        modelo.getBoletoInterno().setContatoResponsavel(visao.getjFTContatoResponsavel().getText());
        modelo.getBoletoInterno().setVencimentoBoleto(visao.getjFTVencimentoBoleto().getText());

        if (visao.getjRBEnderecoBoletoNao().isSelected()) {
            modelo.getBoletoInterno().setMesmoEnderecoInterno("NAO");
        } else if (visao.getjRBEnderecoBoletoSim().isSelected()) {
            modelo.getBoletoInterno().setMesmoEnderecoInterno("SIM");
        }
        //pegando tudo do Panel endereço
        modelo.getBoletoInterno().getEnderecoBoleto().setRua(visao.getjFTRuaBoleto().getText());
        modelo.getBoletoInterno().getEnderecoBoleto().setNumero(visao.getjFTNumeroBoleto().getText());
        modelo.getBoletoInterno().getEnderecoBoleto().setBairro(visao.getjFTBairroBoleto().getText());
        modelo.getBoletoInterno().getEnderecoBoleto().setComplemento(visao.getjFTComplementoBoleto().getText());
        modelo.getBoletoInterno().getEnderecoBoleto().setCEP(visao.getjFTCEPBoleto().getText());
        modelo.getBoletoInterno().getEnderecoBoleto().setTelefone(visao.getjFTTelefoneBoleto().getText());
        modelo.getBoletoInterno().getEnderecoBoleto().setCidade(visao.getjFTCidadeBoleto().getText());
        modelo.getBoletoInterno().getEnderecoBoleto().setEstado((String) visao.getjCBUFBoleto().getSelectedItem());

    }

    //0 - tudo ok
    //1 - erro
    public void atualizaModelo() {
        cadastroAbaInterno();
        cadastroAbaDependencia();
        cadastroAbaQuestionario();
        cadastroAbaOutrasInformacoes();
        cadastroAbaDadosBoleto();
        cadastroAbaFamilia();
        /*if (visao.getjTFamilia().getRowCount() > 0) {
         cadastroAbaFamilia();
         } else {
         visao.msgCadastroFamiliaObrigatorio();
         visao.habilitarAbaFamilia();
         return 1;
         }*/
        //return 0;

    }

    public void procurar() throws BancoDAOException {
        visao.limpaTabela(visao.getjTPesquisa());

        try {
            bancoDAO = new BancoDAO(modelo);
        } catch (BancoDAOException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (visao.getjRBCPF().isSelected()) {
            if (visao.getjTFProcurar().getText().length() < 1) {
                visao.msgBuscaInvalida();
                visao.habilitaNovo();
                visao.habilitaPesquisar();
                visao.desabilitaAlterar();
                visao.desabilitaCancelar();
                visao.desabilitaExcluir();
                return;

            }
            //Interno interno = bancoDAO.pesquisarID(visao.getjTFProcurar().getText());
            Interno interno = bancoDAO.pesquisarCPF(visao.getjTFProcurar().getText());
        } else if (visao.getjRBNome().isSelected()) {
            try {

                String teste = visao.getjTFProcurar().getText();
                if (teste.length() < 1) {
                    visao.msgBuscaInvalida();
                    visao.habilitaNovo();
                    visao.habilitaPesquisar();
                    visao.desabilitaAlterar();
                    visao.desabilitaCancelar();
                    visao.desabilitaExcluir();
                    return;
                }

                ArrayList<Interno> interno = bancoDAO.pesquisar(teste);

                if (interno == null) {
                    visao.msgResgistroNaoEncontrado();
                    visao.habilitarAbaPesquisa();
                    return;
                }
                visao.habilitaAlterar();
                visao.habilitaExcluir();
                visao.habilitaCancelar();
                visao.desabilitaNovo();
                visao.addLinhaPesquisa(interno);
            } catch (BancoDAOException ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void procurarTodos() throws BancoDAOException {
        visao.limpaTabela(visao.getjTPesquisa());
        try {
            bancoDAO = new BancoDAO(modelo);
            ArrayList<Interno> internos = bancoDAO.pesquisarTodos();
            visao.habilitaAlterar();
            visao.habilitaExcluir();
            visao.habilitaCancelar();
            visao.desabilitaNovo();
            visao.addLinhaPesquisa(internos);
        } catch (BancoDAOException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
            //Interno interno = bancoDAO.pesquisarID(visao.getjTFProcurar().getText());

    }

}
