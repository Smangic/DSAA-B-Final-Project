import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class QuadTree {
    Rectangle boundary;
    int capacity;
    ArrayList<Cell> cells;
    QuadTree TopLeft,TopRight,BottomLeft,BottomRight;
    boolean isDivided = false;


    // 在构造树的时候就吧所有的细胞恰进去
    public QuadTree(Rectangle boundary, int capacity,Cell[] cells)
    {
        this.boundary = boundary;
        this.capacity = capacity;
        for(int i = 0; i < cells.length; i++)
        {
            this.insert(cells[i]);
        }

    }

    public QuadTree(Rectangle boundary, int capacity)
    {
        this.boundary = boundary;
        this.capacity = capacity;
    }

    private void subdivide()
    {
        this.TopLeft = new QuadTree(new Rectangle(this.boundary.x-this.boundary.halfWidth/2,this.boundary.y+this.boundary.halfHeight/2,this.boundary.halfWidth/2,this.boundary.halfHeight/2),this.capacity);
        this.TopRight = new QuadTree(new Rectangle(this.boundary.x+this.boundary.halfWidth/2,this.boundary.y+this.boundary.halfHeight/2,this.boundary.halfWidth/2,this.boundary.halfHeight/2),this.capacity);
        this.BottomLeft = new QuadTree(new Rectangle(this.boundary.x-this.boundary.halfWidth/2,this.boundary.y-this.boundary.halfHeight/2,this.boundary.halfWidth/2,this.boundary.halfHeight/2),this.capacity);
        this.BottomRight = new QuadTree(new Rectangle(this.boundary.x+this.boundary.halfWidth/2,this.boundary.y-this.boundary.halfHeight/2,this.boundary.halfWidth/2,this.boundary.halfHeight/2),this.capacity);
        this.isDivided = true;
    }


    public boolean insert(Cell cell)
    {

        //该区间不包含该细胞就直接结束
        if(!this.boundary.contains(cell))
            return false;

        //有了添加再new，可以省内存？
        if(this.cells == null)
        {
            cells = new ArrayList<>();
        }
        //容量没满直接加
        if(this.cells.size() < this.capacity)
        {
            this.cells.add(cell);
            cell.location = this;
            return true;
        }
        else
        {//以及满了，就往子区间加
            if(!this.isDivided) {
                this.subdivide();
            }

            //确保正中边界时只会添加到一边
            if(this.TopRight.insert(cell))
                return true;
            else if(this.TopLeft.insert(cell))
                return true;
            else if(this.BottomRight.insert(cell))
                return true;
            else if(this.BottomLeft.insert(cell))
                return true;

        }
        return false;
    }

    public ArrayList<Cell> query(Rectangle range)
    {
        ArrayList<Cell> found = new ArrayList<>();
        if (!this.boundary.intersects(range))
            return found;
        return query(range,found);

    }

    public ArrayList<Cell> query(Circle range)
    {
        ArrayList<Cell> found = new ArrayList<>();
        if (!range.intersects(this.boundary))
            return found;
        return query(range,found);



    }

    static int totalSearch = 0;
    private ArrayList<Cell> query(Rectangle range, ArrayList found)
    {
        if (!range.intersects(this.boundary))
            return found;
        else
        {
            if(this.cells != null) {
                for (Cell cell : cells) {
                    totalSearch++;
                    if (range.contains(cell))
                        found.add(cell); //找到了就添进去
                }
            }

            if(this.isDivided)
            {
                this.TopLeft.query(range,found);
                this.TopRight.query(range, found);
                this.BottomLeft.query(range, found);
                this.BottomRight.query(range, found);
            }
        }
        return found;
    }


    private ArrayList<Cell> query(Circle range, ArrayList found)
    {
        if (!range.intersects(this.boundary))
            return found;
        else
        {
            if(this.cells != null) {
                for (Cell cell : cells) {
                    totalSearch++;
                    if (range.contains(cell))
                        found.add(cell); //找到了就添进去
                }
            }

            if(this.isDivided)
            {
                this.TopLeft.query(range,found);
                this.TopRight.query(range, found);
                this.BottomLeft.query(range, found);
                this.BottomRight.query(range, found);
            }
        }
        return found;
    }

    //TODO: 我想实现，删除一个指定的细胞，然后修复四叉树的功能,，这样可以避免每一次都要重新构造树
    public Cell delete(Cell cell)
    {
        //首先消除亲子关系
        this.cells.remove(cell);
        cell.location = null;


        return cell;
    }

    public void show(int scale)
    {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.rectangle(this.boundary.x*scale,this.boundary.y*scale,this.boundary.halfWidth*scale,this.boundary.halfHeight*scale);
        if(cells != null) {
            for (int i = 0; i < cells.size(); i++) {
                cells.get(i).draw(scale);
            }
        }
        if(this.isDivided)
        {
            this.TopLeft.show(scale);
            this.TopRight.show(scale);
            this.BottomLeft.show(scale);
            this.BottomRight.show(scale);

        }
    }



}
