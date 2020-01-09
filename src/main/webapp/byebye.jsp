<%@ include file="/taglibs.jsp"%>
<html>

<head>
<title>Nuevos Mercados Logout</title>
<!--YUI CSS -->
<link rel="stylesheet" type="text/css"
	href="${ctx}/${yuiversion}/yui/build/reset-fonts-grids/reset-fonts-grids.css"></link>
<link rel="stylesheet" type="text/css"
	href="${ctx}/${yuiversion}/yui/build/fonts/fonts-min.css"></link>

<!--PLAD -->
<link rel="stylesheet" type="text/css"
	href="${ctx}/mxges/mxges-domain.css?version=${version}"></link>
<style type="text/css">

.gracias {
font-family:Arial, Helvetica, sans-serif;
font-size: 14px;
color:#a5a5a5;
text-align: center;
}

.usuario {
font-family:Arial, Helvetica, sans-serif;
font-size:14px;
color:#0089ca;
text-align:right;
} 
.usuario em {color:#86ba6f; font-style:normal}

</style>
</head>

<body class="yui-skin-sam">

<script language="JavaScript">

if (parent.frames.length > 0) 
{
parent.location.href = location.href;
}

</script>



<br></br>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<div align="center">
<table cellpadding="0" cellspacing="0" border="0" >
	<tr>
		<td align="center" width="262" height="44" bgcolor="#FFFFFF">
			<img src="${ctx}/images/meltsan1.png" />
		</td>
	</tr>
	<tr>
		<td height="44" bgcolor="#FFFFFF">
			<p class="gracias">
				Gracias por utilizar el Sistema de <br>Notificaciones
			</p>
		</td>
	</tr>
	<tr>
		<td align="center" height="44" bgcolor="#FFFFFF">
			<p class="usuario" style="font-weight:bold;">
				<em>Hasta Luego!</em>
			</p>
		</td>
	</tr>
</table>
</div>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<a href='<s:url action="login" method="input" namespace="/" includeParams="none" />' class="pladnavegacion" title="Entrar de nuevo">
<span>Entrar</span>
</a>


</body>



</html>
