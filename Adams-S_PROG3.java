/*
 * Author: Samuel Adams
 * Date: November 27, 2012
 * Homework Assignment 4: Using a GUI to do an encryption/decryption
 * 						  algorithm
 */

	import javax.swing.*;
	import java.awt.*;
	import java.awt.event.*;
	import java.util.*;
	

	public class tcgui extends JFrame {
	   // Transposition Cipher GUI or tcgui
	   private Container contents;
	   private JTextField textin, keyin, textout;
	   private JRadioButton encryptbtn, decryptbtn;
	   private ButtonGroup radiobtngroup; 
	   private JLabel inlabel, keylabel, ctoutlbl;
	   private JButton cancelbutton, clearbutton, computebutton;

	   public tcgui () 
	   {
		  super("tcgui");
		  contents = getContentPane();
		  contents.setLayout( new FlowLayout() );

		  encryptbtn = new JRadioButton( "Encryption",true );
		  decryptbtn = new JRadioButton( "Decryption" );
	 
		  inlabel = new JLabel("Enter Plain Text: ");
		  textin = new JTextField(40); 
		  keylabel = new JLabel("Enter key (4-15 char): ");
		  keyin = new JTextField(15); 
		  ctoutlbl = new JLabel("Encrypted Text: ");
		  textout = new JTextField(40);
		  
		  // instantiate buttons
		  cancelbutton = new JButton("Cancel");
		  clearbutton = new JButton("Clear");
		  computebutton = new JButton("Compute");
		  
		  // add components to the window
		  contents.add( encryptbtn );
		  contents.add( decryptbtn );
		  
		  // create radio button group
		  radiobtngroup = new ButtonGroup( );
		  radiobtngroup.add( encryptbtn );
		  radiobtngroup.add( decryptbtn );
		  
		  contents.add( inlabel );
		  contents.add( textin ); // Plain text or cipher text entry field
		  contents.add( keylabel );
		  contents.add( keyin );  // Keyword entry
		  contents.add( ctoutlbl );
		  contents.add( textout ); // Encrypted text is displayed through this label
		  
		  contents.add(cancelbutton);
		  contents.add(clearbutton);
		  contents.add(computebutton);
		  
		  cancelbutton.addActionListener( new ActionListener()
			{
				public void actionPerformed (ActionEvent e)
				{
					//String s = "Exiting...";
					//JOptionPane.showMessageDialog(null,s);
					System.exit(0);
				}
			});

			clearbutton.addActionListener( new ActionListener()
			{
				public void actionPerformed (ActionEvent e)
				{
					textin.setText ("");
					keyin.setText ("");
					textout.setText ("");
				}
			});
			
			computebutton.addActionListener( new ActionListener()
			{
				public void actionPerformed (ActionEvent e)
				{
					// TODO ... replace  the following with appropriate method calls
					if(encryptbtn.isSelected()) { //encryption radio button is selected
						if(keyin.getText().length() < 4 || keyin.getText().length() > 15){
							errorBox("Invalid key length");
						}
						else if(keyCheck(keyin.getText()) == true && numberKeyCheck(keyin.getText()) == true){
							textout.setText(encryptText(textin.getText(), keyin.getText()));
						}else{
							errorBox("Invalid key, characters are not unique or are not part of the alphabet");
						}
					}
					else if(decryptbtn.isSelected()){ //decryption radio button is selected
						if(keyin.getText().length() < 4 || keyin.getText().length() > 15){
							errorBox("Invalid key length.");
						}
						else if(keyCheck(keyin.getText()) == true && numberKeyCheck(keyin.getText()) == true){
							//decryptText(textin.getText(), keyin.getText());
							textout.setText(decryptText(textin.getText(), keyin.getText()));
						}else{
							errorBox("Invalid key, characters are not unique or are not part of the alphabet");
						}
					}
				}
			});

		  setSize(475, 350);
		  setVisible(true);
		}
	   public static boolean numberKeyCheck(String key){
		   for (int i = 0; i < key.length(); i++){
			   if((key.charAt(i)-48) < 17 || (key.charAt(i) - 48) >= 43 && (key.charAt(i) - 48) <= 48 || (key.charAt(i) - 48) > 74){
				   return false;
			   }
		   	}
		   	return true;
	   }
		//key length and unique key error checking
		public static boolean keyCheck(String key){
			HashSet<Character> keyErrorCheck = new HashSet<Character>(key.length());
			for(int e = 0; e < key.length(); e++){
				if(!keyErrorCheck.add(key.toUpperCase().charAt(e))){
					return false;
				}
			}
			return true;
	   	}
		//pop up for errors
		public static void errorBox(String errorMessage){
			JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
		}
		//eliminating spaces from the typed in text
		public static String removeSpace(String text){
			StringBuffer spaceMinus = new StringBuffer(text);
			int space_count = 0;
			int space_index = 0;
			while(spaceMinus.indexOf(" ", space_index) != -1){
				space_index = spaceMinus.indexOf(" ", space_index);
				spaceMinus.deleteCharAt(spaceMinus.indexOf(" ", space_index));
				space_count++;
			}
			return spaceMinus.toString();
		}
		//sort the decrypted text
		public static char[][] sortDecryptText(char[][] textToDecrypt, int col, int row, String key){
			char[][] decrypted = new char[row][col];
			//Get the key length to ensure no errors for later use
			int size = key.length();
			//shove the key into a single array
			char[] keyToAdd = key.toCharArray();
			//put the key into the 2D array
			for(int i = 0; i < size; i++){
				char temp = keyToAdd[i];
				decrypted[0][i] = temp;
			}
			//set the col for the decrypted array
			for(int comp = 0; comp < col; comp++){
				//set the col of the textToDecrypt array
				for(int comp1 = 0; comp1 < col; comp1++){
					//if the col are the same, run the loop to place the rows into
					//the decrypted array
					if(decrypted[0][comp] == textToDecrypt[0][comp1]){
						for(int comp2 = 0; comp2 < row; comp2++){
							decrypted[comp2][comp] = textToDecrypt[comp2][comp1];
						}
					}
					//if they're not the same, do nothing
					else{}
				}
			}
			return decrypted;
		}
		//sort array
		public static char[][] sortEncryptText(char[][] textToEncrypt, int col, int row, String key){
			char[][] encrypted = new char[row][col];
			int size = key.length();
			char[] keySort = key.toCharArray();
			//sort the key for the encrypted array
			Arrays.sort(keySort);
			for(int i = 0; i < size; i++){
				char temp = keySort[i];
				encrypted[0][i] = temp;
			}
			for(int comp = 0; comp < col; comp++){
				for(int comp1 = 0; comp1 < col; comp1++){
					if(encrypted[0][comp] == textToEncrypt[0][comp1]){
						for(int comp2 = 0; comp2 < row; comp2++){
							encrypted[comp2][comp] = textToEncrypt[comp2][comp1];
						}
					}
					else{}
				}
			}
			return encrypted;
		}
		public static String encryptText(String encryptString, String key){
			int current_text_index = 0;
			int numberOfColumns = key.length();
			int numberOfRows;
			encryptString = removeSpace(encryptString);
			//determining number of rows in multidimensional array
			if(encryptString.length()%numberOfColumns == 0){
				numberOfRows = (encryptString.length()/numberOfColumns) + 1;
			}else{
				numberOfRows = (encryptString.length()/numberOfColumns) + 2;
			}
			char[][] encrypt = new char[numberOfRows][numberOfColumns];
			//appending z to text as needed
			if(((numberOfRows - 1)*numberOfColumns) - encryptString.length() != 0){
				StringBuffer encryptAppend = new StringBuffer(encryptString);
				for(int i = 0; i < ((numberOfRows - 1)*numberOfColumns) - encryptString.length(); i++){
					encryptAppend.append('z');
				}
				encryptString = encryptAppend.toString();
			}
			//put the key in the array
			for(int j = 0; j < numberOfColumns; j++){
				encrypt[0][j] = key.charAt(j);
			}
			//put the plaintext into the array
			for(int i = 1; i < numberOfRows; i++){
				for(int j = 0; j < numberOfColumns; j++){
					encrypt[i][j] = encryptString.charAt(current_text_index);
					current_text_index++;
				}
			}
			char[][] test = sortEncryptText(encrypt, numberOfColumns, numberOfRows, key);
			//stringbuffer to turn the sorted array into a string
			StringBuffer toGUIBuffer = new StringBuffer();
			for(int c = 0; c < numberOfColumns; c++){
				for(int r = 0; r < numberOfRows; r++){
					if(r == 0){}
					else{
						toGUIBuffer.append(test[r][c]);
					}
				}
			}
			String toGUILower = toGUIBuffer.toString();
			String toGUI = toGUILower.toUpperCase();
			System.out.println(toGUI);
			return toGUI;
		}
		
		public static String decryptText(String decryptString, String key){
			int current_text_index = 0;
			int numberOfColumns = key.length();
			decryptString = removeSpace(decryptString);
			int numberOfRows = decryptString.length()/numberOfColumns + 1;
			char[][] decrypt = new char[numberOfRows][numberOfColumns];
			//shove the key into an array and sort it
			char[] keySort = key.toCharArray();
			Arrays.sort(keySort);
			//fill the array with the key
			for(int j = 0; j < numberOfColumns; j++){
				decrypt[0][j] = keySort[j];
			}
			//fill the array with crypttext
			for(int c = 0; c < numberOfColumns; c++){
				for(int r = 1; r < numberOfRows; r++){
					decrypt[r][c] = decryptString.charAt(current_text_index);
					current_text_index++;
				}
			}
			char[][] test = sortDecryptText(decrypt, numberOfColumns, numberOfRows, key);
			//string buffer again, yada yada
			StringBuffer toGUIBuffer = new StringBuffer();
			for(int r = 0; r < numberOfRows; r++){
				for(int c = 0; c < numberOfColumns; c++){
					if(r == 0){}
					else{
						toGUIBuffer.append(test[r][c]);
					}
				}
			}
			String toGUILower = toGUIBuffer.toString();
			String toGUI = toGUILower.toUpperCase();
			System.out.println(toGUI);
			return toGUI;
		}
	  
		public static void main (String [] args) {
			tcgui cy = new tcgui();
			cy.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		}
	}