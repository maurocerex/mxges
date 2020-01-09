<%@ include file="/taglibs.jsp"%>
<script type="text/javascript">
	Y.util.Event
			.onDOMReady(function() {

				Y.namespace("Notificaciones");

				Y.Notificaciones.VigenciaCatalog = [];
				Y.Notificaciones.VigenciaCatalog.push({
					label : 'Activo',
					value : 'A'
				});
				Y.Notificaciones.VigenciaCatalog.push({
					label : 'Inactivo',
					value : 'I'
				});
				
				Y.Notificaciones.MedioCatalog = [];
				Y.Notificaciones.MedioCatalog.push({
					label : 'SMS',
					value : 'SM'
				});
				Y.Notificaciones.MedioCatalog.push({
					label : 'E-Mail',
					value : 'EM'
				});
				
				Y.Notificaciones.CanalCatalog = [];
				Y.Notificaciones.CanalCatalog.push({
					label : 'NORMAL',
					value : '1'
				});
				Y.Notificaciones.CanalCatalog.push({
					label : 'HUB',
					value : '2'
				});
				Y.Notificaciones.CanalCatalog.push({
					label : 'FILES',
					value : '3'
				});
				
				Y.Notificaciones.DestinatarioCatalog = [];
				Y.Notificaciones.DestinatarioCatalog.push({
					label : 'Cliente',
					value : 'CL'
				});
				Y.Notificaciones.DestinatarioCatalog.push({
					label : 'Agente',
					value : 'AG'
				});
				Y.Notificaciones.DestinatarioCatalog.push({
					label : 'Ambos',
					value : 'AM'
				});

				Y.Notificaciones.RegistrosDtblURL = '<s:url action="notificaciones-async" namespace="/" method="listadoNotificaciones" includeParams="none" />';
				Y.Notificaciones.ProcesosURL = '<s:url action="notificaciones-async" namespace="/" includeParams="none" />';
				Y.Notificaciones.MensajesURL = '<s:url action="notificaciones-async" namespace="/"  method="buscaMensajes"  includeParams="none" />';
				Y.Notificaciones.MensajeURL = '<s:url action="notificaciones-async" namespace="/"  method="getMensaje"  includeParams="none" />';
				Y.Notificaciones.ValidarURL = '<s:url action="notificaciones-async" namespace="/"  method="validar"  includeParams="none" />';
				Y.Notificaciones.GuardarCambiosURL = '<s:url action="notificaciones-async" namespace="/"  method="guardar"  includeParams="none" />';
				Y.Notificaciones.FiltrarURL =  '<s:url action="notificaciones-async" namespace="/"  method="busqueda"  includeParams="none" />';
				
				
				initDtblRegistros();

				//Y.Notificaciones.RegistrosDtbl.update();

			});
</script>

<script type="text/javascript" src="${ctx}/notificaciones/js/notificaciones.js?version=${version}"></script>

<div align="left"">
<h1 class="pladnavegacion">Configuraci&oacute;n de Notificaciones</h1>
</div>
<jsp:include page="include/alta-dlg.jsp" />
<div align="left">
<div id="container" class="yui-navset" style="width: 75%">
	<jsp:include page="include/busqueda-notificaciones.jsp"></jsp:include> 
	<jsp:include page="include/notificaciones-dtbl.jsp"></jsp:include>
</div>
</div>


<s:form theme="simple" cssClass="pladform" id="NotificacionesForm">
    <s:hidden name="lob"  value="%{''}" />
    <s:hidden name="status"  value="%{''}" />
    <s:hidden name="proceso" value="%{''}"/>
    <s:hidden name="json" value="%{''}"/>
</s:form>



<s:form theme="simple" cssClass="pladform" id="NotificacionesJsonForm">
	<s:hidden name="jsonrecords" value="%{''}" />
</s:form>


<!--jsp:include page="include/detalle-dlg.jsp" /-->
