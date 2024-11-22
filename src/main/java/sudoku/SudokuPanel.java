package sudoku;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

//classe responsavel para montar o painel do jogo
@SuppressWarnings("serial")
public class SudokuPanel extends JPanel {

	private SudokuPuzzle puzzle; //objeto que representa o estado atual do tabuleiro
	private int currentlySelectedCol; //coluna selecionada pelo jogador 
	private int currentlySelectedRow;//linha selecionada pelo jogador
	private int usedWidth; //largura usada no tabuleiro 
	private int usedHeight; //altura usada no tabuleiro 
	private int fontSize; //tamanho da fonte para exebir os número do tabuleiro 
	
	//construtor padrão do painel, incial 9x9
	public SudokuPanel() {
		this.setPreferredSize(new Dimension(540,450)); //tamanho da tabela
		this.addMouseListener(new SudokuPanelMouseAdapter()); //eventos do clique 
		this.puzzle = new SudokuGenerator().generateRandomSudoku(SudokuPuzzleType.NINEBYNINE);//gera um sudoku 9x9
		currentlySelectedCol = -1;
		currentlySelectedRow = -1;
		usedWidth = 0;
		usedHeight = 0;
		fontSize = 26; //tamanho incial da fonte
	}
	
	//método que aceita o puzzle como parâmetro 
	public SudokuPanel(SudokuPuzzle puzzle) {
		this.setPreferredSize(new Dimension(540,450));
		this.addMouseListener(new SudokuPanelMouseAdapter());
		this.puzzle = puzzle;
		currentlySelectedCol = -1;
		currentlySelectedRow = -1;
		usedWidth = 0;
		usedHeight = 0;
		fontSize = 26;
	}
	
	//atualiza o painel com um novo tabuleiro 
	public void newSudokuPuzzle(SudokuPuzzle puzzle) {
		this.puzzle = puzzle;
	}
	
	//atualiza o tamanho da fonte
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	
	//método principal para desenha o tabuleiro 
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(new Color(1.0f,1.0f,1.0f)); //cor de fundo, sendo branca 
		
		//calcula o tamanho de cada jogo
		int slotWidth = this.getWidth()/puzzle.getNumColumns();
		int slotHeight = this.getHeight()/puzzle.getNumRows();
		
		//calcula as dimensões usadas
		usedWidth = (this.getWidth()/puzzle.getNumColumns())*puzzle.getNumColumns();
		usedHeight = (this.getHeight()/puzzle.getNumRows())*puzzle.getNumRows();
		
		//preenche o fundo do tabuleiro 
		g2d.fillRect(0, 0,usedWidth,usedHeight);
		
		//desenha as linhas horizontais e verticais 
		g2d.setColor(new Color(0.0f,0.0f,0.0f));
		for(int x = 0;x <= usedWidth;x+=slotWidth) {
			if((x/slotWidth) % puzzle.getBoxWidth() == 0) {
				g2d.setStroke(new BasicStroke(2)); //linhas mais grossas para as bordas
				g2d.drawLine(x, 0, x, usedHeight); 
			}
			else {
				g2d.setStroke(new BasicStroke(1)); //linhas normais 
				g2d.drawLine(x, 0, x, usedHeight);
			}
		}
		//this will draw the right most line
		//g2d.drawLine(usedWidth - 1, 0, usedWidth - 1,usedHeight);
		for(int y = 0;y <= usedHeight;y+=slotHeight) {
			if((y/slotHeight) % puzzle.getBoxHeight() == 0) {
				g2d.setStroke(new BasicStroke(2));
				g2d.drawLine(0, y, usedWidth, y);
			}
			else {
				g2d.setStroke(new BasicStroke(1));
				g2d.drawLine(0, y, usedWidth, y);
			}
		}
		//this will draw the bottom line
		//g2d.drawLine(0, usedHeight - 1, usedWidth, usedHeight - 1);
		
		Font f = new Font("Times New Roman", Font.PLAIN, fontSize);
		g2d.setFont(f);
		FontRenderContext fContext = g2d.getFontRenderContext();

		//desenha os números dos tabuleiros
		for(int row=0;row < puzzle.getNumRows();row++) {
			for(int col=0;col < puzzle.getNumColumns();col++) {
				if(!puzzle.isSlotAvailable(row, col)) {
					int textWidth = (int) f.getStringBounds(puzzle.getValue(row, col), fContext).getWidth();
					int textHeight = (int) f.getStringBounds(puzzle.getValue(row, col), fContext).getHeight();
					g2d.drawString(puzzle.getValue(row, col), (col*slotWidth)+((slotWidth/2)-(textWidth/2)), (row*slotHeight)+((slotHeight/2)+(textHeight/2)));
				}
			}
		}
		//destaca o quadrado selecionado
		if(currentlySelectedCol != -1 && currentlySelectedRow != -1) {
			g2d.setColor(new Color(0.0f,0.0f,1.0f,0.3f)); //muda a cor
			g2d.fillRect(currentlySelectedCol * slotWidth,currentlySelectedRow * slotHeight,slotWidth,slotHeight);
		}
	}
	
	 // Método chamado ao clicar em um botão numérico para atualizar o tabuleiro.
	public void messageFromNumActionListener(String buttonValue) {
		if(currentlySelectedCol != -1 && currentlySelectedRow != -1) {
			puzzle.makeMove(currentlySelectedRow, currentlySelectedCol, buttonValue, true); 
			repaint(); //redesenha o tabuleiro
		}
	}
	
	public class NumActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			messageFromNumActionListener(((JButton) e.getSource()).getText());	
		}
	}
	
	//classe para tratar de cliques do mouse
	private class SudokuPanelMouseAdapter extends MouseInputAdapter {
		@Override
		public void mouseClicked(MouseEvent e) { 
			if(e.getButton() == MouseEvent.BUTTON1) { //verifica se o botão do mouse clicado foi o esquerdo
				int slotWidth = usedWidth/puzzle.getNumColumns();
				int slotHeight = usedHeight/puzzle.getNumRows();
				currentlySelectedRow = e.getY() / slotHeight;
				currentlySelectedCol = e.getX() / slotWidth;
				e.getComponent().repaint();
			}
		}
	}
}
