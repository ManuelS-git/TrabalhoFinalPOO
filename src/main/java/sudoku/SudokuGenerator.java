package sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SudokuGenerator {

	/**
	 * Gera um puzzle aleatório baseado no tipo especificado
	 * @param puzzleType tipo de sudoku (dimenções, valores, etc.)
	 * @return retorna um objeto que será o quebra-cabeça gerado
	 */
	public SudokuPuzzle generateRandomSudoku(SudokuPuzzleType puzzleType) {
		//cria um cópia do tipo de tabuleiro escolhido 
		SudokuPuzzle puzzle = new SudokuPuzzle(puzzleType.getRows(), puzzleType.getColumns(), puzzleType.getBoxWidth(), puzzleType.getBoxHeight(), puzzleType.getValidValues());
		
		//faz uma cópia do tabuleiro para preencher e resolver
		SudokuPuzzle copy = new SudokuPuzzle(puzzle);
		
		Random randomGenerator = new Random(); //gera números aleatórios
		
		//preenche a primeira coluna do tabuleiro com valores unicos e aleatórios
		List<String> notUsedValidValues =  new ArrayList<String>(Arrays.asList(copy.getValidValues()));
		for(int r = 0;r < copy.getNumRows();r++) {
			int randomValue = randomGenerator.nextInt(notUsedValidValues.size());
			copy.makeMove(r, 0, notUsedValidValues.get(randomValue), true); //faz um movimento na coluna 
			notUsedValidValues.remove(randomValue); //remove o valor utilizado para evitar repetição 
		}
		
		//resolve o tabuleiro para preencher com os valores restantes
		backtrackSudokuSolver(0, 0, copy);
		
		//calcula o número de valores que devem ser mantidos no quebra cabeça final 
		int numberOfValuesToKeep = (int)(0.22222*(copy.getNumRows()*copy.getNumRows()));
		
		//remove valores aletórios do quebra cabeça, para cria-lo incompleto
		for(int i = 0;i < numberOfValuesToKeep;) {
			int randomRow = randomGenerator.nextInt(puzzle.getNumRows());
			int randomColumn = randomGenerator.nextInt(puzzle.getNumColumns());
			
			//verifica se o slot está disponivel para antes de preenchelo 
			if(puzzle.isSlotAvailable(randomRow, randomColumn)) {
				puzzle.makeMove(randomRow, randomColumn, copy.getValue(randomRow, randomColumn), false);
				i++; //contador somente ao preencher um slot
			}
		}
		
		//retorna quebra cabça gerado
		return puzzle;
	}
	
	/**
	 * Solves the sudoku puzzle
	 * Pre-cond: r = 0,c = 0
	 * Post-cond: solved puzzle
	 * @param r: the current row
	 * @param c: the current column
	 * @return valid move or not or done
	 * Responses: Erroneous data 
	 */
    private boolean backtrackSudokuSolver(int r,int c,SudokuPuzzle puzzle) {
    	//If the move is not valid return false
		if(!puzzle.inRange(r,c)) {
			return false;
		}
		
		//if the current space is empty
		if(puzzle.isSlotAvailable(r, c)) {
			
			//loop to find the correct value for the space
			for(int i = 0;i < puzzle.getValidValues().length;i++) {
				
				//if the current number works in the space
				if(!puzzle.numInRow(r, puzzle.getValidValues()[i]) && !puzzle.numInCol(c,puzzle.getValidValues()[i]) && !puzzle.numInBox(r,c,puzzle.getValidValues()[i])) {
					
					//make the move
					puzzle.makeMove(r, c, puzzle.getValidValues()[i], true);
					
					//if puzzle solved return true
					if(puzzle.boardFull()) {
						return true;
					}
					
					//go to next move
					if(r == puzzle.getNumRows() - 1) {
						if(backtrackSudokuSolver(0,c + 1,puzzle)) return true;
					} else {
						if(backtrackSudokuSolver(r + 1,c,puzzle)) return true;
					}
				}
			}
		}
		
		//if the current space is not empty
		else {
			//got to the next move
			if(r == puzzle.getNumRows() - 1) {
				return backtrackSudokuSolver(0,c + 1,puzzle);
			} else {
				return backtrackSudokuSolver(r + 1,c,puzzle);
			}
		}
		
		//undo move
		puzzle.makeSlotEmpty(r, c);
		
		//backtrack
		return false;
	}
}
