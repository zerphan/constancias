alter table administrador default charset = utf8 collate 
utf8_spanish_ci;

alter table administrador modify column password
varchar(32) collate utf8_bin;

