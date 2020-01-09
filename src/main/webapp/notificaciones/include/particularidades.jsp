<%@ include file="/taglibs.jsp"%>
<s:form theme="simple" cssClass="pladform" id="CobranzaForm">
<fieldset><legend> Cobranza Bancaria </legend>
<table>
	<tr>
		
		<td>Tipo Respuesta:</td>
		<td>
		
		<select name="notificacion.idRespBancaria" style="width: 205px;" onchange="habRespuesta(this.value)">
			<option value="A">Aceptada</option>
			<option value="R">Rechazada</option>
		</select>
		
		</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td><div class="errordiv" id="div.respuesta" style="color:#FF0000"></div></td>
	</tr>
	<tr>
		<td>Reintentable:</td>
		<td><select name="notificacion.reintentable"  style="width: 205px;" >
			<option value="9">-- Selecciona una opcion --</option>
			<option value="1">Reintentable</option>
			<option value="0">No Reintentable</option>
		</select></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td><div class="errordiv" id="div.reintentable" style="color:#FF0000"></div></td>
	</tr>
</table>
</fieldset>
</s:form>
<s:form theme="simple" cssClass="pladform" id="OtrosForm">
<fieldset><legend> Otros </legend>
<table width="100%">
	<tr>
		<td><input type="checkbox" name="activar" onchange="activaDesactivaOtros();">&nbsp;Activar </input></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>Conducto Cobro:</td>
		<td colspan="2">
			<s:checkbox name="notificacion.dxn" value="notificacion.dxn" disabled="true" >&nbsp;DXN </s:checkbox>&nbsp;
			<s:checkbox name="notificacion.cobraBanca" value="notificacion.cobraBanca" disabled="true">&nbsp;Cobranza bancaria</s:checkbox>&nbsp;
			<s:checkbox name="notificacion.pagoDirecto" value="notificacion.pagoDirecto" disabled="true">&nbsp;Pago directo </s:checkbox>
		</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>Regimen Laboral:</td>
		<td>
			<s:checkbox type="checkbox" name="notificacion.regLabBase" value="notificacion.regLabBase" disabled="true">&nbsp;&nbsp;Base </s:checkbox>&nbsp;
			<s:checkbox type="checkbox" name="notificacion.regLabEventual" value="notificacion.regLabEventual" disabled="true">&nbsp;&nbsp;Eventual</s:checkbox>
		</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
</table>
</fieldset>
</s:form>