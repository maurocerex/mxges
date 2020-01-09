Y.util.Event.onDOMReady(function() {
    Y.ModificarBtn = new Y.widget.Button("ModificarBtn", {
        onclick : {
            fn : Y.ModificarBtnClick
        }
    });
});


Y.ModificarBtnClick = function() {
    try {
		Y.ModificarCierre();
    } catch (e) {
        alert("Y.ModificarBtnClick: " + e.description);
    }
};


Y.ModificarCierre = function() {
	try {
		Y.showWait();
		var f = document.getElementById("sms-form");
		var callback = new Y.AsyncCallBack();
		callback.processResult = function(messages) {
			Y.hideWait();

			if (messages.estatus != true) {
				mensajeAlerta('Propiedad Actualizada',
						'No se pudo actualizar la propiedad de Acentos, error de sistema');
			} else {
				mensajeAlerta('Propiedad Actualizada',
						'Se ha actualizado correctamente la Configuraci&oacute;n SMSs.');
			}
		}
		callback.processResultUnsuccess = function(messages) {
			Y.hideWait();
		}
		Y.util.Connect.setForm(f);
		Y.util.Connect.asyncRequest('POST', Y.ModificaURL, callback);

	} catch (x) {
		alert("Y.ModificarCierre:" + x.description);
	}
};

function mensajeAlerta(title, description) {
	try {
		Y.KuspitMsgDlg = new Y.widget.SimpleDialog("KuspitMsgDlg", {
			width : "33em",
			effect : {
				effect : Y.widget.ContainerEffect.FADE,
				duration : 0.25
			},
			fixedcenter : true,
			modal : true,
			visible : false,
			draggable : false
		});
		
		Y.KuspitMsgDlg.cfg.setProperty("icon",Y.widget.SimpleDialog.ICON_WARN);
		
		Y.KuspitMsgDlg.setHeader(title);
		Y.KuspitMsgDlg.setBody(description);
		
		var handleYes = function() {
			try {
				this.hide();
			} catch (e) {
				alert("mensajeAlerta handleYes: " + e.description);
			}
		};
		
		var myButtons = [ 
			{
				text : "Cerrar",
				handler : handleYes,
				isDefault : true
			}
		];
		
		Y.KuspitMsgDlg.cfg.queueProperty("buttons", myButtons);
		Y.KuspitMsgDlg.render(document.body);
		Y.KuspitMsgDlg.show();
	} catch (e) {
		alert("mensajeAlerta: " + e.description);
	}
}

