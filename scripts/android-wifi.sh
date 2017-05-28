#!/bin/bash

source android-config.conf
url=$server_url"/api/trigger/ping/switch"

curl -d '{"token":"'"$token"'"}' -H "Content-Type: application/json" $url
