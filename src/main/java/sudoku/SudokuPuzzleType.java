package sudoku;

public enum SudokuPuzzleType {

	// Definição de tipos de tabuleiro de Sudoku com diferentes configurações:
	// Cada constante do enum define:
	// - Dimensões do tabuleiro (linhas e colunas).
	// - Dimensões das subgrades (boxWidth e boxHeight).
	// - Valores válidos para preencher o tabuleiro.
	// - Descrição textual do tipo de jogo.

	SIXBYSIX(6,6,3,2,new String[] {"1","2","3","4","5","6"},"6 By 6 Game"),
	NINEBYNINE(9,9,3,3,new String[] {"1","2","3","4","5","6","7","8","9"},"9 By 9 Game"),
	TWELVEBYTWELVE(12,12,4,3,new String[] {"1","2","3","4","5","6","7","8","9","A","B","C"},"12 By 12 Game"),
	SIXTEENBYSIXTEEN(16,16,4,4,new String[] {"1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G"},"16 By 16 Game");
	 	
	private final int rows;//número de linhas 
	private final int columns;//número de colunas 
	private final int boxWidth;//largura de cada subgrade
	private final int boxHeight;//altura de cada subgrade
	private final String [] validValues; //conjunto de valores válidos 
	private final String desc; //descrição do tipo de jogo 
	
	private SudokuPuzzleType(int rows,int columns,int boxWidth,int boxHeight,String [] validValues,String desc) {
		this.rows = rows;
		this.columns = columns;
		this.boxWidth = boxWidth;
		this.boxHeight = boxHeight;
		this.validValues = validValues;
		this.desc = desc;
	}
	
	//método para obter o número de linhas 
	public int getRows() {
		return rows;
	}

	//método para obter o número de colunas 
	public int getColumns() {
		return columns;
	}
	
	//retorna a largura da subgrade
	public int getBoxWidth() {
		return boxWidth;
	}
	
	//retorna a altura da subgrade
	public int getBoxHeight() {
		return boxHeight;
	}
	
	//método para obter valores válidos 
	public String [] getValidValues() {
		return validValues;
	}
	
	public String toString() {
		return desc; //retorna a descrição (ex.: 6 by 6 game)
	}
}
