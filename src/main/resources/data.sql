insert into user_details(id,birth_date,name)
values (10001, current_date(), 'John Doe');

insert into user_details(id,birth_date,name)
values (10002, current_date(), 'Jane Doe');

insert into user_details(id,birth_date,name)
values (10003, current_date(), 'Jack Doe');

insert into post(id, description, timestamp, user_id)
values (10001, 'My first post', current_date(), 10001);

insert into post(id, description, timestamp, user_id)
values (10002, 'My second post', current_date(), 10002);

insert into post(id, description, timestamp, user_id)
values (10003, 'My third post', current_date(), 10003);