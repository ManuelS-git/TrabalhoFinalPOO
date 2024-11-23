package sudoku;

public class SudokuPuzzle {

	// Tabuleiro do Sudoku representado como uma matriz de strings
	protected String [][] board;
	
	// Matriz para determinar se cada posição do tabuleiro é mutável
	protected boolean [][] mutable;

	private final int ROWS; //número de linhas no tabuleiro 
	private final int COLUMNS;//número de linhas no tabuleiro
	private final int BOXWIDTH;//largura de cada sub-bloco 
	private final int BOXHEIGHT;//altura de cada sub-bloco 
	private final String [] VALIDVALUES;//valores válidos permitidos 
	
	public SudokuPuzzle(int rows,int columns,int boxWidth,int boxHeight,String [] validValues) {
		this.ROWS = rows;
		this.COLUMNS = columns;
		this.BOXWIDTH = boxWidth;
		this.BOXHEIGHT = boxHeight;
		this.VALIDVALUES = validValues;
		this.board = new String[ROWS][COLUMNS];//inicializa o tabuleiro vazio
		this.mutable = new boolean[ROWS][COLUMNS];//inicializa a mutabilidade
		initializeBoard();//preenche o tabuleiron com valores vazios 
		initializeMutableSlots();//define inicialmente todas as posições como mutaveis 
	}
	
	// Construtor de cópia: cria um novo quebra-cabeça baseado em outro existente
	public SudokuPuzzle(SudokuPuzzle puzzle) {
		this.ROWS = puzzle.ROWS;
		this.COLUMNS = puzzle.COLUMNS;
		this.BOXWIDTH = puzzle.BOXWIDTH;
		this.BOXHEIGHT = puzzle.BOXHEIGHT;
		this.VALIDVALUES = puzzle.VALIDVALUES;
		this.board = new String[ROWS][COLUMNS];

		 // Copia o tabuleiro
		for(int r = 0;r < ROWS;r++) {
			for(int c = 0;c < COLUMNS;c++) {
				board[r][c] = puzzle.board[r][c];
			}
		}

		 // Copia a mutabilidade
		this.mutable = new boolean[ROWS][COLUMNS];
		for(int r = 0;r < ROWS;r++) {
			for(int c = 0;c < COLUMNS;c++) {
				this.mutable[r][c] = puzzle.mutable[r][c];
			}
		}
	}
	
	public int getNumRows() {
		return this.ROWS;
	}
	
	public int getNumColumns() {
		return this.COLUMNS;
	}
	
	public int getBoxWidth() {
		return this.BOXWIDTH;
	}
	
	public int getBoxHeight() {
		return this.BOXHEIGHT;
	}
	
	public String [] getValidValues() {
		return this.VALIDVALUES;
	}
	
	// Realiza uma jogada no tabuleiro
	public void makeMove(int row,int col,String value,boolean isMutable) {
		//verifica se a jogada é valida
		if(this.isValidValue(value) && this.isValidMove(row,col,value) && this.isSlotMutable(row, col)) {
			this.board[row][col] = value; //coloca o valor no tabuleiro 
			this.mutable[row][col] = isMutable; //define se a posição será mutavel 
		}
	}
	
	//verifica se o movimento é valido 
	public boolean isValidMove(int row,int col,String value) {
		if(this.inRange(row,col)) { //verifica se a jogada está dentro dos limites
			if(!this.numInCol(col,value) && !this.numInRow(row,value) && !this.numInBox(row,col,value)) {
				return true;
			}
		}
		return false;
	}
	
	//verifica se o valor já está em uma coluna
	public boolean numInCol(int col,String value) {
		if(col <= this.COLUMNS) {
			for(int row=0;row < this.ROWS;row++) {
				if(this.board[row][col].equals(value)) {
					return true;
				}
			}
		}
		return false;
	}
	
	//verifica se o valor já está em uma linha 
	public boolean numInRow(int row,String value) {
		if(row <= this.ROWS) {
			for(int col=0;col < this.COLUMNS;col++) {
				if(this.board[row][col].equals(value)) {
					return true;
				}
			}
		}
		return false;
	}
	
	//verifica se o valor já está em um bloco
	public boolean numInBox(int row,int col,String value) {
		if(this.inRange(row, col)) {
			int boxRow = row / this.BOXHEIGHT;// Determina o índice do bloco na linha
			int boxCol = col / this.BOXWIDTH;// Determina o índice do bloco na coluna
			
			 // Determina a posição inicial do bloco
			int startingRow = (boxRow*this.BOXHEIGHT);
			int startingCol = (boxCol*this.BOXWIDTH);
			
			//percorre as células do bloco 
			for(int r = startingRow;r <= (startingRow+this.BOXHEIGHT)-1;r++) {
				for(int c = startingCol;c <= (startingCol+this.BOXWIDTH)-1;c++) {
					if(this.board[r][c].equals(value)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	//verifica se a célula está disponivel (vazia e mutavel)
	public boolean isSlotAvailable(int row,int col) {
		 return (this.inRange(row,col) && this.board[row][col].equals("") && this.isSlotMutable(row, col));
	}
	
	//verifica se é mutavel 
	public boolean isSlotMutable(int row,int col) {
		return this.mutable[row][col];
	}
	
	//obtém o valor da célula
	public String getValue(int row,int col) {
		if(this.inRange(row,col)) {
			return this.board[row][col];
		}
		return "";
	}
	
	//retorna o tabuleiro 
	public String [][] getBoard() {
		return this.board;
	}
	
	//verifica se o valor é valido 
	private boolean isValidValue(String value) {
		for(String str : this.VALIDVALUES) {
			if(str.equals(value)) return true;
		}
		return false;
	}
	
	//verifica se a posição está dentro dos tabuleiros
	public boolean inRange(int row,int col) {
		return row <= this.ROWS && col <= this.COLUMNS && row >= 0 && col >= 0;
	}
	
	//verifica se o tabuleiro está cheio 
	public boolean boardFull() {
		for(int r = 0;r < this.ROWS;r++) {
			for(int c = 0;c < this.COLUMNS;c++) {
				if(this.board[r][c].equals("")) return false;
			}
		}
		return true;
	}
	
	//torna uma célula vazia 
	public void makeSlotEmpty(int row,int col) {
		this.board[row][col] = "";
	}
	
	//representa o tabuleiro em forma de string 
	@Override
	public String toString() {
		String str = "Game Board:\n";
		for(int row=0;row < this.ROWS;row++) {
			for(int col=0;col < this.COLUMNS;col++) {
				str += this.board[row][col] + " ";
			}
			str += "\n";
		}
		return str+"\n";
	}
	
	private void initializeBoard() {
		for(int row = 0;row < this.ROWS;row++) {
			for(int col = 0;col < this.COLUMNS;col++) {
				this.board[row][col] = "";
			}
		}
	}
	
	private void initializeMutableSlots() {
		for(int row = 0;row < this.ROWS;row++) {
			for(int col = 0;col < this.COLUMNS;col++) {
				this.mutable[row][col] = true;
			}
		}
	}
}


