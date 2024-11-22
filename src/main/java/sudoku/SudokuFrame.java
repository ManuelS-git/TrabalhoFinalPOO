package sudoku;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class SudokuFrame extends JFrame {

	//painéis da interface
	private JPanel buttonSelectionPanel;
	private SudokuPanel sPanel;
	
	//construtor principal
	public SudokuFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Sudoku");
		this.setMinimumSize(new Dimension(800,600));
		
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("Game");//menu principal
		JMenu newGame = new JMenu("New Game");//menu para novos jogos 

		//opções com diferentes modos de jogo
		JMenuItem sixBySixGame = new JMenuItem("6 By 6 Game"); //modo de jogo 6x6
		sixBySixGame.addActionListener(new NewGameListener(SudokuPuzzleType.SIXBYSIX,30));
		JMenuItem nineByNineGame = new JMenuItem("9 By 9 Game");//modo de jogo 9x9
		nineByNineGame.addActionListener(new NewGameListener(SudokuPuzzleType.NINEBYNINE,26));
		JMenuItem twelveByTwelveGame = new JMenuItem("12 By 12 Game");//modo de jogo 12x12
		twelveByTwelveGame.addActionListener(new NewGameListener(SudokuPuzzleType.TWELVEBYTWELVE,20));
		
		//adiciona as opções de jogos no menu "New Game"
		newGame.add(sixBySixGame);
		newGame.add(nineByNineGame);
		newGame.add(twelveByTwelveGame);
		
		file.add(newGame);
		menuBar.add(file); //adiciona o menu game a barra de janela 
		this.setJMenuBar(menuBar);
		
		JPanel windowPanel = new JPanel();
		windowPanel.setLayout(new FlowLayout()); //define o layout para adicionar os componentes
		windowPanel.setPreferredSize(new Dimension(800,600));// tamanho do painel
		
		buttonSelectionPanel = new JPanel();
		buttonSelectionPanel.setPreferredSize(new Dimension(90,500));

		sPanel = new SudokuPanel(); //criação do painel do tabuleiro 
		
		//adiciona o painel do tabuleiro 
		windowPanel.add(sPanel);
		windowPanel.add(buttonSelectionPanel); //adciona o painel dos botões
		this.add(windowPanel);
		
		//abre a janela já rodando o jogo 9x9
		rebuildInterface(SudokuPuzzleType.NINEBYNINE, 26);
	}
	
	//método para reconstruir a interface de outro jogo
	
	/////////////////////////////////////////////////////////////////////////
	public void rebuildInterface(SudokuPuzzleType puzzleType,int fontSize) { //Código original está no SudokuFrame
		SudokuPuzzle generatedPuzzle = new SudokuGenerator().generateRandomSudoku(puzzleType);
		sPanel.newSudokuPuzzle(generatedPuzzle);
		sPanel.setFontSize(fontSize);

		buttonSelectionPanel.removeAll();

		JTextField texto= new JTextField(10);
		JButton inserir= new JButton("Inserir número");

		//inserir.addActionListener(sPanel.new GameActionListener());
		
		inserir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a){
				String value = texto.getText();
				sPanel.messageFromNumActionListener(value);
			}
		});
		
		buttonSelectionPanel.add(texto);
		buttonSelectionPanel.add(inserir);

		sPanel.repaint();
		buttonSelectionPanel.revalidate();
		buttonSelectionPanel.repaint();

		/**
		 * public class NumActionListener implements ActionListener { //classe original presente no SudokuPanel
		@Override
		public void actionPerformed(ActionEvent e) {
			messageFromNumActionListener(((JButton) e.getSource()).getText());	
		}
	}
		public class GameActionListener implements ActionListener{
			public void actionPerformed(ActionEvent e){
				messageFromNumActionListener2(((JTextField) e.getSource()).getText());
		}
	}

		public void messageFromNumActionListener(String buttonValue) { //classe original presente no SudokuPanel
		if(sPanel.currentlySelectedCol != -1 && sPanel.currentlySelectedRow != -1) {
			puzzle.makeMove(currentlySelectedRow, currentlySelectedCol, buttonValue, true);
			repaint();
		}
	}
		public void messageFromNumActionListener2(String textfieldValue) {
		if(currentlySelectedCol != -1 && currentlySelectedRow != -1) {
			puzzle.makeMove(currentlySelectedRow, currentlySelectedCol, textfieldValue, true);
			repaint();
		}
	}
		 */

	}


	/////////////////////////////////////////////////////////////////////////
	
	//classe privada para lidar com o novo modo de jogo escolhido 
	private class NewGameListener implements ActionListener {

		private SudokuPuzzleType puzzleType; //tipo de tabuleiro 
		private int fontSize; //tamanho da fonte 
		
		public NewGameListener(SudokuPuzzleType puzzleType,int fontSize) {
			this.puzzleType = puzzleType;
			this.fontSize = fontSize;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			//reconstroi a interface com o tipo de jogo e tamanho da fonte
			rebuildInterface(puzzleType,fontSize);
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				//cria uma instância da janela principal e a torna invisivel
				SudokuFrame frame = new SudokuFrame();
				frame.setVisible(true);
			}
		});
	}
}
