#!/usr/bin/env bash

#The scripts require properties configurations
. src/test/resources/user/admin-user.properties


curl -i -u  ${username}/token:${token} ${url}/users/me.json

echo ""
