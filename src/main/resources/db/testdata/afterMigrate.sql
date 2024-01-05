set foreign_key_checks = 0;

delete from users;

set foreign_key_checks = 1;


INSERT INTO users (user_id, name, email, description, creation_date, update_date)
VALUES
    ('65fefd5b-590d-476b-84f0-716b582d89d0', 'John Doe', 'john.doe@example.com', 'User 1 description', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('25e2a119-f62b-45bb-979d-487d61874b7f', 'Jane Smith', 'jane.smith@example.com', 'User 2 description', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('1763a350-dd93-4efc-9093-bbcee5e6311c', 'Bob Johnson', 'bob.johnson@example.com', 'User 3 description', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
