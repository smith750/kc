delimiter /
TRUNCATE TABLE KRIM_EMP_STAT_T
/
INSERT INTO KRIM_EMP_STAT_T (ACTV_IND,DISPLAY_SORT_CD,EMP_STAT_CD,LAST_UPDT_DT,NM,OBJ_ID,VER_NBR)
  VALUES ('Y','01','A',STR_TO_DATE( '20081113140631', '%Y%m%d%H%i%s' ),'Active','5B97C50B037A6110E0404F8189D85213',1)
/
INSERT INTO KRIM_EMP_STAT_T (ACTV_IND,DISPLAY_SORT_CD,EMP_STAT_CD,LAST_UPDT_DT,NM,OBJ_ID,VER_NBR)
  VALUES ('Y','99','D',STR_TO_DATE( '20081113140631', '%Y%m%d%H%i%s' ),'Deceased','5B97C50B037B6110E0404F8189D85213',1)
/
INSERT INTO KRIM_EMP_STAT_T (ACTV_IND,DISPLAY_SORT_CD,EMP_STAT_CD,LAST_UPDT_DT,NM,OBJ_ID,VER_NBR)
  VALUES ('Y','04','L',STR_TO_DATE( '20081113140632', '%Y%m%d%H%i%s' ),'On Non-Pay Leave','5B97C50B037C6110E0404F8189D85213',1)
/
INSERT INTO KRIM_EMP_STAT_T (ACTV_IND,DISPLAY_SORT_CD,EMP_STAT_CD,LAST_UPDT_DT,NM,OBJ_ID,VER_NBR)
  VALUES ('Y','03','N',STR_TO_DATE( '20081113140632', '%Y%m%d%H%i%s' ),'Status Not Yet Processed','5B97C50B037D6110E0404F8189D85213',1)
/
INSERT INTO KRIM_EMP_STAT_T (ACTV_IND,DISPLAY_SORT_CD,EMP_STAT_CD,LAST_UPDT_DT,NM,OBJ_ID,VER_NBR)
  VALUES ('Y','02','P',STR_TO_DATE( '20081113140632', '%Y%m%d%H%i%s' ),'Processing','5B97C50B037E6110E0404F8189D85213',1)
/
INSERT INTO KRIM_EMP_STAT_T (ACTV_IND,DISPLAY_SORT_CD,EMP_STAT_CD,LAST_UPDT_DT,NM,OBJ_ID,VER_NBR)
  VALUES ('Y','10','R',STR_TO_DATE( '20081113140632', '%Y%m%d%H%i%s' ),'Retired','5B97C50B037F6110E0404F8189D85213',1)
/
INSERT INTO KRIM_EMP_STAT_T (ACTV_IND,DISPLAY_SORT_CD,EMP_STAT_CD,LAST_UPDT_DT,NM,OBJ_ID,VER_NBR)
  VALUES ('Y','97','T',STR_TO_DATE( '20081113140632', '%Y%m%d%H%i%s' ),'Terminated','5B97C50B03806110E0404F8189D85213',1)
/
delimiter ;