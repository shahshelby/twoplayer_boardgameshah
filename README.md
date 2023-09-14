# Board Game

The following game is played by two players.

Consider a game board consisting of 5x5 cells, each of which can contain a piece of stone, and a set of colored (red and blue) stones. The first player plays with the red stones, the other with the blue stones. The board is initially empty. Players move in turn. In a move, a player must choose an empty cell and put a stone of his or her color into it. The game is over when a row, a column, or a diagonal contains 3 stones of the same color. (All possible diagonals will be considered, not only the main diagonal and the antidiagonal). The three stones do not have to be adjacent. The player to which these stones belong loses the game.

When the game is started, the program will ask for the names of the players.


For each game, the following information will be stored:
- The date and time when the game was started
- The name of the players
- The number of turns made by the players during the game
- The name of the winner

The program will be able to display a high score table in which the top 5 players with the most wins are displayed.

> **Note:** The game will also store data in a database in a JSON file.