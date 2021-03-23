create sequence hibernate_sequence start 1 increment 1;

create table user_role (
    user_id int8 not null,
    roles varchar(255)
);

create table tests (
    id int8 not null,
    name varchar(255),
    description varchar(255),
    creator int8 not null,
    start timestamp not null,
    stop timestamp not null,
    creation_date varchar(255) not null,
    primary key (id)
);

create table tasks (
    id int8 not null,
    name varchar(255),
    test_text text,
    input_task varchar(255),
    answer varchar(255),
    primary key (id)
);

create table results (
    id int8 not null,
    task varchar(255) null,
    test int8 not null,
    user_name varchar(255) null,
    result varchar(255),
    primary key (id)
);

create table usr (
    id int8 not null,
    activation varchar(255),
    active boolean not null,
    email varchar(255),
    password varchar(255) not null,
    username varchar(255) not null,
    primary key (id)
);

alter table if exists user_role
    add constraint user_role_user_fk
    foreign key (user_id) references usr;

