create table appointment
(
    id    bigint auto_increment
        primary key,
    end   datetime(6) null,
    start datetime(6) null
);

create table cleaner_appointment
(
    appointment_id bigint not null,
    cleaner_id     bigint not null,
    primary key (appointment_id, cleaner_id),
    constraint FK8rtqx4i70ca0dgpwjmnsy9yh7
        foreign key (appointment_id) references appointment (id),
    constraint FKgg5th2pq8rhv6jo727mokl3dg
        foreign key (cleaner_id) references cleaner (id)
);