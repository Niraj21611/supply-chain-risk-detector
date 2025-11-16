CREATE TABLE vendor (
                        id UUID PRIMARY KEY,
                        name VARCHAR(200) NOT NULL,
                        address TEXT,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE vendor_user (
                             id UUID PRIMARY KEY,
                             vendor_id UUID NOT NULL,
                             name VARCHAR(200),
                             email VARCHAR(200),
                             role VARCHAR(50),
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE shipment_type (
                               id UUID PRIMARY KEY,
                               vendor_id UUID NOT NULL,
                               name VARCHAR(200),
                               description TEXT
);

CREATE TABLE shipment_rule (
                               id UUID PRIMARY KEY,
                               shipment_type_id UUID NOT NULL,
                               rule_key VARCHAR(200),
                               min_value DOUBLE PRECISION,
                               max_value DOUBLE PRECISION,
                               metadata JSONB
);
