import java.util.Scanner;
import java.io.*;

public class CMain {
	// Rellenar con los datos de los dos alumnos que presentan la práctica
	static String NombreAlumno1="Alejandro";
	static String ApellidosAlumno1="Garcia Carballo";
	static String NIUAlumno1="1423957"; // NIU alumno1
	static String NombreAlumno2="Michelle";
	static String ApellidosAlumno2="Dominguez Martin";
	static String NIUAlumno2="1426666"; // NIU alumno2

	static String[] NIUS={
        "1420942",
        "1428136",
        "1427702",
        "1393102",
        "1423955",
        "1457749",
        "1424739",
        "1424670",
        "1458234",
        "1423739",
        "1427371",
        "1428733",
        "1461244",
        "1360750",
        "1457227",
        "1459623",
        "1462921",
        "1423210",
        "1458983",
        "1397715",
        "1460678",
        "1401717",
        "1426666",
        "1424818",
        "1424822",
        "1368533",
        "1457309",
        "1358575",
        "1417980",
        "1270092",
        "1428627",
        "1426015",
        "1423957",
        "1425096",
        "1397688",
        "1424348",
        "1397770",
        "1456923",
        "1325626",
        "1393273",
        "1462196",
        "1362857",
        "1457260",
        "1264788",
        "1295920",
        "1306688",
        "1391873",
        "1424310",
        "1424033",
        "1325809",
        "1423837",
        "1424696",
        "1456416",
        "1423191",
        "1360566",
        "1426532",
        "1366098",
        "1459084",
        "1423709",
        "1424350",
        "1368594",
        "1397845",
        "1426737",
        "1459296",
        "1424803",
        "1426841",
        "1391712",
        "1425097",
        "1425785",
        "1424873",
        "1455082",
        "1424408",
        "1390128",
        "1425095",
        "1428260",
        "1391792",
        "1389960",
        "1424821",
        "1425053",
        "1457867",
        "1424718",
        "1425084",
        "1459080",
        "1458353",
        "1424820",
        "1310084",
        "1426805",
        "1370029",
        "1390537",
        "1424671"        
		};

	static boolean NIUCorrecto(String nia) {
		for (int i=0;i<NIUS.length; ++i) if (nia.equals(NIUS[i])) return true;
		return false;
	}

	public static class CSyntaxError extends Exception {
		private static final long serialVersionUID=2001L;
	    public CSyntaxError(String msg) {
	        super(msg);
	    }
	}
	static CInvoicing m_Invoicing;
	static int nLine=0;
	static void ProcesarNuevo(Scanner sl) throws Exception {
		String elemento=sl.next();
		if (elemento.equalsIgnoreCase("Cliente")) {
			String nombre=sl.next();
			int numero= sl.nextInt();
			if (numero<1) throw new Exception("numero de cliente menor que 1 " + numero);
			m_Invoicing.NewClient(new CClient(nombre,numero));
		} 
		else if (elemento.equalsIgnoreCase("Producto")) {
			String nombre=sl.next();
			int codigo= sl.nextInt();
			float precio = sl.nextFloat();
			if (codigo<1) throw new Exception("codigo de producto menor que 1 " + codigo);
			m_Invoicing.NewProduct(new CProduct(nombre,codigo,precio));
		}
		else if (elemento.equalsIgnoreCase("Factura")) {
			int numero=sl.nextInt();
			String nombreCliente= sl.next();
			if (numero<1) throw new Exception("numero de factura menor que 1 " + numero);
			m_Invoicing.NewInvoice(new CInvoice(numero,m_Invoicing.FindClientByName(nombreCliente)));			
		}
		else if (elemento.equalsIgnoreCase("Linea")) {
			int numeroFactura= sl.nextInt();
			String nombreProducto= sl.next();
			int cantidadProducto = sl.nextInt();
			CInvoice factura=m_Invoicing.FindInvoiceByNumber(numeroFactura);
			CProduct producto=m_Invoicing.FindProductByName(nombreProducto);
			m_Invoicing.AddProductoToInvoice(factura, producto,cantidadProducto);			
		}
		else throw new CSyntaxError("Nuevo ...");
	}
	static void ProcesarModificar(Scanner sl) throws Exception {
		String elemento=sl.next();
		if (elemento.equalsIgnoreCase("Cliente")) {
			String nombreCliente=sl.next();
			CClient cliente=m_Invoicing.FindClientByName(nombreCliente);
			String campo=sl.next();
			if (campo.equalsIgnoreCase("Nombre")) {
				String nuevoNombre=sl.next();
				m_Invoicing.UpdateClient(cliente, nuevoNombre, cliente.m_Number);
			}
			else if (campo.equalsIgnoreCase("Numero")) {
				int nuevoNumero=sl.nextInt();
				m_Invoicing.UpdateClient(cliente, cliente.m_Name,nuevoNumero);				
			}
			else throw new CSyntaxError("Modificar Cliente ...");
		} 
		else if (elemento.equalsIgnoreCase("Producto")) {
			String nombreProducto=sl.next();
			CProduct producto=m_Invoicing.FindProductByName(nombreProducto);
			String campo=sl.next();
			if (campo.equalsIgnoreCase("Nombre")) {
				String nuevoNombre=sl.next();
				m_Invoicing.UpdateProduct(producto, nuevoNombre, producto.m_Code, producto.m_Price);
			}
			else if (campo.equalsIgnoreCase("Codigo")) {
				int nuevoCodigo=sl.nextInt();
				m_Invoicing.UpdateProduct(producto, producto.m_Name, nuevoCodigo, producto.m_Price);
			}
			else if (campo.equalsIgnoreCase("Precio")) {
				float nuevoPrecio=sl.nextInt();
				m_Invoicing.UpdateProduct(producto, producto.m_Name, producto.m_Code, nuevoPrecio);
			}
			
			else throw new CSyntaxError("Modificar Producto ...");
		}
		else if (elemento.equalsIgnoreCase("Factura")) {
			int numeroFactura=sl.nextInt();
			CInvoice factura=m_Invoicing.FindInvoiceByNumber(numeroFactura);
			String campo=sl.next();
			if (campo.equalsIgnoreCase("Cliente")) {
				String nuevoCliente=sl.next();
				CClient cliente=m_Invoicing.FindClientByName(nuevoCliente);
				m_Invoicing.UpdateInvoiceHeader(factura,factura.m_Number,cliente);
			}
			else if (campo.equalsIgnoreCase("Numero")) {
				int nuevoNumero=sl.nextInt();
				m_Invoicing.UpdateInvoiceHeader(factura,nuevoNumero,factura.m_Client);
			}
			else throw new CSyntaxError("Modificar Factura ...");
		}		
	}
	static void ProcesarEliminar(Scanner sl) throws Exception {
		String elemento=sl.next();
		if (elemento.equalsIgnoreCase("Cliente")) {
			String nombreCliente=sl.next();
			CClient cliente=m_Invoicing.FindClientByName(nombreCliente);
			m_Invoicing.DeleteClient(cliente);
		} 
		else if (elemento.equalsIgnoreCase("Producto")) {
			String nombreProducto=sl.next();
			CProduct producto=m_Invoicing.FindProductByName(nombreProducto);
			m_Invoicing.DeleteProduct(producto);
		}
		else if (elemento.equalsIgnoreCase("Factura")) {
			int numeroFactura=sl.nextInt();
			CInvoice factura=m_Invoicing.FindInvoiceByNumber(numeroFactura);
			m_Invoicing.DeleteInvoice(factura);
		}		
		else if (elemento.equalsIgnoreCase("Linea")) {
			int numeroFactura= sl.nextInt();
			String nombreProducto= sl.next();
			CInvoice factura=m_Invoicing.FindInvoiceByNumber(numeroFactura);
			CProduct producto=m_Invoicing.FindProductByName(nombreProducto);
			m_Invoicing.DeleteProductFromInvoice(factura, producto);			
		}
		else throw new CSyntaxError("Eliminar ...");
	}
	static void ProcesarVer(Scanner sl)  throws Exception {
		String elemento=sl.next();
		if (elemento.equalsIgnoreCase("Clientes")) {
			System.out.print(nLine + " : Salida : ");
			m_Invoicing.m_Clients.Print(System.out);
			System.out.println();
		} 
		else if (elemento.equalsIgnoreCase("Productos")) {
			System.out.print(nLine + " : Salida : ");
			m_Invoicing.m_Products.Print(System.out);
			System.out.println();
		}
		else if (elemento.equalsIgnoreCase("Facturas")) {
			System.out.print(nLine + " : Salida : ");
			m_Invoicing.m_Invoices.Print(System.out);			
			System.out.println();
		}
		else throw new CSyntaxError("Ver ...");		
	}
	static void ProcesarListado(Scanner sl) throws Exception {
		String elemento=sl.next();
		if (elemento.equalsIgnoreCase("Facturas")) {
			m_Invoicing.ListInvoices();
		}
		else if (elemento.equalsIgnoreCase("Productos")) {
			m_Invoicing.ListProducts();
		}
		else if (elemento.equalsIgnoreCase("Clientes")) {
			m_Invoicing.ListClients();
		}
		else throw new CSyntaxError("Listado ...");	
	}
	public static void main(String[] args) throws Exception {
		System.out.println(NIUAlumno1);
		System.out.println(NombreAlumno1);
		System.out.println(ApellidosAlumno1);
		System.out.println(NIUAlumno2);
		System.out.println(NombreAlumno2);
		System.out.println(ApellidosAlumno2);
		
		if (!NIUCorrecto(NIUAlumno1)) throw new Exception("El NIU " + NIUAlumno1 + " no es de alumno matriculado");
		if (!NIUCorrecto(NIUAlumno2)) throw new Exception("El NIU " + NIUAlumno2 + " no es de alumno matriculado");

		m_Invoicing=new CInvoicing();
		if (args.length!=1) {
			System.out.println("Falta el nombre del fichero de ordendes");
			return;
		}
		String filename=args[0];
		System.out.println("Fichero de ordenes: " + filename);
		try {
			File ordenes=new File(filename);
			Scanner s;
			s = new Scanner(ordenes);
			while (s.hasNextLine()) {
				try {
					++nLine;
					String linea = s.nextLine();
					System.out.println(nLine + " : Linea : " + linea);
					Scanner sl = new Scanner(linea);
					//sl.useDelimiter("\\s*");
					try {
						String orden=sl.next();
						if (orden.equalsIgnoreCase("Nuevo")) ProcesarNuevo(sl);
						else if (orden.equalsIgnoreCase("Nueva")) ProcesarNuevo(sl);
						else if (orden.equalsIgnoreCase("Modificar")) ProcesarModificar(sl);
						else if (orden.equalsIgnoreCase("Eliminar")) ProcesarEliminar(sl);
						else if (orden.equalsIgnoreCase("Ver")) ProcesarVer(sl);
						else if (orden.equalsIgnoreCase("Listado")) ProcesarListado(sl);
						else {
							sl.close();
							throw new CSyntaxError("Orden no reconocida " + linea);
						}
					}
					catch (java.util.NoSuchElementException e) {
						throw new CSyntaxError("Error de sintaxis: " + linea);
					}
				}
				catch (Exception e) {
					System.out.println(nLine + " : Excepcion : "+ e.toString());
					//e.printStackTrace();
				}
				
				catch (AssertionError e)  {
					System.out.println(nLine + " : Assert : "+ e.toString());
					//e.printStackTrace();		
				}
			}
			s.close();
		} 
		catch (Exception e) {
			System.out.println("Excepcion no controlada");
			e.printStackTrace();
		}
	}
}
