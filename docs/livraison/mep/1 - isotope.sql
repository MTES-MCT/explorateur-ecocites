CREATE TABLE asso_user_group (
    id_user bigint NOT NULL,
    id_group bigint NOT NULL
);

CREATE TABLE "group" (
    id bigint NOT NULL,
    name character varying(45),
    shortcut character varying(45) NOT NULL
);

CREATE TABLE is_asso_function_role (
    id bigint NOT NULL,
    id_function bigint NOT NULL,
    id_role bigint NOT NULL
);

CREATE TABLE is_asso_menu_menu_entry (
    id bigint NOT NULL,
    id_menu bigint NOT NULL,
    id_menu_entry bigint NOT NULL,
    ordre integer NOT NULL
);

CREATE TABLE is_asso_user_role (
    id_user bigint NOT NULL,
    id_role bigint NOT NULL
);

CREATE TABLE is_cm_value_list (
    id bigint NOT NULL,
    creation_date timestamp without time zone NOT NULL,
    creator_id bigint,
    creator_name character varying(255) NOT NULL,
    description character varying(255),
    modification_date timestamp without time zone NOT NULL,
    modifier_id bigint,
    modifier_name character varying(255) NOT NULL,
    name character varying(100) NOT NULL,
    shortcut character varying(50) NOT NULL
);

CREATE TABLE is_cm_value_list_entry (
    id bigint NOT NULL,
    code character varying(255) NOT NULL,
    disabled boolean NOT NULL,
    label character varying(255) NOT NULL,
    priority integer NOT NULL,
    value_list_id bigint
);

CREATE TABLE is_function (
    id bigint NOT NULL,
    code character varying(255) NOT NULL,
    date_creation timestamp without time zone NOT NULL,
    type character varying(20)
);

CREATE TABLE is_menu (
    id bigint NOT NULL,
    code character varying(50) NOT NULL
);

CREATE TABLE is_menu_entry (
    id bigint NOT NULL,
    code character varying(50),
    icon character varying(65535),
    id_function bigint NOT NULL,
    id_parent bigint,
    type character varying(20) NOT NULL,
    url character varying(65535)
);

CREATE TABLE is_role (
    id bigint NOT NULL,
    code character varying(50) NOT NULL,
    date_creation timestamp without time zone NOT NULL
);

CREATE TABLE is_url (
    id bigint NOT NULL,
    code_langue character varying(2),
    date_creation timestamp without time zone NOT NULL,
    id_function bigint NOT NULL,
    url character varying(255) NOT NULL
);

CREATE TABLE is_web_lang (
    id bigint NOT NULL,
    code_lang character varying(5) NOT NULL,
    creation_date timestamp without time zone NOT NULL,
    flag_actif_bo boolean NOT NULL,
    flag_actif_fo boolean,
    id_creator bigint NOT NULL,
    id_modifier bigint,
    langue_defaut_bo boolean,
    langue_defaut_fo boolean,
    modification_date timestamp without time zone NOT NULL,
    name character varying(100) NOT NULL
);

CREATE TABLE "user" (
    id bigint NOT NULL,
    company character varying(255),
    date_creation timestamp without time zone NOT NULL,
    date_modification timestamp without time zone,
    disabled boolean NOT NULL,
    email character varying(255) NOT NULL,
    firstname character varying(255) NOT NULL,
    id_user_crea bigint,
    id_user_mod bigint,
    lastname character varying(255) NOT NULL,
    login character varying(50) NOT NULL,
    password_hash character varying(100) NOT NULL,
    phone_number character varying(20)
);

ALTER TABLE ONLY asso_user_group
    ADD CONSTRAINT asso_user_group_pkey PRIMARY KEY (id_user, id_group);

ALTER TABLE ONLY "group"
    ADD CONSTRAINT group_pkey PRIMARY KEY (id);

ALTER TABLE ONLY is_asso_function_role
    ADD CONSTRAINT is_asso_function_role_pkey PRIMARY KEY (id);

ALTER TABLE ONLY is_asso_menu_menu_entry
    ADD CONSTRAINT is_asso_menu_menu_entry_pkey PRIMARY KEY (id);

ALTER TABLE ONLY is_asso_user_role
    ADD CONSTRAINT is_asso_user_role_pkey PRIMARY KEY (id_user, id_role);

ALTER TABLE ONLY is_cm_value_list_entry
    ADD CONSTRAINT is_cm_value_list_entry_pkey PRIMARY KEY (id);

ALTER TABLE ONLY is_cm_value_list
    ADD CONSTRAINT is_cm_value_list_pkey PRIMARY KEY (id);

ALTER TABLE ONLY is_function
    ADD CONSTRAINT is_function_pkey PRIMARY KEY (id);

ALTER TABLE ONLY is_menu_entry
    ADD CONSTRAINT is_menu_entry_pkey PRIMARY KEY (id);

ALTER TABLE ONLY is_menu
    ADD CONSTRAINT is_menu_pkey PRIMARY KEY (id);

ALTER TABLE ONLY is_role
    ADD CONSTRAINT is_role_pkey PRIMARY KEY (id);

ALTER TABLE ONLY is_url
    ADD CONSTRAINT is_url_pkey PRIMARY KEY (id);

ALTER TABLE ONLY is_web_lang
    ADD CONSTRAINT is_web_lang_pkey PRIMARY KEY (id);

ALTER TABLE ONLY "user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);

ALTER TABLE ONLY is_asso_user_role
    ADD CONSTRAINT fkes27fvnpp3uw7nm0l30ky68ba FOREIGN KEY (id_role) REFERENCES is_role(id);

ALTER TABLE ONLY is_cm_value_list_entry
    ADD CONSTRAINT fkgk43lhq0yceaqtaoyewv4rgj1 FOREIGN KEY (value_list_id) REFERENCES is_cm_value_list(id);

ALTER TABLE ONLY asso_user_group
    ADD CONSTRAINT fkkplmtr5hqqarr45bqakhqs52u FOREIGN KEY (id_group) REFERENCES "group"(id);

ALTER TABLE ONLY asso_user_group
    ADD CONSTRAINT fkkpri82cobj7eibo89ha4cu0gr FOREIGN KEY (id_user) REFERENCES "user"(id);

ALTER TABLE ONLY is_asso_user_role
    ADD CONSTRAINT fkl7nk4gdsdow64nbe5per7xo7j FOREIGN KEY (id_user) REFERENCES "user"(id);