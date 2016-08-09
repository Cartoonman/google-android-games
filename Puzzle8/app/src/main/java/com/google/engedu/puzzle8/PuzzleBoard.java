package com.google.engedu.puzzle8;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;


public class PuzzleBoard {

    private static final int NUM_TILES = 3;
    private static final int[][] NEIGHBOUR_COORDS = {
            { -1, 0 },
            { 1, 0 },
            { 0, -1 },
            { 0, 1 }
    };
    private ArrayList<PuzzleTile> tiles = new ArrayList<PuzzleTile>();
    private static int steps = 0;
    PuzzleBoard previousBoard;

    PuzzleBoard(Bitmap bitmap, int parentWidth) {

        int tileCount = (NUM_TILES * NUM_TILES) - 1;
        int num = 1;
        bitmap = Bitmap.createScaledBitmap(bitmap, parentWidth, parentWidth,false);

        Log.i("imagewidth", ""+parentWidth);
        for (int i = 0; i < parentWidth; i += parentWidth / NUM_TILES) {
            for (int j = 0; j < parentWidth; j += parentWidth / NUM_TILES) {
                if (tileCount != 0) {
                    Log.i("ijfj", "i: "+i + " j: "+j + " parentwidth/numtiles: "+ parentWidth / NUM_TILES+" bitmap.width()"+bitmap.getWidth());
                    Bitmap bmp = bitmap.createBitmap(bitmap, j, i, parentWidth / NUM_TILES, parentWidth / NUM_TILES);
                    tileCount--;
                    PuzzleTile tile = new PuzzleTile(bmp, num);
                    num++;
                    tiles.add(tile);
                }
            }
        }
        PuzzleTile tile = null;
        tiles.add(tile);
    }
    PuzzleBoard(PuzzleBoard otherBoard) {
        tiles = (ArrayList<PuzzleTile>) otherBoard.tiles.clone();
        steps = otherBoard.steps + 1;
        previousBoard = new PuzzleBoard(otherBoard);
    }

    public void reset() {
        // Nothing for now but you may have things to reset once you implement the solver.
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        return tiles.equals(((PuzzleBoard) o).tiles);
    }

    public void draw(Canvas canvas) {
        if (tiles == null) {
            return;
        }
        for (int i = 0; i < NUM_TILES * NUM_TILES; i++) {
            PuzzleTile tile = tiles.get(i);
            if (tile != null) {
                tile.draw(canvas, i % NUM_TILES, i / NUM_TILES);
            }
        }
    }

    public boolean click(float x, float y) {
        for (int i = 0; i < NUM_TILES * NUM_TILES; i++) {
            PuzzleTile tile = tiles.get(i);
            if (tile != null) {
                if (tile.isClicked(x, y, i % NUM_TILES, i / NUM_TILES)) {
                    return tryMoving(i % NUM_TILES, i / NUM_TILES);
                }
            }
        }
        return false;
    }

    public boolean resolved() {
        for (int i = 0; i < NUM_TILES * NUM_TILES - 1; i++) {
            PuzzleTile tile = tiles.get(i);
            if (tile == null || tile.getNumber() != i)
                return false;
        }
        return true;
    }

    private int XYtoIndex(int x, int y) {
        return x + y * NUM_TILES;
    }

    protected void swapTiles(int i, int j) {
        PuzzleTile temp = tiles.get(i);
        tiles.set(i, tiles.get(j));
        tiles.set(j, temp);
    }

    private boolean tryMoving(int tileX, int tileY) {
        for (int[] delta : NEIGHBOUR_COORDS) {
            int nullX = tileX + delta[0];
            int nullY = tileY + delta[1];
            if (nullX >= 0 && nullX < NUM_TILES && nullY >= 0 && nullY < NUM_TILES &&
                    tiles.get(XYtoIndex(nullX, nullY)) == null) {
                swapTiles(XYtoIndex(nullX, nullY), XYtoIndex(tileX, tileY));
                return true;
            }

        }
        return false;
    }

    public ArrayList<PuzzleBoard> neighbours() {
        ArrayList<PuzzleBoard> neighs = new ArrayList<PuzzleBoard>();
        ArrayList<PuzzleTile> save =  new ArrayList<PuzzleTile>(tiles); // Saving current layout of tiles for repeated usage

        int nullindx = 0;
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i) == null) {
                nullindx = i;
                break;
            }
        }
            int iX = nullindx % NUM_TILES;
            int iY = nullindx / NUM_TILES;

            for (int[] delta : NEIGHBOUR_COORDS){

                int NewX = iX + delta[0];
                int NewY = iY + delta[1];

                Log.i("Coordinates", "iX: " + iX + " iY: " + iY + " NewX: " + NewX + " NewY: " + NewY);

                if(NewX >= 0 && NewX < NUM_TILES && NewY >= 0 && NewY < NUM_TILES){
                    Log.i("It did the thing!", "sdfghjk");
                    swapTiles(XYtoIndex(iX, iY), XYtoIndex(NewX, NewY));
                    PuzzleBoard k = new PuzzleBoard(this);
                    neighs.add(k);
                    tiles = new ArrayList<PuzzleTile>(save);

                }
            }
        return neighs;
    }



    public int priority() {

        int sum = steps;
        for(int i = 0; i < tiles.size(); i++){
            if(tiles.get(i) == null) continue;
            int nX = ((tiles.get(i).getNumber())-1) % NUM_TILES;
            int nY = ((tiles.get(i).getNumber())-1) / NUM_TILES;
            int correctX = i % NUM_TILES;
            int correctY = i / NUM_TILES;

            sum += Math.abs(nX-correctX);
            sum += Math.abs(nY-correctY);
        }
        return sum;
    }

}
