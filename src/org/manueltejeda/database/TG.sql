use SuperKinalDB;
 
-- total -----
DELIMITER $$
	create procedure sp_AsignarTotal(in tot decimal(10,2),in factId int)
    begin
		update Facturas
			set total = tot
            where facturaId = factId;
    end$$
DELIMITER ;
 
DELIMITER $$
CREATE Function fn_precioPromocion(proId int) returns decimal(10,2) deterministic
BEGIN
	DECLARE precio int;
    DECLARE i INT DEFAULT 1;
    DECLARE curPromocionId INT;
    DECLARE precioPromocion DECIMAL(10,2);
    DECLARE fechaInicio DATE;
    DECLARE fechaFinalizacion DATE;
    DECLARE productoId INT;
 
    DECLARE cursorPromociones CURSOR FOR
        SELECT promocionId, precioPromocion, fechaInicio, fechaFinalizacion, productoId FROM Promociones;
 
    OPEN cursorPromociones;
 
    precioLoop: LOOP
        FETCH cursorPromociones INTO curPromocionId, precioPromocion, fechaInicio, fechaFinalizacion, productoId;
        IF (i = promocionId) and (productoId = proId) and((CURDATE() between fechaInicio and fechaFinalizacion)) THEN
			SET precio = precioprecioPromocion;
		else
			SET precio = precioVentaUnitario;
        END IF;

 
        IF i = (SELECT COUNT(*) FROM Productos) THEN
            LEAVE precioLoop;
        END IF;
 
        SET i = i + 1;
    END LOOP precioLoop;
 
	return precio;
    CLOSE cursorPromociones;
END $$
DELIMITER ;
 
 
-- funcion calcular total
DELIMITER $$
	create function fn_calcularTotal(factID int) returns decimal(10,2) deterministic
    begin
		declare total decimal(10,2) default 0.0;
        declare precio decimal(10,2);
        declare i int default 1;
        declare curFacturaID, curProductoID int;
        declare cursorDetalleFactura cursor for 
			select DF.facturaId, DF.productoId from DetalleFactura DF;
		open cursorDetalleFactura;
        totalLoop : loop
        fetch cursorDetalleFactura into curFacturaID,curProductoID;
        if (factID = curFacturaId)then
			set precio = (FN_precio(curProductoId));
            set total = total + precio;
        end if;
        if i = 	(select count(*)from DetalleFactura)then
        leave totalLoop;
        end if;
        set i = i + 1;
        end loop totalLoop;
        call sp_asignarTotal(total,factID);
        return total;
    end$$
DELIMITER ;
 
select fn_calcularTotal(9);
select * from facturas;
DELIMITER $$
	create trigger tg_totalFactura
    after insert on DetalleFactura
    for each row
    begin
		declare total decimal(10,2);
        set total = fn_calcularTotal(new.facturaID);
    end$$
DELIMITER ;
 
DELIMITER $$
create trigger tg_restarStock
before insert on detalleFactura
for each row
begin
    if (select P.cantidadStock from productos P where productoId = NEW.productoId) = 0 then
		signal sqlstate'45000'
			set message_text="No hay existencias de ese producto, intentelo mas tarde";
    else
		update Productos p 
		set p.cantidadStock = (p.cantidadStock - 1) where productoId = NEW.productoId;
	end if;
end $$
DELIMITER ;
 
 
DELIMITER $$
create trigger fechaHoraFactura
before insert on DetalleFactura
for each row
begin
	Update Facturas
	set
			fecha = CURDATE(),
            hora = CURTIME()
			where facturaId = New.facturaId;
end $$
DELIMITER ;