import java.util.Scanner;

public class ConnectFour {
	public static void main(String[] args) {
		/*	This function is the starting function of the program
		 * 	its job is to initialize the board and some of the optional
		 * 	choices, such as the number of connections to win the game.
		 * 	Along with that it also receives user input and directs the
		 * 	user.
		 * 	
		 * 	Returns: N/A
		 */
		String command;
		String error;
		int[][] board = {
				{1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},
				{1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},
				{1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},
				{1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},
				{1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},
				{1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},
				{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2}
		}; // board creation and initialization
		int winCount = 0; // connections to win
		Scanner input = new Scanner(System.in); // Retrieve user input
		while (true) { // loops for player input
			command = "";
			System.out.println("Enter Command");
			System.out.println("q:quit / p: play");
			try { // catches players entering weird and random inputs
				command = input.next();
				if (command.equalsIgnoreCase("q")) { // quit
					System.out.println("Thank you for Playing!");
					System.exit(0);
				}
				if (command.equalsIgnoreCase("p")) { // play
					System.out.println("Playing");
					System.out.println("Enter Win count (3,4,5)");
					try {
						winCount = input.nextInt(); // Retrieve next integer input
					}
					catch (Exception e){ // except if user enters in invalid character
						winCount = 0;
					}
					if (winCount > 2 && winCount < 6) { // checks that winCount is in range
						System.out.println("Continuing");
						game(board, winCount);
						break; // ends game
					}
					else { // User entered in wrong number
						System.out.println("Invalid Win Count");
					}
						
				}
				else { // something has broken with user input
					System.out.println("Invalid entry");
				}
			}
			catch (Exception e) {
				System.out.println("Error! Please don't do what you just did again!");
			}
		}
		input.close();
	}
	
	public static int vert_Check(int[][] board, int winCount, int team) {
		/*	This function checks the vertical axis for a player victory
		 * 	
		 * 	Return: integer (value between 0-2)
		 */
		// Vertical check
		boolean win = true; // team that wins
		for (int i = 0; i < 7; i++) { // vertical loop
			for (int j = 0; j < 7; j++) { // horizontal loop
				if (board[i][column_finder(j)] == team) { // finds yellow token
					win = true;
					for (int k = 0;k < winCount; k++) { // checks next few token in vertical direction
						try {
							if (board[i+k][column_finder(j)] != team) { // token doesn't = 3, false
								win = false;
							}
						}
						catch (Exception e) { // catches out of bounds errors
							win = false;
						}
					}
					if (win && team == 3) { // return yellow win
						return 1;
					}
					else if (win && team == 4) {
						return 2;
					}
				}
			}
		}
		return 0; // continue with game
	}
	
	public static int horizontal_Check(int[][] board, int winCount, int team) {
		/*	This function checks the horizontal axis for a player victory
		 * 
		 * 	Return: integer (0-2)
		 */	
		boolean win = true; // team that wins
		for (int i = 0; i < 7; i++) { // vertical
			for (int j = 0; j < 7; j++) { // horizontal
				if (board[i][column_finder(j)] == team) { // yellow
					win = true;
					for (int k = 0;k < winCount; k++) { // checks next few token horizontally
						try {
							if (board[i][column_finder(j+k)] != team) { // if token not yellow, False
								win = false;
							}
						}
						catch (Exception e) { // On exception, False
							win = false;
						}
					}
					if (win && team == 3) { // Return yellow
						return 1;
					}
					else if (win && team == 4) { // Return red
						return 2;
					}
				}
			}
		}
		return 0; // continue with game
	}
	
	public static int diagonal_Check(int[][] board, int winCount, int[] direction,int team) {
		/*	This function checks the diagonal axis for a player victory
		 * 
		 * 	Return: integer (0-2)
		 */	
		boolean win = true; // team
		for (int i = 0; i < 7; i++) { // vertical
			for (int j = 0; j < 7; j++) { // horizontal
				if (board[i][column_finder(j)] == team) { // Y
					win = true;
					for (int k = 0;k < winCount; k++) { // check next token
						try {
							// if not yellow, false
							if (board[i + (direction[0]*k)][column_finder(j + (direction[1]*k))] != team) {
								win = false;
							}
						}
						catch (Exception e) { // catch exceptions
							win = false;
						}
					}
					if (win && team == 3) { // yellow win
						return 1;
					}
					else if (win && team == 4) { // Red win
						return 2;
					}
				}
			}
		}
		return 0; // continue game
	}
	
	public static int win_Check(int[][] board, int winCount) {
		/*	This function is the driver code for the three axis win checks
		 * 	it calls those three functions and passes the appropriate
		 * 	variables to said function, before then returning the victor
		 * 	to the game function. This function also determines if there
		 * 	has been a draw.
		 * 
		 * 	Return:	Integer (0-3)
		 */
		// draw check -- note draws are only counted if every space
		// has been filled with a token.
		// Draw check
		int draw_Counter = 0;
		int ending = 0; // initialize ending
		for (int i = 0; i <= 7; i++) { // loops thru top of row of board
			if (board[0][column_finder(i)] > 0) {
				draw_Counter += 1;
			}
		}
		if (draw_Counter > 7) { // top row full
			return 3; // return tie
		}
		// Vertical check
		ending = vert_Check(board,winCount, 3); // Yellow check
		if (ending > 0) { // return ending if not continue
			return ending;
		}
		ending = vert_Check(board,winCount, 4); // Red check
		if (ending > 0) { // return ending if not continue
			return ending;
		}
		// Horizontal check
		ending = horizontal_Check(board,winCount, 3);
		if (ending > 0) { // return ending if not continue
			return ending;
		}
		ending = horizontal_Check(board,winCount, 4);
		if (ending > 0) { // return ending if not continue
			return ending;
		}
		// diagonal check
		// variables for diagonal directions
		int[] topLeft = {1,-1};
		int[] topRight = {1,1};
		int[] downLeft = {-1,-1};
		int[] downRight = {-1,1};
		for (int team = 3; team <= 4; team++) { // checks both teams
			ending = diagonal_Check(board,winCount,topLeft, team);
			if (ending > 0) { // return ending if top-left diagonal wins
				return ending;
			}
			ending = diagonal_Check(board,winCount,topRight, team);
			if (ending > 0) { // return ending if top-right diagonal wins
				return ending;
			}
			ending = diagonal_Check(board,winCount,downLeft, team);
			if (ending > 0) { // return ending if down-left diagonal wins
				return ending;
			}
			ending = diagonal_Check(board,winCount,downRight, team);
			if (ending > 0) { // return ending if down-right diagonal wins
				return ending;
			}
		}
			
		return ending; // return whatever ending is at this point (should be 0)
	}
	
	public static int column_finder(int column) {
		/*	This function takes an inputed column
		 * 	and turns that input into the actual placements of said column
		 * 	ie: user enters in 5, column finder turns that 5 into 9
		 * 	ie: user enters 7, column finder turns that into 13
		 * 	
		 * 	Returns: integer (1-13)
		 */
		if (column == 2) {
			// weird glitch with 2 that I didn't want to figure out
			// is why this if statement exists
			return 3;
		}
		// for all numbers but 2, change input into proper column
		// input
		else if(column > 0) {
			return (column * 2)-1;
		}
		else {
			return 0;
		}
	}
	
	public static int[][] place_Token(int[][] board, int team, int column) {
		/*	This function is the one that places a token on the board.
		 * 
		 * 	Return: Array
		 */
		for (int layer = 0; layer < 8; layer ++) { // iterates thru and places token
			if (board[layer + 1][column] > 1) {
				if (team == 0) {
					board[layer][column] = 3;
					break;
				}
				else {
					board[layer][column] = 4;
					break;
				}
			}
		}
		return board;
	}
	
	public static void game(int[][] board, int winCount) {
		/*	This function is the driver code for the display_board function, 
		 * 	win_Check function and the place_token function. This function
		 * 	also directs the player to enter in the required commands and 
		 * 	determines what the player wishes to do.
		 * 
		 * 	Return: N/A
		 */
		int winner = 0; // initializes variables
		int i = 0;
		int token_placement = 0;
		String cont;
		String error = "BAD"; // used to prevent infinite loop
		Scanner input = new Scanner(System.in);
		while (true) {
			token_placement = 0;
			display_board(board);
			if (i % 2 == 0) {// yellow team
				System.out.println("Yellow Turn!");
				System.out.println("Select Column to place token! (1-7)");
				try {
					token_placement = input.nextInt(); // user enters column
				}
				catch(Exception e) { // error catching
					System.out.println("Invalid entry");
					error = input.next(); // prevents infinite loop
					token_placement = 0;
				}
				if (token_placement < 1 || token_placement > 7) {
					System.out.println("Invalid column number");
				}
				else if (board[0][column_finder(token_placement)] > 0) { // full column
					System.out.println("That Column is full!");
				}
				else { // next turn + token placement
					i += 1;
					board = place_Token(board,0, column_finder(token_placement));
				}
			}
			else {
				System.out.println("Red Turn!");
				System.out.println("Select Column to place token! (1-7)");
				try {
					token_placement = input.nextInt();
				}
				catch(Exception e) { // error catching
					System.out.println("Invalid entry");
					error = input.next(); // prevents infinite loop
					token_placement = 0;
				}
				if (token_placement < 1 || token_placement > 7) {
					System.out.println("Invalid column number");
				}
				else if (board[0][column_finder(token_placement)] > 0) {
					System.out.println("That Column is full!");
				}
				else { // next turn
					i += 1;
					board = place_Token(board,1, column_finder(token_placement));
				}
			}
			winner = win_Check(board,winCount); // checks for winner
			if (winner == 1) {
				display_board(board);
				System.out.println("Yellow Won!");
				break;
			}
			else if (winner == 2) {
				display_board(board);
				System.out.println("Red Won!");
				break;
			}
			else if (winner == 3) {
				display_board(board);
				System.out.println("Tie!");
				break;
			}
		}
		System.out.println("Play again? (Y/N)");
		Scanner input2 = new Scanner(System.in);
		while (true) // checks that user doesn't enter in crazy inputs
		{
			cont = input2.next();
			if (cont.equalsIgnoreCase("Y")) {
				main(null);
			}
			else if (cont.equalsIgnoreCase("N")) {
				System.out.println("Thank you for playing!");
				input2.close();
				System.exit(0);
			}
			else {
				System.out.println("Invalid entry!");
			}
		}
	}
	
	public static void display_board(int[][] board) {
		/*	This function displays the board in a human readable
		 * 	format.
		 * 
		 * 	Return: N/A
		 */
		// connect 4 board is 15 across, 7 tall
		// 0 == '', 1 == |, 2 == -, 3 == Y, 4 == R
				
		for (int i = 0; i < board.length; i++) { // vertical
			for (int j = 0; j < board[i].length; j++) { // horizontal
				// prints board out based off of board array
				if (board[i][j] == 0) {
					System.out.print(" ");
				}
				else if (board[i][j] == 1) {
					System.out.print("|");
				}
				else if (board[i][j] == 2) {
					System.out.print("-");
				}
				else if (board[i][j] == 3) {
					System.out.print("Y");
				}
				else if (board[i][j] == 4) {
					System.out.print("R");
				}
			}
			System.out.println();
		}
	}
}
