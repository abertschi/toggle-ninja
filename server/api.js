"use strict";

const unirest = require('unirest');
const uuid = require('node-uuid');
const Bacon = require('baconjs').Bacon;
const _ = require('lodash');
const Users = require('./users');

const API_KEY = 'AIzaSyC3qeVslFcd5xpvij_sBjWKBa6J9BpFtUg';
const GCS_URL = 'https://gcm-http.googleapis.com/gcm/send';
const CLIENT_TOKEN = '5A1xWB6W-uwJw42iiHDODfdfb4Ere01Tm3auJkXXDaLfbCsCfgnWaKiJErFExOOU8Jh0x33cpPe';

function _respond(stream, res, retCode) {
    stream.onValue((val) => res.status(retCode || 200).send(val));
    stream.onError((err) => res.status(500).send(err));
}

function _streamToGcs(payload) {
    return Bacon.once()
        .flatMap((__) => {
            return unirest.post(GCS_URL)
                .headers({
                    'Content-Type': 'application/json',
                    'Authorization': `key=${API_KEY}`
                });
        })
        .flatMap((req) => {
            return Bacon.fromBinder((sink) => {
                req.send(payload).end((response) => {
                    sink(response);
                });
            });
        });
}

function verifyToken(req, res, next) {
    let userRequest = Users.findUser(req.body.token);

    userRequest.onValue((user) => {
        if (user) next();
        else res.status(401).send({
            error: 'Unauthorized'
        });
    });
}

function verifyClientSecret(req, res, next) {
    console.log('client auth with token ' + req.body.clientSecret);
    if (CLIENT_TOKEN != req.body.clientSecret) {
        res.status(401).send({
            error: 'Unauthorized'
        });
    } else {
        next();
    }
}

function register(req, res, next) {
    let id = req.body.id;
    let token = req.body.token;
    let client = Users.findUser(id);
    let response = client
        .flatMap((user) => {
            if (user == null) {
                console.log("Regist new user");
                return {
                    id: uuid.v1(),
                    token: token,
                    created: new Date()
                };
            } else {
                console.log("Updating user", user);
                let updates = user.updates || 0;
                user.updates = updates += 1;
                user.updated = new Date();
                user.token = token;
                return user;
            }
        })
        .flatMap((user) => createOrUpdateClient(user.id, user))

    response.onValue((user) => res.send(user));
    response.onError((err) => res.send(500, err));
}

function toggle(req, res, next) {
    let userId = req.body.token;
    let command = req.params.command;
    let arg = req.params.arg || '';
    let parms = ''; // TODO
    let payload = req.body.payload || '';

    let userStream = Users.findUser(userId);
    let validateInput = userStream.flatMap((user) => {
        if (user == null) return Bacon.Error('User not found');
        if (!command) return Bacon.Error('Command not set');
        return Bacon.once(user);
    });
    let gcsResponse = validateInput.flatMap((user) => {
        return _streamToGcs({
            to: user.token,
            data: {
                command: command,
                arg: arg,
                payload: payload,
                parms: parms
            }
        });
    });

    let response = gcsResponse.flatMap((result) => {
        if (response.error) return Bacon.Error('Invalid client registration token: ' + JSON.stringify(response.error));
        else return {};
    });
    _respond(response, res);
}

// GET /api/triggers/:command/
function status(req, res, next) {
    let userId = req.body.token;
    let command = req.params.command;

    let userStream = Users.findUser(userId);
    let validateInput = userStream.flatMap((user) => {
        if (user == null) return Bacon.Error('User not found');
        if (!command) return Bacon.Error('Command not set');
        return Bacon.once(user);
    });
    validateInput.flatMap(user => {
        return null; // TODO;
    });
}

module.exports = {
    toggle: toggle,
    register: register,
    verifyToken: verifyToken,
    verifyClientSecret: verifyClientSecret
}
