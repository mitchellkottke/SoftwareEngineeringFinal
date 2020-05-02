var mongoose = require('mongoose');
var Schema = mongoose.Schema;
var random = require('mongoose-simple-random');

var nameSchema = new Schema({
    name: {type: String, required: true},
    year: {type: Number, required: true},
    sex: {type: String, required: true},
    percent: {type: Number, required: true}},
    {collection: 'Names'});
nameSchema.plugin(random);

var name = mongoose.model('names', nameSchema);
module.exports = name;
