"use strict";

const low = require('lowdb')
const db = low('db.json');
var uuid = require('node-uuid');
const Bacon = require('baconjs').Bacon;
const _ = require('lodash');

db.defaults({
    clients: [],
    requestStati: []
}).value();


function findUser(id) {
    return Bacon.fromBinder((sink) => {
        let client = db.get('clients').find({
            id: id
        }).value();
        sink(client);
        sink(new Bacon.End());
    });
}

function createOrUpdateClient(id, user) {
    return Bacon.fromBinder((sink) => {
        let client = db.get('clients').find({
            id: id
        }).value();
        if (client == null) {
            client = db.get('clients').push(user).value();
            sink(user);
        } else {
            client = db.get('clients').find({
                id: id
            }).assign(user).value();
            sink(client);
        }
        sink(new Bacon.End());
    });
}

function createOrOverwriteRequestStatus(id, payload) {
    return getRequestStatus(id).flatMap(status => {
        if (status == null) {
            return db.get('requestStati').push(payload).value();
        } else {
            return db.get('requestStati').find({
                id: id
            }).assign(payload).value();
        }
    })
}

function getRequestStatus(id) {
    return Bacon.fromBinder((sink) => {
        let client = db.get('requestStati').find({
            id: id
        }).value();
        sink(client);
        sink(new Bacon.End());
    });
}

module.exports = {
    findUser: findUser,
    createOrUpdateClient: createOrUpdateClient,
    getRequestStatus: getRequestStatus,
    createOrOverwriteRequestStatus: createOrOverwriteRequestStatus
}
