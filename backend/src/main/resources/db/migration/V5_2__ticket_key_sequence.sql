-- sequence for ticket key generation
CREATE SEQUENCE ticket_key_seq
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 10;
