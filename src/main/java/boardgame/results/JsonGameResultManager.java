package boardgame.results;

import lombok.NonNull;
import boardgame.util.JacksonHelper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages game results by storing and retrieving them from a JSON file.
 */
public class JsonGameResultManager implements GameResultManager {

    private Path filePath;

    /**
     * Constructs a {@code JsonGameResultManager} with the specified file path.
     *
     * @param filePath the path to the JSON file
     */
    public JsonGameResultManager(@NonNull Path filePath) {
        this.filePath = filePath;
    }

    /**
     * Adds a game result to the manager and updates the JSON file.
     *
     * @param result the game result to be added
     * @return the updated list of game results
     * @throws IOException if any I/O error occurs when adding game results to the JSON file
     */
    @Override
    public List<GameResult> add(@NonNull GameResult result) throws IOException {
        var results = Files.exists(filePath) ? getAll() : new ArrayList<GameResult>();
        results.add(result);
        try (var out = Files.newOutputStream(filePath)) {
            JacksonHelper.writeList(out, results);
        }
        return results;
    }

    /**
     * Retrieves all game results stored in the JSON file.
     *
     * @return the list of all game results
     * @throws IOException if any I/O error occurs when retrieving game results in JSON database
     */
    public List<GameResult> getAll() throws IOException {
        try (var in = Files.newInputStream(filePath)) {
            return JacksonHelper.readList(in, GameResult.class);
        }
    }
}