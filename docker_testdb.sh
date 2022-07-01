#!/bin/bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5433/warestoredb_test
export SPRING_DATASOURCE_USERNAME=warestoreuser
export SPRING_DATASOURCE_PASSWORD=warepass

docker run -d --rm --name warestoredb_test -e POSTGRES_DB=warestoredb_test -e POSTGRES_USER=warestoreuser -e POSTGRES_PASSWORD=warepass -p 5433:5432 postgres:14.3-alpine

mvn test

docker stop warestoredb_test