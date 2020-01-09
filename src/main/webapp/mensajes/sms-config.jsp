<%@include file="/taglibs.jsp"%>

<script> 

Y.util.Event.onDOMReady(function() {
  Y.ModificaURL = '<s:url action="sms-config-async" method="guardar" namespace="/" includeParams="none" />';
});
</script> 

<script type="text/javascript" src="${ctx}/mensajes/js/sms-config.js?version=${version}"></script>

<h1 class="pladnavegacion">Configuraci&oacute;n SMS´S</h1>

<s:form theme="simple" cssClass="pladForm" id="sms-form" style="" onSubmit="return false;">
<fieldset style="margin: 10px 10px 10px 0px; width: 60%;">
  <legend class="pladnavegacion">Activar/Desactivar acentos</legend>
  <table style="width: 100%; margin: 10px 10px 10px 10px;">
    <tr>
      <td>
        <s:checkbox name="acento" id="108" value="acento" cssStyle="width:500px;">¿Envíar Acentos?</s:checkbox>
        <div class="errordiv" id="div.acentos" style="color:#FF0000"></div>
      </td>
    </tr>      
    <tr>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
	<tr>
	  <td>
	    <button type="button" id="ModificarBtn">Guardar Cambios</button>
	  </td>
	  <td>&nbsp;</td>
	</tr>
  </table>
</fieldset>
</s:form>
