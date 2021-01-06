create table if not exists users(
    id BIGSERIAL primary key not null  ,
    username varchar(255) not null unique,
    password varchar(255) not null
);

insert into users(username, password)
values('JohnD','123456HH');