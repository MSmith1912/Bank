insert into Users(userId, username, password, firstname, lastname) values (USER_SEQ.nextval, 'admin1', 'password', 'John', 'Smith');
insert into Users(userId, username, password, firstname, lastname) values (USER_SEQ.nextval, 'admin2', 'password', 'Sandra', 'Smith');
insert into Users(userId, username, password, firstname, lastname) values (USER_SEQ.nextval, 'customer1', 'password', 'Barney', 'Rubble');
insert into Users(userId, username, password, firstname, lastname) values (USER_SEQ.nextval, 'customer2', 'password', 'Betty', 'Rubble');

insert into account(accountId, customerId, balance ) values (ACCOUNT_SEQ.nextval, '1', '500.00');


