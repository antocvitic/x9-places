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
create database x9db default character set utf8;
use x9db;

/* create users table */
create table users (
    userID int not null auto_increment , 
    username varchar(64) character set latin1 not null, 
    passwordHash varchar(1024) character set latin1 not null, 
    email varchar(256) character set latin1 not null, 
    name varchar(64), 
    repLevel int, 
    sessionToken varchar(1024) character set latin1 default null,
    isFBConnected boolean, 
    location varchar(256),
    deleteToken varchar(2056),
    primary key (userID),
    unique (username),
    unique (email)
) Engine=InnoDB;

/*
    Some links on charsets:
    MySQL Specifying Character Sets and Collations: http://dev.mysql.com/doc/refman/5.1/en/charset-syntax.html
    Specified key was too long, utf8: http://bugs.mysql.com/bug.php?id=4541
*/

/* table to manage unique ids for Solr */
create table unique_id_table (
    id long not null
) Engine=InnoDB;

/* insert starting id (this id won't be used, but the next one will */
insert into unique_id_table (id) values (1000);

create table ratings (
    userID int not null,
    venueID varchar(64) character set latin1 not null, 
    rating int not null,
    primary key (userID, venueID)
) Engine=InnoDB;

create table rankings (
    userID int not null,
    reviewID varchar(64) character set latin1 not null, 
    ranking int not null,
    primary key (userID, reviewID)
) Engine=InnoDB;


/* create and grant privliges to x9user */
grant all on x9db.* to 'x9user'@'localhost' identified by 'x9pwd';
flush privileges;

