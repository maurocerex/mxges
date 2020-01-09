package com.metlife.nm.utils.calidad.service;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPCellEvent;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPTableEvent;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import com.metlife.nm.domain.AppContext;
import com.metlife.nm.domain.BeanNames;
import com.metlife.nm.domain.ConstantesMxges;
import com.metlife.nm.domain.MxgesProperties;
import com.metlife.nm.notificaciones.vo.DiaVO;
import com.metlife.nm.reportes.dao.ReportesDao;
import com.metlife.nm.reportes.service.ReportesService;
import com.metlife.nm.reportes.vo.ReporteVO;
import com.metlife.nm.utils.calidad.dao.EnviaReporteCalidadDao;
import com.metlife.nm.utils.calidad.vo.DestinatarioVO;
import com.metlife.nm.utils.mail.service.EnviaMailService;
import com.metlife.nm.utils.notificacion.vo.MailVO;

@Service(value = BeanNames.EnviaReporteCalidadService)
public class EnviaReporteCalidadServiceImpl implements PdfPCellEvent, PdfPTableEvent, ApplicationContextAware {

    private static final Logger log = Logger.getLogger(EnviaReporteCalidadServiceImpl.class);
    
    public static SimpleDateFormat SDF_DMY = null;
    static {
        SDF_DMY = new SimpleDateFormat("ddMMyyyy");
        SDF_DMY.setLenient(false);
    }
    @Resource(name = BeanNames.EnviaReporteCalidadDao)
    private EnviaReporteCalidadDao dao;

    @Resource(name = BeanNames.ReportesDao)
    private ReportesDao reporteDao;

    @Resource(name = BeanNames.MxgesProperties)
    private MxgesProperties globalProperties;

    @Resource(name = BeanNames.EnviaMailService)
    private EnviaMailService enviaMailService;

    @Resource(name = BeanNames.ReportesService)
    private ReportesService service;

    private static String[] headerRow = { "No. P�liza", "Nombre Cliente", "Tel�fono Cliente", "Email", "Ref. Bancaria", "Agente Vendedor", "Conducto de Cobro", "Tipo Mensaje", "Proceso", "Status Env�o", "Detalle" };

    private static String[] rowValues = { "poliza", "nombre", "telefono", "mail", "refBancaria", "agenteVendedor", "conCobro", "tipoMensaje", "proceso", "estatus", "detalle" };

    public final Integer getUltimoDia() {

        Calendar calendar = Calendar.getInstance();
        Integer maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        // System.out.println("Max Day: " + maxDay);

        return maxDay;

    }

    public void enviaReporteCalidad() {

        if (log.isDebugEnabled()) {
            log.debug("EnviaReporteCalidad...");
        }

        ArrayList<DiaVO> dias = (ArrayList<DiaVO>) service.getDiasReporteCalidad();
        boolean envia = false;

        Calendar currentDate = Calendar.getInstance();
        Integer diaDeHoy = currentDate.get(Calendar.DAY_OF_MONTH);
        String fin = getClaveDia(currentDate.get(Calendar.DAY_OF_WEEK));
        if (log.isDebugEnabled()) {
            log.debug("Hoy es :" + fin);
        }
        for (DiaVO dia : dias) {
            if (!fin.equals(ConstantesMxges.SATURDAY) && !fin.equals(ConstantesMxges.SUNDAY)) {
                if (fin.equals(ConstantesMxges.MONDAY)) {
                    if (log.isDebugEnabled()) {
                        log.debug("Hoy es Lunes");
                    }
                    Calendar date = Calendar.getInstance();
                    date.set(Calendar.DAY_OF_MONTH, diaDeHoy - 1);
                    Integer domingo = date.get(Calendar.DAY_OF_MONTH);
                    date.set(Calendar.DAY_OF_MONTH, diaDeHoy - 2);
                    Integer sabado = date.get(Calendar.DAY_OF_MONTH);
                    for (DiaVO tmp : dias) {
                    	if (tmp.getDiaEnvio().equals(domingo.toString()) || tmp.getDiaEnvio().equals(sabado.toString())) {
                            if (log.isDebugEnabled()) {
                                log.debug("tenia que enviar el sabado " + tmp.getDiaEnvio().equals(sabado.toString()));
                                log.debug("tenia que enviar el domingo " + tmp.getDiaEnvio().equals(domingo.toString()));
                            }
                            envia = true;
                            break;
                        }else{
                            if (getUltimoDia() <= sabado || getUltimoDia() <= domingo) {
                                if(log.isDebugEnabled()){
                                    log.debug("Ultimo dia del mes en curso ->"+getUltimoDia());
                                    log.debug("El dia de hoy es ->"+diaDeHoy);
                                }
                                envia = true;
                                break;
                            }
                        }
                    }
                }
                if (dia.getDiaEnvio().equals(diaDeHoy.toString())) {
                    if (log.isDebugEnabled()) {
                        log.debug("X dia de la semana");
                    }
                    envia = true;
                    break;
                }
                /*Integer ultimoDia = new Integer(0);
                try {
                    ultimoDia = new Integer(dia.getDiaEnvio());
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Dia Invalido");
                }*/
                if (getUltimoDia() <= diaDeHoy) {
                    if(log.isDebugEnabled()){
                        log.debug("Ultimo dia del mes en curso ->"+getUltimoDia());
                        log.debug("El dia de hoy es ->"+diaDeHoy);
                    }
                    envia = true;
                    break;
                }
            }
            if (envia) {
                break;
            }
        }

        if (envia) {
            String dest = getDestinatario().getDestinatario();
            ArrayList<DestinatarioVO> destinatarios = new ArrayList<DestinatarioVO>();
            if (dest.equals(ConstantesMxges.AMBOS)) {
                if (log.isDebugEnabled()) {
                    log.debug("-------------------AMBOS---------------------");
                }
                destinatarios = (ArrayList<DestinatarioVO>) dao.getPromotorias();
                getRegistros(false, true, destinatarios);

                destinatarios = (ArrayList<DestinatarioVO>) dao.getAgentes();
                getRegistros(true, false, destinatarios);

            } else if (dest.equals(ConstantesMxges.PROMOTOR)) {
                destinatarios.addAll(dao.getPromotorias());
                getRegistros(false, true, destinatarios);
            } else if (dest.equals(ConstantesMxges.AGENTE)) {
                destinatarios.addAll(dao.getAgentes());
                getRegistros(true, false, destinatarios);
            }

        } else {
            if (log.isDebugEnabled()) {
                log.debug("No envio nada hoy!!");
            }
        }

    }

    private void getRegistros(boolean agente, boolean promotor, List<DestinatarioVO> destinatarios) {

        ArrayList<HashMap<String, String>> registros = new ArrayList<HashMap<String, String>>();
        for (DestinatarioVO vo : destinatarios) {
            registros = new ArrayList<HashMap<String, String>>();
            MailVO mailAgente = new MailVO();
            MailVO mailPromotor = new MailVO();
            String urlFile = "";
            if (agente) {

                registros.addAll(dao.getRegistrosAgente(vo.getAgente(), vo.getPromotoria()));
                if (log.isDebugEnabled()) {
                    log.debug("------------------------AGENTES----------------------- " + registros.size());
                }
                mailAgente.setEmailTo(vo.getMailAgente());
                urlFile = makePdf(registros, agente, promotor);
                enviaMailService.sendMail(urlFile, mailAgente);

            }
            if (promotor) {

                registros.addAll(dao.getRegistrosPromotor(vo.getPromotoria()));
                if (log.isDebugEnabled()) {
                    log.debug("------------------------PROMOTORES----------------------- " + registros.size());
                }
                mailPromotor.setEmailTo(vo.getMailPromotoria());
                urlFile = makePdf(registros, agente, promotor);
                enviaMailService.sendMail(urlFile, mailPromotor);
            }

        }
    }

    private ReporteVO getDestinatario() {
        ReporteVO vo = reporteDao.getConfiguracion();
        return reporteDao.getDestinatario(vo);
    }

    private String makePdf(List<HashMap<String, String>> datos, boolean agente, boolean promotor) {
        Calendar currentDate = Calendar.getInstance();

        String nombreFile = null;

        File directorio = new File(dao.getDirectorio());
        // File directorio = new
        // File(globalProperties.getPathArchivosCalidad());

        if (datos.size() > 0) {
            if (agente) {
                nombreFile = datos.get(0).get("promotoria").toString() + "_" + datos.get(0).get("agenteVendedor").toString();
            } else {
                nombreFile = datos.get(0).get("promotoria").toString();
            }

            nombreFile = nombreFile + "_" + SDF_DMY.format(currentDate.getTime()) + ".pdf";

            String urlFile = directorio + "/" + nombreFile;

            nombreFile = urlFile;
            if (!directorio.isDirectory()) {
                directorio.mkdirs();
            }
            try {
                HashMap<String, String> filas = null;
                Document document = new Document();
                OutputStream file = new FileOutputStream(new File(urlFile));
                PdfWriter writer = PdfWriter.getInstance(document, file);
                writer.setHeader(new HeaderFooter(new Phrase(""), new Phrase("")));

                Paragraph p = new Paragraph();
                document.open();

                document.setMargins(10f, 10f, 10f, 10f);
                // document.setMarginMirroring(true);
                // document.setMarginMirroringTopBottom(true);

                p = new Paragraph("REPORTE DE CALIDAD DE DATOS", FontFactory.getFont("arial", 10, Font.BOLD, new Color(0x000000)));
                p.setAlignment(Element.ALIGN_LEFT);
                document.add(p);

                p = new Paragraph("Promotor�a: " + datos.get(0).get("promotoria"), FontFactory.getFont("arial", 8, Font.BOLD, new Color(0x000000)));
                p.setAlignment(Element.ALIGN_LEFT);
                document.add(p);

                p = new Paragraph("Retenedor Centro de Trabajo: " + datos.get(0).get("retenedor"), FontFactory.getFont("arial", 8, Font.BOLD, new Color(0x000000)));
                p.setAlignment(Element.ALIGN_LEFT);
                document.add(p);

                p = new Paragraph("UP Centro de Trabajo: " + datos.get(0).get("unidadPago"), FontFactory.getFont("arial", 8, Font.BOLD, new Color(0x000000)));
                p.setAlignment(Element.ALIGN_LEFT);
                document.add(p);

                document.add(new Paragraph(" "));
                float[] colsWidth = { 50f, 50f, 50f, 50f, 50f, 50f, 50f, 50f, 50f, 50f, 50f };
                PdfPCell celda = null;
                PdfPTable table = null;
                table = new PdfPTable(colsWidth);
                table.setTableEvent(new EnviaReporteCalidadServiceImpl());
                table.getDefaultCell().setCellEvent(new EnviaReporteCalidadServiceImpl());
                table.setTotalWidth(colsWidth);
                table.setLockedWidth(true);

                table.setHorizontalAlignment(Element.ALIGN_CENTER);
                // table.getDefaultCell().setFixedHeight(70);

                ApplicationContext ctx = AppContext.getApplicationContext();
                org.springframework.core.io.Resource template = ctx.getResource("/images/metlife.png");

                try {// Agregar imagen
                    String rutaRelativaApp = template.getURL().toString();

                    Image imghead = Image.getInstance(rutaRelativaApp);
                    imghead.scaleToFit(150, 150);
                    imghead.setAbsolutePosition(0, Chunk.ALIGN_RIGHT);
                    imghead.setAlignment(Chunk.ALIGN_RIGHT);
                    PdfContentByte byte1 = writer.getDirectContent();
                    PdfTemplate tp1 = byte1.createTemplate(600, 150);
                    tp1.addImage(imghead);
                    byte1.addTemplate(tp1, 400, 780);
                    /*
                     * Phrase headPhraseImg = new Phrase(byte1 + "",
                     * FontFactory.getFont(FontFactory.TIMES_ROMAN, 7,
                     * Font.NORMAL)); HeaderFooter headerFooter = new
                     * HeaderFooter(new Phrase(headPhraseImg), true);
                     * document.setHeader(headerFooter);
                     */
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // crear cabeceras del reporte
                for (int i = 0; i < headerRow.length; i++) {

                    celda = new PdfPCell(new Paragraph(1, headerRow[i], FontFactory.getFont("arial", 6, Font.BOLD, new Color(0xFFFFFF))));
                    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                    celda.setBackgroundColor(new Color(79, 129, 189));
                    celda.setPaddingLeft(0.5f);
                    celda.setPaddingRight(0.5f);
                    table.addCell(celda);

                }

                // table.getRow(0);
                table.setHeaderRows(1);
                // table.setFooterRows(0);

                // fi crear cabeceras del reporte
                // crear renglones con contenidos
                for (int i = 0; i < datos.size(); i++) {

                    filas = new HashMap<String, String>();
                    filas = (HashMap<String, String>) datos.get(i);
                    for (int j = 0; j < headerRow.length; j++) {
                        celda = new PdfPCell(new Paragraph(5, filas.get(rowValues[j]).toString(), FontFactory.getFont("arial", 6, Font.NORMAL, new Color(0x000000))));
                        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                        celda.setBorderColor(new Color(0x000000));
                        celda.setBorder(PdfPCell.LEFT);
                        celda.setBorder(PdfPCell.RIGHT);
                        celda.setBorderWidthRight(0.5f);
                        celda.setBorderWidthTop(0.5f);
                        celda.setBorderWidthLeft(0.5f);
                        celda.setBorderWidthBottom(0.5f);
                        celda.setBorderColorRight(new Color(0x000000));
                        celda.setBorderColorLeft(new Color(0x000000));
                        celda.setBackgroundColor(new Color(0xFFFFFF));
                        celda.setPadding(3.0f);
                        celda.setVerticalAlignment(Element.ALIGN_TOP);

                        table.addCell(celda);
                    }
                }
                document.add(table);

                // fin crear renglones con contenidos
                // crear footer
                // Font font = FontFactory.getFont("arial", 8, Font.NORMAL, new
                // Color(0x000000));
                // Rectangle rect = new Rectangle(300, 60, 559, 788);
                // writer.setBoxSize("art", rect);

                // ColumnText.showTextAligned(writer.getDirectContent(),
                // Element.ALIGN_CENTER, new
                // Phrase("Reporte de Calidad � Fecha de Generaci�n " +
                // format_DMY(new Date()), font), rect.getLeft(),
                // rect.getBottom() - 18, 0);
                // ColumnText.showTextAligned(writer.getDirectContent(),
                // Element.ALIGN_CENTER, new Phrase("�METLIFE M�xico", font),
                // rect.getLeft(), rect.getBottom() - 30, 0);
                // fin footer
                document.close();

                file.flush();
                file.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return nombreFile;
    }

    public void tableLayout(PdfPTable table, float[][] width, float[] height, int headerRows, int rowStart, PdfContentByte[] canvas) {
        float widths[] = width[0];
        float x1 = widths[0];
        float x2 = widths[widths.length - 1];
        float y1 = height[0];
        float y2 = height[height.length - 1];
        PdfContentByte cb = canvas[PdfPTable.LINECANVAS];
        cb.rectangle(x1, y1, x2 - x1, y2 - y1);
        cb.stroke();
        cb.resetRGBColorStroke();
    }

    public void cellLayout(PdfPCell cell, Rectangle position, PdfContentByte[] canvases) {
        float x1 = position.getLeft() + 2;
        float x2 = position.getRight() - 2;
        float y1 = position.getTop() - 2;
        float y2 = position.getBottom() + 2;
        PdfContentByte canvas = canvases[PdfPTable.LINECANVAS];
        canvas.rectangle(x1, y1, x2 - x1, y2 - y1);
        canvas.stroke();
    }

    protected String format_DMY(Date date) {
        if (date == null) {
            return "";
        } else {
            return SDF_DMY.format(date);
        }
    }

    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        AppContext.setApplicationContext(ctx);
    }

    private String getClaveDia(int diaSemana) {
        if (log.isDebugEnabled()) {
            log.debug("Dia de la Semana -> " + diaSemana);
        }
        if (diaSemana == ConstantesMxges.SUN) {
            return ConstantesMxges.SUNDAY;
        } else if (diaSemana == ConstantesMxges.MON) {
            return ConstantesMxges.MONDAY;
        } else if (diaSemana == ConstantesMxges.TUE) {
            return ConstantesMxges.TUESDAY;
        } else if (diaSemana == ConstantesMxges.WED) {
            return ConstantesMxges.WEDNESDAY;
        } else if (diaSemana == ConstantesMxges.THU) {
            return ConstantesMxges.THURSDAY;
        } else if (diaSemana == ConstantesMxges.FRI) {
            return ConstantesMxges.FRIDAY;
        } else if (diaSemana == ConstantesMxges.SAT) {
            return ConstantesMxges.SATURDAY;
        }
        return null;
    }
}
