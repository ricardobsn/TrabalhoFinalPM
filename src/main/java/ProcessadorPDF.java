/**
 *
 * @author ricardo
 */

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import java.util.ArrayList;
import java.util.HashMap;
import model.Materia;
 

public class ProcessadorPDF {
    
    private static ArrayList<Materia> materiasCursadas = new ArrayList<Materia>();
//    private static ArrayList<Materia> materiasDuplicadas = new ArrayList<Materia>();
    
    private static HashMap<String, String> mapMaterias = new HashMap<>();

    public static void leitura_PDF(){
    
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

                if(isLinhaDeNota(token)) {
                    criaListaMaterias(token);
                }
            }
        }
       
        System.out.println(mapMaterias);
        //Close the PdfReader.
        pdfReader.close();
        } catch (Exception e) {
          e.printStackTrace(); 
        }
    }
    
    public static boolean isLinhaDeNota(String linha){
       return ( linha.contains("APV") || linha.contains("DIS") || linha.contains("REP"));
    }

    private static HashMap criaListaMaterias(String linha) {
        String [] palavras = linha.split("\\s+");

        String codigo = palavras[0];
        String situacao = palavras[palavras.length-1];
//        Materia materia = new Materia(codigo, situacao);
//        
//        if(materiasCursadas.contains(materia)) {
//            materiasCursadas.remove(materia);
//        }
//        materiasCursadas.add(materia);
        
        //Usando Map
        mapMaterias.put(codigo, situacao);
        
        return mapMaterias;
    }
    
    
    public static void main(String args[]){
      leitura_PDF();

    }
}

 