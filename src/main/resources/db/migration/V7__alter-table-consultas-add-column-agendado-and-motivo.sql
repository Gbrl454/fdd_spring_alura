alter table consultas
    add agendado tinyint, add motivo varchar(150) not null;

update consultas
set agendado = 1;
