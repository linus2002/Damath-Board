package game;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DamathBoard extends JPanel implements MouseListener {
	
	   private static final int SIZE = 10;
	   private boolean dragging = false;
	   private int jumpRow, jumpCol, pick=0;
	    
	   private String[][] numbers = {
			   {"", "", "", "", "", "", "", "", "", ""},
		        {"", "", "0","","3","","5","","8", ""},
		        {"", "7", "", "6", "", "1", "", "8", "", ""},
		        {"", "", "4", "", "2", "", "9", "", "5", ""},
		        {"", "", "", "", "", "", "", "", "", ""},
		        {"", "", "", "", "", "", "", "", "", ""},
		        {"", "3", "", "8", "", "1", "", "2", "", ""},
		        {"", "", "5", "", "6", "", "7", "", "1", ""},
		        {"", "2", "", "7", "", "4", "", "9", "", ""},
		        {"", "", "", "", "", "", "", "", "", ""},
		    };
		    
	
    private int[][] board = {{0,0, 0, 0, 0, 0, 0, 0, 0,0, 0},
    						 {0,0, 1, 0, 1, 0, 1, 0, 1,0, 0},
                             {0,1, 0, 1, 0, 1, 0, 1, 0,0, 0},
                             {0,0, 1, 0, 1, 0, 1, 0, 1,0, 0},
                             {0,0, 0, 0, 0, 0, 0, 0, 0,0, 0},
                             {0,0, 0, 0, 0, 0, 0, 0, 0,0 , 0},
                             {0,-1, 0, -1, 0, -1, 0, -1, 0,0, 0},
                             {0,0, -1, 0, -1, 0, -1, 0, -1,0, 0},
                             {0,-1, 0, -1, 0, -1, 0, -1, 0,0, 0},
    						 {0,0, 0, 0, 0, 0, 0, 0, 0,0 ,0}};
    
    String[][] operations = new String[][]{

        {"", "x", "", "/", "", "-", "", "+", "", ""},
        {"", "", "x", "", "+", "", "-", "", "/", ""},
        {"", "-", "", "+", "", "x", "", "/", "", ""},
        {"", "", "-", "", "/", "", "x", "", "+", ""},
        {"", "x", "", "/", "", "+", "", "-", "", ""},
        {"", "", "x", "", "+", "", "-", "", "/", ""},
        {"", "-", "", "+", "", "x", "", "/", "", ""},
        {"", "", "-", "", "/", "", "x", "", "+", ""},
        {"", "x", "", "/", "", "-", "", "+", "", ""},
        {"", "", "", "", "", "", "", "", "", ""},
    };
    
    private int turn = 1;
    private boolean selected = false;
    private int selectedRow, selectedCol;
    private JLabel redTurnLabel;
    private JLabel blueTurnLabel;
    String player1 = "", player2 = ""; 
   


    public DamathBoard() {
        setPreferredSize(new Dimension(900, 500));
        addMouseListener(this);
   
        player1 = JOptionPane.showInputDialog(null, "Enter Player 1 Name");
        player2 = JOptionPane.showInputDialog(null, "Enter Player 2 Name");
       
        redTurnLabel = new JLabel();
        blueTurnLabel = new JLabel();
    }
   
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g2 = (Graphics2D) graphics;
        g2.setStroke(new BasicStroke(6));
        
        int width = getWidth();
        int height = getHeight();

        GradientPaint gradient = new GradientPaint(0, 0, Color.RED, width, height, Color.BLUE);

        g2.setPaint(gradient);
        g2.fillRect(0, 0, width, height);
        
        Font font = new Font("Serif", Font.BOLD, 17);
       
        graphics.setColor(Color.RED);
        graphics.fillRect(550, 150, 80, 80);
        redTurnLabel.setBounds(650, 150, 100, 80);
        redTurnLabel.setText((turn == 1) ? "Red's turn" : "");
        redTurnLabel.setFont(font);
      
        redTurnLabel.setForeground(Color.WHITE);
        g2.setColor(Color.WHITE);
        g2.drawRect(550, 150, 80, 80);
        add(redTurnLabel);

        graphics.setColor(Color.WHITE);
        graphics.setFont(font);
        graphics.drawString(player1, 565, 190);
    
        graphics.setColor(Color.BLUE);
        graphics.fillRect(550, 250, 80, 80);
        blueTurnLabel.setBounds(650, 250, 100, 80);
        blueTurnLabel.setText((turn == -1) ? "Blue's turn" : "");
        blueTurnLabel.setFont(font);
        g2.setColor(Color.WHITE);
        g2.drawRect(550, 250, 80, 80);
        blueTurnLabel.setForeground(Color.WHITE);
        add(blueTurnLabel);
        
        graphics.setColor(Color.WHITE);
        graphics.setFont(font);
        graphics.drawString(player2, 565, 290);
      
        for (int row = 0; row < 10; row++) {
            for (int column = 0; column < 10; column++) {

                if (row > 0 && row < 9 && column > 0 && column < 9) {
                    Color color = (row + column) % 2 == 0 ? Color.darkGray : Color.WHITE;
                    graphics.setColor(color);
                    graphics.fillRect(column * 50, row * 50, 50, 50);
                }

                if (row == 0 || row == 9) {
                    if (column > 0 && column < 9) {
                        graphics.setColor(Color.white);
                        graphics.setFont(font);
                        graphics.drawString(String.valueOf(column - 1), column * 50 + 20, row * 50 + 28);
                    }
                }

                if (column == 0 || column == 9) {
                    if (row > 0 && row < 9) {
                        graphics.setColor(Color.white);
                        graphics.setFont(font);
                        graphics.drawString(String.valueOf(row - 1), column * 50 + 20, row * 50 + 28);
                    }
                }
                if( row > 0 && row < 9 && column > 0 && column < 9) {
                	
                	Font opFont = new Font("Arial Black", Font.BOLD, 20);
                  
                        String operation = operations[row][column];
                        if (!operation.equals("")) {
                            graphics.setColor(Color.black);
                            graphics.setFont(opFont);
                            graphics.drawString(operation, column * 50 + 20, row * 50 + 28);
                        }
                    }
                
                String num = numbers[row][column];
                if (!num.equals("")) {
                
                if (board[row][column] == 1) {
                    graphics.setColor(Color.RED);
                    graphics.fillOval(column * 50 + 10, row * 50 + 10, 30, 30);
                    if (numbers[row][column] != null) {
                    graphics.setColor(Color.WHITE);
                    graphics.setFont(font);
                    graphics.drawString(num, column * 50 + 20, row * 50 + 28);
                    }
                } else if (board[row][column] == -1) {
                    graphics.setColor(Color.BLUE);
                    graphics.fillOval(column * 50 + 10, row * 50 + 10, 30, 30);
                    if (numbers[row][column] != null) {
                    graphics.setColor(Color.WHITE);
                    graphics.setFont(font);
                    graphics.drawString(num, column * 50 + 20, row * 50 + 28);
                }
                }

                if (selectedRow == row && selectedCol == column && selected) {
                	
                	g2.setColor(Color.YELLOW);
                    g2.drawRect(column * 50, row * 50, 49, 45);

                }
            }
        }
        }
        repaint();
    }

    public void mouseClicked(MouseEvent e) {
    	 int row = e.getY() / 50;
         int col = e.getX() / 50;

         
         if (board[row][col] * turn > 0) {
             selected = true;
             selectedRow = row;
             selectedCol = col;
             repaint(selectedCol*50, selectedRow*50, 50, 50); 
         } 
         
         	else if (selected && board[row][col] == 0) {       	
             if (isValidMove(selectedRow, selectedCol, row, col)) {
                 
                numbers[row][col] = numbers[selectedRow][selectedCol];
                board[row][col] = board[selectedRow][selectedCol];
                board[row][col] = board[selectedRow][selectedCol];
                board[selectedRow][selectedCol] = 0;
                turn *= -1;
                 
                if(pick<1)pick++;
               else pick=0; 
             }
             selected = false;
             repaint(selectedCol*50, selectedRow*50, 50, 50);
             repaint(col*50, row*50, 50, 50);
         } else if (selected && board[row][col] * turn > 0 && e.getButton() == MouseEvent.BUTTON1) {
             dragging = true;
         } else if (dragging && e.getButton() == MouseEvent.NOBUTTON) {
             selectedRow = e.getY() / 50;
             selectedCol = e.getX() / 50;
             repaint();
         } else if (dragging && e.getButton() == MouseEvent.BUTTON1) {
             dragging = false;
         }
    }

    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol) {
        int piece = board[fromRow][fromCol];
        int dx = toCol - fromCol;
        int dy = toRow - fromRow;
        if (Math.abs(dx) != Math.abs(dy)) return false;
        if (piece == 1 && dy < 0) return false;
        if (piece == -1 && dy > 0) return false;
        if (Math.abs(dx) == 1 && Math.abs(dy) == 1) return true; 
        int jumpRow = fromRow + dy/2;
        int jumpCol = fromCol + dx/2;
        if (board[jumpRow][jumpCol]*piece < 0 && board[toRow][toCol] == 0) {
            board[jumpRow][jumpCol] = 0;
            return true; 
        }
        return false;
    }

    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    public static void main(String[] args) {
          
        JFrame frame = new JFrame("THE MATH");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        DamathBoard board = new DamathBoard();
        frame.add(board);
        frame.setBounds(220, 100, 0, 0);
        frame.pack();
        frame.setVisible(true);
       
    }
}