package controller;

/**
 *
 * @author ricardo
 */
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ControllerHistorico {

    private HashMap<String, String> mapMaterias = new HashMap<>();
    private HashMap<String, Integer> mapMateriasAUX = new HashMap<>();
    private String[] materiasOptativas = {"TIN0141", "TIN0165", "TIN0128", "TIN0150", "TIN0159", "TIN0144"};
    private int cont = 0;
    private double cr= 0.0;

    public ControllerHistorico(){
    }

    public void leituraPDF(String pdf){

        try {
            //Cria uma instancia do PDF
            PdfReader leitorPDF = new PdfReader(pdf);

            //Pega o numero de paginas do PDF.
            int paginas = leitorPDF.getNumberOfPages();
            //Itera sobre as pags do PDF.
            for(int i=1; i<=paginas; i++) {
                //Extrai o conteudo do PDF usando PdfTextExtractor.
                String conteudoPagina = PdfTextExtractor.getTextFromPage(leitorPDF, i);
                String [] tokens = conteudoPagina.split("\\n");
                for (int j = 0; j < tokens.length; j++) {
                    String token = tokens[j];

                    if(isLinhaDeNota(token)) {
                        criaListaMaterias(token);
                    }
                    else if(isLinhaDeCoeficienteGeral(token)){
                        cr = pegaCoeficienteGeral(token);
                    }
                }
            }
            verificaJubilamento(cr);
            verificaQtdeDeOptCursadas(materiasOptativas);
            //Fecha o PdfReader.
            leitorPDF.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*Verifica se é uma linha de nota*/
    public  boolean isLinhaDeNota(String linha){
        return ( linha.contains("APV") || linha.contains("DIS") || linha.contains("REP"));
    }

    /*Verifica se é uma linha de coeficiente geral*/
    public boolean isLinhaDeCoeficienteGeral(String linha){

        return ( linha.contains("Geral"));
    }

    public double pegaCoeficienteGeral(String linha){
        String [] palavras = linha.split("\\s+");
        String coeficiente = palavras[palavras.length-1];
        String replaceString=coeficiente.replace(',','.');//troca a linha pelo ponto para converter para double
        double coeficienteRendimento = Double.parseDouble(replaceString.trim());
        return coeficienteRendimento;
    }

    /*Cria o Map de materias cursadas desconsiderando as repetições*/
    public HashMap criaListaMaterias(String linha) {
        String [] palavras = linha.split("\\s+");
        String codigo = palavras[0];
        String situacao = palavras[palavras.length-1];
        mapMaterias.put(codigo, situacao);
        contadorDeMateriasCursadas(codigo+situacao);

        return mapMaterias;
    }

    /*Pega a qtde de vezes que as materias foram cursadas incluindo as repetições*/
    public void contadorDeMateriasCursadas(String codigo){
        if(mapMateriasAUX.containsKey(codigo)){
            mapMateriasAUX.put(codigo, mapMateriasAUX.get(codigo) + 1);
        }
        else
        {
            mapMateriasAUX.put(codigo,1);
        }
    }

    /*Contador de materias opt cursadas e passadas, visto q caso aluno tenha repetido, o mesmo não precisa cursá- la necessariamente*/
    public int verificaQtdeDeOptCursadas(String[] materiasOptativas){
        for (int j = 0; j < materiasOptativas.length; j++) {
            String materiaAux = materiasOptativas[j];
            if (mapMaterias.containsKey(materiaAux) && mapMaterias.containsValue("Aprovado")) {
                cont++;
            }
        }
        return cont;
    }

    public void verificaJubilamento(Double cr){
//        System.out.println(mapMateriasAUX);
        if ((cr < 4) && (mapMateriasAUX.containsValue(4))){
            System.out.println("Aluno precisa ser jubilado...");
        }

    }


    public HashMap<String, String> getMapMaterias() {

        return mapMaterias;
    }

    public int getContOpt() {

        return cont;
    }

}

 