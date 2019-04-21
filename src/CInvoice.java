import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CInvoice extends CElement {
	public int m_Number;
	public CClient m_Client;
	public CProductList m_Products;
	public CInvoiceLineList m_InvoiceLines;
	public Calendar m_Date;
	public CInvoice(int number,CClient client, Calendar date) {
		m_Number=number;
		m_Client=client;
		m_Products=new CProductList();
		m_InvoiceLines=new CInvoiceLineList();
		m_Date = date;
	}
	public void AddProduct(CProduct product, int quantity) {
		m_Products.PushBack(product);
		m_InvoiceLines.PushBack(new CInvoiceLine(product.m_Name,quantity));
	}
	public void DeleteProduct(CProduct product) {
		m_Products.Delete(product);
	}
	public void Print(PrintStream out) {
		out.print("Invoice(");
		out.print(m_Number);
		out.print(",");
		Date data = m_Date.getTime();
		SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
		String fecha = format1.format(data);
		out.print(fecha);
		out.print(",");
		out.print(m_Client.m_Name);
		out.print(",");
		m_InvoiceLines.Print(out);
		out.print(")");
	}
	public float listInvoice(PrintStream out){
		float subtotal = getTotalImport();
		Date data = m_Date.getTime();
		SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
		String fecha = format1.format(data);
		out.print(m_Number);
		//out.print("                   ");
		out.print("                 ");
		out.print(fecha);
		out.print("  ");
		out.print(m_Client.m_Name);
		out.print("                ");
		out.print(subtotal+"\n");
		return subtotal;
	}
	
	public float getTotalImport(){
		float total = 0;
		CList.CNode node = m_InvoiceLines.m_Start;
		CInvoiceLine line = null;
		CProduct product = null;
		while(node!= null){
			line = (CInvoiceLine) node.m_Element;
			product = m_Products.SearchByName(line.m_ProductName);
			total= total + (line.m_ProductQuantity * product.m_Price);
			node = node.m_Next;
		}
		return total;
	}
}
