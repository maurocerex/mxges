<%@ include file="/taglibs.jsp"%>

<script type="text/javascript">
	Y.util.Event
			.onDOMReady(function() {

				Y.namespace("Busqueda");
				Y.Busqueda.Retenedores = '<s:url action="retenedor-unidad-pago-async" method="getRetenedores" namespace="/" includeParams="none" />';
				Y.Busqueda.UnidadesPago = '<s:url action="retenedor-unidad-pago-async" method="getUnidadPago" namespace="/" includeParams="none" />';

				Y.namespace("Exclusiones");

				Y.Exclusiones.ProcesosURL = '<s:url action="exclusiones-async" namespace="/" includeParams="none" />';
				Y.Exclusiones.BusquedaURL = '<s:url action="exclusiones-async" namespace="/" method="busqueda" includeParams="none" />';
				Y.Exclusiones.Guardar = '<s:url action="exclusiones-async" namespace="/" method="guardar" includeParams="none" />';

			});
</script>
<style type="text/css">
.smallBr {
	font-size: 4px;
	line-height: 4px;
}
</style>

<script type="text/javascript" src="${ctx}/exclusiones/js/exclusiones.js?version=${version}"></script>
<script type="text/javascript" src="${ctx}/exclusiones/js/controles.js?version=${version}"></script>
<script type="text/javascript" src="${ctx}/exclusiones/js/multiple-select-functions.js?version=${version}"></script>

<div align="left">
<h1 class="pladnavegacion">Exclusiones</h1>
</div>


<div id="container" class="yui-navset" style="width: 75%">

<jsp:include page="include/busqueda-exclusiones.jsp"></jsp:include>


<form id="camposform" class="pladform"><input type="hidden" name="lob" /> <input type="hidden" name="proceso" /><input type="hidden" name="medioEnvio" id="medioEnvio"/> <s:hidden name="bandera"
	value="%{false}" />
	<s:hidden name="banderaCt" id="banderaCt"
	value="%{false}" />
<fieldset><legend>Exclusiones</legend>
<table class="consultartable">
	<tr>
		<td align="center">No Excluidos</td>
		<td>&nbsp;</td>
		<td align="center">Excluidos</td>
	</tr>
	<tr>
		<td align="center">Retenedor - Unidad de Pago</td>
		<td>&nbsp;</td>
		<td align="center">Retenedor - Unidad de Pago</td>
	</tr>
	<tr>
		<td><select name="disponibles" id="disponibles" style="width: 400px; height: 300px;" multiple="multiple" onclick="banderaCambia(this)">
		</select></td>
		<td align="center">
		<button type="button" id="addAllbutton" name="addAllbutton">>></button>
		<br>
		<br class="smallBr">
		<button type="button" id="addbutton" name="addbutton">></button>
		<br>
		<br class="smallBr">
		<button type="button" id="removebutton" name="removebutton"><</button>
		<br>
		<br class="smallBr">
		<button type="button" id="remAllbutton" name="remAllbutton"><<</button>
		<br>
		<br class="smallBr">
		</td>
		<td><select name="seleccionados" id="seleccionados" style="width: 300px; height: 300px;" multiple="multiple" onclick="banderaCambia(this)">

		</select></td>
	</tr>
	<tr>
		<td align="center">No Excluidos</td>
		<td>&nbsp;</td>
		<td align="center">Excluidos</td>
	</tr>
	<tr>
		<td align="center">Retenedor CT - Unidad de Pago CT</td>
		<td>&nbsp;</td>
		<td align="center">Retenedor CT - Unidad de Pago CT</td>
	</tr>
	<tr>
		<td><select name="disponiblesct" id="disponiblesct" style="width: 400px; height: 300px;" multiple="multiple" onclick="banderaCtCambia(this)">
		</select></td>
		<td align="center">
		<button type="button" id="addAllbuttonct" name="addAllbuttonct">>></button>
		<br>
		<br class="smallBr">
		<button type="button" id="addbuttonct" name="addbuttonct">></button>
		<br>
		<br class="smallBr">
		<button type="button" id="removebuttonct" name="removebuttonct"><</button>
		<br>
		<br class="smallBr">
		<button type="button" id="remAllbuttonct" name="remAllbuttonct"><<</button>
		<br>
		<br class="smallBr">
		</td>
		<td><select name="seleccionadosct" id="seleccionadosct" style="width: 300px; height: 300px;" multiple="multiple" onclick="banderaCtCambia(this)">

		</select></td>
	</tr>
	<tr>
		<td style="padding-left: 80px;" colspan="3" align="center">
		<button type="button" id="guardar">Guardar Cambios</button>
		</td>
	</tr>
</table>
</fieldset>
</form>
</div>
