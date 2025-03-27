insert into users (username, email, password) values ('admin', 'admin@email.com', '$2a$10$e7.rdMkW6mJLKPlFb1Jp/epesxX0wFHBAsM1n3YYruPs/sUd.Rgdy');
insert into users (username, email, password) values ('user', 'user@gmail.com', '$2a$10$ahGtV9VJ.w6XOBINBU4TXOFPZIkODAaxx0yR6pXq8rHXDHSlVh9g2');

insert into roles(id, name) values (1, 'ROLE_ADMIN');
insert into roles(id, name) values (2, 'ROLE_USER');

insert into user_roles(user_id, role_id) values (1,1);
insert into user_roles(user_id, role_id) values (2,2);

insert into tasks(title, description, completed) values ('Shopping', 'Tesco', 'false');
insert into tasks(title, description, completed) values ('React', 'Scrimba course', 'false');
insert into tasks(title, description, completed) values ('Run', '10km', 'false');