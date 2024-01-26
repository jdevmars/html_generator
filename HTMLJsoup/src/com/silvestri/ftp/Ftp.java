/*
 * What to do next:
 * 
 * 1) to find a way to upload a whole folder
 * 2) to create a method to change the image paths to reflect the FTP before uploading
 * 3) to determine a fixed folder for putting the images and the creative in the hard disk
 * 
 */

package com.silvestri.ftp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class Ftp {
	
    public void connect_accor(String html) {
    	
    	/*
    	String server = "expertsenderbrasil.com.br";
        int port = 21;
        String user = "accor@expertsenderbrasil.com.br";
        String pass = "accor@2018";
        */
    	
    	String server = "ftp.mpsilvestri.com";
        int port = 21;
        String user = "java@images.mpsilvestri.com";
        String pass = "M@arcosP@aulo01";
        
        String current_folder = System.getProperty("user.dir");

        // File folder = new File("C:\\Users\\marco\\Documents\\Clientes\\Accor\\2023\\SOF\\02_02"); // => mudar para System.getProperty("user.dir")
        File folder = new File(current_folder);
		File[] listOfFiles = folder.listFiles();
		
		System.out.println("There are " + listOfFiles.length + " items in the folder");
		System.out.println("These are the files available for upload:");
		System.out.println("===========================================================");
		for (File file : listOfFiles) {
			if (file.isFile()) {
				System.out.println(file.getName());
			}
		}
        
        BufferedReader answer = new BufferedReader(new InputStreamReader(System.in));
        
        Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT-3:00"));
        
        
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        
        FTPClient ftpClient = new FTPClient();
        
        try {
        	
        	 ftpClient.connect(server, port);
             ftpClient.login(user, pass);
             ftpClient.enterLocalPassiveMode();
             ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
             
             System.out.print("\nWhich Accor flag to use (1) - (accorhotels) // (2) - (accormail) // (3) - (pullmanspibirapuera) // (4) - Mocha? ");
             String flag = answer.readLine();

             switch(flag) {
             	case "1": {
             		ftpClient.makeDirectory("accorhotels/"+year+"/");
             		ftpClient.makeDirectory("accorhotels/"+year+"/"+day+"_"+month+"/");
                    
                    File creative = new File(current_folder+"\\"+"message_2.html");
                    
                    String remoteCreative = "accorhotels/"+year+"/"+day+"_"+month+"/message_2.html";
                    
                    InputStream inputStream = new FileInputStream(creative);
                    
                    System.out.println("Start uploading first file");
                    
                    boolean done = ftpClient.storeFile(remoteCreative, inputStream);
                    
                    inputStream.close();
                    
                    if (done) {
                        System.out.println("The file has been uploaded successfully.");
                    } else {
                   	 System.out.println("There has been a problem with the file upload. Probably the destination folder does not exist.");
                    }
                    break;
             	} 
             	case "2": {
             		ftpClient.makeDirectory("accormail/"+year+"/");
             		ftpClient.makeDirectory("accormail/"+year+"/"+day+"_"+month+"/");
                    
                    File creative = new File("C:\\Users\\marco\\Pictures\\Java\\message_2.html");
                    
                    String remoteCreative = "accormail/"+year+"/"+day+"_"+month+"/message_2.html";
                    
                    InputStream inputStream = new FileInputStream(creative);
                    
                    System.out.println("Start uploading first file");
                    
                    boolean done = ftpClient.storeFile(remoteCreative, inputStream);
                    
                    inputStream.close();
                    
                    if (done) {
                        System.out.println("The file has been uploaded successfully.");
                    } else {
                   	 System.out.println("There has been a problem with the file upload. Probably the destination folder does not exist.");
                    }
                    break;
             	}
             	case "3": {
             		ftpClient.makeDirectory("pullmanspibirapuera/"+year+"/");
             		ftpClient.makeDirectory("pullmanspibirapuera/"+year+"/"+day+"_"+month+"/");
                    
                    File creative = new File("C:\\Users\\marco\\Pictures\\Java\\message_2.html");
                    
                    String remoteCreative = "pullmanspibirapuera/"+year+"/"+day+"_"+month+"/message_2.html";
                    
                    InputStream inputStream = new FileInputStream(creative);
                    
                    System.out.println("Start uploading first file");
                    
                    boolean done = ftpClient.storeFile(remoteCreative, inputStream);
                    
                    inputStream.close();
                    
                    if (done) {
                        System.out.println("The file has been uploaded successfully.");
                    } else {
                   	 System.out.println("There has been a problem with the file upload. Probably the destination folder does not exist.");
                    }
             	}
             	case "4": {
             		
             		FileOutputStream outputStream = new FileOutputStream(current_folder + "\\" + "message_ftp.html");
             		
             		for (int i = 0; i < listOfFiles.length; i++) {
             			
             			Pattern p = Pattern.compile("\\d+");
             		    Matcher m = p.matcher(listOfFiles[i].getName());
                    	
             			if (m.find()) { // se encontrar ao menos 1 algarismo em nome de arquivo
             			
	             			String remoteFile = "/"+listOfFiles[i].getName();
	                    
	        	            InputStream inputStream = new FileInputStream(listOfFiles[i]);

	        	            System.out.println("\nUploading file " + listOfFiles[i].getName());
	        	            
	        	            boolean done = ftpClient.storeFile(remoteFile, inputStream);
	        	            
	        	            if (done) {
	        	                System.out.println("The file has been uploaded successfully.");
	        	            } else {
	        	           	 System.out.println("There has been a problem with the folder upload. Probably the destination folder does not exist.");
	        	            }
	        	            
	        	            inputStream.close();
        	            
        	            } else { // se não encontrar ao menos 1 algarismo em nome de arquivo
        	            	
        	            	if (!listOfFiles[i].getName().contains("html")) { // se não tiver algarismo e não conter "html"
        	            		System.out.println("\n"+ listOfFiles[i].getName() + " is not being imported!");
        	            	}
        	            	
        	            	if (listOfFiles[i].getName().contains("html") && !listOfFiles[i].getName().contains("ftp")) { // se não conter algarismo e nem conter "ftp", porém conter "html"
        	            		
        	            		//File message_ftp = new File(System.getProperty("user.dir")+"\\message.html");
        	                    //FileUtils.copyFile(original, copied);
        	            		
        	            		/*************************
        	            		String MESSAGE_HTML_FILE_NAME = "message.html";
        	            		 
        	            		String TRANSFORMED_MESSAGE_HTML_FILE_NAME = "message_ftp.html";
        	            		 
        	            		String FTP_PATH = "/Java/FTP/";
        	            		
        	            		var origin = Path.of(MESSAGE_HTML_FILE_NAME);
        	            		var destination = Path.of(TRANSFORMED_MESSAGE_HTML_FILE_NAME);
        	            		 
        	            		var urlToReplace = String.format("file:///%s\\", System.getProperty("user.dir"));
        	            		
        	            		var reader = Files.newBufferedReader(origin);
        	            		var writer = Files.newBufferedWriter(destination, CREATE_NEW, WRITE);
        	            		
        	            		for (
        	            			    var line = reader.readLine();
        	            			    line != null;
        	            			    line = reader.readLine();
        	            			  ) {
        	            			    var replacedLine = line.replace(urlToReplace, FTP_PATH);
        	            			 
        	            			    writer.write(replacedLine);
        	            			    writer.newLine();
        	            			  }
        	            		*************************/
        	            		
        	            		/*
        	            		if (!new File(System.getProperty("user.dir")+"\\message_ftp.html").exists())
        	            			Files.copy(new File(System.getProperty("user.dir")+"\\message.html").toPath(), new File(System.getProperty("user.dir")+"\\message_ftp.html").toPath());
        	            		 */
        	            		
        	            		/*
        	            		FileWriter myWriter = new FileWriter(System.getProperty("user.dir")+"\\message_ftp.html");
        	            		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(System.getProperty("user.dir")+"\\message.html"))));
        	            		String line;
        	            		String current_path = "file:///" + System.getProperty("user.dir") + "\\";
        	                    while ((line = br.readLine()) != null) { // => consegui alterar cada linha com replace, porém falta salvar o arquivo com essas alterações
        	                        if (line.contains(current_path)) {
        	                        	System.out.println("Before Replace: " + line);
        	                        	line = line.replace(current_path, "/Java/FTP/");
        	                        	System.out.println("After Replace: " + line);
        	                        	
        	                        	myWriter.write(line);
        	                        } else {
        	                        	System.out.println("Line does not contain the current path");
        	                        }
        	                    
        	                    	
        	                    }
        	                    
        	                    myWriter.close();
        	                    br.close();
        	            		
        	            		*/
        	            		
        	            		
        	            		
        	            		//FileInputStream fis = new FileInputStream(new File(System.getProperty("user.dir")+"\\message_ftp.html"));
        	            		/*
        	            		int content;
        	            		while ((content = fis.read()) != -1) {
        	            			System.out.println((char)content);
        	            		}
        	            		*/
        	            		
        	            		/*
        	            		FileWriter myWriter = new FileWriter(System.getProperty("user.dir")+"\\message_ftp.html");
        	            		// html.replace("file:///" + System.getProperty("user.dir"), "/Java/FTP/");
        	            		html = html.replace(html.substring(html.indexOf("file"), html.lastIndexOf("\"")), "/Java/FTP/"); // ===> trocar tudo entre src=" e \, está trocando errado
        	            		myWriter.write(html);
        	            		myWriter.close();
        	            		*/
        	            		
        	            		/*
        	            		InputStream is = new FileInputStream(new File(System.getProperty("user.dir")+"\\message.html"));
        	            		OutputStream os = new FileOutputStream(new File(System.getProperty("user.dir")+"\\message_ftp.html"));
        	            		byte[] buffer = new byte[1024];
        	            		int length;
        	            		while ((length = is.read(buffer)) > 0) {
        	                        os.write(buffer, 0, length);
        	                    }
        	            		is.close();
        	                    os.close();
        	                    */
        	            		
        	            		
        	            		
        	            		Document document = Jsoup.parse(new File(System.getProperty("user.dir")+"\\message.html"));
        	            		Elements elements = document.select("img");
        	            		for (int j = 0; j < elements.size(); j++) {
	        	            		elements.get(j).attr("src", "https://images.mpsilvestri.com/campaigns/" + listOfFiles[i].getName());
        	            		}
        	            		
        	            		String html_ftp = document.html();
        	            		byte[] strToBytes = html_ftp.getBytes();
        	            		outputStream.write(strToBytes);
        	            		outputStream.close();
        	            		
        	            		String remoteFile = "/"+listOfFiles[i].getName();
        	                    
    	        	            InputStream inputStream = new FileInputStream(listOfFiles[i]);

    	        	            System.out.println("\nUploading file " + listOfFiles[i].getName());
    	        	            
    	        	            boolean done = ftpClient.storeFile(remoteFile, inputStream);
    	        	            
    	        	            if (done) {
    	        	                System.out.println("The file has been uploaded successfully.");
    	        	            } else {
    	        	           	 System.out.println("There has been a problem with the folder upload. Probably the destination folder does not exist.");
    	        	            }
    	        	            
    	        	            inputStream.close();
        	            	}
        	            	
        	            }
        	            
                    }
             		break;
             	}
             	default: {
             		
             	}
             	
             	
             		
             }

        } catch(IOException ioe) {
        	
        	System.out.println("Error: " + ioe.getMessage());
            ioe.printStackTrace();
                    	
        } finally {
        	try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void connect_adcos() {
    	
    }
    
    public void generate_ftp_version() {
    	try {
    		
    	if (!new File(System.getProperty("user.dir")+"\\message_ftp.html").exists()) {
			Files.copy(new File(System.getProperty("user.dir")+"\\message.html").toPath(), new File(System.getProperty("user.dir")+"\\message_ftp.html").toPath());
    		
    		FileWriter myWriter = new FileWriter(System.getProperty("user.dir")+"\\message_ftp.html");
    		
    		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(System.getProperty("user.dir")+"\\message_ftp.html"))));
    		
    		String line;
    		String current_path = "file:///" + System.getProperty("user.dir") + "\\";
    		
    		while ((line = br.readLine()) != null) { // => consegui alterar cada linha com replace, porém falta salvar o arquivo com essas alterações
                if (line.contains(current_path)) {
                	System.out.println("Before Replace: " + line);
                	line = line.replace(current_path, "/Java/FTP/");
                	System.out.println("After Replace: " + line);
                	
                	//myWriter.write(line);
                } else {
                	System.out.println("File does not contain the current path");
                }
    		}
    		
    		myWriter.close();
            br.close();
            
            
    	}
			
    	} catch (IOException e) {
				e.printStackTrace();
		}
    }

}
