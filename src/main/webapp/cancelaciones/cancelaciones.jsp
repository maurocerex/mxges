<%@ include file="/taglibs.jsp"%>
<script type="text/javascript">
	Y.util.Event.onDOMReady(function() {
		Y.namespace("Cancelaciones");
		Y.Cancelaciones.RetenedoresURL = '<s:url action="cancelaciones-async" method="busqueda" namespace="/" includeParams="none" />';
		Y.Cancelaciones.CancelaNotURL = '<s:url action="cancelaciones-async" method="cancelaNot" namespace="/" includeParams="none" />';
	});
</script>
<script type="text/javascript" src="${ctx}/cancelaciones/js/cancelaciones.js?version=${version}"></script>
<div align="left" style="width: 70%">
	<h1 class="pladnavegacion">Cancelaciones de Notificaciones</h1>
</div>
<s:form theme="simple" id="cancelaNotif" cssClass="pladform">
	<s:hidden name="idRetenedor" value="%{''}"></s:hidden>
	<s:hidden name="idUnidadPago" value="%{''}"></s:hidden>
	<s:hidden name="idRetenedorCT" value="%{''}"></s:hidden>
	<s:hidden name="idUnidadPagoCT" value="%{''}"></s:hidden>
	<s:hidden name="fechaIni" value="%{''}"></s:hidden>
	<s:hidden name="fechaFin" value="%{''}"></s:hidden>
</s:form>
<div id="container" class="yui-navset" style="width: 70%" align="left">
	<jsp:include page="include/busqueda-cancelaciones.jsp"></jsp:include>
	<s:form theme="simple" cssClass="pladform" id="formCancela">
		
	</s:form>
</div>