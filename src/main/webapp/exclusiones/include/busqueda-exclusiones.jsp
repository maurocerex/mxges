<%@ include file="/taglibs.jsp"%>
<s:form theme="simple" cssClass="pladform" id="formBusqueda">
	<input type="hidden" id="retH" name="retH" />
	<fieldset><legend> B&uacute;squeda</legend>
	<table>
		<tr>
			<td>L&iacute;nea de Negocio</td>
			<td>&nbsp;</td>
			<td><s:select cssStyle="width:205px;" name="lob" list="listaLob" id="lob" listKey="keyTxt" listValue="value" emptyOption="true"
				onchange="buscarProcesos();"></s:select></td>
			<td>&nbsp;</td>

		</tr>
		<tr>
			<td></td>
			<td></td>
			<td colspan="2">
			<div class="errordiv" id="div.lob" style="color: #FF0000"></div>
			</td>
			<td></td>
			<td></td>

		</tr>
		<tr>
			<td>Proceso</td>
			<td>&nbsp;</td>
			<td><s:select cssStyle="width:205px;" id="proceso" name="proceso" list="listaProcesos" listKey="keyTxt" listValue="value" emptyOption="true"
				disabled="true" onchange="habilitaReten(this);"></s:select></td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td colspan="2">
			<div class="errordiv" id="div.proceso" style="color: #FF0000"></div>
			</td>
		</tr>
		<tr>
			<td>Tipo de env&iacute;o</td>
			<td>&nbsp;</td>
			<td><s:select cssStyle="width:205px;" id="tipoEnvio" name="tipoEnvio" list="listaTipoEnvio" listKey="keyTxt" listValue="value" emptyOption="false"
				></s:select></td>
		</tr>
		<tr>
			<td>Retenedor</td>
			<td>&nbsp;</td>
			<td><s:textfield id="retenedor" name="retenedor" cssStyle="width:300px;" maxlength="100" value="%{''}" disabled="true"></s:textfield>
			<button type="button" id="btnCleanRet" title="Limpiar Retenedor/UP" onclick="limpia()"><img src="${ctx}/images/goma.png" width="18" height="18"
				alt="Limpiar Retenedor" /></button>
			</td>
			<td>&nbsp;</td>
			<td>
			<button type="button" id="buscar">Buscar</button>
			</td>
		</tr>
	</table>
	</fieldset>
</s:form>