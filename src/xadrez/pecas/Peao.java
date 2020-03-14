package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.CorPecas;
import xadrez.XadrezPeca;

public class Peao extends XadrezPeca {

	public Peao(Tabuleiro tabuleiro, CorPecas corPecas) {
		super(tabuleiro, corPecas);
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean [][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0,0);
		
		if (getCorPecas() == CorPecas.BRANCO) {
			p.setValor(posicao.getLinha()-1, posicao.getColuna());
			if (getTabuleiro().existePosicao(p) && !getTabuleiro().existePeca(p) ) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}

			p.setValor(posicao.getLinha()-2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha()-1, posicao.getColuna());
			if (getTabuleiro().existePosicao(p) && !getTabuleiro().existePeca(p) && getTabuleiro().existePosicao(p2) && !getTabuleiro().existePeca(p2)  && getContarMovimentos() == 0) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.setValor(posicao.getLinha()-1, posicao.getColuna()-1);
			if (getTabuleiro().existePosicao(p) && existePecaOponente(p) ) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.setValor(posicao.getLinha()-1, posicao.getColuna()+1);
			if (getTabuleiro().existePosicao(p) && existePecaOponente(p) ) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
		} else {
			p.setValor(posicao.getLinha()+1, posicao.getColuna());
			if (getTabuleiro().existePosicao(p) && !getTabuleiro().existePeca(p) ) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}

			p.setValor(posicao.getLinha()+2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha()+1, posicao.getColuna());
			if (getTabuleiro().existePosicao(p) && !getTabuleiro().existePeca(p) && getTabuleiro().existePosicao(p2) && !getTabuleiro().existePeca(p2)  && getContarMovimentos() == 0) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.setValor(posicao.getLinha()+1, posicao.getColuna()-1);
			if (getTabuleiro().existePosicao(p) && existePecaOponente(p) ) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.setValor(posicao.getLinha()+1, posicao.getColuna()+1);
			if (getTabuleiro().existePosicao(p) && existePecaOponente(p) ) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
		}
		return matriz;
	}
	
	@Override
	public String toString() {
		return "P";
	}
}
