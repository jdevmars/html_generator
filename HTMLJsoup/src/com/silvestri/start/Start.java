package com.silvestri.start;

import java.io.IOException;

import com.silvestri.ftp.Ftp_2;
import com.silvestri.html.Message;

public class Start {

	public static void main(String[] args) throws NumberFormatException, IOException {

		Message message = new Message();
		String html = message.create_template();
		html = message.create_content(html);
		
		Ftp_2 ftp = new Ftp_2();
		ftp.list_files();
		ftp.generate_copy();
		
		ftp.which_ftp(html);
		
		String final_path = ftp.getFinal_path();
		
		boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
		
		if (isWindows) 
			//Runtime.getRuntime().exec(String.format("cmd.exe /c start %s/message_ftp_ready.html", System.getProperty("user.dir")));
			Runtime.getRuntime().exec(String.format("cmd.exe /c start %s", final_path));
		else
			//Runtime.getRuntime().exec(String.format("/bin/sh -c open -a %s/message_ftp_ready.html", System.getProperty("user.dir")));
			// -a for opening a URL allows to specify the browser to be used (ex: open -a "Google Chrome" http://www.google.com)
			Runtime.getRuntime().exec(String.format("/bin/sh -c open %s", final_path));
		
		
		/* PENDÊNCIAS <<<<====================
		System.out.println("=============================");
		System.out.println("Pendências 00:56 26/02/2023: ");
		System.out.println("=============================");
		System.out.println("- Decidir se mantém o atributo title ou não das imagens");
		System.out.println("- Pedir pro Júlio testar no Mac");
		System.out.println("- Teste de erros intencionais");
		*/
		
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			System.out.println("\n*********************************************************");
			System.out.println("It was not possible to pause the program at the very end.");
			System.out.println("*********************************************************\n");
			e.printStackTrace();
		}
		
	}

}
