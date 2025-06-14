# Remark
## Start up scrit
Although we have `volumes: - ./init:/docker-entrypoint-initdb.d` to let docker run init (create DB) script,
it would only run once when creating docker containers.

I.e. every time a new DB need to be created (by adding init script, the whole container has to be destroyed and to be
recreated again to make it happen)  

## Version
Using Mysql 8.4.X because Flyway is not supporting MySQL 9.X at time of writing.
