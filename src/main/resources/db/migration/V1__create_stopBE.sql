create table T_STOP
(
    ID INTEGER,
    STOP_ID VARCHAR(20) NOT NULL,
    STOP_STATION INTEGER NOT NULL,
    STOP_NAME VARCHAR(200) NOT NULL,
    STOP_LAT DOUBLE precision NOT NULL,
    STOP_LON DOUBLE precision NOT NULL,
    STOP_URL VARCHAR(200) NOT NULL,
    PLATFORM_CODE VARCHAR(10) NOT NULL
);
ALTER TABLE T_STOP ADD CONSTRAINT C_PK_STOP PRIMARY KEY (STOP_ID);
CREATE SEQUENCE S_STOP_ID;
