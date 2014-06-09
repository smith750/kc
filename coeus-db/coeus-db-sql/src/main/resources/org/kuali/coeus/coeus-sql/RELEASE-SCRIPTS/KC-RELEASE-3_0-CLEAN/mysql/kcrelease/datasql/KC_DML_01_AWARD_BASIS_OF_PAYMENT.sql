delimiter /
TRUNCATE TABLE AWARD_BASIS_OF_PAYMENT
/
INSERT INTO AWARD_BASIS_OF_PAYMENT (BASIS_OF_PAYMENT_CODE,DESCRIPTION,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES ('1','Firm fixed price','admin',NOW(),UUID(),1)
/
INSERT INTO AWARD_BASIS_OF_PAYMENT (BASIS_OF_PAYMENT_CODE,DESCRIPTION,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES ('2','Cost reimbursement','admin',NOW(),UUID(),1)
/
INSERT INTO AWARD_BASIS_OF_PAYMENT (BASIS_OF_PAYMENT_CODE,DESCRIPTION,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES ('3','Fixed price level of effort','admin',NOW(),UUID(),1)
/
INSERT INTO AWARD_BASIS_OF_PAYMENT (BASIS_OF_PAYMENT_CODE,DESCRIPTION,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES ('4','Other','admin',NOW(),UUID(),1)
/
INSERT INTO AWARD_BASIS_OF_PAYMENT (BASIS_OF_PAYMENT_CODE,DESCRIPTION,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES ('5','Gift','admin',NOW(),UUID(),1)
/
INSERT INTO AWARD_BASIS_OF_PAYMENT (BASIS_OF_PAYMENT_CODE,DESCRIPTION,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES ('6','No Payment','admin',NOW(),UUID(),1)
/
delimiter ;