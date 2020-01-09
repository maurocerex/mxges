<%@ include file="/taglibs.jsp"%>
<s:form theme="simple" cssClass="pladform" id="formBusqueda">
<fieldset>
<legend>B&uacute;squeda</legend>
<table>
<tr>
<td colspan="7" align="left" class="pladnavegacion">
Debe seleccionar al menos un campo como criterio de b&uacute;squeda.
</td>
</tr>
		<tr>
			<td>L&iacute;nea de Negocio:</td>
			<td><s:select cssStyle="width:205px;" name="lob" list="listaLob" listKey="keyTxt" listValue="value" emptyOption="true"
				onchange="BuscarProcesos();"></s:select></td>
			<td>&nbsp;</td>
			<td align="left">Status:</td>
			<td>&nbsp;</td>
			<td><s:select cssStyle="width:205px;" name="status" list="status" listKey="keyTxt" listValue="value" emptyOption="true"
				onchange="botonBusqueda();"></s:select>
			</td>
			<td>&nbsp;</td>
			<td align="left">
			<button type="button" id="buscar">Buscar</button>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
			<div class="errordiv" id="div.lob.bus" style="color: #FF0000"></div>
			</td>
			<td></td>
			<td></td>
			<td></td>
			<td>
			<div class="errordiv" id="div.status.bus" style="color: #FF0000"></div>
			</td>
		</tr>
		<tr>
			<td>Proceso:</td>
			<td><s:select cssStyle="width:205px;" name="proceso" list="listaProcesos" listKey="keyTxt" listValue="value" emptyOption="true" disabled="true" onchange="botonBusqueda();"></s:select>
			</td>
			<td>&nbsp;</td>
			<td>Medio de Env&iacute;o:</td>
			<td>&nbsp;</td>
			<td><s:select cssStyle="width:205px;" name="medio" list="medio" listKey="keyTxt" listValue="value" emptyOption="true" onchange="botonBusqueda();"
				></s:select>
			</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td></td>
			<td>
			<div class="errordiv" id="div.proceso.bus" style="color: #FF0000"></div>
			</td>
			<td></td>
			<td></td>
			<td></td>
			<td>
			<div class="errordiv" id="div.medio.bus" style="color: #FF0000"></div>
			</td>
		</tr>
</table>
</fieldset>
</s:form>