# DSAA-B-Final-Project -- RGBY Cell Life


## Overview
RGBY Cell Life is a simulation project that models the interactions and dynamics of cells with different colors, sizes, and movement directions on a 2D plane, focusing on the classic problem of collision detection. The project demonstrates the superiority of the Quadtree algorithm over a brute force approach in terms of performance.

## Features
Simulation of cell movement and collision on a 2D plane
Color transformation of cells based on the surrounding cells within their perception range
Implementation of a Quadtree data structure for optimized performance
## How It Works
### Movement
Cells can move within a boundary and collide with other cells. The hitWall and hitCell functions determine the movement and collision responses of the cells.

### Color Transformation
After each round of movement, cells scan for neighbors within their perception range and change color based on specific rules.

### Quadtree Optimization
The project improves upon the Quadtree algorithm for adding nodes and regional searches, significantly reducing the number of cells to be traversed during a search.

## Testing Visualization

To ensure the accuracy and reliability of the RGBY Cell Life simulation, we implement rigorous testing methods. 
These visualizations help us to quickly identify and address any unexpected behaviors or collisions that may occur.

**Visualization of searching algorithm**

<img src="https://smangic-markdown-image.oss-cn-shenzhen.aliyuncs.com/img/image-20220512161346653.png" alt="image-20220512161346653" style="zoom: 50%;" />
> There are 10000 cells in this image. And the green square indicates the searching area. And all the cells with centers in that square will be drawn black. And the square area can move with the mouse.
> Plese see `QuadTreeQueryShow.java` for the testing.

**Visualization of adding algorithm**

![image-20220521203322006](https://smangic-markdown-image.oss-cn-shenzhen.aliyuncs.com/img/image-20220521203322006.png)

![image-20220521203436354](https://smangic-markdown-image.oss-cn-shenzhen.aliyuncs.com/img/image-20220521203436354.png)

> The images shows the process of adding cells. When the number of cells in one area reaches the capacity, it will be divided into four subareas.

## Performance Analysis

In our practical experiments, we found that the size of the canvas significantly affects the speed of drawing.
To control the variables and explore the effects of optimization, we mainly discuss two cases: 
1. Without using GUI, pure computation.
2. Using a specific canvas resolution, here uniformly adopting an equivalent of 600*400, which is 240,000 pixels in resolution.

As for the frame rate, we define it as: \(\frac{15}{\text{the time required to complete 15 rounds of computation}}\).

We analyzed the relationship between the number of cells and the frame rate. Since drawing also consumes time, the scenario where the code is run without drawing is as follows:

![Graph showing the relationship without drawing](https://smangic-markdown-image.oss-cn-shenzhen.aliyuncs.com/img/nodraw.png)

The scenario when drawing is also performed is as follows:

![Graph showing the relationship with drawing](https://smangic-markdown-image.oss-cn-shenzhen.aliyuncs.com/img/draw.png)

The analysis above shows that the use of the Quadtree algorithm has clearly optimized the program. Additionally, drawing significantly slows down the program's execution.
If the time consumed by drawing is on the same order of magnitude as moving and changing colors, theoretically, with 10,000 cells,
it would also be possible to achieve the standard of 15 movements per second, that is, 15 frames.

## How to Run

1. Open the `Project` folder in the command line.
2. Since the real-time frame rate is output in the command line, switch to UTF-8 encoding. Enter in cmd: `CHCP 65001`
3. Compile all the java files in the `src` folder: `javac -cp lib\algs4.jar -encoding utf-8 src\*.java -d bin`
4. Run `main-QuadTree`, specify the mode, and redirect input:
   1. GUI mode: Just redirect input, no extra arguments needed: `java -cp bin;lib\algs4.jar main_QuadTree < sample3.txt`
   2. Terminal mode: `java -cp bin;lib\algs4.jar main_QuadTree --terminal < sample3.txt`

Demonstration Effects:

GUI Mode:

![image showing GUI mode](https://smangic-markdown-image.oss-cn-shenzhen.aliyuncs.com/img/image-20220522171156222.png)

Terminal Mode:

![image showing Terminal mode](https://smangic-markdown-image.oss-cn-shenzhen.aliyuncs.com/img/image-20220522171326753.png)
