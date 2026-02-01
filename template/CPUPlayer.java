import java.util.ArrayList;

// IMPORTANT: Il ne faut pas changer la signature des méthodes
// de cette classe, ni le nom de la classe.
// Vous pouvez par contre ajouter d'autres méthodes (ça devrait 
// être le cas)
class CPUPlayer
{

    // Contient le nombre de noeuds visités (le nombre
    // d'appel à la fonction MinMax ou Alpha Beta)
    // Normalement, la variable devrait être incrémentée
    // au début de votre MinMax ou Alpha Beta.
    private int numExploredNodes;
    private Mark maxPlayer;

    // Le constructeur reçoit en paramètre le
    // joueur MAX (X ou O)
    public CPUPlayer(Mark cpu){
        this.maxPlayer = cpu;
    }

    // Ne pas changer cette méthode
    public int  getNumOfExploredNodes(){
        return numExploredNodes;
    }

    // Retourne la liste des coups possibles.  Cette liste contient
    // plusieurs coups possibles si et seuleument si plusieurs coups
    // ont le même score.
    public ArrayList<Move> getNextMoveMinMax(Board board)
    {
        numExploredNodes = 0;
        ArrayList<Move> possibleMoves = board.getPossibleMoves();
        ArrayList<Move> bestMoves = new ArrayList<>();
        int bestScore = Integer.MIN_VALUE;

        for (Move move : possibleMoves) {
            board.play(move, maxPlayer);
            int score = minimax(board, false);
            board.play(move, Mark.EMPTY);

            if (score > bestScore) {
                bestScore = score;
                bestMoves.clear();
                bestMoves.add(move);
            } else if (score == bestScore) {
                bestMoves.add(move);
            }
        }

        return bestMoves;
    }

    // Retourne la liste des coups possibles.  Cette liste contient
    // plusieurs coups possibles si et seuleument si plusieurs coups
    // ont le même score.
    public ArrayList<Move> getNextMoveAB(Board board){
        numExploredNodes = 0;
        ArrayList<Move> possibleMoves = board.getPossibleMoves();
        ArrayList<Move> bestMoves = new ArrayList<>();
        int bestScore = Integer.MIN_VALUE;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        for (Move move : possibleMoves) {
            board.play(move, maxPlayer);
            int score = alphaBeta(board, false, alpha, beta);
            board.play(move, Mark.EMPTY);

            if (score > bestScore) {
                bestScore = score;
                bestMoves.clear();
                bestMoves.add(move);
            } else if (score == bestScore) {
                bestMoves.add(move);
            }

            alpha = Math.max(alpha, score);
        }

        return bestMoves;
    }

    private int minimax(Board board, boolean isMaximizing) {
        numExploredNodes++;

        if (board.isGameOver()) {
            return board.evaluate(maxPlayer);
        }

        Mark opponent = (maxPlayer == Mark.X) ? Mark.O : Mark.X;
        ArrayList<Move> possibleMoves = board.getPossibleMoves();

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (Move move : possibleMoves) {
                board.play(move, maxPlayer);
                int score = minimax(board, false);
                board.play(move, Mark.EMPTY);
                bestScore = Math.max(bestScore, score);
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (Move move : possibleMoves) {
                board.play(move, opponent);
                int score = minimax(board, true);
                board.play(move, Mark.EMPTY);
                bestScore = Math.min(bestScore, score);
            }
            return bestScore;
        }
    }

    private int alphaBeta(Board board, boolean isMaximizing, int alpha, int beta) {
        numExploredNodes++;

        if (board.isGameOver()) {
            return board.evaluate(maxPlayer);
        }

        Mark opponent = (maxPlayer == Mark.X) ? Mark.O : Mark.X;
        ArrayList<Move> possibleMoves = board.getPossibleMoves();

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (Move move : possibleMoves) {
                board.play(move, maxPlayer);
                int score = alphaBeta(board, false, alpha, beta);
                board.play(move, Mark.EMPTY);
                bestScore = Math.max(bestScore, score);
                alpha = Math.max(alpha, score);
                if (beta <= alpha) {
                    break;
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (Move move : possibleMoves) {
                board.play(move, opponent);
                int score = alphaBeta(board, true, alpha, beta);
                board.play(move, Mark.EMPTY);
                bestScore = Math.min(bestScore, score);
                beta = Math.min(beta, score);
                if (beta <= alpha) {
                    break;
                }
            }
            return bestScore;
        }
    }

}
