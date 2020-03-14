package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.CorPecas;
import xadrez.PartidaXadrez;
import xadrez.XadrezPeca;

public class Peao extends XadrezPeca {

	private PartidaXadrez partidaXadrez;
	
	public Peao(Tabuleiro tabuleiro, CorPecas corPecas, PartidaXadrez partidaXadrez) {
		super(tabuleiro, corPecas);
		this.partidaXadrez = partidaXadrez;
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
			
			//#especial peao vulneravel branco
			if (posicao.getLinha()==3) {
				Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna()-1);
				if (getTabuleiro().existePosicao(esquerda) && existePecaOponente(esquerda) && getTabuleiro().peca(esquerda)== partidaXadrez.getPeaoVulneravel()) {
					matriz[esquerda.getLinha()-1] [esquerda.getColuna()] = true;
				}
				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna()+1);
				if (getTabuleiro().existePosicao(direita) && existePecaOponente(direita) && getTabuleiro().peca(direita)== partidaXadrez.getPeaoVulneravel()) {
					matriz[direita.getLinha()-1] [direita.getColuna()] = true;
				}
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
			
			//#especial peao vulneravel preto
			if (posicao.getLinha()==4) {
				Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna()-1);
				if (getTabuleiro().existePosicao(esquerda) && existePecaOponente(esquerda) && getTabuleiro().peca(esquerda)== partidaXadrez.getPeaoVulneravel()) {
					matriz[esquerda.getLinha()+1] [esquerda.getColuna()] = true;
				}
				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna()+1);
				if (getTabuleiro().existePosicao(direita) && existePecaOponente(direita) && getTabuleiro().peca(direita)== partidaXadrez.getPeaoVulneravel()) {
					matriz[direita.getLinha()-1] [direita.getColuna()] = true;
				}
			}
			
		}
		return matriz;
	}
	
	@Override
	public String toString() {
		return "P";
	}
}
