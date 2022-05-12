import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.Arrays;

public class QuadTreeQueryShow {
    int xlim;
    int ylim;
    int cellNum;  //x y 轴的取值范围； 细胞的数量
    int scale = 10; // 画图的扩张倍数

    int quaryNum; //查询细胞的数量
    int[] ids;
    double[] times; //存储查询细胞的id 以及 对应的秒数
    Cell[] cells; //存储所有细胞

    double seconds; //循环的时间

    Cell[] result; // 查询到的结果
    QuadTree quadTree;



    private void readInput()
    {
        xlim =  StdIn.readInt();
        ylim = StdIn.readInt();
        Rectangle bound = new Rectangle(xlim/2,ylim/2,xlim/2,ylim/2);
        cellNum = StdIn.readInt();
        cells = new Cell[cellNum];



        for(int i = 0; i <cellNum;i++)
        {

            double rx = StdIn.readDouble();
            double ry = StdIn.readDouble();
            double radius = StdIn.readDouble();
            double perceptionRange = StdIn.readDouble();
            String color = StdIn.readString();
            cells[i] = new Cell(rx,ry,radius,perceptionRange,color);
            cells[i].setID(i);
        }
        quadTree = new QuadTree(bound,4,cells);

        quaryNum = StdIn.readInt();
        ids = new int[quaryNum];
        times = new double[quaryNum];
        result = new Cell[quaryNum];


        for(int i = 0; i < quaryNum ;i++)
        {
            times[i] = StdIn.readDouble();
            ids[i] = StdIn.readInt();
        }
        //seconds = StdStats.max(times)+1;
        Arrays.sort(times);
        seconds = times[times.length-1]+1;
    }

    private void setCanvas()
    {
        StdDraw.setCanvasSize(xlim*scale,ylim*scale);
        StdDraw.setXscale(0,xlim*scale);
        StdDraw.setYscale(0,ylim*scale);
        StdDraw.enableDoubleBuffering();
    }

    private int highLighted(Rectangle range)
    {
        ArrayList<Cell> found = new ArrayList<>();


        found = quadTree.query(range);
        for(int i = 0; i < found.size();i++)
        {
            found.get(i).drawSelected(scale);
        }
        StdDraw.show();
        return found.size();
    }
    private void drawAllCells()
    {
        for(int k = 0 ; k < cellNum; k++)
        {//可以加异常处理，判断cells是否为null，但没必要
            cells[k].draw(scale);
        }
        StdDraw.show();
    }




    public static void main(String[] args) {
        QuadTreeQueryShow qTQS = new QuadTreeQueryShow();
        qTQS.readInput();
        qTQS.setCanvas();
        Rectangle range;
        double halfWidth = 20;
        double halfHeight = 20;
        while(true)
        {
            qTQS.drawAllCells();
            double MouseX,MouseY;

            MouseY = StdDraw.mouseY();
            MouseX = StdDraw.mouseX();
            range = new Rectangle(MouseX/ qTQS.scale,MouseY/ qTQS.scale,halfWidth,halfHeight);
            range.draw(qTQS.scale);
            int totalfound = qTQS.highLighted(range);
            StdOut.println("我们一共遍历了 " + qTQS.quadTree.totalSearch+ " 个细胞");
            StdOut.println("一共找到了 "+ totalfound+ " 个细胞");
            qTQS.quadTree.totalSearch = 0;
            StdDraw.clear();

        }



    }
}
