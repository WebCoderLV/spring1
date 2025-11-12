# PostgreSQL Setup Guide for Production Droplet

## 1. Install PostgreSQL (if not installed)
```bash
# Ubuntu/Debian
sudo apt update
sudo apt install postgresql postgresql-contrib

# CentOS/RHEL/Rocky Linux
sudo dnf install postgresql-server postgresql-contrib
sudo postgresql-setup --initdb
```

## 2. Start and Enable PostgreSQL
```bash
sudo systemctl start postgresql
sudo systemctl enable postgresql
sudo systemctl status postgresql
```

## 3. Fix Authentication Issues

### Check PostgreSQL version and config location:
```bash
sudo -u postgres psql -c "SHOW config_file;"
```

### Edit pg_hba.conf for password authentication:
```bash
# Find and edit the config file (replace with your actual path)
sudo nano /etc/postgresql/16/main/pg_hba.conf

# Change these lines:
# FROM:
local   all             postgres                                peer
local   all             all                                     peer

# TO:
local   all             postgres                                md5  
local   all             all                                     md5

# Also ensure these lines exist for network connections:
host    all             all             127.0.0.1/32            md5
host    all             all             ::1/128                 md5
```

### Restart PostgreSQL:
```bash
sudo systemctl restart postgresql
```

## 4. Set Up Database and User

### Set password for postgres user:
```bash
sudo -u postgres psql
ALTER USER postgres PASSWORD 'YourSecurePassword123!';
\q
```

### Create application database and user:
```bash
sudo -u postgres psql
CREATE DATABASE spelite;
CREATE USER spelite_user WITH PASSWORD 'SecureAppPassword123!';
GRANT ALL PRIVILEGES ON DATABASE spelite TO spelite_user;
GRANT ALL PRIVILEGES ON SCHEMA public TO spelite_user;
\q
```

## 5. Test Connection
```bash
# Test with postgres user
psql -U postgres -h localhost -d spelite

# Test with application user  
psql -U spelite_user -h localhost -d spelite
```

## 6. Configure Firewall (if needed)
```bash
# Allow PostgreSQL port (only if connecting from external sources)
sudo ufw allow 5432/tcp
```

## 7. Update Spring Boot Configuration
Update your start-prod.sh script with:
```bash
export DB_URL="jdbc:postgresql://localhost:5432/spelite"
export DB_USERNAME="spelite_user"  # or "postgres"
export DB_PASSWORD="SecureAppPassword123!"  # your actual password
```

## 8. Security Best Practices
- Use strong passwords
- Create dedicated application user (not postgres)
- Limit database privileges to what's needed
- Keep PostgreSQL updated
- Monitor logs: `sudo tail -f /var/log/postgresql/postgresql-*.log`

## Troubleshooting
- Check PostgreSQL status: `sudo systemctl status postgresql`
- View logs: `sudo journalctl -u postgresql`
- Test local connection: `sudo -u postgres psql`
- Check listening ports: `sudo netstat -tulpn | grep 5432`