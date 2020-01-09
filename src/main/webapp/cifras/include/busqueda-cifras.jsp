<%@ include file="/taglibs.jsp"%>
<s:form theme="simple" cssClass="pladform" id="formBusqueda">
	<fieldset>
		<legend>Filtros de Extracci&oacute;n</legend>
		<table>
			<tr>
				<td colspan="7" align="left" class="pladnavegacion">Debe seleccionar al menos un campo como criterio de b&uacute;squeda.</td>
			</tr>
			<tr>
				<td>Tipo Reporte:</td>
				<td><s:select cssStyle="width:205px;" name="report" list="listaTipoReporte" listKey="keyTxt" listValue="value" headerValue="Seleccione..." headerKey="" emptyOption="false"></s:select></td>
			</tr>
			<tr>
				<td>Proceso:</td>
				<td><s:select cssStyle="width:205px;" name="proceso" list="listaProcesos" listKey="keyTxt" listValue="value" headerValue="Todos" headerKey="" emptyOption="false" onchange="habilitaMedioEnvio();"></s:select></td>
			</tr>
			<tr>
				<td></td>
				<td><div class="errordiv" id="div.proceso" style="color: #FF0000"></div></td>
			</tr>
			<tr>
				<td>Medio de Env&iacute;o:</td>
				<td><s:select cssStyle="width:205px;" id="idMedio" name="medio" list="medio"	listKey="keyTxt" listValue="value" headerValue="Ambos" headerKey="" emptyOption="false"></s:select></td>
			</tr>
			<tr>
				<td></td>
				<td><div class="errordiv" id="div.medio" style="color: #FF0000"></div></td>
			</tr>
			<tr>
				<td>Fecha Inicio (dd/mm/aa):</td>
				<td>
					<s:textfield name="fecInicio" id="fecInicio" type="text" style="width: 205px;" readonly="true"></s:textfield>
					<button id="show1up" type="button">
						<img src="images/calbtn.gif" width="18" height="18"	alt="Asigna Fecha"></img>
					</button>
					<div id="fechaWCCDlg" class="plad-calendar-dlg"></div>
				</td>
			</tr>
			<tr>
				<td></td>
				<td><div class="errordiv" id="div.fecha.inicio" style="color: #FF0000"></div></td>
			</tr>
			<tr>
				<td>Fecha Fin (dd/mm/aa):</td>
				<td>
					<s:textfield name="fecFin" id="fecFin" type="text" style="width: 205px;" readonly="true"></s:textfield>
					<button type="button" id="show" title="Asigna Fecha">
						<img src="images/calbtn.gif" width="18" height="18"	alt="Asigna Fecha"></img>
					</button>
					<div id="fechaEnteredWCDlg" class="plad-calendar-dlg"></div>
				</td>
			</tr>
			<tr>
				<td></td>
				<td><div class="errordiv" id="div.fecha.fin" style="color: #FF0000"></div></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><div class="errordiv" id="div.fecha.inicio.mayor" style="color: #FF0000"></div>
					<div class="errordiv" id="div.fecha.dias" style="color: #FF0000"></div>
				</td>
			</tr>
			<tr>
				<td colspan="2"><button type="button" id="buscar">Buscar</button></td>
			</tr>
		</table>
	</fieldset>
</s:form>