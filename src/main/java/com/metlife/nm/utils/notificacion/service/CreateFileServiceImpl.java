package com.metlife.nm.utils.notificacion.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.metlife.nm.domain.BeanNames;
import com.metlife.nm.domain.MxgesProperties;
import com.metlife.nm.utils.notificacion.dao.EnviaNotificacionesDao;
import com.metlife.nm.utils.notificacion.vo.MxgesEnvioNotificacionVo;

@Service("createFileService")
public class CreateFileServiceImpl implements CreateFileService

{

    private static final Logger log = Logger.getLogger(EnviaNotificacionServiceImpl.class);

	@Resource(name = BeanNames.MxgesProperties)
	private MxgesProperties mxgesProperties;
	
	@Resource(name = BeanNames.EnviaNotificacionDao)
    private EnviaNotificacionesDao dao;
	

	
	public synchronized void crearArchivoSmsDESC(String nameFile, String plantilla) {
		log.debug("***************************************************************************");
		log.debug("******************                                      *******************");
		log.debug("******************      INICIA crearArchivoSmsDESC      *******************");
		log.debug("******************                                      *******************");
		log.debug("***************************************************************************");
		final String NEXT_LINE = "\n";
		String nombreDeArchivo = mxgesProperties.getItDirExec() + mxgesProperties.getItDirSmsOutput() + nameFile;
		String extencion = ".desc";
		DateFormat formato = new SimpleDateFormat("yyyyMMdd_HHmm");
		Date fecha = new Date();		
		nombreDeArchivo=nombreDeArchivo+"_SMS_"+formato.format(fecha)+extencion;
		String nombreDeArchivoAux=nameFile+"_SMS_"+formato.format(fecha)+extencion;
		try {
			File fil = new File(nombreDeArchivo);
			log.debug(fil.getAbsolutePath());
			FileWriter fw = new FileWriter(nombreDeArchivo);
			synchronized (fw) {

			log.debug("Nombre archivo:  " + nombreDeArchivo);
			fw.append(mxgesProperties.getItsmsMensaje()+plantilla).append(NEXT_LINE);
			fw.append(mxgesProperties.getItsmsTipo()+"="+mxgesProperties.getItsmsValorTipo()).append(NEXT_LINE);
			log.debug("Archivo -----> FW  sin toString:  " + fw);
			fw.flush();
			
			}
			fw.close();
			
			
	        
			log.debug("***************************************************************************");
			log.debug("******************                                      *******************");
			log.debug("******************      TERMINA crearArchivoSmsDESC     *******************");
			log.debug("******************                                      *******************");
			log.debug("***************************************************************************");
			log.debug("");
			log.debug("");
		} catch (IOException e) {
			// Error al crear el archivo, por ejemplo, el archivo 
			// está actualmente abierto.
			e.printStackTrace();
		}
		
		SFTPUtils sftpUtils = new SFTPUtils();        
        //sftpUtils.setDataConn("10.77.212.58","22", "aq969301","M3tl1f396","/opt/MXGES/output/");
        sftpUtils.setDataConn(mxgesProperties.getItsftpHost(), mxgesProperties.getItsftpPort(),
        		mxgesProperties.getItsftpUser(), mxgesProperties.getItsftpPass(), mxgesProperties.getItsftpOutputdir());
        sftpUtils.sendFileToMFT(nombreDeArchivoAux,nombreDeArchivo); 

    }
	
	
	public synchronized void crearArchivoEmailDESC(String nameFile, String plantilla) {
		log.debug("***************************************************************************");
		log.debug("******************                                      *******************");
		log.debug("******************     INICIA crearArchivoEmailDESC     *******************");
		log.debug("******************                                      *******************");
		log.debug("***************************************************************************");
		final String NEXT_LINE = "\n";
		String nombreDeArchivo = mxgesProperties.getItDirExec() + mxgesProperties.getItDirEmailOutput() + nameFile;
		String extencion = ".desc";
		DateFormat formato = new SimpleDateFormat("yyyyMMdd_HHmm");
		Date fecha = new Date();
		nombreDeArchivo=nombreDeArchivo+"_EM_"+formato.format(fecha)+extencion;
		String nombreDeArchivoAux=nameFile+"_EM_"+formato.format(fecha)+extencion;
		try {
			File fil = new File(nombreDeArchivo);
			System.out.println(fil.getAbsolutePath());
			FileWriter fw = new FileWriter(nombreDeArchivo);
			synchronized (fw) {
			log.debug("Nombre archivo:  " + nombreDeArchivo);
			fw.append(mxgesProperties.getItemailMensaje()+plantilla).append(NEXT_LINE);
			fw.append(mxgesProperties.getItemailTipo()+"="+mxgesProperties.getItemailValorTipo()).append(NEXT_LINE);
			log.debug("Archivo -----> FW  sin toString:  " + fw);
			fw.flush();
			}
			fw.close();
			
			
			
			log.debug("***************************************************************************");
			log.debug("******************                                      *******************");
			log.debug("******************     TERMINA crearArchivoEmailDESC    *******************");
			log.debug("******************                                      *******************");
			log.debug("***************************************************************************");
			log.debug("");
			log.debug("");
		} catch (IOException e) {
			// Error al crear el archivo, por ejemplo, el archivo 
			// está actualmente abierto.
			e.printStackTrace();
		}
		
		SFTPUtils sftpUtils = new SFTPUtils();
		//sftpUtils.setDataConn("10.77.212.58","22", "aq969301","M3tl1f396","/opt/MXGES/output/");
		sftpUtils.setDataConn(mxgesProperties.getItsftpHost(), mxgesProperties.getItsftpPort(),
				mxgesProperties.getItsftpUser(), mxgesProperties.getItsftpPass(), mxgesProperties.getItsftpOutputdir());
		sftpUtils.sendFileToMFT(nombreDeArchivoAux,nombreDeArchivo);  
        
	}
	
	   
	   public synchronized void crearArchivoSmsCSV(String nameFile, String cabecero,
			List<MxgesEnvioNotificacionVo> cadenasNot) throws Exception {
		    log.debug("***************************************************************************");
			log.debug("******************                                      *******************");
			log.debug("******************      INICIA crearArchivoSmsCSV       *******************");
			log.debug("******************                                      *******************");
			log.debug("***************************************************************************");
			final String NEXT_LINE = "\n";
			
			String nombreDeArchivo = mxgesProperties.getItDirExec() + mxgesProperties.getItDirSmsOutput() + nameFile;
			String extencion = ".csv";
			DateFormat formato = new SimpleDateFormat("yyyyMMdd_HHmm");
			Date fecha = new Date();
			nombreDeArchivo=nombreDeArchivo+"_SMS_"+formato.format(fecha)+extencion;
			String nombreDeArchivoAux=nameFile+"_SMS_"+formato.format(fecha)+extencion;
			try {
				File fil = new File(nombreDeArchivo);
				log.debug(fil.getAbsolutePath());
				FileWriter fw = new FileWriter(nombreDeArchivo);
				synchronized (fw) {
				
				log.debug("Nombre archivo:  " + nombreDeArchivo);
				fw.append(cabecero).append(NEXT_LINE);
				log.debug(cabecero);
				log.debug("Contenido FW ANTES del FOR ------>");
				log.debug(fw.toString());
				log.debug("Contenido FW ANTES del FOR ------>");
				for (MxgesEnvioNotificacionVo lineaFile : cadenasNot){
					fw.append(lineaFile.getRegistroArchivo()).append(NEXT_LINE);
					log.debug(lineaFile.getRegistroArchivo());
					log.debug("Contenido FW durante el FOR ------>");
					log.debug(fw.toString());
					log.debug("Contenido FW durante el FOR ------>");
				}
				log.debug("Contenido FW DESPUES del FOR ------>");
				log.debug(fw.toString());
				log.debug("Contenido FW DESPUES del FOR ------>");
				log.debug("Archivo -----> FW  sin toString:  " + fw);
				fw.flush();
				
				}
				fw.close();
				
				
		        
		        log.debug("***************************************************************************");
				log.debug("******************                                      *******************");
				log.debug("******************      TERMINA crearArchivoSmsCSV      *******************");
				log.debug("******************                                      *******************");
				log.debug("***************************************************************************");
				log.debug("");
				log.debug("");
			} catch (IOException e) {
				// Error al crear el archivo, por ejemplo, el archivo 
				// está actualmente abierto.
				e.printStackTrace();
			} 
			
			SFTPUtils sftpUtils = new SFTPUtils();	        
	        //sftpUtils.setDataConn("10.77.212.58","22", "aq969301","M3tl1f396","/opt/MXGES/output/");
	        sftpUtils.setDataConn(mxgesProperties.getItsftpHost(), mxgesProperties.getItsftpPort(),
	        		mxgesProperties.getItsftpUser(), mxgesProperties.getItsftpPass(), mxgesProperties.getItsftpOutputdir());
	        sftpUtils.sendFileToMFT(nombreDeArchivoAux,nombreDeArchivo);
	   }
	   
	   
	   
	   public synchronized void crearArchivoEmailCSV(String nameFile, String cabecero,
				List<MxgesEnvioNotificacionVo> cadenasNot) throws Exception {
		   log.debug("***************************************************************************");
		   log.debug("******************                                      *******************");
		   log.debug("******************      INICIA crearArchivoEmailCSV     *******************");
			log.debug("******************                                      *******************");
			log.debug("***************************************************************************");
			final String NEXT_LINE = "\n";
			
			String nombreDeArchivo = mxgesProperties.getItDirExec() + mxgesProperties.getItDirEmailOutput() + nameFile;
			String extencion = ".csv";
			DateFormat formato = new SimpleDateFormat("yyyyMMdd_HHmm");
			Date fecha = new Date();
			nombreDeArchivo=nombreDeArchivo+"_EM_"+formato.format(fecha)+extencion;
			String nombreDeArchivoAux=nameFile+"_EM_"+formato.format(fecha)+extencion;
			try {
				File fil = new File(nombreDeArchivo);
				log.debug(fil.getAbsolutePath());
				FileWriter fw = new FileWriter(nombreDeArchivo);
				synchronized (fw) {
				
				log.debug("Nombre archivo:  " + nombreDeArchivo);
				fw.append(cabecero).append(NEXT_LINE);
				log.debug(cabecero);
				log.debug("Contenido FW ANTES del FOR ------>");
				log.debug(fw.toString());
				log.debug("Contenido FW ANTES del FOR ------>");
				for (MxgesEnvioNotificacionVo lineaFile : cadenasNot){
					fw.append(lineaFile.getRegistroArchivo()).append(NEXT_LINE);
					log.debug(lineaFile.getRegistroArchivo());
					log.debug("Contenido FW durante el FOR ------>");
					log.debug(fw.toString());
					log.debug("Contenido FW durante el FOR ------>");
				}
				log.debug("Contenido FW DESPUES del FOR ------>");
				log.debug(fw.toString());
				log.debug("Contenido FW DESPUES del FOR ------>");
				log.debug("Archivo -----> FW  sin toString:  " + fw);
	
				fw.flush();
				
				}
				fw.close();
				
				
		        
		        log.debug("***************************************************************************");
				log.debug("******************                                      *******************");
				log.debug("******************      TERMINA crearArchivoEmailCSV    *******************");
				log.debug("******************                                      *******************");
				log.debug("***************************************************************************");
				log.debug("");
				log.debug("");
			} catch (IOException e) {
				e.printStackTrace();
			} 
			
			SFTPUtils sftpUtils = new SFTPUtils();		        
	        //sftpUtils.setDataConn("10.77.212.58","22", "aq969301","M3tl1f396","/opt/MXGES/output/");
	        sftpUtils.setDataConn(mxgesProperties.getItsftpHost(), mxgesProperties.getItsftpPort(),
	        		mxgesProperties.getItsftpUser(), mxgesProperties.getItsftpPass(), mxgesProperties.getItsftpOutputdir());
	        sftpUtils.sendFileToMFT(nombreDeArchivoAux,nombreDeArchivo);
	   }
	   
}