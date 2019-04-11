//Scanner class is imported to get user input
import java.util.Scanner;

public class Calendar {

	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		
		/* year 		-	stores the year value entered by the user
		 * doomsDay 	-	stores the start day of the Year
		 * doomsDay		- 	later on helps in saving the start day of every month 
		 *            		while printing the calendar
		 * */
		int year, doomsDay;
		
		/*
		 * daysOFWeek[]	-	it is a character array that stores the beginning letter of each day
		 * 					(e.g. Sunday - 'S', Monday - 'M')
		 * 				-	used for printing the calendar
		 */
		char[] daysOfWeek = {'S','M','T','W','T','F','S'};
		
		/*
		 * months[]		-	it is a string array that holds the values for all the months
		 * 				-	used for printing the calendar
		 */
		String[] months = {
				"January",
				"February",
				"March",
				"April",
				"May",
				"June",
				"July",
				"August",
				"September",
				"October",
				"November",
				"December"
		};
		
		
		/*
		 * Asks for user input for what year calendar they need
		 * Will throw an exception if the value entered is non numeric
		 */
		System.out.println("Please enter the Year for which you want to see the Calendar");
		year = scan.nextInt();
		
		/*
		 * daysInAMonth	-	it is an array of integers that store the number of days in every month
		 * 					depending on what year is entered by the user.
		 */
		int[] daysInAMonth = new int[months.length];
		
		/*
		 * calls the method getDays(int, int) which returns an int 
		 * array that has the values for days in every month
		 */
		daysInAMonth = getDays(year, months.length);
		
		System.out.println("\n\n");
		
		/*
		 * calculatStartDayOfYear(int) method is called that returns an 
		 * int value which tells what day the year begins from
		 */
		doomsDay = calculateStartDayOfYear(year);
		
		/*
		 * The loop below prints the whole calendar
		 */
		for(int i = 0; i < months.length;i++)
		{
			
			System.out.println("\t\t    " + months[i] + " " + year);
			System.out.println("--------------------------------------------------");
			
			//Prints the days of the week
			for(int j = 0; j < daysOfWeek.length; j++)
				System.out.print(daysOfWeek[j] + "\t");
			
			System.out.print("\n--------------------------------------------------\n");
			
			/*
			 * The loops below prints all the dates of the month
			 */
			for(int j = 0; j < daysInAMonth[i];) {
				
				for(int k = 0; k < 7; k++)
				{
					/*
					 * if the loop has not reached the begin day
					 * position to print then it just prints a tab to
					 * start printing under the next day else it will
					 * print the date when the begin position of the 
					 * week is reached
					 */
					if(k<doomsDay)
					{
						System.out.print("\t");
					}
					else {
						System.out.print(++j + "\t");
						if(j == daysInAMonth[i]) {
							/*
							 * sets doomsday value to k when the last date of the month is printed
							 * will leave doomsDay to 0 if k = 6, which is the last day of the week
							 * so the next month begins printing at a Sunday
							 */
							if(k != 6)
								doomsDay = k+1;
							break;	
						}
						
						/*
						 * sets doomsDay to 0, so when after the first week
						 * the start position for printing the dates must be
						 * below 'S' i.e. Sunday 
						 */
						doomsDay = 0;
					}
					
				}
				System.out.print("\n");	
			}
			System.out.println("--------------------------------------------------\n\n");
			
		}
	}
	
	
	//Returns an array with the number of days in a Month according to the year entered by the user.
	private static int[] getDays(int year, int months) {
		
		int[] days = new int[months];
		
		for(int i = 0; i < days.length; i++)
		{
			 switch(i+1) {
			 	case 1:
				case 3:
				case 5:
				case 7:
				case 8:
				case 10:
				case 12:
					days[i] = 31;
					break;
				case 2:
					if(year%4 == 0)
						days[i] = 29;
					else 
						days[i] =28;
					break;
				case 4:
				case 6:
				case 9:
				case 11:
					days[i] = 30;
					break;
				default:
					days[i] = 0;
					break;
				 
			 }
		}
		
		return days;
	}

	/*
	 * calculateStartDayOfYear(int)
	 * 		-	Uses John H. Conway's algorithm to find the day of the week for any given date.
	 * 		- 	We use this algorithm to figure out the what is the first day of the year.
	 * 		-	Returns and integer which is the first day of the year, below are the values it returns
	 * 				0 if Sunday
	 * 				1 if Monday
	 * 				2 if Tuesday
	 * 				3 if Wednesday
	 * 				4 if Thursday
	 * 				5 if Friday
	 * 				6 if Saturday
	 */
	static int calculateStartDayOfYear(int y) {
		
		/*
		 * dd	-	stores the Dooms Day value for the year entered by the user
		 * firstFinger, secondFinger, thirdFinger and fourthFinger hold the values
		 * used in the calculations to find out the Dooms Day
		 * bd	-	The returning value, which is the first day of the year
		 */
		int dd = 0, firstFinger = 0, secondFinger, thirdFinger, fourthFinger = 0, bd = 0;
		
		dd = y%400;
		
		if(dd >= 0 && dd <= 99) {
			firstFinger = 2;
		}else if(dd >= 100 && dd <= 199) {
			firstFinger = 0;
		}else if(dd >= 200 && dd <= 299) {
			firstFinger = 5;
		}else if(dd >= 300 && dd <= 399) {
			firstFinger = 3;
		}
		
		secondFinger = (y%100)/12;
		
		thirdFinger = (y%100)%12;
		
		if(thirdFinger !=0)
			fourthFinger = thirdFinger/4;
		
		dd = firstFinger + secondFinger + thirdFinger + fourthFinger;
		
		while(dd > 6) {
			dd = dd-7;
		}
		
		/*
		 * According to Conway's Algorithm if the year is a leap year then the
		 * doomsday for January is 4th Jan else for all other years it is 
		 * 3rd of January, so we find out the beginning day 
		 * accordingly
		 */
		if(y%4 == 0)
		{
			if(dd>2)
				bd= dd-3;
			else
				bd = dd+4;
		}
		else
		{
			if(dd>3)
				bd= dd-2;
			else
				bd= dd+5;
		}
		
		//returns the begin day of the year
		return bd;
	}
}
