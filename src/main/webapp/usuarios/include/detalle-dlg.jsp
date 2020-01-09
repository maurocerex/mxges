<%@ include file="/taglibs.jsp"%>

<div id="editar" style="font-size:12px;font-family:Tahoma;"  >
	<div class="hd" align="center">Editar Usuario</div>
  	<div class="bd" >
  	<s:form theme="simple" id="DetalleDlgForm">
  		<s:hidden name="record.id"  value="%{''}" />
        <s:hidden name="parametro.nuevo" value="%{'false'}" />
    	<table >
    		<tr>        
          		<td>
            		L&iacute;nea de Negocio:
          		</td>          
          		<td>
          			<s:select cssStyle="width:205px;" name="parametro.idlob" list="lobs" listKey="keyTxt" listValue="value" ></s:select>&nbsp;
            		<div class="errordiv" id="div.idlob" style="color:#FF0000"></div>
          		</td> 
        	</tr>
        	<tr><td>&nbsp;</td></tr>
        	<tr>        
          		<td>
            		Username:
          		</td>          
          		<td>
          			<s:textfield id="username" cssStyle="width:205px;" name="parametro.username" maxLength="50"/>
                    <div class="errordiv" id="div.username" style="color:#FF0000"></div>
          		</td> 
        	</tr>
        	<tr><td>&nbsp;</td></tr>
         	<tr>
          		<td>
            		Nombre:
          		</td>
          		<td>
            		<s:textfield cssStyle="width:205px;" name="parametro.nombre" maxLength="100"/>
                    <div class="errordiv" id="div.nombre" style="color:#FF0000" ></div>
          		</td> 
        	</tr>   
        	<tr><td>&nbsp;</td></tr>       
         	<tr>
          		<td>
            		Vigente:
          		</td>
          		<td>
            		<select name="parametro.vigencia" style="width:205px;">
            			<option value="A">SI</option>
            			<option value="I">NO</option>
            		</select>
            		<div class="errordiv" id="div.vigencia" style="color:#FF0000"></div>
          		</td> 
        	</tr>
        	<tr><td>&nbsp;</td></tr>
         	<tr>
          		<td>
            		Rol:
          		</td>
          		<td>
            		<s:select cssStyle="width:205px;" name="parametro.rolId" list="roles" listKey="keyTxt" listValue="value" emptyOption="true"></s:select>&nbsp;
                    <div class="errordiv" id="div.rolId" style="color:#FF0000"></div>
          		</td>
        	</tr>
      	</table>
      	<p>&nbsp;</p>
   		<div align="center">
   			<button type="button" id="guardaredit" name="noimporta" value="">Aceptar</button>
			<button type="button" id="cancelaredit" name="noimporta" value="">Cancelar</button>
		</div>  
		</s:form>   
  	</div>
</div>
