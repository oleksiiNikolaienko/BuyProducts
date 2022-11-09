CREATE TABLE users (
	id serial,
	firstname varchar(20),
	lastname varchar(20),
	money numeric(10)
);
insert into users(firstname, lastname, money) values ('Olga', 'Sidorova', 200);
insert into users(firstname, lastname, money) values ('Mihail', 'Petrov', 100);
insert into users(firstname, lastname, money) values ('Petr', 'Ivanoc', 50);







CREATE TABLE products (
	id serial,
	name varchar(20),
	price numeric(10)
);

insert into products(name, price) values ('cucumber', 20);
insert into products(name, price) values ('fish', 44);
insert into products(name, price) values ('potato', 13);







CREATE TABLE infopurchase (
	user_id int,
	product_id int
);



