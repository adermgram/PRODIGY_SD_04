import javax.swing.*;
import java.awt.*;

public class SudokuSolver extends JFrame {
    
    private static final int SIZE = 9;
    private JTextField[][] cells = new JTextField[SIZE][SIZE];
    private JButton solveButton;
    private JButton clearButton;
    
    SudokuSolver() {
        this.setTitle("Sudoku Solver");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(500, 600));
        JPanel gridPanel = new JPanel(new GridLayout(SIZE, SIZE));

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                cells[row][col] = new JTextField();
                cells[row][col].setHorizontalAlignment(JTextField.CENTER);
                cells[row][col].setFont(new Font("Arial", Font.BOLD, 20));
                cells[row][col].setBackground(new Color(0xFFE9D0));
                gridPanel.add(cells[row][col]);
            }
        }

        solveButton = new JButton("Solve");
        clearButton = new JButton("Clear");
        solveButton.setBackground(new Color(0x00FF00));
        clearButton.setBackground(new Color(0xFF0000));
        solveButton.setFocusable(false);
        clearButton.setFocusable(false);

        solveButton.addActionListener((e) -> {
            int[][] board = new int[SIZE][SIZE];
            try {
                for (int row = 0; row < SIZE; row++) {
                    for (int col = 0; col < SIZE; col++) {
                        String text = cells[row][col].getText();
                        if (!text.isEmpty()) {
                            board[row][col] = Integer.parseInt(text);
                        } else {
                            board[row][col] = 0;
                        }
                    }
                }
                if (solveSudoku(board)) {
                    displayBoard(board);
                } else {
                    JOptionPane.showMessageDialog(null, "No solution exists for the given Sudoku puzzle.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter valid numbers (1-9).");
            }
        });

        clearButton.addActionListener((e) -> {
            for (int row = 0; row < SIZE; row++) {
                for (int col = 0; col < SIZE; col++) {
                    cells[row][col].setText("");
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(solveButton);
        buttonPanel.add(clearButton);

        this.add(gridPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void displayBoard(int[][] board) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                cells[row][col].setText(String.valueOf(board[row][col]));
            }
        }
    }

    private boolean isValid(int[][] board, int row, int col, int num) {
        for (int x = 0; x < SIZE; x++) {
            if (board[row][x] == num || board[x][col] == num) {
                return false;
            }
        }

        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i + startRow][j + startCol] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean findEmptyLocation(int[][] board, int[] emptyPos) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == 0) {
                    emptyPos[0] = row;
                    emptyPos[1] = col;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean solveSudoku(int[][] board) {
        int[] emptyPos = new int[2];
        if (!findEmptyLocation(board, emptyPos)) {
            return true;
        }

        int row = emptyPos[0];
        int col = emptyPos[1];

        for (int num = 1; num <= 9; num++) {
            if (isValid(board, row, col, num)) {
                board[row][col] = num;

                if (solveSudoku(board)) {
                    return true;
                }

                board[row][col] = 0;
            }
        }

        return false;
    }
}
