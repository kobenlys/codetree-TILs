import java.io.*;
import java.util.*;

public class Main {
    public static int N, M, K, answer;
    public static int[][] arr1;
    public static boolean[][] vi;
    public static ArrayList<ArrayList<node>> list;

    public static int[] dx = {0, 0, -1, 1};
    public static int[] dy = {-1, 1, 0, 0};

    public static class node {
        int x, y, num;

        public node(int x, int y, int num) {
            this.x = x;
            this.y = y;
            this.num = num;
        }
    }

    public static void findTeams(int x, int y, int team) {
        Queue<node> qu = new ArrayDeque<>();
        qu.offer(new node(x, y, 0));

        while (!qu.isEmpty()) {

            node nd = qu.poll();

            if (arr1[nd.y][nd.x] != 4 && arr1[nd.y][nd.x] != 0) {

                list.get(team).add(new node(nd.x, nd.y, arr1[nd.y][nd.x]));
            }

            for (int i = 0; i < 4; i++) {

                int nx = nd.x + dx[i];
                int ny = nd.y + dy[i];
                if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;

                if (arr1[ny][nx] != 0 && !vi[ny][nx]) {
                    vi[ny][nx] = true;
                    qu.offer(new node(nx, ny, 0));
                }
            }
        }
        Collections.sort(list.get(team), (o1, o2) -> o1.num - o2.num);
    }

    public static void moveTeam() {


        for (int i = 0; i < M; i++) {


            node nd = list.get(i).get(0);
            node nd2 = list.get(i).get(1);

            node nd3 = list.get(i).get(list.get(i).size() - 1);
            node nd4 = list.get(i).get(list.get(i).size() - 2);

            if (nd.num == 1) {
                for (int k = 0; k < 4; k++) {
                    int nx = nd.x + dx[k];
                    int ny = nd.y + dy[k];
                    if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;

                    if (arr1[ny][nx] != 0 && !(nd2.y == ny && nd2.x == nx)) {

                        for (int w = list.get(i).size() - 1; w >= 1; w--) {
                            node before = list.get(i).get(w - 1);
                            list.get(i).get(w).y = before.y;
                            list.get(i).get(w).x = before.x;
                        }
                        nd.y = ny;
                        nd.x = nx;
                        list.get(i).set(0, nd);
                        break;
                    }
                }
            }else{
                for (int k = 0; k < 4; k++) {
                    int nx = nd3.x + dx[k];
                    int ny = nd3.y + dy[k];
                    if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;

                    if (arr1[ny][nx] != 0 && !(nd4.y == ny && nd4.x == nx)) {

                        for (int w = 0; w < list.get(i).size()-1; w++) {
                            node before = list.get(i).get(w + 1);
                            list.get(i).get(w).y = before.y;
                            list.get(i).get(w).x = before.x;
                        }
                        nd3.y = ny;
                        nd3.x = nx;
                        list.get(i).set(list.get(i).size() - 1, nd3);
                        break;
                    }
                }
            }


        }
    }

    public static void reverseTeam(int team) {

        for (int i = 0; i < list.get(team).size(); i++) {

            if (list.get(team).get(i).num == 1) {
                list.get(team).get(i).num = 3;

            } else if (list.get(team).get(i).num == 3) {
                list.get(team).get(i).num = 1;
            }
        }
    }


    public static void leftToRight(int v) {

        for (int i = 0; i < N; i++) {
            int x = i;

            for (int j = 0; j < M; j++) { // team num
                for (int k = 0; k < list.get(j).size(); k++) {

                    node nd = list.get(j).get(k);

                    if (nd.x == x && nd.y == v) {

                        answer += (int) Math.pow(k + 1, 2);

                        reverseTeam(j);
                        return;
                    }
                }
            }
        }
    }

    public static void downToUp(int v) {

        for (int i = N - 1; i >= 0; i--) {
            int y = i;

            for (int j = 0; j < M; j++) { // team num
                for (int k = 0; k < list.get(j).size(); k++) {

                    node nd = list.get(j).get(k);

                    if (nd.x == v && nd.y == y) {

                        answer += (int) Math.pow(k + 1, 2);
                        reverseTeam(j);
                        return;
                    }
                }
            }
        }
    }

    public static void rightToLeft(int v) {
        v = (N - v) - 1;

        for (int i = N - 1; i >= 0; i--) {
            int x = i;

            for (int j = 0; j < M; j++) { // team num
                for (int k = 0; k < list.get(j).size(); k++) {

                    node nd = list.get(j).get(k);

                    if (nd.x == x && nd.y == v) {

                        answer += (int) Math.pow(k + 1, 2);
                        reverseTeam(j);
                        return;
                    }
                }
            }
        }

    }

    public static void upToDown(int v) {
        v = (N - 1) - v;

        for (int i = 0; i < N; i++) {
            int y = i;

            for (int j = 0; j < M; j++) { // team num
                for (int k = 0; k < list.get(j).size(); k++) {

                    node nd = list.get(j).get(k);

                    if (nd.x == v && nd.y == y) {

                        answer += (int) Math.pow(k + 1, 2);
                        reverseTeam(j);
                        return;
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken()); // 맵크기
        M = Integer.parseInt(st.nextToken()); // 팀 수
        K = Integer.parseInt(st.nextToken()); // 라운드 수

        arr1 = new int[N][N];
        vi = new boolean[N][N];
        list = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                arr1[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < M; i++) {
            list.add(new ArrayList<>());
        }

        int teamNum = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (arr1[i][j] != 0 && !vi[i][j]) {
                    vi[i][j] = true;
                    findTeams(j, i, teamNum++);
                }
            }
        }
        int cnt = 0;


        for (int i = 0; i < K; i++) {

            moveTeam();

            if (cnt < N) {
                leftToRight(cnt % N);
            }

            if (cnt >= N && cnt < N * 2) {
                downToUp(cnt % N);
            }

            if (cnt >= N * 2 && cnt < N * 3) {
                rightToLeft(cnt % N);
            }

            if (cnt >= N * 3 && cnt < N * 4) {
                upToDown(cnt % N);
            }

            cnt++;
            if (cnt >= N * 4) {
                cnt = 0;
            }
        }
        System.out.println(answer);
    }
}