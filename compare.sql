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
-- Name: files; Type: TABLE; Schema: public; Owner: songbw; Tablespace: 
--

CREATE TABLE files (
    id integer NOT NULL,
    fid numeric(64,0) NOT NULL,
    name character varying(255),
    size integer DEFAULT 0,
    path character varying(255),
    creator character varying(255),
    category character varying(255),
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.files OWNER TO songbw;

--
-- Name: files_id_seq; Type: SEQUENCE; Schema: public; Owner: songbw
--

CREATE SEQUENCE files_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.files_id_seq OWNER TO songbw;

--
-- Name: files_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: songbw
--

ALTER SEQUENCE files_id_seq OWNED BY files.id;


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
    CONSTRAINT username CHECK ((length((username)::text) > 2))
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

ALTER TABLE ONLY compares ALTER COLUMN id SET DEFAULT nextval('compares_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: songbw
--

ALTER TABLE ONLY departments ALTER COLUMN id SET DEFAULT nextval('departments_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: songbw
--

ALTER TABLE ONLY files ALTER COLUMN id SET DEFAULT nextval('files_id_seq'::regclass);


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

ALTER TABLE ONLY user_department ALTER COLUMN id SET DEFAULT nextval('user_department_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: songbw
--

ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);


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
\.


--
-- Name: departments_id_seq; Type: SEQUENCE SET; Schema: public; Owner: songbw
--

SELECT pg_catalog.setval('departments_id_seq', 1, false);


--
-- Data for Name: files; Type: TABLE DATA; Schema: public; Owner: songbw
--

COPY files (id, fid, name, size, path, creator, category, created_at, updated_at) FROM stdin;
\.


--
-- Name: files_id_seq; Type: SEQUENCE SET; Schema: public; Owner: songbw
--

SELECT pg_catalog.setval('files_id_seq', 1, false);


--
-- Data for Name: lobos_migrations; Type: TABLE DATA; Schema: public; Owner: songbw
--

COPY lobos_migrations (name) FROM stdin;
create-users-table
create-profile-table
create-departments-table
create-user_department-table
create-red_list-table
create-compares-table
create-files-table
\.


--
-- Data for Name: profile; Type: TABLE DATA; Schema: public; Owner: songbw
--

COPY profile (gender, cardid, pid, name, updated_at, user_id, username, created_at, nickname, email, mobile, id, tel, birthday, company) FROM stdin;
\N	\N	110821485665507694425593287660307218433	admin	2014-04-29 17:30:00.327745	110821485665507694425593287660307218432	admin	2014-04-29 17:30:00.327745	\N	\N	\N	1	13811111111	\N	admin
\.


--
-- Name: profile_id_seq; Type: SEQUENCE SET; Schema: public; Owner: songbw
--

SELECT pg_catalog.setval('profile_id_seq', 1, true);


--
-- Data for Name: red_list; Type: TABLE DATA; Schema: public; Owner: songbw
--

COPY red_list (id, rid, car_no, mark_type, regist_date, frame_no, tag_color, created_at, updated_at) FROM stdin;
\.


--
-- Name: red_list_id_seq; Type: SEQUENCE SET; Schema: public; Owner: songbw
--

SELECT pg_catalog.setval('red_list_id_seq', 1, false);


--
-- Data for Name: user_department; Type: TABLE DATA; Schema: public; Owner: songbw
--

COPY user_department (id, ud_id, department_id, username, created_at, updated_at) FROM stdin;
\.


--
-- Name: user_department_id_seq; Type: SEQUENCE SET; Schema: public; Owner: songbw
--

SELECT pg_catalog.setval('user_department_id_seq', 1, false);


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: songbw
--

COPY users (status, refresh_token, expires_in, is_admin, access_token, updated_at, logout_at, username, created_at, uid, login_at, id, password) FROM stdin;
t	\N	30000	t	\N	2014-04-29 17:30:00.327745	\N	admin	2014-04-29 17:30:00.327745	110821485665507694425593287660307218432	\N	1	21232f297a57a5a743894a0e4a801fc3
\.


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: songbw
--

SELECT pg_catalog.setval('users_id_seq', 1, true);


--
-- Name: departments_primary_key_did; Type: CONSTRAINT; Schema: public; Owner: songbw; Tablespace: 
--

ALTER TABLE ONLY departments
    ADD CONSTRAINT departments_primary_key_did PRIMARY KEY (did);


--
-- Name: files_primary_key_fid; Type: CONSTRAINT; Schema: public; Owner: songbw; Tablespace: 
--

ALTER TABLE ONLY files
    ADD CONSTRAINT files_primary_key_fid PRIMARY KEY (fid);


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
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

