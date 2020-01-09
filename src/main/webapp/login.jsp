<%@ include file="/taglibs.jsp"%>
<html>
    <head>
    	<title>Sistema de Notificaciones</title>
    	<META HTTP-EQUIV="Pragma" CONTENT="no-cache"></META>
		<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE"></META>
		<META HTTP-EQUIV="Expires" CONTENT="-1"></META>
		<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=UTF-8"></META>
		<META HTTP-EQUIV="Cache-Control" CONTENT="NO-CACHE, must-revalidate"></META>
		
    	<link rel="stylesheet" type="text/css" href="yui_2.8.1/yui/build/reset-fonts-grids/reset-fonts-grids.css"></link>
		<link rel="stylesheet" type="text/css" href="yui_2.8.1/yui/build/fonts/fonts-min.css"></link>
		<link rel="stylesheet" type="text/css" href="yui_2.8.1/yui/build/assets/skins/plad/button.css"></link>
		<link rel="stylesheet" type="text/css" href="yui_2.8.1/yui/build/assets/skins/plad/datatable.css"></link>
		
		
		<script type="text/javascript" src="yui_2.8.1/yui/build/yahoo-dom-event/yahoo-dom-event.js"></script>
		<script type="text/javascript" src="yui_2.8.1/yui/build/utilities/utilities.js"></script>
		<script type="text/javascript" src="yui_2.8.1/yui/build/button/button-min.js"></script>
		<script type="text/javascript" src="yui_2.8.1/yui/build/datatable/datatable-debug.js"></script>
		
		<script type="text/javascript">
			var Y = YAHOO;
			Y.namespace("dtblFn");
		</script>

		<link rel="stylesheet" type="text/css" href="mxges/mxges-domain.css"></link>
		
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
	<body class="yui-skin-sam">
		<p>&nbsp;</p>
		<div style="width:100%" align="center">
		<table cellpadding="0" cellspacing="0" border="0" >
			<tr>
				<td align="left" width="262" height="44" bgcolor="#FFFFFF">
					<img src="images/meltsan1.png" />
					
				</td>
				<td align="center" width="170" height="44" bgcolor="#FFFFFF">
					<p class="fecha">Sistema de Notificaciones</p>
				</td>
				<td align="center" width="80" height="44">
					<!--p class="usuario">BD: </p-->
				</td>
				<td align="right" width="212" height="44" bgcolor="#FFFFFF">
					<p class="usuario" style="font-weight:bold;">
						<em>Meltsan Solutions</em>
					</p>

				</td>
			</tr>
		</table>
		</div>
		<p>&nbsp;</p>
		<p>&nbsp;</p>
		<p>&nbsp;</p>
		
		<div align="center">
			<s:form theme="simple" cssClass="pladform" id="formLogin" action="login.action">
				<fieldset style="width: 350px;">
					<legend>M&oacute;dulo de Autenticaci&oacute;n</legend>
					<table cellspacing="2" cellpadding="2">
						<tr>
							<td>Usuario</td>
							<td style="padding-left: 2px;">
								<s:textfield name="username" size="10" maxlength="20" cssStyle="text-transform: uppercase;" />
								<s:fielderror fieldName="username"/>
							</td>
						</tr>
						<tr><td></td></tr>
						<tr>
							<td>Password</td>
							<td style="padding-left: 2px;">
								<s:password name="password" size="10" maxlength="20" onkeyup="if (event.keyCode == 13)this.form.submit();" />
								<s:fielderror fieldName="password"/>
							</td>
						</tr>
						<tr><td></td></tr>
						<tr>
							<td colspan="2" align="center">
								<button type="button" id="btnAceptarLogin">Aceptar</button>
							</td>
						</tr>
					</table>
				</fieldset>
				<table align="center">
					<tr>
						<td>
							<s:fielderror fieldName="msg"/>
						</td>
					</tr>
					<tr>
						<td>
							<s:fielderror fieldName="retryCount"/>
						</td>
					</tr>
				</table>
			</s:form>
		</div>
		
		<p>&nbsp;</p>
		<div style="width:100%" align="center">
		<table >
			<tr>
				<td align="center">
					<a href="#" class="pladnavegacion"	onclick="window.open('<s:property value="forgotPwdUrl"/>','mywindow');return false;">
						&iquest;Olvid&oacute; su contrase&ntilde;a?
					</a>
				</td>
			</tr>
			<tr>
				<td align="center">
					<a href="#" class="pladnavegacion" onclick="window.open('<s:property value="changePwdUrl"/>','mywindow');return false;">
						&iquest;Desea modificar su contrase&ntilde;a?
					</a>
				</td>
			</tr>
		</table>
		</div>
		<script language="JavaScript" type="text/javascript">
			Y.util.Event.onDOMReady(function() {
				new Y.widget.Button("btnAceptarLogin", {
					onclick : {
					fn : function(){
						document.getElementById('formLogin').submit();
						}
					}
				});
			});
		</script>
		
	</body>
</html>
