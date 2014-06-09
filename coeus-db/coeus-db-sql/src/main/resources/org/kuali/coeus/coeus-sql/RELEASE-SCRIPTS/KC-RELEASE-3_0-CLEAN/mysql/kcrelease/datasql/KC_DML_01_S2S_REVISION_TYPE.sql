delimiter /
TRUNCATE TABLE S2S_REVISION_TYPE
/
INSERT INTO S2S_REVISION_TYPE (S2S_REVISION_TYPE_CODE,DESCRIPTION,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES ('A','Increase Award','admin',NOW(),UUID(),1)
/
INSERT INTO S2S_REVISION_TYPE (S2S_REVISION_TYPE_CODE,DESCRIPTION,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES ('AC','Increase Award & Increase Duration','admin',NOW(),UUID(),1)
/
INSERT INTO S2S_REVISION_TYPE (S2S_REVISION_TYPE_CODE,DESCRIPTION,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES ('AD','Increase Award & Decrease Duration','admin',NOW(),UUID(),1)
/
INSERT INTO S2S_REVISION_TYPE (S2S_REVISION_TYPE_CODE,DESCRIPTION,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES ('B','Decrease Award','admin',NOW(),UUID(),1)
/
INSERT INTO S2S_REVISION_TYPE (S2S_REVISION_TYPE_CODE,DESCRIPTION,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES ('BC','Decrease Award & Increase Duration','admin',NOW(),UUID(),1)
/
INSERT INTO S2S_REVISION_TYPE (S2S_REVISION_TYPE_CODE,DESCRIPTION,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES ('BD','Decrease Award & Decrease Duration','admin',NOW(),UUID(),1)
/
INSERT INTO S2S_REVISION_TYPE (S2S_REVISION_TYPE_CODE,DESCRIPTION,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES ('C','Increase Duration','admin',NOW(),UUID(),1)
/
INSERT INTO S2S_REVISION_TYPE (S2S_REVISION_TYPE_CODE,DESCRIPTION,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES ('D','Decrease Duration','admin',NOW(),UUID(),1)
/
INSERT INTO S2S_REVISION_TYPE (S2S_REVISION_TYPE_CODE,DESCRIPTION,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES ('E','Other(Specify)','admin',NOW(),UUID(),1)
/
delimiter ;