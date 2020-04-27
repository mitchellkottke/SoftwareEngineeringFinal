var express = require('express')
var MongoClient = require("mongodb").MongoClient
var app = express()

console.log("Swap address you are using for mongo here then erase me")
const url = "mongodb://serveraddress"
const database = "databasename"
const PORTNUMBER = 9999
var db = null //This will cause an error if connection fails, good to know for troubleshooting

MongoClient.connect(url, function(err, client) {
    console.log("Connected successfully to server");
    db = client.db(database);
});


app.listen(PORTNUMBER, ()=>{console.log("listening on " + PORTNUMBER)})
