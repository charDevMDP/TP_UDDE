DROP VIEW IF EXISTS vClientBetween;
CREATE VIEW vClientBetween AS
SELECT
us.id ,us.name AS NameClient, us.surname as SurnameClient, me.number AS MedidorNumber, mea.date AS DateMeasurement, mea.kwh AS consumer_kw, ra.price AS Price FROM users us
INNER JOIN addresses ad ON ad.id_user = us.id INNER JOIN meters me ON me.id_address = ad.id
INNER JOIN meters_for_measurement mfm ON mfm.id_meters = me.id
INNER JOIN measurements mea ON mea.id = mfm.id_measurement
INNER JOIN rates ra ON ra.id = me.id_rate;

SELECT consumer_kw, count(consumer_kw) as Cant_kw from vClientBetween vc where vc.id  = 1 group by consumer_kw order by Cant_kw desc limit 10;

SELECT vc.NameClient AS NameClient, vc.SurnameClient, vc.DateMeasurement, vc.MedidorNumber, vc.consumer_kw AS consumer_kw, vc.Price AS Price from vClientBetween vc where vc.id  = 1 group by consumer_kw order by vc.MedidorNumber desc limit 10;

SELECT *  from vclientbetween vc where vc.id = 1 AND vc.DateMeasurement BETWEEN '2021-06-01' AND '2021-06-15';