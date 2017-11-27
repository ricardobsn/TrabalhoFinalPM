package tests;

import controller.ControllerFluxograma;
import org.junit.Test;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class ControllerFluxogramaTest {

    ControllerFluxograma controleFluxograma = new ControllerFluxograma();

    @Test
    public void verificaSeAMateriaFoiCursadaAprovadaTest() {

        String materia = "0001";
        HashMap materias = new HashMap<String,String>();
        materias.put("0001", "Aprovado");
        materias.put("0002", "por");
        ControllerFluxograma.Status result = controleFluxograma.verificaSeAMateriaFoiCursada(materia, materias);

        assertEquals(ControllerFluxograma.Status.APROVADO, result );
    }

    @Test
    public void verificaSeAMateriaFoiCursadaReprovadoTest() {

        String materia = "0002";
        HashMap materias = new HashMap<String,String>();
        materias.put("0001", "Aprovado");
        materias.put("0002", "por");

        ControllerFluxograma.Status result = controleFluxograma.verificaSeAMateriaFoiCursada(materia, materias);

        assertEquals(ControllerFluxograma.Status.REPROVADO, result );
    }

    @Test
    public void verificaSeAMateriaNaoFoiCursadaTest() {

        String materia = "0003";
        HashMap materias = new HashMap<String,String>();
        materias.put("0001", "Aprovado");
        materias.put("0002", "por");
        materias.put("0003", "Indefinido");

        ControllerFluxograma.Status result = controleFluxograma.verificaSeAMateriaFoiCursada(materia, materias);

        assertEquals(ControllerFluxograma.Status.PENDENTE, result );
    }


}
