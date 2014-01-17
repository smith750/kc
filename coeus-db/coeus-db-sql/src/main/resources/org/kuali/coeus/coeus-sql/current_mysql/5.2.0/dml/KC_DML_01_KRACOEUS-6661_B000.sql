DELIMITER /
insert into NARRATIVE_TYPE (NARRATIVE_TYPE_CODE,DESCRIPTION,SYSTEM_GENERATED,ALLOW_MULTIPLE,UPDATE_TIMESTAMP,UPDATE_USER,NARRATIVE_TYPE_GROUP,VER_NBR,OBJ_ID) 
values (144,'PHS_Career_SupportLtrs','N','N',NOW(),'admin','P',1,UUID())
/
INSERT INTO SEQ_VALID_NARR_FORMS_ID VALUES(NULL)
/
insert into VALID_NARR_FORMS (VALID_NARR_FORMS_ID,FORM_NAME,NARRATIVE_TYPE_CODE,MANDATORY,UPDATE_TIMESTAMP,UPDATE_USER,OBJ_ID) 
values ((SELECT (MAX(ID)) FROM SEQ_VALID_NARR_FORMS_ID),'PHS398_CareerDevelopmentAwardSup_2_0',144,null,NOW(),'admin',UUID())
/
INSERT INTO SEQ_VALID_NARR_FORMS_ID VALUES(NULL)
/
insert into VALID_NARR_FORMS (VALID_NARR_FORMS_ID,FORM_NAME,NARRATIVE_TYPE_CODE,MANDATORY,UPDATE_TIMESTAMP,UPDATE_USER,OBJ_ID) 
values ((SELECT (MAX(ID)) FROM SEQ_VALID_NARR_FORMS_ID),'PHS398_CareerDevelopmentAwardSup_2_0',62,null,NOW(),'admin',UUID())
/
INSERT INTO SEQ_VALID_NARR_FORMS_ID VALUES(NULL)
/
insert into VALID_NARR_FORMS (VALID_NARR_FORMS_ID,FORM_NAME,NARRATIVE_TYPE_CODE,MANDATORY,UPDATE_TIMESTAMP,UPDATE_USER,OBJ_ID) 
values ((SELECT (MAX(ID)) FROM SEQ_VALID_NARR_FORMS_ID),'PHS398_CareerDevelopmentAwardSup_2_0',63,null,NOW(),'admin',UUID())
/
INSERT INTO SEQ_VALID_NARR_FORMS_ID VALUES(NULL)
/
insert into VALID_NARR_FORMS (VALID_NARR_FORMS_ID,FORM_NAME,NARRATIVE_TYPE_CODE,MANDATORY,UPDATE_TIMESTAMP,UPDATE_USER,OBJ_ID) 
values ((SELECT (MAX(ID)) FROM SEQ_VALID_NARR_FORMS_ID),'PHS398_CareerDevelopmentAwardSup_2_0',64,null,NOW(),'admin',UUID())
/
INSERT INTO SEQ_VALID_NARR_FORMS_ID VALUES(NULL)
/
insert into VALID_NARR_FORMS (VALID_NARR_FORMS_ID,FORM_NAME,NARRATIVE_TYPE_CODE,MANDATORY,UPDATE_TIMESTAMP,UPDATE_USER,OBJ_ID) 
values ((SELECT (MAX(ID)) FROM SEQ_VALID_NARR_FORMS_ID),'PHS398_CareerDevelopmentAwardSup_2_0',65,null,NOW(),'admin',UUID())
/
INSERT INTO SEQ_VALID_NARR_FORMS_ID VALUES(NULL)
/
insert into VALID_NARR_FORMS (VALID_NARR_FORMS_ID,FORM_NAME,NARRATIVE_TYPE_CODE,MANDATORY,UPDATE_TIMESTAMP,UPDATE_USER,OBJ_ID) 
values ((SELECT (MAX(ID)) FROM SEQ_VALID_NARR_FORMS_ID),'PHS398_CareerDevelopmentAwardSup_2_0',66,null,NOW(),'admin',UUID())
/
INSERT INTO SEQ_VALID_NARR_FORMS_ID VALUES(NULL)
/
insert into VALID_NARR_FORMS (VALID_NARR_FORMS_ID,FORM_NAME,NARRATIVE_TYPE_CODE,MANDATORY,UPDATE_TIMESTAMP,UPDATE_USER,OBJ_ID) 
values ((SELECT (MAX(ID)) FROM SEQ_VALID_NARR_FORMS_ID),'PHS398_CareerDevelopmentAwardSup_2_0',67,null,NOW(),'admin',UUID())
/
INSERT INTO SEQ_VALID_NARR_FORMS_ID VALUES(NULL)
/
insert into VALID_NARR_FORMS (VALID_NARR_FORMS_ID,FORM_NAME,NARRATIVE_TYPE_CODE,MANDATORY,UPDATE_TIMESTAMP,UPDATE_USER,OBJ_ID) 
values ((SELECT (MAX(ID)) FROM SEQ_VALID_NARR_FORMS_ID),'PHS398_CareerDevelopmentAwardSup_2_0',68,null,NOW(),'admin',UUID())
/
INSERT INTO SEQ_VALID_NARR_FORMS_ID VALUES(NULL)
/
insert into VALID_NARR_FORMS (VALID_NARR_FORMS_ID,FORM_NAME,NARRATIVE_TYPE_CODE,MANDATORY,UPDATE_TIMESTAMP,UPDATE_USER,OBJ_ID) 
values ((SELECT (MAX(ID)) FROM SEQ_VALID_NARR_FORMS_ID),'PHS398_CareerDevelopmentAwardSup_2_0',69,null,NOW(),'admin',UUID())
/
INSERT INTO SEQ_VALID_NARR_FORMS_ID VALUES(NULL)
/
insert into VALID_NARR_FORMS (VALID_NARR_FORMS_ID,FORM_NAME,NARRATIVE_TYPE_CODE,MANDATORY,UPDATE_TIMESTAMP,UPDATE_USER,OBJ_ID) 
values ((SELECT (MAX(ID)) FROM SEQ_VALID_NARR_FORMS_ID),'PHS398_CareerDevelopmentAwardSup_2_0',70,null,NOW(),'admin',UUID())
/
INSERT INTO SEQ_VALID_NARR_FORMS_ID VALUES(NULL)
/
insert into VALID_NARR_FORMS (VALID_NARR_FORMS_ID,FORM_NAME,NARRATIVE_TYPE_CODE,MANDATORY,UPDATE_TIMESTAMP,UPDATE_USER,OBJ_ID) 
values ((SELECT (MAX(ID)) FROM SEQ_VALID_NARR_FORMS_ID),'PHS398_CareerDevelopmentAwardSup_2_0',71,null,NOW(),'admin',UUID())
/
INSERT INTO SEQ_VALID_NARR_FORMS_ID VALUES(NULL)
/
insert into VALID_NARR_FORMS (VALID_NARR_FORMS_ID,FORM_NAME,NARRATIVE_TYPE_CODE,MANDATORY,UPDATE_TIMESTAMP,UPDATE_USER,OBJ_ID) 
values ((SELECT (MAX(ID)) FROM SEQ_VALID_NARR_FORMS_ID),'PHS398_CareerDevelopmentAwardSup_2_0',74,null,NOW(),'admin',UUID())
/
INSERT INTO SEQ_VALID_NARR_FORMS_ID VALUES(NULL)
/
insert into VALID_NARR_FORMS (VALID_NARR_FORMS_ID,FORM_NAME,NARRATIVE_TYPE_CODE,MANDATORY,UPDATE_TIMESTAMP,UPDATE_USER,OBJ_ID) 
values ((SELECT (MAX(ID)) FROM SEQ_VALID_NARR_FORMS_ID),'PHS398_CareerDevelopmentAwardSup_2_0',75,null,NOW(),'admin',UUID())
/
INSERT INTO SEQ_VALID_NARR_FORMS_ID VALUES(NULL)
/
insert into VALID_NARR_FORMS (VALID_NARR_FORMS_ID,FORM_NAME,NARRATIVE_TYPE_CODE,MANDATORY,UPDATE_TIMESTAMP,UPDATE_USER,OBJ_ID) 
values ((SELECT (MAX(ID)) FROM SEQ_VALID_NARR_FORMS_ID),'PHS398_CareerDevelopmentAwardSup_2_0',77,null,NOW(),'admin',UUID())
/
INSERT INTO SEQ_VALID_NARR_FORMS_ID VALUES(NULL)
/
insert into VALID_NARR_FORMS (VALID_NARR_FORMS_ID,FORM_NAME,NARRATIVE_TYPE_CODE,MANDATORY,UPDATE_TIMESTAMP,UPDATE_USER,OBJ_ID) 
values ((SELECT (MAX(ID)) FROM SEQ_VALID_NARR_FORMS_ID),'PHS398_CareerDevelopmentAwardSup_2_0',78,null,NOW(),'admin',UUID())
/
INSERT INTO SEQ_VALID_NARR_FORMS_ID VALUES(NULL)
/
insert into VALID_NARR_FORMS (VALID_NARR_FORMS_ID,FORM_NAME,NARRATIVE_TYPE_CODE,MANDATORY,UPDATE_TIMESTAMP,UPDATE_USER,OBJ_ID) 
values ((SELECT (MAX(ID)) FROM SEQ_VALID_NARR_FORMS_ID),'PHS398_CareerDevelopmentAwardSup_2_0',80,null,NOW(),'admin',UUID())
/
INSERT INTO SEQ_VALID_NARR_FORMS_ID VALUES(NULL)
/
insert into VALID_NARR_FORMS (VALID_NARR_FORMS_ID,FORM_NAME,NARRATIVE_TYPE_CODE,MANDATORY,UPDATE_TIMESTAMP,UPDATE_USER,OBJ_ID) 
values ((SELECT (MAX(ID)) FROM SEQ_VALID_NARR_FORMS_ID),'PHS398_CareerDevelopmentAwardSup_2_0',81,null,NOW(),'admin',UUID())
/
INSERT INTO SEQ_VALID_NARR_FORMS_ID VALUES(NULL)
/
insert into VALID_NARR_FORMS (VALID_NARR_FORMS_ID,FORM_NAME,NARRATIVE_TYPE_CODE,MANDATORY,UPDATE_TIMESTAMP,UPDATE_USER,OBJ_ID) 
values ((SELECT (MAX(ID)) FROM SEQ_VALID_NARR_FORMS_ID),'PHS398_CareerDevelopmentAwardSup_2_0',83,null,NOW(),'admin',UUID())
/
INSERT INTO SEQ_VALID_NARR_FORMS_ID VALUES(NULL)
/
insert into VALID_NARR_FORMS (VALID_NARR_FORMS_ID,FORM_NAME,NARRATIVE_TYPE_CODE,MANDATORY,UPDATE_TIMESTAMP,UPDATE_USER,OBJ_ID) 
values ((SELECT (MAX(ID)) FROM SEQ_VALID_NARR_FORMS_ID),'PHS398_CareerDevelopmentAwardSup_2_0',84,null,NOW(),'admin',UUID())
/
INSERT INTO SEQ_VALID_NARR_FORMS_ID VALUES(NULL)
/
insert into VALID_NARR_FORMS (VALID_NARR_FORMS_ID,FORM_NAME,NARRATIVE_TYPE_CODE,MANDATORY,UPDATE_TIMESTAMP,UPDATE_USER,OBJ_ID) 
values ((SELECT (MAX(ID)) FROM SEQ_VALID_NARR_FORMS_ID),'PHS398_CareerDevelopmentAwardSup_2_0',85,null,NOW(),'admin',UUID())
/
INSERT INTO SEQ_VALID_NARR_FORMS_ID VALUES(NULL)
/
insert into VALID_NARR_FORMS (VALID_NARR_FORMS_ID,FORM_NAME,NARRATIVE_TYPE_CODE,MANDATORY,UPDATE_TIMESTAMP,UPDATE_USER,OBJ_ID) 
values ((SELECT (MAX(ID)) FROM SEQ_VALID_NARR_FORMS_ID),'PHS398_CareerDevelopmentAwardSup_2_0',128,null,NOW(),'admin',UUID())
/

DELIMITER ;