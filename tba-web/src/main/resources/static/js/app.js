var stompClient = null;
var vehicleFrames=[];
function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (message) {
             var json = JSON.parse(message.body)
             var vehicleId= json.vehicleId
             if(vehicleFrames[vehicleId]==null)
             {
                 vehicleFrames[vehicleId]="<span>" +vehicleId+
                     "</span><div class='log' id='" +vehicleId+
                     "_frame'>&nbsp;</div>";
                     $("#frames").append(vehicleFrames[vehicleId]);
             }
            showMessage(json,vehicleId);
        });
    });
}
function showMessage(messageBodyJson,vehicleId) {
    var str ="timeStamp="+messageBodyJson.timeStamp+","+
        "longitude="+round(messageBodyJson.startLongitude)+","+
        "latitude="+round(messageBodyJson.startLatitude)+","+
        "status="+messageBodyJson.status;
    console.log("<p>"+str+"</p>");
    $("#"+vehicleId+"_frame").append(str+"</br>");
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function move() {
    if(!validateMove()){
        return;
    }

    $.ajax({url: "/start",
        data:{'vehicleId': $("#vehicleId").val(),
            'speed':$("#speed").val(),'period':$("#period").val(),'direction':$("#direction").val()},
        success: function(result){
            alert("started");
        }})
}
function create() {
    if(!validateCreate()){
        return;
    }
    $.ajax({url: "/create",
        data:{'vehicleId': $("#vehicleId1").val(),
            'startLatitude':$("#startLatitude").val(),'startLongitude':$("#startLongitude").val()},
        success: function(result){
            var newVehicle=$("#vehicleId1").val();
            var option = $('<option></option>').attr("value", newVehicle).text(newVehicle);
            $("#vehicleId").append(option);
            $("#create-form").trigger("reset");
            alert(result);
        }})

}
function fillVehicles(){
    $(function () {
        $.ajax({url:"/vehicles",success:function (result) {
                if(result!=null && result!='') {
                    var vehicles = Array.from(result);
                    vehicles.forEach(function (element) {
                        var option = $('<option></option>').attr("value", element).text(element);
                        $("#vehicleId").append(option);
                    })
                }
            }})

    });
}
function validateCreate(){
    if($("#vehicleId").val()=='' || $("#startLatitude").val()=='' || $("#startLongitude").val()==''){
         alert("All Fields are mandatory");
         return false;
    }else if(isNaN($("#startLatitude").val())){
        alert("start latitude should be number");
        return false;
    }else if(isNaN($("#startLongitude").val())){
        alert("start longitude should be number");
        return false;
    }
    return true;

}
function  validateMove(){
    if($("#vehicle").val()=='' || $("#speed").val()=='' ||
        $("#direction").val()=='' ||
        $("#period").val()=='' ) {
        alert("All Fields are mandatory");
        return false;
    }else if(isNaN($("#speed").val())){
        alert("speed should be number");
        return false;
    }else if(isNaN($("#direction").val())){
        alert("Direction should be number");
        return false;
    }else if(isNaN($("#period").val())){
        alert("Period should be number");
        return false;
    }
    return true;
}

/**
 * this function round numbers to 4 decimal digits
 * @param double
 */
function round(doubleStr){
    var rounded=doubleStr*10000;
    rounded= Math.round(rounded);
    rounded=rounded/10000;
    return rounded;
}
$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });

    fillVehicles();
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#move" ).click(function() { move(); });
    $( "#create" ).click(function() { create();});

    connect();

});


