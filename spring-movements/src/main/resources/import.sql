/* POPULATE COMPANY_TYPES */
INSERT INTO company_types (id, code, description) VALUES (1, 'S.L.', 'Societat Limitada');
INSERT INTO company_types (id, code, description) VALUES (2, 'S.A.', 'Societat Anònima');
INSERT INTO company_types (id, code, description) VALUES (3, 'S.C.P.', 'Societat Civil Privada');
/* POPULATE COMPANY*/
INSERT INTO companies (id, company_name, company_type_fk, create_at, email, phone, logo) VALUES (1, 'Empresa1', 1, '2020-02-05' , 'mail@empresa1.com', '931234567', '');
INSERT INTO companies (id, company_name, company_type_fk, create_at, email, phone, logo) VALUES (2, 'Empresa2', 2, '2020-02-05' , 'info@empresa2.com', '939876543', '');
/* POPULATE INFORMATION */
INSERT INTO informations (id, description, create_at) VALUES (1, 'Duració contracte', '2020-02-05');
INSERT INTO informations (id, description, create_at) VALUES (2, 'Irpf anual', '2020-02-05');
INSERT INTO informations (id, description, create_at) VALUES (3, 'dades treballador', '2020-02-05');
/* POPULATE EMPLOYEE */
INSERT INTO employees (id, employee_name, nif, naf, email, phone, create_at, company_fk) VALUES (1, 'Primer treballador', '234455', '24245', 'olgapc@gmail.com', '83563563', NOW(), 1);
INSERT INTO employees (id, employee_name, nif, naf, email, phone, create_at, company_fk) VALUES (2, 'Segon treballador', '23665', '24665', 'olgapc2@gmail.com', '677773', NOW(), 1);