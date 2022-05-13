import edu.princeton.cs.algs4.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import static java.lang.Math.sqrt;

public class main_Brute {
    static int xlim;
    static int ylim;
    int cellNum;  //x y 轴的取值范围； 细胞的数量
    int scale = 1; // 画图的扩张倍数

    int quaryNum; //查询细胞的数量
    int[] ids;
    double[]    times; //存储查询细胞的id 以及 对应的秒数
    static Cell[] cells; //存储所有细胞

    double seconds; //循环的时间

    Cell[] result; // 查询到的结果

    private static final double steplen = 1.0/15;



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
            double perceptionRange = StdIn.readDouble();
            String color = StdIn.readString();
            cells[i] = new Cell(rx,ry,radius,perceptionRange,color);
            cells[i].setID(i);
        }

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

    /**
     * 改变颜色完善这里
     */

    private void changeAllColor()
    {
        //scan all cells
        for(int i = 0; i < cells.length;i++){//当前cell
            //清零之前的数据
            cells[i].Rcount=0;
            cells[i].Gcount=0;
            cells[i].Bcount=0;
            cells[i].Ycount=0;
            for(int j = 0; j < cells.length;j++)//遍历所有cell
            {
                //判断cells[j]是否在cells[i]的范围内,相切
                if(isInRange(cells[i],cells[j])){
                    //记录周围cell[j]颜色
                    if(cells[j].color== Color.RED)
                        cells[i].Rcount++;
                    else if(cells[j].color==Color.GREEN)
                        cells[i].Gcount++;
                    else if(cells[j].color==Color.BLUE)
                        cells[i].Bcount++;
                    else
                        cells[i].Ycount++;
                }
                //不满足就continue
            }
            //减去自己
            if(cells[i].color==Color.RED)
                cells[i].Rcount --;
            else if(cells[i].color==Color.GREEN)
                cells[i].Gcount --;
            else if(cells[i].color==Color.BLUE)
                cells[i].Bcount --;
            else
                cells[i].Ycount --;
        }
        //change color
        for(int i=0;i<cells.length;i++){
            //根据4个count来变化颜色
            if(cells[i].color==Color.RED){
                if(cells[i].Rcount>=3 && (double)cells[i].Rcount/(cells[i].Rcount+cells[i].Gcount+cells[i].Bcount+cells[i].Ycount)>0.7)
                    cells[i].color = Color.GREEN;
                else if(cells[i].Ycount>=1 && (double)cells[i].Ycount/(cells[i].Rcount+cells[i].Gcount+cells[i].Bcount+cells[i].Ycount)<0.1)
                    cells[i].color = Color.YELLOW;
                //else cell.color = Color.BLUE;
            }
            else if(cells[i].color==Color.GREEN){
                if(cells[i].Gcount>=3 && (double)cells[i].Gcount/(cells[i].Rcount+cells[i].Gcount+cells[i].Bcount+cells[i].Ycount)>0.7)
                    cells[i].color = Color.BLUE;
                else if(cells[i].Rcount>=1 && (double)cells[i].Rcount/(cells[i].Rcount+cells[i].Gcount+cells[i].Bcount+cells[i].Ycount)<0.1)
                    cells[i].color = Color.RED;
                //else cell.color = Color.YELLOW;
            }
            else if(cells[i].color==Color.BLUE){
                if(cells[i].Bcount>=3 && (double)cells[i].Bcount/(cells[i].Rcount+cells[i].Gcount+cells[i].Bcount+cells[i].Ycount)>0.7)
                    cells[i].color = Color.YELLOW;
                else if(cells[i].Gcount>=1 && (double)cells[i].Gcount/(cells[i].Rcount+cells[i].Gcount+cells[i].Bcount+cells[i].Ycount)<0.1)
                    cells[i].color = Color.GREEN;
                //else cell.color = Color.RED;
            }
            else {
                if(cells[i].Ycount>=3 && (double)cells[i].Ycount/(cells[i].Rcount+cells[i].Gcount+cells[i].Bcount+cells[i].Ycount)>0.7)
                    cells[i].color = Color.RED;
                else if(cells[i].Gcount>=1 && (double)cells[i].Gcount/(cells[i].Rcount+cells[i].Gcount+cells[i].Bcount+cells[i].Ycount)<0.1)
                    cells[i].color = Color.BLUE;
                //else cell.color = Color.RED;
            }
        }
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
    private static double pointToPoint(double x1, double y1, double x2, double y2) {
        double lineLength = 0;
        lineLength = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        return lineLength;
    }

//    private static double getDistance(Cell c1, Cell c2)
//    {
//        return sqrt((c1.rx-c2.rx)*(c1.rx-c2.rx)+(c1.ry-c2.ry) *(c1.ry-c2.ry));
//    }

    /**
     * 移动细胞完善这里
     */
    private void moveAllCells()
    {
        for(int i=0;i<cells.length;i++){
            moveCell(cells[i]);
        }
    }
    private void moveCell(Cell cell){
        ArrayList<Double> distance = new ArrayList<>();
        Cell temp = new Cell(cell.rx,cell.ry,cell.radius,cell.color,cell.ID);
        if(temp.color == Color.RED){temp.ry += steplen;}
        else if(temp.color==Color.GREEN){temp.ry += -steplen;}
        else if(temp.color==Color.BLUE){temp.rx += -steplen;}
        else if(temp.color==Color.YELLOW){temp.rx += steplen;}

        if(hitWall(temp)>=0) distance.add(hitWall(temp));
        if(hitCell(temp)!=-1) distance.add(hitCell(temp));

        if(distance.size()==0) moveOneStep(cell);
        else {
            distance.sort(Comparator.naturalOrder());
            moveMin(cell,distance.get(0));
        }


    }
    private void moveMin(Cell cell, double distance){
        if(cell.color == Color.RED){
            cell.ry += distance;
        }
        else if(cell.color==Color.GREEN){
            cell.ry += -distance;
        }
        else if(cell.color==Color.BLUE){
            cell.rx += -distance;
        }
        else if(cell.color==Color.YELLOW){
            cell.rx += distance;
        }
    }

    private void moveOneStep(Cell cell)
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

    private double hitWall(Cell temp){
        if(temp.color==Color.red){
            if(temp.ry + temp.radius >= ylim) return ylim-temp.radius-(temp.ry-steplen);
        }
        else if(temp.color==Color.GREEN){
            if(temp.ry - temp.radius <= 0) return temp.ry-temp.radius+steplen;
        }
        else if(temp.color==Color.BLUE){
            if(temp.rx - temp.radius <= 0) return temp.rx-temp.radius+steplen;
        }
        else if(temp.color==Color.YELLOW){
            if(temp.rx + temp.radius >= xlim) return xlim-(temp.rx+temp.radius-steplen);
        }
        //do not hit wall
        return -1;
    }
    private double hitCell(Cell temp){
        ArrayList<Double> min = new ArrayList<>();
        //Circle circle = new Circle(temp.rx, temp.ry,temp.radius);
        //ArrayList<Cell> found = quadTree.query(circle);
        ArrayList<Cell> found = new ArrayList<>();
        for(int i=0;i<cells.length;i++){
            if(temp.ID==cells[i].ID){}//不和自己比
            else {
                if(getDistance(temp,cells[i]) < cells[i].radius+temp.radius)//会撞，记录cell
                    found.add(cells[i]);
            }
        }

        for(int i=0;i<found.size();i++){
            min.add(getMinDis(found.get(i),temp));
        }
        if(min.size()>0) {
            min.sort(Comparator.naturalOrder());
            return min.get(0);
        }
        return -1;
    }
    private double getMinDis(Cell cell,Cell temp){
        if(temp.color == Color.RED)
            return cell.ry-(temp.ry-steplen)-Math.sqrt((cell.radius+temp.radius)*(cell.radius+temp.radius)-(cell.rx-temp.rx)*(cell.rx-temp.rx) );
        else if(temp.color==Color.GREEN)
            return temp.ry+steplen-cell.ry-Math.sqrt((cell.radius+temp.radius)*(cell.radius+temp.radius)-(cell.rx-temp.rx)*(cell.rx-temp.rx) );
        else if(temp.color==Color.BLUE)
            return temp.rx+steplen-cell.rx-Math.sqrt((cell.radius+temp.radius)*(cell.radius+temp.radius)-(cell.ry-temp.ry)*(cell.ry-temp.ry) );
        else if(temp.color==Color.YELLOW)
            return cell.rx-(temp.rx-steplen)-Math.sqrt((cell.radius+temp.radius)*(cell.radius+temp.radius)-(cell.ry-temp.ry)*(cell.ry-temp.ry) );

        return -1;
    }
    private double getDistance(Cell c1,Cell c2){
        return Math.sqrt((c1.rx-c2.rx)*(c1.rx-c2.rx)+(c1.ry-c2.ry)*(c1.ry-c2.ry));
    }


    /**
     * 画出所有的细胞
     */
    private void drawAllCells()
    {
        for(int k = 0 ; k < cellNum; k++)
        {//可以加异常处理，判断cells是否为null，但没必要
            cells[k].draw(scale);
        }
        StdDraw.show();
    }

    /**
     * 设置画布
     */
    private void setCanvas()
    {
        StdDraw.setCanvasSize(xlim*scale,ylim*scale);
        StdDraw.setXscale(0,xlim*scale);
        StdDraw.setYscale(0,ylim*scale);
        StdDraw.enableDoubleBuffering();
    }

    //循环跟新位置和颜色
    private void loop(Stopwatch stopwatch)
    {
        int k = 0;
        for(int i = 0; i < seconds; i++)
        {//i表示循环的秒数，第i秒就在
            double t1 = stopwatch.elapsedTime();
            for(int j = 0; j < 15; j++)
            {

                while(k <quaryNum && (i + (double)j/15) <= times[k]&& times[k] <(i+(j+1.0)/15))
                {
                    result[k] = new Cell (cells[ids[k]].rx,cells[ids[k]].ry,cells[ids[k]].color);
                    k++;
                }

                //StdDraw.pause(1000/15);
                double t0 = stopwatch.elapsedTime();
                moveAllCells();
                double t2 = stopwatch.elapsedTime();
                StdOut.println("移动细胞用时："+(t2 - t0));
                StdDraw.clear();
                drawAllCells();

                changeAllColor();
                double t3 = stopwatch.elapsedTime();
                StdOut.println("改变颜色用时："+(t3-t2));
                drawAllCells();
                StdDraw.clear();
            }
            StdOut.println("当前帧数 ："+ 15/(stopwatch.elapsedTime()-t1));
        }
    }

    private void printResult()
    {
        for(int i = 0; i < quaryNum; i++)
        {
            StdOut.println(result[i]);
        }
    }


    /**
     * main函数在这里
     * @param args
     */
    public static void main(String[] args) {
        main_Brute brute = new main_Brute();
        brute.readInput();
        brute.setCanvas();
        Stopwatch timer = new Stopwatch();

        brute.loop(timer);
        brute.printResult();
    }

}
//40 50 3 0.5 1.5 0.5 6.1 r 1.1 0.5 0.3 5 g 1.8 1 0.1 4 b 3 4 2 10 0 14 1
