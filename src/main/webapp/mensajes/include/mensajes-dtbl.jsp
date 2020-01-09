<%@ include file="/taglibs.jsp"%>

<form class="pladForm">
	<fieldset>
		<legend>Registros</legend>
			<div class="plad-dtbl-container" style="width: 100%">
				<div class="pgnt-top-izquierda" style="font-size: 12px;">
					<button type="button" id="guardar">Guardar Cambios</button>
					<button type="button" id="agregar">Agregar</button>
				</div>
				<p>&nbsp;</p>
				<div class="errordiv" id="div.asignaciones.proceso" style="color: #FF0000"></div>
				<div class="pgnt-top-derecha">
					<div id="dtbl.Pgnt.Registros"></div>
				</div>
				<div class="pgnt-clear"></div>
				<div class="plad-dtbl" id="dtbl.Cont.Registros"></div>
				<div class="pgnt-bottom-izquierda">
					<div id="dtbl.Errr.Registros">&nbsp;</div>
				</div>
				<div class="pgnt-bottom-derecha">
					<div id="dtbl.Ttal.Registros">0</div>
						&nbsp;registro (s) encontrado(s)
				</div>
				<div class="pgnt-clear"></div>
			</div>
	</fieldset>
</form>