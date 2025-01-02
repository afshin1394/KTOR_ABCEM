#!/bin/bash

# -----------------------------------------------------------------------------
# run_local.sh
#
# This script sets up the local environment by loading environment variables,
# starting necessary services (Redis, Zookeeper, Kafka, PostgreSQL), and
# running the Ktor application.
# -----------------------------------------------------------------------------

set -e  # Exit immediately if a command exits with a non-zero status

# Function to check if a service is running
is_service_running() {
  local service_name=$1
  brew services list | grep "$service_name" | grep "started" > /dev/null
}
# shellcheck disable=SC2046


# Load environment variables from .env.local
if [ -f .env.local ]; then
  # shellcheck disable=SC2046
  export $(grep -v '^#' .env.local | xargs)
  echo "Loaded environment variables from .env.local"
else
  echo ".env.local file not found! Please create it with the necessary environment variables."
  exit 1
fi

# Start Redis if not running
if is_service_running "redis"; then
  echo "Redis is already running."
else
  echo "Starting Redis..."
  brew services start redis
fi

# Start Zookeeper if not running
if is_service_running "zookeeper"; then
  echo "Zookeeper is already running."
else
  echo "Starting Zookeeper..."
  brew services start zookeeper
fi

# Start Kafka if not running
if is_service_running "kafka"; then
  echo "Kafka is already running."
else
  echo "Starting Kafka..."
  brew services start kafka
fi

# Function to check if PostgreSQL service is running
is_service_running() {
  brew services list | grep -q "$1.*started"
}



# Step 1: Initialize the data directory if it doesn't exist
if [ ! -d "$DATA_DIR" ]; then
  echo "Initializing PostgreSQL data directory for $DB_HOST..."
  initdb -D "$DATA_DIR" -E UTF8 --locale=en_US.UTF-8
else
  echo "Data directory already exists at $DATA_DIR."
fi

# Step 2: Start the PostgreSQL server
if pg_ctl -D "$DATA_DIR" status > /dev/null 2>&1; then
  echo "PostgreSQL server is already running."
else
  echo "Starting PostgreSQL server..."
  pg_ctl -D "$DATA_DIR" -l "$LOG_FILE" start
fi

# Wait for PostgreSQL to be ready
echo "Waiting for PostgreSQL to be ready..."
until pg_isready -h localhost -p "$DB_PORT" -U "$DB_ADMIN_USER" > /dev/null 2>&1; do
  echo "PostgreSQL is not ready yet. Waiting..."
  sleep 2
done
echo "PostgreSQL is ready."

# Step 3: Check if the database exists and create it if necessary
echo "Checking if database '$DB_NAME' exists..."
DB_EXISTS=$(psql -h localhost -p "$DB_PORT" -U "$DB_ADMIN_USER" -tAc "SELECT 1 FROM pg_database WHERE datname='$DB_NAME'")

if [ "$DB_EXISTS" = "1" ]; then
  echo "Database '$DB_NAME' already exists."
else
  echo "Creating database '$DB_NAME'..."
  createdb -h localhost -p "$DB_PORT" -U "$DB_ADMIN_USER" "$DB_NAME"
  echo "Database '$DB_NAME' created successfully."
fi



# Step 1: Ensure PostgreSQL server is running
if is_service_running "$DB_HOST"; then
  echo "PostgreSQL service is already running."
else
  echo "Starting PostgreSQL service..."
  brew services start "$DB_HOST"
fi

# Step 2: Wait until PostgreSQL is ready
echo "Waiting for PostgreSQL to be ready..."
until pg_isready -h "$DB_HOST" -p "$DB_PORT" -U "$DB_ADMIN_USER" > /dev/null 2>&1; do
  echo "PostgreSQL is not ready yet. Waiting..."
  sleep 2
done
echo "PostgreSQL is ready."

# Step 3: Create the database and user if they don’t exist
echo "Setting up database and user..."


echo "Database setup complete. PostgreSQL server is running."

# (Optional) Initialize Read-Only Database as a separate connection
# Since both databases are on the same PostgreSQL instance, no additional setup is needed.

# Run the Ktor application
echo "Starting Ktor application..."
# Option 1: Using Gradle Wrapper
./gradlew run &


# Option 2: Using the fat JAR (Uncomment if using)
# java -jar build/libs/my-ktor-app.jar &
# Wait for Ktor application to exit
wait
