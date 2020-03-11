package xadrez;

import tabuleiro.Peca;
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
	
	public XadrezPeca perfomXadrezMove(PosicaoXadrez origemPosicao, PosicaoXadrez destinoPosicao) {
		Posicao origem = origemPosicao.toPosicao();
		Posicao destino = destinoPosicao.toPosicao();
		validarOrigemPosicao(origem);
		Peca capturarPeca = movimentoPeca(origem, destino);
		return (XadrezPeca) capturarPeca;
	}
	
	private Peca movimentoPeca(Posicao origem, Posicao destino) {
		Peca p = tabuleiro.removePeca(origem);
		Peca capturarPeca = tabuleiro.removePeca(destino);
		tabuleiro.localPeca(p, destino);
		return capturarPeca;
	}
	
	private void validarOrigemPosicao(Posicao posicao) {
		if (!tabuleiro.existePeca(posicao)) {
			throw new XadrezExcecao("Nao existe peca na posicao de origem");
		}
		
	}
	
	private void localNovaPeca(char coluna, int linha, XadrezPeca peca) {
		tabuleiro.localPeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
	}
	
	private void inicioPartida() {
		localNovaPeca('c', 1, new Torre(tabuleiro, CorPecas.BRANCO));
        localNovaPeca('c', 2, new Torre(tabuleiro, CorPecas.BRANCO));
        localNovaPeca('d', 2, new Torre(tabuleiro, CorPecas.BRANCO));
        localNovaPeca('e', 2, new Torre(tabuleiro, CorPecas.BRANCO));
        localNovaPeca('e', 1, new Torre(tabuleiro, CorPecas.BRANCO));
        localNovaPeca('d', 1, new Rei(tabuleiro, CorPecas.BRANCO));

        localNovaPeca('c', 7, new Torre(tabuleiro, CorPecas.PRETO));
        localNovaPeca('c', 8, new Torre(tabuleiro, CorPecas.PRETO));
        localNovaPeca('d', 7, new Torre(tabuleiro, CorPecas.PRETO));
        localNovaPeca('e', 7, new Torre(tabuleiro, CorPecas.PRETO));
        localNovaPeca('e', 8, new Torre(tabuleiro, CorPecas.PRETO));
        localNovaPeca('d', 8, new Rei(tabuleiro, CorPecas.PRETO));
	
	
	}
}
