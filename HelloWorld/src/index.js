var express = require('express');
var uuid = require('node-uuid');

var net = require('net');
var os = require('os')

var PORT = 8080;

var serverUUID = uuid.v4();
// Constants

var serverIp = "unkown";

// App
var app = express();
app.get('/', function (req, res) {
  var reqUUID = uuid.v4();
  var message = '<h1>Hello JUG Dortmund from a Dockerfile Demo!</h1><p>Created via dockerfile</p><p>My UUID is : '+serverUUID + '</p><p>My Ip is: ' + req.host + '</p><p>Your request UUID is: ' + reqUUID + '</p><ul>';
  
  message += '</ul>'
  res.send(message);
  console.log('UUID : ' + serverUUID + ' Request ID : ' + reqUUID );
});

app.listen(PORT);
console.log('Running on http://localhost:' + PORT);
console.log('UUID is:' + serverUUID);
