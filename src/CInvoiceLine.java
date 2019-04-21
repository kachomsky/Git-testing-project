import java.io.PrintStream;

public class CInvoiceLine extends CElement{
	public String m_ProductName;
	public int m_ProductQuantity;
	
	public CInvoiceLine(String name, int quantity){
		m_ProductName=name;
		m_ProductQuantity=quantity;
	}
	
	public void Print(PrintStream out) {
		out.print("CInvoiceLine(");
		out.print(m_ProductName);
		out.print(",");
		out.print(m_ProductQuantity);
		out.print(")");
	}
	
	

}
