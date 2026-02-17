#!/bin/bash

# Function to kill all background processes on script exit
cleanup() {
    echo "Stopping all services..."
    kill $(jobs -p) 2>/dev/null
    exit
}

# Trap SIGINT (Ctrl+C) and call cleanup
trap cleanup SIGINT

# Ensure PostgreSQL is running
if ! systemctl is-active --quiet postgresql; then
  echo "Starting PostgreSQL..."
  sudo systemctl start postgresql
fi

# Create databases if they don't exist (requires sudo/passwordless setup or user intervention previously)
# We assume databases are created as per instructions given to user

echo "Starting Microservices Ecosystem..."

# Create logs directory if it doesn't exist
mkdir -p logs

# Start Auth Service (Port 8081)
echo "Starting Auth Service on port 8081..."
cd auth-service
./mvnw spring-boot:run > ../logs/auth-service.log 2>&1 &
AUTH_PID=$!
cd ..

# Start User Service (Port 8082)
echo "Starting User Service on port 8082..."
cd user-service
./mvnw spring-boot:run > ../logs/user-service.log 2>&1 &
USER_PID=$!
cd ..

# Start Order Service (Port 8083)
echo "Starting Order Service on port 8083..."
cd order-service
./mvnw spring-boot:run > ../logs/order-service.log 2>&1 &
ORDER_PID=$!
cd ..

# Start Payment Service (Port 8084)
echo "Starting Payment Service on port 8084..."
cd payment-service
./mvnw spring-boot:run > ../logs/payment-service.log 2>&1 &
PAYMENT_PID=$!
cd ..

# Start Notification Service (Port 8085)
echo "Starting Notification Service on port 8085..."
cd notification-service
./mvnw spring-boot:run > ../logs/notification-service.log 2>&1 &
NOTIFICATION_PID=$!
cd ..

echo "Waiting 20 seconds for services to initialize..."
sleep 20

# Start Angular Frontend
echo "Starting Angular Frontend..."
cd frontend-app
export NG_CLI_ANALYTICS=false
npm start > ../logs/frontend.log 2>&1 &
FRONTEND_PID=$!
cd ..

echo "All services started!"
echo "Auth Service: http://localhost:8081"
echo "User Service: http://localhost:8082"
echo "Order Service: http://localhost:8083"
echo "Payment Service: http://localhost:8084"
echo "Notification Service: http://localhost:8085"
echo "Frontend: http://localhost:4200"
echo "Logs are being written to the 'logs' directory."
echo "Press Ctrl+C to stop all services."

# Wait indefinitely to keep the script running
wait
