import javax.swing.JFrame;

public class TestTablero {
	
	public static void main(String[] args) {
		
		Tablero t = new Tablero();
	
		JFrame app = new JFrame("Tablero");

		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setBounds(0, 0, 410, 430);
		//app.setBounds(0, 0, 1366, 768);
		app.add(t);
		app.setVisible(true);
		
	}	
		
}
