import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        int blackCnt = 0;
        int whiteCnt = 0;
        String str1 = br.readLine();
        String str2 = br.readLine();

        for (int i = 0; i < N; i++) {
            if (str1.charAt(i) != str2.charAt(i)) {
                if (str1.charAt(i) == 'W') whiteCnt++;
                if (str1.charAt(i) == 'B') blackCnt++;
            }
        }

        int answer = Math.abs(blackCnt - whiteCnt);
        answer += Math.min(blackCnt, whiteCnt);

        System.out.println(answer);

    }
}