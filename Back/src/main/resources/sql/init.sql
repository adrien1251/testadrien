INSERT INTO public.users
(id,dtype, email, first_name, last_name, password, reset_token, role, birthday, phone_num, sexe)
VALUES(1, 'CUSTOMER', 'client@comparetout.fr', 'Adrien', 'Deblock', '$2a$12$gPhTV2TjgEreC0L8sjZWqe.dg48cPXdiHTt95GgKei9fcBBO7ZTx2', NULL, 'CUSTOMER','01/01/1959', '1234567890', 'MAN')
ON CONFLICT DO NOTHING;

INSERT INTO public.users
(id, dtype, email, first_name, last_name, password, reset_token, role, siret,  web_site)
VALUES(2, 'SUPPLIER', 'fournisseur@comparetout.fr', 'Lukas', 'Ramus', '$2a$12$gPhTV2TjgEreC0L8sjZWqe.dg48cPXdiHTt95GgKei9fcBBO7ZTx2', NULL,'SUPPLIER', '123456788835345', 'https://google.com' )
ON CONFLICT DO NOTHING;

INSERT INTO public.users
(id, dtype, email, first_name, last_name, password, reset_token, role, phone_num)
VALUES(3, 'ADMIN', 'admin@comparetout.fr', 'Pierre', 'Paul', '$2a$12$gPhTV2TjgEreC0L8sjZWqe.dg48cPXdiHTt95GgKei9fcBBO7ZTx2', NULL,'ADMIN', '1234567844' )
ON CONFLICT DO NOTHING;

insert into public.criteria
(id, is_mandatory, name, type, unit)
values (1, true, 'price', 0, 'euros')
ON CONFLICT DO NOTHING;

insert into public.category
(id, name, parent_id)
values (10, 'Téléphonie', null)
ON CONFLICT DO NOTHING;

insert into public.product
(id, description, name, supplier_link, category_id)
values (3, 'Un jolie téléphone', 'Galaxy S0 noir', 'https://samsung.com/galaxyS10', 10)
ON CONFLICT DO NOTHING;

insert into public.criteria_product
(criteria_id, product_id, value)
values (1, 3, '700')
ON CONFLICT DO NOTHING;


