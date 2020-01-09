function initDtblRegistros(){
	
	
	Y.Administra.StatusEditor = new YAHOO.widget.DropdownCellEditor({
	        dropdownOptions: Y.Administra.EstatusCatalog,
	        disableBtns: false,
	        LABEL_SAVE: "Aceptar",
	        LABEL_CANCEL: "Cancelar"
	});
	
	Y.Administra.HoraEditor = new YAHOO.widget.DropdownCellEditor({
        dropdownOptions: Y.Administra.HorasCatalog,
        disableBtns: true
	});
	
	Y.Administra.MinutoEditor = new YAHOO.widget.DropdownCellEditor({
        dropdownOptions: Y.Administra.MinutosCatalog,
        disableBtns: true
	});
	
	Y.Administra.EstatusFmt = function(cell, row, col, data){
        var combo = Y.Administra.EstatusCatalog;
        for (var i = 0; i < combo.length; i++) {
            if (data == combo[i].value) {
                cell.innerHTML = combo[i].label;
                return;
            }
        }
    };
    
    
    
	
    Y.Administra.RegistrosDtbl = new Y.Dtbl();
    Y.Administra.RegistrosDtbl.setRowMouse(true);
    Y.Administra.RegistrosDtbl.setRowSelect(true);
    Y.Administra.RegistrosDtbl.setUrl(Y.Administra.RegistrosDtblURL);
    Y.Administra.RegistrosDtbl.setDtblContainer('dtbl.Cont.Registros');
    Y.Administra.RegistrosDtbl.setPgntContainer('dtbl.Pgnt.Registros');
    Y.Administra.RegistrosDtbl.setPgntTotalContainer('dtbl.Ttal.Registros');
    Y.Administra.RegistrosDtbl.setPgntRecords(10);
    
    Y.Administra.RegistrosDtbl.setFields(["idJob", "what", "broken", "hora", "minuto"]);
    var columns = [{
        key: "idJob",
        label: ""
    }, {
        key: "what",
        label: "PROCESO"
    },
    {
        key: "broken",
        label: "ESTATUS",
        formatter: Y.Administra.EstatusFmt,
        editor: Y.Administra.StatusEditor
    }, 
    {
        key: "hora",
        label: "Hora",
        editor: Y.Administra.HoraEditor
    },
    {
        key: "minuto",
        label: "Minuto",
        editor: Y.Administra.MinutoEditor
    }];
    
    Y.Administra.RegistrosDtbl.setColumnsDefs(columns);
    Y.Administra.RegistrosDtbl.setEditor(true);
    /**
     * Se construye el objecto datatable interno con los parametros asignados
     * previamente
     */
    Y.Administra.RegistrosDtbl.construct();
    
    
    Y.util.Event.onDOMReady(function() {
    	Y.btnGuardar = new Y.widget.Button("guardar", {
    		onclick : {
    			fn : function() {
    				GuardarBtnClick();
    			}
    		},
    		disabled : false
    	});
  });

}  
    
    

    
    
    
    
    
function GuardarBtnClick() {
	try {
		var f = document.getElementById("JobsJsonForm");

		f.elements["jsonrecords"].value = Y.Administra.RegistrosDtbl.getJsonRecords();

		var callback = new Y.AsyncCallBack();
		callback.processResult = function(messages) {
			var msg = '<div><center>';
			msg += 'Los cambios se guardaron correctamente.';
			msg += '</center></div>';
			new Y.MessagePnl().showmsg({
				msg : msg,
				fnSubmit : function() {
					Y.Administra.RegistrosDtbl.update();
				}
			});
		};

		Y.util.Connect.setForm(f);
		Y.showWait();
		var request = Y.util.Connect.asyncRequest('POST', Y.Administra.GuardarCambiosURL, callback);

	} catch (e) {
		alert("guardargeneral: " + e.description);
	}
};
