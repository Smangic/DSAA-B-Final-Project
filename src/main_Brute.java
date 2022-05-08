import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdStats;

public class main_Brute {
    int xlim,ylim,cellNum;  //x y 轴的取值范围； 细胞的数量
    int scale = 10; // 画图的扩张倍数

    int quaryNum; //查询细胞的数量
    int[] ids, times; //存储查询细胞的id 以及 对应的秒数
    Cell[] cells; //存储所有细胞

    int seconds; //循环的时间

    Cell[] result; // 查询到的结果

    private static double steplen = 1;



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
         times = new int[quaryNum];
         result = new Cell[quaryNum];


         for(int i = 0; i < quaryNum ;i++)
         {
             times[i] = StdIn.readInt();
             ids[i] = StdIn.readInt();
         }
         seconds = StdStats.max(times)+1;
     }

    /**
     * 改变颜色完善这里
     */
    private void changeAllColor()
     {

     }

    /**
     * 移动细胞完善这里
     */
    private void moveAllCells()
     {

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
         for(int i = 0; i < seconds; i++)
         {//i表示循环的秒数，第i秒就在
             for(int k  = 0; k < quaryNum; k++)
             {//如果查询名单中有第i秒时的某个细胞，则。。。
                 if(times[k]==i){
                     result[k] = cells[ids[k]];
                 }
             }

             for(int j = 0; j < 15; j++)
             {
                 moveAllCells();
                 drawAllCells();
                 changeAllColor();
                 drawAllCells();
                 StdDraw.clear();
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
        main_Brute brute = new main_Brute();
        brute.readInput();
        brute.setCanvas();
        brute.loop();
        brute.printResult();
    }

}
