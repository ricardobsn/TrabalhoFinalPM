package controller;

/**
 *
 * @author ricardo
 */
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import java.util.HashMap;
import view.TelaVisualizacao;

public class ControllerHistorico {

    private HashMap<String, String> mapMaterias = new HashMap<>();
    private HashMap<String, Integer> mapMateriasAUX = new HashMap<>();
    private String[] materiasOptativas = {"TIN0141", "TIN0165", "TIN0128", "TIN0150", "TIN0159", "TIN0144"};
    private String[] materiasEletivas = {"TIN0151", "TIN0152", "TIN0153", "TIN0154"};
    private int contOptativa = 0;
    private int contEletiva = 0;
    private double cr= 0.0;    
    private String [] palavras;
    private String nomeAluno;
    private int anoMatricula;
    private int anoCorrente;
    private TelaVisualizacao telaVisualizacao = new TelaVisualizacao(this);

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
                    if(isLinhaDeNota(token)) 
                        criaListaMaterias(token);
                    
                    else if(isLinhaDeCoeficienteGeral(token)){
                       cr = pegaCoeficienteGeral(token);
                    }
                    else if(isLinhaDeMatricula(token)){
                       anoMatricula = pegaMatricula(token);                        
                    }
                    else if(isLinhaDeNomeAluno(token)){
                       nomeAluno = pegaNome(token);                        
                    }
                    else if(isLinhaDeAnoCorrente(token)){
                       anoCorrente = pegaAnoCorrente(token);                        
                    }
                }
            }
            verificaJubilamento(cr);
            quantidadeDeAnosNaFaculdade(anoMatricula, anoCorrente);
            verificaQtdeDeOptCursadas(materiasOptativas);
            verificaQtdeDeEletivasCursadas(materiasEletivas);  
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

    /*Verifica se é uma linha que possui o nome do aluno*/
    public  boolean isLinhaDeNomeAluno(String linha){
     
        return ( linha.contains("Nome"));
    }

    /*Verifica se é a linha de coeficiente geral*/
    public boolean isLinhaDeCoeficienteGeral(String linha){
     
        return ( linha.contains("Geral"));
    }

    /*Verifica se é a linha de matricula*/
    public boolean isLinhaDeMatricula(String linha){
     
        return ( linha.contains("Matrícula"));
    }
    
    public boolean isLinhaDeAnoCorrente(String linha){
     
        return ( linha.contains("semestre de"));
    }

    /*Função para pegar ano atual*/
    public int pegaAnoCorrente(String linha){
    
        String anoCorrente = "";
        String ano[] = linha.split("\\s+");
        anoCorrente = ano[4];
        int anoCorrenteAtual = Integer.parseInt(anoCorrente);
        return anoCorrenteAtual;
    }
    
    /*Pega o nome do Aluno*/
    public String pegaNome(String linha){
    
        String nomeAluno = "";
        if(linha.startsWith("Nome")){
            String nome[] = linha.split("\\s+"); //Quebra a linha em um array e separa os elementos pelo espaço
            nomeAluno = nome[2];
             
        }  
        return nomeAluno;
    }
    
    /*Pega a matricula do aluno*/
    public int pegaMatricula(String linha){
     
        int anoMatricula = 0;
        if(linha.startsWith("Matrícula")){
            palavras = linha.split("\\s+");
            String matriculaAux = palavras[1];
            String matricula = matriculaAux.substring(0, 4);
            anoMatricula = Integer.parseInt(matricula);             
        }  
        return anoMatricula;
    }

    /*Pega o CR geral do aluno*/
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
        alteraCodigoIHC(situacao);
        contadorDeMateriasCursadas(codigo+situacao);

        return mapMaterias;
    }

    /*Inclui o código de IHC caso o aluno tenha cursdo e passado,
    pois os códigos da matéria no fluxograma e no histórico estão diferentes*/
    public void alteraCodigoIHC(String situacao){
   
        if (mapMaterias.containsKey("TIN0110")){
            mapMaterias.put("TIN0010", situacao);
        }
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
    
    /*Contador de materias opt cursadas e passadas, visto q caso aluno tenha repetido, o mesmo não precisa cursá- la novamente*/
    public int verificaQtdeDeOptCursadas(String[] materiasOptativas){
    
        for (int j = 0; j < materiasOptativas.length; j++) {
            String materiaAux = materiasOptativas[j];
            if (mapMaterias.containsKey(materiaAux) && mapMaterias.containsValue("Aprovado")) {
                contOptativa++;
            }
        }
        return contOptativa;
    }
    
    /*Contador de materias eletivas cursadas e passadas, visto q caso aluno tenha repetido, o mesmo não precisa cursá- la novamente*/
    public int verificaQtdeDeEletivasCursadas(String[] materiasEletivas){
       
        for (int j = 0; j < materiasEletivas.length; j++) {
            String materiaAux = materiasEletivas[j];
            if (mapMaterias.containsKey(materiaAux) && mapMaterias.containsValue("Aprovado")) {
                contEletiva++;
            }
        }
        return contEletiva;
    }

    /*Verifica se tem CRA menor que 4,0 e quatro ou mais reprovações em uma mesma disciplina e mostra na tela*/
    public void verificaJubilamento(Double cr){

        if ((cr < 4) && (mapMateriasAUX.containsValue(4))){
            telaVisualizacao.getLabelJubilamento().setText("Você será Jubilado !");
        }
    }
    
    /*Verifica as condições de integralização, prorrogação e jubilamento por tempo de entrada e permanência no curso*/
    public void quantidadeDeAnosNaFaculdade(int entrada, int atual){
        
        int anosNaFaculdade = 0;
        if (entrada < 2013){
            anosNaFaculdade = atual - entrada;
            if (anosNaFaculdade == 6){
                telaVisualizacao.getLabelIntegracao().setText("Antes 2014 no 6° ano deve pedir prorrogação !"); 
            }
            else if (anosNaFaculdade >= 7){
                telaVisualizacao.getLabelIntegracao().setText("Antes 2014 no 7° ano será Jubilado !");
            }
        }
        else if (entrada > 2013){
            anosNaFaculdade = atual - entrada;
            if (anosNaFaculdade >= 3){
                telaVisualizacao.getLabelIntegracao().setText("Depois 2014 no 7° período deve pedir prorrogação !");
            }
            else if (anosNaFaculdade >= 6){
                telaVisualizacao.getLabelIntegracao().setText("Depois 2014 6° será Jubilado !");
            }
        }
    }

    public HashMap<String, String> getMapMaterias() {

        return mapMaterias;
    }

    public int getContOpt() {

        return contOptativa;
    }
    
     public int getContEletiva() {

        return contEletiva;
    }

    public double getCr() {
        return cr;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public int getAnoMatricula() {
        return anoMatricula;
    }

    public int getAnoCorrente() {
        return anoCorrente;
    }
    
    public TelaVisualizacao getTelaVisualizacao() {
        return telaVisualizacao;
    }
}

 