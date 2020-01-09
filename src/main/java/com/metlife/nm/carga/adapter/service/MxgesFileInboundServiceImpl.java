package com.metlife.nm.carga.adapter.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.metlife.nm.domain.BeanNames;
import com.metlife.nm.domain.MxgesProperties;
import com.metlife.nm.notificaciones.dao.NotificacionesDaoImpl;

public class MxgesFileInboundServiceImpl implements MxgesFileInboundService {

	private static final Logger log = Logger.getLogger(NotificacionesDaoImpl.class);

	private String path;

	@Resource(name = BeanNames.MxgesProperties)
	private MxgesProperties mxgesProperties;

	public boolean checkChange(File file) {
		return false;
	}

	@SuppressWarnings("unchecked")
	public void readDocument() {

		try {
			/*
			 * File file = new File(path); List<String> lines = FileUtils.readLines(file);
			 * //Collections.sort(lines); for (String line : lines) {
			 * System.out.println("line:" + line); }
			 */
			// Aquí la carpeta que queremos explorar
			// String path = "/datos/sigah/sms/prov/respaldos";

			String javaExec = "java -jar ";
			String jarExec = "cargaNotif.jar ";
			String jarProperties = "cargaNotif.properties ";
			StringBuffer cadExecution = new StringBuffer();
			StringBuffer cadExecutionAux = new StringBuffer();
			cadExecution.append(javaExec).append(mxgesProperties.getItDirExec()).append(jarExec)
					.append(mxgesProperties.getItDirExec()).append(jarProperties).append(mxgesProperties.getItDirExec())
					.append(mxgesProperties.getItDirInput()).append(" ").append(mxgesProperties.getItDirExec())
					.append(mxgesProperties.getItDirInput()).append(mxgesProperties.getItDirInputRespaldos())
					.append(" ").append(mxgesProperties.getItDirExec()).append(mxgesProperties.getItDirInput())
					.append(mxgesProperties.getItDirInputLogs()).append(" ");
			String files;
			String val = "0";
			StringBuilder content = new StringBuilder();
			BufferedReader buff = null;

			cadExecutionAux.append(cadExecution.toString());
			File folder = new File(mxgesProperties.getItDirExec() + mxgesProperties.getItDirInput());
			File[] listOfFiles = folder.listFiles();
			log.debug("Ejecucion JAR Inicial  ------------->  :    " + cadExecution.toString());
			if (listOfFiles.length > 0) {
				log.debug("INICIA consulta inicial DE PERMISOS ARCHIVOS ****************************************");
				for (int i = 0; i < listOfFiles.length; i++) {
					if (listOfFiles[i].isFile()) {
						files = listOfFiles[i].getName();
						if (files.endsWith(".txt") || files.endsWith(".TXT")) {
							
							log.debug("NOMBRE ARCHIVO ------->   :   " + files);
							log.debug("DIRECTORIO / ARCHIVO ------->   :   " + mxgesProperties.getItDirExec() + mxgesProperties.getItDirInput() + files);
							if (listOfFiles[i].canRead()){
								log.debug(files + " tiene permiso de Lectura R");
							}
							if (listOfFiles[i].canWrite()){
								log.debug(files + " tiene permiso de Escritura W");
							}
							if (listOfFiles[i].canExecute()){
								log.debug(files + " tiene permiso de Ejecucion X");
							}
							/*Process procX = null;
							log.debug("Se INICIA ejecucion sentencia --->   :  chmod 777 " + mxgesProperties.getItDirExec() + mxgesProperties.getItDirInput() + files);
							procX = Runtime.getRuntime().exec("chmod 777 " + mxgesProperties.getItDirExec() + mxgesProperties.getItDirInput() + files);
							log.debug("Se TERMINA ejecucion sentencia --->   :  chmod 777 " + mxgesProperties.getItDirExec() + mxgesProperties.getItDirInput() + files);
							
							listOfFiles[i].setWritable(true, false);
							listOfFiles[i].setReadable(true, false);
							listOfFiles[i].setExecutable(true, false);
							log.debug("Se CAMBIARON PERMISOS de ARCHIVO rwx");
							procX.destroy();
							*/
						}
							
					}
				}
				log.debug("TERMINA consulta inicial DE PERMISOS ARCHIVOS ****************************************");
				Process procX = null;
				log.debug("Se INICIA ejecucion sentencia --->   :  chmod 777 *.txt");
				procX = Runtime.getRuntime().exec("chmod 777 *.txt");
				procX.destroy();
				log.debug("Se TERMINA ejecucion sentencia --->   :  chmod 777 *.txt");
				log.debug("INICIA consulta FINAL DE PERMISOS ARCHIVOS ******************************************");
				for (int i = 0; i < listOfFiles.length; i++) {
					if (listOfFiles[i].isFile()) {
						files = listOfFiles[i].getName();
						if (files.endsWith(".txt") || files.endsWith(".TXT")) {
							
							log.debug("NOMBRE ARCHIVO ------->   :   " + files);
							log.debug("DIRECTORIO / ARCHIVO ------->   :   " + mxgesProperties.getItDirExec() + mxgesProperties.getItDirInput() + files);
							/*Process procX = null;
							log.debug("Se INICIA ejecucion sentencia --->   :  chmod 777 " + mxgesProperties.getItDirExec() + mxgesProperties.getItDirInput() + files);
							procX = Runtime.getRuntime().exec("chmod 777 " + mxgesProperties.getItDirExec() + mxgesProperties.getItDirInput() + files);
							log.debug("Se TERMINA ejecucion sentencia --->   :  chmod 777 " + mxgesProperties.getItDirExec() + mxgesProperties.getItDirInput() + files);
							
							listOfFiles[i].setWritable(true, false);
							listOfFiles[i].setReadable(true, false);
							listOfFiles[i].setExecutable(true, false);
							log.debug("Se CAMBIARON PERMISOS de ARCHIVO rwx");
							procX.destroy();
							*/
							if (listOfFiles[i].canRead()){
								log.debug(files + " tiene permiso de Lectura R");
							}
							if (listOfFiles[i].canWrite()){
								log.debug(files + " tiene permiso de Escritura W");
							}
							if (listOfFiles[i].canExecute()){
								log.debug(files + " tiene permiso de Ejecucion X");
							}
							
						}
					}
				}
				log.debug("TERMINA consulta FINAL DE PERMISOS ARCHIVOS *****************************************");
				log.debug("SLEEEEEPING START");
				Thread.sleep(5000);
				log.debug("SLEEEEEPING END");
				for (int i = 0; i < listOfFiles.length; i++) {
					if (listOfFiles[i].isFile()) {
						files = listOfFiles[i].getName();
						if (files.endsWith(".txt") || files.endsWith(".TXT")) {
							try {// Ejecutar JAR desde java
								log.debug("INICIA EJECUCION JAR ****************************************");
								log.debug("NOMBRE ARCHIVO ------->   :   " + files);
								log.debug("DIRECTORIO / ARCHIVO ------->   :   " + mxgesProperties.getItDirExec() + mxgesProperties.getItDirInput() + files);
								cadExecution.setLength(0);
								cadExecution.append(cadExecutionAux.toString());
								cadExecution.append(files);
								log.debug("Ejecucion JAR FINAL ------------->  :    " + cadExecution.toString());
								// Runtime.getRuntime().exec(cadExecution.toString());
								FileChannel channel = new RandomAccessFile(listOfFiles[i],"rw").getChannel();
								//FileChannel channel = new RandomAccessFile(listOfFiles[i], "r").getChannel();
								FileLock lock;
							    try {
							        lock = channel.tryLock();
							        
							        Process proc = Runtime.getRuntime().exec(cadExecution.toString());
									InputStream cm = proc.getInputStream();
									buff = new BufferedReader(new InputStreamReader(cm));

									String s = null;
									while ((s = buff.readLine()) != null) {
										s += "\n";
										content.append(s);
										// System.out.print(s);
									}
									cm = proc.getErrorStream();
									buff = new BufferedReader(new InputStreamReader(cm));
									while ((s = buff.readLine()) != null) {
										s += "\n";
										// System.out.print(s);
									}
									log.debug(content.toString());
									lock.release();
									channel.close();
									log.debug("Info: Resultado del aplicativo:  " + val);									
									cm.close();
									proc.destroy();	
									log.debug("FIN FILELOCK");
									
							      } catch (OverlappingFileLockException e) {
							    	  log.debug("Info: No se pueden leer los registros. El archivo se encuentra bloqueado");
							      }
							    log.debug("TERMINA EJECUCION JAR ****************************************");
								// Thread.sleep(60000);
								// $JAR_FILE $PROP_FILE $ORIG_DIR $OUT_DIR $LOG_DIR $NOMBRE_ARCHIVO
							} catch (Exception e) {
								log.debug("Error al procesar el directorio....");
								e.printStackTrace();
								log.debug(e.getMessage());
							} catch (Throwable trw) {
								log.debug("Trowable ->  Error al procesar el directorio....");
								trw.printStackTrace();
								log.debug(trw.getMessage());
							}
						}
					}
				}
			} else {
				log.debug("PROC_CARGA EJECUTADO, NO se ha detectado ningun archivo de carga.....");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void listFilesTxt() {

	}
}
