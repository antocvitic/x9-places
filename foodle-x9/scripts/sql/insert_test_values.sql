use x9db;

insert into users
    (username, passwordHash, email, 
     name, repLevel, sessionToken, isFBConnected)
    values 
    ('abel', '$2a$10$4JtOPG1Ger19Ht4sy65EEuSGtinXNYSxm7GhvjpoE0jVyRBXdjLtC', /* pwd = leba */
     'abel@nonmail.com','Abel', 0, 'hejabel', false );

insert into users
    (username, passwordHash, email, 
     name, repLevel, sessionToken, isFBConnected)
    values 
    ('cain', '$2a$10$D.Z9RxE4vJlClp2d1IepOezsZqEv1ftqRYnXerCY.PJXkvl9ikMiC', /* pwd = niac */
     'cain@nonmail.com', 'Cain', 0, 'hejcain', false );
