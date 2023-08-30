create database library;

\c library

create table book (
    id_book serial primary key,
    title varchar(100),
    description text,
    autor varchar(100)
);

create table client (
    id_client serial primary key,
    first_name varchar(150),
    last_name varchar(150),
    email varchar(150),
    phone varchar(20),
    cin varchar(50)
);

create table borrowing (
    id_borrowing serial primary key,
    loan_date date,
    return_date date,
    id_client bigint REFERENCES client(id_client)
);

create table purchase(
    id_purchase serial primary key,
    date_of_purchase date,
    price int,
    id_client bigint REFERENCES client(id_client)
);

create table to_buy(
    id_book bigint REFERENCES book(id_book),
    id_purchase bigint REFERENCES purchase(id_purchase)
);

create table to_lend(
    id_book bigint REFERENCES book(id_book),
    id_borrowing bigint REFERENCES borrowing(id_borrowing)
);