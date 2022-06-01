CREATE TABLE IF NOT EXISTS `user`
(
    `id`                    UUID DEFAULT RANDOM_UUID() NOT NULL PRIMARY KEY,
    `username`              VARCHAR(200),
    `password`              VARCHAR(200)
);