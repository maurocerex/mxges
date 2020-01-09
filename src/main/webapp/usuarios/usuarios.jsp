<%@ include file="/taglibs.jsp"%>

<script type="text/javascript">
	Y.util.Event.onDOMReady(function() {

				Y.namespace("UsuariosAbc");

				Y.UsuariosAbc.VigenciaCatalog = [];
				Y.UsuariosAbc.VigenciaCatalog.push({
					label : 'SI',
					value : 'A'
				});
				Y.UsuariosAbc.VigenciaCatalog.push({
					label : 'NO',
					value : 'I'
				});

				Y.UsuariosAbc.RegistrosDtblURL = '<s:url action="usuarios-async" namespace="/" includeParams="none" />';
				Y.UsuariosAbc.ValidarURL = '<s:url action="usuarios-async" method="validar" namespace="/" includeParams="none" />';
				Y.UsuariosAbc.GuardarCambiosURL = '<s:url action="usuarios-async" method="guardarCambios" namespace="/" includeParams="none" />';
				initDtblUsuariosAbc();

				Y.UsuariosAbc.RegistrosDtbl.update();
				
				

	});
</script>

<script type="text/javascript" src="${ctx}/usuarios/js/usuarios.js?version=${version}"></script>

<h1 class="pladnavegacion">Usuarios del Sistema.</h1>
<div align="left">
	<s:form theme="simple" cssClass="pladform" id="formUsuariosAbc">
		<fieldset style="width:75%;">
			<legend>Usuarios</legend>
			<jsp:include page="include/usuarios-dtbl.jsp" />
			<p>&nbsp;</p>
		</fieldset>
	</s:form>
</div>

<s:form theme="simple" cssClass="pladform" id="UsuariosAbcForm">
    <s:hidden name="jsonrecords"  value="%{''}" />
</s:form>

<jsp:include page="include/detalle-dlg.jsp" />

