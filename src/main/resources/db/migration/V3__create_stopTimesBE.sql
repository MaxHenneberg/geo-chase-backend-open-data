CREATE TABLE t_stop_times (
    id                  INTEGER,
    trip_id             VARCHAR(20)      NOT NULL,
    arrival_time        TIMESTAMP        NOT NULL,
    departure_time      TIMESTAMP        NOT NULL,
    stop_id             VARCHAR(20)      NOT NULL,
    stop_sequence       INTEGER          NOT NULL,
    stop_headsign       VARCHAR(200)     NOT NULL,
    pickup_type         VARCHAR(10)      NOT NULL,
    drop_off_type       VARCHAR(10)      NOT NULL,
    shape_dist_traveled DOUBLE PRECISION NOT NULL
);
ALTER TABLE t_stop_times
    ADD CONSTRAINT c_pk_stop_times PRIMARY KEY (id);
CREATE SEQUENCE s_stop_times_id;
