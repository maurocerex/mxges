Y.util.Event.onDOMReady(function() {

	Y.btnGuardar = new Y.widget.Button("guardaUs", {
		onclick : {
			fn : function() {
				//Y.DetalleDlg.show();
				GuardarBtnClick();
			}
		},
		disabled : false
	});

	Y.DetalleDlg = new Y.widget.Panel("Detalle", {

		fixedcenter : "contained",
		modal : true,
		visible : false,
		constraintoviewport : false,
		draggable : true
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
});

function GuardarBtnClick() {
	try {
		var dat = document.getElementById("formDatos");
		var destino = document.getElementById("formDestino");
		var dias = document.getElementById("formPeriodicidad");
		
		dat.elements["detinatario"].value = destino.elements['destinatario'].value;
		dat.elements['dia_1'].value = dias.elements['dia1'].value;
		dat.elements['dia_2'].value = dias.elements['dia2'].value;
		dat.elements['dia_3'].value = dias.elements['dia3'].value;
		dat.elements['dia_4'].value = dias.elements['dia4'].value;
		
		var callback = new Y.AsyncCallBack();
		callback.processResult = function(messages) {
			var msg = '<div><center>';
			msg += 'Los cambios se guardaron correctamente.';
			msg += '</center></div>';
			new Y.MessagePnl().showmsg({
				msg : msg,
				fnSubmit : function() {
					var form = getMainRedirectForm();
	                var actionUrl = Y.redirects['reportes'];
	                form.action = actionUrl;
	                form.submit();					
				}
			});
		};

		Y.util.Connect.setForm(dat);
		Y.showWait();
		var request = Y.util.Connect.asyncRequest('POST', Y.Reporte.GuardarCambiosURL, callback);

	} catch (e) {
		alert("guardargeneral: " + e.description);
	}
};


function getReporteCalidad(){
    try {
        
        var callback = new Y.AsyncCallBack();
        callback.processResult = function(messages){
			var f = document.getElementById("formReporte");
			f.elements['reporte.idProceso'].value=messages.idProceso;
			f.elements['reporte.idLob'].value=messages.idLob;
			f.elements['reporte.idMensaje'].value=messages.idMensaje;
			f.elements['reporte.idNotificacion'].value=messages.idNotificacion;
			hideWait();
        };
                
				showWait();
        var sUrl = Y.Reporte.DatosURL;
        var postData = "";
        var request = Y.util.Connect.asyncRequest('POST', sUrl, callback, postData);
        
    } catch (x) {
        alert("Y.Reporte.ReporteCalidad :" + x.description);
    }
}
