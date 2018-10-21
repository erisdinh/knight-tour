package quynh;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/NonIntelligent")
public class NonIntelligent extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public NonIntelligent() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		File file = new File(getServletContext().getRealPath("/") + "quynhdinhNonIntelligentMethod.txt");
		FileWriter fout = new FileWriter(file);

		int x = Integer.parseInt(request.getParameter("xPosition"));
		int y = Integer.parseInt(request.getParameter("yPosition"));
		int times = Integer.parseInt(request.getParameter("time"));

		for (int n = 1; n <= times; n++) {

			// initiate variables
			int move = 1;
			int[][] chessBoard = new int[8][8];
			Knight knight = new Knight(x, y);
			boolean continueMove = true;

			// set the first move
			chessBoard[x][y] = move;

			// if knight can still move
			while (continueMove == true) {

				// check available of all squares (8 positions)
				// if available, its key will be 1
				// otherwise, its key will be 0
				ArrayList<Square> squares = checkAllPosition(knight, chessBoard);

				// sort the list based on the key
				Collections.sort(squares);

				// remove all positions are not available (having key 0)
				for (int i = 0; i < squares.size(); i++) {
					if (squares.get(i).getKey() == 0) {
						squares.remove(i);
						i--;
					}
				}

				// size of available square list > 0
				if (squares.size() != 0) {

					// choose one randomly
					int random = (int) (Math.random() * squares.size()) + 1;
					int newX = squares.get(random - 1).getX();
					knight.setxPosition(newX);
					int newY = squares.get(random - 1).getY();
					knight.setyPosition(newY);

					// move to new position
					move++;
					chessBoard[newX][newY] = move;

					// if not, finish
				} else {
					continueMove = false;
				}
			}

			// Display the final chess board in browser
			PrintWriter out = response.getWriter();
			
			String outputBrowser = "<p>";
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
			outputBrowser += "</p>";
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
		// if the position is outside the chessboard -> false
		if (x < 0 || x >= 8 || y < 0 || y >= 8) {
			available = false;

			// if the position is not available -> false
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

			// set x and y for the square
			Square square = new Square();
			square.setX(xTempPosition);
			square.setY(yTempPosition);

			// check availability of the square
			// if the square is available -> set key = 1
			if (isAvailable(xTempPosition, yTempPosition, chessBoard)) {
				square.setKey(1);
			} else {

				// if not available, set key = 0
				square.setKey(0);
			}

			// add square into list
			squares.add(square);
		}
		return squares;
	}
}
