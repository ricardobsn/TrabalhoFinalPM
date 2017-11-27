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
    private static HashMap<String, String> mapMaterias = new HashMap<>();
    private final String VERMELHO = "ff0000";
    private final String VERDE = "01f900";
    private final String BRANCO = "ffffff";

    public ControllerFluxograma(File arquivo, HashMap<String, String> mapMaterias, Integer contOpt){
        this.arquivo = arquivo;
        this.mapMaterias = mapMaterias;
        this.contOpt = contOpt;
    }

    public void editFile() throws IOException, ParserConfigurationException, SAXException {
        docFactory = DocumentBuilderFactory.newInstance();
        doc = docFactory.newDocumentBuilder().parse(arquivo.toString());
    }

    public void pathParser() throws IOException, TransformerException{
        doc.getDocumentElement().normalize();
        NodeList listOfPathNodes = doc.getElementsByTagName("path");
        int totalPaths = listOfPathNodes.getLength();

        for(int i = 0; i < totalPaths; i++){
            Element el = (Element) listOfPathNodes.item(i);
            String id = el.getAttribute("id");   // Get id data
            verificaSeAMateriaFoiCursada(id, mapMaterias, el);
        }
        pintaMateriasOptativas();
        salvarAlteracoes(doc);
    }

    /*Percorre dentro dos nós os ids das matérias e pinta com a cor correspondente*/
    public void verificaSeAMateriaFoiCursada(String idMateria, HashMap<String, String> mat, Element el){
        String situacao = mat.get(idMateria);
        String estilo = el.getAttribute("style");
        String estiloContinuacao = estilo.substring(11, estilo.length());

        if (!estilo.contains("none")){
            if (("Aprovado".equals(situacao)) || ("nota".equals(situacao))){
                estilo = "fill:#"+ VERDE + estiloContinuacao; // concatena a string com o valor da cor nova

            }
            else if ("por".equals(situacao)){
                estilo = "fill:#"+ VERMELHO + estiloContinuacao;
            }
            else{
                estilo = "fill:#"+ BRANCO + estiloContinuacao;
            }
            el.setAttribute("style",estilo);
        }
    }

    /*Verifica a partir da qtde de materias opt q foram cursadas, qtde q tem q pintar*/
    public void pintaMateriasOptativas(){

        doc.getDocumentElement().normalize();
        NodeList listaDeTodosNos = doc.getElementsByTagName("path");
        int caminhosTotais = listaDeTodosNos.getLength();
        int qtdOpt = 0;

        for(int i = 0; i < caminhosTotais; i++){
            Element  el = (Element) listaDeTodosNos.item(i);
            String id = el.getAttribute("id");   // Pega o id da Materia dentro do SVG
            String estilo = el.getAttribute("style"); // Pega o estilo correspondente
            String estiloContinuacao = estilo.substring(12, estilo.length());

            if(id.startsWith("OPTATIVA") && qtdOpt < contOpt){
                estilo = "fill:#"+ VERDE + estiloContinuacao;
                qtdOpt = qtdOpt+1;
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

