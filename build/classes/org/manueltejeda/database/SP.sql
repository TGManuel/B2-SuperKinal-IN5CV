use  SuperKinalDB;

DELIMITER $$
	create procedure sp_agregarCliente(nom varchar(30),ape varchar(30),tel varchar(15),dir varchar(150), nit varchar(30))
    begin
		insert into Clientes(nombre,apellido,telefono,direccion, nit) values
			(nom,ape,tel,dir, nit);
    end $$
DELIMITER ;
 
call sp_agregarCliente('Juan', 'Pérez', '123456789', 'Calle Principal 123', '1234567890');
call sp_agregarCliente('María', 'García', '987654321', 'Avenida del Sol 456', '9876543210');
call sp_agregarCliente('Pedro', 'López', '1122334455', 'Calle Luna 789', '1122334455');
call sp_agregarCliente('Ana', 'Rodríguez', '678901234', 'Barrio Las Flores 1011', '6789012340');
call sp_agregarCliente('Carlos', 'Sánchez', '234567890', 'Urbanización El Bosque 2021', '2345678900');
-- listar
delimiter $$
create procedure sp_listarClientes()
	begin
		select * from Clientes;
    end $$
delimiter ;
 
 -- call sp_listarClientes();
 
-- eliminar
delimiter $$
create procedure sp_eliminarCliente(cliId int)
	begin
		delete from Clientes
		where clienteId = cliId;
    end $$
delimiter ;
 
-- call sp_eliminarCliente();
-- buscar
delimiter $$
create procedure sp_buscarClientes(cliId int)
	begin 
		select *from Clientes
        where clienteId = cliId;
    end $$
delimiter ;
 
-- call sp_buscarClientes(3);
 
-- editar
delimiter $$
create procedure sp_editarClientes(cliId int,nom varchar (30), ape varchar (30), tel varchar(15), dir varchar(150), nit varchar(30) )
	begin
        update Clientes
			set
            nombre = nom,
            apellido = ape,
            telefono = tel,
            direccion = dir,
            nit = nit
            where clienteId = cliId;			
    end $$
delimiter ;
-- call sp_editarClientes();

DELIMITER $$
create procedure sp_agregarCargo(nomCar varchar(30),desCar varchar(100))
    begin
		insert into Cargos(nombreCargo,descripcionCargo) values
			(nomCar, desCar);
    end $$
DELIMITER ;

 
-- listar
delimiter $$
create procedure sp_listarCargos()
	begin
		select * from Cargos;
    end $$
delimiter ;
 
-- elimiar
delimiter $$
create procedure sp_eliminarCargo(carId int)
	begin
		delete from Cargos
		where cargoId = carId;
    end $$
delimiter ;
 
-- buscar
delimiter $$
create procedure sp_buscarCargo(carId int)
	begin 
		select *from Cargos
        where cargoId = carId;
    end $$
delimiter ;
 
-- editar
delimiter $$
create procedure sp_editarCargos(carId int, nomCar varchar(30), desCar varchar(100)  )
	begin
        update Cargos
			set
            nombreCargo = nomCar,
            descripcionCargo = desCar
            where cargoId = carId;			
    end $$
delimiter ;

DELIMITER $$
	create procedure sp_AgregarEmpleado(nom varchar(30), ape varchar(30), sue decimal (10,2), horE Time, horS time, carId int)
		begin
			insert into Empleados (nombreEmpleado, apellidoEmpleado, sueldo, horaEntrada, horaSalida, cargoId) values 
				(nom, ape, sue, horE, horS, carId);
        end $$
DELIMITER ;
 
-- call  sp_AgregarEmpleado('1', '2', 2.5, '10:10:10', '10:10:10', 1);
 
DELIMITER $$
create procedure sp_ListarEmpleados()
	begin 
		select *
			from Empleados;
    end $$
DELIMITER ;
 
call sp_ListarEmpleados();
 
DELIMITER $$
create procedure sp_EditarEmpleado(empId int, nom varchar(30), ape varchar(30), sue decimal (10,2), horE Time, horS time, carId int)
	begin
		update Empleados
			set nombreEmpleado = nom,
				apellidoEmpleado = ape,
                sueldo = sue,
                horaEntrada = horE,
                horaSalida = horS,
                cargoId = carId
                where empId = empleadoId;
    end $$
DELIMITER ;
 
call  sp_EditarEmpleado(1,'1', '2', 2.5, '10:10:10', '10:10:10', 1);
 
DELIMITER $$
create procedure sp_EliminarEmpleado(empId int)
	begin
		delete from Empleados
			where empId = empleadoId;
    end $$
DELIMITER ;
 
call sp_EliminarEmpleado(2);
 
DELIMITER $$
create procedure sp_BuscarEmpleado(empId int)
	begin
		select *
			from Empleados
            where empleadoId = empId;
    end $$
DELIMITER ;
 
call sp_BuscarEmpleado(3);
 
DELIMITER $$
create procedure sp_AsignarEncargado(empId int, encId int)
	begin
    Update Empleados
		set encargadoId = encId 
			where empId = empleadoId;
    end $$
DELIMITER ;
 
call sp_AsignarEncargado(3,3);

DELIMITER $$
	create procedure sp_agregarDistribuidores(nomDis varchar(30), dirDis varchar(200), nitDis varchar(15), telDis varchar(15), web varchar(50))
    begin
		insert into Distribuidores(nombreDistribuidor,direccionDistribuidor,nitDistribuidor,telefonoDistribuidor,web) values
			(nomDis, dirDis, nitDis, telDis, web);
    end $$
DELIMITER ;
 
-- listar
delimiter $$
create procedure sp_listarDistribuidores()
	begin
		select * from Distribuidores;
    end $$
delimiter ;
 
-- eliminar
delimiter $$
create procedure sp_eliminarDistribuidores(disId int)
	begin
		delete from Distribuidores
		where distribuidorId = disId;
    end $$
delimiter ;
 
-- buscar
delimiter $$
create procedure sp_buscarDistribuidores(disId int)
	begin 
		select *from Distribuidores
        where distribuidorId = disId;
    end $$
delimiter ;
 
-- editar
delimiter $$
create procedure sp_editarDistribuidores(disId int,nomDis varchar(30), dirDis varchar(200), nitDis varchar(15), telDis varchar(15), web varchar(50))
	begin
        update Distribuidores
			set
            nombreDistribuidor = nomDis,
            direccionDistribuidor = dirDis,
            nitDistribuidor = nitDis,
            telefonoDistribuidor = telDis,
            web = web
            where distribuidorId = disId;			
    end $$
delimiter ;
 
 
DELIMITER $$
create procedure sp_agregarCategoriaProducto(nomCat varchar(30), desCat varchar(100))
    begin
		insert into CategoriaProductos(nombreCategoria, descripcionCategoria) values
			(nomCat, desCat);
    end $$
DELIMITER ;
 
-- listar
delimiter $$
create procedure sp_listarCategoriaProductos()
	begin
		select * from CategoriaProductos;
    end $$
delimiter ;
 
-- eliminar
delimiter $$
create procedure sp_eliminarCategoriaProducto(catId int)
	begin
		delete from CategoriaProductos
		where categoriaProductoId = catId;
    end $$
delimiter ;
 
-- buscar
delimiter $$
create procedure sp_buscarCategoriaProducto(catId int)
	begin 
		select *from CategoriaProductos
        where categoriaProductoId = catId;
    end $$
delimiter ;
 
-- Editar
delimiter $$
create procedure sp_editarCategoriaProductos(catId int,nomCat varchar(30), desCat varchar(100))
	begin
        update CategoriaProductos
			set
            nombreCategoria = nomCat,
            descripcionCategoria = desCat
            where categoriaProductosId = catId;			
    end $$
delimiter ;

-- Compras
DELIMITER $$
	create procedure sp_AgregarCompra(fec date, tot decimal(10,2))
    begin
		insert into Compras(fechaCompra.totalCompra) values
			(fec,tot);
    end $$
DELIMITER ;
 
DELIMITER $$
	create procedure sp_ListarCompra()
    begin
		select* from Compras;
    end$$
DELIMITER ;
 
DELIMITER $$
	create procedure sp_EliminarCompra(comId int)
    begin
		delete from Comprars
        where compraId = comId;
    end$$
DELIMITER ;
 
DELIMITER $$
create procedure sp_buscarCompras(comId int)
	BEGIN
		select * 
			from Compras
            where compraId = comId;
	END $$
DELIMITER ;
 
DELIMITER $$
create procedure sp_editarCompra(comId int, fec date, tot decimal(10,2))
	BEGIN
		update Compras
			set
				fechaCompra = fec,
                totalCompra = tot
					where compraId = comId;
	END $$
DELIMITER ;


DELIMITER $$
	create procedure sp_AgregarTicketSoporte(des varchar(250), cliId int, facId int)
		begin
			insert into TicketSoporte (descripcionTicket, estatus, clienteId, facturaId) values 
				(des, 'Recién Creado', cliId, facId);
        end $$
DELIMITER ;

DELIMITER $$
create procedure sp_ListarTicketSoporte()
	begin 
		select TS.ticketSoporteId, TS.descripcionTicket, TS.estatus, 
				CONCAT('ID: ', C.clienteId, ' | ', C.nombre,' ', C.apellido) AS 'cliente',
                TS.facturaId from TicketSoporte TS
		join Clientes C on TS.clienteId = C.clienteId;
    end $$
DELIMITER ;
 
call sp_ListarTicketSoporte();
 
DELIMITER $$
create procedure sp_EditarTicketSoporte(ticId int, des varchar(250), est varchar(30), cliId int)
	begin
		update TicketSoporte
			set descripcionTicket = des,
				estatus = est,
                clienteId = cliId
                where ticId = ticketSoporteId;
    end $$
DELIMITER ;
 
call sp_EditarTicketSoporte(1, 'ppp', 'reciente',1);
 
DELIMITER $$
create procedure sp_EliminarTicketSoporte(ticId int)
	begin
		delete from TicketSoporte
			where ticId = ticketSoporteId;
    end $$
DELIMITER ;
 
call sp_EliminarTicketSoporte(2);
 
DELIMITER $$
create procedure sp_BuscarTicketSoporte(ticId int)
	begin
		select *
			from TicketSoporte
            where ticId = ticketSoporteId;
    end $$
DELIMITER ;
 
call sp_BuscarTicketSoporte(1);

DELIMITER $$
create procedure sp_AgregarFactura(fech date, hor time, cliId int(11), empId int(11), tot decimal(10,2))
begin
insert into Facturas(fecha,hora,clienteId,empleadoId,total)values
(fech,hor,cliId,empId,tot);
end $$
DELIMITER ;
 
-- Listar
DELIMITER $$
create procedure sp_ListarFacturas()
begin
select * from Facturas;
end $$
DELIMITER ;
 
-- Eliminar
DELIMITER $$
create procedure sp_EliminarFactura(facId int)
begin
delete from Facturas where facturaId = facId;
end $$
DELIMITER ;
 
-- Buscar
DELIMITER $$
create procedure sp_BuscarFactura(facId int)
begin
select * from Facturas where facturaId = facId;
 
end $$
DELIMITER ;
 
-- Editar
DELIMITER $$
create procedure sp_EditarFactura(facId int,fech date, hor time, cliId int(11), empId int(11), tot decimal(10,2))
begin
Update Facturas
set
fecha = fech,
            hora = hor,
            clienteId = cliId,
            empleadoId = empId,
            total = tot
where facturaId = facId;
end $$
DELIMITER ;

-- Agregar
DELIMITER $$
create procedure sp_AgregarProducto(in nomP varchar(50),in desP varchar(100),in canS int(11),in preVU decimal(10,2), in preVM decimal(10,2), in preC decimal(10,2),in imgP blob,in disId int(11),in catPId int(11))
begin
insert into Productos(nombreProducto,descripcionProducto,cantidadStock,precioVentaUnitario,precioVentaMayor,precioCompra,imagenProducto,distribuidorId,categoriaProductosId) values
(nom,desP,canS,preVU,preVM,preC,imgP,disId,catPId);
end $$
DELIMITER ;
 
-- Listar
DELIMITER $$
create procedure sp_ListarProducto()
begin
select * from Productos;
end $$
DELIMITER ;
 
 
-- Eliminar
DELIMITER $$
create procedure sp_EliminarProducto(in proId int)
begin
delete from Productos where productoId = proId;
end $$
DELIMITER ;
 
-- Buscar
DELIMITER $$
create procedure sp_BuscarProducto(proId int)
begin
select * from Productos where productoId = proId;
end $$
DELIMITER ;