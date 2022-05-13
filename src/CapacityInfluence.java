import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.Arrays;

public class CapacityInfluence {
    int xlim,ylim,cellNum;  //x y 轴的取值范围； 细胞的数量
    int scale = 5; // 画图的扩张倍数

    int quaryNum; //查询细胞的数量
    int[] ids;
    double[] times; //存储查询细胞的id 以及 对应的秒数
    Cell[] cells; //存储所有细胞

    double seconds; //循环的时间

    Cell[] result; // 查询到的结果

    //QuadTree quadTree; //我还没有new哦，需要了再new
    Rectangle boundary ;
    QuadTree quadTree ;
    double maxRadius = 0;

    private static double steplen = 1.0/15;

    /**
     * 读取标准输入啦
     */
    private void readInput()
    {
        xlim =  StdIn.readInt();
        ylim = StdIn.readInt();
        cellNum = StdIn.readInt();
        cells = new Cell[cellNum];


        for(int i = 0; i <cellNum;i++)
        {
            double rx = StdIn.readDouble();
            double ry = StdIn.readDouble();
            double radius = StdIn.readDouble();
            if(radius > maxRadius) maxRadius = radius;
            double perceptionRange = StdIn.readDouble();
            String color = StdIn.readString();
            cells[i] = new Cell(rx,ry,radius,perceptionRange,color);
            cells[i].setID(i);
        }

        boundary = new Rectangle(xlim/2.0,ylim/2.0,xlim/2.0,ylim/2.0);
        quadTree = new QuadTree(boundary,4,cells);
        quaryNum = StdIn.readInt();
        ids = new int[quaryNum];
        times = new double[quaryNum];
        result = new Cell[quaryNum];

        for(int i = 0; i < quaryNum ;i++)
        {
            times[i] = StdIn.readDouble();
            ids[i] = StdIn.readInt();
        }
        Arrays.sort(times);
        seconds = times[times.length-1]+1;
    }
    private void scanAllCells()
    {
        for(int i = 0; i < cellNum;i++)
        {
            Rectangle range = new Rectangle(cells[i].rx, cells[i].ry,cells[i].perceptionRange+maxRadius,cells[i].perceptionRange+maxRadius);
            ArrayList<Cell> found = quadTree.query(range);
        }
    }
    private static double pointToPoint(double x1, double y1, double x2, double y2) {
        double lineLength = 0;
        lineLength = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        return lineLength;
    }

    private boolean isInRange(Cell c1,Cell c2){//c2是否在c1范围里，是则 return true
        if(Math.abs(c1.rx-c2.rx) <= c1.perceptionRange && Math.abs(c1.ry-c2.ry) <= c1.perceptionRange)//*圆心在内
            return true;
        else if(Math.abs(c1.rx-c2.rx) <= c1.perceptionRange+c2.radius && Math.abs(c1.ry-c2.ry) <= c1.perceptionRange)
            return true;
        else if(Math.abs(c1.rx-c2.rx) <= c1.perceptionRange && Math.abs(c1.ry-c2.ry) <= c1.perceptionRange+c2.radius)
            return true;
        else if(pointToPoint(c2.rx,c2.ry,c1.rx-c1.perceptionRange,c1.ry+c1.perceptionRange) <= c2.radius
                || pointToPoint(c2.rx,c2.ry,c1.rx-c1.perceptionRange,c1.ry-c1.perceptionRange) <= c2.radius
                || pointToPoint(c2.rx,c2.ry,c1.rx+c1.perceptionRange,c1.ry+c1.perceptionRange) <= c2.radius
                || pointToPoint(c2.rx,c2.ry,c1.rx+c1.perceptionRange,c1.ry-c1.perceptionRange) <= c2.radius)
            return true;

        return false;
    }



    private void scanBrute()
    {
        for(int i = 0; i < cellNum;i++)
        {
            for(int j = 0; j < cellNum;j++)
            {
                if(i != j)
                {
                    isInRange(cells[i],cells[j]);
                }
            }
        }
    }


    private void loop(Stopwatch stopwatch)
    {
        for(int i = 0; i < seconds; i++)
        {//i表示循环的秒数，第i秒就在
            StdOut.println("在前"+ i+ "秒一共遍历了"+quadTree.totalSearch+"个细胞，当前用时 ："+ stopwatch.elapsedTime()+"s");
            for(int j = 0; j < 15; j++)
            {
                scanAllCells();
            }
        }
    }

    private void loop_Brute(Stopwatch stopwatch)
    {
        for(int i = 0; i < seconds; i++)
        {//i表示循环的秒数，第i秒就在
            StdOut.println("在前"+ i+ "秒一共遍历了"+quadTree.totalSearch+"个细胞，当前用时 ："+ stopwatch.elapsedTime()+"s");
            for(int j = 0; j < 15; j++)
            {
                scanBrute();
            }
        }
    }


    public static void main(String[] args) {
        CapacityInfluence cI = new CapacityInfluence();
        cI.readInput();
        Stopwatch timer = new Stopwatch();
        cI.loop_Brute(timer);
    }

}
