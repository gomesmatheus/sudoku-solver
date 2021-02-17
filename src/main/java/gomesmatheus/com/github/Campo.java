package gomesmatheus.com.github;

import java.util.ArrayList;
import java.util.List;

public class Campo {

	private String id;
	private List<Integer> possiveis = new ArrayList<Integer>();  
	private Integer x;
	private Integer y;
	private Integer sessao;
	private Integer valor;
	private boolean isCampoMarcado;
	
	public Campo(String id, Integer x, Integer y, Integer sessao, Integer valor, boolean isCampoMarcado) {
		super();
		this.id = id;
		this.x = x;
		this.y = y;
		this.sessao = sessao;
		this.valor = valor;
		this.isCampoMarcado = isCampoMarcado;
		if(!isCampoMarcado) {
			possiveis.add(1);
			possiveis.add(2);
			possiveis.add(3);
			possiveis.add(4);
			possiveis.add(5);
			possiveis.add(6);
			possiveis.add(7);
			possiveis.add(8);
			possiveis.add(9);
		}
	}
	
	public void removeOsPossiveis() {
		possiveis = new ArrayList<Integer>();  
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<Integer> getPossiveis() {
		return possiveis;
	}
	public Integer getX() {
		return x;
	}
	public void setX(Integer x) {
		this.x = x;
	}
	public Integer getY() {
		return y;
	}
	public void setY(Integer y) {
		this.y = y;
	}
	public Integer getSessao() {
		return sessao;
	}
	public void setSessao(Integer sessao) {
		this.sessao = sessao;
	}
	public Integer getValor() {
		return valor;
	}
	public void setValor(Integer valor) {
		this.valor = valor;
	}
	public boolean isCampoMarcado() {
		return isCampoMarcado;
	}
	public void setCampoMarcado(boolean isCampoMarcado) {
		this.isCampoMarcado = isCampoMarcado;
	}
}