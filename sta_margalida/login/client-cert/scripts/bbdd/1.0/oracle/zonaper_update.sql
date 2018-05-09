create table ZPE_TCKCRT  (
   TKC_TICKET           VARCHAR2(100)                   not null,
   TKC_FCALTA           DATE,
   TKC_IDIOMA           VARCHAR2(2)                     not null,
   TKC_URLCBK           VARCHAR2(4000)                  not null,
   TKC_URLDST           VARCHAR2(4000)                  not null,
   TKC_NIF              VARCHAR2(10),
   TKC_NOMAPE           VARCHAR2(1000),
   TKC_FCULT            DATE
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
