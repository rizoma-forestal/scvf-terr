-- Table: usuario

-- DROP TABLE usuario;

CREATE TABLE usuario
(
  id serial NOT NULL,
  nombre character varying(20) NOT NULL,
  rol_id bigint,
  admin_id bigint,
  nombrecompleto character varying(100),
  CONSTRAINT usuario_pkey PRIMARY KEY (id),
  CONSTRAINT fk_usuario_admin_id FOREIGN KEY (admin_id)
      REFERENCES adminentidad (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_usuario_rol_id FOREIGN KEY (rol_id)
      REFERENCES rol (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT usuario_nombre_key UNIQUE (nombre)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE usuario
  OWNER TO postgres;