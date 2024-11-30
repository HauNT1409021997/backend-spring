-- Create the 'test' schema if it doesn't exist
CREATE SCHEMA IF NOT EXISTS postgresqlDB;

-- Switch to the 'postgresqlDB' schema
SET search_path TO postgresqlDB;

DROP TABLE IF EXISTS postgresqlDB.user_info;
-- Create the 'users' table if it doesn't exist
CREATE TABLE IF NOT EXISTS postgresqlDB.user_info
(
    id    SERIAL  PRIMARY KEY,
    name  VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);

-- Insert initial data into the 'users' table
-- INSERT INTO postgresqlDB.user_info (id, name, email)
-- VALUES (0, 'alice', 'alice@example.com'),
--        (1, 'bob', 'bob@example.com')
-- ON CONFLICT DO NOTHING;

