<%@ include file="/taglibs.jsp"%>

<%@ page contentType="text/html; charset=UTF-8"%>

<html>
<head>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Expires" CONTENT="-1" />
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1252" />
<META HTTP-EQUIV="Expires" CONTENT="-1" />
<style type="text/css">

.fecha {
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

.salir span {display:none; }

.salir {
	position:absolute;

	top:2px;
	width:83px;
	height:46px;
	display:block;
	background-image:url(${ctx}/images/salir.png); 
}

.salir:hover {
	background-position:0px -46px;
}


</style>

</head>
<body>

<table cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr>
		<td align="left" width="262px" height="44" bgcolor="#FFFFFF"><img src="${ctx}/images/meltsan1.png" /></td>
		<td align="left" width="210px" height="44" bgcolor="#FFFFFF">
			
		<p class="fecha">
		Sistema de Notificaciones<br>	

		Versi&oacute;n 5.0 <br>
		</p>
		</td>
		<td align="left" width="100px" height="44">
			<p class="usuario"> <!-- s:property value="%{#session.BASESID}"/--></p>
		</td>
		<td align="left" width="212px" height="44" bgcolor="#FFFFFF">
		<p class="usuario" style="font-weight:bold;">Bienvenido <em><s:property value="%{#session.USER.nombreCompleto}"></s:property></em>
			<br>
			<a href="${ctx}/logout.action" class="salir" title="Salir"><span>Salir</span></a>
		</p>

		</td>
	</tr>
</table>

</body>

</html>
