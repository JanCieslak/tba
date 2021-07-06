delete from ticket;
delete from reserved_seat;
delete from reservation;
delete from screening;
delete from seat;
delete from screening_room_row;
delete from screening_room;
delete from movie;

-- ScreeningRooms setup
-- [o-o-o-o]
-- [o-o-o-o]
-- [o-o-o-o]

-- Screening Room 0
insert into screening_room (id) values (0);

insert into screening_room_row (id, screening_room_row, screening_room_id) values (0, 1, 0);
insert into screening_room_row (id, screening_room_row, screening_room_id) values (1, 2, 0);
insert into screening_room_row (id, screening_room_row, screening_room_id) values (2, 3, 0);

-- Row 1
insert into seat (id, seat_col, screening_room_row_id) values (0, 1, 0);
insert into seat (id, seat_col, screening_room_row_id) values (1, 2, 0);
insert into seat (id, seat_col, screening_room_row_id) values (2, 3, 0);
insert into seat (id, seat_col, screening_room_row_id) values (3, 4, 0);

-- Row 2
insert into seat (id, seat_col, screening_room_row_id) values (4, 1, 1);
insert into seat (id, seat_col, screening_room_row_id) values (5, 2, 1);
insert into seat (id, seat_col, screening_room_row_id) values (6, 3, 1);
insert into seat (id, seat_col, screening_room_row_id) values (7, 4, 1);

-- Row 3
insert into seat (id, seat_col, screening_room_row_id) values (8, 1, 2);
insert into seat (id, seat_col, screening_room_row_id) values (9, 2, 2);
insert into seat (id, seat_col, screening_room_row_id) values (10, 3, 2);
insert into seat (id, seat_col, screening_room_row_id) values (11, 4, 2);

-- Screening Room 1
insert into screening_room (id) values (1);

insert into screening_room_row (id, screening_room_row, screening_room_id) values (3, 1, 1);
insert into screening_room_row (id, screening_room_row, screening_room_id) values (4, 2, 1);
insert into screening_room_row (id, screening_room_row, screening_room_id) values (5, 3, 1);

-- Row 1
insert into seat (id, seat_col, screening_room_row_id) values (12, 1, 3);
insert into seat (id, seat_col, screening_room_row_id) values (13, 2, 3);
insert into seat (id, seat_col, screening_room_row_id) values (14, 3, 3);
insert into seat (id, seat_col, screening_room_row_id) values (15, 4, 3);

-- Row 2
insert into seat (id, seat_col, screening_room_row_id) values (16, 1, 4);
insert into seat (id, seat_col, screening_room_row_id) values (17, 2, 4);
insert into seat (id, seat_col, screening_room_row_id) values (18, 3, 4);
insert into seat (id, seat_col, screening_room_row_id) values (19, 4, 4);

-- Row 3
insert into seat (id, seat_col, screening_room_row_id) values (20, 1, 5);
insert into seat (id, seat_col, screening_room_row_id) values (21, 2, 5);
insert into seat (id, seat_col, screening_room_row_id) values (22, 3, 5);
insert into seat (id, seat_col, screening_room_row_id) values (23, 4, 5);

-- Screening Room 2
insert into screening_room (id) values (2);

insert into screening_room_row (id, screening_room_row, screening_room_id) values (6, 1, 2);
insert into screening_room_row (id, screening_room_row, screening_room_id) values (7, 2, 2);
insert into screening_room_row (id, screening_room_row, screening_room_id) values (8, 3, 2);

-- Row 1
insert into seat (id, seat_col, screening_room_row_id) values (24, 1, 6);
insert into seat (id, seat_col, screening_room_row_id) values (25, 2, 6);
insert into seat (id, seat_col, screening_room_row_id) values (26, 3, 6);
insert into seat (id, seat_col, screening_room_row_id) values (27, 4, 6);

-- Row 2
insert into seat (id, seat_col, screening_room_row_id) values (28, 1, 7);
insert into seat (id, seat_col, screening_room_row_id) values (29, 2, 7);
insert into seat (id, seat_col, screening_room_row_id) values (30, 3, 7);
insert into seat (id, seat_col, screening_room_row_id) values (31, 4, 7);

-- Row 3
insert into seat (id, seat_col, screening_room_row_id) values (32, 1, 8);
insert into seat (id, seat_col, screening_room_row_id) values (33, 2, 8);
insert into seat (id, seat_col, screening_room_row_id) values (34, 3, 8);
insert into seat (id, seat_col, screening_room_row_id) values (35, 4, 8);

insert into movie (id, title) values (0, 'Batman');
insert into screening (id, screening_room_id, movie_id, from_date_time, to_date_time) values (0, 0, 0, '2021-07-30 8:00:00', '2021-07-30 9:30:00');
insert into screening (id, screening_room_id, movie_id, from_date_time, to_date_time) values (1, 0, 0, '2021-07-30 10:00:00', '2021-07-30 11:30:00');

insert into movie (id, title) values (1, 'Jaws');
insert into screening (id, screening_room_id, movie_id, from_date_time, to_date_time) values (2, 1, 1, '2021-07-30 11:00:00', '2021-07-30 12:30:00');
insert into screening (id, screening_room_id, movie_id, from_date_time, to_date_time) values (3, 1, 1, '2021-07-30 14:00:00', '2021-07-30 15:30:00');

insert into movie (id, title) values (2, 'Zażółć gęślą jaźń');
insert into screening (id, screening_room_id, movie_id, from_date_time, to_date_time) values (4, 2, 2, '2021-07-30 15:30:00', '2021-07-30 17:00:00');
insert into screening (id, screening_room_id, movie_id, from_date_time, to_date_time) values (5, 2, 2, '2021-07-30 17:30:00', '2021-07-30 19:00:00');

