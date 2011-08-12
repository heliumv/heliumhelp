import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.IOException;

public class ihvz_index
{
	static String head =														//Der Kopf der HTML-Datei
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
	
	static String body = "";													//Der Körper der HTML-Datei
	
	static String footer = 														//Der Fuß der HTML-Datei
			"</body>" + "\n" +
			"</html>";
	
	static int filecounter = 0;													//Der Dateizaehler
	static String[] htmlfiles = new String[500];								//Alle eingelesenen Files
	
	public static void main(String[] args)
	throws IOException
	{
		File outputfile = new File("inhaltsverzeichnis.htm");					//Ausgabedatei
		outputfile.delete();													//Löscht die Datei falls sie schon verhanden ist
		FileWriter outwriter = new FileWriter(outputfile, true);				//Erstellt einen FileWriter zur Ausgabedatei
		
		getjavamenu();															//Liest die javamenu.htm ein
		
		body = body.replaceAll("<p class=\"answer\".*?</p>", "");				//Entfernt alle Antworten
		body = body.replaceAll("<p class=\"inhaltsverzeichnis\".*?></p>", "");	//Entfernt alle Zeilen ohne inhalt
		body = body.replaceAll("Q: ", "");										//Entfert alle "Q:"
		body = body.replaceAll("&nbsp;", "");									//Entfernt alle "&nbsp;"
		
		outwriter.write(head + body + footer);									//Schreibt den Kopf, Körper und Fußtext in die Ausgabedatei
		
		outwriter.close();														//Schließt den FileWriter
		
		System.out.println("Es wurden " + filecounter + " Dateien eingelesen.");//Gibt die Anzahl der Dateien die eingelesen wurden aus
		
	}
	
	public static void getjavamenu()											//Liest die javamenu.html ein
	throws IOException
	{
		File javamenu = new File("javamenu.htm");								//Datei für die Strukturierung des Inhaltsverzeichnisses
		FileReader javamenureader = new FileReader(javamenu);					//FileReader für die javamenu.htm
		BufferedReader javamenubreader = new BufferedReader(javamenureader);	//BufferedReader für die javamenu.htm
		Pattern patwidth = Pattern.compile("width=\"(.*?)\"");					//Pattern für die Einrückung
		Pattern patanker = Pattern.compile("<a href=\"(.*?)\">(.*?)</a>", 32);	//Patern für die Anker
		
		boolean bodystarted = false;											//Gibt an ob der body-Tag aktiv ist
		String tmp = "";														//String für das einlesen der Datei
		Matcher matwidth;														//Matcher für die Einrückung
		Matcher matanker;														//Matcher für die Anker
		int width = 0;															//Gibt die Einrückung in Pixel an
		
		while ((tmp = javamenubreader.readLine()) != null)
		{
			if (tmp.matches("<body>"))											//Frägt ab ob der body-Tag schon gestartet/beendet wurde, damit er nicht den Kopf/Fuß der HTML-Datei einliest
			{
				bodystarted = true;
				continue;
			}
			else if (tmp.matches("</body>"))
				bodystarted = false;
			
			if (bodystarted)													//Durchsucht die Zeile auf anker und Einrückungen und liest anschließend die Fragen aus
			{
				body += tmp + "\n";
				matwidth = patwidth.matcher(tmp);
				matanker = patanker.matcher(tmp);
				
				if (matwidth.find())
					width = Integer.parseInt(matwidth.group(1).replaceAll("px", ""));
				else
					width = 0;
				
				if (matanker.find())
					getQuestions(matanker.group(1), width, true);
			}
		}
		
	}
	
	public static void createHTMLBody(String[] questions, int width)			//Schreibt alle Fragen der aktuellen Datei in den body
	{
		for (int i = 0; questions[i] != null; i++)
			body += "<img src=\"Bilder/leer.gif\" width=\"" + (width + 10) + "px\" height=\"1\">" + questions[i] + "<br>" + "\n";
	}
	
	public static void getQuestions(String filedir, int width, boolean ismenu)	//Liest alle Fragen der aktuellen Datei ein
	throws IOException
	{
	
		for (int i = 0; i <= htmlfiles.length - 1; i++)							//Prüft ob die HTML-Datei schonmal eingelesen wurde
			if (htmlfiles[i] != null && htmlfiles[i].equals(filedir))
				return;
	
		File htmlfile = new File(filedir);
		if (! htmlfile.exists())												//Prüft ob die Datei existiert
			return;
		if (! htmlfile.getName().endsWith("htm"))								//Prüft ob die Dateiendung htm ist
			return;
		
		htmlfiles[filecounter] = filedir;
		filecounter += 1;
		System.out.println(filecounter + " " + filedir);
		
		FileReader fileReader = new FileReader(htmlfile);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		Pattern patquestions = Pattern.compile("<p class=\"question\".*?>(.*?)</p>");
		Pattern pattitle = Pattern.compile("<h1>(.*?)</h1>");
		
		String tmp = "";
		String datei = "";
		String[] questions = new String[100];
		String title = "";
		
		while ((tmp = bufferedReader.readLine()) != null)						//Schreibt die Datei Zeile für Zeile in das Objekt datei
		{
			datei += " " + tmp;
		}
		
		datei = datei.replaceAll("<img.*?>", "");								//Entfernt alle Bilder
		
		bufferedReader.close();
		fileReader.close();
		
		Matcher matquestions = patquestions.matcher(datei);
		Matcher mattitle = pattitle.matcher(datei);
		
		if (mattitle.find())
			title = mattitle.group(1);
		
		for (int i = 0; matquestions.find(); i++)
			questions[i] = clearQuestion(matquestions.group(1));
		
		if (! ismenu)															//Fügt einen Anker ein, wenn der Methodenaufruf nicht von getJavamenu stammt
			body += "<img src=\"Bilder/leer.gif\" width=\"" + (width) + "px\" height=\"1\"><a href=\"" + filedir + "\">" + title + "</a><br>";
		
		createHTMLBody(questions, width);
		
		getAnker(datei, width, filedir);
	}
	
	public static void getAnker(String datei, int width, String filedir)		//Liest alle Anker aus
	throws IOException
	{
		String newfile;
		String[] split;
		Pattern patanker = Pattern.compile("<a href=\"(.*?)\">");
		
		Matcher matanker = patanker.matcher(datei);
		for (int i = 0; matanker.find(); i++)									//Durchsucht die Datei auf Anker und bearbeitet den Pfad so dass er brauchbar ist
		{
			newfile = matanker.group(1);
			split = newfile.split("#");
			newfile = split[0];
			split = newfile.split("/");
			if (newfile.startsWith(".."))
			{
				newfile = newfile.replaceFirst("..", ".");
				getQuestions(newfile, width + 10, false);
			}
			else if (split.length == 1)
			{
				split = filedir.split("/");
				if (split[0].equals("."))
					newfile = "./" + split[1] + "/" + newfile;
				else
					newfile = "./" + split[0] + "/" + newfile;
					
				getQuestions(newfile, width + 10, false);
			}
		}
	}
	
	public static String clearQuestion(String question)							//Säubert die aktuelle Frage von veerschiedenen Tags
	{
		String clearquestion = question;
		
		clearquestion = clearquestion.replaceAll("<a .*?>", "");
		clearquestion = clearquestion.replaceAll("</a>", "");
		clearquestion = clearquestion.replaceAll("<br>", "");
		
		return clearquestion;
	}
	
}