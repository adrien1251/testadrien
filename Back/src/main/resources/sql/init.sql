INSERT INTO public.users
(id, email, first_name, last_name, password, reset_token, role)
VALUES(1, 'client@comparetout.fr', 'Adrien', 'Deblock', '$2a$12$gPhTV2TjgEreC0L8sjZWqe.dg48cPXdiHTt95GgKei9fcBBO7ZTx2', NULL, 'CUSTOMER') ON CONFLICT DO NOTHING;
INSERT INTO public.users
(id, email, first_name, last_name, password, reset_token, role)
VALUES(2, 'fournisseur@comparetout.fr', 'Lukas', 'Ramus', '$2a$12$gPhTV2TjgEreC0L8sjZWqe.dg48cPXdiHTt95GgKei9fcBBO7ZTx2', NULL,'SUPPLIER' ) ON CONFLICT DO NOTHING;
INSERT INTO public.customer
(id,birthday, phone_num, sexe, user_id)
VALUES(1,'01/01/1959', '1234567890', 'MAN', 1 ) ON CONFLICT DO NOTHING;
INSERT INTO public.supplier
(id,siret, web_site, user_id)
VALUES(1,'123456788835345', 'https://google.com', 2 ) ON CONFLICT DO NOTHING;