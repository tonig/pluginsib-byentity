create table ZPE_TCKCRT  (
   TKC_TICKET           character varying(100)                   not null,
   TKC_FCALTA           TIMESTAMP WITH TIME ZONE,
   TKC_IDIOMA           character varying(2)                     not null,
   TKC_URLCBK           character varying(4000)                  not null,
   TKC_URLDST           character varying(4000)                  not null,
   TKC_NIF              character varying(10),
   TKC_NOMAPE           character varying(1000),
   TKC_FCULT            TIMESTAMP WITH TIME ZONE
);

comment on table ZPE_TCKCRT is
'Sesiones para autenticaciones basadas en ticket para modulo client-cert';

comment on column ZPE_TCKCRT.TKC_TICKET is
'Ticket';

comment on column ZPE_TCKCRT.TKC_FCALTA is
'Fecha alta';

comment on column ZPE_TCKCRT.TKC_IDIOMA is
'Idioma';

comment on column ZPE_TCKCRT.TKC_URLCBK is
'Url callback sistra';

comment on column ZPE_TCKCRT.TKC_URLDST is
'Url destino sistra';

comment on column ZPE_TCKCRT.TKC_NIF is
'Nif';

comment on column ZPE_TCKCRT.TKC_NOMAPE is
'Nombre completo';

comment on column ZPE_TCKCRT.TKC_FCULT is
'Fecha ultimo login';

alter table ZPE_TCKCRT
   add constraint ZPE_TKC_PK primary key (TKC_TICKET);
