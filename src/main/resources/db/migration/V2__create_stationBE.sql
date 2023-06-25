create table T_STATION
(
    ID INTEGER,
    STATION_ID INTEGER NOT NULL,
    STATION_NAME VARCHAR(200) NOT NULL,
    STATION_LAT DOUBLE precision NOT NULL,
    STATION_LON DOUBLE precision NOT NULL
);
ALTER TABLE T_STATION ADD CONSTRAINT C_PK_STATION PRIMARY KEY (STATION_ID);
CREATE SEQUENCE S_STATION_ID;
