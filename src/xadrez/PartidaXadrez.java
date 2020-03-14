package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {
	
	private int turno;
	private CorPecas atualJogador;	
	private Tabuleiro tabuleiro;
	private boolean xeque;
	private boolean xequeMate;
	
	private List<Peca> pecasTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();

	public PartidaXadrez() {
		tabuleiro = new Tabuleiro (8,8);
		turno = 1;
		atualJogador = CorPecas.BRANCO;
		xeque = false;
		inicioPartida();
	}
	
	public int getTurno() {
		return turno;
	}
	
	public CorPecas getAtualJogador() {
		return atualJogador;
	}
	
	public boolean getXeque() {
		return xeque;
	}
	
	public boolean getXequeMate() {
		return xequeMate;
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
		
		if (testeXeque(atualJogador)) {
			desfazerMovimento(origem, destino, capturarPeca);
			throw new XadrezExcecao("Voce nao pode se colocar em xeque");
		}
		
		xeque = (testeXeque(corOponente(atualJogador))) ? true  : false;
		
		if (testeXequeMate(corOponente(atualJogador))) {
			xequeMate = true;
		} else {
			proximoTurno();
		}
		return (XadrezPeca) capturarPeca;
	}
	
	private Peca movimentoPeca(Posicao origem, Posicao destino) {
		Peca p = tabuleiro.removePeca(origem);
		Peca capturarPeca = tabuleiro.removePeca(destino);
		tabuleiro.localPeca(p, destino);
		
		if (capturarPeca != null) {
			pecasTabuleiro.remove(capturarPeca);
			pecasCapturadas.add(capturarPeca);
		}
		return capturarPeca;
	}
	
	private void desfazerMovimento(Posicao origem, Posicao destino, Peca capturarPeca) {
		Peca p = tabuleiro.removePeca(destino);
		tabuleiro.localPeca(p, origem);
		
		if (capturarPeca != null) {
			tabuleiro.localPeca(capturarPeca, destino);
			pecasCapturadas.remove(capturarPeca);
			pecasTabuleiro.add(capturarPeca);			
		}
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
	
	private CorPecas corOponente (CorPecas corPecas) {
		return (corPecas == CorPecas.BRANCO) ? CorPecas.PRETO : CorPecas.BRANCO;
	}
	
	private XadrezPeca rei(CorPecas corPecas) {
		List<Peca> lista = pecasTabuleiro.stream().filter(x -> ((XadrezPeca)x).getCorPecas() == corPecas).collect(Collectors.toList());
		for (Peca p : lista) {
			if (p instanceof Rei) {
				return (XadrezPeca)p;
			}
		} 
		throw new IllegalStateException("Nao existe o rei" + corPecas + "no tabuleiro");
	}
	
	private boolean testeXeque(CorPecas corPecas) {
		Posicao reiPosicao = rei(corPecas).getPosicaoXadrez().toPosicao();
		List<Peca> oponetePecas =  pecasTabuleiro.stream().filter(x -> ((XadrezPeca)x).getCorPecas() == corOponente(corPecas)).collect(Collectors.toList());
		for (Peca p : oponetePecas) {
			boolean[][] matriz = p.possiveisMovimentos();
			if (matriz[reiPosicao.getLinha()][reiPosicao.getColuna()]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean testeXequeMate(CorPecas corPecas) {
		if (!testeXeque(corPecas)) {
			return false;
		}
		List<Peca> list = pecasTabuleiro.stream().filter(x -> ((XadrezPeca)x).getCorPecas() == corPecas).collect(Collectors.toList());
		for (Peca p : list) {
			boolean[][] matriz = p.possiveisMovimentos();
			for (int i=0; i<tabuleiro.getLinhas(); i++) {
				for (int j=0; j<tabuleiro.getColunas(); j++) {
					if (matriz[i][j]) {
						Posicao origem = ((XadrezPeca)p).getPosicaoXadrez().toPosicao();
						Posicao destino = new Posicao(i, j);
						Peca capturarPeca = movimentoPeca(origem, destino);
						boolean testeXeque = testeXeque(corPecas);
						desfazerMovimento(origem, destino, capturarPeca);
						if (!testeXeque) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	private void localNovaPeca(char coluna, int linha, XadrezPeca peca) {
		tabuleiro.localPeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
		pecasTabuleiro.add(peca);
	}
	
	private void inicioPartida() {
		/*
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
	*/
		localNovaPeca('h', 7, new Torre(tabuleiro, CorPecas.BRANCO));
        localNovaPeca('d', 1, new Torre(tabuleiro, CorPecas.BRANCO));
        localNovaPeca('e', 1, new Rei(tabuleiro, CorPecas.BRANCO));
        
        localNovaPeca('b', 8, new Torre(tabuleiro, CorPecas.PRETO));
        localNovaPeca('a', 8, new Rei(tabuleiro, CorPecas.PRETO));
        
	}
}
