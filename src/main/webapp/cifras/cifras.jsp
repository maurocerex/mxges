<%@ include file="/taglibs.jsp"%>

<script type="text/javascript">
	Y.util.Event.onDOMReady(function() {

		Y.namespace("Cifras");
		
		Y.Cifras.MedioCatalog = [];
		Y.Cifras.MedioCatalog.push({
			label : 'SMS',
			value : 'SM'
		});
		Y.Cifras.MedioCatalog.push({
			label : 'E-Mail',
			value : 'EM'
		});
		
		Y.Cifras.StatusCatalog = [];
		Y.Cifras.StatusCatalog.push({
			label : 'Pendiente',
			value : 'PE'
		});
		Y.Cifras.StatusCatalog.push({
			label : 'Transformado',
			value : 'TR'
		});
		Y.Cifras.StatusCatalog.push({
			label : 'Error',
			value : 'ER'
		});
		Y.Cifras.StatusCatalog.push({
			label : 'Enviado',
			value : 'EN'
		});
		Y.Cifras.StatusCatalog.push({
			label : 'Cancelada por Prioridad',
			value : 'CP'
		});
		Y.Cifras.StatusCatalog.push({
			label : 'Error Datos Contacto',
			value : 'EC'
		});
		Y.Cifras.StatusCatalog.push({
			label : 'Falta de Datos de Contacto',
			value : 'FC'
		});

		Y.Cifras.RegistrosDtblURL = '<s:url action="cifras-async" namespace="/" includeParams="none" />';
		Y.Cifras.BusquedaURL = '<s:url action="cifras-async" namespace="/" method="busqueda" includeParams="none" />';
		Y.Cifras.ProcesosURL = '<s:url action="cifras-async" namespace="/" includeParams="none" />';
		Y.Cifras.DetalleURL = '<s:url action="cifras-async" namespace="/" method="detalleRegistro" includeParams="none" />';
		Y.Cifras.GuardarCambiosURL = '<s:url action="cifras-async" namespace="/" method="guardar" includeParams="none" />';
		
		initDtblRegistros();
		initDtblDetalle();

		//Y.Cifras.RegistrosDtbl.update();
		//Y.Cifras.DetalleDtbl.update();
		

	});
</script>

<script type="text/javascript" src="${ctx}/cifras/js/cifras.js?version=${version}"></script>

<div align="left">
	<h1 class="pladnavegacion">Cifras Control</h1>
</div>
<div id="container" class="yui-navset" style="width: 78%">
	<jsp:include page="include/busqueda-cifras.jsp"></jsp:include>
</div>

<s:form theme="simple" id="reporteCifras" cssClass="pladform">
	<s:hidden name="proceso" value="%{''}"></s:hidden>
	<s:hidden name="medio" value="%{''}"></s:hidden>
	<s:hidden name="fecInicio" value="%{''}"></s:hidden>
	<s:hidden name="fecFin" value="%{''}"></s:hidden>
	<s:hidden name="jsonrecords" value="%{''}"></s:hidden>
</s:form>

<div id="container" class="yui-navset" style="width: 78%">
<form action="" class="pladform" id="formCifras">
	<fieldset>
		<legend>Resultados</legend>
		<div class="plad-dtbl-container">
			<div class="pgnt-top-izquierda" style="font-size: 12px;">
				<button type="button" id="reenviar">Reenviar</button>
			</div>
<p>&nbsp;</p>
			<div class="errordiv" id="div.asignaciones.proceso"
				style="color: #FF0000"></div>
			<div class="pgnt-top-derecha">
				<div id="dtbl.Pgnt.Registros"></div>
			</div>
			<div class="pgnt-clear"></div>
			<div class="plad-dtbl" id="dtbl.Cont.Registros" align="center"></div>
			<div class="pgnt-bottom-izquierda">
				<div id="dtbl.Errr.Registros">&nbsp;</div>
			</div>
			<div class="pgnt-bottom-derecha">
				<div id="dtbl.Ttal.Registros">0</div>
				&nbsp;registro (s) encontrado(s)
			</div>
			<div class="pgnt-clear"></div>
		</div>

	</fieldset>
</form>
</div>

	<jsp:include page="include/detalle-cifras.jsp"></jsp:include>



