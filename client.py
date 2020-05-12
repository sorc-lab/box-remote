#!/usr/bin/env python3

import requests

resp = requests.get('http://localhost:8080/bleh')

if (resp.status_code != 200):
    # https://stackoverflow.com/questions/30970905/python-conditional-exception-messages
    # raise ApiError('GET / {}'.format(resp.status_code))
    print(resp.status_code)

for line in resp.json():
    print(line)
