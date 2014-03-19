CREATE OR REPLACE VIEW OSP$AWARD_COST_SHARING AS SELECT 
	AWARD_NUMBER MIT_AWARD_NUMBER, 
	SEQUENCE_NUMBER SEQUENCE_NUMBER,
	FISCAL_YEAR FISCAL_YEAR,
	COST_SHARE_PERCENTAGE COST_SHARING_PERCENTAGE, 
	COST_SHARE_TYPE_CODE COST_SHARING_TYPE_CODE,
	SOURCE SOURCE_ACCOUNT, 
	DESTINATION DESTINATION_ACCOUNT, 
	COMMITMENT_AMOUNT AMOUNT,
	UPDATE_TIMESTAMP UPDATE_TIMESTAMP, 
	UPDATE_USER UPDATE_USER
FROM AWARD_COST_SHARE