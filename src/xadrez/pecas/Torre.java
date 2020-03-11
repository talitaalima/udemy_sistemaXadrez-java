package xadrez.pecas;

import tabuleiro.Tabuleiro;
import xadrez.CorPecas;
import xadrez.XadrezPeca;

public class Torre extends XadrezPeca{

	public Torre(Tabuleiro tabuleiro, CorPecas corPecas) {
		super(tabuleiro, corPecas);
	}
	
	@Override
	public String toString() {
		return "T";
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean [][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		return matriz;
	}
}
