package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.*;
import DAO.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BancoDAO implements InterfaceBancoDAO {

    private Connection conn;
    private Interno modelo;
    private int tamVetFamilia;
    private FamiliaSocial familia;

    public BancoDAO(Interno modelo) throws BancoDAOException {
        try {
            //modelo = new Interno();
            this.modelo = modelo;
            this.conn = ConnectionBancoFactory.getConnection();
        } catch (Exception e) {
            throw new BancoDAOException("Errooooo: " + e.getMessage() + "\n");
        }
    }

    public BancoDAO() throws BancoDAOException {
        this.conn = ConnectionBancoFactory.getConnection();

    }

    public void salvar() throws BancoDAOException {
        //A classe PreparedStatement faz cláusulas (instruções) sql com parâmetros
        PreparedStatement ps = null;
        PreparedStatement ps_query = null;
        Connection conn = null;
        ResultSet rs = null;
        if (modelo == null) {
            throw new BancoDAOException("O valor passado não pode ser nulo");
        }

        try {

            String SQL = "INSERT INTO Endereco (Rua, Numero, Complemento, Bairro, Cidade, Estado, Telefone, CEP) "
                    + "values (?, ?, ?, ?, ?, ?, ?, ?)";
            conn = this.getConn();

            ps = conn.prepareStatement(SQL);
            ps.setString(1, modelo.getEndereco().getRua());
            ps.setString(2, modelo.getEndereco().getNumero());
            ps.setString(3, modelo.getEndereco().getComplemento());
            ps.setString(4, modelo.getEndereco().getBairro());
            ps.setString(5, modelo.getEndereco().getCidade());
            ps.setString(6, modelo.getEndereco().getEstado());
            ps.setString(7, modelo.getEndereco().getTelefone());
            ps.setString(8, modelo.getEndereco().getCEP());
            ps.executeUpdate();

            SQL = "SELECT MAX(key_end) FROM Endereco";
            ps_query = conn.prepareStatement(SQL);
            rs = ps_query.executeQuery();
            if (!rs.next()) {
                throw new BancoDAOException("Não foi encontrado nenhum "
                        + "registro de Interno com o endereço citado");
            }
            int codigoEndereco = rs.getInt(1);
            //System.out.println("numero " + codigoEndereco);
            ps.clearParameters();

            SQL = "INSERT INTO EnderecoBoleto (Rua, Numero, Complemento, Bairro, Cidade, Estado, Telefone, CEP) "
                    + "values (?, ?, ?, ?, ?, ?, ?, ?)";
            conn = this.getConn();

            ps = conn.prepareStatement(SQL);
            ps.setString(1, modelo.getBoletoInterno().getEnderecoBoleto().getRua());
            ps.setString(2, modelo.getBoletoInterno().getEnderecoBoleto().getNumero());
            ps.setString(3, modelo.getBoletoInterno().getEnderecoBoleto().getComplemento());
            ps.setString(4, modelo.getBoletoInterno().getEnderecoBoleto().getBairro());
            ps.setString(5, modelo.getBoletoInterno().getEnderecoBoleto().getCidade());
            ps.setString(6, modelo.getBoletoInterno().getEnderecoBoleto().getEstado());
            ps.setString(7, modelo.getBoletoInterno().getEnderecoBoleto().getTelefone());
            ps.setString(8, modelo.getBoletoInterno().getEnderecoBoleto().getCEP());
            ps.executeUpdate();

            //SQL = "SELECT Identificador FROM Endereco WHERE Rua = ? and Numero = ? and Complemento = ? and Bairro = ? and Cidade = ? and Estado = ? and Telefone = ?";
            SQL = "SELECT MAX(key_endBol) FROM EnderecoBoleto";
            ps_query = conn.prepareStatement(SQL);
            rs = ps_query.executeQuery();
            if (!rs.next()) {
                throw new BancoDAOException("Não foi encontrado nenhum "
                        + "registro de Interno com o endereçoBol citado");
            }
            int codigoEnderecoBoleto = rs.getInt(1);
            //System.out.println("numero " + codigoEndereco);
            ps.clearParameters();

            SQL = "INSERT INTO Interno (Nome, Idade, DataNascimento, RG, CPF,"
                    + " Naturalidade, EstadoCivil, CTPSNumero, PIS, TituloEleitor, Zona, "
                    + "Secao, ImagemAntes, ImagemDepois, key_end, key_endBol, ComQuemMoraAtual, Sexo,Numero)"
                    + " values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";

            ps = conn.prepareStatement(SQL);
            ps.setString(1, modelo.getNome());
            ps.setString(2, modelo.getIdade());
            //ps.setString(3, modelo.getDataNascimento());
            if (modelo.getDataNascimento() != null) {
                java.sql.Date dataSql = new java.sql.Date(modelo.getDataNascimento().getTime());
                ps.setDate(3, dataSql);
            } else {
                ps.setDate(3, null);
            }

            ps.setString(4, modelo.getRG());
            ps.setString(5, modelo.getCPF());
            //erro:campo desnecessario
            //ps.setString(6, modelo.getBoletoInterno().getResponsavel());
            ps.setString(6, modelo.getNaturalidade());
            ps.setString(7, modelo.getEstadoCivil());
            ps.setString(8, modelo.getNumeroCTPS());
            ps.setString(9, modelo.getPIS());
            ps.setString(10, modelo.getTituloEleitoral().getNumero());
            ps.setString(11, modelo.getTituloEleitoral().getZona());
            ps.setString(12, modelo.getTituloEleitoral().getSecao());
            //erro: mudei de String para ImageIcon
            ps.setString(13, modelo.getFotoAntes());
            ps.setString(14, modelo.getFotoDepois());
            ps.setInt(15, codigoEndereco);
            ps.setInt(16, codigoEnderecoBoleto);
            ps.setString(17, modelo.getComQuemMoraAtualmente());
            ps.setString(18, modelo.getSexo());
            //ps.setString(19, modelo.getCodigo()+"");
            ps.setString(19, modelo.getNumero() + "");
            ps.executeUpdate();
            ps.clearParameters();

            SQL = "SELECT MAX(key_int) FROM Interno";
            ps_query = conn.prepareStatement(SQL);
            rs = ps_query.executeQuery();
            if (!rs.next()) {
                throw new BancoDAOException("Não foi encontrado nenhum "
                        + "registro de Interno");
            }
            int codigoInterno = rs.getInt(1);
            ps.clearParameters();

            SQL = "INSERT INTO Boleto (Responsavel, ResponsavelContato, VencimentoBoleto, MesmoEnderecoInterno, key_int) "
                    + "values (?, ?, ?, ?, ?)";

            conn = this.getConn();
            ps = conn.prepareStatement(SQL);
            ps.setString(1, modelo.getBoletoInterno().getContatoResponsavel());
            ps.setString(2, modelo.getBoletoInterno().getResponsavel());
            ps.setString(3, modelo.getBoletoInterno().getVencimentoBoleto());
            ps.setString(4, modelo.getBoletoInterno().getMesmoEnderecoInterno());
            ps.setInt(5, codigoInterno);
            //ps.setString(4, modelo.getBoletoInterno().getMesmoEnderecoInterno());
            ps.executeUpdate();

            SQL = "INSERT INTO Profissao (TrabalhoAtual, TrabalhoCA, Aposentado, AfastadoPS, SalarioDesemprego,Escolaridade, key_int) "
                    + "values (?, ?, ?, ?, ?, ?, ?)";

            ps = conn.prepareStatement(SQL);
            ps.setString(1, modelo.getProfissao().getTrabalhoAtual());
            ps.setString(2, modelo.getProfissao().getTrabalhoCA());
            ps.setString(3, modelo.getProfissao().getAposentado());
            ps.setString(4, modelo.getProfissao().getAfastadoPS());
            ps.setString(5, modelo.getProfissao().getSalarioDesemprego());
            ps.setString(6, modelo.getProfissao().getEscolaridade());
            ps.setInt(7, codigoInterno);
            ps.executeUpdate();
            ps.clearParameters();

            SQL = "INSERT INTO Questionario (ConhecimentoColonia, ExpectativaTratamento, MotivoUsarDrogas, Sexualidade"
                    + ", ProcessosJustica, Observacoes, PossuiRelacionamentoAfetivo, GrupoApoio, RelacionamentoSocial, key_int) "
                    + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            ps = conn.prepareStatement(SQL);
            ps.setString(1, modelo.getQuestionario().getConhecimentoColonia());
            ps.setString(2, modelo.getQuestionario().getExpectativaTratamento());
            //erro:campo nao existe
            ps.setString(3, modelo.getQuestionario().getMotivoUsarDrogas());
            ps.setString(4, modelo.getQuestionario().getSexualidade());
            ps.setString(5, modelo.getQuestionario().getProcessoJustica());
            ps.setString(6, modelo.getQuestionario().getObservacoes());
            ps.setString(7, modelo.getQuestionario().getPossuiRelacionamentoAfetivo());
            ps.setString(8, modelo.getQuestionario().getGrupoApoio());
            ps.setString(9, modelo.getQuestionario().getRelacionamentoSocial());

            ps.setInt(10, codigoInterno);
            ps.executeUpdate();
            ps.clearParameters();

            SQL = "INSERT INTO Dependencia (Maconha, Alcool, Inalantes, CocainaA, "
                    + "CocainaI, Crack, Comprimidos, CigarroTabaco, UsadaNoMomento, TempoDeUso, OutrasDrogas, key_int) "
                    + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            ps = conn.prepareStatement(SQL);
            ps.setString(1, modelo.getDependencia().getMaconha());
            ps.setString(2, modelo.getDependencia().getAlcool());
            ps.setString(3, modelo.getDependencia().getInalantes());
            ps.setString(4, modelo.getDependencia().getCocainaA());
            ps.setString(5, modelo.getDependencia().getCocainaI());
            ps.setString(6, modelo.getDependencia().getCrack());
            ps.setString(7, modelo.getDependencia().getComprimidos());
            ps.setString(8, modelo.getDependencia().getCigarroTabaco());
            ps.setString(9, modelo.getDependencia().getDrogaUsadaAtual());
            ps.setString(10, modelo.getDependencia().getTempoUso());
            ps.setString(11, modelo.getDependencia().getOutrasDrogas());
            ps.setInt(12, codigoInterno);
            ps.executeUpdate();
            ps.clearParameters();

            SQL = "INSERT INTO Processo (EntrevistadoraUm, EntrevistadoraDois,EntrevistadoraInternamento,EntrevistadoraSaida, DataEntrevistaUm, DataEntrevistaDois,DataEntrevistaInternamento,DataEntrevistaSaida, Status, key_int) "
                    + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            ps = conn.prepareStatement(SQL);
            ps.setString(1, modelo.getProcesso().getEntrevistadoraUm());
            ps.setString(2, modelo.getProcesso().getEntrevistadoraDois());
            ps.setString(3, modelo.getProcesso().getEntrevistadoraInternamento());
            ps.setString(4, modelo.getProcesso().getEntrevistadoraSaida());

            if (modelo.getProcesso().getDataUm() != null) {
                java.sql.Date dataSql = new java.sql.Date(modelo.getProcesso().getDataUm().getTime());
                ps.setDate(5, dataSql);
            } else {
                ps.setDate(5, null);
            }
            if (modelo.getProcesso().getDataDois() != null) {
                java.sql.Date dataSql = new java.sql.Date(modelo.getProcesso().getDataDois().getTime());
                ps.setDate(6, dataSql);
            } else {
                ps.setDate(6, null);
            }
            if (modelo.getProcesso().getDataInternamento() != null) {
                java.sql.Date dataSql = new java.sql.Date(modelo.getProcesso().getDataInternamento().getTime());
                ps.setDate(7, dataSql);
            } else {
                ps.setDate(7, null);
            }
            if (modelo.getProcesso().getDataSaida() != null) {
                java.sql.Date dataSql = new java.sql.Date(modelo.getProcesso().getDataSaida().getTime());
                ps.setDate(8, dataSql);
            } else {
                ps.setDate(8, null);
            }

            /*ps.setString(5, modelo.getProcesso().getDataUm());
             ps.setString(6, modelo.getProcesso().getDataDois());
             ps.setString(7, modelo.getProcesso().getDataInternamento());
             ps.setString(8, modelo.getProcesso().getDataSaida());*/
            ps.setString(9, modelo.getProcesso().getStatus());
            ps.setInt(10, codigoInterno);
            ps.executeUpdate();
            ps.clearParameters();

            //tamVetFamilia = modelo.getFamiliaSocial().size();            
            for (Iterator it = modelo.getFamiliaSocial().iterator(); it.hasNext();) {
                //familia = new FamiliaSocial();
                familia = (FamiliaSocial) it.next();
                SQL = "INSERT INTO FamiliaESocial (Parentesco, Nome, EstadoCivil, CasoDeDrogas, Idade, Profissao,"
                        + "key_int,Relacionamento,TempoCasados, Falecido) "
                        + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                ps = conn.prepareStatement(SQL);
                //erro: tem que incluir mais de um familiar
                ps.setString(1, familia.getParantesco());
                ps.setString(2, familia.getNome());
                ps.setString(3, familia.getEstadoCivil());
                ps.setString(4, familia.getCasoDrogas());
                ps.setString(5, familia.getIdade());
                ps.setString(6, familia.getProfissao());
                ps.setInt(7, codigoInterno);
                ps.setString(8, familia.getRelacionamentoFamiliar());
                ps.setString(9, familia.getTempoCasado());
                ps.setString(10, familia.getFalecido());
                ps.executeUpdate();
                ps.clearParameters();

            }

            SQL = "INSERT INTO Saude (Sono, Alimentacao, AlucinacaoDelirios, DesmaiosConvulsoes, Medicacao, AutoExterminio, ExamesEspecificos, key_int) "
                    + "values (?, ?, ?, ?, ?, ?, ?, ?)";

            ps = conn.prepareStatement(SQL);
            ps.setString(1, modelo.getSaude().getSono());
            ps.setString(2, modelo.getSaude().getAlimentacao());
            ps.setString(3, modelo.getSaude().getAlucinacaoDelirios());
            ps.setString(4, modelo.getSaude().getDesmaiosConvulcoes());
            ps.setString(5, modelo.getSaude().getMedicacao());
            ps.setString(6, modelo.getSaude().getAutoExterminio());
            ps.setString(7, modelo.getSaude().getExamesEspecificos());
            ps.setInt(8, codigoInterno);
            ps.executeUpdate();
            ps.clearParameters();

            SQL = "SELECT MAX(key_saude) FROM Saude";
            ps_query = conn.prepareStatement(SQL);
            rs = ps_query.executeQuery();
            if (!rs.next()) {
                throw new BancoDAOException("Não foi encontrado nenhum "
                        + "registro de Saude");
            }
            int codigoSaude = rs.getInt(1);
            ps.clearParameters();
            SQL = "INSERT INTO TratamentoAnteriores (Local, DataEntrada, DataSaida, MotivoSaida, key_saude) "
                    + "values (?, ?, ?, ?, ?)";

            ps = conn.prepareStatement(SQL);
            ps.setString(1, modelo.getTratamentosAnteriores().getClinica());
            /* ps.setString(2, modelo.getTratamentosAnteriores().getDataEntrada());
             ps.setString(3, modelo.getTratamentosAnteriores().getDataSaida());*/
            if (modelo.getTratamentosAnteriores().getDataEntrada() != null) {
                java.sql.Date dataSql = new java.sql.Date(modelo.getTratamentosAnteriores().getDataEntrada().getTime());
                ps.setDate(2, dataSql);
            } else {
                ps.setDate(2, null);
            }
            if (modelo.getTratamentosAnteriores().getDataSaida() != null) {
                java.sql.Date dataSql = new java.sql.Date(modelo.getTratamentosAnteriores().getDataSaida().getTime());
                ps.setDate(3, dataSql);
            } else {
                ps.setDate(3, null);
            }
            ps.setString(4, modelo.getTratamentosAnteriores().getMotivoSaida());
            ps.setInt(5, codigoSaude);
            ps.executeUpdate();
            ps.clearParameters();

            SQL = "INSERT INTO SituacaoEconomica (RendaFamilia, RendaFamiliaSeparados, RendaPessoal, key_int, AjudaFinanceira) "
                    + "values (?, ?, ?, ?, ?)";

            ps = conn.prepareStatement(SQL);
            ps.setString(1, modelo.getSituacaoEconomica().getRendaFamilia());
            //System.out.println(modelo.getSituacaoEconomica().getRendaFamilia());
            ps.setString(2, modelo.getSituacaoEconomica().getRendaFamiliaSeparados());
            ps.setString(3, modelo.getSituacaoEconomica().getRendaPessoal());
            ps.setInt(4, codigoInterno);
            ps.setString(5, modelo.getSituacaoEconomica().getAjudaFinanceira());
            ps.executeUpdate();
            ps.clearParameters();

//javax.swing.JOptionPane.showMessageDialog(null, "Dados salvos com sucesso!");
        } catch (SQLException sqle) {
            throw new BancoDAOException("Erro ao inserir dados " + sqle);
        } finally {
            ConnectionBancoFactory.fecharConexao(conn, ps);
        }
    }

    public void excluir(int codigoInterno) throws BancoDAOException {

        //this.modelo = modelo;
        PreparedStatement ps = null, ps_query = null;
        ResultSet rs = null;
        Connection conn = null;
        if (modelo == null) {
            throw new BancoDAOException("O valor passado não pode ser nulo");
        }
        try {
            conn = this.getConn();

            ps = conn.prepareStatement("DELETE FROM Questionario where key_int=?");
            ps.setInt(1, codigoInterno);
            ps.executeUpdate();
            ps.clearParameters();

            ps = conn.prepareStatement("DELETE FROM processo where key_int=?");
            ps.setInt(1, codigoInterno);
            ps.executeUpdate();
            ps.clearParameters();

            ps = conn.prepareStatement("DELETE FROM Profissao where key_int=?");
            ps.setInt(1, codigoInterno);
            ps.executeUpdate();
            ps.clearParameters();

            ps = conn.prepareStatement("DELETE FROM Dependencia where key_int=?");
            ps.setInt(1, codigoInterno);
            ps.executeUpdate();
            ps.clearParameters();

            ps = conn.prepareStatement("DELETE FROM FamiliaESocial where key_int=?");
            ps.setInt(1, codigoInterno);
            ps.executeUpdate();
            ps.clearParameters();

            ps = conn.prepareStatement("DELETE FROM SituacaoEconomica where key_int=?");
            ps.setInt(1, codigoInterno);
            ps.executeUpdate();
            ps.clearParameters();

            ps = conn.prepareStatement("DELETE FROM tratamentoanteriores where key_saude = (SELECT key_saude FROM Saude WHERE key_int = ?)");
            ps.setInt(1, codigoInterno);
            ps.executeUpdate();
            ps.clearParameters();

            ps = conn.prepareStatement("DELETE FROM saude where key_int=?");
            ps.setInt(1, codigoInterno);
            ps.executeUpdate();
            ps.clearParameters();

            String SQL = "Select key_end from Interno where key_int = ?";
            ps_query = conn.prepareStatement(SQL);
            ps_query.setInt(1, codigoInterno);
            rs = ps_query.executeQuery();
            if (!rs.next()) {
                throw new BancoDAOException("Não foi encontrado nenhum "
                        + "registro de Interno com o endereço citado");
            }
            int codigoEndereco = rs.getInt(1);
            //System.out.println("numero " + codigoEndereco);
            ps.clearParameters();

            ps = conn.prepareStatement("DELETE FROM Boleto where key_int = ?");
            ps.setInt(1, codigoEndereco);
            ps.executeUpdate();
            ps.clearParameters();

            SQL = "Select key_endbol from Interno where key_int = ?";
            ps_query = conn.prepareStatement(SQL);
            ps_query.setInt(1, codigoInterno);
            rs = ps_query.executeQuery();
            if (!rs.next()) {
                throw new BancoDAOException("Não foi encontrado nenhum "
                        + "registro de Interno com o endereço citado");
            }
            int codigoEndBol = rs.getInt(1);

            ps = conn.prepareStatement("DELETE FROM Interno where key_int=?");
            ps.setInt(1, codigoInterno);
            ps.executeUpdate();
            ps.clearParameters();

            ps = conn.prepareStatement("DELETE FROM Endereco where key_end = ?");
            ps.setInt(1, codigoEndereco);
            ps.executeUpdate();
            ps.clearParameters();

            ps = conn.prepareStatement("DELETE FROM EnderecoBoleto where key_endbol = ?");
            ps.setInt(1, codigoEndBol);
            ps.executeUpdate();
            ps.clearParameters();

            //javax.swing.JOptionPane.showMessageDialog(null, "Dados excluídos com sucesso!");
        } catch (SQLException sqle) {
            throw new BancoDAOException("Erro ao excluir dados:" + sqle);
        } finally {
            ConnectionBancoFactory.fecharConexao(conn, ps);
        }
    }

    public void atualizar(int codigoInterno) throws BancoDAOException {
        PreparedStatement ps = null, ps_query = null;
        Connection conn = null;
        ResultSet rs = null;

        if (modelo == null) {
            throw new BancoDAOException("O valor passado não pode ser nulo");
        }
        try {
            conn = this.getConn();

            String SQL = "UPDATE Interno SET Nome=?, Idade=?, DataNascimento=?, RG=?, CPF=?,"
                    + "EstadoCivil=?, CTPSNumero=?, PIS=?, TituloEleitor=?, Zona=?, Secao=?,"
                    + " Naturalidade = ?, ImagemAntes=?, ImagemDepois=?, ComQuemMoraAtual=?, Sexo=?, Numero=?  where key_int = ?";
            //Maioria dos erros está na sql de update, não no sistema

            String SQL_QUERY = "SELECT * FROM Interno WHERE key_int = ?";
            ps_query = conn.prepareStatement(SQL_QUERY);
            ps_query.setInt(1, codigoInterno);
            rs = ps_query.executeQuery();
            if (!rs.next()) {
                throw new BancoDAOException("Não foi encontrado nenhum "
                        + "registro de Interno com o codigo: "
                        + codigoInterno);
            }

            ps = conn.prepareStatement(SQL);
            ps.setString(1, modelo.getNome());
            ps.setString(2, modelo.getIdade());
            if (modelo.getDataNascimento() != null) {
                java.sql.Date dataSql = new java.sql.Date(modelo.getDataNascimento().getTime());
                ps.setDate(3, dataSql);
            } else {
                ps.setDate(3, null);
            }
            //ps.setString(3, modelo.getDataNascimento());
            ps.setString(4, modelo.getRG());
            ps.setString(5, modelo.getCPF());
            ps.setString(6, modelo.getEstadoCivil());
            ps.setString(7, modelo.getNumeroCTPS());
            ps.setString(8, modelo.getPIS());
            ps.setString(9, modelo.getTituloEleitoral().getNumero());
            ps.setString(10, modelo.getTituloEleitoral().getZona());
            ps.setString(11, modelo.getTituloEleitoral().getSecao());
            ps.setString(12, modelo.getNaturalidade());
            ps.setString(13, modelo.getFotoAntes());
            ps.setString(14, modelo.getFotoDepois());
            ps.setString(15, modelo.getComQuemMoraAtualmente());
            ps.setString(16, modelo.getSexo());
            ps.setInt(17, modelo.getNumero());
            ps.setInt(18, codigoInterno);
            ps.executeUpdate();
            ps.clearParameters();

            SQL = "UPDATE dependencia SET Maconha=?, Alcool=?, Inalantes=?, CocainaA=?, "
                    + "CocainaI=?, Crack=?, Comprimidos=?, CigarroTabaco=?, UsadaNoMomento=?, TempoDeUso=?, OutrasDrogas=? where key_int = ?";
            //Maioria dos erros está na sql de update, não no sistema

            ps = conn.prepareStatement(SQL);
            ps.setString(1, modelo.getDependencia().getMaconha());
            ps.setString(2, modelo.getDependencia().getAlcool());
            ps.setString(3, modelo.getDependencia().getInalantes());
            ps.setString(4, modelo.getDependencia().getCocainaA());
            ps.setString(5, modelo.getDependencia().getCocainaI());
            ps.setString(6, modelo.getDependencia().getCrack());
            ps.setString(7, modelo.getDependencia().getComprimidos());
            ps.setString(8, modelo.getDependencia().getCigarroTabaco());
            ps.setString(9, modelo.getDependencia().getDrogaUsadaAtual());
            ps.setString(10, modelo.getDependencia().getTempoUso());
            ps.setString(11, modelo.getDependencia().getOutrasDrogas());
            ps.setInt(12, codigoInterno);
            ps.executeUpdate();
            ps.clearParameters();

            SQL = "UPDATE endereco SET Rua=?, Numero=?, Complemento=?, Bairro=?, Cidade=?, Estado=?, Telefone=?, CEP=? where key_end = (Select key_end from Interno where key_int = ?)";
            //Maioria dos erros está na sql de update, não no sistema

            ps = conn.prepareStatement(SQL);
            ps.setString(1, modelo.getEndereco().getRua());
            ps.setString(2, modelo.getEndereco().getNumero());
            ps.setString(3, modelo.getEndereco().getComplemento());
            ps.setString(4, modelo.getEndereco().getBairro());
            ps.setString(5, modelo.getEndereco().getCidade());
            ps.setString(6, modelo.getEndereco().getEstado());
            ps.setString(7, modelo.getEndereco().getTelefone());
            ps.setString(8, modelo.getEndereco().getCEP());
            ps.setInt(9, modelo.getCodigo());
            ps.executeUpdate();
            ps.clearParameters();

            //FamiliaSocial fam;
            for (Iterator it = modelo.getFamiliaSocial().iterator(); it.hasNext();) {

                //fam = new FamiliaSocial();
                familia = (FamiliaSocial) it.next();

                SQL = "UPDATE Familiaesocial SET Parentesco=?, Nome=?, EstadoCivil=?,CasoDeDrogas=?,Idade=?,Profissao=?,Relacionamento=?,TempoCasados=?, Falecido=? where key_famsoc = ?";
                //Maioria dos erros está na sql de update, não no sistema

                ps = conn.prepareStatement(SQL);
                ps.setString(1, familia.getParantesco());
                ps.setString(2, familia.getNome());
                ps.setString(3, familia.getEstadoCivil());
                ps.setString(4, familia.getCasoDrogas());
                ps.setString(5, familia.getIdade());
                ps.setString(6, familia.getProfissao());
                ps.setString(7, familia.getRelacionamentoFamiliar());
                ps.setString(8, familia.getTempoCasado());
                ps.setString(9, familia.getFalecido());
                ps.setInt(10, familia.getId());
                if (ps.executeUpdate() == 0) {
                    cadastraFamilia(familia, modelo.getCodigo());
                }

                ps.clearParameters();

            }

            /*
             SQL = "UPDATE Organizacao SET CNPJ=?, Nome=? where Interno_Numero = ?";
             //Maioria dos erros está na sql de update, não no sistema

             ps = conn.prepareStatement(SQL);
             ps.setString(2, modelo.getOrganizacao().getNome());
             ps.setString(1, modelo.getOrganizacao().getCNPJ());
             ps.setInt(3, modelo.getCodigo());
             ps.executeUpdate();
             ps.clearParameters();               
             */
            SQL = "UPDATE processo SET EntrevistadoraUm=?, EntrevistadoraDois=?, EntrevistadoraInternamento=?, EntrevistadoraSaida=?, DataEntrevistaUm=?, DataEntrevistaDois=?,DataEntrevistaInternamento=?,DataEntrevistaSaida=?,Status=? where key_int = ?";
            //Maioria dos erros está na sql de update, não no sistema

            ps = conn.prepareStatement(SQL);
            ps.setString(1, modelo.getProcesso().getEntrevistadoraUm());
            ps.setString(2, modelo.getProcesso().getEntrevistadoraDois());
            ps.setString(3, modelo.getProcesso().getEntrevistadoraInternamento());
            ps.setString(4, modelo.getProcesso().getEntrevistadoraSaida());

            if (modelo.getProcesso().getDataUm() != null) {
                java.sql.Date dataSql = new java.sql.Date(modelo.getProcesso().getDataUm().getTime());
                ps.setDate(5, dataSql);
            } else {
                ps.setDate(5, null);
            }

            if (modelo.getProcesso().getDataDois() != null) {
                java.sql.Date dataSql = new java.sql.Date(modelo.getProcesso().getDataDois().getTime());
                ps.setDate(6, dataSql);
            } else {
                ps.setDate(6, null);
            }
            if (modelo.getProcesso().getDataInternamento() != null) {
                java.sql.Date dataSql = new java.sql.Date(modelo.getProcesso().getDataInternamento().getTime());
                ps.setDate(7, dataSql);
            } else {
                ps.setDate(7, null);
            }
            if (modelo.getProcesso().getDataSaida() != null) {
                java.sql.Date dataSql = new java.sql.Date(modelo.getProcesso().getDataSaida().getTime());
                ps.setDate(8, dataSql);
            } else {
                ps.setDate(8, null);
            }
            /* ps.setString(5, modelo.getProcesso().getDataUm());
             ps.setString(6, modelo.getProcesso().getDataDois());
             ps.setString(7, modelo.getProcesso().getDataInternamento());
             ps.setString(8, modelo.getProcesso().getDataSaida());*/
            ps.setString(9, modelo.getProcesso().getStatus());
            ps.setInt(10, modelo.getCodigo());
            ps.executeUpdate();
            ps.clearParameters();

            SQL = "UPDATE profissao SET TrabalhoAtual=?, TrabalhoCA=?, Aposentado=?, AfastadoPS=?, SalarioDesemprego=?, Escolaridade=? where key_int = ?";
            //Maioria dos erros está na sql de update, não no sistema

            ps = conn.prepareStatement(SQL);
            ps.setString(1, modelo.getProfissao().getTrabalhoAtual());
            ps.setString(2, modelo.getProfissao().getTrabalhoCA());
            ps.setString(3, modelo.getProfissao().getAposentado());
            ps.setString(4, modelo.getProfissao().getAfastadoPS());
            ps.setString(5, modelo.getProfissao().getSalarioDesemprego());
            ps.setString(6, modelo.getProfissao().getEscolaridade());
            ps.setInt(7, modelo.getCodigo());
            ps.executeUpdate();
            ps.clearParameters();

            SQL = "UPDATE questionario SET ConhecimentoColonia=?, ExpectativaTratamento=?, MotivoUsarDrogas=?, Sexualidade=?,"
                    + "ProcessosJustica=?, Observacoes=?, PossuiRelacionamentoAfetivo=?, GrupoApoio=?, RelacionamentoSocial=? where key_int = ?";
            //Maioria dos erros está na sql de update, não no sistema

            ps = conn.prepareStatement(SQL);
            ps.setString(1, modelo.getQuestionario().getConhecimentoColonia());
            ps.setString(2, modelo.getQuestionario().getExpectativaTratamento());
            ps.setString(3, modelo.getQuestionario().getMotivoUsarDrogas());
            ps.setString(4, modelo.getQuestionario().getSexualidade());
            ps.setString(5, modelo.getQuestionario().getProcessoJustica());
            ps.setString(6, modelo.getQuestionario().getObservacoes());
            ps.setString(7, modelo.getQuestionario().getPossuiRelacionamentoAfetivo());
            ps.setString(8, modelo.getQuestionario().getGrupoApoio());
            ps.setString(9, modelo.getQuestionario().getRelacionamentoSocial());
            ps.setInt(10, modelo.getCodigo());
            ps.executeUpdate();
            ps.clearParameters();

            SQL = "UPDATE saude SET Sono=?, Alimentacao=?, AlucinacaoDelirios=?, DesmaiosConvulsoes=?, Medicacao=?,"
                    + "AutoExterminio=?, ExamesEspecificos=? where key_int = ?";
            //Maioria dos erros está na sql de update, não no sistema

            ps = conn.prepareStatement(SQL);
            ps.setString(1, modelo.getSaude().getSono());
            ps.setString(2, modelo.getSaude().getAlimentacao());
            ps.setString(3, modelo.getSaude().getAlucinacaoDelirios());
            ps.setString(4, modelo.getSaude().getDesmaiosConvulcoes());
            ps.setString(5, modelo.getSaude().getMedicacao());
            ps.setString(6, modelo.getSaude().getAutoExterminio());
            ps.setString(7, modelo.getSaude().getExamesEspecificos());
            ps.setInt(8, modelo.getCodigo());
            ps.executeUpdate();
            ps.clearParameters();

            SQL = "UPDATE situacaoeconomica SET RendaFamilia=?, RendaFamiliaSeparados=?, RendaPessoal=? where key_int = ?";
            //Maioria dos erros está na sql de update, não no sistema

            ps = conn.prepareStatement(SQL);
            ps.setString(1, modelo.getSituacaoEconomica().getRendaFamilia());
            ps.setString(2, modelo.getSituacaoEconomica().getRendaFamiliaSeparados());
            ps.setString(3, modelo.getSituacaoEconomica().getRendaPessoal());
            ps.setInt(4, modelo.getCodigo());
            ps.executeUpdate();
            ps.clearParameters();

            SQL = "UPDATE tratamentoanteriores SET Local=?, DataEntrada=?, DataSaida=?, "
                    + " MotivoSaida=? where key_saude = (SELECT key_saude FROM saude WHERE key_int = ?)";
            //Maioria dos erros está na sql de update, não no sistema

            ps = conn.prepareStatement(SQL);
            ps.setString(1, modelo.getTratamentosAnteriores().getClinica());
            /* ps.setString(2, modelo.getTratamentosAnteriores().getDataEntrada());
             ps.setString(3, modelo.getTratamentosAnteriores().getDataSaida());*/
            if (modelo.getTratamentosAnteriores().getDataEntrada() != null) {
                java.sql.Date dataSql = new java.sql.Date(modelo.getTratamentosAnteriores().getDataEntrada().getTime());
                ps.setDate(2, dataSql);
            } else {
                ps.setDate(2, null);
            }
            if (modelo.getTratamentosAnteriores().getDataSaida() != null) {
                java.sql.Date dataSql = new java.sql.Date(modelo.getTratamentosAnteriores().getDataSaida().getTime());
                ps.setDate(3, dataSql);
            } else {
                ps.setDate(3, null);
            }
            ps.setString(4, modelo.getTratamentosAnteriores().getMotivoSaida());
            ps.setInt(5, modelo.getCodigo());
            ps.executeUpdate();
            ps.clearParameters();

            SQL = "UPDATE boleto SET Responsavel=?, ResponsavelContato=?, VencimentoBoleto=?, MesmoEnderecoInterno=? where key_int = ?";
            //Maioria dos erros está na sql de update, não no sistema

            ps = conn.prepareStatement(SQL);
            ps.setString(1, modelo.getBoletoInterno().getResponsavel());
            ps.setString(2, modelo.getBoletoInterno().getContatoResponsavel());
            ps.setString(3, modelo.getBoletoInterno().getVencimentoBoleto());
            ps.setString(4, modelo.getBoletoInterno().getMesmoEnderecoInterno());
            ps.setInt(5, modelo.getCodigo());
            ps.executeUpdate();
            ps.clearParameters();

            SQL = "UPDATE EnderecoBoleto SET Rua=?, Numero=?, Complemento=?, Bairro=?, Cidade=?, Estado=?, Telefone=?, CEP=? where key_endbol = "
                    + "(select key_endbol from interno where key_int = ?)";
            //Maioria dos erros está na sql de update, não no sistema

            ps = conn.prepareStatement(SQL);
            ps.setString(1, modelo.getBoletoInterno().getEnderecoBoleto().getRua());
            ps.setString(2, modelo.getBoletoInterno().getEnderecoBoleto().getNumero());
            ps.setString(3, modelo.getBoletoInterno().getEnderecoBoleto().getComplemento());
            ps.setString(4, modelo.getBoletoInterno().getEnderecoBoleto().getBairro());
            ps.setString(5, modelo.getBoletoInterno().getEnderecoBoleto().getCidade());
            ps.setString(6, modelo.getBoletoInterno().getEnderecoBoleto().getEstado());
            ps.setString(7, modelo.getBoletoInterno().getEnderecoBoleto().getTelefone());
            ps.setString(8, modelo.getBoletoInterno().getEnderecoBoleto().getCEP());
            ps.setInt(9, modelo.getCodigo());

            ps.executeUpdate();
            ps.clearParameters();

            //javax.swing.JOptionPane.showMessageDialog(null, "Dados atualizados com sucesso!");
        } catch (SQLException sqle) {
            throw new BancoDAOException("Erro ao atualizar dados: " + sqle);
        } finally {
            ConnectionBancoFactory.fecharConexao(conn, ps);
        }
    }

    public ArrayList<Interno> pesquisar(String nomeCliente) throws BancoDAOException {

        int flagFamiliaNaoEncontrada = 0;

        Interno interno = null;
        Dependencia dependencia = null;
        Endereco endereco = null;
        Endereco enderecoBol = null;
        FamiliaSocial familiaSocial = null;
        Organizacao organizacao = null;
        Processo processo = null;
        Profissao profissao = null;
        Questionario questionario = null;
        Relacionamento relacionamento = null;
        Saude saude = null;
        SituacaoEconomica situacaoEconomica = null;
        TituloEleitoral tituloEleitoral = null;
        TratamentosAnteriores tratamentosAnteriores = null;
        Boleto boleto = null;

        Connection conn = null;

        ResultSet rsInterno = null;
        ResultSet rsDependencia = null;
        ResultSet rsEndereco = null;
        ResultSet rsFamiliaSocial = null;
        ResultSet rsOrganizacao = null;
        ResultSet rsProcesso = null;
        ResultSet rsProfissao = null;
        ResultSet rsQuestionario = null;
        ResultSet rsRelacionamento = null;
        ResultSet rsSaude = null;
        ResultSet rsSituacaoEconomica = null;
        ResultSet rsTituloEleitoral = null;
        ResultSet rsTratamentosAnteriores = null;
        ResultSet rsBoleto = null;
        ResultSet rsEnderecoboleto = null;

        PreparedStatement ps = null;
        String SQL = null;

        ArrayList<Interno> internos = new ArrayList();

        if (nomeCliente.equals("")) {
            throw new BancoDAOException("O nome do cliente a ser pesquisado não pode ser nulo");
        } else {
            try {
                conn = this.getConn();
                SQL = "SELECT * FROM Interno WHERE Nome LIKE ? order by Nome";
                ps = conn.prepareStatement(SQL);
                ps.setString(1, "%" + nomeCliente + "%");
                rsInterno = ps.executeQuery();
                ps.clearParameters();
                if (!rsInterno.next()) {
                    //throw new BancoDAOException("Não foi encontrado nenhum " +
                    //      "registro de Cliente com o nome : " + nomeCliente);
                    return null;

                }

                do {
                    int codigoCliente = rsInterno.getInt(1);

                    SQL = "SELECT * FROM Dependencia WHERE key_int = ?";
                    ps = conn.prepareStatement(SQL);
                    ps.setInt(1, codigoCliente);
                    rsDependencia = ps.executeQuery();
                    ps.clearParameters();
                    if (!rsDependencia.next()) {
                        throw new BancoDAOException("Não foi encontrado nenhum "
                                + "registro de Interno na tabela Dependencia com o codigo: " + codigoCliente);
                    }

                    SQL = "SELECT * FROM EnderecoBoleto WHERE key_endbol = (select key_endbol from interno where key_int = ?)";
                    ps = conn.prepareStatement(SQL);
                    ps.setInt(1, codigoCliente);
                    rsEnderecoboleto = ps.executeQuery();
                    ps.clearParameters();
                    if (!rsEnderecoboleto.next()) {
                        throw new BancoDAOException("Não foi encontrado nenhum "
                                + "registro de endbol na tabela endereçoBoleto com o codigoCliente: " + codigoCliente);
                    }

                    SQL = "SELECT * FROM endereco WHERE key_end = (Select key_end FROM Interno WHERE key_int = ?)";
                    ps = conn.prepareStatement(SQL);
                    ps.setInt(1, codigoCliente);
                    rsEndereco = ps.executeQuery();
                    ps.clearParameters();
                    if (!rsEndereco.next()) {
                        throw new BancoDAOException("Não foi encontrado nenhum "
                                + "registro de Interno na tabela Endereco com o codigo: " + codigoCliente);
                    }

                    SQL = "SELECT * FROM Boleto WHERE key_int = ?";
                    ps = conn.prepareStatement(SQL);
                    ps.setInt(1, codigoCliente);
                    rsBoleto = ps.executeQuery();
                    ps.clearParameters();
                    if (!rsBoleto.next()) {
                        throw new BancoDAOException("Não foi encontrado nenhum "
                                + "endereço de boleto com o codigo: " + codigoCliente);
                    }

                    SQL = "SELECT * FROM familiaesocial WHERE key_int = ?";
                    ps = conn.prepareStatement(SQL);
                    ps.setInt(1, codigoCliente);
                    rsFamiliaSocial = ps.executeQuery();
                    ps.clearParameters();
                    if (!rsFamiliaSocial.next()) {
                        flagFamiliaNaoEncontrada = 1;
                        // throw new BancoDAOException("Não foi encontrado nenhum "
                        //       + "registro de Interno na tabela FamiliaESocial com o codigo: " + codigoCliente);
                    }

                    SQL = "SELECT * FROM processo WHERE key_int = ?";
                    ps = conn.prepareStatement(SQL);
                    ps.setInt(1, codigoCliente);
                    rsProcesso = ps.executeQuery();
                    ps.clearParameters();
                    if (!rsProcesso.next()) {
                        throw new BancoDAOException("Não foi encontrado nenhum "
                                + "registro de Interno na tabela Processo com o codigo: " + codigoCliente);
                    }

                    SQL = "SELECT * FROM profissao WHERE key_int = ?";
                    ps = conn.prepareStatement(SQL);
                    ps.setInt(1, codigoCliente);
                    rsProfissao = ps.executeQuery();
                    ps.clearParameters();
                    if (!rsProfissao.next()) {
                        throw new BancoDAOException("Não foi encontrado nenhum "
                                + "registro de Interno na tabela Profissao com o codigo: " + codigoCliente);
                    }

                    SQL = "SELECT * FROM questionario WHERE key_int = ?";
                    ps = conn.prepareStatement(SQL);
                    ps.setInt(1, codigoCliente);
                    rsQuestionario = ps.executeQuery();
                    ps.clearParameters();
                    if (!rsQuestionario.next()) {
                        throw new BancoDAOException("Não foi encontrado nenhum "
                                + "registro de Interno na tabela Questionario com o codigo: " + codigoCliente);
                    }

                    SQL = "SELECT * FROM Saude WHERE key_int = ?";
                    ps = conn.prepareStatement(SQL);
                    ps.setInt(1, codigoCliente);
                    rsSaude = ps.executeQuery();
                    ps.clearParameters();
                    if (!rsSaude.next()) {
                        throw new BancoDAOException("Não foi encontrado nenhum "
                                + "registro de Interno na tabela Saude com o codigo: " + codigoCliente);
                    }

                    SQL = "SELECT * FROM SituacaoEconomica WHERE key_int = ?";
                    ps = conn.prepareStatement(SQL);
                    ps.setInt(1, codigoCliente);
                    rsSituacaoEconomica = ps.executeQuery();
                    ps.clearParameters();
                    if (!rsSituacaoEconomica.next()) {
                        throw new BancoDAOException("Não foi encontrado nenhum "
                                + "registro de Interno na tabela SituacaoEconomica com o codigo: " + codigoCliente);
                    }

                    /*
                     SQL = "SELECT * FROM TituloEleitoral WHERE Interno_Numero = ?";
                     ps = conn.prepareStatement(SQL);
                     ps.setInt(1, codigoCliente);
                     rsTituloEleitoral = ps.executeQuery();
                     ps.clearParameters();
                     if (!rsTituloEleitoral.next()) {
                     throw new BancoDAOException("Não foi encontrado nenhum " +
                     "registro de Interno na tabela TituloEleitoral com o codigo: " + codigoCliente);
                     } 
                     */
                    SQL = "SELECT * FROM TratamentoAnteriores WHERE key_saude = (SELECT key_saude FROM Saude WHERE key_int = ?)";
                    ps = conn.prepareStatement(SQL);
                    ps.setInt(1, codigoCliente);
                    rsTratamentosAnteriores = ps.executeQuery();
                    ps.clearParameters();
                    if (!rsTratamentosAnteriores.next()) {
                        throw new BancoDAOException("Não foi encontrado nenhum "
                                + "registro de Interno na tabela TratamentosAnteriores com o codigo: " + codigoCliente);
                    }

                    interno = new Interno();
                    interno.setCodigo(Integer.parseInt(rsInterno.getString("key_int")));
                    interno.setNumero(Integer.parseInt(rsInterno.getString("Numero")));
                    interno.setNome(rsInterno.getString("Nome"));
                    interno.setSexo(rsInterno.getString("Sexo"));
                    interno.setIdade(rsInterno.getString("Idade"));
                    //interno.setDataNascimento(rsInterno.getString("DataNascimento"));
                    interno.setDataNascimento(rsInterno.getDate("DataNascimento"));
                    interno.setRG(rsInterno.getString("RG"));
                    interno.setCPF(rsInterno.getString("CPF"));
                    interno.setComQuemMoraAtualmente(rsInterno.getString("ComQuemMoraAtual"));
                    interno.setNaturalidade(rsInterno.getString("Naturalidade"));
                    interno.setEstadoCivil(rsInterno.getString("EstadoCivil"));
                    interno.setNumeroCTPS(rsInterno.getString("CTPSNumero"));
                    interno.setPIS(rsInterno.getString("PIS"));

                    interno.getTituloEleitoral().setNumero(rsInterno.getString("TituloEleitor"));
                    interno.getTituloEleitoral().setZona(rsInterno.getString("Zona"));
                    interno.getTituloEleitoral().setSecao(rsInterno.getString("Secao"));
                    //interno.setResponsavel(rsInterno.getString("Responsavel_Cpf"));
                    interno.setFotoAntes(rsInterno.getString("ImagemAntes"));
                    interno.setFotoDepois(rsInterno.getString("ImagemDepois"));
                    //interno.set(rsInterno.getString("Endereco_Identificador"));

                    boleto = new Boleto();
                    boleto.setContatoResponsavel(rsBoleto.getString("ResponsavelContato"));
                    boleto.setVencimentoBoleto(rsBoleto.getString("VencimentoBoleto"));
                    boleto.setResponsavel(rsBoleto.getString("Responsavel"));
                    boleto.setMesmoEnderecoInterno(rsBoleto.getString("MesmoEnderecoInterno"));
                    interno.setBoletoInterno(boleto);

                    endereco = new Endereco();
                    endereco.setRua(rsEndereco.getString("Rua"));
                    endereco.setBairro(rsEndereco.getString("Bairro"));
                    endereco.setNumero(rsEndereco.getString("Numero"));
                    endereco.setCidade(rsEndereco.getString("Cidade"));
                    endereco.setEstado(rsEndereco.getString("Estado"));
                    endereco.setComplemento(rsEndereco.getString("Complemento"));
                    endereco.setTelefone(rsEndereco.getString("Telefone"));
                    endereco.setCEP(rsEndereco.getString("CEP"));
                    interno.setEndereco(endereco);

                    enderecoBol = new Endereco();
                    enderecoBol.setRua(rsEnderecoboleto.getString("Rua"));
                    enderecoBol.setBairro(rsEnderecoboleto.getString("Bairro"));
                    enderecoBol.setNumero(rsEnderecoboleto.getString("Numero"));
                    enderecoBol.setCidade(rsEnderecoboleto.getString("Cidade"));
                    enderecoBol.setEstado(rsEnderecoboleto.getString("Estado"));
                    enderecoBol.setComplemento(rsEnderecoboleto.getString("Complemento"));
                    enderecoBol.setTelefone(rsEnderecoboleto.getString("Telefone"));
                    enderecoBol.setCEP(rsEnderecoboleto.getString("CEP"));
                    interno.getBoletoInterno().setEnderecoBoleto(enderecoBol);

                    questionario = new Questionario();
                    questionario.setConhecimentoColonia(rsQuestionario.getString("ConhecimentoColonia"));
                    questionario.setExpectativaTratamento(rsQuestionario.getString("ExpectativaTratamento"));
                    //erro:campo nao existe
                    questionario.setMotivoUsarDrogas(rsQuestionario.getString("MotivoUsarDrogas"));
                    questionario.setSexualidade(rsQuestionario.getString("Sexualidade"));
                    questionario.setPossuiRelacionamentoAfetivo(rsQuestionario.getString("PossuiRelacionamentoAfetivo"));
                    questionario.setGrupoApoio(rsQuestionario.getString("GrupoApoio"));
                    questionario.setRelacionamentoSocial(rsQuestionario.getString("RelacionamentoSocial"));
                    questionario.setProcessoJustica(rsQuestionario.getString("ProcessosJustica"));
                    questionario.setObservacoes(rsQuestionario.getString("Observacoes"));
                    interno.setQuestionario(questionario);

                    profissao = new Profissao();
                    profissao.setTrabalhoAtual(rsProfissao.getString("TrabalhoAtual"));
                    profissao.setTrabalhoCA(rsProfissao.getString("TrabalhoCA"));
                    profissao.setAposentado(rsProfissao.getString("Aposentado"));
                    profissao.setAfastadoPS(rsProfissao.getString("AfastadoPS"));
                    profissao.setSalarioDesemprego(rsProfissao.getString("SalarioDesemprego"));
                    profissao.setEscolaridade(rsProfissao.getString("Escolaridade"));

                    interno.setProfissao(profissao);

                    dependencia = new Dependencia();
                    dependencia.setMaconha(rsDependencia.getString("Maconha"));
                    dependencia.setCocainaA(rsDependencia.getString("CocainaA"));
                    dependencia.setCocainaI(rsDependencia.getString("CocainaI"));
                    dependencia.setCrack(rsDependencia.getString("Crack"));
                    dependencia.setCigarroTabaco(rsDependencia.getString("CigarroTabaco"));
                    dependencia.setInalantes(rsDependencia.getString("Inalantes"));
                    dependencia.setComprimidos(rsDependencia.getString("Comprimidos"));
                    dependencia.setAlcool(rsDependencia.getString("Alcool"));
                    dependencia.setDrogaUsadaAtual(rsDependencia.getString("UsadaNoMomento"));
                    dependencia.setTempoUso(rsDependencia.getString("TempoDeUso"));
                    dependencia.setOutrasDrogas(rsDependencia.getString("OutrasDrogas"));
                    interno.setDependencia(dependencia);

                    processo = new Processo();
                    processo.setEntrevistadoraUm(rsProcesso.getString("EntrevistadoraUm"));
                    processo.setEntrevistadoraDois(rsProcesso.getString("EntrevistadoraDois"));
                    processo.setEntrevistadoraInternamento(rsProcesso.getString("EntrevistadoraInternamento"));
                    processo.setEntrevistadoraSaida(rsProcesso.getString("EntrevistadoraSaida"));
                    /* processo.setDataUm(rsProcesso.getString("DataEntrevistaUm"));
                     processo.setDataDois(rsProcesso.getString("DataEntrevistaDois"));
                     processo.setDataInternamento(rsProcesso.getString("DataEntrevistaInternamento"));
                     processo.setDataSaida(rsProcesso.getString("DataEntrevistaSaida"));*/
                    processo.setDataUm(rsProcesso.getDate("DataEntrevistaUm"));
                    processo.setDataDois(rsProcesso.getDate("DataEntrevistaDois"));
                    processo.setDataInternamento(rsProcesso.getDate("DataEntrevistaInternamento"));
                    processo.setDataSaida(rsProcesso.getDate("DataEntrevistaSaida"));
                    processo.setStatus(rsProcesso.getString("Status"));
                    interno.setProcesso(processo);

                    ArrayList<FamiliaSocial> ff = new ArrayList<FamiliaSocial>();

                    if (flagFamiliaNaoEncontrada == 1) {
                        flagFamiliaNaoEncontrada = 0;
                    } else {
                        do {
                            familiaSocial = new FamiliaSocial();
                            familiaSocial.setParantesco(rsFamiliaSocial.getString("Parentesco"));
                            familiaSocial.setNome(rsFamiliaSocial.getString("Nome"));
                            familiaSocial.setEstadoCivil(rsFamiliaSocial.getString("EstadoCivil"));
                            familiaSocial.setCasoDrogas(rsFamiliaSocial.getString("CasoDeDrogas"));
                            familiaSocial.setIdade(rsFamiliaSocial.getString("Idade"));
                            familiaSocial.setProfissao(rsFamiliaSocial.getString("Profissao"));
                            familiaSocial.setRelacionamentoFamiliar(rsFamiliaSocial.getString("Relacionamento"));
                            familiaSocial.setTempoCasado(rsFamiliaSocial.getString("TempoCasados"));
                            familiaSocial.setId(Integer.parseInt(rsFamiliaSocial.getString("key_famsoc")));
                            familiaSocial.setFalecido(rsFamiliaSocial.getString("Falecido"));
                            familiaSocial.setId(rsFamiliaSocial.getInt("key_famsoc"));

                            ff.add(familiaSocial);

                            //interno.setFamiliaSocial(familiaSocial);
                            //erro:mudei de classe foi para o interno                
                            //familiaSocial.setResponsavel(rsFamiliaSocial           .getString("Responsavel"));
                            //erro: campo desnecessario
                            //familiaSocial.setCPF(rsFamiliaSocial                   .getString("CPF"));
                            //familiaSocial.setRelacionamentoFamiliar(rsFamiliaSocial.getString("RelacionamentoFamiliar"));
                        } while (rsFamiliaSocial.next());
                    }
                    interno.setFamiliaSocial(ff);

                    saude = new Saude();
                    saude.setSono(rsSaude.getString("Sono"));
                    saude.setAlimentacao(rsSaude.getString("Alimentacao"));
                    saude.setAlucinacaoDelirios(rsSaude.getString("AlucinacaoDelirios"));
                    saude.setDesmaiosConvulcoes(rsSaude.getString("DesmaiosConvulsoes"));
                    saude.setMedicacao(rsSaude.getString("Medicacao"));
                    saude.setAutoExterminio(rsSaude.getString("AutoExterminio"));
                    saude.setExamesEspecificos(rsSaude.getString("ExamesEspecificos"));
                    interno.setSaude(saude);

                    situacaoEconomica = new SituacaoEconomica();
                    situacaoEconomica.setRendaFamilia(rsSituacaoEconomica.getString("RendaFamilia"));
                    situacaoEconomica.setRendaFamiliaSeparados(rsSituacaoEconomica.getString("RendaFamiliaSeparados"));
                    situacaoEconomica.setRendaPessoal(rsSituacaoEconomica.getString("RendaPessoal"));
                    situacaoEconomica.setAjudaFinanceira(rsSituacaoEconomica.getString("AjudaFinanceira"));
                    interno.setSituacaoEconomica(situacaoEconomica);

                    tratamentosAnteriores = new TratamentosAnteriores();
                    tratamentosAnteriores.setClinica(rsTratamentosAnteriores.getString("Local"));
                    /*tratamentosAnteriores.setDataEntrada(rsTratamentosAnteriores.getString("DataEntrada"));
                     tratamentosAnteriores.setDataSaida(rsTratamentosAnteriores.getString("DataSaida"));*/
                    tratamentosAnteriores.setDataEntrada(rsTratamentosAnteriores.getDate("DataEntrada"));
                    tratamentosAnteriores.setDataSaida(rsTratamentosAnteriores.getDate("DataSaida"));
                    tratamentosAnteriores.setMotivoSaida(rsTratamentosAnteriores.getString("MotivoSaida"));
                    interno.setTratamentosAnteriores(tratamentosAnteriores);
                    internos.add(interno);

                } while (rsInterno.next());

            } catch (SQLException ex) {
                throw new BancoDAOException("Erro ao pesquisar o cliente.");
            } finally {
                ConnectionBancoFactory.fecharConexao(conn, ps);
            }
        }

        return internos;
    }

    public ArrayList<Interno> pesquisarTodos() throws BancoDAOException {

        int flagFamiliaNaoEncontrada = 0;

        Interno interno = null;
        Dependencia dependencia = null;
        Endereco endereco = null;
        Endereco enderecoBol = null;
        FamiliaSocial familiaSocial = null;
        Organizacao organizacao = null;
        Processo processo = null;
        Profissao profissao = null;
        Questionario questionario = null;
        Relacionamento relacionamento = null;
        Saude saude = null;
        SituacaoEconomica situacaoEconomica = null;
        TituloEleitoral tituloEleitoral = null;
        TratamentosAnteriores tratamentosAnteriores = null;
        Boleto boleto = null;

        Connection conn = null;

        ResultSet rsInterno = null;
        ResultSet rsDependencia = null;
        ResultSet rsEndereco = null;
        ResultSet rsFamiliaSocial = null;
        ResultSet rsOrganizacao = null;
        ResultSet rsProcesso = null;
        ResultSet rsProfissao = null;
        ResultSet rsQuestionario = null;
        ResultSet rsRelacionamento = null;
        ResultSet rsSaude = null;
        ResultSet rsSituacaoEconomica = null;
        ResultSet rsTituloEleitoral = null;
        ResultSet rsTratamentosAnteriores = null;
        ResultSet rsBoleto = null;
        ResultSet rsEnderecoboleto = null;

        PreparedStatement ps = null;
        String SQL = null;

        ArrayList<Interno> internos = new ArrayList();

        try {
            conn = this.getConn();
            SQL = "SELECT * FROM Interno order by Numero";
            ps = conn.prepareStatement(SQL);
            //ps.setString(1, "%" + nomeCliente + "%");
            rsInterno = ps.executeQuery();
            ps.clearParameters();
            if (!rsInterno.next()) {
                //throw new BancoDAOException("Não foi encontrado nenhum " +
                //      "registro de Cliente com o nome : " + nomeCliente);
                return null;

            }

            do {
                int codigoCliente = rsInterno.getInt(1);

                SQL = "SELECT * FROM Dependencia WHERE key_int = ?";
                ps = conn.prepareStatement(SQL);
                ps.setInt(1, codigoCliente);
                rsDependencia = ps.executeQuery();
                ps.clearParameters();
                if (!rsDependencia.next()) {
                    throw new BancoDAOException("Não foi encontrado nenhum "
                            + "registro de Interno na tabela Dependencia com o codigo: " + codigoCliente);
                }

                SQL = "SELECT * FROM EnderecoBoleto WHERE key_endbol = (select key_endbol from interno where key_int = ?)";
                ps = conn.prepareStatement(SQL);
                ps.setInt(1, codigoCliente);
                rsEnderecoboleto = ps.executeQuery();
                ps.clearParameters();
                if (!rsEnderecoboleto.next()) {
                    throw new BancoDAOException("Não foi encontrado nenhum "
                            + "registro de endbol na tabela endereçoBoleto com o codigoCliente: " + codigoCliente);
                }

                SQL = "SELECT * FROM endereco WHERE key_end = (Select key_end FROM Interno WHERE key_int = ?)";
                ps = conn.prepareStatement(SQL);
                ps.setInt(1, codigoCliente);
                rsEndereco = ps.executeQuery();
                ps.clearParameters();
                if (!rsEndereco.next()) {
                    throw new BancoDAOException("Não foi encontrado nenhum "
                            + "registro de Interno na tabela Endereco com o codigo: " + codigoCliente);
                }

                SQL = "SELECT * FROM Boleto WHERE key_int = ?";
                ps = conn.prepareStatement(SQL);
                ps.setInt(1, codigoCliente);
                rsBoleto = ps.executeQuery();
                ps.clearParameters();
                if (!rsBoleto.next()) {
                    throw new BancoDAOException("Não foi encontrado nenhum "
                            + "endereço de boleto com o codigo: " + codigoCliente);
                }

                SQL = "SELECT * FROM familiaesocial WHERE key_int = ?";
                ps = conn.prepareStatement(SQL);
                ps.setInt(1, codigoCliente);
                rsFamiliaSocial = ps.executeQuery();
                ps.clearParameters();
                if (!rsFamiliaSocial.next()) {
                    flagFamiliaNaoEncontrada = 1;
                    // throw new BancoDAOException("Não foi encontrado nenhum "
                    //       + "registro de Interno na tabela FamiliaESocial com o codigo: " + codigoCliente);
                }

                SQL = "SELECT * FROM processo WHERE key_int = ?";
                ps = conn.prepareStatement(SQL);
                ps.setInt(1, codigoCliente);
                rsProcesso = ps.executeQuery();
                ps.clearParameters();
                if (!rsProcesso.next()) {
                    throw new BancoDAOException("Não foi encontrado nenhum "
                            + "registro de Interno na tabela Processo com o codigo: " + codigoCliente);
                }

                SQL = "SELECT * FROM profissao WHERE key_int = ?";
                ps = conn.prepareStatement(SQL);
                ps.setInt(1, codigoCliente);
                rsProfissao = ps.executeQuery();
                ps.clearParameters();
                if (!rsProfissao.next()) {
                    throw new BancoDAOException("Não foi encontrado nenhum "
                            + "registro de Interno na tabela Profissao com o codigo: " + codigoCliente);
                }

                SQL = "SELECT * FROM questionario WHERE key_int = ?";
                ps = conn.prepareStatement(SQL);
                ps.setInt(1, codigoCliente);
                rsQuestionario = ps.executeQuery();
                ps.clearParameters();
                if (!rsQuestionario.next()) {
                    throw new BancoDAOException("Não foi encontrado nenhum "
                            + "registro de Interno na tabela Questionario com o codigo: " + codigoCliente);
                }

                SQL = "SELECT * FROM Saude WHERE key_int = ?";
                ps = conn.prepareStatement(SQL);
                ps.setInt(1, codigoCliente);
                rsSaude = ps.executeQuery();
                ps.clearParameters();
                if (!rsSaude.next()) {
                    throw new BancoDAOException("Não foi encontrado nenhum "
                            + "registro de Interno na tabela Saude com o codigo: " + codigoCliente);
                }

                SQL = "SELECT * FROM SituacaoEconomica WHERE key_int = ?";
                ps = conn.prepareStatement(SQL);
                ps.setInt(1, codigoCliente);
                rsSituacaoEconomica = ps.executeQuery();
                ps.clearParameters();
                if (!rsSituacaoEconomica.next()) {
                    throw new BancoDAOException("Não foi encontrado nenhum "
                            + "registro de Interno na tabela SituacaoEconomica com o codigo: " + codigoCliente);
                }

                /*
                 SQL = "SELECT * FROM TituloEleitoral WHERE Interno_Numero = ?";
                 ps = conn.prepareStatement(SQL);
                 ps.setInt(1, codigoCliente);
                 rsTituloEleitoral = ps.executeQuery();
                 ps.clearParameters();
                 if (!rsTituloEleitoral.next()) {
                 throw new BancoDAOException("Não foi encontrado nenhum " +
                 "registro de Interno na tabela TituloEleitoral com o codigo: " + codigoCliente);
                 } 
                 */
                SQL = "SELECT * FROM TratamentoAnteriores WHERE key_saude = (SELECT key_saude FROM Saude WHERE key_int = ?)";
                ps = conn.prepareStatement(SQL);
                ps.setInt(1, codigoCliente);
                rsTratamentosAnteriores = ps.executeQuery();
                ps.clearParameters();
                if (!rsTratamentosAnteriores.next()) {
                    throw new BancoDAOException("Não foi encontrado nenhum "
                            + "registro de Interno na tabela TratamentosAnteriores com o codigo: " + codigoCliente);
                }

                interno = new Interno();
                interno.setNumero(Integer.parseInt(rsInterno.getString("Numero")));
                interno.setCodigo(Integer.parseInt(rsInterno.getString("key_int")));
                interno.setNome(rsInterno.getString("Nome"));
                interno.setSexo(rsInterno.getString("Sexo"));
                interno.setIdade(rsInterno.getString("Idade"));
                //interno.setDataNascimento(rsInterno.getString("DataNascimento"));
                interno.setDataNascimento(rsInterno.getDate("DataNascimento"));
                interno.setRG(rsInterno.getString("RG"));
                interno.setCPF(rsInterno.getString("CPF"));
                interno.setComQuemMoraAtualmente(rsInterno.getString("ComQuemMoraAtual"));
                interno.setNaturalidade(rsInterno.getString("Naturalidade"));
                interno.setEstadoCivil(rsInterno.getString("EstadoCivil"));
                interno.setNumeroCTPS(rsInterno.getString("CTPSNumero"));
                interno.setPIS(rsInterno.getString("PIS"));

                interno.getTituloEleitoral().setNumero(rsInterno.getString("TituloEleitor"));
                interno.getTituloEleitoral().setZona(rsInterno.getString("Zona"));
                interno.getTituloEleitoral().setSecao(rsInterno.getString("Secao"));
                //interno.setResponsavel(rsInterno.getString("Responsavel_Cpf"));
                interno.setFotoAntes(rsInterno.getString("ImagemAntes"));
                interno.setFotoDepois(rsInterno.getString("ImagemDepois"));
                //interno.set(rsInterno.getString("Endereco_Identificador"));

                boleto = new Boleto();
                boleto.setContatoResponsavel(rsBoleto.getString("ResponsavelContato"));
                boleto.setVencimentoBoleto(rsBoleto.getString("VencimentoBoleto"));
                boleto.setResponsavel(rsBoleto.getString("Responsavel"));
                boleto.setMesmoEnderecoInterno(rsBoleto.getString("MesmoEnderecoInterno"));
                interno.setBoletoInterno(boleto);

                endereco = new Endereco();
                endereco.setRua(rsEndereco.getString("Rua"));
                endereco.setBairro(rsEndereco.getString("Bairro"));
                endereco.setNumero(rsEndereco.getString("Numero"));
                endereco.setCidade(rsEndereco.getString("Cidade"));
                endereco.setEstado(rsEndereco.getString("Estado"));
                endereco.setComplemento(rsEndereco.getString("Complemento"));
                endereco.setTelefone(rsEndereco.getString("Telefone"));
                endereco.setCEP(rsEndereco.getString("CEP"));
                interno.setEndereco(endereco);

                enderecoBol = new Endereco();
                enderecoBol.setRua(rsEnderecoboleto.getString("Rua"));
                enderecoBol.setBairro(rsEnderecoboleto.getString("Bairro"));
                enderecoBol.setNumero(rsEnderecoboleto.getString("Numero"));
                enderecoBol.setCidade(rsEnderecoboleto.getString("Cidade"));
                enderecoBol.setEstado(rsEnderecoboleto.getString("Estado"));
                enderecoBol.setComplemento(rsEnderecoboleto.getString("Complemento"));
                enderecoBol.setTelefone(rsEnderecoboleto.getString("Telefone"));
                enderecoBol.setCEP(rsEnderecoboleto.getString("CEP"));
                interno.getBoletoInterno().setEnderecoBoleto(enderecoBol);

                questionario = new Questionario();
                questionario.setConhecimentoColonia(rsQuestionario.getString("ConhecimentoColonia"));
                questionario.setExpectativaTratamento(rsQuestionario.getString("ExpectativaTratamento"));
                //erro:campo nao existe
                questionario.setMotivoUsarDrogas(rsQuestionario.getString("MotivoUsarDrogas"));
                questionario.setSexualidade(rsQuestionario.getString("Sexualidade"));
                questionario.setPossuiRelacionamentoAfetivo(rsQuestionario.getString("PossuiRelacionamentoAfetivo"));
                questionario.setGrupoApoio(rsQuestionario.getString("GrupoApoio"));
                questionario.setRelacionamentoSocial(rsQuestionario.getString("RelacionamentoSocial"));
                questionario.setProcessoJustica(rsQuestionario.getString("ProcessosJustica"));
                questionario.setObservacoes(rsQuestionario.getString("Observacoes"));
                interno.setQuestionario(questionario);

                profissao = new Profissao();
                profissao.setTrabalhoAtual(rsProfissao.getString("TrabalhoAtual"));
                profissao.setTrabalhoCA(rsProfissao.getString("TrabalhoCA"));
                profissao.setAposentado(rsProfissao.getString("Aposentado"));
                profissao.setAfastadoPS(rsProfissao.getString("AfastadoPS"));
                profissao.setSalarioDesemprego(rsProfissao.getString("SalarioDesemprego"));
                profissao.setEscolaridade(rsProfissao.getString("Escolaridade"));

                interno.setProfissao(profissao);

                dependencia = new Dependencia();
                dependencia.setMaconha(rsDependencia.getString("Maconha"));
                dependencia.setCocainaA(rsDependencia.getString("CocainaA"));
                dependencia.setCocainaI(rsDependencia.getString("CocainaI"));
                dependencia.setCrack(rsDependencia.getString("Crack"));
                dependencia.setCigarroTabaco(rsDependencia.getString("CigarroTabaco"));
                dependencia.setInalantes(rsDependencia.getString("Inalantes"));
                dependencia.setComprimidos(rsDependencia.getString("Comprimidos"));
                dependencia.setAlcool(rsDependencia.getString("Alcool"));
                dependencia.setDrogaUsadaAtual(rsDependencia.getString("UsadaNoMomento"));
                dependencia.setTempoUso(rsDependencia.getString("TempoDeUso"));
                dependencia.setOutrasDrogas(rsDependencia.getString("OutrasDrogas"));
                interno.setDependencia(dependencia);

                processo = new Processo();
                processo.setEntrevistadoraUm(rsProcesso.getString("EntrevistadoraUm"));
                processo.setEntrevistadoraDois(rsProcesso.getString("EntrevistadoraDois"));
                processo.setEntrevistadoraInternamento(rsProcesso.getString("EntrevistadoraInternamento"));
                processo.setEntrevistadoraSaida(rsProcesso.getString("EntrevistadoraSaida"));
                /*processo.setDataUm(rsProcesso.getString("DataEntrevistaUm"));
                 processo.setDataDois(rsProcesso.getString("DataEntrevistaDois"));
                 processo.setDataInternamento(rsProcesso.getString("DataEntrevistaInternamento"));
                 processo.setDataSaida(rsProcesso.getString("DataEntrevistaSaida"));
                 processo.setDataSaida(rsProcesso.getString("DataEntrevistaSaida"));*/
                processo.setDataUm(rsProcesso.getDate("DataEntrevistaUm"));
                processo.setDataDois(rsProcesso.getDate("DataEntrevistaDois"));
                processo.setDataInternamento(rsProcesso.getDate("DataEntrevistaInternamento"));
                processo.setDataSaida(rsProcesso.getDate("DataEntrevistaSaida"));
                processo.setDataSaida(rsProcesso.getDate("DataEntrevistaSaida"));
                processo.setStatus(rsProcesso.getString("Status"));
                interno.setProcesso(processo);

                ArrayList<FamiliaSocial> ff = new ArrayList<FamiliaSocial>();

                if (flagFamiliaNaoEncontrada == 1) {
                    flagFamiliaNaoEncontrada = 0;
                } else {
                    do {
                        familiaSocial = new FamiliaSocial();
                        familiaSocial.setParantesco(rsFamiliaSocial.getString("Parentesco"));
                        familiaSocial.setNome(rsFamiliaSocial.getString("Nome"));
                        familiaSocial.setEstadoCivil(rsFamiliaSocial.getString("EstadoCivil"));
                        familiaSocial.setCasoDrogas(rsFamiliaSocial.getString("CasoDeDrogas"));
                        familiaSocial.setIdade(rsFamiliaSocial.getString("Idade"));
                        familiaSocial.setProfissao(rsFamiliaSocial.getString("Profissao"));
                        familiaSocial.setRelacionamentoFamiliar(rsFamiliaSocial.getString("Relacionamento"));
                        familiaSocial.setTempoCasado(rsFamiliaSocial.getString("TempoCasados"));
                        familiaSocial.setId(Integer.parseInt(rsFamiliaSocial.getString("key_famsoc")));
                        familiaSocial.setFalecido(rsFamiliaSocial.getString("Falecido"));
                        familiaSocial.setId(rsFamiliaSocial.getInt("key_famsoc"));

                        ff.add(familiaSocial);

                        //interno.setFamiliaSocial(familiaSocial);
                        //erro:mudei de classe foi para o interno                
                        //familiaSocial.setResponsavel(rsFamiliaSocial           .getString("Responsavel"));
                        //erro: campo desnecessario
                        //familiaSocial.setCPF(rsFamiliaSocial                   .getString("CPF"));
                        //familiaSocial.setRelacionamentoFamiliar(rsFamiliaSocial.getString("RelacionamentoFamiliar"));
                    } while (rsFamiliaSocial.next());
                }
                interno.setFamiliaSocial(ff);

                saude = new Saude();
                saude.setSono(rsSaude.getString("Sono"));
                saude.setAlimentacao(rsSaude.getString("Alimentacao"));
                saude.setAlucinacaoDelirios(rsSaude.getString("AlucinacaoDelirios"));
                saude.setDesmaiosConvulcoes(rsSaude.getString("DesmaiosConvulsoes"));
                saude.setMedicacao(rsSaude.getString("Medicacao"));
                saude.setAutoExterminio(rsSaude.getString("AutoExterminio"));
                saude.setExamesEspecificos(rsSaude.getString("ExamesEspecificos"));
                interno.setSaude(saude);

                situacaoEconomica = new SituacaoEconomica();
                situacaoEconomica.setRendaFamilia(rsSituacaoEconomica.getString("RendaFamilia"));
                situacaoEconomica.setRendaFamiliaSeparados(rsSituacaoEconomica.getString("RendaFamiliaSeparados"));
                situacaoEconomica.setRendaPessoal(rsSituacaoEconomica.getString("RendaPessoal"));
                situacaoEconomica.setAjudaFinanceira(rsSituacaoEconomica.getString("AjudaFinanceira"));
                interno.setSituacaoEconomica(situacaoEconomica);

                tratamentosAnteriores = new TratamentosAnteriores();
                tratamentosAnteriores.setClinica(rsTratamentosAnteriores.getString("Local"));
                /* tratamentosAnteriores.setDataEntrada(rsTratamentosAnteriores.getString("DataEntrada"));
                 tratamentosAnteriores.setDataSaida(rsTratamentosAnteriores.getString("DataSaida"));*/
                tratamentosAnteriores.setDataEntrada(rsTratamentosAnteriores.getDate("DataEntrada"));
                tratamentosAnteriores.setDataSaida(rsTratamentosAnteriores.getDate("DataSaida"));
                tratamentosAnteriores.setMotivoSaida(rsTratamentosAnteriores.getString("MotivoSaida"));
                interno.setTratamentosAnteriores(tratamentosAnteriores);
                internos.add(interno);

            } while (rsInterno.next());

        } catch (SQLException ex) {
            throw new BancoDAOException("Erro ao pesquisar o cliente.");
        } finally {
            ConnectionBancoFactory.fecharConexao(conn, ps);
        }

        return internos;
    }

    /* @Override
     public Interno pesquisarID(String ID) throws BancoDAOException {
     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
     }*/
    @Override
    public Interno pesquisarCPF(String CPF) throws BancoDAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Interno pesquisar() throws BancoDAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the conn
     */
    public Connection getConn() {
        return conn;
    }

    /**
     * @param conn the conn to set
     */
    public void setConn(Connection conn) {
        this.conn = conn;
    }

    private void cadastraFamilia(FamiliaSocial familia, int codigoInterno) throws SQLException {
        String SQL;
        PreparedStatement ps;
        SQL = "INSERT INTO FamiliaESocial (Parentesco, Nome, EstadoCivil, CasoDeDrogas, Idade, Profissao,"
                + "key_int,Relacionamento,TempoCasados, Falecido) "
                + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        ps = conn.prepareStatement(SQL);
        //erro: tem que incluir mais de um familiar
        ps.setString(1, familia.getParantesco());
        ps.setString(2, familia.getNome());
        ps.setString(3, familia.getEstadoCivil());
        ps.setString(4, familia.getCasoDrogas());
        ps.setString(5, familia.getIdade());
        ps.setString(6, familia.getProfissao());
        ps.setInt(7, codigoInterno);
        ps.setString(8, familia.getRelacionamentoFamiliar());
        ps.setString(9, familia.getTempoCasado());
        //ps.setInt(10, codigoEndereco);
        ps.setString(10, familia.getFalecido());
        ps.executeUpdate();
        ps.clearParameters();
        //To change body of generated methods, choose Tools | Templates.
    }

    public void excluirFamiliares(ArrayList<Integer> codigoFamiliar) throws BancoDAOException {

        PreparedStatement ps = null, ps_query = null;
        ResultSet rs = null;
        Connection conn = null;
        if (codigoFamiliar.size() <= 0) {
            return;
        }

        try {
            conn = this.getConn();
            int codigoDoFamiliar;

            for (Iterator it = codigoFamiliar.iterator(); it.hasNext();) {
                //familia = new FamiliaSocial();
                codigoDoFamiliar = (int) it.next();

                ps = conn.prepareStatement("DELETE FROM FamiliaESocial where key_famsoc=?");
                ps.setInt(1, codigoDoFamiliar);
                ps.executeUpdate();
                ps.clearParameters();

            }
            //javax.swing.JOptionPane.showMessageDialog(null, "Dados excluídos com sucesso!");
        } catch (SQLException sqle) {
            throw new BancoDAOException("Erro ao excluir familiar:" + sqle);
        } finally {
            //ConnectionBancoFactory.fecharConexao(conn, ps);
        }
    }

    
}
