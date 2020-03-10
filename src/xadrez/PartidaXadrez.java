package xadrez;

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
	
	private void localNovaPeca(char coluna, int linha, XadrezPeca peca) {
		tabuleiro.localPeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
	}
	
	private void inicioPartida() {
		localNovaPeca('b', 6, new Torre(tabuleiro, CorPecas.BRANCO));
		localNovaPeca('e', 8, new Rei(tabuleiro, CorPecas.PRETO));
		localNovaPeca('e', 1, new Rei(tabuleiro, CorPecas.BRANCO));
	}
}
