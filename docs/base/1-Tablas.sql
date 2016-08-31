
CREATE TABLE adminentidad (
    id integer NOT NULL,
    fechaalta date NOT NULL,
    fechabaja date,
    fechamodif date,
    habilitado boolean,
    usalta_id bigint NOT NULL,
    usbaja_id bigint,
    usmodif_id bigint
);

CREATE SEQUENCE adminentidad_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE centropoblado (
    id integer NOT NULL,
    nombre character varying(255),
    centropobladotipo_id bigint,
    departamento_id bigint,
    adminentidad_id bigint
);

CREATE SEQUENCE centropoblado_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE centropobladotipo (
    id integer NOT NULL,
    nombre character varying(255),
    adminentidad_id bigint
);

CREATE SEQUENCE centropobladotipo_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE departamento (
    id integer NOT NULL,
    nombre character varying(255),
    provincia_id bigint,
    adminentidad_id bigint
);

CREATE SEQUENCE departamento_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE especificidadderegion (
    id integer NOT NULL,
    nombre character varying(255),
    adminentidad_id bigint
);

CREATE SEQUENCE especificidadderegion_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE localidad (
    id integer NOT NULL,
    codigopostal character varying(10) NOT NULL,
    nombre character varying(50) NOT NULL
);

CREATE SEQUENCE localidad_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE municipio (
    id integer NOT NULL,
    nombre character varying(255),
    departamento_id bigint,
    provincia_id bigint,
    adminentidad_id bigint
);

CREATE TABLE municipiosindec (
    municipio text,
    departamento text,
    prov_id integer,
    muni_cod text,
    tipo text
);

CREATE TABLE provincia (
    id integer NOT NULL,
    nombre character varying(255),
    adminentidad_id bigint,
    prov_id integer
);

CREATE VIEW muni AS
 SELECT rtrim(ltrim((m.nombre)::text)) AS municipios_nuestros_mayusculas,
    p.id AS id_provincia1,
    mi.prov_id AS id_provincia2,
    rtrim(ltrim(upper(translate(mi.municipio, '·ÈÌÛ˙¡…Õ”⁄‰ÎÔˆ¸ƒÀœ÷‹'::text, 'aeiouAEIOUaeiouAEIOU'::text))))
	AS municipios_del_indec_mayusculas
   FROM ((municipiosindec mi
     LEFT JOIN provincia p ON ((mi.prov_id = p.prov_id)))
     LEFT JOIN municipio m ON (((rtrim(ltrim((m.nombre)::text)) = 
	 rtrim(ltrim(upper(translate(mi.municipio, '·ÈÌÛ˙¡…Õ”⁄‰ÎÔˆ¸ƒÀœ÷‹'::text, 'aeiouAEIOUaeiouAEIOU'::text))))) 
	 AND (p.id = m.provincia_id))));

CREATE SEQUENCE municipio_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE municipios (
    idmunicipio integer,
    nombre text,
    idprovincia integer
);

CREATE VIEW muniindec AS
 SELECT p.id AS provincia_id,
    rtrim(ltrim(upper(translate(mi.departamento, '·ÈÌÛ˙¡…Õ”⁄‰ÎÔˆ¸ƒÀœ÷‹'::text, 'aeiouAEIOUaeiouAEIOU'::text)))) AS departamento,
    rtrim(ltrim(upper(translate(mi.municipio, '·ÈÌÛ˙¡…Õ”⁄‰ÎÔˆ¸ƒÀœ÷‹'::text, 'aeiouAEIOUaeiouAEIOU'::text)))) AS municipio
   FROM (municipiosindec mi
     JOIN provincia p ON ((mi.prov_id = p.prov_id)));

CREATE VIEW muniindec2 AS
 SELECT m.provincia_id,
    m.id AS muni_id,
    mi.departamento,
    mi.municipio
   FROM (municipio m
     JOIN muniindec mi ON (((mi.provincia_id = m.provincia_id) 
	 AND (rtrim(ltrim((m.nombre)::text)) = rtrim(ltrim(mi.municipio))))));

CREATE VIEW muniindec3 AS
 SELECT mi2.muni_id AS municipio_id,
    d.id AS departamento_id
   FROM (muniindec2 mi2
     JOIN departamento d ON (((mi2.provincia_id = d.provincia_id) 
	 AND (rtrim(ltrim((d.nombre)::text)) = rtrim(ltrim(mi2.departamento))))));

CREATE VIEW munis AS
 SELECT rtrim(ltrim((m.nombre)::text)) AS municipios_nuestros_mayusculas,
    p.prov_id AS id_provincia1,
    mi.prov_id AS id_provincia2,
        CASE
            WHEN (mi.prov_id IS NULL) THEN 0
            ELSE mi.prov_id
        END AS field_alias,
    rtrim(ltrim(upper(translate(mi.municipio, '·ÈÌÛ˙¡…Õ”⁄‰ÎÔˆ¸ƒÀœ÷‹'::text, 'aeiouAEIOUaeiouAEIOU'::text)))) 
	AS municipios_del_indec_mayusculas
   FROM ((municipiosindec mi
     JOIN provincia p ON ((mi.prov_id = p.prov_id)))
     RIGHT JOIN municipio m ON (((rtrim(ltrim((m.nombre)::text)) =
	 rtrim(ltrim(upper(translate(mi.municipio, '·ÈÌÛ˙¡…Õ”⁄‰ÎÔˆ¸ƒÀœ÷‹'::text, 'aeiouAEIOUaeiouAEIOU'::text)))))
	 AND (p.id = m.provincia_id))));

CREATE SEQUENCE provincia_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE region (
    id integer NOT NULL,
    nombre character varying(255),
    especificidadderegion_id bigint,
    adminentidad_id bigint
);

CREATE SEQUENCE region_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE regionesxprovincias (
    provincia_fk bigint NOT NULL,
    region_fk bigint NOT NULL
);

CREATE TABLE rol (
    id integer NOT NULL,
    nombre character varying(255),
    adminentidad_id bigint
);

CREATE SEQUENCE rol_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE sequence (
    seq_name character varying(50) NOT NULL,
    seq_count numeric(38,0)
);

CREATE TABLE usuario (
    id integer NOT NULL,
    nombre character varying(20) NOT NULL,
    rol_id bigint,
    admin_id bigint,
    nombrecompleto character varying(100)
);

CREATE SEQUENCE usuario_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
