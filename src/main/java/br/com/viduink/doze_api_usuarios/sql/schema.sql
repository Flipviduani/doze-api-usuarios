create table perfis (
    id      int             auto_increment,
    nome    varchar(25)     not null unique,
    primary key(id)
);

create table usuarios(
    id                   int              auto_increment,
    nome                 varchar(100)     not null,
    email                varchar(100)     not null unique,
    senha                varchar(100)     not null,
    data_hora_cadastro   timestamp        not null,
    perfil_id            int              not null,
    primary key(id),
    foreign key(perfil_id) references perfis(id)
);