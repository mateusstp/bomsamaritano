/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Relatorios;

import DAO.BancoDAO;
import DAO.BancoDAOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Mateus Santos
 */
public class Relatorios {

    private BancoDAO dao;
    private PreparedStatement ps;
    private ResultSet rs;
    private JRResultSetDataSource relatResult;
    private JasperPrint jpPrint;
    private JasperViewer jv;

    public Relatorios() {
        try {
            dao = new BancoDAO();
        } catch (BancoDAOException ex) {
            Logger.getLogger(Relatorios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gerarRalarotioPeriodo(String SQL) {
        
        try {
            //System.out.println(SQL);
            executaConsulta(SQL);
            relatResult = new JRResultSetDataSource(this.rs);
            jpPrint = JasperFillManager.fillReport("src/Relatorios/RelatorioPeriodo.jasper", new HashMap(), relatResult);
            jv = new  JasperViewer(jpPrint);
            jv.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(Relatorios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void executaConsulta(String SQL) {

        Connection conn = null;

        ResultSet rs = null;
        PreparedStatement ps = null;
        conn = dao.getConn();
        try {
            ps = conn.prepareStatement(SQL);
            this.rs = ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(BancoDAO.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    /**
     * @return the dao
     */
    public BancoDAO getDao() {
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    public void setDao(BancoDAO dao) {
        this.dao = dao;
    }

    /**
     * @return the ps
     */
    public PreparedStatement getPs() {
        return ps;
    }

    /**
     * @param ps the ps to set
     */
    public void setPs(PreparedStatement ps) {
        this.ps = ps;
    }

    /**
     * @return the rs
     */
    public ResultSet getRs() {
        return rs;
    }

    /**
     * @param rs the rs to set
     */
    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    /**
     * @return the relatResult
     */
    public JRResultSetDataSource getRelatResult() {
        return relatResult;
    }

    /**
     * @param relatResult the relatResult to set
     */
    public void setRelatResult(JRResultSetDataSource relatResult) {
        this.relatResult = relatResult;
    }

    /**
     * @return the jpPrint
     */
    public JasperPrint getJpPrint() {
        return jpPrint;
    }

    /**
     * @param jpPrint the jpPrint to set
     */
    public void setJpPrint(JasperPrint jpPrint) {
        this.jpPrint = jpPrint;
    }

    /**
     * @return the jv
     */
    public JasperViewer getJv() {
        return jv;
    }

    /**
     * @param jv the jv to set
     */
    public void setJv(JasperViewer jv) {
        this.jv = jv;
    }
}
