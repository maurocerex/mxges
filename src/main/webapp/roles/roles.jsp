<%@ include file="/taglibs.jsp"%>
<script type="text/javascript">
	Y.util.Event.onDOMReady(function() {

			Y.namespace("Roles");
			
			
			Y.Roles.RegistrosDtblURL = '<s:url action="roles-async" namespace="/" method="rolPantallas" includeParams="none" />';
			Y.Roles.ActPantUser = '<s:url action="roles-async" method="updatePantUsr" namespace="/" includeParams="none" />';
			
			initDtblRoles();
			
			
			
	});
</script>
<script type="text/javascript" src="${ctx}/roles/js/roles.js?version=${version}"></script>

<div align="left">

<h1 class="pladnavegacion">Administraci&oacute;n Seguridad</h1>
<s:form theme="simple" cssClass="pladform" id="rolesform">
	<s:hidden name="pantallasAsig" value="%{'0'}"></s:hidden>
	<fieldset style="width:75%;"><legend>Opciones de Consulta</legend>
	<table cellspacing="2" cellpadding="2">
		<tr>
			<td>ROL&nbsp;</td>
			<td><s:select cssStyle="width:205px;" name="idRol" list="roles" listKey="keyTxt" listValue="value" emptyOption="true"
				onchange="onChangeRoleId();"></s:select></td>
		</tr>
	</table>
	</fieldset>
</s:form>

			<form action=""  class="pladform" id="formLogin">
				<fieldset style="width:75%;">
					<legend>Roles/Permisos</legend>

					<div class="plad-dtbl-container">
	      				<div class="pgnt-top-izquierda" style="font-size:12px;">
	        				<button type="button" id="guardaUs" name="noimporta" value="">Guardar Cambios</button>
	      				</div>
	      				<p>&nbsp;</p>
	       				<div class="errordiv" id="div.asignaciones.proceso" style="color:#FF0000"></div>
	      				<div class="pgnt-top-derecha">
	        				<div id="dtbl.Pgnt.Registros"></div>
	      				</div>
	      				<div class="pgnt-clear"></div>
	      				<div class="plad-dtbl" id="dtbl.Cont.Registros"></div>
	      				<div class="pgnt-bottom-izquierda">
	        				<div id="dtbl.Errr.Registros">&nbsp;</div>
	      				</div>
	      				<div class="pgnt-bottom-derecha">
	        				<div id="dtbl.Ttal.Registros">0</div>&nbsp;registro (s) encontrado(s)
	        			</div>
	      				<div class="pgnt-clear"></div>
	    			</div>
					<p>&nbsp;</p>
				</fieldset>
			</form>
		</div>
		
		<div id="detalle" style="background-color:#F2F2F2; font-size:12px;font-family:Tahoma; width:400px; height:100px;" align="center" >
			<div class="hd">Detalle Rechazo</div>
			<div class="bd">
				<p>Cambios guardados correctamente.</p>
			</div>
		</div>