#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE USER microuser WITH PASSWORD 'micropass';
    CREATE DATABASE auth_db OWNER microuser;
    CREATE DATABASE user_db OWNER microuser;
    CREATE DATABASE order_db OWNER microuser;
    CREATE DATABASE payment_db OWNER microuser;
    CREATE DATABASE notification_db OWNER microuser;
EOSQL
