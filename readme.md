# Toggle Ninja for Android

<img src="https://github.com/abertschi/android-remote-trigger/blob/master/landing/ninja.png" alt="Toggle Ninja" width="200px" align="">

>Be a Ninja and toggle features on your phone remotely

Toggle Ninja is a plattform for your Android phone that exposes toggle like features such as GPS, WIFI or Hotspot to the Web and SMS. It features a node.js backend and an Android app.  
This project is currently under development 🔥 🚀  

 https://abertschi.github.io/toggle-ninja/landing/

## Access
 - Web
 - REST API
 - SMS

## Features

- ping
- GPS (requires root)
- change volume
- flight mode
- lock device
- flashlight
- disturb mode
- read out loud text
- call number
- vibrate
- make noise
- turn off
- play music
- unlock phone
- take a picture
- send a text
- clean notifications


## Rest API

### General
- Response code is always 200 on success
- If response code is not 200, response contains a field called `error` with an error description

### POST /api/users/regist
- Register a user with the backend

#### Request
```json
{
    "clientSecret": "",
    "domain": "",
    "clientToken": "Client token for google cloud services"
}
```

#### Response
```json
{
    "token": "auth token for further requests on this api ...",
    "created": "date"
}
```

### POST /api/triggers/notify
#### Request
```json
{
    "token": "auth_token",
    "payload": {
        "title": "",
        "subtitle": "",
        "url": "http:// ... url_to_open_on_notification_press",
        "stack": true,
        "icon": "http:// ...."
    }
}
```

#### Response
```json
{
    "statusToken": ""
}
```

### Generic toggle API
#### GET /api/triggers/:command/
- Get status for command `:command`

##### Request
The following attributes are set as query strings:
```json
{
    "token": "auth_token"
}
```

##### Response
```json
{
    "statusToken": ""
}
```

#### POST /api/triggers/:command/:argument?parms=...
- Trigger a command

##### Request
```json
{
    "token": "auth_token",
    "intentFilter": "expose trigger request to 3rd party commands",
    "payload": {
        "quiet": false
    }
}
```

##### Response
```json
{
    "statusToken": ""
}
```

#### GET /api/triggers/status/:statusToken
The following attributes are set as query strings:
##### Request
```json
{
    "token": "auth_token"
}
```

##### Response
```json
{
    "status": "error|pending|done",
    "payload": {
    },
    "sent_at": "date",
    "received_at": "date",
    "executed_at": "date"
}
```

#### POST /api/triggers/status/:statusToken
- Update the status
##### Request
```json
{
    "token": "auth_token",
    "status": "error|pending|done",
    "executed_at": "date",
    "payload": {
    }
}
```

##### Response
```json
{
    "status": "error|pending|done",
    "payload": {
    },
    "sent_at": "date",
    "executed_at": "date"
}
```


### Service Provider Interface
Create your own plugin and call it via toggle-ninja's API.
Export your service and create an intent-filter which you pass in the body of the API calls.
Toggle Ninja will forward the request to your plugin.

```json
{
    "intentFilter": "expose trigger request to 3rd party commands",    
}
```

### Design choices
- Use token in query string or body? https://community.apigee.com/questions/28794/best-practices-for-passing-an-access-token-without.html
