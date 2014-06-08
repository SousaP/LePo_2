package Test;

import static org.junit.Assert.*;
import logic.Board;
import logic.Gem;

import org.junit.Test;

public class Teste {

	Board BTest = new Board();
	Gem GTest;

	char RandomBoard[][] = { 
			{ 'B', 'G', 'O', 'P', 'R', 'W', 'Y' , 'B'},
			{ 'G', 'O', 'G', 'O', 'G', 'O', 'G', 'O' },
			{ 'B', 'G', 'O', 'P', 'R', 'W', 'Y' , 'B'},
			{ 'W', 'Y', 'W', 'Y', 'W', 'Y', 'W', 'Y' },
			{ 'B', 'G', 'O', 'P', 'R', 'W', 'Y' , 'B'},
			{ 'P', 'R', 'P', 'R', 'P', 'R', 'P', 'R'},
			{ 'G', 'O', 'G', 'O', 'G', 'O', 'G', 'O' },
			{ 'B', 'G', 'O', 'P', 'R', 'W', 'Y' , 'B' },};
	
	char OnePlayBoard[][] = { 
			{ 'B', 'G', 'O', 'P', 'R', 'W', 'Y' , 'B'},
			{ 'G', 'O', 'G', 'O', 'G', 'O', 'G', 'O' },
			{ 'B', 'G', 'O', 'P', 'R', 'W', 'Y' , 'B'},
			{ 'W', 'Y', 'W', 'Y', 'W', 'Y', 'W', 'Y' },
			{ 'B', 'G', 'O', 'P', 'R', 'W', 'Y' , 'B'},
			{ 'P', 'O', 'P', 'R', 'P', 'R', 'P', 'R'},
			{ 'G', 'O', 'G', 'O', 'G', 'O', 'G', 'O' },
			{ 'B', 'G', 'O', 'P', 'R', 'W', 'Y' , 'B' },};

	@Test
	public void ExisteJogada() {
		GTest = BTest.Tip();

		assertTrue("verifica se que existe jogada", GTest != null);

	}

	@Test
	public void NaoExisteJogada() {
		BTest = new Board(RandomBoard);
		GTest = BTest.Tip();
		assertTrue("verifica se que nao existe jogada", GTest != null);
	}

	@Test
	public void ReconheceJogada() {
		BTest = new Board(OnePlayBoard);
	
		assertTrue("verifica se que existe jogada", BTest.MakePlay(BTest.getTab()[7][1], BTest.getTab()[7][2]) > 0);
		// Reconhece uma jogada valida
	}

	@Test
	public void JogadaInvalida() {
		
		BTest = new Board(RandomBoard);
		
		assertFalse("verifica se que existe jogada", BTest.MakePlay(BTest.getTab()[7][1], BTest.getTab()[7][2]) > 0);
		
		// Reconhece que � uma jogada invalida
	}

	@Test
	public void CriacaoAleatoria() {
		Board B1 = new Board(), B2 = new Board();
		assertTrue("verifica se que existe jogada", B1 != B2);
	}

	@Test
	public void JogadaProposta() {
		GTest = BTest.Tip();

		assertTrue("Jogada proposta", GTest != null);
	}

	@Test
	public void FinalDoJogoTempo() {
		// Se acaba o jogo depois de algum tempo
	}

	@Test
	public void FinalDoJogoObjetivos() {
		// Se acabar o jogo depois de atingir um objetivo proposto
	}

}
