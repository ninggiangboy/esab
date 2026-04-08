--liquibase formatted sql

--changeset esab:008-01-add-permissions-to-grp-group-members
ALTER TABLE grp_group_members
    ADD COLUMN permissions BIGINT NOT NULL DEFAULT 0;

COMMENT ON COLUMN grp_group_members.permissions IS
    'Bitmask of GroupAdminPermission bits. Only meaningful for ADMIN role members. OWNER implicitly holds all permissions.';
--rollback ALTER TABLE grp_group_members DROP COLUMN permissions;
