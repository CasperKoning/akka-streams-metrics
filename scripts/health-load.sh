#!/bin/bash

echo 'GET http://localhost:8080/health' | vegeta attack > /dev/null