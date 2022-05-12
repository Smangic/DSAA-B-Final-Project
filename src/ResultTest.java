import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

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
            error_x += Math.abs( res_x[i] - my_x[i]);
            error_y += Math.abs( res_y[i] - my_y[i]);
            if(res[i].equals(my[i]))
                right++;
            else
                StdOut.println(i);

        }
        StdOut.println(right);
        StdOut.println(error_x);
        StdOut.println(error_y);
    }
}
