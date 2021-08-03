var initialized = false
var canvas = null
var context = null
var canvasfont = "60px Arial"
var timestamp = new Date()
var dataRate = new Date()
var currenttime = new Date()
var wsalive = false
var socket = null
var config = null
var data = null

// Button that shows when connection is  lost
var btnreconnect = document.createElement("input")
btnreconnect.setAttribute('type', 'button')
btnreconnect.setAttribute('value', 'reconnect')
btnreconnect.setAttribute('id','connectbutton')
btnreconnect.setAttribute('onclick', "connect()")
btnreconnect.setAttribute('class',"button")
connect()

function connect()
{
    socket = new WebSocket("ws://localhost:9123/climate/socket");

    socket.onopen = function (event) 
    {
      wsalive = true
      socket.send("config");
      socket.send("subscribe");
    };

    socket.onclose = function(event) 
    {
      wsalive = false
      console.log("disconnected")
      draw()
    };

    socket.onmessage = function (event) 
    {
      now = new Date()
      dataRate = now - currenttime
      
      var object = null
      if(event.data != "disconnected" && event.data !="subscribe")
      {
        object = JSON.parse(event.data)
        
        if(object.units != null)
        {
          config = object
        }

        else 
        {
          data = object
          currenttime = now
        }
  
          draw()
      }
    }
}

window.onload = function() 
{
  init()
}

function canvasresize()
{
  canvas.width = window.innerWidth; //document.width is obsolete
  canvas.height = window.innerHeight; ; //document.height is obsolete
  context.fillStyle = "#000000";
  context.fillRect(0, 0, canvas.width, canvas.height);
  draw()
}

function init()
{
  canvas = document.getElementById('drawing')
  context = canvas.getContext("2d")
  window.addEventListener('resize', canvasresize)
  canvasresize()
  context.font = canvasfont
  initialized = true
}

function draw()
{

  if(context != null)
  {
    context.font = canvasfont
    context.fillStyle = "#000000";
    context.fillRect(0, 0, canvas.width, canvas.height);
    context.fillStyle = "#FFFFFF";
  }

  if(data != null)
  {
    drawTemp()
    drawHumid()
    drawAirQ()
  }
    drawDiagnostics()
}

function drawDiagnostics()
{
  var controlConnected = false;
  if(data!=null)
  {
    controlConnected = data.controllerConnected
  }
    context.fillStyle = "#FFFFFF";
    context.font = "20px Arial"

    if(controlConnected)
    {
      context.fillText("WS rate: " + dataRate + " ms", 10, canvas.height - 50, canvas.width/3); 
    }

    context.fillText("controller alive: " + controlConnected, 10, canvas.height -  100, canvas.width/3);
    context.fillText("ws alive: " + wsalive, 10, canvas.height -  150, canvas.width/3); 
    if(wsalive == false)
    {
      document.body.insertBefore(btnreconnect, canvas)
    }

    var present = document.getElementById("connectbutton")
    if(wsalive && present )
    {
      document.body.removeChild(btnreconnect)
    }
}

function drawTemp() 
{
    units = "C"
    max = 38
    if(config.units == "1")
    {
      units = "F"
      max = 100
    }
    context.fillText("Temperature: " + data.temperature + units, canvas.width *0.33, 50, canvas.width/3); 
    context.strokeStyle = "red"
    context.lineWidth = 20
    context.beginPath()
    context.arc(canvas.width*0.33, 150, 75, 0, (2 * Math.PI) * data.temperature/max);
    context.stroke()
}

function drawHumid() 
{

  if(data != null)
  {
    context.fillStyle = "#FFFFFF"
    context.fillText("Humidity: " + data.humidity + "%" , canvas.width * 0.66, 50, canvas.width/3);
    context.strokeStyle = "blue";
    context.lineWidth = 20
    context.beginPath();
    humidity = data.humidity
    context.arc(canvas.width * 0.66, 150, 75, 0, (2 * Math.PI) * humidity/100);
    context.stroke();
  }
}

function drawAirQ()
{
  if(config != null && config.airQualityEnable == "1")
  {
      quality = ""
      if(data.airQuality < 200)
      {
        quality = "Good"
      }

      else if(data.airQuality > 200 && quality < 300)
      {
        quality = "Moderate"
      }

      else if(data.airQuality > 300)
      {
        quality = "Bad"
      }

    context.textAlign = 'center'
    context.fillText("Air Quality: " + quality,  canvas.width/2, canvas.height/2, canvas.width/3);
    context.strokeStyle = "yellow";
    context.lineWidth = 20
    context.beginPath();
    context.arc(canvas.width/2, canvas.height/1.5, 75, 0, (2 * Math.PI) * data.airQuality/300);
    context.stroke();
  }
}

function drawconnecting()
{
  context.fillText("Connecting....", 10, 50); 
}