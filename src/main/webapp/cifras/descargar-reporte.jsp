<%@include file="/taglibs.jsp"%>

<s:url id="tempUrl" action="cifras-reporte" method="descargar" escapeAmp="false">
 <s:param name="proceso" > <s:property value="%{proceso}" /> </s:param>
<s:param name="medio"   > <s:property value="%{medio}" /> </s:param>
<s:param name="fecInicio"  > <s:property value="%{fecInicio}" /></s:param>
<s:param name="fecFin"  > <s:property value="%{fecFin}" /></s:param>
<s:param name="tipoReport"  > <s:property value="%{tipoReport}" /></s:param>
</s:url>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta http-equiv="Refresh" content="2;URL=<s:property value="%{tempUrl}" />">
<link rel="stylesheet" type="text/css" href="${ctx}/mxges/mxges-domain.css?version=${version}"></link>
<script language="JavaScript" type="text/javascript">
function descargar(){
  window.location = '<s:property value="%{tempUrl}" />';
  return false;
}
</script>
</head>
<body class="yui-skin-sam" id="yahoo-com">
<div class="messagebox">El archivo se esta descargando. Por favor espere</div>

</body>
</html>