INSERT INTO public.users(
  id, discriminator, email, first_name, last_name, password,
  reset_token, siret, web_site, birthday, phone_num, sexe)
VALUES(1, 'CUSTOMER', 'client@comparetout.fr', 'Adrien', 'Deblock', '$2a$12$gPhTV2TjgEreC0L8sjZWqe.dg48cPXdiHTt95GgKei9fcBBO7ZTx2', NULL,
 NULL, NULL, '01/01/1959', '1234567890', 'MAN')
ON CONFLICT DO NOTHING;

INSERT INTO public.users(
  id, discriminator, email, first_name, last_name, password,
  reset_token, siret, web_site, birthday, phone_num, sexe)
VALUES(2, 'SUPPLIER', 'fournisseur@comparetout.fr', 'Lukas', 'Ramus', '$2a$12$gPhTV2TjgEreC0L8sjZWqe.dg48cPXdiHTt95GgKei9fcBBO7ZTx2', NULL,
 'SIRET', 'http://website.fr', NULL, '123456788835345', 'MAN' )
ON CONFLICT DO NOTHING;

INSERT INTO public.users
(id, discriminator, email, first_name, last_name, password, reset_token, phone_num)
VALUES(3, 'ADMIN', 'admin@comparetout.fr', 'Pierre', 'Paul', '$2a$12$gPhTV2TjgEreC0L8sjZWqe.dg48cPXdiHTt95GgKei9fcBBO7ZTx2', NULL, '1234567844' )
ON CONFLICT DO NOTHING;

insert into public.criteria
(criteria_id, is_mandatory, name, type, unit)
values (1, true, 'price', 0, 'euros')
ON CONFLICT DO NOTHING;

insert into public.criteria
(criteria_id, is_mandatory, name, type, unit)
values (2, false, 'colors', 1, NULL)
ON CONFLICT DO NOTHING;

insert into public.category
(id, name, parent_id)
values (10, 'Téléphonie', null)
ON CONFLICT DO NOTHING;

insert into public.product
(product_id, description, name, supplier_link, category_id)
values (1, 'Un jolie téléphone', 'Galaxy S10 noir', 'https://samsung.com/galaxyS10', 10)
ON CONFLICT DO NOTHING;

insert into public.product
(product_id, description, name, supplier_link, category_id)
values (2, 'Un iphone classique', 'Iphone XX', 'https://apple.com/IphoneXX', 10)
ON CONFLICT DO NOTHING;

insert into public.criteria_product(product_id, criteria_id, value)
values (1, 1, '700') ON CONFLICT DO NOTHING;

insert into public.criteria_product(product_id, criteria_id, value)
values (1, 2, 'ROUGE') ON CONFLICT DO NOTHING;

insert into public.criteria_category(fk_criteria, fk_category)
values (1,10) ON CONFLICT DO NOTHING;
insert into public.criteria_category(fk_criteria, fk_category)
values (2,10) ON CONFLICT DO NOTHING;

insert into public.category_product_list(category_id, product_list_product_id)
values (10,1) ON CONFLICT DO NOTHING;
insert into public.category_product_list(category_id, product_list_product_id)
values (10,2) ON CONFLICT DO NOTHING;