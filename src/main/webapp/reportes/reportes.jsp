<%@ include file="/taglibs.jsp"%>
<script type="text/javascript">

Y.util.Event.onDOMReady(function() {
	
	Y.namespace("Reporte");
	
	Y.Reporte.GuardarCambiosURL = '<s:url action="reportes-async" namespace="/" method="guardar" includeParams="none" />';
	Y.Reporte.DatosURL = '<s:url action="reportes-async" namespace="/" method="getReporte" includeParams="none" />';
	
});

function isNumberKey(evt)
{
	
    var e = evt; // for trans-browser compatibility
    var charCode = e.which || e.keyCode;

    if (charCode > 31 && (charCode < 48 || charCode > 57))
        return false;

    return true;

}

</script>
<script type="text/javascript" src="${ctx}/reportes/js/reportes.js?version=${version}"></script>

<div align="left">
<h1 class="pladnavegacion">Reporte Calidad de Datos</h1>


<s:form theme="simple" cssClass="pladform" id="formDatos">
	<s:hidden name="detinatario" value="%{''}"></s:hidden>
	<s:hidden name="dia_1" value="%{''}"></s:hidden>
	<s:hidden name="dia_2" value="%{''}"></s:hidden>
	<s:hidden name="dia_3" value="%{''}"></s:hidden>
	<s:hidden name="dia_4" value="%{''}"></s:hidden>
</s:form> <s:form theme="simple" cssClass="pladform" id="formDestino">
	<fieldset style="width: 75%;"><legend>Destinatario</legend>
	<table>
		<tr>
			<td width="50px">Destino:</td>
			<td><s:select cssStyle="width:205px;" name="destinatario" list="destinatarios" listKey="keyTxt" listValue="value" emptyOption="false"
				></s:select>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
	</table>
	</fieldset>

</s:form> <s:form theme="simple" cssClass="pladform" id="formPeriodicidad">
	<fieldset style="width: 75%;"><legend>Periodicidad</legend>
	<table>
		<tr>
			<td>
			<table>
				<tr>
					<td width="50px">D&iacute;a 1 :</td>
					<td><s:textfield name="dia1" value="%{dia1}" type="text" style="width: 205px;" onkeypress="return isNumberKey(event)" maxlength="2">
					</s:textfield></td>
				</tr>
				
				<tr>
					<td width="50px">D&iacute;a 2 :</td>
					<td><s:textfield name="dia2" value="%{dia2}" type="text" style="width: 205px;" onkeypress="return isNumberKey(event)" maxlength="2"></s:textfield></td>
				</tr>
				
				<tr>
					<td width="50px">D&iacute;a 3 :</td>
					<td><s:textfield name="dia3" value="%{dia3}" type="text" style="width: 205px;" onkeypress="return isNumberKey(event)" maxlength="2"></s:textfield></td>
				</tr>
				
				<tr>
					<td width="50px">D&iacute;a 4 :</td>
					<td><s:textfield name="dia4" value="%{dia4}" type="text" style="width: 205px;" onkeypress="return isNumberKey(event)" maxlength="2"></s:textfield></td>
				</tr>
				<tr>
					<td colspan="2">
					<div class="errordiv" id="div.consecutivo" style="color: #FF0000"></div>
					<div class="errordiv" id="div.invalido" style="color: #FF0000"></div>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
					<button type="button" id="guardaUs" name="noimporta" value="">Guardar</button>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	</fieldset>
</s:form></div>
<form class="pladform" id="ErrorForm">
<div title="Tipo Rechazo" id="Detalle" style="font-size: 12px; font-family: Tahoma;" align="center">
<div class="hd">Mensaje</div>
<div class="bd">Informacion guardada correctamente.</div>
</div>
</form>
<s:form theme="simple" cssClass="pladform" id="formReporte">
	<s:hidden name="reporte.idProceso" value="%{''}"></s:hidden>
	<s:hidden name="reporte.idLob" value="%{''}"></s:hidden>
	<s:hidden name="reporte.idMensaje" value="%{''}"></s:hidden>
	<s:hidden name="reporte.idNotificacion" value="%{''}"></s:hidden>
	<s:hidden name="reporte.dia" value="%{''}"></s:hidden>
</s:form>

