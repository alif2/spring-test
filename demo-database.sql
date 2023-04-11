use demo;

drop table if exists person;

create table person
(
    Id        int not null auto_increment,
    FirstName varchar(255),
    LastName  varchar(255),
    primary key (Id)
);

insert into person(FirstName, LastName) values ("John", "Smith");