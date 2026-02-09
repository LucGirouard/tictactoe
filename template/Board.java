import java.util.ArrayList;

// IMPORTANT: Il ne faut pas changer la signature des méthodes
// de cette classe, ni le nom de la classe.
// Vous pouvez par contre ajouter d'autres méthodes (ça devrait 
// être le cas)
class Board
{
    private Mark[][] board;

    // Ne pas changer la signature de cette méthode
    public Board() {
        // initialisation du plateau
        board = new Mark[3][3];

        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                board[i][j] = Mark.EMPTY;
            }
        }
    }

    // Place la pièce 'mark' sur le plateau, à la
    // position spécifiée dans Move
    //
    // Ne pas changer la signature de cette méthode
    public void play(Move m, Mark mark){
        board[m.getRow()][m.getCol()] = mark;
    }


    // retourne  100 pour une victoire
    //          -100 pour une défaite
    //           0   pour un match nul
    // Ne pas changer la signature de cette méthode
    public int evaluate(Mark mark){
        Mark opponentMark;
        
        // initialisation du mark de l'adversaire
        if( mark == Mark.X){
            opponentMark = Mark.O;
        } else {
            opponentMark = Mark.X;
        }

        // vérifier les lignes et les colonnes
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == mark && board[i][1] == mark && board[i][2] == mark)
                return 100;
            if (board[0][i] == mark && board[1][i] == mark && board[2][i] == mark)
                return 100;
            if (board[i][0] == opponentMark && board[i][1] == opponentMark && board[i][2] == opponentMark)
                return -100;
            if (board[0][i] == opponentMark && board[1][i] == opponentMark && board[2][i] == opponentMark)
                return -100;
        }

        // vérifier les diagonales
        if (board[0][0] == mark && board[1][1] == mark && board[2][2] == mark)
            return 100;
        if (board[0][2] == mark && board[1][1] == mark && board[2][0] == mark)
            return 100;
        if (board[0][0] == opponentMark && board[1][1] == opponentMark && board[2][2] == opponentMark)
            return -100;
        if (board[0][2] == opponentMark && board[1][1] == opponentMark && board[2][0] == opponentMark)
            return -100;

        // vérifier le match nul
        boolean isDraw = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == Mark.EMPTY) {
                    isDraw = false;
                    break;
                }
            }
        }
        if (isDraw) {
            return 0;
        }

        // partie en cours
        return 0;
    }

    // génère la liste des coups possibles (cases vides)
    public ArrayList<Move> getPossibleMoves(){
        ArrayList<Move> possibleMoves = new ArrayList<Move>();
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if(board[i][j] == Mark.EMPTY){
                    possibleMoves.add( new Move(i, j));
                }
            }
        }
        return possibleMoves;

    }

    public void display() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean isGameOver() {
        return evaluate(Mark.X) == 100 || evaluate(Mark.O) == 100 || getPossibleMoves().isEmpty();
    }
}
