insert into users (name, userrole) values ('Пушкин', 'ADMIN');
insert into users (name, userrole) values ('Лермонтов', 'USER');
insert into files (name, filestatus, user_id) values ('ololol.txt', 'ACTIVE', 1);
insert into files (name, filestatus, user_id) values ('gsom.txt', 'ACTIVE', 1);
insert into files (name, filestatus, user_id) values ('vit.txt', 'ACTIVE', 2);
insert into events (type, file_id) values ('CREATE', 1);
insert into events (type, file_id) values ('CREATE', 2);
insert into events (type, file_id) values ('CREATE', 3);