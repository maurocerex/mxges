function getDetalleDlgForm(){
    return getForm("DetalleDlgForm");
}
function getUsuariosAbcForm() {
  return getForm("UsuariosAbcForm");
}


function initDtblUsuariosAbc(){

    Y.UsuariosAbc.VigenciaEditor = new YAHOO.widget.DropdownCellEditor({
        dropdownOptions: Y.UsuariosAbc.VigenciaCatalog,
        disableBtns: false,
        LABEL_SAVE: "Aceptar",
        LABEL_CANCEL: "Cancelar"
    });
    
    Y.UsuariosAbc.VigenciaFmt = function(cell, row, col, data){
        var combo = Y.UsuariosAbc.VigenciaCatalog;
        for (var i = 0; i < combo.length; i++) {
            if (data == combo[i].value) {
                cell.innerHTML = combo[i].label;
                return;
            }
        }
    };
    
    //Y.UsuariosAbc.RegistrosDtbl = new Y.DtblDyn();  paginacion con base de datos
    Y.UsuariosAbc.RegistrosDtbl = new Y.Dtbl();
    Y.UsuariosAbc.RegistrosDtbl.setUrl(Y.UsuariosAbc.RegistrosDtblURL);
    Y.UsuariosAbc.RegistrosDtbl.setRowMouse(true);
    Y.UsuariosAbc.RegistrosDtbl.setRowSelect(true);
    Y.UsuariosAbc.RegistrosDtbl.setDtblContainer('dtbl.Cont.Registros');
    Y.UsuariosAbc.RegistrosDtbl.setPgntContainer('dtbl.Pgnt.Registros');
    Y.UsuariosAbc.RegistrosDtbl.setPgntTotalContainer('dtbl.Ttal.Registros');
    Y.UsuariosAbc.RegistrosDtbl.setPgntRecords(10);
    Y.UsuariosAbc.RegistrosDtbl.setFields(["idlob", "lob", "idusuario", "username", "nombre", "sinovigente", "rol", "idrol", "nuevo", "modificado"]);
    var columns = [{
        key: "username",
        label: "USERNAME"
    }, {
        key: "nombre",
        label: "NOMBRE"
    }, {
        key: "sinovigente",
        label: "VIGENTE",
        formatter: Y.UsuariosAbc.VigenciaFmt
    }, {
        key: "rol",
        label: "ROL"
    }, {
        key: "detalle",
        label: "EDITAR",
        formatter: Y.Dtbl.detalleFmt
    }];
    
    Y.UsuariosAbc.RegistrosDtbl.setColumnsDefs(columns);
    
    // Indicamos que las columnas del datatable tienen un editor relacionado
    Y.UsuariosAbc.RegistrosDtbl.setEditor(true);
    /**
     * Se construye el objecto datatable interno con los parametros asignados
     * previamente
     */
    Y.UsuariosAbc.RegistrosDtbl.construct();
    
    /**
     * Se define el evento que gestionara los clicks de los botones de cada
     * columna.
     */
    Y.UsuariosAbc.RegistrosDtbl.myDataTable.subscribe("buttonClickEvent", function(oArgs){
        var target = oArgs.target;
        var record = this.getRecord(target);
        if (target.value == "Detalle") {
			
            DetalleDlgEditar(record);
			
        } else {
            alert("buttonClickEvent: " + target.value);
        }
    });
    
    Y.UsuariosAbc.RegistrosDtbl.actualizarRegistro = function(oData){
        Y.logplad("actualizarRegistro...");
        
        try {
            var dtbl = this.myDataTable;
            var nRecordsLength = dtbl.getRecordSet().getLength();
            var record;
            
            for (i = 0; i < nRecordsLength; i++) {
                record = dtbl.getRecordSet().getRecord(i);
                if (record.getId() == oData['recordId']) {
                
                    record.setData("lob", oData['lob']);
                    record.setData("username", oData['username']);
                    record.setData("nombre", oData['nombre']);
                    record.setData("sinovigente", oData['sinovigente']);
                    record.setData("rol", oData['rol']);
                    record.setData("idrol", oData['idrol']);
                    record.setData("modificado", true);
                    break;
                }
            }
            dtbl.render();
        } catch (x) {
            alert("actualizarRegistro: " + x.description);
        }
    };
	
    Y.UsuariosAbc.RegistrosDtbl.insertarRegistro = function(oData){
        Y.logplad("insertarRegistro...");
        try {
            var dtbl = this.myDataTable;
            oData['nuevo'] = true;
            oData['modificado'] = false;
            dtbl.addRow(oData, 0);
        } catch (x) {
            alert("insertarRegistro: " + x.description);
            return false;
        }
    };
}



Y.util.Event.onDOMReady(function(){

    Y.NuevoDlg = new Y.widget.Panel("NuevoDlg", {
        width: "30em",
        fixedcenter: true,
        modal: true,
        visible: false,
        constraintoviewport: false,
        draggable: true
    });
    var kl = new Y.util.KeyListener(document, {
        keys: 27
    }, {
        fn: Y.NuevoDlg.hide,
        scope: Y.NuevoDlg,
        correctScope: true
    });
    kl.enable();
    
    Y.NuevoDlg.render();
    
    Y.DetalleEditDlg = new Y.widget.Panel("editar", {
        width: "30em",
        height: "auto",
        fixedcenter: "contained",
        modal: true,
        visible: false,
        constraintoviewport: false,
        draggable: true
    });
    var kl = new Y.util.KeyListener(document, {
        keys: 27
    }, {
        fn: Y.DetalleEditDlg.hide,
        scope: Y.DetalleEditDlg,
        correctScope: true
    });
    kl.enable();
    
    Y.DetalleEditDlg.render();
    
    Y.DetalleDlgAgregar = function(record){
        try {
            Y.NuevoDlg.show();
        } catch (e) {
            alert("DetalleDlgEditar:" + e.description);
        }
    };
    
    Y.btnGuardar = new Y.widget.Button("guardar", {
        onclick: {
            fn: function(){
               GuardarBtnClick();
            }
        },
        disabled: false
    });
    
    Y.btnAgregar = new Y.widget.Button("agregar", {
        onclick: {
            fn: function(){
            	DetalleDlgNuevo();
            }
        },
        disabled: false
    });
    
       
    Y.btnGuardaEd = new Y.widget.Button("guardaredit", {
        onclick: {
            fn: function(){
				
				DetalleAceptarBtnClick();
                //Y.DetalleEditDlg.hide();
            }
        },
        disabled: false
    });
    
    
    Y.btnGuardaEd = new Y.widget.Button("cancelaredit", {
        onclick: {
            fn: function(){
                Y.DetalleEditDlg.hide();
            }
        },
        disabled: false
    });
    

});

function DetalleDlgNuevo(){
  try {
    var f = getDetalleDlgForm();
    f.elements['record.id'].value = '';
    f.elements['parametro.idlob'].value = '';
    f.elements['parametro.username'].value = '';
    document.getElementById("username").disabled=false;
    f.elements['parametro.nombre'].value = '';    
    f.elements['parametro.vigencia'].value = '';
    f.elements['parametro.rolId'].value = '';
    
    
    Y.publishMsg("div.idlob", "");
	Y.publishMsg("div.username", "");
	Y.publishMsg("div.vigencia", "");
	Y.publishMsg("div.rolId", "");
	Y.publishMsg("div.nombre", "");
    
	

    Y.DetalleEditDlg.setHeader("Nuevo Usuario");
    Y.DetalleEditDlg.show();
	Y.DetalleEditDlg.center();
  } catch (e) {
    alert("DetalleDlgNuevo:" + e.description);
  }
};
  
function DetalleAceptarBtnClick(){
    try {
    
        var f = getDetalleDlgForm();
        if (f.elements['record.id'].value == '') {
            // valida un registro nuevo
            f.elements['parametro.nuevo'].value = true;
        } else {
            f.elements['parametro.nuevo'].value = false;
        }
        
        var callback = new Y.AsyncCallBack();
        callback.processResult = function(messages){
            var recordId = f.elements['record.id'].value;
            var oData = {};
            
            oData['recordId'] = recordId;            
			oData['idlob'] = f.elements['parametro.idlob'].value;	
			
			var indexlob = f.elements['parametro.idlob'].selectedIndex;
            if (indexlob == -1) {
                oData['lob'] = " ";
            } else {
                oData['lob'] = f.elements['parametro.idlob'].options[indexlob].text;
            }
			
			
            oData['username'] = f.elements['parametro.username'].value;            
            oData['nombre'] = f.elements['parametro.nombre'].value;
            oData['sinovigente'] = f.elements['parametro.vigencia'].value;
			
            if (f.elements['parametro.vigencia'].value == 'I') {
                oData['sinovigente'] = "I";
            } else {
                oData['sinovigente'] = "A";
            }
            
            oData['idrol'] = f.elements['parametro.rolId'].value;
            // asigna el texto del combo en el datatable
            var index = f.elements['parametro.rolId'].selectedIndex;
            if (index == -1) {
                oData['rol'] = " ";
            } else {
                oData['rol'] = f.elements['parametro.rolId'].options[index].text;
            }
            
            
            if (recordId == '') {
                Y.UsuariosAbc.RegistrosDtbl.insertarRegistro(oData);
            } else {
                Y.UsuariosAbc.RegistrosDtbl.actualizarRegistro(oData);
            }
	  
            
            
            
            Y.DetalleEditDlg.hide();
            
        };
        
        Y.util.Connect.setForm(f);
        Y.showWait();
        var request = Y.util.Connect.asyncRequest('POST', Y.UsuariosAbc.ValidarURL, callback);
        
    } catch (e) {
        alert("DetalleAceptarBtnClick: " + e.description);
    }
};


  
function DetalleDlgEditar(record) {
	Y.publishMsg("div.idlob", "");
	Y.publishMsg("div.username", "");
	Y.publishMsg("div.vigencia", "");
	Y.publishMsg("div.rolId", "");
	Y.publishMsg("div.nombre", "");
    try {
        var f = getDetalleDlgForm();
		f.elements['record.id'].value = record.getId();
		
		f.elements['parametro.idlob'].value = "";
		f.elements['parametro.idlob'].value = record.getData('idlob');
        f.elements['parametro.username'].value = record.getData('username');
        f.elements['parametro.nombre'].value = record.getData('nombre');
		
		//f.elements['parametro.vigencia'].value = record.getData('sinovigente');
	//	if (record.getData('sinovigente') == 'A') {
			f.elements['parametro.vigencia'].value = record.getData('sinovigente');
		/*}else{
			f.elements['parametro.vigencia'].value = 'NO';
		}*/
        f.elements['parametro.rolId'].value = "";
		f.elements['parametro.rolId'].value = record.getData('idrol');
		
		Y.DetalleEditDlg.setHeader("Detalle Usuario");
		document.getElementById("username").disabled=true;
        Y.DetalleEditDlg.show();
        Y.DetalleEditDlg.center();
    } catch (e) {
        alert("DetalleDlgEditar:" + e.description);
    }
};

function GuardarBtnClick() {
  try {
    var f = getUsuariosAbcForm();
    f.elements["jsonrecords"].value = Y.UsuariosAbc.RegistrosDtbl.getJsonRecords();
	
    var callback = new Y.AsyncCallBack();
    callback.processResult = function(messages) {
      var msg = '<div><center>';
      msg += 'Los cambios se guardaron correctamente.';
      msg += '</center></div>';
      new Y.MessagePnl().showmsg( {
        msg : msg,
        fnSubmit : function() {
          Y.UsuariosAbc.RegistrosDtbl.update();
        }
      });
    };
    callback.processResultUnsuccess = function(messages){
       var msg = '<div><center>';
        msg += messages.username;
        msg += '</center></div>';
        new Y.MessagePnl().showmsg( {
          msg : msg,
          fnSubmit : function() {
            Y.UsuariosAbc.RegistrosDtbl.update();
          }
        });
      };
    Y.util.Connect.setForm(f);
    Y.showWait();
    var request = Y.util.Connect.asyncRequest('POST', Y.UsuariosAbc.GuardarCambiosURL, callback);

  } catch (e) {
    alert("guardargeneral: " + e.description);
  }
};