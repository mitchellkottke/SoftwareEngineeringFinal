var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var answerSchema = new Schema({
    name: {type: String, required: true},
    sex: {type: String, required: true},
    user: {type: String, required: true},
    answer: {type: String, required: true},
    year: {type: Number, required: false},
    percent: {type: Number, required: false}},
    {collection: 'Answered'});

var answer = mongoose.model('answer', answerSchema);
module.exports = answer;
