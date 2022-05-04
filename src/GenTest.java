
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
        int num = 200; //*需要生成的数据量
        double ratio = 0.6; //*生成cell占全空间的比例
        Out out = new Out(String.format("TestData_%d_%f",num,ratio));

        //*分别用来储存合理的x,y,r
        double[] xs = new double [num];
        double[] ys = new double [num];
        double[] rs = new double [num];
        int i = 0;
        int xMax = StdRandom.uniform(50, 100); //*就随机啦
        int yMax = StdRandom.uniform(50, 100);
        
        int area = xMax*yMax;
        double Rarea = ratio * area;
        double expectation = Math.sqrt(Rarea/num);
        double sigma = (Math.min(xMax, yMax) - expectation)/5;

        out.println(xMax+" "+yMax);
        out.println(num);
        while(i < num)
        {
            double rxTemp = StdRandom.gaussian(xMax/2,xMax/8); //*就是说有4sigma的概率落在范围内
            double ryTemp = StdRandom.gaussian(yMax/2,yMax/8);
            double radiusTemp = Math.abs(StdRandom.gaussian(expectation,sigma));//尽可能均匀一点吧

            if(i==0 && rxTemp >=radiusTemp && ryTemp >= radiusTemp)
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
                if(testData(xs,ys,rs,i,rxTemp,ryTemp,radiusTemp)){
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
        //out.println("the first test");
        out.close();
    }

    //校验发现没有重叠就返回true
    private static boolean testData(double[] xs,double[] ys,double[] rs,int j,double rxTemp,double ryTemp, double radiusTemp)
    {
        for(int i = 0; i < j;i++)
        {
            if(calDistance(xs[i],ys[i],rs[i],rxTemp,ryTemp,radiusTemp) < 0 || rxTemp <radiusTemp || ryTemp < radiusTemp)
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
