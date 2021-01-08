create table if not exists users(
    id BIGSERIAL primary key not null,
    username varchar(255) not null unique,
    password varchar(255) not null,
    authority varchar(64)  not null
);

insert into users(username, password,authority)
values('JohnD','123456HH','ROLE_ADMIN');

insert into users(username, password,authority)
values('bill','333','ROLE_USER');