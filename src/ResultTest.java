import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdStats;

public class ResultTest {
    public static void main(String[] args) {
        //In res = new In();
        //In mine = new In();
        String[] res = new String[500];
        String[] my = new String[500];

        double[] res_x = new double[500];
        double[] res_y = new double[500];

        double[] my_x = new double[500];
        double[] my_y = new double[500];

        double[] error_xs = new double[500];
        double[] error_ys = new double[500];

        for(int i = 0; i < 500; i++)
        {
            my_x[i] = StdIn.readDouble();
            my_y[i] = StdIn.readDouble();
            my[i] = StdIn.readString();
        }
        for(int i = 0; i < 500; i++)
        {
            res_x[i] = StdIn.readDouble();
            res_y[i] = StdIn.readDouble();
            res[i] = StdIn.readString();
        }
        int right = 0;
        double error_x = 0;
        double error_y = 0;
        for(int i = 0; i < 500; i++)
        {
            error_xs[i] = Math.abs( res_x[i] - my_x[i]);
            error_x += error_xs[i];
            error_ys[i] = Math.abs( res_y[i] - my_y[i]);
            error_y += error_ys[i];
            if(res[i].equals(my[i]))
                right++;
            else
                StdOut.println(i);

        }
        StdOut.println("颜色匹配：" + right);
        StdOut.println("累计的x坐标（绝对值）误差: "+error_x);
        StdOut.println("x坐标的误差的均值为： "+ StdStats.mean(error_xs) + "标准差为： "+StdStats.stddev(error_xs));
        StdOut.println("累积的y坐标（绝对值）误差: "+error_y);
        StdOut.println("y坐标的误差的均值为： "+StdStats.mean(error_ys) + "标准差为： "+StdStats.stddev(error_ys));

    }
}
