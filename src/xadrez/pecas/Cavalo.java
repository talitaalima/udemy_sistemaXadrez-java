package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.CorPecas;
import xadrez.XadrezPeca;

public class Cavalo extends XadrezPeca{

	public Cavalo(Tabuleiro tabuleiro, CorPecas corPecas) {
		super(tabuleiro, corPecas);
	}
	
	@Override
	public String toString () {
		return "C";
	}
	
	public boolean podeMover (Posicao posicao) {
		XadrezPeca p = (XadrezPeca)getTabuleiro().peca(posicao);
		return p == null || p.getCorPecas() != getCorPecas();
	}
	
	@Override
	public boolean[][] possiveisMovimentos() {
		boolean [][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0, 0);
		
		p.setValor(posicao.getLinha() - 1, posicao.getColuna()-2);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		p.setValor(posicao.getLinha() -2, posicao.getColuna()-1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		p.setValor(posicao.getLinha()-2, posicao.getColuna() + 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		p.setValor(posicao.getLinha()-1, posicao.getColuna() + 2);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		p.setValor(posicao.getLinha() + 1, posicao.getColuna() +2);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		p.setValor(posicao.getLinha() +2, posicao.getColuna() + 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		p.setValor(posicao.getLinha() + 2, posicao.getColuna() - 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//sudeste |>
		p.setValor(posicao.getLinha() + 1, posicao.getColuna() -2);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
			
		return matriz;
	}
}
