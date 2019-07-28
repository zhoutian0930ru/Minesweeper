import java.util.Random;
import java.util.Scanner;

public class Minesweeper {
    private static int totalmine;
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        int xlen = 20;  //设定矩阵的x长度
        int ylen = 15;  //设定矩阵的y长度
        int mine = 15;  //设定雷的数量
        totalmine = mine;

        System.out.println("xlen: "+xlen+" , ylen: "+ylen+", mine: "+mine);

        int[][] map = new int[xlen][ylen];
        boolean[][] covered = new boolean[xlen][ylen];  //游戏中现实给玩家的界面，表示是否已经被打开
        boolean[][] open = new boolean[xlen][ylen];     //打开所有的格子

        //初始化 扫雷矩阵 以及 是否检查被打开的数组
        initial(map,mine,covered,open);
        print(map,covered);
        //print(map,open);

        int step = 1;
        boolean valid = true;
        System.out.println("Step: "+step+" (输入格式:  x,y,0/1)");
        String input = in.nextLine();
        while((!input.equals("0")) && valid){
            String[] strarr = input.split(",");
            if(strarr.length!=3) continue;
            int x = Integer.parseInt(strarr[0]);   //x值
            int y = Integer.parseInt(strarr[1]);   //y值
            int z = Integer.parseInt(strarr[2]);   //0表示非雷，1表示雷
            if(!covered[x][y]){
                check(map,x,y,covered);
            }else{
                if(z==0){
                    valid = select(map,x,y,covered);
                }else{
                    if(map[x][y]==-1){
                        covered[x][y]=false;
                        totalmine--;
                    }else{
                        map[x][y]=-2;
                        valid = false;
                    }
                }
            }

            if(totalmine==0) valid = false;
            if(valid){
                print(map,covered);
                step++;
                System.out.println("Step: "+step);
                input = in.nextLine();
            }else{
                if(totalmine==0){
                    print(map,open);
                    System.out.println("Congratulation");
                }else{
                    print(map,open);
                    System.out.println("GAME OVER");
                }
            }
        }

    }

    //检查已经被打开的格子周围，是否可以自动打开；
    public static void check(int[][] map,int x,int y,boolean[][] covered){
        if(map[x][y]>=0){
            int count = 0;
            int cover = 0;

            int sx = x-1;
            int ex = x+1;
            int sy = y-1;
            int ey = y+1;

            if(x==0) sx = x;
            if(x==map.length-1) ex=x;
            if(y==0) sy = y;
            if(y==map[0].length-1) ey = y;

            for(int i=sx;i<ex+1;i++){
                for(int j=sy;j<ey+1;j++){
                    if(covered[i][j]) cover++;
                    else if(map[i][j]==-1){
                        count++;
                    }
                }
            }
            if(map[x][y]==0){
                for(int i=sx;i<ex+1;i++){
                    for(int j=sy;j<ey+1;j++){
                        select(map,i,j,covered);
                    }
                }
            }else if(map[x][y]>0 && count==map[x][y] || map[x][y]==count+cover){
                totalmine-=cover;
                for(int i=sx;i<ex+1;i++){
                    for(int j=sy;j<ey+1;j++){
                        covered[i][j]=false;
                    }
                }
            }
        }
    }

    //不作为雷打开坐标为（x,y）的格子
    public static boolean select(int[][] map,int x,int y,boolean[][] covered){
        //if(x>map.length-1 || y>map[0].length-1) return false;
        if(!covered[x][y]) return true;
        if(map[x][y]==-1){
            map[x][y]=-2;
            return false;
        }else if(map[x][y]==0){
            covered[x][y] = false;
            check(map,x,y,covered);
            return true;
        }else{
            covered[x][y] = false;
            return true;
        }
    }

    //初始化的一部分--计算周围雷的个数
    public static void count(int[][] map,int x,int y){
        int sx = x-1;
        int ex = x+1;
        int sy = y-1;
        int ey = y+1;

        if(x==0) sx = x;
        if(x==map.length-1) ex=x;
        if(y==0) sy = y;
        if(y==map[0].length-1) ey = y;

        for(int i=sx;i<ex+1;i++){
            for(int j=sy;j<ey+1;j++){
                if(map[i][j]>=0 && (i!=x || j!=y)){
                    map[i][j]++;
                }
            }
        }
    }

    //初始化矩阵
    public static void initial(int[][] map,int n,boolean[][] covered,boolean[][] open){
        //set mines
        int total = map.length * map[0].length;
        int mine = n;
        for(int i=0;i<total;i++){
            Random ra = new Random();
            int temp = ra.nextInt(total-i);
            if(temp < mine){
                mine--;
                map[i/map[0].length][i%map[0].length]=-1;
                count(map,i/map[0].length,i%map[0].length);
            }
        }

        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[0].length;j++){
                covered[i][j] = true;
                open[i][j] = false;
            }
        }
    }

    //打印矩阵
    public static void print(int[][] map,boolean[][] covered){
        int n = map.length;
        int m = map[0].length;
        System.out.print("  ");
        for(int j=0;j<m;j++){
            if(j<9) System.out.print(" "+j+"  ");
            else System.out.print(" "+j+" ");
        }
        System.out.println();
        for(int i=0;i<n;i++){
            if(i<10) System.out.print(" "+i);
            else System.out.print(i);
            for(int j=0;j<m;j++){
                if(covered[i][j]) System.out.print("[#] ");
                else if(map[i][j]==-1) System.out.print("[.] ");
                else if(map[i][j]==-2) System.out.print("[X] ");
                else if(map[i][j]==0) System.out.print("[ ] ");
                else System.out.print("["+map[i][j]+"] ");
            }
            System.out.println();
            //System.out.println();
        }
    }
}
