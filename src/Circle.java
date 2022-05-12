import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

public class Circle {
    double x,y,radius;

    public Circle(double x, double y, double radius)
    {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }



    public boolean contains(Cell cell) {
        double d = Math.sqrt((cell.rx - x)*(cell.rx - x) + (cell.ry - y)*(cell.ry - y));
        //return d <= radius;
        return d <= radius+cell.radius;//相切
    }

    public boolean intersects(Rectangle range)
    {
        double xDist = Math.abs(range.x - this.x);
        double yDist = Math.abs(range.y - this.y);
        double edge = Math.pow((xDist - range.halfWidth),2)+Math.pow(yDist-range.halfHeight, 2);

        //无交点
        if(xDist > (this.radius + range.halfWidth) || yDist > (this.radius + range.halfHeight))
            return false;
        if(xDist <= range.halfWidth || yDist <= range.halfHeight)
            return true;

        //是否相交在圆上
        return edge <= Math.pow(this.radius,2);

    }

    public void draw(int scale)
    {
        StdDraw.setPenColor(Color.GREEN);
        StdDraw.circle(x*scale,y*scale,radius*scale);
    }
}
