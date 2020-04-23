/* POPULATE COMPANY_TYPES */
INSERT INTO company_types (id, code, description) VALUES (1, 'S.L.', 'Societat Limitada');
INSERT INTO company_types (id, code, description) VALUES (2, 'S.A.', 'Societat Anònima');
INSERT INTO company_types (id, code, description) VALUES (3, 'S.C.P.', 'Societat Civil Privada');
/* POPULATE COMPANY*/
INSERT INTO companies (id, company_name, company_type_fk, create_at, email, phone, logo) VALUES (1, 'Empresa1', 1, NOW() , 'mail@empresa1.com', '931234567', '');
INSERT INTO companies (id, company_name, company_type_fk, create_at, email, phone, logo) VALUES (2, 'Empresa2', 2, NOW() , 'info@empresa2.com', '939876543', '');
/* POPULATE INFORMATION */
INSERT INTO informations (id, description, create_at) VALUES (1, 'Duració contracte', NOW());
INSERT INTO informations (id, description, create_at) VALUES (2, 'Irpf anual', NOW());
INSERT INTO informations (id, description, create_at) VALUES (3, 'dades treballador', NOW());
/* POPULATE EMPLOYEE */
INSERT INTO employees (id, employee_name, nif, naf, email, phone, create_at, company_fk) VALUES (1, 'Primer treballador', '234455', '24245', 'olgapc@gmail.com', '83563563', NOW(), 1);
INSERT INTO employees (id, employee_name, nif, naf, email, phone, create_at, company_fk) VALUES (2, 'Segon treballador', '23665', '24665', 'olgapc2@gmail.com', '677773', NOW(), 1);
/* POPULATE USERS */
INSERT INTO `users` (username, password, enabled, create_at) VALUES ('olga','$2a$10$O9wxmH/AeyZZzIS09Wp8YOEMvFnbRVJ8B4dmAMVSGloR62lj.yqXG', 1, NOW());
INSERT INTO `users` (username, password, enabled, create_at) VALUES ('admin','$2a$10$DOMDxjYyfZ/e7RcBfUpzqeaCs8pLgcizuiQWXPkU35nOhZlFcE9MS', 1, NOW());
/* POPULATE ROLES */
INSERT INTO `roles` (description, role, create_at) VALUES ('Usuari','ROLE_USER', NOW());
INSERT INTO `roles` (description, role, create_at) VALUES ('Administrador','ROLE_ADMIN', NOW());
/* POPULATE USERS ROLES */
INSERT INTO `users_roles` (user_fk, role_fk) VALUES (1,1);
INSERT INTO `users_roles` (user_fk, role_fk) VALUES (1,2);
INSERT INTO `users_roles` (user_fk, role_fk) VALUES (2,2);