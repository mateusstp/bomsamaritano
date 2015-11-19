package visao;

import DAO.BancoDAOException;
import DAO.*;
import Relatorios.Relatorios;
import com.toedter.calendar.JDateChooser;
import controlador.Controlador;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTable;
import modelo.FamiliaSocial;
import modelo.Interno;
import modelo.Observer;

/**
 *
 * @author Guilherme
 */
public class Visao extends javax.swing.JFrame implements Observer {

    private ConexaoBanco conexaobanco;
    private final String NOVO;
    private final String ALTERAR;
    private final String EXCLUIR;
    private final String PESQUISAR;
    private final String CANCELAR;
    private final String PROCURAR;
    private final String ALTERAR_FOTO_ANTES;
    private final String ALTERAR_FOTO_DEPOIS;
    private final String CADASTRAR;
    private final String FECHAR;
    private final String NOVOF;
    private final String ALTERARF;
    private final String EXCLUIRF;
    private final String CANCELARF;
    private final String CADASTRARF;
    private final String ENDERECOSIM;
    private final String ENDERECONAO;
    private final String BANCODADOS;
    private final String ATUALIZAR;
    private final String RELATORIO;
    
    // private final String ENTER;
    private String diretorioFotoPadrao = "D:\\\\BS\\\\5.9\\\\BomSamaritanov5.9\\\\src\\\\icones\\\\foto.jpg";
    private Interno modelo;
    private Controlador controlador;
    private ArrayList<Interno> interno;
    ///recebe diversos componentes
    private ArrayList<Component> Componentes;
    private ArrayList<Component> InciaComp;
    private ArrayList<Component> ComponentesTeste;
    private ArrayList<Integer> FamiliarExcluido;

    private String diretorio_FOTO_ANTES;
    private String diretorio_FOTO_DEPOIS;
    
    
    

    public Visao(Interno modelo) throws IOException {

        //inicia lista de componentes
        Componentes = new ArrayList<Component>();
        InciaComp = new ArrayList<Component>();
        interno = new ArrayList<Interno>();
        ComponentesTeste = new ArrayList<Component>();
        FamiliarExcluido = new ArrayList<Integer>();
        //tela de conexao
        conexaobanco = new ConexaoBanco();
        //conexaobanco.setVisible(true);
        conexaobanco.setVisible(false);

        //inicia compenentes
        initComponents();
        quebraLinhaJTArea(jPSaude);
        quebraLinhaJTArea(jPDependencia);
        quebraLinhaJTArea(jPQuestionario);
        quebraLinhaJTArea(jPTratamentosAnteriores);
        quebraLinhaJTArea(jPObservacao);

        jTFID.setText("0");

        jTFProcurar.setActionCommand("ENTER");

        setarModelTabela();
        setarTeclasPermitidas();

        //inicializa variaveis action listen
        NOVO = jBNovo.getActionCommand();
        ALTERAR = jBAlterar.getActionCommand();
        EXCLUIR = jBExcluir.getActionCommand();
        PESQUISAR = jBPesquisar.getActionCommand();
        CANCELAR = jBCancelar.getActionCommand();
        ALTERAR_FOTO_DEPOIS = jBAlterarFotoDepois.getActionCommand();
        ALTERAR_FOTO_ANTES = jBAlterarFotoAntes.getActionCommand();
        CADASTRAR = jBCadastrar.getActionCommand();
        FECHAR = jBFechar.getActionCommand();
        PROCURAR = jBProcurar.getActionCommand();
        NOVOF = jBNovoFamilia.getActionCommand();
        ALTERARF = jBAlterarFamilia.getActionCommand();
        EXCLUIRF = jBExcluirFamilia.getActionCommand();
        CANCELARF = jBCancelarFamilia.getActionCommand();
        CADASTRARF = jBCadastrarFamilia.getActionCommand();
        ENDERECOSIM = jRBEnderecoBoletoSim.getActionCommand();
        ENDERECONAO = jRBEnderecoBoletoNao.getActionCommand();
        BANCODADOS = jMenuBancoDados.getActionCommand();
        ATUALIZAR = jBAtualizarTodos.getActionCommand();
        RELATORIO = jBGerarRelatorio.getActionCommand();
        // ENTER = jTFProcurar.();
        //define modelo
        this.modelo = modelo;
        //desabilitar todos componentes campos
        desabilitaTodosCampos();
        //iniciar modelo
        this.modelo = modelo;
        this.modelo.registro(this);
        anexarControle(constroiControlador());
        //limpa compos
        limpaTodosCampos();
        //inicia lable com foto padrao
        limpaIconeFoto();
        //desabilita cadastrar/cancelar/excluir
        desabilitaBotoes();
        //
        desabilitaAlterar();

        habilitaNovo();
        jBExcluir.setEnabled(false);
        jMenuExcluir.setEnabled(false);
        jBIExcluir.setEnabled(false);
        
        try {
            //testa a conexao com o banco e dados
            conexaobanco.getConnection();
        } catch (BancoDAOException e) {
            JOptionPane.showMessageDialog(null, "Falha na conexão! Verifique o arquivo de conexão em C:\\sisbs\\bd.txt \n" + e.toString());
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Falha na conexão! Verifique o arquivo de conexão em C:\\sisbs\\bd.txt\n" + e.toString());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Falha na conexão! Verifique o arquivo de conexão em C:\\sisbs\\bd.txt\n" + e.toString());
        }

    }

    private Visao() {
        //initComponents();
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void quebraLinhaJTArea(JPanel p) {
        for (Component comp : p.getComponents()) {
            if (comp instanceof JScrollPane) {
                Object[] compo = ((JScrollPane) comp).getComponents();
                JViewport rep = (JViewport) compo[0];
                ((JTextArea) rep.getComponent(0)).setLineWrap(true);

            }
        }
    }

    public void ini() {
        getjTFID().setText("0");
        getjRBEnderecoBoletoSim().setSelected(true);
        inicializaCampos(getjPDadosPessoais(), "1");
        inicializaCampos(getjPDocumentacao(), "1");
        inicializaCampos(getjPEndereco(), "1");
        inicializaCampos(getjPProcesso(), "1");
        inicializaCampos(getjPSaude(), "1");
        inicializaCampos(getjPDependencia(), "1");
        inicializaCampos(getjPQuestionario(), "1");
        inicializaCampos(getjPTratamentosAnteriores(), "1");
        //inicializaCampos(jPFamiliaeSocial, "teste");
        inicializaCampos(getjPRenda(), "1");
        inicializaCampos(getjPProfissao(), "1");
        inicializaCampos(getjPObservacao(), "1");
        inicializaCampos(getjPComQuemMoraAtualmente(), "1");
        inicializaCampos(getjPDrogas(), "1");
        inicializaCampos(getjPBoleto(), "1");
        inicializaCampos(getjPDadosResponsavel(), "1");
        inicializaCampos(getjPEnderecoBoleto(), "1");
        //inicializaCampos(jPEnderecoBoletoInternoSimNao,"teste");
    }

    public void setarTeclasPermitidas() {
        //bloquei letras nos campos
        getjFTIdade().setDocument(new teclasPermitidas());
        getjFTIdadeFamilia().setDocument(new teclasPermitidas());
        getjFTNumero().setDocument(new teclasPermitidas());
        getjFTNumeroBoleto().setDocument(new teclasPermitidas());
        getjFTZona().setDocument(new teclasPermitidas());
        getjFTSecao().setDocument(new teclasPermitidas());
        getjFTTituloEleitoral().setDocument(new teclasPermitidas());
    }

    public void setarModelTabela() {
        getjTPesquisa().setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Registro", "Nome", "CPF", "Telefone"}) {

                    boolean[] canEdit = new boolean[]{
                        false, false, false, false, false
                    };

                    @Override
                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        return canEdit[columnIndex];
                    }
                });

        getjTFamilia().setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Nome", "Idade", "Parentesco", "Caso de Drogas", "Tempo Casados", "Estado Civíl", "Profissão", "Relacionamento", "Falecido", "ID"} ) {
               
                    boolean[] canEdit = new boolean[]{
                        false, false, false, false, false, false, false, false, false, false,false
                    };

                    @Override
                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        return canEdit[columnIndex];
                    }
                });

    }

    public void iniFamilia() {
        inicializaCampos(getjPFamiliaeSocial(), "1");
    }

    public void inicializaCampos(JPanel jp, String ini) {

        for (Component comp : jp.getComponents()) {
            if (comp instanceof JTextField) {
                if (((JTextField) comp).getText().indexOf("/") >= 0) {
                    ((JTextField) comp).setText("00/00/00");
                } else if (((JTextField) comp).getText().indexOf("(") >= 0) {
                    ((JTextField) comp).setText("(00)0000-0000");

                } else if (((JTextField) comp).getText().indexOf(".") >= 0) {
                    ((JTextField) comp).setText("000.000.000-00");
                } else if (((JTextField) comp).getText().indexOf("-") >= 0) {
                    ((JTextField) comp).setText("00000-000");
                } else {

                    ((JTextField) comp).setText(ini);
                }

            } else if (comp instanceof JComboBox) {
                ((JComboBox) comp).setSelectedIndex(1);
            }/* else if (comp instanceof JDateChooser) {
             ((JDateChooser) comp).setDateFormatString("00/00/0000");
             }*/ else if (comp instanceof JTextArea) {
                //desnecessarfio nao esta sendo usadda 
                ((JTextArea) comp).setText(ini);
            } else if (comp instanceof JFormattedTextField) {
            } else if (comp instanceof JRadioButton) {
                ((JRadioButton) comp).setSelected(true);
            } else if (comp instanceof JCheckBox) {
                ((JCheckBox) comp).setSelected(true);
            } else if (comp instanceof JScrollPane) {
                Object[] compo = ((JScrollPane) comp).getComponents();
                JViewport rep = (JViewport) compo[0];
                ((JTextArea) rep.getComponent(0)).setText(ini);

            }
        }

    }

    /* public void alteraLinhaFamilia() {
     int linha = jTFamilia.getSelectedRow();
     if (linha >= 0) {
     String n = getjFTNomeFamilia().getText();
     String p = (String) getjCBParentesco().getSelectedItem();
     String i = getjFTIdadeFamilia().getText();
     String c = getjFTCasodeDrogas().getText();
     String e = (String) getjCBEstadoCivilFamilia().getSelectedItem();
     String tc = getjFTTempoCasado().getText();
     String pr = getjFTProfissao().getText();
     String r = getjFTRelacionamento().getText();

     DefaultTableModel dtm = (DefaultTableModel) jTFamilia.getModel();

     dtm.setValueAt(n, linha, 0);
     dtm.setValueAt(i, linha, 1);
     dtm.setValueAt(p, linha, 2);
     dtm.setValueAt(c, linha, 3);
     dtm.setValueAt(tc, linha, 4);
     dtm.setValueAt(e, linha, 5);
     dtm.setValueAt(pr, linha, 6);
     dtm.setValueAt(r, linha, 7);

     } else {
     JOptionPane.showMessageDialog(null, "Favor selecionar uma linha");
     }
     }*/
    //pega dodos jTFamiliaSocial e add no arry list do modelo familia social
  /*  public void setjTFamiliaSocial() {
     DefaultTableModel model = (DefaultTableModel) getjTFamilia().getModel();
     limpaTabela(jTFamilia);
     for (ArrayList<FamiliaSocial> fs : modelo.getFamiliaSocial()) {
     addLinhaFamilia();
     }

     }*/
    //pega dodos jTFamiliaSocial e add no arry list do modelo familia social
    public void addFamiliaSocial() {
        //   DefaultTableModel model = (DefaultTableModel) getjTFamilia().getModel();
        /*if (jTFamilia.getRowCount() > 0) {
         for (int k = 0; k < jTFamilia.getRowCount(); k++) {
         String dados[] = retornaLinhaFamilia(k);
         FamiliaSocial fs = new FamiliaSocial(dados[0], Integer.parseInt(dados[1]), dados[2], dados[3], dados[4], dados[5], dados[6], dados[7]);
         modelo.getFamiliaSocial().add(fs);
         }
         }*/
        DefaultTableModel dtm = (DefaultTableModel) getjTFamilia().getModel();
        for (FamiliaSocial f : getModelo().getFamiliaSocial()) {

            String n = f.getNome();
            String p = f.getParantesco();
            String i = f.getIdade() + "";
            String c = f.getCasoDrogas();
            String e = f.getEstadoCivil();
            String tc = f.getTempoCasado();
            String pr = f.getProfissao();
            String r = f.getRelacionamentoFamiliar();
            String fa = f.getFalecido();
            String linha[] = {n, i, p, c, tc, e, pr, r, fa,"0"};
            dtm.addRow(linha);
            getjTFamilia().setModel(dtm);
        }

    }

    public void removeLinhaFamilia() {
        DefaultTableModel model = (DefaultTableModel) getjTFamilia().getModel();
        int linha = getjTFamilia().getSelectedRow();
        if (linha >= 0) {
            getFamiliarExcluido().add(Integer.parseInt(getjTFamilia().getValueAt(linha, 9).toString()));
            model.removeRow(linha);

        } else {
            JOptionPane.showMessageDialog(null, "Favor selecionar uma linha");
        }

    }

    public void addLinhaFamiliaInterno(int indice) {
        limpaTabela(getjTFamilia());
        DefaultTableModel dtm = (DefaultTableModel) getjTFamilia().getModel();
        //Interno i;
        FamiliaSocial f;

        // for (Iterator it = interno.iterator(); it.hasNext();) {
        // i = (Interno) it.next();
        for (Iterator itf = getInterno().get(indice).getFamiliaSocial().iterator(); itf.hasNext();) {

            f = (FamiliaSocial) itf.next();
            getModelo().getFamiliaSocial().add(f); //gambiarra para excluir suahsuhaushau

            String idd = f.getId() + "";
            String n = f.getNome();
            String p = f.getParantesco();
            String id = f.getIdade() + "";
            String c = f.getCasoDrogas();
            String e = f.getEstadoCivil();
            String tc = f.getTempoCasado();
            String pr = f.getProfissao();
            String r = f.getRelacionamentoFamiliar();
            String fa = f.getFalecido();
            String linha[] = {n, id, p, c, tc, e, pr, r, fa, idd};
            dtm.addRow(linha);

        }
        getjTFamilia().setModel(dtm);
        //}
    }

    public void addLinhaFamiliaCadastrarF() {
        DefaultTableModel dtm = (DefaultTableModel) getjTFamilia().getModel();
        String n = getjFTNomeFamilia().getText();
        String p = (String) getjCBParentesco().getSelectedItem();
        String i = getjFTIdadeFamilia().getText();
        String c = getjFTCasodeDrogas().getText();
        String e = (String) getjCBEstadoCivilFamilia().getSelectedItem();
        String tc = getjFTTempoCasado().getText();
        String pr = getjFTProfissao().getText();
        String r = getjFTRelacionamento().getText();
        String fa = getjFTFalecido().getText();
        System.out.print("***********************"+n+" "+ i+" "+p+" "+ c+" "+ tc+" "+ e+ " "+pr+" "+ r+" "+fa);
        String linha[] = {n, i, p, c, tc, e, pr, r, fa};
        dtm.addRow(linha);
        getjTFamilia().setModel(dtm);
    }

    public void alteraLinhaFamilia() {
        int linha = getjTFamilia().getSelectedRow();
        if (linha >= 0) {
            String n = getjFTNomeFamilia().getText();
            String p = (String) getjCBParentesco().getSelectedItem();
            String i = getjFTIdadeFamilia().getText();
            String c = getjFTCasodeDrogas().getText();
            String e = (String) getjCBEstadoCivilFamilia().getSelectedItem();
            String tc = getjFTTempoCasado().getText();
            String pr = getjFTProfissao().getText();
            String r = getjFTRelacionamento().getText();
            String fa = getjFTFalecido().getText();

            DefaultTableModel dtm = (DefaultTableModel) getjTFamilia().getModel();

            dtm.setValueAt(n, linha, 0);
            dtm.setValueAt(i, linha, 1);
            dtm.setValueAt(p, linha, 2);
            dtm.setValueAt(c, linha, 3);
            dtm.setValueAt(tc, linha, 4);
            dtm.setValueAt(e, linha, 5);
            dtm.setValueAt(pr, linha, 6);
            dtm.setValueAt(r, linha, 7);
            dtm.setValueAt(fa, linha, 8);

        } else {
            JOptionPane.showMessageDialog(null, "Favor selecionar uma linha");
        }
    }

    public String[] retornaLinhaFamilia(int linha) {

        if (linha >= 0) {
            DefaultTableModel dtm = (DefaultTableModel) getjTFamilia().getModel();
            String idd = "0";
            if (dtm.getValueAt(linha, 9) != null) {
                idd = dtm.getValueAt(linha, 9).toString();
            }
            String n = dtm.getValueAt(linha, 0).toString();
            String i = dtm.getValueAt(linha, 1).toString();
            String p = dtm.getValueAt(linha, 2).toString();
            String c = dtm.getValueAt(linha, 3).toString();
            String tc = dtm.getValueAt(linha, 4).toString();
            String e = dtm.getValueAt(linha, 5).toString();
            String pr = dtm.getValueAt(linha, 6).toString();
            String r = dtm.getValueAt(linha, 7).toString();
            String fa = dtm.getValueAt(linha, 8).toString();
            String[] dados = {n, i, p, c, tc, e, pr, r, fa, idd};
            return dados;

        } else {
            JOptionPane.showMessageDialog(null, "Favor selecionar uma linha");
        }
        return null;
    }

    public void linhaSelecionadaFamilia() {
        int linha = getjTFamilia().getSelectedRow();
        if (linha >= 0) {
            getjFTNomeFamilia().setText(getjTFamilia().getValueAt(linha, 0).toString());
            getjFTIdadeFamilia().setText(getjTFamilia().getValueAt(linha, 1).toString());
            getjCBParentesco().setSelectedItem(getjTFamilia().getValueAt(linha, 2).toString());
            getjFTCasodeDrogas().setText(getjTFamilia().getValueAt(linha, 3).toString());
            getjFTFalecido().setText(getjTFamilia().getValueAt(linha, 4).toString());
            getjCBEstadoCivilFamilia().setSelectedItem(getjTFamilia().getValueAt(linha, 5).toString());
            getjFTProfissao().setText(getjTFamilia().getValueAt(linha, 6).toString());
            getjFTRelacionamento().setText(getjTFamilia().getValueAt(linha, 7).toString());
            getjFTFalecido().setText(getjTFamilia().getValueAt(linha, 8).toString());
        } else {
            JOptionPane.showMessageDialog(null, "Favor selecionar uma linha");
        }
    }

    public void addLinhaPesquisa(ArrayList<Interno> internos) {
        //this.interno = interno;
        Interno interno = new Interno();
        this.setInterno(internos);

        for (Iterator it = this.getInterno().iterator(); it.hasNext();) {
            interno = (Interno) it.next();
            String r = interno.getNumero() + "";
            String n = interno.getNome();
            String c = interno.getCPF();
            String t = interno.getEndereco().getTelefone();

            DefaultTableModel model = (DefaultTableModel) getjTPesquisa().getModel();
            Object[] linha = {r, n, c, t};//alguma linha  
            model.addRow(linha);
        }

    }

    public String convertDateToString(Date indate) {
        String dateString = null;
        SimpleDateFormat sdfr = new SimpleDateFormat("dd/MM/yyyy");
        /*you can also use DateFormat reference instead of SimpleDateFormat 
         * like this: DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
         */
        try {
            if (indate != null) {
                dateString = sdfr.format(indate);
                return dateString;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro na conversaõ de data!(Data para String)\n Data: " + indate + "\nErro: " + ex.toString());
        }
        return "";

    }
    
    public String convertDateToStringYYYYMMDD(Date indate) {
        String dateString = null;
        SimpleDateFormat sdfr = new SimpleDateFormat("yyyy/MM/dd");
        /*you can also use DateFormat reference instead of SimpleDateFormat 
         * like this: DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
         */
        try {
            if (indate != null) {
                dateString = sdfr.format(indate);
                return dateString;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro na conversaõ de data!(Data para String)\n Data: " + indate + "\nErro: " + ex.toString());
        }
        return "";

    }

    public Date convertStringToDate(String indate) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            if(indate != null){
                Date date = formatter.parse(indate);
            return date;
           }else{
                return null;
            }
        } catch (ParseException e) {
            return null;
        }

    }

    public void removeLinhaPesquisa(int linha) {
        DefaultTableModel dtm = (DefaultTableModel) getjTPesquisa().getModel();
        if (linha >= 0 && linha < getjTPesquisa().getRowCount()) {
            dtm.removeRow(linha);
            getjTPesquisa().setModel(dtm);
            getInterno().remove(linha);
        } else {
            JOptionPane.showMessageDialog(null, "Favor selecionar uma linha");
        }
    }

    public String buscarInterno() {
        return getjTFProcurar().getText();
    }

    public void habilitarAbaPesquisa() {
        //Mudar o foco pra aba de pesquisa
        getjTPAbas().setSelectedIndex(7);
    }

    public void habilitarAbaFamilia() {
        //Mudar o foco pra aba de pesquisa
        getjTPAbas().setSelectedIndex(3);
    }

    public void habilitarAbaPrincipal() {
        //Mudar o foto pra aba de pesquisa
        getjTPAbas().setSelectedIndex(0);
    }

    //limpa tados os campos
    public void limpaTodosCampos() {
        limpaCamposPaineis(getjPInternoGeral());
        limpaCamposPaineis(getjPDependenciaGeral());
        limpaCamposPaineis(getjPQuestionarioGeral());
        limpaCamposPaineis(getjPPesquisaGeral());
        limpaCamposPaineis(getjPOutrasInformacoesGeral());
        limpaCamposPaineis(getjPFamiliaGeral());
        limpaCamposPaineis(getjPBoleto());
        limpaIconeFoto();
        limpaTabela(getjTFamilia());

    }

    public ImageIcon redimensinarImagem(String imagem, int w, int h) {

        ImageIcon icon = new ImageIcon(imagem); //Somente esta linha foi alterada  
        icon.setImage(icon.getImage().getScaledInstance(w, h, 100));
        return icon;
    }
    /*public ImageIcon redimensinarImagem(ImageIcon icon, int w, int h) {

     //ImageIcon icon = new ImageIcon(imagem); //omente esta linha foi alterada  
     icon.setImage(icon.getImage().getScaledInstance(w, h, 100));
     return icon;
     }*/

    public void limpaIconeFoto() {

        getjLFotoAntes().setIcon(redimensinarImagem(getDiretorioFotoPadrao(), getjLFotoAntes().getWidth() - 5, getjLFotoAntes().getHeight() - 5));
        getjLFotoDepois().setIcon(redimensinarImagem(getDiretorioFotoPadrao(), getjLFotoDepois().getWidth() - 5, getjLFotoDepois().getHeight() - 5));
        //getjLFotoAntes().setIcon(modelo.getFotoAntes());
        //getjLFotoDepois().setIcon(modelo.getFotoDepois());

    }

    //libeta todos os campos para alteracao
    public void habilitaTodosCampos() {
        habilitaPaineis(getjPInternoGeral());
        habilitaPaineis(getjPDependenciaGeral());
        habilitaPaineis(getjPQuestionarioGeral());
        habilitaPaineis(getjPBoleto());
        habilitaPaineis(getjPDadosResponsavel());
        habilitaPaineis(getjPEnderecoBoleto());
        //habilitaPaineis(jPPesquisaGeral);
        habilitaPaineis(getjPOutrasInformacoesGeral());
        habilitaPaineis(getjPFamiliaGeral());
        habilitaMenus();
        habilitaBotoesIcones();
        habilitaID();
    }

    //habilita campos aba pesquisar
    public void habilitaCamposPesquisa() {
        habilitaPaineis(getjPPesquisaGeral());
    }

    //habilita menus cadastrar\cancelar e excluir
    public void habilitaMenus() {
        getjMenuCadastrar().setEnabled(true);
        //getjMenuExcluir().setEnabled(true);
        getjMenuCancelar().setEnabled(true);

    }

    public void habilitaID() {
        getjTFID().setEnabled(true);

    }

    public void desabilitaID() {
        getjTFID().setEnabled(false);

    }

    //habilita icones cadastrar cancelar e excluir
    public void habilitaBotoesIcones() {
        getjBCadastrar().setEnabled(true);
        //getjBExcluir().setEnabled(true);
        getjBCancelar().setEnabled(true);

    }

    public void desabilitaTodosCampos() {
        desabilitaPaineis(getjPInternoGeral());
        desabilitaPaineis(getjPDependenciaGeral());
        desabilitaPaineis(getjPQuestionarioGeral());
        desabilitaPaineis(getjPPesquisaGeral());
        desabilitaPaineis(getjPOutrasInformacoesGeral());
        desabilitaPaineis(getjPFamiliaGeral());
        desabilitaPaineis(getjPBoleto());
        desabilitaPaineis(getjPDadosResponsavel());
        desabilitaPaineis(getjPEnderecoBoleto());
        getjTFID().setText("0");
        desabilitaID();
    }

    //recebe painel  e deshabilita campos paineis
    public void desabilitaPaineis(JPanel jp) {
        for (Component comp : jp.getComponents()) {
            if (comp instanceof JPanel) {
                desabilitaCampos((JPanel) comp);
            }
        }
    }

    //recebe painel  e habilita campos paineis
    public void habilitaPaineis(JPanel jp) {
        for (Component comp : jp.getComponents()) {
            if (comp instanceof JPanel) {
                habilitaCampos((JPanel) comp);
            }
        }
    }

    //recebe painel  e habilita campos
    public void habilitaCampos(JPanel jp) {
        for (Component comp : jp.getComponents()) {
            if (comp instanceof JTextField) {
                ((JTextField) comp).setEnabled(true);
            } else if (comp instanceof JComboBox) {
                ((JComboBox) comp).setEnabled(true);
            } else if (comp instanceof JDateChooser) {
                ((JDateChooser) comp).setEnabled(true);
            } else if (comp instanceof JTextArea) {
                ((JTextArea) comp).setEnabled(true);
            } else if (comp instanceof JFormattedTextField) {
                ((JFormattedTextField) comp).setEnabled(true);
            } else if (comp instanceof JRadioButton) {
                ((JRadioButton) comp).setEnabled(true);
            } else if (comp instanceof JButton) {
                ((JButton) comp).setEnabled(true);
            } else if (comp instanceof JCheckBox) {
                ((JCheckBox) comp).setEnabled(true);
            } else if (comp instanceof JScrollPane) {
                Object[] compo = ((JScrollPane) comp).getComponents();
                JViewport rep = (JViewport) compo[0];
                rep.getComponent(0).setEnabled(true);
            }
        }
    }

    //recebe panel e desabilita campos
    public void desabilitaCampos(JPanel jp) {

        for (Component comp : jp.getComponents()) {
            if (comp instanceof JTextField) {

                ((JTextField) comp).setEnabled(false);
            } else if (comp instanceof JComboBox) {
                ((JComboBox) comp).setEnabled(false);
            } else if (comp instanceof JDateChooser) {
                ((JDateChooser) comp).setEnabled(false);
            } else if (comp instanceof JTextArea) {
                //desnecessarfio nao esta sendo usadda                    
                ((JTextArea) comp).setEditable(false);
            } else if (comp instanceof JFormattedTextField) {
                ((JFormattedTextField) comp).setEnabled(false);
            } else if (comp instanceof JRadioButton) {
                ((JRadioButton) comp).setEnabled(false);
            } else if (comp instanceof JButton) {
                ((JButton) comp).setEnabled(false);
            } else if (comp instanceof JCheckBox) {
                ((JCheckBox) comp).setEnabled(false);
            } else if (comp instanceof JScrollPane) {
                Object[] compo = ((JScrollPane) comp).getComponents();
                JViewport rep = (JViewport) compo[0];
                rep.getComponent(0).setEnabled(false);
            }
        }
    }

    //limpa todos os campos detro de um painel
    public void limpaCamposPaineis(JPanel jp) {
        for (Component comp : jp.getComponents()) {
            if (comp instanceof JPanel) {
                limpaCampos((JPanel) comp);
            }
        }
    }

    public void limpaTabela(JTable jt) {
        DefaultTableModel dtm = (DefaultTableModel) jt.getModel();
        dtm.setNumRows(0);
    }

    //limpa todos os campos
    public void limpaCampos(JPanel jp) {

        for (Component comp : jp.getComponents()) {
            if (comp instanceof JTextField) {
                ((JTextField) comp).setText(null);
            } else if (comp instanceof JTextArea) {
                ((JTextArea) comp).setText(null);
            } else if (comp instanceof JDateChooser) {
                ((JTextField) ((JDateChooser) comp).getDateEditor().getUiComponent()).setText("");
            } else if (comp instanceof JFormattedTextField) {
                ((JFormattedTextField) comp).setText(null);
            } else if (comp instanceof JScrollPane) {
                Object[] compo = ((JScrollPane) comp).getComponents();
                JViewport rep = (JViewport) compo[0];
                ((JTextArea) rep.getComponent(0)).setText(null);
            } else if (comp instanceof JComboBox) {
                ((JComboBox) comp).setSelectedIndex(0);
            }
        }
    }

    /*desabilita cadastrar\cancelar e excluir*/
    public void desabilitaBotoes() {
        getjBCadastrar().setEnabled(false);
        getjBCancelar().setEnabled(false);
        getjBExcluir().setEnabled(false);

        getjBICadastrar().setEnabled(false);
        getjBICancelar().setEnabled(false);
        getjBIExcluir().setEnabled(false);

        getjMenuCadastrar().setEnabled(false);
        getjMenuCancelar().setEnabled(false);
        getjMenuExcluir().setEnabled(false);

    }

    public void desabilitaCadastrar() {
        getjBCadastrar().setEnabled(false);
        getjBICadastrar().setEnabled(false);
        getjMenuCadastrar().setEnabled(false);

    }

    public void desabilitaBotoesFamilia() {
        getjBCadastrarFamilia().setEnabled(false);
        getjBAlterarFamilia().setEnabled(false);
        getjBCancelarFamilia().setEnabled(false);
        getjBExcluirFamilia().setEnabled(false);
    }

    public void desabilitaCadastrarF() {
        getjBCadastrarFamilia().setEnabled(false);
    }

    //Desabilita botoes aba pesquisa
    public void desabilitaBotoesAbaPesquisar() {
        getjBCadastrar().setEnabled(false);
        getjBCancelar().setEnabled(true);
        getjBPesquisar().setEnabled(false);

        getjBICadastrar().setEnabled(false);
        getjBICancelar().setEnabled(true);
        getjBIPesquisar().setEnabled(false);

        getjMenuCadastrar().setEnabled(false);
        getjMenuCancelar().setEnabled(true);
        getjMenuPesquisar().setEnabled(false);
    }

    //habilita cadastrar/cancelar e excluir
    public void habilitaBotoes() {
        getjBCadastrar().setEnabled(true);
        getjBCancelar().setEnabled(true);
        //getjBExcluir().setEnabled(true);

        getjBICadastrar().setEnabled(true);
        getjBICancelar().setEnabled(true);
        //getjBIExcluir().setEnabled(true);

        getjMenuCadastrar().setEnabled(true);
        getjMenuCancelar().setEnabled(true);
        //getjMenuExcluir().setEnabled(true);
    }

    public void habilitaBotoesFamilia() {
        getjBCadastrarFamilia().setEnabled(true);
        getjBCancelarFamilia().setEnabled(true);
        //getjBExcluir().setEnabled(true);
    }
//habilita novo registro

    public void habilitaNovo() {
        getjBNovo().setEnabled(true);
        getjBINovo().setEnabled(true);
        getjMenuNovo().setEnabled(true);
    }

    public void habilitaNovoFamilia() {
        getjBNovoFamilia().setEnabled(true);
    }

    //desabilita novo registro
    public void desabilitaNovo() {
        getjBNovo().setEnabled(false);
        getjBINovo().setEnabled(false);
        getjMenuNovo().setEnabled(false);
    }

    public void desabilitaNovoFamilia() {
        getjBNovoFamilia().setEnabled(false);
    }

    //desabilita opcao de alterar
    public void desabilitaAlterar() {
        getjBAlterar().setEnabled(false);
        getjBIAlterar().setEnabled(false);
        getjMenuAlterar().setEnabled(false);
    }

    public void desabilitaFechar() {
        getjBFechar().setEnabled(false);
        getjBIFechar().setEnabled(false);
        getjMenuFechar().setEnabled(false);
    }

    public void desabilitaAlterarFamilia() {
        getjBAlterarFamilia().setEnabled(false);
    }

    public void habilitaAlterar() {

        getjBAlterar().setEnabled(true);

        getjBIAlterar().setEnabled(true);

        getjMenuAlterar().setEnabled(true);
    }

    public void habilitaAlterarFamilia() {

        getjBAlterarFamilia().setEnabled(true);

    }

    public void desabilitaExcluir() {
        getjBExcluir().setEnabled(false);
        getjBIExcluir().setEnabled(false);
        getjMenuExcluir().setEnabled(false);
    }

    public void desabilitaExcluirFamilia() {
        getjBExcluirFamilia().setEnabled(false);
    }

    public void habilitaExcluir() {
        /*/ getjBExcluir().setEnabled(true);
         getjBIExcluir().setEnabled(true);
         getjMenuExcluir().setEnabled(true);*/
    }

    public void habilitaExcluirFamilia() {
        getjBExcluirFamilia().setEnabled(true);
    }

    public void desabilitaCancelar() {
        getjBCancelar().setEnabled(false);

        getjBICancelar().setEnabled(false);

        getjMenuCancelar().setEnabled(false);
    }

    public void desabilitaCancelarFamilia() {
        getjBCancelarFamilia().setEnabled(false);
    }

    public void habilitaCancelar() {
        getjBCancelar().setEnabled(true);

        getjBICancelar().setEnabled(true);

        getjMenuCancelar().setEnabled(true);
    }

    public void habilitaCancelarFamilia() {
        getjBCancelarFamilia().setEnabled(true);
    }

    public void desabilitaPesquisar() {
        getjBPesquisar().setEnabled(false);

        getjBIPesquisar().setEnabled(false);

        getjMenuPesquisar().setEnabled(false);
    }

    public void habilitaPesquisar() {
        getjBPesquisar().setEnabled(true);

        getjBIPesquisar().setEnabled(true);

        getjMenuPesquisar().setEnabled(true);
    }

    public void fechar() {
        System.exit(0);
    }

    public void msgFechar() {
        Object[] opcoes = {"Sim", "Não"};
        int opcao = JOptionPane.showOptionDialog(null,
                "Deseja realmente fechar?", "Fechar",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                opcoes, opcoes[0]);
        if (opcao == 0) {
            fechar();
        }
    }

    public void msgBuscaInvalida() {
        JOptionPane.showMessageDialog(rootPane, "Busca inválida!");
    }

    public int msgDesjaExcluir() {
        Object[] opcoes = {"Sim", "Não"};
        int opcao = JOptionPane.showOptionDialog(null,
                "Deseja realmente excluir?", "Fechar",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                opcoes, opcoes[0]);
        return opcao;
    }

    public void msgCadastroFamiliaObrigatorio() {
        JOptionPane.showMessageDialog(rootPane, "Cadastro de familiar obrigatório!");
    }

    public void msgExcluirApenasUm() {
        JOptionPane.showMessageDialog(rootPane, "Escolha apenas um interno para excluir!");
    }

    public void msgResgistroNaoEncontrado() {
        JOptionPane.showMessageDialog(rootPane, "Registro não encontrado!");

    }

    public void msgCadastroEfetuado() {
        JOptionPane.showMessageDialog(rootPane, "Cadastro Efetuado com sucesso!");
        habilitarAbaPrincipal();
    }

    public void msgAlteracaoEfetuada() {
        JOptionPane.showMessageDialog(rootPane, "Alteração Efetuado com sucesso!");
        habilitarAbaPrincipal();
    }

    public void msgExclusaoEfetuada() {
        JOptionPane.showMessageDialog(rootPane, "Exclusão Efetuado com sucesso!");
        habilitarAbaPrincipal();
    }

    public void msgBuscaNaoEncontrada() {
        JOptionPane.showMessageDialog(rootPane, "Cadastro não encontrado!");
    }

    public void msgBotaoCancelar() {

        Object[] opcoes = {"Sim", "Não"};
        int opcao = JOptionPane.showOptionDialog(null,
                "Deseja realmente cancelar a operação?", "Cancelar operação0",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                opcoes, opcoes[0]);
        if (opcao == 1) {
            JOptionPane.showMessageDialog(null, "Continue trabalhando na aplicação",
                    "Informação", JOptionPane.INFORMATION_MESSAGE);
        } else {
            //limpa compos
            limpaCamposPaineis(getjPInternoGeral());
            limpaCamposPaineis(getjPDependenciaGeral());
            limpaCamposPaineis(getjPQuestionarioGeral());
            limpaCamposPaineis(getjPPesquisaGeral());
            limpaCamposPaineis(getjPOutrasInformacoesGeral());
            limpaCamposPaineis(getjPFamiliaGeral());
            this.desabilitaBotoes();
            this.desabilitaAlterar();
            habilitarAbaPrincipal();
        }
    }

    public void msgBotaoCancelarFamilia() {

        Object[] opcoes = {"Sim", "Não"};
        int opcao = JOptionPane.showOptionDialog(null,
                "Deseja realmente cancelar a operação?", "Cancelar operação0",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                opcoes, opcoes[0]);
        if (opcao == 1) {
            JOptionPane.showMessageDialog(null, "Continue trabalhando na aplicação",
                    "Informação", JOptionPane.INFORMATION_MESSAGE);
        } else {
            //limpa compos
            limpaCamposPaineis(getjPFamiliaeSocial());
            this.desabilitaBotoesFamilia();
            this.desabilitaAlterarFamilia();
        }
    }

    public void update() {

        updateAbaInterno();
        updateAbaDependencia();
        updateAbaOutrasInformacoes();
        updateFamilia();
        updateAbaQuestionario();
        updateAbaBoleto();

    }

    public void updateAbaBoleto() {
        getjFTNomeResponsavel().setText(getModelo().getBoletoInterno().getResponsavel());
        getjFTContatoResponsavel().setText(getModelo().getBoletoInterno().getContatoResponsavel());
        getjFTVencimentoBoleto().setText(getModelo().getBoletoInterno().getVencimentoBoleto());

        if (getModelo().getBoletoInterno().getMesmoEnderecoInterno().equals("SIM")) {
            getjRBEnderecoBoletoSim().setSelected(true);
        } else if (getModelo().getBoletoInterno().getMesmoEnderecoInterno().equals("NAO")) {
            getjRBEnderecoBoletoNao().setSelected(true);
        }
        /*painel endereco*/
        getjFTRuaBoleto().setText(getModelo().getBoletoInterno().getEnderecoBoleto().getRua());
        getjFTBairroBoleto().setText(getModelo().getBoletoInterno().getEnderecoBoleto().getBairro());
        getjFTNumeroBoleto().setText(getModelo().getBoletoInterno().getEnderecoBoleto().getNumero());
        getjFTCEPBoleto().setText(getModelo().getBoletoInterno().getEnderecoBoleto().getCEP());
        getjFTComplementoBoleto().setText(getModelo().getBoletoInterno().getEnderecoBoleto().getComplemento());
        getjFTTelefoneBoleto().setText(getModelo().getBoletoInterno().getEnderecoBoleto().getTelefone());
        getjFTCidadeBoleto().setText(getModelo().getBoletoInterno().getEnderecoBoleto().getCidade());
        getjCBUFBoleto().setSelectedItem(getModelo().getBoletoInterno().getEnderecoBoleto().getEstado());
    }
    /*public void updateAbaQuestionario(){
     getjFTNomeResponsavel().setText(getModelo().getBoletoInterno().getResponsavel());
     getjFTContatoResponsavel().setText(getModelo().getBoletoInterno().getContatoResponsavel());
     getjFTVencimentoBoleto().setText(getModelo().getBoletoInterno().getVencimentoBoleto());
      
     /*painel endereco*/
    /* getjFTRuaBoleto().setText(getModelo().getEndereco().getRua());
     getjFTBairroBoleto().setText(getModelo().getEndereco().getBairro());
     getjFTNumeroBoleto().setText(Integer.toString(getModelo().getEndereco().getNumero()));
     getjFTCEPBoleto().setText(getModelo().getEndereco().getCEP());
     getjFTComplementoBoleto().setText(getModelo().getEndereco().getComplemento());
     getjFTTelefoneBoleto().setText(getModelo().getEndereco().getTelefone());
     getjFTCidadeBoleto().setText(getModelo().getEndereco().getCidade());
     getjCBUFBoleto().setSelectedItem(getModelo().getEndereco().getEstado());


     }*/

    public void updateFamilia() {
        /*Painel familia social*/
        addFamiliaSocial();

    }

    public void updateAbaInterno() {

        /*registro*/
        getjTFID().setText(getModelo().getNumero() + "");
        /*painel dados pessoais*/
        getjFTNome().setText(getModelo().getNome());
        
        //Date d = convertStringToDate(getModelo().getDataNascimento());
        //if(d==null)
        if(getModelo().getDataNascimento()==null)
            ((JTextField) jDCDataNascimento.getDateEditor().getUiComponent()).setText("");
        else
            jDCDataNascimento.setDate(getModelo().getDataNascimento());
        
        // }
        getjFTIdade().setText(getModelo().getIdade());
        getjCBEstadoCivil().setSelectedItem(getModelo().getEstadoCivil());
        //getjFTResponsavel().setText(getModelo().getResponsavel());
        getjFTNaturalidade().setText(getModelo().getNaturalidade());
        if (getModelo().getSexo().equals("M")) {
            getjRBMasculino().setSelected(true);
        } else if (getModelo().getSexo().equals("F")) {
            getjRBFeminino().setSelected(true);
        }
        /*Com quem mora atualmente*/
        getjTFComQuemMoraAtualmente().setText(getModelo().getComQuemMoraAtualmente());
        //*painel documentacao*//
        getjFTCPF().setText(getModelo().getCPF());
        getjFTTituloEleitoral().setText(getModelo().getTituloEleitoral().getNumero());
        getjFTSecao().setText(getModelo().getTituloEleitoral().getSecao());
        getjFTZona().setText(getModelo().getTituloEleitoral().getZona());
        getjFTCarteiradeTrabalho().setText(getModelo().getNumeroCTPS());
        getjFTRG().setText(getModelo().getRG());
        getjFTPIS().setText(getModelo().getPIS());

        /*painel endereco*/
        getjFTRua().setText(getModelo().getEndereco().getRua());
        getjFTBairro().setText(getModelo().getEndereco().getBairro());
        getjFTNumero().setText(getModelo().getEndereco().getNumero());
        getjFTCEP().setText(getModelo().getEndereco().getCEP());
        getjFTComplemento().setText(getModelo().getEndereco().getComplemento());
        getjFTTelefone().setText(getModelo().getEndereco().getTelefone());
        getjFTCidade().setText(getModelo().getEndereco().getCidade());
        getjCBUF().setSelectedItem(getModelo().getEndereco().getEstado());

        /*Fotos antes/depois*/
        if (getModelo().getFotoAntes() != null) {
            getjLFotoAntes().setIcon(redimensinarImagem(getModelo().getFotoAntes(), getjLFotoAntes().getWidth(), getjLFotoAntes().getHeight()));
        }
        if (getModelo().getFotoDepois() != null) {
            getjLFotoDepois().setIcon(redimensinarImagem(getModelo().getFotoDepois(), getjLFotoDepois().getWidth(), getjLFotoDepois().getHeight()));
        }

        //String imagem[] = modelo.getFotoAntes().split("/");
        //File fotoantes = //edimensinarImagem(modelo.getFotoAntes(), 120, 155);
//        setIconeFoto(fotoantes.getPath(), fotoantes.getPath(),jLFotoAntes.getWidth(), jLFotoAntes.);
        //setIconeFoto(modelo.getFotoAntes(), modelo.getFotoDepois());
    }

    public void atualizaFotos(String fotoAntes, String fotoDepois) {
        getjLFotoAntes().setIcon(redimensinarImagem(fotoAntes, getjLFotoAntes().getWidth(), getjLFotoAntes().getHeight()));
        getjLFotoDepois().setIcon(redimensinarImagem(fotoDepois, getjLFotoDepois().getWidth(), getjLFotoDepois().getHeight()));
    }

    public void updateAbaDependencia() {
        /*Saude*/
        getjTASono().setText(getModelo().getSaude().getSono());
        getjTAAlimentacao().setText(getModelo().getSaude().getAlimentacao());
        getjTAMedicacao().setText(getModelo().getSaude().getMedicacao());
        getjTAExamesEspecificos().setText(getModelo().getSaude().getExamesEspecificos());
        getjTAAlucinacaoDelirio().setText(getModelo().getSaude().getAlucinacaoDelirios());
        getjTADemaisConvulsoes().setText(getModelo().getSaude().getDesmaiosConvulcoes());
        getjTAAutoextermino().setText(getModelo().getSaude().getAutoExterminio());
        /*Dependencia*/
        getjTATempodeUso().setText(getModelo().getDependencia().getTempoUso());
        getjTAUsadaAtualmente().setText(getModelo().getDependencia().getDrogaUsadaAtual());
        //setDrogasUsadas();
        /*JPanel Drogas*/
        getjFTMaconha().setText(getModelo().getDependencia().getMaconha());
        getjFTAlcool().setText(getModelo().getDependencia().getAlcool());
        getjFTComprimido().setText(getModelo().getDependencia().getComprimidos());
        getjFTInalantes().setText(getModelo().getDependencia().getInalantes());
        getjFTCocainaA().setText(getModelo().getDependencia().getCocainaA());
        getjFTCocainaI().setText(getModelo().getDependencia().getCocainaI());
        getjFTCrack().setText(getModelo().getDependencia().getCrack());
        getjFTCigarroTabaco().setText(getModelo().getDependencia().getCigarroTabaco());
        getjFTOutrasDrogas().setText(getModelo().getDependencia().getOutrasDrogas());

    }

    public void updateAbaQuestionario() {
        /*painel questionario*/
        getjTAConhecimentoColonia().setText(getModelo().getQuestionario().getConhecimentoColonia());
        getjTAExpectativaTratamento().setText(getModelo().getQuestionario().getExpectativaTratamento());
        getjTAMotivoLevouDrogas().setText(getModelo().getQuestionario().getMotivoUsarDrogas());
        getjTASexualidade().setText(getModelo().getQuestionario().getSexualidade());
        getjTAProcessosnaJustica().setText(getModelo().getQuestionario().getProcessoJustica());
        getjTARelacionamentoAfetivo().setText(getModelo().getQuestionario().getPossuiRelacionamentoAfetivo());
        getjTAGrupoApoio().setText(getModelo().getQuestionario().getGrupoApoio());
        getjTARelacionamentoSocial().setText(getModelo().getQuestionario().getRelacionamentoSocial());

        /*painel tratamentos anteriores*/
        getjFTClinica().setText(getModelo().getTratamentosAnteriores().getClinica());
        /*primeira atribuição do metodo convertStringToDate sempre gera exeção*/
        //if (!inicializando) {
        // getjDCDataEntradaClinica().setDate(convertStringToDate(getModelo().getTratamentosAnteriores().getDataEntrada()));
        //Date d = convertStringToDate(getModelo().getTratamentosAnteriores().getDataEntrada());
        /*if(getModelo().getTratamentosAnteriores().getDataEntrada()==null)
            ((JTextField) jDCDataEntradaClinica.getDateEditor().getUiComponent()).setText("");
        else*/
            jDCDataEntradaClinica.setDate(getModelo().getTratamentosAnteriores().getDataEntrada());
         
        //d = convertStringToDate(getModelo().getTratamentosAnteriores().getDataSaida());
        /*if(getModelo().getTratamentosAnteriores().getDataSaida()==null)
            ((JTextField) jDCDataSaidaClinica.getDateEditor().getUiComponent()).setText("");
        else*/
            jDCDataSaidaClinica.setDate(getModelo().getTratamentosAnteriores().getDataSaida());
        
        
        getjTAMotivoSaida().setText(getModelo().getTratamentosAnteriores().getMotivoSaida());
    }

    public void updateAbaOutrasInformacoes() {
        /*painel rnda*/
        getjFTRendaPessoal().setText(getModelo().getSituacaoEconomica().getRendaPessoal());
        getjFTRendaFamiliar().setText(getModelo().getSituacaoEconomica().getRendaFamilia());
        getjFTRendaFamiliarSeparado().setText(getModelo().getSituacaoEconomica().getRendaFamiliaSeparados());
        getjFTAjudaFinanceira().setText(getModelo().getSituacaoEconomica().getAjudaFinanceira());

        /*Profissao*/
        getjFTTrabalhoAtual().setText(getModelo().getProfissao().getTrabalhoAtual());
        getjFTAposentadoria().setText(getModelo().getProfissao().getAposentado());
        getjFTSalarioDesemprego().setText(getModelo().getProfissao().getSalarioDesemprego());
        getjFTCarteiraAssinada().setText(getModelo().getProfissao().getTrabalhoCA());
        getjFTAfastamentoPrevidenciaSocial().setText(getModelo().getProfissao().getAfastadoPS());
        getjCBEscolaridade().setSelectedItem(getModelo().getProfissao().getEscolaridade());

        /*painel processo*/
        getjFTEntrevistadoraUm().setText(getModelo().getProcesso().getEntrevistadoraUm());
        getjFTEntrevistadoraDois().setText(getModelo().getProcesso().getEntrevistadoraDois());
        getjFTEntrevistadoraInternamento().setText(getModelo().getProcesso().getEntrevistadoraInternamento());
        getjFTEntrevistadoraSaida().setText(getModelo().getProcesso().getEntrevistadoraSaida());
         
       /* Date d = convertStringToDate(getModelo().getProcesso().getDataUm());
        if(d==null)
            ((JTextField) jDCEntrevistaUm.getDateEditor().getUiComponent()).setText("");
        else*/
            jDCEntrevistaUm.setDate(getModelo().getProcesso().getDataUm());
        
         /*d = convertStringToDate(getModelo().getProcesso().getDataDois());
        if(d==null)
            ((JTextField) jDCEntrevistaDois.getDateEditor().getUiComponent()).setText("");
        else*/
            jDCEntrevistaDois.setDate(getModelo().getProcesso().getDataDois());
        
        /*d = convertStringToDate(getModelo().getProcesso().getDataInternamento());
        if(d==null)
            ((JTextField) jDCEntrevistaInternamento.getDateEditor().getUiComponent()).setText("");
        else*/
            jDCEntrevistaInternamento.setDate(getModelo().getProcesso().getDataInternamento());
        
        /*d = convertStringToDate(getModelo().getProcesso().getDataSaida());
        if(d==null)
            ((JTextField) jDCEntrevistaSaida.getDateEditor().getUiComponent()).setText("");
        else*/
            jDCEntrevistaSaida.setDate(getModelo().getProcesso().getDataSaida());
        
        //}
        getjCBStatus().setSelectedItem(getModelo().getProcesso().getStatus());

        getjTAObservacoes().setText(getModelo().getQuestionario().getObservacoes());
    }

    public void testaCampos(JPanel jp) {

        for (Component comp : jp.getComponents()) {
            if (comp instanceof JTextField) {
                /*if (((JTextField) comp).getText().indexOf("/") >= 0) {
                 ((JTextField) comp).setText("88/88/8888");
                 } else */
                if (((JTextField) comp).getText().indexOf("(") >= 0) {
                    ((JTextField) comp).setText("(12)9888-5555");

                } else if (((JTextField) comp).getText().indexOf(".") >= 0) {
                    ((JTextField) comp).setText("333.222.444-45");
                } else if (((JTextField) comp).getText().indexOf("-") >= 0) {
                    ((JTextField) comp).setText("12240-563");
                } else {

//                    ((JTextField) comp).setText(ini);
                }

            } else if (comp instanceof JComboBox) {
                ((JComboBox) comp).setSelectedIndex(1);
            } else if (comp instanceof JDateChooser) {
                //((JDateChooser) comp).setDateFormatString("88/88/8888");
            } else if (comp instanceof JTextArea) {
                //desnecessarfio nao esta sendo usadda 
                //    ((JTextArea) comp).setText(ini);
            } else if (comp instanceof JFormattedTextField) {
            } else if (comp instanceof JRadioButton) {
                ((JRadioButton) comp).setSelected(true);
            } else if (comp instanceof JCheckBox) {
                ((JCheckBox) comp).setSelected(true);
            } else if (comp instanceof JScrollPane) {
                Object[] compo = ((JScrollPane) comp).getComponents();
                JViewport rep = (JViewport) compo[0];
                //        ((JTextArea) rep.getComponent(0)).setText(ini);

            }
        }

    }

    public void testeVisao() {

        testaCampos(getjPDadosPessoais());
        testaCampos(getjPDocumentacao());
        testaCampos(getjPComQuemMora());
        testaCampos(getjPEndereco());
        testaCampos(getjPSaude());
        testaCampos(getjPDependencia());
        testaCampos(getjPDrogas());
        testaCampos(getjPQuestionario());
        testaCampos(getjPTratamentosAnteriores());
        testaCampos(getjPRenda());
        testaCampos(getjPProfissao());
        testaCampos(getjPDadosResponsavel());
        testaCampos(getjPEnderecoBoleto());
        testaCampos(getjPProcesso());
        //familia        

        Iterator<Component> it = getComponentesTeste().iterator();
        while (it.hasNext()) {
            Component comp = it.next();
            if (comp instanceof JTextField) {
                if (((JTextField) comp).getText().indexOf("/") >= 0) {
                    ((JTextField) comp).setText("00/00/0000");
                } else if (((JTextField) comp).getText().indexOf("(") >= 0) {
                    ((JTextField) comp).setText("(00)0000-0000");

                } else if (((JTextField) comp).getText().indexOf(".") >= 0) {
                    ((JTextField) comp).setText("000.000.000-00");
                } else if (((JTextField) comp).getText().indexOf("-") >= 0) {
                    ((JTextField) comp).setText("00000-000");
                } else if (((JTextField) comp).getDocument().equals(new teclasPermitidas())) {
                    ((JTextField) comp).setText("0123");
                } else {

                    ((JTextField) comp).setText("*****");
                }
            }
            if (comp instanceof JComboBox) {
                ((JComboBox) comp).setSelectedIndex(1);
            } else if (comp instanceof JTextArea) {
                //desnecessarfio nao esta sendo usadda 
                ((JTextArea) comp).setText("*****");
            } else if (comp instanceof JFormattedTextField) {
            } else if (comp instanceof JRadioButton) {
                ((JRadioButton) comp).setSelected(true);
            } else if (comp instanceof JCheckBox) {
                ((JCheckBox) comp).setSelected(true);
            } else if (comp instanceof JScrollPane) {
                Object[] compo = ((JScrollPane) comp).getComponents();
                JViewport rep = (JViewport) compo[0];
                ((JTextArea) rep.getComponent(0)).setText("****");
            }
        }

    }

    public ImageIcon GeraThumbnail(ImageIcon imagem, int labelWidth) {
        ImageIcon thumbnail = null;
        if (imagem.getIconWidth() > labelWidth) {
            thumbnail = new ImageIcon(
                    imagem.getImage().getScaledInstance(labelWidth, -1, Image.SCALE_DEFAULT));
        } else {
            thumbnail = imagem;
        }

        return thumbnail;
    }

    public void componenteEvento() {

        //adciona botoes controle ao array list 
        compPainel(getjPControles());
        // compPainel(getjPControlesFamilia());
        compPainel(getjPControlesFamilia());
        //addicoina botoes atalhos ao array list
        getComponentes().add(getjBIAlterar());
        getComponentes().add(getjBICadastrar());
        getComponentes().add(getjBICancelar());
        getComponentes().add(getjBIExcluir());
        getComponentes().add(getjBIFechar());
        getComponentes().add(getjBINovo());
        getComponentes().add(getjBIPesquisar());
        //addicoina menus ao array list
        getComponentes().add(getjMBMenu());
        getComponentes().add(getjMSair());
        getComponentes().add(getjMOpcoes());
        getComponentes().add(getjMenuAlterar());
        getComponentes().add(getjMenuCadastrar());
        getComponentes().add(getjMenuCancelar());
        getComponentes().add(getjMenuExcluir());
        getComponentes().add(getjMenuFechar());
        getComponentes().add(getjMenuNovo());
        getComponentes().add(getjMenuPesquisar());
        //getComponentes().add(getjMenuTesteConexao());
        getComponentes().add(getjMenuBancoDados());
        getComponentes().add(getjMIFechar());

        //booes foto
        getComponentes().add(getjBAlterarFotoAntes());
        getComponentes().add(getjBAlterarFotoDepois());
        //boao procura
        getComponentes().add(getjBProcurar());
        getComponentes().add(getjTFamilia());
        getComponentes().add(getjTPesquisa());
        getComponentes().add(getjBAtualizarTodos());

        getComponentes().add(getjRBEnderecoBoletoSim());
        getComponentes().add(getjRBEnderecoBoletoNao());
        
        getComponentes().add(jBGerarRelatorio);

    }

    public void compPainel(JPanel p) {
        for (Component c : p.getComponents()) {
            if (c != null) {
                //  System.out.println("**");
                getComponentes().add(c);
            }
        }
    }

    public void compPainelInicia(JPanel p) {
        for (Component c : p.getComponents()) {
            if (c != null) {
                //  System.out.println("**");
                getInciaComp().add(c);
            }
        }
    }

    public void compPainelTeste(JPanel p) {
        for (Component c : p.getComponents()) {
            if (c != null) {
                //  System.out.println("**");
                getComponentesTeste().add(c);
            }
        }
    }

    public void adicionaEventos(Controlador controlador) {
        controlador.actionPerformed(null);
    }

    public void removeEventos(Controlador controlador) {
    }

    protected Controlador constroiControlador() {
        return new Controlador(this, getModelo());
    }

    public void anexarControle(Controlador controlador) {

        componenteEvento();
        if (this.getControlador() != null) {
            Iterator<Component> it = getComponentes().iterator();
            while (it.hasNext()) {
                Component comp = it.next();
                if (comp instanceof JButton) {
                    ((JButton) comp).removeActionListener(this.getControlador());
                } else if (comp instanceof JMenuItem) {
                    ((JMenuItem) comp).removeActionListener(this.getControlador());
                } else if (comp instanceof JTextField) {
                    ((JTextField) comp).removeActionListener(this.getControlador());
                } else if (comp instanceof JRadioButton) {
                    ((JRadioButton) comp).removeActionListener(this.getControlador());
                }

            }
        }

        this.setControlador(controlador);

        Iterator<Component> it = getComponentes().iterator();
        while (it.hasNext()) {
            Component comp = it.next();
            if (comp instanceof JButton) {
                ((JButton) comp).addActionListener(controlador);
            } else if (comp instanceof JMenuItem) {
                ((JMenuItem) comp).addActionListener(controlador);
            } else if (comp instanceof JTextField) {
                ((JTextField) comp).addActionListener(this.getControlador());
            } else if (comp instanceof JRadioButton) {
                ((JRadioButton) comp).addActionListener(this.getControlador());
            }
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bGPesquisarPor = new javax.swing.ButtonGroup();
        bGEnderecoBoleto = new javax.swing.ButtonGroup();
        bGSexo = new javax.swing.ButtonGroup();
        jPComQuemMora = new javax.swing.JPanel();
        jTFComQuemMora = new javax.swing.JTextField();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jTPAbas = new javax.swing.JTabbedPane();
        jPInternoGeral = new javax.swing.JPanel();
        jPDadosPessoais = new javax.swing.JPanel();
        jLNome = new javax.swing.JLabel();
        jLDatadeNascimento = new javax.swing.JLabel();
        jLIdade = new javax.swing.JLabel();
        jFTIdade = new javax.swing.JFormattedTextField();
        jLEstadoCivil = new javax.swing.JLabel();
        jCBEstadoCivil = new javax.swing.JComboBox();
        jFTNome = new javax.swing.JFormattedTextField();
        jLNaturalidade = new javax.swing.JLabel();
        jFTNaturalidade = new javax.swing.JFormattedTextField();
        jLSexo = new javax.swing.JLabel();
        jRBMasculino = new javax.swing.JRadioButton();
        jRBFemenino = new javax.swing.JRadioButton();
        jDCDataNascimento = new com.toedter.calendar.JDateChooser();
        jPDocumentacao = new javax.swing.JPanel();
        jLCPF = new javax.swing.JLabel();
        jLRG = new javax.swing.JLabel();
        jLPIS = new javax.swing.JLabel();
        jFTCPF = new javax.swing.JFormattedTextField();
        jFTRG = new javax.swing.JFormattedTextField();
        jFTPIS = new javax.swing.JFormattedTextField();
        jLTituloEleitoral = new javax.swing.JLabel();
        jFTTituloEleitoral = new javax.swing.JFormattedTextField();
        jLSecao = new javax.swing.JLabel();
        jFTSecao = new javax.swing.JFormattedTextField();
        jLZona = new javax.swing.JLabel();
        jFTZona = new javax.swing.JFormattedTextField();
        jLCarteiradeTrabalho = new javax.swing.JLabel();
        jFTCarteiradeTrabalho = new javax.swing.JFormattedTextField();
        jPEndereco = new javax.swing.JPanel();
        jLLogradouro = new javax.swing.JLabel();
        jFTRua = new javax.swing.JFormattedTextField();
        jLBairro = new javax.swing.JLabel();
        jFTBairro = new javax.swing.JFormattedTextField();
        jLNumero = new javax.swing.JLabel();
        jFTNumero = new javax.swing.JFormattedTextField();
        jLComplemento = new javax.swing.JLabel();
        jFTComplemento = new javax.swing.JFormattedTextField();
        jLCEP = new javax.swing.JLabel();
        jLTelefone = new javax.swing.JLabel();
        jFTTelefone = new javax.swing.JFormattedTextField();
        jLCidade = new javax.swing.JLabel();
        jFTCEP = new javax.swing.JFormattedTextField();
        jFTCidade = new javax.swing.JFormattedTextField();
        jLUF = new javax.swing.JLabel();
        jCBUF = new javax.swing.JComboBox();
        jPFoto = new javax.swing.JPanel();
        jLFotoAntes = new javax.swing.JLabel();
        jLFotoDepois = new javax.swing.JLabel();
        jBAlterarFotoAntes = new javax.swing.JButton();
        jBAlterarFotoDepois = new javax.swing.JButton();
        jPComQuemMoraAtualmente = new javax.swing.JPanel();
        jTFComQuemMoraAtualmente = new javax.swing.JTextField();
        jPFamiliaGeral = new javax.swing.JPanel();
        jPFamiliaeSocial = new javax.swing.JPanel();
        jLNomeFamilia = new javax.swing.JLabel();
        jFTNomeFamilia = new javax.swing.JFormattedTextField();
        jLParentesco = new javax.swing.JLabel();
        jCBParentesco = new javax.swing.JComboBox();
        jLEstadoCivilFamilia = new javax.swing.JLabel();
        jCBEstadoCivilFamilia = new javax.swing.JComboBox();
        jLIdadeFamilia = new javax.swing.JLabel();
        jFTIdadeFamilia = new javax.swing.JFormattedTextField();
        jLProfissao = new javax.swing.JLabel();
        jFTProfissao = new javax.swing.JFormattedTextField();
        jLCasodeDrogas = new javax.swing.JLabel();
        jFTCasodeDrogas = new javax.swing.JFormattedTextField();
        jLRelacionamento = new javax.swing.JLabel();
        jFTRelacionamento = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        jFTFalecido = new javax.swing.JFormattedTextField();
        jLFalecido = new javax.swing.JLabel();
        jFTTempoCasado = new javax.swing.JFormattedTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTFamilia = new javax.swing.JTable();
        jPControlesFamilia = new javax.swing.JPanel();
        jBNovoFamilia = new javax.swing.JButton();
        jBCadastrarFamilia = new javax.swing.JButton();
        jBCancelarFamilia = new javax.swing.JButton();
        jBAlterarFamilia = new javax.swing.JButton();
        jBExcluirFamilia = new javax.swing.JButton();
        jPDependenciaGeral = new javax.swing.JPanel();
        jPSaude = new javax.swing.JPanel();
        jLSono = new javax.swing.JLabel();
        jLAlimentacao = new javax.swing.JLabel();
        jLMedicacao = new javax.swing.JLabel();
        jSPAlimentacao = new javax.swing.JScrollPane();
        jTAAlimentacao = new javax.swing.JTextArea();
        jSPSono = new javax.swing.JScrollPane();
        jTASono = new javax.swing.JTextArea();
        jSPMedicacao = new javax.swing.JScrollPane();
        jTAMedicacao = new javax.swing.JTextArea();
        jLExamesEspecificos = new javax.swing.JLabel();
        jSPExamesEspecificos = new javax.swing.JScrollPane();
        jTAExamesEspecificos = new javax.swing.JTextArea();
        jLAlucinacaoDelirios = new javax.swing.JLabel();
        jLDemaisConvulsoes = new javax.swing.JLabel();
        jSPDemaisConvulsoes = new javax.swing.JScrollPane();
        jTADemaisConvulsoes = new javax.swing.JTextArea();
        jLAutoextermino = new javax.swing.JLabel();
        jSPAutoexterminio = new javax.swing.JScrollPane();
        jTAAutoextermino = new javax.swing.JTextArea();
        jSPAlucinacaoDelirio = new javax.swing.JScrollPane();
        jTAAlucinacaoDelirio = new javax.swing.JTextArea();
        jPDependencia = new javax.swing.JPanel();
        jLTempodeUso = new javax.swing.JLabel();
        jSPTempodeUso = new javax.swing.JScrollPane();
        jTATempodeUso = new javax.swing.JTextArea();
        jLUsadaAtualmente = new javax.swing.JLabel();
        jSPUsadaAtualmente = new javax.swing.JScrollPane();
        jTAUsadaAtualmente = new javax.swing.JTextArea();
        jPDrogas = new javax.swing.JPanel();
        jFTInalantes = new javax.swing.JFormattedTextField();
        jFTCigarroTabaco = new javax.swing.JFormattedTextField();
        jFTAlcool = new javax.swing.JFormattedTextField();
        jFTMaconha = new javax.swing.JFormattedTextField();
        jFTCocainaI = new javax.swing.JFormattedTextField();
        jFTCocainaA = new javax.swing.JFormattedTextField();
        jFTCrack = new javax.swing.JFormattedTextField();
        jFTOutrasDrogas = new javax.swing.JFormattedTextField();
        jFTComprimido = new javax.swing.JFormattedTextField();
        jLMaconha = new javax.swing.JLabel();
        jLAlcool = new javax.swing.JLabel();
        jLInalantes = new javax.swing.JLabel();
        jLCocainaA = new javax.swing.JLabel();
        jLCocainaI = new javax.swing.JLabel();
        jLCrack = new javax.swing.JLabel();
        jLCigarroTabaco = new javax.swing.JLabel();
        jLComprimido = new javax.swing.JLabel();
        jLOutrasDrogas = new javax.swing.JLabel();
        jPQuestionarioGeral = new javax.swing.JPanel();
        jPQuestionario = new javax.swing.JPanel();
        jLConhecimentoColonia = new javax.swing.JLabel();
        jSPConhecimentoColonia = new javax.swing.JScrollPane();
        jTAConhecimentoColonia = new javax.swing.JTextArea();
        jLExpectativaTratamento = new javax.swing.JLabel();
        jSPExpectativaTratamento = new javax.swing.JScrollPane();
        jTAExpectativaTratamento = new javax.swing.JTextArea();
        jLSexualidade = new javax.swing.JLabel();
        jLProcessosnaJustica = new javax.swing.JLabel();
        jLRelacionamentoAfetivo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTARelacionamentoAfetivo = new javax.swing.JTextArea();
        jLGrupoApoio = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTAMotivoLevouDrogas = new javax.swing.JTextArea();
        jLRelacionamentoSocial = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTARelacionamentoSocial = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTASexualidade = new javax.swing.JTextArea();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTAGrupoApoio = new javax.swing.JTextArea();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTAProcessosnaJustica = new javax.swing.JTextArea();
        jPTratamentosAnteriores = new javax.swing.JPanel();
        jLClinica = new javax.swing.JLabel();
        jFTClinica = new javax.swing.JFormattedTextField();
        jLDatadeEntrada = new javax.swing.JLabel();
        jLDatadeSaida = new javax.swing.JLabel();
        jLMotivoSaida = new javax.swing.JLabel();
        jSPLocal = new javax.swing.JScrollPane();
        jTAMotivoSaida = new javax.swing.JTextArea();
        jDCDataEntradaClinica = new com.toedter.calendar.JDateChooser();
        jDCDataSaidaClinica = new com.toedter.calendar.JDateChooser();
        jPOutrasInformacoesGeral = new javax.swing.JPanel();
        jPRenda = new javax.swing.JPanel();
        jLPessoal = new javax.swing.JLabel();
        jLFamiliar = new javax.swing.JLabel();
        jLFamiliarSeparados = new javax.swing.JLabel();
        jFTRendaPessoal = new javax.swing.JFormattedTextField();
        jFTRendaFamiliar = new javax.swing.JFormattedTextField();
        jFTRendaFamiliarSeparado = new javax.swing.JFormattedTextField();
        jFTAjudaFinanceira = new javax.swing.JFormattedTextField();
        jLPossuiAlguemAjudarFinanceira = new javax.swing.JLabel();
        jPProfissao = new javax.swing.JPanel();
        jLTrabalhoAtual = new javax.swing.JLabel();
        jLCarteiraAssinada = new javax.swing.JLabel();
        jLAposentadoria = new javax.swing.JLabel();
        jLAfastamentoPrevidenciaSocial = new javax.swing.JLabel();
        jLSalarioDesemprego = new javax.swing.JLabel();
        jLEscolaridade = new javax.swing.JLabel();
        jFTTrabalhoAtual = new javax.swing.JFormattedTextField();
        jFTAposentadoria = new javax.swing.JFormattedTextField();
        jFTSalarioDesemprego = new javax.swing.JFormattedTextField();
        jFTCarteiraAssinada = new javax.swing.JFormattedTextField();
        jFTAfastamentoPrevidenciaSocial = new javax.swing.JFormattedTextField();
        jCBEscolaridade = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jPProcesso = new javax.swing.JPanel();
        jLSaida = new javax.swing.JLabel();
        jLEntrevistadora1 = new javax.swing.JLabel();
        jFTEntrevistadoraUm = new javax.swing.JFormattedTextField();
        jLDataPriEntre = new javax.swing.JLabel();
        jLEntrevistadora2 = new javax.swing.JLabel();
        jFTEntrevistadoraDois = new javax.swing.JFormattedTextField();
        jLDataSegEntre = new javax.swing.JLabel();
        jDCEntrevistaDois = new com.toedter.calendar.JDateChooser();
        jLEntrevistadoraInternamento = new javax.swing.JLabel();
        jFTEntrevistadoraInternamento = new javax.swing.JFormattedTextField();
        jDCEntrevistaUm = new com.toedter.calendar.JDateChooser();
        jDCEntrevistaInternamento = new com.toedter.calendar.JDateChooser();
        jDCEntrevistaSaida = new com.toedter.calendar.JDateChooser();
        jLDataSaida = new javax.swing.JLabel();
        jLDataInternamento = new javax.swing.JLabel();
        jFTEntrevistadoraSaida = new javax.swing.JFormattedTextField();
        jLStatus = new javax.swing.JLabel();
        jCBStatus = new javax.swing.JComboBox();
        jPBoleto = new javax.swing.JPanel();
        jPEnderecoBoleto = new javax.swing.JPanel();
        jLLogradouroBoleto = new javax.swing.JLabel();
        jFTRuaBoleto = new javax.swing.JFormattedTextField();
        jLBairroBoleto = new javax.swing.JLabel();
        jFTBairroBoleto = new javax.swing.JFormattedTextField();
        jLNumero1 = new javax.swing.JLabel();
        jFTNumeroBoleto = new javax.swing.JFormattedTextField();
        jLComplementoBoleto = new javax.swing.JLabel();
        jFTComplementoBoleto = new javax.swing.JFormattedTextField();
        jLCEPBoleto = new javax.swing.JLabel();
        jLTelefoneBoleto = new javax.swing.JLabel();
        jFTTelefoneBoleto = new javax.swing.JFormattedTextField();
        jLCidadeBoleto = new javax.swing.JLabel();
        jFTCEPBoleto = new javax.swing.JFormattedTextField();
        jFTCidadeBoleto = new javax.swing.JFormattedTextField();
        jLUFBoleto = new javax.swing.JLabel();
        jCBUFBoleto = new javax.swing.JComboBox();
        jPEnderecoBoletoInternoSimNao = new javax.swing.JPanel();
        jRBEnderecoBoletoSim = new javax.swing.JRadioButton();
        jRBEnderecoBoletoNao = new javax.swing.JRadioButton();
        jPDadosResponsavel = new javax.swing.JPanel();
        jFTNomeResponsavel = new javax.swing.JFormattedTextField();
        jLNomeResponsavel = new javax.swing.JLabel();
        jFTVencimentoBoleto = new javax.swing.JFormattedTextField();
        jLTelefoneResponsavel = new javax.swing.JLabel();
        jLVencimento = new javax.swing.JLabel();
        jFTContatoResponsavel = new javax.swing.JFormattedTextField();
        jPObservacao = new javax.swing.JPanel();
        jSPObservacoes = new javax.swing.JScrollPane();
        jTAObservacoes = new javax.swing.JTextArea();
        javax.swing.JPanel jPRelatorios = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jRBRelatorioPeriodo = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        jDCInicio = new com.toedter.calendar.JDateChooser();
        jDCFim = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jBGerarRelatorio = new javax.swing.JButton();
        jPPesquisaGeral = new javax.swing.JPanel();
        jSPPesquisa = new javax.swing.JScrollPane();
        jTPesquisa = new javax.swing.JTable();
        jPPesquisarPor = new javax.swing.JPanel();
        jRBNome = new javax.swing.JRadioButton();
        jRBCPF = new javax.swing.JRadioButton();
        jPPesquisar = new javax.swing.JPanel();
        jBProcurar = new javax.swing.JButton();
        jTFProcurar = new javax.swing.JTextField();
        jPMostrarTodos = new javax.swing.JPanel();
        jBAtualizarTodos = new javax.swing.JButton();
        jPControles = new javax.swing.JPanel();
        jBNovo = new javax.swing.JButton();
        jBCadastrar = new javax.swing.JButton();
        jBCancelar = new javax.swing.JButton();
        jBAlterar = new javax.swing.JButton();
        jBExcluir = new javax.swing.JButton();
        jBPesquisar = new javax.swing.JButton();
        jBFechar = new javax.swing.JButton();
        jPIControles = new javax.swing.JPanel();
        jBINovo = new javax.swing.JButton();
        jBICadastrar = new javax.swing.JButton();
        jBICancelar = new javax.swing.JButton();
        jBIAlterar = new javax.swing.JButton();
        jBIExcluir = new javax.swing.JButton();
        jBIPesquisar = new javax.swing.JButton();
        jBIFechar = new javax.swing.JButton();
        jLResidenteNumero = new javax.swing.JLabel();
        jTFID = new javax.swing.JTextField();
        jMBMenu = new javax.swing.JMenuBar();
        jMOpcoes = new javax.swing.JMenu();
        jMenuNovo = new javax.swing.JMenuItem();
        jMenuCadastrar = new javax.swing.JMenuItem();
        jMenuCancelar = new javax.swing.JMenuItem();
        jMenuAlterar = new javax.swing.JMenuItem();
        jMenuExcluir = new javax.swing.JMenuItem();
        jMenuPesquisar = new javax.swing.JMenuItem();
        jMenuFechar = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuTesteConexao = new javax.swing.JMenuItem();
        jMRelatorios = new javax.swing.JMenu();
        jMConexao = new javax.swing.JMenu();
        jMenuBancoDados = new javax.swing.JMenuItem();
        jMSair = new javax.swing.JMenu();
        jMIFechar = new javax.swing.JMenuItem();

        jPComQuemMora.setBorder(javax.swing.BorderFactory.createTitledBorder("Com quem mora atualmente"));

        javax.swing.GroupLayout jPComQuemMoraLayout = new javax.swing.GroupLayout(jPComQuemMora);
        jPComQuemMora.setLayout(jPComQuemMoraLayout);
        jPComQuemMoraLayout.setHorizontalGroup(
            jPComQuemMoraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPComQuemMoraLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTFComQuemMora)
                .addContainerGap())
        );
        jPComQuemMoraLayout.setVerticalGroup(
            jPComQuemMoraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPComQuemMoraLayout.createSequentialGroup()
                .addComponent(jTFComQuemMora, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                .addContainerGap())
        );

        jMenuItem1.setText("jMenuItem1");

        jMenuItem2.setText("jMenuItem2");

        jMenuItem3.setText("jMenuItem3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("FramePrincipal"); // NOI18N
        setResizable(false);

        jTPAbas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTPAbasMouseClicked(evt);
            }
        });

        jPInternoGeral.setMaximumSize(new java.awt.Dimension(933, 477));
        jPInternoGeral.setPreferredSize(new java.awt.Dimension(933, 477));

        jPDadosPessoais.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados Pessoais"));

        jLNome.setText("Nome:");

        jLDatadeNascimento.setText("Data de Nascimento:");

        jLIdade.setText("Idade:");

        jFTIdade.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        jLEstadoCivil.setText("Estado Civil:");

        jCBEstadoCivil.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NULL", "Solteiro", "Casado", "Divorciado", "Separado", "Víuvo", "Amasiado", "Desquitado", "Outros" }));

        jLNaturalidade.setText("Naturalidade:");

        jLSexo.setText("Sexo:");

        bGSexo.add(jRBMasculino);
        jRBMasculino.setText("Masculino");

        bGSexo.add(jRBFemenino);
        jRBFemenino.setText("Femenino");

        javax.swing.GroupLayout jPDadosPessoaisLayout = new javax.swing.GroupLayout(jPDadosPessoais);
        jPDadosPessoais.setLayout(jPDadosPessoaisLayout);
        jPDadosPessoaisLayout.setHorizontalGroup(
            jPDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPDadosPessoaisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLNome)
                    .addComponent(jLDatadeNascimento)
                    .addComponent(jLNaturalidade))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jFTNome)
                    .addGroup(jPDadosPessoaisLayout.createSequentialGroup()
                        .addGroup(jPDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jFTNaturalidade, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPDadosPessoaisLayout.createSequentialGroup()
                                .addComponent(jDCDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLIdade, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jFTIdade, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(24, 24, 24)
                        .addGroup(jPDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLEstadoCivil)
                            .addComponent(jLSexo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCBEstadoCivil, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPDadosPessoaisLayout.createSequentialGroup()
                                .addComponent(jRBMasculino)
                                .addGap(18, 18, 18)
                                .addComponent(jRBFemenino)
                                .addGap(0, 102, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPDadosPessoaisLayout.setVerticalGroup(
            jPDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPDadosPessoaisLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFTNome, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLNome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLDatadeNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLIdade, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLEstadoCivil)
                        .addComponent(jCBEstadoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPDadosPessoaisLayout.createSequentialGroup()
                        .addComponent(jFTIdade, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jDCDataNascimento, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLNaturalidade)
                    .addComponent(jFTNaturalidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLSexo)
                    .addComponent(jRBMasculino)
                    .addComponent(jRBFemenino))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        jPDocumentacao.setBorder(javax.swing.BorderFactory.createTitledBorder("Documentação"));

        jLCPF.setText("CPF:");

        jLRG.setText("RG:");

        jLPIS.setText("PIS:");

        try {
            jFTCPF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLTituloEleitoral.setText("Título Eleitoral:");

        jLSecao.setText("Seção:");

        jLZona.setText("Zona:");

        jLCarteiradeTrabalho.setText("Carteira de Trabalho:");

        javax.swing.GroupLayout jPDocumentacaoLayout = new javax.swing.GroupLayout(jPDocumentacao);
        jPDocumentacao.setLayout(jPDocumentacaoLayout);
        jPDocumentacaoLayout.setHorizontalGroup(
            jPDocumentacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPDocumentacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPDocumentacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPDocumentacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLRG)
                        .addComponent(jLCPF))
                    .addComponent(jLPIS))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPDocumentacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jFTPIS)
                    .addComponent(jFTRG)
                    .addComponent(jFTCPF))
                .addGap(18, 18, 18)
                .addGroup(jPDocumentacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLTituloEleitoral)
                    .addComponent(jLCarteiradeTrabalho)
                    .addComponent(jLSecao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPDocumentacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPDocumentacaoLayout.createSequentialGroup()
                        .addComponent(jFTSecao, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLZona, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFTZona, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jFTCarteiradeTrabalho, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFTTituloEleitoral, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );
        jPDocumentacaoLayout.setVerticalGroup(
            jPDocumentacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPDocumentacaoLayout.createSequentialGroup()
                .addGroup(jPDocumentacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFTCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLTituloEleitoral, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFTTituloEleitoral, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPDocumentacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPDocumentacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLZona, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jFTZona, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jFTSecao, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLSecao))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPDocumentacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLRG, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jFTRG, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPDocumentacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPDocumentacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLCarteiradeTrabalho)
                        .addComponent(jFTCarteiradeTrabalho, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPDocumentacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLPIS, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jFTPIS, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPEndereco.setBorder(javax.swing.BorderFactory.createTitledBorder("Endereço"));

        jLLogradouro.setText("Logradouro:");

        jLBairro.setText("Bairro:");

        jLNumero.setText("Número:");

        jFTNumero.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        jLComplemento.setText("Complemento:");

        jLCEP.setText("CEP:");

        jLTelefone.setText("Telefone:");

        try {
            jFTTelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLCidade.setText("Cidade:");

        try {
            jFTCEP.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLUF.setText("UF:");

        jCBUF.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NULL", "AC - Acre    ", "AL - Alagoas    ", "AM - Amazonas    ", "AP - Amapá    ", "BA - Bahia    ", "CE - Ceará    ", "DF - Distrito Federal    ", "ES - Espirito Santo    ", "GO - Goias    ", "MA - Maranhão    ", "MG - Minas Gerais    ", "MS - Mato Grosso Sul    ", "MT - Mato Grosso    ", "PA - Pará    ", "PB - Paraiba    ", "PE - Pernanbuco    ", "PI - Piaui    ", "PR - Parana    ", "RJ - Rio de Janeiro    ", "RN - Rio Grande do Norte    ", "RO - Rondônia    ", "RR - Roraima    ", "RS - Rio Grande do Sul    ", "SC - Santa Catarina    ", "SE - Sergipe    ", "SP - São Paulo    ", "TO - Tocantins" }));

        javax.swing.GroupLayout jPEnderecoLayout = new javax.swing.GroupLayout(jPEndereco);
        jPEndereco.setLayout(jPEnderecoLayout);
        jPEnderecoLayout.setHorizontalGroup(
            jPEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPEnderecoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLBairro)
                    .addComponent(jLLogradouro)
                    .addComponent(jLCidade))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPEnderecoLayout.createSequentialGroup()
                        .addGroup(jPEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jFTCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFTBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLTelefone)
                            .addComponent(jLCEP))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jFTTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFTCEP, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jFTRua))
                .addGroup(jPEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPEnderecoLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLComplemento))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPEnderecoLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLNumero, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLUF, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCBUF, 0, 1, Short.MAX_VALUE)
                    .addComponent(jFTNumero)
                    .addComponent(jFTComplemento))
                .addContainerGap())
        );
        jPEnderecoLayout.setVerticalGroup(
            jPEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPEnderecoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFTRua, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLLogradouro, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLNumero)
                    .addComponent(jFTNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFTBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFTTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLComplemento)
                    .addComponent(jFTComplemento, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFTCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLCEP, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFTCEP, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCBUF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLUF))
                .addContainerGap())
        );

        jPFoto.setBorder(javax.swing.BorderFactory.createTitledBorder("Foto"));

        jLFotoAntes.setBackground(new java.awt.Color(255, 255, 255));
        jLFotoAntes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 255)));

        jLFotoDepois.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 255)));

        jBAlterarFotoAntes.setText("Foto Antes");
        jBAlterarFotoAntes.setActionCommand("ALTERAR_FOTO_ANTES");

        jBAlterarFotoDepois.setText("Foto Depois");
        jBAlterarFotoDepois.setActionCommand("ALTERAR_FOTO_DEPOIS");

        javax.swing.GroupLayout jPFotoLayout = new javax.swing.GroupLayout(jPFoto);
        jPFoto.setLayout(jPFotoLayout);
        jPFotoLayout.setHorizontalGroup(
            jPFotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPFotoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPFotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPFotoLayout.createSequentialGroup()
                        .addGap(0, 4, Short.MAX_VALUE)
                        .addComponent(jBAlterarFotoAntes, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jBAlterarFotoDepois, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLFotoDepois, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLFotoAntes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPFotoLayout.setVerticalGroup(
            jPFotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPFotoLayout.createSequentialGroup()
                .addComponent(jLFotoAntes, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jBAlterarFotoAntes)
                .addGap(18, 18, 18)
                .addComponent(jLFotoDepois, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBAlterarFotoDepois)
                .addContainerGap())
        );

        jPComQuemMoraAtualmente.setBorder(javax.swing.BorderFactory.createTitledBorder("Com quem mora atualmente"));

        javax.swing.GroupLayout jPComQuemMoraAtualmenteLayout = new javax.swing.GroupLayout(jPComQuemMoraAtualmente);
        jPComQuemMoraAtualmente.setLayout(jPComQuemMoraAtualmenteLayout);
        jPComQuemMoraAtualmenteLayout.setHorizontalGroup(
            jPComQuemMoraAtualmenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPComQuemMoraAtualmenteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTFComQuemMoraAtualmente)
                .addContainerGap())
        );
        jPComQuemMoraAtualmenteLayout.setVerticalGroup(
            jPComQuemMoraAtualmenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPComQuemMoraAtualmenteLayout.createSequentialGroup()
                .addComponent(jTFComQuemMoraAtualmente, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 5, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPInternoGeralLayout = new javax.swing.GroupLayout(jPInternoGeral);
        jPInternoGeral.setLayout(jPInternoGeralLayout);
        jPInternoGeralLayout.setHorizontalGroup(
            jPInternoGeralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPInternoGeralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPInternoGeralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPDocumentacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPDadosPessoais, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPEndereco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPComQuemMoraAtualmente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPFoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPInternoGeralLayout.setVerticalGroup(
            jPInternoGeralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPInternoGeralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPInternoGeralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPFoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPInternoGeralLayout.createSequentialGroup()
                        .addComponent(jPDadosPessoais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPDocumentacao, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPComQuemMoraAtualmente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTPAbas.addTab("Interno", jPInternoGeral);

        jPFamiliaeSocial.setBorder(javax.swing.BorderFactory.createTitledBorder("Família e social"));

        jLNomeFamilia.setText("Nome:");

        jLParentesco.setText("Parentesco:");

        jCBParentesco.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NULL", "Esposa(o)", "Avó", "Avô", "Filha", "Filho", "Irmã", "Irmão", "Mãe", "Pai", "Sobrinha", "Sobrinho", "Tia", "Tio", "Outros" }));

        jLEstadoCivilFamilia.setText("Estado Civil:");

        jCBEstadoCivilFamilia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NULL", "Solteiro", "Casado", "Divorciado", "Viúvo", "Outros" }));

        jLIdadeFamilia.setText("Idade:");

        jFTIdadeFamilia.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        jLProfissao.setText("Profissão:");

        jLCasodeDrogas.setText("Caso de Drogas:");

        jLRelacionamento.setText("Relacionamento:");

        jLabel3.setText("Tempo de casados:");

        jLFalecido.setText("Falecido:");

        javax.swing.GroupLayout jPFamiliaeSocialLayout = new javax.swing.GroupLayout(jPFamiliaeSocial);
        jPFamiliaeSocial.setLayout(jPFamiliaeSocialLayout);
        jPFamiliaeSocialLayout.setHorizontalGroup(
            jPFamiliaeSocialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPFamiliaeSocialLayout.createSequentialGroup()
                .addGroup(jPFamiliaeSocialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLFalecido)
                    .addGroup(jPFamiliaeSocialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPFamiliaeSocialLayout.createSequentialGroup()
                            .addGap(32, 32, 32)
                            .addGroup(jPFamiliaeSocialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLNomeFamilia)
                                .addComponent(jLParentesco)
                                .addComponent(jLProfissao)))
                        .addGroup(jPFamiliaeSocialLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLRelacionamento))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPFamiliaeSocialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPFamiliaeSocialLayout.createSequentialGroup()
                        .addGroup(jPFamiliaeSocialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jFTProfissao, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPFamiliaeSocialLayout.createSequentialGroup()
                                .addComponent(jCBParentesco, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jFTTempoCasado)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPFamiliaeSocialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLIdadeFamilia)
                            .addComponent(jLCasodeDrogas))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPFamiliaeSocialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPFamiliaeSocialLayout.createSequentialGroup()
                                .addComponent(jFTIdadeFamilia, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLEstadoCivilFamilia)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCBEstadoCivilFamilia, 0, 247, Short.MAX_VALUE))
                            .addComponent(jFTCasodeDrogas)))
                    .addComponent(jFTNomeFamilia)
                    .addComponent(jFTRelacionamento, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jFTFalecido))
                .addContainerGap())
        );
        jPFamiliaeSocialLayout.setVerticalGroup(
            jPFamiliaeSocialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPFamiliaeSocialLayout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPFamiliaeSocialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFTNomeFamilia, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLNomeFamilia, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPFamiliaeSocialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPFamiliaeSocialLayout.createSequentialGroup()
                        .addGroup(jPFamiliaeSocialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jFTIdadeFamilia, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLEstadoCivilFamilia, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCBEstadoCivilFamilia, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLIdadeFamilia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPFamiliaeSocialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPFamiliaeSocialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLProfissao, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jFTProfissao, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPFamiliaeSocialLayout.createSequentialGroup()
                                .addGroup(jPFamiliaeSocialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jFTCasodeDrogas, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLCasodeDrogas, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(1, 1, 1))))
                    .addGroup(jPFamiliaeSocialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jCBParentesco, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLParentesco, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)
                        .addComponent(jFTTempoCasado, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(5, 5, 5)
                .addGroup(jPFamiliaeSocialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPFamiliaeSocialLayout.createSequentialGroup()
                        .addComponent(jLRelacionamento)
                        .addGap(18, 18, 18)
                        .addGroup(jPFamiliaeSocialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLFalecido)
                            .addComponent(jFTFalecido, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPFamiliaeSocialLayout.createSequentialGroup()
                        .addComponent(jFTRelacionamento, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        jTFamilia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome", "Idade", "Parentesco", "Caso de Drogas", "Tempo Casados", "Estado Civil", "Profissao", "Relacionamento", "Falecido", "ID"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTFamilia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTFamiliaMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTFamilia);

        jPControlesFamilia.setForeground(new java.awt.Color(255, 255, 255));
        jPControlesFamilia.setLayout(new java.awt.GridLayout(1, 0));

        jBNovoFamilia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/01.png"))); // NOI18N
        jBNovoFamilia.setText("<html>N<u>o</u>vo");
        jBNovoFamilia.setToolTipText("Novo (CTRL+O)");
        jBNovoFamilia.setActionCommand("NOVOF");
        jPControlesFamilia.add(jBNovoFamilia);

        jBCadastrarFamilia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/user.png"))); // NOI18N
        jBCadastrarFamilia.setText("<html><u>C</u>adastrar");
        jBCadastrarFamilia.setToolTipText("Cadastrar (CTRL+C)");
        jBCadastrarFamilia.setActionCommand("CADASTRARF");
        jPControlesFamilia.add(jBCadastrarFamilia);

        jBCancelarFamilia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/03.png"))); // NOI18N
        jBCancelarFamilia.setText("<html>Ca<u>n</u>celar");
        jBCancelarFamilia.setToolTipText("Cancelar (CTRL+N)");
        jBCancelarFamilia.setActionCommand("CANCELARF");
        jPControlesFamilia.add(jBCancelarFamilia);

        jBAlterarFamilia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/02.png"))); // NOI18N
        jBAlterarFamilia.setText("<html><u>A</u>lterar");
        jBAlterarFamilia.setToolTipText("Alterar (CTRL+A)");
        jBAlterarFamilia.setActionCommand("ALTERARF");
        jPControlesFamilia.add(jBAlterarFamilia);

        jBExcluirFamilia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/09.png"))); // NOI18N
        jBExcluirFamilia.setText("<html><u>E</u>xcluir");
        jBExcluirFamilia.setToolTipText("Excluir (CTRL+E)");
        jBExcluirFamilia.setActionCommand("EXCLUIRF");
        jPControlesFamilia.add(jBExcluirFamilia);

        javax.swing.GroupLayout jPFamiliaGeralLayout = new javax.swing.GroupLayout(jPFamiliaGeral);
        jPFamiliaGeral.setLayout(jPFamiliaGeralLayout);
        jPFamiliaGeralLayout.setHorizontalGroup(
            jPFamiliaGeralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPFamiliaGeralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPFamiliaGeralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPFamiliaeSocial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4)
                    .addComponent(jPControlesFamilia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPFamiliaGeralLayout.setVerticalGroup(
            jPFamiliaGeralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPFamiliaGeralLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPFamiliaeSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPControlesFamilia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTPAbas.addTab("Familia", jPFamiliaGeral);

        jPSaude.setBorder(javax.swing.BorderFactory.createTitledBorder("Saúde"));

        jLSono.setText("Sono:");

        jLAlimentacao.setText("Alimentação:");

        jLMedicacao.setText("Medicação:");

        jTAAlimentacao.setColumns(20);
        jTAAlimentacao.setRows(5);
        jTAAlimentacao.setPreferredSize(new java.awt.Dimension(164, 100));
        jSPAlimentacao.setViewportView(jTAAlimentacao);

        jTASono.setColumns(20);
        jTASono.setRows(5);
        jTASono.setPreferredSize(new java.awt.Dimension(164, 100));
        jSPSono.setViewportView(jTASono);

        jTAMedicacao.setColumns(20);
        jTAMedicacao.setRows(5);
        jTAMedicacao.setPreferredSize(new java.awt.Dimension(164, 100));
        jSPMedicacao.setViewportView(jTAMedicacao);

        jLExamesEspecificos.setText("Exames específicos:");

        jTAExamesEspecificos.setColumns(20);
        jTAExamesEspecificos.setRows(5);
        jTAExamesEspecificos.setPreferredSize(new java.awt.Dimension(164, 100));
        jSPExamesEspecificos.setViewportView(jTAExamesEspecificos);

        jLAlucinacaoDelirios.setText("Alucinação/Deliríos:");

        jLDemaisConvulsoes.setText("Desmaios/Convulsões:");

        jTADemaisConvulsoes.setColumns(20);
        jTADemaisConvulsoes.setRows(5);
        jTADemaisConvulsoes.setPreferredSize(new java.awt.Dimension(164, 100));
        jSPDemaisConvulsoes.setViewportView(jTADemaisConvulsoes);

        jLAutoextermino.setText("Autoextermino:");

        jTAAutoextermino.setColumns(20);
        jTAAutoextermino.setRows(5);
        jTAAutoextermino.setPreferredSize(new java.awt.Dimension(164, 100));
        jSPAutoexterminio.setViewportView(jTAAutoextermino);

        jTAAlucinacaoDelirio.setColumns(20);
        jTAAlucinacaoDelirio.setRows(5);
        jTAAlucinacaoDelirio.setPreferredSize(new java.awt.Dimension(164, 100));
        jSPAlucinacaoDelirio.setViewportView(jTAAlucinacaoDelirio);

        javax.swing.GroupLayout jPSaudeLayout = new javax.swing.GroupLayout(jPSaude);
        jPSaude.setLayout(jPSaudeLayout);
        jPSaudeLayout.setHorizontalGroup(
            jPSaudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPSaudeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPSaudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLMedicacao)
                    .addComponent(jLAlimentacao)
                    .addComponent(jLSono)
                    .addComponent(jLExamesEspecificos))
                .addGap(4, 4, 4)
                .addGroup(jPSaudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPSaudeLayout.createSequentialGroup()
                        .addGroup(jPSaudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jSPAlimentacao, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                            .addComponent(jSPSono, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSPMedicacao))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPSaudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPSaudeLayout.createSequentialGroup()
                                .addGroup(jPSaudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLAutoextermino, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLAlucinacaoDelirios, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPSaudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jSPExamesEspecificos, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                                    .addComponent(jSPDemaisConvulsoes)))
                            .addGroup(jPSaudeLayout.createSequentialGroup()
                                .addComponent(jLDemaisConvulsoes)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSPAlucinacaoDelirio, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jSPAutoexterminio))
                .addContainerGap())
        );
        jPSaudeLayout.setVerticalGroup(
            jPSaudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPSaudeLayout.createSequentialGroup()
                .addGroup(jPSaudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLSono, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLAlucinacaoDelirios)
                    .addComponent(jSPExamesEspecificos, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(jSPSono, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPSaudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLAlimentacao, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLDemaisConvulsoes, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSPAlucinacaoDelirio, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(jSPAlimentacao, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPSaudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLAutoextermino)
                    .addComponent(jLMedicacao, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSPDemaisConvulsoes, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(jSPMedicacao, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPSaudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLExamesEspecificos, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSPAutoexterminio, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPDependencia.setBorder(javax.swing.BorderFactory.createTitledBorder("Dependência"));

        jLTempodeUso.setText("Quanto tempo faz uso de drogas:");

        jTATempodeUso.setColumns(20);
        jTATempodeUso.setRows(5);
        jSPTempodeUso.setViewportView(jTATempodeUso);

        jLUsadaAtualmente.setText("Usada atualmente:");

        jTAUsadaAtualmente.setColumns(20);
        jTAUsadaAtualmente.setRows(5);
        jSPUsadaAtualmente.setViewportView(jTAUsadaAtualmente);

        javax.swing.GroupLayout jPDependenciaLayout = new javax.swing.GroupLayout(jPDependencia);
        jPDependencia.setLayout(jPDependenciaLayout);
        jPDependenciaLayout.setHorizontalGroup(
            jPDependenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPDependenciaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLTempodeUso)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSPTempodeUso, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 155, Short.MAX_VALUE)
                .addComponent(jLUsadaAtualmente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSPUsadaAtualmente, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPDependenciaLayout.setVerticalGroup(
            jPDependenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPDependenciaLayout.createSequentialGroup()
                .addGroup(jPDependenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSPUsadaAtualmente, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jSPTempodeUso, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPDependenciaLayout.createSequentialGroup()
                        .addGroup(jPDependenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLTempodeUso, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLUsadaAtualmente, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 29, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPDrogas.setBorder(javax.swing.BorderFactory.createTitledBorder("Drogas Usadas"));

        jLMaconha.setText("Maconha:");

        jLAlcool.setText("Álcool:");

        jLInalantes.setText("Inalantes:");

        jLCocainaA.setText("Cocaína(A):");

        jLCocainaI.setText("Cocaína(I):");

        jLCrack.setText("Crack:");

        jLCigarroTabaco.setText("Cigarro/Tabaco:");

        jLComprimido.setText("Comprimido:");

        jLOutrasDrogas.setText("Outras Drogas:");

        javax.swing.GroupLayout jPDrogasLayout = new javax.swing.GroupLayout(jPDrogas);
        jPDrogas.setLayout(jPDrogasLayout);
        jPDrogasLayout.setHorizontalGroup(
            jPDrogasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPDrogasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPDrogasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPDrogasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLAlcool)
                        .addComponent(jLInalantes))
                    .addComponent(jLMaconha))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPDrogasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jFTAlcool, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(jFTMaconha)
                    .addComponent(jFTInalantes))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPDrogasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLCocainaI, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLCocainaA, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLCrack, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPDrogasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jFTCocainaI, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(jFTCrack, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(jFTCocainaA, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPDrogasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLComprimido, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLCigarroTabaco, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLOutrasDrogas, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPDrogasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jFTCigarroTabaco, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFTComprimido, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFTOutrasDrogas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPDrogasLayout.setVerticalGroup(
            jPDrogasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPDrogasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPDrogasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFTCigarroTabaco, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFTMaconha, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFTCocainaA, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLMaconha)
                    .addComponent(jLCocainaA)
                    .addComponent(jLCigarroTabaco))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPDrogasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLCocainaI)
                    .addGroup(jPDrogasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jFTAlcool, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jFTComprimido, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLComprimido)
                        .addComponent(jLAlcool)
                        .addComponent(jFTCocainaI, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPDrogasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPDrogasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jFTInalantes, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jFTCrack, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLInalantes)
                        .addComponent(jLCrack))
                    .addGroup(jPDrogasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLOutrasDrogas)
                        .addComponent(jFTOutrasDrogas, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPDependenciaGeralLayout = new javax.swing.GroupLayout(jPDependenciaGeral);
        jPDependenciaGeral.setLayout(jPDependenciaGeralLayout);
        jPDependenciaGeralLayout.setHorizontalGroup(
            jPDependenciaGeralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPDependenciaGeralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPDependenciaGeralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPSaude, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPDependencia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPDrogas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPDependenciaGeralLayout.setVerticalGroup(
            jPDependenciaGeralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPDependenciaGeralLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPSaude, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPDependencia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPDrogas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTPAbas.addTab("Dependência", jPDependenciaGeral);

        jPQuestionario.setBorder(javax.swing.BorderFactory.createTitledBorder("Questionário"));

        jLConhecimentoColonia.setText("Como conheceu a colônia:");

        jTAConhecimentoColonia.setColumns(20);
        jTAConhecimentoColonia.setRows(5);
        jSPConhecimentoColonia.setViewportView(jTAConhecimentoColonia);

        jLExpectativaTratamento.setText("Expectativa do tratamento:");

        jTAExpectativaTratamento.setColumns(20);
        jTAExpectativaTratamento.setRows(5);
        jSPExpectativaTratamento.setViewportView(jTAExpectativaTratamento);

        jLSexualidade.setText("Sexualidade:");

        jLProcessosnaJustica.setText("Processos na justiça:");

        jLRelacionamentoAfetivo.setText("Possui relacionamento afetivo no momento(é usuario de drogas):");

        jTARelacionamentoAfetivo.setColumns(20);
        jTARelacionamentoAfetivo.setRows(5);
        jScrollPane1.setViewportView(jTARelacionamentoAfetivo);

        jLGrupoApoio.setText("Participa de grupo(s) de apoio:");

        jLabel2.setText("Motivo que o levou a experimentar drogras, ou álcool, etc");

        jTAMotivoLevouDrogas.setColumns(20);
        jTAMotivoLevouDrogas.setRows(5);
        jScrollPane2.setViewportView(jTAMotivoLevouDrogas);

        jLRelacionamentoSocial.setText("Relacionamento social:");

        jTARelacionamentoSocial.setColumns(20);
        jTARelacionamentoSocial.setRows(5);
        jScrollPane3.setViewportView(jTARelacionamentoSocial);

        jTASexualidade.setColumns(20);
        jTASexualidade.setRows(5);
        jScrollPane5.setViewportView(jTASexualidade);

        jTAGrupoApoio.setColumns(20);
        jTAGrupoApoio.setRows(5);
        jScrollPane6.setViewportView(jTAGrupoApoio);

        jTAProcessosnaJustica.setColumns(20);
        jTAProcessosnaJustica.setRows(5);
        jScrollPane7.setViewportView(jTAProcessosnaJustica);

        javax.swing.GroupLayout jPQuestionarioLayout = new javax.swing.GroupLayout(jPQuestionario);
        jPQuestionario.setLayout(jPQuestionarioLayout);
        jPQuestionarioLayout.setHorizontalGroup(
            jPQuestionarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPQuestionarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPQuestionarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLConhecimentoColonia)
                    .addComponent(jLExpectativaTratamento)
                    .addComponent(jLabel2)
                    .addComponent(jLProcessosnaJustica)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
                    .addComponent(jSPExpectativaTratamento)
                    .addComponent(jSPConhecimentoColonia)
                    .addComponent(jScrollPane7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 176, Short.MAX_VALUE)
                .addGroup(jPQuestionarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPQuestionarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLRelacionamentoSocial)
                        .addGroup(jPQuestionarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLGrupoApoio, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLRelacionamentoAfetivo, javax.swing.GroupLayout.Alignment.LEADING))
                        .addComponent(jLSexualidade))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPQuestionarioLayout.setVerticalGroup(
            jPQuestionarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPQuestionarioLayout.createSequentialGroup()
                .addGroup(jPQuestionarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLConhecimentoColonia, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLSexualidade, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPQuestionarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPQuestionarioLayout.createSequentialGroup()
                        .addComponent(jSPConhecimentoColonia, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPQuestionarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLExpectativaTratamento, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLRelacionamentoAfetivo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPQuestionarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSPExpectativaTratamento, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPQuestionarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLGrupoApoio))
                .addGroup(jPQuestionarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPQuestionarioLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPQuestionarioLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPQuestionarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLProcessosnaJustica)
                    .addComponent(jLRelacionamentoSocial))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPQuestionarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPTratamentosAnteriores.setBorder(javax.swing.BorderFactory.createTitledBorder("Tratamentos Anteriores"));

        jLClinica.setText("Clínica:");

        jLDatadeEntrada.setText("Data de Entrada:");

        jLDatadeSaida.setText("Data de Saída:");

        jLMotivoSaida.setText("Motivo da saída:");

        jTAMotivoSaida.setColumns(20);
        jTAMotivoSaida.setRows(5);
        jSPLocal.setViewportView(jTAMotivoSaida);

        javax.swing.GroupLayout jPTratamentosAnterioresLayout = new javax.swing.GroupLayout(jPTratamentosAnteriores);
        jPTratamentosAnteriores.setLayout(jPTratamentosAnterioresLayout);
        jPTratamentosAnterioresLayout.setHorizontalGroup(
            jPTratamentosAnterioresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPTratamentosAnterioresLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPTratamentosAnterioresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPTratamentosAnterioresLayout.createSequentialGroup()
                        .addComponent(jLClinica)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFTClinica, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLDatadeEntrada)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDCDataEntradaClinica, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLDatadeSaida)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDCDataSaidaClinica, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPTratamentosAnterioresLayout.createSequentialGroup()
                        .addComponent(jLMotivoSaida, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jSPLocal))
                .addContainerGap())
        );
        jPTratamentosAnterioresLayout.setVerticalGroup(
            jPTratamentosAnterioresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPTratamentosAnterioresLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPTratamentosAnterioresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPTratamentosAnterioresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLDatadeSaida, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPTratamentosAnterioresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLClinica, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLDatadeEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFTClinica, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jDCDataEntradaClinica, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDCDataSaidaClinica, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLMotivoSaida, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSPLocal, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPQuestionarioGeralLayout = new javax.swing.GroupLayout(jPQuestionarioGeral);
        jPQuestionarioGeral.setLayout(jPQuestionarioGeralLayout);
        jPQuestionarioGeralLayout.setHorizontalGroup(
            jPQuestionarioGeralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPQuestionarioGeralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPQuestionarioGeralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPQuestionario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPTratamentosAnteriores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPQuestionarioGeralLayout.setVerticalGroup(
            jPQuestionarioGeralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPQuestionarioGeralLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPQuestionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPTratamentosAnteriores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTPAbas.addTab("Questionário", jPQuestionarioGeral);

        jPRenda.setBorder(javax.swing.BorderFactory.createTitledBorder("Renda"));

        jLPessoal.setText("Pessoal:");

        jLFamiliar.setText("Familiar:");

        jLFamiliarSeparados.setText("Familiar moram separados:");

        jLPossuiAlguemAjudarFinanceira.setText("Possui alguém que pode ajudar financeiramente?");

        javax.swing.GroupLayout jPRendaLayout = new javax.swing.GroupLayout(jPRenda);
        jPRenda.setLayout(jPRendaLayout);
        jPRendaLayout.setHorizontalGroup(
            jPRendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPRendaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPRendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLPossuiAlguemAjudarFinanceira)
                    .addGroup(jPRendaLayout.createSequentialGroup()
                        .addComponent(jLPessoal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFTRendaPessoal, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLFamiliar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPRendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPRendaLayout.createSequentialGroup()
                        .addComponent(jFTRendaFamiliar, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLFamiliarSeparados)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jFTRendaFamiliarSeparado))
                    .addComponent(jFTAjudaFinanceira))
                .addContainerGap())
        );
        jPRendaLayout.setVerticalGroup(
            jPRendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPRendaLayout.createSequentialGroup()
                .addGroup(jPRendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLPessoal, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLFamiliar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLFamiliarSeparados, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFTRendaPessoal, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFTRendaFamiliar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFTRendaFamiliarSeparado, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPRendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLPossuiAlguemAjudarFinanceira, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFTAjudaFinanceira, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPProfissao.setBorder(javax.swing.BorderFactory.createTitledBorder("Profissão"));

        jLTrabalhoAtual.setText("Trabalho atual(local/função):");

        jLCarteiraAssinada.setText("Trabalhou carteira assinada?(quanto tempo/quando):");

        jLAposentadoria.setText("Aposentado(motivo):");

        jLAfastamentoPrevidenciaSocial.setText("Afastamento previdência social(motivo):");

        jLSalarioDesemprego.setText("Recebe salário desemprego(quantas parcelas recebeu):");

        jLEscolaridade.setText("Escolaridade:");

        jCBEscolaridade.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NULL", "Alfabetizado", "Semialfabetizado", "Não Alfabetizado", "Ensino Fundamental", "Ensino Fundamental Incompleto", "Ensino Médio", "Ensino Médio Incompleto", "Ensino Técnico ", "Ensino Técnico Incompleto", "Superior", "Superior Incompleto", "Mestrado", "Mestrado Incompleto", "Doutorado", "Doutorado Incompleto", " " }));

        javax.swing.GroupLayout jPProfissaoLayout = new javax.swing.GroupLayout(jPProfissao);
        jPProfissao.setLayout(jPProfissaoLayout);
        jPProfissaoLayout.setHorizontalGroup(
            jPProfissaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPProfissaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPProfissaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLTrabalhoAtual)
                    .addComponent(jFTTrabalhoAtual)
                    .addComponent(jLCarteiraAssinada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jFTCarteiraAssinada))
                .addGap(26, 26, 26)
                .addGroup(jPProfissaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLAposentadoria)
                    .addComponent(jFTAposentadoria, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFTAfastamentoPrevidenciaSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLAfastamentoPrevidenciaSocial))
                .addGap(30, 30, 30)
                .addGroup(jPProfissaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPProfissaoLayout.createSequentialGroup()
                        .addGroup(jPProfissaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLEscolaridade)
                            .addGroup(jPProfissaoLayout.createSequentialGroup()
                                .addComponent(jLSalarioDesemprego)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1)))
                        .addGap(0, 135, Short.MAX_VALUE))
                    .addComponent(jCBEscolaridade, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jFTSalarioDesemprego))
                .addContainerGap())
        );
        jPProfissaoLayout.setVerticalGroup(
            jPProfissaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPProfissaoLayout.createSequentialGroup()
                .addGroup(jPProfissaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLTrabalhoAtual, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLAfastamentoPrevidenciaSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLSalarioDesemprego, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPProfissaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPProfissaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jFTTrabalhoAtual, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jFTAfastamentoPrevidenciaSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jFTSalarioDesemprego, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPProfissaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLCarteiraAssinada, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLAposentadoria, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLEscolaridade, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPProfissaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFTCarteiraAssinada, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFTAposentadoria, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCBEscolaridade, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPProcesso.setBorder(javax.swing.BorderFactory.createTitledBorder("Processo Internamento"));

        jLSaida.setText("Saída. Entrevistadora:");

        jLEntrevistadora1.setText("Primeira entrevista. Entrevistadora:");

        jLDataPriEntre.setText("Data:");

        jLEntrevistadora2.setText("Segunda entrevista. Entrevistadora:");

        jLDataSegEntre.setText("Data:");

        jLEntrevistadoraInternamento.setText("Internamento. Entrevistadora:");

        jLDataSaida.setText("Data:");

        jLDataInternamento.setText("Data:");

        jLStatus.setText("Status:");

        jCBStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Em tratamento", "Evadiu-se", "Desistência", "Conclusão" }));

        javax.swing.GroupLayout jPProcessoLayout = new javax.swing.GroupLayout(jPProcesso);
        jPProcesso.setLayout(jPProcessoLayout);
        jPProcessoLayout.setHorizontalGroup(
            jPProcessoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPProcessoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPProcessoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLStatus)
                    .addComponent(jLSaida)
                    .addComponent(jLEntrevistadoraInternamento)
                    .addComponent(jLEntrevistadora1)
                    .addComponent(jLEntrevistadora2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPProcessoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jFTEntrevistadoraUm, javax.swing.GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE)
                    .addComponent(jFTEntrevistadoraDois)
                    .addComponent(jFTEntrevistadoraInternamento)
                    .addComponent(jFTEntrevistadoraSaida)
                    .addComponent(jCBStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPProcessoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPProcessoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLDataSegEntre)
                        .addComponent(jLDataSaida))
                    .addComponent(jLDataInternamento, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLDataPriEntre, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPProcessoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDCEntrevistaDois, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDCEntrevistaUm, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDCEntrevistaInternamento, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDCEntrevistaSaida, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPProcessoLayout.setVerticalGroup(
            jPProcessoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPProcessoLayout.createSequentialGroup()
                .addGroup(jPProcessoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPProcessoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLEntrevistadora1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jFTEntrevistadoraUm, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLDataPriEntre, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jDCEntrevistaUm, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPProcessoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPProcessoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jFTEntrevistadoraDois, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLDataSegEntre, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLEntrevistadora2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jDCEntrevistaDois, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPProcessoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPProcessoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jFTEntrevistadoraInternamento, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLEntrevistadoraInternamento, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLDataInternamento, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jDCEntrevistaInternamento, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPProcessoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLDataSaida)
                    .addComponent(jDCEntrevistaSaida, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPProcessoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jFTEntrevistadoraSaida, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLSaida, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPProcessoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCBStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLStatus))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPOutrasInformacoesGeralLayout = new javax.swing.GroupLayout(jPOutrasInformacoesGeral);
        jPOutrasInformacoesGeral.setLayout(jPOutrasInformacoesGeralLayout);
        jPOutrasInformacoesGeralLayout.setHorizontalGroup(
            jPOutrasInformacoesGeralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPOutrasInformacoesGeralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPOutrasInformacoesGeralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPRenda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPProfissao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPProcesso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPOutrasInformacoesGeralLayout.setVerticalGroup(
            jPOutrasInformacoesGeralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPOutrasInformacoesGeralLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPRenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPProfissao, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPProcesso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTPAbas.addTab("Outras Informações", jPOutrasInformacoesGeral);

        jPEnderecoBoleto.setBorder(javax.swing.BorderFactory.createTitledBorder("Endereço"));

        jLLogradouroBoleto.setText("Logradouro:");

        jLBairroBoleto.setText("Bairro:");

        jLNumero1.setText("Número:");

        jFTNumeroBoleto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        jLComplementoBoleto.setText("Complemento:");

        jLCEPBoleto.setText("CEP:");

        jLTelefoneBoleto.setText("Telefone:");

        try {
            jFTTelefoneBoleto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLCidadeBoleto.setText("Cidade:");

        try {
            jFTCEPBoleto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLUFBoleto.setText("UF:");

        jCBUFBoleto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NULL", "AC - Acre    ", "AL - Alagoas    ", "AM - Amazonas    ", "AP - Amapá    ", "BA - Bahia    ", "CE - Ceará    ", "DF - Distrito Federal    ", "ES - Espirito Santo    ", "GO - Goias    ", "MA - Maranhão    ", "MG - Minas Gerais    ", "MS - Mato Grosso Sul    ", "MT - Mato Grosso    ", "PA - Pará    ", "PB - Paraiba    ", "PE - Pernanbuco    ", "PI - Piaui    ", "PR - Parana    ", "RJ - Rio de Janeiro    ", "RN - Rio Grande do Norte    ", "RO - Rondônia    ", "RR - Roraima    ", "RS - Rio Grande do Sul    ", "SC - Santa Catarina    ", "SE - Sergipe    ", "SP - São Paulo    ", "TO - Tocantins" }));

        javax.swing.GroupLayout jPEnderecoBoletoLayout = new javax.swing.GroupLayout(jPEnderecoBoleto);
        jPEnderecoBoleto.setLayout(jPEnderecoBoletoLayout);
        jPEnderecoBoletoLayout.setHorizontalGroup(
            jPEnderecoBoletoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPEnderecoBoletoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPEnderecoBoletoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLBairroBoleto)
                    .addComponent(jLLogradouroBoleto)
                    .addComponent(jLCidadeBoleto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPEnderecoBoletoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPEnderecoBoletoLayout.createSequentialGroup()
                        .addComponent(jFTCidadeBoleto, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLCEPBoleto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFTCEPBoleto))
                    .addComponent(jFTBairroBoleto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPEnderecoBoletoLayout.createSequentialGroup()
                        .addComponent(jFTRuaBoleto, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLNumero1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFTNumeroBoleto)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 268, Short.MAX_VALUE)
                .addGroup(jPEnderecoBoletoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPEnderecoBoletoLayout.createSequentialGroup()
                        .addComponent(jLComplementoBoleto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFTComplementoBoleto, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPEnderecoBoletoLayout.createSequentialGroup()
                        .addComponent(jLTelefoneBoleto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFTTelefoneBoleto, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPEnderecoBoletoLayout.createSequentialGroup()
                        .addComponent(jLUFBoleto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCBUFBoleto, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPEnderecoBoletoLayout.setVerticalGroup(
            jPEnderecoBoletoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPEnderecoBoletoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPEnderecoBoletoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFTRuaBoleto, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLLogradouroBoleto, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLNumero1)
                    .addComponent(jFTNumeroBoleto, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLComplementoBoleto)
                    .addComponent(jFTComplementoBoleto, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPEnderecoBoletoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFTBairroBoleto, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLBairroBoleto, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLTelefoneBoleto, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFTTelefoneBoleto, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPEnderecoBoletoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLCidadeBoleto, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFTCidadeBoleto, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLCEPBoleto, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFTCEPBoleto, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCBUFBoleto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLUFBoleto))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPEnderecoBoletoInternoSimNao.setBorder(javax.swing.BorderFactory.createTitledBorder("Endereço do boleto é do interno?"));
        jPEnderecoBoletoInternoSimNao.setAutoscrolls(true);

        bGEnderecoBoleto.add(jRBEnderecoBoletoSim);
        jRBEnderecoBoletoSim.setText("Sim");
        jRBEnderecoBoletoSim.setActionCommand("SIM");

        bGEnderecoBoleto.add(jRBEnderecoBoletoNao);
        jRBEnderecoBoletoNao.setText("Não");
        jRBEnderecoBoletoNao.setActionCommand("NAO");

        javax.swing.GroupLayout jPEnderecoBoletoInternoSimNaoLayout = new javax.swing.GroupLayout(jPEnderecoBoletoInternoSimNao);
        jPEnderecoBoletoInternoSimNao.setLayout(jPEnderecoBoletoInternoSimNaoLayout);
        jPEnderecoBoletoInternoSimNaoLayout.setHorizontalGroup(
            jPEnderecoBoletoInternoSimNaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPEnderecoBoletoInternoSimNaoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRBEnderecoBoletoSim, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRBEnderecoBoletoNao, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPEnderecoBoletoInternoSimNaoLayout.setVerticalGroup(
            jPEnderecoBoletoInternoSimNaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPEnderecoBoletoInternoSimNaoLayout.createSequentialGroup()
                .addGroup(jPEnderecoBoletoInternoSimNaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRBEnderecoBoletoSim)
                    .addComponent(jRBEnderecoBoletoNao))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPDadosResponsavel.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados do responsável e boleto"));

        jLNomeResponsavel.setText("Nome:");

        jLTelefoneResponsavel.setText("Contato:");

        jLVencimento.setText("Vencimento boleto:");

        javax.swing.GroupLayout jPDadosResponsavelLayout = new javax.swing.GroupLayout(jPDadosResponsavel);
        jPDadosResponsavel.setLayout(jPDadosResponsavelLayout);
        jPDadosResponsavelLayout.setHorizontalGroup(
            jPDadosResponsavelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPDadosResponsavelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPDadosResponsavelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLVencimento)
                    .addComponent(jLNomeResponsavel)
                    .addComponent(jLTelefoneResponsavel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPDadosResponsavelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jFTVencimentoBoleto)
                    .addComponent(jFTNomeResponsavel)
                    .addComponent(jFTContatoResponsavel))
                .addContainerGap())
        );
        jPDadosResponsavelLayout.setVerticalGroup(
            jPDadosResponsavelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPDadosResponsavelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPDadosResponsavelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLNomeResponsavel)
                    .addComponent(jFTNomeResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPDadosResponsavelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFTContatoResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLTelefoneResponsavel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPDadosResponsavelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFTVencimentoBoleto, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLVencimento))
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jPObservacao.setBorder(javax.swing.BorderFactory.createTitledBorder("Observações Gerais"));

        jTAObservacoes.setColumns(20);
        jTAObservacoes.setRows(5);
        jSPObservacoes.setViewportView(jTAObservacoes);

        javax.swing.GroupLayout jPObservacaoLayout = new javax.swing.GroupLayout(jPObservacao);
        jPObservacao.setLayout(jPObservacaoLayout);
        jPObservacaoLayout.setHorizontalGroup(
            jPObservacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPObservacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSPObservacoes)
                .addContainerGap())
        );
        jPObservacaoLayout.setVerticalGroup(
            jPObservacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPObservacaoLayout.createSequentialGroup()
                .addComponent(jSPObservacoes, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPBoletoLayout = new javax.swing.GroupLayout(jPBoleto);
        jPBoleto.setLayout(jPBoletoLayout);
        jPBoletoLayout.setHorizontalGroup(
            jPBoletoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPBoletoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPBoletoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPDadosResponsavel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPEnderecoBoletoInternoSimNao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPEnderecoBoleto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPObservacao, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPBoletoLayout.setVerticalGroup(
            jPBoletoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPBoletoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPDadosResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jPEnderecoBoletoInternoSimNao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPEnderecoBoleto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPObservacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTPAbas.addTab("Dados do Responsável", jPBoleto);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Relatórios"));

        jRBRelatorioPeriodo.setSelected(true);
        jRBRelatorioPeriodo.setText("Relatório por período.");
        jRBRelatorioPeriodo.setToolTipText("");

        jLabel4.setText("Fim:");

        jLabel5.setText(" Inicio:");

        jBGerarRelatorio.setText("Gerar Relatório");
        jBGerarRelatorio.setActionCommand("RELATORIO");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRBRelatorioPeriodo)
                .addGap(35, 35, 35)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDCInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDCFim, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(378, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jBGerarRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(368, 368, 368))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jDCInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDCFim, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jRBRelatorioPeriodo)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 311, Short.MAX_VALUE)
                .addComponent(jBGerarRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPRelatoriosLayout = new javax.swing.GroupLayout(jPRelatorios);
        jPRelatorios.setLayout(jPRelatoriosLayout);
        jPRelatoriosLayout.setHorizontalGroup(
            jPRelatoriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPRelatoriosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPRelatoriosLayout.setVerticalGroup(
            jPRelatoriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPRelatoriosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTPAbas.addTab("Relatórios", jPRelatorios);

        jPPesquisaGeral.setMaximumSize(new java.awt.Dimension(500000, 500000));

        jTPesquisa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Registro", "Nome", "CPF", "Telefone"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTPesquisa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTPesquisaMouseClicked(evt);
            }
        });
        jSPPesquisa.setViewportView(jTPesquisa);

        jPPesquisarPor.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisa por:"));

        bGPesquisarPor.add(jRBNome);
        jRBNome.setText("Nome");

        bGPesquisarPor.add(jRBCPF);
        jRBCPF.setText("CPF");
        jRBCPF.setActionCommand("jRBCPF");

        javax.swing.GroupLayout jPPesquisarPorLayout = new javax.swing.GroupLayout(jPPesquisarPor);
        jPPesquisarPor.setLayout(jPPesquisarPorLayout);
        jPPesquisarPorLayout.setHorizontalGroup(
            jPPesquisarPorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPPesquisarPorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPPesquisarPorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRBNome)
                    .addComponent(jRBCPF))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPPesquisarPorLayout.setVerticalGroup(
            jPPesquisarPorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPPesquisarPorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRBNome, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRBCPF)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPPesquisar.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisar"));

        jBProcurar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/07.png"))); // NOI18N
        jBProcurar.setText("Procurar");
        jBProcurar.setActionCommand("PROCURAR");

        jTFProcurar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTFProcurarKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPPesquisarLayout = new javax.swing.GroupLayout(jPPesquisar);
        jPPesquisar.setLayout(jPPesquisarLayout);
        jPPesquisarLayout.setHorizontalGroup(
            jPPesquisarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPPesquisarLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTFProcurar, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBProcurar, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(223, 223, 223))
        );
        jPPesquisarLayout.setVerticalGroup(
            jPPesquisarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPPesquisarLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPPesquisarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFProcurar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBProcurar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPMostrarTodos.setBorder(javax.swing.BorderFactory.createTitledBorder("Mostrar todos"));

        jBAtualizarTodos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/07.png"))); // NOI18N
        jBAtualizarTodos.setText("Atualizar Todos");
        jBAtualizarTodos.setActionCommand("ATUALIZAR");

        javax.swing.GroupLayout jPMostrarTodosLayout = new javax.swing.GroupLayout(jPMostrarTodos);
        jPMostrarTodos.setLayout(jPMostrarTodosLayout);
        jPMostrarTodosLayout.setHorizontalGroup(
            jPMostrarTodosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 196, Short.MAX_VALUE)
            .addGroup(jPMostrarTodosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPMostrarTodosLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jBAtualizarTodos, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPMostrarTodosLayout.setVerticalGroup(
            jPMostrarTodosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPMostrarTodosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPMostrarTodosLayout.createSequentialGroup()
                    .addGap(15, 15, 15)
                    .addComponent(jBAtualizarTodos, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(16, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPPesquisaGeralLayout = new javax.swing.GroupLayout(jPPesquisaGeral);
        jPPesquisaGeral.setLayout(jPPesquisaGeralLayout);
        jPPesquisaGeralLayout.setHorizontalGroup(
            jPPesquisaGeralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPPesquisaGeralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPPesquisaGeralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSPPesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, 948, Short.MAX_VALUE)
                    .addGroup(jPPesquisaGeralLayout.createSequentialGroup()
                        .addComponent(jPPesquisarPor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 638, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPMostrarTodos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPPesquisaGeralLayout.setVerticalGroup(
            jPPesquisaGeralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPPesquisaGeralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPPesquisaGeralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPPesquisarPor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPPesquisar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPMostrarTodos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSPPesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTPAbas.addTab("Pesquisar", jPPesquisaGeral);

        jPControles.setForeground(new java.awt.Color(255, 255, 255));
        jPControles.setLayout(new java.awt.GridLayout(1, 0));

        jBNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/01.png"))); // NOI18N
        jBNovo.setText("<html>N<u>o</u>vo");
        jBNovo.setToolTipText("Novo (CTRL+O)");
        jBNovo.setActionCommand("NOVO");
        jPControles.add(jBNovo);
        jBNovo.getAccessibleContext().setAccessibleDescription("");

        jBCadastrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/user.png"))); // NOI18N
        jBCadastrar.setText("<html><u>C</u>adastrar");
        jBCadastrar.setToolTipText("Cadastrar (CTRL+C)");
        jBCadastrar.setActionCommand("CADASTRAR");
        jPControles.add(jBCadastrar);
        jBCadastrar.getAccessibleContext().setAccessibleDescription("");

        jBCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/03.png"))); // NOI18N
        jBCancelar.setText("<html>Ca<u>n</u>celar");
        jBCancelar.setToolTipText("Cancelar (CTRL+N)");
        jBCancelar.setActionCommand("CANCELAR");
        jPControles.add(jBCancelar);
        jBCancelar.getAccessibleContext().setAccessibleDescription("");

        jBAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/02.png"))); // NOI18N
        jBAlterar.setText("<html><u>A</u>lterar");
        jBAlterar.setToolTipText("Alterar (CTRL+A)");
        jBAlterar.setActionCommand("ALTERAR");
        jPControles.add(jBAlterar);
        jBAlterar.getAccessibleContext().setAccessibleDescription("");

        jBExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/09.png"))); // NOI18N
        jBExcluir.setText("<html><u>E</u>xcluir");
        jBExcluir.setToolTipText("Excluir (CTRL+E)");
        jBExcluir.setActionCommand("EXCLUIR");
        jPControles.add(jBExcluir);
        jBExcluir.getAccessibleContext().setAccessibleDescription("");

        jBPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/07.png"))); // NOI18N
        jBPesquisar.setText("<html><u>P</u>esquisar");
        jBPesquisar.setToolTipText("Pesquisar (CTRL+P)");
        jBPesquisar.setActionCommand("PESQUISAR");
        jPControles.add(jBPesquisar);
        jBPesquisar.getAccessibleContext().setAccessibleDescription("");

        jBFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/08.png"))); // NOI18N
        jBFechar.setText("<html><u>F</u>echar");
        jBFechar.setToolTipText("Fechar (CTRL+F)");
        jBFechar.setActionCommand("FECHAR");
        jPControles.add(jBFechar);
        jBFechar.getAccessibleContext().setAccessibleDescription("");

        jPIControles.setLayout(new java.awt.GridLayout(1, 0));

        jBINovo.setForeground(new java.awt.Color(255, 255, 255));
        jBINovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/01.png"))); // NOI18N
        jBINovo.setToolTipText("Novo (CTRL+O)");
        jBINovo.setActionCommand("NOVO");
        jPIControles.add(jBINovo);

        jBICadastrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/user.png"))); // NOI18N
        jBICadastrar.setToolTipText("Cadastrar (CTRL+C)");
        jBICadastrar.setActionCommand("CADASTRAR");
        jBICadastrar.setMaximumSize(new java.awt.Dimension(59, 39));
        jBICadastrar.setMinimumSize(new java.awt.Dimension(59, 39));
        jPIControles.add(jBICadastrar);

        jBICancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/03.png"))); // NOI18N
        jBICancelar.setToolTipText("Cancelar (CTRL+N)");
        jBICancelar.setActionCommand("CANCELAR");
        jPIControles.add(jBICancelar);

        jBIAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/02.png"))); // NOI18N
        jBIAlterar.setToolTipText("Alterar (CTRL+A)");
        jBIAlterar.setActionCommand("ALTERAR");
        jPIControles.add(jBIAlterar);

        jBIExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/09.png"))); // NOI18N
        jBIExcluir.setToolTipText("Excluir (CTRL+E)");
        jBIExcluir.setActionCommand("EXCLUIR");
        jPIControles.add(jBIExcluir);

        jBIPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/07.png"))); // NOI18N
        jBIPesquisar.setToolTipText("Pesquisar (CTRL+P)");
        jBIPesquisar.setActionCommand("PESQUISAR");
        jPIControles.add(jBIPesquisar);

        jBIFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/08.png"))); // NOI18N
        jBIFechar.setToolTipText("Fechar (CTRL+F)");
        jBIFechar.setActionCommand("FECHAR");
        jPIControles.add(jBIFechar);

        jLResidenteNumero.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLResidenteNumero.setText("Residente Nº");

        jMOpcoes.setText("Opções");

        jMenuNovo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuNovo.setText("<html>N<u>o</u>vo");
        jMenuNovo.setToolTipText("Novo (CTRL+O)");
        jMenuNovo.setActionCommand("NOVO");
        jMOpcoes.add(jMenuNovo);

        jMenuCadastrar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        jMenuCadastrar.setText("<html><u>C</u>adastrar");
        jMenuCadastrar.setToolTipText("Cadastrar (CTRL+C)");
        jMenuCadastrar.setActionCommand("CADASTRAR");
        jMOpcoes.add(jMenuCadastrar);

        jMenuCancelar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuCancelar.setText("<html>Ca<u>n</u>celar");
        jMenuCancelar.setToolTipText("Cancelar (CTRL+N)");
        jMenuCancelar.setActionCommand("CANCELAR");
        jMOpcoes.add(jMenuCancelar);

        jMenuAlterar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        jMenuAlterar.setText("<html><u>A</u>lterar");
        jMenuAlterar.setToolTipText("Alterar (CTRL+A)");
        jMenuAlterar.setActionCommand("ALTERAR");
        jMOpcoes.add(jMenuAlterar);

        jMenuExcluir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        jMenuExcluir.setText("<html><u>E</u>xcluir");
        jMenuExcluir.setToolTipText("Excluir (CTRL+E)");
        jMenuExcluir.setActionCommand("EXCLUIR");
        jMOpcoes.add(jMenuExcluir);

        jMenuPesquisar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        jMenuPesquisar.setText("<html><u>P</u>esquisar");
        jMenuPesquisar.setToolTipText("Pesquisar (CTRL+P)");
        jMenuPesquisar.setActionCommand("PESQUISAR");
        jMOpcoes.add(jMenuPesquisar);

        jMenuFechar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        jMenuFechar.setText("<html><u>F</u>echar");
        jMenuFechar.setToolTipText("Fechar (CTRL+F)");
        jMenuFechar.setActionCommand("FECHAR");
        jMOpcoes.add(jMenuFechar);
        jMOpcoes.add(jSeparator1);

        jMenuTesteConexao.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        jMenuTesteConexao.setText("Teste Conexao");
        jMenuTesteConexao.setActionCommand("TESTECONEXAO");
        jMOpcoes.add(jMenuTesteConexao);

        jMBMenu.add(jMOpcoes);

        jMRelatorios.setText("Relatórios");
        jMRelatorios.setActionCommand("");
        jMBMenu.add(jMRelatorios);

        jMConexao.setText("Conexão");
        jMConexao.setActionCommand("CONEXAOBD");

        jMenuBancoDados.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_MASK));
        jMenuBancoDados.setText("Banco de Dados");
        jMenuBancoDados.setToolTipText("");
        jMenuBancoDados.setActionCommand("BANCODADOS");
        jMConexao.add(jMenuBancoDados);

        jMBMenu.add(jMConexao);

        jMSair.setText("Sair");
        jMSair.setActionCommand("");

        jMIFechar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        jMIFechar.setText("Fechar");
        jMIFechar.setToolTipText("Fechar (CTRL+F)");
        jMIFechar.setActionCommand("FECHAR");
        jMSair.add(jMIFechar);

        jMBMenu.add(jMSair);

        setJMenuBar(jMBMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPIControles, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(321, 321, 321)
                        .addComponent(jLResidenteNumero)
                        .addGap(18, 18, 18)
                        .addComponent(jTFID, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTPAbas, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPControles, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLResidenteNumero)
                            .addComponent(jTFID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jPIControles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTPAbas, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jPControles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTPAbasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTPAbasMouseClicked
        if (getjTPAbas().getSelectedIndex() == 7) {
            //limpaTabela(getjTPesquisa());
            habilitaCamposPesquisa();
            getjTFProcurar().requestFocus();
            /* BancoDAO t;
             try {
             t = new BancoDAO(modelo);
             // addLinhaPesquisa(t.pesquisarTodos());
             } catch (BancoDAOException ex) {
             Logger.getLogger(Visao.class.getName()).log(Level.SEVERE, null, ex);
             }*/

            getjRBNome().setSelected(true);
        }
    }//GEN-LAST:event_jTPAbasMouseClicked

    private void jTFProcurarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFProcurarKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            Controlador c = new Controlador(this, getModelo());
            try {
                c.procurar();

                //eventoClicjTPesquisa();
            } catch (BancoDAOException ex) {
                Logger.getLogger(Visao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jTFProcurarKeyPressed

    private void jTPesquisaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTPesquisaMouseClicked

        eventoClicjTPesquisa();
        habilitaTodosCampos();
        desabilitaCadastrar();
    }//GEN-LAST:event_jTPesquisaMouseClicked

    private void jTFamiliaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTFamiliaMouseClicked
        int linha = getjTFamilia().getSelectedRow();
        getjFTNomeFamilia().setText(getjTFamilia().getValueAt(linha, 0).toString());
        getjFTIdadeFamilia().setText(getjTFamilia().getValueAt(linha, 1).toString());
        getjCBParentesco().setSelectedItem(getjTFamilia().getValueAt(linha, 2).toString());
        getjFTCasodeDrogas().setText(getjTFamilia().getValueAt(linha, 3).toString());
        getjFTFalecido().setText(getjTFamilia().getValueAt(linha, 4).toString());
        getjCBEstadoCivilFamilia().setSelectedItem(getjTFamilia().getValueAt(linha, 5).toString());
        getjFTProfissao().setText(getjTFamilia().getValueAt(linha, 6).toString());
        getjFTRelacionamento().setText(getjTFamilia().getValueAt(linha, 7).toString());        // TODO add your handling code here:
        getjFTFalecido().setText(getjTFamilia().getValueAt(linha, 8).toString());        // TODO add your handling code here:
        desabilitaNovoFamilia();
        habilitaCampos(getjPFamiliaeSocial());
        habilitaAlterarFamilia();
        habilitaExcluirFamilia();
        habilitaCancelarFamilia();
        desabilitaCadastrarF();
    }//GEN-LAST:event_jTFamiliaMouseClicked
    public void visualizaConexaoBanco() {
        WindowAdapter wa = new WindowAdapter() {
            public void windowClossing(WindowEvent e) {
                System.exit(0);
            }
        };
        getConexaobanco().addWindowListener(wa);
        getConexaobanco().pack();
        getConexaobanco().setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        Dimension tamanhoTela = Toolkit.getDefaultToolkit().getScreenSize();

        int largura = tamanhoTela.width;
        int altura = tamanhoTela.height;
        int largura_janela = (int) getConexaobanco().getPreferredSize().getWidth();
        int altura_janela = (int) getConexaobanco().getPreferredSize().getHeight();
        getConexaobanco().setLocation((largura - largura_janela) / 2, (altura - altura_janela) / 2);
        getConexaobanco().setVisible(true);
    }

    public void clicjTFamilia() {
        int linha = getjTFamilia().getSelectedRow();
        getjFTNomeFamilia().setText(getjTFamilia().getValueAt(linha, 0).toString());
        getjFTIdadeFamilia().setText(getjTFamilia().getValueAt(linha, 1).toString());
        getjCBParentesco().setSelectedItem(getjTFamilia().getValueAt(linha, 2).toString());
        getjFTCasodeDrogas().setText(getjTFamilia().getValueAt(linha, 3).toString());
        getjFTFalecido().setText(getjTFamilia().getValueAt(linha, 4).toString());
        getjCBEstadoCivilFamilia().setSelectedItem(getjTFamilia().getValueAt(linha, 5).toString());
        getjFTProfissao().setText(getjTFamilia().getValueAt(linha, 6).toString());
        getjFTRelacionamento().setText(getjTFamilia().getValueAt(linha, 7).toString());        // TODO add your handling code here:
        getjFTFalecido().setText(getjTFamilia().getValueAt(linha, 8).toString());        // TODO add your handling code here:
        habilitaCampos(getjPFamiliaeSocial());
        habilitaAlterarFamilia();
        habilitaExcluirFamilia();
    }

    public void eventoClicjTPesquisa() {

        int linha = getjTPesquisa().getSelectedRow();

        //Aba Interno
        getjTFID().setText(getInterno().get(linha).getNumero() + "");
        getjFTNome().setText(getInterno().get(linha).getNome());
      //  Date d = convertStringToDate(getInterno().get(linha).getDataNascimento());
          
        if(getInterno().get(linha).getDataNascimento()==null)
            ((JTextField) jDCDataNascimento.getDateEditor().getUiComponent()).setText("");
        else
            jDCDataNascimento.setDate(getInterno().get(linha).getDataNascimento());
        
        //}
        getjFTIdade().setText(String.valueOf(getInterno().get(linha).getIdade()));
        getjCBEstadoCivil().setSelectedItem(getInterno().get(linha).getEstadoCivil());
//        jFTResponsavel.setText(getInterno().get(linha).getResponsavel());
        getjFTNaturalidade().setText(getInterno().get(linha).getNaturalidade());
        if (getInterno().get(linha).getSexo().equals("M")) {
            getjRBMasculino().setSelected(true);
        } else {
            getjRBFeminino().setSelected(true);
        }
        getjTFComQuemMoraAtualmente().setText(getInterno().get(linha).getComQuemMoraAtualmente());
        getjFTCPF().setText(getInterno().get(linha).getCPF());
        getjFTTituloEleitoral().setText(getInterno().get(linha).getTituloEleitoral().getNumero());
        getjFTRG().setText(getInterno().get(linha).getRG());
        getjFTSecao().setText(getInterno().get(linha).getTituloEleitoral().getSecao());
        getjFTZona().setText(getInterno().get(linha).getTituloEleitoral().getZona());
        getjFTPIS().setText(getInterno().get(linha).getPIS());
        getjFTCarteiradeTrabalho().setText(getInterno().get(linha).getNumeroCTPS());
        getjFTRua().setText(getInterno().get(linha).getEndereco().getRua());
        getjFTNumero().setText(String.valueOf(getInterno().get(linha).getEndereco().getNumero()));
        getjFTComplemento().setText(getInterno().get(linha).getEndereco().getComplemento());
        getjFTBairro().setText(getInterno().get(linha).getEndereco().getBairro());
        getjFTTelefone().setText(getInterno().get(linha).getEndereco().getTelefone());
        getjFTCidade().setText(getInterno().get(linha).getEndereco().getCidade());
        getjFTCEP().setText(getInterno().get(linha).getEndereco().getCEP());
        getjCBUF().setSelectedItem(getInterno().get(linha).getEndereco().getEstado());
        getjFTEntrevistadoraUm().setText(getInterno().get(linha).getProcesso().getEntrevistadoraUm());
        getjFTEntrevistadoraDois().setText(getInterno().get(linha).getProcesso().getEntrevistadoraDois());
        getjFTEntrevistadoraInternamento().setText(getInterno().get(linha).getProcesso().getEntrevistadoraInternamento());
        getjFTEntrevistadoraSaida().setText(getInterno().get(linha).getProcesso().getEntrevistadoraSaida());
        
       /* Date d = convertStringToDate(getInterno().get(linha).getProcesso().getDataUm());
        if(d==null)
            ((JTextField) jDCEntrevistaUm.getDateEditor().getUiComponent()).setText("");
        else*/
            jDCEntrevistaUm.setDate(getInterno().get(linha).getProcesso().getDataUm());
        
        /*d = convertStringToDate(getInterno().get(linha).getProcesso().getDataDois());
        if(d==null)
            ((JTextField) jDCEntrevistaDois.getDateEditor().getUiComponent()).setText("");
        else*/
            jDCEntrevistaDois.setDate(getInterno().get(linha).getProcesso().getDataDois());
        
        /*d = convertStringToDate(getInterno().get(linha).getProcesso().getDataInternamento());
        if(d==null)
            ((JTextField) jDCEntrevistaInternamento.getDateEditor().getUiComponent()).setText("");
        else*/
            jDCEntrevistaInternamento.setDate(getInterno().get(linha).getProcesso().getDataInternamento());
        
         /*d = convertStringToDate(getInterno().get(linha).getProcesso().getDataSaida());
        if(d==null)
            ((JTextField) jDCEntrevistaSaida.getDateEditor().getUiComponent()).setText("");
        else*/
            jDCEntrevistaSaida.setDate(getInterno().get(linha).getProcesso().getDataSaida());
        
        //}
        getjCBStatus().setSelectedItem(getInterno().get(linha).getProcesso().getStatus());

        atualizaFotos(getInterno().get(linha).getFotoAntes(), getInterno().get(linha).getFotoDepois());
        //jLFotoAntes.setText(String.valueOf(interno.get(linha).getFotoAntes()));
        //jLFotoDepois.setText(String.valueOf(interno.get(linha).getFotoDepois()));

        //Aba dependencia
        getjTASono().setText(getInterno().get(linha).getSaude().getSono());
        getjTAAlucinacaoDelirio().setText(getInterno().get(linha).getSaude().getAlucinacaoDelirios());
        getjTAAlimentacao().setText(getInterno().get(linha).getSaude().getAlimentacao());
        getjTADemaisConvulsoes().setText(getInterno().get(linha).getSaude().getDesmaiosConvulcoes());
        getjTAMedicacao().setText(getInterno().get(linha).getSaude().getMedicacao());
        getjTAAutoextermino().setText(getInterno().get(linha).getSaude().getAutoExterminio());
        getjTAExamesEspecificos().setText(getInterno().get(linha).getSaude().getExamesEspecificos());
        getjTATempodeUso().setText(getInterno().get(linha).getDependencia().getTempoUso());
        //setDrogasUsadasInterno(linha);
        getjFTMaconha().setText(getInterno().get(linha).getDependencia().getMaconha());
        getjFTAlcool().setText(getInterno().get(linha).getDependencia().getAlcool());
        getjFTComprimido().setText(getInterno().get(linha).getDependencia().getComprimidos());
        getjFTInalantes().setText(getInterno().get(linha).getDependencia().getInalantes());
        getjFTCocainaA().setText(getInterno().get(linha).getDependencia().getCocainaA());
        getjFTCocainaI().setText(getInterno().get(linha).getDependencia().getCocainaI());
        getjFTCrack().setText(getInterno().get(linha).getDependencia().getCrack());
        getjFTCigarroTabaco().setText(getInterno().get(linha).getDependencia().getCigarroTabaco());
        getjFTOutrasDrogas().setText(getInterno().get(linha).getDependencia().getOutrasDrogas());

        getjTAUsadaAtualmente().setText(getInterno().get(linha).getDependencia().getDrogaUsadaAtual());

        //Aba questionario
        getjTAConhecimentoColonia().setText(getInterno().get(linha).getQuestionario().getConhecimentoColonia());
        getjTASexualidade().setText(getInterno().get(linha).getQuestionario().getSexualidade());
        getjTAExpectativaTratamento().setText(getInterno().get(linha).getQuestionario().getExpectativaTratamento());
        getjTARelacionamentoAfetivo().setText(getInterno().get(linha).getQuestionario().getPossuiRelacionamentoAfetivo());
        getjTAMotivoLevouDrogas().setText(getInterno().get(linha).getQuestionario().getMotivoUsarDrogas());
        getjTAGrupoApoio().setText(getInterno().get(linha).getQuestionario().getGrupoApoio());
        getjTAProcessosnaJustica().setText(getInterno().get(linha).getQuestionario().getProcessoJustica());
        getjTARelacionamentoSocial().setText(getInterno().get(linha).getQuestionario().getRelacionamentoSocial());
        getjFTClinica().setText(getInterno().get(linha).getTratamentosAnteriores().getClinica());
        
        
        
        /* d = convertStringToDate(getInterno().get(linha).getTratamentosAnteriores().getDataEntrada());
        if(d==null)
            ((JTextField) jDCDataEntradaClinica.getDateEditor().getUiComponent()).setText("");
        else*/
            jDCDataEntradaClinica.setDate(getInterno().get(linha).getTratamentosAnteriores().getDataEntrada());
        
        
         /*d = convertStringToDate(getInterno().get(linha).getTratamentosAnteriores().getDataSaida());
        if(d==null)
            ((JTextField) jDCDataSaidaClinica.getDateEditor().getUiComponent()).setText("");
        else*/
            jDCDataSaidaClinica.setDate(getInterno().get(linha).getTratamentosAnteriores().getDataSaida());
        
        getjTAMotivoSaida().setText(getInterno().get(linha).getTratamentosAnteriores().getMotivoSaida());

        //Aba Familia
        addLinhaFamiliaInterno(linha);

        //Aba Outras informações
        getjFTRendaPessoal().setText(getInterno().get(linha).getSituacaoEconomica().getRendaPessoal());
        getjFTRendaFamiliar().setText(getInterno().get(linha).getSituacaoEconomica().getRendaFamilia());
        getjFTRendaFamiliarSeparado().setText(getInterno().get(linha).getSituacaoEconomica().getRendaFamiliaSeparados());
        getjFTAjudaFinanceira().setText(getInterno().get(linha).getSituacaoEconomica().getAjudaFinanceira());
        getjFTTrabalhoAtual().setText(getInterno().get(linha).getProfissao().getTrabalhoAtual());
        getjFTAfastamentoPrevidenciaSocial().setText(getInterno().get(linha).getProfissao().getAfastadoPS());
        getjFTSalarioDesemprego().setText(getInterno().get(linha).getProfissao().getSalarioDesemprego());
        getjFTCarteiraAssinada().setText(getInterno().get(linha).getProfissao().getTrabalhoCA());
        getjFTAposentadoria().setText(getInterno().get(linha).getProfissao().getAposentado());
        getjCBEscolaridade().setSelectedItem(getInterno().get(linha).getProfissao().getEscolaridade());
        getjTAObservacoes().setText(getInterno().get(linha).getQuestionario().getObservacoes());

        //aba boleto
        getjFTNomeResponsavel().setText(getInterno().get(linha).getBoletoInterno().getResponsavel());
        getjFTVencimentoBoleto().setText(getInterno().get(linha).getBoletoInterno().getVencimentoBoleto());
        getjFTContatoResponsavel().setText(getInterno().get(linha).getBoletoInterno().getContatoResponsavel());
        if (getInterno().get(linha).getBoletoInterno().getMesmoEnderecoInterno().equals("SIM")) {
            getjRBEnderecoBoletoSim().setSelected(true);
        } else if (getInterno().get(linha).getBoletoInterno().getMesmoEnderecoInterno().equals("NAO")) {
            getjRBEnderecoBoletoNao().setSelected(true);
        }

        getjFTRuaBoleto().setText(getInterno().get(linha).getBoletoInterno().getEnderecoBoleto().getRua());
        getjFTNumeroBoleto().setText(String.valueOf(getInterno().get(linha).getBoletoInterno().getEnderecoBoleto().getNumero()));
        getjFTComplementoBoleto().setText(getInterno().get(linha).getBoletoInterno().getEnderecoBoleto().getComplemento());
        getjFTBairroBoleto().setText(getInterno().get(linha).getBoletoInterno().getEnderecoBoleto().getBairro());
        getjFTTelefoneBoleto().setText(getInterno().get(linha).getBoletoInterno().getEnderecoBoleto().getTelefone());
        getjFTCidadeBoleto().setText(getInterno().get(linha).getBoletoInterno().getEnderecoBoleto().getCidade());
        getjFTCEPBoleto().setText(getInterno().get(linha).getBoletoInterno().getEnderecoBoleto().getCEP());
        getjCBUFBoleto().setSelectedItem(getInterno().get(linha).getBoletoInterno().getEnderecoBoleto().getEstado());

    }

    //retorna o indece do vertor de internos -1 se nao encontrar
    /*public int procuraInterno(int linha) {
     int id = Integer.parseInt((String) jTPesquisa.getValueAt(linha, 0));
     for (int i = 0; i < interno.length; i++) {
     if (id == interno[i].getCodigo()) {
     return i;
     }
     }
     return -1;
     }*/
    public Interno procuraInterno(int linha) {
        return getInterno().get(linha);
    }
    /**
     * @param args the command line arguments
     */
    /*  public static void main(String args[]) {
     /* Set the Nimbus look and feel */
    //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
     * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
     */
    /*        try {
     for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
     if ("Windows".equals(info.getName())) {
     javax.swing.UIManager.setLookAndFeel(info.getClassName());
     break;
     }
     }
     } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
     java.util.logging.Logger.getLogger(Visao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
     }
     //</editor-fold>

     /* Create and display the form */
    /*java.awt.EventQueue.invokeLater(new Runnable() {
     public void run() {
     new Visao().setVisible(true);
     }
     });
     }*/
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bGEnderecoBoleto;
    private javax.swing.ButtonGroup bGPesquisarPor;
    private javax.swing.ButtonGroup bGSexo;
    private javax.swing.JButton jBAlterar;
    private javax.swing.JButton jBAlterarFamilia;
    private javax.swing.JButton jBAlterarFotoAntes;
    private javax.swing.JButton jBAlterarFotoDepois;
    private javax.swing.JButton jBAtualizarTodos;
    private javax.swing.JButton jBCadastrar;
    private javax.swing.JButton jBCadastrarFamilia;
    private javax.swing.JButton jBCancelar;
    private javax.swing.JButton jBCancelarFamilia;
    private javax.swing.JButton jBExcluir;
    private javax.swing.JButton jBExcluirFamilia;
    private javax.swing.JButton jBFechar;
    private javax.swing.JButton jBGerarRelatorio;
    private javax.swing.JButton jBIAlterar;
    private javax.swing.JButton jBICadastrar;
    private javax.swing.JButton jBICancelar;
    private javax.swing.JButton jBIExcluir;
    private javax.swing.JButton jBIFechar;
    private javax.swing.JButton jBINovo;
    private javax.swing.JButton jBIPesquisar;
    private javax.swing.JButton jBNovo;
    private javax.swing.JButton jBNovoFamilia;
    private javax.swing.JButton jBPesquisar;
    private javax.swing.JButton jBProcurar;
    private javax.swing.JComboBox jCBEscolaridade;
    private javax.swing.JComboBox jCBEstadoCivil;
    private javax.swing.JComboBox jCBEstadoCivilFamilia;
    private javax.swing.JComboBox jCBParentesco;
    private javax.swing.JComboBox jCBStatus;
    private javax.swing.JComboBox jCBUF;
    private javax.swing.JComboBox jCBUFBoleto;
    private com.toedter.calendar.JDateChooser jDCDataEntradaClinica;
    private com.toedter.calendar.JDateChooser jDCDataNascimento;
    private com.toedter.calendar.JDateChooser jDCDataSaidaClinica;
    private com.toedter.calendar.JDateChooser jDCEntrevistaDois;
    private com.toedter.calendar.JDateChooser jDCEntrevistaInternamento;
    private com.toedter.calendar.JDateChooser jDCEntrevistaSaida;
    private com.toedter.calendar.JDateChooser jDCEntrevistaUm;
    private com.toedter.calendar.JDateChooser jDCFim;
    private com.toedter.calendar.JDateChooser jDCInicio;
    private javax.swing.JFormattedTextField jFTAfastamentoPrevidenciaSocial;
    private javax.swing.JFormattedTextField jFTAjudaFinanceira;
    private javax.swing.JFormattedTextField jFTAlcool;
    private javax.swing.JFormattedTextField jFTAposentadoria;
    private javax.swing.JFormattedTextField jFTBairro;
    private javax.swing.JFormattedTextField jFTBairroBoleto;
    private javax.swing.JFormattedTextField jFTCEP;
    private javax.swing.JFormattedTextField jFTCEPBoleto;
    private javax.swing.JFormattedTextField jFTCPF;
    private javax.swing.JFormattedTextField jFTCarteiraAssinada;
    private javax.swing.JFormattedTextField jFTCarteiradeTrabalho;
    private javax.swing.JFormattedTextField jFTCasodeDrogas;
    private javax.swing.JFormattedTextField jFTCidade;
    private javax.swing.JFormattedTextField jFTCidadeBoleto;
    private javax.swing.JFormattedTextField jFTCigarroTabaco;
    private javax.swing.JFormattedTextField jFTClinica;
    private javax.swing.JFormattedTextField jFTCocainaA;
    private javax.swing.JFormattedTextField jFTCocainaI;
    private javax.swing.JFormattedTextField jFTComplemento;
    private javax.swing.JFormattedTextField jFTComplementoBoleto;
    private javax.swing.JFormattedTextField jFTComprimido;
    private javax.swing.JFormattedTextField jFTContatoResponsavel;
    private javax.swing.JFormattedTextField jFTCrack;
    private javax.swing.JFormattedTextField jFTEntrevistadoraDois;
    private javax.swing.JFormattedTextField jFTEntrevistadoraInternamento;
    private javax.swing.JFormattedTextField jFTEntrevistadoraSaida;
    private javax.swing.JFormattedTextField jFTEntrevistadoraUm;
    private javax.swing.JFormattedTextField jFTFalecido;
    private javax.swing.JFormattedTextField jFTIdade;
    private javax.swing.JFormattedTextField jFTIdadeFamilia;
    private javax.swing.JFormattedTextField jFTInalantes;
    private javax.swing.JFormattedTextField jFTMaconha;
    private javax.swing.JFormattedTextField jFTNaturalidade;
    private javax.swing.JFormattedTextField jFTNome;
    private javax.swing.JFormattedTextField jFTNomeFamilia;
    private javax.swing.JFormattedTextField jFTNomeResponsavel;
    private javax.swing.JFormattedTextField jFTNumero;
    private javax.swing.JFormattedTextField jFTNumeroBoleto;
    private javax.swing.JFormattedTextField jFTOutrasDrogas;
    private javax.swing.JFormattedTextField jFTPIS;
    private javax.swing.JFormattedTextField jFTProfissao;
    private javax.swing.JFormattedTextField jFTRG;
    private javax.swing.JFormattedTextField jFTRelacionamento;
    private javax.swing.JFormattedTextField jFTRendaFamiliar;
    private javax.swing.JFormattedTextField jFTRendaFamiliarSeparado;
    private javax.swing.JFormattedTextField jFTRendaPessoal;
    private javax.swing.JFormattedTextField jFTRua;
    private javax.swing.JFormattedTextField jFTRuaBoleto;
    private javax.swing.JFormattedTextField jFTSalarioDesemprego;
    private javax.swing.JFormattedTextField jFTSecao;
    private javax.swing.JFormattedTextField jFTTelefone;
    private javax.swing.JFormattedTextField jFTTelefoneBoleto;
    private javax.swing.JFormattedTextField jFTTempoCasado;
    private javax.swing.JFormattedTextField jFTTituloEleitoral;
    private javax.swing.JFormattedTextField jFTTrabalhoAtual;
    private javax.swing.JFormattedTextField jFTVencimentoBoleto;
    private javax.swing.JFormattedTextField jFTZona;
    private javax.swing.JLabel jLAfastamentoPrevidenciaSocial;
    private javax.swing.JLabel jLAlcool;
    private javax.swing.JLabel jLAlimentacao;
    private javax.swing.JLabel jLAlucinacaoDelirios;
    private javax.swing.JLabel jLAposentadoria;
    private javax.swing.JLabel jLAutoextermino;
    private javax.swing.JLabel jLBairro;
    private javax.swing.JLabel jLBairroBoleto;
    private javax.swing.JLabel jLCEP;
    private javax.swing.JLabel jLCEPBoleto;
    private javax.swing.JLabel jLCPF;
    private javax.swing.JLabel jLCarteiraAssinada;
    private javax.swing.JLabel jLCarteiradeTrabalho;
    private javax.swing.JLabel jLCasodeDrogas;
    private javax.swing.JLabel jLCidade;
    private javax.swing.JLabel jLCidadeBoleto;
    private javax.swing.JLabel jLCigarroTabaco;
    private javax.swing.JLabel jLClinica;
    private javax.swing.JLabel jLCocainaA;
    private javax.swing.JLabel jLCocainaI;
    private javax.swing.JLabel jLComplemento;
    private javax.swing.JLabel jLComplementoBoleto;
    private javax.swing.JLabel jLComprimido;
    private javax.swing.JLabel jLConhecimentoColonia;
    private javax.swing.JLabel jLCrack;
    private javax.swing.JLabel jLDataInternamento;
    private javax.swing.JLabel jLDataPriEntre;
    private javax.swing.JLabel jLDataSaida;
    private javax.swing.JLabel jLDataSegEntre;
    private javax.swing.JLabel jLDatadeEntrada;
    private javax.swing.JLabel jLDatadeNascimento;
    private javax.swing.JLabel jLDatadeSaida;
    private javax.swing.JLabel jLDemaisConvulsoes;
    private javax.swing.JLabel jLEntrevistadora1;
    private javax.swing.JLabel jLEntrevistadora2;
    private javax.swing.JLabel jLEntrevistadoraInternamento;
    private javax.swing.JLabel jLEscolaridade;
    private javax.swing.JLabel jLEstadoCivil;
    private javax.swing.JLabel jLEstadoCivilFamilia;
    private javax.swing.JLabel jLExamesEspecificos;
    private javax.swing.JLabel jLExpectativaTratamento;
    private javax.swing.JLabel jLFalecido;
    private javax.swing.JLabel jLFamiliar;
    private javax.swing.JLabel jLFamiliarSeparados;
    private javax.swing.JLabel jLFotoAntes;
    private javax.swing.JLabel jLFotoDepois;
    private javax.swing.JLabel jLGrupoApoio;
    private javax.swing.JLabel jLIdade;
    private javax.swing.JLabel jLIdadeFamilia;
    private javax.swing.JLabel jLInalantes;
    private javax.swing.JLabel jLLogradouro;
    private javax.swing.JLabel jLLogradouroBoleto;
    private javax.swing.JLabel jLMaconha;
    private javax.swing.JLabel jLMedicacao;
    private javax.swing.JLabel jLMotivoSaida;
    private javax.swing.JLabel jLNaturalidade;
    private javax.swing.JLabel jLNome;
    private javax.swing.JLabel jLNomeFamilia;
    private javax.swing.JLabel jLNomeResponsavel;
    private javax.swing.JLabel jLNumero;
    private javax.swing.JLabel jLNumero1;
    private javax.swing.JLabel jLOutrasDrogas;
    private javax.swing.JLabel jLPIS;
    private javax.swing.JLabel jLParentesco;
    private javax.swing.JLabel jLPessoal;
    private javax.swing.JLabel jLPossuiAlguemAjudarFinanceira;
    private javax.swing.JLabel jLProcessosnaJustica;
    private javax.swing.JLabel jLProfissao;
    private javax.swing.JLabel jLRG;
    private javax.swing.JLabel jLRelacionamento;
    private javax.swing.JLabel jLRelacionamentoAfetivo;
    private javax.swing.JLabel jLRelacionamentoSocial;
    private javax.swing.JLabel jLResidenteNumero;
    private javax.swing.JLabel jLSaida;
    private javax.swing.JLabel jLSalarioDesemprego;
    private javax.swing.JLabel jLSecao;
    private javax.swing.JLabel jLSexo;
    private javax.swing.JLabel jLSexualidade;
    private javax.swing.JLabel jLSono;
    private javax.swing.JLabel jLStatus;
    private javax.swing.JLabel jLTelefone;
    private javax.swing.JLabel jLTelefoneBoleto;
    private javax.swing.JLabel jLTelefoneResponsavel;
    private javax.swing.JLabel jLTempodeUso;
    private javax.swing.JLabel jLTituloEleitoral;
    private javax.swing.JLabel jLTrabalhoAtual;
    private javax.swing.JLabel jLUF;
    private javax.swing.JLabel jLUFBoleto;
    private javax.swing.JLabel jLUsadaAtualmente;
    private javax.swing.JLabel jLVencimento;
    private javax.swing.JLabel jLZona;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenuBar jMBMenu;
    private javax.swing.JMenu jMConexao;
    private javax.swing.JMenuItem jMIFechar;
    private javax.swing.JMenu jMOpcoes;
    private javax.swing.JMenu jMRelatorios;
    private javax.swing.JMenu jMSair;
    private javax.swing.JMenuItem jMenuAlterar;
    private javax.swing.JMenuItem jMenuBancoDados;
    private javax.swing.JMenuItem jMenuCadastrar;
    private javax.swing.JMenuItem jMenuCancelar;
    private javax.swing.JMenuItem jMenuExcluir;
    private javax.swing.JMenuItem jMenuFechar;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuNovo;
    private javax.swing.JMenuItem jMenuPesquisar;
    private javax.swing.JMenuItem jMenuTesteConexao;
    private javax.swing.JPanel jPBoleto;
    private javax.swing.JPanel jPComQuemMora;
    private javax.swing.JPanel jPComQuemMoraAtualmente;
    private javax.swing.JPanel jPControles;
    private javax.swing.JPanel jPControlesFamilia;
    private javax.swing.JPanel jPDadosPessoais;
    private javax.swing.JPanel jPDadosResponsavel;
    private javax.swing.JPanel jPDependencia;
    private javax.swing.JPanel jPDependenciaGeral;
    private javax.swing.JPanel jPDocumentacao;
    private javax.swing.JPanel jPDrogas;
    private javax.swing.JPanel jPEndereco;
    private javax.swing.JPanel jPEnderecoBoleto;
    private javax.swing.JPanel jPEnderecoBoletoInternoSimNao;
    private javax.swing.JPanel jPFamiliaGeral;
    private javax.swing.JPanel jPFamiliaeSocial;
    private javax.swing.JPanel jPFoto;
    private javax.swing.JPanel jPIControles;
    private javax.swing.JPanel jPInternoGeral;
    private javax.swing.JPanel jPMostrarTodos;
    private javax.swing.JPanel jPObservacao;
    private javax.swing.JPanel jPOutrasInformacoesGeral;
    private javax.swing.JPanel jPPesquisaGeral;
    private javax.swing.JPanel jPPesquisar;
    private javax.swing.JPanel jPPesquisarPor;
    private javax.swing.JPanel jPProcesso;
    private javax.swing.JPanel jPProfissao;
    private javax.swing.JPanel jPQuestionario;
    private javax.swing.JPanel jPQuestionarioGeral;
    private javax.swing.JPanel jPRenda;
    private javax.swing.JPanel jPSaude;
    private javax.swing.JPanel jPTratamentosAnteriores;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRBCPF;
    private javax.swing.JRadioButton jRBEnderecoBoletoNao;
    private javax.swing.JRadioButton jRBEnderecoBoletoSim;
    private javax.swing.JRadioButton jRBFemenino;
    private javax.swing.JRadioButton jRBMasculino;
    private javax.swing.JRadioButton jRBNome;
    private javax.swing.JRadioButton jRBRelatorioPeriodo;
    private javax.swing.JScrollPane jSPAlimentacao;
    private javax.swing.JScrollPane jSPAlucinacaoDelirio;
    private javax.swing.JScrollPane jSPAutoexterminio;
    private javax.swing.JScrollPane jSPConhecimentoColonia;
    private javax.swing.JScrollPane jSPDemaisConvulsoes;
    private javax.swing.JScrollPane jSPExamesEspecificos;
    private javax.swing.JScrollPane jSPExpectativaTratamento;
    private javax.swing.JScrollPane jSPLocal;
    private javax.swing.JScrollPane jSPMedicacao;
    private javax.swing.JScrollPane jSPObservacoes;
    private javax.swing.JScrollPane jSPPesquisa;
    private javax.swing.JScrollPane jSPSono;
    private javax.swing.JScrollPane jSPTempodeUso;
    private javax.swing.JScrollPane jSPUsadaAtualmente;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTextArea jTAAlimentacao;
    private javax.swing.JTextArea jTAAlucinacaoDelirio;
    private javax.swing.JTextArea jTAAutoextermino;
    private javax.swing.JTextArea jTAConhecimentoColonia;
    private javax.swing.JTextArea jTADemaisConvulsoes;
    private javax.swing.JTextArea jTAExamesEspecificos;
    private javax.swing.JTextArea jTAExpectativaTratamento;
    private javax.swing.JTextArea jTAGrupoApoio;
    private javax.swing.JTextArea jTAMedicacao;
    private javax.swing.JTextArea jTAMotivoLevouDrogas;
    private javax.swing.JTextArea jTAMotivoSaida;
    private javax.swing.JTextArea jTAObservacoes;
    private javax.swing.JTextArea jTAProcessosnaJustica;
    private javax.swing.JTextArea jTARelacionamentoAfetivo;
    private javax.swing.JTextArea jTARelacionamentoSocial;
    private javax.swing.JTextArea jTASexualidade;
    private javax.swing.JTextArea jTASono;
    private javax.swing.JTextArea jTATempodeUso;
    private javax.swing.JTextArea jTAUsadaAtualmente;
    private javax.swing.JTextField jTFComQuemMora;
    private javax.swing.JTextField jTFComQuemMoraAtualmente;
    private javax.swing.JTextField jTFID;
    private javax.swing.JTextField jTFProcurar;
    private javax.swing.JTable jTFamilia;
    private javax.swing.JTabbedPane jTPAbas;
    private javax.swing.JTable jTPesquisa;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the NOVO
     */
    public String getNOVO() {
        return NOVO;
    }

    /**
     * @return the ALTERAR
     */
    public String getALTERAR() {
        return ALTERAR;
    }

    /**
     * @return the EXCLUIR
     */
    public String getEXCLUIR() {
        return EXCLUIR;
    }

    /**
     * @return the PESQUISAR
     */
    public String getPESQUISAR() {
        return PESQUISAR;
    }

    /**
     * @return the CANCELAR
     */
    public String getCANCELAR() {
        return CANCELAR;
    }

    /**
     * @return the ALTERAR_FOTO_ANTES
     */
    public String getALTERAR_FOTO_ANTES() {
        return ALTERAR_FOTO_ANTES;
    }

    /**
     * @return the ALTERAR_FOTO_DEPOIS
     */
    public String getALTERAR_FOTO_DEPOIS() {
        return ALTERAR_FOTO_DEPOIS;
    }

    /**
     * @return the CADASTRAR
     */
    public String getCADASTRAR() {
        return CADASTRAR;
    }

    /**
     * @return the FECHAR
     */
    public String getFECHAR() {
        return FECHAR;
    }

    /**
     * @return the bGPesquisarPor
     */
    public javax.swing.ButtonGroup getbGPesquisarPor() {
        return bGPesquisarPor;
    }

    /**
     * @param bGPesquisarPor the bGPesquisarPor to set
     */
    public void setbGPesquisarPor(javax.swing.ButtonGroup bGPesquisarPor) {
        this.bGPesquisarPor = bGPesquisarPor;
    }

    /**
     * @return the jBAlterar
     */
    public javax.swing.JButton getjBAlterar() {
        return jBAlterar;
    }

    /**
     * @param jBAlterar the jBAlterar to set
     */
    public void setjBAlterar(javax.swing.JButton jBAlterar) {
        this.jBAlterar = jBAlterar;
    }

    /**
     * @return the jBAlterarFotoDepois
     */
    public javax.swing.JButton getjBAlterarFotoDepois() {
        return jBAlterarFotoDepois;
    }

    /**
     * @param jBAlterarFotoDepois the jBAlterarFotoDepois to set
     */
    public void setjBAlterarFotoDepois(javax.swing.JButton jBAlterarFotoDepois) {
        this.jBAlterarFotoDepois = jBAlterarFotoDepois;
    }

    /**
     * @return the jBAlterarFotosAntes
     */
    public javax.swing.JButton getjBAlterarFotoAntes() {
        return jBAlterarFotoAntes;
    }

    /**
     * @param jBAlterarFotosAntes the jBAlterarFotosAntes to set
     */
    public void setjBAlterarFotosAntes(javax.swing.JButton jBAlterarFotosAntes) {
    }

    /**
     * @return the jBCadastrar
     */
    public javax.swing.JButton getjBCadastrar() {
        return jBCadastrar;
    }

    /**
     * @param jBCadastrar the jBCadastrar to set
     */
    public void setjBCadastrar(javax.swing.JButton jBCadastrar) {
        this.jBCadastrar = jBCadastrar;
    }

    /**
     * @return the jBCancelar
     */
    public javax.swing.JButton getjBCancelar() {
        return jBCancelar;
    }

    /**
     * @param jBCancelar the jBCancelar to set
     */
    public void setjBCancelar(javax.swing.JButton jBCancelar) {
        this.jBCancelar = jBCancelar;
    }

    /**
     * @return the jBExcluir
     */
    public javax.swing.JButton getjBExcluir() {
        return jBExcluir;
    }

    /**
     * @param jBExcluir the jBExcluir to set
     */
    public void setjBExcluir(javax.swing.JButton jBExcluir) {
        this.jBExcluir = jBExcluir;
    }

    /**
     * @return the jBFechar
     */
    public javax.swing.JButton getjBFechar() {
        return jBFechar;
    }

    /**
     * @param jBFechar the jBFechar to set
     */
    public void setjBFechar(javax.swing.JButton jBFechar) {
        this.jBFechar = jBFechar;
    }

    /**
     * @return the jBIAlterar
     */
    public javax.swing.JButton getjBIAlterar() {
        return jBIAlterar;
    }

    /**
     * @param jBIAlterar the jBIAlterar to set
     */
    public void setjBIAlterar(javax.swing.JButton jBIAlterar) {
        this.jBIAlterar = jBIAlterar;
    }

    /**
     * @return the jBICadastrar
     */
    public javax.swing.JButton getjBICadastrar() {
        return jBICadastrar;
    }

    /**
     * @param jBICadastrar the jBICadastrar to set
     */
    public void setjBICadastrar(javax.swing.JButton jBICadastrar) {
        this.jBICadastrar = jBICadastrar;
    }

    /**
     * @return the jBICancelar
     */
    public javax.swing.JButton getjBICancelar() {
        return jBICancelar;
    }

    /**
     * @param jBICancelar the jBICancelar to set
     */
    public void setjBICancelar(javax.swing.JButton jBICancelar) {
        this.jBICancelar = jBICancelar;
    }

    /**
     * @return the jBIExcluir
     */
    public javax.swing.JButton getjBIExcluir() {
        return jBIExcluir;
    }

    /**
     * @param jBIExcluir the jBIExcluir to set
     */
    public void setjBIExcluir(javax.swing.JButton jBIExcluir) {
        this.jBIExcluir = jBIExcluir;
    }

    /**
     * @return the jBIFechar
     */
    public javax.swing.JButton getjBIFechar() {
        return jBIFechar;
    }

    /**
     * @param jBIFechar the jBIFechar to set
     */
    public void setjBIFechar(javax.swing.JButton jBIFechar) {
        this.jBIFechar = jBIFechar;
    }

    /**
     * @return the jBINovo
     */
    public javax.swing.JButton getjBINovo() {
        return jBINovo;
    }

    /**
     * @param jBINovo the jBINovo to set
     */
    public void setjBINovo(javax.swing.JButton jBINovo) {
        this.jBINovo = jBINovo;
    }

    /**
     * @return the jBIPesquisar
     */
    public javax.swing.JButton getjBIPesquisar() {
        return jBIPesquisar;
    }

    /**
     * @param jBIPesquisar the jBIPesquisar to set
     */
    public void setjBIPesquisar(javax.swing.JButton jBIPesquisar) {
        this.jBIPesquisar = jBIPesquisar;
    }

    /**
     * @return the jBNovo
     */
    public javax.swing.JButton getjBNovo() {
        return jBNovo;
    }

    /**
     * @param jBNovo the jBNovo to set
     */
    public void setjBNovo(javax.swing.JButton jBNovo) {
        this.jBNovo = jBNovo;
    }

    /**
     * @return the jBPesquisar
     */
    public javax.swing.JButton getjBPesquisar() {
        return jBPesquisar;
    }

    /**
     * @param jBPesquisar the jBPesquisar to set
     */
    public void setjBPesquisar(javax.swing.JButton jBPesquisar) {
        this.jBPesquisar = jBPesquisar;
    }

    /**
     * @return the jBProcurar
     */
    public javax.swing.JButton getjBProcurar() {
        return jBProcurar;
    }

    /**
     * @param jBProcurar the jBProcurar to set
     */
    public void setjBProcurar(javax.swing.JButton jBProcurar) {
        this.jBProcurar = jBProcurar;
    }

    /**
     * @return the jCBEscolaridade
     */
    public javax.swing.JComboBox getjCBEscolaridade() {
        return jCBEscolaridade;
    }

    /**
     * @param jCBEscolaridade the jCBEscolaridade to set
     */
    public void setjCBEscolaridade(javax.swing.JComboBox jCBEscolaridade) {
        this.jCBEscolaridade = jCBEscolaridade;
    }

    /**
     * @return the jCBEstadoCivil
     */
    public javax.swing.JComboBox getjCBEstadoCivil() {
        return jCBEstadoCivil;
    }

    /**
     * @param jCBEstadoCivil the jCBEstadoCivil to set
     */
    public void setjCBEstadoCivil(javax.swing.JComboBox jCBEstadoCivil) {
        this.jCBEstadoCivil = jCBEstadoCivil;
    }

    /**
     * @return the jCBEstadoCivilFamilia
     */
    public javax.swing.JComboBox getjCBEstadoCivilFamilia() {
        return jCBEstadoCivilFamilia;
    }

    /**
     * @param jCBEstadoCivilFamilia the jCBEstadoCivilFamilia to set
     */
    public void setjCBEstadoCivilFamilia(javax.swing.JComboBox jCBEstadoCivilFamilia) {
        this.jCBEstadoCivilFamilia = jCBEstadoCivilFamilia;
    }

    /**
     * @return the jCBParentesco
     */
    public javax.swing.JComboBox getjCBParentesco() {
        return jCBParentesco;
    }

    /**
     * @param jCBParentesco the jCBParentesco to set
     */
    public void setjCBParentesco(javax.swing.JComboBox jCBParentesco) {
        this.jCBParentesco = jCBParentesco;
    }

    /**
     * @return the jFTAfastamentoPrevidenciaSocial
     */
    public javax.swing.JFormattedTextField getjFTAfastamentoPrevidenciaSocial() {
        return jFTAfastamentoPrevidenciaSocial;
    }

    /**
     * @param jFTAfastamentoPrevidenciaSocial the
     * jFTAfastamentoPrevidenciaSocial to set
     */
    public void setjFTAfastamentoPrevidenciaSocial(javax.swing.JFormattedTextField jFTAfastamentoPrevidenciaSocial) {
        this.jFTAfastamentoPrevidenciaSocial = jFTAfastamentoPrevidenciaSocial;
    }

    /**
     * @return the jFTAposentadoria
     */
    public javax.swing.JFormattedTextField getjFTAposentadoria() {
        return jFTAposentadoria;
    }

    /**
     * @param jFTAposentadoria the jFTAposentadoria to set
     */
    public void setjFTAposentadoria(javax.swing.JFormattedTextField jFTAposentadoria) {
        this.jFTAposentadoria = jFTAposentadoria;
    }

    /**
     * @return the jFTBairro
     */
    public javax.swing.JFormattedTextField getjFTBairro() {
        return jFTBairro;
    }

    /**
     * @param jFTBairro the jFTBairro to set
     */
    public void setjFTBairro(javax.swing.JFormattedTextField jFTBairro) {
        this.jFTBairro = jFTBairro;
    }

    /**
     * @return the jFTCEP
     */
    public javax.swing.JFormattedTextField getjFTCEP() {
        return jFTCEP;
    }

    /**
     * @param jFTCEP the jFTCEP to set
     */
    public void setjFTCEP(javax.swing.JFormattedTextField jFTCEP) {
        this.jFTCEP = jFTCEP;
    }

    /**
     * @return the jFTCPF
     */
    public javax.swing.JFormattedTextField getjFTCPF() {
        return jFTCPF;
    }

    /**
     * @param jFTCPF the jFTCPF to set
     */
    public void setjFTCPF(javax.swing.JFormattedTextField jFTCPF) {
        this.jFTCPF = jFTCPF;
    }

    /**
     * @return the jFTCarteiraAssinada
     */
    public javax.swing.JFormattedTextField getjFTCarteiraAssinada() {
        return jFTCarteiraAssinada;
    }

    /**
     * @param jFTCarteiraAssinada the jFTCarteiraAssinada to set
     */
    public void setjFTCarteiraAssinada(javax.swing.JFormattedTextField jFTCarteiraAssinada) {
        this.jFTCarteiraAssinada = jFTCarteiraAssinada;
    }

    /**
     * @return the jFTCarteiradeTrabalho
     */
    public javax.swing.JFormattedTextField getjFTCarteiradeTrabalho() {
        return jFTCarteiradeTrabalho;
    }

    /**
     * @param jFTCarteiradeTrabalho the jFTCarteiradeTrabalho to set
     */
    public void setjFTCarteiradeTrabalho(javax.swing.JFormattedTextField jFTCarteiradeTrabalho) {
        this.jFTCarteiradeTrabalho = jFTCarteiradeTrabalho;
    }

    /**
     * @return the jFTCasodeDrogas
     */
    public javax.swing.JFormattedTextField getjFTCasodeDrogas() {
        return jFTCasodeDrogas;
    }

    /**
     * @param jFTCasodeDrogas the jFTCasodeDrogas to set
     */
    public void setjFTCasodeDrogas(javax.swing.JFormattedTextField jFTCasodeDrogas) {
        this.jFTCasodeDrogas = jFTCasodeDrogas;
    }

    /**
     * @return the jFTCidade
     */
    public javax.swing.JFormattedTextField getjFTCidade() {
        return jFTCidade;
    }

    /**
     * @param jFTCidade the jFTCidade to set
     */
    public void setjFTCidade(javax.swing.JFormattedTextField jFTCidade) {
        this.jFTCidade = jFTCidade;
    }

    /**
     * @return the jFTClinica
     */
    public javax.swing.JFormattedTextField getjFTClinica() {
        return jFTClinica;
    }

    /**
     * @param jFTClinica the jFTClinica to set
     */
    public void setjFTClinica(javax.swing.JFormattedTextField jFTClinica) {
        this.jFTClinica = jFTClinica;
    }

    /**
     * @return the jFTComplemento
     */
    public javax.swing.JFormattedTextField getjFTComplemento() {
        return jFTComplemento;
    }

    /**
     * @param jFTComplemento the jFTComplemento to set
     */
    public void setjFTComplemento(javax.swing.JFormattedTextField jFTComplemento) {
        this.jFTComplemento = jFTComplemento;
    }

    /**
     * @return the jFTEndereco
     */
    public javax.swing.JFormattedTextField getjFTRua() {
        return jFTRua;
    }

    /**
     * @param jFTEndereco the jFTEndereco to set
     */
    public void setjFTEndereco(javax.swing.JFormattedTextField jFTEndereco) {
        this.setjFTRua(jFTEndereco);
    }

    /**
     * @param jFTEntrevistadora the jFTEntrevistadora to set
     */
    public void setjFTEntrevistadora(javax.swing.JFormattedTextField jFTEntrevistadora) {
        this.setjFTEntrevistadoraUm(jFTEntrevistadora);
    }

    /**
     * @return the jFTIdade
     */
    public javax.swing.JFormattedTextField getjFTIdade() {
        return jFTIdade;
    }

    /**
     * @param jFTIdade the jFTIdade to set
     */
    public void setjFTIdade(javax.swing.JFormattedTextField jFTIdade) {
        this.jFTIdade = jFTIdade;
    }

    /**
     * @return the jFTIdadeFamilia
     */
    public javax.swing.JFormattedTextField getjFTIdadeFamilia() {
        return jFTIdadeFamilia;
    }

    /**
     * @param jFTIdadeFamilia the jFTIdadeFamilia to set
     */
    public void setjFTIdadeFamilia(javax.swing.JFormattedTextField jFTIdadeFamilia) {
        this.jFTIdadeFamilia = jFTIdadeFamilia;
    }

    /**
     * @return the jFTNome
     */
    public javax.swing.JFormattedTextField getjFTNome() {
        return jFTNome;
    }

    /**
     * @param jFTNome the jFTNome to set
     */
    public void setjFTNome(javax.swing.JFormattedTextField jFTNome) {
        this.jFTNome = jFTNome;
    }

    /**
     * @return the jFTNomeFamilia
     */
    public javax.swing.JFormattedTextField getjFTNomeFamilia() {
        return jFTNomeFamilia;
    }

    /**
     * @param jFTNomeFamilia the jFTNomeFamilia to set
     */
    public void setjFTNomeFamilia(javax.swing.JFormattedTextField jFTNomeFamilia) {
        this.jFTNomeFamilia = jFTNomeFamilia;
    }

    /**
     * @return the jFTNumero
     */
    public javax.swing.JFormattedTextField getjFTNumero() {
        return jFTNumero;
    }

    /**
     * @param jFTNumero the jFTNumero to set
     */
    public void setjFTNumero(javax.swing.JFormattedTextField jFTNumero) {
        this.jFTNumero = jFTNumero;
    }

    /**
     * @return the jFTPIS
     */
    public javax.swing.JFormattedTextField getjFTPIS() {
        return jFTPIS;
    }

    /**
     * @param jFTPIS the jFTPIS to set
     */
    public void setjFTPIS(javax.swing.JFormattedTextField jFTPIS) {
        this.jFTPIS = jFTPIS;
    }

    /**
     * @return the jFTProfissao
     */
    public javax.swing.JFormattedTextField getjFTProfissao() {
        return jFTProfissao;
    }

    /**
     * @param jFTProfissao the jFTProfissao to set
     */
    public void setjFTProfissao(javax.swing.JFormattedTextField jFTProfissao) {
        this.jFTProfissao = jFTProfissao;
    }

    /**
     * @return the jFTRG
     */
    public javax.swing.JFormattedTextField getjFTRG() {
        return jFTRG;
    }

    /**
     * @param jFTRG the jFTRG to set
     */
    public void setjFTRG(javax.swing.JFormattedTextField jFTRG) {
        this.jFTRG = jFTRG;
    }

    /**
     * @return the jFTResponsavelFamilia
     */
    /**
     * @return the jFTSalarioDesemprego
     */
    public javax.swing.JFormattedTextField getjFTSalarioDesemprego() {
        return jFTSalarioDesemprego;
    }

    /**
     * @param jFTSalarioDesemprego the jFTSalarioDesemprego to set
     */
    public void setjFTSalarioDesemprego(javax.swing.JFormattedTextField jFTSalarioDesemprego) {
        this.jFTSalarioDesemprego = jFTSalarioDesemprego;
    }

    /**
     * @return the jFTSecao
     */
    public javax.swing.JFormattedTextField getjFTSecao() {
        return jFTSecao;
    }

    /**
     * @param jFTSecao the jFTSecao to set
     */
    public void setjFTSecao(javax.swing.JFormattedTextField jFTSecao) {
        this.jFTSecao = jFTSecao;
    }

    /**
     * @return the jFTSexualidade
     */
    /**
     * @return the jFTTelefone
     */
    public javax.swing.JFormattedTextField getjFTTelefone() {
        return jFTTelefone;
    }

    /**
     * @param jFTTelefone the jFTTelefone to set
     */
    public void setjFTTelefone(javax.swing.JFormattedTextField jFTTelefone) {
        this.jFTTelefone = jFTTelefone;
    }

    /**
     * @return the jFTTituloEleitoral
     */
    public javax.swing.JFormattedTextField getjFTTituloEleitoral() {
        return jFTTituloEleitoral;
    }

    /**
     * @param jFTTituloEleitoral the jFTTituloEleitoral to set
     */
    public void setjFTTituloEleitoral(javax.swing.JFormattedTextField jFTTituloEleitoral) {
        this.jFTTituloEleitoral = jFTTituloEleitoral;
    }

    /**
     * @return the jFTTrabalhoAtual
     */
    public javax.swing.JFormattedTextField getjFTTrabalhoAtual() {
        return jFTTrabalhoAtual;
    }

    /**
     * @param jFTTrabalhoAtual the jFTTrabalhoAtual to set
     */
    public void setjFTTrabalhoAtual(javax.swing.JFormattedTextField jFTTrabalhoAtual) {
        this.jFTTrabalhoAtual = jFTTrabalhoAtual;
    }

    /**
     * @return the jFTUF
     */
    public javax.swing.JComboBox getjCBUF() {
        return jCBUF;
    }

    /**
     * @param jFTUF the jFTUF to set
     */
    public void setjFTUF(javax.swing.JComboBox jCBUF) {
        this.setjCBUF(jCBUF);
    }

    /**
     * @return the jFTZona
     */
    public javax.swing.JFormattedTextField getjFTZona() {
        return jFTZona;
    }

    /**
     * @param jFTZona the jFTZona to set
     */
    public void setjFTZona(javax.swing.JFormattedTextField jFTZona) {
        this.jFTZona = jFTZona;
    }

    /**
     * @return the jMBMenu
     */
    public javax.swing.JMenuBar getjMBMenu() {
        return jMBMenu;
    }

    /**
     * @param jMBMenu the jMBMenu to set
     */
    public void setjMBMenu(javax.swing.JMenuBar jMBMenu) {
        this.jMBMenu = jMBMenu;
    }

    /**
     * @return the jMOpcoes
     */
    public javax.swing.JMenu getjMOpcoes() {
        return jMOpcoes;
    }

    /**
     * @param jMOpcoes the jMOpcoes to set
     */
    public void setjMOpcoes(javax.swing.JMenu jMOpcoes) {
        this.jMOpcoes = jMOpcoes;
    }

    /**
     * @return the jMSair
     */
    public javax.swing.JMenu getjMSair() {
        return jMSair;
    }

    /**
     * @param jMSair the jMSair to set
     */
    public void setjMSair(javax.swing.JMenu jMSair) {
        this.jMSair = jMSair;
    }

    /**
     * @return the jMenuAlterar
     */
    public javax.swing.JMenuItem getjMenuAlterar() {
        return jMenuAlterar;
    }

    /**
     * @param jMenuAlterar the jMenuAlterar to set
     */
    public void setjMenuAlterar(javax.swing.JMenuItem jMenuAlterar) {
        this.jMenuAlterar = jMenuAlterar;
    }

    /**
     * @return the jMenuCadastrar
     */
    public javax.swing.JMenuItem getjMenuCadastrar() {
        return jMenuCadastrar;
    }

    /**
     * @param jMenuCadastrar the jMenuCadastrar to set
     */
    public void setjMenuCadastrar(javax.swing.JMenuItem jMenuCadastrar) {
        this.jMenuCadastrar = jMenuCadastrar;
    }

    /**
     * @return the jMenuCancelar
     */
    public javax.swing.JMenuItem getjMenuCancelar() {
        return jMenuCancelar;
    }

    /**
     * @param jMenuCancelar the jMenuCancelar to set
     */
    public void setjMenuCancelar(javax.swing.JMenuItem jMenuCancelar) {
        this.jMenuCancelar = jMenuCancelar;
    }

    /**
     * @return the jMenuExcluir
     */
    public javax.swing.JMenuItem getjMenuExcluir() {
        return jMenuExcluir;
    }

    /**
     * @param jMenuExcluir the jMenuExcluir to set
     */
    public void setjMenuExcluir(javax.swing.JMenuItem jMenuExcluir) {
        this.jMenuExcluir = jMenuExcluir;
    }

    /**
     * @return the jMenuFechar
     */
    public javax.swing.JMenuItem getjMenuFechar() {
        return jMenuFechar;
    }

    /**
     * @param jMenuFechar the jMenuFechar to set
     */
    public void setjMenuFechar(javax.swing.JMenuItem jMenuFechar) {
        this.jMenuFechar = jMenuFechar;
    }

    /**
     * @return the jMenuNovo
     */
    public javax.swing.JMenuItem getjMenuNovo() {
        return jMenuNovo;
    }

    /**
     * @param jMenuNovo the jMenuNovo to set
     */
    public void setjMenuNovo(javax.swing.JMenuItem jMenuNovo) {
        this.jMenuNovo = jMenuNovo;
    }

    /**
     * @return the jMenuPesquisar
     */
    public javax.swing.JMenuItem getjMenuPesquisar() {
        return jMenuPesquisar;
    }

    /**
     * @param jMenuPesquisar the jMenuPesquisar to set
     */
    public void setjMenuPesquisar(javax.swing.JMenuItem jMenuPesquisar) {
        this.jMenuPesquisar = jMenuPesquisar;
    }

    /**
     * @return the jPControles
     */
    public javax.swing.JPanel getjPControles() {
        return jPControles;
    }

    /**
     * @param jPControles the jPControles to set
     */
    public void setjPControles(javax.swing.JPanel jPControles) {
        this.jPControles = jPControles;
    }

    /**
     * @return the jPDadosPessoais
     */
    public javax.swing.JPanel getjPDadosPessoais() {
        return jPDadosPessoais;
    }

    /**
     * @param jPDadosPessoais the jPDadosPessoais to set
     */
    public void setjPDadosPessoais(javax.swing.JPanel jPDadosPessoais) {
        this.jPDadosPessoais = jPDadosPessoais;
    }

    /**
     * @return the jPDependencia
     */
    public javax.swing.JPanel getjPDependencia() {
        return jPDependencia;
    }

    /**
     * @param jPDependencia the jPDependencia to set
     */
    public void setjPDependencia(javax.swing.JPanel jPDependencia) {
        this.jPDependencia = jPDependencia;
    }

    /**
     * @return the jPDependenciaGeral
     */
    public javax.swing.JPanel getjPDependenciaGeral() {
        return jPDependenciaGeral;
    }

    /**
     * @param jPDependenciaGeral the jPDependenciaGeral to set
     */
    public void setjPDependenciaGeral(javax.swing.JPanel jPDependenciaGeral) {
        this.jPDependenciaGeral = jPDependenciaGeral;
    }

    /**
     * @return the jPDocumentacao
     */
    public javax.swing.JPanel getjPDocumentacao() {
        return jPDocumentacao;
    }

    /**
     * @param jPDocumentacao the jPDocumentacao to set
     */
    public void setjPDocumentacao(javax.swing.JPanel jPDocumentacao) {
        this.jPDocumentacao = jPDocumentacao;
    }

    /**
     * @return the jPEndereco
     */
    public javax.swing.JPanel getjPEndereco() {
        return jPEndereco;
    }

    /**
     * @param jPEndereco the jPEndereco to set
     */
    public void setjPEndereco(javax.swing.JPanel jPEndereco) {
        this.jPEndereco = jPEndereco;
    }

    /**
     * @return the jPFamiliaeSocial
     */
    public javax.swing.JPanel getjPFamiliaeSocial() {
        return jPFamiliaeSocial;
    }

    /**
     * @param jPFamiliaeSocial the jPFamiliaeSocial to set
     */
    public void setjPFamiliaeSocial(javax.swing.JPanel jPFamiliaeSocial) {
        this.jPFamiliaeSocial = jPFamiliaeSocial;
    }

    /**
     * @return the jPFoto
     */
    public javax.swing.JPanel getjPFoto() {
        return jPFoto;
    }

    /**
     * @param jPFoto the jPFoto to set
     */
    public void setjPFoto(javax.swing.JPanel jPFoto) {
        this.jPFoto = jPFoto;
    }

    /**
     * @return the jPInternoGeral
     */
    public javax.swing.JPanel getjPInternoGeral() {
        return jPInternoGeral;
    }

    /**
     * @param jPInternoGeral the jPInternoGeral to set
     */
    public void setjPInternoGeral(javax.swing.JPanel jPInternoGeral) {
        this.jPInternoGeral = jPInternoGeral;
    }

    /**
     * @return the jPOutrasInformacoes
     */
    public javax.swing.JPanel getjPOutrasInformacoes() {
        return getjPOutrasInformacoesGeral();
    }

    /**
     * @param jPOutrasInformacoes the jPOutrasInformacoes to set
     */
    public void setjPOutrasInformacoes(javax.swing.JPanel jPOutrasInformacoes) {
        this.setjPOutrasInformacoesGeral(jPOutrasInformacoes);
    }

    /**
     * @return the jPPesquisaGeral
     */
    public javax.swing.JPanel getjPPesquisaGeral() {
        return jPPesquisaGeral;
    }

    /**
     * @param jPPesquisaGeral the jPPesquisaGeral to set
     */
    public void setjPPesquisaGeral(javax.swing.JPanel jPPesquisaGeral) {
        this.jPPesquisaGeral = jPPesquisaGeral;
    }

    /**
     * @return the jPPesquisar
     */
    public javax.swing.JPanel getjPPesquisar() {
        return jPPesquisar;
    }

    /**
     * @param jPPesquisar the jPPesquisar to set
     */
    public void setjPPesquisar(javax.swing.JPanel jPPesquisar) {
        this.jPPesquisar = jPPesquisar;
    }

    /**
     * @return the jPPesquisarPor
     */
    public javax.swing.JPanel getjPPesquisarPor() {
        return jPPesquisarPor;
    }

    /**
     * @param jPPesquisarPor the jPPesquisarPor to set
     */
    public void setjPPesquisarPor(javax.swing.JPanel jPPesquisarPor) {
        this.jPPesquisarPor = jPPesquisarPor;
    }

    /**
     * @return the jPProcesso
     */
    public javax.swing.JPanel getjPProcesso() {
        return jPProcesso;
    }

    /**
     * @param jPProcesso the jPProcesso to set
     */
    public void setjPProcesso(javax.swing.JPanel jPProcesso) {
        this.jPProcesso = jPProcesso;
    }

    /**
     * @return the jPProfissao
     */
    public javax.swing.JPanel getjPProfissao() {
        return jPProfissao;
    }

    /**
     * @param jPProfissao the jPProfissao to set
     */
    public void setjPProfissao(javax.swing.JPanel jPProfissao) {
        this.jPProfissao = jPProfissao;
    }

    /**
     * @return the jPQuestionario
     */
    public javax.swing.JPanel getjPQuestionario() {
        return jPQuestionario;
    }

    /**
     * @param jPQuestionario the jPQuestionario to set
     */
    public void setjPQuestionario(javax.swing.JPanel jPQuestionario) {
        this.jPQuestionario = jPQuestionario;
    }

    /**
     * @return the jPQuestionarioGeral
     */
    public javax.swing.JPanel getjPQuestionarioGeral() {
        return jPQuestionarioGeral;
    }

    /**
     * @param jPQuestionarioGeral the jPQuestionarioGeral to set
     */
    public void setjPQuestionarioGeral(javax.swing.JPanel jPQuestionarioGeral) {
        this.jPQuestionarioGeral = jPQuestionarioGeral;
    }

    /**
     * @return the jPSaude
     */
    public javax.swing.JPanel getjPSaude() {
        return jPSaude;
    }

    /**
     * @param jPSaude the jPSaude to set
     */
    public void setjPSaude(javax.swing.JPanel jPSaude) {
        this.jPSaude = jPSaude;
    }

    /**
     * @return the jPTratamentosAnteriores
     */
    public javax.swing.JPanel getjPTratamentosAnteriores() {
        return jPTratamentosAnteriores;
    }

    /**
     * @param jPTratamentosAnteriores the jPTratamentosAnteriores to set
     */
    public void setjPTratamentosAnteriores(javax.swing.JPanel jPTratamentosAnteriores) {
        this.jPTratamentosAnteriores = jPTratamentosAnteriores;
    }

    /**
     * @return the jRBNome
     */
    public javax.swing.JRadioButton getjRBNome() {
        return jRBNome;
    }

    /**
     * @param jRBNome the jRBNome to set
     */
    public void setjRBNome(javax.swing.JRadioButton jRBNome) {
        this.jRBNome = jRBNome;
    }

    /**
     * @return the jSPAlimentacao
     */
    public javax.swing.JScrollPane getjSPAlimentacao() {
        return jSPAlimentacao;
    }

    /**
     * @param jSPAlimentacao the jSPAlimentacao to set
     */
    public void setjSPAlimentacao(javax.swing.JScrollPane jSPAlimentacao) {
        this.jSPAlimentacao = jSPAlimentacao;
    }

    /**
     * @return the jSPAlucinacaoDelirio
     */
    public javax.swing.JScrollPane getjSPAlucinacaoDelirio() {
        return jSPAlucinacaoDelirio;
    }

    /**
     * @param jSPAlucinacaoDelirio the jSPAlucinacaoDelirio to set
     */
    public void setjSPAlucinacaoDelirio(javax.swing.JScrollPane jSPAlucinacaoDelirio) {
        this.jSPAlucinacaoDelirio = jSPAlucinacaoDelirio;
    }

    /**
     * @return the jSPAutoexterminio
     */
    public javax.swing.JScrollPane getjSPAutoexterminio() {
        return jSPAutoexterminio;
    }

    /**
     * @param jSPAutoexterminio the jSPAutoexterminio to set
     */
    public void setjSPAutoexterminio(javax.swing.JScrollPane jSPAutoexterminio) {
        this.jSPAutoexterminio = jSPAutoexterminio;
    }

    /**
     * @return the jSPConhecimentoColonia
     */
    public javax.swing.JScrollPane getjSPConhecimentoColonia() {
        return jSPConhecimentoColonia;
    }

    /**
     * @param jSPConhecimentoColonia the jSPConhecimentoColonia to set
     */
    public void setjSPConhecimentoColonia(javax.swing.JScrollPane jSPConhecimentoColonia) {
        this.jSPConhecimentoColonia = jSPConhecimentoColonia;
    }

    /**
     * @return the jSPDemaisConvulsoes
     */
    public javax.swing.JScrollPane getjSPDemaisConvulsoes() {
        return jSPDemaisConvulsoes;
    }

    /**
     * @param jSPDemaisConvulsoes the jSPDemaisConvulsoes to set
     */
    public void setjSPDemaisConvulsoes(javax.swing.JScrollPane jSPDemaisConvulsoes) {
        this.jSPDemaisConvulsoes = jSPDemaisConvulsoes;
    }

    /**
     * @return the jSPDroga
     */
    /**
     * @return the jSPExamesEspecificos
     */
    public javax.swing.JScrollPane getjSPExamesEspecificos() {
        return jSPExamesEspecificos;
    }

    /**
     * @param jSPExamesEspecificos the jSPExamesEspecificos to set
     */
    public void setjSPExamesEspecificos(javax.swing.JScrollPane jSPExamesEspecificos) {
        this.jSPExamesEspecificos = jSPExamesEspecificos;
    }

    /**
     * @return the jSPExpectativaTratamento
     */
    public javax.swing.JScrollPane getjSPExpectativaTratamento() {
        return jSPExpectativaTratamento;
    }

    /**
     * @param jSPExpectativaTratamento the jSPExpectativaTratamento to set
     */
    public void setjSPExpectativaTratamento(javax.swing.JScrollPane jSPExpectativaTratamento) {
        this.jSPExpectativaTratamento = jSPExpectativaTratamento;
    }

    /**
     * @return the jSPLocal
     */
    public javax.swing.JScrollPane getjSPLocal() {
        return jSPLocal;
    }

    /**
     * @param jSPLocal the jSPLocal to set
     */
    public void setjSPLocal(javax.swing.JScrollPane jSPLocal) {
        this.jSPLocal = jSPLocal;
    }

    /**
     * @return the jSPMedicacao
     */
    public javax.swing.JScrollPane getjSPMedicacao() {
        return jSPMedicacao;
    }

    /**
     * @param jSPMedicacao the jSPMedicacao to set
     */
    public void setjSPMedicacao(javax.swing.JScrollPane jSPMedicacao) {
        this.jSPMedicacao = jSPMedicacao;
    }

    /**
     * @return the jSPNumeroInterno
     */
    /**
     * @return the jSPObservacoes
     */
    public javax.swing.JScrollPane getjSPObservacoes() {
        return jSPObservacoes;
    }

    /**
     * @param jSPObservacoes the jSPObservacoes to set
     */
    public void setjSPObservacoes(javax.swing.JScrollPane jSPObservacoes) {
        this.jSPObservacoes = jSPObservacoes;
    }

    /**
     * @return the jSPPesquisa
     */
    public javax.swing.JScrollPane getjSPPesquisa() {
        return jSPPesquisa;
    }

    /**
     * @param jSPPesquisa the jSPPesquisa to set
     */
    public void setjSPPesquisa(javax.swing.JScrollPane jSPPesquisa) {
        this.jSPPesquisa = jSPPesquisa;
    }

    /**
     * @return the jSPSaudeIdentificador
     */
    /**
     * @return the jSPSono
     */
    public javax.swing.JScrollPane getjSPSono() {
        return jSPSono;
    }

    /**
     * @param jSPSono the jSPSono to set
     */
    public void setjSPSono(javax.swing.JScrollPane jSPSono) {
        this.jSPSono = jSPSono;
    }

    /**
     * @return the jSPTempodeUso
     */
    public javax.swing.JScrollPane getjSPTempodeUso() {
        return jSPTempodeUso;
    }

    /**
     * @param jSPTempodeUso the jSPTempodeUso to set
     */
    public void setjSPTempodeUso(javax.swing.JScrollPane jSPTempodeUso) {
        this.jSPTempodeUso = jSPTempodeUso;
    }

    /**
     * @return the jSPUsadaAtualmente
     */
    public javax.swing.JScrollPane getjSPUsadaAtualmente() {
        return jSPUsadaAtualmente;
    }

    /**
     * @param jSPUsadaAtualmente the jSPUsadaAtualmente to set
     */
    public void setjSPUsadaAtualmente(javax.swing.JScrollPane jSPUsadaAtualmente) {
        this.jSPUsadaAtualmente = jSPUsadaAtualmente;
    }

    /**
     * @return the jTAAlimentacao
     */
    public javax.swing.JTextArea getjTAAlimentacao() {
        return jTAAlimentacao;
    }

    /**
     * @param jTAAlimentacao the jTAAlimentacao to set
     */
    public void setjTAAlimentacao(javax.swing.JTextArea jTAAlimentacao) {
        this.jTAAlimentacao = jTAAlimentacao;
    }

    /**
     * @return the jTAAlucinacaoDelirio
     */
    public javax.swing.JTextArea getjTAAlucinacaoDelirio() {
        return jTAAlucinacaoDelirio;
    }

    /**
     * @param jTAAlucinacaoDelirio the jTAAlucinacaoDelirio to set
     */
    public void setjTAAlucinacaoDelirio(javax.swing.JTextArea jTAAlucinacaoDelirio) {
        this.jTAAlucinacaoDelirio = jTAAlucinacaoDelirio;
    }

    /**
     * @return the jTAAutoextermino
     */
    public javax.swing.JTextArea getjTAAutoextermino() {
        return jTAAutoextermino;
    }

    /**
     * @param jTAAutoextermino the jTAAutoextermino to set
     */
    public void setjTAAutoextermino(javax.swing.JTextArea jTAAutoextermino) {
        this.jTAAutoextermino = jTAAutoextermino;
    }

    /**
     * @return the jTAConhecimentoColonia
     */
    public javax.swing.JTextArea getjTAConhecimentoColonia() {
        return jTAConhecimentoColonia;
    }

    /**
     * @param jTAConhecimentoColonia the jTAConhecimentoColonia to set
     */
    public void setjTAConhecimentoColonia(javax.swing.JTextArea jTAConhecimentoColonia) {
        this.jTAConhecimentoColonia = jTAConhecimentoColonia;
    }

    /**
     * @return the jTADemaisConvulsoes
     */
    public javax.swing.JTextArea getjTADemaisConvulsoes() {
        return jTADemaisConvulsoes;
    }

    /**
     * @param jTADemaisConvulsoes the jTADemaisConvulsoes to set
     */
    public void setjTADemaisConvulsoes(javax.swing.JTextArea jTADemaisConvulsoes) {
        this.jTADemaisConvulsoes = jTADemaisConvulsoes;
    }

    /**
     * @return the jTAExamesEspecificos
     */
    public javax.swing.JTextArea getjTAExamesEspecificos() {
        return jTAExamesEspecificos;
    }

    /**
     * @param jTAExamesEspecificos the jTAExamesEspecificos to set
     */
    public void setjTAExamesEspecificos(javax.swing.JTextArea jTAExamesEspecificos) {
        this.jTAExamesEspecificos = jTAExamesEspecificos;
    }

    /**
     * @return the jTAExpectativaTratamento
     */
    public javax.swing.JTextArea getjTAExpectativaTratamento() {
        return jTAExpectativaTratamento;
    }

    /**
     * @param jTAExpectativaTratamento the jTAExpectativaTratamento to set
     */
    public void setjTAExpectativaTratamento(javax.swing.JTextArea jTAExpectativaTratamento) {
        this.jTAExpectativaTratamento = jTAExpectativaTratamento;
    }

    /**
     * @return the jTALocal
     */
    public javax.swing.JTextArea getjTALocal() {
        return getjTAMotivoSaida();
    }

    /**
     * @param jTALocal the jTALocal to set
     */
    public void setjTALocal(javax.swing.JTextArea jTALocal) {
        this.setjTAMotivoSaida(jTALocal);
    }

    /**
     * @return the jTAMedicacao
     */
    public javax.swing.JTextArea getjTAMedicacao() {
        return jTAMedicacao;
    }

    /**
     * @param jTAMedicacao the jTAMedicacao to set
     */
    public void setjTAMedicacao(javax.swing.JTextArea jTAMedicacao) {
        this.jTAMedicacao = jTAMedicacao;
    }

    /**
     * @return the jTANumeroInterno
     */
    /**
     * @return the jTAObservacoes
     */
    public javax.swing.JTextArea getjTAObservacoes() {
        return jTAObservacoes;
    }

    /**
     * @param jTAObservacoes the jTAObservacoes to set
     */
    public void setjTAObservacoes(javax.swing.JTextArea jTAObservacoes) {
        this.jTAObservacoes = jTAObservacoes;
    }

    /**
     * @return the jTASono
     */
    public javax.swing.JTextArea getjTASono() {
        return jTASono;
    }

    /**
     * @param jTASono the jTASono to set
     */
    public void setjTASono(javax.swing.JTextArea jTASono) {
        this.jTASono = jTASono;
    }

    /**
     * @return the jTATempodeUso
     */
    public javax.swing.JTextArea getjTATempodeUso() {
        return jTATempodeUso;
    }

    /**
     * @param jTATempodeUso the jTATempodeUso to set
     */
    public void setjTATempodeUso(javax.swing.JTextArea jTATempodeUso) {
        this.jTATempodeUso = jTATempodeUso;
    }

    /**
     * @return the jTAUsadaAtualmente
     */
    public javax.swing.JTextArea getjTAUsadaAtualmente() {
        return jTAUsadaAtualmente;
    }

    /**
     * @param jTAUsadaAtualmente the jTAUsadaAtualmente to set
     */
    public void setjTAUsadaAtualmente(javax.swing.JTextArea jTAUsadaAtualmente) {
        this.jTAUsadaAtualmente = jTAUsadaAtualmente;
    }

    /**
     * @return the jTFProcurar
     */
    public javax.swing.JTextField getjTFProcurar() {
        return jTFProcurar;
    }

    /**
     * @param jTFProcurar the jTFProcurar to set
     */
    public void setjTFProcurar(javax.swing.JTextField jTFProcurar) {
        this.jTFProcurar = jTFProcurar;
    }

    /**
     * @return the jTPAbas
     */
    public javax.swing.JTabbedPane getjTPAbas() {
        return jTPAbas;
    }

    /**
     * @param jTPAbas the jTPAbas to set
     */
    public void setjTPAbas(javax.swing.JTabbedPane jTPAbas) {
        this.jTPAbas = jTPAbas;
    }

    /**
     * @return the jTPesquisa
     */
    public javax.swing.JTable getjTPesquisa() {
        return jTPesquisa;
    }

    /**
     * @param jTPesquisa the jTPesquisa to set
     */
    public void setjTPesquisa(javax.swing.JTable jTPesquisa) {
        this.jTPesquisa = jTPesquisa;
    }

    /**
     * @return the PROCURAR
     */
    public String getPROCURAR() {
        return PROCURAR;
    }

    /**
     * @return the NOVOF
     */
    public String getNOVOF() {
        return NOVOF;
    }

    /**
     * @return the ALTERARF
     */
    public String getALTERARF() {
        return ALTERARF;
    }

    /**
     * @return the EXCLUIRF
     */
    public String getEXCLUIRF() {
        return EXCLUIRF;
    }

    /**
     * @return the CANCELARF
     */
    public String getCANCELARF() {
        return CANCELARF;
    }

    /**
     * @return the CADASTRARF
     */
    public String getCADASTRARF() {
        return CADASTRARF;
    }

    /**
     * @return the diretorioFotoPadrao
     */
    public String getDiretorioFotoPadrao() {
        return diretorioFotoPadrao;
    }

    /**
     * @param diretorioFotoPadrao the diretorioFotoPadrao to set
     */
    public void setDiretorioFotoPadrao(String diretorioFotoPadrao) {
        this.diretorioFotoPadrao = diretorioFotoPadrao;
    }

    /**
     * @return the modelo
     */
    public Interno getModelo() {
        return modelo;
    }

    /**
     * @param modelo the modelo to set
     */
    public void setModelo(Interno modelo) {
        this.modelo = modelo;
    }

    /**
     * @return the controlador
     */
    public Controlador getControlador() {
        return controlador;
    }

    /**
     * @param controlador the controlador to set
     */
    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    /**
     * @return the Componentes
     */
    public ArrayList<Component> getComponentes() {
        return Componentes;
    }

    /**
     * @param Componentes the Componentes to set
     */
    public void setComponentes(ArrayList<Component> Componentes) {
        this.Componentes = Componentes;
    }

    /**
     * @return the jBAlterarFamilia
     */
    public javax.swing.JButton getjBAlterarFamilia() {
        return jBAlterarFamilia;
    }

    /**
     * @param jBAlterarFamilia the jBAlterarFamilia to set
     */
    public void setjBAlterarFamilia(javax.swing.JButton jBAlterarFamilia) {
        this.jBAlterarFamilia = jBAlterarFamilia;
    }

    /**
     * @param jBAlterarFotoAntes the jBAlterarFotoAntes to set
     */
    public void setjBAlterarFotoAntes(javax.swing.JButton jBAlterarFotoAntes) {
        this.jBAlterarFotoAntes = jBAlterarFotoAntes;
    }

    /**
     * @return the jBCadastrarFamilia
     */
    public javax.swing.JButton getjBCadastrarFamilia() {
        return jBCadastrarFamilia;
    }

    /**
     * @param jBCadastrarFamilia the jBCadastrarFamilia to set
     */
    public void setjBCadastrarFamilia(javax.swing.JButton jBCadastrarFamilia) {
        this.jBCadastrarFamilia = jBCadastrarFamilia;
    }

    /**
     * @return the jBCancelarFamilia
     */
    public javax.swing.JButton getjBCancelarFamilia() {
        return jBCancelarFamilia;
    }

    /**
     * @param jBCancelarFamilia the jBCancelarFamilia to set
     */
    public void setjBCancelarFamilia(javax.swing.JButton jBCancelarFamilia) {
        this.jBCancelarFamilia = jBCancelarFamilia;
    }

    /**
     * @return the jBExcluirFamilia
     */
    public javax.swing.JButton getjBExcluirFamilia() {
        return jBExcluirFamilia;
    }

    /**
     * @param jBExcluirFamilia the jBExcluirFamilia to set
     */
    public void setjBExcluirFamilia(javax.swing.JButton jBExcluirFamilia) {
        this.jBExcluirFamilia = jBExcluirFamilia;
    }

    /**
     * @return the jBNovoFamilia
     */
    public javax.swing.JButton getjBNovoFamilia() {
        return jBNovoFamilia;
    }

    /**
     * @param jBNovoFamilia the jBNovoFamilia to set
     */
    public void setjBNovoFamilia(javax.swing.JButton jBNovoFamilia) {
        this.jBNovoFamilia = jBNovoFamilia;
    }

    /**
     * @param jCBUF the jCBUF to set
     */
    public void setjCBUF(javax.swing.JComboBox jCBUF) {
        this.jCBUF = jCBUF;
    }

    /**
     * @return the jFTNaturalidade
     */
    public javax.swing.JFormattedTextField getjFTNaturalidade() {
        return jFTNaturalidade;
    }

    /**
     * @param jFTNaturalidade the jFTNaturalidade to set
     */
    public void setjFTNaturalidade(javax.swing.JFormattedTextField jFTNaturalidade) {
        this.jFTNaturalidade = jFTNaturalidade;
    }

    /**
     * @return the jFTRelacionamento
     */
    public javax.swing.JFormattedTextField getjFTRelacionamento() {
        return jFTRelacionamento;
    }

    /**
     * @param jFTRelacionamento the jFTRelacionamento to set
     */
    public void setjFTRelacionamento(javax.swing.JFormattedTextField jFTRelacionamento) {
        this.jFTRelacionamento = jFTRelacionamento;
    }

    /**
     * @param jFTRua the jFTRua to set
     */
    public void setjFTRua(javax.swing.JFormattedTextField jFTRua) {
        this.jFTRua = jFTRua;
    }

    /**
     * @return the jFTTempoCasado
     */
    public javax.swing.JFormattedTextField getjFTTempoCasado() {
        return jFTTempoCasado;
    }

    /**
     * @param jFTTempoCasado the jFTTempoCasado to set
     */
    public void setjFTTempoCasado(javax.swing.JFormattedTextField jFTTempoCasado) {
        this.setjFTFalecido(jFTTempoCasado);
    }

    /**
     * @return the jLNome
     */
    public javax.swing.JLabel getjLNome() {
        return jLNome;
    }

    /**
     * @return the jMIFechar
     */
    public javax.swing.JMenuItem getjMIFechar() {
        return jMIFechar;
    }

    /**
     * @param jMIFechar the jMIFechar to set
     */
    public void setjMIFechar(javax.swing.JMenuItem jMIFechar) {
        this.jMIFechar = jMIFechar;
    }

    /**
     * @return the jPControlesFamilia
     */
    public javax.swing.JPanel getjPControlesFamilia() {
        return jPControlesFamilia;
    }

    /**
     * @param jPControlesFamilia the jPControlesFamilia to set
     */
    public void setjPControlesFamilia(javax.swing.JPanel jPControlesFamilia) {
        this.jPControlesFamilia = jPControlesFamilia;
    }

    /**
     * @return the jPFamiliaGeral
     */
    public javax.swing.JPanel getjPFamiliaGeral() {
        return jPFamiliaGeral;
    }

    /**
     * @param jPFamiliaGeral the jPFamiliaGeral to set
     */
    public void setjPFamiliaGeral(javax.swing.JPanel jPFamiliaGeral) {
        this.jPFamiliaGeral = jPFamiliaGeral;
    }

    /**
     * @return the jPIControles
     */
    public javax.swing.JPanel getjPIControles() {
        return jPIControles;
    }

    /**
     * @param jPIControles the jPIControles to set
     */
    public void setjPIControles(javax.swing.JPanel jPIControles) {
        this.jPIControles = jPIControles;
    }

    /**
     * @return the jPObservacao
     */
    public javax.swing.JPanel getjPObservacao() {
        return jPObservacao;
    }

    /**
     * @param jPObservacao the jPObservacao to set
     */
    public void setjPObservacao(javax.swing.JPanel jPObservacao) {
        this.jPObservacao = jPObservacao;
    }

    /**
     * @return the jPOutrasInformacoesGeral
     */
    public javax.swing.JPanel getjPOutrasInformacoesGeral() {
        return jPOutrasInformacoesGeral;
    }

    /**
     * @param jPOutrasInformacoesGeral the jPOutrasInformacoesGeral to set
     */
    public void setjPOutrasInformacoesGeral(javax.swing.JPanel jPOutrasInformacoesGeral) {
        this.jPOutrasInformacoesGeral = jPOutrasInformacoesGeral;
    }

    /**
     * @return the jScrollPane1
     */
    public javax.swing.JScrollPane getjScrollPane1() {
        return jScrollPane1;
    }

    /**
     * @param jScrollPane1 the jScrollPane1 to set
     */
    public void setjScrollPane1(javax.swing.JScrollPane jScrollPane1) {
        this.jScrollPane1 = jScrollPane1;
    }

    /**
     * @return the jScrollPane2
     */
    public javax.swing.JScrollPane getjScrollPane2() {
        return jScrollPane2;
    }

    /**
     * @param jScrollPane2 the jScrollPane2 to set
     */
    public void setjScrollPane2(javax.swing.JScrollPane jScrollPane2) {
        this.jScrollPane2 = jScrollPane2;
    }

    /**
     * @return the jScrollPane3
     */
    public javax.swing.JScrollPane getjScrollPane3() {
        return jScrollPane3;
    }

    /**
     * @param jScrollPane3 the jScrollPane3 to set
     */
    public void setjScrollPane3(javax.swing.JScrollPane jScrollPane3) {
        this.jScrollPane3 = jScrollPane3;
    }

    /**
     * @return the jScrollPane4
     */
    public javax.swing.JScrollPane getjScrollPane4() {
        return jScrollPane4;
    }

    /**
     * @param jScrollPane4 the jScrollPane4 to set
     */
    public void setjScrollPane4(javax.swing.JScrollPane jScrollPane4) {
        this.jScrollPane4 = jScrollPane4;
    }

    /**
     * @return the jTAMotivoLevouDrogas
     */
    public javax.swing.JTextArea getjTAMotivoLevouDrogas() {
        return jTAMotivoLevouDrogas;
    }

    /**
     * @param jTAMotivoLevouDrogas the jTAMotivoLevouDrogas to set
     */
    public void setjTAMotivoLevouDrogas(javax.swing.JTextArea jTAMotivoLevouDrogas) {
        this.jTAMotivoLevouDrogas = jTAMotivoLevouDrogas;
    }

    /**
     * @return the jTARelacionamentoAfetivo
     */
    public javax.swing.JTextArea getjTARelacionamentoAfetivo() {
        return jTARelacionamentoAfetivo;
    }

    /**
     * @param jTARelacionamentoAfetivo the jTARelacionamentoAfetivo to set
     */
    public void setjTARelacionamentoAfetivo(javax.swing.JTextArea jTARelacionamentoAfetivo) {
        this.jTARelacionamentoAfetivo = jTARelacionamentoAfetivo;
    }

    /**
     * @return the jTARelacionamentoSocial
     */
    public javax.swing.JTextArea getjTARelacionamentoSocial() {
        return jTARelacionamentoSocial;
    }

    /**
     * @param jTARelacionamentoSocial the jTARelacionamentoSocial to set
     */
    public void setjTARelacionamentoSocial(javax.swing.JTextArea jTARelacionamentoSocial) {
        this.jTARelacionamentoSocial = jTARelacionamentoSocial;
    }

    /**
     * @return the jTFamilia
     */
    public javax.swing.JTable getjTFamilia() {
        return jTFamilia;
    }

    /**
     * @param jTFamilia the jTFamilia to set
     */
    public void setjTFamilia(javax.swing.JTable jTFamilia) {
        this.jTFamilia = jTFamilia;
    }

    /**
     * @return the jLFotoAntes
     */
    public javax.swing.JLabel getjLFotoAntes() {
        return jLFotoAntes;
    }

    /**
     * @param jLFotoAntes the jLFotoAntes to set
     */
    public void setjLFotoAntes(javax.swing.JLabel jLFotoAntes) {
        this.jLFotoAntes = jLFotoAntes;
    }

    /**
     * @return the jLFotoDepois
     */
    public javax.swing.JLabel getjLFotoDepois() {
        return jLFotoDepois;
    }

    /**
     * @param jLFotoDepois the jLFotoDepois to set
     */
    public void setjLFotoDepois(javax.swing.JLabel jLFotoDepois) {
        this.jLFotoDepois = jLFotoDepois;
    }

    /**
     * @return the interno
     */
    public ArrayList<Interno> getInterno() {
        return interno;
    }

    /**
     * @param interno the interno to set
     */
    public void setInterno(ArrayList<Interno> interno) {
        this.interno = interno;
    }

    /**
     * @return the diretorio_FOTO_ANTES
     */
    public String getDiretorio_FOTO_ANTES() {
        return diretorio_FOTO_ANTES;
    }

    /**
     * @param diretorio_FOTO_ANTES the diretorio_FOTO_ANTES to set
     */
    public void setDiretorio_FOTO_ANTES(String diretorio_FOTO_ANTES) {
        this.diretorio_FOTO_ANTES = diretorio_FOTO_ANTES;
    }

    /**
     * @return the diretorio_FOTO_DEPOIS
     */
    public String getDiretorio_FOTO_DEPOIS() {
        return diretorio_FOTO_DEPOIS;
    }

    /**
     * @param diretorio_FOTO_DEPOIS the diretorio_FOTO_DEPOIS to set
     */
    public void setDiretorio_FOTO_DEPOIS(String diretorio_FOTO_DEPOIS) {
        this.diretorio_FOTO_DEPOIS = diretorio_FOTO_DEPOIS;
    }

    /**
     * @return the bGEnderecoBoleto
     */
    public javax.swing.ButtonGroup getbGEnderecoBoleto() {
        return bGEnderecoBoleto;
    }

    /**
     * @param bGEnderecoBoleto the bGEnderecoBoleto to set
     */
    public void setbGEnderecoBoleto(javax.swing.ButtonGroup bGEnderecoBoleto) {
        this.bGEnderecoBoleto = bGEnderecoBoleto;
    }

    /**
     * @return the bGSexo
     */
    public javax.swing.ButtonGroup getbGSexo() {
        return bGSexo;
    }

    /**
     * @param bGSexo the bGSexo to set
     */
    public void setbGSexo(javax.swing.ButtonGroup bGSexo) {
        this.bGSexo = bGSexo;
    }

    /**
     * @return the jCBUF1
     */
    public javax.swing.JComboBox getjCBUF1() {
        return getjCBUFBoleto();
    }

    /**
     * @param jCBUF1 the jCBUF1 to set
     */
    public void setjCBUF1(javax.swing.JComboBox jCBUF1) {
        this.setjCBUFBoleto(jCBUF1);
    }

    /**
     * @return the jCKBOutrasDrogas
     */
    /**
     * @return the jFTAlcool
     */
    public javax.swing.JFormattedTextField getjFTAlcool() {
        return jFTAlcool;
    }

    /**
     * @param jFTAlcool the jFTAlcool to set
     */
    public void setjFTAlcool(javax.swing.JFormattedTextField jFTAlcool) {
        this.jFTAlcool = jFTAlcool;
    }

    /**
     * @return the jFTBairro1
     */
    public javax.swing.JFormattedTextField getjFTBairro1() {
        return getjFTBairroBoleto();
    }

    /**
     * @param jFTBairro1 the jFTBairro1 to set
     */
    public void setjFTBairro1(javax.swing.JFormattedTextField jFTBairro1) {
        this.setjFTBairroBoleto(jFTBairro1);
    }

    /**
     * @return the jFTCEP1
     */
    public javax.swing.JFormattedTextField getjFTCEP1() {
        return getjFTCEPBoleto();
    }

    /**
     * @param jFTCEP1 the jFTCEP1 to set
     */
    public void setjFTCEP1(javax.swing.JFormattedTextField jFTCEP1) {
        this.setjFTCEPBoleto(jFTCEP1);
    }

    /**
     * @return the jFTCidade1
     */
    public javax.swing.JFormattedTextField getjFTCidade1() {
        return getjFTCidadeBoleto();
    }

    /**
     * @param jFTCidade1 the jFTCidade1 to set
     */
    public void setjFTCidade1(javax.swing.JFormattedTextField jFTCidade1) {
        this.setjFTCidadeBoleto(jFTCidade1);
    }

    /**
     * @return the jFTCigarroTabaco
     */
    public javax.swing.JFormattedTextField getjFTCigarroTabaco() {
        return jFTCigarroTabaco;
    }

    /**
     * @param jFTCigarroTabaco the jFTCigarroTabaco to set
     */
    public void setjFTCigarroTabaco(javax.swing.JFormattedTextField jFTCigarroTabaco) {
        this.jFTCigarroTabaco = jFTCigarroTabaco;
    }

    /**
     * @return the jFTCocainaA
     */
    public javax.swing.JFormattedTextField getjFTCocainaA() {
        return jFTCocainaA;
    }

    /**
     * @param jFTCocainaA the jFTCocainaA to set
     */
    public void setjFTCocainaA(javax.swing.JFormattedTextField jFTCocainaA) {
        this.jFTCocainaA = jFTCocainaA;
    }

    /**
     * @return the jFTCocainaI
     */
    public javax.swing.JFormattedTextField getjFTCocainaI() {
        return jFTCocainaI;
    }

    /**
     * @param jFTCocainaI the jFTCocainaI to set
     */
    public void setjFTCocainaI(javax.swing.JFormattedTextField jFTCocainaI) {
        this.jFTCocainaI = jFTCocainaI;
    }

    /**
     * @return the jFTComplemento1
     */
    public javax.swing.JFormattedTextField getjFTComplemento1() {
        return getjFTComplementoBoleto();
    }

    /**
     * @param jFTComplemento1 the jFTComplemento1 to set
     */
    public void setjFTComplemento1(javax.swing.JFormattedTextField jFTComplemento1) {
        this.setjFTComplementoBoleto(jFTComplemento1);
    }

    /**
     * @return the jFTComprimido
     */
    public javax.swing.JFormattedTextField getjFTComprimido() {
        return jFTComprimido;
    }

    /**
     * @param jFTComprimido the jFTComprimido to set
     */
    public void setjFTComprimido(javax.swing.JFormattedTextField jFTComprimido) {
        this.jFTComprimido = jFTComprimido;
    }

    /**
     * @return the jFTContatoResponsavel
     */
    public javax.swing.JFormattedTextField getjFTContatoResponsavel() {
        return jFTContatoResponsavel;
    }

    /**
     * @param jFTContatoResponsavel the jFTContatoResponsavel to set
     */
    public void setjFTContatoResponsavel(javax.swing.JFormattedTextField jFTContatoResponsavel) {
        this.jFTContatoResponsavel = jFTContatoResponsavel;
    }

    /**
     * @return the jFTCrack
     */
    public javax.swing.JFormattedTextField getjFTCrack() {
        return jFTCrack;
    }

    /**
     * @param jFTCrack the jFTCrack to set
     */
    public void setjFTCrack(javax.swing.JFormattedTextField jFTCrack) {
        this.jFTCrack = jFTCrack;
    }

    /**
     * @return the jFTFalecido
     */
    public javax.swing.JFormattedTextField getjFTFalecido() {
        return jFTFalecido;
    }

    /**
     * @param jFTFalecido the jFTFalecido to set
     */
    public void setjFTFalecido(javax.swing.JFormattedTextField jFTFalecido) {
        this.jFTFalecido = jFTFalecido;
    }

    /**
     * @return the jFTInalantes
     */
    public javax.swing.JFormattedTextField getjFTInalantes() {
        return jFTInalantes;
    }

    /**
     * @param jFTInalantes the jFTInalantes to set
     */
    public void setjFTInalantes(javax.swing.JFormattedTextField jFTInalantes) {
        this.jFTInalantes = jFTInalantes;
    }

    /**
     * @return the jFTMaconha
     */
    public javax.swing.JFormattedTextField getjFTMaconha() {
        return jFTMaconha;
    }

    /**
     * @param jFTMaconha the jFTMaconha to set
     */
    public void setjFTMaconha(javax.swing.JFormattedTextField jFTMaconha) {
        this.jFTMaconha = jFTMaconha;
    }

    /**
     * @return the jFTNomeResponsavel
     */
    public javax.swing.JFormattedTextField getjFTNomeResponsavel() {
        return jFTNomeResponsavel;
    }

    /**
     * @param jFTNomeResponsavel the jFTNomeResponsavel to set
     */
    public void setjFTNomeResponsavel(javax.swing.JFormattedTextField jFTNomeResponsavel) {
        this.jFTNomeResponsavel = jFTNomeResponsavel;
    }

    /**
     * @return the jFTNumero1
     */
    public javax.swing.JFormattedTextField getjFTNumero1() {
        return getjFTNumeroBoleto();
    }

    /**
     * @param jFTNumero1 the jFTNumero1 to set
     */
    public void setjFTNumero1(javax.swing.JFormattedTextField jFTNumero1) {
        this.setjFTNumeroBoleto(jFTNumero1);
    }

    /**
     * @return the jFTOutrasDrogas
     */
    public javax.swing.JFormattedTextField getjFTOutrasDrogas() {
        return jFTOutrasDrogas;
    }

    /**
     * @param jFTOutrasDrogas the jFTOutrasDrogas to set
     */
    public void setjFTOutrasDrogas(javax.swing.JFormattedTextField jFTOutrasDrogas) {
        this.jFTOutrasDrogas = jFTOutrasDrogas;
    }

    /**
     * @return the jFTRendaFamiliar
     */
    public javax.swing.JFormattedTextField getjFTRendaFamiliar() {
        return jFTRendaFamiliar;
    }

    /**
     * @param jFTRendaFamiliar the jFTRendaFamiliar to set
     */
    public void setjFTRendaFamiliar(javax.swing.JFormattedTextField jFTRendaFamiliar) {
        this.jFTRendaFamiliar = jFTRendaFamiliar;
    }

    /**
     * @return the jFTRendaFamiliarSeparado
     */
    public javax.swing.JFormattedTextField getjFTRendaFamiliarSeparado() {
        return jFTRendaFamiliarSeparado;
    }

    /**
     * @param jFTRendaFamiliarSeparado the jFTRendaFamiliarSeparado to set
     */
    public void setjFTRendaFamiliarSeparado(javax.swing.JFormattedTextField jFTRendaFamiliarSeparado) {
        this.jFTRendaFamiliarSeparado = jFTRendaFamiliarSeparado;
    }

    /**
     * @return the jFTRendaPessoal
     */
    public javax.swing.JFormattedTextField getjFTRendaPessoal() {
        return jFTRendaPessoal;
    }

    /**
     * @param jFTRendaPessoal the jFTRendaPessoal to set
     */
    public void setjFTRendaPessoal(javax.swing.JFormattedTextField jFTRendaPessoal) {
        this.jFTRendaPessoal = jFTRendaPessoal;
    }

    /**
     * @return the jFTRendaPessoal1
     */
    public javax.swing.JFormattedTextField getjFTRendaPessoal1() {
        return getjFTAjudaFinanceira();
    }

    /**
     * @param jFTRendaPessoal1 the jFTRendaPessoal1 to set
     */
    public void setjFTRendaPessoal1(javax.swing.JFormattedTextField jFTRendaPessoal1) {
        this.setjFTAjudaFinanceira(jFTRendaPessoal1);
    }

    /**
     * @return the jFTRua1
     */
    public javax.swing.JFormattedTextField getjFTRua1() {
        return getjFTRuaBoleto();
    }

    /**
     * @param jFTRua1 the jFTRua1 to set
     */
    public void setjFTRua1(javax.swing.JFormattedTextField jFTRua1) {
        this.setjFTRuaBoleto(jFTRua1);
    }

    /**
     * @return the jFTTelefone1
     */
    public javax.swing.JFormattedTextField getjFTTelefone1() {
        return getjFTTelefoneBoleto();
    }

    /**
     * @param jFTTelefone1 the jFTTelefone1 to set
     */
    public void setjFTTelefone1(javax.swing.JFormattedTextField jFTTelefone1) {
        this.setjFTTelefoneBoleto(jFTTelefone1);
    }

    /**
     * @return the jFTVencimentoBoleto
     */
    public javax.swing.JFormattedTextField getjFTVencimentoBoleto() {
        return jFTVencimentoBoleto;
    }

    /**
     * @param jFTVencimentoBoleto the jFTVencimentoBoleto to set
     */
    public void setjFTVencimentoBoleto(javax.swing.JFormattedTextField jFTVencimentoBoleto) {
        this.jFTVencimentoBoleto = jFTVencimentoBoleto;
    }

    /**
     * @return the jLAfastamentoPrevidenciaSocial
     */
    public javax.swing.JLabel getjLAfastamentoPrevidenciaSocial() {
        return jLAfastamentoPrevidenciaSocial;
    }

    /**
     * @param jLAfastamentoPrevidenciaSocial the jLAfastamentoPrevidenciaSocial
     * to set
     */
    public void setjLAfastamentoPrevidenciaSocial(javax.swing.JLabel jLAfastamentoPrevidenciaSocial) {
        this.jLAfastamentoPrevidenciaSocial = jLAfastamentoPrevidenciaSocial;
    }

    /**
     * @return the jPBoleto
     */
    public javax.swing.JPanel getjPBoleto() {
        return jPBoleto;
    }

    /**
     * @param jPBoleto the jPBoleto to set
     */
    public void setjPBoleto(javax.swing.JPanel jPBoleto) {
        this.jPBoleto = jPBoleto;
    }

    /**
     * @return the jPDadosResponsavel
     */
    public javax.swing.JPanel getjPDadosResponsavel() {
        return jPDadosResponsavel;
    }

    /**
     * @param jPDadosResponsavel the jPDadosResponsavel to set
     */
    public void setjPDadosResponsavel(javax.swing.JPanel jPDadosResponsavel) {
        this.jPDadosResponsavel = jPDadosResponsavel;
    }

    /**
     * @return the jPDrogas
     */
    public javax.swing.JPanel getjPDrogas() {
        return jPDrogas;
    }

    /**
     * @param jPDrogas the jPDrogas to set
     */
    public void setjPDrogas(javax.swing.JPanel jPDrogas) {
        this.jPDrogas = jPDrogas;
    }

    /**
     * @return the jPEndereco1
     */
    public javax.swing.JPanel getjPEndereco1() {
        return getjPEnderecoBoleto();
    }

    /**
     * @param jPEndereco1 the jPEndereco1 to set
     */
    public void setjPEndereco1(javax.swing.JPanel jPEndereco1) {
        this.setjPEnderecoBoleto(jPEndereco1);
    }

    /**
     * @return the jPEnderecoBoletoInternoSimNao
     */
    public javax.swing.JPanel getjPEnderecoBoletoInternoSimNao() {
        return jPEnderecoBoletoInternoSimNao;
    }

    /**
     * @param jPEnderecoBoletoInternoSimNao the jPEnderecoBoletoInternoSimNao to
     * set
     */
    public void setjPEnderecoBoletoInternoSimNao(javax.swing.JPanel jPEnderecoBoletoInternoSimNao) {
        this.jPEnderecoBoletoInternoSimNao = jPEnderecoBoletoInternoSimNao;
    }

    /**
     * @return the jRBEnderecoBoletoNao
     */
    public javax.swing.JRadioButton getjRBEnderecoBoletoNao() {
        return jRBEnderecoBoletoNao;
    }

    /**
     * @param jRBEnderecoBoletoNao the jRBEnderecoBoletoNao to set
     */
    public void setjRBEnderecoBoletoNao(javax.swing.JRadioButton jRBEnderecoBoletoNao) {
        this.jRBEnderecoBoletoNao = jRBEnderecoBoletoNao;
    }

    /**
     * @return the jRBEnderecoBoletoSim
     */
    public javax.swing.JRadioButton getjRBEnderecoBoletoSim() {
        return jRBEnderecoBoletoSim;
    }

    /**
     * @param jRBEnderecoBoletoSim the jRBEnderecoBoletoSim to set
     */
    public void setjRBEnderecoBoletoSim(javax.swing.JRadioButton jRBEnderecoBoletoSim) {
        this.jRBEnderecoBoletoSim = jRBEnderecoBoletoSim;
    }

    /**
     * @return the jRBFeminino
     */
    public javax.swing.JRadioButton getjRBFeminino() {
        return getjRBFemenino();
    }

    /**
     * @param jRBFeminino the jRBFeminino to set
     */
    public void setjRBFeminino(javax.swing.JRadioButton jRBFeminino) {
        this.setjRBFemenino(jRBFeminino);
    }

    /**
     * @return the jRBMasculino
     */
    public javax.swing.JRadioButton getjRBMasculino() {
        return jRBMasculino;
    }

    /**
     * @param jRBMasculino the jRBMasculino to set
     */
    public void setjRBMasculino(javax.swing.JRadioButton jRBMasculino) {
        this.jRBMasculino = jRBMasculino;
    }

    /**
     * @return the jTAMotivoSaida
     */
    public javax.swing.JTextArea getjTAMotivoSaida() {
        return jTAMotivoSaida;
    }

    /**
     * @param jTAMotivoSaida the jTAMotivoSaida to set
     */
    public void setjTAMotivoSaida(javax.swing.JTextArea jTAMotivoSaida) {
        this.jTAMotivoSaida = jTAMotivoSaida;
    }

    /**
     * @return the jFTAjudaFinanceira
     */
    public javax.swing.JFormattedTextField getjFTAjudaFinanceira() {
        return jFTAjudaFinanceira;
    }

    /**
     * @param jFTAjudaFinanceira the jFTAjudaFinanceira to set
     */
    public void setjFTAjudaFinanceira(javax.swing.JFormattedTextField jFTAjudaFinanceira) {
        this.jFTAjudaFinanceira = jFTAjudaFinanceira;
    }

    /**
     * @return the jPEnderecoBoleto
     */
    public javax.swing.JPanel getjPEnderecoBoleto() {
        return jPEnderecoBoleto;
    }

    /**
     * @param jPEnderecoBoleto the jPEnderecoBoleto to set
     */
    public void setjPEnderecoBoleto(javax.swing.JPanel jPEnderecoBoleto) {
        this.jPEnderecoBoleto = jPEnderecoBoleto;
    }

    /**
     * @return the jCBUFBoleto
     */
    public javax.swing.JComboBox getjCBUFBoleto() {
        return jCBUFBoleto;
    }

    /**
     * @param jCBUFBoleto the jCBUFBoleto to set
     */
    public void setjCBUFBoleto(javax.swing.JComboBox jCBUFBoleto) {
        this.jCBUFBoleto = jCBUFBoleto;
    }

    /**
     * @return the jFTBairroBoleto
     */
    public javax.swing.JFormattedTextField getjFTBairroBoleto() {
        return jFTBairroBoleto;
    }

    /**
     * @param jFTBairroBoleto the jFTBairroBoleto to set
     */
    public void setjFTBairroBoleto(javax.swing.JFormattedTextField jFTBairroBoleto) {
        this.jFTBairroBoleto = jFTBairroBoleto;
    }

    /**
     * @return the jFTCidadeBoleto
     */
    public javax.swing.JFormattedTextField getjFTCidadeBoleto() {
        return jFTCidadeBoleto;
    }

    /**
     * @param jFTCidadeBoleto the jFTCidadeBoleto to set
     */
    public void setjFTCidadeBoleto(javax.swing.JFormattedTextField jFTCidadeBoleto) {
        this.jFTCidadeBoleto = jFTCidadeBoleto;
    }

    /**
     * @return the jFTComplementoBoleto
     */
    public javax.swing.JFormattedTextField getjFTComplementoBoleto() {
        return jFTComplementoBoleto;
    }

    /**
     * @param jFTComplementoBoleto the jFTComplementoBoleto to set
     */
    public void setjFTComplementoBoleto(javax.swing.JFormattedTextField jFTComplementoBoleto) {
        this.jFTComplementoBoleto = jFTComplementoBoleto;
    }

    /**
     * @return the jFTNumeroBoleto
     */
    public javax.swing.JFormattedTextField getjFTNumeroBoleto() {
        return jFTNumeroBoleto;
    }

    /**
     * @param jFTNumeroBoleto the jFTNumeroBoleto to set
     */
    public void setjFTNumeroBoleto(javax.swing.JFormattedTextField jFTNumeroBoleto) {
        this.jFTNumeroBoleto = jFTNumeroBoleto;
    }

    /**
     * @return the jFTRuaBoleto
     */
    public javax.swing.JFormattedTextField getjFTRuaBoleto() {
        return jFTRuaBoleto;
    }

    /**
     * @param jFTRuaBoleto the jFTRuaBoleto to set
     */
    public void setjFTRuaBoleto(javax.swing.JFormattedTextField jFTRuaBoleto) {
        this.jFTRuaBoleto = jFTRuaBoleto;
    }

    /**
     * @return the jFTTelefoneBoleto
     */
    public javax.swing.JFormattedTextField getjFTTelefoneBoleto() {
        return jFTTelefoneBoleto;
    }

    /**
     * @param jFTTelefoneBoleto the jFTTelefoneBoleto to set
     */
    public void setjFTTelefoneBoleto(javax.swing.JFormattedTextField jFTTelefoneBoleto) {
        this.jFTTelefoneBoleto = jFTTelefoneBoleto;
    }

    /**
     * @return the jFTCEPBoleto
     */
    public javax.swing.JFormattedTextField getjFTCEPBoleto() {
        return jFTCEPBoleto;
    }

    /**
     * @param jFTCEPBoleto the jFTCEPBoleto to set
     */
    public void setjFTCEPBoleto(javax.swing.JFormattedTextField jFTCEPBoleto) {
        this.jFTCEPBoleto = jFTCEPBoleto;
    }

    /**
     * @return the jTFComQuemMoraAtualmente
     */
    public javax.swing.JTextField getjTFComQuemMoraAtualmente() {
        return jTFComQuemMoraAtualmente;
    }

    /**
     * @param jTFComQuemMoraAtualmente the jTFComQuemMoraAtualmente to set
     */
    public void setjTFComQuemMoraAtualmente(javax.swing.JTextField jTFComQuemMoraAtualmente) {
        this.jTFComQuemMoraAtualmente = jTFComQuemMoraAtualmente;
    }

    /**
     * @return the jPRenda
     */
    public javax.swing.JPanel getjPRenda() {
        return jPRenda;
    }

    /**
     * @param jPRenda the jPRenda to set
     */
    public void setjPRenda(javax.swing.JPanel jPRenda) {
        this.jPRenda = jPRenda;
    }

    /**
     * @return the ENDERECOSIM
     */
    public String getENDERECOSIM() {
        return ENDERECOSIM;
    }

    /**
     * @return the ENDERECONAO
     */
    public String getENDERECONAO() {
        return ENDERECONAO;
    }

    /**
     * @return the FamiliarExcluido
     */
    public ArrayList<Integer> getFamiliarExcluido() {
        return FamiliarExcluido;
    }

    /**
     * @param FamiliarExcluido the FamiliarExcluido to set
     */
    public void setFamiliarExcluido(ArrayList<Integer> FamiliarExcluido) {
        this.FamiliarExcluido = FamiliarExcluido;
    }

    /**
     * @return the jTAGrupoApoio
     */
    public javax.swing.JTextArea getjTAGrupoApoio() {
        return jTAGrupoApoio;
    }

    /**
     * @param jTAGrupoApoio the jTAGrupoApoio to set
     */
    public void setjTAGrupoApoio(javax.swing.JTextArea jTAGrupoApoio) {
        this.jTAGrupoApoio = jTAGrupoApoio;
    }

    /**
     * @return the jTASexualidade
     */
    public javax.swing.JTextArea getjTASexualidade() {
        return jTASexualidade;
    }

    /**
     * @param jTASexualidade the jTASexualidade to set
     */
    public void setjTASexualidade(javax.swing.JTextArea jTASexualidade) {
        this.jTASexualidade = jTASexualidade;
    }

    /**
     * @return the jTAProcessosnaJustica
     */
    public javax.swing.JTextArea getjTAProcessosnaJustica() {
        return jTAProcessosnaJustica;
    }

    /**
     * @param jTAProcessosnaJustica the jTAProcessosnaJustica to set
     */
    public void setjTAProcessosnaJustica(javax.swing.JTextArea jTAProcessosnaJustica) {
        this.jTAProcessosnaJustica = jTAProcessosnaJustica;
    }

    /**
     *
     * @return the jMenuTesteConexao
     */
    public javax.swing.JMenuItem getjMenuTesteConexao() {
        return jMenuTesteConexao;
    }

    /**
     * @param jMenuTesteConexao the jMenuTesteConexao to set
     */
    public void setjMenuTesteConexao(javax.swing.JMenuItem jMenuTesteConexao) {
        this.jMenuTesteConexao = jMenuTesteConexao;
    }

    /**
     * @return the conexaobanco
     */
    public ConexaoBanco getConexaobanco() {
        return conexaobanco;
    }

    /**
     * @param conexaobanco the conexaobanco to set
     */
    public void setConexaobanco(ConexaoBanco conexaobanco) {
        this.conexaobanco = conexaobanco;
    }

    /**
     * @return the InciaComp
     */
    public ArrayList<Component> getInciaComp() {
        return InciaComp;
    }

    /**
     * @param InciaComp the InciaComp to set
     */
    public void setInciaComp(ArrayList<Component> InciaComp) {
        this.InciaComp = InciaComp;
    }

    /**
     * @return the ComponentesTeste
     */
    public ArrayList<Component> getComponentesTeste() {
        return ComponentesTeste;
    }

    /**
     * @param ComponentesTeste the ComponentesTeste to set
     */
    public void setComponentesTeste(ArrayList<Component> ComponentesTeste) {
        this.ComponentesTeste = ComponentesTeste;
    }

    /**
     * @return the jDCEntrevistaDois
     */
    public com.toedter.calendar.JDateChooser getjDCEntrevistaDois() {
        return jDCEntrevistaDois;
    }

    /**
     * @param jDCEntrevistaDois the jDCEntrevistaDois to set
     */
    public void setjDCEntrevistaDois(com.toedter.calendar.JDateChooser jDCEntrevistaDois) {
        this.jDCEntrevistaDois = jDCEntrevistaDois;
    }

    /**
     * @return the jDCEntrevistaInternamento
     */
    public com.toedter.calendar.JDateChooser getjDCEntrevistaInternamento() {
        return jDCEntrevistaInternamento;
    }

    /**
     * @param jDCEntrevistaInternamento the jDCEntrevistaInternamento to set
     */
    public void setjDCEntrevistaInternamento(com.toedter.calendar.JDateChooser jDCEntrevistaInternamento) {
        this.jDCEntrevistaInternamento = jDCEntrevistaInternamento;
    }

    /**
     * @return the jDCEntrevistaSaida
     */
    public com.toedter.calendar.JDateChooser getjDCEntrevistaSaida() {
        return jDCEntrevistaSaida;
    }

    /**
     * @param jDCEntrevistaSaida the jDCEntrevistaSaida to set
     */
    public void setjDCEntrevistaSaida(com.toedter.calendar.JDateChooser jDCEntrevistaSaida) {
        this.jDCEntrevistaSaida = jDCEntrevistaSaida;
    }

    /**
     * @return the jDCEntrevistaUm
     */
    public com.toedter.calendar.JDateChooser getjDCEntrevistaUm() {
        return jDCEntrevistaUm;
    }

    /**
     * @param jDCEntrevistaUm the jDCEntrevistaUm to set
     */
    public void setjDCEntrevistaUm(com.toedter.calendar.JDateChooser jDCEntrevistaUm) {
        this.jDCEntrevistaUm = jDCEntrevistaUm;
    }

    /**
     * @return the jFTEntrevistadoraDois
     */
    public javax.swing.JFormattedTextField getjFTEntrevistadoraDois() {
        return jFTEntrevistadoraDois;
    }

    /**
     * @param jFTEntrevistadoraDois the jFTEntrevistadoraDois to set
     */
    public void setjFTEntrevistadoraDois(javax.swing.JFormattedTextField jFTEntrevistadoraDois) {
        this.jFTEntrevistadoraDois = jFTEntrevistadoraDois;
    }

    /**
     * @return the jFTEntrevistadoraInternamento
     */
    public javax.swing.JFormattedTextField getjFTEntrevistadoraInternamento() {
        return jFTEntrevistadoraInternamento;
    }

    /**
     * @param jFTEntrevistadoraInternamento the jFTEntrevistadoraInternamento to
     * set
     */
    public void setjFTEntrevistadoraInternamento(javax.swing.JFormattedTextField jFTEntrevistadoraInternamento) {
        this.jFTEntrevistadoraInternamento = jFTEntrevistadoraInternamento;
    }

    /**
     * @return the jFTEntrevistadoraSaida
     */
    public javax.swing.JFormattedTextField getjFTEntrevistadoraSaida() {
        return jFTEntrevistadoraSaida;
    }

    /**
     * @param jFTEntrevistadoraSaida the jFTEntrevistadoraSaida to set
     */
    public void setjFTEntrevistadoraSaida(javax.swing.JFormattedTextField jFTEntrevistadoraSaida) {
        this.jFTEntrevistadoraSaida = jFTEntrevistadoraSaida;
    }

    /**
     * @return the jFTEntrevistadoraUm
     */
    public javax.swing.JFormattedTextField getjFTEntrevistadoraUm() {
        return jFTEntrevistadoraUm;
    }

    /**
     * @param jFTEntrevistadoraUm the jFTEntrevistadoraUm to set
     */
    public void setjFTEntrevistadoraUm(javax.swing.JFormattedTextField jFTEntrevistadoraUm) {
        this.jFTEntrevistadoraUm = jFTEntrevistadoraUm;
    }

    /**
     * @return the jLAlcool
     */
    public javax.swing.JLabel getjLAlcool() {
        return jLAlcool;
    }

    /**
     * @param jLAlcool the jLAlcool to set
     */
    public void setjLAlcool(javax.swing.JLabel jLAlcool) {
        this.jLAlcool = jLAlcool;
    }

    /**
     * @return the jLAlimentacao
     */
    public javax.swing.JLabel getjLAlimentacao() {
        return jLAlimentacao;
    }

    /**
     * @param jLAlimentacao the jLAlimentacao to set
     */
    public void setjLAlimentacao(javax.swing.JLabel jLAlimentacao) {
        this.jLAlimentacao = jLAlimentacao;
    }

    /**
     * @return the jLAlucinacaoDelirios
     */
    public javax.swing.JLabel getjLAlucinacaoDelirios() {
        return jLAlucinacaoDelirios;
    }

    /**
     * @param jLAlucinacaoDelirios the jLAlucinacaoDelirios to set
     */
    public void setjLAlucinacaoDelirios(javax.swing.JLabel jLAlucinacaoDelirios) {
        this.jLAlucinacaoDelirios = jLAlucinacaoDelirios;
    }

    /**
     * @return the jLAposentadoria
     */
    public javax.swing.JLabel getjLAposentadoria() {
        return jLAposentadoria;
    }

    /**
     * @param jLAposentadoria the jLAposentadoria to set
     */
    public void setjLAposentadoria(javax.swing.JLabel jLAposentadoria) {
        this.jLAposentadoria = jLAposentadoria;
    }

    /**
     * @return the jLAutoextermino
     */
    public javax.swing.JLabel getjLAutoextermino() {
        return jLAutoextermino;
    }

    /**
     * @param jLAutoextermino the jLAutoextermino to set
     */
    public void setjLAutoextermino(javax.swing.JLabel jLAutoextermino) {
        this.jLAutoextermino = jLAutoextermino;
    }

    /**
     * @return the jLBairro
     */
    public javax.swing.JLabel getjLBairro() {
        return jLBairro;
    }

    /**
     * @param jLBairro the jLBairro to set
     */
    public void setjLBairro(javax.swing.JLabel jLBairro) {
        this.jLBairro = jLBairro;
    }

    /**
     * @return the jLBairroBoleto
     */
    public javax.swing.JLabel getjLBairroBoleto() {
        return jLBairroBoleto;
    }

    /**
     * @param jLBairroBoleto the jLBairroBoleto to set
     */
    public void setjLBairroBoleto(javax.swing.JLabel jLBairroBoleto) {
        this.jLBairroBoleto = jLBairroBoleto;
    }

    /**
     * @return the jLCEP
     */
    public javax.swing.JLabel getjLCEP() {
        return jLCEP;
    }

    /**
     * @param jLCEP the jLCEP to set
     */
    public void setjLCEP(javax.swing.JLabel jLCEP) {
        this.jLCEP = jLCEP;
    }

    /**
     * @return the jLCEPBoleto
     */
    public javax.swing.JLabel getjLCEPBoleto() {
        return jLCEPBoleto;
    }

    /**
     * @param jLCEPBoleto the jLCEPBoleto to set
     */
    public void setjLCEPBoleto(javax.swing.JLabel jLCEPBoleto) {
        this.jLCEPBoleto = jLCEPBoleto;
    }

    /**
     * @return the jLCPF
     */
    public javax.swing.JLabel getjLCPF() {
        return jLCPF;
    }

    /**
     * @param jLCPF the jLCPF to set
     */
    public void setjLCPF(javax.swing.JLabel jLCPF) {
        this.jLCPF = jLCPF;
    }

    /**
     * @return the jLCarteiraAssinada
     */
    public javax.swing.JLabel getjLCarteiraAssinada() {
        return jLCarteiraAssinada;
    }

    /**
     * @param jLCarteiraAssinada the jLCarteiraAssinada to set
     */
    public void setjLCarteiraAssinada(javax.swing.JLabel jLCarteiraAssinada) {
        this.jLCarteiraAssinada = jLCarteiraAssinada;
    }

    /**
     * @return the jLCarteiradeTrabalho
     */
    public javax.swing.JLabel getjLCarteiradeTrabalho() {
        return jLCarteiradeTrabalho;
    }

    /**
     * @param jLCarteiradeTrabalho the jLCarteiradeTrabalho to set
     */
    public void setjLCarteiradeTrabalho(javax.swing.JLabel jLCarteiradeTrabalho) {
        this.jLCarteiradeTrabalho = jLCarteiradeTrabalho;
    }

    /**
     * @return the jLCasodeDrogas
     */
    public javax.swing.JLabel getjLCasodeDrogas() {
        return jLCasodeDrogas;
    }

    /**
     * @param jLCasodeDrogas the jLCasodeDrogas to set
     */
    public void setjLCasodeDrogas(javax.swing.JLabel jLCasodeDrogas) {
        this.jLCasodeDrogas = jLCasodeDrogas;
    }

    /**
     * @return the jLCidade
     */
    public javax.swing.JLabel getjLCidade() {
        return jLCidade;
    }

    /**
     * @param jLCidade the jLCidade to set
     */
    public void setjLCidade(javax.swing.JLabel jLCidade) {
        this.jLCidade = jLCidade;
    }

    /**
     * @return the jLCidadeBoleto
     */
    public javax.swing.JLabel getjLCidadeBoleto() {
        return jLCidadeBoleto;
    }

    /**
     * @param jLCidadeBoleto the jLCidadeBoleto to set
     */
    public void setjLCidadeBoleto(javax.swing.JLabel jLCidadeBoleto) {
        this.jLCidadeBoleto = jLCidadeBoleto;
    }

    /**
     * @return the jLCigarroTabaco
     */
    public javax.swing.JLabel getjLCigarroTabaco() {
        return jLCigarroTabaco;
    }

    /**
     * @param jLCigarroTabaco the jLCigarroTabaco to set
     */
    public void setjLCigarroTabaco(javax.swing.JLabel jLCigarroTabaco) {
        this.jLCigarroTabaco = jLCigarroTabaco;
    }

    /**
     * @return the jLClinica
     */
    public javax.swing.JLabel getjLClinica() {
        return jLClinica;
    }

    /**
     * @param jLClinica the jLClinica to set
     */
    public void setjLClinica(javax.swing.JLabel jLClinica) {
        this.jLClinica = jLClinica;
    }

    /**
     * @return the jLCocainaA
     */
    public javax.swing.JLabel getjLCocainaA() {
        return jLCocainaA;
    }

    /**
     * @param jLCocainaA the jLCocainaA to set
     */
    public void setjLCocainaA(javax.swing.JLabel jLCocainaA) {
        this.jLCocainaA = jLCocainaA;
    }

    /**
     * @return the jLCocainaI
     */
    public javax.swing.JLabel getjLCocainaI() {
        return jLCocainaI;
    }

    /**
     * @param jLCocainaI the jLCocainaI to set
     */
    public void setjLCocainaI(javax.swing.JLabel jLCocainaI) {
        this.jLCocainaI = jLCocainaI;
    }

    /**
     * @return the jLComplemento
     */
    public javax.swing.JLabel getjLComplemento() {
        return jLComplemento;
    }

    /**
     * @param jLComplemento the jLComplemento to set
     */
    public void setjLComplemento(javax.swing.JLabel jLComplemento) {
        this.jLComplemento = jLComplemento;
    }

    /**
     * @return the jLComplementoBoleto
     */
    public javax.swing.JLabel getjLComplementoBoleto() {
        return jLComplementoBoleto;
    }

    /**
     * @param jLComplementoBoleto the jLComplementoBoleto to set
     */
    public void setjLComplementoBoleto(javax.swing.JLabel jLComplementoBoleto) {
        this.jLComplementoBoleto = jLComplementoBoleto;
    }

    /**
     * @return the jLComprimido
     */
    public javax.swing.JLabel getjLComprimido() {
        return jLComprimido;
    }

    /**
     * @param jLComprimido the jLComprimido to set
     */
    public void setjLComprimido(javax.swing.JLabel jLComprimido) {
        this.jLComprimido = jLComprimido;
    }

    /**
     * @return the jLConhecimentoColonia
     */
    public javax.swing.JLabel getjLConhecimentoColonia() {
        return jLConhecimentoColonia;
    }

    /**
     * @param jLConhecimentoColonia the jLConhecimentoColonia to set
     */
    public void setjLConhecimentoColonia(javax.swing.JLabel jLConhecimentoColonia) {
        this.jLConhecimentoColonia = jLConhecimentoColonia;
    }

    /**
     * @return the jLCrack
     */
    public javax.swing.JLabel getjLCrack() {
        return jLCrack;
    }

    /**
     * @param jLCrack the jLCrack to set
     */
    public void setjLCrack(javax.swing.JLabel jLCrack) {
        this.jLCrack = jLCrack;
    }

    /**
     * @return the jLData
     */
    public javax.swing.JLabel getjLData() {
        return jLDataPriEntre;
    }

    /**
     * @param jLData the jLData to set
     */
    public void setjLData(javax.swing.JLabel jLData) {
        this.jLDataPriEntre = jLData;
    }

    /**
     * @return the jLData1
     */
    public javax.swing.JLabel getjLData1() {
        return jLDataSegEntre;
    }

    /**
     * @param jLData1 the jLData1 to set
     */
    public void setjLData1(javax.swing.JLabel jLData1) {
        this.jLDataSegEntre = jLData1;
    }

    /**
     * @return the jLDataInternamento
     */
    public javax.swing.JLabel getjLDataInternamento() {
        return jLDataInternamento;
    }

    /**
     * @param jLDataInternamento the jLDataInternamento to set
     */
    public void setjLDataInternamento(javax.swing.JLabel jLDataInternamento) {
        this.jLDataInternamento = jLDataInternamento;
    }

    /**
     * @return the jLDataSaida
     */
    public javax.swing.JLabel getjLDataSaida() {
        return jLDataSaida;
    }

    /**
     * @param jLDataSaida the jLDataSaida to set
     */
    public void setjLDataSaida(javax.swing.JLabel jLDataSaida) {
        this.jLDataSaida = jLDataSaida;
    }

    /**
     * @return the jLDatadeEntrada
     */
    public javax.swing.JLabel getjLDatadeEntrada() {
        return jLDatadeEntrada;
    }

    /**
     * @param jLDatadeEntrada the jLDatadeEntrada to set
     */
    public void setjLDatadeEntrada(javax.swing.JLabel jLDatadeEntrada) {
        this.jLDatadeEntrada = jLDatadeEntrada;
    }

    /**
     * @return the jLDatadeNascimento
     */
    public javax.swing.JLabel getjLDatadeNascimento() {
        return jLDatadeNascimento;
    }

    /**
     * @param jLDatadeNascimento the jLDatadeNascimento to set
     */
    public void setjLDatadeNascimento(javax.swing.JLabel jLDatadeNascimento) {
        this.jLDatadeNascimento = jLDatadeNascimento;
    }

    /**
     * @return the jLDatadeSaida
     */
    public javax.swing.JLabel getjLDatadeSaida() {
        return jLDatadeSaida;
    }

    /**
     * @param jLDatadeSaida the jLDatadeSaida to set
     */
    public void setjLDatadeSaida(javax.swing.JLabel jLDatadeSaida) {
        this.jLDatadeSaida = jLDatadeSaida;
    }

    /**
     * @return the jLDemaisConvulsoes
     */
    public javax.swing.JLabel getjLDemaisConvulsoes() {
        return jLDemaisConvulsoes;
    }

    /**
     * @param jLDemaisConvulsoes the jLDemaisConvulsoes to set
     */
    public void setjLDemaisConvulsoes(javax.swing.JLabel jLDemaisConvulsoes) {
        this.jLDemaisConvulsoes = jLDemaisConvulsoes;
    }

    /**
     * @return the jLEntrevistadora1
     */
    public javax.swing.JLabel getjLEntrevistadora1() {
        return jLEntrevistadora1;
    }

    /**
     * @param jLEntrevistadora1 the jLEntrevistadora1 to set
     */
    public void setjLEntrevistadora1(javax.swing.JLabel jLEntrevistadora1) {
        this.jLEntrevistadora1 = jLEntrevistadora1;
    }

    /**
     * @return the jLEntrevistadora2
     */
    public javax.swing.JLabel getjLEntrevistadora2() {
        return jLEntrevistadora2;
    }

    /**
     * @param jLEntrevistadora2 the jLEntrevistadora2 to set
     */
    public void setjLEntrevistadora2(javax.swing.JLabel jLEntrevistadora2) {
        this.jLEntrevistadora2 = jLEntrevistadora2;
    }

    /**
     * @return the jLEntrevistadoraInternamento
     */
    public javax.swing.JLabel getjLEntrevistadoraInternamento() {
        return jLEntrevistadoraInternamento;
    }

    /**
     * @param jLEntrevistadoraInternamento the jLEntrevistadoraInternamento to
     * set
     */
    public void setjLEntrevistadoraInternamento(javax.swing.JLabel jLEntrevistadoraInternamento) {
        this.jLEntrevistadoraInternamento = jLEntrevistadoraInternamento;
    }

    /**
     * @return the jLEscolaridade
     */
    public javax.swing.JLabel getjLEscolaridade() {
        return jLEscolaridade;
    }

    /**
     * @param jLEscolaridade the jLEscolaridade to set
     */
    public void setjLEscolaridade(javax.swing.JLabel jLEscolaridade) {
        this.jLEscolaridade = jLEscolaridade;
    }

    /**
     * @return the jLEstadoCivil
     */
    public javax.swing.JLabel getjLEstadoCivil() {
        return jLEstadoCivil;
    }

    /**
     * @param jLEstadoCivil the jLEstadoCivil to set
     */
    public void setjLEstadoCivil(javax.swing.JLabel jLEstadoCivil) {
        this.jLEstadoCivil = jLEstadoCivil;
    }

    /**
     * @return the jLEstadoCivilFamilia
     */
    public javax.swing.JLabel getjLEstadoCivilFamilia() {
        return jLEstadoCivilFamilia;
    }

    /**
     * @param jLEstadoCivilFamilia the jLEstadoCivilFamilia to set
     */
    public void setjLEstadoCivilFamilia(javax.swing.JLabel jLEstadoCivilFamilia) {
        this.jLEstadoCivilFamilia = jLEstadoCivilFamilia;
    }

    /**
     * @return the jLExamesEspecificos
     */
    public javax.swing.JLabel getjLExamesEspecificos() {
        return jLExamesEspecificos;
    }

    /**
     * @param jLExamesEspecificos the jLExamesEspecificos to set
     */
    public void setjLExamesEspecificos(javax.swing.JLabel jLExamesEspecificos) {
        this.jLExamesEspecificos = jLExamesEspecificos;
    }

    /**
     * @return the jLExpectativaTratamento
     */
    public javax.swing.JLabel getjLExpectativaTratamento() {
        return jLExpectativaTratamento;
    }

    /**
     * @param jLExpectativaTratamento the jLExpectativaTratamento to set
     */
    public void setjLExpectativaTratamento(javax.swing.JLabel jLExpectativaTratamento) {
        this.jLExpectativaTratamento = jLExpectativaTratamento;
    }

    /**
     * @return the jLFalecido
     */
    public javax.swing.JLabel getjLFalecido() {
        return jLFalecido;
    }

    /**
     * @param jLFalecido the jLFalecido to set
     */
    public void setjLFalecido(javax.swing.JLabel jLFalecido) {
        this.jLFalecido = jLFalecido;
    }

    /**
     * @return the jLFamiliar
     */
    public javax.swing.JLabel getjLFamiliar() {
        return jLFamiliar;
    }

    /**
     * @param jLFamiliar the jLFamiliar to set
     */
    public void setjLFamiliar(javax.swing.JLabel jLFamiliar) {
        this.jLFamiliar = jLFamiliar;
    }

    /**
     * @return the jLFamiliarSeparados
     */
    public javax.swing.JLabel getjLFamiliarSeparados() {
        return jLFamiliarSeparados;
    }

    /**
     * @param jLFamiliarSeparados the jLFamiliarSeparados to set
     */
    public void setjLFamiliarSeparados(javax.swing.JLabel jLFamiliarSeparados) {
        this.jLFamiliarSeparados = jLFamiliarSeparados;
    }

    /**
     * @return the jLGrupoApoio
     */
    public javax.swing.JLabel getjLGrupoApoio() {
        return jLGrupoApoio;
    }

    /**
     * @param jLGrupoApoio the jLGrupoApoio to set
     */
    public void setjLGrupoApoio(javax.swing.JLabel jLGrupoApoio) {
        this.jLGrupoApoio = jLGrupoApoio;
    }

    /**
     * @return the jLIdade
     */
    public javax.swing.JLabel getjLIdade() {
        return jLIdade;
    }

    /**
     * @param jLIdade the jLIdade to set
     */
    public void setjLIdade(javax.swing.JLabel jLIdade) {
        this.jLIdade = jLIdade;
    }

    /**
     * @return the jLIdadeFamilia
     */
    public javax.swing.JLabel getjLIdadeFamilia() {
        return jLIdadeFamilia;
    }

    /**
     * @param jLIdadeFamilia the jLIdadeFamilia to set
     */
    public void setjLIdadeFamilia(javax.swing.JLabel jLIdadeFamilia) {
        this.jLIdadeFamilia = jLIdadeFamilia;
    }

    /**
     * @return the jLInalantes
     */
    public javax.swing.JLabel getjLInalantes() {
        return jLInalantes;
    }

    /**
     * @param jLInalantes the jLInalantes to set
     */
    public void setjLInalantes(javax.swing.JLabel jLInalantes) {
        this.jLInalantes = jLInalantes;
    }

    /**
     * @return the jLLogradouro
     */
    public javax.swing.JLabel getjLLogradouro() {
        return jLLogradouro;
    }

    /**
     * @param jLLogradouro the jLLogradouro to set
     */
    public void setjLLogradouro(javax.swing.JLabel jLLogradouro) {
        this.jLLogradouro = jLLogradouro;
    }

    /**
     * @return the jLLogradouroBoleto
     */
    public javax.swing.JLabel getjLLogradouroBoleto() {
        return jLLogradouroBoleto;
    }

    /**
     * @param jLLogradouroBoleto the jLLogradouroBoleto to set
     */
    public void setjLLogradouroBoleto(javax.swing.JLabel jLLogradouroBoleto) {
        this.jLLogradouroBoleto = jLLogradouroBoleto;
    }

    /**
     * @return the jLMaconha
     */
    public javax.swing.JLabel getjLMaconha() {
        return jLMaconha;
    }

    /**
     * @param jLMaconha the jLMaconha to set
     */
    public void setjLMaconha(javax.swing.JLabel jLMaconha) {
        this.jLMaconha = jLMaconha;
    }

    /**
     * @return the jLMedicacao
     */
    public javax.swing.JLabel getjLMedicacao() {
        return jLMedicacao;
    }

    /**
     * @param jLMedicacao the jLMedicacao to set
     */
    public void setjLMedicacao(javax.swing.JLabel jLMedicacao) {
        this.jLMedicacao = jLMedicacao;
    }

    /**
     * @return the jLMotivoSaida
     */
    public javax.swing.JLabel getjLMotivoSaida() {
        return jLMotivoSaida;
    }

    /**
     * @param jLMotivoSaida the jLMotivoSaida to set
     */
    public void setjLMotivoSaida(javax.swing.JLabel jLMotivoSaida) {
        this.jLMotivoSaida = jLMotivoSaida;
    }

    /**
     * @return the jLNaturalidade
     */
    public javax.swing.JLabel getjLNaturalidade() {
        return jLNaturalidade;
    }

    /**
     * @param jLNaturalidade the jLNaturalidade to set
     */
    public void setjLNaturalidade(javax.swing.JLabel jLNaturalidade) {
        this.jLNaturalidade = jLNaturalidade;
    }

    /**
     * @param jLNome the jLNome to set
     */
    public void setjLNome(javax.swing.JLabel jLNome) {
        this.jLNome = jLNome;
    }

    /**
     * @return the jLNomeFamilia
     */
    public javax.swing.JLabel getjLNomeFamilia() {
        return jLNomeFamilia;
    }

    /**
     * @param jLNomeFamilia the jLNomeFamilia to set
     */
    public void setjLNomeFamilia(javax.swing.JLabel jLNomeFamilia) {
        this.jLNomeFamilia = jLNomeFamilia;
    }

    /**
     * @return the jLNomeResponsavel
     */
    public javax.swing.JLabel getjLNomeResponsavel() {
        return jLNomeResponsavel;
    }

    /**
     * @param jLNomeResponsavel the jLNomeResponsavel to set
     */
    public void setjLNomeResponsavel(javax.swing.JLabel jLNomeResponsavel) {
        this.jLNomeResponsavel = jLNomeResponsavel;
    }

    /**
     * @return the jLNumero
     */
    public javax.swing.JLabel getjLNumero() {
        return jLNumero;
    }

    /**
     * @param jLNumero the jLNumero to set
     */
    public void setjLNumero(javax.swing.JLabel jLNumero) {
        this.jLNumero = jLNumero;
    }

    /**
     * @return the jLNumero1
     */
    public javax.swing.JLabel getjLNumero1() {
        return jLNumero1;
    }

    /**
     * @param jLNumero1 the jLNumero1 to set
     */
    public void setjLNumero1(javax.swing.JLabel jLNumero1) {
        this.jLNumero1 = jLNumero1;
    }

    /**
     * @return the jLOutrasDrogas
     */
    public javax.swing.JLabel getjLOutrasDrogas() {
        return jLOutrasDrogas;
    }

    /**
     * @param jLOutrasDrogas the jLOutrasDrogas to set
     */
    public void setjLOutrasDrogas(javax.swing.JLabel jLOutrasDrogas) {
        this.jLOutrasDrogas = jLOutrasDrogas;
    }

    /**
     * @return the jLPIS
     */
    public javax.swing.JLabel getjLPIS() {
        return jLPIS;
    }

    /**
     * @param jLPIS the jLPIS to set
     */
    public void setjLPIS(javax.swing.JLabel jLPIS) {
        this.jLPIS = jLPIS;
    }

    /**
     * @return the jLParentesco
     */
    public javax.swing.JLabel getjLParentesco() {
        return jLParentesco;
    }

    /**
     * @param jLParentesco the jLParentesco to set
     */
    public void setjLParentesco(javax.swing.JLabel jLParentesco) {
        this.jLParentesco = jLParentesco;
    }

    /**
     * @return the jLPessoal
     */
    public javax.swing.JLabel getjLPessoal() {
        return jLPessoal;
    }

    /**
     * @param jLPessoal the jLPessoal to set
     */
    public void setjLPessoal(javax.swing.JLabel jLPessoal) {
        this.jLPessoal = jLPessoal;
    }

    /**
     * @return the jLPossuiAlguemAjudarFinanceira
     */
    public javax.swing.JLabel getjLPossuiAlguemAjudarFinanceira() {
        return jLPossuiAlguemAjudarFinanceira;
    }

    /**
     * @param jLPossuiAlguemAjudarFinanceira the jLPossuiAlguemAjudarFinanceira
     * to set
     */
    public void setjLPossuiAlguemAjudarFinanceira(javax.swing.JLabel jLPossuiAlguemAjudarFinanceira) {
        this.jLPossuiAlguemAjudarFinanceira = jLPossuiAlguemAjudarFinanceira;
    }

    /**
     * @return the jLProcessosnaJustica
     */
    public javax.swing.JLabel getjLProcessosnaJustica() {
        return jLProcessosnaJustica;
    }

    /**
     * @param jLProcessosnaJustica the jLProcessosnaJustica to set
     */
    public void setjLProcessosnaJustica(javax.swing.JLabel jLProcessosnaJustica) {
        this.jLProcessosnaJustica = jLProcessosnaJustica;
    }

    /**
     * @return the jLProfissao
     */
    public javax.swing.JLabel getjLProfissao() {
        return jLProfissao;
    }

    /**
     * @param jLProfissao the jLProfissao to set
     */
    public void setjLProfissao(javax.swing.JLabel jLProfissao) {
        this.jLProfissao = jLProfissao;
    }

    /**
     * @return the jLRG
     */
    public javax.swing.JLabel getjLRG() {
        return jLRG;
    }

    /**
     * @param jLRG the jLRG to set
     */
    public void setjLRG(javax.swing.JLabel jLRG) {
        this.jLRG = jLRG;
    }

    /**
     * @return the jLRelacionamento
     */
    public javax.swing.JLabel getjLRelacionamento() {
        return jLRelacionamento;
    }

    /**
     * @param jLRelacionamento the jLRelacionamento to set
     */
    public void setjLRelacionamento(javax.swing.JLabel jLRelacionamento) {
        this.jLRelacionamento = jLRelacionamento;
    }

    /**
     * @return the jLRelacionamentoAfetivo
     */
    public javax.swing.JLabel getjLRelacionamentoAfetivo() {
        return jLRelacionamentoAfetivo;
    }

    /**
     * @param jLRelacionamentoAfetivo the jLRelacionamentoAfetivo to set
     */
    public void setjLRelacionamentoAfetivo(javax.swing.JLabel jLRelacionamentoAfetivo) {
        this.jLRelacionamentoAfetivo = jLRelacionamentoAfetivo;
    }

    /**
     * @return the jLRelacionamentoSocial
     */
    public javax.swing.JLabel getjLRelacionamentoSocial() {
        return jLRelacionamentoSocial;
    }

    /**
     * @param jLRelacionamentoSocial the jLRelacionamentoSocial to set
     */
    public void setjLRelacionamentoSocial(javax.swing.JLabel jLRelacionamentoSocial) {
        this.jLRelacionamentoSocial = jLRelacionamentoSocial;
    }

    /**
     * @return the jLResidenteNumero
     */
    public javax.swing.JLabel getjLResidenteNumero() {
        return jLResidenteNumero;
    }

    /**
     * @param jLResidenteNumero the jLResidenteNumero to set
     */
    public void setjLResidenteNumero(javax.swing.JLabel jLResidenteNumero) {
        this.jLResidenteNumero = jLResidenteNumero;
    }

    /**
     * @return the jLSaida
     */
    public javax.swing.JLabel getjLSaida() {
        return jLSaida;
    }

    /**
     * @param jLSaida the jLSaida to set
     */
    public void setjLSaida(javax.swing.JLabel jLSaida) {
        this.jLSaida = jLSaida;
    }

    /**
     * @return the jLSalarioDesemprego
     */
    public javax.swing.JLabel getjLSalarioDesemprego() {
        return jLSalarioDesemprego;
    }

    /**
     * @param jLSalarioDesemprego the jLSalarioDesemprego to set
     */
    public void setjLSalarioDesemprego(javax.swing.JLabel jLSalarioDesemprego) {
        this.jLSalarioDesemprego = jLSalarioDesemprego;
    }

    /**
     * @return the jLSecao
     */
    public javax.swing.JLabel getjLSecao() {
        return jLSecao;
    }

    /**
     * @param jLSecao the jLSecao to set
     */
    public void setjLSecao(javax.swing.JLabel jLSecao) {
        this.jLSecao = jLSecao;
    }

    /**
     * @return the jLSexo
     */
    public javax.swing.JLabel getjLSexo() {
        return jLSexo;
    }

    /**
     * @param jLSexo the jLSexo to set
     */
    public void setjLSexo(javax.swing.JLabel jLSexo) {
        this.jLSexo = jLSexo;
    }

    /**
     * @return the jLSexualidade
     */
    public javax.swing.JLabel getjLSexualidade() {
        return jLSexualidade;
    }

    /**
     * @param jLSexualidade the jLSexualidade to set
     */
    public void setjLSexualidade(javax.swing.JLabel jLSexualidade) {
        this.jLSexualidade = jLSexualidade;
    }

    /**
     * @return the jLSono
     */
    public javax.swing.JLabel getjLSono() {
        return jLSono;
    }

    /**
     * @param jLSono the jLSono to set
     */
    public void setjLSono(javax.swing.JLabel jLSono) {
        this.jLSono = jLSono;
    }

    /**
     * @return the jLTelefone
     */
    public javax.swing.JLabel getjLTelefone() {
        return jLTelefone;
    }

    /**
     * @param jLTelefone the jLTelefone to set
     */
    public void setjLTelefone(javax.swing.JLabel jLTelefone) {
        this.jLTelefone = jLTelefone;
    }

    /**
     * @return the jLTelefoneBoleto
     */
    public javax.swing.JLabel getjLTelefoneBoleto() {
        return jLTelefoneBoleto;
    }

    /**
     * @param jLTelefoneBoleto the jLTelefoneBoleto to set
     */
    public void setjLTelefoneBoleto(javax.swing.JLabel jLTelefoneBoleto) {
        this.jLTelefoneBoleto = jLTelefoneBoleto;
    }

    /**
     * @return the jLTelefoneResponsavel
     */
    public javax.swing.JLabel getjLTelefoneResponsavel() {
        return jLTelefoneResponsavel;
    }

    /**
     * @param jLTelefoneResponsavel the jLTelefoneResponsavel to set
     */
    public void setjLTelefoneResponsavel(javax.swing.JLabel jLTelefoneResponsavel) {
        this.jLTelefoneResponsavel = jLTelefoneResponsavel;
    }

    /**
     * @return the jLTempodeUso
     */
    public javax.swing.JLabel getjLTempodeUso() {
        return jLTempodeUso;
    }

    /**
     * @param jLTempodeUso the jLTempodeUso to set
     */
    public void setjLTempodeUso(javax.swing.JLabel jLTempodeUso) {
        this.jLTempodeUso = jLTempodeUso;
    }

    /**
     * @return the jLTituloEleitoral
     */
    public javax.swing.JLabel getjLTituloEleitoral() {
        return jLTituloEleitoral;
    }

    /**
     * @param jLTituloEleitoral the jLTituloEleitoral to set
     */
    public void setjLTituloEleitoral(javax.swing.JLabel jLTituloEleitoral) {
        this.jLTituloEleitoral = jLTituloEleitoral;
    }

    /**
     * @return the jLTrabalhoAtual
     */
    public javax.swing.JLabel getjLTrabalhoAtual() {
        return jLTrabalhoAtual;
    }

    /**
     * @param jLTrabalhoAtual the jLTrabalhoAtual to set
     */
    public void setjLTrabalhoAtual(javax.swing.JLabel jLTrabalhoAtual) {
        this.jLTrabalhoAtual = jLTrabalhoAtual;
    }

    /**
     * @return the jLUF
     */
    public javax.swing.JLabel getjLUF() {
        return jLUF;
    }

    /**
     * @param jLUF the jLUF to set
     */
    public void setjLUF(javax.swing.JLabel jLUF) {
        this.jLUF = jLUF;
    }

    /**
     * @return the jLUFBoleto
     */
    public javax.swing.JLabel getjLUFBoleto() {
        return jLUFBoleto;
    }

    /**
     * @param jLUFBoleto the jLUFBoleto to set
     */
    public void setjLUFBoleto(javax.swing.JLabel jLUFBoleto) {
        this.jLUFBoleto = jLUFBoleto;
    }

    /**
     * @return the jLUsadaAtualmente
     */
    public javax.swing.JLabel getjLUsadaAtualmente() {
        return jLUsadaAtualmente;
    }

    /**
     * @param jLUsadaAtualmente the jLUsadaAtualmente to set
     */
    public void setjLUsadaAtualmente(javax.swing.JLabel jLUsadaAtualmente) {
        this.jLUsadaAtualmente = jLUsadaAtualmente;
    }

    /**
     * @return the jLVencimento
     */
    public javax.swing.JLabel getjLVencimento() {
        return jLVencimento;
    }

    /**
     * @param jLVencimento the jLVencimento to set
     */
    public void setjLVencimento(javax.swing.JLabel jLVencimento) {
        this.jLVencimento = jLVencimento;
    }

    /**
     * @return the jLZona
     */
    public javax.swing.JLabel getjLZona() {
        return jLZona;
    }

    /**
     * @param jLZona the jLZona to set
     */
    public void setjLZona(javax.swing.JLabel jLZona) {
        this.jLZona = jLZona;
    }

    /**
     * @return the jLabel1
     */
    public javax.swing.JLabel getjLabel1() {
        return jLabel1;
    }

    /**
     * @param jLabel1 the jLabel1 to set
     */
    public void setjLabel1(javax.swing.JLabel jLabel1) {
        this.jLabel1 = jLabel1;
    }

    /**
     * @return the jLabel2
     */
    public javax.swing.JLabel getjLabel2() {
        return jLabel2;
    }

    /**
     * @param jLabel2 the jLabel2 to set
     */
    public void setjLabel2(javax.swing.JLabel jLabel2) {
        this.jLabel2 = jLabel2;
    }

    /**
     * @return the jLabel3
     */
    public javax.swing.JLabel getjLabel3() {
        return jLabel3;
    }

    /**
     * @param jLabel3 the jLabel3 to set
     */
    public void setjLabel3(javax.swing.JLabel jLabel3) {
        this.jLabel3 = jLabel3;
    }


    /**
     * @return the jMRelatorios
     */
    public javax.swing.JMenu getjMRelatorios() {
        return jMRelatorios;
    }

    /**
     * @param jMRelatorios the jMRelatorios to set
     */
    public void setjMRelatorios(javax.swing.JMenu jMRelatorios) {
        this.jMRelatorios = jMRelatorios;
    }

    /**
     * @return the jMenuItem1
     */
    public javax.swing.JMenuItem getjMenuItem1() {
        return jMenuItem1;
    }

    /**
     * @param jMenuItem1 the jMenuItem1 to set
     */
    public void setjMenuItem1(javax.swing.JMenuItem jMenuItem1) {
        this.jMenuItem1 = jMenuItem1;
    }

    /**
     * @return the jMenuItem2
     */
    public javax.swing.JMenuItem getjMenuItem2() {
        return jMenuItem2;
    }

    /**
     * @param jMenuItem2 the jMenuItem2 to set
     */
    public void setjMenuItem2(javax.swing.JMenuItem jMenuItem2) {
        this.jMenuItem2 = jMenuItem2;
    }

    /**
     * @return the jMenuItem3
     */
    public javax.swing.JMenuItem getjMenuItem3() {
        return jMenuItem3;
    }

    /**
     * @param jMenuItem3 the jMenuItem3 to set
     */
    public void setjMenuItem3(javax.swing.JMenuItem jMenuItem3) {
        this.jMenuItem3 = jMenuItem3;
    }

    /**
     * @return the jPComQuemMora
     */
    public javax.swing.JPanel getjPComQuemMora() {
        return jPComQuemMora;
    }

    /**
     * @param jPComQuemMora the jPComQuemMora to set
     */
    public void setjPComQuemMora(javax.swing.JPanel jPComQuemMora) {
        this.jPComQuemMora = jPComQuemMora;
    }

    /**
     * @return the jPComQuemMoraAtualmente
     */
    public javax.swing.JPanel getjPComQuemMoraAtualmente() {
        return jPComQuemMoraAtualmente;
    }

    /**
     * @param jPComQuemMoraAtualmente the jPComQuemMoraAtualmente to set
     */
    public void setjPComQuemMoraAtualmente(javax.swing.JPanel jPComQuemMoraAtualmente) {
        this.jPComQuemMoraAtualmente = jPComQuemMoraAtualmente;
    }

    /**
     * @return the jPanel1
     */
    public javax.swing.JPanel getjPanel1() {
        return jPanel1;
    }

    /**
     * @param jPanel1 the jPanel1 to set
     */
    public void setjPanel1(javax.swing.JPanel jPanel1) {
        this.jPanel1 = jPanel1;
    }

    /**
     * @return the jRBFemenino
     */
    public javax.swing.JRadioButton getjRBFemenino() {
        return jRBFemenino;
    }

    /**
     * @param jRBFemenino the jRBFemenino to set
     */
    public void setjRBFemenino(javax.swing.JRadioButton jRBFemenino) {
        this.jRBFemenino = jRBFemenino;
    }

    /**
     * @return the jScrollPane5
     */
    public javax.swing.JScrollPane getjScrollPane5() {
        return jScrollPane5;
    }

    /**
     * @param jScrollPane5 the jScrollPane5 to set
     */
    public void setjScrollPane5(javax.swing.JScrollPane jScrollPane5) {
        this.jScrollPane5 = jScrollPane5;
    }

    /**
     * @return the jScrollPane6
     */
    public javax.swing.JScrollPane getjScrollPane6() {
        return jScrollPane6;
    }

    /**
     * @param jScrollPane6 the jScrollPane6 to set
     */
    public void setjScrollPane6(javax.swing.JScrollPane jScrollPane6) {
        this.jScrollPane6 = jScrollPane6;
    }

    /**
     * @return the jScrollPane7
     */
    public javax.swing.JScrollPane getjScrollPane7() {
        return jScrollPane7;
    }

    /**
     * @param jScrollPane7 the jScrollPane7 to set
     */
    public void setjScrollPane7(javax.swing.JScrollPane jScrollPane7) {
        this.jScrollPane7 = jScrollPane7;
    }

    /**
     * @return the jSeparator1
     */
    public javax.swing.JPopupMenu.Separator getjSeparator1() {
        return jSeparator1;
    }

    /**
     * @param jSeparator1 the jSeparator1 to set
     */
    public void setjSeparator1(javax.swing.JPopupMenu.Separator jSeparator1) {
        this.jSeparator1 = jSeparator1;
    }

    /**
     * @return the jTFComQuemMora
     */
    public javax.swing.JTextField getjTFComQuemMora() {
        return jTFComQuemMora;
    }

    /**
     * @param jTFComQuemMora the jTFComQuemMora to set
     */
    public void setjTFComQuemMora(javax.swing.JTextField jTFComQuemMora) {
        this.jTFComQuemMora = jTFComQuemMora;
    }

    /**
     * @return the jDCDataEntradaClinica
     */
    public com.toedter.calendar.JDateChooser getjDCDataEntradaClinica() {
        return jDCDataEntradaClinica;
    }

    /**
     * @param jDCDataEntradaClinica the jDCDataEntradaClinica to set
     */
    public void setjDCDataEntradaClinica(com.toedter.calendar.JDateChooser jDCDataEntradaClinica) {
        this.jDCDataEntradaClinica = jDCDataEntradaClinica;
    }

    /**
     * @return the jDCDataNascimento
     */
    public com.toedter.calendar.JDateChooser getjDCDataNascimento() {
        return jDCDataNascimento;
    }

    /**
     * @param jDCDataNascimento the jDCDataNascimento to set
     */
    public void setjDCDataNascimento(com.toedter.calendar.JDateChooser jDCDataNascimento) {
        this.jDCDataNascimento = jDCDataNascimento;
    }

    /**
     * @return the jDCDataSaidaClinica
     */
    public com.toedter.calendar.JDateChooser getjDCDataSaidaClinica() {
        return jDCDataSaidaClinica;
    }

    /**
     * @param jDCDataSaidaClinica the jDCDataSaidaClinica to set
     */
    public void setjDCDataSaidaClinica(com.toedter.calendar.JDateChooser jDCDataSaidaClinica) {
        this.jDCDataSaidaClinica = jDCDataSaidaClinica;
    }

    /**
     * @return the jTFID
     */
    public javax.swing.JTextField getjTFID() {
        return jTFID;
    }

    /**
     * @param jTFID the jTFID to set
     */
    public void setjTFID(javax.swing.JTextField jTFID) {
        this.jTFID = jTFID;
    }

    /**
     * @return the jRBCPF
     */
    public javax.swing.JRadioButton getjRBCPF() {
        return jRBCPF;
    }

    /**
     * @param jRBCPF the jRBCPF to set
     */
    public void setjRBCPF(javax.swing.JRadioButton jRBCPF) {
        this.jRBCPF = jRBCPF;
    }

    /**
     * @return the jBAtualizarTodos
     */
    public javax.swing.JButton getjBAtualizarTodos() {
        return jBAtualizarTodos;
    }

    /**
     * @param jBAtualizarTodos the jBAtualizarTodos to set
     */
    public void setjBAtualizarTodos(javax.swing.JButton jBAtualizarTodos) {
        this.jBAtualizarTodos = jBAtualizarTodos;
    }

    /**
     * @return the ATUALIZAR
     */
    public String getATUALIZAR() {
        return ATUALIZAR;
    }

    /**
     * @return the jCBStatus
     */
    public javax.swing.JComboBox getjCBStatus() {
        return jCBStatus;
    }

    /**
     * @param jCBStatus the jCBStatus to set
     */
    public void setjCBStatus(javax.swing.JComboBox jCBStatus) {
        this.jCBStatus = jCBStatus;
    }

    /**
     * @return the jMenuBancoDados
     */
    public javax.swing.JMenuItem getjMenuBancoDados() {
        return jMenuBancoDados;
    }

    /**
     * @return the BANCODADOS
     */
    public String getBANCODADOS() {
        return BANCODADOS;
    }

    /**
     * @return the RELATORIO
     */
    public String getRELATORIO() {
        return RELATORIO;
    }

    /**
     * @return the jRBRelatorioPeriodo
     */
    public javax.swing.JRadioButton getjRBRelatorioPeriodo() {
        return jRBRelatorioPeriodo;
    }

    /**
     * @param jRBRelatorioPeriodo the jRBRelatorioPeriodo to set
     */
    public void setjRBRelatorioPeriodo(javax.swing.JRadioButton jRBRelatorioPeriodo) {
        this.jRBRelatorioPeriodo = jRBRelatorioPeriodo;
    }

    /**
     * @return the jDCFim
     */
    public com.toedter.calendar.JDateChooser getjDCFim() {
        return jDCFim;
    }

    /**
     * @param jDCFim the jDCFim to set
     */
    public void setjDCFim(com.toedter.calendar.JDateChooser jDCFim) {
        this.jDCFim = jDCFim;
    }

    /**
     * @return the jDCInicio
     */
    public com.toedter.calendar.JDateChooser getjDCInicio() {
        return jDCInicio;
    }

    /**
     * @param jDCInicio the jDCInicio to set
     */
    public void setjDCInicio(com.toedter.calendar.JDateChooser jDCInicio) {
        this.jDCInicio = jDCInicio;
    }

}
