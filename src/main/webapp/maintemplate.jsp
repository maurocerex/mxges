<%@ include file="/taglibs.jsp"%>

<%
	response.setHeader("Pragma", "no-cache");
	response.setHeader("pageEncoding", "UTF-8");
%>


<html>
<head>
<title>Nuevos Mercados</title>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache"></META>
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE"></META>
<META HTTP-EQUIV="Expires" CONTENT="-1"></META>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=UTF-8"></META>
<META HTTP-EQUIV="Cache-Control" CONTENT="NO-CACHE, must-revalidate"></META>
<%-- <link rel="stylesheet" type="text/css" href="${ctx}/css/plad.css"></link>  --%>
<!--CSS -->
<link rel="stylesheet" type="text/css" href="${ctx}/${yuiversion}/yui/build/reset-fonts-grids/reset-fonts-grids.css"></link>
<link rel="stylesheet" type="text/css" href="${ctx}/${yuiversion}/yui/build/fonts/fonts-min.css"></link>
<!--CSS CONTROLS-->
<link rel="stylesheet" type="text/css" href="${ctx}/${yuiversion}/${skins}/container.css"></link>
<link rel="stylesheet" type="text/css" href="${ctx}/${yuiversion}/${skins}/menu.css"></link>
<link rel="stylesheet" type="text/css" href="${ctx}/${yuiversion}/${skins}/button.css"></link>
<link rel="stylesheet" type="text/css" href="${ctx}/${yuiversion}/${skins}/autocomplete.css"></link>
<link rel="stylesheet" type="text/css" href="${ctx}/${yuiversion}/${skins}/datatable.css"></link>
<link rel="stylesheet" type="text/css" href="${ctx}/${yuiversion}/${skins}/tabview.css"></link>
<link rel="stylesheet" type="text/css" href="${ctx}/${yuiversion}/${skins}/calendar.css"></link>
<link rel="stylesheet" type="text/css" href="${ctx}/${yuiversion}/${skins}/logger.css"></link>
<link rel="stylesheet" type="text/css" href="${ctx}/${yuiversion}/${skins}/slider.css" />

<script type="text/javascript" src="${ctx}/${yuiversion}/yui/build/yahoo-dom-event/yahoo-dom-event.js"></script>
<script type="text/javascript" src="${ctx}/${yuiversion}/yui/build/element/element-min.js"></script>
<script type="text/javascript" src="${ctx}/${yuiversion}/yui/build/utilities/utilities.js"></script>
<script type="text/javascript" src="${ctx}/${yuiversion}/yui/build/datasource/datasource-min.js"></script>
<script type="text/javascript" src="${ctx}/${yuiversion}/yui/build/autocomplete/autocomplete-min.js"></script>
<script type="text/javascript" src="${ctx}/${yuiversion}/yui/build/container/container-min.js"></script>
<script type="text/javascript" src="${ctx}/${yuiversion}/yui/build/menu/menu-min.js"></script>
<script type="text/javascript" src="${ctx}/${yuiversion}/yui/build/button/button-min.js"></script>
<script type="text/javascript" src="${ctx}/${yuiversion}/yui/build/paginator/paginator-min.js"></script>
<script type="text/javascript" src="${ctx}/${yuiversion}/yui/build/datatable/datatable-debug.js"></script>

<script type="text/javascript" src="${ctx}/${yuiversion}/yui/build/json/json-min.js"></script>
<script type="text/javascript" src="${ctx}/${yuiversion}/yui/build/resize/resize-min.js"></script>
<script type="text/javascript" src="${ctx}/${yuiversion}/yui/build/layout/layout-min.js"></script>
<script type="text/javascript" src="${ctx}/${yuiversion}/yui/build/tabview/tabview-min.js"></script>
<script type="text/javascript" src="${ctx}/${yuiversion}/yui/build/logger/logger-min.js"></script>
<script type="text/javascript" src="${ctx}/${yuiversion}/yui/build/yuitest/yuitest-min.js"></script>
<script type="text/javascript" src="${ctx}/${yuiversion}/yui/build/calendar/calendar-min.js"></script>
<script type="text/javascript" src="${ctx}/${yuiversion}/yui/build/uploader/uploader-min.js"></script>
<script type="text/javascript" src="${ctx}/${yuiversion}/yui/build/animation/animation-min.js"></script>
<script type="text/javascript" src="${ctx}/${yuiversion}/yui/build/dragdrop/dragdrop-min.js"></script>
<script type="text/javascript" src="${ctx}/${yuiversion}/yui/build/slider/slider-min.js"></script>
<!--CHARTS -->
<script type="text/javascript" src="${ctx}/${yuiversion}/yui/build/swf/swf-min.js"></script>
<script type="text/javascript" src="${ctx}/${yuiversion}/yui/build/charts/charts-min.js"></script>


<script type="text/javascript">
var Y = YAHOO;
Y.namespace("dtblFn");
Y.ctx = '${ctx}';
Y.version = '1.0';
Y.ultimaact = 'XXXX';

/**
 * Log para la categoria de plad.
 */
Y.logplad = function(message) {
  Y.log(message, 'pladcategory');
};

</script>

<link rel="stylesheet" type="text/css" href="${ctx}/mxges/mxges-domain.css?version=${version}"></link>
<link rel="stylesheet" type="text/css" href="${ctx}/mxges/mxges-dtbl-pgnt.css?version=${version}"></link>
<link rel="stylesheet" type="text/css" href="${ctx}/mxges/mxges-menu.css?version=${version}"></link>
<link rel="stylesheet" type="text/css" href="${ctx}/mxges/mxges-exception.css?version=${version}"></link>
<link rel="stylesheet" type="text/css" href="${ctx}/${yuiversion}/${skins}/databl.css"></link>
<script type="text/javascript" src="${ctx}/mxges/mxges-menu.js?version=${version}"></script> 
<script type="text/javascript" src="${ctx}/mxges/mxges-wait.js?version=${version}"></script> 
<script type="text/javascript" src="${ctx}/mxges/mxges-exception.js?version=${version}"></script>
<script type="text/javascript" src="${ctx}/mxges/mxges-message.js?version=${version}"></script> 
<script type="text/javascript" src="${ctx}/mxges/mxges-dtbl-pgnt.js?version=${version}"></script> 
<script type="text/javascript" src="${ctx}/mxges/mxges-scroll-dtbl-pgnt.js?version=${version}"></script> 
<script type="text/javascript" src="${ctx}/mxges/mxges-utils.js?version=${version}"></script> 
<script type="text/javascript" src="${ctx}/mxges/mxges-async.js?version=${version}"></script> 

<script type="text/javascript">
Y.redirects = {};

Y.redirects['usuarios'] = '<s:url action="usuarios" method="input" includeParams="none"></s:url>';
Y.redirects['roles'] = '<s:url action="roles" method="input" includeParams="none"></s:url>';
Y.redirects['mensajes'] = '<s:url action="mensajes" method="input" includeParams="none"></s:url>';
Y.redirects['prioridad'] = '<s:url action="prioridad" method="input" includeParams="none"></s:url>';
Y.redirects['notificaciones'] = '<s:url action="notificaciones" method="input" includeParams="none"></s:url>';
Y.redirects['reportes'] = '<s:url action="reportes" method="input" includeParams="none"></s:url>';
Y.redirects['cifras'] = '<s:url action="cifras" method="input" includeParams="none"></s:url>';
Y.redirects['exclusiones'] = '<s:url action="exclusiones" method="input" includeParams="none"></s:url>';
Y.redirects['cancelaciones'] = '<s:url action="cancelaciones" method="input" includeParams="none"></s:url>';
Y.redirects['smsConfig'] = '<s:url action="sms-config" method="input" includeParams="none"></s:url>';
Y.redirects['administra'] = '<s:url action="administra" method="input" includeParams="none"></s:url>';

Y.redirects['menuasync'] = '<s:url action="menu-async" namespace="/" includeParams="none" />';
Y.redirects['ayudaasync'] = '<s:url action="ayuda-async" namespace="/" includeParams="none" />';

function getMainRedirectForm() {
  try {
    return getForm("mainredirect");
  } catch (e) {
    alert("getMainRedirectForm: " + e.description);
  }
}
</script>    

</head>
  
<body class="yui-skin-sam" id="yahoo-com">


  <table align="center" width="100%" border="0" cellpadding="1" cellspacing="1">
    <tr>
      <td>
        <s:form theme="simple" id="mainredirect"></s:form>
        <s:form theme="simple" id="ayudaredirect"><s:hidden name="pladActionId" value="%{pladActionId}"></s:hidden></s:form>
      </td>
    </tr>
    <tr id="body">
      <td style="padding-left:2px;">
        <tiles:insertAttribute name="body"></tiles:insertAttribute>
      </td>
    </tr>
    <c:if test="${createlog == true}">
    <tr>
      <td style="padding-left:2px;">
        <img src='${ctx}/mxges/images/showlog.gif' id="showlogimg"/> 
        <div id="yplalog" style="text-align: left;"></div>
      </td>
    </tr>  
    </c:if>  
  </table>
  <div id="exceptiondiv" class="errorMessage" style="display: inline;color:#FF0000"></div>
  
<c:if test="${createlog == true}">
<script type="text/javascript">

Y.logReader = new Y.widget.LogReader("yplalog", {
  newestOnTop : false, width:'400px' , verboseOutput:true 
});
Y.logReader.setTitle("Y log");
Y.logReader.collapse();
Y.logReaderHidden = false;

Y.util.Event.addListener("showlogimg", "click", function() {
  if (Y.logReaderHidden) {
    Y.logReader.show();
    Y.logReaderHidden = false;
  } else {
    Y.logReader.hide();
    Y.logReaderHidden = true;
  }
});

<c:if test="${hidelog == true}">
	Y.logReader.hide();
	Y.logReaderHidden = true;
</c:if>


</script>
</c:if>

</body>  
</html>