package xadrez;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {
	
	private Tabuleiro tabuleiro;

	public PartidaXadrez() {
		tabuleiro = new Tabuleiro (8,8);
		inicioPartida();
	}
	
	public XadrezPeca[][] getPecas() {
		XadrezPeca[][] matriz = new XadrezPeca[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i=0; i<tabuleiro.getLinhas(); i++) {
			for (int j=0; j<tabuleiro.getColunas(); j++) {
				matriz[i][j] = (XadrezPeca) tabuleiro.peca(i,j);
			}
		}
		return matriz;
	}
	
	private void inicioPartida() {
		tabuleiro.localPeca(new Torre(tabuleiro, CorPecas.BRANCO), new Posicao(2,1));
		tabuleiro.localPeca(new Rei(tabuleiro, CorPecas.PRETO), new Posicao(0,4));
		tabuleiro.localPeca(new Rei(tabuleiro, CorPecas.BRANCO), new Posicao(7,4));
	}
}
