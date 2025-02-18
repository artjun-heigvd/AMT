-- TODO: exercise 3, sql migration to support a role for staff
CREATE table role(
                     id BIGINT PRIMARY KEY,
                     role VARCHAR(255)
);

CREATE table staff_role(
    users_staff_id INT,
    roles_id BIGINT
)

