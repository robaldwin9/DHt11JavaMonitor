

var canvas = null
var context = null
var temp = null
var humidity = null
var airq = null
var control = false
var initialized = false
var canvasfont = "60px Arial"
var timestamp = new Date()
var dataRate = new Date()
var currenttime = new Date()
var wsalive = false
var socket = null


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

      
    currenttime = new Date()
    dataRate = currenttime - timestamp
    timestamp = currenttime

    values = event.data.split(",")
    for(let i =0; i < values.length; i++)
    {

      value = values[i].split(":")

      if(value[0]==="temp" && temp != value[1])
      {
       
          temp = value[1]
      }

      else if(value[0]==="Humid" && humidity != value[1])
      {
        humidity = value[1]
      }

     else if(value[0]==="airq" && airq != value[1])
    {

        airq = value[1]

        if(airq < 200)
        {
         airq = "Good"
        }

        else if(airq > 200 && quality < 300)
        {
          airq = "Moderate"
        }


        else if(quality > 300)
        {
          airq = "Bad"
        }

    }

    else if(value[0]==="control")
    {
        control = (value[1]==="true") 
    }

    else
    {
      console.log("error occured in parsing value in WS value")
    }

  }

  if(initialized)
  {
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
  context.font = canvasfont
  context.fillStyle = "#000000";
  context.fillRect(0, 0, canvas.width, canvas.height);
  context.fillStyle = "#FFFFFF";
  drawTemp()
  drawHumid()
  drawAirQ()
  drawDiagnostics()

}

function drawHumid() {

  if(humidity != null)
  {
    context.fillStyle = "#FFFFFF"
    context.fillText("Humidity: " + humidity , canvas.width - canvas.width/3, 50, canvas.width/3);
  
      context.strokeStyle = "blue";
      context.lineWidth = 20
      context.beginPath();
      humidity = humidity.replace('%', '')
      console.log(humidity)
      context.arc(canvas.width - canvas.width/4, 150, 75, 0, (2 * Math.PI) * humidity/100);
      context.stroke();
    }
}

function drawDiagnostics()
{
    context.fillStyle = "#FFFFFF";
    context.font = "20px Arial"
    context.fillText("WS rate: " + dataRate + " ms", 10, canvas.height - 50, canvas.width/3); 
    context.fillText("controller alive: " + control, 10, canvas.height -  100, canvas.width/3);
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


function drawTemp() {

  if(temp != null)
  {
    context.fillText("Temperature: " + temp, canvas.width/9, 50, canvas.width/3); 


      context.strokeStyle = "red";
      context.lineWidth = 20
      context.beginPath();
      temp = temp.replace('F', '').replace('C', '')
      console.log(humidity)
      context.arc(canvas.width/4.3, 150, 75, 0, (2 * Math.PI) * temp/100);
      context.stroke();
  }
}

function drawAirQ()
{
  if(airq != null)
  {
    context.fillText("Air Quality: " + airq,  canvas.width/3 + 60, canvas.height/2, canvas.width/3);
  }
}

function drawconnecting()
{
  context.fillText("Connecting....", 10, 50); 
}