"use strict";

const express = require('express');
const unirest = require('unirest');
const bodyParser = require('body-parser');
const low = require('lowdb')
const db = low('db.json')
var uuid = require('node-uuid');

const API_KEY = 'AIzaSyC3qeVslFcd5xpvij_sBjWKBa6J9BpFtUg';
const GCS_URL = 'https://gcm-http.googleapis.com/gcm/send';
const CLIENT_TOKEN = '5A1xWB6W-uwJw42iiHDODfdfb4Ere01Tm3auJkXXDaLfbCsCfgnWaKiJErFExOOU8Jh0x33cpPe';

db.defaults({
  clients: []
}).value()

const app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
  extended: true
}));

let tokenAuth = (req, res, next) => {
  let client = db.get('clients')
    .find({
      id: req.body.token
    }).value();

  if (client) {
    next();

  } else {
    res.status(401).send({
      error: 'Unauthorized'
    });
  }
}

let clientTokenAuth = (req, res, next) => {
  if (CLIENT_TOKEN != req.body.clientSecret) {
    res.status(401).send({
      error: 'Unauthorized'
    });
  } else {
    next();
  }
}

let prepareRequest = () => {
  return unirest.post(GCS_URL)
    .headers({
      'Content-Type': 'application/json',
      'Authorization': `key=${API_KEY}`
    });
}

app.get('/', function(req, res) {
  res.send('Android Remote Trigger');
});

app.post('/api/client', clientTokenAuth, (req, res, next) => {
  let client;

  if (req.body.id) {
    client = db.get('clients')
      .find({
        id: req.body.id
      }).value();

    if (client) {
      console.log('Updating client ' + client.id + ' with new token ' + req.body.token)
      let numberOfTokenUpdates = client.numberOfTokenUpdates || 0;

      client = db.get('clients')
        .find({
          id: client.id
        })
        .assign({
          token: req.body.token,
          updated: new Date(),
          numberOfTokenUpdates: numberOfTokenUpdates + 1
        })
        .value();
    }
  }

  if (!client || !req.body.id) {
    client = {
      id: uuid.v1(),
      token: req.body.token,
      created: new Date()
    };
    console.log('Adding new client ' + client.id + ' with token ' + client.token)
    db.get('clients').push(client).value();
  }

  res.send(client);
});

app.post('/api/trigger/:command/:arg?', tokenAuth, (req, res, next) => {
  if (!req.params.command) {
    res.status(500).send();
  }
  let command = req.params.command;
  let clientId = req.body.token;
  let arg = req.params.arg || '';
  let payload = req.query || '';

  console.log(`Triggering ${command} with ${arg} ${payload} for client ${clientId}`);

  prepareRequest()
    .send({
      to: clientId,
      data: {
        command: command,
        arg: arg,
        payload: payload
      }
    })
    .end(response => {
      if (!response.body.success) {
        res.status(500).send({
          error: 'Invalid client registration token'
        })
      };
      res.status(200).send();
    });
});

app.listen(3000, () => {
  console.log('Example app listening on port 3000!');
});
