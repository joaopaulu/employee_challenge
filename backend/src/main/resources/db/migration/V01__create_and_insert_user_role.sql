create table tb_user
(
    id         int not null AUTO_INCREMENT,
    email      varchar(255),
    first_name varchar(255),
    last_name  varchar(255),
    password   varchar(255),
    primary key (id)
);

create table tb_role
(
    id        int not null AUTO_INCREMENT,
    authority varchar(255),
    primary key (id)
);

create table tb_user_role
(
    user_id int not null,
    role_id int not null,
    primary key (user_id, role_id),
    constraint fk_roles foreign key (role_id) references tb_role (id),
    constraint fk_users foreign key (user_id) references tb_user (id)
);


INSERT INTO tb_user (first_name, last_name, email, password)
VALUES ('Admin', 'SYS', 'admin@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');

INSERT INTO tb_role (authority)
VALUES ('ROLE_OPERATOR');
INSERT INTO tb_role (authority)
VALUES ('ROLE_ADMIN');

INSERT INTO tb_user_role (user_id, role_id)
VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id)
VALUES (1, 2);
