var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var answerSchema = new Schema({
    name: {type: String, required: true},
    percent: {type: Decimal128, required: true},
    year: {type: Number, required: true},
    sex: {type: String, required: true},
    user: {type: String, required: true},
    answer: {type: String, required: true}},
    {collection: 'answeredNames'});

var answer = mongoose.model('answer', answerSchema);
module.exports = answer;
