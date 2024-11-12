-- Insert initial data into the 'users' table
INSERT INTO test.user_info (id, name, email)
VALUES (0, 'alice', 'alice@example.com'),
       (1, 'bob', 'bob@example.com')
ON CONFLICT DO NOTHING;
