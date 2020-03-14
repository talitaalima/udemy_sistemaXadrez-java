package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.CorPecas;
import xadrez.PartidaXadrez;
import xadrez.XadrezPeca;

public class Rei extends XadrezPeca {

	private PartidaXadrez partidaXadrez;

	public Rei(Tabuleiro tabuleiro, CorPecas corPecas, PartidaXadrez partidaXadrez) {
		super(tabuleiro, corPecas);
		this.partidaXadrez = partidaXadrez;
	}

	@Override
	public String toString() {
		return "R";
	}

	public boolean podeMover(Posicao posicao) {
		XadrezPeca p = (XadrezPeca) getTabuleiro().peca(posicao);
		return p == null || p.getCorPecas() != getCorPecas();
	}

	private boolean testeTorreRoque(Posicao posicao) {
		XadrezPeca p = (XadrezPeca) getTabuleiro().peca(posicao);
		return p != null && p instanceof Torre && p.getCorPecas() == getCorPecas() && p.getContarMovimentos() == 0;
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0, 0);

		// acima
		p.setValor(posicao.getLinha() - 1, posicao.getColuna());
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// abaixo
		p.setValor(posicao.getLinha() + 1, posicao.getColuna());
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// esquerda
		p.setValor(posicao.getLinha(), posicao.getColuna() - 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// direita
		p.setValor(posicao.getLinha(), posicao.getColuna() + 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// noroeste <^
		p.setValor(posicao.getLinha() - 1, posicao.getColuna() - 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// nordoeste ^>
		p.setValor(posicao.getLinha() - 1, posicao.getColuna() + 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// sudoeste <|
		p.setValor(posicao.getLinha() + 1, posicao.getColuna() - 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// sudeste |>
		p.setValor(posicao.getLinha() + 1, posicao.getColuna() + 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// #especial Roque
		if (getContarMovimentos() == 0 && !partidaXadrez.getXeque()) {
			// roque pequeno
			Posicao posicaoTorreRei = new Posicao(posicao.getLinha(), posicao.getColuna() + 3);
			if (testeTorreRoque(posicaoTorreRei)) {
				Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() + 2);
				if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null) {
					matriz[posicao.getLinha()][posicao.getColuna() + 2] = true;
				}

			}
			
			// #especial Roque
			if (getContarMovimentos() == 0 && !partidaXadrez.getXeque()) {
				// roque grande
				Posicao posicaoTorreRainha = new Posicao(posicao.getLinha(), posicao.getColuna() -4);
				if (testeTorreRoque(posicaoTorreRainha)) {
					Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
					Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 2);
					Posicao p3 = new Posicao(posicao.getLinha(), posicao.getColuna() - 3);
					if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null  && getTabuleiro().peca(p3) == null) {
						matriz[posicao.getLinha()][posicao.getColuna() -2] = true;
					}
	
				}
			}
		}

		return matriz;
	}
}
