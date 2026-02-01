import java.util.ArrayList;

public class Test {
    
    public static void main(String[] args) {
        System.out.println("=== Tests pour Tic Tac Toe ===\n");
        
        // Test 1: Test basique de compilation et d'initialisation
        testBasicInitialization();
        
        // Test 2: Test de l'évaluation du plateau
        testBoardEvaluation();
        
        // Test 3: Test Minimax sur plateau vide (début de partie)
        testMinimaxEmptyBoard();
        
        // Test 4: Test Alpha-Beta sur plateau vide
        testAlphaBetaEmptyBoard();
        
        // Test 5: Test Minimax avec situation où X peut gagner
        testMinimaxWinningMove();
        
        // Test 6: Test Alpha-Beta avec situation où X peut gagner
        testAlphaBetaWinningMove();
        
        // Test 7: Test Minimax avec situation défensive
        testMinimaxDefensiveMove();
        
        // Test 8: Test Alpha-Beta avec situation défensive
        testAlphaBetaDefensiveMove();
        
        // Test 9: Comparer le nombre de noeuds explorés (Alpha-Beta devrait être plus efficace)
        compareAlgorithmEfficiency();
        
        // Test 10: Test avec joueur O comme MAX
        testPlayerO();
        
        // Test 11: Partie complète avec Minimax
        testFullGameMinimax();
        
        // Test 12: Partie complète avec Alpha-Beta
        testFullGameAlphaBeta();
        
        System.out.println("\n=== Tous les tests terminés ===");
    }
    
    private static void testBasicInitialization() {
        System.out.println("Test 1: Initialisation basique");
        try {
            Board board = new Board();
            CPUPlayer playerX = new CPUPlayer(Mark.X);
            CPUPlayer playerO = new CPUPlayer(Mark.O);
            System.out.println("✓ Initialisation réussie");
            System.out.println();
        } catch (Exception e) {
            System.out.println("✗ ÉCHEC: " + e.getMessage());
            e.printStackTrace();
            System.out.println();
        }
    }
    
    private static void testBoardEvaluation() {
        System.out.println("Test 2: Évaluation du plateau");
        Board board = new Board();
        
        // Test plateau vide
        int scoreEmpty = board.evaluate(Mark.X);
        System.out.println("Score plateau vide: " + scoreEmpty + " (attendu: 0)");
        
        // Test victoire X
        board.play(new Move(0, 0), Mark.X);
        board.play(new Move(0, 1), Mark.X);
        board.play(new Move(0, 2), Mark.X);
        int scoreXWin = board.evaluate(Mark.X);
        System.out.println("Score victoire X: " + scoreXWin + " (attendu: 100)");
        
        // Test défaite X (victoire O)
        Board board2 = new Board();
        board2.play(new Move(1, 0), Mark.O);
        board2.play(new Move(1, 1), Mark.O);
        board2.play(new Move(1, 2), Mark.O);
        int scoreXLoss = board2.evaluate(Mark.X);
        System.out.println("Score défaite X: " + scoreXLoss + " (attendu: -100)");
        
        if (scoreEmpty == 0 && scoreXWin == 100 && scoreXLoss == -100) {
            System.out.println("✓ Évaluation correcte");
        } else {
            System.out.println("✗ ÉCHEC: Évaluation incorrecte");
        }
        System.out.println();
    }
    
    private static void testMinimaxEmptyBoard() {
        System.out.println("Test 3: Minimax sur plateau vide");
        Board board = new Board();
        CPUPlayer player = new CPUPlayer(Mark.X);
        
        ArrayList<Move> moves = player.getNextMoveMinMax(board);
        int nodesExplored = player.getNumOfExploredNodes();
        
        System.out.println("Nombre de noeuds explorés: " + nodesExplored);
        System.out.println("Nombre de meilleurs coups: " + moves.size());
        if (!moves.isEmpty()) {
            System.out.print("Meilleurs coups: ");
            for (Move m : moves) {
                System.out.print("(" + m.getRow() + "," + m.getCol() + ") ");
            }
            System.out.println();
        }
        
        if (moves.isEmpty()) {
            System.out.println("✗ ÉCHEC: Aucun coup retourné");
        } else {
            System.out.println("✓ Coups générés");
        }
        System.out.println();
    }
    
    private static void testAlphaBetaEmptyBoard() {
        System.out.println("Test 4: Alpha-Beta sur plateau vide");
        Board board = new Board();
        CPUPlayer player = new CPUPlayer(Mark.X);
        
        ArrayList<Move> moves = player.getNextMoveAB(board);
        int nodesExplored = player.getNumOfExploredNodes();
        
        System.out.println("Nombre de noeuds explorés: " + nodesExplored);
        System.out.println("Nombre de meilleurs coups: " + moves.size());
        if (!moves.isEmpty()) {
            System.out.print("Meilleurs coups: ");
            for (Move m : moves) {
                System.out.print("(" + m.getRow() + "," + m.getCol() + ") ");
            }
            System.out.println();
        }
        
        if (moves.isEmpty()) {
            System.out.println("✗ ÉCHEC: Aucun coup retourné");
        } else {
            System.out.println("✓ Coups générés");
        }
        System.out.println();
    }
    
    private static void testMinimaxWinningMove() {
        System.out.println("Test 5: Minimax - détection coup gagnant");
        Board board = new Board();
        // Configuration: X peut gagner en (0,2)
        // X X .
        // O O .
        // . . .
        board.play(new Move(0, 0), Mark.X);
        board.play(new Move(0, 1), Mark.X);
        board.play(new Move(1, 0), Mark.O);
        board.play(new Move(1, 1), Mark.O);
        
        System.out.println("Configuration du plateau:");
        board.display();
        
        CPUPlayer player = new CPUPlayer(Mark.X);
        ArrayList<Move> moves = player.getNextMoveMinMax(board);
        int nodesExplored = player.getNumOfExploredNodes();
        
        System.out.println("Nombre de noeuds explorés: " + nodesExplored);
        System.out.println("Nombre de coups avec même score: " + moves.size());
        
        // Vérifier si (0,2) est dans les meilleurs coups
        boolean hasWinningMove = false;
        for (Move m : moves) {
            System.out.println("Coup suggéré: (" + m.getRow() + "," + m.getCol() + ")");
            if (m.getRow() == 0 && m.getCol() == 2) {
                hasWinningMove = true;
            }
        }
        
        if (hasWinningMove) {
            System.out.println("✓ Coup gagnant détecté");
        } else {
            System.out.println("✗ ÉCHEC: Coup gagnant non détecté");
        }
        System.out.println();
    }
    
    private static void testAlphaBetaWinningMove() {
        System.out.println("Test 6: Alpha-Beta - détection coup gagnant");
        Board board = new Board();
        // Configuration: X peut gagner en (0,2)
        board.play(new Move(0, 0), Mark.X);
        board.play(new Move(0, 1), Mark.X);
        board.play(new Move(1, 0), Mark.O);
        board.play(new Move(1, 1), Mark.O);
        
        System.out.println("Configuration du plateau:");
        board.display();
        
        CPUPlayer player = new CPUPlayer(Mark.X);
        ArrayList<Move> moves = player.getNextMoveAB(board);
        int nodesExplored = player.getNumOfExploredNodes();
        
        System.out.println("Nombre de noeuds explorés: " + nodesExplored);
        System.out.println("Nombre de coups avec même score: " + moves.size());
        
        boolean hasWinningMove = false;
        for (Move m : moves) {
            System.out.println("Coup suggéré: (" + m.getRow() + "," + m.getCol() + ")");
            if (m.getRow() == 0 && m.getCol() == 2) {
                hasWinningMove = true;
            }
        }
        
        if (hasWinningMove) {
            System.out.println("✓ Coup gagnant détecté");
        } else {
            System.out.println("✗ ÉCHEC: Coup gagnant non détecté");
        }
        System.out.println();
    }
    
    private static void testMinimaxDefensiveMove() {
        System.out.println("Test 7: Minimax - détection coup défensif");
        Board board = new Board();
        // Configuration: O peut gagner en (2,2), X doit bloquer
        // X O .
        // . O .
        // . . .
        board.play(new Move(0, 0), Mark.X);
        board.play(new Move(0, 1), Mark.O);
        board.play(new Move(1, 1), Mark.O);
        
        System.out.println("Configuration du plateau:");
        board.display();
        
        CPUPlayer player = new CPUPlayer(Mark.X);
        ArrayList<Move> moves = player.getNextMoveMinMax(board);
        int nodesExplored = player.getNumOfExploredNodes();
        
        System.out.println("Nombre de noeuds explorés: " + nodesExplored);
        System.out.println("Coups suggérés:");
        for (Move m : moves) {
            System.out.println("  (" + m.getRow() + "," + m.getCol() + ")");
        }
        
        // X devrait bloquer en (2,2)
        boolean hasDefensiveMove = false;
        for (Move m : moves) {
            if (m.getRow() == 2 && m.getCol() == 2) {
                hasDefensiveMove = true;
            }
        }
        
        if (hasDefensiveMove) {
            System.out.println("✓ Coup défensif détecté");
        } else {
            System.out.println("✗ ÉCHEC: Coup défensif non détecté");
        }
        System.out.println();
    }
    
    private static void testAlphaBetaDefensiveMove() {
        System.out.println("Test 8: Alpha-Beta - détection coup défensif");
        Board board = new Board();
        // Configuration: O peut gagner en (2,2), X doit bloquer
        board.play(new Move(0, 0), Mark.X);
        board.play(new Move(0, 1), Mark.O);
        board.play(new Move(1, 1), Mark.O);
        
        System.out.println("Configuration du plateau:");
        board.display();
        
        CPUPlayer player = new CPUPlayer(Mark.X);
        ArrayList<Move> moves = player.getNextMoveAB(board);
        int nodesExplored = player.getNumOfExploredNodes();
        
        System.out.println("Nombre de noeuds explorés: " + nodesExplored);
        System.out.println("Coups suggérés:");
        for (Move m : moves) {
            System.out.println("  (" + m.getRow() + "," + m.getCol() + ")");
        }
        
        boolean hasDefensiveMove = false;
        for (Move m : moves) {
            if (m.getRow() == 2 && m.getCol() == 2) {
                hasDefensiveMove = true;
            }
        }
        
        if (hasDefensiveMove) {
            System.out.println("✓ Coup défensif détecté");
        } else {
            System.out.println("✗ ÉCHEC: Coup défensif non détecté");
        }
        System.out.println();
    }
    
    private static void compareAlgorithmEfficiency() {
        System.out.println("Test 9: Comparaison d'efficacité Minimax vs Alpha-Beta");
        Board board = new Board();
        // Plateau après quelques coups
        board.play(new Move(1, 1), Mark.X);
        board.play(new Move(0, 0), Mark.O);
        board.play(new Move(2, 2), Mark.X);
        
        System.out.println("Configuration du plateau:");
        board.display();
        
        CPUPlayer playerMM = new CPUPlayer(Mark.X);
        ArrayList<Move> movesMM = playerMM.getNextMoveMinMax(board);
        int nodesMM = playerMM.getNumOfExploredNodes();
        
        CPUPlayer playerAB = new CPUPlayer(Mark.X);
        ArrayList<Move> movesAB = playerAB.getNextMoveAB(board);
        int nodesAB = playerAB.getNumOfExploredNodes();
        
        System.out.println("Minimax - Noeuds explorés: " + nodesMM);
        System.out.println("Alpha-Beta - Noeuds explorés: " + nodesAB);
        System.out.println("Réduction: " + (nodesMM - nodesAB) + " noeuds (" + 
                          String.format("%.1f", 100.0 * (nodesMM - nodesAB) / nodesMM) + "%)");
        
        if (nodesAB <= nodesMM) {
            System.out.println("✓ Alpha-Beta est au moins aussi efficace que Minimax");
        } else {
            System.out.println("✗ ATTENTION: Alpha-Beta explore plus de noeuds que Minimax");
        }
        System.out.println();
    }
    
    private static void testPlayerO() {
        System.out.println("Test 10: Test avec O comme joueur MAX");
        Board board = new Board();
        board.play(new Move(0, 0), Mark.X);
        
        System.out.println("Configuration du plateau:");
        board.display();
        
        CPUPlayer playerO = new CPUPlayer(Mark.O);
        ArrayList<Move> moves = playerO.getNextMoveMinMax(board);
        
        System.out.println("Coups suggérés pour O:");
        for (Move m : moves) {
            System.out.println("  (" + m.getRow() + "," + m.getCol() + ")");
        }
        
        if (!moves.isEmpty()) {
            System.out.println("✓ Agent peut jouer avec O");
        } else {
            System.out.println("✗ ÉCHEC: Aucun coup généré pour O");
        }
        System.out.println();
    }
    
    private static void testFullGameMinimax() {
        System.out.println("Test 11: Partie complète avec Minimax (X vs O)");
        Board board = new Board();
        CPUPlayer playerX = new CPUPlayer(Mark.X);
        CPUPlayer playerO = new CPUPlayer(Mark.O);
        
        int turnCount = 0;
        Mark currentPlayer = Mark.X;
        
        while (!board.isGameOver() && turnCount < 9) {
            System.out.println("Tour " + (turnCount + 1) + " - Joueur " + currentPlayer + ":");
            
            ArrayList<Move> moves;
            if (currentPlayer == Mark.X) {
                moves = playerX.getNextMoveMinMax(board);
            } else {
                moves = playerO.getNextMoveMinMax(board);
            }
            
            if (moves.isEmpty()) {
                System.out.println("Aucun coup disponible!");
                break;
            }
            
            Move move = moves.get(0);
            board.play(move, currentPlayer);
            System.out.println("Coup joué: (" + move.getRow() + "," + move.getCol() + ")");
            board.display();
            
            currentPlayer = (currentPlayer == Mark.X) ? Mark.O : Mark.X;
            turnCount++;
        }
        
        if (board.hasWon(Mark.X)) {
            System.out.println("Résultat: X a gagné");
        } else if (board.hasWon(Mark.O)) {
            System.out.println("Résultat: O a gagné");
        } else {
            System.out.println("Résultat: Match nul");
        }
        
        System.out.println("✓ Partie complète terminée");
        System.out.println();
    }
    
    private static void testFullGameAlphaBeta() {
        System.out.println("Test 12: Partie complète avec Alpha-Beta (X vs O)");
        Board board = new Board();
        CPUPlayer playerX = new CPUPlayer(Mark.X);
        CPUPlayer playerO = new CPUPlayer(Mark.O);
        
        int turnCount = 0;
        Mark currentPlayer = Mark.X;
        
        while (!board.isGameOver() && turnCount < 9) {
            System.out.println("Tour " + (turnCount + 1) + " - Joueur " + currentPlayer + ":");
            
            ArrayList<Move> moves;
            if (currentPlayer == Mark.X) {
                moves = playerX.getNextMoveAB(board);
            } else {
                moves = playerO.getNextMoveAB(board);
            }
            
            if (moves.isEmpty()) {
                System.out.println("Aucun coup disponible!");
                break;
            }
            
            Move move = moves.get(0);
            board.play(move, currentPlayer);
            System.out.println("Coup joué: (" + move.getRow() + "," + move.getCol() + ")");
            board.display();
            
            currentPlayer = (currentPlayer == Mark.X) ? Mark.O : Mark.X;
            turnCount++;
        }
        
        if (board.hasWon(Mark.X)) {
            System.out.println("Résultat: X a gagné");
        } else if (board.hasWon(Mark.O)) {
            System.out.println("Résultat: O a gagné");
        } else {
            System.out.println("Résultat: Match nul");
        }
        
        System.out.println("✓ Partie complète terminée");
        System.out.println();
    }
}
