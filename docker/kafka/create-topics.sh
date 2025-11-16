#!/bin/bash

echo "⏳ Waiting for Kafka to be ready..."
sleep 10

echo "📌 Creating Kafka topics..."

kafka-topics --create --topic vendor-events --bootstrap-server kafka:29092 --replication-factor 1 --partitions 3
kafka-topics --create --topic user-events --bootstrap-server kafka:29092 --replication-factor 1 --partitions 3
kafka-topics --create --topic shipment-update --bootstrap-server kafka:29092 --replication-factor 1 --partitions 3
kafka-topics --create --topic rule-violations --bootstrap-server kafka:29092 --replication-factor 1 --partitions 3

echo "✅ Kafka topics created."
