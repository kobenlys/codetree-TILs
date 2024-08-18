import java.io.*;
import java.util.*;

public class Main {
    public static int N, M, K, C, answer;
    public static int[][] arr1;
    public static int[][] antiBug;

    public static int[] dx = {0, 0, -1, 1};
    public static int[] dy = {-1, 1, 0, 0};

    public static class node implements Comparable<node> {
        int x, y, cnt;

        public node(int x, int y, int cnt) {
            this.x = x;
            this.y = y;
            this.cnt = cnt;
        }


        @Override
        public int compareTo(node o) {
            if (cnt == o.cnt) {
                if (x == o.x) {
                    return y - o.y;
                }
                return x - o.x;
            }
            return o.cnt - cnt;
        }
    }

    public static void growWoods(int x, int y) {
        //  주변에 나무가 있는 만큼 성장

        int cnt = 0;

        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;

            if (arr1[ny][nx] > 0) {
                cnt++;
            }
        }
        arr1[y][x] += cnt;
    }

    public static void spreadWoods() {

        int[][] woods = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {

                if (arr1[i][j] > 0) {
                    ArrayList<node> list = new ArrayList<>();

                    for (int k = 0; k < 4; k++) {
                        int nx = j + dx[k];
                        int ny = i + dy[k];

                        if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;

                        if (arr1[ny][nx] == 0 && antiBug[ny][nx] == 0) {
                            list.add(new node(nx, ny, -1));
                        }
                    }
                    if(list.isEmpty()) continue;
                    int num = arr1[i][j] / list.size();
                    for (node nd : list) {
                        woods[nd.y][nd.x] += num;
                    }
                }
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (arr1[i][j] == 0) {
                    arr1[i][j] += woods[i][j];
                }
            }
        }
    }

    public static void antiBugSpray() {

        int[] dx1 = {-1, 1, -1, 1};
        int[] dy1 = {1, 1, -1, -1};
        ArrayList<node> list = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if(arr1[i][j] == -1) continue;
                int cnt = arr1[i][j];
                int idx = 1;

                while (true) {
                    int nx = j + dx1[0] * idx;
                    int ny = i + dy1[0] * idx++;

                    if (nx < 0 || ny < 0 || nx >= N || ny >= N) break;
                    if (arr1[ny][nx] == -1) break;

                    cnt += arr1[ny][nx];
                    if(idx == K+1) break;
                }
                idx = 1;
                while (true) {
                    int nx = j + dx1[1] * idx;
                    int ny = i + dy1[1] * idx++;

                    if (nx < 0 || ny < 0 || nx >= N || ny >= N) break;
                    if (arr1[ny][nx] == -1) break;

                    cnt += arr1[ny][nx];
                    if(idx == K+1) break;
                }
                idx = 1;
                while (true) {
                    int nx = j + dx1[2] * idx;
                    int ny = i + dy1[2] * idx++;

                    if (nx < 0 || ny < 0 || nx >= N || ny >= N) break;
                    if (arr1[ny][nx] == -1) break;

                    cnt += arr1[ny][nx];
                    if(idx == K+1) break;
                }
                idx = 1;
                while (true) {
                    int nx = j + dx1[3] * idx;
                    int ny = i + dy1[3] * idx++;

                    if (nx < 0 || ny < 0 || nx >= N || ny >= N) break;
                    if (arr1[ny][nx] == -1) break;

                    cnt += arr1[ny][nx];
                    if(idx == K+1) break;
                }
                list.add(new node(j, i, cnt));
            }
        }

        Collections.sort(list);
        node tmp = list.get(0);
        answer += tmp.cnt;
        arr1[tmp.y][tmp.x] = 0;
        antiBug[tmp.y][tmp.x] = C;

        int idx = 1;
        while (true) {
            int nx = tmp.x + dx1[0] * idx;
            int ny = tmp.y + dy1[0] * idx++;

            if (nx < 0 || ny < 0 || nx >= N || ny >= N) break;
            if (arr1[ny][nx] == -1) break;

            arr1[ny][nx] = 0;
            antiBug[ny][nx] = C;
            if(idx == K+1) break;
        }

        idx = 1;
        while (true) {
            int nx = tmp.x + dx1[1] * idx;
            int ny = tmp.y + dy1[1] * idx++;

            if (nx < 0 || ny < 0 || nx >= N || ny >= N) break;
            if (arr1[ny][nx] == -1) break;

            arr1[ny][nx] = 0;
            antiBug[ny][nx] = C;
            if(idx == K+1) break;
        }

        idx = 1;
        while (true) {

            int nx = tmp.x + dx1[2] * idx;
            int ny = tmp.y + dy1[2] * idx++;

            if (nx < 0 || ny < 0 || nx >= N || ny >= N) break;
            if (arr1[ny][nx] == -1) break;
            arr1[ny][nx] = 0;
            antiBug[ny][nx] = C;
            if(idx == K+1) break;

        }

        idx = 1;
        while (true) {
            int nx = tmp.x + dx1[3] * idx;
            int ny = tmp.y + dy1[3] * idx++;

            if (nx < 0 || ny < 0 || nx >= N || ny >= N) break;
            if (arr1[ny][nx] == -1) break;
            arr1[ny][nx] = 0;
            antiBug[ny][nx] = C;
            if(idx == K+1) break;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        arr1 = new int[N][N];
        antiBug = new int[N][N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                arr1[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < M; i++) {

            //제초제 효과 제거
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < M; k++) {
                    if(antiBug[i][j] > 0){
                        antiBug[i][j]--;
                    }
                }
            }

            // 성장
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < N; k++) {
                    if (arr1[j][k] > 0) {
                        growWoods(k, j);
                    }
                }
            }
            // 번식
            spreadWoods();

            // 제초제
            antiBugSpray();
        }

        System.out.println(answer);
    }
}