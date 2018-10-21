package quynh;

import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Heuristic")
public class Heuristic extends HttpServlet {
	private static final long serialVersionUID = 1L;

	int[][] modelChessBoard = { { 2, 3, 4, 4, 4, 4, 3, 2 }, { 3, 4, 6, 6, 6, 6, 4, 3 },
			{ 4, 6, 8, 8, 8, 8, 6, 4 }, { 4, 6, 8, 8, 8, 8, 6, 4 }, { 4, 6, 8, 8, 8, 8, 6, 4 },
			{ 4, 6, 8, 8, 8, 8, 6, 4 }, { 3, 4, 6, 6, 6, 6, 4, 3 }, { 2, 3, 4, 4, 4, 4, 3, 2 } };
	
    public Heuristic() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		File file = new File(getServletContext().getRealPath("/")+ "quynhdinhHeuristicsMethod.txt");
		FileWriter fout = new FileWriter(file);
		
		HttpSession session = request.getSession(true);
		int x = Integer.parseInt((String) session.getAttribute("xPosition"));
		int y = Integer.parseInt((String) session.getAttribute("yPosition"));
		int times = Integer.parseInt((String) session.getAttribute("time"));

		for (int n = 1; n <= times; n++) {

			// initiate variables
			int move = 1;
			int[][] chessBoard = new int[8][8];
			Knight knight = new Knight(x, y);
			boolean continueMove = true;

			// set the first move
			chessBoard[x][y] = move;

			// if knight still can move
			while (continueMove == true) {
				
				// check available of all squares and get square's key number
				// returns list of squares (8 positions)
				ArrayList<Square> squares = checkAllPosition(knight, chessBoard);

				// sort the list based on its key number
				Collections.sort(squares);
				
				// remove the square is not available (key number = 0)
				for (int i = 0; i < squares.size(); i++) {
					if (squares.get(i).getKey() == 0) {
						squares.remove(i);
						i--;
					}
				}
				
				// set the minimum key num of available squares
				if (squares.size() > 0) { 
					int min = squares.get(0).getKey();
					
					// remove squares that have key number larger than minimum key number
					for (int i = 0; i < squares.size(); i++) {
						if (squares.get(i).getKey() > min) {
							squares.remove(i);
							i--;
						}
					}
				}

				// Among squares that have the minimum key number
				if (squares.size() != 0) {
					
					// choose one randomly
					int random = (int) (Math.random() * squares.size()) + 1;
					int newX = squares.get(random - 1).getX();
					knight.setxPosition(newX);
					int newY = squares.get(random - 1).getY();
					knight.setyPosition(newY);

					// move the knight to new position
					move++;
					chessBoard[newX][newY] = move;
				} else {
					
					// if there is no move, finish the trial
					continueMove = false;
				}
			}
			
			// Display the final chess board in browser
			PrintWriter out = response.getWriter();
			
			String outputBrowser = "";
			String outputFile = "";
			
			outputBrowser += "Trial " + n + ": The knight was able to successfully touch " + move + " squares.</br>";
			outputFile += "Trial " + n + ": The knight was able to successfully touch " + move + " squares.\n";
			for (int i = 0; i < 8; i++) {
				for (int k = 0; k < 8; k++) {
					outputBrowser += String.format("%-3d", chessBoard[k][i]);
					outputFile += String.format("%-3d", chessBoard[k][i]);
				}
				outputBrowser += "</br>";
				outputFile += "\n";
			}
			outputBrowser += "</br>";
			outputFile += "\n";
			out.println(outputBrowser);
			
			// Write text file
			try {
			    fout.write(outputFile);
				System.out.println("File is created successfully");
			} catch (Exception e) {
				System.out.println("Cannot write");
				e.printStackTrace();
			}
		}
		fout.close();
	}

	// check the square is available or not
	public boolean isAvailable(int x, int y, int[][] chessBoard) {
		boolean available = true;

		// check if it is an available position
		if (x < 0 || x >= 8 || y < 0 || y >= 8) {
			available = false;
		} else if (chessBoard[x][y] != 0) {
			available = false;
		}

		return available;
	}

	// check all 8 positions that the knight can move in
	public ArrayList<Square> checkAllPosition(Knight knight, int[][] chessBoard) {

		ArrayList<Square> squares = new ArrayList<>();

		for (int i = 1; i <= 8; i++) {

			int xTempPosition = knight.getxPosition();
			int yTempPosition = knight.getyPosition();

			switch (i) {

			// up left
			case 1:
				yTempPosition -= 2;
				xTempPosition -= 1;
				break;

			// up right
			case 2:
				yTempPosition -= 2;
				xTempPosition += 1;
				break;

			// down left
			case 3:
				yTempPosition += 2;
				xTempPosition -= 1;
				break;

			// down right
			case 4:
				yTempPosition += 2;
				xTempPosition += 1;
				break;

			// left up
			case 5:
				xTempPosition -= 2;
				yTempPosition -= 1;
				break;

			// left down
			case 6:
				xTempPosition -= 2;
				yTempPosition += 1;
				break;

			// right up
			case 7:
				xTempPosition += 2;
				yTempPosition -= 1;
				break;

			// right down
			case 8:
				xTempPosition += 2;
				yTempPosition += 1;
				break;

			}

			// set position of square
			Square square = new Square();
			square.setX(xTempPosition);
			square.setY(yTempPosition);

			// check if the square is available or not
			if (isAvailable(xTempPosition, yTempPosition, chessBoard)) {
				
				// set the key number of the square
				int key = modelChessBoard[xTempPosition][yTempPosition];
				square.setKey(key);
			} else {
				
				// if the square is not available, flag its key as 0
				square.setKey(0);
			}

			// add all 8 positions and their key number
			squares.add(square);
		}
		return squares;
	}
}
