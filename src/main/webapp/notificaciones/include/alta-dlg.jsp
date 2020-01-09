<%@ include file="/taglibs.jsp"%>
<div id="AltaDlg" style="font-size: 12px; font-family: Tahoma;" align="left">
<div class="hd" align="center">Agregar</div>
<div class="bd">

<div align="left" id="container" class="yui-navset">
<ul class="yui-nav">
	<li class="selected"><a href="#tab4"><em>Generales</em></a></li>
	<li><a href="#tab5"><em>Particularidades</em></a></li>
	<li><a href="#tab6"><em>Frecuencia</em></a></li>
</ul>
<div class="yui-content">
<div id="tab4"><jsp:include page="generales.jsp" /></div>
<div id="tab5"><jsp:include page="particularidades.jsp" /></div>
<div id="tab6"><jsp:include page="frecuencia.jsp" />
<p>&nbsp;</p>
</div>
</div>
</div>

<p>&nbsp;</p>
<div align="center">
<button type="button" id="aceptar">Guardar</button>
<button type="button" id="cancelar">Cancelar</button>
</div>
</div>
</div>