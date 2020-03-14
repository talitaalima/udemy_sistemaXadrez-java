package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.CorPecas;
import xadrez.XadrezPeca;

public class Bispo extends XadrezPeca {

	public Bispo(Tabuleiro tabuleiro, CorPecas corPecas) {
		super(tabuleiro, corPecas);
	}

	@Override
	public String toString() {
		return "B";
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean [][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0,0);
		
		// noroeste
		p.setValor(posicao.getLinha() - 1, posicao.getColuna() -1);
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().existePeca(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setValor(p.getLinha()-1, p.getColuna()-1); ;
		}
		if (getTabuleiro().existePosicao(p) && existePecaOponente(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;			
		}
		
		// nordeste
		p.setValor(posicao.getLinha() - 1, posicao.getColuna()+1);
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().existePeca(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setValor(p.getLinha()-1, p.getColuna()+1); ;
		}
		if (getTabuleiro().existePosicao(p) && existePecaOponente(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;			
		}

		// sudeste
		p.setValor(posicao.getLinha()+1, posicao.getColuna() + 1);
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().existePeca(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setValor(p.getLinha()+1, p.getColuna()+1); ;
		}
		if (getTabuleiro().existePosicao(p) && existePecaOponente(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;			
		}
		
		// sudoeste
		p.setValor(posicao.getLinha()+1, posicao.getColuna() - 1);
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().existePeca(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setValor(p.getLinha()+1, p.getColuna()-1); ;
		}
		if (getTabuleiro().existePosicao(p) && existePecaOponente(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;			
		}
		
		return matriz;
	}
}
