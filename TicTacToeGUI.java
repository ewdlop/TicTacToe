/**
 * This implementation of TicTacToe uses a 1 dimensional array of 10 elements.
 * Only array indices 1-9 are used. Array index 0 is not used.
 * The untaken TicTacToe squares contain values 1-9. A square taken by "X" contains 11,
 * and a square taken by "O" contains 12. X takes the first turn every time.
 * 
 * @author Raymond Lei, Chengzhang Zhu, Aishat A. Adegboye
 * @version 1
 */

// GUI includes:
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
//for button image
import javax.imageio.ImageIO;

public class TicTacToeGUI extends JFrame
{
    // instance variables
    private int turn; // start at 1, if odd then X's turn, if even then O's turn
    private int xplayer;
    private int oplayer;
    // list of player type constants
    private final int HUMAN = 1;
    private final int MACHINE = 2; // experienced machine player
    // GUI additions:
    // - an array of Square buttons (instead of int); call it 'board'
    // - a game status label
    private Square[] board;
    private JLabel label;
    /**
     * Constructor for objects of class TicTacToe
     */
    public TicTacToeGUI()
    {

        // 1. Create/initialize components:
        //    Construct the 9 Square buttons in the array here; add a SquareButtonListener to each of the 9 buttons.
        board = new Square[10];  
        for(int i = 1; i <= 9; i++)
        {
            board[i] = new Square(i);
            board[i].addActionListener(new SquareButtonListener());
        }

        //    Add three game choice JButtons and add a GameChoiceButtonListener to each button.
        //    Extra credit - Use radio buttons instead of JButtons
        JButton hvsh = new JButton("Human vs Human");
        hvsh.addActionListener(new GameChoiceButtonListener());
        JButton hvsm = new JButton("Human vs Machine");
        hvsm.addActionListener(new GameChoiceButtonListener());
        JButton mvsh = new JButton("Machine vs Human");
        mvsh.addActionListener(new GameChoiceButtonListener());
        JButton mvsm = new JButton("Machine vs Machine");
        mvsm.addActionListener(new GameChoiceButtonListener());;
        // 2. Create content panels, set layouts:
        //    Need at least 2 panels
        //    - 1 panel for 9 Square buttons
        //    - 1 panel for game status label and game choice buttons

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3,3));
        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new GridLayout(5,1));
        label = new JLabel("Choose a game below to start");
        // 3. Add the components to the content panels.
        for(int i = 1; i <= 9; i++)
        {
            buttonPanel.add(board[i]);
        }
        statusPanel.add(label);
        statusPanel.add(hvsh);
        statusPanel.add(hvsm);
        statusPanel.add(mvsh);
        statusPanel.add(mvsm);
        // 4. Set this window's attributes, and pack it.

        setLayout(new GridLayout(2,1)); // Set JFrame layout because of multiple panels.
        add(buttonPanel);
        add(statusPanel);
        pack(); // Applies the above FlowLayout
        setTitle("FractionGUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    } // end constructor

    /**
     * Square button listener; to be added to 9 Square buttons;
     * Used only by a human player.
     */
    class SquareButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            Object source = e.getSource();
            Square button = (Square)source;
            takeSquare(button.get());
            button.setEnabled(false);
        }
    }// end listener inner class

    /**
     * Game choice button listener; to be added to 3 game choice buttons
     */
    class GameChoiceButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            Object source = e.getSource();
            JButton button = (JButton)source;
            if(button.getText() == "Human vs Human")
            {
                xplayer = HUMAN;
                oplayer = HUMAN;
            }
            else
            if(button.getText() == "Human vs Machine")
            {
                xplayer = HUMAN;
                oplayer = MACHINE;
            }
            else
            if(button.getText() == "Machine vs Human")
            {
                xplayer = MACHINE;
                oplayer = HUMAN;
            }
            else
            if(button.getText() == "Machine vs Machine")
            {
                xplayer = MACHINE;
                oplayer = MACHINE;
            }
            setupNewGame();
        }    
    } // end listener inner class

    /**
     * Set up new game.
     * Needs to be called from the GameChoiceButtonListener to start new games.
     * Set turn to 1.
     * Reset 9 Square Buttons in array to values 1-9.
     */
    private void setupNewGame()
    {
        for(int i = 1; i <= 9; i++)
        {
            turn = 1;
            board[i].set(i);
            board[i].setEnabled(true);
            board[i].setText("");
            board[i].setIcon(null);
            board[i].setDisabledIcon(null);
        }
        if(xplayer == MACHINE)
        {
            machinePlay();
        }
        else
        {
            setGameStatusLabel();
        }
    }

    /**
     * Method to play as machine.
     * There's no loop.
     * It will call the getMachineSquare() method to get a square number to take and then take that square number.
     * If it's a machine(X) vs human(O) game, it must be called from the GameChoiceButtonListener to start the game.
     * If it's a machine(X) vs human(O) game or human(X) vs machine(O) game, it must be called from the 
     *      SquareButtonListener after a human player take a turn.
     */
    private void machinePlay()
    {
        int i = getMachineSquare();
        board[i].setEnabled(false);
        if (turn % 2 != 0) {
            board[i].set(11); // X
            //board[i].setText("X");
            try {
                Image img = ImageIO.read(getClass().getResource("X.png"));
                board[i].setIcon(new ImageIcon(img));
                board[i].setDisabledIcon(new ImageIcon(img));
            } 
            catch (Exception ex) {
                System.err.println("X Image not found");
            }
            if(hasWon())
            {
                for(int j = 1; j <= 9; j++)
                {
                    board[j].setEnabled(false);
                }
                setGameStatusLabel();
                return;
            }
            //if there is no winner yet
            turn++;
            setGameStatusLabel();
            
            if(oplayer == MACHINE && turn < 10)
            {
                machinePlay();
            }
        }
        else 
        {
            board[i].set(12); // O
            //board[i].setText("O");
             try {
                Image img = ImageIO.read(getClass().getResource("O.png"));
                board[i].setIcon(new ImageIcon(img));
                board[i].setDisabledIcon(new ImageIcon(img));
            } 
            catch (Exception ex) {
                System.err.println("O Image not found");
            }
            if(hasWon())
            {
                for(int j = 1; j<= 9; j++)
                {
                    board[j].setEnabled(false);
                }
                setGameStatusLabel();
                return;
            }
            turn++;
            setGameStatusLabel();
            if(xplayer == MACHINE && turn < 10)
            {
                machinePlay();
            }
        }

    }

    /**
     * Display game status in game status label.
     * Display whose turn it is, who has won or whether it's a draw.
     * It must be called from the SquareButtonListener and the machinePlay() method.
     */
    private void setGameStatusLabel()
    {        
        if( turn >= 10)
        {
            label.setText("Draw");
            return;
        }
        if (turn % 2 != 0) {
            if(hasWon())
            {
                label.setText("Winner: X");
                return;
            }
            label.setText("It's X's turn.");
        }
        else 
        {
            if(hasWon())
            {
                label.setText("Winner: O");
                return;
            }
            label.setText("It's O's turn.");
        }
    } // end setGameStatusLabel()

    /*
     * 
     * 
     * The following methods are from the non-GUI TicTacToe project. They must be altered in some places for the 
     * TicTacToeGUI project. Mainly, they need to be changed to take into account the fact that TicTacToeGUI's board
     * array is an array of Square buttons, not an array of ints.
     * 
     * 
     */

    /**
     * takeSquare: Method to put "X" or "O" in one of the 9 Square buttons
     * Place an "X" or "O" in the square indicated by squareNumber, depending on the value of turn.
     * Called from machinePlay() or from the SquareButtonListener.
     *
     * @param squareNumber (number from 1 - 9)
     */
    private void takeSquare(int squareNumber)
    {
        if (turn % 2 != 0) {
            board[squareNumber].set(11); // X
            //board[squareNumber].setText("X");
             try {
                Image img = ImageIO.read(getClass().getResource("X.png"));
                board[squareNumber].setIcon(new ImageIcon(img));
                board[squareNumber].setDisabledIcon(new ImageIcon(img));
            } 
            catch (Exception ex) {
                System.err.println("X Image not found");
            }
            if(hasWon())
            {
                for(int i = 1; i<= 9; i++)
                {
                    board[i].setEnabled(false);
                }
                setGameStatusLabel();
                return;
            }
            turn++;
            setGameStatusLabel();
            if(oplayer == MACHINE && turn < 10)
            {
                machinePlay();
            }
        }
        else 
        {
            board[squareNumber].set(12);// O
            //board[squareNumber].setText("O");
            try {
                Image img = ImageIO.read(getClass().getResource("O.png"));
                board[squareNumber].setIcon(new ImageIcon(img));
                board[squareNumber].setDisabledIcon(new ImageIcon(img));
            } 
            catch (Exception ex) {
                System.err.println("O Image not found");
            }
            if(hasWon())
            {
                for(int i = 1; i<= 9; i++)
                {
                    board[i].setEnabled(false);
                }
                setGameStatusLabel();
                return;
            }
            turn++;
            setGameStatusLabel();
            if(xplayer == MACHINE && turn < 10)
            {
                machinePlay();
            }
        }

    }

    /**
     * validSquare: Method to check if input squareNumber is valid.
     * A squareNumber is valid if it's between 1-9 AND
     * if the board[squareNumber] does not contain an 11 (X) or 12 (O).
     * 
     * @param squareNumber
     * @return valid, true if valid, false if invalid
     */
    private boolean validSquare(int squareNumber)
    {
        boolean valid = true;

        if (squareNumber < 1 || squareNumber > 9) {
            valid = false;
        }

        if (board[squareNumber].get() > 10) {
            valid = false;
        }

        return valid;
    }

    /**
     * hasWon: Check for game won. Called at the end a turn.
     *         If this method returns true, X or O has won.
     * @return won, true or false
     */
    private boolean hasWon()
    {
        boolean won = false;

        // I'm using as compact a coding style as I can below:

        // rows
        if ( board[1].get() == board[2].get() && board[2].get() == board[3].get() ) won = true;
        if ( board[4].get() == board[5].get() && board[5].get() == board[6].get() ) won = true;
        if ( board[7].get() == board[8].get() && board[8].get() == board[9].get() ) won = true;
        // columns
        if ( board[1].get() == board[4].get() && board[4].get() == board[7].get() ) won = true;
        if ( board[2].get() == board[5].get() && board[5].get() == board[8].get() ) won = true;
        if ( board[3].get() == board[6].get() && board[6].get() == board[9].get() ) won = true;
        // diagonals
        if ( board[1].get() == board[5].get() && board[5].get() == board[9].get() ) won = true;
        if ( board[3].get() == board[5].get() && board[5].get() == board[7].get() ) won = true;

        return won;
    }

    /**
     * getMachineSquare: This method contains an algorithm through which the
     *                   computer will choose a valid square number to take to
     *                   win the game or to prevent the opponent from winning
     *                   the game. The computer can be playing either X or O.
     *                   This represents an experienced machine type player.
     * @return squareNumber, a valid squareNumber from 1-9
     */
    private int getMachineSquare()
    {
        int mySymbol;
        int oppoSymbol;

        if (turn % 2 != 0) {
            mySymbol = 11; // X
            oppoSymbol = 12;
        }
        else{
            mySymbol = 12; // O
            oppoSymbol = 11;
        }

        // Experienced X, first turn, so choose corners (1, 3, 7, 9) or center (5):
        if (turn == 1)
        {
            int i = (int)(Math.random() * 5); // range: 0-4
            return (i + i) + 1; // returns 1, 3, 5, 7 or 9; skips rest of method
        }

        // Experienced O, second turn, so choose corners or center:
        if (turn == 2)
        {
            int sq = 0;

            if (validSquare(5))
            {
                sq = 5; // center is open, so take it
            }
            else // take a corner (1, 3, 7 or 9)
            {
                while(true)
                {
                    int i = (int)(Math.random() * 5);
                    sq = (i + i) + 1;

                    if (sq != 5)
                    {
                        break; // if not center (5), break
                    }
                }
            }

            return sq; // returns 1, 3, 5, 7 or 9; skips rest of method
        }

        //
        // 1) Try to win if my win is imminent
        //
        for (int i = 1; i <= 9; i++)
        {
            // If square i not taken...
            if (validSquare(i))
            {
                // Take the square
                board[i].set(mySymbol);
                // If taking the square wins me the game...
                if (hasWon())
                {
                    // I'll take that square to win.
                    return i; // skip rest of method
                }
                else
                {
                    // I'll put the number back and check the next square.
                    board[i].set(i);
                }
            } // end if
        } // end for

        //
        // 2) Try to block if an opponent win is imminent
        //
        for (int i = 1; i <= 9; i++)
        {
            // If square i not taken...
            if (validSquare(i))
            {
                // Let opponent take the square
                board[i].set(oppoSymbol);
                // If taking the square wins my opponent the game...
                if (hasWon())
                {
                    // I'll that square to block.
                    return i; // skip rest of method
                }
                else
                {
                    // I'll put the number back and check the next square.
                    board[i].set(i);
                }
            } // end if
        } // end for

        //
        // 3) Last resort: Play like a novice by choosing a random square that is available.
        //
        return getNoviceSquare(); // See getNoviceSquare() method definition below

    } // end getMachineSquare()

    /**
     * getNoviceSquare: Returns a random untaken square.
     *                  This represents a novice machine type player.
     * @return squareNumber, a valid squareNumber from 1-9
     */
    private int getNoviceSquare()
    {
        int squarenum;

        while(true) {
            squarenum = (int)(Math.random() * 9) + 1; // range: 1-9
            if (validSquare(squarenum)) {
                break;
            }
        }

        return squarenum;
    }    

    public static void main(String[] args) {
        TicTacToeGUI window = new TicTacToeGUI();
        window.setVisible(true);
    }    

} // end class
