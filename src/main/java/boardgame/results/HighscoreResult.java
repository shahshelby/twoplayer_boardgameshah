package boardgame.results;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

/**
 * Represents a high score result, storing the name of the winner and the total number of wins.
 */
@Data
@AllArgsConstructor
public class HighscoreResult {

    /** The name of the winner. */
    @NonNull private String winnerName;

    /** The total number of wins. */
    private long numberOfWins;
}