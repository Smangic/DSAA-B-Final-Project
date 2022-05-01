import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.util.InputMismatchException;

/**
 * 就是定义的Cell啦
 * 有移动，变化颜色
 */
public class Cell {

    //感觉加private的话会不太方便，就自己用的小心点吧
    int ID;  //名字

    double rx,ry; //x坐标，y坐标
    double vx = 1,vy = 0; //x方向速度，y方向速度
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
     * @param id 建议从1开始标通号
     */
    public void setID(int id)
    {
        this.ID = id;
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
    public void move()
    {

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
    public void changeColor()
    {

    }

    /**
     * 绘制细胞啦，这里感觉直接画也有点诡异，传入参数太小了，应该有个scale会好一点
     */
    public void draw()
    {
        StdDraw.setPenColor(this.color);
        StdDraw.filledCircle(rx,ry,radius);
    }

    /**
     * 用于打印命令行输出
     * @return
     */
    public String toString()
    {
        return String.format("");
    }


}
