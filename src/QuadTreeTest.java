import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class QuadTreeTest {
    public static void main(String[] args) {
        //StdOut.print("Please input the width and height: ");
        int xlim,ylim;
        int cellNum; //细胞的数量
        int total = 0;
        /**
         * 获取标准输入
         */
        xlim =  StdIn.readInt();
        ylim = StdIn.readInt();
        int scale = 10;
        //StdOut.print("Please input the number of cells:");
        cellNum = StdIn.readInt();
        Cell[] cells = new Cell[cellNum];
        for(int i = 0; i <cellNum;i++)
        {
            //StdOut.printf("Please input the information of the %dth cell",i);
            double rx = StdIn.readDouble();
            double ry = StdIn.readDouble();
            double radius = StdIn.readDouble();
            double perceptionRange = StdIn.readDouble();
            String color = StdIn.readString();
            cells[i] = new Cell(rx,ry,radius,perceptionRange,color);
            cells[i].setID(i);
        }
        StdDraw.setCanvasSize(xlim*scale,ylim*scale);
        StdDraw.setXscale(0,xlim*scale);
        StdDraw.setYscale(0,ylim*scale);

        Rectangle boundary = new Rectangle(xlim/2,ylim/2,xlim/2,ylim/2);
        QuadTree quadTree = new QuadTree(boundary,4);

        //test the insert function.
        for(int i = 0; i < cellNum; i++)
        {
            if(quadTree.insert(cells[i]))
                total++;

        }
        StdOut.println(total);
        quadTree.show(scale);


        //test the query function.
//        Rectangle range = new Rectangle(xlim/4,ylim/4,xlim/10,ylim/10);
//        range.draw(scale);
//        ArrayList<Cell> found = new ArrayList<>();
//        found = quadTree.query(range);

        Circle circleRange = new Circle(xlim/2,ylim/2,ylim/6);

        circleRange.draw(scale);
        ArrayList<Cell> cfound = new ArrayList<>();
        cfound = quadTree.query(circleRange);

        StdOut.println(cfound.size());
        StdOut.println(quadTree.totalSearch);

        for(int i = 0; i < cfound.size();i++)
        {
            cfound.get(i).drawSelected(scale);
        }

    }
}
