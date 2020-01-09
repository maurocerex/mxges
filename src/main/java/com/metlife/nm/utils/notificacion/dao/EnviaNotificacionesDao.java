package com.metlife.nm.utils.notificacion.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.metlife.nm.notificaciones.vo.FrecuenciaVO;
import com.metlife.nm.utils.notificacion.vo.CabeceraVO;
import com.metlife.nm.utils.notificacion.vo.EnvioNotificacionVO;
import com.metlife.nm.utils.notificacion.vo.MensajeVO;
import com.metlife.nm.utils.notificacion.vo.MxgesEnvioNotificacionVo;
import com.metlife.nm.utils.notificacion.vo.NotificacionVO;
import com.metlife.nm.utils.notificacion.vo.PolizaCliente;
import com.metlife.nm.utils.notificacion.vo.VarProcVo;


public interface EnviaNotificacionesDao {

    List<NotificacionVO> getConfiguraciones(String diaDeHoy, int hora, int minuto);
    
    List<MxgesEnvioNotificacionVo> getMxgesEnvioNotificacion(StringBuffer qry, NotificacionVO notf);
    
	void updCabecera(int idProceso, String status);

	int getIdProcesoEnvioNuevo();

	int getRetroactividad();
	
	int getLimite();
	
	List<CabeceraVO> getCabeceras(String lob, int retroactividad, String proceso);

	void insNuevaCabecera(CabeceraVO cabecera, int idProcesoNuevo);

	List<MensajeVO> getMensajes(int idProcesoPadre, NotificacionVO conf, String proceso);

	String getColumnaFisica(String variable);
	
	String getMensajePlantilla(String variable);
	
	String getProcesoNomFile(String variable);

	String getValorVariable(int idRegistro, int idProceso, String columna);

	void insEnvioSMS(final MensajeVO mensaje, final int idEnvio, final String destino, String destinatario, String status, String error)  throws SQLException;
	
	void insEnvioEmail(final MensajeVO mensaje, final int idEnvio, final String destino, String cliente, String status, String error)  throws SQLException;

	int getIdEnvio();

	void updRegistro(int idProceso, int idRegistro, String string);
	
	void updRegistrosRenew(int idProceso, int idRegistro, String cveProcesoOrigen);

    void updEnvio(String enviado, String idEnvio, String error);
    
    void updItRegistros(String proceso);
    
    void procesaSinDatos(String tipoEnvio, int retroactividad);
    
	/**
	 * Determina si existe un proceso de envio de notificaciones ejecutandose
	 * actualmente en base a una bandera en la vista MXGES_V_METATABLAS1
	 * 
	 * @return True.- Si se esta ejecutando, de lo contrario False.
	 */
	boolean isConcurrrent();
	
	/**
	 * Actualiza la bandera de concurrencia para env�o de notificaciones.
	 * 
	 * @param estatus
	 *            S.- Enciende bandera N.- Apaga bandera
	 */
	public void updateFlagConcurrence(String estatus);
	
	/**
	 * Determina si se quitaran acentos de los mensajes enviados por SMS.
	 * 
	 * @return True.- Si se remueve acentos de lo contrario False.
	 */
	public boolean removerAcentos();

	
	public List<NotificacionVO> getNotificacionesActivas();
	
	public List<FrecuenciaVO> getFrecuenciasByIdNotificacion(NotificacionVO notificacion);
	
	public List<VarProcVo> getVarProc(NotificacionVO notificacion);
	
	public List<Map<String,Object>> getNotificacionesCobranzaAMail(int retroactividad, int limite);
	
	public List<Map<String,Object>> getNotificacionesCobranzaRMail(int retroactividad, int limite);
	
	public List<EnvioNotificacionVO> getNotificacionesCobranzaRMailNULL(int retroactividad);
	
	public List<EnvioNotificacionVO> getNotificacionesCobranzaAMailNULL(int retroactividad);
	
	public void updEnvioNotificaciones(final String registro, final String proceso, final String nomina, final String poliza);
	
	public void insInfoEnvios(final String email, final String subject, final String body, final String registro, final String proceso, final String V_COL_01, final String V_COL_04, final String V_DES_MENSAJE_ERROR);
	
	public void insInfoEnviosNull(final String email, final String subject, final String body, final String registro, final String proceso, final String V_COL_01, final String V_DES_MENSAJE_ERROR);
	
	public List<PolizaCliente> getPolizasClientesHUB(String date);
	
}
