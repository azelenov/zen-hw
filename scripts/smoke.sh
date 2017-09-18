#!/usr/bin/env bash

#The scripts require properties configurations
. src/test/resources/user/admin.properties


curl -i -u  ${username}/token:${token} https://${org}.zendesk.com/api/v2/users/me.json

echo ""
