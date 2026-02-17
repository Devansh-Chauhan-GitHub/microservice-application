# VM Deployment Guide: Microservice Application

Since you have already pushed the code to GitHub, follow these steps to get it running on your VM.

## 1. Prepare the VM (Ubuntu/Debian)

Run these commands on your VM to install **Docker**, **Docker Compose**, and **Git**:

```bash
# Update system
sudo apt update && sudo apt upgrade -y

# Install Docker
sudo apt install docker.io -y
sudo systemctl start docker
sudo systemctl enable docker

# Install Docker Compose V2
sudo apt install docker-compose-v2 -y

# Install Git
sudo apt install git -y

# Add your user to the docker group (to avoid using sudo)
sudo usermod -aG docker $USER
# Log out and log back in for this to take effect!
```

## 2. Clone the Repository

```bash
git clone https://github.com/Devansh-Chauhan-GitHub/microservice-application.git
cd microservice-application
```

## 3. Launch the Application

```bash
docker compose up -d --build
```
*The `-d` flag runs it in the background (detached).*

## 4. Firewall & Cloud Security

Ensure the following ports are **Open/Allowed** in your VM's Security Group or Firewall settings:

| Port | Service | Access Name |
| :--- | :--- | :--- |
| **80** | Frontend | Web Browser |
| **8081** | Auth | API Access |
| **8082** | User | API Access |
| **8083** | Order | API Access |
| **8084** | Payment | API Access |
| **8085** | Notification | API Access |

## 5. Troubleshooting Check

To check if everything is running correctly:
```bash
docker compose ps
docker compose logs -f
```

---
**Your app will be live at: `http://<YOUR_VM_IP_ADDRESS>`**
