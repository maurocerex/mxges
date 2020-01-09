package com.metlife.nm.utils.notificacion.service;

import java.util.List;

import com.metlife.nm.utils.notificacion.vo.MxgesEnvioNotificacionVo;

public interface CreateFileService {
	
	public void crearArchivoSmsCSV(String nameFile, String cabecero, List<MxgesEnvioNotificacionVo> cadenasNot) throws Exception;
	public void crearArchivoEmailCSV(String nameFile, String cabecero, List<MxgesEnvioNotificacionVo> cadenasNot) throws Exception;
	public void crearArchivoSmsDESC(String nameFile, String cabecero) throws Exception;
	public void crearArchivoEmailDESC(String nameFile, String plantilla) throws Exception;

}
