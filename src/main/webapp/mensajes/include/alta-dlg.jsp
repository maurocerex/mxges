<%@ include file="/taglibs.jsp"%>
<div id="alta" style="font-size: 12px; font-family: Tahoma;" align="center">
<div class="hd">Agregar Mensaje</div>
<div class="bd"><s:form theme="simple" cssClass="pladform" id="AltaMensaje">
		<s:hidden name="record.id"  value="%{''}" />
		<s:hidden name="lob"  value="%{''}" />
		<s:hidden name="proceso"  value="%{''}" />
		<s:hidden name="status"  value="%{''}" />
		<s:hidden name="medio"  value="%{''}" />
        <s:hidden name="mensaje.nuevo" value="%{'false'}" />
	<table>
		<tr>
			<td>Id Mensaje:</td>
			<td><s:textfield name="mensaje.idMensaje" value="%{''}" cssStyle="width:205px;" maxlength="30"></s:textfield>
			<div class="errordiv" id="div.mensaje" style="color:#FF0000"></div>
			</td>
		</tr>
		
		<tr>
			<td>Descripci&oacute;n:</td>
			<td><s:textfield name="mensaje.descripcion" value="%{''}" cssStyle="width:205px;" maxlength="100"></s:textfield>
			<div class="errordiv" id="div.descripcion" style="color:#FF0000"></div>
			</td>
		</tr>
		
		<tr>
			<td>L&iacute;nea de Negocio:</td>
			<td><s:select cssStyle="width:205px;" name="mensaje.idLob" list="listaLob" listKey="keyTxt" listValue="value" emptyOption="true"
				onchange="BuscarProcesos('ds');"></s:select>
				<s:textfield name="mensaje.idLobDet" value="%{''}" cssStyle="width:205px; display:none" disabled="true"></s:textfield>
				<div class="errordiv" id="div.lob" style="color:#FF0000"></div>
				</td>
		</tr>
		
		<tr>
			<td>Proceso:</td>
			<td><s:select cssStyle="width:205px;" name="mensaje.idProceso" list="listaProcesos" listKey="keyTxt" listValue="value" emptyOption="true" disabled="true" onchange="getVariables(this)"></s:select>
			<s:textfield name="mensaje.idProcesoDet" value="%{''}" cssStyle="width:205px; display:none" disabled="true"></s:textfield>
			<div class="errordiv" id="div.proceso" style="color:#FF0000"></div>
			</td>
		</tr>


		<!-- tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>Status:</td>
			<td><select style="width: 205px;" name="mensaje.status" >
				<option value="A">Activo</option>
				<option value="I">Inactivo</option>
			</select>
			<s:textfield name="mensaje.statusDet" value="%{''}" cssStyle="width:205px; display:none" disabled="true"></s:textfield>
			<div class="errordiv" id="div.status" style="color:#FF0000"></div>
			</td>
		</tr-->
		
		<tr>
			<td>Medio de Env&iacute;o:</td>
			<td><s:select cssStyle="width:205px;" name="mensaje.medio" list="medio" listKey="keyTxt" listValue="value" emptyOption="true"
				onchange="validaEnvio(this)"></s:select>
			<s:textfield name="mensaje.medioDet" value="%{''}" cssStyle="width:205px; display:none" disabled="true"></s:textfield>
			<div class="errordiv" id="div.medio" style="color:#FF0000"></div>
			
			</td>
		</tr>
		

		<tr>
			<td>Asunto:</td>
			<td><s:textfield name="mensaje.asunto" value="%{''}" cssStyle="width:205px;" maxlength="50"></s:textfield>
			<div class="errordiv" id="div.asunto" style="color:#FF0000"/>
			</td>
		</tr>
		
		<tr>
			<td>Mensaje:</td>
			<td><s:textarea name="mensaje.mensaje" rows="7" cols="" style="width: 205px" onkeydown="cuenta()" onkeyup="cuenta()"></s:textarea>
			<div class="errordiv" id="div.mensaje.cuerpo" style="color:#FF0000"/>
			</td>
		</tr>
		<tr><td colspan="2"><div align="right">Car&aacute;cter(es):<div id="chars" name="chars" align="right" >0</div></div></td></tr>
	</table>
</s:form>
	<form class="pladForm">
	<fieldset style="background-color: #F2F2F2"><legend> Variables Permitidas en Formato </legend>
	<div class="plad-dtbl-container" style="background-color: #F2F2F2">
	<div class="errordiv" id="div.asignaciones.proceso" style="color: #FF0000"></div>
	<div class="pgnt-top-derecha">
	<div id="dtbl.Pgnt.RegistrosAlta"></div>
	</div>
	<div class="pgnt-clear"></div>
	<div class="plad-dtbl" id="dtbl.Cont.RegistrosAlta"></div>
	<div class="pgnt-bottom-izquierda">
	<div id="dtbl.Errr.Registros">&nbsp;</div>
	</div>
	<div class="pgnt-bottom-derecha">
	<div id="dtbl.Ttal.RegistrosAlta">0</div>
	&nbsp;registro (s) encontrado(s)</div>
	<div class="pgnt-clear"></div>
	</div>

	</fieldset>
	</form>
	<button type="button" id="guardaralta">Guardar</button>
	<button type="button" id="cancelaralta">Cancelar</button>
</div>
</div>
