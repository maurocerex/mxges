<%@ include file="/taglibs.jsp"%>

<%
	response.setHeader("Pragma", "no-cache");
%>


<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100">
	<tr>
		<td>
		<table border="0" cellpadding="0" cellspacing="0" style="font-family: Arial; font-size: 7.0pt;">
			<tr>
				<td width="250" height="19">Bienvenido :</td>
				<td width="250"><s:property value="%{#session.USER.nombre}"></s:property> &nbsp; <s:property value="%{#session.USER.email}"></s:property></td>
			</tr>
		</table>
		</td>
	</tr>
</table>