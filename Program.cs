/*******************************************************************************
* Thracian Treasure Slot Simulation version 0.9.0                              *
* Copyrights (C) 2013-2023 Velbazhd Software LLC                               *
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
*******************************************************************************/

using System;
using System.Collections.Generic;

namespace CSharpSimulation
{
	/**
	 * Main application class.
	 */
	class MainClass
	{
		/**
		 * Free spins helper class.
		 * Only keeps information of free spin properties.
		 */
		private class FreeSpin {
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
		private static Random prng = new Random();

		/**
		 * List of symbols names.
		 */
		private static String[] symbols = {
			"",
			"SYM01",
			"",
			"SYM03",
			"SYM04",
			"SYM05",
			"SYM06",
			"SYM07",
			"SYM08",
			"SYM09",
			"SYM10",
			"SYM11",
			"SYM12",
			"",
			"",
			"",
			"SYM16",
		};

		/**
		 * Slot game paytable.
		 */
		private static int[][] paytable = {
			new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			new int[]{0,0,0,30,20,4,0,0,0,0,0,0,0,0,0,0,0},
			new int[]{0,0,0,150,75,50,40,5,4,4,3,3,2,0,0,0,0},
			new int[]{0,0,0,250,100,100,75,50,40,20,6,5,4,0,0,0,0},
			new int[]{0,0,0,500,250,175,150,100,50,30,25,15,10,0,0,0,0},
		};

		/**
		 * Scatter multipliers discret distribution.
		 */
		private static int[] scatterMultipliers = {0, 0, 0, 1, 2, 3};

		/**
		 * Lines combinations.
		 */
		private static int[][] lines = {
			new int[]{1,1,1,1,1},
			new int[]{0,0,0,0,0},
			new int[]{2,2,2,2,2},
			new int[]{0,1,2,1,0},
			new int[]{2,1,0,1,2},
			new int[]{0,0,1,2,2},
			new int[]{2,2,1,0,0},
			new int[]{1,0,1,2,1},
			new int[]{1,2,1,0,1},
			new int[]{0,1,1,1,2},
			new int[]{2,1,1,1,0},
			new int[]{1,0,0,1,2},
			new int[]{1,2,2,1,0},
			new int[]{1,1,0,1,2},
			new int[]{1,1,2,1,0},
			new int[]{0,1,0,1,2},
			new int[]{2,1,2,1,0},
			new int[]{2,1,0,0,1},
			new int[]{0,1,2,2,1},
			new int[]{0,1,1,2,1},
			new int[]{0,1,0,1,0},
			new int[]{2,1,2,1,2},
			new int[]{0,2,0,2,0},
			new int[]{2,0,2,0,2},
			new int[]{0,0,2,0,0},
			new int[]{2,2,0,2,2},
			new int[]{0,2,2,2,0},
			new int[]{2,0,0,0,2},
			new int[]{1,0,2,0,1},
			new int[]{1,2,0,2,1},
			new int[]{0,0,0,1,2},
			new int[]{2,2,2,1,0},
			new int[]{2,0,1,0,2},
			new int[]{0,2,1,2,0},
			new int[]{1,2,1,2,1},
			new int[]{1,0,1,0,1},
			new int[]{0,0,0,2,2},
			new int[]{2,2,2,0,0},
			new int[]{0,1,1,1,0},
			new int[]{2,1,1,1,2},
			new int[]{1,2,2,2,1},
			new int[]{1,0,0,0,1},
			new int[]{0,0,2,2,2},
			new int[]{2,2,0,0,0},
			new int[]{1,1,0,1,1},
			new int[]{1,1,2,1,1},
			new int[]{2,0,1,0,1},
			new int[]{0,2,1,2,1},
			new int[]{1,0,2,2,0},
			new int[]{1,2,0,0,2},
		};

		/**
		 * Stips 1 in base game.
		 */
		private static int[][] baseReels1 = {
			new int[]{3,7,4,7,11,12,6,11,7,7,9,4,10,12,7,7,5,9,8,1,9,8,10,9,7,5,5,7,9,10,10,12,6,6,10,10,8,11,8,7,4,3,5,12,6,9,8,1,9,8,9,8,4,3,11,11,7,9,8,11,7,3,11},
			new int[]{3,9,4,11,7,4,11,9,10,5,5,11,3,9,8,1,9,8,11,6,6,6,10,10,12,12,7,6,11,10,10,10,6,11,7,4,3,10,5,6,9,4,6,7,7,7,12,12,11,3,10,11,11,8,12,9,9,9,11,7,4,11,10},
			new int[]{8,11,10,7,9,11,3,8,8,7,11,3,8,8,3,3,11,6,7,4,11,6,16,12,5,12,7,4,10,10,6,8,8,6,6,6,7,3,6,10,10,6,3,10,9,7,7,4,10,3,10,8,10,16,10,5,9,5,4,9,10,3,10},
			new int[]{3,8,7,5,16,6,3,5,3,9,3,7,7,12,3,7,9,4,10,6,5,8,4,7,9,11,7,6,6,5,16,8,7,7,4,8,10,9,16,10,3,5,3,8,4,9,3,4,9,10,10,7,5,5,6,10,3,12,16,10,10,5,3},
			new int[]{10,3,5,3,10,8,10,7,5,6,5,5,7,3,10,3,4,8,3,12,5,4,3,8,10,4,6,3,9,8,10,10,5,10,6,3,8,5,8,8,4,5,5,7,3,4,6,6,5,3,3,5,6,3,3,12,5,4,4,3,3,5,5},
		};

		/**
		 * Stips 2 in base game.
		 */
		private static int[][] baseReels2 = {
			new int[]{7,7,4,16,3,12,6,9,7,3,9,10,10,3,7,3,7,5,8,8,9,8,3,9,7,3,11,16,9,10,10,3,6,6,10,16,8,8,8,7,10,12,4,4,6,8,4,8,7,8,9,8,10,7,11,9,7,9,8,9,7,4,4},
			new int[]{12,9,4,11,9,8,1,9,8,5,3,11,3,7,7,8,11,3,3,6,6,6,8,10,4,12,7,6,3,4,10,4,5,3,8,8,4,12,12,9,8,1,9,8,7,7,12,4,11,5,10,12,11,8,8,9,9,9,10,7,11,11,10},
			new int[]{8,11,10,7,9,11,5,5,3,7,11,3,8,8,5,11,11,6,7,11,5,6,11,12,5,12,7,10,10,10,8,8,8,6,6,6,7,10,9,10,10,6,10,10,9,7,7,4,10,9,10,8,10,10,10,5,9,5,10,9,10,10,10},
			new int[]{8,8,7,9,10,6,5,11,5,9,3,12,7,12,7,7,9,12,10,6,8,8,12,7,9,11,12,6,6,8,8,8,7,16,4,4,4,9,8,10,3,5,12,8,4,9,3,11,9,10,16,7,11,11,6,10,12,3,3,10,5,11,5},
			new int[]{10,3,8,8,10,8,10,7,9,6,8,8,7,3,10,9,10,8,8,12,5,4,10,4,10,8,12,10,3,3,12,10,5,10,6,11,8,11,11,8,8,10,10,7,11,4,3,11,11,3,3,12,6,12,3,12,10,4,12,11,11,11,12},
		};

		/**
		 * Stips 3 in base game.
		 */
		private static int[][] baseReels3 = {
			new int[]{16,7,7,16,10,12,16,9,7,16,11,4,16,12,7,16,7,12,16,7,10,16,10,9,16,11,12,16,7,10,16,12,6,16,10,10,16,8,7,16,4,12,16,12,7,16,10,10,16,10,12,16,12,8,16,8,7,16,12,12,16,11,11},
			new int[]{9,8,16,3,8,16,5,8,16,5,9,16,8,9,16,8,9,16,8,9,16,8,8,16,3,9,16,6,4,16,4,4,16,3,3,16,8,3,16,6,9,16,4,7,16,3,4,16,4,5,16,4,3,16,9,9,16,9,3,16,4,3,16},
			new int[]{10,10,12,11,8,11,8,12,12,6,9,12,11,7,12,7,12,3,10,9,10,8,8,12,5,4,10,4,10,8,12,10,9,12,11,10,5,10,6,11,8,8,3,7,8,8,11,11,11,6,7,6,11,16,12,12,7,6,11,7,4,10,7},
			new int[]{9,12,16,11,7,16,5,5,16,5,11,16,3,7,16,7,11,16,11,6,16,6,12,16,12,12,16,6,11,16,10,8,16,11,8,16,8,12,16,6,9,16,12,7,16,7,12,16,11,5,16,12,11,16,11,8,16,9,10,16,6,12,16},
			new int[]{10,3,8,8,8,8,10,7,9,6,8,8,7,3,10,9,10,8,8,12,5,4,10,4,10,8,12,10,9,12,12,10,5,10,6,11,8,11,11,8,8,10,10,7,11,4,9,11,11,8,8,12,6,12,3,12,10,4,16,11,11,11,12},
		};

		/**
		 * Stips 4 in base game.
		 */
		private static int[][] baseReels4 = {
			new int[]{7,7,4,12,3,12,6,9,7,7,9,4,10,12,7,7,7,5,8,9,9,8,10,9,7,11,11,12,9,10,10,12,6,6,10,8,8,8,8,7,8,12,12,12,8,5,8,7,9,8,9,8,8,7,11,9,7,9,8,9,7,11,11},
			new int[]{12,9,4,11,7,11,5,5,10,5,11,11,3,7,6,11,1,6,11,6,6,6,16,10,12,12,7,6,11,1,6,11,5,11,8,8,8,16,12,6,6,11,1,6,11,7,12,12,11,5,10,12,11,8,16,9,9,9,10,7,11,11,10},
			new int[]{8,11,10,7,9,11,12,8,8,7,11,3,8,8,11,11,11,6,7,11,11,6,16,12,5,12,7,10,11,10,8,8,8,6,6,6,7,10,9,10,10,6,10,10,9,7,7,4,10,9,10,8,10,16,10,5,12,12,10,9,10,10,10},
			new int[]{8,11,7,9,10,6,11,11,11,9,3,12,6,11,1,6,11,12,10,6,8,12,12,6,11,1,6,11,6,8,8,8,7,7,4,4,4,9,8,10,3,5,12,8,4,9,11,11,9,10,11,7,11,6,11,1,6,11,3,10,5,11,12},
			new int[]{10,3,8,8,9,8,10,7,9,6,8,8,7,3,10,9,10,8,8,12,5,4,10,4,10,8,12,10,9,12,11,10,5,10,6,11,8,11,12,8,11,11,10,7,12,4,9,11,11,12,8,12,6,12,3,12,10,4,10,11,11,11,12},
		};

		/**
		 * Stips 5 in base game.
		 */
		private static int[][] baseReels5 = {
			new int[]{3,7,10,10,7,10,3,3,7,10,10,7,10,3,3,7,10,10,7,10,3,3,7,10,10,7,10,3,3,7,10,10,7,10,3,3,7,10,10,7,10,3,3,7,10,10,7,10,3,3,7,10,10,7,10,3,3,7,10,10,7,10,3},
			new int[]{4,4,8,16,4,8,8,4,8,16,4,8,16,8,4,4,8,16,4,8,8,4,8,16,4,8,16,8,4,4,8,16,4,8,8,4,8,16,4,8,16,8,4,4,8,16,4,8,8,4,8,16,4,8,16,8,4,8,16,4,8,16,8},
			new int[]{5,5,7,10,7,10,7,10,10,5,7,10,5,7,5,5,7,10,7,10,7,10,10,5,7,10,5,7,5,5,7,10,7,10,7,10,10,5,7,10,5,7,5,5,7,10,7,10,7,10,10,5,7,10,5,7,10,10,5,7,10,5,7},
			new int[]{9,6,9,16,6,6,6,9,16,9,9,6,9,16,9,6,9,16,6,6,6,9,16,9,9,6,9,16,9,6,9,16,6,6,6,9,16,9,9,6,9,16,9,6,9,16,6,6,6,9,16,9,9,6,16,9,9,16,9,9,6,9,16},
			new int[]{12,9,8,12,9,8,9,8,12,8,8,9,8,8,12,9,8,12,9,8,9,8,12,8,8,9,8,8,12,9,8,12,9,8,9,8,12,8,8,9,8,8,12,9,8,12,9,8,9,8,12,8,8,9,8,8,8,12,8,8,9,8,8},
		};

		/**
		 * Stips 1 in free spins.
		 */
		private static int[][] freeReels1 = {
			new int[]{3,7,4,11,11,12,6,11,7,3,9,4,10,12,16,7,5,9,8,1,9,8,10,9,7,16,11,7,9,10,10,12,6,6,10,16,8,8,8,7,4,12,12,12,6,9,8,1,9,8,9,8,4,7,11,4,7,9,8,11,7,16,11},
			new int[]{12,9,4,11,7,4,11,9,10,5,11,11,3,7,7,8,3,11,3,6,6,6,9,8,1,9,8,6,6,7,4,11,6,11,8,8,8,8,12,6,9,4,12,7,16,7,12,12,11,9,10,12,11,8,4,9,9,9,10,7,4,11,10},
			new int[]{8,11,10,7,9,11,12,8,8,7,11,3,8,8,11,16,11,6,7,4,11,6,16,12,5,12,7,4,10,10,8,8,8,6,6,6,7,10,9,10,10,6,10,10,9,7,16,4,10,9,10,8,10,11,10,5,9,5,4,9,10,10,10},
			new int[]{8,8,7,9,10,6,11,11,11,9,3,12,7,12,7,16,9,4,10,6,8,8,4,7,9,16,12,6,6,8,8,8,7,16,4,8,10,9,8,10,3,5,12,8,4,9,11,4,9,10,8,7,11,11,6,10,12,16,3,10,10,11,12},
			new int[]{10,3,16,8,11,8,10,7,9,6,8,8,7,3,10,9,4,8,8,12,5,4,10,8,10,4,12,16,9,12,8,10,5,10,6,11,8,16,8,8,8,5,5,7,11,4,9,11,11,16,8,12,6,12,3,7,5,4,8,11,11,11,12},
		};

		/**
		 * Stips 2 in free spins.
		 */
		private static int[][] freeReels2 = {
			new int[]{3,7,4,16,11,12,6,11,7,7,9,4,10,12,7,7,5,9,8,1,9,8,10,9,7,11,11,7,9,10,10,12,6,6,10,10,8,8,8,7,4,12,12,12,6,9,8,1,9,8,9,8,4,7,11,9,7,9,8,11,7,11,11},
			new int[]{12,9,4,11,7,4,16,9,10,5,11,11,3,7,7,8,16,11,11,6,6,6,8,10,3,12,7,6,11,10,10,10,9,11,8,8,8,9,12,6,9,4,12,7,7,7,12,16,11,9,10,12,11,8,9,9,9,9,10,7,4,11,10},
			new int[]{8,11,10,7,9,11,12,8,8,7,11,3,8,8,11,11,11,6,7,4,16,6,8,12,5,12,7,4,10,16,8,8,8,6,6,6,3,10,9,10,16,6,10,10,9,7,7,4,10,9,10,8,9,9,10,5,9,5,4,9,10,10,10},
			new int[]{8,8,7,9,10,6,11,11,11,9,3,12,7,12,16,7,9,4,10,6,8,8,4,7,9,11,12,6,6,8,8,8,7,16,4,8,10,9,8,10,3,5,12,8,4,9,16,4,9,10,9,7,9,9,6,10,12,11,3,10,16,11,12},
			new int[]{10,3,8,8,16,8,10,7,9,6,8,8,7,3,10,9,4,8,8,16,5,4,10,8,10,8,3,10,9,12,9,10,16,10,6,11,8,11,5,8,8,5,5,7,11,8,9,11,11,8,8,12,6,12,3,12,5,4,9,11,11,11,12},
		};

		/**
		 * Stips 3 in free spins.
		 */
		private static int[][] freeReels3 = {
			new int[]{3,7,4,6,11,12,6,11,7,7,9,4,10,12,7,7,5,9,8,1,9,8,10,9,7,11,11,7,9,10,10,12,6,6,10,6,8,8,8,7,4,12,12,12,6,9,8,1,9,8,9,8,4,7,11,6,7,9,8,11,7,11,11},
			new int[]{12,9,4,11,7,4,11,9,10,5,11,11,3,7,7,8,11,11,11,6,6,6,6,10,12,12,7,6,11,10,10,10,9,11,8,8,8,6,12,6,9,4,12,7,7,7,12,12,11,9,10,12,11,8,6,9,9,9,10,7,4,11,10},
			new int[]{8,11,10,7,9,11,12,8,8,7,11,3,8,8,11,11,11,6,7,4,11,6,6,12,5,12,7,4,10,10,8,8,8,6,6,6,7,10,9,10,10,6,10,10,9,7,7,4,10,9,10,8,10,6,10,5,9,5,4,9,10,10,10},
			new int[]{8,8,7,9,10,6,11,11,11,9,3,12,7,12,7,7,9,4,10,6,16,8,4,7,9,11,12,6,6,8,16,8,7,6,4,8,10,9,8,10,3,5,12,8,4,9,11,4,9,10,6,7,11,11,6,10,16,11,3,10,10,11,12},
			new int[]{10,3,8,8,6,8,10,7,9,6,8,8,7,3,10,9,4,8,8,12,5,4,10,16,10,4,12,10,9,12,6,10,5,10,6,11,8,11,6,8,8,5,5,7,11,4,9,11,11,16,8,12,6,12,3,12,5,4,6,11,11,11,12},
		};

		/**
		 * Wild expansion in base game after 1 wild.
		 */
		private static int[][] baseReels1Wilds1 = {
			new int[]{0,0,0},
			new int[]{2,2,2},
			new int[]{2,2,2},
			new int[]{2,10,2},
			new int[]{10,10,10},
		};

		/**
		 * Wild expansion discrete distribution.
		 */
		private static int[][] baseReels1Wilds2 = {
			new int[]{2,2,2},
			new int[]{2,2,2},
			new int[]{2,2,2},
			new int[]{2,10,2},
			new int[]{10,10,10},
		};

		/**
		 * Wild expansion discrete distribution.
		 */
		private static int[][] baseReels2Wilds1 = {
			new int[]{2,4,5},
			new int[]{4,3,4},
			new int[]{5,5,5},
			new int[]{6,3,4},
			new int[]{4,6,2},
		};

		/**
		 * Wild expansion discrete distribution.
		 */
		private static int[][] baseReels2Wilds2 = {
			new int[]{2,4,5},
			new int[]{4,3,4},
			new int[]{5,5,5},
			new int[]{6,3,4},
			new int[]{4,6,2},
		};

		/**
		 * Wild expansion discrete distribution.
		 */
		private static int[][] baseReels3Wilds1 = {
			new int[]{2,2,4},
			new int[]{2,2,2},
			new int[]{2,3,2},
			new int[]{2,3,2},
			new int[]{2,3,2},
		};

		/**
		 * Wild expansion discrete distribution.
		 */
		private static int[][] baseReels3Wilds2 = {
			new int[]{3,3,2},
			new int[]{3,2,3},
			new int[]{3,3,2},
			new int[]{2,3,2},
			new int[]{3,2,3},
		};

		/**
		 * Wild expansion discrete distribution.
		 */
		private static int[][] baseReels4Wilds1 = {
			new int[]{2,4,5},
			new int[]{15,15,15},
			new int[]{20,20,20},
			new int[]{6,3,4},
			new int[]{4,6,2},
		};

		/**
		 * Wild expansion discrete distribution.
		 */
		private static int[][] baseReels4Wilds2 = {
			new int[]{2,4,5},
			new int[]{15,15,15},
			new int[]{20,20,20},
			new int[]{6,3,4},
			new int[]{4,6,2},
		};

		/**
		 * Wild expansion discrete distribution.
		 */
		private static int[][] baseReels5Wilds1 = {
			new int[]{0,0,0},
			new int[]{0,0,0},
			new int[]{0,0,0},
			new int[]{0,0,0},
			new int[]{0,0,0},
		};

		/**
		 * Wild expansion discrete distribution.
		 */
		private static int[][] baseReels5Wilds2 = {
			new int[]{0,0,0},
			new int[]{0,0,0},
			new int[]{0,0,0},
			new int[]{0,0,0},
			new int[]{0,0,0},
		};

		/**
		 * Wild expansion discrete distribution.
		 */
		private static int[][] freeReels1Wilds1 = {
			new int[]{2,4,5},
			new int[]{10,10,10},
			new int[]{5,5,5},
			new int[]{6,6,6},
			new int[]{4,6,2},
		};

		/**
		 * Wild expansion discrete distribution.
		 */
		private static int[][] freeReels1Wilds2 = {
			new int[]{2,4,5},
			new int[]{5,5,5},
			new int[]{5,5,5},
			new int[]{4,3,4},
			new int[]{4,6,2},
		};

		/**
		 * Wild expansion discrete distribution.
		 */
		private static int[][] freeReels2Wilds1 = {
			new int[]{2,4,5},
			new int[]{4,3,4},
			new int[]{5,5,5},
			new int[]{5,5,5},
			new int[]{4,6,2},
		};

		/**
		 * Wild expansion discrete distribution.
		 */
		private static int[][] freeReels2Wilds2 = {
			new int[]{2,4,5},
			new int[]{4,3,4},
			new int[]{5,5,5},
			new int[]{5,5,5},
			new int[]{4,6,2},
		};

		/**
		 * Wild expansion discrete distribution.
		 */
		private static int[][] freeReels3Wilds1 = {
			new int[]{2,4,5},
			new int[]{20,20,20},
			new int[]{5,5,5},
			new int[]{6,3,4},
			new int[]{4,6,2},
		};

		/**
		 * Wild expansion discrete distribution.
		 */
		private static int[][] freeReels3Wilds2 = {
			new int[]{2,4,5},
			new int[]{20,20,20},
			new int[]{5,5,5},
			new int[]{6,3,4},
			new int[]{4,6,2},
		};

		/**
		 * Free spins multipliers discrete distribution.
		 */
		private static int[] freeMultiplierDistribution = {2,3,4,5,6,7,8,2,3,4,5,6,7,8,2,3,4,5,6,7,8,2,3,4,5,6,7,8,2,3,4,5,6,7,8,2,3,4,5,6,7,8,2,3,4,5,6,7,8,2,3,4,5,6,7,8,2,3,4,5,6,7,8,2,3,4,5,6,7,8,2,3,4,5,6,7,8,2,3,4,5,6,7,8,2,3,4,5,6,7,8,2,3,4,5,6,7,8,2,3,4,5,6,7,8,2,3,4,5,6,7,2,3,4,5,6,7,2,3,4,5,6,7,2,3,4,5,6,7,2,3,4,5,6,7,2,3,4,5,6,2,3,4,5,6,2,3,4,5,6,2,3,4,5,6,2,3,4,5,6,2,3,4,5,6,2,3,4,5,6,2,3,4,5,6,2,3,4,5,6,2,3,4,5,6,2,3,4,5,2,3,4,5,2,3,4,5,2,3,4,5,2,3,4,5,2,3,4,5,2,3,4,5,2,3,4,5,2,3,4,5,2,3,4,5,2,3,4,5,2,3,4,5,2,3,4,5,2,3,4,5,2,3,4,5,2,3,4,5,2,3,4,5,2,3,4,5,2,3,4,5,2,3,4,5,2,3,4,2,3,4,2,3,4,2,3,4,2,3,4,2,3,4,2,3,4,2,3,4,2,3,4,2,3,4,2,3,4,2,3,4,2,3,4,2,3,4,2,3,4,2,3,4,2,3,4,2,3,4,2,3,4,2,3,4,2,3,4,2,3,4,2,3,4,2,3,4,2,3,4,2,3,4,2,3,4,2,3,4,2,3,4,2,3,4,2,3,4,2,3,4,2,3,4,2,3,4,2,3,4,2,3,4,2,3,4,2,3,4,2,3,4,2,3,4,2,3,4,2,3,4,2,3,4,2,3,4,2,3,4,2,3,4,2,3,4,2,3,4,2,3,4,2,3,4,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3};

		/**
		 * Base game scatter discrete distribution.
		 */
		private static int[] baseScatterDistritution = {10,12,14,15,17,20,22,25,30,40,10,12,14,15,17,20,22,25,30,40,10,12,14,15,17,20,25,30,40,10,12,14,15,17,20,25,30,40,10,12,14,15,17,20,25,30,40,10,12,14,15,17,20,25,30,40,10,12,14,15,17,20,25,30,40,10,12,14,15,17,20,25,30,40,10,12,14,15,17,20,25,30,40,10,12,14,15,17,20,25,30,40,12,15,17,20,25,12,15,17,20,25,12,15,17,20,25,12,15,17,20,25,12,15,17,20,25,12,15,17,20,25,12,15,17,20,25,12,15,17,20,25,12,15,17,20,25,12,15,17,20,25,15,17,20,25,15,17,20,25,15,17,20,25,15,17,20,25,15,17,20,25,15,20,25,15,20,25,15,20,25,15,20,25,15,20,25,20,25,20,25,20,25,20,25,20,25,20,25,20,25,20,25,20,25,20,25,25,25,25,25,25,25,25,25,25,25};

		/**
		 * Free spins scatter discrete distribution.
		 */
		private static int[] free1ScatterDistritution = {10,12,14,15,17,20,22,25,30,40,10,12,14,15,17,20,22,25,30,40,10,12,14,15,17,20,25,30,40,10,12,14,15,17,20,25,30,40,10,12,14,15,17,20,25,30,40,10,12,14,15,17,20,25,30,40,10,12,14,15,17,20,25,30,40,10,12,14,15,17,20,25,30,40,10,12,14,15,17,20,25,30,40,10,12,14,15,17,20,25,30,40,12,15,17,20,25,12,15,17,20,25,12,15,17,20,25,12,15,17,20,25,12,15,17,20,25,12,15,17,20,25,12,15,17,20,25,12,15,17,20,25,12,15,17,20,25,12,15,17,20,25,15,17,20,25,15,17,20,25,15,17,20,25,15,17,20,25,15,17,20,25,15,20,25,15,20,25,15,20,25,15,20,25,15,20,25,20,25,20,25,20,25,20,25,20,25,20,25,20,25,20,25,20,25,20,25,25,25,25,25,25,25,25,25,25,25};

		/**
		 * Free spins scatter discrete distribution.
		 */
		private static int[] free2ScatterDistritution = {10,12,14,15,17,20,22,25,30,40,10,12,14,15,17,20,22,25,30,40,10,12,14,15,17,20,25,30,40,10,12,14,15,17,20,25,30,40,10,12,14,15,17,20,25,30,40,10,12,14,15,17,20,25,30,40,10,12,14,15,17,20,25,30,40,10,12,14,15,17,20,25,30,40,10,12,14,15,17,20,25,30,40,10,12,14,15,17,20,25,30,40,12,15,17,20,25,12,15,17,20,25,12,15,17,20,25,12,15,17,20,25,12,15,17,20,25,12,15,17,20,25,12,15,17,20,25,12,15,17,20,25,12,15,17,20,25,12,15,17,20,25,15,17,20,25,15,17,20,25,15,17,20,25,15,17,20,25,15,17,20,25,15,20,25,15,20,25,15,20,25,15,20,25,15,20,25,20,25,20,25,20,25,20,25,20,25,20,25,20,25,20,25,20,25,20,25,25,25,25,25,25,25,25,25,25,25};

		/**
		 * Free spins scatter discrete distribution.
		 */
		private static int[] free3ScatterDistritution = {10,12,14,15,17,20,22,25,30,40,10,12,14,15,17,20,22,25,30,40,10,12,14,15,17,20,25,30,40,10,12,14,15,17,20,25,30,40,10,12,14,15,17,20,25,30,40,10,12,14,15,17,20,25,30,40,10,12,14,15,17,20,25,30,40,10,12,14,15,17,20,25,30,40,10,12,14,15,17,20,25,30,40,10,12,14,15,17,20,25,30,40,12,15,17,20,25,12,15,17,20,25,12,15,17,20,25,12,15,17,20,25,12,15,17,20,25,12,15,17,20,25,12,15,17,20,25,12,15,17,20,25,12,15,17,20,25,12,15,17,20,25,15,17,20,25,15,17,20,25,15,17,20,25,15,17,20,25,15,17,20,25,15,20,25,15,20,25,15,20,25,15,20,25,15,20,25,20,25,20,25,20,25,20,25,20,25,20,25,20,25,20,25,20,25,20,25,25,25,25,25,25,25,25,25,25,25};

		/**
		 * Base game strips discrete distribution.
		 */
		private static int[] baseStripsDistribution = {1,2,3,4,5,1,2,3,4,5,1,2,3,4,5,1,2,3,4,5,1,2,3,4,5,1,2,3,4,5,1,2,3,4,5,1,2,3,4,5,1,2,3,4,5,1,2,3,4,5,1,2,3,4,5,1,2,3,4,5,1,2,3,4,5,1,2,3,4,5,1,2,3,4,5,1,2,3,4,5,1,2,3,4,5,1,2,3,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,1,2,4,5,2,4,5,2,4,5,2,4,5,2,4,5,2,4,5,2,4,5,2,4,5,2,4,5,2,4,5,2,4,5,2,4,5,2,4,5,2,4,5,2,4,5,2,4,5,2,4,5,2,4,5,2,4,5,2,4,5,2,4,5,2,4,5,2,4,5,2,4,5,2,4,5,2,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5};

		/**
		 * All scatters discrete distribution together.
		 */
		private static int[][] scatterDistritutions = {baseScatterDistritution, free1ScatterDistritution, free2ScatterDistritution, free3ScatterDistritution};

		/**
		 * Reels strips 1.
		 */
		private static int[][][] reels1 = {baseReels1, freeReels1, freeReels2, freeReels3};

		/**
		 * Reels strips 2.
		 */
		private static int[][][] reels2 = {baseReels2, freeReels1, freeReels2, freeReels3};

		/**
		 * Reels strips 3.
		 */
		private static int[][][] reels3 = {baseReels3, freeReels1, freeReels2, freeReels3};

		/**
		 * Reels strips 4.
		 */
		private static int[][][] reels4 = {baseReels4, freeReels1, freeReels2, freeReels3};

		/**
		 * Reels strips 5.
		 */
		private static int[][][] reels5 = {baseReels5, freeReels1, freeReels2, freeReels3};

		/**
		 * All strips together.
		 */
		private static int[][][][] reelsSets = {reels1, reels2, reels3, reels4, reels5};

		/**
		 * Wild symbols distributions together.
		 */
		private static int[][][][] wildsSets = {
			new int[][][]{new int[][]{}, baseReels1Wilds1, baseReels1Wilds2},
			new int[][][]{new int[][]{}, baseReels2Wilds1, baseReels2Wilds2},
			new int[][][]{new int[][]{}, baseReels3Wilds1, baseReels3Wilds2},
			new int[][][]{new int[][]{}, baseReels4Wilds1, baseReels4Wilds2},
			new int[][][]{new int[][]{}, baseReels5Wilds1, baseReels5Wilds2},
			new int[][][]{new int[][]{}, freeReels1Wilds1, freeReels1Wilds2},
			new int[][][]{new int[][]{}, freeReels2Wilds1, freeReels2Wilds2},
			new int[][][]{new int[][]{}, freeReels3Wilds1, freeReels3Wilds2},
		};

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
		private static int[][] view = {
			new int[]{ -1, -1, -1 },
			new int[]{ -1, -1, -1 },
			new int[]{ -1, -1, -1 },
			new int[]{ -1, -1, -1 },
			new int[]{ -1, -1, -1 }
		};

		/**
		 * Current scatter multiplier.
		 */
		private static int scatterMultiplier = 1;

		/**
		 * Total bet in single base game spin.
		 */
		private static int totalBet = lines.Length;

		/**
		 * List of free spins to be played.
		 */
		private static List<FreeSpin> freeGamesList = new List<FreeSpin>();

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
		private static bool verboseOutput = false;

		/**
		 * Free spins flag.
		 */
		private static bool freeOff = false;

		/**
		 * Wild substitution flag.
		 */
		private static bool wildsOff = false;

		/**
		 * Wild expansion flag.
		 */
		private static bool wildExpandOff = false;

		/**
		 * Symbols win hit rate in base game.
		 */
		private static long[][] baseSymbolMoney = {
			new long[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
			new long[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
			new long[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
			new long[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
			new long[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
			new long[]{0,0,0,0,0,0,0,0,0,0,0,0,0}
		};

		/**
		 * Symbols hit rate in base game.
		 */
		private static long[][] baseGameSymbolsHitRate = {
			new long[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
			new long[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
			new long[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
			new long[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
			new long[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
			new long[]{0,0,0,0,0,0,0,0,0,0,0,0,0}
		};

		/**
		 * Static constructor for discrete distributions shuffling.
		 */
		static MainClass() {
			for (int last=freeMultiplierDistribution.Length-1, r=-1, swap=-1; last>0; last--) {
				r = prng.Next(last+1);
				swap = freeMultiplierDistribution[last];
				freeMultiplierDistribution[last] = freeMultiplierDistribution[r];
				freeMultiplierDistribution[r] = swap;
			}
			for (int last=baseScatterDistritution.Length-1, r=-1, swap=-1; last>0; last--) {
				r = prng.Next(last+1);
				swap = baseScatterDistritution[last];
				baseScatterDistritution[last] = baseScatterDistritution[r];
				baseScatterDistritution[r] = swap;
			}
			for (int last=free1ScatterDistritution.Length-1, r=-1, swap=-1; last>0; last--) {
				r = prng.Next(last+1);
				swap = free1ScatterDistritution[last];
				free1ScatterDistritution[last] = free1ScatterDistritution[r];
				free1ScatterDistritution[r] = swap;
			}
			for (int last=free2ScatterDistritution.Length-1, r=-1, swap=-1; last>0; last--) {
				r = prng.Next(last+1);
				swap = free2ScatterDistritution[last];
				free2ScatterDistritution[last] = free2ScatterDistritution[r];
				free2ScatterDistritution[r] = swap;
			}
			for (int last=free3ScatterDistritution.Length-1, r=-1, swap=-1; last>0; last--) {
				r = prng.Next(last+1);
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
			for (int i = 0; i < view.Length && i < reels.Length; i++) {
				int r = prng.Next(reels[i].Length);
				int u = r - 1;
				int d = r + 1;

				if (u < 0) {
					u = reels[i].Length - 1;
				}

				if (d >= reels[i].Length) {
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
			int symbol = line [0];

			/*
			 * Wild wymbol passing to find first regular symbol.
			 */
			for (int i=0; i<line.Length; i++) {
				/*
				 * First no wild symbol found.
				 */
				if(symbol != 1 && symbol != 2) {
					break;
				}

				symbol = line [i];
			}

			/*
			 * Wild symbol substitution. Other wild are artificial they are not part of the pay table.
			 */
			for (int i = 0; i<line.Length && wildsOff==false; i++) {
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
			for (int i = 0; i < line.Length; i++) {
				if (line [i] == symbol) {
					number++;
				} else {
					break;
				}
			}

			/*
			 * Cleare unused symbols.
			 */
			for (int i = number; i < line.Length; i++) {
				line[i] = -1;
			}

			int win = paytable[number][symbol];

			/*
			 * There is multiplier in free games mode.
			 */
			if(freeGamesList.Count > 0) {
				win *= freeGamesMultiplier;
			}

			if(win > 0 && freeGamesList.Count==0) {
				baseSymbolMoney[number][symbol] += win;
				baseGameSymbolsHitRate[number][symbol]++;
			}

			return( win );
		}

		/**
		 * Calculate win in all possible lines.
		 *
		 * @param view Symbols visible in screen view.
		 *
		 * @return Calculated win.
		 */
		private static int linesWin (int[][] view) {
			int win = 0;

			/*
			 * Check wins in all possible lines.
			 */
			for (int l = 0; l < lines.Length; l++) {
				int[] line = { -1, -1, -1, -1, -1 };

				/*
				 * Prepare line for combination check.
				 */
				for (int i = 0; i < line.Length; i++) {
					int index = lines [l] [i];
					line [i] = view [i] [index];
				}

				int result = lineWin( line );

				/*
				 * Accumulate line win.
				 */
				win += result;
			}

			return( win );
		}

		/**
		 * Setup parameters for free spins mode.
		 */
		private static void freeGamesSetup() {
			int numberOfScatters = 0;
			int numberOfWilds = 0;
			for (int i = 0; i < view.Length; i++) {
				for (int j = 0; j < view[i].Length; j++) {
					if (view[i][j] == 1) {
						numberOfWilds++;
					}
					if (view[i][j] == 16) {
						numberOfScatters++;
					}
				}
			}

			scatterMultiplier = scatterMultipliers[ numberOfScatters ];

			/*
			 * In base game 3+ scatters turn into free spins.
			 */
			if(numberOfScatters<3 && freeGamesList.Count==0) {
				return;
			} else if(numberOfScatters>=3 && freeGamesList.Count==0) {
				int freeGamesNumber = baseScatterDistritution[ prng.Next(baseScatterDistritution.Length) ];
				freeGamesMultiplier = freeMultiplierDistribution[ prng.Next(freeMultiplierDistribution.Length) ];
				for(int i=0; i<freeGamesNumber; i++) {
					freeGamesList.Add( new FreeSpin((i+1), reels[1], wildsSets[5][numberOfWilds]) );
				}
			} else if(numberOfScatters>=3 && freeGamesList.Count>0) {
				int next = -1;
				if(freeGamesList[freeGamesList.Count-1].reels == reels[1]) {
					next = 2;
				} else if(freeGamesList[freeGamesList.Count-1].reels == reels[2]) {
					next = 3;
				} else {
					next = 3;
				}

				int distributionIndex = -1;
				if(freeGamesList[freeGamesList.Count-1].reels == reels[1]) {
					distributionIndex = 1;
				} else if(freeGamesList[freeGamesList.Count-1].reels == reels[2]) {
					distributionIndex = 2;
				} else if(freeGamesList[freeGamesList.Count-1].reels == reels[3]){
					distributionIndex = 3;
				}

				int freeGamesNumber = scatterDistritutions[distributionIndex][ prng.Next(scatterDistritutions[distributionIndex].Length) ];
				for(int i=0; i<freeGamesNumber; i++) {
					freeGamesList.Add( new FreeSpin((i+1), reels[next], wildsSets[4+next][numberOfWilds]) );
				}
			}
		}

		/**
		 * Expand wild.
		 */
		private static void expandWild() {
			if(wildExpandOff == true) {
				return;
			}

			int numberOfWilds = 0;
			for (int i = 0; i < view.Length; i++) {
				for (int j = 0; j < view[i].Length; j++) {
					if (view[i][j] == 1) {
						numberOfWilds++;
					}
				}
			}

			if(numberOfWilds == 1) {
				int[][] transforms = new int[][] {
					new int[]{ 1+prng.Next(100), 1+prng.Next(100), 1+prng.Next(100) },
					new int[]{ 1+prng.Next(100), 1+prng.Next(100), 1+prng.Next(100) },
					new int[]{ 1+prng.Next(100), 1+prng.Next(100), 1+prng.Next(100) },
					new int[]{ 1+prng.Next(100), 1+prng.Next(100), 1+prng.Next(100) },
					new int[]{ 1+prng.Next(100), 1+prng.Next(100), 1+prng.Next(100) }
				};
				for(int i=0; i<transforms.Length; i++) {
					for(int j=0; j<transforms[i].Length; j++) {
						if(transforms[i][j]<=wilds[numberOfWilds][i][j] && view[i][j]!=1 && view[i][j]!=16) {
							transforms[i][j] = 1;
						} else {
							transforms[i][j] = 0;
						}
					}
				}
				int sum = 0;
				for(int i=0; i<transforms.Length; i++) {
					for(int j=0; j<transforms[i].Length; j++) {
						sum += transforms[i][j];
					}
				}
				if(sum==0  && view[4][1]!=1 && view[4][1]!=16) {
					transforms[4][1] = 1;
				}
				for(int j=0, k=0; j<transforms[0].Length&&k<3; j++) {
					for(int i=0; i<transforms.Length&&k<3; i++) {
						if(transforms[i][j] == 1) {
							view[i][j] = 1;
							k++;
						}
					}
				}
			} else if(numberOfWilds == 2) {
				int[][] transforms = new int[][] {
					new int[]{ 1+prng.Next(100), 1+prng.Next(100), 1+prng.Next(100) },
					new int[]{ 1+prng.Next(100), 1+prng.Next(100), 1+prng.Next(100) },
					new int[]{ 1+prng.Next(100), 1+prng.Next(100), 1+prng.Next(100) },
					new int[]{ 1+prng.Next(100), 1+prng.Next(100), 1+prng.Next(100) },
					new int[]{ 1+prng.Next(100), 1+prng.Next(100), 1+prng.Next(100) }
				};
				for(int i=0; i<transforms.Length; i++) {
					for(int j=0; j<transforms[i].Length; j++) {
						if(transforms[i][j]<=wilds[numberOfWilds][i][j] && view[i][j]!=1 && view[i][j]!=16) {
							transforms[i][j] = 1;
						} else {
							transforms[i][j] = 0;
						}
					}
				}
				int sum = 0;
				for(int i=0; i<transforms.Length; i++) {
					for(int j=0; j<transforms[i].Length; j++) {
						sum += transforms[i][j];
					}
				}
				if(sum==0  && view[4][0]!=1 && view[4][0]!=16) {
					transforms[4][0] = 1;
				}
				if(sum==0  && view[4][2]!=1 && view[4][2]!=16) {
					transforms[4][2] = 1;
				}
				if(sum==1  && view[4][1]!=1 && view[4][1]!=16) {
					transforms[4][1] = 1;
				}
				for(int j=0, k=0; j<transforms[0].Length&&k<2; j++) {
					for(int i=0; i<transforms.Length&&k<2; i++) {
						if(transforms[i][j] == 1) {
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
			if(freeOff == true) {
				return;
			}

			/*
			 * Spin reels.
			 * In retriggered games from FS1 to FS2 and from FS2 to FS3. FS3 can not rettriger FS.
			 */
			spin( freeGamesList[0].reels );

			freeGamesSetup();

			expandWild();

			/*
			 * Win accumulated by lines.
			 */
			int win = linesWin (view) + (scatterMultiplier*totalBet);

			/*
			 * Add win to the statistics.
			 */
			freeMoney += win;
			wonMoney += win;
			if(freeMaxWin < win) {
				freeMaxWin = win;
			}

			/*
			 * Count base game hit rate.
			 */
			if(win > 0) {
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
			int r = baseStripsDistribution[prng.Next(baseStripsDistribution.Length)]-1;
			reels = reelsSets[ r ];
			wilds = wildsSets[ r ];

			/*
			 * Spin reels.
			 */
			spin( reels[0] );

			freeGamesSetup();

			expandWild();

			/*
			 * Win accumulated by lines.
			 */
			int win = linesWin (view) + (scatterMultiplier*totalBet);

			/*
			 * Add win to the statistics.
			 */
			baseMoney += win;
			wonMoney += win;
			if(baseMaxWin < win) {
				baseMaxWin = win;
			}

			/*
			 * Count base game hit rate.
			 */
			if(win > 0) {
				baseGameHitRate++;
			}

			/*
			 * Count into free spins hit rate.
			 */
			if(freeGamesList.Count > 0) {
				totalNumberOfFreeGameStarts++;
			}

			/*
			 * Play all free games.
			 */
			while(freeGamesList.Count > 0) {
				totalNumberOfFreeGames++;

				singleFreeGame();

				freeGamesList.RemoveAt( 0 );
			}
			freeGamesMultiplier = 1;
			freeGamesList.Clear();
		}

		/**
		 * Print help information.
		 */
		private static void printHelp () {
			Console.WriteLine( "*******************************************************************************" );
			Console.WriteLine( "* Thracian Treasure Slot Simulation version 0.9.0                             *" );
			Console.WriteLine( "* Copyrights (C) 2013-2023 Velbazhd Software LLC                              *" );
			Console.WriteLine( "*                                                                             *" );
			Console.WriteLine( "* developed by Todor Balabanov ( todor.balabanov@gmail.com )                  *" );
			Console.WriteLine( "* Sofia, Bulgaria                                                             *" );
			Console.WriteLine( "*                                                                             *" );
			Console.WriteLine( "* This program is free software: you can redistribute it and/or modify        *" );
			Console.WriteLine( "* it under the terms of the GNU General Public License as published by        *" );
			Console.WriteLine( "* the Free Software Foundation, either version 3 of the License, or           *" );
			Console.WriteLine( "* (at your option) any later version.                                         *" );
			Console.WriteLine( "*                                                                             *" );
			Console.WriteLine( "* This program is distributed in the hope that it will be useful,             *" );
			Console.WriteLine( "* but WITHOUT ANY WARRANTY; without even the implied warranty of              *" );
			Console.WriteLine( "* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the               *" );
			Console.WriteLine( "* GNU General Public License for more details.                                *" );
			Console.WriteLine( "*                                                                             *" );
			Console.WriteLine( "* You should have received a copy of the GNU General Public License           *" );
			Console.WriteLine( "* along with this program. If not, see <http://www.gnu.org/licenses/>.        *" );
			Console.WriteLine( "*                                                                             *" );
			Console.WriteLine( "*******************************************************************************" );
			Console.WriteLine( "*                                                                             *" );
			Console.WriteLine( "* -h              Help screen.                                                *" );
			Console.WriteLine( "* -help           Help screen.                                                *" );
			Console.WriteLine( "*                                                                             *" );
			Console.WriteLine( "* -g<number>      Number of games (default 10 000 000).                       *" );
			Console.WriteLine( "* -p<number>      Progress on each iteration number (default 10 000 000).     *" );
			Console.WriteLine( "*                                                                             *" );
			Console.WriteLine( "* -freeoff        Switch off free spins.                                      *" );
			Console.WriteLine( "* -wildsoff       Switch off wilds.                                           *" );
			Console.WriteLine( "* -expandoff      Switch off wild expansion.                                  *" );
			Console.WriteLine( "*                                                                             *" );
			Console.WriteLine( "* -verify         Print input data structures.                                *" );
			Console.WriteLine( "*                                                                             *" );
			Console.WriteLine( "*******************************************************************************" );
		}

		/**
		 * Print all simulation input data structures.
		 */
		private static void printDataStructures() {
			Console.WriteLine("Paytable:");
			for(int i=3; i<paytable.Length; i++) {
				Console.Write("\t" + i + " of");
			}
			Console.WriteLine();
			for(int j=3; j<paytable[0].Length; j++) {
				Console.Write("SYM" + j + "\t");
				for(int i=3; i<paytable.Length; i++) {
					Console.Write(paytable[i][j] + "\t");
				}
				Console.WriteLine();
			}
			Console.WriteLine();

			Console.WriteLine("Lines:");
			for(int i=0; i<lines.Length; i++) {
				for(int j=0; j<lines[0].Length; j++) {
					Console.Write(lines[i][j] + " ");
				}
				Console.WriteLine();
			}
			Console.WriteLine();

			Console.WriteLine("Base Game Reels:");
			for(int i=0; i<reels[0].Length; i++) {
				for(int j=0; j<reels[0].Length; j++) {
					if(j % 10 == 0) {
						Console.WriteLine();
					}
					Console.Write("SYM" + reels[0][i][j] + " ");
				}
				Console.WriteLine();
			}
			Console.WriteLine();

			Console.WriteLine("Base Game Reels:");
			/* Count symbols in reels. */ {
				int[][] counters = {
					new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
					new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
					new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
					new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
					new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
				};
				for(int i=0; i<reels[0].Length; i++) {
					for(int j=0; j<reels[0][i].Length; j++) {
						counters[i][reels[0][i][j]]++;
					}
				}
				for(int i=0; i<reels[0].Length; i++) {
					Console.Write("\tReel " + (i+1));
				}
				Console.WriteLine();
				for(int j=0; j<counters[0].Length; j++) {
					Console.Write("SYM" + j + "\t");
					for(int i=0; i<counters.Length; i++) {
						Console.Write(counters[i][j] + "\t");
					}
					Console.WriteLine();
				}
				Console.WriteLine("---------------------------------------------");
				Console.Write("Total:\t");
				long combinations = 1L;
				for(int i=0; i<counters.Length; i++) {
					int sum = 0;
					for(int j=0; j<counters[0].Length; j++) {
						sum += counters[i][j];
					}
					Console.Write(sum + "\t");
					if(sum != 0) {
						combinations *= sum;
					}
				}
				Console.WriteLine();
				Console.WriteLine("---------------------------------------------");
				Console.WriteLine("Combinations:\t" + combinations);
			}
			Console.WriteLine();
		}

		/**
		 * Print simulation statistics.
		 */
		private static void printStatistics ()
		{
			Console.WriteLine ("Won money:\t" + wonMoney);
			Console.WriteLine ("Lost money:\t" + lostMoney);
			Console.WriteLine ("Total Number of Games:\t" + totalNumberOfGames);
			Console.WriteLine ();
			Console.WriteLine ("Total RTP:\t" + ((double)wonMoney / (double)lostMoney) + "\t\t" + (100.0D * (double)wonMoney / (double)lostMoney) + "%");
			Console.WriteLine ("Base Game RTP:\t" + ((double)baseMoney / (double)lostMoney) + "\t\t" + (100.0D * (double)baseMoney / (double)lostMoney) + "%");
			Console.WriteLine ("Free Game RTP:\t" + ((double)freeMoney / (double)lostMoney) + "\t\t" + (100.0D * (double)freeMoney / (double)lostMoney) + "%");
			Console.WriteLine ();
			Console.WriteLine ("Hit Frequency in Base Game:\t" + ((double)baseGameHitRate / (double)totalNumberOfGames) + "\t\t" + (100.0D * (double)baseGameHitRate / (double)totalNumberOfGames) + "%");
			Console.WriteLine ("Hit Frequency into Free Game:\t" + ((double)totalNumberOfFreeGameStarts / (double)totalNumberOfGames) + "\t\t" + (100.0D * (double)totalNumberOfFreeGameStarts / (double)totalNumberOfGames) + "%");
			Console.WriteLine ();
			Console.WriteLine ("Max Win in Base Game:\t" + baseMaxWin);
			Console.WriteLine ("Max Win in Free Game:\t" + freeMaxWin);

			/**/
			Console.WriteLine ();
			Console.WriteLine ("Base Game Symbols RTP:");
			Console.Write ("\t");
			for (int i=0; i<baseSymbolMoney.Length; i++) {
				Console.Write ("" + i + "of\t");
			}
			Console.WriteLine ();
			for (int j=0; j<baseSymbolMoney[0].Length; j++) {
				Console.Write ("SYM" + j + "\t");
				for (int i=0; i<baseSymbolMoney.Length; i++) {
					Console.Write ((double)baseSymbolMoney [i] [j] / (double)lostMoney + "\t");
				}
				Console.WriteLine ();
			}
			Console.WriteLine ();
			Console.WriteLine ("Base Game Symbols Hit Frequency:");
			Console.Write ("\t");
			for (int i=0; i<baseGameSymbolsHitRate.Length; i++) {
				Console.Write ("" + i + "of\t");
			}
			Console.WriteLine ();
			for (int j=0; j<baseGameSymbolsHitRate[0].Length; j++) {
				Console.Write ("SYM" + j + "\t");
				for (int i=0; i<baseGameSymbolsHitRate.Length; i++) {
					Console.Write ((double)baseGameSymbolsHitRate [i] [j] / (double)totalNumberOfGames + "\t");
				}
				Console.WriteLine ();
			}
			/**/
		}

		/**
		 * Print screen view.
		 */
		private static void printView () {
			int max = view[0].Length;
			for (int i=0; i<view.Length; i++) {
				if(max < view[i].Length) {
					max = view[i].Length;
				}
			}

			for(int j=0; j<max; j++) {
				for (int i=0; i<view.Length && j<view[i].Length; i++) {
					if(view[i][j] < 10 && view[i][j]>=0) {
						Console.Write(" ");
					}
					Console.Write(view[i][j] + " ");
				}

				Console.WriteLine();
			}
		}

		/**
		 * Print simulation execution command.
		 *
		 * @param args Command line arguments list.
		 */
		private static void printExecuteCommand(string[] args) {
			Console.WriteLine( "Execute command:" );
			Console.WriteLine();
			Console.Write( System.IO.Path.GetFileName(System.Reflection.Assembly.GetEntryAssembly().Location)+" " );
			for(int i=0; i<args.Length; i++) {
				Console.Write(args[i] + " ");
			}
			Console.WriteLine();
		}

		/**
		 * Main application entry point.
		 *
		 * @param args Command line arguments list.
		 */
		public static void Main (string[] args) {
			printExecuteCommand(args);
			Console.WriteLine();

			long numberOfSimulations = 10000000L;
			long progressPrintOnIteration = 10000000L;

			/*
			 * Parse command line arguments.
			 */
			for(int a=0; a<args.Length; a++) {
				if(args.Length > 0 && args[a].Contains("-g")) {
					String parameter = args[a].Substring(2);

					if(parameter.Contains("k")) {
						parameter = parameter.Substring(0, parameter.Length-1);
						parameter += "000";
					}

					if(parameter.Contains("m")) {
						parameter = parameter.Substring(0, parameter.Length-1);
						parameter += "000000";
					}

					try {
						numberOfSimulations = Int64.Parse(parameter );
					} catch(Exception) {
					}
				}

				if(args.Length > 0 && args[a].Contains("-p")) {
					String parameter = args[a].Substring(2);

					if(parameter.Contains("k")) {
						parameter = parameter.Substring(0, parameter.Length-1);
						parameter += "000";
					}

					if(parameter.Contains("m")) {
						parameter = parameter.Substring(0, parameter.Length-1);
						parameter += "000000";
					}

					try {
						progressPrintOnIteration = Int64.Parse(parameter );
						verboseOutput = true;
					} catch(Exception) {
					}
				}

				if(args.Length > 0 && args[a].Contains("-freeoff")) {
					freeOff = true;
				}

				if(args.Length > 0 && args[a].Contains("-wildsoff")) {
					wildsOff = true;
				}

				if(args.Length > 0 && args[a].Contains("-expandoff")) {
					wildExpandOff = true;
				}

				if(args.Length > 0 && args[a].Contains("-verify")) {
					printDataStructures();
					Environment.Exit(0);
				}

				if(args.Length > 0 && args[a].Contains("-help")) {
					printHelp();
					Console.WriteLine();
					Environment.Exit(0);
				}

				if(args.Length > 0 && args[a].Contains("-h")) {
					printHelp();
					Console.WriteLine();
					Environment.Exit(0);
				}
			}

			/*
			 * Simulation main loop.
			 */
			for (long g = 0L; g < numberOfSimulations; g++) {
				if(verboseOutput == true && g==0) {
					Console.WriteLine("Games\tRTP\tRTP(Base)\tRTP(Free)");
				}

				/*
				 * Print progress report.
				 */
				if(verboseOutput == true && g%progressPrintOnIteration == 0) {
					try {
						Console.Write(g);
						Console.Write("\t");
						Console.Write(String.Format("  {0:F6}", ((double) wonMoney / (double) lostMoney)));
						Console.Write("\t");
						Console.Write(String.Format("  {0:F6}", ((double) baseMoney / (double) lostMoney)));
						Console.Write("\t");
						Console.Write(String.Format("  {0:F6}", ((double) freeMoney / (double) lostMoney)));
					} catch( Exception ) {
					}
					Console.WriteLine();
				}

				totalNumberOfGames++;

				lostMoney += totalBet;

				singleBaseGame();
			}

			Console.WriteLine("********************************************************************************");
			printStatistics();
			Console.WriteLine("********************************************************************************");
		}
	}
}
