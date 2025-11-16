CREATE TABLE shipment (
                          id UUID PRIMARY KEY,
                          vendor_id UUID NOT NULL,
                          shipment_type_id UUID NOT NULL,
                          origin VARCHAR(200),
                          destination VARCHAR(200),
                          expected_delivery TIMESTAMP,
                          status VARCHAR(50)
);

CREATE TABLE shipment_event (
                                id UUID PRIMARY KEY,
                                shipment_id UUID NOT NULL,
                                event_type VARCHAR(100),
                                data JSONB,
                                timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
