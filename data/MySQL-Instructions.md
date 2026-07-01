# MySQL docker instructions

### Docker command to run MySQL container:
docker run --name java-2-mysql -e MYSQL_ROOT_PASSWORD=admin -e MYSQL_DATABASE=inventory_db -p 3306:3306 -d mysql:8.0

### Database connection details:
- Host: localhost
- Port: 3306
- Database: inventory_db
- Username: root
- Password: admin

### MySQL properties for JDBC connection:
allowPublicKeyRetrieval = true
useSSL = false

### Backup and restore MySQL database command:
docker exec java-2-mysql mysqldump -u root -padmin inventory_db > backup_loja.sql