insert into users (username, email, password) values ('admin', 'admin@email.com', '$2a$10$e7.rdMkW6mJLKPlFb1Jp/epesxX0wFHBAsM1n3YYruPs/sUd.Rgdy');

insert into roles(id, name) values (1, 'ROLE_ADMIN');

insert into user_roles(user_id, role_id) values (1,1);
