import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.Timer;

public class QuadTreeShow {
    public static void main(String[] args) {

        int scale = 10;
        int xlim = 150,ylim = 80;
        String[] colors = {"r","g","b","y"};
        StdDraw.setCanvasSize(xlim*scale,ylim*scale);
        StdDraw.setXscale(0,xlim*scale);
        StdDraw.setYscale(0,ylim*scale);
        Rectangle boundary = new Rectangle(xlim/2,ylim/2,xlim/2,ylim/2);

        ArrayList<Cell> cells = new ArrayList<>();

        QuadTree quadTree = new QuadTree(boundary,4);

        while(true)
        {
            double MouseX,MouseY, cellX,cellY;
            if(StdDraw.isMousePressed()){
                MouseX = StdDraw.mouseX();
                MouseY = StdDraw.mouseY();
                cellX = MouseX/scale;
                cellY = MouseY/scale;

                double cellRadius = StdRandom.uniform(2,4);
                Cell cell = new Cell(cellX,cellY,cellRadius,2,colors[StdRandom.uniform(4)]);
                quadTree.insert(cell);
                quadTree.show(scale);
                StdDraw.pause(100);

            }
        }
    }
}
