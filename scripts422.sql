create database car with owner student; -- создание базы данных с пользователем студент

create table cars   -- создание таблицы машины
(
    id serial primary key,
    brand varchar(20) not null,
    model text not null,
    price int
);

alter table cars
    add constraint cars_unique unique (brand, model);

create table persons     -- создание таблицы люди
(
    id serial primary key,
    name text not null,
    age int check ( age > 0 ),
    license boolean,
    car_id int references cars (id)
);

alter table persons
    add constraint age_constraints check ( age >= 18 );
