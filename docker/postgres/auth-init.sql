CREATE TABLE auth_user (
                           id UUID PRIMARY KEY,
                           email VARCHAR(200) UNIQUE NOT NULL,
                           password_hash VARCHAR(500) NOT NULL,
                           role VARCHAR(50) NOT NULL,
                           vendor_id UUID NULL,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE refresh_token (
                               token VARCHAR(500) PRIMARY KEY,
                               user_id UUID NOT NULL,
                               expires_at TIMESTAMP NOT NULL
);
