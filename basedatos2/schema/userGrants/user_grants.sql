#Create user Backoffice
CREATE USER 'backoffice'@'localhost';
SET PASSWORD FOR 'backoffice'@'localhost' = PASSWORD('1234');

/*PERMISOS DE ESCRITURA*/

GRANT INSERT on uddedb.users to 'backoffice'@'localhost';
GRANT INSERT on uddedb.meters to 'backoffice'@'localhost';
GRANT INSERT on uddedb.rates to 'backoffice'@'localhost';

/*PERMISOS DE LECTURA*/

grant select on uddedb.* to 'backoffice'@'localhost';

/*PERMISOS DE UPDATE*/

grant UPDATE(name, lastname, user_type, username, email, password) on uddedb.users to 'backoffice'@'localhost';
grant UPDATE(number, password) on uddedb.meters to 'backoffice'@'localhost';
grant UPDATE(price, type) on uddedb.rates to 'backoffice'@'localhost';

#Create user client
CREATE USER 'client'@'localhost';
SET PASSWORD FOR 'client'@'localhost' = PASSWORD('12345');

/*PERMISOS DE ESCRITURA: No posee*/

/*PERMISOS DE LECTURA*/
/*SELECT privilege is used to select data in a database object, applies to the entire object, and cannot be granted to specific columns.*/
/*El privilegio SELECT se usa para seleccionar datos en un objeto de base de datos, se aplica a todo el objeto y no se puede otorgar a columnas específicas. **/

grant select on uddedb.measurements to 'client'@'localhost';
grant select on uddedb.invoices to 'client'@'localhost';

/*PERMISOS DE UPDATE*/

grant update(username, password ) on uddedb to 'client'@'localhost';

/*PERMISOS DE DELETE: No posee*/

#Create user Broadcast
CREATE USER 'broadcast'@'localhost';
SET PASSWORD FOR 'broadcast'@'localhost' = PASSWORD('1234');

/*PERMISOS DE ESCRITURA*/

grant insert on uddedb.measurements to 'broadcast'@'localhost';

/* Create user Billing*/
CREATE USER 'billing'@'localhost';
SET PASSWORD FOR 'Billing'@'localhost' = PASSWORD('123456');

/*Permisos solo para usar Store Procedure*/

GRANT SELECT ON uddedb.sp_facturation TO 'Billing'@'localhost';