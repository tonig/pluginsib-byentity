create sequence ZPE_SEQTCK;

create sequence ZPE_SEQTCX;

create table ZPE_TICKET  (
   TCK_CODIGO           NUMBER(20)                      not null,
   TCK_FCSES            DATE                            not null,
   TCK_IDIOMA           VARCHAR2(2)                     not null,
   TCK_IDPS             VARCHAR2(100),
   TCK_URLCBK           VARCHAR2(4000)                  not null,
   TCK_URLDST           VARCHAR2(4000)                  not null,
   TCK_TICKET           VARCHAR2(100),
   TCK_FCALTA           DATE,
   TCK_NIVAUT           VARCHAR2(1),
   TCK_NIF              VARCHAR2(10),
   TCK_NOMAPE           VARCHAR2(1000),
   TCK_FCULT            DATE
);

comment on table ZPE_TICKET is
'Sesiones para autenticaciones basadas en ticket para Cl@ve';

comment on column ZPE_TICKET.TCK_CODIGO is
'Codigo interno secuencial';

comment on column ZPE_TICKET.TCK_FCSES is
'Fecha inicio sesion';

comment on column ZPE_TICKET.TCK_IDIOMA is
'Idioma';

comment on column ZPE_TICKET.TCK_IDPS is
'IDPS';

comment on column ZPE_TICKET.TCK_URLCBK is
'Url callback sistra';

comment on column ZPE_TICKET.TCK_URLDST is
'Url destino sistra';

comment on column ZPE_TICKET.TCK_TICKET is
'Ticket';

comment on column ZPE_TICKET.TCK_FCALTA is
'Fecha alta';

comment on column ZPE_TICKET.TCK_NIVAUT is
'Nivel autenticacion (C/U)';

comment on column ZPE_TICKET.TCK_NIF is
'Nif';

comment on column ZPE_TICKET.TCK_NOMAPE is
'Nombre completo';

comment on column ZPE_TICKET.TCK_FCULT is
'Fecha ultimo login';

alter table ZPE_TICKET
   add constraint ZPE_TCK_PK primary key (TCK_CODIGO);

create unique index ZPE_TCKTCK_IDX on ZPE_TICKET (
   TCK_TICKET ASC
);

create table ZPE_TICKEX  (
   TCX_CODIGO           NUMBER(20)                      not null,
   TCX_FCSES            DATE                            not null,
   TCX_IDIOMA           VARCHAR2(2)                     not null,
   TCX_IDPS             VARCHAR2(100)                   not null,
   TCX_URLCBK           VARCHAR2(4000)                  not null,
   TCX_TICKET           VARCHAR2(100),
   TCX_FCALTA           DATE,
   TCX_NIVAUT           VARCHAR2(50),
   TCX_NIF              VARCHAR2(10),
   TCX_NOM              VARCHAR2(1000),
   TCX_APE              VARCHAR2(1000)
);

comment on table ZPE_TICKEX is
'Sesiones para autenticaciones basadas en ticket para Cl@ve para aplicaciones externas';

comment on column ZPE_TICKEX.TCX_CODIGO is
'Codigo interno secuencial';

comment on column ZPE_TICKEX.TCX_FCSES is
'Fecha inicio sesion';

comment on column ZPE_TICKEX.TCX_IDIOMA is
'Idioma';

comment on column ZPE_TICKEX.TCX_IDPS is
'Idps (separados por ;)';

comment on column ZPE_TICKEX.TCX_URLCBK is
'Url callback';

comment on column ZPE_TICKEX.TCX_TICKET is
'Ticket';

comment on column ZPE_TICKEX.TCX_FCALTA is
'Fecha alta';

comment on column ZPE_TICKEX.TCX_NIVAUT is
'Nivel autenticacion (Idp con el que se ha autenticado)';

comment on column ZPE_TICKEX.TCX_NIF is
'Nif';

comment on column ZPE_TICKEX.TCX_NOM is
'Nombre';

comment on column ZPE_TICKEX.TCX_APE is
'Apellidos';

alter table ZPE_TICKEX
   add constraint ZPE_TCX_PK primary key (TCX_CODIGO);

create unique index ZPE_TCX_IDX on ZPE_TICKEX (
   TCX_TICKET ASC
);

