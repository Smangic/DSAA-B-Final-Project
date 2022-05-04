package src;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class DrawTest {
    public static void main(String[] args) {
        StdOut.println("Test StdDraw:");

        //StdDraw.setPenRadius(0.05);//粗细，我们应该用不到。
        StdDraw.setCanvasSize(500,500);//单位就是像素,传入的参数还是有点小了，可能需要考虑给一个scale
        StdDraw.setXscale(0,500);
        StdDraw.setYscale(0,500);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.point(0.0, 0.0);//左下角
        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.point(1.0, 0.0);//
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.point(0.0, 1.0);

        StdDraw.line(0.2, 0.2, 250, 250);
        StdDraw.rectangle(20, 20, 200, 200);

    }
}
