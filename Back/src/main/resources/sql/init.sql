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

