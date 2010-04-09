/**
 * Usage:
 *  shell$ ./mysql -u root < init_db.sql
 * or
 *  mysql> source init_db.sql
 */
 
/* cleanup from previous dbs */
drop database if exists x9db;

/* ugly workaround, 'drop user if exists' doesn't exist: http://bugs.mysql.com/bug.php?id=15287 */
grant usage on *.* to 'x9user'@'localhost' identified by 'x9pwd';
drop user 'x9user'@'localhost';

/* create database */
create database x9db;
use x9db;

/* create users table */
create table users (
    userID int not null auto_increment , 
    username varchar(64) not null, 
    passwordHash varchar(1024) not null, 
    email varchar(256) not null, 
    name varchar(64), 
    repLevel int, 
    sessionToken varchar(1024) default null,
    isFBConnected boolean, 
    primary key (userID),
    unique (username),
    unique (email)
) Engine=InnoDB;

/* create and grant privliges to x9user */
grant all on x9db.* to 'x9user'@'localhost' identified by 'x9pwd';
flush privileges;

