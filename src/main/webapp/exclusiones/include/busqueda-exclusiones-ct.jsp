<%@ include file="/taglibs.jsp"%>
	<input type="hidden" id="retHCt" name="retHCt" />
	<fieldset><legend> B&uacute;squeda</legend>
	<table>
	<tbody>
		<tr>
			<td>Retenedor</td>
			<td>&nbsp;</td>
			<td><s:textfield id="retenedorCt" name="retenedorCt" cssStyle="width:300px;" maxlength="100" value="%{''}" disabled="true"></s:textfield>
			<button type="button" id="btnCleanRetCt" title="Limpiar Retenedor/UP" onclick="limpiaCt()"><img src="${ctx}/images/goma.png" width="18" height="18"
				alt="Limpiar Retenedor" /></button>
			</td>
			<td>&nbsp;</td>
			<td>
			<button type="button" id="buscar">Buscar</button>
			</td>
		</tr>
		</tbody>
	</table>
	</fieldset>