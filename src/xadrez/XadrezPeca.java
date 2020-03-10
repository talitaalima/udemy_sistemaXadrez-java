package xadrez;

import tabuleiro.Peca;
import tabuleiro.Tabuleiro;

public class XadrezPeca extends Peca {
	
	private CorPecas corPecas;

	public XadrezPeca(Tabuleiro tabuleiro, CorPecas corPecas) {
		super(tabuleiro);
		this.corPecas = corPecas;
	}

	public CorPecas getCorPecas() {
		return corPecas;
	}

}
