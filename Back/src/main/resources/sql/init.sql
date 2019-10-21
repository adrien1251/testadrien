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
values (3, 'Un jolie téléphone', 'Galaxy S10 noir', 'https://samsung.com/galaxyS10', 10)
ON CONFLICT DO NOTHING;

insert into public.product
(id, description, name, supplier_link, category_id)
values (4, 'Un iphone classique', 'Iphone XX', 'https://apple.com/IphoneXX', 10)
ON CONFLICT DO NOTHING;

insert into public.criteria_product
(criteria_id, product_id, value)
values (1, 3, '700')
ON CONFLICT DO NOTHING;

insert into public.criteria_product
(criteria_id, product_id, value)
values (1, 4, '1200')
ON CONFLICT DO NOTHING;

insert into public.compare_hist (customer_id, product_id)
values (1,3)
ON CONFLICT DO NOTHING;

insert into public.compare_hist (customer_id, product_id)
values (1,4)
ON CONFLICT DO NOTHING;

insert into public.image (id, url, product_id)
values (1, 'https://images.samsung.com/is/image/samsung/p5/global/mkt/galaxy-s10/specs/s10-plus/galaxy-s10-plus_specs_design_colors_prism_black.jpg', 3)
ON CONFLICT DO NOTHING;

insert into public.image (id, url, product_id)
values (2, 'https://store.storeimages.cdn-apple.com/4668/as-images.apple.com/is/iphone-11-pro-select-2019-family_GEO_EMEA?wid=882&amp;hei=1058&amp;fmt=jpeg&amp;qlt=80&amp;op_usm=0.5,0.5&amp;.v=1567812929188', 4)
ON CONFLICT DO NOTHING;

insert into public.favori (id, recherche, user_id)
values (1, 'recherche1', 1)
ON CONFLICT DO NOTHING;

/*
insert into public.favori (id, recherche, user_id)
values (2, 'recherche2', 1)
ON CONFLICT DO NOTHING;
*/
