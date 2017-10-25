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
import java.util.ArrayList;
import model.Materia;
 

public class ProcessadorPDF {
    
    private static ArrayList<Materia> materias_cursadas = new ArrayList<Materia>();

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
                isLinhaDeNota(token);       
//                System.out.println(token);
            }
        }
        //Close the PdfReader.
        pdfReader.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
    }
    
    public static ArrayList isLinhaDeNota(String linha){

       if ( linha.contains("Aprovado") || linha.contains("Dispensa") || linha.contains("Reprovado")){ 
           String [] frase = linha.split("\\s+");
           for (int j = 0; j < frase.length; j++) {
           String codigo = frase[0];
           String situacao = frase[frase.length-1];
           Materia materia = new Materia(codigo, situacao);
           materias_cursadas.add(materia);
//           System.out.println(codigo + situacao );
           }
       }
          return materias_cursadas;
    }
    
   
    // Faz um for percorrendo os itens da lista. Para cada item da lista percorrido, faz um outro for para percorrer todos os itens da lista novamente (Pulando o primeiro item do for externo)
    // para verificar qual objeto tem id igual ao objeto do for mais externo e se são objetos diferentes.
   
    public static ArrayList remocaoMateriasRepetidas(ArrayList<Materia> materias){
        
        ArrayList<Materia> materias_cursadas_sem_repeticao = new ArrayList<Materia>();
        for ( int i = 0; i < materias.size(); i++ ) {
            for ( int j = i + 1; j < materias.size(); j++ ) {
                // Se os objetos tiverem o mesmo id mas forem objetos diferentes, entao pegamos o campo primario do segundo objeto e o colocamos no campo secundario da primeiro objeto.
                // Adicionamos o segundo objeto na lista para eliminação de objetos repetidos.
                if ( ( materias.get(i).getCodigo()+materias.get(i).getSituacao()) == (materias.get(j).getCodigo()+materias.get(j).getSituacao()) &&
                    !( ( (Object) materias.get(i) ).equals( materias.get(j) ) )  ) {

                    i++; // Incrementa i em 1 para evitar verificações desnecessárias.
                    break;
                }
            }
        }
        //elimina todos os elementos que foram marcados para remoção
        materias.removeAll(materias_cursadas_sem_repeticao);
        System.out.println(materias_cursadas_sem_repeticao);
        
       return materias_cursadas_sem_repeticao;
    }
    
    
    public static void main(String args[]){
      leitura_PDF();
//      remocaoMateriasRepetidas(materias_cursadas);

    }
}

 