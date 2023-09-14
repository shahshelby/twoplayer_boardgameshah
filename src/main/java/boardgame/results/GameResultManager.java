package boardgame.results;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementations of this interface are responsible for saving and retrieving game results.
 */
public interface GameResultManager {

    /**
     * Adds a game result to the manager.
     *
     * @param result the game result to be added
     * @return the updated list of game results
     * @throws IOException if any I/O error occurs while adding the game result
     */
    List<GameResult> add(GameResult result) throws IOException;

    /**
     * Retrieves all game results from the manager.
     *
     * @return the list of all game results
     * @throws IOException if any I/O error occurs while retrieving the game result
     */
    List<GameResult> getAll() throws IOException;

    /**
     * Retrieves the best players with the highest number of wins.
     *
     * @param limit the maximum number of the best players to retrieve (e.g., 5)
     * @return the list of high score results
     * @throws IOException if any I/O error occurs while retrieving the high score results
     */
    default List<HighscoreResult> getBestPlayers(int limit) throws IOException {
        var winnerMap = getAll()
                .stream()
                .collect(Collectors.groupingBy(GameResult::getWinnerName, Collectors.counting()));
        return winnerMap.entrySet()
                .stream()
                .map(entry -> new HighscoreResult(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparingLong(HighscoreResult::getNumberOfWins).reversed())
                .limit(limit)
                .toList();
    }
}
