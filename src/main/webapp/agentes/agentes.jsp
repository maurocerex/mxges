<%@ include file="/taglibs.jsp"%>
		<script type="text/javascript">
		Y.util.Event.onDOMReady(function() {

                     
            
			
			
			
					
	
			YAHOO.example.Datas = {
			    bookorders: [
			        {clave:"1", claveAgente:"1231", nombreAgente:"Lance Galipeau", celular:"6234524", email:"lance@hotmail.com", status:"Activo", eliminar:"", editar:""}	
			    ]
			}
			var data = {clave:"2", claveAgente:"123", nombreAgente:"Nannie Parkey", celular:"5234123", email:"nannie@metlife.com", status:"Activo", eliminar:"", editar:""}
			    YAHOO.Basics = function() {
			        var myColumnDefs = [
			            {key:"clave", label:"CLAVE PROMOTORIA", sortable:true, resizeable:true},
			            {key:"claveAgente", label:"CLAVE AGENTE", formatter:YAHOO.widget.DataTable.formatDate, sortable:true, sortOptions:{defaultDir:YAHOO.widget.DataTable.CLASS_DESC},resizeable:true},
			            {key:"nombreAgente", label:"NOMBRE AGENTE", sortable:true, resizeable:true},
						{key:"celular", label:"TELEFONO CELULAR", sortable:true, resizeable:true},
						{key:"email", label:"E-MAIL", sortable:true, resizeable:true},
						{key:"status", label:"STATUS", sortable:true, resizeable:true,  formatter: function(el, oRecord, oColumn, oData) {
                            el.innerHTML = '<select style="width:70px;"><option>Activo</option><option>Inactivo</option></select>';}},
						{key:"editar", label:"DETALLE", sortable:true, resizeable:true, width:100, formatter: function(el, oRecord, oColumn, oData) {
                            el.innerHTML = '<input type="image" id="detalle" src="images/edit.gif" onClick="return false;">';
            				
        				}}
			            
			        ];
			
			        var myDataSource = new YAHOO.util.DataSource(YAHOO.example.Datas.bookorders);
			        myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSARRAY;
			        myDataSource.responseSchema = {
			            fields: ["clave","claveAgente","nombreAgente","celular", "email", "status", "eliminar", "editar"]
			        };
			
			        var myDataTable = new YAHOO.widget.DataTable("basics",
			                myColumnDefs, myDataSource);
			                
			       
			        Y.btnGuardar = new Y.widget.Button("guardar", {
				        onclick: {
				            fn: function(){
				            	var record = YAHOO.widget.DataTable._cloneObject(data);
				            	myDataTable.addRow(record);				            	 
				            	Y.DetalleDlg.hide();
				            }
				        },
				        disabled: false
				    });
			        
			        myDataTable.subscribe("buttonClickEvent", function(oArgs){
			            var target = oArgs.target;
			            var record = this.getRecord(target);
			            
			            if(target.id=="borrar"){			            	
			            	if(myDataTable.getRecordSet().getLength() > 0) { 
			            		myDataTable.deleteRow(0); 
			            	} 
			            }else if(target.id=="detalle"){
			            	Y.DetalledtblDlg.show();
			        	}
			            
			            //Y.DetalleDlg.show();
			        });
			        
			        
			        return {
			            oDS: myDataSource,
			            oDT: myDataTable
			        };
			    }();
			    
			    
			    Y.DetalleDlg = new Y.widget.Panel("DetalleDlg", {
				      width:"28em",
				      fixedcenter : "containted",
				      modal : true,
				      visible : false,
				      constraintoviewport : false,
				      draggable:true
				    });
				  var kl = new Y.util.KeyListener(document, {
				      keys : 27
				    }, {
				      fn : Y.DetalleDlg.hide,
				      scope : Y.DetalleDlg,
				      correctScope : true
				    });
				    kl.enable();
				    
				    Y.DetalleDlg.render();
				    
				    Y.DetalledtblDlg = new Y.widget.Panel("Detalletable", {
				    	width:"27em",
					      fixedcenter : "containted",
					      modal : true,
					      visible : false,
					      constraintoviewport : false,
					      draggable:true
					    });
					  var kl = new Y.util.KeyListener(document, {
					      keys : 27
					    }, {
					      fn : Y.DetalledtblDlg.hide,
					      scope : Y.DetalledtblDlg,
					      correctScope : true
					    });
					    kl.enable();
					    
					    Y.DetalledtblDlg.render();
				    
					    Y.DlgGuardar = new Y.widget.Panel("Dlguardar", {
						      
						      fixedcenter : true,
						      modal : true,
						      visible : false,
						      constraintoviewport : false,
						      draggable:true
						    });
						  var kl = new Y.util.KeyListener(document, {
						      keys : 27
						    }, {
						      fn : Y.DlgGuardar.hide,
						      scope : Y.DlgGuardar,
						      correctScope : true
						    });
						    kl.enable();
						    
						    Y.DlgGuardar.render();
						    
			    Y.DetalleDlgAgregar = function(record){
			        try {
			           
			            Y.DetalleDlg.show();
			        } catch (e) {
			            alert("DetalleDlgEditar:" + e.description);
			        }
			    };
			    
			    
			    Y.btnReasignar = new Y.widget.Button("buttonAsignar", {
			        onclick: {
			            fn: function(){
			            	Y.DetalleDlg.show();
			            }
			        },
			        disabled: false
			    });
	
			    
			    
			    Y.btnCancelar = new Y.widget.Button("cancelar", {
			        onclick: {
			            fn: function(){
			            	Y.DetalleDlg.hide();
			            }
			        },
			        disabled: false
			    });
			    
			    Y.btnCancelara = new Y.widget.Button("cancelara", {
			        onclick: {
			            fn: function(){
			            	Y.DetalledtblDlg.hide();
			            }
			        },
			        disabled: false
			    });
			    
			    Y.btnGuardara = new Y.widget.Button("guardara", {
			        onclick: {
			            fn: function(){
			            	Y.DetalledtblDlg.hide();
			            }
			        },
			        disabled: false
			    });
			    
			    Y.btnBuscar = new Y.widget.Button("buscar", {
			        onclick: {
			            fn: function(){
			            	
			            }
			        },
			        disabled: false
			    });
			    
			    Y.btnGuardarCamb = new Y.widget.Button("guardarcmb", {
			        onclick: {
			            fn: function(){
			            	Y.DlgGuardar.show();
			            }
			        },
			        disabled: false
			    });
            });
			
			</script>
			

<div align="left">
	<h1 class="pladnavegacion">Catalogo Agentes</h1>
	<form action=""  class="pladform" id="formLogin">
				<fieldset style="width:75%;">
					<legend>B&uacute;squeda</legend>
	<p>&nbsp;</p>
        	<table >
        		<tr>
        			
        				
        						<td >Promotor&iacute;a:</td>
								<td width="205px">
									<select style="width:205px">
										<option>Promotoria 1523</option>
									</select>
								</td>
								<td>&nbsp;</td>
        						<td >Carga Masiva</td>
        						<td align="left">
        							<input type="file" style="width:200px;" name="Carga Masiva" ></input>
        						</td>
								
        					
						
        					</tr>
							<tr><td>&nbsp;</td></tr>
							<tr>
								<td>Clave Agente:</td>
        						<td>
        							<input type="text" style="width:205px;" />
        						</td>
        					</tr>
							<tr><td>&nbsp;</td></tr>
							<tr>
								<td>Agente:</td>
        						<td>
									<input type="text" style="width:205px"/>
        						</td>
        					</tr>
							<tr><td>&nbsp;</td></tr>
							
							<tr>
        						<td>Status</td>
        						<td>
									<select style="width:205px">
										<option>Activo</option>
										<option>Inactivo</option>
									</select>
        						</td>
        					</tr>
        					<tr><td>&nbsp;</td></tr>
							<tr>
        						<td >
        							<button type="button" id="buscar" name="noimporta" value="">Buscar</button>
        						</td>        						
        					
        			
										
        		</tr>
				
        	</table>
        	</fieldset></form>
			<p>&nbsp;</p>
			<form action=""  class="pladform" id="formLogin">
				<fieldset style="width:75%;">
					<legend>Resultados</legend>
						<div class="plad-dtbl-container">
						      <div class="pgnt-top-izquierda" style="font-size:12px;">
						        <button type="button" id="guardarcmb">Guardar</button>
						       <button type="button" style="width:100px;" id="buttonAsignar">Agregar Agente</button>
						      </div>
						      <p>&nbsp;</p>
						       <div class="errordiv" id="div.asignaciones.proceso" style="color:#FF0000"></div>
						      <div class="pgnt-top-derecha">
						        <div id="dtbl.Pgnt.Registros"></div>
						      </div>
						      <div class="pgnt-clear"></div>
						      <div class="plad-dtbl" id="basics"></div>
						      <div class="pgnt-bottom-izquierda">
						        <div id="dtbl.Errr.Registros">&nbsp;</div>
						      </div>
						      <div class="pgnt-bottom-derecha">
						        <div id="dtbl.Ttal.Registros">1&nbsp;registro (s) encontrado(s)</div>
						      <div class="pgnt-clear"></div>
						    </div>
						    </div>
				    </fieldset></form>
					<p>&nbsp;</p>
</div>
</div>
<p>&nbsp;</p>
<div id="DetalleDlg" style="font-size:12px;font-family:Tahoma;" align="center" >
  <div class="hd">Agregar Agente</div>
  <div class="bd">
    
      <table cellspacing="2" cellpadding="2">
       
        <tr>        
          <td>
            Clave Promotoria:
          </td>          
          <td>
            <input type="text" style="width:205px;"/>
          </td> 
        </tr>
        <tr><td>&nbsp;</td></tr>
         <tr>
          <td>
            Clave Agente:
          </td>
          <td>
            <input type="text" style="width:205px;"/>
          </td> 
        </tr>   
        <tr><td>&nbsp;</td></tr>       
         <tr>
          <td>
            Nombre Agente:
          </td>
          <td>
            <input type="text" style="width:205px;"/>
          </td> 
        </tr>
        <tr><td>&nbsp;</td></tr>
         <tr>
          <td>
            Telefono Celular:
          </td>
          <td>
            <input type="text" style="width:205px;"/>
          </td> 
        </tr>
        <tr><td>&nbsp;</td></tr>
         <tr>
          <td>
            Email:
          </td>
          <td>
            <input type="text" style="width:205px;"/>
          </td> 
        </tr>
      </table>
      <p>&nbsp;</p>
    <div align="center">
				        <button type="button" id="guardar" name="noimporta" value="">Guardar</button>
				        &nbsp;
				        <button type="button" id="cancelar" name="noimporta" value="">Cancelar</button>
		</div>		      
  </div>
</div>
<div id="Dlguardar" style="font-size:12px;font-family:Tahoma;" align="left">
  <div class="hd">Detalle Agente</div>
  <div class="bd">
  	<p>Informacion guardada correctamente.</p>
  </div>
</div>

<div id="Detalletable" style="font-size:12px;font-family:Tahoma;" align="center">
  <div class="hd">Detalle Agente</div>
  <div class="bd">
    
      <table cellspacing="2" cellpadding="2">
       
        <tr>        
          <td>
            Nombre Agente:
          </td>          
          <td>
            <input type="text" style="width:205px;"/>
          </td> 
        </tr>
        <tr><td>&nbsp;</td></tr>
         <tr>
          <td>
            Telefono Celular:
          </td>
          <td>
            <input type="text" style="width:205px;"/>
          </td> 
        </tr>   
        <tr><td>&nbsp;</td></tr>       
         <tr>
          <td>
            Email:
          </td>
          <td>
            <input type="text" style="width:205px;"/>
          </td> 
        </tr>
        <tr><td>&nbsp;</td></tr>
         <tr>
          <td>
            Estatus:
          </td>
          <td>
            <select style="width:205px;">
            	<option>Activo</option>
            	<option>Inactivo</option>
            </select>
          </td> 
        </tr>
        <tr><td>&nbsp;</td></tr>
         </table>
    <div align="center">
				        <button type="button" id="guardara" name="noimporta" value="">Guardar</button>
				        &nbsp;
				        <button type="button" id="cancelara" name="noimporta" value="">Cancelar</button>
		</div>		      
  </div>
</div>