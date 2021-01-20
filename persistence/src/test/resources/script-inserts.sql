INSERT INTO tag (id_tag, name_tag) VALUES
(1, 'gift'),
(2, 'sport'),
(3, 'jumping'),
(4, 'riding'),
(5, 'wonderful gift'),
(6, 'relax'),
(7, 'make you fun');

INSERT INTO gift_certificate (id_gift_certificate, name_gift_certificate,
description, price, duration, create_date, last_update_date) VALUES
(1, 'Skating', 'Ice skating is a sport in which people slide over a smooth ice surface on steel-bladed skates. Millions of people skate in those parts of the world where the winters are cold enough.', 10, 30, '2021-01-10 12:15:37', '2021-01-10 12:15:37'),
(2, 'Fitness', 'Physical fitness is a state of health and well-being and, more specifically, the ability to perform aspects of sports, occupations and daily activities. Physical fitness is generally achieved through proper nutrition, moderate-vigorous physical exercise, and sufficient rest.', 80, 30, '2021-01-11 10:30:01', '2021-01-11 10:30:01'),
(3, 'Horseback riding', 'Horseback riding is the activity of riding a horse, especially for enjoyment or as a form of exercise.', 100, 30, '2021-01-12 11:34:18', '2021-01-12 11:34:18'),
(4, 'Trampoline jumping', 'Trampoline jumping can be fun.', 20, 30, '2021-01-12 11:34:18', '2021-01-12 11:34:18');

INSERT INTO gift_certificate_tag (id_gift_certificate, id_tag) VALUES
(1, 1),
(1, 2),
(1, 7),
(2, 2),
(2, 5),
(2, 7),
(3, 1),
(3, 4),
(3, 5),
(3, 6),
(4, 2),
(4, 3),
(4, 6),
(4, 7);
