package com.esir.sr.sweetsnake.game.map;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.esir.sr.sweetsnake.element.Sweet;

@Service
public class BoardGenerator {

	public Board generateBoard(int height, int width, int nbSweet) {
		Board board = new Board(height, width);

		final Random random = new Random();
		for (int i = 0; i < nbSweet; i++) {
			int j, k;
			do {
				j = random.nextInt(height);
				k = random.nextInt(width);
			} while (board.hasElement(j, k));

			Sweet sweet = new Sweet();
			sweet.setXY(j, k);
			board.set(sweet);
		}
		return board;
	}
}
