/*==============================================================================
* Thracian Treasure Slot Simulation version 0.9.1                              *
* Copyrights (C) 2013-2024 Velbazhd Software LLC                               *
*                                                                              *
* developed by Todor Balabanov ( todor.balabanov@gmail.com )                   *
* Sofia, Bulgaria                                                              *
*                                                                              *
* This program is free software: you can redistribute it and/or modify         *
* it under the terms of the GNU General Public License as published by         *
* the Free Software Foundation, either version 3 of the License, or            *
* (at your option) any later version.                                          *
*                                                                              *
* This program is distributed in the hope that it will be useful,              *
* but WITHOUT ANY WARRANTY; without even the implied warranty of               *
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                *
* GNU General Public License for more details.                                 *
*                                                                              *
* You should have received a copy of the GNU General Public License            *
* along with this program. If not, see <http://www.gnu.org/licenses/>.         *
*                                                                              *
*==============================================================================*/

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Main application class.
 */
class MainClass {
	/**
	 * Free spins helper class. Only keeps information of free spin properties.
	 */
	private static class FreeSpin {
		public int freeGamesNumber = 0;
		public int[][] reels = {};
		public int[][] wilds = {};

		public FreeSpin(int freeGamesNumber, int[][] reels, int[][] wilds) {
			this.freeGamesNumber = freeGamesNumber;
			this.reels = reels;
			this.wilds = wilds;
		}
	}

	/**
	 * Pseudo-random number generator.
	 */
	private static Random prng = new SecureRandom();

	/**
	 * List of symbols names.
	 */
	private static String[] symbols = { "", "SYM01", "", "SYM03", "SYM04", "SYM05", "SYM06", "SYM07", "SYM08", "SYM09",
			"SYM10", "SYM11", "SYM12", "", "", "", "SYM16", };

	/**
	 * Slot game pay table.
	 */
	private static int[][] paytable = { new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			new int[] { 0, 0, 0, 30, 20, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			new int[] { 0, 0, 0, 150, 75, 50, 40, 5, 4, 4, 3, 3, 2, 0, 0, 0, 0 },
			new int[] { 0, 0, 0, 250, 100, 100, 75, 50, 40, 20, 6, 5, 4, 0, 0, 0, 0 },
			new int[] { 0, 0, 0, 500, 250, 175, 150, 100, 50, 30, 25, 15, 10, 0, 0, 0, 0 }, };

	/**
	 * Scatter multipliers discrete distribution.
	 */
	private static int[] scatterMultipliers = { 0, 0, 0, 1, 2, 3 };

	/**
	 * Lines combinations.
	 */
	private static int[][] lines = { new int[] { 1, 1, 1, 1, 1 }, new int[] { 0, 0, 0, 0, 0 },
			new int[] { 2, 2, 2, 2, 2 }, new int[] { 0, 1, 2, 1, 0 }, new int[] { 2, 1, 0, 1, 2 },
			new int[] { 0, 0, 1, 2, 2 }, new int[] { 2, 2, 1, 0, 0 }, new int[] { 1, 0, 1, 2, 1 },
			new int[] { 1, 2, 1, 0, 1 }, new int[] { 0, 1, 1, 1, 2 }, new int[] { 2, 1, 1, 1, 0 },
			new int[] { 1, 0, 0, 1, 2 }, new int[] { 1, 2, 2, 1, 0 }, new int[] { 1, 1, 0, 1, 2 },
			new int[] { 1, 1, 2, 1, 0 }, new int[] { 0, 1, 0, 1, 2 }, new int[] { 2, 1, 2, 1, 0 },
			new int[] { 2, 1, 0, 0, 1 }, new int[] { 0, 1, 2, 2, 1 }, new int[] { 0, 1, 1, 2, 1 },
			new int[] { 0, 1, 0, 1, 0 }, new int[] { 2, 1, 2, 1, 2 }, new int[] { 0, 2, 0, 2, 0 },
			new int[] { 2, 0, 2, 0, 2 }, new int[] { 0, 0, 2, 0, 0 }, new int[] { 2, 2, 0, 2, 2 },
			new int[] { 0, 2, 2, 2, 0 }, new int[] { 2, 0, 0, 0, 2 }, new int[] { 1, 0, 2, 0, 1 },
			new int[] { 1, 2, 0, 2, 1 }, new int[] { 0, 0, 0, 1, 2 }, new int[] { 2, 2, 2, 1, 0 },
			new int[] { 2, 0, 1, 0, 2 }, new int[] { 0, 2, 1, 2, 0 }, new int[] { 1, 2, 1, 2, 1 },
			new int[] { 1, 0, 1, 0, 1 }, new int[] { 0, 0, 0, 2, 2 }, new int[] { 2, 2, 2, 0, 0 },
			new int[] { 0, 1, 1, 1, 0 }, new int[] { 2, 1, 1, 1, 2 }, new int[] { 1, 2, 2, 2, 1 },
			new int[] { 1, 0, 0, 0, 1 }, new int[] { 0, 0, 2, 2, 2 }, new int[] { 2, 2, 0, 0, 0 },
			new int[] { 1, 1, 0, 1, 1 }, new int[] { 1, 1, 2, 1, 1 }, new int[] { 2, 0, 1, 0, 1 },
			new int[] { 0, 2, 1, 2, 1 }, new int[] { 1, 0, 2, 2, 0 }, new int[] { 1, 2, 0, 0, 2 }, };

	/**
	 * Strips 1 in base game.
	 */
	private static int[][] baseReels1 = {
			new int[] { 3, 7, 4, 7, 11, 12, 6, 11, 7, 7, 9, 4, 10, 12, 7, 7, 5, 9, 8, 1, 9, 8, 10, 9, 7, 5, 5, 7, 9, 10,
					10, 12, 6, 6, 10, 10, 8, 11, 8, 7, 4, 3, 5, 12, 6, 9, 8, 1, 9, 8, 9, 8, 4, 3, 11, 11, 7, 9, 8, 11,
					7, 3, 11 },
			new int[] { 3, 9, 4, 11, 7, 4, 11, 9, 10, 5, 5, 11, 3, 9, 8, 1, 9, 8, 11, 6, 6, 6, 10, 10, 12, 12, 7, 6, 11,
					10, 10, 10, 6, 11, 7, 4, 3, 10, 5, 6, 9, 4, 6, 7, 7, 7, 12, 12, 11, 3, 10, 11, 11, 8, 12, 9, 9, 9,
					11, 7, 4, 11, 10 },
			new int[] { 8, 11, 10, 7, 9, 11, 3, 8, 8, 7, 11, 3, 8, 8, 3, 3, 11, 6, 7, 4, 11, 6, 16, 12, 5, 12, 7, 4, 10,
					10, 6, 8, 8, 6, 6, 6, 7, 3, 6, 10, 10, 6, 3, 10, 9, 7, 7, 4, 10, 3, 10, 8, 10, 16, 10, 5, 9, 5, 4,
					9, 10, 3, 10 },
			new int[] { 3, 8, 7, 5, 16, 6, 3, 5, 3, 9, 3, 7, 7, 12, 3, 7, 9, 4, 10, 6, 5, 8, 4, 7, 9, 11, 7, 6, 6, 5,
					16, 8, 7, 7, 4, 8, 10, 9, 16, 10, 3, 5, 3, 8, 4, 9, 3, 4, 9, 10, 10, 7, 5, 5, 6, 10, 3, 12, 16, 10,
					10, 5, 3 },
			new int[] { 10, 3, 5, 3, 10, 8, 10, 7, 5, 6, 5, 5, 7, 3, 10, 3, 4, 8, 3, 12, 5, 4, 3, 8, 10, 4, 6, 3, 9, 8,
					10, 10, 5, 10, 6, 3, 8, 5, 8, 8, 4, 5, 5, 7, 3, 4, 6, 6, 5, 3, 3, 5, 6, 3, 3, 12, 5, 4, 4, 3, 3, 5,
					5 }, };

	/**
	 * Strips 2 in base game.
	 */
	private static int[][] baseReels2 = {
			new int[] { 7, 7, 4, 16, 3, 12, 6, 9, 7, 3, 9, 10, 10, 3, 7, 3, 7, 5, 8, 8, 9, 8, 3, 9, 7, 3, 11, 16, 9, 10,
					10, 3, 6, 6, 10, 16, 8, 8, 8, 7, 10, 12, 4, 4, 6, 8, 4, 8, 7, 8, 9, 8, 10, 7, 11, 9, 7, 9, 8, 9, 7,
					4, 4 },
			new int[] { 12, 9, 4, 11, 9, 8, 1, 9, 8, 5, 3, 11, 3, 7, 7, 8, 11, 3, 3, 6, 6, 6, 8, 10, 4, 12, 7, 6, 3, 4,
					10, 4, 5, 3, 8, 8, 4, 12, 12, 9, 8, 1, 9, 8, 7, 7, 12, 4, 11, 5, 10, 12, 11, 8, 8, 9, 9, 9, 10, 7,
					11, 11, 10 },
			new int[] { 8, 11, 10, 7, 9, 11, 5, 5, 3, 7, 11, 3, 8, 8, 5, 11, 11, 6, 7, 11, 5, 6, 11, 12, 5, 12, 7, 10,
					10, 10, 8, 8, 8, 6, 6, 6, 7, 10, 9, 10, 10, 6, 10, 10, 9, 7, 7, 4, 10, 9, 10, 8, 10, 10, 10, 5, 9,
					5, 10, 9, 10, 10, 10 },
			new int[] { 8, 8, 7, 9, 10, 6, 5, 11, 5, 9, 3, 12, 7, 12, 7, 7, 9, 12, 10, 6, 8, 8, 12, 7, 9, 11, 12, 6, 6,
					8, 8, 8, 7, 16, 4, 4, 4, 9, 8, 10, 3, 5, 12, 8, 4, 9, 3, 11, 9, 10, 16, 7, 11, 11, 6, 10, 12, 3, 3,
					10, 5, 11, 5 },
			new int[] { 10, 3, 8, 8, 10, 8, 10, 7, 9, 6, 8, 8, 7, 3, 10, 9, 10, 8, 8, 12, 5, 4, 10, 4, 10, 8, 12, 10, 3,
					3, 12, 10, 5, 10, 6, 11, 8, 11, 11, 8, 8, 10, 10, 7, 11, 4, 3, 11, 11, 3, 3, 12, 6, 12, 3, 12, 10,
					4, 12, 11, 11, 11, 12 }, };

	/**
	 * Strips 3 in base game.
	 */
	private static int[][] baseReels3 = {
			new int[] { 16, 7, 7, 16, 10, 12, 16, 9, 7, 16, 11, 4, 16, 12, 7, 16, 7, 12, 16, 7, 10, 16, 10, 9, 16, 11,
					12, 16, 7, 10, 16, 12, 6, 16, 10, 10, 16, 8, 7, 16, 4, 12, 16, 12, 7, 16, 10, 10, 16, 10, 12, 16,
					12, 8, 16, 8, 7, 16, 12, 12, 16, 11, 11 },
			new int[] { 9, 8, 16, 3, 8, 16, 5, 8, 16, 5, 9, 16, 8, 9, 16, 8, 9, 16, 8, 9, 16, 8, 8, 16, 3, 9, 16, 6, 4,
					16, 4, 4, 16, 3, 3, 16, 8, 3, 16, 6, 9, 16, 4, 7, 16, 3, 4, 16, 4, 5, 16, 4, 3, 16, 9, 9, 16, 9, 3,
					16, 4, 3, 16 },
			new int[] { 10, 10, 12, 11, 8, 11, 8, 12, 12, 6, 9, 12, 11, 7, 12, 7, 12, 3, 10, 9, 10, 8, 8, 12, 5, 4, 10,
					4, 10, 8, 12, 10, 9, 12, 11, 10, 5, 10, 6, 11, 8, 8, 3, 7, 8, 8, 11, 11, 11, 6, 7, 6, 11, 16, 12,
					12, 7, 6, 11, 7, 4, 10, 7 },
			new int[] { 9, 12, 16, 11, 7, 16, 5, 5, 16, 5, 11, 16, 3, 7, 16, 7, 11, 16, 11, 6, 16, 6, 12, 16, 12, 12,
					16, 6, 11, 16, 10, 8, 16, 11, 8, 16, 8, 12, 16, 6, 9, 16, 12, 7, 16, 7, 12, 16, 11, 5, 16, 12, 11,
					16, 11, 8, 16, 9, 10, 16, 6, 12, 16 },
			new int[] { 10, 3, 8, 8, 8, 8, 10, 7, 9, 6, 8, 8, 7, 3, 10, 9, 10, 8, 8, 12, 5, 4, 10, 4, 10, 8, 12, 10, 9,
					12, 12, 10, 5, 10, 6, 11, 8, 11, 11, 8, 8, 10, 10, 7, 11, 4, 9, 11, 11, 8, 8, 12, 6, 12, 3, 12, 10,
					4, 16, 11, 11, 11, 12 }, };

	/**
	 * Strips 4 in base game.
	 */
	private static int[][] baseReels4 = {
			new int[] { 7, 7, 4, 12, 3, 12, 6, 9, 7, 7, 9, 4, 10, 12, 7, 7, 7, 5, 8, 9, 9, 8, 10, 9, 7, 11, 11, 12, 9,
					10, 10, 12, 6, 6, 10, 8, 8, 8, 8, 7, 8, 12, 12, 12, 8, 5, 8, 7, 9, 8, 9, 8, 8, 7, 11, 9, 7, 9, 8, 9,
					7, 11, 11 },
			new int[] { 12, 9, 4, 11, 7, 11, 5, 5, 10, 5, 11, 11, 3, 7, 6, 11, 1, 6, 11, 6, 6, 6, 16, 10, 12, 12, 7, 6,
					11, 1, 6, 11, 5, 11, 8, 8, 8, 16, 12, 6, 6, 11, 1, 6, 11, 7, 12, 12, 11, 5, 10, 12, 11, 8, 16, 9, 9,
					9, 10, 7, 11, 11, 10 },
			new int[] { 8, 11, 10, 7, 9, 11, 12, 8, 8, 7, 11, 3, 8, 8, 11, 11, 11, 6, 7, 11, 11, 6, 16, 12, 5, 12, 7,
					10, 11, 10, 8, 8, 8, 6, 6, 6, 7, 10, 9, 10, 10, 6, 10, 10, 9, 7, 7, 4, 10, 9, 10, 8, 10, 16, 10, 5,
					12, 12, 10, 9, 10, 10, 10 },
			new int[] { 8, 11, 7, 9, 10, 6, 11, 11, 11, 9, 3, 12, 6, 11, 1, 6, 11, 12, 10, 6, 8, 12, 12, 6, 11, 1, 6,
					11, 6, 8, 8, 8, 7, 7, 4, 4, 4, 9, 8, 10, 3, 5, 12, 8, 4, 9, 11, 11, 9, 10, 11, 7, 11, 6, 11, 1, 6,
					11, 3, 10, 5, 11, 12 },
			new int[] { 10, 3, 8, 8, 9, 8, 10, 7, 9, 6, 8, 8, 7, 3, 10, 9, 10, 8, 8, 12, 5, 4, 10, 4, 10, 8, 12, 10, 9,
					12, 11, 10, 5, 10, 6, 11, 8, 11, 12, 8, 11, 11, 10, 7, 12, 4, 9, 11, 11, 12, 8, 12, 6, 12, 3, 12,
					10, 4, 10, 11, 11, 11, 12 }, };

	/**
	 * Strips 5 in base game.
	 */
	private static int[][] baseReels5 = {
			new int[] { 3, 7, 10, 10, 7, 10, 3, 3, 7, 10, 10, 7, 10, 3, 3, 7, 10, 10, 7, 10, 3, 3, 7, 10, 10, 7, 10, 3,
					3, 7, 10, 10, 7, 10, 3, 3, 7, 10, 10, 7, 10, 3, 3, 7, 10, 10, 7, 10, 3, 3, 7, 10, 10, 7, 10, 3, 3,
					7, 10, 10, 7, 10, 3 },
			new int[] { 4, 4, 8, 16, 4, 8, 8, 4, 8, 16, 4, 8, 16, 8, 4, 4, 8, 16, 4, 8, 8, 4, 8, 16, 4, 8, 16, 8, 4, 4,
					8, 16, 4, 8, 8, 4, 8, 16, 4, 8, 16, 8, 4, 4, 8, 16, 4, 8, 8, 4, 8, 16, 4, 8, 16, 8, 4, 8, 16, 4, 8,
					16, 8 },
			new int[] { 5, 5, 7, 10, 7, 10, 7, 10, 10, 5, 7, 10, 5, 7, 5, 5, 7, 10, 7, 10, 7, 10, 10, 5, 7, 10, 5, 7, 5,
					5, 7, 10, 7, 10, 7, 10, 10, 5, 7, 10, 5, 7, 5, 5, 7, 10, 7, 10, 7, 10, 10, 5, 7, 10, 5, 7, 10, 10,
					5, 7, 10, 5, 7 },
			new int[] { 9, 6, 9, 16, 6, 6, 6, 9, 16, 9, 9, 6, 9, 16, 9, 6, 9, 16, 6, 6, 6, 9, 16, 9, 9, 6, 9, 16, 9, 6,
					9, 16, 6, 6, 6, 9, 16, 9, 9, 6, 9, 16, 9, 6, 9, 16, 6, 6, 6, 9, 16, 9, 9, 6, 16, 9, 9, 16, 9, 9, 6,
					9, 16 },
			new int[] { 12, 9, 8, 12, 9, 8, 9, 8, 12, 8, 8, 9, 8, 8, 12, 9, 8, 12, 9, 8, 9, 8, 12, 8, 8, 9, 8, 8, 12, 9,
					8, 12, 9, 8, 9, 8, 12, 8, 8, 9, 8, 8, 12, 9, 8, 12, 9, 8, 9, 8, 12, 8, 8, 9, 8, 8, 8, 12, 8, 8, 9,
					8, 8 }, };

	/**
	 * Strips 1 in free spins.
	 */
	private static int[][] freeReels1 = {
			new int[] { 3, 7, 4, 11, 11, 12, 6, 11, 7, 3, 9, 4, 10, 12, 16, 7, 5, 9, 8, 1, 9, 8, 10, 9, 7, 16, 11, 7, 9,
					10, 10, 12, 6, 6, 10, 16, 8, 8, 8, 7, 4, 12, 12, 12, 6, 9, 8, 1, 9, 8, 9, 8, 4, 7, 11, 4, 7, 9, 8,
					11, 7, 16, 11 },
			new int[] { 12, 9, 4, 11, 7, 4, 11, 9, 10, 5, 11, 11, 3, 7, 7, 8, 3, 11, 3, 6, 6, 6, 9, 8, 1, 9, 8, 6, 6, 7,
					4, 11, 6, 11, 8, 8, 8, 8, 12, 6, 9, 4, 12, 7, 16, 7, 12, 12, 11, 9, 10, 12, 11, 8, 4, 9, 9, 9, 10,
					7, 4, 11, 10 },
			new int[] { 8, 11, 10, 7, 9, 11, 12, 8, 8, 7, 11, 3, 8, 8, 11, 16, 11, 6, 7, 4, 11, 6, 16, 12, 5, 12, 7, 4,
					10, 10, 8, 8, 8, 6, 6, 6, 7, 10, 9, 10, 10, 6, 10, 10, 9, 7, 16, 4, 10, 9, 10, 8, 10, 11, 10, 5, 9,
					5, 4, 9, 10, 10, 10 },
			new int[] { 8, 8, 7, 9, 10, 6, 11, 11, 11, 9, 3, 12, 7, 12, 7, 16, 9, 4, 10, 6, 8, 8, 4, 7, 9, 16, 12, 6, 6,
					8, 8, 8, 7, 16, 4, 8, 10, 9, 8, 10, 3, 5, 12, 8, 4, 9, 11, 4, 9, 10, 8, 7, 11, 11, 6, 10, 12, 16, 3,
					10, 10, 11, 12 },
			new int[] { 10, 3, 16, 8, 11, 8, 10, 7, 9, 6, 8, 8, 7, 3, 10, 9, 4, 8, 8, 12, 5, 4, 10, 8, 10, 4, 12, 16, 9,
					12, 8, 10, 5, 10, 6, 11, 8, 16, 8, 8, 8, 5, 5, 7, 11, 4, 9, 11, 11, 16, 8, 12, 6, 12, 3, 7, 5, 4, 8,
					11, 11, 11, 12 }, };

	/**
	 * Strips 2 in free spins.
	 */
	private static int[][] freeReels2 = {
			new int[] { 3, 7, 4, 16, 11, 12, 6, 11, 7, 7, 9, 4, 10, 12, 7, 7, 5, 9, 8, 1, 9, 8, 10, 9, 7, 11, 11, 7, 9,
					10, 10, 12, 6, 6, 10, 10, 8, 8, 8, 7, 4, 12, 12, 12, 6, 9, 8, 1, 9, 8, 9, 8, 4, 7, 11, 9, 7, 9, 8,
					11, 7, 11, 11 },
			new int[] { 12, 9, 4, 11, 7, 4, 16, 9, 10, 5, 11, 11, 3, 7, 7, 8, 16, 11, 11, 6, 6, 6, 8, 10, 3, 12, 7, 6,
					11, 10, 10, 10, 9, 11, 8, 8, 8, 9, 12, 6, 9, 4, 12, 7, 7, 7, 12, 16, 11, 9, 10, 12, 11, 8, 9, 9, 9,
					9, 10, 7, 4, 11, 10 },
			new int[] { 8, 11, 10, 7, 9, 11, 12, 8, 8, 7, 11, 3, 8, 8, 11, 11, 11, 6, 7, 4, 16, 6, 8, 12, 5, 12, 7, 4,
					10, 16, 8, 8, 8, 6, 6, 6, 3, 10, 9, 10, 16, 6, 10, 10, 9, 7, 7, 4, 10, 9, 10, 8, 9, 9, 10, 5, 9, 5,
					4, 9, 10, 10, 10 },
			new int[] { 8, 8, 7, 9, 10, 6, 11, 11, 11, 9, 3, 12, 7, 12, 16, 7, 9, 4, 10, 6, 8, 8, 4, 7, 9, 11, 12, 6, 6,
					8, 8, 8, 7, 16, 4, 8, 10, 9, 8, 10, 3, 5, 12, 8, 4, 9, 16, 4, 9, 10, 9, 7, 9, 9, 6, 10, 12, 11, 3,
					10, 16, 11, 12 },
			new int[] { 10, 3, 8, 8, 16, 8, 10, 7, 9, 6, 8, 8, 7, 3, 10, 9, 4, 8, 8, 16, 5, 4, 10, 8, 10, 8, 3, 10, 9,
					12, 9, 10, 16, 10, 6, 11, 8, 11, 5, 8, 8, 5, 5, 7, 11, 8, 9, 11, 11, 8, 8, 12, 6, 12, 3, 12, 5, 4,
					9, 11, 11, 11, 12 }, };

	/**
	 * Strips 3 in free spins.
	 */
	private static int[][] freeReels3 = {
			new int[] { 3, 7, 4, 6, 11, 12, 6, 11, 7, 7, 9, 4, 10, 12, 7, 7, 5, 9, 8, 1, 9, 8, 10, 9, 7, 11, 11, 7, 9,
					10, 10, 12, 6, 6, 10, 6, 8, 8, 8, 7, 4, 12, 12, 12, 6, 9, 8, 1, 9, 8, 9, 8, 4, 7, 11, 6, 7, 9, 8,
					11, 7, 11, 11 },
			new int[] { 12, 9, 4, 11, 7, 4, 11, 9, 10, 5, 11, 11, 3, 7, 7, 8, 11, 11, 11, 6, 6, 6, 6, 10, 12, 12, 7, 6,
					11, 10, 10, 10, 9, 11, 8, 8, 8, 6, 12, 6, 9, 4, 12, 7, 7, 7, 12, 12, 11, 9, 10, 12, 11, 8, 6, 9, 9,
					9, 10, 7, 4, 11, 10 },
			new int[] { 8, 11, 10, 7, 9, 11, 12, 8, 8, 7, 11, 3, 8, 8, 11, 11, 11, 6, 7, 4, 11, 6, 6, 12, 5, 12, 7, 4,
					10, 10, 8, 8, 8, 6, 6, 6, 7, 10, 9, 10, 10, 6, 10, 10, 9, 7, 7, 4, 10, 9, 10, 8, 10, 6, 10, 5, 9, 5,
					4, 9, 10, 10, 10 },
			new int[] { 8, 8, 7, 9, 10, 6, 11, 11, 11, 9, 3, 12, 7, 12, 7, 7, 9, 4, 10, 6, 16, 8, 4, 7, 9, 11, 12, 6, 6,
					8, 16, 8, 7, 6, 4, 8, 10, 9, 8, 10, 3, 5, 12, 8, 4, 9, 11, 4, 9, 10, 6, 7, 11, 11, 6, 10, 16, 11, 3,
					10, 10, 11, 12 },
			new int[] { 10, 3, 8, 8, 6, 8, 10, 7, 9, 6, 8, 8, 7, 3, 10, 9, 4, 8, 8, 12, 5, 4, 10, 16, 10, 4, 12, 10, 9,
					12, 6, 10, 5, 10, 6, 11, 8, 11, 6, 8, 8, 5, 5, 7, 11, 4, 9, 11, 11, 16, 8, 12, 6, 12, 3, 12, 5, 4,
					6, 11, 11, 11, 12 }, };

	/**
	 * Wild expansion in base game after 1 wild.
	 */
	private static int[][] baseReels1Wilds1 = { new int[] { 0, 0, 0 }, new int[] { 2, 2, 2 }, new int[] { 2, 2, 2 },
			new int[] { 2, 10, 2 }, new int[] { 10, 10, 10 }, };

	/**
	 * Wild expansion discrete distribution.
	 */
	private static int[][] baseReels1Wilds2 = { new int[] { 2, 2, 2 }, new int[] { 2, 2, 2 }, new int[] { 2, 2, 2 },
			new int[] { 2, 10, 2 }, new int[] { 10, 10, 10 }, };

	/**
	 * Wild expansion discrete distribution.
	 */
	private static int[][] baseReels2Wilds1 = { new int[] { 2, 4, 5 }, new int[] { 4, 3, 4 }, new int[] { 5, 5, 5 },
			new int[] { 6, 3, 4 }, new int[] { 4, 6, 2 }, };

	/**
	 * Wild expansion discrete distribution.
	 */
	private static int[][] baseReels2Wilds2 = { new int[] { 2, 4, 5 }, new int[] { 4, 3, 4 }, new int[] { 5, 5, 5 },
			new int[] { 6, 3, 4 }, new int[] { 4, 6, 2 }, };

	/**
	 * Wild expansion discrete distribution.
	 */
	private static int[][] baseReels3Wilds1 = { new int[] { 2, 2, 4 }, new int[] { 2, 2, 2 }, new int[] { 2, 3, 2 },
			new int[] { 2, 3, 2 }, new int[] { 2, 3, 2 }, };

	/**
	 * Wild expansion discrete distribution.
	 */
	private static int[][] baseReels3Wilds2 = { new int[] { 3, 3, 2 }, new int[] { 3, 2, 3 }, new int[] { 3, 3, 2 },
			new int[] { 2, 3, 2 }, new int[] { 3, 2, 3 }, };

	/**
	 * Wild expansion discrete distribution.
	 */
	private static int[][] baseReels4Wilds1 = { new int[] { 2, 4, 5 }, new int[] { 15, 15, 15 },
			new int[] { 20, 20, 20 }, new int[] { 6, 3, 4 }, new int[] { 4, 6, 2 }, };

	/**
	 * Wild expansion discrete distribution.
	 */
	private static int[][] baseReels4Wilds2 = { new int[] { 2, 4, 5 }, new int[] { 15, 15, 15 },
			new int[] { 20, 20, 20 }, new int[] { 6, 3, 4 }, new int[] { 4, 6, 2 }, };

	/**
	 * Wild expansion discrete distribution.
	 */
	private static int[][] baseReels5Wilds1 = { new int[] { 0, 0, 0 }, new int[] { 0, 0, 0 }, new int[] { 0, 0, 0 },
			new int[] { 0, 0, 0 }, new int[] { 0, 0, 0 }, };

	/**
	 * Wild expansion discrete distribution.
	 */
	private static int[][] baseReels5Wilds2 = { new int[] { 0, 0, 0 }, new int[] { 0, 0, 0 }, new int[] { 0, 0, 0 },
			new int[] { 0, 0, 0 }, new int[] { 0, 0, 0 }, };

	/**
	 * Wild expansion discrete distribution.
	 */
	private static int[][] freeReels1Wilds1 = { new int[] { 2, 4, 5 }, new int[] { 10, 10, 10 }, new int[] { 5, 5, 5 },
			new int[] { 6, 6, 6 }, new int[] { 4, 6, 2 }, };

	/**
	 * Wild expansion discrete distribution.
	 */
	private static int[][] freeReels1Wilds2 = { new int[] { 2, 4, 5 }, new int[] { 5, 5, 5 }, new int[] { 5, 5, 5 },
			new int[] { 4, 3, 4 }, new int[] { 4, 6, 2 }, };

	/**
	 * Wild expansion discrete distribution.
	 */
	private static int[][] freeReels2Wilds1 = { new int[] { 2, 4, 5 }, new int[] { 4, 3, 4 }, new int[] { 5, 5, 5 },
			new int[] { 5, 5, 5 }, new int[] { 4, 6, 2 }, };

	/**
	 * Wild expansion discrete distribution.
	 */
	private static int[][] freeReels2Wilds2 = { new int[] { 2, 4, 5 }, new int[] { 4, 3, 4 }, new int[] { 5, 5, 5 },
			new int[] { 5, 5, 5 }, new int[] { 4, 6, 2 }, };

	/**
	 * Wild expansion discrete distribution.
	 */
	private static int[][] freeReels3Wilds1 = { new int[] { 2, 4, 5 }, new int[] { 20, 20, 20 }, new int[] { 5, 5, 5 },
			new int[] { 6, 3, 4 }, new int[] { 4, 6, 2 }, };

	/**
	 * Wild expansion discrete distribution.
	 */
	private static int[][] freeReels3Wilds2 = { new int[] { 2, 4, 5 }, new int[] { 20, 20, 20 }, new int[] { 5, 5, 5 },
			new int[] { 6, 3, 4 }, new int[] { 4, 6, 2 }, };

	/**
	 * Free spins multipliers discrete distribution.
	 */
	private static int[] freeMultiplierDistribution = { 2, 3, 4, 5, 6, 7, 8, 2, 3, 4, 5, 6, 7, 8, 2, 3, 4, 5, 6, 7, 8,
			2, 3, 4, 5, 6, 7, 8, 2, 3, 4, 5, 6, 7, 8, 2, 3, 4, 5, 6, 7, 8, 2, 3, 4, 5, 6, 7, 8, 2, 3, 4, 5, 6, 7, 8, 2,
			3, 4, 5, 6, 7, 8, 2, 3, 4, 5, 6, 7, 8, 2, 3, 4, 5, 6, 7, 8, 2, 3, 4, 5, 6, 7, 8, 2, 3, 4, 5, 6, 7, 8, 2, 3,
			4, 5, 6, 7, 8, 2, 3, 4, 5, 6, 7, 8, 2, 3, 4, 5, 6, 7, 2, 3, 4, 5, 6, 7, 2, 3, 4, 5, 6, 7, 2, 3, 4, 5, 6, 7,
			2, 3, 4, 5, 6, 7, 2, 3, 4, 5, 6, 2, 3, 4, 5, 6, 2, 3, 4, 5, 6, 2, 3, 4, 5, 6, 2, 3, 4, 5, 6, 2, 3, 4, 5, 6,
			2, 3, 4, 5, 6, 2, 3, 4, 5, 6, 2, 3, 4, 5, 6, 2, 3, 4, 5, 6, 2, 3, 4, 5, 2, 3, 4, 5, 2, 3, 4, 5, 2, 3, 4, 5,
			2, 3, 4, 5, 2, 3, 4, 5, 2, 3, 4, 5, 2, 3, 4, 5, 2, 3, 4, 5, 2, 3, 4, 5, 2, 3, 4, 5, 2, 3, 4, 5, 2, 3, 4, 5,
			2, 3, 4, 5, 2, 3, 4, 5, 2, 3, 4, 5, 2, 3, 4, 5, 2, 3, 4, 5, 2, 3, 4, 5, 2, 3, 4, 5, 2, 3, 4, 2, 3, 4, 2, 3,
			4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3,
			4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3,
			4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3,
			4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 3, 3,
			3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
			3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 };

	/**
	 * Base game scatter discrete distribution.
	 */
	private static int[] baseScatterDistritution = { 10, 12, 14, 15, 17, 20, 22, 25, 30, 40, 10, 12, 14, 15, 17, 20, 22,
			25, 30, 40, 10, 12, 14, 15, 17, 20, 25, 30, 40, 10, 12, 14, 15, 17, 20, 25, 30, 40, 10, 12, 14, 15, 17, 20,
			25, 30, 40, 10, 12, 14, 15, 17, 20, 25, 30, 40, 10, 12, 14, 15, 17, 20, 25, 30, 40, 10, 12, 14, 15, 17, 20,
			25, 30, 40, 10, 12, 14, 15, 17, 20, 25, 30, 40, 10, 12, 14, 15, 17, 20, 25, 30, 40, 12, 15, 17, 20, 25, 12,
			15, 17, 20, 25, 12, 15, 17, 20, 25, 12, 15, 17, 20, 25, 12, 15, 17, 20, 25, 12, 15, 17, 20, 25, 12, 15, 17,
			20, 25, 12, 15, 17, 20, 25, 12, 15, 17, 20, 25, 12, 15, 17, 20, 25, 15, 17, 20, 25, 15, 17, 20, 25, 15, 17,
			20, 25, 15, 17, 20, 25, 15, 17, 20, 25, 15, 20, 25, 15, 20, 25, 15, 20, 25, 15, 20, 25, 15, 20, 25, 20, 25,
			20, 25, 20, 25, 20, 25, 20, 25, 20, 25, 20, 25, 20, 25, 20, 25, 20, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25,
			25 };

	/**
	 * Free spins scatter discrete distribution.
	 */
	private static int[] free1ScatterDistritution = { 10, 12, 14, 15, 17, 20, 22, 25, 30, 40, 10, 12, 14, 15, 17, 20,
			22, 25, 30, 40, 10, 12, 14, 15, 17, 20, 25, 30, 40, 10, 12, 14, 15, 17, 20, 25, 30, 40, 10, 12, 14, 15, 17,
			20, 25, 30, 40, 10, 12, 14, 15, 17, 20, 25, 30, 40, 10, 12, 14, 15, 17, 20, 25, 30, 40, 10, 12, 14, 15, 17,
			20, 25, 30, 40, 10, 12, 14, 15, 17, 20, 25, 30, 40, 10, 12, 14, 15, 17, 20, 25, 30, 40, 12, 15, 17, 20, 25,
			12, 15, 17, 20, 25, 12, 15, 17, 20, 25, 12, 15, 17, 20, 25, 12, 15, 17, 20, 25, 12, 15, 17, 20, 25, 12, 15,
			17, 20, 25, 12, 15, 17, 20, 25, 12, 15, 17, 20, 25, 12, 15, 17, 20, 25, 15, 17, 20, 25, 15, 17, 20, 25, 15,
			17, 20, 25, 15, 17, 20, 25, 15, 17, 20, 25, 15, 20, 25, 15, 20, 25, 15, 20, 25, 15, 20, 25, 15, 20, 25, 20,
			25, 20, 25, 20, 25, 20, 25, 20, 25, 20, 25, 20, 25, 20, 25, 20, 25, 20, 25, 25, 25, 25, 25, 25, 25, 25, 25,
			25, 25 };

	/**
	 * Free spins scatter discrete distribution.
	 */
	private static int[] free2ScatterDistritution = { 10, 12, 14, 15, 17, 20, 22, 25, 30, 40, 10, 12, 14, 15, 17, 20,
			22, 25, 30, 40, 10, 12, 14, 15, 17, 20, 25, 30, 40, 10, 12, 14, 15, 17, 20, 25, 30, 40, 10, 12, 14, 15, 17,
			20, 25, 30, 40, 10, 12, 14, 15, 17, 20, 25, 30, 40, 10, 12, 14, 15, 17, 20, 25, 30, 40, 10, 12, 14, 15, 17,
			20, 25, 30, 40, 10, 12, 14, 15, 17, 20, 25, 30, 40, 10, 12, 14, 15, 17, 20, 25, 30, 40, 12, 15, 17, 20, 25,
			12, 15, 17, 20, 25, 12, 15, 17, 20, 25, 12, 15, 17, 20, 25, 12, 15, 17, 20, 25, 12, 15, 17, 20, 25, 12, 15,
			17, 20, 25, 12, 15, 17, 20, 25, 12, 15, 17, 20, 25, 12, 15, 17, 20, 25, 15, 17, 20, 25, 15, 17, 20, 25, 15,
			17, 20, 25, 15, 17, 20, 25, 15, 17, 20, 25, 15, 20, 25, 15, 20, 25, 15, 20, 25, 15, 20, 25, 15, 20, 25, 20,
			25, 20, 25, 20, 25, 20, 25, 20, 25, 20, 25, 20, 25, 20, 25, 20, 25, 20, 25, 25, 25, 25, 25, 25, 25, 25, 25,
			25, 25 };

	/**
	 * Free spins scatter discrete distribution.
	 */
	private static int[] free3ScatterDistritution = { 10, 12, 14, 15, 17, 20, 22, 25, 30, 40, 10, 12, 14, 15, 17, 20,
			22, 25, 30, 40, 10, 12, 14, 15, 17, 20, 25, 30, 40, 10, 12, 14, 15, 17, 20, 25, 30, 40, 10, 12, 14, 15, 17,
			20, 25, 30, 40, 10, 12, 14, 15, 17, 20, 25, 30, 40, 10, 12, 14, 15, 17, 20, 25, 30, 40, 10, 12, 14, 15, 17,
			20, 25, 30, 40, 10, 12, 14, 15, 17, 20, 25, 30, 40, 10, 12, 14, 15, 17, 20, 25, 30, 40, 12, 15, 17, 20, 25,
			12, 15, 17, 20, 25, 12, 15, 17, 20, 25, 12, 15, 17, 20, 25, 12, 15, 17, 20, 25, 12, 15, 17, 20, 25, 12, 15,
			17, 20, 25, 12, 15, 17, 20, 25, 12, 15, 17, 20, 25, 12, 15, 17, 20, 25, 15, 17, 20, 25, 15, 17, 20, 25, 15,
			17, 20, 25, 15, 17, 20, 25, 15, 17, 20, 25, 15, 20, 25, 15, 20, 25, 15, 20, 25, 15, 20, 25, 15, 20, 25, 20,
			25, 20, 25, 20, 25, 20, 25, 20, 25, 20, 25, 20, 25, 20, 25, 20, 25, 20, 25, 25, 25, 25, 25, 25, 25, 25, 25,
			25, 25 };

	/**
	 * Base game strips discrete distribution.
	 */
	private static int[] baseStripsDistribution = { 1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 1, 2, 3,
			4, 5, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 1, 2, 3, 4,
			5, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 1, 2, 4, 5, 1,
			2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1,
			2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1,
			2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1,
			2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1,
			2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1,
			2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1,
			2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1,
			2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1,
			2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1,
			2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1,
			2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1,
			2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1,
			2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1,
			2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1,
			2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1,
			2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1,
			2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1,
			2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1,
			2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1,
			2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1, 2, 4, 5, 1,
			2, 4, 5, 1, 2, 4, 5, 2, 4, 5, 2, 4, 5, 2, 4, 5, 2, 4, 5, 2, 4, 5, 2, 4, 5, 2, 4, 5, 2, 4, 5, 2, 4, 5, 2, 4,
			5, 2, 4, 5, 2, 4, 5, 2, 4, 5, 2, 4, 5, 2, 4, 5, 2, 4, 5, 2, 4, 5, 2, 4, 5, 2, 4, 5, 2, 4, 5, 2, 4, 5, 2, 4,
			5, 2, 4, 5, 2, 4, 5, 2, 4, 5, 4, 5, 4, 5, 4, 5, 4, 5, 4, 5, 4, 5, 4, 5, 4, 5, 4, 5, 4, 5, 4, 5, 4, 5, 4, 5,
			4, 5, 4, 5, 4, 5, 4, 5, 4, 5, 4, 5, 4, 5, 4, 5, 4, 5, 4, 5, 4, 5, 4, 5, 4, 5, 4, 5, 4, 5, 4, 5, 4, 5, 4, 5,
			4, 5, 4, 5, 4, 5, 4, 5, 4, 5, 4, 5, 4, 5, 4, 5, 4, 5, 4, 5, 4, 5, 4, 5, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
			5, 5, 5, 5, 5 };

	/**
	 * All scatters discrete distribution together.
	 */
	private static int[][] scatterDistritutions = { baseScatterDistritution, free1ScatterDistritution,
			free2ScatterDistritution, free3ScatterDistritution };

	/**
	 * Base reels strips.
	 */
	private static int[][][] baseReels = { baseReels1, baseReels2, baseReels3, baseReels4, baseReels5 };

	/**
	 * Base reels strips.
	 */
	private static int[][][] freeReels = { freeReels1, freeReels2, freeReels3 };

	/**
	 * Reels strips 1.
	 */
	private static int[][][] reels1 = { baseReels1, freeReels1, freeReels2, freeReels3 };

	/**
	 * Reels strips 2.
	 */
	private static int[][][] reels2 = { baseReels2, freeReels1, freeReels2, freeReels3 };

	/**
	 * Reels strips 3.
	 */
	private static int[][][] reels3 = { baseReels3, freeReels1, freeReels2, freeReels3 };

	/**
	 * Reels strips 4.
	 */
	private static int[][][] reels4 = { baseReels4, freeReels1, freeReels2, freeReels3 };

	/**
	 * Reels strips 5.
	 */
	private static int[][][] reels5 = { baseReels5, freeReels1, freeReels2, freeReels3 };

	/**
	 * All strips together.
	 */
	private static int[][][][] reelsSets = { reels1, reels2, reels3, reels4, reels5 };

	/**
	 * Wild symbols distributions together.
	 */
	private static int[][][][] wildsSets = { new int[][][] { new int[][] {}, baseReels1Wilds1, baseReels1Wilds2 },
			new int[][][] { new int[][] {}, baseReels2Wilds1, baseReels2Wilds2 },
			new int[][][] { new int[][] {}, baseReels3Wilds1, baseReels3Wilds2 },
			new int[][][] { new int[][] {}, baseReels4Wilds1, baseReels4Wilds2 },
			new int[][][] { new int[][] {}, baseReels5Wilds1, baseReels5Wilds2 },
			new int[][][] { new int[][] {}, freeReels1Wilds1, freeReels1Wilds2 },
			new int[][][] { new int[][] {}, freeReels2Wilds1, freeReels2Wilds2 },
			new int[][][] { new int[][] {}, freeReels3Wilds1, freeReels3Wilds2 }, };

	/**
	 * Current reels reference.
	 */
	private static int[][][] reels = {};

	/**
	 * Current wilds distributions reference.
	 */
	private static int[][][] wilds = {};

	/**
	 * Current visible symbols on the screen.
	 */
	private static int[][] view = { new int[] { -1, -1, -1 }, new int[] { -1, -1, -1 }, new int[] { -1, -1, -1 },
			new int[] { -1, -1, -1 }, new int[] { -1, -1, -1 } };

	/**
	 * Current scatter multiplier.
	 */
	private static int scatterMultiplier = 1;

	/**
	 * Total bet in single base game spin.
	 */
	private static int totalBet = lines.length;

	/**
	 * List of free spins to be played.
	 */
	private static List<FreeSpin> freeGamesList = new ArrayList<FreeSpin>();

	/**
	 * Current free spins multiplier.
	 */
	private static int freeGamesMultiplier = 1;

	/**
	 * Total amount of won money.
	 */
	private static long wonMoney = 0L;

	/**
	 * Total amount of lost money.
	 */
	private static long lostMoney = 0L;

	/**
	 * Total amount of won money in base game.
	 */
	private static long baseMoney = 0L;

	/**
	 * Total amount of won money in free spins.
	 */
	private static long freeMoney = 0L;

	/**
	 * Max amount of won money in base game.
	 */
	private static long baseMaxWin = 0L;

	/**
	 * Max amount of won money in free spins.
	 */
	private static long freeMaxWin = 0L;

	/**
	 * Total number of base games played.
	 */
	private static long totalNumberOfGames = 0L;

	/**
	 * Total number of free spins played.
	 */
	private static long totalNumberOfFreeGames = 0L;

	/**
	 * Total number of free spins started.
	 */
	private static long totalNumberOfFreeGameStarts = 0L;

	/**
	 * Hit rate of wins in base game.
	 */
	private static long baseGameHitRate = 0L;

	/**
	 * Hit rate of wins in free spins.
	 */
	private static long freeGamesHitRate = 0L;

	/**
	 * Verbose output flag.
	 */
	private static boolean verboseOutput = false;

	/**
	 * Free spins flag.
	 */
	private static boolean freeOff = false;

	/**
	 * Wild substitution flag.
	 */
	private static boolean wildsOff = false;

	/**
	 * Wild expansion flag.
	 */
	private static boolean wildExpandOff = false;

	/**
	 * Symbols win hit rate in base game.
	 */
	private static long[][] baseSymbolMoney = { new long[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			new long[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, new long[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			new long[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, new long[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			new long[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };

	/**
	 * Symbols hit rate in base game.
	 */
	private static long[][] baseGameSymbolsHitRate = { new long[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			new long[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, new long[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			new long[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, new long[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			new long[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };

	/**
	 * Static constructor for discrete distributions shuffling.
	 */
	static {
		for (int last = freeMultiplierDistribution.length - 1, r = -1, swap = -1; last > 0; last--) {
			r = prng.nextInt(last + 1);
			swap = freeMultiplierDistribution[last];
			freeMultiplierDistribution[last] = freeMultiplierDistribution[r];
			freeMultiplierDistribution[r] = swap;
		}
		for (int last = baseScatterDistritution.length - 1, r = -1, swap = -1; last > 0; last--) {
			r = prng.nextInt(last + 1);
			swap = baseScatterDistritution[last];
			baseScatterDistritution[last] = baseScatterDistritution[r];
			baseScatterDistritution[r] = swap;
		}
		for (int last = free1ScatterDistritution.length - 1, r = -1, swap = -1; last > 0; last--) {
			r = prng.nextInt(last + 1);
			swap = free1ScatterDistritution[last];
			free1ScatterDistritution[last] = free1ScatterDistritution[r];
			free1ScatterDistritution[r] = swap;
		}
		for (int last = free2ScatterDistritution.length - 1, r = -1, swap = -1; last > 0; last--) {
			r = prng.nextInt(last + 1);
			swap = free2ScatterDistritution[last];
			free2ScatterDistritution[last] = free2ScatterDistritution[r];
			free2ScatterDistritution[r] = swap;
		}
		for (int last = free3ScatterDistritution.length - 1, r = -1, swap = -1; last > 0; last--) {
			r = prng.nextInt(last + 1);
			swap = free3ScatterDistritution[last];
			free3ScatterDistritution[last] = free3ScatterDistritution[r];
			free3ScatterDistritution[r] = swap;
		}
	}

	/**
	 * Single reels spin to fill view with symbols.
	 *
	 * @param reels Reels strips.
	 */
	private static void spin(int[][] reels) {
		for (int i = 0; i < view.length && i < reels.length; i++) {
			int r = prng.nextInt(reels[i].length);
			int u = r - 1;
			int d = r + 1;

			if (u < 0) {
				u = reels[i].length - 1;
			}

			if (d >= reels[i].length) {
				d = 0;
			}

			view[i][0] = reels[i][u];
			view[i][1] = reels[i][r];
			view[i][2] = reels[i][d];
		}
	}

	/**
	 * Calculate win in particular line.
	 *
	 * @param line Single line.
	 *
	 * @return Calculated win.
	 */
	private static int lineWin(int[] line) {
		/*
		 * Keep first symbol in the line.
		 */
		int symbol = line[0];

		/*
		 * Wild wymbol passing to find first regular symbol.
		 */
		for (int i = 0; i < line.length; i++) {
			/*
			 * First no wild symbol found.
			 */
			if (symbol != 1 && symbol != 2) {
				break;
			}

			symbol = line[i];
		}

		/*
		 * Wild symbol substitution. Other wild are artificial they are not part of the
		 * pay table.
		 */
		for (int i = 0; i < line.length && wildsOff == false; i++) {
			if (line[i] == 1 || line[i] == 2) {
				/*
				 * Substitute wild with regular symbol.
				 */
				line[i] = symbol;
			}
		}

		/*
		 * Count symbols in winning line.
		 */
		int number = 0;
		for (int i = 0; i < line.length; i++) {
			if (line[i] == symbol) {
				number++;
			} else {
				break;
			}
		}

		/*
		 * Clear unused symbols.
		 */
		for (int i = number; i < line.length; i++) {
			line[i] = -1;
		}

		int win = paytable[number][symbol];

		/*
		 * There is multiplier in free games mode.
		 */
		if (freeGamesList.size() > 0) {
			win *= freeGamesMultiplier;
		}

		if (win > 0 && freeGamesList.size() == 0) {
			baseSymbolMoney[number][symbol] += win;
			baseGameSymbolsHitRate[number][symbol]++;
		}

		return (win);
	}

	/**
	 * Calculate win in all possible lines.
	 *
	 * @param view Symbols visible in screen view.
	 *
	 * @return Calculated win.
	 */
	private static int linesWin(int[][] view) {
		int win = 0;

		/*
		 * Check wins in all possible lines.
		 */
		for (int l = 0; l < lines.length; l++) {
			int[] line = { -1, -1, -1, -1, -1 };

			/*
			 * Prepare line for combination check.
			 */
			for (int i = 0; i < line.length; i++) {
				int index = lines[l][i];
				line[i] = view[i][index];
			}

			int result = lineWin(line);

			/*
			 * Accumulate line win.
			 */
			win += result;
		}

		return (win);
	}

	/**
	 * Setup parameters for free spins mode.
	 */
	private static void freeGamesSetup() {
		int numberOfScatters = 0;
		int numberOfWilds = 0;
		for (int i = 0; i < view.length; i++) {
			for (int j = 0; j < view[i].length; j++) {
				if (view[i][j] == 1) {
					numberOfWilds++;
				}
				if (view[i][j] == 16) {
					numberOfScatters++;
				}
			}
		}

		scatterMultiplier = scatterMultipliers[numberOfScatters];

		/*
		 * In base game 3+ scatters turn into free spins.
		 */
		if (numberOfScatters < 3 && freeGamesList.size() == 0) {
			return;
		} else if (numberOfScatters >= 3 && freeGamesList.size() == 0) {
			int freeGamesNumber = baseScatterDistritution[prng.nextInt(baseScatterDistritution.length)];
			freeGamesMultiplier = freeMultiplierDistribution[prng.nextInt(freeMultiplierDistribution.length)];
			for (int i = 0; i < freeGamesNumber; i++) {
				freeGamesList.add(new FreeSpin((i + 1), reels[1], wildsSets[5][numberOfWilds]));
			}
		} else if (numberOfScatters >= 3 && freeGamesList.size() > 0) {
			int next = -1;
			if (freeGamesList.get(freeGamesList.size() - 1).reels == reels[1]) {
				next = 2;
			} else if (freeGamesList.get(freeGamesList.size() - 1).reels == reels[2]) {
				next = 3;
			} else {
				next = 3;
			}

			int distributionIndex = -1;
			if (freeGamesList.get(freeGamesList.size() - 1).reels == reels[1]) {
				distributionIndex = 1;
			} else if (freeGamesList.get(freeGamesList.size() - 1).reels == reels[2]) {
				distributionIndex = 2;
			} else if (freeGamesList.get(freeGamesList.size() - 1).reels == reels[3]) {
				distributionIndex = 3;
			}

			int freeGamesNumber = scatterDistritutions[distributionIndex][prng
					.nextInt(scatterDistritutions[distributionIndex].length)];
			for (int i = 0; i < freeGamesNumber; i++) {
				freeGamesList.add(new FreeSpin((i + 1), reels[next], wildsSets[4 + next][numberOfWilds]));
			}
		}
	}

	/**
	 * Expand wild.
	 */
	private static void expandWild() {
		if (wildExpandOff == true) {
			return;
		}

		int numberOfWilds = 0;
		for (int i = 0; i < view.length; i++) {
			for (int j = 0; j < view[i].length; j++) {
				if (view[i][j] == 1) {
					numberOfWilds++;
				}
			}
		}

		if (numberOfWilds == 1) {
			int[][] transforms = new int[][] {
					new int[] { 1 + prng.nextInt(100), 1 + prng.nextInt(100), 1 + prng.nextInt(100) },
					new int[] { 1 + prng.nextInt(100), 1 + prng.nextInt(100), 1 + prng.nextInt(100) },
					new int[] { 1 + prng.nextInt(100), 1 + prng.nextInt(100), 1 + prng.nextInt(100) },
					new int[] { 1 + prng.nextInt(100), 1 + prng.nextInt(100), 1 + prng.nextInt(100) },
					new int[] { 1 + prng.nextInt(100), 1 + prng.nextInt(100), 1 + prng.nextInt(100) } };
			for (int i = 0; i < transforms.length; i++) {
				for (int j = 0; j < transforms[i].length; j++) {
					if (transforms[i][j] <= wilds[numberOfWilds][i][j] && view[i][j] != 1 && view[i][j] != 16) {
						transforms[i][j] = 1;
					} else {
						transforms[i][j] = 0;
					}
				}
			}
			int sum = 0;
			for (int i = 0; i < transforms.length; i++) {
				for (int j = 0; j < transforms[i].length; j++) {
					sum += transforms[i][j];
				}
			}
			if (sum == 0 && view[4][1] != 1 && view[4][1] != 16) {
				transforms[4][1] = 1;
			}
			for (int j = 0, k = 0; j < transforms[0].length && k < 3; j++) {
				for (int i = 0; i < transforms.length && k < 3; i++) {
					if (transforms[i][j] == 1) {
						view[i][j] = 1;
						k++;
					}
				}
			}
		} else if (numberOfWilds == 2) {
			int[][] transforms = new int[][] {
					new int[] { 1 + prng.nextInt(100), 1 + prng.nextInt(100), 1 + prng.nextInt(100) },
					new int[] { 1 + prng.nextInt(100), 1 + prng.nextInt(100), 1 + prng.nextInt(100) },
					new int[] { 1 + prng.nextInt(100), 1 + prng.nextInt(100), 1 + prng.nextInt(100) },
					new int[] { 1 + prng.nextInt(100), 1 + prng.nextInt(100), 1 + prng.nextInt(100) },
					new int[] { 1 + prng.nextInt(100), 1 + prng.nextInt(100), 1 + prng.nextInt(100) } };
			for (int i = 0; i < transforms.length; i++) {
				for (int j = 0; j < transforms[i].length; j++) {
					if (transforms[i][j] <= wilds[numberOfWilds][i][j] && view[i][j] != 1 && view[i][j] != 16) {
						transforms[i][j] = 1;
					} else {
						transforms[i][j] = 0;
					}
				}
			}
			int sum = 0;
			for (int i = 0; i < transforms.length; i++) {
				for (int j = 0; j < transforms[i].length; j++) {
					sum += transforms[i][j];
				}
			}
			if (sum == 0 && view[4][0] != 1 && view[4][0] != 16) {
				transforms[4][0] = 1;
			}
			if (sum == 0 && view[4][2] != 1 && view[4][2] != 16) {
				transforms[4][2] = 1;
			}
			if (sum == 1 && view[4][1] != 1 && view[4][1] != 16) {
				transforms[4][1] = 1;
			}
			for (int j = 0, k = 0; j < transforms[0].length && k < 2; j++) {
				for (int i = 0; i < transforms.length && k < 2; i++) {
					if (transforms[i][j] == 1) {
						view[i][j] = 1;
						k++;
					}
				}
			}
		}
	}

	/**
	 * Play single free spin game.
	 */
	private static void singleFreeGame() {
		if (freeOff == true) {
			return;
		}

		/*
		 * Spin reels. In re-triggered games from FS1 to FS2 and from FS2 to FS3. FS3
		 * can not re-trigger FS.
		 */
		spin(freeGamesList.get(0).reels);

		freeGamesSetup();

		expandWild();

		/*
		 * Win accumulated by lines.
		 */
		int win = linesWin(view) + (scatterMultiplier * totalBet);

		/*
		 * Add win to the statistics.
		 */
		freeMoney += win;
		wonMoney += win;
		if (freeMaxWin < win) {
			freeMaxWin = win;
		}

		/*
		 * Count base game hit rate.
		 */
		if (win > 0) {
			freeGamesHitRate++;
		}
	}

	/**
	 * Play single base game.
	 */
	private static void singleBaseGame() {
		/*
		 * Select reels according base game strip distribution.
		 */
		int r = baseStripsDistribution[prng.nextInt(baseStripsDistribution.length)] - 1;
		reels = reelsSets[r];
		wilds = wildsSets[r];

		/*
		 * Spin reels.
		 */
		spin(reels[0]);

		freeGamesSetup();

		expandWild();

		/*
		 * Win accumulated by lines.
		 */
		int win = linesWin(view) + (scatterMultiplier * totalBet);

		/*
		 * Add win to the statistics.
		 */
		baseMoney += win;
		wonMoney += win;
		if (baseMaxWin < win) {
			baseMaxWin = win;
		}

		/*
		 * Count base game hit rate.
		 */
		if (win > 0) {
			baseGameHitRate++;
		}

		/*
		 * Count into free spins hit rate.
		 */
		if (freeGamesList.size() > 0) {
			totalNumberOfFreeGameStarts++;
		}

		/*
		 * Play all free games.
		 */
		while (freeGamesList.size() > 0) {
			totalNumberOfFreeGames++;

			singleFreeGame();

			freeGamesList.remove(0);
		}
		freeGamesMultiplier = 1;
		freeGamesList.clear();
	}

	/**
	 * Print help information.
	 */
	private static void printHelp() {
		System.out.println("*******************************************************************************");
		System.out.println("* Thracian Treasure Slot Simulation version 0.9.1                             *");
		System.out.println("* Copyrights (C) 2013-2024 Velbazhd Software LLC                              *");
		System.out.println("*                                                                             *");
		System.out.println("* developed by Todor Balabanov ( todor.balabanov@gmail.com )                  *");
		System.out.println("* Sofia, Bulgaria                                                             *");
		System.out.println("*                                                                             *");
		System.out.println("* This program is free software: you can redistribute it and/or modify        *");
		System.out.println("* it under the terms of the GNU General Public License as published by        *");
		System.out.println("* the Free Software Foundation, either version 3 of the License, or           *");
		System.out.println("* (at your option) any later version.                                         *");
		System.out.println("*                                                                             *");
		System.out.println("* This program is distributed in the hope that it will be useful,             *");
		System.out.println("* but WITHOUT ANY WARRANTY; without even the implied warranty of              *");
		System.out.println("* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the               *");
		System.out.println("* GNU General Public License for more details.                                *");
		System.out.println("*                                                                             *");
		System.out.println("* You should have received a copy of the GNU General Public License           *");
		System.out.println("* along with this program. If not, see <http://www.gnu.org/licenses/>.        *");
		System.out.println("*                                                                             *");
		System.out.println("*******************************************************************************");
		System.out.println("*                                                                             *");
		System.out.println("* -h              Help screen.                                                *");
		System.out.println("* -help           Help screen.                                                *");
		System.out.println("*                                                                             *");
		System.out.println("* -g<number>      Number of games (default 10 000 000).                       *");
		System.out.println("* -p<number>      Progress on each iteration number (default 10 000 000).     *");
		System.out.println("*                                                                             *");
		System.out.println("* -freeoff        Switch off free spins.                                      *");
		System.out.println("* -wildsoff       Switch off wilds.                                           *");
		System.out.println("* -expandoff      Switch off wild expansion.                                  *");
		System.out.println("*                                                                             *");
		System.out.println("* -verify         Print input data structures.                                *");
		System.out.println("*                                                                             *");
		System.out.println("*******************************************************************************");
	}

	/**
	 * Print all simulation input data structures.
	 */
	private static void printDataStructures() {
		System.out.println("Paytable:");
		for (int i = 3; i < paytable.length; i++) {
			System.out.print("\t" + i + " of");
		}
		System.out.println();
		for (int j = 3; j < paytable[0].length; j++) {
			System.out.print("SYM" + j + "\t");
			for (int i = 3; i < paytable.length; i++) {
				System.out.print(paytable[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println();

		System.out.println("Lines:");
		for (int i = 0; i < lines.length; i++) {
			for (int j = 0; j < lines[0].length; j++) {
				System.out.print(lines[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();

		System.out.println("Base Game Reels:");
		for (int k = 0; k < baseReels.length; k++) {
			for (int i = 0; i < baseReels[k].length; i++) {
				for (int j = 0; j < baseReels[0][i].length; j++) {
					if (j % 10 == 0) {
						System.out.println();
					}
					System.out.print("SYM" + baseReels[0][i][j] + " ");
				}
				System.out.println();
			}
			System.out.println();
		}
		System.out.println();

		System.out.println("Base Game Reels:");
		/* Count symbols in reels. */ {
			for (int k = 0; k < baseReels.length; k++) {
				int[][] counters = { new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
						new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
						new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
						new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
						new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };
				for (int i = 0; i < baseReels[k].length; i++) {
					for (int j = 0; j < baseReels[k][i].length; j++) {
						counters[i][baseReels[k][i][j]]++;
					}
				}
				for (int i = 0; i < baseReels[k].length; i++) {
					System.out.print("\tReel " + (i + 1));
				}
				System.out.println();
				for (int j = 0; j < counters[k].length; j++) {
					System.out.print("SYM" + j + "\t");
					for (int i = 0; i < counters.length; i++) {
						System.out.print(counters[i][j] + "\t");
					}
					System.out.println();
				}
				System.out.println("---------------------------------------------");
				System.out.print("Total:\t");
				long combinations = 1L;
				for (int i = 0; i < counters.length; i++) {
					int sum = 0;
					for (int j = 0; j < counters[k].length; j++) {
						sum += counters[i][j];
					}
					System.out.print(sum + "\t");
					if (sum != 0) {
						combinations *= sum;
					}
				}
				System.out.println();
				System.out.println("---------------------------------------------");
				System.out.println("Combinations:\t" + combinations);
			}
			System.out.println();
		}
		System.out.println();
	}

	/**
	 * Print simulation statistics.
	 */
	private static void printStatistics() {
		System.out.println("Won money:\t" + wonMoney);
		System.out.println("Lost money:\t" + lostMoney);
		System.out.println("Total Number of Games:\t" + totalNumberOfGames);
		System.out.println();
		System.out.println("Total RTP:\t" + ((double) wonMoney / (double) lostMoney) + "\t\t"
				+ (100.0D * (double) wonMoney / (double) lostMoney) + "%");
		System.out.println("Base Game RTP:\t" + ((double) baseMoney / (double) lostMoney) + "\t\t"
				+ (100.0D * (double) baseMoney / (double) lostMoney) + "%");
		System.out.println("Free Game RTP:\t" + ((double) freeMoney / (double) lostMoney) + "\t\t"
				+ (100.0D * (double) freeMoney / (double) lostMoney) + "%");
		System.out.println();
		System.out.println("Hit Frequency in Base Game:\t" + ((double) baseGameHitRate / (double) totalNumberOfGames)
				+ "\t\t" + (100.0D * (double) baseGameHitRate / (double) totalNumberOfGames) + "%");
		System.out.println(
				"Hit Frequency into Free Game:\t" + ((double) totalNumberOfFreeGameStarts / (double) totalNumberOfGames)
						+ "\t\t" + (100.0D * (double) totalNumberOfFreeGameStarts / (double) totalNumberOfGames) + "%");
		System.out.println();
		System.out.println("Max Win in Base Game:\t" + baseMaxWin);
		System.out.println("Max Win in Free Game:\t" + freeMaxWin);

		/**/
		System.out.println();
		System.out.println("Base Game Symbols RTP:");
		System.out.print("\t");
		for (int i = 0; i < baseSymbolMoney.length; i++) {
			System.out.print("" + i + "of\t");
		}
		System.out.println();
		for (int j = 0; j < baseSymbolMoney[0].length; j++) {
			System.out.print("SYM" + j + "\t");
			for (int i = 0; i < baseSymbolMoney.length; i++) {
				System.out.print((double) baseSymbolMoney[i][j] / (double) lostMoney + "\t");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println("Base Game Symbols Hit Frequency:");
		System.out.print("\t");
		for (int i = 0; i < baseGameSymbolsHitRate.length; i++) {
			System.out.print("" + i + "of\t");
		}
		System.out.println();
		for (int j = 0; j < baseGameSymbolsHitRate[0].length; j++) {
			System.out.print("SYM" + j + "\t");
			for (int i = 0; i < baseGameSymbolsHitRate.length; i++) {
				System.out.print((double) baseGameSymbolsHitRate[i][j] / (double) totalNumberOfGames + "\t");
			}
			System.out.println();
		}
		/**/
	}

	/**
	 * Print screen view.
	 */
	private static void printView() {
		int max = view[0].length;
		for (int i = 0; i < view.length; i++) {
			if (max < view[i].length) {
				max = view[i].length;
			}
		}

		for (int j = 0; j < max; j++) {
			for (int i = 0; i < view.length && j < view[i].length; i++) {
				if (view[i][j] < 10 && view[i][j] >= 0) {
					System.out.print(" ");
				}
				System.out.print(view[i][j] + " ");
			}

			System.out.println();
		}
	}

	/**
	 * Print simulation execution command.
	 *
	 * @param args Command line arguments list.
	 */
	private static void printExecuteCommand(String[] args) {
		System.out.println("Execute command:");
		System.out.println();
		System.out.print("java Main ");
		for (int i = 0; i < args.length; i++) {
			System.out.print(args[i] + " ");
		}
		System.out.println();
	}

	/**
	 * Main application entry point.
	 * 
	 * java Main -g100m -p10k
	 * 
	 * java Main -verify
	 * 
	 * java Main -help
	 *
	 * @param args Command line arguments list.
	 */
	public static void main(String[] args) {
		printExecuteCommand(args);
		System.out.println();

		long numberOfSimulations = 10000000L;
		long progressPrintOnIteration = 10000000L;

		/*
		 * Parse command line arguments.
		 */
		for (int a = 0; a < args.length; a++) {
			if (args.length > 0 && args[a].contains("-g")) {
				String parameter = args[a].substring(2);

				if (parameter.contains("k")) {
					parameter = parameter.substring(0, parameter.length() - 1);
					parameter += "000";
				}

				if (parameter.contains("m")) {
					parameter = parameter.substring(0, parameter.length() - 1);
					parameter += "000000";
				}

				try {
					numberOfSimulations = Integer.valueOf(parameter);
				} catch (Exception exception) {
				}
			}

			if (args.length > 0 && args[a].contains("-p")) {
				String parameter = args[a].substring(2);

				if (parameter.contains("k")) {
					parameter = parameter.substring(0, parameter.length() - 1);
					parameter += "000";
				}

				if (parameter.contains("m")) {
					parameter = parameter.substring(0, parameter.length() - 1);
					parameter += "000000";
				}

				try {
					progressPrintOnIteration = Integer.valueOf(parameter);
					verboseOutput = true;
				} catch (Exception exception) {
				}
			}

			if (args.length > 0 && args[a].contains("-freeoff")) {
				freeOff = true;
			}

			if (args.length > 0 && args[a].contains("-wildsoff")) {
				wildsOff = true;
			}

			if (args.length > 0 && args[a].contains("-expandoff")) {
				wildExpandOff = true;
			}

			if (args.length > 0 && args[a].contains("-verify")) {
				printDataStructures();
				System.exit(0);
			}

			if (args.length > 0 && args[a].contains("-help")) {
				printHelp();
				System.out.println();
				System.exit(0);
			}

			if (args.length > 0 && args[a].contains("-h")) {
				printHelp();
				System.out.println();
				System.exit(0);
			}
		}

		/*
		 * Simulation main loop.
		 */
		for (long g = 0L; g < numberOfSimulations; g++) {
			if (verboseOutput == true && g == 0) {
				System.out.println("Games\tRTP\tRTP(Base)\tRTP(Free)");
			}

			/*
			 * Print progress report.
			 */
			if (verboseOutput == true && g % progressPrintOnIteration == 0) {
				try {
					System.out.print(g);
					System.out.print("\t");
					System.out.print(String.format("  %6.2f", ((double) wonMoney / (double) lostMoney)));
					System.out.print("\t");
					System.out.print(String.format("  %6.2f", ((double) baseMoney / (double) lostMoney)));
					System.out.print("\t");
					System.out.print(String.format("  %6.2f", ((double) freeMoney / (double) lostMoney)));
				} catch (Exception exception) {
				}
				System.out.println();
			}

			totalNumberOfGames++;

			lostMoney += totalBet;

			singleBaseGame();
		}

		System.out.println("********************************************************************************");
		printStatistics();
		System.out.println("********************************************************************************");
	}
}
