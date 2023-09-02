CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username TEXT NOT NULL,
    password TEXT NOT NULL,
    role TEXT NOT NULL
);

INSERT INTO users(id, username, password, role)
SELECT 1, 'dani', '$2a$10$EJi5UtvLuYyBGK1IjWQRb.VHq0Cebo4j1TqVfDPeU.U70GWfunI9S', 'ROLE_USER'
WHERE
NOT EXISTS (
SELECT username FROM users WHERE username='dani' and password='$2a$10$EJi5UtvLuYyBGK1IjWQRb.VHq0Cebo4j1TqVfDPeU.U70GWfunI9S'
);