
import java.io.*;
import java.util.*;
/**
 * 
 * @author Camille Wong
 * @date 10/10/21
 * 
 * An Exploration of StringTokenizer and File handling.
 *
 */
public class PasswordHandler 
{
	private static final String SPECIAL_CHARS = "!@#$%";
	public static void main(String[] args) throws Exception {
		Scanner IO = new Scanner(System.in);
		int option = 0;
		while(option != 5)
		{
			option = welcomePrompt(IO);
			switch(option){
			case 1: createPassword(IO); break;
			case 2: changePassword(IO); break;
			case 3: 
				System.out.print("Enter a password:");			
				if(isValid(IO.nextLine().trim()))
					System.out.println("Password is valid\n");
				else
					System.out.println("Password is not valid\n");
				break;
			case 4: 
				System.out.printf("\nSuggested Password: %s\n", suggestPassword()); break;
			}
		}
		IO.close();
	}

	private static int welcomePrompt(Scanner sc) {
		System.out.println("*/\\*Password Handler*/\\*");
		System.out.println("1. Create a password");
		System.out.println("2. Change your password");
		System.out.println("3. Check to see if a password is valid");
		System.out.println("4. Get a suggested password");
		System.out.println("5. Quit");
		System.out.print("Choose a number (1-5) ");
		String selection = sc.nextLine().trim();
		int num;
		try {
			num = Integer.parseInt(selection);
		}
		catch(NumberFormatException e) {
			System.out.println("\nSelection must be a number between 1 and 5\n");
			return welcomePrompt(sc);
		}
		if(num < 1 || num > 5) {
			System.out.println("\nSelection must be a number between 1 and 5\n");
			return welcomePrompt(sc);
		}
		return num;
	}



	public static void createPassword(Scanner s) throws IOException{
		File file = new File("src/pwds.txt");
		if(file.createNewFile())
		{
			System.out.println("File Created");
		}
		
		FileWriter fw= new FileWriter(file, true);
		BufferedWriter buffer = new BufferedWriter(fw);
		System.out.println("type in a password");
		String passwd= s.nextLine();
		if (isValid(passwd)==true)
		{
		buffer.write(passwd);
		buffer.newLine();
		buffer.close();
		System.out.println("password created successfully");
		}
		else
			createPassword(s);
	}

	

	public static void changePassword(Scanner s) throws IOException
	{
		//forgot to account for case if the pwds.txt has NO password in it yet

		System.out.println("type your current password");
		int n=0; //counts lines
		String currPass= s.nextLine();
 			BufferedReader reader;
 			try {
 		reader = new BufferedReader(new FileReader("src/pwds.txt"));
 		String line = reader.readLine();
 				while (line != null) {
 					n++;
 					line = reader.readLine();
 				}
 		 		reader.close();
 			} catch (IOException e) {
 				e.printStackTrace();
 			}
 		//reopen reader to check if last line (current password) is correct
 		reader = new BufferedReader(new FileReader("src/pwds.txt"));

 				for (int i = 0; i < n-1; i++) {
 					reader.readLine();
					}

					if (reader.readLine().equals(currPass))
					{
						System.out.println("correct! Now please type in your new password");
						String newPass= s.nextLine();
						if(isValid(newPass)) {
						//change password
							//create scanner to read out file and append lines to string buffer
					      Scanner sc = new Scanner(new File("src/pwds.txt"));
					      StringBuffer buffer1 = new StringBuffer();
					      while (sc.hasNextLine()) {
					         buffer1.append(sc.nextLine()+System.lineSeparator());
					      }
					      //convert file contents that have been appended to the string buffer into a string
					      //so can use the replaceAll() method to modify the line with the current password
					      String contents = buffer1.toString();
					      sc.close();
					      //Replacing the current password with new password
					      contents = contents.replaceAll(currPass, newPass);
					      //instantiating the FileWriter class to modify file with new password
					      FileWriter writer = new FileWriter("src/pwds.txt");
					      System.out.println("");
					      writer.append(contents);
					      writer.flush();
					      System.out.println("password change successful!");
						}
						//if new password is invalid call the method again
						else {
			 				changePassword(s);
						}
					    }
					//if the current password typed in was incorrect call the method again
					else {
 				System.out.println("incorrect password");
 				changePassword(s);
 				}
 
 		}
   
		
		    




	public static boolean isValid(String password)
	{ 
		if(password.length()<6 || password.length() >10) {
			System.out.println("password must be between 6-10 characters");
		return false;
	}
		else {
		
		int lowercase=0;
		int uppercase=0;
		int num=0;
		
		for(int i=0; i<password.length(); i++) {
			if('a'<=password.charAt(i)&& password.charAt(i)<='z')
				lowercase++;
		}
		if (lowercase==0) {
			System.out.println("Must have a lowercase letter");
			return false;
			}

		for(int i=0; i<password.length(); i++) {
			if('A'<=password.charAt(i) && password.charAt(i)<='Z')
				uppercase++;
		}
		if(uppercase==0) {
			System.out.println("Must have a uppercase letter");
			return false;
			}
		
		for(int i=0; i<password.length(); i++) {
			if('0'<=password.charAt(i) && password.charAt(i)<='9')
				num++;
		}
		if(num==0) {
			System.out.println("Must have a number");
			return false;
			}

		StringTokenizer letters = new StringTokenizer(password, "!@#$%", true);
		int count = letters.countTokens();
		if(count<2) {
			System.out.println("Must have at least one special character");
			return false;
		}
		

			try {
		BufferedReader reader = new BufferedReader(new FileReader("src/pwds.txt"));
		String line= reader.readLine();
				while (line != null) {
					if(line.equals(password)) {
						System.out.println("Password already exists");
						return false;
				}
					else
						line= reader.readLine();
					}
			reader.close();
			}catch (IOException e) {
				e.printStackTrace();
			}

		}
		return true;
		}
		
		
	
	

	public static String suggestPassword() throws IOException
	{
		//make 4 cases for each one so its much more likely to get valid password
		 String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	      String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
	      String specialCharacters = "!@#$";
	      String numbers = "1234567890";
	      String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
	      Random random = new Random();
	      String password = "";

	   
	     
	      while(isValid(password)==false)
	  		{
	  			for(int i=4; i<((int)(Math.random()*5+6)); i++)
	  			{
	  				password+= "" + combinedChars.charAt(random.nextInt(combinedChars.length()));
	  			}

}
			return password;

	}
}
