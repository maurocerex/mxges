package com.metlife.nm.domain;

public class MxgesProperties extends BaseObjectVO {

    /**
	 * 
	 */
    private static final long serialVersionUID = 5467818899851284659L;

    private String pathArchivosCifras;
    private String pathArchivosCalidad;
    private String ipWs;
    private String portWs;
    private String sourceCountry;
    private String sourceCompany;
    private String sourceSystem;
    private String idLob;
    private String idivrValue;
    private String transType;
    private String enviaMensajesOl;
    
    // NR2.5 MCE Modificacion HUB Propiedades
    private String endpointHubComServiceMass;
    private String fileExtension;
    private String olifeSourceCountry;
    private String olifeSourceCompany;
    private String olifeSourceSystem;
    private String testIndicator;
    private String testIndicatorValue;
    private String transtypeTC;
    private String transtypeValue;
    private String transRefGUID;
    private String templateHubCom;
    
    //directorios
    private String itDirExec;
    private String itDirInput;
    private String itDirInputLogs;
	private String itDirInputRespaldos;
    private String itDirSmsOutput;
    private String itDirEmailOutput;
    
    //datos MFT
    private String itsftpHost;
    private String itsftpPort;
    private String itsftpUser;
    private String itsftpPass;
    private String itsftpOutputdir;
    
    //Variables de Plantilla SMS
    private String itsmsTipo;
    private String itsmsValorTipo;
    private String itsmsNombre;
    private String itsmsFechaInicio;
    private String itsmsMensaje;
    private String itsmsCsvTelefono;
    private String itsmsCsvNombre;
    private String itsmsCsvCuenta;
    private String itsmsCsvSaldo;

    //Variables de Plantilla EMAIL
    private String itemailTipo;
    private String itemailValorTipo;
    private String itemailNombre;
    private String itemailFechaInicio;
    private String itemailMensaje;
    private String itemailCsvTelefono;
    private String itemailCsvNombre;
    private String itemailCsvCuenta;
    private String itemailCsvSaldo;
    
	public String getPathArchivosCifras() {
		return pathArchivosCifras;
	}
	public final void setPathArchivosCifras(String pathArchivosCifras) {
		this.pathArchivosCifras = pathArchivosCifras;
	}
	public final String getIpWs() {
		return ipWs;
	}
	public final void setIpWs(String ipWs) {
		this.ipWs = ipWs;
	}
	public final String getPortWs() {
		return portWs;
	}
	public final void setPortWs(String portWs) {
		this.portWs = portWs;
	}
	public final String getSourceCountry() {
		return sourceCountry;
	}
	public final void setSourceCountry(String sourceCountry) {
		this.sourceCountry = sourceCountry;
	}
	public final String getSourceCompany() {
		return sourceCompany;
	}
	public final void setSourceCompany(String sourceCompany) {
		this.sourceCompany = sourceCompany;
	}
	public final String getSourceSystem() {
		return sourceSystem;
	}
	public final void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}
	public final String getIdLob() {
		return idLob;
	}
	public final void setIdLob(String idLob) {
		this.idLob = idLob;
	}
	public final String getIdivrValue() {
		return idivrValue;
	}
	public final void setIdivrValue(String idivrValue) {
		this.idivrValue = idivrValue;
	}
	public final String getTransType() {
		return transType;
	}
	public final void setTransType(String transType) {
		this.transType = transType;
	}
	public final String getEnviaMensajesOl() {
		return enviaMensajesOl;
	}
	public final void setEnviaMensajesOl(String enviaMensajesOl) {
		this.enviaMensajesOl = enviaMensajesOl;
	}
    public final String getPathArchivosCalidad() {
        return pathArchivosCalidad;
    }
    public final void setPathArchivosCalidad(String pathArchivosCalidad) {
        this.pathArchivosCalidad = pathArchivosCalidad;
    }
	public String getEndpointHubComServiceMass() {
		return endpointHubComServiceMass;
	}
	public void setEndpointHubComServiceMass(String endpointHubComServiceMass) {
		this.endpointHubComServiceMass = endpointHubComServiceMass;
	}
	public String getFileExtension() {
		return fileExtension;
	}
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	public String getOlifeSourceCountry() {
		return olifeSourceCountry;
	}
	public void setOlifeSourceCountry(String olifeSourceCountry) {
		this.olifeSourceCountry = olifeSourceCountry;
	}
	public String getOlifeSourceCompany() {
		return olifeSourceCompany;
	}
	public void setOlifeSourceCompany(String olifeSourceCompany) {
		this.olifeSourceCompany = olifeSourceCompany;
	}
	public String getOlifeSourceSystem() {
		return olifeSourceSystem;
	}
	public void setOlifeSourceSystem(String olifeSourceSystem) {
		this.olifeSourceSystem = olifeSourceSystem;
	}
	public String getTestIndicator() {
		return testIndicator;
	}
	public void setTestIndicator(String testIndicator) {
		this.testIndicator = testIndicator;
	}
	public String getTestIndicatorValue() {
		return testIndicatorValue;
	}
	public void setTestIndicatorValue(String testIndicatorValue) {
		this.testIndicatorValue = testIndicatorValue;
	}
	public String getTranstypeTC() {
		return transtypeTC;
	}
	public void setTranstypeTC(String transtypeTC) {
		this.transtypeTC = transtypeTC;
	}
	public String getTranstypeValue() {
		return transtypeValue;
	}
	public void setTranstypeValue(String transtypeValue) {
		this.transtypeValue = transtypeValue;
	}
	public String getTransRefGUID() {
		return transRefGUID;
	}
	public void setTransRefGUID(String transRefGUID) {
		this.transRefGUID = transRefGUID;
	}
	public String getTemplateHubCom() {
		return templateHubCom;
	}
	public void setTemplateHubCom(String templateHubCom) {
		this.templateHubCom = templateHubCom;
	}
	public String getItsmsTipo() {
		return itsmsTipo;
	}
	public void setItsmsTipo(String itsmsTipo) {
		this.itsmsTipo = itsmsTipo;
	}
	public String getItsmsValorTipo() {
		return itsmsValorTipo;
	}
	public void setItsmsValorTipo(String itsmsValorTipo) {
		this.itsmsValorTipo = itsmsValorTipo;
	}
	public String getItsmsNombre() {
		return itsmsNombre;
	}
	public void setItsmsNombre(String itsmsNombre) {
		this.itsmsNombre = itsmsNombre;
	}
	public String getItsmsFechaInicio() {
		return itsmsFechaInicio;
	}
	public void setItsmsFechaInicio(String itsmsFechaInicio) {
		this.itsmsFechaInicio = itsmsFechaInicio;
	}
	public String getItsmsMensaje() {
		return itsmsMensaje;
	}
	public void setItsmsMensaje(String itsmsMensaje) {
		this.itsmsMensaje = itsmsMensaje;
	}
	public String getItsmsCsvTelefono() {
		return itsmsCsvTelefono;
	}
	public void setItsmsCsvTelefono(String itsmsCsvTelefono) {
		this.itsmsCsvTelefono = itsmsCsvTelefono;
	}
	public String getItsmsCsvNombre() {
		return itsmsCsvNombre;
	}
	public void setItsmsCsvNombre(String itsmsCsvNombre) {
		this.itsmsCsvNombre = itsmsCsvNombre;
	}
	public String getItsmsCsvCuenta() {
		return itsmsCsvCuenta;
	}
	public void setItsmsCsvCuenta(String itsmsCsvCuenta) {
		this.itsmsCsvCuenta = itsmsCsvCuenta;
	}
	public String getItsmsCsvSaldo() {
		return itsmsCsvSaldo;
	}
	public void setItsmsCsvSaldo(String itsmsCsvSaldo) {
		this.itsmsCsvSaldo = itsmsCsvSaldo;
	}
	public String getItemailTipo() {
		return itemailTipo;
	}
	public void setItemailTipo(String itemailTipo) {
		this.itemailTipo = itemailTipo;
	}
	public String getItemailValorTipo() {
		return itemailValorTipo;
	}
	public void setItemailValorTipo(String itemailValorTipo) {
		this.itemailValorTipo = itemailValorTipo;
	}
	public String getItemailNombre() {
		return itemailNombre;
	}
	public void setItemailNombre(String itemailNombre) {
		this.itemailNombre = itemailNombre;
	}
	public String getItemailFechaInicio() {
		return itemailFechaInicio;
	}
	public void setItemailFechaInicio(String itemailFechaInicio) {
		this.itemailFechaInicio = itemailFechaInicio;
	}
	public String getItemailMensaje() {
		return itemailMensaje;
	}
	public void setItemailMensaje(String itemailMensaje) {
		this.itemailMensaje = itemailMensaje;
	}
	public String getItemailCsvTelefono() {
		return itemailCsvTelefono;
	}
	public void setItemailCsvTelefono(String itemailCsvTelefono) {
		this.itemailCsvTelefono = itemailCsvTelefono;
	}
	public String getItemailCsvNombre() {
		return itemailCsvNombre;
	}
	public void setItemailCsvNombre(String itemailCsvNombre) {
		this.itemailCsvNombre = itemailCsvNombre;
	}
	public String getItemailCsvCuenta() {
		return itemailCsvCuenta;
	}
	public void setItemailCsvCuenta(String itemailCsvCuenta) {
		this.itemailCsvCuenta = itemailCsvCuenta;
	}
	public String getItemailCsvSaldo() {
		return itemailCsvSaldo;
	}
	public void setItemailCsvSaldo(String itemailCsvSaldo) {
		this.itemailCsvSaldo = itemailCsvSaldo;
	}
	public String getItDirInput() {
		return itDirInput;
	}
	public void setItDirInput(String itDirInput) {
		this.itDirInput = itDirInput;
	}
	public String getItDirInputLogs() {
		return itDirInputLogs;
	}
	public void setItDirInputLogs(String itDirInputLogs) {
		this.itDirInputLogs = itDirInputLogs;
	}
	public String getItDirInputRespaldos() {
		return itDirInputRespaldos;
	}
	public void setItDirInputRespaldos(String itDirInputRespaldos) {
		this.itDirInputRespaldos = itDirInputRespaldos;
	}
	public String getItDirSmsOutput() {
		return itDirSmsOutput;
	}
	public void setItDirSmsOutput(String itDirOmsOutput) {
		this.itDirSmsOutput = itDirOmsOutput;
	}
	public String getItDirEmailOutput() {
		return itDirEmailOutput;
	}
	public void setItDirEmailOutput(String itDirEmailOutput) {
		this.itDirEmailOutput = itDirEmailOutput;
	}
	public String getItDirExec() {
		return itDirExec;
	}
	public void setItDirExec(String itDirExec) {
		this.itDirExec = itDirExec;
	}
	public String getItsftpHost() {
		return itsftpHost;
	}
	public void setItsftpHost(String itsftpHost) {
		this.itsftpHost = itsftpHost;
	}
	public String getItsftpPort() {
		return itsftpPort;
	}
	public void setItsftpPort(String itsftpPort) {
		this.itsftpPort = itsftpPort;
	}
	public String getItsftpUser() {
		return itsftpUser;
	}
	public void setItsftpUser(String itsftpUser) {
		this.itsftpUser = itsftpUser;
	}
	public String getItsftpPass() {
		return itsftpPass;
	}
	public void setItsftpPass(String itsftpPass) {
		this.itsftpPass = itsftpPass;
	}
	public String getItsftpOutputdir() {
		return itsftpOutputdir;
	}
	public void setItsftpOutputdir(String itsftpOutputdir) {
		this.itsftpOutputdir = itsftpOutputdir;
	}
		
}
