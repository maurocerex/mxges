[ 
        { 
        text: "Administraci&oacute;n", 
        submenu: {
			id: "nm01",
			itemdata: [{
				text: "Seguridad",
				submenu: {
					id: "nm01-01",
					itemdata: [{
						id: "1",
						text: "Usuarios",
						onclick: {
							obj: "usuarios"
						},
						disabled: false
					}, {
						id: "2",
						text: "Roles",
						onclick: {
							obj: "roles"
						},
						disabled: false
					}]
				}
			}]
		}
			
    }, 
       { 
        text: "SMS´s", 
        submenu:         { 
            id: "nm02", 
            itemdata:             [ 
                    { 
                    	id: "9", 
                        text: "Configuraci&oacute;n", 
                        onclick: {obj: "smsConfig"}, 
                        disabled: false 
                    }
            ] 
        } 
    }
	, { 
        text: "Cat&aacutelogos", 
        submenu:         { 
            id: "nm03", 
            itemdata:             [ 
                    { 
                    	id: "3", 
                        text: "Cat&aacutelogo Mensajes", 
                        onclick: {obj: "mensajes"}, 
                        disabled: false 
                    },  
                      { 
                    	id: "4", 
                        text: "Cat&aacutelogo Prioridades", 
                        onclick: {obj: "prioridad"}, 
                        disabled: false 
                    }
                 
            ] 
        } 
    }
	, { 
	    	id: "5", 
	        text: "Notificaciones", 
	        onclick: {obj: "notificaciones"}, 
	        disabled: false 
    	},
		 { 
	    	id: "8", 
	        text: "Exclusiones", 
	        onclick: {obj: "exclusiones"}, 
	        disabled: false 
    	},
    	{ 
	    	id: "11", 
	        text: "Cancelaciones", 
	        onclick: {obj: "cancelaciones"}, 
	        disabled: false 
    	},
		{ 
	    	id: "6", 
	        text: "Reporte Notificaciones", 
	        onclick: {obj: "reportes"}, 
	        disabled: false 
    	}
		,
		{ 
	    	id: "7", 
	        text: "Cifras Control", 
	        onclick: {obj: "cifras"}, 
	        disabled: false 
    	},
		{ 
	    	id: "10", 
	        text: "Administracion Jobs", 
	        onclick: {obj: "administra"}, 
	        disabled: false 
    	}
	]