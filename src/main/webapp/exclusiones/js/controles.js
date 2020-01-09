function top() {
   // alert("topbutton");
    try {
        element = getSeleccionados();
        FM.shiftSelectionsTop(element);
    } catch (e) {
        alert("top: " + e);
    }
}
function addall() {
    // alert("upbutton");
    try {
    	 fromElement = getDisponibles();
         toElement = getSeleccionados();
         FM.selectAllOptions(fromElement);
         
         FM.moveSelections(fromElement, toElement);
         // FM.sortOptions(toElement);
         FM.deselectAllOptions(fromElement);
         FM.deselectAllOptions(toElement);
    } catch (e) {
        alert("up: " + e);
    }
}
function add() {
    // alert("addbutton");
    try {

        fromElement = getDisponibles();
        toElement = getSeleccionados();

        FM.moveSelections(fromElement, toElement);
        // FM.sortOptions(toElement);
        FM.deselectAllOptions(fromElement);
        FM.deselectAllOptions(toElement);

    } catch (e) {
        alert("add: " + e);
    }

}
function remove() {
    // alert("removebutton");
    try {
        toElement = getDisponibles();
        fromElement = getSeleccionados();

        FM.moveSelections(fromElement, toElement);
        // FM.sortOptions(toElement);

        FM.deselectAllOptions(fromElement);
        FM.deselectAllOptions(toElement);

    } catch (e) {
        alert("remove: " + e);
    }
}
function removeall() {
    // alert("downbutton");
    try {
    	toElement = getDisponibles();
        fromElement = getSeleccionados();
        
        FM.selectAllOptions(fromElement);
        
        FM.moveSelections(fromElement, toElement);
        // FM.sortOptions(toElement);

        FM.deselectAllOptions(fromElement);
        FM.deselectAllOptions(toElement);
    } catch (e) {
        alert("down: " + e);
    }
}




function addallCt() {
    // alert("upbutton");
    try {
    	 fromElement = document.getElementById("disponiblesct");
         toElement = document.getElementById("seleccionadosct");
         FM.selectAllOptions(fromElement);
         
         FM.moveSelections(fromElement, toElement);
         // FM.sortOptions(toElement);
         FM.deselectAllOptions(fromElement);
         FM.deselectAllOptions(toElement);
    } catch (e) {
        alert("up: " + e);
    }
}
function addCt() {
    // alert("addbutton");
    try {

    	fromElement = document.getElementById("disponiblesct");
        toElement = document.getElementById("seleccionadosct");

        FM.moveSelections(fromElement, toElement);
        // FM.sortOptions(toElement);
        FM.deselectAllOptions(fromElement);
        FM.deselectAllOptions(toElement);

    } catch (e) {
        alert("add: " + e);
    }

}
function removeCt() {
    // alert("removebutton");
    try {
        toElement =  document.getElementById("disponiblesct");
        fromElement = document.getElementById("seleccionadosct");

        FM.moveSelections(fromElement, toElement);
        // FM.sortOptions(toElement);

        FM.deselectAllOptions(fromElement);
        FM.deselectAllOptions(toElement);

    } catch (e) {
        alert("remove: " + e);
    }
}
function removeallCt() {
    // alert("downbutton");
    try {
    	toElement =  document.getElementById("disponiblesct");
        fromElement = document.getElementById("seleccionadosct");
        
        FM.selectAllOptions(fromElement);
        
        FM.moveSelections(fromElement, toElement);
        // FM.sortOptions(toElement);

        FM.deselectAllOptions(fromElement);
        FM.deselectAllOptions(toElement);
    } catch (e) {
        alert("down: " + e);
    }
}







function bottom() {
    //alert("bottombutton");
    try {
        element = getSeleccionados();
        FM.shiftSelectionsBottom(element);
    } catch (e) {
        alert("bottom: " + e);
    }
}

function getDisponibles() {

    try {
        return document.getElementById("disponibles");
    } catch (e) {
        alert("bottom: " + e);
    }
}

function getSeleccionados() {

    try {
        return document.getElementById("seleccionados");
    } catch (e) {
        alert("getSeleccionados: " + e);
    }
}
