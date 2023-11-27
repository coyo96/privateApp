-- ********************************************************************************
-- This script creates the database users and grants them the necessary permissions
-- ********************************************************************************

CREATE USER events_project_owner
WITH PASSWORD 'eventsproject';

GRANT ALL
ON ALL TABLES IN SCHEMA public
TO events_project_owner;

GRANT ALL
ON ALL SEQUENCES IN SCHEMA public
TO events_project_owner;

CREATE USER events_project_appuser
WITH PASSWORD 'eventsproject';

GRANT SELECT, INSERT, UPDATE, DELETE
ON ALL TABLES IN SCHEMA public
TO events_project_appuser;

GRANT USAGE, SELECT
ON ALL SEQUENCES IN SCHEMA public
TO events_project_appuser;
