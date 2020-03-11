package aplication;

import java.util.Scanner;

import xadrez.PartidaXadrez;
import xadrez.PosicaoXadrez;
import xadrez.XadrezPeca;

public class JogoXadrez {
	
	public static void main (String[] args) {
		
		Scanner sc = new Scanner(System.in);
		PartidaXadrez partidaXadrez = new PartidaXadrez();
		
		while (true) {
			UI.printTabuleiro(partidaXadrez.getPecas());
			System.out.println();
			System.out.print("Origem: ");
			PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);

			System.out.println();
			System.out.print("Destino: ");
			PosicaoXadrez destino = UI.lerPosicaoXadrez(sc);
		
			XadrezPeca capturarPeca = partidaXadrez.perfomXadrezMove(origem, destino);
		}
	}

}
