package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

public abstract class XadrezPeca extends Peca {
	
	private CorPecas corPecas;
	private int contarMovimentos;

	public XadrezPeca(Tabuleiro tabuleiro, CorPecas corPecas) {
		super(tabuleiro);
		this.corPecas = corPecas;
	}

	public CorPecas getCorPecas() {
		return corPecas;
	}
	
	public int getContarMovimentos() {
		return contarMovimentos;
	}
	public void incrementarMovimentos () {
		contarMovimentos++;
	}
	
	public void decrementarMovimentos () {
		contarMovimentos--;
	}
	
	public PosicaoXadrez getPosicaoXadrez() {
		return PosicaoXadrez.fromProsicao(posicao);
	}
	
	protected boolean existePecaOponente(Posicao posicao) {
		XadrezPeca p = (XadrezPeca)getTabuleiro().peca(posicao);		
		return p != null && p.getCorPecas() != corPecas;
	}
}
