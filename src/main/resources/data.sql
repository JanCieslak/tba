delete from screening;
delete from movie;

insert into movie(id, title) values (0, 'Batman');
insert into movie(id, title) values (1, 'Jaws');

insert into screening(id, movie_id, from_date_time, to_date_time) values (0, 0, '2021-06-30 8:00:00', '2021-06-30 9:30:00');
insert into screening(id, movie_id, from_date_time, to_date_time) values (1, 0, '2021-06-30 10:00:00', '2021-06-30 11:30:00');
insert into screening(id, movie_id, from_date_time, to_date_time) values (2, 1, '2021-06-30 12:00:00', '2021-06-30 13:00:00');
