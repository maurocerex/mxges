<%@ include file="/taglibs.jsp"%>
<div id="detalle" style="font-size: 12px; font-family: Tahoma;" align="center">
<div class="hd">Detalle Mensaje</div>
<div class="bd">
<table>
	<tr>
		<td>Id Mensaje:</td>
		<td><input type="text" style="width: 205px" disabled="disabled"></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>Descripci&oacute;n:</td>
		<td><input type="text" style="width: 205px"> </input></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>L&iacute;nea de Negocio:</td>
		<td><select style="width: 205px">
			<option>Individual de Gobierno</option>
		</select></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>Medio de Env&iacute;o:</td>
		<td><select style="width: 205px">
			<option>SMS</option>
			<option>E-mail</option>
		</select></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>Proceso:</td>
		<td><select style="width: 205px">
			<option>Previo de Cancelaci&oacute;n 1</option>
			<option>Previo de Cancelaci&oacute;n 2</option>
			<option>Cancelaci&oacute;n definitiva</option>
			<option>Suspensi&oacute;n de Descuentos</option>
			<option>Cobranza Bancaria</option>
		</select></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>Status:</td>
		<td><select style="width: 205px">
			<option>Activo</option>
			<option>Inactivo</option>

		</select></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>Asunto:</td>
		<td><input type="text" style="width: 205px" /></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>Mensaje:</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td colspan="2"><textarea id="mensajeDet" rows="7" cols="" style="width: 100%"></textarea>
		<div id="charsDet" align="right" class="pgnt-bottom-derecha">Car&aacute;cter(es): 0</div>
		</td>
	</tr>
</table>
<p>&nbsp;</p>
<form class="pladForm">
<fieldset style="background-color: #F2F2F2"><legend> Variables Permitidas en Formato </legend>
<div class="plad-dtbl-container" style="background-color: #F2F2F2">
<div class="errordiv" id="div.asignaciones.proceso" style="color: #FF0000"></div>
<div class="pgnt-top-derecha">
<div id="dtbl.Pgnt.RegistrosDetalle"></div>
</div>
<div class="pgnt-clear"></div>
<div class="plad-dtbl" id="dtbl.Cont.RegistrosDetalle"></div>
<div class="pgnt-bottom-izquierda">
<div id="dtbl.Errr.Registros">&nbsp;</div>
</div>
<div class="pgnt-bottom-derecha">
<div id="dtbl.Ttal.RegistrosDetalle">0</div>
&nbsp;registro (s) encontrado(s)</div>
<div class="pgnt-clear"></div>
</div>
</fieldset>
</form>
<div align="center">
<button type="button" id="guardardetalle">Guardar</button>
<button type="button" id="cancelardetalle">Cancelar</button>
</div>
</div>
</div>
