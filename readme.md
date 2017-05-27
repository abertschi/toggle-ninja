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
    "clientSecret": ""
}
```
#### Response
```json
{
    "id": "uuid ....",
    "token": "auth token for further requests ...",
    "created": "date"
}
```

### Generic toggle API
- Supported `:command` values:
 - bluetooth
 - wifi
 - hotspot

- Supported `argument` values:
 - on
 - off
 - toggle

#### GET /api/triggers/:command/
- Get status

##### Request
```json
{
    "token": "auth_token"
}
```
##### Response
```json
{
    "status": "..."
}
```

#### POST /api/triggers/:command/:argument
- Toggle command

#### Request
```json
{
    "token": "auth_token"
}
```

### POST /api/triggers/notify
#### Request
```json
{
    "token": "auth_token",
    "title": "",
    "subtitle": "",
    "url": "",
    "stack": true,
    "icon": "http:// ...."
}
```
