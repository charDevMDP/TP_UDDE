SET GLOBAL event_scheduler = ON;

CREATE EVENT event_generate_invoice
ON SCHEDULE EVERY "1" MONTH
STARTS "2021-07-01 00:00:00"
DO
BEGIN
	CALL sp_facturation();
END;