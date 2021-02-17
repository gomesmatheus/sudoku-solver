package gomesmatheus.com.github;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Tabuleiro {

	private static String URL = "https://www.geniol.com.br/logica/sudoku/";
	private static WebDriver driver = new ChromeDriver();
	private static Campo[][] matriz = new Campo[9][9];
	
	public void trocaDificuldade() {
		
	}

	public void lerMatrizCampos() {
		driver.manage().window().maximize();
		driver.get(URL);

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				String id = "c" + ((i * 9) + j);
				String valor = driver.findElement(By.id(id)).getText();
				Integer valorInt;
				boolean isCampoMarcado;
				try {
					valorInt = Integer.valueOf(valor);
					isCampoMarcado = true;
				} catch (Exception e) {
					valorInt = null;
					isCampoMarcado = false;
				}
				matriz[i][j] = new Campo(id, i, j, retornaSessaoDoCampo(i, j), valorInt, isCampoMarcado);
			}
		}
		exibeMatriz();

		// ATUALIZA TODOS CAMPOS
		for (int l = 0; l < 5000; l++) {
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					Campo campo = matriz[i][j];
					if (campo.isCampoMarcado()) {
						atualizaCamposPossiveis(campo);
					}
				}
			}

		// CHECA SE OS CAMPOS POSSUEM APENAS UM VALOR QUE PODE SER INSERIDO
			for (int k = 0; k < 100; k++) {
				for (int i = 0; i < 9; i++) {
					for (int j = 0; j < 9; j++) {
						Campo campo = matriz[i][j];
						if (!campo.isCampoMarcado() && campo.getPossiveis().size() == 1) {
							campo.setValor(campo.getPossiveis().get(0));
							marcaCampo(campo);
							atualizaCamposPossiveis(campo);
						}
					}
				}
			}

		// CHECA APENAS UM CAMPO DE CADA LINHA É O COMPATIVEL COM UM CERTO VALOR
		
			for (int i = 0; i < 9; i++) {
				for (int valor = 1; valor <= 9; valor++) {
					int camposEmQueOValorEPossivel = 0;
					Campo campoPossivel = null;
					for (int j = 0; j < 9; j++) {
						Campo campo = matriz[i][j];
						if (campo.getPossiveis().contains(new Integer(valor))) {
							camposEmQueOValorEPossivel++;
							campoPossivel = campo;
						}
					}
					if (camposEmQueOValorEPossivel == 1) {
						System.out.println("Marcou Pela Linha o id " + campoPossivel.getId());
						campoPossivel.setValor(valor);
						marcaCampo(campoPossivel);
						atualizaCamposPossiveis(campoPossivel);
					}
				}

			}

			// CHECA APENAS UM CAMPO DE CADA COLUNA É O COMPATIVEL COM UM CERTO VALOR
			for (int i = 0; i < 9; i++) {
				for (int valor = 1; valor <= 9; valor++) {
					int camposEmQueOValorEPossivel = 0;
					Campo campoPossivel = null;
					for (int j = 0; j < 9; j++) {
						Campo campo = matriz[j][i];
						if (campo.getPossiveis().contains(new Integer(valor))) {
							camposEmQueOValorEPossivel++;
							campoPossivel = campo;
						}
					}
					if (camposEmQueOValorEPossivel == 1) {
						System.out.println("Marcou pela coluna o id " + campoPossivel.getId());
						campoPossivel.setValor(valor);
						marcaCampo(campoPossivel);
						atualizaCamposPossiveis(campoPossivel);
					}
				}
			}
		}

		// CHECA APENAS UM CAMPO DE CADA SESSÃO É O COMPATIVEL COM UM CERTO VALOR
		for (int sessao = 1; sessao <= 9; sessao++) {
			for (int valor = 1; valor <= 9; valor++) {
				int camposEmQueOValorEPossivel = 0;
				Campo campoPossivel = null;
				List<Campo> camposNaMesmaSessao = retornaCamposNaMesmaSessao(sessao);
				for (int j = 0; j < 9; j++) {
					Campo campo = camposNaMesmaSessao.get(j);
					if (campo.getPossiveis().contains(new Integer(valor))) {
						camposEmQueOValorEPossivel++;
						campoPossivel = campo;
					}
				}
				if (camposEmQueOValorEPossivel == 1) {
					System.out.println("Marcou pela sessão o id " + campoPossivel.getId());
					campoPossivel.setValor(valor);
					marcaCampo(campoPossivel);
					atualizaCamposPossiveis(campoPossivel);
				}
			}

		}
		
		retornaCamposNaMesmaColuna(3);

		System.out.println("Campos atualizados");
	}

	private static void marcaCampo(Campo campo) {
		driver.findElement(By.id(campo.getId())).click();
		driver.findElement(By.xpath("//td[@data-value='" + campo.getValor() + "']")).click();
//		campo.setValor(campo.getPossiveis().get(0));
		campo.removeOsPossiveis();
		campo.setCampoMarcado(true);
		System.out.println("Marcou campo com id " + campo.getId() + " com o valor " + campo.getValor());
	}

	private static Integer retornaSessaoDoCampo(Integer x, Integer y) {
		if (x >= 0 && x <= 2 && y >= 0 && y <= 2) {
			return 1;
		} else if (x >= 0 && x <= 2 && y >= 3 && y <= 5) {
			return 2;
		} else if (x >= 0 && x <= 2 && y >= 6 && y <= 8) {
			return 3;
		} else if (x >= 3 && x <= 5 && y >= 0 && y <= 2) {
			return 4;
		} else if (x >= 3 && x <= 5 && y >= 3 && y <= 5) {
			return 5;
		} else if (x >= 3 && x <= 5 && y >= 6 && y <= 8) {
			return 6;
		} else if (x >= 6 && x <= 8 && y >= 0 && y <= 2) {
			return 7;
		} else if (x >= 6 && x <= 8 && y >= 3 && y <= 5) {
			return 8;
		} else if (x >= 6 && x <= 8 && y >= 6 && y <= 8) {
			return 9;
		}
		return -1;
	}

	private void atualizaCamposPossiveis(Campo campo) {
		for (Campo campoAux : retornaCamposNaMesmaLinha(campo.getX())) {
			if (!campoAux.isCampoMarcado() && campoAux.getPossiveis().contains(new Integer(campo.getValor()))) {
				campoAux.getPossiveis().remove(new Integer(campo.getValor()));
			}
		}

		for (Campo campoAux : retornaCamposNaMesmaColuna(campo.getY())) {
			if (!campoAux.isCampoMarcado() && campoAux.getPossiveis().contains(new Integer(campo.getValor()))) {
				campoAux.getPossiveis().remove(new Integer(campo.getValor()));
			}
		}

		for (Campo campoAux : retornaCamposNaMesmaSessao(campo.getSessao())) {
			if (!campoAux.isCampoMarcado() && campoAux.getPossiveis().contains(new Integer(campo.getValor()))) {
				campoAux.getPossiveis().remove(new Integer(campo.getValor()));
			}
		}
	}

	private List<Campo> retornaCamposNaMesmaLinha(Integer x) {
		List<Campo> campos = new ArrayList<Campo>();
		for (int i = 0; i < 9; i++) {
			campos.add(matriz[x][i]);
		}
		return campos;
	}

	private List<Campo> retornaCamposNaMesmaColuna(Integer y) {
		List<Campo> campos = new ArrayList<Campo>();
		for (int i = 0; i < 9; i++) {
			campos.add(matriz[i][y]);
		}
		return campos;
	}

	private List<Campo> retornaCamposNaMesmaSessao(Integer sessao) {
		List<Campo> campos = new ArrayList<Campo>();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (matriz[i][j].getSessao() == sessao) {
					campos.add(matriz[i][j]);
				}
			}
		}
		return campos;
	}

	private static void exibeMatriz() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				Campo campo = matriz[i][j];
				System.out.print((campo.getValor() == null ? " " : campo.getValor()) + "[" + campo.getSessao() + "] ");
			}
			System.out.println();
		}
	}
}