DROP PROCEDURE billing_measurement_client;

DELIMITER //

CREATE PROCEDURE billing_measurement_client (IN id_meter INTEGER, IN id_user INTEGER)
BEGIN

    #variables para generar la factura
	DECLARE id_invoices INTEGER;
	DECLARE consumer_kw FLOAT DEFAULT 0;
	DECLARE cost_price FLOAT DEFAULT 0;
	DECLARE measurement_number INTEGER DEFAULT 1;
	DECLARE datee DATETIME;
	DECLARE due_date DATE;

	#variables axiliares
	DECLARE consumer_kw_sum FLOAT DEFAULT 0;
	DECLARE cost_sum FLOAT DEFAULT 0;
	DECLARE id_measurements INTEGER;
	DECLARE finished INT DEFAULT 0;

	#declaro el cursor
	DECLARE cur_invoices CURSOR FOR SELECT mea.id, mea.kwh AS consumer_kw, rt.price AS cost_price FROM measurements mea
	INNER JOIN meters_for_measurement mfm ON mfm.id_measurement = mea.id
	INNER JOIN meters me ON me.id = mfm.id_meters
	INNER JOIN rates rt ON rt.id = me.id_rate WHERE mea.invoiced IS NULL AND me.id = id_meter
	GROUP BY mea.id;


   	#declaro el finalizador del cursor
   	DECLARE CONTINUE HANDLER FOR NOT FOUND SET finished = 1;

    #insertamos una nueva factura, solo con el dato del numero de medidor, fecha, y fecha de vencimiento.
   	CALL sp_due_date_now(@datee, @due_date) ;

    INSERT INTO invoices (id_meters, date_invoice , date_initial, id_user) VALUES (id_meter, @datee, @due_date, id_user);

    #tomo el id de la factura
    SET id_invoices = LAST_INSERT_ID();

    #abro el cursor
   OPEN cur_invoices;
    FETCH cur_invoices INTO id_measurements, consumer_kw, cost_price;
    WHILE (finished=0) DO

            #se suman los datos de las mediciones
	        SET consumer_kw_sum = (consumer_kw + consumer_kw_sum);
	        SET cost_sum = (cost_price + cost_sum) * (consumer_kw);
		SET measurement_number = (measurement_number + 1);

            #Se updatea la medicion asignandole el id de la factura
	        UPDATE measurements mea SET mea.invoiced = id_invoices
	       	WHERE mea.id = id_measurements;

            FETCH cur_invoices INTO id_measurements, consumer_kw, cost_price;

    END WHILE;

    #asigno el total_price, cost_price, measurement_number y el id de usuario a la factura
    UPDATE invoices i SET i.consumer_kw = consumer_kw_sum , i.total = cost_sum, i.number = measurement_number, i.id_user = id_user
   	WHERE i.id =  id_invoices;

    #si la medidor nunca registro mediciones, pongo el estado de la factura en PAGO.
    IF EXISTS(SELECT i.id_meters  FROM invoices i WHERE i.id = id AND i.consumer_kw = 0) THEN
    	BEGIN
	    	UPDATE invoices ii SET ii.invoice_status = "PAID" WHERE ii.id = id_invoices;
	  	END;
	END IF;

    CLOSE cur_invoices;

END; //
DELIMITER ;

