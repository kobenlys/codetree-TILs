import java.io.*;
import java.util.*;

public class Main {
    public static int N, M, K;
    public static node[][] arr1;
    public static player[][] map;
    public static PriorityQueue<player> pq = new PriorityQueue<>();
    public static int[] score;

    public static class player {
        int dir, humanNum, humanAttack, gunAttack;

        public player(int dir, int humanNum, int humanAttack, int gunAttack) {
            this.dir = dir;
            this.humanNum = humanNum;
            this.humanAttack = humanAttack;
            this.gunAttack = gunAttack;
        }
    }

    public static class node {
        ArrayList<Integer> list = new ArrayList<>();

        public node(int gun) {
            this.list.add(gun);
        }
    }

    public static void playerFight(int nx, int ny, player p1, player p2) {
        int moveP = p1.gunAttack + p1.humanAttack;
        int beforeP = p2.gunAttack + p2.humanAttack;
        

        // 진사람 이동 해야함.., 총기 떨어트리고
        if (moveP > beforeP) {

            score[p1.humanNum] += Math.abs(moveP - beforeP);
            arr1[ny][nx].list.add(p2.gunAttack);
            p2.gunAttack = 0;

            map[ny][nx] = p1;

            winPlayerPickUp(ny, nx);
            losePlayerMove(p2, nx, ny);
        }

        if (moveP < beforeP) {
            score[p2.humanNum] += Math.abs(moveP - beforeP);
            arr1[ny][nx].list.add(p1.gunAttack);
            p1.gunAttack = 0;
            map[ny][nx] = p2;

            winPlayerPickUp(ny, nx);
            losePlayerMove(p1, nx, ny);
        }

        if (moveP == beforeP) {
            if (p1.humanAttack > p2.humanAttack) {
                score[p1.humanNum] += Math.abs(moveP - beforeP);
                arr1[ny][nx].list.add(p2.gunAttack);
                p2.gunAttack = 0;

                map[ny][nx] = p1;
                winPlayerPickUp(ny, nx);
                losePlayerMove(p2, nx, ny);
            }
            if (p1.humanAttack < p2.humanAttack) {
                score[p2.humanNum] += Math.abs(moveP - beforeP);
                arr1[ny][nx].list.add(p1.gunAttack);
                p1.gunAttack = 0;

                map[ny][nx] = p2;
                winPlayerPickUp(ny, nx);
                losePlayerMove(p1, nx, ny);
            }
        }
    }

    public static void losePlayerMove(player p, int x, int y) {
        int[] dx = {0, 1, 0, -1};
        int[] dy = {-1, 0, 1, 0};


        for (int i = p.dir; i < i + 4; i++) {
            int newDir = i % 4;
            int nx = x + dx[newDir];
            int ny = y + dy[newDir];
            if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;

            if (map[ny][nx].humanNum == 0) {
                map[ny][nx] = p;

                Collections.sort(arr1[ny][nx].list, Comparator.reverseOrder());
                if (p.gunAttack < arr1[ny][nx].list.get(0)) {
                    if (arr1[ny][nx].list.get(0) == 0) return;
                    arr1[ny][nx].list.add(p.gunAttack);
                    p.gunAttack = arr1[ny][nx].list.get(0);
                    arr1[ny][nx].list.remove(0);
                }
                p.dir = newDir;
                map[ny][nx] = p;
                break;
            }
        }
    }

    public static void winPlayerPickUp(int ny, int nx) {
        Collections.sort(arr1[ny][nx].list, Comparator.reverseOrder());
        player p = map[ny][nx];

        if (p.gunAttack < arr1[ny][nx].list.get(0)) {
            if (arr1[ny][nx].list.get(0) == 0) return;
            arr1[ny][nx].list.add(p.gunAttack);
            p.gunAttack = arr1[ny][nx].list.get(0);
            arr1[ny][nx].list.remove(0);
        }
        map[ny][nx] = p;
    }

    public static void playerMove(int x, int y) {
        int[] dx = {0, 1, 0, -1};
        int[] dy = {-1, 0, 1, 0};

        player p1 = map[y][x];
        map[y][x] = new player(0, 0, 0, 0);
        int nx = x + dx[p1.dir];
        int ny = y + dy[p1.dir];

        if (p1.dir == 0 || p1.dir == 2) {
            if (ny < 0) {
                ny = 1;
                p1.dir = 2;
            }
            if (ny >= N) {
                ny = N - 2;
                p1.dir = 0;
            }

        } else {
            if (nx < 0) {
                nx = 1;
                p1.dir = 1;
            }
            if (nx >= N) {
                nx = N - 2;
                p1.dir = 3;
            }

        }

        if (map[ny][nx].humanNum > 0) {
            player p2 = map[ny][nx];
            map[ny][nx] = new player(0, 0, 0, 0);
            map[y][x] = new player(0, 0, 0, 0);
            playerFight(nx, ny, p1, p2);
        } else {

            Collections.sort(arr1[ny][nx].list, Comparator.reverseOrder());
            if (p1.gunAttack < arr1[ny][nx].list.get(0)) {
                if (arr1[ny][nx].list.get(0) == 0) return;
                arr1[ny][nx].list.add(p1.gunAttack);
                p1.gunAttack = arr1[ny][nx].list.get(0);
                arr1[ny][nx].list.remove(0);
            }
            map[ny][nx] = p1;
        }
    }


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        // 빨강배경 : 총 공격력, 플레이어 공격력 , 노란배경 : 플레이어 순서
        // 진행 순서
        // 1. 플레이어는 바라보는 방향으로 한칸 전진
        // 1 - 1. 격자를 벗어난다면 반대방향으로 전진
        // 2. 이동 후 총이 있는 칸에 도착했다면 총을 챙기기, 총이있다면 더 좋은 총 챙기기 후 가지고있는 총 두기
        // 3. 두 플레이어가 만나게된다면 초기능력치 + 가지고있는 총 능력치 로 싸움, 같다면 초기능력치 큰 사람이 이김
        // 3-1. 이긴 플레이어는 상대가 가지고 있는 능력치 초기능력치 - 가지고있능 총 능력치 절댓값을 포인트로 얻는다.
        // 4. 진 플레이어는 총을 격자 안에 내려놓는다, 가고자 하는 방향으로 한칸 전진
        // 4-1. 가려고하는데 사람 + 맵밖이라면 오른쪽으로 90도회전을 반복하여 빈칸 찾기.
        // 4-2. 이동했다면 총 획득하기
        st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        arr1 = new node[N][N];
        map = new player[N][N];
        score = new int[M + 1];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                int gun = Integer.parseInt(st.nextToken());
                arr1[i][j] = new node(gun);
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                map[i][j] = new player(0, 0, 0, 0);
            }
        }

        for (int i = 1; i <= M; i++) {
            st = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(st.nextToken()) - 1;
            int x = Integer.parseInt(st.nextToken()) - 1;
            int d = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            map[y][x] = new player(d, i, s, 0);
        }

        while (K-- > 0) {

            for (int p = 1; p <= M; p++) {

                st:
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        if (p == map[i][j].humanNum) {
                            playerMove(j, i);
                            break st;
                        }
                    }
                }
            }
        }
        for (int i = 1; i <= M; i++) {
            sb.append(score[i]).append(" ");
        }
        System.out.println(sb);
    }
}