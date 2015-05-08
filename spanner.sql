--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: cars; Type: TABLE; Schema: public; Owner: songbw; Tablespace: 
--

CREATE TABLE cars (
    issued_date timestamp without time zone,
    updated_at timestamp without time zone,
    car_id character varying(255),
    cid numeric(64,0) NOT NULL,
    mark_type character varying(255),
    label_no character varying(255),
    period_date timestamp without time zone,
    created_at timestamp without time zone,
    regist_date timestamp without time zone,
    fuel_type character varying(255),
    emission_norm character varying(255),
    id integer NOT NULL,
    tag_color character varying(255)
);


ALTER TABLE public.cars OWNER TO songbw;

--
-- Name: cars_id_seq; Type: SEQUENCE; Schema: public; Owner: songbw
--

CREATE SEQUENCE cars_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cars_id_seq OWNER TO songbw;

--
-- Name: cars_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: songbw
--

ALTER SEQUENCE cars_id_seq OWNED BY cars.id;


--
-- Name: compare; Type: TABLE; Schema: public; Owner: songbw; Tablespace: 
--

CREATE TABLE compare (
    violation character varying(255),
    illegal_code character varying(255),
    illegal_address character varying(255),
    speed_limit integer DEFAULT 0,
    record_time timestamp without time zone,
    car_brand character varying(255),
    ultra_rate double precision DEFAULT 0.0,
    fines integer DEFAULT 0,
    car_type character varying(255),
    car_no character varying(255),
    name character varying(255),
    updated_at timestamp without time zone DEFAULT now(),
    travel_speed integer DEFAULT 0,
    capture_no character varying(255),
    cid numeric(64,0) NOT NULL,
    mark_type character varying(255),
    serial_no character varying(255),
    created_at timestamp without time zone DEFAULT now(),
    phone character varying(255),
    record_no character varying(255),
    illegal_time timestamp without time zone,
    address character varying(255),
    id integer NOT NULL
);


ALTER TABLE public.compare OWNER TO songbw;

--
-- Name: compare_id_seq; Type: SEQUENCE; Schema: public; Owner: songbw
--

CREATE SEQUENCE compare_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.compare_id_seq OWNER TO songbw;

--
-- Name: compare_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: songbw
--

ALTER SEQUENCE compare_id_seq OWNED BY compare.id;


--
-- Name: compares; Type: TABLE; Schema: public; Owner: songbw; Tablespace: 
--

CREATE TABLE compares (
    violation character varying(255),
    illegal_code character varying(255),
    illegal_address character varying(255),
    speed_limit character varying(255),
    record_time character varying(255),
    car_brand character varying(255),
    ultra_rate character varying(255),
    fines character varying(255),
    car_type character varying(255),
    car_no character varying(255),
    name character varying(255),
    updated_at timestamp without time zone DEFAULT now(),
    travel_speed character varying(255),
    capture_no character varying(255),
    mark_type character varying(255),
    serial_no character varying(255),
    created_at timestamp without time zone DEFAULT now(),
    phone character varying(255),
    record_no character varying(255),
    department integer,
    illegal_time character varying(255),
    address character varying(255),
    id integer NOT NULL
);


ALTER TABLE public.compares OWNER TO songbw;

--
-- Name: compares_id_seq; Type: SEQUENCE; Schema: public; Owner: songbw
--

CREATE SEQUENCE compares_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.compares_id_seq OWNER TO songbw;

--
-- Name: compares_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: songbw
--

ALTER SEQUENCE compares_id_seq OWNED BY compares.id;


--
-- Name: departments; Type: TABLE; Schema: public; Owner: songbw; Tablespace: 
--

CREATE TABLE departments (
    did numeric(64,0) NOT NULL,
    father_id numeric(64,0),
    name character varying(255),
    updated_at timestamp without time zone DEFAULT now(),
    created_at timestamp without time zone DEFAULT now(),
    keyword character varying(255),
    phone character varying(255),
    email character varying(255),
    address character varying(255),
    id integer NOT NULL,
    description character varying(255)
);


ALTER TABLE public.departments OWNER TO songbw;

--
-- Name: departments_id_seq; Type: SEQUENCE; Schema: public; Owner: songbw
--

CREATE SEQUENCE departments_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.departments_id_seq OWNER TO songbw;

--
-- Name: departments_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: songbw
--

ALTER SEQUENCE departments_id_seq OWNED BY departments.id;


--
-- Name: images; Type: TABLE; Schema: public; Owner: songbw; Tablespace: 
--

CREATE TABLE images (
    path character varying(255),
    creator character varying(255),
    iid numeric(64,0) NOT NULL,
    name character varying(255),
    updated_at timestamp without time zone,
    size integer DEFAULT 0,
    created_at timestamp without time zone,
    type character varying(255),
    id integer NOT NULL,
    description character varying(255),
    md5 character varying(255),
    category character varying(255)
);


ALTER TABLE public.images OWNER TO songbw;

--
-- Name: images_id_seq; Type: SEQUENCE; Schema: public; Owner: songbw
--

CREATE SEQUENCE images_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.images_id_seq OWNER TO songbw;

--
-- Name: images_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: songbw
--

ALTER SEQUENCE images_id_seq OWNED BY images.id;


--
-- Name: lobos_migrations; Type: TABLE; Schema: public; Owner: songbw; Tablespace: 
--

CREATE TABLE lobos_migrations (
    name character varying(255)
);


ALTER TABLE public.lobos_migrations OWNER TO songbw;

--
-- Name: profile; Type: TABLE; Schema: public; Owner: songbw; Tablespace: 
--

CREATE TABLE profile (
    gender character(1),
    cardid character varying(255),
    pid numeric(64,0) NOT NULL,
    name character varying(255),
    updated_at timestamp without time zone DEFAULT now(),
    user_id numeric(64,0) NOT NULL,
    username character varying(255),
    created_at timestamp without time zone DEFAULT now(),
    nickname character varying(255),
    email character varying(255),
    mobile character varying(32),
    id integer NOT NULL,
    tel character varying(32),
    birthday timestamp without time zone,
    company character varying(255)
);


ALTER TABLE public.profile OWNER TO songbw;

--
-- Name: profile_id_seq; Type: SEQUENCE; Schema: public; Owner: songbw
--

CREATE SEQUENCE profile_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.profile_id_seq OWNER TO songbw;

--
-- Name: profile_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: songbw
--

ALTER SEQUENCE profile_id_seq OWNED BY profile.id;


--
-- Name: red_list; Type: TABLE; Schema: public; Owner: songbw; Tablespace: 
--

CREATE TABLE red_list (
    id integer NOT NULL,
    rid numeric(64,0) NOT NULL,
    car_no character varying(255),
    mark_type character varying(255),
    regist_date timestamp without time zone,
    frame_no character varying(255),
    tag_color character varying(255),
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.red_list OWNER TO songbw;

--
-- Name: red_list_id_seq; Type: SEQUENCE; Schema: public; Owner: songbw
--

CREATE SEQUENCE red_list_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.red_list_id_seq OWNER TO songbw;

--
-- Name: red_list_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: songbw
--

ALTER SEQUENCE red_list_id_seq OWNED BY red_list.id;


--
-- Name: ukeys; Type: TABLE; Schema: public; Owner: songbw; Tablespace: 
--

CREATE TABLE ukeys (
    id integer NOT NULL,
    kid numeric(64,0) NOT NULL,
    user_id numeric(64,0) NOT NULL,
    username character varying(255),
    serialno character varying(255),
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.ukeys OWNER TO songbw;

--
-- Name: ukeys_id_seq; Type: SEQUENCE; Schema: public; Owner: songbw
--

CREATE SEQUENCE ukeys_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.ukeys_id_seq OWNER TO songbw;

--
-- Name: ukeys_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: songbw
--

ALTER SEQUENCE ukeys_id_seq OWNED BY ukeys.id;


--
-- Name: user_department; Type: TABLE; Schema: public; Owner: songbw; Tablespace: 
--

CREATE TABLE user_department (
    id integer NOT NULL,
    ud_id numeric(64,0) NOT NULL,
    department_id integer,
    username character varying(255),
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.user_department OWNER TO songbw;

--
-- Name: user_department_id_seq; Type: SEQUENCE; Schema: public; Owner: songbw
--

CREATE SEQUENCE user_department_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_department_id_seq OWNER TO songbw;

--
-- Name: user_department_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: songbw
--

ALTER SEQUENCE user_department_id_seq OWNED BY user_department.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: songbw; Tablespace: 
--

CREATE TABLE users (
    status boolean DEFAULT true,
    refresh_token character varying(128),
    expires_in bigint DEFAULT 30000,
    is_admin boolean DEFAULT false,
    access_token character varying(128),
    updated_at timestamp without time zone DEFAULT now(),
    logout_at timestamp without time zone,
    username character varying(255),
    created_at timestamp without time zone DEFAULT now(),
    uid numeric(64,0) NOT NULL,
    login_at timestamp without time zone,
    id integer NOT NULL,
    password character varying(128) NOT NULL,
    CONSTRAINT username CHECK ((length((username)::text) > 4))
);


ALTER TABLE public.users OWNER TO songbw;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: songbw
--

CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO songbw;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: songbw
--

ALTER SEQUENCE users_id_seq OWNED BY users.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: songbw
--

ALTER TABLE ONLY cars ALTER COLUMN id SET DEFAULT nextval('cars_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: songbw
--

ALTER TABLE ONLY compare ALTER COLUMN id SET DEFAULT nextval('compare_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: songbw
--

ALTER TABLE ONLY compares ALTER COLUMN id SET DEFAULT nextval('compares_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: songbw
--

ALTER TABLE ONLY departments ALTER COLUMN id SET DEFAULT nextval('departments_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: songbw
--

ALTER TABLE ONLY images ALTER COLUMN id SET DEFAULT nextval('images_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: songbw
--

ALTER TABLE ONLY profile ALTER COLUMN id SET DEFAULT nextval('profile_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: songbw
--

ALTER TABLE ONLY red_list ALTER COLUMN id SET DEFAULT nextval('red_list_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: songbw
--

ALTER TABLE ONLY ukeys ALTER COLUMN id SET DEFAULT nextval('ukeys_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: songbw
--

ALTER TABLE ONLY user_department ALTER COLUMN id SET DEFAULT nextval('user_department_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: songbw
--

ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);


--
-- Data for Name: cars; Type: TABLE DATA; Schema: public; Owner: songbw
--

COPY cars (issued_date, updated_at, car_id, cid, mark_type, label_no, period_date, created_at, regist_date, fuel_type, emission_norm, id, tag_color) FROM stdin;
\.


--
-- Name: cars_id_seq; Type: SEQUENCE SET; Schema: public; Owner: songbw
--

SELECT pg_catalog.setval('cars_id_seq', 1, false);


--
-- Data for Name: compare; Type: TABLE DATA; Schema: public; Owner: songbw
--

COPY compare (violation, illegal_code, illegal_address, speed_limit, record_time, car_brand, ultra_rate, fines, car_type, car_no, name, updated_at, travel_speed, capture_no, cid, mark_type, serial_no, created_at, phone, record_no, illegal_time, address, id) FROM stdin;
\.


--
-- Name: compare_id_seq; Type: SEQUENCE SET; Schema: public; Owner: songbw
--

SELECT pg_catalog.setval('compare_id_seq', 1, false);


--
-- Data for Name: compares; Type: TABLE DATA; Schema: public; Owner: songbw
--

COPY compares (violation, illegal_code, illegal_address, speed_limit, record_time, car_brand, ultra_rate, fines, car_type, car_no, name, updated_at, travel_speed, capture_no, mark_type, serial_no, created_at, phone, record_no, department, illegal_time, address, id) FROM stdin;
\.


--
-- Name: compares_id_seq; Type: SEQUENCE SET; Schema: public; Owner: songbw
--

SELECT pg_catalog.setval('compares_id_seq', 1, false);


--
-- Data for Name: departments; Type: TABLE DATA; Schema: public; Owner: songbw
--

COPY departments (did, father_id, name, updated_at, created_at, keyword, phone, email, address, id, description) FROM stdin;
110773461277115185554567950396022587392	0	\N	2014-04-22 17:07:27.841892	2014-04-22 17:07:27.841892	\N	\N	aeja@qq.com	\N	1	\N
110773466585402074010278569163467259904	0	\N	2014-04-22 17:08:34.238654	2014-04-22 17:08:34.238654	\N	\N	aeja@qq.com	\N	2	\N
110773468407649811838358333814978117632	0	\N	2014-04-22 17:08:57.335089	2014-04-22 17:08:57.335089	\N	\N	aeja@qq.com	\N	3	\N
110773551914133101872970157410301771776	0	\N	2014-04-22 17:26:36.960238	2014-04-22 17:26:36.960238	\N	\N	啊大大	\N	4	\N
110773686522781213608079728841473392640	0	\N	2014-04-22 17:54:50.51279	2014-04-22 17:54:50.51279	\N	\N	的擦地板哈德吧	\N	5	\N
110780011544679214872942829833319088128	0	songbingwei	2014-04-23 16:05:23.269005	2014-04-23 16:05:23.269005	\N	songbingwei	songbingwei	songbingwei	11	songbingwei
110778759105886189382294156613880119296	0	null	2014-04-23 11:41:55.761021	2014-04-23 11:41:55.761021	\N	13232323232	zhangj@qq.com	null	8	null
110784965523224906793443881161353854976	0	zhangjiang	2014-04-24 09:27:31.730367	2014-04-24 09:27:31.730367	\N	13543434343	zhangjiang@qq.com	zhangjiang	12	zhangjiang
110793553697585128019110342814177689600	0	zhangjiang	2014-04-25 15:34:09.362728	2014-04-25 15:34:09.362728	\N	13232323232	zhangqq@qq.com	zhang	38	zhang
110793555836745515904247457839864348672	0	zhangjiangw	2014-04-25 15:34:36.964151	2014-04-25 15:34:36.964151	\N	13232323232	zhangqq@qq.com	zhang	39	zhang
110794020193006012007530093609547202560	0	zhangjiang3	2014-04-25 17:12:17.560995	2014-04-25 17:12:17.560995	\N	13545454545	zhangjiang@qq.com	beijing	41	zhangjiang
110794031364176926518801694299244199936	0	huhu	2014-04-25 17:14:38.803402	2014-04-25 17:14:38.803402	\N	13434343434	dada@164.com	afhudhf	42	dadad
\.


--
-- Name: departments_id_seq; Type: SEQUENCE SET; Schema: public; Owner: songbw
--

SELECT pg_catalog.setval('departments_id_seq', 42, true);


--
-- Data for Name: images; Type: TABLE DATA; Schema: public; Owner: songbw
--

COPY images (path, creator, iid, name, updated_at, size, created_at, type, id, description, md5, category) FROM stdin;
\.


--
-- Name: images_id_seq; Type: SEQUENCE SET; Schema: public; Owner: songbw
--

SELECT pg_catalog.setval('images_id_seq', 1, false);


--
-- Data for Name: lobos_migrations; Type: TABLE DATA; Schema: public; Owner: songbw
--

COPY lobos_migrations (name) FROM stdin;
create-compare-table
create-users-table
create-profile-table
create-ukeys-table
create-images-table
create-cars-table
create-departments-table
create-red_list-table
create-user_department-table
create-compares-table
\.


--
-- Data for Name: profile; Type: TABLE DATA; Schema: public; Owner: songbw
--

COPY profile (gender, cardid, pid, name, updated_at, user_id, username, created_at, nickname, email, mobile, id, tel, birthday, company) FROM stdin;
\N	\N	110795053011332547957434963061368684545	huahua	2014-04-25 20:49:33.698107	110795053011332547957434963061368684544	huahua	2014-04-25 20:49:33.698107	\N	\N	\N	2	13232323232	\N	232323
\N	\N	110820459264662322131099759991305797633	病危	2014-04-29 13:54:05.192806	110820459264662322131099759991305797632	 小病危 小病危	2014-04-29 13:54:05.192806	\N	\N	\N	5	13232323232	\N	鬼门关
\N	\N	110723734117053920114399044992870907904	admin	2014-04-15 10:46:42.485886	110723734037825757600134707399326957568	admin	2014-04-15 10:46:42.485886	\N	\N	\N	1	13822222222	\N	company
\N	\N	110795360099690453246007475646310121473	ddad222	2014-04-25 21:54:09.964669	110795360099690453246007475646310121472	songbeiwei	2014-04-25 21:54:09.964669	\N	\N	\N	4	13212344321	\N	adadad
\N	\N	110820790279925306727502227991183753217	zhangjiang	2014-04-29 15:03:43.76184	110820790279925306727502227991183753216	zhangjiang	2014-04-29 15:03:43.76184	\N	\N	\N	7	13765648596	\N	beijing
\N	\N	110820800421130108553337439964809396225	liqingwei	2014-04-29 15:05:51.44947	110820800421130108553337439964809396224	liqingwei	2014-04-29 15:05:51.44947	\N	\N	\N	8	13434343434	\N	huahua
\.


--
-- Name: profile_id_seq; Type: SEQUENCE SET; Schema: public; Owner: songbw
--

SELECT pg_catalog.setval('profile_id_seq', 8, true);


--
-- Data for Name: red_list; Type: TABLE DATA; Schema: public; Owner: songbw
--

COPY red_list (id, rid, car_no, mark_type, regist_date, frame_no, tag_color, created_at, updated_at) FROM stdin;
1	110781171048837611131523512465723752448	呼呼呼	呼呼呼	\N	呼呼呼	呼呼呼	2014-04-23 20:09:19.138147	2014-04-23 20:09:19.138147
3	110781315957146849720996972135645708288	呼呼呼是的所得税	呼呼呼	\N	呼呼呼	呼呼呼	2014-04-23 20:39:47.597165	2014-04-23 20:39:47.597165
8	110785436772335541637723887560770453504	VVVVV	ASASAS	\N	SASASAS	SAASAS	2014-04-24 11:06:39.872816	2014-04-24 11:06:39.872816
10	110794131033205369463338386977533722624	333	333	\N	333	333	2014-04-25 17:35:36.735037	2014-04-25 17:35:36.735037
11	110794131746258832091717425319429275648	eweq	qeqe	\N	qeqe	qeqe	2014-04-25 17:35:45.971387	2014-04-25 17:35:45.971387
12	110794132300855969691567788474236928000	qeqeq	qeqe	\N	qeqe	qeqe	2014-04-25 17:35:52.886161	2014-04-25 17:35:52.886161
13	110794132934681269805682489222588530688	qeqeqe	qeqeqe	\N	qeqeqe	qeqeqe	2014-04-25 17:36:00.936352	2014-04-25 17:36:00.936352
14	110794133806191057462590202751571984384	qeqeqe	qeqeqe	\N	qeqee	qeqeqe	2014-04-25 17:36:11.650484	2014-04-25 17:36:11.650484
15	110794134677700845119497916280555438080	qeqeqe	qeee	\N	qeqeqe	qeee	2014-04-25 17:36:22.038294	2014-04-25 17:36:22.038294
16	110794135311526145233612617028907040768	qeqeqe	qeqeqe	\N	qeee	qeqeqe	2014-04-25 17:36:30.474689	2014-04-25 17:36:30.474689
17	110794135866123282833462980183714693120	qeqeqe	qeqeqe	\N	qeqeqe	qeqeqe	2014-04-25 17:36:37.992669	2014-04-25 17:36:37.992669
\.


--
-- Name: red_list_id_seq; Type: SEQUENCE SET; Schema: public; Owner: songbw
--

SELECT pg_catalog.setval('red_list_id_seq', 17, true);


--
-- Data for Name: ukeys; Type: TABLE DATA; Schema: public; Owner: songbw
--

COPY ukeys (id, kid, user_id, username, serialno, created_at, updated_at) FROM stdin;
\.


--
-- Name: ukeys_id_seq; Type: SEQUENCE SET; Schema: public; Owner: songbw
--

SELECT pg_catalog.setval('ukeys_id_seq', 1, false);


--
-- Data for Name: user_department; Type: TABLE DATA; Schema: public; Owner: songbw
--

COPY user_department (id, ud_id, department_id, username, created_at, updated_at) FROM stdin;
1	110820800421130108553337439964809396226	38	liqingwei	2014-04-29 15:05:51.503936	2014-04-29 15:05:51.503936
\.


--
-- Name: user_department_id_seq; Type: SEQUENCE SET; Schema: public; Owner: songbw
--

SELECT pg_catalog.setval('user_department_id_seq', 1, true);


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: songbw
--

COPY users (status, refresh_token, expires_in, is_admin, access_token, updated_at, logout_at, username, created_at, uid, login_at, id, password) FROM stdin;
t	\N	30000	f	\N	2014-04-29 13:54:05.192806	\N	 小病危 小病危	2014-04-29 13:54:05.192806	110820459264662322131099759991305797632	\N	6	d41d8cd98f00b204e9800998ecf8427e
t	bc10c66c32f62899fafa4ddcc18bda7f	1398769787651	t	bbe942f641442425e2ff830409f4722c	2014-04-15 10:46:42.485886	\N	admin	2014-04-15 10:46:42.485886	110723734037825757600134707399326957568	\N	1	e10adc3949ba59abbe56e057f20f883e
t	\N	30000	f	\N	2014-04-25 21:54:09.964669	\N	songbeiwei	2014-04-25 21:54:09.964669	110795360099690453246007475646310121472	\N	5	96e79218965eb72c92a549dd5a330112
t	\N	30000	t	\N	2014-04-29 15:03:43.76184	\N	zhangjiang	2014-04-29 15:03:43.76184	110820790279925306727502227991183753216	\N	8	96e79218965eb72c92a549dd5a330112
t	\N	30000	f	\N	2014-04-29 15:05:51.44947	\N	liqingwei	2014-04-29 15:05:51.44947	110820800421130108553337439964809396224	\N	9	e3ceb5881a0a1fdaad01296d7554868d
t	\N	30000	t	\N	2014-04-25 20:49:33.698107	\N	huahua	2014-04-25 20:49:33.698107	110795053011332547957434963061368684544	\N	2	0b4e7a0e5fe84ad35fb5f95b9ceeac79
\.


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: songbw
--

SELECT pg_catalog.setval('users_id_seq', 9, true);


--
-- Name: cars_primary_key_cid; Type: CONSTRAINT; Schema: public; Owner: songbw; Tablespace: 
--

ALTER TABLE ONLY cars
    ADD CONSTRAINT cars_primary_key_cid PRIMARY KEY (cid);


--
-- Name: compare_primary_key_cid; Type: CONSTRAINT; Schema: public; Owner: songbw; Tablespace: 
--

ALTER TABLE ONLY compare
    ADD CONSTRAINT compare_primary_key_cid PRIMARY KEY (cid);


--
-- Name: departments_primary_key_did; Type: CONSTRAINT; Schema: public; Owner: songbw; Tablespace: 
--

ALTER TABLE ONLY departments
    ADD CONSTRAINT departments_primary_key_did PRIMARY KEY (did);


--
-- Name: images_primary_key_iid; Type: CONSTRAINT; Schema: public; Owner: songbw; Tablespace: 
--

ALTER TABLE ONLY images
    ADD CONSTRAINT images_primary_key_iid PRIMARY KEY (iid);


--
-- Name: profile_primary_key_pid; Type: CONSTRAINT; Schema: public; Owner: songbw; Tablespace: 
--

ALTER TABLE ONLY profile
    ADD CONSTRAINT profile_primary_key_pid PRIMARY KEY (pid);


--
-- Name: red_list_primary_key_rid; Type: CONSTRAINT; Schema: public; Owner: songbw; Tablespace: 
--

ALTER TABLE ONLY red_list
    ADD CONSTRAINT red_list_primary_key_rid PRIMARY KEY (rid);


--
-- Name: ukeys_primary_key_kid; Type: CONSTRAINT; Schema: public; Owner: songbw; Tablespace: 
--

ALTER TABLE ONLY ukeys
    ADD CONSTRAINT ukeys_primary_key_kid PRIMARY KEY (kid);


--
-- Name: user_department_primary_key_ud_id; Type: CONSTRAINT; Schema: public; Owner: songbw; Tablespace: 
--

ALTER TABLE ONLY user_department
    ADD CONSTRAINT user_department_primary_key_ud_id PRIMARY KEY (ud_id);


--
-- Name: users_primary_key_uid; Type: CONSTRAINT; Schema: public; Owner: songbw; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_primary_key_uid PRIMARY KEY (uid);


--
-- Name: users_unique_username; Type: CONSTRAINT; Schema: public; Owner: songbw; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_unique_username UNIQUE (username);


--
-- Name: profile_fkey_user_id; Type: FK CONSTRAINT; Schema: public; Owner: songbw
--

ALTER TABLE ONLY profile
    ADD CONSTRAINT profile_fkey_user_id FOREIGN KEY (user_id) REFERENCES users(uid) ON DELETE CASCADE;


--
-- Name: ukeys_fkey_user_id; Type: FK CONSTRAINT; Schema: public; Owner: songbw
--

ALTER TABLE ONLY ukeys
    ADD CONSTRAINT ukeys_fkey_user_id FOREIGN KEY (user_id) REFERENCES users(uid) ON DELETE CASCADE;


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

