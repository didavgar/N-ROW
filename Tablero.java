import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Tablero extends JPanel  {

	private int[][] tablero;
	private int TURNO = 1;
	private boolean GANADOR = false;
	
	private final int JUGADOR1 = 1;
	private final int JUGADOR2 = -1;
	private final int ANCHO = 70;
	private final int MARGEN = 20;
		
	
	//Constructores
	
	Tablero() {
		//Sin parámetro
		this.tablero = new int[5][5];
		addMouseListener(new MouseHandler());
		
	}
	
	Tablero(int n){
		//Validamos el tamaño del tablero
		if(n > 0) {
			this.tablero = new int[n][n];
			addMouseListener(new MouseHandler());
		}
	}
	
	
	//Set y get de los atributos
	
	public boolean get_Ganador() {
		return this.GANADOR;
	}
	
	public void set_Ganador(boolean ganador) {
		this.GANADOR = ganador;
	}
	
	public void set_Turno(int turno) {
		this.TURNO = turno;
	}
	
	public int get_Turno() {
		return this.TURNO;
	}
	
	
	
	//Validacion del click
	
	public boolean Dentro_Tablero(int X, int Y) {
		
		System.out.printf("Pos %d,%d -> ", X, Y);
		
		//Comprobamos si las coordenadas se produjeron en una región correcta del tablero
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
		
		int v[] = {Y, X};
		return v;
	}
		
	public boolean Posicion_Valida(int[] posicion) {
		
		System.out.printf("Fila:%d Columna:%d ", posicion[0] + 1, posicion[1] + 1);
		
		if( this.tablero[posicion[0]][posicion[1]] == 0 || 					//La posición sera valida si la casilla esta vacia 
			this.tablero[posicion[0]][posicion[1]] == this.get_Turno() ) { 		//o la casilla coincide con el turno del jugador
			
			System.out.print("(Casilla válida)\n");
			return true;
		}
		
		JOptionPane.showMessageDialog(null, "Posicion no valida");
		System.out.print("(Casilla No Válida)\n");
		return false;	
	}


	
	//Movimientos en el tablero
	
	public boolean Dirección_Valida(String direccion, int[] posicion) {
		
		//direccion arriba
		if( direccion.equals("A") || direccion.equals("a") ) {
			
			if( posicion[0] == 0 ||								//Primera fila
					
				posicion[1] == 0 &&								//Columna de la izquierda menos la ultima fila
			    posicion[0] < this.tablero.length - 1 ||
			
			    posicion[1] == this.tablero.length - 1 &&		//Columna de la derecha menos la ultima fila
			    posicion[0] < this.tablero.length - 1 ) {
				
									
				Arriba(posicion);								//Desplaza la columna indicada hacia arriba
				System.out.println("Dirección ARRIBA " + this.get_Turno());
				return true;
			}
			
			JOptionPane.showMessageDialog(null,"Direccion INCORRECTA");
			System.out.println("Dirección INCORRECTA " + this.get_Turno());
			return false;
		}

		//direccion abajo
		else if( direccion.equals("B") || direccion.equals("b")) {
			
			if( posicion[0] == this.tablero.length - 1 ||		//Ultima fila
				
				posicion[1] == 0 &&								//Columna de la izquierda menos la primera fila
				posicion[0] > 0	 ||
				
				posicion[1] == this.tablero.length - 1 &&		//Columna de la derecha menos la primera fila
				posicion[0] > 0 ) {
				
				Abajo(posicion);								//Desplaza la columna indicada hacia abajo
				System.out.println("Dirección ABAJO " + this.get_Turno());
				return true;	
			}
			
			JOptionPane.showMessageDialog(null,"Direccion INCORRECTA");
			System.out.println("Dirección INCORRECTA " + this.get_Turno());
			return false;
		}

		//direccionn izquierda
		else if( direccion.equals("I") || direccion.equals("i")) {
			
			if( posicion[1] == 0 ||						 	    //Primera columna
				
				posicion[0] == 0 &&
				posicion[1] < this.tablero.length - 1 ||		//Primera fila menos la ultima columna
				
				posicion[0] == this.tablero.length - 1 &&		//Ultima fila menos la ultima columna
				posicion[1] < this.tablero.length - 1 ) {
				
				Izquierda(posicion);
				System.out.println("Dirección IZQUIERDA " + this.get_Turno());
				return true;
			}
			
			JOptionPane.showMessageDialog(null,"Direccion INCORRECTA");
			System.out.println("Dirección INCORRECTA " + this.get_Turno());
			return false;
		}
		
		//direccion derecha
		else if( direccion.equals("D") || direccion.equals("d")) {
			
			if( posicion[1] == this.tablero.length - 1 ||		//Ultima columna
					
				posicion[0] == 0 &&								//Primera fila menos la primera columna
				posicion[1] > 0  ||
				
				posicion[0] == this.tablero.length - 1 &&		//Ultima fila menos la primera columna
				posicion[1] > 0 ) {
				
				Derecha(posicion);
				System.out.println("Dirección DERECHA " + this.get_Turno());
				return true;
			}
			
			JOptionPane.showMessageDialog(null,"Direccion INCORRECTA");
			System.out.println("Dirección INCORRECTA " + this.get_Turno());
			return false;
		}
		
		JOptionPane.showMessageDialog(null,"Direccion INCORRECTA");
		System.out.println("Dirección INCORRECTA " + this.get_Turno());
		return false;
	}
	
	public void Abajo(int[] posicion){
		//desplaza la columna hacia abajo desde el lugar donde se hizo click
		
		for(int i = posicion[0]; i >= 1; i--) {
			this.tablero[i][posicion[1]] = this.tablero[i - 1][posicion[1]];
		}
		
		this.tablero[0][posicion[1]] = this.get_Turno();
		
	}
	
	public void Arriba(int[] posicion){
		//desplaza la columna hacia arriba desde el lugar donde se hizo click

		for(int i = posicion[0]; i < this.tablero.length - 1; i++) {
			this.tablero[i][posicion[1]] = this.tablero[i + 1][posicion[1]];
		}
		
		this.tablero[this.tablero.length - 1][posicion[1]] = this.get_Turno();
		
	}
		
	public void Izquierda(int[] posicion) {
		//desplaza la fila hacia la izquierda desde el lugar donde se hizo click 

		for(int i = posicion[1]; i < this.tablero.length - 1; i++) {
			this.tablero[posicion[0]][i] = this.tablero[posicion[0]][i + 1];
		}
		
		this.tablero[posicion[0]][this.tablero.length - 1] = this.get_Turno();
	}
	
	public void Derecha(int[] posicion) {
		//desplaza la fila hacia la derecha desde el lugar donde se hizo click

		for(int i = posicion[1]; i >= 1; i--) {
			this.tablero[posicion[0]][i] = this.tablero[posicion[0]][i - 1];
		}
		
		this.tablero[posicion[0]][0] = this.get_Turno();
	}
	
	
	//Búsqueda del ganador
	
	public boolean[] Busca_NRaya() {
		//Para hacer n en raya los jugadores tienen que tener alguna fila, columna o diagonal en la que sume 'n' o '-n'

		boolean ganador_1 = false; 
		boolean ganador_2 = false;
		
		int diagonal_1 = 0;
		int diagonal_2 = 0;
		
		for(int i = 0, k = this.tablero.length - 1; i < this.tablero.length && k >= 0; i++, k--) {
			
			int horizontal = 0;
			int vertical = 0;
			
			for(int j = 0; j < this.tablero.length; j++) {
				//System.out.printf("%d %d %d \n", i, k, j);
				
				//
				horizontal += this.tablero[i][j];
				vertical += this.tablero[j][i];	
			}
			//System.out.printf("Fila %d: %d   Columna %d: %d \n", i + 1, horizontal, i + 1, vertical);
			
			//Comprobamos si el JUGADOR1 tiene n en raya en la horizontal o en la vertical 
			if( horizontal == this.tablero.length || vertical == this.tablero.length ) ganador_1 = true;
			
			//Comprobamos si el JUGADOR2 tiene n en raya en la horizontal o en la vertical 
			if( horizontal == this.tablero.length * -1 || vertical == this.tablero.length * -1 ) ganador_2 = true;	
			
			//System.out.printf("%d %d\n",i,m.length - 1 - k);
			//System.out.printf("%d %d\n", k, i);
			
			diagonal_1 += this.tablero[i][this.tablero.length - 1 - k]; 		//sumamos el valor de la casilla a la diagonal principal
			diagonal_2 += this.tablero[k][i]; 									//sumamos el valor de la casilla a la diagonal secundaria
		}
		
		//Comprobamos si el JUGADOR1 tiene n en raya en alguna de las diagonales
		if(diagonal_1 == this.tablero.length || diagonal_2 == this.tablero.length) ganador_1 = true;

		//Comprobamos si el JUGADOR2 tiene n en raya en alguna de las diagonales
		if( diagonal_1 == this.tablero.length * -1 || diagonal_2 == this.tablero.length * -1 ) ganador_2 = true;
		
		
		//resultado[0] - almacena el estado del JUGADOR1 (rojo)
		//resultado[1] - almacena el estado del JUGADOR2 (azul)
		boolean[] resultado = new boolean[] {ganador_1, ganador_2};
		return resultado;	
	}
	
	public boolean Hay_Ganador(boolean[] resultado) {
		//resultado[0] - almacena el estado del JUGADOR1 (rojo)
		//resultado[1] - almacena el estado del JUGADOR2 (azul)
		
		//Ningun jugador hace n en raya
		if(resultado[0] == false && resultado[1] == false) {
			System.out.print("Se continua jugando");
			this.set_Ganador(false); //Nos aseguramos que el juego continua
			return this.get_Ganador();
		}
		
		//Los dos jugadores "ganan"
		else if(resultado[0] == true && resultado[1] == true) {
			
			//En este caso el jugador que haya hecho el movimiento, pierde. (En este caso el jugador rojo)
			if(this.TURNO == 1) {
				System.out.print("El jugador azul ha ganado");
				this.set_Turno(this.JUGADOR2); //Colocamos el turno al jugador azul
				this.set_Ganador(true);	//Finalizamos el juego, hay ganador
				return this.get_Ganador();
			}
			else if (this.TURNO == -1) {
				System.out.print("El jugador rojo ha ganado");
				this.set_Turno(this.JUGADOR1); //Colocamos el turno al jugador rojo
				this.set_Ganador(true); //Finalizamos el juego, hay ganador
				return this.get_Ganador();
			}
		
		}
		
		else if(resultado[0] == true && resultado[1] == false && this.get_Turno() == this.JUGADOR2 ||    //Gana el jugador 1 (rojo), el jugador2 hizo ganar al jugador1 
				resultado[0] == false && resultado[1] == true && this.get_Turno() == this.JUGADOR1) {    //Gana el jugador 2 (azul), el jugador1 hizo ganar al jugador2
			
			if(this.get_Turno() == this.JUGADOR1) {
				System.out.print("El jugador azul ha ganado");
				this.set_Turno(this.JUGADOR2);
				this.set_Ganador(true);
				return this.get_Ganador();
			}
			
			else if(this.get_Turno() == this.JUGADOR2) {
				System.out.print("El jugador rojo ha ganado");
				this.set_Turno(this.JUGADOR1);
				this.set_Ganador(true);
				return this.get_Ganador();
			}
		}
		
	
		//Un jugador hace n en raya
		System.out.print("El jugador " + this.get_Turno() + " ha ganado");
		this.set_Ganador(true);
		return this.get_Ganador();
	}
	
	
	//Utilidades
	
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
		
		System.out.println("\n\n");
	}
	
	public void Turno() {
		//Cambia el turno de juego
		if( this.get_Turno() == 1 ) this.set_Turno(this.JUGADOR2);
		else this.set_Turno(this.JUGADOR1);
		
	}
	
	public void Reiniciar() {
		
		//Reinicia el juego colocando el trablero a 0
		for(int i = 0; i < this.tablero.length; i++) {
			for(int j = 0; j < this.tablero.length; j++) {
				this.tablero[i][j] = 0;
			}
		}
		//Ajusta el turno al jugador 1 (rojo)
		this.set_Turno(this.JUGADOR1);
	}
	
	
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		int MARGEN_sup = this.MARGEN;
		for(int i = 0; i < this.tablero.length; i++) {
			
			int MARGEN_izq = this.MARGEN;
			for(int j = 0; j < this.tablero.length; j++){
				 
				g.setColor(Color.BLACK);
				g.drawRect(MARGEN_izq, MARGEN_sup, this.ANCHO, this.ANCHO);		//Dibuja tablero
				
				//Lee la matriz y pinta el estado del tablero, pinta circulos
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
			
			//Consigue las coordenadas del click
			int X = e.getX();
			int Y = e.getY();
			
			//Convierte las coordenadas a la casilla de la matriz donde se hizo click
			int[] posicion = Captura_Posicion(X, Y);
			
			//Comprobamos que se clickeo dentro del tablero y si este se produjo en una casilla correcta
			if( Dentro_Tablero(X, Y) && Posicion_Valida( posicion ) ) {
						
				//Después habría que solicitar la dirección
				String direccion = JOptionPane.showInputDialog("Direccion: (A)rriba - a(B)ajo - (I)zquierda - (D)erecha");
						
				//Mientras la direccion no sea valida se sigue solicitando hasta que sea correcta
				while( ! Dirección_Valida(direccion, posicion) ) {
							
					direccion = JOptionPane.showInputDialog("Direccion: (A)rriba - a(B)ajo - (I)zquierda - (D)erecha");
				}
						
				//Seguramente habrá que repintar el tablero si se realizó una jugada válida
				repaint();
				
				if(get_Turno() == 1) System.out.println("Turno: ROJO");
				else if(get_Turno() == -1) System.out.println("Turno: AZUL");
				
				//Mientras no haya ganador se siguie jugando
				if( ! Hay_Ganador( Busca_NRaya() ) ){
					
					//Cambia el turno del jugador y continua el juego			
					Turno();
	
				}
				else{
					//Victoria para algun jugador
					if(get_Turno() == 1) JOptionPane.showMessageDialog(null,"Victoria para rojo");
					else if(get_Turno() == -1) JOptionPane.showMessageDialog(null, "Victoria para azul");
					
					//Se reinicia el tablero
					Reiniciar();
					//Se repinta una vez reiniciado
					repaint();
				}
			}
			//Si se clickea fuera del tablero no sucede nada, se mantiene el turno y continua el mismo jugador
			Imprimir_Tablero();
		}		
	}
}