var express = require('express');
var app = express();
var mongoose = require('mongoose');
var session = require('express-session');
var bp = require('body-parser');

//Route information in routing.json
const fs = require('fs');
var route = readFileSync('routing.json');
var jsonRoute = JSON.parse(route);

//Connecting mongoose to db
mongoose.connect('mongodb://ukko.d.umn.edu:4321/AppNull');
var db = mongoose.connection;

//For mongoose connection errors
db.on('error', console.error.bind(console, 'connection error:'));
db.once('open', function(){
    console.log("Contion established to mongodb://ukko.d.umn.edu:4321/AppNull");
});

//Schema declarations


//Login tracking
app.use(session({
    secret: 'hardly technical',
    resave: true,
    saveUninitialized: false
}));

app.use(bp.json());
app.use(bp.urlencoded({extended: false}));

console.log("\nStarting Server, set-up complete\n");

//Write functions here
