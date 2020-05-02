var i = 52;
var express = require('express');
var app = express();
var mongoose = require('mongoose');
var session = require('express-session');
var bp = require('body-parser');

//For data insertion
var csv = require('fast-csv');
var babyNames = require('../db_stuff/baby-names.csv');

//Route information in routing.json
const fs = require('fs');
var route = fs.readFileSync('routing.json');
var jsonRoute = JSON.parse(route);

//Configure mongoose
mongoose.set('useNewUrlParser', true);
mongoose.set('useUnifiedTopology', true);

//Connecting mongoose to db
mongoose.connect('mongodb://ukko.d.umn.edu:4321/AppNull');
var db = mongoose.connection;

//For mongoose connection errors
db.on('error', console.error.bind(console, 'connection error:'));
db.once('open', function(){
    console.log("Conection established to " + jsonRoute.url);
    console.log("Skips: " + i);
});

//Schema declarations
var Names = require('./models/Name.js');
var Users = require('./models/User.js');
var Answered = require('./models/Answer.js');

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
app.get('/', function(req,res){
    console.log("Hello");
    res.send("Hello");
});

app.post('/insertNames', function(req,res){
    console.log("/insertNames called...");
    if(!babyNames)
        res.status(400).send('File not found');
    var names = [];
    var lineSkips = i*5000;
    csv.parseFile("../db_stuff/baby-names.csv", {
        headers : true,
        maxRows : 5000,
        skipRows : lineSkips
    })
        .on('error', function(error){ console.log(error); })
        .on("data",function(data){
            console.log("New item");
            data["_id"] = new mongoose.Types.ObjectId();
            names.push(data);
        })
        .on("end",function(){
            Names.create(names, function(err, docs){
                if(err) console.log("Error: ",err);
                else console.log("Finished section");
            });
        });
    res.send("Sent");
});

app.listen(jsonRoute.port, ()=>console.log("NULL SERVER LAUNCHED. LISTENING ON PORT: " + jsonRoute.port));
