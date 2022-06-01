CREATE TABLE IF NOT EXISTS `user_login`
(
    `id`                    UUID DEFAULT RANDOM_UUID() NOT NULL PRIMARY KEY,
    `username`              VARCHAR(200),
    `password`              VARCHAR(200),
    `failed_login_attempts` INT,
    `start_failed_at`       DATETIME,
    `last_failed_at`        DATETIME,
    `login_disabled`        BOOLEAN,
);