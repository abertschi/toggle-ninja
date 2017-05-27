"use strict";

const express = require('express');
const bodyParser = require('body-parser');
const Api = require('./api');

if (process.env.NODE_ENV == 'PRODUCTION') {
    console.log('running in PRODUCTION env');
    var fs = require('fs');
    var util = require('util');
    var log_file = fs.createWriteStream(__dirname + '/debug.log', {
        flags: 'a'
    });
    var log_stdout = process.stdout;

    console.log = function(d) { //
        log_file.write(util.format(d) + '\n');
        log_stdout.write(util.format(d) + '\n');
    };
}

const app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
    extended: true
}));
app.use('/', express.static('assets'));
app.post('/api/users/regist', Api.verifyClientSecret, Api.register);
app.post('/api/trigger/:command/:arg?', Api.verifyToken, Api.toggle);
app.listen(3000, () => {
    console.log('Example app listening on port 3000!');
});
