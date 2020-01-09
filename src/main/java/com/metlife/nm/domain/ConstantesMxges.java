package com.metlife.nm.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ConstantesMxges {

    /**
     * No es la mejor solucion, pero si mas rapida que usar una internacionalizacion
     */
    public static String[] MESES = new String[12];

    static {
        MESES[0] = "Enero";
        MESES[1] = "Febrero";
        MESES[2] = "Marzo";
        MESES[3] = "Abril";
        MESES[4] = "Mayo";
        MESES[5] = "Junio";
        MESES[6] = "Julio";
        MESES[7] = "Agosto";
        MESES[8] = "Septiembre";
        MESES[9] = "Octubre";
        MESES[10] = "Noviembre";
        MESES[11] = "Diciembre";

    }
    
    public static final int SUN = 1;
    public static final int MON = 2;
    public static final int TUE = 3;
    public static final int WED = 4;
    public static final int THU = 5;
    public static final int FRI = 6;
    public static final int SAT = 7;
    
    public static final String SUNDAY = "SUN";
    public static final String MONDAY = "MON";
    public static final String TUESDAY = "TUE";
    public static final String WEDNESDAY = "WED";
    public static final String THURSDAY = "THU";
    public static final String FRIDAY = "FRI";
    public static final String SATURDAY = "SAT";

    public static final String USER = "USER";
    public static final String BASESID = "BASESID";
    public static final String SI = "S";
    public static final String NO = "N";
    public static final String EMAIL = "EM";
    public static final String SMS = "SM";
    
    public static final String AGENTE = "AG";
    public static final String PROMOTOR = "PR";
    public static final String CLIENTE = "CL";
    public static final String AMBOS = "AM";
    
    public static final String ENVIADO = "EN";
    public static final String ERROR = "ER";
    public static final String FALTA_DATOS_CONTACTO = "FC";
    
    public static final String COBRANZA = "12";
    public static final String PAGO_DIRECTO = "02";
    public static final String DxN = "01";
    public static final String REG_BASE = "1";
    public static final String REG_EVENTUAL = "2";
    
    public static final String PROC_COBRBANC = "PROC_COBRBANC";
    
    public static String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
	public static DateFormat FORMAT_EXE_DATE = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);

	public static String TIME_FORMAT_HH_mm_ss = "HH:mm:ss";
	public static DateFormat FORMAT_EXE_TIME = new SimpleDateFormat(TIME_FORMAT_HH_mm_ss);
	
	public static final String FINALIZADO = "F";
	public static final String INICIADO = "I";
	
	/**
	 * Constante para encender alguna propiedad de sistema.
	 */
	public static final String TURN_ON = "S";
	/**
	 * Constante para apagar alguna propiedad de sistema.
	 */
	public static final String TURN_OFF = "N";
	
	/**
	 * Constante que indica que un SMS fue enviado de manera exitosa.
	 */
	public static final String SMS_ENVIADO = "3";
	
	/**
	 * Constantes para identificar los tipo de reportes 
	 */
	public static final String TIPO_REPORTE_PENDIENTES = "01";
	public static final String TIPO_REPORTE_CIFRAS_CONTROL = "02";
	public static final String TIPO_REPORTE_PENDIENTES_DESC = "Notificaciones Pendientes";
	public static final String TIPO_REPORTE_CIFRAS_CONTROL_DESC ="Cifras de Control";
	// Se agregan nuevas contastantes para la mejora 2085269
	public static final String TIPO_REPORTE_SMS = "03";
	public static final String TIPO_REPORTE_SMS_DESC = "Reporte de SMS";
	
	/**
	 * Constantes utilizadas en el flujo de Cancelaciones
	 */
	public static final String PARAM_KEY_GENERIC = "01";
	public static final String PARAM_VALUE_GENERIC = "No se encontraron resultados";
	public static final String PARAM_TIPO_PROCESO_EXCLU = "1";
	public static final String PARAM_TIPO_PROCESO_CANCEL = "2";
	public static final String PARAM_TIPO_PROCESO_NOTIF = "3";
	public static final String PARAM_TIPO_OPER_ALTA = "1";
	public static final String PARAM_TIPO_OPER_BAJA = "2";
	public static final String PARAM_TIPO_OPER_MODIF = "3";

}
