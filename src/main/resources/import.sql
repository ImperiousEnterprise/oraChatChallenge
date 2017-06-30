INSERT INTO users (id, name, email, password, password_confirmation) VALUES (1, 'user', 'Adefemi@yahoo.com' ,'$2a$10$dT01cCoiRaBQQv80mXPPJeGGru6Bylqqm2PKNxH8iZ6gcRbzMw3J.', '$2a$10$dT01cCoiRaBQQv80mXPPJeGGru6Bylqqm2PKNxH8iZ6gcRbzMw3J.');
INSERT INTO users (id, name, email, password,  password_confirmation) VALUES (2, 'admin', 'ImperiousEnterprise@hotmail.com','$2a$10$dT01cCoiRaBQQv80mXPPJeGGru6Bylqqm2PKNxH8iZ6gcRbzMw3J.', '$2a$10$dT01cCoiRaBQQv80mXPPJeGGru6Bylqqm2PKNxH8iZ6gcRbzMw3J.');

INSERT INTO authority (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO authority (id, name) VALUES (2, 'ROLE_ADMIN');

INSERT INTO users_authority (users_id, authority_id) VALUES (1, 1);
INSERT INTO users_authority (users_id, authority_id) VALUES (2, 1);
INSERT INTO users_authority (users_id, authority_id) VALUES (2, 2);
