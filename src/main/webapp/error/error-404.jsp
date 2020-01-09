<html>

<head>
<style type="text/css">

			.fecha {
			font-family:Arial, Helvetica, sans-serif;
			font-size: 14px;
			color:#a5a5a5;
			text-align: right;
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
<script type="text/javascript">
function delayedRedirect(){
    window.location ="<%=request.getContextPath()%>/login!input.action";
}
</script>
<body onLoad="setTimeout('delayedRedirect()', 5000)">
<center>
		<table cellpadding="0" cellspacing="0" border="0" >
			<tr>
				<td align="left" width="262" height="44" bgcolor="#FFFFFF">
					<img src="images/metlife.png" /> 
					
				</td>
				<td align="left" width="126" height="44" bgcolor="#FFFFFF">
					<p class="fecha">Nuevos Mercados</p>
				</td>
				<td align="left" width="100" height="44">
					<!--p class="usuario">BD: </p-->
				</td>
				<td align="left" width="212" height="44" bgcolor="#FFFFFF">
					<p class="usuario" style="font-weight:bold;">
						MetLife <em>Nuevos Mercados</em>
					</p>

				</td>
			</tr>
		</table>
</center>


<div style="text-align: center">
	<br>
	<br>
	<br>
	<p>
		Solicitud de Página Erronea. Verifique la P&aacute;gina Solicitada
	</p>
	<br>
	
</div>


</body>

</html>