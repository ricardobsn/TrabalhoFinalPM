/**
 *
 * @author ricardo
 */

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import java.io.IOException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
 

 
   

public class PDFRead {
    
  public static void isLinhaDeNota(String linha){
       
     if ( linha.contains("Aprovado") || linha.contains("Dispensa") || linha.contains("Reprovado")){ 
         String [] frase = linha.split("\\s+");
         for (int j = 0; j < frase.length; j++) {
         String codigo = frase[0];
         String situacao = frase[frase.length-1];
          System.out.println(codigo + situacao );
        }
     }
        else{
         // nao contem;
                }
  }
    
  public static void main(String args[]){
    try {
	//Create PdfReader instance.
	PdfReader pdfReader = new PdfReader("file:///home/ricardo/Documentos/historicoEscolarCRAprovados.pdf");	
 
	//Get the number of pages in pdf.
	int pages = pdfReader.getNumberOfPages(); 
 
	//Iterate the pdf through pages.
	for(int i=1; i<=pages; i++) { 
	  //Extract the page content using PdfTextExtractor.
	  String pageContent = PdfTextExtractor.getTextFromPage(pdfReader, i);
          String [] tokens = pageContent.split("\\n");
            for (int j = 0; j < tokens.length; j++) {
                String token = tokens[j];
                    isLinhaDeNota(token); 
                   
//                System.out.println(token);
            }
	  //Print the page content on console.
//	  System.out.println(var_1);
      }
 
      //Close the PdfReader.
      pdfReader.close();
    } catch (Exception e) {
	e.printStackTrace();
    }
  }
}

 