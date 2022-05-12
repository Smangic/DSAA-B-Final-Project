import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdStats;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class main_QuadTree {
    int xlim,ylim,cellNum;  //x y 轴的取值范围； 细胞的数量
    int scale = 10; // 画图的扩张倍数

    int quaryNum; //查询细胞的数量
    int[] ids;
    double[] times; //存储查询细胞的id 以及 对应的秒数
    Cell[] cells; //存储所有细胞

    double seconds; //循环的时间

    Cell[] result; // 查询到的结果

    //QuadTree quadTree; //我还没有new哦，需要了再new
    Rectangle boundary = new Rectangle(xlim/2,ylim/2,xlim/2,ylim/2);
    QuadTree quadTree = new QuadTree(boundary,4);

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
        Arrays.sort(times);
        seconds = times[times.length-1]+1;
    }

    /**
     * 改变颜色完善这里
     */
    private void changeAllColor() {
        for (int i = 0; i < cells.length; i++) {
            //Rectangle rectangle = new Rectangle(cells[i].rx, cells[i].ry,cells[i].perceptionRange,cells[i].perceptionRange);
            Circle circle = new Circle(cells[i].rx, cells[i].ry,cells[i].perceptionRange);
            //得到周围细胞
            ArrayList<Cell> found = new ArrayList<>();
            found = quadTree.query(circle);

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

    private void changeColor(Cell cell)
    {

        //根据4个count来变化颜色
        if(cell.color== Color.RED){
            if(cell.Rcount>=3 && (double)cell.Rcount/(cell.Rcount+cell.Gcount+cell.Bcount+cell.Ycount)>0.7)
                cell.color = Color.GREEN;
            else if(cell.Ycount>=1 && (double)cell.Ycount/(cell.Rcount+cell.Gcount+cell.Bcount+cell.Ycount)<0.1)
                cell.color = Color.YELLOW;
            //else cell.color = Color.BLUE;
        }
        else if(cell.color==Color.GREEN){
            if(cell.Gcount>=3 && (double)cell.Gcount/(cell.Rcount+cell.Gcount+cell.Bcount+cell.Ycount)>0.7)
                cell.color = Color.BLUE;
            else if(cell.Rcount>=1 && (double)cell.Rcount/(cell.Rcount+cell.Gcount+cell.Bcount+cell.Ycount)<0.1)
                cell.color = Color.RED;
            //else cell.color = Color.YELLOW;
        }
        else if(cell.color==Color.BLUE){
            if(cell.Bcount>=3 && (double)cell.Bcount/(cell.Rcount+cell.Gcount+cell.Bcount+cell.Ycount)>0.7)
                cell.color = Color.YELLOW;
            else if(cell.Gcount>=1 && (double)cell.Gcount/(cell.Rcount+cell.Gcount+cell.Bcount+cell.Ycount)<0.1)
                cell.color = Color.GREEN;
            //else cell.color = Color.RED;
        }
        else {
            if(cell.Ycount>=3 && (double)cell.Ycount/(cell.Rcount+cell.Gcount+cell.Bcount+cell.Ycount)>0.7)
                cell.color = Color.BLUE;
            else if(cell.Gcount>=1 && (double)cell.Gcount/(cell.Rcount+cell.Gcount+cell.Bcount+cell.Ycount)<0.1)
                cell.color = Color.GREEN;
            //else cell.color = Color.RED;
        }
    }



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
                if(getDistance(temp,cells[i])<cells[i].radius+temp.radius)//会撞，记录cell
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
    }

    /**
     * 设置画布
     */
    private void setCanvas()
    {
        StdDraw.setCanvasSize(xlim*scale,ylim*scale);
        StdDraw.setXscale(0,xlim*scale);
        StdDraw.setYscale(0,ylim*scale);
    }

    //循环跟新位置和颜色
    private void loop()
    {
        int k = 0;
        for(int i = 0; i < seconds; i++)
        {//i表示循环的秒数，第i秒就在
//            for(int k  = 0; k < quaryNum; k++)
//            {//如果查询名单中有第i秒时的某个细胞，则。。。
//                if(times[k]==i){
//                    result[k] = cells[ids[k]];
//                }
//            }

            for(int j = 0; j < 15; j++)
            {
                while(k <quaryNum && i + j/15 <= times[k]&& times[k] < i+(j+1)/15)
                {
                    result[k] = cells[ids[k]];
                    k++;
                }
                moveAllCells();
                //drawAllCells();
                changeAllColor();
                //drawAllCells();
                //StdDraw.clear();
            }
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
        main_QuadTree Main_quadTree = new main_QuadTree();


        Main_quadTree.readInput();
        Main_quadTree.setCanvas();
        Main_quadTree.loop();
        Main_quadTree.printResult();
    }
}
