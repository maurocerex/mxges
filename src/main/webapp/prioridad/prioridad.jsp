<%@ include file="/taglibs.jsp"%>
<script type="text/javascript">
	Y.util.Event.onDOMReady(function() {
		
				Y.namespace("Prioridad");

				Y.Prioridad.cuenta=true;
				
				Y.Prioridad.PrioridadCatalog = [];

				Y.Prioridad.RegistrosDtblURL = '<s:url action="prioridad-async" namespace="/" includeParams="none" />';
				Y.Prioridad.ActPrioridad = '<s:url action="prioridad-async" namespace="/" method="updPrioridad" includeParams="none" />';
				initDtblPrioridad();

				
			});
</script>
<script type="text/javascript" src="${ctx}/prioridad/js/prioridad.js?version=${version}"></script>

<jsp:include page="include/busqueda-prioridad.jsp" />
<div align="left"  style="width: 70%">
<form action="" class="pladform">
<fieldset><legend> Prioridad de Proceso </legend>
<div class="plad-dtbl-container">
<div class="pgnt-top-izquierda" style="font-size: 12px;">
<button type="button" id="guardaros">Guardar Cambios</button>
</div>
<p>&nbsp;</p>
<div class="errordiv" id="div.asignaciones.proceso" style="color: #FF0000"></div>
<div class="pgnt-top-derecha">
<div id="dtbl.Pgnt.Registros"></div>
</div>
<div class="pgnt-clear"></div>
<div class="plad-dtbl" id="dtbl.Cont.Registros"></div>
<div class="pgnt-bottom-izquierda">
<div id="dtbl.Errr.Registros">&nbsp;</div>
</div>
<div class="pgnt-bottom-derecha">
<div id="dtbl.Ttal.Registros">0</div>
&nbsp;registro (s) encontrado(s)</div>
<div class="pgnt-clear"></div>
</div>

</fieldset>
</form>
</div>
