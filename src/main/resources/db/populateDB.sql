DELETE FROM votes;
DELETE FROM dishes;
DELETE FROM menus;
DELETE FROM restaurants;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 1;

INSERT INTO users (name, password) VALUES
  /*pass1*/('user1', '$2a$10$rnzgFLTBv9CflcoFNOVYJeeDt5L9TPBTlqaDS0pV1/0Z9gzVJr5Im'),
  /*pass2*/('user2', '$2a$10$ezeChTkLXslhexdaHbZvkuNiuQ0n54X4sqlQsAOOwYOUblFkIph1a'),
  /*admin*/('admin', '$2a$10$OeOK2UDzB6yTcQH9nME/7eYYsG7hGZToA4jEpA9GglaCEiqKNZjkW');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 1),
  ('ROLE_USER', 2),
  ('ROLE_ADMIN', 3);

INSERT INTO restaurants (name) VALUES
  /*4*/('Бочонок'),
  /*5*/('Форпост'),
  /*6*/('Рубин');

INSERT INTO menus (restaurant_id, date) VALUES
  /*7*/(4, '2017-06-04'),
  /*8*/(5, '2017-06-04'),
  /*9*/(6, '2017-06-04'),
  /*10*/(4, '2017-06-08'),
  /*11*/(5, '2017-06-08'),
  /*12*/(6, '2017-06-08');

INSERT INTO dishes (menu_id, name, price) VALUES
  /*13*/(7, 'Шашлык', 500),
  /*14*/(7, 'Салат', 200),
  /*15*/(8, 'Утка', 700),
  /*16*/(9, 'Окрошка', 200),
  /*17*/(9, 'Хлеб', 50),
  /*18*/(9, 'Салат', 100),
  /*19*/(10, 'Пицца', 300),
  /*20*/(11, 'Борщ', 200),
  /*21*/(12, 'Суши', 220);

INSERT INTO votes (user_id, restaurant_id, date) VALUES
  /*22*/(1, 4, '2017-06-08'),
  /*23*/(2, 5, '2017-06-08'),
  /*24*/(3, 4, '2017-06-08');