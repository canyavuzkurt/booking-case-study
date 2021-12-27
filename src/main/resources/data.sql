INSERT INTO DRIVER (F_NAME, L_NAME) VALUES ('Driver1', 'Driverson');
INSERT INTO DRIVER (F_NAME, L_NAME) VALUES ('Driver2', 'Driverson');
INSERT INTO DRIVER (F_NAME, L_NAME) VALUES ('Driver3', 'Driverson');
INSERT INTO DRIVER (F_NAME, L_NAME) VALUES ('Driver4', 'Driverson');
INSERT INTO DRIVER (F_NAME, L_NAME) VALUES ('Driver5', 'Driverson');

INSERT INTO VEHICLE (DRIVER_ID) VALUES ( 1 );
INSERT INTO VEHICLE (DRIVER_ID) VALUES ( 2 );
INSERT INTO VEHICLE (DRIVER_ID) VALUES ( 3 );
INSERT INTO VEHICLE (DRIVER_ID) VALUES ( 4 );
INSERT INTO VEHICLE (DRIVER_ID) VALUES ( 5 );

INSERT INTO CLEANER (F_NAME, L_NAME, VEHICLE_ID) VALUES ( 'Cleaner1-1', 'Cleanerson', 1 );
INSERT INTO CLEANER (F_NAME, L_NAME, VEHICLE_ID) VALUES ( 'Cleaner1-2', 'Cleanerson', 1 );
INSERT INTO CLEANER (F_NAME, L_NAME, VEHICLE_ID) VALUES ( 'Cleaner1-3', 'Cleanerson', 1 );
INSERT INTO CLEANER (F_NAME, L_NAME, VEHICLE_ID) VALUES ( 'Cleaner1-4', 'Cleanerson', 1 );
INSERT INTO CLEANER (F_NAME, L_NAME, VEHICLE_ID) VALUES ( 'Cleaner1-5', 'Cleanerson', 1 );

INSERT INTO CLEANER (F_NAME, L_NAME, VEHICLE_ID) VALUES ( 'Cleaner2-1', 'Cleanerson', 2 );
INSERT INTO CLEANER (F_NAME, L_NAME, VEHICLE_ID) VALUES ( 'Cleaner2-2', 'Cleanerson', 2 );
INSERT INTO CLEANER (F_NAME, L_NAME, VEHICLE_ID) VALUES ( 'Cleaner2-3', 'Cleanerson', 2 );
INSERT INTO CLEANER (F_NAME, L_NAME, VEHICLE_ID) VALUES ( 'Cleaner2-4', 'Cleanerson', 2 );
INSERT INTO CLEANER (F_NAME, L_NAME, VEHICLE_ID) VALUES ( 'Cleaner2-5', 'Cleanerson', 2 );

INSERT INTO CLEANER (F_NAME, L_NAME, VEHICLE_ID) VALUES ( 'Cleaner3-1', 'Cleanerson', 3 );
INSERT INTO CLEANER (F_NAME, L_NAME, VEHICLE_ID) VALUES ( 'Cleaner3-2', 'Cleanerson', 3 );
INSERT INTO CLEANER (F_NAME, L_NAME, VEHICLE_ID) VALUES ( 'Cleaner3-3', 'Cleanerson', 3 );
INSERT INTO CLEANER (F_NAME, L_NAME, VEHICLE_ID) VALUES ( 'Cleaner3-4', 'Cleanerson', 3 );
INSERT INTO CLEANER (F_NAME, L_NAME, VEHICLE_ID) VALUES ( 'Cleaner3-5', 'Cleanerson', 3 );

INSERT INTO CLEANER (F_NAME, L_NAME, VEHICLE_ID) VALUES ( 'Cleaner4-1', 'Cleanerson', 4 );
INSERT INTO CLEANER (F_NAME, L_NAME, VEHICLE_ID) VALUES ( 'Cleaner4-2', 'Cleanerson', 4 );
INSERT INTO CLEANER (F_NAME, L_NAME, VEHICLE_ID) VALUES ( 'Cleaner4-3', 'Cleanerson', 4 );
INSERT INTO CLEANER (F_NAME, L_NAME, VEHICLE_ID) VALUES ( 'Cleaner4-4', 'Cleanerson', 4 );
INSERT INTO CLEANER (F_NAME, L_NAME, VEHICLE_ID) VALUES ( 'Cleaner4-5', 'Cleanerson', 4 );

INSERT INTO CLEANER (F_NAME, L_NAME, VEHICLE_ID) VALUES ( 'Cleaner5-1', 'Cleanerson', 5 );
INSERT INTO CLEANER (F_NAME, L_NAME, VEHICLE_ID) VALUES ( 'Cleaner5-2', 'Cleanerson', 5 );
INSERT INTO CLEANER (F_NAME, L_NAME, VEHICLE_ID) VALUES ( 'Cleaner5-3', 'Cleanerson', 5 );
INSERT INTO CLEANER (F_NAME, L_NAME, VEHICLE_ID) VALUES ( 'Cleaner5-4', 'Cleanerson', 5 );
INSERT INTO CLEANER (F_NAME, L_NAME, VEHICLE_ID) VALUES ( 'Cleaner5-5', 'Cleanerson', 5 );

# Saturday
INSERT INTO appointment ( end, start) VALUES ( '2021-12-25 10:00:00', '2021-12-25 08:00:00');
INSERT INTO appointment ( end, start) VALUES ( '2021-12-25 11:30:00', '2021-12-25 09:30:00');
INSERT INTO appointment ( end, start) VALUES ( '2021-12-25 14:00:00', '2021-12-25 10:00:00');
INSERT INTO appointment ( end, start) VALUES ( '2021-12-25 17:20:00', '2021-12-25 15:20:00');
INSERT INTO appointment ( end, start) VALUES ( '2021-12-25 20:00:00', '2021-12-25 16:00:00');
INSERT INTO appointment ( end, start) VALUES ( '2021-12-25 21:15:00', '2021-12-25 17:15:00');
INSERT INTO appointment ( end, start) VALUES ( '2021-12-25 22:00:00', '2021-12-25 20:00:00');

INSERT INTO cleaner_appointment (appointment_id, cleaner_id) VALUES (1, 1);
INSERT INTO cleaner_appointment (appointment_id, cleaner_id) VALUES (1, 2);
INSERT INTO cleaner_appointment (appointment_id, cleaner_id) VALUES (1, 3);
INSERT INTO cleaner_appointment (appointment_id, cleaner_id) VALUES (2, 4);
INSERT INTO cleaner_appointment (appointment_id, cleaner_id) VALUES (2, 5);
INSERT INTO cleaner_appointment (appointment_id, cleaner_id) VALUES (3, 6);
INSERT INTO cleaner_appointment (appointment_id, cleaner_id) VALUES (3, 7);
INSERT INTO cleaner_appointment (appointment_id, cleaner_id) VALUES (4, 8);
INSERT INTO cleaner_appointment (appointment_id, cleaner_id) VALUES (4, 9);
INSERT INTO cleaner_appointment (appointment_id, cleaner_id) VALUES (4, 10);
INSERT INTO cleaner_appointment (appointment_id, cleaner_id) VALUES (5, 11);
INSERT INTO cleaner_appointment (appointment_id, cleaner_id) VALUES (5, 12);
INSERT INTO cleaner_appointment (appointment_id, cleaner_id) VALUES (6, 13);
INSERT INTO cleaner_appointment (appointment_id, cleaner_id) VALUES (7, 14);
INSERT INTO cleaner_appointment (appointment_id, cleaner_id) VALUES (7, 15);

# Sunday
INSERT INTO appointment ( end, start) VALUES ( '2021-12-26 10:00:00', '2021-12-26 08:00:00');
INSERT INTO appointment ( end, start) VALUES ( '2021-12-26 11:30:00', '2021-12-26 09:30:00');
INSERT INTO appointment ( end, start) VALUES ( '2021-12-26 14:00:00', '2021-12-26 10:00:00');
INSERT INTO appointment ( end, start) VALUES ( '2021-12-26 17:20:00', '2021-12-26 15:20:00');
INSERT INTO appointment ( end, start) VALUES ( '2021-12-26 20:00:00', '2021-12-26 16:00:00');
INSERT INTO appointment ( end, start) VALUES ( '2021-12-26 21:15:00', '2021-12-26 17:15:00');
INSERT INTO appointment ( end, start) VALUES ( '2021-12-26 22:00:00', '2021-12-26 20:00:00');

INSERT INTO cleaner_appointment (appointment_id, cleaner_id) VALUES (8, 16);
INSERT INTO cleaner_appointment (appointment_id, cleaner_id) VALUES (8, 17);
INSERT INTO cleaner_appointment (appointment_id, cleaner_id) VALUES (9, 18);
INSERT INTO cleaner_appointment (appointment_id, cleaner_id) VALUES (9, 19);
INSERT INTO cleaner_appointment (appointment_id, cleaner_id) VALUES (10, 20);
INSERT INTO cleaner_appointment (appointment_id, cleaner_id) VALUES (11, 21);
INSERT INTO cleaner_appointment (appointment_id, cleaner_id) VALUES (11, 22);
INSERT INTO cleaner_appointment (appointment_id, cleaner_id) VALUES (12, 23);
INSERT INTO cleaner_appointment (appointment_id, cleaner_id) VALUES (13, 24);
INSERT INTO cleaner_appointment (appointment_id, cleaner_id) VALUES (14, 25);