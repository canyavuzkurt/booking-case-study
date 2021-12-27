create table cleaner
(
    id         bigint auto_increment
        primary key,
    f_name     varchar(255) null,
    l_name     varchar(255) null,
    vehicle_id bigint       null
);

create index FKxw5dq5abq8c4ji8shalt2eva
    on cleaner (vehicle_id);

create table driver
(
    id     bigint auto_increment
        primary key,
    f_name varchar(255) null,
    l_name varchar(255) null
);

create table vehicle
(
    id        bigint auto_increment
        primary key,
    driver_id bigint null,
    constraint FKdpor9ohov2f3optwe7twe49tt
        foreign key (driver_id) references driver (id)
);
