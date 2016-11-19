/**
*Email Program
*Client side of Email program
* Sends email composed of a subject and a body of multiple lines
*@author: Elia Eiroa Lledo
*
*@version: 1.0
*/

import java.io.*;
import java.net.*;
class Email {

    public static void main(String argv[]) throws Exception
    {
        //defining vars
        String fromAddress = "";
        String toAddress = "";
        String subject = "";
        String body = "";
        String userInput = "";

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        Socket clientSocket = null;
        //user prompts
        System.out.println("Enter your email:");
        fromAddress = inFromUser.readLine();

        System.out.println("Enter the destination email:");
        toAddress = inFromUser.readLine();

        System.out.println("Enter the subject: ");
        subject = inFromUser.readLine();

        System.out.println("Enter your message: \n(to end body input a period in a line by itself)");
        //if user input is not a "." by itself in a line it continues adding lines to body
        //"while the last line does not equal '.'"
        while(!userInput.equals("."))
        {
          //add each additional line to the body
          userInput = "";
          userInput = inFromUser.readLine();
          body = body + userInput + "\n";
        }
        //combines all data for the body
        body = "To: " + toAddress + "\nFrom: " + fromAddress + "\nSubject: " + subject + "\n" + body;
        //open socket connection
        try
     		{
     			clientSocket = new Socket("smtp.chapman.edu", 25);
     		}
     		catch(Exception e)
     		{
     			System.out.println("Failed to open socket connection.\n");
     			System.exit(0);
     		}

        String modifiedSentence = "";
        PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream(),true);
        BufferedReader inFromServer =  new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        //identifying ourselves and initiating the SMTP conversation
        outToServer.println("HELO icd.chapman.edu");
        modifiedSentence = inFromServer.readLine();
        System.out.println(modifiedSentence);
        //FROM
        outToServer.println("MAIL FROM: " + fromAddress);
        modifiedSentence = inFromServer.readLine();
        System.out.println(modifiedSentence);
        //TO
        outToServer.println("RCPT TO: " + toAddress);
        modifiedSentence = inFromServer.readLine();
        System.out.println(modifiedSentence);

        //DATA portion
        outToServer.println("DATA");
        modifiedSentence = inFromServer.readLine();
        System.out.println(modifiedSentence);
        outToServer.println(body);
        modifiedSentence = inFromServer.readLine();
        System.out.println("DATA:\n" + body);
        System.out.println(modifiedSentence);

        outToServer.println("QUIT");
        modifiedSentence = inFromServer.readLine();
        System.out.println("QUIT");
        System.out.println(modifiedSentence);

        System.out.println("\nGoodbye (:\n");
        clientSocket.close();
    }
}
