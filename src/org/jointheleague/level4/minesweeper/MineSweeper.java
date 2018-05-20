package org.jointheleague.level4.minesweeper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Implement the Mine Sweeper game by following the instructions in the
 * comments.
 * 
 * All instructions are marked with "TO DO" tags, and will have a blue
 * indicator on the right side, by the scroll bar.
 */
public class MineSweeper {
    static final int WIDTH = 10;
    static final int HEIGHT = 15;
    static final int CELL_SIZE = 20;
    static final int NUM_MINES = 15;
    
    final JFrame frame = new JFrame();
    final JButton[][] buttons = new JButton[HEIGHT][WIDTH];
    final Random rng = new Random();
    Optional<boolean[][]> mines = Optional.empty();
    int numCellsToOpen;
    
    /**
     * Initializes:
     * 1. The `mines` variable with `NUM_MINES` randomly distributed mines
     * 2. The `numCellsToOpen` variable to the number of non-mine
     *    cells (`WIDTH` * `HEIGHT` - `NUM_MINES`).
     * 
     * @param firstCellCol Column of first cell opened. This cannot be a mine.
     * @param firstCellRow Row of first cell opened. This cannot be a mine.
     */
    void initializeMines(int firstCellCol, int firstCellRow) {
        // TODO fill in
        // Hint, use `Optional.of(...)` to create a non-empty `Optional`.
    		boolean[][] b=new boolean[WIDTH][HEIGHT];
    		int xx=0;
    		while(xx>NUM_MINES) {
    			int x=rng.nextInt(WIDTH);
    			int y=rng.nextInt(HEIGHT);
    			if(b[x][y]!=true && !(firstCellCol==x && firstCellRow==y)) {
    				b[x][y]=true;
    				xx++;
    			}
    			
    		}
    		mines=Optional.of(b);
    	
    }
    
    /**
     * Inspects a given cell, and count the number of neighboring cells that
     * are mines.
     * 
     * @param col Column of cell to inspect.
     * @param row Row of cell to inspect.
     * @return The number of neighboring cells that are mines (0-8).
     */
    Integer getNeighboringMinesCount(int col, int row) {
        // TODO fill in
    	int num=0;
    	boolean[][] b=mines.get();
    		for(int i=-1;i<2;i++) {
    			for(int x=-1;x<2;x++) {
    		if(b[row+i][col+x]==true) {
    			num++;
    		}
    		}
    }
        return num;
    }
    
    /**
     * Resets the game by:
     * 1. Setting the `mines` variable back to empty.
     * 2. Clearing the button texts, and setting button states to enabled.
     * 
     * @param unused Just here so that method can be passed as ActionListener.
     */
     void resetGame() {
        // TODO fill in
    	mines=mines.empty();
    	for(int i=0;i<buttons.length;i++) {
    		for(int x=0;x<buttons[i].length;x++) {
    			buttons[i][x].setText("");
    			buttons[i][x].setEnabled(true);
    		}
    	}
    	}
    
    
    private void createAndShowFrame() {
        final JMenuItem resetMenuItem = new JMenuItem("Reset");
         
        resetMenuItem.addActionListener(evt-> resetGame()); // TODO replace null with method reference that resets the game
        
        final JMenu gameMenu = new JMenu("Game");
        gameMenu.add(resetMenuItem);
        
        final JMenuBar menuBar = new JMenuBar();
        menuBar.add(gameMenu);
        
        final JPanel controlPanel = new JPanel();
        final JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(evt->resetGame()); // TODO replace null with method reference that resets the game
        controlPanel.add(resetButton);
        
        final JPanel gameBoardPanel = new JPanel();
        gameBoardPanel.setSize(WIDTH * CELL_SIZE, HEIGHT * CELL_SIZE);
        gameBoardPanel.setBackground(Color.WHITE);
        gameBoardPanel.setLayout(new GridLayout(HEIGHT, WIDTH));
        IntStream.range(0, HEIGHT).forEach(row ->
            IntStream.range(0, WIDTH).forEach(col -> {
            		boolean[][] c=new boolean[WIDTH][HEIGHT];
            		initializeMines(row, col);
            		c=mines.get();
                // This code loops through the rows and columns,
                // creating a button for each cell.
                final JButton b = new JButton();
                buttons[row][col] = b;
                boolean p=false;
                for(int x=0;x<WIDTH;x++) {
                	for(int y=0;y<HEIGHT;y++) {
                		if(c[x][y]==true) {
                			p=true;
                		}
                	}
                }
                
                // When the cell button is pressed, it should:
                // 1. Initializes the mines if it is not yet initialized.
                
                // 2. If the button is a mine:
                //    - Change the button text to an "X"
                //    - Display "You Lose" in a dialog box
                
                if(c[row][col]==true) {
                	b.setText("X");
                	System.out.println("you lose");
                }
                //    Otherwise:
                //    - Change the button text to the number of neighboring
                //      cells are mines.
               if(c[row][col]!=true) {
                //	b.setText(""+getNeighboringMinesCount(col, row));
               }
                numCellsToOpen--;
                //    - Decrement `numCellsToOpen`
                //    - If all cells are open (i.e., numCellsToOpen == 0),
                //      display "You Win" in a dialog box
                if(numCellsToOpen==0) {
                	System.out.println("you win");
                }
                //    - Extra credit: If the number of neighboring cells is 0,
                //      automatically open all neighboring cells
                b.addActionListener(null); // TODO replace null with lambda expression
                gameBoardPanel.add(b);
            })
        );
        
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(WIDTH * CELL_SIZE, (HEIGHT + 1) * CELL_SIZE + 27);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
        frame.setJMenuBar(menuBar);
        frame.add(gameBoardPanel, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.PAGE_END);
        
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        final MineSweeper mineSweeper = new MineSweeper();
        SwingUtilities.invokeLater(mineSweeper::createAndShowFrame);
    }
}
