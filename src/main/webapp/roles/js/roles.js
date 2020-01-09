function onChangeRoleId(){
    var f = getForm("rolesform");
    if (f.elements['idRol'].value == '') {
    
        Y.Roles.RegistrosDtbl.clear();
        Y.Roles.btnGuardar.set("disabled", true);
        
    } else {
    
        Y.Roles.RegistrosDtbl.update();
        Y.Roles.btnGuardar.set("disabled", true);
        
    }
    
}

function initDtblRoles(){

    Y.Roles.RegistrosDtbl = new Y.Dtbl();
    Y.Roles.RegistrosDtbl.setUrl(Y.Roles.RegistrosDtblURL);
    Y.Roles.RegistrosDtbl.setRowMouse(true);
    Y.Roles.RegistrosDtbl.setRowSelect(true);
    Y.Roles.RegistrosDtbl.setDtblContainer('dtbl.Cont.Registros');
    Y.Roles.RegistrosDtbl.setPgntContainer('dtbl.Pgnt.Registros');
    Y.Roles.RegistrosDtbl.setPgntTotalContainer('dtbl.Ttal.Registros');
    Y.Roles.RegistrosDtbl.setPgntRecords(10);
    Y.Roles.RegistrosDtbl.checkFmt = function(cell, row, col, data){
        var checked = row.getData("asigPant");
        if (checked == 'true' || checked == true) {
            cell.innerHTML = '<input type="checkbox" name="ignorame" checked="checked" ></input>';
        } else {
            cell.innerHTML = '<input type="checkbox" name="ignorame" ></input>';
        }
    };
    Y.Roles.RegistrosDtbl.setFields(["edoIni", "asigPant", "idPantalla", "menu", "pantalla", "descripcion", "nuevo", "modificado", "editado"]);
    
    var columns = [{
        key: "asigPant",
        label: "*",
        formatter: Y.Roles.RegistrosDtbl.checkFmt
    }, {
        key: "idPantalla",
        label: "ID PANTALLA"
    }, {
        key: "menu",
        label: "MENU"
    }, {
        key: "pantalla",
        label: "PANTALLA"
    
    }, {
        key: "descripcion",
        label: "DESCRIPCION"
    }];
    
    Y.Roles.RegistrosDtbl.setColumnsDefs(columns);
    
    // Indicamos que las columnas del datatable tienen un editor relacionado
    Y.Roles.RegistrosDtbl.setEditor(true);
    /**
     * Se construye el objecto datatable interno con los parametros asignados
     * previamente
     */
    Y.Roles.RegistrosDtbl.construct();
    
    Y.Roles.RegistrosDtbl.getPostData = function(){
        try {
            var f = getForm("rolesform");
            return "&idRol=" + f.elements['idRol'].value;
        } catch (e) {
            alert("getPostData:" + e.description);
        }
    };
    
    Y.Roles.RegistrosDtbl.myDataTable.subscribe("checkboxClickEvent", function(oArgs){
        var target = oArgs.target;
        var record = this.getRecord(target);
        
        record.setData("asigPant", target.checked);
        record.setData("editado", true);
        
        var records = Y.Roles.RegistrosDtbl.myDataTable.getRecordSet().getRecords();
        Y.Roles.btnGuardar.set("disabled", true);
        for (i=0; i < records.length; i++) {
            if (records[i].getData("asigPant")){
            	Y.Roles.btnGuardar.set("disabled", false);
            	break;
            }
        }
        
        
        
    });
    
    Y.Roles.RegistrosDtbl.obtenActivPant = function(){
    
        try {
            var dtbl = Y.Roles.RegistrosDtbl.myDataTable;
            var recordSet = dtbl.getRecordSet();
            var arrayTemp = [];
            for (i = 0; i < recordSet.getLength(); i++) {
                var record = recordSet.getRecord(i);
                if (record.getData("editado") == true) {
                    arrayTemp.push({
                        pantalla: record.getData("idPantalla"),
                        editado: record.getData("editado"),
                        asigPant: record.getData("asigPant"),
                        edoIni: record.getData("edoIni")
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

    Y.Roles.GuardarPantallas = function(){
    
        try {
            var f = getForm('rolesform');
            var tmpIdRol = f.elements['idRol'].value;
            f.elements['pantallasAsig'].value = Y.lang.JSON.stringify((Y.Roles.RegistrosDtbl.obtenActivPant()));
            
            var callback = new Y.AsyncCallBack();
            callback.processResult = function(messages){
                var msg = '<div><center>';
                msg += 'Cambios Efectuados.';
                msg += '</center></div>';
                new Y.MessagePnl().showmsg({
                    msg: msg,
                    fnSubmit: function(){
                        Y.Roles.RegistrosDtbl.update();
                        Y.Roles.btnGuardar.set("disabled", true);
                        f.elements['idRol'].value = tmpIdRol;
                        
                    }
                });
                
            };
            
            Y.util.Connect.setForm(f);
            Y.showWait();
            var request = Y.util.Connect.asyncRequest('POST', Y.Roles.ActPantUser, callback);
            
        } catch (e) {
        
            alert("Y.SegAdmRolPant.GuardarPantallas" + e.description)
        }
    };
    
    
    Y.Roles.btnGuardar = new Y.widget.Button("guardaUs", {
        onclick: {
            fn: function(){
                Y.Roles.GuardarPantallas();
            }
        },
        disabled: false
    });
    
    Y.DetalleRechaDlg = new Y.widget.Panel("detalle", {
    
        fixedcenter: "contained",
        modal: true,
        visible: false,
        constraintoviewport: false,
        draggable: true
    });
    var kl = new Y.util.KeyListener(document, {
        keys: 27
    }, {
        fn: Y.DetalleRechaDlg.hide,
        scope: Y.DetalleRechaDlg,
        correctScope: true
    });
    kl.enable();
    
    Y.DetalleRechaDlg.render();
    
});

function validaChange(){
    var f = getForm("rolesform");
    Y.Roles.SegAdmUserConfirmDlg = new Y.widget.SimpleDialog("SegAdmUserConfirmDlg", {
        width: "20em",
        effect: {
            effect: Y.widget.ContainerEffect.FADE,
            duration: 0.25
        },
        fixedcenter: true,
        modal: true,
        visible: false,
        draggable: false
    });
    
    Y.Roles.SegAdmUserConfirmDlg.setHeader("Confirmaci&oacute;n");
    var msg = '<div><center>';
    msg += 'Se perderan los cambios de asignacion de pantallas.<br/>';
    msg += '&iquest; Desea continuar ?';
    msg += '</center></div>';
    Y.Roles.SegAdmUserConfirmDlg.setBody(msg);
    Y.Roles.SegAdmUserConfirmDlg.cfg.setProperty("icon", Y.widget.SimpleDialog.ICON_WARN);
    var handleYes = function(){
        try {
            if (f.elements["idRol"].value == '') {
                Y.Roles.RegistrosDtbl.clear();
            } else {
                Y.Roles.RegistrosDtbl.update();
            }
            Y.Roles.btnGuardar.set("disabled", true);
            
            this.hide();
        } catch (e) {
            alert("eliminar handleYes: " + e.description);
        }
    };
    var handleNo = function(){
    
    
        this.hide();
    };
    var myButtons = [{
        text: "Aceptar",
        handler: handleYes
    }, {
        text: "Cancelar",
        handler: handleNo,
        isDefault: true
    }];
    
    Y.Roles.SegAdmUserConfirmDlg.cfg.queueProperty("buttons", myButtons);
    Y.Roles.SegAdmUserConfirmDlg.render(document.body);
    Y.Roles.SegAdmUserConfirmDlg.show();
    
}

