\prompt 'Usuario: ' user
\prompt 'Database: ' db
\c :db;
create schema administrativo authorization :user;
--------------------------------------------------------------------------------------------------------------
-- Usuario
--------------------------------------------------------------------------------------------------------------
create sequence administrativo.usuario_sequencia start 1 increment 1;
alter table administrativo.usuario_sequencia owner to :user;

create table administrativo.usuario (
    id_usuario bigint not null,
    constraint home_id_usuario_pk primary key (id_usuario),

    login varchar (255) unique,
    email varchar (255) unique,
    nome varchar (255),
    senha varchar (255)
);

alter table administrativo.usuario owner to :user;
--------------------------------------------------------------------------------------------------------------
-- Grupo
--------------------------------------------------------------------------------------------------------------
create table administrativo.grupo (
    nome_grupo varchar (255) not null,
    constraint home_nome_grupo_pk primary key (nome_grupo),

    descricao text
);

alter table administrativo.grupo owner to :user;
--------------------------------------------------------------------------------------------------------------
-- Permissão
--------------------------------------------------------------------------------------------------------------
create table administrativo.permissao (
    codigo_permissao varchar (255) not null,
    constraint home_codigo_permissao_pk primary key (codigo_permissao),

    descricao text
);

alter table administrativo.permissao owner to :user;
--------------------------------------------------------------------------------------------------------------
-- Usuario Grupo M2M
--------------------------------------------------------------------------------------------------------------
create table administrativo.usuario_grupo (
    id_usuario bigint not null,
    nome_grupo varchar (255) not null,
    constraint home_id_usuario_nome_grupo_pk primary key (id_usuario, nome_grupo),

    constraint home_id_usuario_fk foreign key (id_usuario) references administrativo.usuario (id_usuario),
    constraint home_nome_grupo_fk foreign key (nome_grupo) references administrativo.grupo (nome_grupo)
);

alter table administrativo.usuario_grupo owner to :user;
--------------------------------------------------------------------------------------------------------------
-- Usuario Permissão M2M
--------------------------------------------------------------------------------------------------------------
create table administrativo.usuario_permissao (
    id_usuario bigint not null,
    codigo_permissao varchar (255) not null,
    constraint home_id_usuario_codigo_permissao_pk primary key (id_usuario, codigo_permissao),

    constraint home_id_usuario_fk foreign key (id_usuario) references administrativo.usuario (id_usuario),
    constraint home_codigo_permissao_fk foreign key (codigo_permissao) references administrativo.permissao (codigo_permissao)
);

alter table administrativo.usuario_permissao owner to :user;
--------------------------------------------------------------------------------------------------------------
-- Grupo Permissão M2M
--------------------------------------------------------------------------------------------------------------
create table administrativo.grupo_permissao (
    nome_grupo varchar (255) not null,
    codigo_permissao varchar (255) not null,
    constraint home_nome_grupo_codigo_permissao_pk primary key (nome_grupo, codigo_permissao),

    constraint home_nome_grupo_fk foreign key (nome_grupo) references administrativo.grupo (nome_grupo),
    constraint home_codigo_permissao_fk foreign key (codigo_permissao) references administrativo.permissao (codigo_permissao)
);

alter table administrativo.grupo_permissao owner to :user;
--------------------------------------------------------------------------------------------------------------
insert into administrativo.permissao values ('USUARIO', 'Usuário');
insert into administrativo.permissao values ('ADMIN', 'Administrador');

insert into administrativo.usuario values (nextval('administrativo.usuario_sequencia'), 'igor.carvalho', 'igor@domain.com', 'Igor Oak', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8');
insert into administrativo.usuario_permissao values (1, 'USUARIO');

insert into administrativo.usuario values (nextval('administrativo.usuario_sequencia'), 'admin', 'admin@domain.com', 'Administrador', 'b20b0f63ce2ed361e8845d6bf2e59811aaa06ec96bcdb92f9bc0c5a25e83c9a6');
insert into administrativo.usuario_permissao values (2, 'USUARIO');
insert into administrativo.usuario_permissao values (2, 'ADMIN');