DELIMITER /

CREATE TABLE SUBCONTRACT_COPYRIGHT_TYPE
(
    VER_NBR                 DECIMAL(8,0) DEFAULT 1 NOT NULL
    , COPYRIGHT_TYPE_CODE   VARCHAR(3) NOT NULL
    , DESCRIPTION           VARCHAR(200) NOT NULL
    , UPDATE_TIMESTAMP      DATE NOT NULL
    , UPDATE_USER           VARCHAR(60) NOT NULL
    , OBJ_ID                VARCHAR(36) NOT NULL
)
/

ALTER TABLE SUBCONTRACT_COPYRIGHT_TYPE
ADD CONSTRAINT COPYRIGHT_TYPE_CODE_PK
PRIMARY KEY (COPYRIGHT_TYPE_CODE)
/

DELIMITER ;