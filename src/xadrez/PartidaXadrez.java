package xadrez;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Peao;
import xadrez.pecas.Rainha;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {

	private int turno;
	private CorPecas atualJogador;
	private Tabuleiro tabuleiro;
	private boolean xeque;
	private boolean xequeMate;
	private XadrezPeca peaoVulneravel;
	private XadrezPeca promocaoPeao;

	private List<Peca> pecasTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();

	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
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

	public XadrezPeca getPeaoVulneravel() {
		return peaoVulneravel;
	}

	public XadrezPeca getPromocaoPeao() {
		return promocaoPeao;
	}
	
	public XadrezPeca[][] getPecas() {
		XadrezPeca[][] matriz = new XadrezPeca[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				matriz[i][j] = (XadrezPeca) tabuleiro.peca(i, j);
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
		validarDestinoPosicao(origem, destino);
		Peca capturarPeca = movimentoPeca(origem, destino);

		if (testeXeque(atualJogador)) {
			desfazerMovimento(origem, destino, capturarPeca);
			throw new XadrezExcecao("Voce nao pode se colocar em xeque");
		}

		XadrezPeca pecaMovida = (XadrezPeca) tabuleiro.peca(destino);

		// especial promocao Peao
		promocaoPeao = null;
		if (pecaMovida instanceof Peao) {
			if ((pecaMovida.getCorPecas()==CorPecas.BRANCO && destino.getLinha() == 0) || (pecaMovida.getCorPecas()==CorPecas.PRETO && destino.getLinha() == 7))  {
				promocaoPeao = (XadrezPeca)tabuleiro.peca(destino);
				promocaoPeao = replacePromocaoPeca("Q");
			}
		}
		
		xeque = (testeXeque(corOponente(atualJogador))) ? true : false;

		if (testeXequeMate(corOponente(atualJogador))) {
			xequeMate = true;
		} else {
			proximoTurno();
		}

		// especial peao vulneravel
		if (pecaMovida instanceof Peao	&& (destino.getLinha() == origem.getLinha() - 2 || destino.getLinha() == origem.getLinha() + 2)) {
			peaoVulneravel = pecaMovida;
		} else {
			peaoVulneravel = null;
		}

		return (XadrezPeca) capturarPeca;
	}
	
	public XadrezPeca replacePromocaoPeca (String type) {
		if (promocaoPeao == null) {
			throw new IllegalStateException("Nao ha pecas para serem promovidas");
		}
		if (!type.equals("B") && !type.equals("T") && !type.equals("Q") && !type.equals("C")) {
			throw new InvalidParameterException("Tipo invalido para promocao");
		}
		Posicao posicao = promocaoPeao.getPosicaoXadrez().toPosicao();
		Peca p = tabuleiro.removePeca(posicao);
		pecasTabuleiro.remove(p);
		
		XadrezPeca novaPeca = novaPeca(type, promocaoPeao.getCorPecas());
		tabuleiro.localPeca(novaPeca, posicao);
		pecasTabuleiro.add(novaPeca);
		
		return novaPeca;
	}
	
	private XadrezPeca novaPeca(String type, CorPecas corPecas) {
		if (type.equals("B")) return new Bispo(tabuleiro, corPecas);
		if (type.equals("C")) return new Cavalo(tabuleiro, corPecas);
		if (type.equals("Q")) return new Rainha(tabuleiro, corPecas);
		return new Torre(tabuleiro, corPecas);			
	}

 	private Peca movimentoPeca(Posicao origem, Posicao destino) {
		XadrezPeca p = (XadrezPeca) tabuleiro.removePeca(origem);
		p.incrementarMovimentos();
		Peca capturarPeca = tabuleiro.removePeca(destino);
		tabuleiro.localPeca(p, destino);

		if (capturarPeca != null) {
			pecasTabuleiro.remove(capturarPeca);
			pecasCapturadas.add(capturarPeca);
		}
		// #especial roque pequeno
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			XadrezPeca torre = (XadrezPeca) tabuleiro.removePeca(origemTorre);
			tabuleiro.localPeca(torre, destinoTorre);
			torre.incrementarMovimentos();
		}
		// #especial roque grande
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			XadrezPeca torre = (XadrezPeca) tabuleiro.removePeca(origemTorre);
			tabuleiro.localPeca(torre, destinoTorre);
			torre.incrementarMovimentos();
		}
		// #especial peao vulneravel
		if (p instanceof Peao) {
			if (origem.getColuna() != destino.getColuna() && capturarPeca == null) {
				Posicao posicaoPeao;
				if (p.getCorPecas() == CorPecas.BRANCO) {
					posicaoPeao = new Posicao(destino.getLinha() + 1, destino.getColuna());
				} else {
					posicaoPeao = new Posicao(destino.getLinha() - 1, destino.getColuna());
				}
				capturarPeca = tabuleiro.removePeca(posicaoPeao);
				pecasCapturadas.add(capturarPeca);
				pecasTabuleiro.remove(capturarPeca);
			}
		}
		return capturarPeca;
	}

	private void desfazerMovimento(Posicao origem, Posicao destino, Peca capturarPeca) {
		XadrezPeca p = (XadrezPeca) tabuleiro.removePeca(destino);
		p.decrementarMovimentos();
		tabuleiro.localPeca(p, origem);

		if (capturarPeca != null) {
			tabuleiro.localPeca(capturarPeca, destino);
			pecasCapturadas.remove(capturarPeca);
			pecasTabuleiro.add(capturarPeca);
		}

		// #especial roque pequeno
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			XadrezPeca torre = (XadrezPeca) tabuleiro.removePeca(destinoTorre);
			tabuleiro.localPeca(torre, origemTorre);
			torre.decrementarMovimentos();
		}
		// #especial roque grande
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			XadrezPeca torre = (XadrezPeca) tabuleiro.removePeca(destinoTorre);
			tabuleiro.localPeca(torre, origemTorre);
			torre.decrementarMovimentos();
		}
		// #especial peao vulneravel
		if (p instanceof Peao) {
			if (origem.getColuna() != destino.getColuna() && capturarPeca == peaoVulneravel) {
				XadrezPeca peao = (XadrezPeca)tabuleiro.removePeca(destino);
				Posicao posicaoPeao;
				if (p.getCorPecas() == CorPecas.BRANCO) {
					posicaoPeao = new Posicao(3, destino.getColuna());
				} else {
					posicaoPeao = new Posicao(4, destino.getColuna());
				}
				tabuleiro.localPeca(peao, posicaoPeao);
			}
		}
	}

	private void validarOrigemPosicao(Posicao posicao) {
		if (!tabuleiro.existePeca(posicao)) {
			throw new XadrezExcecao("Nao existe peca na posicao de origem");
		}
		if (atualJogador != ((XadrezPeca) tabuleiro.peca(posicao)).getCorPecas()) {
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

	private CorPecas corOponente(CorPecas corPecas) {
		return (corPecas == CorPecas.BRANCO) ? CorPecas.PRETO : CorPecas.BRANCO;
	}

	private XadrezPeca rei(CorPecas corPecas) {
		List<Peca> lista = pecasTabuleiro.stream().filter(x -> ((XadrezPeca) x).getCorPecas() == corPecas)
				.collect(Collectors.toList());
		for (Peca p : lista) {
			if (p instanceof Rei) {
				return (XadrezPeca) p;
			}
		}
		throw new IllegalStateException("Nao existe o rei" + corPecas + "no tabuleiro");
	}

	private boolean testeXeque(CorPecas corPecas) {
		Posicao reiPosicao = rei(corPecas).getPosicaoXadrez().toPosicao();
		List<Peca> oponetePecas = pecasTabuleiro.stream()
				.filter(x -> ((XadrezPeca) x).getCorPecas() == corOponente(corPecas)).collect(Collectors.toList());
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
		List<Peca> list = pecasTabuleiro.stream().filter(x -> ((XadrezPeca) x).getCorPecas() == corPecas)
				.collect(Collectors.toList());
		for (Peca p : list) {
			boolean[][] matriz = p.possiveisMovimentos();
			for (int i = 0; i < tabuleiro.getLinhas(); i++) {
				for (int j = 0; j < tabuleiro.getColunas(); j++) {
					if (matriz[i][j]) {
						Posicao origem = ((XadrezPeca) p).getPosicaoXadrez().toPosicao();
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

		localNovaPeca('a', 1, new Torre(tabuleiro, CorPecas.BRANCO));
		localNovaPeca('b', 1, new Cavalo(tabuleiro, CorPecas.BRANCO));
		localNovaPeca('c', 1, new Bispo(tabuleiro, CorPecas.BRANCO));
		localNovaPeca('d', 1, new Rainha(tabuleiro, CorPecas.BRANCO));
		localNovaPeca('e', 1, new Rei(tabuleiro, CorPecas.BRANCO, this));
		localNovaPeca('f', 1, new Bispo(tabuleiro, CorPecas.BRANCO));
		localNovaPeca('g', 1, new Cavalo(tabuleiro, CorPecas.BRANCO));
		localNovaPeca('h', 1, new Torre(tabuleiro, CorPecas.BRANCO));
		localNovaPeca('a', 2, new Peao(tabuleiro, CorPecas.BRANCO, this));
		localNovaPeca('b', 2, new Peao(tabuleiro, CorPecas.BRANCO, this));
		localNovaPeca('c', 2, new Peao(tabuleiro, CorPecas.BRANCO, this));
		localNovaPeca('d', 2, new Peao(tabuleiro, CorPecas.BRANCO, this));
		localNovaPeca('e', 2, new Peao(tabuleiro, CorPecas.BRANCO, this));
		localNovaPeca('f', 2, new Peao(tabuleiro, CorPecas.BRANCO, this));
		localNovaPeca('g', 2, new Peao(tabuleiro, CorPecas.BRANCO, this));
		localNovaPeca('h', 2, new Peao(tabuleiro, CorPecas.BRANCO, this));

		localNovaPeca('a', 8, new Torre(tabuleiro, CorPecas.PRETO));
		localNovaPeca('b', 8, new Cavalo(tabuleiro, CorPecas.PRETO));
		localNovaPeca('c', 8, new Bispo(tabuleiro, CorPecas.PRETO));
		localNovaPeca('d', 8, new Rainha(tabuleiro, CorPecas.PRETO));
		localNovaPeca('e', 8, new Rei(tabuleiro, CorPecas.PRETO, this));
		localNovaPeca('f', 8, new Bispo(tabuleiro, CorPecas.PRETO));
		localNovaPeca('g', 8, new Cavalo(tabuleiro, CorPecas.PRETO));
		localNovaPeca('h', 8, new Torre(tabuleiro, CorPecas.PRETO));
		localNovaPeca('a', 7, new Peao(tabuleiro, CorPecas.PRETO, this));
		localNovaPeca('b', 7, new Peao(tabuleiro, CorPecas.PRETO, this));
		localNovaPeca('c', 7, new Peao(tabuleiro, CorPecas.PRETO, this));
		localNovaPeca('d', 7, new Peao(tabuleiro, CorPecas.PRETO, this));
		localNovaPeca('e', 7, new Peao(tabuleiro, CorPecas.PRETO, this));
		localNovaPeca('f', 7, new Peao(tabuleiro, CorPecas.PRETO, this));
		localNovaPeca('g', 7, new Peao(tabuleiro, CorPecas.PRETO, this));
		localNovaPeca('h', 7, new Peao(tabuleiro, CorPecas.PRETO, this));
	}
}
