ALTER TABLE BUD_FORMULATED_COST_DETAIL 
ADD CONSTRAINT FK_BUD_FORMULATED_COST_DETAIL 
FOREIGN KEY (FORMULATED_TYPE_CODE) 
REFERENCES FORMULATED_TYPE (FORMULATED_TYPE_CODE)
/