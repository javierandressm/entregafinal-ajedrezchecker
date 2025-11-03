public class FenValidator {

    public void validate(String fen) throws Exception {
        String[] parts = fen.trim().split(" ");
        if (parts.length != 6)
            throw new Exception("Debe contener 6 campos separados por espacio.");

        String board = parts[0];
        validateBoard(board);
        validateTurn(parts[1]);
        validateCastling(parts[2]);
        validateEnPassant(parts[3]);
        validateNumber(parts[4], "Halfmove clock");
        validateNumber(parts[5], "Fullmove counter");
    }

    private void validateBoard(String board) throws Exception {
        String[] ranks = board.split("/");
        if (ranks.length != 8)
            throw new Exception("El tablero debe tener 8 filas.");

        for (int i = 0; i < 8; i++) {
            String rank = ranks[i];
            int count = 0;
            for (char c : rank.toCharArray()) {
                if (Character.isDigit(c)) {
                    int n = c - '0';
                    if (n < 1 || n > 8)
                        throw new Exception("Número inválido en la fila " + (i + 1));
                    count += n;
                } else if ("PNBRQKpnbrqk".indexOf(c) != -1) {
                    count++;
                } else {
                    throw new Exception("Símbolo inválido '" + c + "' en la fila " + (i + 1));
                }
            }
            if (count != 8)
                throw new Exception("Fila " + (i + 1) + " tiene " + count + " casillas (debe tener 8).");
        }
    }

    private void validateTurn(String s) throws Exception {
        if (!s.equals("w") && !s.equals("b"))
            throw new Exception("El turno debe ser 'w' o 'b'.");
    }

    private void validateCastling(String s) throws Exception {
        if (!s.equals("-") && !s.matches("[KQkq]{1,4}"))
            throw new Exception("Campo de enroque inválido.");
    }

    private void validateEnPassant(String s) throws Exception {
        if (!s.equals("-") && !s.matches("^[a-h][36]$"))
            throw new Exception("Campo En Passant inválido.");
    }

    private void validateNumber(String s, String name) throws Exception {
        if (!s.matches("\\d+"))
            throw new Exception(name + " debe ser un número.");
    }
}
