create table clients
(
    client_id      bigint not null auto_increment,
    name           varchar(255),
    order_order_id bigint,
    primary key (client_id)
);
create table cooks
(
    restaurant_id bigint    not null auto_increment,
    name          varchar(255),
    salary        float(53) not null,
    primary key (restaurant_id)
);
create table ingredients
(
    ingredient_id   bigint not null auto_increment,
    ingredient_name varchar(255),
    primary key (ingredient_id)
);
create table order_to_pizza
(
    order_id bigint not null,
    pizza_id bigint not null
);
create table orders
(
    order_id bigint not null auto_increment,
    primary key (order_id)
);
create table paydesk
(
    paydesk_id   bigint    not null auto_increment,
    availability tinyint check (availability between 0 and 1),
    name         varchar(255),
    salary       float(53) not null,
    primary key (paydesk_id)
);
create table pizza_lsit
(
    pizza_id      bigint not null auto_increment,
    creation_time bigint,
    primary key (pizza_id)
);
create table pizza_lsit_ingredient_list
(
    pizza_pizza_id                bigint not null,
    ingredient_list_ingredient_id bigint not null
);
create table users
(
    id   bigint  not null auto_increment,
    age  integer not null,
    name varchar(255),
    role tinyint check (role between 0 and 1),
    primary key (id)
);
alter table clients
    drop index UK_tmgtq7ivnnpoliw3nh5ff3dpv;

alter table clients
    add constraint UK_tmgtq7ivnnpoliw3nh5ff3dpv unique (order_order_id);

alter table pizza_lsit_ingredient_list
    drop index UK_rpoiyet0jxnptxgwk2etj3y14;

alter table pizza_lsit_ingredient_list
    add constraint UK_rpoiyet0jxnptxgwk2etj3y14 unique (ingredient_list_ingredient_id);

alter table clients
    add constraint FK7w5s4rruusjjjs082732qr350
        foreign key (order_order_id)
            references orders (order_id);

alter table order_to_pizza
    add constraint FKsb3l1789kef8aei0mr2l42i49
        foreign key (pizza_id)
            references pizza_lsit (pizza_id);

alter table order_to_pizza
    add constraint FKtitnbsl403dnvo49b0ffjbgkl
        foreign key (order_id)
            references orders (order_id);

alter table pizza_lsit_ingredient_list
    add constraint FK3qwmkeydtx7ygo8ie81r0u1da
        foreign key (ingredient_list_ingredient_id)
            references ingredients (ingredient_id);

alter table pizza_lsit_ingredient_list
    add constraint FK34b3h5oepr6om2t4r4wi0j8hl
        foreign key (pizza_pizza_id)
            references pizza_lsit (pizza_id);