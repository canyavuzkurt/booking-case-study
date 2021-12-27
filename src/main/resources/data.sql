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

-- INSERT INTO CLEANER (F_NAME, L_NAME, VEHICLE_ID) VALUES ( 'Cleaner3-1', 'Cleanerson', 3 );
-- INSERT INTO CLEANER (F_NAME, L_NAME, VEHICLE_ID) VALUES ( 'Cleaner3-2', 'Cleanerson', 3 );
-- INSERT INTO CLEANER (F_NAME, L_NAME, VEHICLE_ID) VALUES ( 'Cleaner3-3', 'Cleanerson', 3 );
-- INSERT INTO CLEANER (F_NAME, L_NAME, VEHICLE_ID) VALUES ( 'Cleaner3-4', 'Cleanerson', 3 );
-- INSERT INTO CLEANER (F_NAME, L_NAME, VEHICLE_ID) VALUES ( 'Cleaner3-5', 'Cleanerson', 3 );
--
-- INSERT INTO CLEANER (F_NAME, L_NAME, VEHICLE_ID) VALUES ( 'Cleaner4-1', 'Cleanerson', 4 );
-- INSERT INTO CLEANER (F_NAME, L_NAME, VEHICLE_ID) VALUES ( 'Cleaner4-2', 'Cleanerson', 4 );
-- INSERT INTO CLEANER (F_NAME, L_NAME, VEHICLE_ID) VALUES ( 'Cleaner4-3', 'Cleanerson', 4 );
-- INSERT INTO CLEANER (F_NAME, L_NAME, VEHICLE_ID) VALUES ( 'Cleaner4-4', 'Cleanerson', 4 );
-- INSERT INTO CLEANER (F_NAME, L_NAME, VEHICLE_ID) VALUES ( 'Cleaner4-5', 'Cleanerson', 4 );
--
-- INSERT INTO CLEANER (F_NAME, L_NAME, VEHICLE_ID) VALUES ( 'Cleaner5-1', 'Cleanerson', 5 );
-- INSERT INTO CLEANER (F_NAME, L_NAME, VEHICLE_ID) VALUES ( 'Cleaner5-2', 'Cleanerson', 5 );
-- INSERT INTO CLEANER (F_NAME, L_NAME, VEHICLE_ID) VALUES ( 'Cleaner5-3', 'Cleanerson', 5 );
-- INSERT INTO CLEANER (F_NAME, L_NAME, VEHICLE_ID) VALUES ( 'Cleaner5-4', 'Cleanerson', 5 );
-- INSERT INTO CLEANER (F_NAME, L_NAME, VEHICLE_ID) VALUES ( 'Cleaner5-5', 'Cleanerson', 5 );

INSERT INTO appointment ( end, start) VALUES ( '2021-12-26 10:00:00', '2021-12-26 08:00:00');
INSERT INTO appointment ( end, start) VALUES ( '2021-12-25 11:00:00', '2021-12-25 09:00:00');
INSERT INTO appointment ( end, start) VALUES ( '2021-12-26 10:00:00', '2021-12-26 08:00:00');
INSERT INTO appointment ( end, start) VALUES ( '2021-12-27 10:00:00', '2021-12-27 08:00:00');

INSERT INTO cleaner_appointment (appointment_id, cleaner_id) VALUES (1, 1);
INSERT INTO cleaner_appointment (appointment_id, cleaner_id) VALUES (2, 1);
INSERT INTO cleaner_appointment (appointment_id, cleaner_id) VALUES (1, 2);
INSERT INTO cleaner_appointment (appointment_id, cleaner_id) VALUES (2, 2);
INSERT INTO cleaner_appointment (appointment_id, cleaner_id) VALUES (1, 3);
INSERT INTO cleaner_appointment (appointment_id, cleaner_id) VALUES (2, 3);
INSERT INTO cleaner_appointment (appointment_id, cleaner_id) VALUES (3, 4);
INSERT INTO cleaner_appointment (appointment_id, cleaner_id) VALUES (3, 5);
INSERT INTO cleaner_appointment (appointment_id, cleaner_id) VALUES (4, 9);
INSERT INTO cleaner_appointment (appointment_id, cleaner_id) VALUES (4, 10);