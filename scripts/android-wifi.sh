#!/bin/bash

source config.conf
url=$server_url"/api/trigger/wifi/switch"

curl -d '{"token":"'"$token"'"}' -H "Content-Type: application/json" $url
