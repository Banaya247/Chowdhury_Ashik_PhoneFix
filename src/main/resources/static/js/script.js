//alert("connected test 04");

function selectStoreAndTechnician() {
    
}

function formOptionInput() {

    let obj = document.getElementById("selectForm00");

    if (obj.selectedIndex == 1) {
        document.getElementById("deviceInput").value = "Android";
    }
    else if (obj.selectedIndex == 2) {
        document.getElementById("deviceInput").value = "Apple";
    }
}



function refreshSymptomCheckboxInput() {

    location.reload();

}