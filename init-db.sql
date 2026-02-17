#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE DATABASE auth_db;
    CREATE DATABASE user_db;
    CREATE DATABASE order_db;
    CREATE DATABASE payment_db;
    CREATE DATABASE notification_db;
    CREATE USER microuser WITH PASSWORD 'micropass';
    GRANT ALL PRIVILEGES ON DATABASE auth_db TO microuser;
    GRANT ALL PRIVILEGES ON DATABASE user_db TO microuser;
    GRANT ALL PRIVILEGES ON DATABASE order_db TO microuser;
    GRANT ALL PRIVILEGES ON DATABASE payment_db TO microuser;
    GRANT ALL PRIVILEGES ON DATABASE notification_db TO microuser;
EOSQL
