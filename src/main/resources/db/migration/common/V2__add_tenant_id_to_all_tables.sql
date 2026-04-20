ALTER TABLE public.categories ADD tenant_id varchar NOT NULL;
COMMENT ON COLUMN public.categories.tenant_id IS 'Tenant ID';

ALTER TABLE public.products ADD tenant_id varchar NOT NULL;
COMMENT ON COLUMN public.products.tenant_id IS 'Tenant ID';

ALTER TABLE public.stock_mvts ADD tenant_id varchar NOT NULL;
COMMENT ON COLUMN public.stock_mvts.tenant_id IS 'Tenant ID';


