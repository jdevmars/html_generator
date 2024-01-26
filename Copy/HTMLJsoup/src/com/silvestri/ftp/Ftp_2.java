package com.silvestri.ftp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Ftp_2 {
	
	private final String CURRENT_FOLDER = System.getProperty("user.dir");
	
	private File folder = new File(CURRENT_FOLDER);
	private File[] listOfFiles = folder.listFiles();
	
	private BufferedReader answer = new BufferedReader(new InputStreamReader(System.in));
    
    private Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT-3:00"));
    
    private int year = calendar.get(Calendar.YEAR);
    private int month = calendar.get(Calendar.MONTH) + 1;
    private int day = calendar.get(Calendar.DAY_OF_MONTH);
    private int hour = calendar.get(Calendar.HOUR_OF_DAY);
    private int minute = calendar.get(Calendar.MINUTE);
    
    private String project = "";
    
    private String final_path = "";
    
	public String getFinal_path() {
		return final_path;
	}

	public void list_files() {
		
		System.out.println("\n=================================");
		System.out.println("There are " + listOfFiles.length + " items in the folder\n");
		System.out.println("These are the files available for upload: ");
		System.out.println("=========================================");
		for (File file : listOfFiles) {
			if (file.isFile()) {
				System.out.println("=> " + file.getName());
			}
		}
	}
	
	public void generate_copy() {
		try {
			
			if (!new File(System.getProperty("user.dir")+"\\message_ftp.html").exists()) {
				Files.copy(new File(System.getProperty("user.dir")+"\\message.html").toPath(), new File(System.getProperty("user.dir")+"\\message_ftp.html").toPath());
			} else {
				new File(System.getProperty("user.dir")+"\\message_ftp.html").delete();
				Files.copy(new File(System.getProperty("user.dir")+"\\message.html").toPath(), new File(System.getProperty("user.dir")+"\\message_ftp.html").toPath());
			}

		} catch (IOException ioe) {
			System.out.println("****************************");
			System.out.println("Issue to generate file copy: ");
			System.out.println("****************************");
			ioe.printStackTrace();
		}
	}
	
	public String create_final_file(String ftp_option, String project) {
		
		String ftp_path_images = "";
		String ftp_path = "";
		
		try {

			Document document = Jsoup.parse(new File(System.getProperty("user.dir")+"\\message_ftp.html"));
			
			Elements images = document.getElementsByTag("img");
			
			for (Element image : images) {
			
				String line = image.toString();
				
				if (ftp_option.equals("5")) {
					ftp_path_images = "https://images.mpsilvestri.com/campaigns/";
					image.attr("src", ftp_path_images + line.substring(line.lastIndexOf("\\")+1, line.indexOf("alt")-2));
					ftp_path = "https://images.mpsilvestri.com/campaigns/"+"creative.html";
					this.final_path = ftp_path;
				}
				else if (ftp_option.equals("4")) {
					ftp_path_images = "https://expertsenderbrasil.com.br/adcos/"+project+"/"+year+"/"+"day_"+day+"_"+month+"_hour_"+hour+"_"+minute+"/";
					image.attr("src", ftp_path_images + line.substring(line.lastIndexOf("\\")+1, line.indexOf("alt")-2));
					ftp_path = "https://expertsenderbrasil.com.br/adcos/"+project+"/"+year+"/"+"day_"+day+"_"+month+"_hour_"+hour+"_"+minute+"/"+"creative.html";
					this.final_path = ftp_path;
				}
				else if (ftp_option.equals("3")) {
					ftp_path_images = "https://expertsenderbrasil.com.br/accor/pullmanspibirapuera/"+year+"/"+"day_"+day+"_"+month+"_hour_"+hour+"_"+minute+"/";
					image.attr("src", ftp_path_images + line.substring(line.lastIndexOf("\\")+1, line.indexOf("alt")-2));
					ftp_path = "https://expertsenderbrasil.com.br/accor/pullmanspibirapuera/"+year+"/"+"day_"+day+"_"+month+"_hour_"+hour+"_"+minute+"/"+"creative.html";
					this.final_path = ftp_path;
				} else if (ftp_option.equals("2")) {
					ftp_path_images = "https://expertsenderbrasil.com.br/accor/accormail/"+year+"/"+"day_"+day+"_"+month+"_hour_"+hour+"_"+minute+"/";
					image.attr("src", ftp_path_images + line.substring(line.lastIndexOf("\\")+1, line.indexOf("alt")-2));
					ftp_path = "https://expertsenderbrasil.com.br/accor/accormail/"+year+"/"+"day_"+day+"_"+month+"_hour_"+hour+"_"+minute+"/"+"creative.html";
					this.final_path = ftp_path;
				} else if (ftp_option.equals("1")) {
					ftp_path_images = "https://expertsenderbrasil.com.br/accor/accorhotels/"+year+"/"+"day_"+day+"_"+month+"_hour_"+hour+"_"+minute+"/";
					image.attr("src", ftp_path_images + line.substring(line.lastIndexOf("\\")+1, line.indexOf("alt")-2));
					ftp_path = "https://expertsenderbrasil.com.br/accor/accorhotels/"+year+"/"+"day_"+day+"_"+month+"_hour_"+hour+"_"+minute+"/"+"creative.html";
					this.final_path = ftp_path;
			}
				
			String html_ftp = document.html();
			FileOutputStream outputStream = new FileOutputStream(CURRENT_FOLDER + "\\" + "creative.html");
			byte[] strToBytes = html_ftp.getBytes();
			outputStream.write(strToBytes);
			outputStream.close();

		}
			
		} catch (IOException ioe) {
			System.out.println("*********************************************************************************************");
			System.out.println("Possible issue for Jsoup to parse the FTP copy of the file OR generation of amended FTP copy: ");
			System.out.println("*********************************************************************************************");
			ioe.printStackTrace();
		}
		
		return ftp_path;
	}
	
	public void which_ftp(String html) {
		
		System.out.println("\nTo which FTP accout would you like to connect?\n");
		System.out.println("1) accorhotels");
		System.out.println("2) accormail");
		System.out.println("3) pullmanspibirapuera");
		System.out.println("4) adcos");
		System.out.println("5) mocha");
        
		try {
			
			System.out.print("\n=> Answer: "); String ftp_option = answer.readLine();
			
			switch(ftp_option) {
				case "1": {
					this.project = "";
					String ftp_path = create_final_file(ftp_option, project);
					upload_accor_hotels(html);
					System.out.println("\n===================================================");
					System.out.println("This is the path for the generated creative online: ");
					System.out.println(ftp_path);
					System.out.println("===================================================\n");
					break;
				}
				case "2": {
					this.project = "";
					String ftp_path = create_final_file(ftp_option, project);
					upload_accor_mail(html);
					System.out.println("\n===================================================");
					System.out.println("This is the path for the generated creative online: ");
					System.out.println(ftp_path);
					System.out.println("===================================================\n");
					break;
				}
				case "3": {
					this.project = "";
					String ftp_path = create_final_file(ftp_option, project);
					upload_accor_pullmanspibirapuera(html);
					System.out.println("\n===================================================");
					System.out.println("This is the path for the generated creative online: ");
					System.out.println(ftp_path);
					System.out.println("===================================================\n");
					break;
				}
				case "4": {
					
					try {
					
					System.out.println("\n=> To which account does the creative belong to? \n");
					System.out.println("1) adcospro");
					System.out.println("2) homecare");
					System.out.println("3) teste");
					System.out.print("\n=> Answer: "); String option = answer.readLine();
					
					switch(option) {
						case "1": {
							this.project = "adcospro";
						} break;
						case "2": {
							this.project = "homecare";
						} break;
						case "3": {
							this.project = "teste";
						} break;
						default: {
							System.out.println("\n*****************************************************************");
							System.out.println("You've informed an incorrect option!!! Terminating the program...");
							System.out.println("*****************************************************************\n");
							System.exit(0);
						} break;
					}
					
				} catch (NumberFormatException nfe) {
					System.out.println("\n**************************************");
					System.out.println("You've informed an incorrect option!!!");
					System.out.println("**************************************\n");
				} catch (IOException e) {
					System.out.println("\n***************************************************");
					System.out.println("Possible problem to connect to the remote server!!!");
					System.out.println("***************************************************\n");
					e.printStackTrace();
				}
					
					String ftp_path = create_final_file(ftp_option, project);
					connect_upload_adcos(html);
					System.out.println("\n===================================================");
					System.out.println("This is the path for the generated creative online: ");
					System.out.println(ftp_path);
					System.out.println("===================================================\n");
					break;
					
				}
				case "5": {
					this.project = "";
					String ftp_path = create_final_file(ftp_option, project);
					connect_upload_mocha_campaigns(html);
					System.out.println("\n===================================================");
					System.out.println("This is the path for the generated creative online: ");
					System.out.println(ftp_path);
					System.out.println("===================================================\n");
					break;
				}
			} 
		
		} catch (IOException ioe) {
			System.out.println("\n***********************************************************************************************");
			System.out.println("It was not possible to read the answer about which FTP to connect!!! Terminating the program...");
			System.out.println("***********************************************************************************************\n");
			System.exit(0);
		}
	}
	
	public void connect_upload_mocha_campaigns(String html) {

		final String SERVER = "ftp.mpsilvestri.com";
		final int PORT = 21;
		final String USER = "java@images.mpsilvestri.com";
		final String PASS = "M@arcosP@aulo01";
		
		FTPClient ftpClient = new FTPClient();
		InputStream inputStream = null;
		
		try {
				
			ftpClient.connect(SERVER, PORT);
	        ftpClient.login(USER, PASS);
	        ftpClient.enterLocalPassiveMode();
	        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
	        
	        for (int i = 0; i < listOfFiles.length; i++) {
	 			
	 			Pattern p = Pattern.compile("\\d+");
	 		    Matcher m = p.matcher(listOfFiles[i].getName());
	        	
	 			if (m.find()) { // se encontrar ao menos 1 algarismo em nome de arquivo, proceda com o upload
	 			
	     			String remoteFile = "/"+listOfFiles[i].getName();
	            
		            inputStream = new FileInputStream(listOfFiles[i]);

		            System.out.println("\nUploading file " + listOfFiles[i].getName());
		            
		            boolean done = ftpClient.storeFile(remoteFile, inputStream);
		            
		            if (done) {
		                System.out.println("=== The file has been uploaded successfully. ===");
		            } else {
		           	 System.out.println("*** There has been a problem with the folder upload. Probably the destination folder does not exist. ***");
		            }
		            
		            inputStream.close();
	            
	            } else { // se não encontrar ao menos 1 algarismo em nome de arquivo
	            	
	            	// não importe o arquivo caso ele não contenha "html" no nome
	            	if (!listOfFiles[i].getName().contains("html")) { // se não tiver algarismo e não conter "html"
	            		System.out.println("\n"+ listOfFiles[i].getName() + " is not being imported!");
	            	}
	            	
	            	// captura o arquivo html ready
	            	if (listOfFiles[i].getName().contains("html") && listOfFiles[i].getName().contains("ready")) { // se não conter algarismo, e se for o html ready
	            		String remoteFile = "/"+listOfFiles[i].getName();
	                    
	    	            inputStream = new FileInputStream(listOfFiles[i]);

	    	            System.out.println("\nUploading file " + listOfFiles[i].getName());
	    	            
	    	            boolean done = ftpClient.storeFile(remoteFile, inputStream);
	    	            
	    	            if (done) {
	    	                System.out.println("=== The file has been uploaded successfully. ===");
	    	            } else {
	    	           	 System.out.println("*** There has been a problem with the upload of the final HTML file. Probably the destination folder does not exist. ***");
	    	            }
	    	            
	            	}
			
	            }
			}
	        
	        boolean final_file_exists = new File(CURRENT_FOLDER + "\\" + "creative.html").exists();
			if (final_file_exists) {
				String remoteFile = "/"+"creative.html";	
	            
	            inputStream = new FileInputStream(new File(CURRENT_FOLDER + "\\" + "creative.html"));

	            System.out.println("\nUploading file " + "creative.html");
	            
	            boolean done = ftpClient.storeFile(remoteFile, inputStream);
	            
	            if (done) {
	                System.out.println("=== The file has been uploaded successfully. ===");
	            } else {
	           	 System.out.println("*** There has been a problem with the upload of the final HTML file. Probably the destination folder does not exist. ***");
	            }
			} else {
				System.out.println("*** File (creative.html) NOT FOUND for upload!!! ***");
			}
	        
	        
			} catch (FileNotFoundException fnfe) {
				System.out.println("\n*************************************************");
				System.out.println("It was not possible to find the file on hard disk");
				System.out.println("*************************************************\n");
			} catch (IOException ioe) {
				System.out.println("\n*************************************************************");
				System.out.println("Possible problem with connecting to the Mocca's remove server");
				System.out.println("*************************************************************\n");
			} finally {
		        try {
					ftpClient.disconnect();
				} catch (IOException e) {
					System.out.println("\n**********************************************************");
					System.out.println("Impossibility to close FTP connection to the remote server");
					System.out.println("**********************************************************\n");
					e.printStackTrace();
				}
		        try {
					inputStream.close();
				} catch (IOException e) {
					System.out.println("\n********************************");
					System.out.println("Problem to close the inputStream");
					System.out.println("********************************\n");
					e.printStackTrace();
				}
			}
	}
	
	// será chamado por cada método upload abaixo
	public FTPClient connect_accor() {
		
		String SERVER = "expertsenderbrasil.com.br";
        int PORT = 21;
        String USER = "accor@expertsenderbrasil.com.br";
        String PASS = "accor@2018";
        
        FTPClient ftpClient = new FTPClient();
        
        try {
        
        ftpClient.connect(SERVER, PORT);
        ftpClient.login(USER, PASS);
        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        
        } catch (IOException ioe) {
        	System.out.println("\n************************************************");
        	System.out.println("It was not possible to connect to Accor's FTP!!!");
        	System.out.println("************************************************\n");
        }
        
        return ftpClient;
		
	}
	
	public void upload_accor_hotels(String html) {
		
		FTPClient ftpClient = connect_accor();
		InputStream inputStream = null;
		
		try {
			
			ftpClient.makeDirectory("accorhotels/"+year+"/");
			//ftpClient.makeDirectory("accorhotels/"+year+"/"+day+"_"+month+"/");
			ftpClient.makeDirectory("accorhotels/"+year+"/"+"day_"+day+"_"+month+"_hour_"+hour+"_"+minute+"/");
		
		for (int i = 0; i < listOfFiles.length; i++) {
 			
			Pattern p = Pattern.compile("\\d+");
 		    Matcher m = p.matcher(listOfFiles[i].getName());
        	
 			if (m.find()) { // se encontrar ao menos 1 algarismo em nome de arquivo, proceda com o upload
 			
     			// String remoteFile = "/"+listOfFiles[i].getName();
 				String remoteFile = "accorhotels/"+year+"/"+"day_"+day+"_"+month+"_hour_"+hour+"_"+minute+"/"+listOfFiles[i].getName();
            
	            inputStream = new FileInputStream(listOfFiles[i]);

	            System.out.println("\nUploading file " + listOfFiles[i].getName());
	            
	            boolean done = ftpClient.storeFile(remoteFile, inputStream);
	            
	            if (done) {
	                System.out.println("=== The file has been uploaded successfully. ===");
	            } else {
	           	 System.out.println("*** There has been a problem with the folder upload. Probably the destination folder does not exist. ***");
	            }
	            
	            
            
            } else { // se não encontrar ao menos 1 algarismo em nome de arquivo
            	
            	// não importe o arquivo caso ele não contenha "html" no nome
            	if (!listOfFiles[i].getName().contains("html")) { // se não tiver algarismo e não conter "html"
            		System.out.println("\n"+ listOfFiles[i].getName() + " is not being imported!");
            		continue;
            	}
            	
            	// captura o arquivo html ready
            	if (listOfFiles[i].getName().contains("html") && listOfFiles[i].getName().contains("ready")) { // se não conter algarismo, e se for o html ready
            		
            		//String remoteFile = "/"+listOfFiles[i].getName();
            		String remoteFile = "accorhotels/"+year+"/"+"day_"+day+"_"+month+"_hour_"+hour+"_"+minute+"/"+listOfFiles[i].getName();	
                    
    	            inputStream = new FileInputStream(listOfFiles[i]);

    	            System.out.println("\nUploading file " + listOfFiles[i].getName());
    	            
    	            boolean done = ftpClient.storeFile(remoteFile, inputStream);
    	            
    	            if (done) {
    	                System.out.println("=== The file has been uploaded successfully. ===");
    	            } else {
    	           	 System.out.println("*** There has been a problem with the upload of the final HTML file. Probably the destination folder does not exist. ***");
    	            }

            	}
		
            }
		}
		
		boolean final_file_exists = new File(CURRENT_FOLDER + "\\" + "creative.html").exists();
		if (final_file_exists) {
			String remoteFile = "accorhotels/"+year+"/"+"day_"+day+"_"+month+"_hour_"+hour+"_"+minute+"/"+"creative.html";	
            
            inputStream = new FileInputStream(new File(CURRENT_FOLDER + "\\" + "creative.html"));

            System.out.println("\nUploading file " + "creative.html");
            
            boolean done = ftpClient.storeFile(remoteFile, inputStream);
            
            if (done) {
                System.out.println("=== The file has been uploaded successfully. ===");
            } else {
           	 System.out.println("*** There has been a problem with the upload of the final HTML file. Probably the destination folder does not exist. ***");
            }
		} else {
			System.out.println("*** File (creative.html) NOT FOUND for upload!!! ***");
		}
		
		} catch (FileNotFoundException fnfe) {
			System.out.println("\n***************************************");
			System.out.println("File not found on hard disk for upload!");
			System.out.println("***************************************\n");
		} catch (IOException ioe) {
			System.out.println("\n***************************************************************");
			System.out.println("A problem occurred while trying to connect to the remote server");
			System.out.println("***************************************************************\n");
		} finally {
	        try {
				ftpClient.disconnect();
			} catch (IOException e) {
				System.out.println("\n**********************************************************");
				System.out.println("Impossibility to close FTP connection to the remote server");
				System.out.println("**********************************************************\n");
				e.printStackTrace();
			}
	        try {
				inputStream.close();
			} catch (IOException e) {
				System.out.println("\n********************************");
				System.out.println("Problem to close the inputStream");
				System.out.println("********************************\n");
				e.printStackTrace();
			}
		}
		
	}
	
	public void upload_accor_mail(String html) {
		
		FTPClient ftpClient = connect_accor();
		InputStream inputStream = null;
		
		try {
			
			ftpClient.makeDirectory("accormail/"+year+"/");
			//ftpClient.makeDirectory("accormail/"+year+"/"+day+"_"+month+"/");
			ftpClient.makeDirectory("accormail/"+year+"/"+"day_"+day+"_"+month+"_hour_"+hour+"_"+minute+"/");
		
		for (int i = 0; i < listOfFiles.length; i++) {
 			
			Pattern p = Pattern.compile("\\d+");
 		    Matcher m = p.matcher(listOfFiles[i].getName());
        	
 			if (m.find()) { // se encontrar ao menos 1 algarismo em nome de arquivo, proceda com o upload
 			
     			// String remoteFile = "/"+listOfFiles[i].getName();
 				String remoteFile = "accormail/"+year+"/"+"day_"+day+"_"+month+"_hour_"+hour+"_"+minute+"/"+listOfFiles[i].getName();
            
	            inputStream = new FileInputStream(listOfFiles[i]);

	            System.out.println("\nUploading file " + listOfFiles[i].getName());
	            
	            boolean done = ftpClient.storeFile(remoteFile, inputStream);
	            
	            if (done) {
	                System.out.println("=== The file has been uploaded successfully. ===");
	            } else {
	           	 System.out.println("*** There has been a problem with the folder upload. Probably the destination folder does not exist. ***");
	            }
            
            } else { // se não encontrar ao menos 1 algarismo em nome de arquivo
            	
            	// não importe o arquivo caso ele não contenha "html" no nome
            	if (!listOfFiles[i].getName().contains("html")) { // se não tiver algarismo e não conter "html"
            		System.out.println("\n"+ listOfFiles[i].getName() + " is not being imported!");
            		continue;
            	}
            	
            	// captura o arquivo html ready
            	if (listOfFiles[i].getName().contains("html") && listOfFiles[i].getName().contains("ready")) { // se não conter algarismo, e se for o html ready
            		
            		//String remoteFile = "/"+listOfFiles[i].getName();
            		String remoteFile = "accormail/"+year+"/"+"day_"+day+"_"+month+"_hour_"+hour+"_"+minute+"/"+listOfFiles[i].getName();	
                    
    	            inputStream = new FileInputStream(listOfFiles[i]);

    	            System.out.println("\nUploading file " + listOfFiles[i].getName());
    	            
    	            boolean done = ftpClient.storeFile(remoteFile, inputStream);
    	            
    	            if (done) {
    	                System.out.println("=== The file has been uploaded successfully. ===");
    	            } else {
    	           	 System.out.println("*** There has been a problem with the upload of the final HTML file. Probably the destination folder does not exist. ***");
    	            }
    	            
            	}
		
            }
		}
		
		boolean final_file_exists = new File(CURRENT_FOLDER + "\\" + "creative.html").exists();
		if (final_file_exists) {
			String remoteFile = "accormail/"+year+"/"+"day_"+day+"_"+month+"_hour_"+hour+"_"+minute+"/"+"creative.html";	
            
            inputStream = new FileInputStream(new File(CURRENT_FOLDER + "\\" + "creative.html"));

            System.out.println("\nUploading file " + "creative.html");
            
            boolean done = ftpClient.storeFile(remoteFile, inputStream);
            
            if (done) {
                System.out.println("=== The file has been uploaded successfully. ===");
            } else {
           	 System.out.println("*** There has been a problem with the upload of the final HTML file. Probably the destination folder does not exist. ***");
            }
		} else {
			System.out.println("*** File (creative.html) NOT FOUND for upload!!! ***");
		}
		
		} catch (FileNotFoundException fnfe) {
			System.out.println("\n***************************************");
			System.out.println("File not found on hard disk for upload.");
			System.out.println("***************************************\n");
		} catch (IOException ioe) {
			System.out.println("\n***************************************************************");
			System.out.println("A problem occurred while trying to connect to the remote server");
			System.out.println("***************************************************************\n");
		} finally {
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				System.out.println("\n**********************************************************");
				System.out.println("Impossibility to close FTP connection to the remote server");
				System.out.println("**********************************************************\n");
				e.printStackTrace();
			}
	        try {
				inputStream.close();
			} catch (IOException e) {
				System.out.println("\n*********************************");
				System.out.println("Problem to close the inputStream.");
				System.out.println("*********************************\n");
				e.printStackTrace();
			}
		}
		
	}
	
	public void upload_accor_pullmanspibirapuera(String html) {
		
		FTPClient ftpClient = connect_accor();
		InputStream inputStream = null;
		
		try {
			
			ftpClient.makeDirectory("pullmanspibirapuera/"+year+"/");
			//ftpClient.makeDirectory("pullmanspibirapuera/"+year+"/"+day+"_"+month+"/");
			ftpClient.makeDirectory("pullmanspibirapuera/"+year+"/"+"day_"+day+"_"+month+"_hour_"+hour+"_"+minute+"/");
		
			for (int i = 0; i < listOfFiles.length; i++) {
	 			
				Pattern p = Pattern.compile("\\d+");
	 		    Matcher m = p.matcher(listOfFiles[i].getName());
	        	
	 			if (m.find()) { // se encontrar ao menos 1 algarismo em nome de arquivo, proceda com o upload
	 			
	     			// String remoteFile = "/"+listOfFiles[i].getName();
	 				String remoteFile = "pullmanspibirapuera/"+year+"/"+"day_"+day+"_"+month+"_hour_"+hour+"_"+minute+"/"+listOfFiles[i].getName();
	            
		            inputStream = new FileInputStream(listOfFiles[i]);

		            System.out.println("\nUploading file " + listOfFiles[i].getName());
		            
		            boolean done = ftpClient.storeFile(remoteFile, inputStream);
		            
		            if (done) {
		                System.out.println("=== The file has been uploaded successfully. ===");
		            } else {
		           	 System.out.println("*** There has been a problem with the folder upload. Probably the destination folder does not exist. ***");
		            }
	            
	            } else { // se não encontrar ao menos 1 algarismo em nome de arquivo
	            	
	            	// não importe o arquivo caso ele não contenha "html" no nome
	            	if (!listOfFiles[i].getName().contains("html")) { // se não tiver algarismo e não conter "html"
	            		System.out.println("\n"+ listOfFiles[i].getName() + " is not being imported!");
	            		continue;
	            	}
	            	
	            	// captura o arquivo html ready
	            	if (listOfFiles[i].getName().contains("html") && listOfFiles[i].getName().contains("ready")) { // se não conter algarismo, e se for o html ready
	            		
	            		//String remoteFile = "/"+listOfFiles[i].getName();
	            		String remoteFile = "pullmanspibirapuera/"+year+"/"+"day_"+day+"_"+month+"_hour_"+hour+"_"+minute+"/"+listOfFiles[i].getName();	
	                    
	    	            inputStream = new FileInputStream(listOfFiles[i]);

	    	            System.out.println("\nUploading file " + listOfFiles[i].getName());
	    	            
	    	            boolean done = ftpClient.storeFile(remoteFile, inputStream);
	    	            
	    	            if (done) {
	    	                System.out.println("=== The file has been uploaded successfully. ===");
	    	            } else {
	    	           	 System.out.println("*** There has been a problem with the upload of the final HTML file. Probably the destination folder does not exist. ***");
	    	            }
	    	            
	            	}
			
	            }
			}
			
			boolean final_file_exists = new File(CURRENT_FOLDER + "\\" + "creative.html").exists();
			if (final_file_exists) {
				String remoteFile = "pullmanspibirapuera/"+year+"/"+"day_"+day+"_"+month+"_hour_"+hour+"_"+minute+"/"+"creative.html";	
	            
	            inputStream = new FileInputStream(new File(CURRENT_FOLDER + "\\" + "creative.html"));

	            System.out.println("\nUploading file " + "creative.html");
	            
	            boolean done = ftpClient.storeFile(remoteFile, inputStream);
	            
	            if (done) {
	                System.out.println("=== The file has been uploaded successfully. ===");
	            } else {
	           	 System.out.println("*** There has been a problem with the upload of the final HTML file. Probably the destination folder does not exist. ***");
	            }
			} else {
				System.out.println("*** File (creative.html) NOT FOUND for upload!!! ***");
			}
			
			} catch (FileNotFoundException fnfe) {
				System.out.println("\n***************************************");
				System.out.println("File not found on hard disk for upload.");
				System.out.println("***************************************\n");
			} catch (IOException ioe) {
				System.out.println("\n****************************************************************");
				System.out.println("A problem occurred while trying to connect to the remote server.");
				System.out.println("****************************************************************\n");
			} finally {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					System.out.println("\n***********************************************************");
					System.out.println("Impossibility to close FTP connection to the remote server.");
					System.out.println("***********************************************************\n");
					e.printStackTrace();
				}
		        try {
					inputStream.close();
				} catch (IOException e) {
					System.out.println("\n*********************************");
					System.out.println("Problem to close the inputStream.");
					System.out.println("*********************************\n");
					e.printStackTrace();
				}
			}
		
	}
	
	// método que conecta e faz upload (só tem uma opção de conexão)
	public void connect_upload_adcos(String html) {
		
		final String SERVER = "expertsenderbrasil.com.br";
		final int PORT = 21;
		final String USER = "adcos@expertsenderbrasil.com.br";
		final String PASS = "Adcos@2021";
		
		
		FTPClient ftpClient = new FTPClient();
		InputStream inputStream = null;
		
		try {
			
			ftpClient.connect(SERVER, PORT);
	        ftpClient.login(USER, PASS);
	        ftpClient.enterLocalPassiveMode();
	        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			
				
			ftpClient.makeDirectory("/"+project+"/");
			ftpClient.makeDirectory("/"+project+"/"+year+"/");
			//ftpClient.makeDirectory("pullmanspibirapuera/"+year+"/"+"day_"+day+"_"+month+"_hour_"+hour+"_"+minute+"/");
			ftpClient.makeDirectory("/"+project+"/"+year+"/"+"day_"+day+"_"+month+"_hour_"+hour+"_"+minute+"/");
		
			for (int i = 0; i < listOfFiles.length; i++) {
	 			
				Pattern p = Pattern.compile("\\d+");
	 		    Matcher m = p.matcher(listOfFiles[i].getName());
	        	
	 			if (m.find()) { // se encontrar ao menos 1 algarismo em nome de arquivo, proceda com o upload
	 			
	     			// String remoteFile = "/"+listOfFiles[i].getName();
	 				String remoteFile = "/"+project+"/"+year+"/"+"day_"+day+"_"+month+"_hour_"+hour+"_"+minute+"/"+listOfFiles[i].getName();
	            
		            inputStream = new FileInputStream(listOfFiles[i]);

		            System.out.println("\nUploading file " + listOfFiles[i].getName());
		            
		            boolean done = ftpClient.storeFile(remoteFile, inputStream);
		            
		            if (done) {
		                System.out.println("=== The file has been uploaded successfully. ===");
		            } else {
		           	 System.out.println("*** There has been a problem with the folder upload. Probably the destination folder does not exist. ***");
		            }
	            
	            } else { // se não encontrar ao menos 1 algarismo em nome de arquivo
	            	
	            	// não importe o arquivo caso ele não contenha "html" no nome
	            	if (!listOfFiles[i].getName().contains("html")) { // se não tiver algarismo e não conter "html"
	            		System.out.println("\n"+ listOfFiles[i].getName() + " is not being imported!");
	            		continue;
	            	}
	            	
	            	// captura o arquivo html ready
	            	if (listOfFiles[i].getName().contains("html") && listOfFiles[i].getName().contains("ready")) { // se não conter algarismo, e se for o html ready
	            		
	            		//String remoteFile = "/"+listOfFiles[i].getName();
	            		String remoteFile = "/"+project+"/"+year+"/"+"day_"+day+"_"+month+"_hour_"+hour+"_"+minute+"/"+listOfFiles[i].getName();	
	                    
	    	            inputStream = new FileInputStream(listOfFiles[i]);

	    	            System.out.println("\nUploading file " + listOfFiles[i].getName());
	    	            
	    	            boolean done = ftpClient.storeFile(remoteFile, inputStream);
	    	            
	    	            if (done) {
	    	                System.out.println("=== The file has been uploaded successfully. ===");
	    	            } else {
	    	           	 System.out.println("*** There has been a problem with the upload of the final HTML file. Probably the destination folder does not exist. ***");
	    	            }
	    	            
	            	}
			
	            }
			}
			
			boolean final_file_exists = new File(CURRENT_FOLDER + "\\" + "creative.html").exists();
			if (final_file_exists) {
				String remoteFile = "/"+project+"/"+year+"/"+"day_"+day+"_"+month+"_hour_"+hour+"_"+minute+"/"+"creative.html";	
	            
	            inputStream = new FileInputStream(new File(CURRENT_FOLDER + "\\" + "creative.html"));

	            System.out.println("\nUploading file " + "creative.html");
	            
	            boolean done = ftpClient.storeFile(remoteFile, inputStream);
	            
	            if (done) {
	                System.out.println("=== The file has been uploaded successfully. ===");
	            } else {
	           	 System.out.println("*** There has been a problem with the upload of the final HTML file. Probably the destination folder does not exist. ***");
	            }
			} else {
				System.out.println("*** File (creative.html) NOT FOUND for upload!!! ***");
			}
			
			} catch (FileNotFoundException fnfe) {
				System.out.println("\n***************************************");
				System.out.println("File not found on hard disk for upload.");
				System.out.println("***************************************\n");
			} catch (IOException ioe) {
				System.out.println("\n****************************************************************");
				System.out.println("A problem occurred while trying to connect to the remote server.");
				System.out.println("****************************************************************\n");
			} finally {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					System.out.println("\n***********************************************************");
					System.out.println("Impossibility to close FTP connection to the remote server.");
					System.out.println("***********************************************************\n");
					e.printStackTrace();
				}
		        try {
					inputStream.close();
				} catch (IOException e) {
					System.out.println("\n*********************************");
					System.out.println("Problem to close the inputStream.");
					System.out.println("*********************************\n");
					e.printStackTrace();
				}
			}
		
	}
}
