ALTER TABLE PMC_UNIT_ADMINISTRATOR
  ADD CONSTRAINT FK_PMC_UNIT_ADMINISTRATOR_ID
  FOREIGN KEY (PERSON_MASS_CHANGE_ID) REFERENCES PERSON_MASS_CHANGE (PERSON_MASS_CHANGE_ID)
/