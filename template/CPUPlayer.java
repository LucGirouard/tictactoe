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
    private Mark cpu;

    // Le constructeur reçoit en paramètre le
    // joueur MAX (X ou O)
    public CPUPlayer(Mark cpu){
        numExploredNodes = 0;
        this.cpu = cpu;
    }

    // Ne pas changer cette méthode
    public int  getNumOfExploredNodes(){
        return numExploredNodes;
    }

    public Mark getMark(){
        return cpu;
    }

    // Retourne la liste des coups possibles.  Cette liste contient
    // plusieurs coups possibles si et seuleument si plusieurs coups
    // ont le même score.
    public ArrayList<Move> getNextMoveMinMax(Board board) {
        numExploredNodes = 0;
        int bestValue = Integer.MIN_VALUE;
        ArrayList<Move> bestMoves = new ArrayList<Move>();
        ArrayList<Move> possibleMoves = board.getPossibleMoves();
        int depth = possibleMoves.size();

        for (Move move : possibleMoves) {
            board.play(move, cpu);
            //demande a mini max d'aller checher le meilleur coup...
            int moveValue = miniMax(board, getOpponentMark(cpu), depth - 1);
            board.play(move, Mark.EMPTY); // annule le coup (simuler)

            if (moveValue > bestValue) {
                bestValue = moveValue;
                bestMoves.clear();
                bestMoves.add(move);
            } else if (moveValue == bestValue) {
                bestMoves.add(move);
            }
        }
        return bestMoves;
    }


    public int miniMax(Board board, Mark mark, int depth){
        numExploredNodes++;
        int bestScore;
        int tempScore;

        int score = board.evaluate(cpu);
        if(score == 100 || score == -100 || depth == 0 || board.getPossibleMoves().isEmpty()) {
            return score;
        }

        if(mark == cpu){
            bestScore = Integer.MIN_VALUE;
            for( Move move : board.getPossibleMoves()){
                board.play(move, mark);
                tempScore = miniMax(board, getOpponentMark(mark), depth - 1);
                board.play(move, Mark.EMPTY); //annule "move", pour simuler le fait de jouer
                if(tempScore > bestScore){ //veut le meilleur "score"
                    bestScore = tempScore;
                }
            }
            return bestScore;
        } 
        // minimizing player
        else{
            bestScore = Integer.MAX_VALUE;
            for( Move move : board.getPossibleMoves()){
                board.play(move, mark);
                tempScore = miniMax(board, getOpponentMark(mark), depth - 1);
                board.play(move, Mark.EMPTY); //annule "move", pour simuler le fait de jouer
                if(tempScore < bestScore){ //veut le pire "score"
                    bestScore = tempScore;
                }
            }
            return bestScore;
        }
    }

    private Mark getOpponentMark(Mark mark) {
        return (mark == Mark.X) ? Mark.O : Mark.X;
    }

    // Retourne la liste des coups possibles.  Cette liste contient
    // plusieurs coups possibles si et seuleument si plusieurs coups
    // ont le même score.
    public ArrayList<Move> getNextMoveAB(Board board){
        numExploredNodes = 0;

    }

}
