*Instructions:
- Run program on web browser.
- Choose method.
- Enter knight position: column X and row Y (from 0 to 7).
- Enter number of trial.
- Press Submit button.

*Note: 
_ Error page will display if:
	. Any of fields is not filled before pressing Submit button.
	. Column and row Y value is not in the range from 0 to 7.
	. Input of fields is not integer (column X, row Y and number of trial times)
-> Error page will inform the exception.
-> Click 'Homepage' to go back the web application Homepage

* After the user clicks Submit button:
- It calls KnightTour servlet
-> If the user chooses NonIntelligent method
	. Using RequestDispatcher to "NonIntelligent" servlet
-> If the user chooses Heuristic method
	. Using sendRedirect to "Heuristic" servlet.
	
* Go to web application physical path to find output textfiles.
