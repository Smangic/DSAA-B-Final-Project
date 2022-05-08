

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
        if(color.equals("b"))
            this.color = Color.BLUE;
        else if(color.equals("g"))
            this.color = Color.GREEN;
        else if(color.equals("r"))
            this.color = Color.RED;
        else if(color.equals("y"))
            this.color = Color.YELLOW;
        else{
            throw new InputMismatchException("no such color exception");
        }
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
        StdDraw.setPenColor(Color.BLACK);
        //StdDraw.text(rx*scale,ry*scale,String.format("%d",this.ID));
        StdDraw.filledCircle(rx*scale,ry*scale,3);
        //可以考虑现在这里把感知范围的框框画出来，最后删掉就好。
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
        return String.format("the position is (%f, %f),and the color is %s",rx,ry,color);
    }

    public void move()
    {
        rx += vx;
        ry += vy;
    }




}
