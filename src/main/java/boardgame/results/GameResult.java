package boardgame.results;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import java.time.ZonedDateTime;

/**
 * Encapsulates the game outcome and provides methods for accessing and manipulating the result data.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonPropertyOrder({ "dateStarted", "redPlayer", "bluePlayer", "numberOfMovesRed", "numberOfMovesBlue", "winnerName" })

public class GameResult {

    /** The date and time when the game started. */
    @NonNull private ZonedDateTime dateStarted;

    /** The name of the red player. */
    @NonNull private String redPlayer;

    /** The name of the blue player. */
    @NonNull private String bluePlayer;

    /** The number of moves made by the red player. */
    private int numberOfMovesRed;

    /** The number of moves made by the blue player. */
    private int numberOfMovesBlue;

    /** The name of the winner. */
    private String winnerName;

}
