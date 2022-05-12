
import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.StdRandom;
//*用于生成测试数据

/**
 * 使用的的时候只需要修改num和ratio就好了
 * 然后把main的输入重定向到指定的txt即可
 * 目前感知范围没有加太大的约束，可能比较离谱，但是debug就是要离谱的(X)
 */
public class GenTest {
    public static void main(String[] args) {
        String[] colors = {"r","g","b","y"};
        int num = 2000; //*需要生成的数据量
        double ratio = 0.4; //*生成cell占全空间的比例
        Out out = new Out(String.format("TestData_%d_%.2f.txt",num,ratio));

        //*分别用来储存合理的x,y,r
        double[] xs = new double [num];
        double[] ys = new double [num];
        double[] rs = new double [num];
        int i = 0;
        int xMax = StdRandom.uniform(100, 190); //*就随机啦
        int yMax = StdRandom.uniform(80, 100);

        int area = xMax*yMax;
        double Rarea = ratio * area;
        double expectation = Math.sqrt(Rarea/num)/2;
        double sigma = expectation / 2; //标准差越小细胞大小差距不会那么夸张

        out.println(xMax+" "+yMax);
        out.println(num);
        while(i < num)
        {
            double rxTemp = StdRandom.uniform(expectation,xMax-expectation); //坐标可能还是均匀分布比较好
            double ryTemp = StdRandom.uniform(expectation,yMax-expectation);
            double radiusTemp = Math.abs(StdRandom.gaussian(expectation,sigma));//尽可能均匀一点吧

            if(i==0 && rxTemp >=radiusTemp && ryTemp >= radiusTemp && (rxTemp+radiusTemp) <=xMax && (ryTemp+radiusTemp) <= yMax)
            {
                //第一个可以直接加进去
                double percepTemp = StdRandom.uniform(radiusTemp,Math.min(xMax, yMax));
                String color = colors[StdRandom.uniform(4)];
                xs[i] = rxTemp;
                ys[i] = ryTemp;
                rs[i] = radiusTemp;
                out.println(xs[i]+" "+ys[i]+" "+rs[i]+" "+ percepTemp+" "+color);
                i++;
            }
            else
            {
                //后面的都需要和前面的所有校验后才可以加进去
                if(testData(xs,ys,rs,i,rxTemp,ryTemp,radiusTemp,xMax,yMax)){
                    double percepTemp = StdRandom.uniform(radiusTemp,Math.min(xMax, yMax));
                    String color = colors[StdRandom.uniform(4)];
                    xs[i] = rxTemp;
                    ys[i] = ryTemp;
                    rs[i] = radiusTemp;
                    out.println(xs[i]+" "+ys[i]+" "+rs[i]+" "+ percepTemp+" "+color);
                    i++;
                }
            }
        }
        out.println("3");
        out.println("4   2");
        out.println("10  0");
        out.println("14  1");
        out.close();
    }

    //校验发现没有重叠，没有越界就返回true
    private static boolean testData(double[] xs,double[] ys,double[] rs,int j,double rxTemp,double ryTemp, double radiusTemp,int xMax,int yMax)
    {
        for(int i = 0; i < j;i++)
        {
            if(calDistance(xs[i],ys[i],rs[i],rxTemp,ryTemp,radiusTemp) < 0 || rxTemp <radiusTemp || ryTemp < radiusTemp ||(rxTemp+radiusTemp) >xMax || (ryTemp+radiusTemp) > yMax || radiusTemp < 0.1)
                return false;
        }
        return true;
    }

    private static double calDistance(double x1,double y1,double r1, double x2, double y2, double r2)
    {
        double x = x1 - x2;
        double y = y1 - y2;
        return Math.sqrt(x*x + y*y)-r1-r2;
    }
}

