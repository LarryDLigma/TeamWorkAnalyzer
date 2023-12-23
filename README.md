Teamwork Analyzer App by LarryDLigma(Soti)


Overview
  
  
   Discover, manage, and celebrate teamwork like never before.
  
  
  Features
    
    
    1.	View Employees: Here we are showing each member's vital details, including Employee ID, Project ID, Start Date, and End Date.

    
    2.	Add Employee: When pressed, we get the option to add a new team member. We are asked to provide: Employee ID, Project ID, Start Date, and End Date.

    
    3.	Edit Employee: The editing feature allows us to modify Project ID, Start Date, and End Date.

    
    4.	Delete Employee: basically saying to someone – “yeah, you are fired”

    
    5.	Longest Worked Together: In here we iterate over a list of the TeamWork class instances using a nested loop.
        The outer loop selects the first employee, and the inner selects the second one. We check for overlap by comparing the dates of employees. Then we calculate the days (if overlap in the periods is present).
        We use the computing method in the program to do that. We check for each pair and if the next has more days together than the last, we update the result.
        Once the iterations are completed, we construct a “WorkedTogetherResult” object to encapsulate the details. And after all of this we return the result. There might be confusion in why I have this: totalDaysWorked: daysWorked / 2.
        That’s because I found it difficult to write a way to not sum the days of the 2 employees, so I divide by 2 so the result is correct. 


  How to Use

    1.	Load Data from CSV: The app loads employee data from the provided employees.csv file, seamlessly integrating historical collaboration data.


    2.	Main Menu: Navigate through the app's features via an intuitive main menu.

     
    3.	View, Add, Edit, Delete: Manage your project team.

     
    4.	Longest Worked Together: Delve into the depths of collaboration analytics. The app's algorithm identifies pairs of employees who've worked together for the longest duration, offering valuable insights into your team's history.

     


Classes
  
  
  TeamWork
    The backbone of the app, tracking the who, what, and when of project collaborations.

  
  CSVReader & CSVWriter
    The duo handling the translation between Java and CSV.
  
  
  WorkedTogetherResult
    The findMostTimeWorkedPair algorithm determines pairs with the longest collaborative history.
  
  
  WorkedTogetherApp
    Well, this is where the actual magic happens 🙂.

