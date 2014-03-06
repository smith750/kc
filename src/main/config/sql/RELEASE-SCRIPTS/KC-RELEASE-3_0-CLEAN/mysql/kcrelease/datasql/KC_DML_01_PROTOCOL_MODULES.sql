delimiter /
TRUNCATE TABLE PROTOCOL_MODULES
/
INSERT INTO PROTOCOL_MODULES (PROTOCOL_MODULE_CODE,DESCRIPTION,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES ('001','General Info','admin',NOW(),UUID(),1)
/
INSERT INTO PROTOCOL_MODULES (PROTOCOL_MODULE_CODE,DESCRIPTION,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES ('002','Protocol Personnel','admin',NOW(),UUID(),1)
/
INSERT INTO PROTOCOL_MODULES (PROTOCOL_MODULE_CODE,DESCRIPTION,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES ('004','Areas of Research','admin',NOW(),UUID(),1)
/
INSERT INTO PROTOCOL_MODULES (PROTOCOL_MODULE_CODE,DESCRIPTION,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES ('006','Subjects','admin',NOW(),UUID(),1)
/
INSERT INTO PROTOCOL_MODULES (PROTOCOL_MODULE_CODE,DESCRIPTION,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES ('007','Special Review','admin',NOW(),UUID(),1)
/
INSERT INTO PROTOCOL_MODULES (PROTOCOL_MODULE_CODE,DESCRIPTION,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES ('008','Add/Modify Attachments','admin',NOW(),UUID(),1)
/
INSERT INTO PROTOCOL_MODULES (PROTOCOL_MODULE_CODE,DESCRIPTION,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES ('016','Protocol References','admin',NOW(),UUID(),1)
/
INSERT INTO PROTOCOL_MODULES (PROTOCOL_MODULE_CODE,DESCRIPTION,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES ('017','Protocol Organizations','admin',NOW(),UUID(),1)
/
INSERT INTO PROTOCOL_MODULES (PROTOCOL_MODULE_CODE,DESCRIPTION,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES ('023','Others','admin',NOW(),UUID(),1)
/
INSERT INTO PROTOCOL_MODULES (PROTOCOL_MODULE_CODE,DESCRIPTION,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES ('024','Funding Source','admin',NOW(),UUID(),1)
/
INSERT INTO PROTOCOL_MODULES (PROTOCOL_MODULE_CODE,DESCRIPTION,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES ('025','Protocol Permissions','admin',NOW(),UUID(),1)
/
delimiter ;