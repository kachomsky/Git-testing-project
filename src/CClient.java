import java.io.PrintStream;

public class CClient extends CElement {
	public String m_Name;
	public int m_Number;
	public CClient(String name,int number) {
		m_Name=name;
		m_Number=number;
	}
	public void Print(PrintStream out) {
		out.print("Client(");
		out.print(m_Name);
		out.print(",");
		out.print(m_Number);
		out.print(")");
	}
	
	public void listClient(PrintStream out){
		String maxSpace="                    ";
		String maxSpace2 = "                    ";
		String space = maxSpace.substring(Integer.toString(m_Number).length());
		String space2 = maxSpace2.substring(m_Name.length());
		out.print(m_Number+space+m_Name+space2+"\n");
	}
}

