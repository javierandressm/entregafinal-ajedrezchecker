public class ChessBoardPrinter {
    private static final java.util.Map<Character, String> unicodePieces = new java.util.HashMap<>();

    static {
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

    public static void printBoard(String fen) {
        String[] parts = fen.split(" ");
        String[] ranks = parts[0].split("/");

        System.out.println("Tablero generado:\n");

        for (String rank : ranks) {
            for (char c : rank.toCharArray()) {
                if (Character.isDigit(c)) {
                    int spaces = c - '0';
                    for (int i = 0; i < spaces; i++) System.out.print(". ");
                } else {
                    System.out.print(unicodePieces.getOrDefault(c, "?") + " ");
                }
            }
            System.out.println();
        }
    }
}