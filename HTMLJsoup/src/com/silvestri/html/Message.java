package com.silvestri.html;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
// import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
// import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.imageio.IIOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.apache.commons.io.FilenameUtils;

public class Message {

	public Map<Integer, Integer> matriz_map;
	public String FILE_EXTENSION;
	
	public String create_ex_header() {
		return "<p id='viewinbrowser'>Clique <a href='*[link_viewinbrowser]*'>aqui</a> para ver online</p>";
	}
	
	public String create_ex_footer() {
		return "<p id='unsubscribe'>Clique <a href='*[link_unsubscribe]*'>aqui</a> para se descadastrar</p>";
	}
	
	public String create_header() {
		return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"
				+ "<html>"
				+ "<head>"
				+ "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />"
				+ "<title>Mensagem</title>"
				+ "</head>"
				+ "<body>"
				+ "<center>";
	}
	
	public String create_footer() {
		return "</center></body></html>";
	}
	
	public String create_template() {
		return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
				+ "<html>\n"
				+ "\t<head>\n"
				+ "\t\t<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />\n"
				+ "\t\t<title>Mensagem</title>\n"
				+ "\t</head>\n"
				+ "<body>\n"
				+ "\t<center id='center'>\n"
				+ "<div id='ex_header'></div>"
				+ "<div id='ex_footer'></div>"
				+ "\t</center>\n"
				+ "</body>\n"
				+ "</html>";
	}
	
	public String check_extension(File file) {
				
		FILE_EXTENSION = FilenameUtils.getExtension(file.getName());
			
		if(FILE_EXTENSION.equalsIgnoreCase("jpg") || FILE_EXTENSION.equalsIgnoreCase("jpeg") || FILE_EXTENSION.equalsIgnoreCase("png")) {
			System.out.println("\n====================================");
			System.out.println("=> Valid file extension found: " + FILE_EXTENSION.toUpperCase());
			System.out.println("====================================\n");
		} else {
			System.out.println("\n*****************************************************************************************************************");
			System.out.println("The file extension of the 0 file should be one of the following: JPG, JPEG or PNG!!! Terminating the program...");
			System.out.println("*****************************************************************************************************************\n");
			System.exit(0);
		}
		
		return "yes";
	}
	
	public String create_content(String html) throws NumberFormatException, IOException {
		
		File folder = new File(System.getProperty("user.dir"));
		File[] listOfFiles = folder.listFiles();
		
		System.out.println("\n============================================================ ");
		System.out.println("These are the files available in the current working folder: ");
		System.out.println("============================================================\n ");
		for (File files : listOfFiles) {
			if (files.isFile()) {
				System.out.println("=> " + files.getName());
			}
		}
		
		Boolean[] boolArray = new Boolean[listOfFiles.length];
		for(int i = 0; i < boolArray.length; i++) {
			if (listOfFiles[i].isFile()) {
				if (listOfFiles[i].getName().contains("0") && listOfFiles[i].getName().startsWith("0")) {
					boolArray[i] = true;
				} else {
					boolArray[i] = false;
				}
			}
		}
		
		for(int i = 0; i < boolArray.length; i++) {
			if (Arrays.asList(boolArray[i]).contains(null) || !boolArray[i].booleanValue() == true) {
				if (i == boolArray.length - 1) {
					System.out.println("\n*******************************************************************************************************");
					System.out.println("Source file identified by number 0 NOT FOUND in the current working folder!!! Terminating the program...");
					System.out.println("********************************************************************************************************\n");
					System.exit(0);
				}
				continue;
			} else if (!Arrays.asList(boolArray[i]).contains(null) && boolArray[i].booleanValue() == true) {
					String extension_validation;
					extension_validation = check_extension(listOfFiles[i]); 
					if (extension_validation.equals("yes")) {
						break;
					}
				} 
		}

		Map<Integer, Integer> matriz_map = new HashMap<Integer, Integer>();
		String current_folder = System.getProperty("user.dir");
		FileOutputStream outputStream = new FileOutputStream(current_folder + "\\" + "message.html");
		
		String links;
		
		BufferedReader answer = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("=> How many rows is the creative going to have? ");
		Integer rows = Integer.parseInt(answer.readLine());
		System.out.println();
		
		for(int i = 1; i <= rows; i++) {

			System.out.print("=> Enter the number of columns for line " + i + ": ");
			String columns = answer.readLine();
			try {
				
				Integer columns_int = Integer.parseInt(columns);
				
				if(columns_int >= 7) {
					System.out.println("\n***************************************************");
					System.out.println("More than 6 columns not allowed. Exiting program...");
					System.out.println("***************************************************\n");
					System.exit(0);
				} else if(columns_int < 1) {
					System.out.println("\n**************************************************");
					System.out.println("At least 1 column is necessary. Exiting program...");
					System.out.println("**************************************************\n");
					System.exit(0);
				} else {
					matriz_map.put(i, columns_int);
				}

			} catch (NumberFormatException nfe) {
				System.out.println("\n*************************************************");
				System.out.println("Invalid number inserted!!! Exiting the program...");
				System.out.println("*************************************************\n");
				System.exit(0);
			}
			
		}
		
		/* FOR REMOVAL
		System.out.println();
		int maxValueInMap=(Collections.max(matriz_map.values()));  // This will return max value in the HashMap
        for (Entry<Integer, Integer> entry : matriz_map.entrySet()) {  // Iterate through HashMap
            if (entry.getValue()==maxValueInMap) {
                System.out.println("- Total number of columns in row "+entry.getKey()+" is " + entry.getValue());     // Print the key with max value -> Highest number of columns in map
            }
        }
        */
		
		System.out.println("\n==============================================");
        System.out.println("This is the table structure (rows VS columns): " + matriz_map);
        System.out.println("==============================================\n");
		
		// answer.close();
		
		Document document = Jsoup.parse(html);
		
		Element div_ex_header = document.getElementById("ex_header");
		div_ex_header.append("<p id='viewinbrowser' style='font-family: Verdana, Arial; font-size: 10px;'>Clique <a href='*[link_viewinbrowser]*'>aqui</a> para ver online</p>");
		
		Element ex_footer = document.getElementById("ex_footer");
		ex_footer.append("<p id='unsubscribe' style='font-family: Verdana, Arial; font-size: 10px;'>Clique <a href='*[link_unsubscribe]*'>aqui</a> para se descadastrar</p>");
		
		// nem prepend() e nem append() coloca isso no lugar, tem que ficar entre as <div>'s, mas fica antes ou depois delas respectivamente
		/*
		Element table_structure = document.getElementById("center");
		table_structure.append("<table id='structure' cellspacing='0' cellpadding='0' width='650px'>");
		*/
		/*
		Element tag_center = document.select("center").first();
		tag_center.appendElement("table").attr("id","structure").attr("cellspacing","0").attr("cellpadding","0").attr("width","650px");
		*/
		
		//Element table_structure = document.getElementById("center");
		//table_structure.append("<table id='structure' cellspacing='0' cellpadding='0' width='650px'>");
		
		/*
		Elements elements = document.select("div");
		Element element = new Element("<table id='structure' cellspacing='0' cellpadding='0' width='650px'>");
		elements.add(1, element);
		*/
		
		Elements elements = document.select("div");
		Element element = elements.get(0);
		element.after("<table id='structure' cellspacing='0' cellpadding='0' width='650px'>");
		
		Element table = document.getElementById("structure");
		
		try {
		
		for(int i = 1; i <= matriz_map.size(); i++) {
			
			table.append("<tr id='"+i+"'>");
			
			if (matriz_map.get(i) == 1) { 
				Element tr_1 = document.getElementById(i+"");
				tr_1.append("<td id='"+i+"_1'>");
				
				Element img_1 = document.getElementById(i+"_1");
				img_1.html("<img id='img_"+i+"_1' src='file:///"+new File(current_folder+"\\"+i+"."+this.FILE_EXTENSION).getPath()+"' alt='image' width='"+ImageIO.read(new File(current_folder+"\\"+i+"."+this.FILE_EXTENSION)).getWidth()+"' height='"+ImageIO.read(new File(current_folder+"\\"+i+"."+this.FILE_EXTENSION)).getHeight()+"' style='display: block; border: 0'/>");
				
				System.out.print("=> insert URL for the image "+i+" or 'n' for none? ");
				links = answer.readLine();
				if(!links.equals("n") && !links.equals("N")) {
					Element lnk_1 = document.getElementById(""+i+"_1");
					lnk_1.html("<a href='"+links+"' title='Link "+i+"_1' target='_blank'><img id='img_"+i+"_1' src='file:///"+new File(current_folder+"\\"+i+"."+this.FILE_EXTENSION).getPath()+"' alt='image' width='"+ImageIO.read(new File(current_folder+"\\"+i+"."+this.FILE_EXTENSION)).getWidth()+"' height='"+ImageIO.read(new File(current_folder+"\\"+i+"."+this.FILE_EXTENSION)).getHeight()+"' style='display: block; border: 0'/></a>");
				}
				
				
				System.out.println("\nRow number " + i + " contains 1 column\n");
			} else if (matriz_map.get(i) == 2) {
				Element tr_1 = document.getElementById(+i+""); 
				tr_1.append("<td id='"+i+"___'>"); 
				Element td_1___ = document.getElementById(i+"___"); 
				td_1___.append("<table id='"+i+"__' cellspacing='0' cellpadding='0' width='650px'>"); 
				Element tr_1__ = document.getElementById(i+"__"); 
				tr_1__.append("<tr id='"+i+"_'>"); 
				Element td_1_ = document.getElementById(i+"_");
				td_1_.append("<td id='"+i+"_1'>");
				Element img_1 = document.getElementById(i+"_1");
				img_1.html("<img id='img_"+i+"_1' src='file:///"+new File(current_folder+"\\"+i+"_1."+this.FILE_EXTENSION).getPath()+"' alt='image' width='"+ImageIO.read(new File(current_folder+"\\"+i+"_1."+this.FILE_EXTENSION)).getWidth()+"' height='"+ImageIO.read(new File(current_folder+"\\"+i+"_1."+this.FILE_EXTENSION)).getHeight()+"' style='display: block; border: 0'/>");
				
				System.out.print("Insert URL for image "+i+"_1 or 'n' for none? ");
				links = answer.readLine();
				if(!links.equals("n") && !links.equals("N")) {
					Element lnk_1 = document.getElementById(""+i+"_1");
					lnk_1.html("<a href='"+links+"' title='Link "+i+"_1' target='_blank'><img id='img_"+i+"_1' src='file:///"+new File(current_folder+"\\"+i+"_1."+this.FILE_EXTENSION).getPath()+"' alt='image' width='"+ImageIO.read(new File(current_folder+"\\"+i+"_1."+this.FILE_EXTENSION)).getWidth()+"' height='"+ImageIO.read(new File(current_folder+"\\"+i+"_1."+this.FILE_EXTENSION)).getHeight()+"' style='display: block; border: 0'/></a>");
				}
				
				td_1_.append("<td id='"+i+"_2'>");
				Element img_2 = document.getElementById(i+"_2");
				img_2.html("<img id='img_"+i+"_2' src='file:///"+new File(current_folder+"\\"+i+"_2."+this.FILE_EXTENSION).getPath()+"' alt='image' width='"+ImageIO.read(new File(current_folder+"\\"+i+"_2."+this.FILE_EXTENSION)).getWidth()+"' height='"+ImageIO.read(new File(current_folder+"\\"+i+"_2."+this.FILE_EXTENSION)).getHeight()+"' style='display: block; border: 0'/>");
				
				System.out.print("Insert URL for image "+i+"_2 or 'n' for none? "); 
				links = answer.readLine();
				if(!links.equals("n") && !links.equals("N")) {
					Element lnk_2 = document.getElementById(""+i+"_2");
					lnk_2.html("<a href='"+links+"' title='Link "+i+"_2' target='_blank'><img id='img_"+i+"_2' src='file:///"+new File(current_folder+"\\"+i+"_2."+this.FILE_EXTENSION).getPath()+"' alt='image' width='"+ImageIO.read(new File(current_folder+"\\"+i+"_2."+this.FILE_EXTENSION)).getWidth()+"' height='"+ImageIO.read(new File(current_folder+"\\"+i+"_2."+this.FILE_EXTENSION)).getHeight()+"' style='display: block; border: 0'/></a>");
				}
				
				System.out.println("\nRow number " + i + " contains 2 column\n");
			} else if (matriz_map.get(i) == 3) {
				Element tr_1 = document.getElementById(+i+"");
				tr_1.append("<td id='"+i+"___'>");
				Element td_1___ = document.getElementById(i+"___");
				td_1___.append("<table id='"+i+"__' cellspacing='0' cellpadding='0' width='650px'>");
				Element tr_1__ = document.getElementById(i+"__");
				tr_1__.append("<tr id='"+i+"_'>");
				
				Element td_1_ = document.getElementById(i+"_");
				td_1_.append("<td id='"+i+"_1'>");
				Element img_1 = document.getElementById(i+"_1");
				img_1.html("<img id='img_"+i+"_1' src='file:///"+new File(current_folder+"\\"+i+"_1."+this.FILE_EXTENSION).getPath()+"' alt='image' width='"+ImageIO.read(new File(current_folder+"\\"+i+"_1."+this.FILE_EXTENSION)).getWidth()+"' height='"+ImageIO.read(new File(current_folder+"\\"+i+"_1."+this.FILE_EXTENSION)).getHeight()+"' style='display: block; border: 0'/>");
				
				System.out.print("Insert URL for image "+i+"_1 or 'n' for none? "); 
				links = answer.readLine();
				if(!links.equals("n") && !links.equals("N")) {
					Element lnk_1 = document.getElementById(""+i+"_1");
					lnk_1.html("<a href='"+links+"' title='Link "+i+"_1' target='_blank'><img id='img_"+i+"_1' src='file:///"+new File(current_folder+"\\"+i+"_1."+this.FILE_EXTENSION).getPath()+"' alt='image' width='"+ImageIO.read(new File(current_folder+"\\"+i+"_1."+this.FILE_EXTENSION)).getWidth()+"' height='"+ImageIO.read(new File(current_folder+"\\"+i+"_1."+this.FILE_EXTENSION)).getHeight()+"' style='display: block; border: 0'/></a>");
				}
				
				td_1_.append("<td id='"+i+"_2'>");
				Element img_2 = document.getElementById(i+"_2");
				img_2.html("<img id='img_"+i+"_2' src='file:///"+new File(current_folder+"\\"+i+"_2."+this.FILE_EXTENSION).getPath()+"' alt='image' width='"+ImageIO.read(new File(current_folder+"\\"+i+"_2."+this.FILE_EXTENSION)).getWidth()+"' height='"+ImageIO.read(new File(current_folder+"\\"+i+"_2."+this.FILE_EXTENSION)).getHeight()+"' style='display: block; border: 0'/>");
				
				System.out.print("Insert URL for image "+i+"_2 or 'n' for none? "); 
				links = answer.readLine();
				if(!links.equals("n") && !links.equals("N")) {
					Element lnk_2 = document.getElementById(""+i+"_2");
					lnk_2.html("<a href='"+links+"' title='Link "+i+"_2' target='_blank'><img id='img_"+i+"_2' src='file:///"+new File(current_folder+"\\"+i+"_2."+this.FILE_EXTENSION).getPath()+"' alt='image' width='"+ImageIO.read(new File(current_folder+"\\"+i+"_2."+this.FILE_EXTENSION)).getWidth()+"' height='"+ImageIO.read(new File(current_folder+"\\"+i+"_2."+this.FILE_EXTENSION)).getHeight()+"' style='display: block; border: 0'/></a>");
				}
				
				td_1_.append("<td id='"+i+"_3'>");
				Element img_3 = document.getElementById(i+"_3");
				img_3.html("<img id='img_"+i+"_3' src='file:///"+new File(current_folder+"\\"+i+"_3."+this.FILE_EXTENSION).getPath()+"' alt='image' width='"+ImageIO.read(new File(current_folder+"\\"+i+"_3."+this.FILE_EXTENSION)).getWidth()+"' height='"+ImageIO.read(new File(current_folder+"\\"+i+"_3."+this.FILE_EXTENSION)).getHeight()+"' style='display: block; border: 0'/>");
				
				System.out.print("Insert URL for image "+i+"_3 or 'n' for none? "); 
				links = answer.readLine();
				if(!links.equals("n") && !links.equals("N")) {
					Element lnk_3 = document.getElementById(""+i+"_3");
					lnk_3.html("<a href='"+links+"' title='Link "+i+"_3' target='_blank'><img id='img_"+i+"_3' src='file:///"+new File(current_folder+"\\"+i+"_3."+this.FILE_EXTENSION).getPath()+"' alt='image' width='"+ImageIO.read(new File(current_folder+"\\"+i+"_3."+this.FILE_EXTENSION)).getWidth()+"' height='"+ImageIO.read(new File(current_folder+"\\"+i+"_3."+this.FILE_EXTENSION)).getHeight()+"' style='display: block; border: 0'/></a>");
				}
				
				System.out.println("\nRow number " + i + " contains 3 column\n");
			} else if (matriz_map.get(i) == 4) {
				Element tr_1 = document.getElementById(+i+"");
				tr_1.append("<td id='"+i+"___'>");
				Element td_1___ = document.getElementById(i+"___");
				td_1___.append("<table id='"+i+"__' cellspacing='0' cellpadding='0' width='650px'>");
				Element tr_1__ = document.getElementById(i+"__");
				tr_1__.append("<tr id='"+i+"_'>");
				
				Element td_1_ = document.getElementById(i+"_");
				td_1_.append("<td id='"+i+"_1'>");
				Element img_1 = document.getElementById(i+"_1");
				img_1.html("<img id='img_"+i+"_1' src='file:///"+new File(current_folder+"\\"+i+"_1."+this.FILE_EXTENSION).getPath()+"' alt='image' width='"+ImageIO.read(new File(current_folder+"\\"+i+"_1."+this.FILE_EXTENSION)).getWidth()+"' height='"+ImageIO.read(new File(current_folder+"\\"+i+"_1."+this.FILE_EXTENSION)).getHeight()+"' style='display: block; border: 0'/>");
				
				System.out.print("Insert URL for image "+i+"_1 or 'n' for none? "); 
				links = answer.readLine();
				if(!links.equals("n") && !links.equals("N")) {
					Element lnk_1 = document.getElementById(""+i+"_1");
					lnk_1.html("<a href='"+links+"' title='Link "+i+"_1' target='_blank'><img id='img_"+i+"_1' src='file:///"+new File(current_folder+"\\"+i+"_1."+this.FILE_EXTENSION).getPath()+"' alt='image' width='"+ImageIO.read(new File(current_folder+"\\"+i+"_1."+this.FILE_EXTENSION)).getWidth()+"' height='"+ImageIO.read(new File(current_folder+"\\"+i+"_1."+this.FILE_EXTENSION)).getHeight()+"' style='display: block; border: 0'/></a>");
				}
				
				td_1_.append("<td id='"+i+"_2'>");
				Element img_2 = document.getElementById(i+"_2");
				img_2.html("<img id='img_"+i+"_2' src='file:///"+new File(current_folder+"\\"+i+"_2."+this.FILE_EXTENSION).getPath()+"' alt='image' width='"+ImageIO.read(new File(current_folder+"\\"+i+"_2."+this.FILE_EXTENSION)).getWidth()+"' height='"+ImageIO.read(new File(current_folder+"\\"+i+"_2."+this.FILE_EXTENSION)).getHeight()+"' style='display: block; border: 0'/>");
				
				System.out.print("Insert URL for image "+i+"_2 or 'n' for none? "); 
				links = answer.readLine();
				if(!links.equals("n") && !links.equals("N")) {
					Element lnk_2 = document.getElementById(""+i+"_2");
					lnk_2.html("<a href='"+links+"' title='Link "+i+"_2' target='_blank'><img id='img_"+i+"_2' src='file:///"+new File(current_folder+"\\"+i+"_2."+this.FILE_EXTENSION).getPath()+"' alt='image' width='"+ImageIO.read(new File(current_folder+"\\"+i+"_2."+this.FILE_EXTENSION)).getWidth()+"' height='"+ImageIO.read(new File(current_folder+"\\"+i+"_2."+this.FILE_EXTENSION)).getHeight()+"' style='display: block; border: 0'/></a>");
				}
				
				td_1_.append("<td id='"+i+"_3'>");
				Element img_3 = document.getElementById(i+"_3");
				img_3.html("<img id='img_"+i+"_3' src='file:///"+new File(current_folder+"\\"+i+"_3."+this.FILE_EXTENSION).getPath()+"' alt='image' width='"+ImageIO.read(new File(current_folder+"\\"+i+"_3."+this.FILE_EXTENSION)).getWidth()+"' height='"+ImageIO.read(new File(current_folder+"\\"+i+"_3."+this.FILE_EXTENSION)).getHeight()+"' style='display: block; border: 0'/>");
				
				System.out.print("Insert URL for image "+i+"_3 or 'n' for none? "); 
				links = answer.readLine();
				if(!links.equals("n") && !links.equals("N")) {
					Element lnk_3 = document.getElementById(""+i+"_3");
					lnk_3.html("<a href='"+links+"' title='Link "+i+"_3' target='_blank'><img id='img_"+i+"_3' src='file:///"+new File(current_folder+"\\"+i+"_3."+this.FILE_EXTENSION).getPath()+"' alt='image' width='"+ImageIO.read(new File(current_folder+"\\"+i+"_3."+this.FILE_EXTENSION)).getWidth()+"' height='"+ImageIO.read(new File(current_folder+"\\"+i+"_3."+this.FILE_EXTENSION)).getHeight()+"' style='display: block; border: 0'/></a>");
				}
				
				td_1_.append("<td id='"+i+"_4'>");
				Element img_4 = document.getElementById(i+"_4");
				img_4.html("<img id='img_"+i+"_4' src='file:///"+new File(current_folder+"\\"+i+"_4."+this.FILE_EXTENSION).getPath()+"' alt='image' width='"+ImageIO.read(new File(current_folder+"\\"+i+"_4."+this.FILE_EXTENSION)).getWidth()+"' height='"+ImageIO.read(new File(current_folder+"\\"+i+"_4."+this.FILE_EXTENSION)).getHeight()+"' style='display: block; border: 0'/>");
				
				System.out.print("Insert URL for image "+i+"_4 or 'n' for none? "); 
				links = answer.readLine();
				if(!links.equals("n") && !links.equals("N")) {
					Element lnk_4 = document.getElementById(""+i+"_4");
					lnk_4.html("<a href='"+links+"' title='Link "+i+"_4' target='_blank'><img id='img_"+i+"_4' src='file:///"+new File(current_folder+"\\"+i+"_4."+this.FILE_EXTENSION).getPath()+"' alt='image' width='"+ImageIO.read(new File(current_folder+"\\"+i+"_4."+this.FILE_EXTENSION)).getWidth()+"' height='"+ImageIO.read(new File(current_folder+"\\"+i+"_4."+this.FILE_EXTENSION)).getHeight()+"' style='display: block; border: 0'/></a>");
				}
				
				System.out.println("\nRow number " + i + " contains 4 column\n");
			} else if (matriz_map.get(i) == 5) {
				Element tr_1 = document.getElementById(+i+"");
				tr_1.append("<td id='"+i+"___'>");
				Element td_1___ = document.getElementById(i+"___");
				td_1___.append("<table id='"+i+"__' cellspacing='0' cellpadding='0' width='650px'>");
				Element tr_1__ = document.getElementById(i+"__");
				tr_1__.append("<tr id='"+i+"_'>");
				
				Element td_1_ = document.getElementById(i+"_");
				td_1_.append("<td id='"+i+"_1'>");
				Element img_1 = document.getElementById(i+"_1");
				img_1.html("<img id='img_"+i+"_1' src='file:///"+new File(current_folder+"\\"+i+"_1."+this.FILE_EXTENSION).getPath()+"' alt='image' width='"+ImageIO.read(new File(current_folder+"\\"+i+"_1."+this.FILE_EXTENSION)).getWidth()+"' height='"+ImageIO.read(new File(current_folder+"\\"+i+"_1."+this.FILE_EXTENSION)).getHeight()+"' style='display: block; border: 0'/>");
				
				td_1_.append("<td id='"+i+"_2'>");
				Element img_2 = document.getElementById(i+"_2");
				img_2.html("<img id='img_"+i+"_2' src='file:///"+new File(current_folder+"\\"+i+"_2."+this.FILE_EXTENSION).getPath()+"' alt='image' width='"+ImageIO.read(new File(current_folder+"\\"+i+"_2."+this.FILE_EXTENSION)).getWidth()+"' height='"+ImageIO.read(new File(current_folder+"\\"+i+"_2."+this.FILE_EXTENSION)).getHeight()+"' style='display: block; border: 0'/>");
				
				td_1_.append("<td id='"+i+"_3'>");
				Element img_3 = document.getElementById(i+"_3");
				img_3.html("<img id='img_"+i+"_3' src='file:///"+new File(current_folder+"\\"+i+"_3."+this.FILE_EXTENSION).getPath()+"' alt='image' width='"+ImageIO.read(new File(current_folder+"\\"+i+"_3."+this.FILE_EXTENSION)).getWidth()+"' height='"+ImageIO.read(new File(current_folder+"\\"+i+"_3."+this.FILE_EXTENSION)).getHeight()+"' style='display: block; border: 0'/>");
				
				td_1_.append("<td id='"+i+"_4'>");
				Element img_4 = document.getElementById(i+"_4");
				img_4.html("<img id='img_"+i+"_4' src='file:///"+new File(current_folder+"\\"+i+"_4."+this.FILE_EXTENSION).getPath()+"' alt='image' width='"+ImageIO.read(new File(current_folder+"\\"+i+"_4."+this.FILE_EXTENSION)).getWidth()+"' height='"+ImageIO.read(new File(current_folder+"\\"+i+"_4."+this.FILE_EXTENSION)).getHeight()+"' style='display: block; border: 0'/>");
				
				td_1_.append("<td id='"+i+"_5'>");
				Element img_5 = document.getElementById(i+"_5");
				img_5.html("<img id='img_"+i+"_5' src='file:///"+new File(current_folder+"\\"+i+"_5."+this.FILE_EXTENSION).getPath()+"' alt='image' width='"+ImageIO.read(new File(current_folder+"\\"+i+"_5."+this.FILE_EXTENSION)).getWidth()+"' height='"+ImageIO.read(new File(current_folder+"\\"+i+"_5."+this.FILE_EXTENSION)).getHeight()+"' style='display: block; border: 0'/>");
				
				System.out.println("\nRow number " + i + " contains 5 column\n");
			} else if (matriz_map.get(i) == 6) {
				Element tr_1 = document.getElementById(+i+"");
				tr_1.append("<td id='"+i+"___'>");
				Element td_1___ = document.getElementById(i+"___");
				td_1___.append("<table id='"+i+"__' cellspacing='0' cellpadding='0' width='650px'>");
				Element tr_1__ = document.getElementById(i+"__");
				tr_1__.append("<tr id='"+i+"_'>");
				
				Element td_1_ = document.getElementById(i+"_");
				td_1_.append("<td id='"+i+"_1'>");
				Element img_1 = document.getElementById(i+"_1");
				img_1.html("<img id='img_"+i+"_1' src='file:///"+new File(current_folder+"\\"+i+"_1."+this.FILE_EXTENSION).getPath()+"' alt='image' width='"+ImageIO.read(new File(current_folder+"\\"+i+"_1."+this.FILE_EXTENSION)).getWidth()+"' height='"+ImageIO.read(new File(current_folder+"\\"+i+"_1."+this.FILE_EXTENSION)).getHeight()+"' style='display: block; border: 0'/>");
				
				td_1_.append("<td id='"+i+"_2'>");
				Element img_2 = document.getElementById(i+"_2");
				img_2.html("<img id='img_"+i+"_2' src='file:///"+new File(current_folder+"\\"+i+"_2."+this.FILE_EXTENSION).getPath()+"' alt='image' width='"+ImageIO.read(new File(current_folder+"\\"+i+"_2."+this.FILE_EXTENSION)).getWidth()+"' height='"+ImageIO.read(new File(current_folder+"\\"+i+"_2."+this.FILE_EXTENSION)).getHeight()+"' style='display: block; border: 0'/>");
				
				td_1_.append("<td id='"+i+"_3'>");
				Element img_3 = document.getElementById(i+"_3");
				img_3.html("<img id='img_"+i+"_3' src='file:///"+new File(current_folder+"\\"+i+"_3."+this.FILE_EXTENSION).getPath()+"' alt='image' width='"+ImageIO.read(new File(current_folder+"\\"+i+"_3."+this.FILE_EXTENSION)).getWidth()+"' height='"+ImageIO.read(new File(current_folder+"\\"+i+"_3."+this.FILE_EXTENSION)).getHeight()+"' style='display: block; border: 0'/>");
				
				td_1_.append("<td id='"+i+"_4'>");
				Element img_4 = document.getElementById(i+"_4");
				img_4.html("<img id='img_"+i+"_4' src='file:///"+new File(current_folder+"\\"+i+"_4."+this.FILE_EXTENSION).getPath()+"' alt='image' width='"+ImageIO.read(new File(current_folder+"\\"+i+"_4."+this.FILE_EXTENSION)).getWidth()+"' height='"+ImageIO.read(new File(current_folder+"\\"+i+"_4."+this.FILE_EXTENSION)).getHeight()+"' style='display: block; border: 0'/>");
				
				td_1_.append("<td id='"+i+"_5'>");
				Element img_5 = document.getElementById(i+"_5");
				img_5.html("<img id='img_"+i+"_5' src='file:///"+new File(current_folder+"\\"+i+"_5."+this.FILE_EXTENSION).getPath()+"' alt='image' width='"+ImageIO.read(new File(current_folder+"\\"+i+"_5."+this.FILE_EXTENSION)).getWidth()+"' height='"+ImageIO.read(new File(current_folder+"\\"+i+"_5."+this.FILE_EXTENSION)).getHeight()+"' style='display: block; border: 0'/>");
				
				td_1_.append("<td id='"+i+"_6'>");
				Element img_6 = document.getElementById(i+"_6");
				img_6.html("<img id='img_"+i+"_6' src='file:///"+new File(current_folder+"\\"+i+"_6."+this.FILE_EXTENSION).getPath()+"' alt='image' width='"+ImageIO.read(new File(current_folder+"\\"+i+"_6."+this.FILE_EXTENSION)).getWidth()+"' height='"+ImageIO.read(new File(current_folder+"\\"+i+"_6."+this.FILE_EXTENSION)).getHeight()+"' style='display: block; border: 0'/>");
				
				System.out.println("\nRow number " + i + " contains 6 column\n");
			} else {
				System.out.println("Row number " + i + " contains more than 6 column, exiting program\n");
				System.exit(0);
			}
			
		} 
		} catch (IIOException iioex) {
			System.out.println("\n*******************************************************************************************************************************");
			System.out.println("Could not find the sliced image in the folder, you indicated an incorrect number of columns per row. Terminating the program...");
			System.out.println("*******************************************************************************************************************************\n");
			iioex.printStackTrace();
			System.exit(0);
		}
		
		String clean_table = document.html().replaceAll("<tbody>", "");
		clean_table = clean_table.replaceAll("</tbody>", "");
		clean_table = clean_table.replaceAll("(?m)^[ \t]*\r?\n", "");
		
		byte[] strToBytes = clean_table.getBytes();
		outputStream.write(strToBytes);
		outputStream.close();
		System.out.println("===================================================================");
		System.out.println("===================================================================");
		System.out.println("Creative file generated successfully in the current working folder.");
		System.out.println("This is the creative: \n");
		System.out.println(clean_table);
		
		this.matriz_map = matriz_map;
		return document.html();
	}

}
//
