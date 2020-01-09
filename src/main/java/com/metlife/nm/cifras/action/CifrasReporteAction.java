package com.metlife.nm.cifras.action;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;

import au.com.bytecode.opencsv.CSVWriter;

import com.metlife.nm.cifras.service.CifrasService;
import com.metlife.nm.domain.BeanNames;
import com.metlife.nm.domain.ConstantesMxges;
import com.opensymphony.xwork2.ActionSupport;

@Results({ @Result(name = "descargar", type = "stream", params = { "contentType", "application/csv", "inputName", "inputStream", "contentDisposition", "filename=${fileName}" }), @Result(name = "ventanita", location = "cifras/descargar-reporte.jsp") })
public class CifrasReporteAction extends ActionSupport implements SessionAware {

    /**
	 * 
	 */
    private static final long serialVersionUID = 7413381213770746106L;

    private static final Logger log = Logger.getLogger(CifrasReporteAction.class);

    protected Map<String, Object> session;
    private String nombreArchivo;
    private InputStream inputStream;
    private String fileName;
    private int contentLength;
    private String proceso;
    private String medio;
    private String fecInicio;
    private String fecFin;
    private String tipoReport;

    private static String[] headerRow = { "DES_LINEA_NEGOCIO", "ID_POLIZA", "NOMBRE_CLIENTE", "RFC", "HOMOCLAVE", 
    	"CURP", "ID_NOMINAL", "TEL_CLIENTE", "TEL_CLIENTE_PARTICULAR", "TEL_CLIENTE_OFICINA", "EMAIL_CLIENTE",
    	"EMAIL_CLIENTE2", "EMAIL_CLIENTE3", "REF_BANCARIA", "CVE_PROMOTORIA", "CVE_APODERADO", "ID_RETENEDOR", "ID_UNIDAD_PAGO",
    	"ID_RETENEDOR_CT", "CENTRO_TRABAJO", "TIPO_MENSAJE", "DESTINATARIO", "ENVIADO_A", "FECHA_DIA_COBRO", "DES_PROCESO", "ID_DES_MSJ",
    	"ESTATUS_ENVIO","DETALLE" };
    
    private static String[] headerRowPrevio = {"CVE_PROCESO", "ID_REGISTRO", "ID_PROCESO", "CVE_RESP_BANCARIA", "CREADO_POR", 
    	"FEC_CREACION", "ACTUALIZADO_POR", "FEC_ACTUALIZACION", "CVE_MOTIVO_RECHAZO", "SW_REINTENTABLE", "DIAS_PREV_CANCELACION",
    	"MOTIVO_CANCELACION", "BANCO_CONVENIO", "CONDUCTO_COBRO", "ID_REGIMEN_LABORAL", "ID_PROCESO_ORIGEN", "CVE_SIST_ORIGEN", "CVE_ESTATUS",
    	"CVE_MEDIO_ENVIO", "CVE_PROCESO_ORIGEN", "FEC_PROBABLE_CANCELACION", "CVE_LINEA_NEGOCIO", "ID_POLIZA", "CVE_PROMOTORIA", "EMAIL_PROMOTORIA", "CVE_APODERADO",
    	"NOMBRE_APODERADO","TEL_MOVIL_APODERADO","ID_RETENEDOR","ID_UNIDAD_PAGO","ID_RETENEDOR_CT","ID_UNIDAD_PAGO_CT","NOMBRE_CLIENTE","APELLIDO_PATERNO_CLIENTE",
    	"APELLIDO_MATERNO_CLIENTE","TEL_MOVIL_CLIENTE","EMAIL_CLIENTE","RFC","HOMOCLAVE","CURP","ID_NOMINAL","REF_BANCARIA","MONTO_RECIBO","NUM_RECIBOS_PENDIENTES",
    	"BANCO","COLUMN_1","COLUMN_2","COLUMN_3","COLUMN_4","COLUMN_6","COLUMN_7","COLUMN_8","COLUMN_9","COLUMN_10","COLUMN_11","COLUMN_12",
    	"COLUMN_13","COLUMN_14","COLUMN_15","CVE_PRODUCTO","ID_PROCESO_JOB","EMAIL_APODERADO","IMPORTE_01","FECHA_DIA_COBRO","IMPORTE_02",
    	"IMPORTE_03","IMPORTE_04","IMPORTE_05","DES_RESP_BANCARIA","DES_MOTIVO_RECHAZO","DES_REGIMEN_LABORAL","APELLIDO_PATERNO_APODERADO",
    	"APELLIDO_MATERNO_APODERADO","LADA_PAIS_APODERADO","LADA_PAIS_CLIENTE","DES_MENSAJE_ERROR" };
    
    private static String[] headerRowSMS = {"TELEFONO","NOMINA","POLIZA","IMPORTE"};

    @Resource(name = BeanNames.CifrasService)
    private CifrasService service;

    public String input() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("input...");
        }
        throw new RuntimeException("El metodo input no esta soportado.");

    }

    public String execute() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("execute...");
        }
        throw new RuntimeException("Este metodo execute no esta soportado.");
    }

    public String ventanita() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("ventanita...");
            log.debug("proceso: " + proceso);
            log.debug("medio: " + medio);
            log.debug("fecInicio: " + fecInicio);
            log.debug("fecFin: " + fecFin);
            log.debug("tipo reporte: " + tipoReport);
        }
        return "ventanita";
    }

    public String descargar() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("descargar...");
            log.debug("proceso: " + proceso);
            log.debug("medio: " + medio);
            log.debug("fecInicio: " + fecInicio);
            log.debug("fecFin" + fecFin);
            log.debug("tipo de reporte: " + tipoReport);
        }

        try {
        	CSVWriter writer = null;
            FileWriter fWriter = null;
            String nombreArchivo = " ";
            
            if(tipoReport.equalsIgnoreCase(ConstantesMxges.TIPO_REPORTE_CIFRAS_CONTROL)){
            	nombreArchivo="CifrasControl.csv";
            }else if(tipoReport.equalsIgnoreCase(ConstantesMxges.TIPO_REPORTE_SMS)){
            	if(proceso.equals("PROC_REPORTE_NR")){
            		nombreArchivo="ReporteNR.csv";
            	}else{
            		nombreArchivo="ReporteCA.csv";
            	}
            }else{
            	nombreArchivo="PENDIENTES.csv";
            }
            File file = new File(nombreArchivo);
            
            try {
                fWriter = new FileWriter(file);
                writer = new CSVWriter(fWriter, '|', '\0');
                
                List<Map<String,Object>> allLines = service.getReporte(proceso, medio, fecInicio, fecFin, tipoReport); 
                
                if(tipoReport.equalsIgnoreCase(ConstantesMxges.TIPO_REPORTE_CIFRAS_CONTROL)){
                	writer.writeNext(headerRow);
                }else if(tipoReport.equalsIgnoreCase(ConstantesMxges.TIPO_REPORTE_PENDIENTES)){
                	writer.writeNext(headerRowPrevio);
                }else if(tipoReport.equalsIgnoreCase(ConstantesMxges.TIPO_REPORTE_SMS)){
                	
                	writer = new CSVWriter(fWriter, ',', '\0','#');
                	writer.writeNext(headerRowSMS);
                }
                
                for(int i = 0; i<allLines.size();i++){
                	writer.writeNext(allLines.get(i).values().toString().replaceAll(",  ", ",").replaceAll(", ", ",").replace("[", "").replace("]", "").replaceAll("null", "").split(","));
                }
                //writer.writeAll(allLines);
            } catch (IOException e) {
                log.error("descargar", e);
                throw new RuntimeException("Existio un problema al generar el archivo: " + e.getMessage());
            } finally {
                if (fWriter != null) {
                    try {
                        fWriter.close();
                    } catch (IOException ignorame) {
                    }
                }
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException ignorame) {

                    }
                }
            }

            contentLength = (int) file.length();
            inputStream = FileUtils.openInputStream(file);
            fileName = nombreArchivo;
        } catch (Exception e) {
            log.error("descargar", e);
            throw e;
        } finally {
        	log.debug("Exit descargar");
        }

        return "descargar";
    }

    public final void setSession(Map<String, Object> session) {
        this.session = session;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public Map<String, Object> getSession() {
        return session;
    }

    public String getProceso() {
        return proceso;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    public String getMedio() {
        return medio;
    }

    public void setMedio(String medio) {
        this.medio = medio;
    }

    public final void setFecInicio(String fecInicio) {
        this.fecInicio = fecInicio;
    }

    public final void setFecFin(String fecFin) {
        this.fecFin = fecFin;
    }

    public final String getFecInicio() {
        return fecInicio;
    }

    public final String getFecFin() {
        return fecFin;
    }

	public String getTipoReport() {
		return tipoReport;
	}

	public void setTipoReport(String tipoReport) {
		this.tipoReport = tipoReport;
	}

}
