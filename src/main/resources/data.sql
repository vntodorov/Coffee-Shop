INSERT INTO roles(id, role)
VALUES (1, 'CLIENT'), (2, 'MODERATOR'), (3, 'ADMIN');

INSERT INTO categories(id, name)
VALUES (1, 'LATTE'), (2, 'ESPRESSO'), (3, 'CAPPUCCINO'), (4, 'AMERICANO');

INSERT INTO users(created, email, first_name, image_url, last_name, middle_name, password, username)
VALUES ('2023-03-12', 'admin@abv.bg', 'admin', null, 'admin', 'admin', '$2a$10$8/hM3Sukc/tF0X9axqD9keyJD46aBdm2OyeQt5hNxNkzMMhRyEuJy', 'admin'),
       ('2023-03-12', 'user@abv.bg', 'user', null, 'user', 'user', '$2a$10$8/hM3Sukc/tF0X9axqD9keyJD46aBdm2OyeQt5hNxNkzMMhRyEuJy', 'user');

INSERT INTO users_roles(user_id, role_id)
VALUES (1,1), (1,3), (2,1);

INSERT INTO brands(description, image_url, name)
VALUES ("Good brand!", "https://www.nestle.com/sites/default/files/styles/brand_logo/public/nescafe-logo-square.png?h=a7e6d17b&itok=HbeWWpwO", "Nescafe"),
       ("Amazing brand!", "https://play-lh.googleusercontent.com/zScFS2Y-H_crW7WbEXz4GbWGv6cuNOg7bWRFs0L19936utOhpDpoEc4qSG6jYNdUYnc", "Tim Hortons"),
       ("Popular brand!", "https://upload.wikimedia.org/wikipedia/en/thumb/d/d3/Starbucks_Corporation_Logo_2011.svg/640px-Starbucks_Corporation_Logo_2011.svg.png", "Starbucks");

INSERT INTO products(added, description, image_url, name, price, weight, brand_id, category_id)
VALUES ('2023-03-12', 'Good product!', 'https://www.maxicoffee.com/en-gb/images/products/large/sans_titre_1-80.png', 'Caffe LATTE beans', 22.00, 1.65, 3, 1),
       ('2023-03-12', 'Amazing product!', 'https://assets.sainsburys-groceries.co.uk/gol/8085502/1/640x640.jpg', 'Caffe MACCHIATO vanilla beans', 14.00, 2.01, 1, 2),
       ('2023-03-12', 'Average product!', 'https://www.dolce-gusto.co.uk/media/catalog/product/cache/d22af66f75f51f60e100631e2c10a99a/s/t/starbucks-sunny-day-blend-americano-box.webp', 'Caffe AMERICANO beans', 32.00, 3.76, 3, 4);