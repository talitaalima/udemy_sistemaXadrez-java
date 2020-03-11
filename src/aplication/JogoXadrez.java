package aplication;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.PartidaXadrez;
import xadrez.PosicaoXadrez;
import xadrez.XadrezExcecao;
import xadrez.XadrezPeca;

public class JogoXadrez {
	
	public static void main (String[] args) {
		
		Scanner sc = new Scanner(System.in);
		PartidaXadrez partidaXadrez = new PartidaXadrez();
		List<XadrezPeca> capturada = new ArrayList<>();
		
		while (true) {
			try {
				UI.clearScreen();
				UI.printPartida(partidaXadrez, capturada);
				System.out.println();
				System.out.print("Origem: ");
				PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);
				
				boolean [][] possiveisMovimentos = partidaXadrez.possiveisMovimentos(origem);
				UI.clearScreen();
				UI.printTabuleiro(partidaXadrez.getPecas(), possiveisMovimentos);
								
				System.out.println();
				System.out.print("Destino: ");
				PosicaoXadrez destino = UI.lerPosicaoXadrez(sc);
			
				XadrezPeca capturarPeca = partidaXadrez.perfomXadrezMove(origem, destino);
				
				if (capturarPeca != null) {
					capturada.add(capturarPeca);
				}
			
			} catch (XadrezExcecao e) {
				System.out.println(e.getMessage());
				sc.nextLine();	
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
	}
}
