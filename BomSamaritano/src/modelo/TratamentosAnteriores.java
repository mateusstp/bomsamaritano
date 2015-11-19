package modelo;

import java.util.Date;

public class TratamentosAnteriores {
    private Date dataEntrada;
    private Date dataSaida;
    private String Clinica;
    private String MotivoSaida;

    public TratamentosAnteriores(){
     dataEntrada=null;
     dataSaida=null;
     Clinica="";
     MotivoSaida="";
     

    }

    /**
     * @return the dataEntrada
     */
    public Date getDataEntrada() {
        return dataEntrada;
    }

    /**
     * @param dataEntrada the dataEntrada to set
     */
    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
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
     * @return the Clinica
     */
    public String getClinica() {
        return Clinica;
    }

    /**
     * @param Clinica the Clinica to set
     */
    public void setClinica(String Clinica) {
        this.Clinica = Clinica;
    }

    /**
     * @return the MotivoSaida
     */
    public String getMotivoSaida() {
        return MotivoSaida;
    }

    /**
     * @param MotivoSaida the MotivoSaida to set
     */
    public void setMotivoSaida(String MotivoSaida) {
        this.MotivoSaida = MotivoSaida;
    }

}
