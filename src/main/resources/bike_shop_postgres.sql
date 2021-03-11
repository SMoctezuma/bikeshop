create table account (
	-- Column name | data type | constraints <- Format
	id serial primary key,
	-- long strings use text type
	username varchar(30) unique not null,
	password varchar(30),
	roleId int references role
);

create table role (
	id serial primary key,
	name varchar(30) unique not null
);

create table product (
	id serial primary key,
	name varchar(30) unique not null,
	statusId int references status,
	category varchar(30) not null
);

create table offer (
	id serial primary key,
	accountId int references account,
	offer numeric not null,
	statusId int references status
);

create table payment (
	originalPrice numeric not null,
	amountPaid numeric not null,
	pay numeric not null,
	productId int references product
);

create table status (
	id serial primary key,
	name varchar(30) unique not null
);

alter table account drop column roleId;

alter table account add column roleId int references role;

insert into status values
	(default, 'Available'),
	(default, 'Not Available'),
	(default, 'Pending'),
	(default, 'Rejected'),
	(default, 'Accepted')
	
insert into role values
	(default, 'Customer'),
	(default, 'Employee'),
	(default, 'Manager')
	
insert into account values (default, 'steven', 'test', 1);

update status set name = 'Sold' where name='Not Available';

alter table offer add column productId int references product;
alter table offer drop column accountId;
alter table offer add column accountId int references account;

insert into product values
	(default, 'bike 1', 1, 'Bicycle'),
	(default, 'bike 2', 1, 'Bicycle'),
	(default, 'bike 3', 1, 'Bicycle')

select * from product where statusid = 1;
select * from product where statusid = 1 union select * from offer where product.id = id;

insert into account values (default, 'esteban', 'test', 2);

update product set category = 'Bicycles' where category = 'Bicycle';

alter table product add column owner int references account;

insert into product values
	(default, 'moped', 1, 'Bicycle')
	
delete from payment where accountid isnull;

update product set category = 'Bicycles' where category = 'Bicycle';

insert into payment values
	(500.00, 0, 0, 8, 1)

insert into payment values
	(700.00, 0, 0, 5, 1)

update offer set statusid = 3 where productid = 2;