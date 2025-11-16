CREATE TABLE alert (
                       id UUID PRIMARY KEY,
                       shipment_id UUID NOT NULL,
                       rule_id UUID,
                       message TEXT,
                       severity VARCHAR(50),
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
