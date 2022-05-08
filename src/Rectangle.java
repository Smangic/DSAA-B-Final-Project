import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

public class Rectangle {
    double x,y; //矩形的中心点坐标
    double halfHeight, halfWidth; // height为高度的一半，width为宽度的一半 ，主要目的是和StdDraw给的Rectangle对应。

     public Rectangle (double x, double y,double halfWidth,double halfHeight)
    {
        this.x = x;
        this.y = y;
        this.halfHeight = halfHeight;
        this.halfWidth = halfWidth;
    }

    public boolean contains(Cell cell)
    {
        return (cell.rx >= x - halfWidth &&
                cell.rx <= x + halfWidth &&
                cell.ry <= y + halfHeight &&
                cell.ry >= y - halfHeight);
    }
    public boolean intersects(Rectangle range)
    {
        return !(range.x - range.halfWidth > this.x + this.halfWidth ||
                 range.x + range.halfWidth < this.x - this.halfWidth ||
                 range.y + range.halfHeight < this.y - this.halfHeight||
                 range.y - range.halfHeight > this.y + this.halfHeight);

    }



    public void draw(int scale)
    {
        StdDraw.setPenColor(Color.GREEN);
        StdDraw.rectangle(x*scale,y*scale,halfWidth*scale,halfHeight*scale);
    }



}
