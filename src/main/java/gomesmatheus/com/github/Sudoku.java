package gomesmatheus.com.github;

import org.junit.Test;


public class Sudoku {

	@Test
	public void tentaResolver() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\NAJA INFORMATICA\\Downloads\\chromedriver_win32\\chromedriver.exe");
		Tabuleiro tabuleiro = new Tabuleiro();
		tabuleiro.lerMatrizCampos();
	}
}