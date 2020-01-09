<%@ include file="/taglibs.jsp"%>
<s:form theme="simple" cssClass="pladform" id="formBusqueda">
	<input type="hidden" id="retH" name="retH" />
	<fieldset>
		
		<table>
			<tr>
				<td>Retenedor:</td>
				<td>&nbsp;</td>
				<td>
					<s:textfield id="retenedor" name="retenedor" cssStyle="width:300px;" maxlength="100" value="%{''}" disabled="false" ></s:textfield>
					<button type="button" id="btnCleanRet" title="Limpiar Retenedor/UP" onclick="limpiaRet()">
						<img src="${ctx}/images/goma.png" width="18" height="18" alt="Limpiar Retenedor" />
					</button>
				</td>
				<td>&nbsp;</td>
				<td><button type="button" id="buscar">Buscar</button></td>
				<td></td>
			</tr>
			<tr>
				<td colspan="6"><div class="errordiv" id="div.retenedor.empty" style="color: #FF0000"></div></td>
			</tr>
			<tr>
				<td>Unidad de Pago:</td>
				<td>&nbsp;</td>
				<td colspan="4">
					<select name="unidadPago" id="unidadPago" style="width: 400px;" ></select>
				</td>
			</tr>
			<tr>
				<td colspan="6">&nbsp;</td>
			</tr>
			<tr>
				<td>Retenedor CT:</td>
				<td>&nbsp;</td>
				<td>
					<s:textfield id="retenedorCT" name="retenedorCT" cssStyle="width:300px;" maxlength="100" value="%{''}" disabled="false"></s:textfield>
					<button type="button" id="btnCleanRetCT" title="Limpiar Retenedor/UP" onclick="limpiaRetCT()">
						<img src="${ctx}/images/goma.png" width="18" height="18" alt="Limpiar Retenedor" />
					</button>
				</td>
				<td>&nbsp;</td>
				<td><button type="button" id="buscarCt">Buscar</button></td>
				<td></td>
			</tr>
			<tr>
				<td colspan="6"><div class="errordiv" id="div.retenedorCT.empty" style="color: #FF0000"></div></td>
			</tr>
			<tr>
				<td>Unidad de Pago CT:</td>
				<td>&nbsp;</td>
				<td colspan="4">
					<select name="unidadPagoCt" id="unidadPagoCt" style="width: 400px;"></select>
				</td>
				
			</tr>
			<tr>
				<td colspan="6">&nbsp;</td>
			</tr>
		</table>
		<table>
			<tr>
				<td>Fecha Inicio (dd/mm/aa):</td>
				<td>&nbsp;</td>
				<td>
					<s:textfield name="fecInicio" id="fecInicio" type="text" style="width: 100px;" readonly="true"></s:textfield>
					<button id="show1up" type="button">
						<img src="images/calbtn.gif" width="18" height="18"	alt="Asigna Fecha"></img>
					</button>
					<div id="fechaWCCDlg" class="plad-calendar-dlg"></div>
				</td>
				<td>Fecha Fin (dd/mm/aa):</td>
				<td>&nbsp;</td>
				<td>
					<s:textfield name="fecFin" id="fecFin" type="text" style="width: 100px;" readonly="true"></s:textfield>
					<button type="button" id="show">
						<img src="images/calbtn.gif" width="18" height="18"	alt="Asigna Fecha"></img>
					</button>
					<div id="fechaEnteredWCDlg" class="plad-calendar-dlg"></div>
				</td>
				<td>&nbsp;</td>
				<td>
					<button type="button" id="guardar">Guardar</button>
				</td>
			</tr>
			<tr>
				<td><div class="errordiv" id="div.fecha.inicio" style="color: #FF0000"></div></td>
			</tr>
			<tr>
				<td><div class="errordiv" id="div.fecha.fin" style="color: #FF0000"></div></td>
			</tr>
			<tr>
				<td>
					<div class="errordiv" id="div.fecha.inicio.mayor" style="color: #FF0000"></div>
					<div class="errordiv" id="div.fecha.dias" style="color: #FF0000"></div>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<div class="errordiv" id="div.Canceladas" style="color: #FF0000"></div>
				</td>
			</tr>
		</table>
	</fieldset>	
</s:form>