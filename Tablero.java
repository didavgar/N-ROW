import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Tablero extends JPanel  {

	private int[][] tablero;
	private int TURNO = 1;
	private boolean NO_GANADOR = true;
	private int NUM_JUGADA = 0;
	
	private final int JUGADOR1 = 1;
	private final int JUGADOR2 = -1;
	private final int ANCHO = 70;
	private final int MARGEN = 20;
		
	Tablero(int n) {
		
		this.tablero = new int[n][n];
		addMouseListener(new MouseHandler());
		
	}
	
	public void Imprimir_Tablero() {
			
		//Imprime tablero
		System.out.println("");
		for(int i = 0; i < this.tablero.length; i++) {
			
			System.out.print(i + 1 + "  ");
			
			for(int j = 0; j < this.tablero.length; j++) {
				
				if(this.tablero[i][j] == 1) System.out.print("X" + "   ");
				else if(this.tablero[i][j] == -1) System.out.print("O" + "   ");
				else System.out.print("-" + "   ");
			}
			
			System.out.println(" ");	
		}
		for(int i = 0; i < this.tablero.length; i++) {
			System.out.print("   " + (i + 1));
		}
		
		System.out.println("\n");
	}

	public boolean Dentro_Tablero(int X, int Y) {
		
		System.out.printf("Pos %d,%d -> ", X, Y);
		
		if( X >= this.MARGEN &&																//Rectangulo superior
			X <= this.MARGEN + this.ANCHO &&
			Y >= this.MARGEN &&
			Y <= this.MARGEN + this.ANCHO * this.tablero.length ||
			
			X >= this.MARGEN + this.ANCHO * (this.tablero.length - 1) && 					//Rectangulo inferior
			X <= this.MARGEN + this.ANCHO * this.tablero.length &&
			Y >= this.MARGEN &&
			Y <= this.MARGEN + this.ANCHO * this.tablero.length || 
			
			Y >= this.MARGEN &&																//Rectangulo izquierdo
			Y <= this.MARGEN + this.ANCHO &&
			X >= this.MARGEN &&
			X <= this.MARGEN + this.ANCHO * this.tablero.length ||
			
			Y >= this.MARGEN + this.ANCHO * (this.tablero.length - 1) &&					//Rectangulo derecho
			Y <= (this.MARGEN + this.ANCHO * this.tablero.length) &&
			X >= this.MARGEN &&
			X <= this.MARGEN + this.ANCHO * this.tablero.length) {
			


			return true;
		}
		System.out.print("(Casilla no valida)\n");
		return false;
	}
	
	public int[] Captura_Posicion(int X, int Y) {
		
		//Restar margen
		X -= this.MARGEN;
		Y -= this.MARGEN;
		
		//Dividir entre el tamaño del cuadrado para saber que posicion clickeo
		X /= this.ANCHO;
		Y /= this.ANCHO;
		
		//int v[] = {X, Y};
		//return v;
		return new int[] {Y, X};
	}
		
	public boolean Posicion_Valida(int[] posicion) {
		
		System.out.printf("Fila:%d Columna:%d ", posicion[0] + 1, posicion[1] + 1);
		
		if( this.tablero[posicion[0]][posicion[1]] == 0 || 					//La posicion sera valida si la casilla esta vacia 
			this.tablero[posicion[0]][posicion[1]] == this.TURNO ) { 		//o la casilla coincide con el turno del jugador
			
			System.out.print("(Casilla válida)\n");
			return true;
		}
		
		JOptionPane.showMessageDialog(null, "Posicion no valida");
		System.out.print("(Casilla No Válida)\n");
		return false;	
	}

	public void Turno() {
		//Este metodo hay que llamarlo al final de la jugada para poder usar la variable TURNO 	
		
		if( this.TURNO == 1 ) this.TURNO = this.JUGADOR2;
		else this.TURNO = this.JUGADOR1;
		
		this.NUM_JUGADA++;
	}
	
	
	//Movimientos en el tablero
	
	public void Abajo(int[] posicion){
		
		for(int i = posicion[0]; i >= 1; i--) {
			this.tablero[i][posicion[1]] = this.tablero[i - 1][posicion[1]];
		}
		
		this.tablero[0][posicion[1]] = this.TURNO;
		
	}
	
	public void Arriba(int[] posicion){
		
		for(int i = posicion[0]; i < this.tablero.length - 1; i++) {
			this.tablero[i][posicion[1]] = this.tablero[i + 1][posicion[1]];
		}
		
		this.tablero[this.tablero.length - 1][posicion[1]] = this.TURNO;
		
	}
		
	public void Izquierda(int[] posicion) {
		
		for(int i = posicion[1]; i < this.tablero.length - 1; i++) {
			this.tablero[posicion[0]][i] = this.tablero[posicion[0]][i + 1];
		}
		
		this.tablero[posicion[0]][this.tablero.length - 1] = this.TURNO;
	}
	
	public void Derecha(int[] posicion) {
		
		for(int i = posicion[1]; i >= 1; i--) {
			this.tablero[posicion[0]][i] = this.tablero[posicion[0]][i - 1];
		}
		
		this.tablero[posicion[0]][0] = this.TURNO;
	}
	
	public boolean Direccion_Valida(String direccion, int[] posicion) {
		
		if( direccion.equals("A") ) {
			
			if( posicion[0] == 0 ||								//Primera fila
					
				posicion[1] == 0 &&								//Columna de la izquierda menos la ultima fila
			    posicion[0] < this.tablero.length - 1 ||
			
			    posicion[1] == this.tablero.length - 1 &&		//Columna de la derecha menos la ultima fila
			    posicion[0] < this.tablero.length - 1 ) {
				
									
				Arriba(posicion);								//Desplaza la columna indicada hacia arriba
				System.out.println("Dirección ARRIBA " + this.TURNO);
				return true;
			}
			
			System.out.println("Dirección INCORRECTA " + this.TURNO);
			return false;
		}
		
		else if( direccion.equals("B") ) {
			
			if( posicion[0] == this.tablero.length - 1 ||		//Ultima fila
				
				posicion[1] == 0 &&								//Columna de la izquierda menos la primera fila
				posicion[0] > 0	 ||
				
				posicion[1] == this.tablero.length - 1 &&		//Columna de la derecha menos la primera fila
				posicion[0] > 0 ) {
				
				Abajo(posicion);								//Desplaza la columna indicada hacia abajo
				System.out.println("Dirección ABAJO " + this.TURNO);
				return true;	
			}
			
			System.out.println("Dirección INCORRECTA " + this.TURNO);
			return false;
		}
		
		else if( direccion.equals("I") ) {
			
			if( posicion[1] == 0 ||						 	    //Primera columna
				
				posicion[0] == 1 &&
				posicion[1] < this.tablero.length - 1 ||		//Primera fila menos la ultima columna
				
				posicion[0] == this.tablero.length - 1 &&		//Ultima fila menos la ultima columna
				posicion[1] < this.tablero.length - 1 ) {
				
				Izquierda(posicion);
				System.out.println("Dirección IZQUIERDA " + this.TURNO);
				return true;
			}
			
			System.out.println("Dirección INCORRECTA " + this.TURNO);
			return false;
		}
		
		else if( direccion.equals("D") ) {
			
			if( posicion[1] == this.tablero.length - 1 ||		//Ultima columna
					
				posicion[0] == 0 &&								//Primera fila menos la primera columna
				posicion[1] > 0  ||
				
				posicion[0] == this.tablero.length - 1 &&		//Ultima fila menos la primera columna
				posicion[1] > 0 ) {
				
				Derecha(posicion);
				System.out.println("Dirección DERECHA " + this.TURNO);
				return true;
			}
			
			System.out.println("Dirección INCORRECTA " + this.TURNO);
			return false;
		}
		
		System.out.println("Dirección INCORRECTA " + this.TURNO);
		return false;
	}
	
	public void Reiniciar() {
		
		//Reinicia el juego colocando el trablero a 0
		for(int i = 0; i < this.tablero.length; i++) {
			for(int j = 0; j < this.tablero.length; j++) {
				this.tablero[i][j] = 0;
			}
		}
		//Ajusta el turno al jugador 1 (rojo)
		this.TURNO = this.JUGADOR1;
	}
	
	public boolean[] No_Ganador() {
		
		boolean no_ganador_1 = true;
		boolean no_ganador_2 = true;
		
		int diagonal_1 = 0;
		int diagonal_2 = 0;
		
		for(int i = 0, k = this.tablero.length - 1; i < this.tablero.length && k >= 0; i++, k--) {
			
			int horizontal = 0;
			int vertical = 0;
			
			for(int j = 0; j < this.tablero.length; j++) {
				//System.out.printf("%d %d %d \n", i, k, j);
		
				horizontal += this.tablero[i][j];
				vertical += this.tablero[j][i];	
			}
			//System.out.printf("Fila %d: %d   Columna %d: %d \n", i + 1, horizontal, i + 1, vertical);
			
			//Comprobamos que el JUGADOR_1 no tenga n en raya ni en la horizontal ni el la diagonal
			if( horizontal == this.tablero.length || vertical == this.tablero.length ) no_ganador_1 = false;
			
			//Comprobamos que el JUGADOR_2 no tenga n en raya ni el la horizonal ni en la diagonal
			if( horizontal == this.tablero.length * -1 || vertical == this.tablero.length * -1 ) no_ganador_2 = false;	
			
			//System.out.printf("%d %d\n",i,m.length - 1 - k);
			//System.out.printf("%d %d\n", k, i);
			
			diagonal_1 += this.tablero[i][this.tablero.length - 1 - k]; 		//Diagonal principal
			diagonal_2 += this.tablero[k][i]; 									//Diagonal secundaria
		}
		
		if(diagonal_1 == this.tablero.length || diagonal_2 == this.tablero.length) no_ganador_1 = false;
		if( diagonal_1 == this.tablero.length * -1 || diagonal_2 == this.tablero.length * -1 ) no_ganador_2 = false;
		
		return new boolean[] {no_ganador_1, no_ganador_2};	
	}
	
	public boolean Determinar_Resultado(boolean[] resultado) {
		
		if(resultado[0] == false && resultado[1] == false) {
			if(this.TURNO == 1) System.out.print("El jugador rojo ha perdido");
			/////////////////////////////////////////
		}
		else if(resultado[0] == true && resultado[1] == true) {
			return this.NO_GANADOR;
		}
		
		this.NO_GANADOR = false;
		return this.NO_GANADOR;
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		int MARGEN_sup = this.MARGEN;
		for(int i = 0; i < this.tablero.length; i++) {
			
			int MARGEN_izq = this.MARGEN;
			for(int j = 0; j < this.tablero.length; j++){
				 
				g.setColor(Color.BLACK);
				g.drawRect(MARGEN_izq, MARGEN_sup, this.ANCHO, this.ANCHO);		//Dibuja tablero
				
				switch( this.tablero[i][j] ) {
					
					case JUGADOR1: 
					
						g.setColor(Color.RED);
						g.fillOval(MARGEN_izq, MARGEN_sup, this.ANCHO, this.ANCHO);
						break;
					
					case JUGADOR2:
					
						g.setColor(Color.BLUE);
						g.fillOval(MARGEN_izq, MARGEN_sup, this.ANCHO, this.ANCHO);
						break;
					
					default:
						
						break;
					
				}
				MARGEN_izq += this.ANCHO;
			}
			MARGEN_sup += this.ANCHO;
		}
	}	
	
	private class MouseHandler extends MouseAdapter {	
		public void mouseClicked (MouseEvent e) {
			//JOptionPane.showMessageDialog(null, String.format("Ratón %d,%d \n",e.getX(),e.getY()));
			
			int X = e.getX();
			int Y = e.getY();
			
			int[] posicion = Captura_Posicion(X, Y);
			
			//Comprobamos que se clickeo dentro del tablero y si este se produjo en una posicion correcta
			if( Dentro_Tablero(X, Y) && Posicion_Valida( posicion ) ) {
			
				//Mientras no haya ganador se siguie jugando
				if( Determinar_Resultado( No_Ganador() ) ) {
						
					//Después habría que solicitar la dirección
					String direccion = JOptionPane.showInputDialog("Direccion: (A)rriba - a(B)ajo - (I)zquierda - (D)erecha");
						
					//Mientras la direccion no sea valida se sigue solicitando hasta que sea correcta
					while( ! Direccion_Valida(direccion, posicion) ) {
							
						direccion = JOptionPane.showInputDialog("Direccion: (A)rriba - a(B)ajo - (I)zquierda - (D)erecha");
					}
						
					//Seguramente habrá que repintar el tablero si se realizó una jugada válida
					repaint();
						
					//Cambia el turno del jugador
					Turno();	
				}
				else{
					//Victoria para algun jugador
					JOptionPane.showMessageDialog(null,"Victoria para");
					Reiniciar();
				}
			}
			
			Imprimir_Tablero();
			//Si clickea fuera del tablero no hace nada
		}		
	}
}