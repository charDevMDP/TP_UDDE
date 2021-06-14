DELIMITER //
CREATE PROCEDURE sp_due_date_now (OUT datee DATETIME, OUT due_date DATE)
BEGIN
	DECLARE date_now DATETIME;

	DECLARE due DATE;

	SET date_now = NOW();

	SET datee = date_now;

	SELECT DATE_ADD(CAST(date_now AS DATE), INTERVAL 15 DAY) INTO due;

	SET due_date = due;

END; //

DELIMITER ;