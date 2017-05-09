#Core

## User
> Feature-00. Autor: Jesús Bernal

> _**Incorporar el manejo de usuarios básicos del TPV**_

###Arquitectura del API
####Arquitectura de paquetes
![](https://github.com/miw-upm/SPRING.tpv/blob/develop/docs/wiki/TPV.Architecture.png)
####Diagrama de clases
![](https://github.com/miw-upm/SPRING.tpv/blob/develop/docs/wiki/TPV.Users.Entities.png)
![](https://github.com/miw-upm/SPRING.tpv/blob/develop/docs/wiki/TPV.Users.Wrappers.png)
####API
* GET /api/v0/admins
 
 >Devuelve la versión del API

 >Rol: ANONYMOUS

* DELETE /api/v0/admins
 
 >Borra toda la BBDD excepto el usuario ´admin´ 

 >Rol: ADMIN

* POST /api/v0/users
 
 >Registra un nuevo usuario de categoría ´MANAGER´ 

 >Body: UserWrapper

 >Rol: ADMIN

* POST /api/v0/customers
 
 >Registra un nuevo cliente de categoría ´CUSTOMER´ 

 >Body: UserWrapper

 >Rol: MANAGER & OPERATOR

* POST /api/v0/tokens
 
 >Obtiene un token asociado a un usuario

 >Header: Authorization: "Basic user:password" (Auth BAsic)

 >return: TokenWrapper

 >Rol: AUTHENTICATED

### Feature-00.1 Front-end
Creación del Front-end mediante AngularJS

### Feature-00.2 Front-end. Refactorización
Reorganización del código para mejorar su estructura

##Entities & Daos
> Feature-01. Autor: Jesús Bernal

> **_Incorporar las entidades básicas del núcleo: facturas, tickets, productos, proveedores..._**

> Las descripción de las ampliaciones serán generales y deberemos especificar con más detalle lo que se va a desarrollar

### Feature-01.1 Entidades
####Diagrama de clases

![](https://github.com/miw-upm/SPRING.tpv/blob/develop/docs/wiki/TPV.Core.Entities.png)

### Feature-01.2 DAOS
Se crearán los DAOS: ArticleDao, EmbroideryDao, InvoiceDao, ProviderDao, TextilePrintingDao, TicketDao y VoucherDao.
Además, para los test, se crea una población de test

##Provider
> Feature-02. Autor: --  --

>_**Permitir la creación, lectura, modificación y borrado de proveedores**_. 

>_**Mostrar una lista de proveedores (id-company) para facilitar la creación de artículos**_

>Se debe realizar en todas las capas: `Front-end >> API >> Controller >> DAOS` y realizando los test oportunos de cada capa

## Product
> Feature-03. Autor: --  --

>_**Permitir la creación, lectura, modificación de productos y borrado**_. Se debe realizar en sus tres variantes: `Artículos `y servicios de `Bordados `o `PrendaImpresa`. La lectura debe ser sencilla, sin búsquedas, mostrando una lista de todos los productos.

>Se debe realizar en todas las capas: `Front-end >> API >> Controller >> DAOS` y realizando los test oportunos de cada capa

## Products Query
> Feature-04. Autor: --  --

>_**Permitir la búsqueda de productos mediante filtros**_. Se debe ofrecer una búsqueda de productos avanzada, mediante filtros, para localizar un producto con rapidez.

>Se debe realizar en todas las capas: `Front-end >> API >> Controller >> DAOS` y realizando los test oportunos de cada capa

## Products Family
> Feature-05. Autor: --  --

>_**Permitir la búsqueda de productos mediante familias**_. Se debe ofrecer una búsqueda de productos avanzada, mediante una organización jerárquica de familia de productos. Un producto puede pertenecer a varias familias. Aquí se deberá aplicar el patrón _Composite_

>Se debe realizar en todas las capas: `Front-end >> API >> Controller >> DAOS` y realizando los test oportunos de cada capa

## Ticket
> Feature-06. Autor: --  --

>_**Permitir la creación, lectura, modificación de tickets**_. La modificación de tickets se permitira reducir la cantidad de compras de productos o eliminarlos del ticket, y además, hacer evolucionar el estado. El resto de aspectos del ticket deben ser invariantes.

>Se debe realizar en todas las capas: `Front-end >> API >> Controller >> DAOS` y realizando los test oportunos de cada capa

## Voucher
> Feature-07. Autor: --  --

>_**Permitir la creación, lectura**_. No se debe permitir el borrado de vales. 

>_**Permitir el consumo de un vale**_. Se debe asegurar previamente que el vale no ha sido ya consumido.

>_**Permitir realizar consultas de vales**_. Se debe ofrecer saber el total de dinero en vales activos.

>Se debe realizar en todas las capas: `Front-end >> API >> Controller >> DAOS` y realizando los test oportunos de cada capa

## Invoice
> Feature-08. Autor: --  --

>_**Permitir la creación y lectura de facturas**_. Se deberá asegurar que el usuario tiene los datos necesarios (dni, nombre y apellidos, dirección completa.

>_**Permitir la modificación y lectura de usuarios**_. 

>Se debe realizar en todas las capas: `Front-end >> API >> Controller >> DAOS` y realizando los test oportunos de cada capa

## Cashier balancing
> Feature-09. Autor: --  --

>_**Permitir el cierre de caja al final del dia**_. Debe crearse también la entidad del cierre de cada día.

>Se debe realizar en todas las capas: `Front-end >> API >> Controller >> DAOS >> Entities` y realizando los test oportunos de cada capa

## Stock
>Feature-10. Autor: --  --

>_**Permitir la creación, lectura, modificación y borrado de alarmas por stock**_. Una alarma es establecer unos valores mínimos (con nivel WARNING y CRITICAL) de un conjunto de artículos, de tal manera, que cuando se alcanza dicho valor, aparece como ventana emergente y se manda por correo la situación.

>También prepara un pedido, teniendo en cuenta el pedido anterior. 

>Se debe realizar en todas las capas: `Front-end >> API >> Controller >> DAOS >> Entities` y realizando los test oportunos de cada capa

## Products service
> Feature-11. Autor: --  --

>_**Permitir el seguimiento de productos de servicios (Bordados, Prenda Impresa o Artículo sin stock)**_. Cuando se cree un producto de tipo servicio, se creará un token y se enviará un correo al cliente con el enlace para que el cliente solo pueda consultar el estado del producto, pudiendo consultar los datos del pedido y el estado en que se encuentra. En este caso, el token se enviará mediante un parámetro de un método GET. Cuando el pedido alcance el estado de CERRADO, se le enviará un email al cliente indicando que ya lo puede recorrer. Dicho token será válido hasta que el producto alcance el estado de COMMIT, a partir del cual el token quedará anulado.

>Se debe realizar en todas las capas: `Front-end >> API >> Controller >> DAOS >> Entities` y realizando los test oportunos de cada capa

## Populate
> Feature-12. Autor: -- --

>_**Permitir poblar las Bases de Datos a partir de un fichero en formato YAML**_. Sólo se podrá realizar con el Rol de ADMIN. En la petición, se le indicará el nombre del fichero.

>Se debe realizar en todas las capas: `Front-end >> API >> Controller >> DAOS >> Entities` y realizando los test oportunos de cada capa

## Token expiration
> Feature-13. Autor: -- --

>_**Aumentar el control de los tokens, estableciendo caducidad de los mismos**_. Se deberá ofrecer una funcionalidad para eliminar los tokens caducados. Lanzar cada noche mediante _Spring Batch_, un proceso de limpieza de tokens caducados

>Se debe realizar en todas las capas: `Front-end >> API >> Controller >> DAOS >> Entities` y realizando los test oportunos de cada capa

## Statistics
> Feature-14. Autor: -- --

>_**Ofrecer un conjunto de estudios estadisticos, mostrando en gráficas, de diferentes estudios sobre las Bases de Datos**_. Tambien se permitirá obtener los datos de forma numérica. Los estudios estadisticos serán abiertos, como ejemplo, se podría estudiar las ventas de un producto a lo largo de un periodo de tiempo, la evolución de ventas en total a lo largo de un periodo...

>Se debe realizar en todas las capas: `Front-end >> API >> Controller >> DAOS >> Entities` y realizando los test oportunos de cada capa

## Pdf creator
> Feature-15. Autor: -- --

>_**A partir de tickets y facturas, crear pdfs**_. Las facturas se crearán para din-A4 y los tickets para papel continuo de 80mm

>Se debe realizar en todas las capas: `Front-end >> API >> Controller >> DAOS >> Entities` y realizando los test oportunos de cada capa