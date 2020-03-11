package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {
	
	private int turno;
	private CorPecas atualJogador;	
	private Tabuleiro tabuleiro;

	public PartidaXadrez() {
		tabuleiro = new Tabuleiro (8,8);
		turno = 1;
		atualJogador = CorPecas.BRANCO;
		inicioPartida();
	}
	
	public int getTurno() {
		return turno;
	}
	
	public CorPecas getAtualJogador() {
		return atualJogador;
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
	
	public boolean[][] possiveisMovimentos(PosicaoXadrez origemPosicao) {
		Posicao posicao = origemPosicao.toPosicao();
		validarOrigemPosicao(posicao);
		return tabuleiro.peca(posicao).possiveisMovimentos();
	}
	
	public XadrezPeca perfomXadrezMove(PosicaoXadrez origemPosicao, PosicaoXadrez destinoPosicao) {
		Posicao origem = origemPosicao.toPosicao();
		Posicao destino = destinoPosicao.toPosicao();
		validarOrigemPosicao(origem);
		validarDestinoPosicao(origem,destino);
		Peca capturarPeca = movimentoPeca(origem, destino);
		proximoTurno();
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
		if (atualJogador != ((XadrezPeca)tabuleiro.peca(posicao)).getCorPecas()) {
			throw new XadrezExcecao("A peca escolhida nao e sua");
		}
		if (!tabuleiro.peca(posicao).existeMovimentoPossivel()) {
			throw new XadrezExcecao("Nao ha movimentos possiveis para a peca escolhida");
		}
	}
	
	private void validarDestinoPosicao(Posicao origem, Posicao destino) {
		if (!tabuleiro.peca(origem).possivelMovimento(destino)) {
			throw new XadrezExcecao("A peca escolhida nao pode se mover para a posicao de destino");
		}
	}	
	
	private void proximoTurno() {
		turno++;
		atualJogador = (atualJogador == CorPecas.BRANCO) ? CorPecas.PRETO : CorPecas.BRANCO;
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
