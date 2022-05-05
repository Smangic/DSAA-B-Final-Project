import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class DataTest {
    public static void main(String[] args) {
        In in  = new In();
        int x = in.readInt();
        int y = in.readInt();
        int num = in.readInt();
        double rx[] = new double[num];
        double ry[] = new double[num];
        double rs[] = new double[num];
        double pers[] = new double[num];
        double area[] = new double[num];
        for(int i = 0; i < num; i++)
        {
            rx[i] = in.readDouble();
            ry[i] = in.readDouble();
            rs[i] = in.readDouble();
            pers[i] = in.readDouble();
            area[i] = 4 * rs[i] * rs[i];
            in.readString();

        }
        StdOut.println("x range is: "+x);
        StdOut.println("y range is: "+y);

        StdOut.println("The mean of x is: "+StdStats.mean(rx));
        StdOut.println("The mean of y is: "+StdStats.mean(ry));
        StdOut.println("The mean of radius is: "+StdStats.mean(rs));
        StdOut.println("The standard deviation of radius is: "+StdStats.stddev(rs));
        StdOut.println("The ratio of area that cells take place is : "+(4*StdStats.stddev(rs)*StdStats.stddev(rs)*num)/(x*y));
        StdOut.println("The mean of perception Range is: "+StdStats.mean(pers));


    }
}
