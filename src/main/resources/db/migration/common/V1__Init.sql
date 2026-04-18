
CREATE TABLE public.categories (
	id varchar(255) NOT NULL,
	created_at timestamp(6) NOT NULL,
	created_by varchar(255) NOT NULL,
	deleted bool NOT NULL,
	updated_at timestamp(6) NULL,
	updated_by varchar(255) NULL,
	"Categorie Name" varchar(255) NOT NULL,
	description text NOT NULL,
	CONSTRAINT categories_pkey PRIMARY KEY (id),
	CONSTRAINT categorie_name_unique_constraint UNIQUE ("Categorie Name")
);

CREATE TABLE public.products (
	id varchar(255) NOT NULL,
	created_at timestamp(6) NOT NULL,
	created_by varchar(255) NOT NULL,
	deleted bool NOT NULL,
	updated_at timestamp(6) NULL,
	updated_by varchar(255) NULL,
	alerte_thresholds int4 NOT NULL,
	description text NOT NULL,
	price numeric(38, 2) NOT NULL,
	"name" varchar(255) NOT NULL,
	reference varchar(255) NOT NULL,
	category_id varchar(255) NULL,
	CONSTRAINT products_pkey PRIMARY KEY (id),
	CONSTRAINT product_reference_unique_constraint UNIQUE (reference),
	CONSTRAINT fk_categorie FOREIGN KEY (category_id) REFERENCES public.categories(id)
);

CREATE TABLE public.stock_mvts (
	id varchar(255) NOT NULL,
	created_at timestamp(6) NOT NULL,
	created_by varchar(255) NOT NULL,
	deleted bool NOT NULL,
	updated_at timestamp(6) NULL,
	updated_by varchar(255) NULL,
	"comment" text NULL,
	date_mvt timestamp(6) NOT NULL,
	quantity int4 NOT NULL,
	type_mvt varchar(255) NOT NULL,
	product_id varchar(255) NULL,
	CONSTRAINT stock_mvts_pkey PRIMARY KEY (id),
	CONSTRAINT stock_mvts_type_mvt_check CHECK (((type_mvt)::text = ANY ((ARRAY['IN'::character varying, 'OUT'::character varying])::text[]))),
	CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES public.products(id)
);