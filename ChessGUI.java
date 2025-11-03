import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ChessGUI extends JFrame {
    private final JTextField fenField = new JTextField(50);
    private final JButton renderButton = new JButton("Mostrar Tablero");
    private final JLabel statusLabel = new JLabel(" ");
    private final JPanel boardPanel = new JPanel(new GridLayout(8, 8));

    private final Map<Character, String> unicodePieces = new HashMap<>();

    public ChessGUI() {
        super("FEN Viewer");
        initPieceMap();
        buildUI();
        attachHandlers();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(640, 720);
        setLocationRelativeTo(null);
    }

    private void initPieceMap() {
        unicodePieces.put('K', "♔");
        unicodePieces.put('Q', "♕");
        unicodePieces.put('R', "♖");
        unicodePieces.put('B', "♗");
        unicodePieces.put('N', "♘");
        unicodePieces.put('P', "♙");
        unicodePieces.put('k', "♚");
        unicodePieces.put('q', "♛");
        unicodePieces.put('r', "♜");
        unicodePieces.put('b', "♝");
        unicodePieces.put('n', "♞");
        unicodePieces.put('p', "♟");
    }

    private void buildUI() {
        JPanel inputPanel = new JPanel(new BorderLayout(8, 8));
        JPanel topRow = new JPanel(new BorderLayout(8, 8));
        topRow.add(new JLabel("Cadena FEN:"), BorderLayout.WEST);
        topRow.add(fenField, BorderLayout.CENTER);
        topRow.add(renderButton, BorderLayout.EAST);
        inputPanel.add(topRow, BorderLayout.NORTH);

        statusLabel.setForeground(new Color(0x0A7A0A));
        inputPanel.add(statusLabel, BorderLayout.SOUTH);

        boardPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        rebuildEmptyBoard();

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(inputPanel, BorderLayout.NORTH);
        getContentPane().add(boardPanel, BorderLayout.CENTER);

        String sample = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        fenField.setText(sample);
    }

    private void attachHandlers() {
        renderButton.addActionListener(e -> renderFromFEN());
        fenField.addActionListener(e -> renderFromFEN());
    }

    private void renderFromFEN() {
        String fen = fenField.getText() == null ? "" : fenField.getText().trim();
        FenValidator validator = new FenValidator();
        try {
            validator.validate(fen);
            statusLabel.setText("FEN válida.");
            statusLabel.setForeground(new Color(0x0A7A0A));
            drawBoard(fen);
        } catch (Exception ex) {
            statusLabel.setText("Error: " + ex.getMessage());
            statusLabel.setForeground(new Color(0xB00020));
        }
    }

    private void rebuildEmptyBoard() {
        boardPanel.removeAll();
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                JLabel cell = createCell(r, c);
                boardPanel.add(cell);
            }
        }
        boardPanel.revalidate();
        boardPanel.repaint();
    }

    private JLabel createCell(int row, int col) {
        JLabel label = new JLabel("", SwingConstants.CENTER);
        label.setOpaque(true);
        label.setFont(label.getFont().deriveFont(Font.PLAIN, 36f));
        Color light = new Color(240, 217, 181); // light brown
        Color dark = new Color(181, 136, 99);   // dark brown
        label.setBackground(((row + col) % 2 == 0) ? light : dark);
        label.setForeground(((row + col) % 2 == 0) ? Color.BLACK : Color.WHITE);
        label.setBorder(BorderFactory.createLineBorder(new Color(120, 120, 120)));
        label.setPreferredSize(new Dimension(64, 64));
        return label;
    }

    private void drawBoard(String fen) {
        String[] parts = fen.split(" ");
        String[] ranks = parts[0].split("/");

        boardPanel.removeAll();
        for (int r = 0; r < 8; r++) {
            String rank = ranks[r];
            int col = 0;
            for (int i = 0; i < rank.length(); i++) {
                char ch = rank.charAt(i);
                if (Character.isDigit(ch)) {
                    int spaces = ch - '0';
                    for (int s = 0; s < spaces; s++) {
                        JLabel cell = createCell(r, col);
                        cell.setText("");
                        boardPanel.add(cell);
                        col++;
                    }
                } else {
                    JLabel cell = createCell(r, col);
                    cell.setText(unicodePieces.getOrDefault(ch, "?"));
                    boardPanel.add(cell);
                    col++;
                }
            }
        }
        boardPanel.revalidate();
        boardPanel.repaint();
    }
}
