package gameScriptifier;
import java.io.*;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane; 

public class Scriptifier {
	// Scriptifier allows you to convert your handwritten game scripts into Reactive Engine compatible JSX

	public static void main(final String[] args) throws Exception {
		final JFileChooser jfc = new JFileChooser();
	    jfc.showDialog(null,"Please select input file");
	    jfc.setVisible(true);
	    final File file= jfc.getSelectedFile();

		final BufferedReader br = new BufferedReader(new FileReader(file)); 

		String inputLine; 
		String output = "var script = (";
		output += "<div id=\"gameContainer\">\r\n"; // the opening lines of all our scripts
		
		boolean initialRun = true;
		
		while ((inputLine = br.readLine()) != null)  {
			// Comments support - remove anything that comes after a "//" in our script
			final String[] sp = inputLine.split("//");
			inputLine = sp[0]; // everything that comes before a // is used, everything after is ignored
						
			if(!inputLine.equals("")) // blank lines are ignored
			{
			
			if(inputLine.charAt(0) == '[') { // if the start of a text section (div)
				String init = "";
				if(initialRun) {
					init = "id=\"txt-active\" "; // the first text section is set as the active one
					initialRun = false;
				}
				else {
					output += "</div>\r\n\r\n\r\n"; // this isn't our first text section, so close the previous one up
				}
					
				inputLine = inputLine.replaceAll("\\[", "").replaceAll("\\]",""); // remove the brackets around section name
				output += "<div " + init + "className=\"" + inputLine + "\">\r\n";
			}
			else if(inputLine.charAt(0) == '>') { // Option component
				// <Option label="Answer it" text="answerPhone"/>
				final String[] parts = inputLine.split(" > ");  // label and text fields seperated by another > and space on each side
				final String label = parts[0].replace(">", ""); // remove the > from the new label String
				final String text = parts[1];
				
				output += "\t<Option label=\"" + label + "\" text=\"" + text + "\"/>\r\n";
			}
			else if(inputLine.charAt(0) == '@') { // Input component
				final String[] parts = inputLine.split(" > ");  // fields seperated by another > and space on each side
				final String valueToSet = parts[0].replace("@", ""); // remove the @ from the new label String
				final String goTo = parts[1];
				final String placeholder = parts[2];
				final String capitaliseFirstLetter = parts[3];
				
				output += "\t<Input valueToSet=\"" + valueToSet + "\" goTo=\"" + goTo + "\" placeholder=\"" + placeholder + "\" capitaliseFirstLetter=\"" + capitaliseFirstLetter + "\"/>\r\n";
			}
			else { // no tag, so just a regular dialouge line; wrap in <p> tag
				output += "\t<p>" + inputLine + "</p>\r\n";
				// check for player set variables like {this} and turn them into <span className="this"></span>
				output = output.replace("{", "<span className=\"");
				output = output.replace("}", "\"></span>");
			}
			
			}
			
		}
		
		br.close(); // close our input file reader
		
		output += "</div>\r\n</div>\r\n);"; //close the last game section div, as well as the gameContainer div
		output += "ReactDOM.render(script, document.getElementById('root'));"; // and finally, make sure React renders it
		
		//ouput as UTF-8 for compatability
		Writer out = new BufferedWriter(new OutputStreamWriter(	new FileOutputStream(jfc.getCurrentDirectory() + "\\game_script.js"), "UTF-8"));
		try {
			out.write(output);
		} finally {
			out.close();
		}

		System.out.println(output); 
		JOptionPane.showMessageDialog(null, "\"game_script.js\" succesfully written to " + jfc.getCurrentDirectory() + " directory.");
	} 
}