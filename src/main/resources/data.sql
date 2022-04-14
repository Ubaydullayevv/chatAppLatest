
------------------------------------- insert into users
insert into users (id, firstname,lastname,password, email, phone_number, username, last_seen, bio,is_registration_completed) values (1000006, 'Birk', 'Isakowicz', '111','bisakowicz5@google.com.hk', '768-461-7801', 'birk', '2022-03-21', 'faucibus accumsan odio',true);
insert into users (id, firstname,lastname,password, email, phone_number, username, last_seen, bio,is_registration_completed) values (1000007, 'Avery', 'Croal', '111','acroal6@amazon.de', '482-550-5060', 'avery', '2022-03-13', 'et commodo',true);
insert into users (id, firstname,lastname,password, email, phone_number, username, last_seen, bio,is_registration_completed) values (1000008, 'Nanon', 'Apfelmann', '111','napfelmann7@google.co.uk', '861-461-9971', 'nanon', '2022-04-01', 'nulla pede',true);
insert into users (id, firstname,lastname,password, email, phone_number, username, last_seen, bio,is_registration_completed) values (1000009, 'Malorie', 'Frobisher', '111','mfrobisher8@behance.net', '447-515-8794', 'malorie', '2022-04-08', 'in ante',true);
insert into users (id, firstname,lastname,password, email, phone_number, username, last_seen, bio,is_registration_completed) values (1000010, 'Vic', 'Bucktharp', '111','vbucktharp9@pen.io', '622-265-7225', 'vic', '2022-04-10', 'non mauris morbi',true);

-------------------------------------------------- insert into chat room
insert into chat_rooms(id, user1_id, user2_id) values (1000011, 1000010,1000009);
insert into chat_rooms(id, user1_id, user2_id) values (1000012, 1000008,1000009);
insert into chat_rooms(id, user1_id, user2_id) values (1000013, 1000007,1000009);
insert into chat_rooms(id, user1_id, user2_id) values (1000014, 1000008,1000009);


--------------------------------------- inset into
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000015, '2022-04-08',false,null,1000011,1000010, 'ac nibh fusce', '2021-06-07');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000016, '2021-09-28',false,null,1000011,1000010, 'proin eu', '2021-08-01');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000017, '2022-01-27',false,null,1000011,1000010, 'etiam faucibus cursus', '2022-02-23');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000018, '2022-01-15',false,null,1000011,1000010, 'ultrices vel augue', '2021-12-03');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000019, '2021-11-06',false,null,1000011,1000010, 'ac est', '2021-11-15');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000020, '2021-10-30',false,null,1000011,1000010, 'porta volutpat', '2021-06-12');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000021, '2022-02-04',false,null,1000011,1000010, 'neque sapien placerat ante', '2021-05-29');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000022, '2021-10-14',false,null,1000011,1000010, 'hac habitasse platea', '2021-06-30');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000023, '2021-10-06',false,null,1000011,1000010, 'non lectus', '2021-12-12');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000024, '2021-11-21',false,null,1000011,1000010, 'sit amet', '2022-03-28');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000025, '2022-03-25',false,null,1000011,1000010, 'vitae mattis', '2022-02-24');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000026, '2022-01-29',false,null,1000011,1000010, 'nulla nisl', '2021-06-23');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000027, '2021-11-13',false,null,1000011,1000010, 'dolor sit', '2021-10-22');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000028, '2022-03-17',false,null,1000011,1000010, 'lacinia eget', '2021-04-12');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000029, '2022-01-17',false,null,1000011,1000010, 'accumsan odio', '2021-06-07');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000030, '2021-11-01',false,null,1000011,1000009, 'in est risus', '2021-04-18');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000031, '2021-11-12',false,null,1000011,1000009, 'congue etiam', '2021-12-19');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000032, '2022-03-25',false,null,1000011,1000009, 'id nulla', '2021-04-19');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000033, '2021-11-01',false,null,1000012,1000008, 'tincidunt ante', '2021-12-20');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000034, '2021-10-29',false,null,1000012,1000008, 'nibh in hac', '2022-02-19');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000035, '2022-01-30',false,null,1000012,1000008, 'ut dolor morbi vel', '2021-05-09');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000036, '2021-12-12',false,null,1000012,1000008, 'volutpat erat quisque', '2021-07-14');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000037, '2022-03-31',false,null,1000012,1000008, 'condimentum neque sapien placerat', '2021-12-17');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000038, '2022-04-07',false,null,1000012,1000008, 'sed vestibulum sit', '2021-12-24');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000039, '2022-02-01',false,null,1000012,1000008, 'proin eu mi nulla', '2022-01-19');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000040, '2021-09-11',false,null,1000012,1000008, 'vel lectus in', '2022-01-14');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000041, '2022-01-14',false,null,1000012,1000008, 'ligula pellentesque', '2021-06-16');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000042, '2022-02-21',false,null,1000012,1000008, 'quam pharetra', '2021-06-20');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000043, '2021-11-06',false,null,1000012,1000009, 'iaculis diam erat', '2022-01-28');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000044, '2021-12-03',false,null,1000012,1000009, 'posuere cubilia', '2021-06-08');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000045, '2021-10-16',false,null,1000012,1000009, 'neque sapien placerat', '2022-03-02');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000046, '2022-02-25',false,null,1000013,1000007, 'lorem ipsum', '2021-04-14');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000047, '2022-01-23',false,null,1000013,1000007, 'amet eleifend', '2021-10-08');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000048, '2021-08-23',false,null,1000013,1000007, 'sapien sapien', '2021-06-07');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000049, '2022-01-18',false,null,1000013,1000007, 'curae duis faucibus', '2021-05-03');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000050, '2021-10-18',false,null,1000013,1000007, 'erat id mauris vulputate', '2021-09-10');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000051, '2021-10-10',false,null,1000013,1000007, 'quisque porta volutpat erat', '2021-09-28');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000052, '2021-08-25',false,null,1000013,1000007, 'velit donec diam neque', '2022-04-09');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000053, '2021-10-20',false,null,1000013,1000007, 'nibh quisque id justo', '2021-08-03');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000054, '2021-11-28',false,null,1000013,1000007, 'amet erat nulla', '2021-06-12');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000055, '2022-03-18',false,null,1000013,1000007, 'sodales scelerisque mauris', '2021-05-06');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000056, '2021-08-22',false,null,1000013,1000007, 'pede malesuada in imperdiet', '2021-08-28');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000057, '2021-12-01',false,null,1000013,1000007, 'vitae nisl', '2021-06-23');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000058, '2022-03-03',false,null,1000013,1000007, 'cras in purus', '2021-11-20');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000059, '2021-09-17',false,null,1000013,1000007, 'donec dapibus duis at', '2022-03-28');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000060, '2022-04-07',false,null,1000013,1000007, 'ac diam cras pellentesque', '2022-03-29');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000061, '2021-09-11',false,null,1000013,1000007, 'vestibulum quam sapien varius', '2021-04-13');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000062, '2021-10-21',false,null,1000013,1000009, 'orci eget orci vehicula', '2021-09-27');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000063, '2021-12-12',false,null,1000013,1000009, 'volutpat dui maecenas tristique', '2021-12-06');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000064, '2021-12-02',false,null,1000013,1000009, 'mauris laoreet ut', '2021-11-24');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000065, '2022-02-25',false,null,1000013,1000009, 'mollis molestie', '2021-10-05');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000066, '2021-09-13',false,null,1000013,1000009, 'quis orci eget', '2021-07-27');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000067, '2021-12-09',false,null,1000013,1000009, 'nulla ut erat id', '2022-01-04');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000068, '2022-02-03',false,null,1000013,1000009, 'in felis eu', '2021-05-06');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000069, '2022-02-12',false,null,1000013,1000009, 'nisi venenatis', '2021-07-23');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000070, '2022-02-07',false,null,1000013,1000009, 'erat vestibulum sed', '2022-01-27');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000071, '2021-12-06',false,null,1000013,1000009, 'lorem integer', '2021-09-29');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000072, '2021-08-30',false,null,1000013,1000009, 'posuere metus', '2021-04-20');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000073, '2022-02-03',false,null,1000013,1000009, 'tortor eu', '2021-05-27');
insert into chat_messages (id, created_at,is_read,delete_from, chat_room_id, from_id, text, updated_at) values (1000074, '2022-02-03',false,null,1000013,1000009, 'mattis odio', '2022-01-29');



------------------------------------------- insert into permissions
insert into permissions(id, name, permission_enum) values (1000075, 'MEMBER', 'MEMBER');
insert into permissions(id, name, permission_enum) values (1000076, 'DELETE_USER', 'DELETE_USER');
insert into permissions(id, name, permission_enum) values (1000077, 'DELETE_USER_MESSAGE', 'DELETE_USER_MESSAGE');
insert into permissions(id, name, permission_enum) values (1000078, 'CHANGE_AVATAR', 'CHANGE_AVATAR');
insert into permissions(id, name, permission_enum) values (1000079, 'CHANGE_NAME', 'CHANGE_NAME');
insert into permissions(id, name, permission_enum) values (1000080, 'OWNER', 'OWNER');
insert into permissions(id, name, permission_enum) values (1000081, 'BLOCKED', 'BLOCKED');
















