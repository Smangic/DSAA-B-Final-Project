

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.awt.*;
import java.util.InputMismatchException;

/**
 * 就是定义的Cell啦
 * 有移动，变化颜色
 */
public class Cell {

    //感觉加private的话会不太方便，就自己用的小心点吧
    int ID;  //名字
    int Rcount = 0;
    int Gcount = 0;
    int Bcount = 0;
    int Ycount = 0;
    double rx,ry; //x坐标，y坐标
    double vx = 0.1,vy = 0; //x方向速度，y方向速度
    double radius; //半径
    double perceptionRange; //感知范围，以细胞为中心，2p为边长的正方形。
    Color color; //这个Color 来自于java.awt
    QuadTree location;


    //空的构造函数，有必要再完善。
    public Cell()
    {

    }

    /**根据给的输入定义的构造函数
     *
     * @param rx x坐标
     * @param ry y坐标
     * @param radius 半径
     * @param perceptionRange 感知范围。以细胞为中心，2p为边长的正方形
     * @param color 颜色 r,g,b,y
     */
    public Cell(double rx, double ry, double radius, double perceptionRange, String color)
    {
        this.rx = rx;
        this.ry = ry;
        this.radius = radius;
        this.perceptionRange = perceptionRange;
        //this.ID ++;
        if(color.equals("b")||color.equals("BLUE"))
            this.color = Color.BLUE;
        else if(color.equals("g")||color.equals("GREEN"))
            this.color = Color.GREEN;
        else if(color.equals("r")||color.equals("RED"))
            this.color = Color.RED;
        else if(color.equals("y")||color.equals("YELLOW"))
            this.color = Color.YELLOW;
        else{
            throw new InputMismatchException("no such color exception");
        }
    }
    public Cell(double rx, double ry, double radius, Color color,int id)
    {
        this.rx = rx;
        this.ry = ry;
        this.radius = radius;
       this.color = color;
       this.ID = id;
    }

    public Cell(double rx, double ry, Color color)
    {
        this.rx = rx;
        this.ry = ry;
        this.color = color;
    }



    /**设置ID，因为标准输入没有传入ID，我们需要自己设定ID
     * @param id 建议从0开始标通号
     */
    public void setID(int id)
    {
        this.ID = id;
    }




    /**
     * 绘制细胞啦，这里感觉直接画也有点诡异，传入参数太小了，应该有个scale会好一点
     */
    public void draw(int scale)
    {
        StdDraw.setPenColor(this.color);
        StdDraw.filledCircle(rx*scale,ry*scale,radius*scale);
        //StdDraw.setPenColor(Color.BLACK);
        //StdDraw.text(rx*scale,ry*scale,String.format("%d",this.ID));
        //StdDraw.filledCircle(rx*scale,ry*scale,3);
        //可以考虑现在这里把感知范围的框框画出来，最后删掉就好。
    }

    public void clear(int scale)
    {
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.filledCircle(rx*scale,ry*scale,radius*scale);
    }

    public void drawSelected(int scale)
    {
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledCircle(rx*scale,ry*scale,radius*scale);
    }


    /**
     * 用于打印命令行输出
     * @return 标准的长这样 0.1 1 b 最后再改吧
     */
    public String toString()
    {
        String thatColor;
        if(this.color.equals(Color.BLUE)) thatColor = "b";
        else if(this.color.equals(Color.RED)) thatColor = "r";
        else if(this.color.equals(Color.YELLOW)) thatColor = "y";
        else thatColor = "g";
        return String.format("%.1f %.1f %s",rx,ry,thatColor);
    }
    //可以考虑key为 cell的id，value为 cell 的 hashmap






}
