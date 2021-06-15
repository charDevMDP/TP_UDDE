DROP PROCEDURE sp_rate_change;

DELIMITER //
CREATE PROCEDURE sp_rate_change (IN id_rate INTEGER, IN price FLOAT)
BEGIN

    #variables para updatea la factura
    DECLARE id_invoices INTEGER;
	DECLARE consumer_kw FLOAT DEFAULT 0;

	#variables axiliares
	DECLARE cost_sum FLOAT DEFAULT 0;
	DECLARE finished INT DEFAULT 0;

	#declaro el cursor
	DECLARE cur_invoices_owed CURSOR FOR SELECT i.id AS id_invoices, i.consumer_kw AS consumer_kw FROM rates ra
                                    INNER JOIN meters me ON me.id_rate = ra.id
                                    INNER JOIN invoices i ON i.id_meters = me.id
                                    WHERE ra.id = id_rate;


   	#declaro el finalizador del cursor
   	DECLARE CONTINUE HANDLER FOR NOT FOUND SET finished = 1;

    #abro el cursor
    OPEN cur_invoices_owed;
    FETCH cur_invoices_owed INTO id_invoices, consumer_kw;
    WHILE (finished=0) DO

            #se los multiplica el precio de la nueva tarifa por el consumo total de kw_consumidos.
	        SET cost_sum = price * consumer_kw;

            #Se updatea la medicion asignandole el id de la factura
            UPDATE invoices i SET i.total = cost_sum
            WHERE i.id = id_invoices AND i.invoice_status = 'OWED' AND i.consumer_kw > 0;

            FETCH cur_invoices_owed INTO id_invoices, consumer_kw;

    END WHILE;

    #si la tarifa existe le asigno el nuevo precio.
    IF EXISTS(SELECT ra.id FROM rates ra WHERE ra.id = id_rate) THEN
    	BEGIN
	    	UPDATE rates re SET re.price = price WHERE re.id = id_rate;
	  	END;
	END IF;

    CLOSE cur_invoices_owed;

END; //
DELIMITER ;

call sp_rate_change(1,10);