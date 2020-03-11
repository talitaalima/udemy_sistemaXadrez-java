package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

public abstract class XadrezPeca extends Peca {
	
	private CorPecas corPecas;

	public XadrezPeca(Tabuleiro tabuleiro, CorPecas corPecas) {
		super(tabuleiro);
		this.corPecas = corPecas;
	}

	public CorPecas getCorPecas() {
		return corPecas;
	}
	
	protected boolean existePecaOponente(Posicao posicao) {
		XadrezPeca p = (XadrezPeca)getTabuleiro().peca(posicao);		
		return p != null && p.getCorPecas() != corPecas;
	}
}
