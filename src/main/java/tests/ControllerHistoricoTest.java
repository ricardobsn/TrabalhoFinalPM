package tests;

import controller.ControllerHistorico;
import org.junit.Test;

import java.util.HashMap;

import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ControllerHistoricoTest {

    ControllerHistorico controleHistorico = new ControllerHistorico();

    @Test
    public void verificaCoeficienteRendimentoTest() {

        String linha = "4,5";
        double result = controleHistorico.pegaCoeficienteGeral(linha);
        assertEquals(4.5, result, 0.0);
    }

    @Test
    public void verificaSeELinhaDeNotaTest() {

        String linha = "APV";
        boolean result = controleHistorico.isLinhaDeNota(linha);
        assertTrue(result);
    }

    @Test
    public void verificaSeNaoELinhaDeNotaTest() {

        String linha = "Banana";
        boolean result = controleHistorico.isLinhaDeNota(linha);
        assertFalse(result);
    }

    @Test
    public void verificaSeELinhaDeCoeficienteGeralTest() {

        String linha = "Geral";
        boolean result = controleHistorico.isLinhaDeCoeficienteGeral(linha);
        assertTrue(result);
    }

    @Test
    public void verificaSeNaoELinhaDeCoeficienteGeralTest() {

        String linha = "Cachorro";
        boolean result = controleHistorico.isLinhaDeCoeficienteGeral(linha);
        assertFalse(result);
    }

    @Test
    public void testaTamanhoDoMapDeMateriasTest() {

        String linha = "Programação_Modular Aprovado";
        HashMap materias = new HashMap<String,String>();
        materias = controleHistorico.criaListaMaterias(linha);
        assertThat(materias.size(), is(1));
    }

    @Test
    public void montaMapDeMateriasTest() {

        HashMap materiasAux = new HashMap<String,String>();
        materiasAux.put("Programação_Modular", "Aprovado");
        String linha = "Programação_Modular Aprovado";
        HashMap materias = new HashMap<String,String>();
        materias = controleHistorico.criaListaMaterias(linha);
        assertThat(materiasAux, is(materias));
    }

    @Test
    public void verificaQuantidadeDeOptativasPassadasTest() {

        HashMap materias = new HashMap<String,String>();
        materias.put("TIN0141", "Aprovado");
        assertThat(materias.size(), is(1));
    }

    @Test
    public void verificaQuantidadeDeEletivasPassadasTest() {

        HashMap materias = new HashMap<String,String>();
        materias.put("TIN0151", "Aprovado");
        materias.put("TIN0152", "Aprovado");
        assertThat(materias.size(), is(2));
    }
}
