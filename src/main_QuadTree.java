import edu.princeton.cs.algs4.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Timer;

public class main_QuadTree {
    int xlim, ylim, cellNum;  //x y 轴的取值范围； 细胞的数量
    double scale = 100; // 画图的扩张倍数

    int quaryNum; //查询细胞的数量
    int[] ids;
    double[] times; //存储查询细胞的id 以及 对应的秒数
    Cell[] cells; //存储所有细胞
    double maxRadius = 0;//最大半径

    double seconds; //循环的时间

    Cell[] result; // 查询到的结果

    //QuadTree quadTree; //我还没有new哦，需要了再new
//    Rectangle boundary = new Rectangle(xlim/2.0,ylim/2.0,xlim/2.0,ylim/2.0);
//    QuadTree quadTree = new QuadTree(boundary,4);
    Rectangle boundary;
    QuadTree quadTree;

    private static double steplen = 1.0 / 15;

    double sum = 0;


    /**
     * 读取标准输入啦
     */
    private void readInput() {
        xlim = StdIn.readInt();
        ylim = StdIn.readInt();
        cellNum = StdIn.readInt();
        cells = new Cell[cellNum];
        double size = xlim * ylim;
        scale = Math.sqrt(240000 / size);

        for (int i = 0; i < cellNum; i++) {
            double rx = StdIn.readDouble();
            double ry = StdIn.readDouble();
            double radius = StdIn.readDouble();
            if (radius > maxRadius) maxRadius = radius;
            double perceptionRange = StdIn.readDouble();
            String color = StdIn.readString();
            cells[i] = new Cell(rx, ry, radius, perceptionRange, color);
            cells[i].setID(i);
        }

        boundary = new Rectangle(xlim / 2.0, ylim / 2.0, xlim / 2.0, ylim / 2.0);
        quadTree = new QuadTree(boundary, 1, cells);
        quaryNum = StdIn.readInt();
        ids = new int[quaryNum];
        times = new double[quaryNum];
        result = new Cell[quaryNum];

        for (int i = 0; i < quaryNum; i++) {
            times[i] = StdIn.readDouble();
            ids[i] = StdIn.readInt();
        }
        Arrays.sort(times);
        seconds = times[times.length - 1] + 1;
    }

    /**
     * 改变颜色完善这里
     */
    private void changeAllColor() {

        for (int i = 0; i < cells.length; i++) {
            //double t0 = timer.elapsedTime();
            //清零之前的数据
            cells[i].Rcount = 0;
            cells[i].Gcount = 0;
            cells[i].Bcount = 0;
            cells[i].Ycount = 0;

            Rectangle rectangle = new Rectangle(cells[i].rx, cells[i].ry, cells[i].perceptionRange + maxRadius, cells[i].perceptionRange + maxRadius);
            //Circle circle = new Circle(cells[i].rx, cells[i].ry,cells[i].perceptionRange);
            //得到周围细胞
            ArrayList<Cell> found = quadTree.query(rectangle);//可能在范围里的cell
            //double t1 = timer.elapsedTime();
            //StdOut.println("查询细胞用时： "+(t1-t0));

            //周围小于4个细胞一定不会变色
            if (found.size() < 4) continue;

            for (int j = 0; j < found.size(); j++) {
                if (isInRange(cells[i], found.get(j))) {//在范围里
                    if (found.get(j).color == Color.RED)
                        cells[i].Rcount++;
                    else if (found.get(j).color == Color.GREEN)
                        cells[i].Gcount++;
                    else if (found.get(j).color == Color.BLUE)
                        cells[i].Bcount++;
                    else
                        cells[i].Ycount++;
                }
            }
            if (cells[i].color == Color.RED)
                cells[i].Rcount--;
            else if (cells[i].color == Color.GREEN)
                cells[i].Gcount--;
            else if (cells[i].color == Color.BLUE)
                cells[i].Bcount--;
            else
                cells[i].Ycount--;
            //double t2 = timer.elapsedTime();
            //StdOut.println("确定三个count"+ (t2-t1));


        }
        //color change
        for (int i = 0; i < cells.length; i++) {
            changeColor(cells[i]);
        }


    }

    private boolean isInRange(Cell c1, Cell c2) {//c2是否在c1范围里，是则 return true
        double x = Math.abs(c1.rx - c2.rx) - c1.perceptionRange;
        double y = Math.abs(c1.ry - c2.ry) - c1.perceptionRange;
        x = x < 0 ? 0 : x;
        y = y < 0 ? 0 : y;
        return x * x + y * y <= c2.radius * c2.radius;

    }

    private void changeColor(Cell cell) {
        //根据4个count来变化颜色
        if (cell.color == Color.RED) {
            if (cell.Rcount >= 3 && (double) cell.Rcount / (cell.Rcount + cell.Gcount + cell.Bcount + cell.Ycount) > 0.7)
                cell.color = Color.GREEN;
            else if (cell.Ycount >= 1 && (double) cell.Ycount / (cell.Rcount + cell.Gcount + cell.Bcount + cell.Ycount) < 0.1)
                cell.color = Color.YELLOW;
            //else cell.color = Color.BLUE;
        } else if (cell.color == Color.GREEN) {
            if (cell.Gcount >= 3 && (double) cell.Gcount / (cell.Rcount + cell.Gcount + cell.Bcount + cell.Ycount) > 0.7)
                cell.color = Color.BLUE;
            else if (cell.Rcount >= 1 && (double) cell.Rcount / (cell.Rcount + cell.Gcount + cell.Bcount + cell.Ycount) < 0.1)
                cell.color = Color.RED;
            //else cell.color = Color.YELLOW;
        } else if (cell.color == Color.BLUE) {
            if (cell.Bcount >= 3 && (double) cell.Bcount / (cell.Rcount + cell.Gcount + cell.Bcount + cell.Ycount) > 0.7)
                cell.color = Color.YELLOW;
            else if (cell.Gcount >= 1 && (double) cell.Gcount / (cell.Rcount + cell.Gcount + cell.Bcount + cell.Ycount) < 0.1)
                cell.color = Color.GREEN;
            //else cell.color = Color.RED;
        } else {
            if (cell.Ycount >= 3 && (double) cell.Ycount / (cell.Rcount + cell.Gcount + cell.Bcount + cell.Ycount) > 0.7)
                cell.color = Color.RED;
            else if (cell.Bcount >= 1 && (double) cell.Bcount / (cell.Rcount + cell.Gcount + cell.Bcount + cell.Ycount) < 0.1)
                cell.color = Color.BLUE;
            //else cell.color = Color.RED;
        }
    }


    /**
     * 移动细胞完善这里
     */
    private void moveAllCells() {
        for (int i = 0; i < cells.length; i++) {
            moveCell(cells[i]);

            if (!cells[i].location.boundary.contains(cells[i])) {
                quadTree = new QuadTree(quadTree.boundary, quadTree.capacity, cells);

            }
        }

    }

    private void moveCell(Cell cell) {
        ArrayList<Double> distance = new ArrayList<>();
        Cell temp = new Cell(cell.rx, cell.ry, cell.radius, cell.color, cell.ID);
        if (temp.color == Color.RED) {
            temp.ry += steplen;
        } else if (temp.color == Color.GREEN) {
            temp.ry += -steplen;
        } else if (temp.color == Color.BLUE) {
            temp.rx += -steplen;
        } else if (temp.color == Color.YELLOW) {
            temp.rx += steplen;
        }

        if (hitWall(temp) >= 0) distance.add(hitWall(temp));
        if (hitCell(cell, temp) != -1) distance.add(hitCell(cell, temp));

        if (distance.size() == 0) moveOneStep(cell);
        else {
            distance.sort(Comparator.naturalOrder());
            moveMin(cell, distance.get(0));
        }


    }

    private void moveMin(Cell cell, double distance) {
        if (cell.color == Color.RED) {
            cell.ry += distance;
        } else if (cell.color == Color.GREEN) {
            cell.ry += -distance;
        } else if (cell.color == Color.BLUE) {
            cell.rx += -distance;
        } else if (cell.color == Color.YELLOW) {
            cell.rx += distance;
        }
    }

    private void moveOneStep(Cell cell) {
        if (cell.color == Color.RED) {
            cell.ry += steplen;
        } else if (cell.color == Color.GREEN) {
            cell.ry += -steplen;
        } else if (cell.color == Color.BLUE) {
            cell.rx += -steplen;
        } else if (cell.color == Color.YELLOW) {
            cell.rx += steplen;
        }
    }

    private double hitWall(Cell temp) {
        if (temp.color == Color.red) {
            if (temp.ry + temp.radius >= ylim) return ylim - temp.radius - (temp.ry - steplen);
        } else if (temp.color == Color.GREEN) {
            if (temp.ry - temp.radius <= 0) return temp.ry - temp.radius + steplen;
        } else if (temp.color == Color.BLUE) {
            if (temp.rx - temp.radius <= 0) return temp.rx - temp.radius + steplen;
        } else if (temp.color == Color.YELLOW) {
            if (temp.rx + temp.radius >= xlim) return xlim - (temp.rx + temp.radius - steplen);
        }
        //do not hit wall
        return -1;
    }

    private double hitCell(Cell cell, Cell temp) {
        ArrayList<Double> min = new ArrayList<>();
        Circle circle = new Circle(cell.rx, cell.ry, cell.radius + steplen + maxRadius);
        //ArrayList<Cell> found = quadTree.query(circle);
        ArrayList<Cell> found = new ArrayList<>();

        //得到可能会撞的cell
        found = quadTree.query(circle);

        //计算移动距离
        for (int i = 0; i < found.size(); i++) {
            //排除自己，得到可能会撞的cell并计算移动距离
            if (!found.get(i).equals(cell) && getDistance(found.get(i), temp) < found.get(i).radius + temp.radius)
                min.add(getMinDis(found.get(i), temp));
        }
        //获得最小移动距离
        if (min.size() > 0) {
            min.sort(Comparator.naturalOrder());
            return min.get(0);
        }
        return -1;
    }

    private double getMinDis(Cell cell, Cell temp) {
        if (temp.color == Color.RED)
            return cell.ry - (temp.ry - steplen) - Math.sqrt((cell.radius + temp.radius) * (cell.radius + temp.radius) - (cell.rx - temp.rx) * (cell.rx - temp.rx));
        else if (temp.color == Color.GREEN)
            return temp.ry + steplen - cell.ry - Math.sqrt((cell.radius + temp.radius) * (cell.radius + temp.radius) - (cell.rx - temp.rx) * (cell.rx - temp.rx));
        else if (temp.color == Color.BLUE)
            return temp.rx + steplen - cell.rx - Math.sqrt((cell.radius + temp.radius) * (cell.radius + temp.radius) - (cell.ry - temp.ry) * (cell.ry - temp.ry));
        else if (temp.color == Color.YELLOW)
            return cell.rx - (temp.rx - steplen) - Math.sqrt((cell.radius + temp.radius) * (cell.radius + temp.radius) - (cell.ry - temp.ry) * (cell.ry - temp.ry));

        return -1;
    }

    private double getDistance(Cell c1, Cell c2) {
        return Math.sqrt((c1.rx - c2.rx) * (c1.rx - c2.rx) + (c1.ry - c2.ry) * (c1.ry - c2.ry));
    }

    /**
     * 画出所有的细胞
     */
    private void drawAllCells() {
        for (int k = 0; k < cellNum; k++) {//可以加异常处理，判断cells是否为null，但没必要
            cells[k].draw(scale);
        }
        StdDraw.show();
    }

    /**
     * 设置画布
     */
    private void setCanvas() {
        StdDraw.setCanvasSize((int) (xlim * scale), (int) (ylim * scale));
        StdDraw.setXscale(0, xlim * scale);
        StdDraw.setYscale(0, ylim * scale);
        StdDraw.enableDoubleBuffering();
    }

    //循环跟新位置和颜色
//    private void loop(Stopwatch stopwatch) {
//        int k = 0;
//        for (int i = 0; i < seconds; i++) {//i表示循环的秒数，第i秒就在
//            double t1 = stopwatch.elapsedTime();
//            for (int j = 0; j < 15; j++) {
//
//                while (k < quaryNum && (i + (double) j / 15) <= times[k] && times[k] < i + (j + 1.0) / 15) {
//                    result[k] = new Cell(cells[ids[k]].rx, cells[ids[k]].ry, cells[ids[k]].color);
//                    k++;
//                }
//
//                StdDraw.pause(10);
//                double t0 = stopwatch.elapsedTime();
//                moveAllCells();
//                double t2 = stopwatch.elapsedTime();
//                //StdOut.println("移动细胞用时："+(t2 - t0));
//                //StdDraw.clear();
//                drawAllCells();
//
//                changeAllColor();
//                double t3 = stopwatch.elapsedTime();
//                // StdOut.println("改变颜色用时："+(t3-t2));
//                drawAllCells();
////                double t4 = stopwatch.elapsedTime();
////                StdOut.println("画图用时："+(t4-t3));
//                StdDraw.clear();
//
//                //StdDraw.pause(1000);
//            }
//            //StdOut.println( 15/(stopwatch.elapsedTime()-t1));
//        }
//    }

    private void loopTerminal(Stopwatch stopwatch) {
        int k = 0;
        for (int i = 0; i < seconds; i++) {//i表示循环的秒数，第i秒就在
            double t1 = stopwatch.elapsedTime();
            for (int j = 0; j < 15; j++) {

                while (k < quaryNum && (i + (double) j / 15) <= times[k] && times[k] < i + (j + 1.0) / 15) {
                    result[k] = new Cell(cells[ids[k]].rx, cells[ids[k]].ry, cells[ids[k]].color);
                    StdOut.println(result[k]);
                    k++;
                }

                moveAllCells();
                changeAllColor();

            }
        }
    }

    private void loopGUI(Stopwatch stopwatch) {
        while(true) {
            double t1 = stopwatch.elapsedTime();
            for(int j = 0; j <15; j++){
                double t0 = stopwatch.elapsedTime();
                StdDraw.clear();
                moveAllCells();
                changeAllColor();
                drawAllCells();
                double t3 = stopwatch.elapsedTime();
                if(t3 - t0 < 1.0/15) StdDraw.pause((int)(1000*(1.0/15 - (t3 - t0))));
            }
            StdOut.println("当前帧数 ："+ 15/(stopwatch.elapsedTime()-t1));
        }
    }

    private void printResult() {
        for (int i = 0; i < quaryNum; i++) {
            StdOut.println(result[i]);
        }
    }


    /**
     * main函数在这里
     *
     * @param args
     */
    public static void main(String[] args) {
//        main_QuadTree Main_quadTree = new main_QuadTree();
//
//        Main_quadTree.readInput();
//        Main_quadTree.setCanvas();
//        Stopwatch timer = new Stopwatch();
//        Main_quadTree.loop(timer);
//        Main_quadTree.printResult();

//javac -cp lib\algs4.jar  -encoding utf-8 src\*.java -d bin
// java -cp bin;lib\algs4.jar main_QuadTree
        if (args.length!=0 && args[0].equals("--terminal")) {
            main_QuadTree Main_quadTree = new main_QuadTree();
            Main_quadTree.readInput();
            //Main_quadTree.setCanvas();
            Stopwatch timer = new Stopwatch();
            Main_quadTree.loopTerminal(timer);
        } else {
            main_QuadTree Main_quadTree = new main_QuadTree();
            Main_quadTree.readInput();
            Main_quadTree.setCanvas();
            Stopwatch timer = new Stopwatch();
            Main_quadTree.loopGUI(timer);

        }


    }

}
