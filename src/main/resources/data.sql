delete from reserved_seat;
delete from reservation;
delete from screening;
delete from seat;
delete from screening_room_row;
delete from screening_room;
delete from movie;

insert into movie (id, title) values (0, 'Batman');
insert into movie (id, title) values (1, 'Jaws');

insert into screening_room (id) values (0);

insert into screening (id, screening_room_id, movie_id, from_date_time, to_date_time) values (0, 0, 0, '2021-06-30 8:00:00', '2021-06-30 9:30:00');
insert into screening (id, screening_room_id, movie_id, from_date_time, to_date_time) values (1, 0, 0, '2021-06-30 10:00:00', '2021-06-30 11:30:00');
insert into screening (id, screening_room_id, movie_id, from_date_time, to_date_time) values (2, 0, 1, '2021-06-30 12:00:00', '2021-06-30 13:00:00');

-- Room 0 setup
-- [o-o-o-o]
-- [o-o-o-o]
-- [o-o-o-o]

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

-- Block seat row: 1 coL: 1
insert into reservation (id, screening_id, firstname, lastname) values (0, 1, 'John', 'Wick');
insert into reserved_seat (id, reservation_id, seat_id) values (0, 0, 0);

insert into reservation (id, screening_id, firstname, lastname) values (1, 2, 'Kenny', 'Ackerman');
insert into reserved_seat (id, reservation_id, seat_id) values (1, 1, 1);