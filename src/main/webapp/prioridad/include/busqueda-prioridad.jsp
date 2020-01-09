<%@ include file="/taglibs.jsp"%>
<s:form theme="simple" cssClass="pladform" id="formBusqueda">
	<s:hidden name="procesoAsig" value="%{'0'}"></s:hidden>
	<div align="left" style="width: 70%">
	<h1 class="pladnavegacion">Catalogo de Prioridades</h1>
	<fieldset ><legend>B&uacute;squeda</legend>
	<table>
	<tr>
			<td colspan="7" align="left" class="pladnavegacion">
				Debe seleccionar al menos un campo como criterio de b&uacute;squeda.
			</td>
		</tr>
		<tr>
			<td>L&iacute;nea de Negocio:</td>
			<td><s:select cssStyle="width:205px;" name="idlob" list="listaLob" listKey="keyTxt" listValue="value" emptyOption="true"
				onchange="habilita()"></s:select></td>
				<td>
                        <button type="button" id="buscar" >
                            Buscar
                        </button>
                    </td>
		</tr>
		<tr>
                    <td>
                        &nbsp;
                    </td>
                </tr>
                <tr>
                    
                </tr>
	</table>
	</fieldset>
</s:form>
</div>