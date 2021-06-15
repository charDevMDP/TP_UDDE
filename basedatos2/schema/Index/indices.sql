//Creo el indice para recorrer las meters.
CREATE INDEX idxMeterRate ON
meters (number , id_rate) USING HASH;

//Para ver los índices que tiene esta tambal
SHOW INDEX FROM meters

//Elimino el indice
DROP INDEX idxMeterRate ON meters

//Creo el indice para recorrer las mediciones.
CREATE  INDEX idxMeasurement ON measurements(date) USING BTREE;

DROP INDEX idxMeasurement ON measurements


