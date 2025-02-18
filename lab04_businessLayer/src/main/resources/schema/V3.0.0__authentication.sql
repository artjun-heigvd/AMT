-- Description: This script creates the tables for the authentication module
-- Authors: Rachel Tranchida, Edwin Häffner, Arthur Junod, Eva Ray

CREATE table role(
                     id BIGINT PRIMARY KEY,
                     role VARCHAR(255)
);

CREATE table staff_role(
    users_staff_id INT,
    roles_id BIGINT
)

