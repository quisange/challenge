--
-- PostgreSQL database dump
-- Customers
--

--
-- TOC entry 2875 (class 0 OID 16702)
-- Dependencies: 197
-- Data for Name: customer; Type: TABLE DATA; Schema: public; Owner: usr_develcorp
--

INSERT INTO public.customer VALUES (1, 'Otavalo sn y principal ', 43, 'Masculino', '0194827364', 'Jose Lema', '098254785', '1234', true);
INSERT INTO public.customer VALUES (2, 'Amazonas y NNUU', 32, 'Femenino', '0194837267', 'Marianela Montalvo', '097548965', '5678', true);
INSERT INTO public.customer VALUES (3, '13 junio y Equinoccial', 35, 'Masculino', '0194857398', 'Juan Osorio ', '098874587', '1245', true);


--
-- TOC entry 2884 (class 0 OID 0)
-- Dependencies: 196
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: usr_develcorp
--

SELECT pg_catalog.setval('public.hibernate_sequence', 4, true);


--
-- PostgreSQL database dump
-- Accounts
--
--
-- TOC entry 2879 (class 0 OID 16666)
-- Dependencies: 196
-- Data for Name: account_type; Type: TABLE DATA; Schema: public; Owner: usr_develcorp
--

INSERT INTO public.account_type VALUES (1, 'Ahorros');
INSERT INTO public.account_type VALUES (2, 'Corriente');

--
-- TOC entry 2881 (class 0 OID 16719)
-- Dependencies: 198
-- Data for Name: account; Type: TABLE DATA; Schema: public; Owner: usr_develcorp
--

INSERT INTO public.account VALUES (478758, 1, 2000.00, true, 1);
INSERT INTO public.account VALUES (225487, 2, 100.00, true, 2);
INSERT INTO public.account VALUES (495878, 3, 1.00, true, 1);
INSERT INTO public.account VALUES (496825, 2, 540.00, true, 1);
INSERT INTO public.account VALUES (585545, 1, 1000.00, true, 2);

--
-- TOC entry 2890 (class 0 OID 0)
-- Dependencies: 197
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: usr_develcorp
--

SELECT pg_catalog.setval('public.hibernate_sequence', 3, true);

--
-- PostgreSQL database dump
-- Transactions
--

--
-- TOC entry 2879 (class 0 OID 16705)
-- Dependencies: 197
-- Data for Name: balance; Type: TABLE DATA; Schema: public; Owner: usr_develcorp
--

INSERT INTO public.balance VALUES (1, 478758, 1425.00, '2022-05-08 17:11:45.693');
INSERT INTO public.balance VALUES (2, 225487, 700.00, '2022-05-08 17:11:02.782');
INSERT INTO public.balance VALUES (3, 495878, 151.00, '2022-05-08 17:12:05.955');
INSERT INTO public.balance VALUES (4, 496825, 0.00, '2022-05-08 17:12:16.257');
INSERT INTO public.balance VALUES (5, 585545, 1000.00, '2022-05-08 17:14:57.454');

--
-- TOC entry 2880 (class 0 OID 16712)
-- Dependencies: 198
-- Data for Name: transaction; Type: TABLE DATA; Schema: public; Owner: usr_develcorp
--

INSERT INTO public.transaction VALUES (1, 478758, 2000.00, '2022-05-08', 'Apertura de 2000', true, 'Apertura', 2000.00);
INSERT INTO public.transaction VALUES (2, 225487, 100.00, '2022-05-08', 'Apertura de 100', true, 'Apertura', 100.00);
INSERT INTO public.transaction VALUES (3, 495878, 1.00, '2022-05-08', 'Apertura de 1', true, 'Apertura', 1.00);
INSERT INTO public.transaction VALUES (4, 496825, 540.00, '2022-05-08', 'Apertura de 540', true, 'Apertura', 540.00);
INSERT INTO public.transaction VALUES (5, 585545, 1000.00, '2022-05-08', 'Apertura de 1000', true, 'Apertura', 1000.00);
INSERT INTO public.transaction VALUES (6, 478758, 1425.00, '2022-05-08', 'Retiro de 575', true, 'Retiro', 575.00);
INSERT INTO public.transaction VALUES (7, 225487, 700.00, '2022-05-08', 'Deposito de 600', true, 'Deposito', 600.00);
INSERT INTO public.transaction VALUES (8, 495878, 151.00, '2022-05-08', 'Deposito de 150', true, 'Deposito', 150.00);
INSERT INTO public.transaction VALUES (9, 496825, 0.00, '2022-05-08', 'Retiro de 540', true, 'Retiro', 540.00);

--
-- TOC entry 2889 (class 0 OID 0)
-- Dependencies: 196
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: usr_develcorp
--

SELECT pg_catalog.setval('public.hibernate_sequence', 10, true);
