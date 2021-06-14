DROP PROCEDURE sp_facturation;

DELIMITER //
CREATE PROCEDURE sp_facturation()
BEGIN
	#para recorrer cada medicion
	DECLARE id_meter INTEGER;
	DECLARE id_user INTEGER;
	DECLARE finished INT DEFAULT 0;

	#declaro el cursor
	DECLARE facturation CURSOR FOR SELECT me.id AS 'id_meter', us.id AS 'id_user'
                         FROM meters me
                         INNER JOIN addresses ad ON ad.id = me.id_address
                         INNER JOIN users us ON us.id = ad.id_user
                         GROUP BY me.id ASC;

   	#declaro el finalizador del cursor
   	DECLARE CONTINUE HANDLER FOR NOT FOUND SET finished = 1;

   	#abro el cursor
   	OPEN facturation;
    FETCH facturation INTO id_meter, id_user;
    WHILE (finished=0) DO
	    START TRANSACTION;

			CALL billing_measurement_client(id_meter, id_user);

	        FETCH facturation INTO id_meter, id_user;

	     COMMIT;

    END WHILE;

    CLOSE facturation;
END; //

DELIMITER ;

call sp_facturation();

#PARA REVISAR QUE SEAN CORRECTOS LOS DATOS
select count(mea.id) as measurements_qty, sum(mea.kwh) as total_kw, sum(re.price) * SUM(mea.kwh) as cost_price
from measurements mea
INNER JOIN meters_for_measurement mfm on mfm.id_measurement = mea.id
INNER JOIN meters me ON me.id = mfm.id_meters
INNER JOIN rates re on re.id = me.id_rate
group by me.id asc;