
ALTER TABLE ONLY adminentidad
    ADD CONSTRAINT adminentidad_pkey PRIMARY KEY (id);

ALTER TABLE ONLY centropoblado
    ADD CONSTRAINT centropoblado_pkey PRIMARY KEY (id);

ALTER TABLE ONLY centropobladotipo
    ADD CONSTRAINT centropobladotipo_pkey PRIMARY KEY (id);

ALTER TABLE ONLY departamento
    ADD CONSTRAINT departamento_pkey PRIMARY KEY (id);

ALTER TABLE ONLY especificidadderegion
    ADD CONSTRAINT especificidadderegion_pkey PRIMARY KEY (id);

ALTER TABLE ONLY localidad
    ADD CONSTRAINT localidad_codigopostal_key UNIQUE (codigopostal);

ALTER TABLE ONLY localidad
    ADD CONSTRAINT localidad_nombre_key UNIQUE (nombre);

ALTER TABLE ONLY localidad
    ADD CONSTRAINT localidad_pkey PRIMARY KEY (id);

ALTER TABLE ONLY municipio
    ADD CONSTRAINT municipio_pkey PRIMARY KEY (id);

ALTER TABLE ONLY provincia
    ADD CONSTRAINT provincia_pkey PRIMARY KEY (id);

ALTER TABLE ONLY region
    ADD CONSTRAINT region_pkey PRIMARY KEY (id);

ALTER TABLE ONLY regionesxprovincias
    ADD CONSTRAINT regionesxprovincias_pkey PRIMARY KEY (provincia_fk, region_fk);

ALTER TABLE ONLY rol
    ADD CONSTRAINT rol_nombre_key UNIQUE (nombre);

ALTER TABLE ONLY rol
    ADD CONSTRAINT rol_pkey PRIMARY KEY (id);

ALTER TABLE ONLY sequence
    ADD CONSTRAINT sequence_pkey PRIMARY KEY (seq_name);

ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuario_nombre_key UNIQUE (nombre);

ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);

ALTER TABLE ONLY centropoblado
    ADD CONSTRAINT fk_centropoblado_adminentidad_id FOREIGN KEY (adminentidad_id) REFERENCES adminentidad(id);

ALTER TABLE ONLY centropoblado
    ADD CONSTRAINT fk_centropoblado_centropobladotipo_id FOREIGN KEY (centropobladotipo_id) REFERENCES centropobladotipo(id);

ALTER TABLE ONLY centropoblado
    ADD CONSTRAINT fk_centropoblado_departamento_id FOREIGN KEY (departamento_id) REFERENCES departamento(id);

ALTER TABLE ONLY centropobladotipo
    ADD CONSTRAINT fk_centropobladotipo_adminentidad_id FOREIGN KEY (adminentidad_id) REFERENCES adminentidad(id);

ALTER TABLE ONLY departamento
    ADD CONSTRAINT fk_departamento_adminentidad_id FOREIGN KEY (adminentidad_id) REFERENCES adminentidad(id);

ALTER TABLE ONLY departamento
    ADD CONSTRAINT fk_departamento_provincia_id FOREIGN KEY (provincia_id) REFERENCES provincia(id);

ALTER TABLE ONLY especificidadderegion
    ADD CONSTRAINT fk_especificidadderegion_adminentidad_id FOREIGN KEY (adminentidad_id) REFERENCES adminentidad(id);

ALTER TABLE ONLY municipio
    ADD CONSTRAINT fk_municipio_adminentidad_id FOREIGN KEY (adminentidad_id) REFERENCES adminentidad(id);

ALTER TABLE ONLY municipio
    ADD CONSTRAINT fk_municipio_departamento_id FOREIGN KEY (departamento_id) REFERENCES departamento(id);

ALTER TABLE ONLY municipio
    ADD CONSTRAINT fk_municipio_provincia_id FOREIGN KEY (provincia_id) REFERENCES provincia(id);

ALTER TABLE ONLY provincia
    ADD CONSTRAINT fk_provincia_adminentidad_id FOREIGN KEY (adminentidad_id) REFERENCES adminentidad(id);

ALTER TABLE ONLY region
    ADD CONSTRAINT fk_region_adminentidad_id FOREIGN KEY (adminentidad_id) REFERENCES adminentidad(id);

ALTER TABLE ONLY region
    ADD CONSTRAINT fk_region_especificidadderegion_id FOREIGN KEY (especificidadderegion_id) REFERENCES especificidadderegion(id);

ALTER TABLE ONLY regionesxprovincias
    ADD CONSTRAINT fk_regionesxprovincias_provincia_fk FOREIGN KEY (provincia_fk) REFERENCES provincia(id);

ALTER TABLE ONLY regionesxprovincias
    ADD CONSTRAINT fk_regionesxprovincias_region_fk FOREIGN KEY (region_fk) REFERENCES region(id);

ALTER TABLE ONLY usuario
    ADD CONSTRAINT fk_usuario_admin_id FOREIGN KEY (admin_id) REFERENCES adminentidad(id);

ALTER TABLE ONLY usuario
    ADD CONSTRAINT fk_usuario_rol_id FOREIGN KEY (rol_id) REFERENCES rol(id);
