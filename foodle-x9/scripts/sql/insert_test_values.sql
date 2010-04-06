use x9db;

insert into users
    (username, passwordHash, email, 
     name, repLevel, isFBConnected)
    values 
    ('abel', '$ASDsBF908127123', 'abel@nonmail.com',
     'Abel', 0, false );

insert into users
    (username, passwordHash, email, 
     name, repLevel, isFBConnected)
    values 
    ('cain', '$897234f8asd7a9sda3', 'cain@nonmail.com',
     'Cain', 0, false );
