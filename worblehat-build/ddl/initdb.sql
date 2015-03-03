drop database if exists worblehat;
create database worblehat;

grant all on worblehat.* to worblehat@'%' identified by 'worblehat';
grant all on worblehat.* to worblehat@'localhost' identified by 'worblehat';
