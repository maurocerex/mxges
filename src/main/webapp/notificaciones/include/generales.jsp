<%@ include file="/taglibs.jsp"%>
<s:form theme="simple" cssClass="pladform" id="GeneralesForm">
<s:hidden name="record.id"  value="%{''}" />
<s:hidden name="status"  value="%{''}" />
<s:hidden name="lob"  value="%{''}" />
<s:hidden name="proceso"  value="%{''}" />
<s:hidden name="dia"  value="%{'true'}" />
<s:hidden name="hora"  value="%{''}" />
<s:hidden name="min"  value="%{''}" />
<s:hidden name="idRespBancaria"  value="%{''}" />
<s:hidden name="reintentable"  value="%{''}" />
<s:hidden name="idMensajeAnt"  value="%{''}" />
<s:hidden name="notificacion.nuevo" value="%{'false'}" />
<fieldset><legend> Criterios de Notificaci&oacute;n </legend>
<p>&nbsp;</p>
<table>
	<tr>
		<td width="125px">Id Notificaci&oacute;n:</td>
		<td><s:textfield name="notificacion.idNotificacion" maxlength="50" value="%{''}" cssStyle="width:205px;"></s:textfield>
		<div class="errordiv" id="div.notificacion" style="color:#FF0000"></div>
		</td>
	</tr>
	
	<tr>
		<td width="125px">Descripci&oacute;n:</td>
		<td><s:textfield name="notificacion.notificacionDesc" maxlength="80" value="%{''}" cssStyle="width:205px;"></s:textfield>
		<div class="errordiv" id="div.descripcion" style="color:#FF0000"></div>
		</td>
	</tr>
	
	<tr>
		<td width="125px">L&iacute;nea de Negocio:</td>
		<td>
			<s:select cssStyle="width:205px;" name="notificacion.idLob" list="listaLob" listKey="keyTxt" listValue="value" emptyOption="true"
				onchange="BuscarProcesos('ds');"></s:select>
			<s:textfield name="notificacion.idLobDet" value="%{''}" cssStyle="width:205px; display:none" disabled="true"></s:textfield>
				<div class="errordiv" id="div.lob" style="color:#FF0000"></div>
		</td>
	</tr>
	
	<tr>
		<td width="125px">Proceso:</td>
		<td><s:select cssStyle="width:205px;" name="notificacion.idProceso" list="listaProcesos" listKey="keyTxt" listValue="value" emptyOption="true" onchange="buscarMensajes();"  disabled="true"></s:select>
		<s:textfield name="notificacion.idProcesoDet" value="%{''}" cssStyle="width:205px; display:none" disabled="true"></s:textfield>
		<div class="errordiv" id="div.proceso" style="color:#FF0000"></div>
		</td>
	</tr>
 
	<!--<tr>
		<td width="125px">Canal de Env&iacute;o:</td>
		<td>
		<<s:select cssStyle="width:205px;" name="notificacion.canalEnvio" list="canalEnvioList" listKey="keyTxt" listValue="value" emptyOption="true"  disabled="true"  ></s:select>
		<div class="errordiv" id="div.canalEnvio" style="color:#FF0000"></div>
		<div id="div.canalEnvio_info" ></div>
	</tr>-->
	
	<tr>
		<td width="125px">Medio de Env&iacute;o:</td>
		<td>
		<s:select cssStyle="width:205px;" name="notificacion.medio" list="medio" listKey="keyTxt" listValue="value" emptyOption="true"  disabled="true" onchange="buscarMensajes();" ></s:select>
		<div class="errordiv" id="div.medio" style="color:#FF0000"></div>
	</tr>
	
	<tr>
		<td width="125px">ID Mensaje:</td>
		<td><s:select cssStyle="width:205px;" name="notificacion.mensaje.idMensaje" list="listaMensajes" listKey="keyTxt" listValue="value" emptyOption="true" onchange="SeleccionMensaje('ds');"></s:select>
		
		<div class="errordiv" id="div.mensaje" style="color:#FF0000"></div>
		</td>
	</tr>
	
	<tr>
		<td width="125px">Asunto Mensaje</td>
		<td><s:textfield name="notificacion.mensaje.asunto" maxlength="250" value="%{''}" cssStyle="width:205px;" readonly="true"></s:textfield></td>
	</tr>
	
	<tr>
		<td width="125px">Detalle Mensaje</td>
		<td><s:textarea name="notificacion.mensaje.mensaje" maxlength="250" value="%{''}" cssStyle="width:205px;" readonly="true"></s:textarea></td>
	</tr>
	
	<tr>
		<td width="125px">Destinatario:</td>
		<td><s:checkbox name="notificacion.envioCliente" value="notificacion.envioCliente">&nbsp; Cliente </s:checkbox><s:checkbox name="notificacion.envioAgente" value="notificacion.envioAgente">&nbsp; Agente </s:checkbox></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td><div class="errordiv" id="div.envio" style="color:#FF0000"></div></td>
	</tr>
</table>
<p>&nbsp;</p>
</fieldset>
</s:form>