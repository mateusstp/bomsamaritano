package DAO;

import java.util.ArrayList;
import modelo.*;

public interface InterfaceBancoDAO {

    void atualizar(int codigoInterno)                     throws BancoDAOException;
    //void atualizar(Endereco endereco)                           throws BancoDAOException;
    //void atualizar(FamiliaSocial familiasocial)                 throws BancoDAOException;
    //void atualizar(Interno interno)                             throws BancoDAOException;
    //void atualizar(Organizacao organizacao)                     throws BancoDAOException;
    //void atualizar(Processo processo)                           throws BancoDAOException;
    //void atualizar(Profissao profissao)                         throws BancoDAOException;
    //void atualizar(Questionario questionario)                   throws BancoDAOException;
    //void atualizar(Relacionamento relacionamento)               throws BancoDAOException;
    //void atualizar(Saude saude)                                 throws BancoDAOException;
    //void atualizar(SituacaoEconomica situacaoeconomica)         throws BancoDAOException;
    //void atualizar(TituloEleitoral tituloeleitoral)             throws BancoDAOException;
    //void atualizar(TratamentosAnteriores tratamentosanteriores) throws BancoDAOException;

    void excluir(int codigoInterno) throws BancoDAOException;

    void salvar() throws BancoDAOException;
    
    Interno pesquisar() throws BancoDAOException;
    ArrayList<Interno> pesquisarTodos() throws BancoDAOException;
    //Interno pesquisarID(String ID) throws BancoDAOException;
    Interno pesquisarCPF(String CPF) throws BancoDAOException;
}
