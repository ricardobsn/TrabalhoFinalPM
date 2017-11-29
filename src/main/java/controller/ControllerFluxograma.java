package controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ControllerFluxograma {

    private File arquivo;
    private DocumentBuilderFactory  docFactory;
    private Document doc;
    private int contOpt;
    private int contEletiva;
    private static HashMap<String, String> mapMaterias = new HashMap<>();
    private final String VERMELHO = "ff0000";
    private final String VERDE = "01f900";

    public enum Status{
        APROVADO, REPROVADO, PENDENTE
    }

    public ControllerFluxograma(File arquivo, HashMap<String, String> mapMaterias, Integer contOpt, Integer contEletiva ){
        this.arquivo = arquivo;
        this.mapMaterias = mapMaterias;
        this.contOpt = contOpt;
        this.contEletiva = contEletiva;
        
    }

    public ControllerFluxograma(){

    }

    public void editFile() throws IOException, ParserConfigurationException, SAXException {

        docFactory = DocumentBuilderFactory.newInstance();
        doc = docFactory.newDocumentBuilder().parse(arquivo.toString());
    }

    /*Faz o Parser do documento XML e separa em nós*/
    public void pathParser() throws Exception {

        doc.getDocumentElement().normalize();
        NodeList listOfPathNodes = doc.getElementsByTagName("path");
        int totalPaths = listOfPathNodes.getLength();

        for(int i = 0; i < totalPaths; i++){
            Element el = (Element) listOfPathNodes.item(i);
            String id = el.getAttribute("id");   // Get id data
            Status status = verificaSeAMateriaFoiCursada(id, mapMaterias);
            atualizaACor(status, el);
        }
        pintaMateriasOptativasEEletivas();
        salvarAlteracoes(doc);
    }

   /*Verifica dentro do Map de matérias cursadas se o aluno passou ou não*/
    public Status verificaSeAMateriaFoiCursada(String idMateria, HashMap<String, String> materias){

        String situacao = materias.get(idMateria);
        if (("Aprovado".equals(situacao)) || ("nota".equals(situacao))) // Aprovado ou dispensado
            return Status.APROVADO;
        else if ("por".equals(situacao)) // Reprovado
            return Status.REPROVADO;
        else // Pendente
            return Status.PENDENTE;
    }

    /*Percorre dentro dos nós os ids das matérias e pinta com a cor correspondente*/
    public void atualizaACor(Status status, Element el){

        String estilo = el.getAttribute("style");
        String estiloContinuacao = estilo.substring(11, estilo.length());
        String cor = null;

        if (status.equals(Status.PENDENTE)) //Como a materia não foi cursada não preicsa ser atualizada
            return;

        if (status.equals(Status.APROVADO))
            cor = VERDE;
        else // Se reprovado
            cor = VERMELHO;

        estilo = "fill:#"+ cor + estiloContinuacao; // concatena a string com o valor da cor nova
        el.setAttribute("style",estilo);

    }

    /*Verifica a partir da qtde de materias opt e eletivas q foram cursadas, qtde q tem q pintar*/
    public void pintaMateriasOptativasEEletivas(){

        doc.getDocumentElement().normalize();
        NodeList listaDeTodosNos = doc.getElementsByTagName("path");
        int caminhosTotais = listaDeTodosNos.getLength();
        int qtdOpt = 0;
        int qtdEletiva = 0;

        for(int i = 0; i < caminhosTotais; i++){
            Element  el = (Element) listaDeTodosNos.item(i);
            String id = el.getAttribute("id");   // Pega o id da Materia dentro do SVG
            String estilo = el.getAttribute("style"); // Pega o estilo correspondente
            String estiloContinuacao = estilo.substring(11, estilo.length());

            if(id.startsWith("OPTATIVA") && qtdOpt < contOpt){  //Pinta a qtde correspondente de opt cursadas e contOpt serve como condição de parada
                estilo = "fill:#"+ VERDE + estiloContinuacao;
                qtdOpt = qtdOpt+1;
            }
            
             if(id.startsWith("ELETIVA") && qtdEletiva < contEletiva){  //Pinta a qtde correspondente de eletivas cursadas e contEletiva serve como condição de parada
                estilo = "fill:#"+ VERDE + estiloContinuacao;
                qtdEletiva = qtdEletiva+1;
            }

        el.setAttribute("style",estilo);

        }
    }
    
    /*Gera um fluxograma novo com as alterações*/
    public void salvarAlteracoes(Document d) throws IOException, TransformerException{

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(d);
        StreamResult result = new StreamResult(new File("output.svg"));
        transformer.transform(source, result);
    }



}

