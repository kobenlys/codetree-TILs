import java.util.*;
import java.io.*;

public class Main {
    public static int[][] arr1;

    public static void isWin(int color, int x, int y){
        int[] dx = {0,0,-1,1,-1,1,-1,1};
        int[] dy = {-1,1,0,0,-1,-1,1,1};

        for(int i=0; i<8; i++){
            int cnt = 1;
            for(int j=1; j<=6; j++){
                int nx = x + dx[i] * j;
                int ny = y + dy[i] * j;
                if(nx < 0 || ny < 0 || nx >=19 || ny >= 19) break;
                if(arr1[ny][nx] != color) break;
                cnt++;

                if(cnt == 5){
                    if(i == 6){
                        System.out.println(color);
                        System.out.println((ny+1)+" "+(nx+1));
                    }else{
                        System.out.println(color);
                        System.out.println((y+1)+" "+(x+1));
                    }
                    System.exit(0);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        arr1 = new int[19][19];

        for(int i=0; i<19; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<19; j++){
                arr1[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for(int i=0; i<19; i++){
            for(int j=0; j<19; j++){
                if( arr1[i][j] > 0){
                    isWin(arr1[i][j], j, i);
                }
            }
        }
    }
}