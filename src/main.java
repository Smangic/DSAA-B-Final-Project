
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class main {
    // int xlim,ylim,cellNum;

    // int scale; // 画图的扩张倍数

    public static double steplen = 1;




    // public void readInput()
    // {
    //     xlim =  StdIn.readInt();
    //     ylim = StdIn.readInt();
    //     //StdOut.print("Please input the number of cells:");
    //     cellNum = StdIn.readInt();
    //     Cell[] cells = new Cell[cellNum];

    //     //HashMap<Integer,Cell> cellHashMap = new HashMap<Integer,Cell>();

    //     for(int i = 0; i <cellNum;i++)
    //     {
    //         //StdOut.printf("Please input the information of the %dth cell",i);
    //         double rx = StdIn.readDouble();
    //         double ry = StdIn.readDouble();
    //         double radius = StdIn.readDouble();
    //         double perceptionRange = StdIn.readDouble();
    //         String color = StdIn.readString();
    //         cells[i] = new Cell(rx,ry,radius,perceptionRange,color);
    //         cells[i].setID(i);
    //         //cellHashMap.put(i,cells[i]);
    //     }
    // }




    public static void main(String[] args) {
        
        //StdOut.print("Please input the width and height: ");
        int xlim,ylim;
        int cellNum; //细胞的数量
        int scale = 10;
        /**
         * 获取标准输入
         */
        xlim =  StdIn.readInt();
        ylim = StdIn.readInt();
        //StdOut.print("Please input the number of cells:");
        cellNum = StdIn.readInt();
        Cell[] cells = new Cell[cellNum];


        //HashMap<Integer,Cell> cellHashMap = new HashMap<Integer,Cell>();

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
            //cellHashMap.put(i,cells[i]);
        }


        //查询部分的
//        int quaryNum = StdIn.readInt();
//        int[] ids = new int[quaryNum];
//        int[] times = new int[quaryNum];
//        for(int i = 0; i < quaryNum ;i++)
//        {
//            ids[i] = StdIn.readInt();
//            times[i] = StdIn.readInt();
//        }


        StdDraw.setCanvasSize(xlim*scale,ylim*scale);
        StdDraw.setXscale(0,xlim*scale);
        StdDraw.setYscale(0,ylim*scale);
        Rectangle boundary = new Rectangle(xlim/2,ylim/2,xlim/2,ylim/2);

        QuadTree quadTree;
        int seconds = 10;
        for(int i = 0; i< seconds;i++)
        {
            for(int j = 0; j < 15; j++)
            {
                move_all_cells(cells,xlim,ylim);
                for(int k = 0 ; k < cellNum; k++)
                {
                    cells[k].draw(scale);
                }
                quadTree = new QuadTree(boundary,4,cells);
                changeAllColor(cells,quadTree);
                for(int k = 0 ; k < cellNum; k++)
                {
                    cells[k].draw(scale);
                }
                StdDraw.clear();
            }
        }
    }





    /**
     * 求两个细胞间的距离
     * @param c1
     * @param c2
     * @return
     */
    private static double getDistance(Cell c1, Cell c2)
    {
        double distance =0;
        distance =Math.sqrt((c1.rx-c2.rx)+(c1.ry-c2.ry) )-c1.radius-c2.radius;
        return distance;
    }


    public static Cell return_moved_newcell(Cell cell)
    {
        Cell moved_cell = new Cell();
        moved_cell.color = cell.color;
        moved_cell.rx = cell.rx;
        moved_cell.ry = cell.ry;


        if(cell.color == Color.RED){
            moved_cell.ry += steplen;

        }
        else if(cell.color==Color.GREEN){
            moved_cell.ry += -steplen;
        }
        else if(cell.color==Color.BLUE){
            moved_cell.rx += -steplen;
        }
        else if(cell.color==Color.YELLOW){
            moved_cell.rx += steplen;
        }
        return moved_cell;
    }


// 点到直线的最短距离的判断 点（x0,y0） 到由两点组成的线段（x1,y1） ,( x2,y2 )

    private static double pointToLine(double x1, double y1, double x2, double y2, double x0, double y0) {

        double space = 0;

        double a, b, c;

        a = lineSpace(x1, y1, x2, y2);// 线段的长度

        b = lineSpace(x1, y1, x0, y0);// (x1,y1)到点的距离

        c = lineSpace(x2, y2, x0, y0);// (x2,y2)到点的距离

        if (c+b == a) {//点在线段上

            space = 0;

            return space;

        }

        if (a <= 0.000001) {//不是线段，是一个点

            space = b;

            return space;

        }

        if (c * c >= a * a + b * b) { //组成直角三角形或钝角三角形，(x1,y1)为直角或钝角

            space = b;

            return space;

        }

        if (b * b >= a * a + c * c) {//组成直角三角形或钝角三角形，(x2,y2)为直角或钝角

            space = c;

            return space;

        }
        //组成锐角三角形，则求三角形的高
        double p = (a + b + c) / 2;// 半周长

        double s = Math.sqrt(p * (p - a) * (p - b) * (p - c));// 海伦公式求面积

        space = 2 * s / a;// 返回点到线的距离（利用三角形面积公式求高）

        return space;

    }


// 计算两点之间的距离

    private static double lineSpace(double x1, double y1, double x2, double y2) {

        double lineLength = 0;

        lineLength = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2)

                * (y1 - y2));

        return lineLength;


    }



    public static boolean whether_crash(Cell c1_tomove,Cell c2)
    {
        if(lineSpace(c1_tomove.rx,c1_tomove.ry,c2.rx,c2.ry)<=c1_tomove.radius+c2.radius){
            return false;
        }
        else if(lineSpace(return_moved_newcell(c1_tomove).rx ,return_moved_newcell(c1_tomove).ry,c2.rx,c2.ry)<=c1_tomove.radius+c2.radius)
        {return false;}
        else if (c1_tomove.color == Color.RED || c1_tomove.color==Color.GREEN){
            //pointToLine(x1, y1, x2, y2, x0, y0);
            if(pointToLine(c1_tomove.rx, c1_tomove.ry-c1_tomove.radius, return_moved_newcell(c1_tomove).rx, return_moved_newcell(c1_tomove).ry-c1_tomove.radius,c2.rx, c2.ry)<=c2.radius){return false;}
            if(pointToLine(c1_tomove.rx, c1_tomove.ry+c1_tomove.radius, return_moved_newcell(c1_tomove).rx, return_moved_newcell(c1_tomove).ry+c1_tomove.radius,c2.rx, c2.ry)<=c2.radius){return false;}

        }
        else if(c1_tomove.color == Color.BLUE||c1_tomove.color==Color.YELLOW){
            if(pointToLine(c1_tomove.rx-c1_tomove.radius, c1_tomove.ry, return_moved_newcell(c1_tomove).rx-c1_tomove.radius, return_moved_newcell(c1_tomove).ry,c2.rx, c2.ry)<=c2.radius){return false;}
            if(pointToLine(c1_tomove.rx+c1_tomove.radius, c1_tomove.ry, return_moved_newcell(c1_tomove).rx+c1_tomove.radius, return_moved_newcell(c1_tomove).ry,c2.rx, c2.ry)<=c2.radius){return false;}
        }
        else {return true;}
        return false;
    }


    public static void move_one_step(Cell cell)
    {
        if(cell.color == Color.RED){
            cell.ry += steplen;
        }
        else if(cell.color==Color.GREEN){
            cell.ry += -steplen;
        }
        else if(cell.color==Color.BLUE){
            cell.rx += -steplen;
        }
        else if(cell.color==Color.YELLOW){
            cell.rx += steplen;
        }

    }

    private static void move_one_cell(Cell cell, Cell[] cells,double xlim,double ylim)
    {
        for(int i = 0; i < cells.length;i++){
            if(return_moved_newcell(cell).rx + cell.radius >= xlim ||
            return_moved_newcell(cell).ry + cell.radius >= ylim||
            return_moved_newcell(cell).rx - cell.radius <= 0||
            return_moved_newcell(cell).ry - cell.radius <= 0){continue;}
            else if(whether_crash(return_moved_newcell(cell), cells[i])){
                move_one_step(cell);
            }
        }
    }

    private static void move_all_cells(Cell[] cells, double xlim,double ylim)
    {
        for(int i =0; i < cells.length; i++)
        {
            move_one_cell(cells[i],cells,xlim,ylim);
        }
    }


    /**
     * Red -> Green: 不包含自身，感知范围内，>=3个Red细胞，且Red细胞的占比>70%
     * Red -> Yellow: 上一条件不满足; 感知范围内有>=1个Yellow细胞，且Yellow细胞的占比<10%
     *
     * Green -> Blue: 不包含自身，感知范围内，>=3个Green细胞，且Green细胞的占比>70%
     * Green -> Red: 上一条件不满足; 感知范围内有>=1个Red细胞，且Red细胞的占比<10%
     *
     * Blue -> Yellow: 不包含自身，感知范围内，>=3个Blue细胞，且Blue细胞的占比>70%
     * Blue -> Green: 上一条件不满足; 感知范围内有>=1个Green细胞，且Green细胞的占比<10%
     *
     * Yellow -> Blue: 不包含自身，感知范围内，>=3个Yellow细胞，且Yellow细胞的占比>70%
     * Yellow -> Green: 上一条件不满足; 感知范围内有>=1个Green细胞，且Green细胞的占比<10%
     */
    public static void changeAllColor(Cell[] cells,QuadTree quadTree)
    {
        for (int i = 0; i < cells.length; i++) {
            Rectangle rectangle = new Rectangle(cells[i].rx, cells[i].ry,cells[i].perceptionRange,cells[i].perceptionRange);
            //得到周围细胞
            ArrayList<Cell> found = new ArrayList<>();
            found = quadTree.query(rectangle);

            for (int j = 0; j < found.size(); j++) {
                if (cells[j].color == Color.RED)
                    cells[i].Rcount++;
                else if (cells[j].color == Color.GREEN)
                    cells[i].Gcount++;
                else if (cells[j].color == Color.BLUE)
                    cells[i].Bcount++;
                else
                    cells[i].Ycount++;
            }
            if (cells[i].color == Color.RED)
                cells[i].Rcount--;
            else if (cells[i].color == Color.GREEN)
                cells[i].Gcount--;
            else if (cells[i].color == Color.BLUE)
                cells[i].Bcount--;
            else
                cells[i].Ycount--;
        }
        //color change
        for (int i = 0; i < cells.length; i++) {
            changeColor(cells[i]);
        }
    }


    public static void changeColor(Cell cell)
    {
        //根据4个count来变化颜色
        if(cell.color==Color.RED){
            if(cell.Rcount>=3 && (double)cell.Rcount/(cell.Rcount+cell.Gcount+cell.Bcount+cell.Ycount)>0.7)
                cell.color = Color.GREEN;
            else if(cell.Ycount>=1 && (double)cell.Ycount/(cell.Rcount+cell.Gcount+cell.Bcount+cell.Ycount)<0.1)
                cell.color = Color.YELLOW;
            else cell.color = Color.BLUE;
        }
        else if(cell.color==Color.GREEN){
            if(cell.Gcount>=3 && (double)cell.Gcount/(cell.Rcount+cell.Gcount+cell.Bcount+cell.Ycount)>0.7)
                cell.color = Color.BLUE;
            else if(cell.Rcount>=1 && (double)cell.Rcount/(cell.Rcount+cell.Gcount+cell.Bcount+cell.Ycount)<0.1)
                cell.color = Color.RED;
            else cell.color = Color.YELLOW;
        }
        else if(cell.color==Color.BLUE){
            if(cell.Bcount>=3 && (double)cell.Bcount/(cell.Rcount+cell.Gcount+cell.Bcount+cell.Ycount)>0.7)
                cell.color = Color.YELLOW;
            else if(cell.Gcount>=1 && (double)cell.Gcount/(cell.Rcount+cell.Gcount+cell.Bcount+cell.Ycount)<0.1)
                cell.color = Color.GREEN;
            else cell.color = Color.RED;
        }
        else {
            if(cell.Ycount>=3 && (double)cell.Ycount/(cell.Rcount+cell.Gcount+cell.Bcount+cell.Ycount)>0.7)
                cell.color = Color.BLUE;
            else if(cell.Gcount>=1 && (double)cell.Gcount/(cell.Rcount+cell.Gcount+cell.Bcount+cell.Ycount)<0.1)
                cell.color = Color.GREEN;
            else cell.color = Color.RED;
        }
    }




    /**不要求同时移动，每个细胞是轮流移动的。
     * 根据vx vy 改变 rx，ry。
     * Red：向上移动1/15
     * Green：向下移动1/15
     * Blue：向左移动1/15
     * Yellow：向右移动1/15
     * 不能有重叠，如果间距小于1/15，就移到刚好挨到就好
     * 如果有撞击就停下，障碍消失，继续移动（下一个1/15）
     * TODO: 确定移动距离，移动方向，以及判断是否移动
     */
    public static void move()
    {

    }




    /**
     * 计算移动方向上的最小长度，如果小于1/15 以最小长度为准
     * @param cells
     * @return
     */
    private static double minLength(Cell[] cells)
    {
        return 0;
    }





    private static void redraw(Cell[] cells,int scale)
    {
        StdDraw.clear();
        for(int i = 0; i < cells.length;i++)
        {
            cells[i].draw(scale);
        }
        StdDraw.show();
    }

}
