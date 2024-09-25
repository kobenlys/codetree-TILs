import java.io.*;
import java.util.*;

public class Main {

    public static int directionToIndex(String str){
        if(str.equals("N")){
            return 0;
        }else if(str.equals("S")){
            return 1;
        }else if(str.equals("W")){
            return 2;
        }
        return 3;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());

        int[][] arr1 = new int[2005][2005];

        int x = 1003;
        int y = 1003;
        int cnt = 1;
        int answer = Integer.MAX_VALUE;
        arr1[y][x] = cnt++;

        int[] dx = {0, 0, -1, 1};
        int[] dy = {-1, 1, 0, 0};

        while (N-- > 0) {

            st = new StringTokenizer(br.readLine());
            String dir = st.nextToken();
            int step = Integer.parseInt(st.nextToken());

            int idx = directionToIndex(dir);

            for (int i = 0; i < step; i++) {

                x += dx[idx];
                y += dy[idx];

                if(arr1[y][x] == 0){
                    arr1[y][x] = cnt++;
                }else{
                    answer = Math.min(answer, Math.abs(cnt - arr1[y][x]));
                    arr1[y][x] = cnt++;
                }
            }
        }


        System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
    }
}