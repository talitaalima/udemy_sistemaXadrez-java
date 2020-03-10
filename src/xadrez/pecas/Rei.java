package xadrez.pecas;

import tabuleiro.Tabuleiro;
import xadrez.CorPecas;
import xadrez.XadrezPeca;

public class Rei extends XadrezPeca{

	public Rei(Tabuleiro tabuleiro, CorPecas corPecas) {
		super(tabuleiro, corPecas);
	}
	
	@Override
	public String toString () {
		return "R";
	}
	

}
