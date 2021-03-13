create TABLE users (
  id SERIAL PRIMARY KEY,
  name VARCHAR(245) NULL,
  userrole VARCHAR(20) NULL);


create TABLE events (
  id SERIAL PRIMARY KEY,
  type VARCHAR(245) NULL);



   create TABLE files (
  id SERIAL PRIMARY KEY,
 name VARCHAR(245) NULL,
  filestatus VARCHAR(20) NULL,
  user_id integer REFERENCES users(id) ON DELETE CASCADE);

 alter table events add COLUMN
 file_id integer REFERENCES files(id) ON DELETE CASCADE;

alter table events
    add COLUMN date_event date;