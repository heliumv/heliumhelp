import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.IOException;

public class ihvz_index
{
	static String head =
		"<html>" + "\n" +
		"<head>" + "\n" +
		"<meta http-equiv=\"Content-Language\" content=\"de\">" + "\n" +
		"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=windows-1252\">" + "\n" + 
		"<link rel=\"stylesheet\" type=\"text/css\" href=\"css/main.css\">" + "\n" + 
		"<title>" + "Inhaltsverzeichnis Helium V" + "</title>" + "\n" +
		"<style type=\"text/css\">" + "\n" +
		".inhaltsverzeichnis {" + "\n" +
		"	font-variant: normal;" + "\n" +
		"	font-weight: bold;" + "\n" +
		"	padding-bottom: 10px;" + "\n" +
		"}" + "\n" +
		"</style>" + "\n" +
		"</head>" + "\n" + 
		"<body>" + "\n" +
		"<h1><a name=head>Inhaltsverzeichnis Helium V</a></h1>" + "\n";
			
	static String body = "";
	
	static String footer = 
			"</body>" + "\n" +
			"</html>";
	
	public static void main(String[] args)
	throws IOException
	{
		File outputfile = new File("inhaltsverzeichnis.htm"); //Ausgabedatei
		outputfile.delete(); //Löscht die Datei falls sie schon verhanden ist
		FileWriter outwriter = new FileWriter(outputfile, true); //Erstellt einen FileWriter zur Ausgabedatei
		
		getjavamenu();
		
		body = body.replaceAll("<p class=\"answer\".*?</p>", ""); //Entfernt alle Antworten
		body = body.replaceAll("<p class=\"inhaltsverzeichnis\".*?></p>", ""); //Entfernt alle Zeilen ohne inhalt
		body = body.replaceAll("Q: ", ""); //Entfert alle "Q:"
		body = body.replaceAll("&nbsp;", ""); //Entfernt alle "&nbsp;"
		
		outwriter.write(head + body + footer); //Schreibt den Kopf, Körper und Fußtext in die Ausgabedatei
		
		outwriter.close(); //Schließt den FileWriter
	}
	
	public static void getjavamenu()
	throws IOException
	{
		File javamenu = new File("javamenu.htm"); //Datei für die Strukturierung des Inhaltsverzeichnisses
		FileReader javamenureader = new FileReader(javamenu);
		BufferedReader javamenubreader = new BufferedReader(javamenureader);
		Pattern patwidth = Pattern.compile("width=\"(.*?)\"");
		Pattern patanker = Pattern.compile("<a href=\"(.*?)\">(.*?)</a>", 32);
		
		boolean bodystarted = false;
		String tmp = "";
		Matcher matwidth;
		Matcher matanker;
		int width = 0;
		
		while ((tmp = javamenubreader.readLine()) != null)
		{
			if (tmp.matches("<body>"))
			{
				bodystarted = true;
				continue;
			}
			else if (tmp.matches("</body>"))
				bodystarted = false;
			
			if (bodystarted)
			{
				body += tmp + "\n";
				matwidth = patwidth.matcher(tmp);
				matanker = patanker.matcher(tmp);
				
				if (matwidth.find())
					width = Integer.parseInt(matwidth.group(1).replaceAll("px", ""));
				else
					width = 0;
				
				if (matanker.find())
					createHTMLBody(getQuestions(matanker.group(1)), width);
			}
		}
		
	}
	
	public static void createHTMLBody(String[] questions, int width)
	{
		for (int i = 0; questions[i] != null; i++)
			body += "<img src=\"Bilder/leer.gif\" width=\"" + (width + 10) + "\" height=\"1\">" + questions[i] + "<br>" + "\n";
	}
	
	public static String[] getQuestions(String filedir)
	throws IOException
	{
		FileReader fileReader = new FileReader(new File(filedir));
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		Pattern patquestions = Pattern.compile("<p class=\"question\".*?>(.*?)</p>");

		String tmp = "";
		String datei = "";
		String[] questions = new String[100];
		
		while ((tmp = bufferedReader.readLine()) != null)
		{
			datei += " " + tmp;
		}
		
		datei = datei.replaceAll("<img.*?>", ""); //Entfernt alle Bilder
		
		bufferedReader.close();
		fileReader.close();
		
		Matcher matquestions = patquestions.matcher(datei);
		
		for (int i = 0; matquestions.find(); i++)
			questions[i] = clearQuestion(matquestions.group(1));
		
		System.out.println(filedir);
		
		return questions;
	}
	
	public static String clearQuestion(String question)
	{
		String clearquestion = question;
		
		clearquestion = clearquestion.replaceAll("<a .*?>", "");
		clearquestion = clearquestion.replaceAll("</a>", "");
		clearquestion = clearquestion.replaceAll("<br>", "");
		
		return clearquestion;
	}
	
}