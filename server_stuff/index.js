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
//    console.log("Skips: " + i);
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

/**
   DONT FUCKING CALL THIS GODDAMN FUNCTION YOU WILL OVERLOAD THE
   SERVER IF IT ISNT DONE RIGHT
*/
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


//Name decision stuff

/**
   Get a random name that the user hasnt answered yet
   @author kottk055
   @param req
       user: Current user, REQUIRED
       sex: Boy or girl, which sex the user wants to filter by, OPTIONAL
*/
app.post('/getName', function(req,res){
    console.log("/getName called...");
    var user = req.body.user;
    var checkAnswered = function(name){
        console.log("Checking if name has been answered");
        var checkName = {
            name : name.name,
            user : user
        };
        Answered.findOne(checkName, function(err,doc){
            if(err){
                console.log("Error checking for name");
                res.status(402).send("Cannot check name at this time");
            }else if(doc){
                console.log("Name already answered");
                getName();
            }else{
                console.log("Name has not been answered, sending");
                res.status(200).send(name);
            }
        });
    };
    var getName = function(){
        console.log("Looking for name");
        if(filter){
            Names.findOneRandom(filter, function(err,doc){
                if(err){
                    console.log("Error", err);
                    res.status(401).send("Cannot get name at this time");
                }else{
                    console.log("Found name");
                    checkAnswered(doc);
                }
            });
        }else{
            Names.findOneRandom(function(err,doc){
                if(err){
                    console.log("Error", err);
                    res.status(401).send("Cannot get name at this time");
                }else{
                    console.log("Found name");
                    checkAnswered(doc);
                }
            });
        }
    };
    var filter;
    if(req.body.sex) filter = {sex:req.body.sex}
    else filter = 0;
    getName();
});

app.post('/nameAnswered', function(req,res){
    var answer = {
        user : req.body.user,
        name : req.body.name,
        percent : req.body.percent,
        sex : req.body.sex,
        year : req.body.year,
        answer : req.body.answer
    };
});

//Login Stuff
// @author Justin

app.post('/createUser', function(req,res) {
	console.log("/creatUser called...");
	var newUser = {
		username: req.body.user,
		password: req.body.password
	}
	Users.create(newUser, function(err, doc) {
	if(err){
		console.log("Error: "+err);
		res.send("Error could not add user to collection");
	}
	else {
		console.log("Successfully created new user");
		res.send("Successfully created new user");
	}
	});
});

app.post('/checkPassword', function(req,res) {
	console.log("/checkPassword called...");
	var user = req.body.user;
	var password = req.body.password;
	console.log("Checking if username is in the users database");
	Users.findOne({username:user}, function(err, doc){
	if(err || !doc){
		console.log("This username does not exist");
		res.send("This username does not exist");
		}
	else { 
		console.log("User: "+user+" has been found"); 
		Users.findOne({username:user,password:password}, function(err, doc){ 
			if(err || !doc) {
			console.log("The password for this user is incorrect");
			res.send("The password is incorrect");
			}
			else {
			console.log("The password is correct!");
			res.send("The password is correct");
			}
		});
	}
});
});

app.listen(jsonRoute.port, ()=>console.log("NULL SERVER LAUNCHED. LISTENING ON PORT: " + jsonRoute.port));
