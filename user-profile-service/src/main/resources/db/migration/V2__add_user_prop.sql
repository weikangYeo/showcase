alter table user_profile
    add column oidc_user_id varchar(36);
alter table user_profile
    add column role varchar(100);