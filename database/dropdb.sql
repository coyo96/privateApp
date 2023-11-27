-- **************************************************************
-- This script destroys the database and associated users
-- **************************************************************

-- The following line terminates any active connections to the database so that it can be destroyed
SELECT pg_terminate_backend(pid)
FROM pg_stat_activity
WHERE datname = 'events_project';

DROP DATABASE events_project;

DROP USER events_project_owner;
DROP USER events_project_appuser;