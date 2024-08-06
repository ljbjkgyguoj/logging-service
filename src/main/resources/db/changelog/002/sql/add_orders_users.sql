insert into users values (nextval('users_id_seq'), 'user1', 'user1@email.com');
insert into users values (nextval('users_id_seq'), 'user2', 'user2@email.com');
insert into users values (nextval('users_id_seq'), 'user3', 'user3@email.com');
insert into users values (nextval('users_id_seq'), 'user4', 'user4@email.com');
insert into users values (nextval('users_id_seq'), 'user5', 'user5@email.com');

insert into orders values (nextval('orders_id_seq'), '11111', 'CREATED', (select id from users where name='user5'));
insert into orders values (nextval('orders_id_seq'),'22222', 'IN_PROGRESS', (select id from users where name='user1'));
insert into orders values (nextval('orders_id_seq'),'33333', 'IN_PROGRESS', (select id from users where name='user2'));
insert into orders values (nextval('orders_id_seq'),'44444', 'DONE', (select id from users where name='user3'));
insert into orders values (nextval('orders_id_seq'),'55555', 'CANCELED', (select id from users where name='user2'));
insert into orders values (nextval('orders_id_seq'),'66666', 'CREATED', (select id from users where name='user1'));
insert into orders values (nextval('orders_id_seq'),'66666', 'DONE', (select id from users where name='user4'));
insert into orders values (nextval('orders_id_seq'),'66666', 'CANCELED', (select id from users where name='user4'));