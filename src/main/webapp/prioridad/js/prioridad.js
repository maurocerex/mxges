
function onChangeLob(){
    var f = getForm("formBusqueda");
    if (f.elements['idlob'].value == '') {
        Y.Prioridad.RegistrosDtbl.clear();
        Y.Prioridad.btnGuardar.set("disabled", true);
    } else {
        Y.Prioridad.RegistrosDtbl.update();
		Y.Prioridad.cuenta=true;
		Y.Prioridad.RegistrosDtbl.myDataTable.render();
        //getPrioridad();
    }
}

function cuenta(){
	
    Y.Prioridad.cuenta = false;
	Y.Prioridad.PrioridadCatalog.length =0;
    var nRecordsLength = Y.Prioridad.RegistrosDtbl.myDataTable.getRecordSet().getLength();
    for (i = 1; i <= nRecordsLength; i++) {
        Y.Prioridad.PrioridadCatalog.push({
            label: '' + i + '',
            value: '' + i + ''
        });
    }
	Y.Prioridad.PrioridadEditor.render();
}


function habilita(){
    var f = getForm("formBusqueda");
    if (f.elements['idlob'].value == '') {
        Y.btnBuscar.set("disabled", true);
        
    } else {
        Y.btnBuscar.set("disabled", false);
    }
    
}

function initDtblPrioridad(){

    Y.Prioridad.RegistrosDtbl = new Y.Dtbl();
    Y.Prioridad.RegistrosDtbl.setUrl(Y.Prioridad.RegistrosDtblURL);
    Y.Prioridad.RegistrosDtbl.setRowMouse(true);
    Y.Prioridad.RegistrosDtbl.setRowSelect(true);
    Y.Prioridad.RegistrosDtbl.setDtblContainer('dtbl.Cont.Registros');
    Y.Prioridad.RegistrosDtbl.setPgntContainer('dtbl.Pgnt.Registros');
    Y.Prioridad.RegistrosDtbl.setPgntTotalContainer('dtbl.Ttal.Registros');
    Y.Prioridad.RegistrosDtbl.setPgntRecords(10);
    Y.Prioridad.RegistrosDtbl.checkFmt = function(cell, row, col, data){
        var checked = row.getData("notifica");
        if (checked == 'true' || checked == true) {
            cell.innerHTML = '<input type="checkbox" name="ignorame" checked="checked" ></input>';
        } else {
            cell.innerHTML = '<input type="checkbox" name="ignorame" ></input>';
        }
    };
    
    Y.Prioridad.PrioridadEditor = new YAHOO.widget.DropdownCellEditor({
        dropdownOptions: Y.Prioridad.PrioridadCatalog,
        disableBtns: false,
        LABEL_SAVE: "Aceptar",
        LABEL_CANCEL: "Cancelar"
    });
	
    Y.Prioridad.PrioridadFmt = function(cell, row, col, data){
    	if(Y.Prioridad.cuenta==true || Y.Prioridad.cuenta=="true"){
			cuenta();
		}
		
        var combo = Y.Prioridad.PrioridadCatalog;
        for (var i = 0; i < combo.length; i++) {
            if (data == combo[i].value) {
                cell.innerHTML = combo[i].label;
                return;
            }
        }
    };
    
    Y.Prioridad.RegistrosDtbl.setFields(["idLob", "lob", "idProceso", "proceso", "prioridad", "notifica", "nuevo", "modificado"]);
    var columns = [{
        key: "lob",
        label: "LOB"
    }, {
        key: "proceso",
        label: "PROCESO"
    }, {
        key: "prioridad",
        label: "* PRIORIDAD",
        formatter: Y.Prioridad.PrioridadFmt,
        editor: Y.Prioridad.PrioridadEditor
    }, {
        key: "notifica",
        label: "NOTIFICAR REPORTE",
        formatter: Y.Prioridad.RegistrosDtbl.checkFmt
    }];
    
    Y.Prioridad.RegistrosDtbl.setColumnsDefs(columns);
    
    // Indicamos que las columnas del datatable tienen un editor relacionado
    Y.Prioridad.RegistrosDtbl.setEditor(true);
    /**
     * Se construye el objecto datatable interno con los parametros asignados
     * previamente
     */
    Y.Prioridad.RegistrosDtbl.construct();
    
    Y.Prioridad.RegistrosDtbl.getPostData = function(){
        try {
            var f = getForm("formBusqueda");
            return "&idlob=" + f.elements['idlob'].value;
        } catch (e) {
            alert("getPostData:" + e.description);
        }
    };
    
    Y.Prioridad.RegistrosDtbl.myDataTable.subscribe("checkboxClickEvent", function(oArgs){
        var target = oArgs.target;
        var record = this.getRecord(target);
        
        record.setData("notifica", target.checked);
        record.setData("modificado", true);
        
        Y.btnGuardar.set("disabled", false);
        
    });
    
    Y.Prioridad.RegistrosDtbl.obtenActivPant = function(){
    
        try {
            var dtbl = Y.Prioridad.RegistrosDtbl.myDataTable;
            var recordSet = dtbl.getRecordSet();
            var arrayTemp = [];
            for (i = 0; i < recordSet.getLength(); i++) {
                var record = recordSet.getRecord(i);
                if (record.getData("modificado") == true) {
                    arrayTemp.push({
                        proceso: record.getData("idProceso"),
                        lob: record.getData("idLob"),
                        prioridad: record.getData("prioridad"),
                        notifica: record.getData("notifica")
                    });
                }
            }
            return arrayTemp;
        } catch (x) {
            alert("Y.SegAdmRolPant.RegistrosDtbl.obtenActivPant: " + x.description);
        }
        
    };
    
    
}

Y.util.Event.onDOMReady(function(){

    Y.Prioridad.GuardarPantallas = function(){
    
        try {
            var f = getForm('formBusqueda');
            var tmpIdRol = f.elements['idlob'].value;
            var lal = Y.Prioridad.RegistrosDtbl.getJsonRecords()
            lal = Y.lang.JSON.parse(lal);
            f.elements['procesoAsig'].value = Y.lang.JSON.stringify(lal);
            
            var callback = new Y.AsyncCallBack();
            callback.processResult = function(messages){
                var msg = '<div><center>';
                msg += 'Cambios Efectuados.';
                msg += '</center></div>';
                new Y.MessagePnl().showmsg({
                    msg: msg,
                    fnSubmit: function(){
                        Y.Prioridad.RegistrosDtbl.update();
                        Y.btnGuardar.btnGuardar.set("disabled", true);
                        f.elements['idlob'].value = tmpIdRol;
                        
                    }
                });
            };
            callback.processResultUnsuccess = function(messages){
                if (messages.proceso != undefined) {
                    var msg = '<div><center>';
                    msg += messages.proceso;
                    msg += '</center></div>';
                    new Y.MessagePnl().showmsg({
                        msg: msg
                    });
                }
                Y.btnGuardar.set("disabled", true);
            };
            Y.util.Connect.setForm(f);
            Y.showWait();
            var request = Y.util.Connect.asyncRequest('POST', Y.Prioridad.ActPrioridad, callback);
            
        } catch (e) {
            alert("Y.Prioridad.GuardarPantallas" + e.description)
        }
    };
    
    
    Y.btnGuardar = new Y.widget.Button("guardaros", {
        onclick: {
            fn: function(){
                Y.Prioridad.GuardarPantallas()
            }
        },
        disabled: false
    });
    
    Y.btnBuscar = new Y.widget.Button("buscar", {
        onclick: {
            fn: function(){
                onChangeLob();
            }
        },
        disabled: true
    });
    
});
