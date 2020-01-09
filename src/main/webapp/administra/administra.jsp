<%@ include file="/taglibs.jsp"%>

<script type="text/javascript">
	Y.util.Event
			.onDOMReady(function() {

				Y.namespace("Administra");

				Y.Administra.EstatusCatalog = [];
				Y.Administra.EstatusCatalog.push({
					label : 'ACTIVO',
					value : 'N'
				});
				Y.Administra.EstatusCatalog.push({
					label : 'INACTIVO',
					value : 'Y'
				});
				
				var valor;
				
				Y.Administra.HorasCatalog = [];
				for (var h=0; h<24;h++){
					if (h<10)
						valor='0'+ h;
					else
						valor=h;
					Y.Administra.HorasCatalog.push({
						label : valor,
						value : valor
					});
				
				};
				
				Y.Administra.MinutosCatalog = [];
				for (var m=0; m<60;m++){
					if (m<10)
						valor='0'+ m;
					else
						valor=m;
					Y.Administra.MinutosCatalog.push({
						label : valor,
						value : valor
					});
				
				};
				
				Y.Administra.RegistrosDtblURL = '<s:url action="administra-async" namespace="/" method="listadoJobs" includeParams="none" />';
				Y.Administra.GuardarCambiosURL = '<s:url action="administra-async" namespace="/"  method="guardar"  includeParams="none" />';
				initDtblRegistros();

				//Y.Notificaciones.RegistrosDtbl.update();
				Y.Administra.RegistrosDtbl.update()

			});
</script>

<script type="text/javascript" src="${ctx}/administra/js/jobs.js?version=${version}"></script>
<div align="left">
<h1 class="pladnavegacion">Configuraci&oacute;n de ejecuciones</h1>
</div>
<div align="left">
<div id="container" class="yui-navset" style="width: 75%"> 
	<jsp:include page="include/jobs-dtbl.jsp"></jsp:include>
</div>
</div>



<s:form theme="simple" cssClass="pladform" id="JobsJsonForm">
	<s:hidden name="jsonrecords" value="%{''}" />
</s:form>