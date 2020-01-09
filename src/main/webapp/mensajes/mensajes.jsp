<%@ include file="/taglibs.jsp"%>
<script type="text/javascript">
    
    
    Y.util.Event.onDOMReady(function(){
    
        Y.namespace("Mensajes");
        
        Y.Mensajes.VigenciaCatalog = [];
        Y.Mensajes.VigenciaCatalog.push({
            label: 'Activo',
            value: 'A'
        });
        Y.Mensajes.VigenciaCatalog.push({
            label: 'Inactivo',
            value: 'I'
        });
        Y.Mensajes.MedioCatalog = [];
        Y.Mensajes.MedioCatalog.push({
            label: 'SMS',
            value: 'SM'
        });
        Y.Mensajes.MedioCatalog.push({
            label: 'E-Mail',
            value: 'EM'
        });
        Y.Mensajes.RegistrosDtblURL = '<s:url action="mensajes-async" namespace="/" method="listadoMensajes" includeParams="none" />';
        Y.Mensajes.DetalleDtblURL = '<s:url action="mensajes-async" namespace="/" method="getVariables" includeParams="none" />';
        Y.Mensajes.ProcesosURL = '<s:url action="mensajes-async" namespace="/" includeParams="none" />';
        Y.Mensajes.FiltrarURL = '<s:url action="mensajes-async" namespace="/" method="busqueda" includeParams="none" />';
        Y.Mensajes.ValidarURL = '<s:url action="mensajes-async" namespace="/" method="validar" includeParams="none" />';
        Y.Mensajes.GuardarCambiosURL = '<s:url action="mensajes-async" namespace="/" method="guardar" includeParams="none" />';
        Y.Mensajes.AsociadoURL =  '<s:url action="mensajes-async" namespace="/"  method="asociado"  includeParams="none" />';
        
        
        initDtblMensajes();
        //initDtblDetalle();
		initDtblAlta();
        
        Y.Mensajes.RegistrosDtbl.update();
        //Y.Mensajes.DetalleDtbl.update();
        //Y.Mensajes.AltaDtbl.update();
        
        
    });
    
</script>

<script type="text/javascript" src="${ctx}/mensajes/js/mensajes.js?version=${version}"></script>
       

<div align="left" >
	<h1 class="pladnavegacion">Cat&aacute;logo Mensajes</h1>
</div>


<div align="left">
	<div id="container" class="yui-navset" style="width: 70%">
		<jsp:include page="include/busqueda-mensajes.jsp"></jsp:include>
		<jsp:include page="include/mensajes-dtbl.jsp"></jsp:include>
	</div>
</div>
		

<jsp:include page="include/alta-dlg.jsp"></jsp:include>
		
<s:form theme="simple" cssClass="pladform" id="MensajesForm">
    <s:hidden name="lob"  value="%{''}" />
    <s:hidden name="status"  value="%{''}" />
    <s:hidden name="proceso" value="%{''}"/>
    <s:hidden name="medio" value="%{''}"/>
    <s:hidden name="json" value="%{''}"/>
</s:form>

<s:form theme="simple" cssClass="pladform" id="MensajesJsonForm">
    <s:hidden name="jsonrecords"  value="%{''}" />
</s:form>

