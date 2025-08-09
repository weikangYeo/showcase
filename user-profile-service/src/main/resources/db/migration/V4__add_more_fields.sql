alter table user_profile
    CHANGE COLUMN name username varchar (100);

alter table user_profile
    add column first_name varchar(100);

alter table user_profile
    add column last_name varchar(100);

alter table user_profile drop column role;

create table permission
(
    permission_key varchar(255) not null primary key,
    name           varchar(255),
    description    varchar(500)
);

create table role
(
    id        bigint not null auto_increment primary key,
    role_name varchar(255)
);

create table role_permission
(
    role_id        bigint       not null,
    permission_key varchar(255) not null,
    PRIMARY KEY (role_id, permission_key),
    constraint fk_role_permission_role_id
        foreign key (role_id) references role (id),
    constraint fk_role_permission_permission_key
        foreign key (permission_key) references permission (permission_key)
);

create table user_role
(
    user_id bigint not null,
    role_id bigint not null,
    PRIMARY KEY (user_id, role_id),
    constraint fk_user_role_role_id
        foreign key (role_id) references role (id),
    constraint fk_user_role_user_id
        foreign key (user_id) references user_profile (id)
);

insert into permission (permission_key, name, description)
values ('allow_casa_read', 'Allow Read Casa Account Permission',
        'Allow Read Casa Account Permission');

insert into role (role_name)
values ('Basic Casa User');

insert into role_permission (role_id, permission_key)
values (1, 'allow_casa_read');