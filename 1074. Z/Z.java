import java.util.Scanner;
/*
* 찾아야 하는 r, c가 사각형의 처음 r,c와 마지막 r,c 사이에 있다면 재귀를 돌아 더 작은 사각형으로 비교한다. 
* 만약에 사이에 없다면 for문을 돌아 사각형의 r, c를 변경해준다. 이때 재귀를 돌지 않을 때 r,c는 최대 4번 바뀐다.(그 안에 재귀를 돈다)
*/
public class Main {
	
	static int answer = -1;
	static int N, r, c, setN, cnt;

	public static void visited(int startRow, int startCol, int size) {
		
		for(int i = startRow; i > startRow - size * 2; i = i - size) {
			for(int j = startCol ; j > startCol - size * 2 ; j = j - size) {
				if(i == r && j == c) {
					answer = cnt;
					return;
				}
				if(size != 0 && i - size < r && j - size < c) {
					size = size / 2;
					visited(i, j, size);
				}
				if(answer != -1) return;
				cnt -= size * size;
			}
		}
		
	}
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		setN = (int) Math.pow(2, N);
		r = sc.nextInt();
		c = sc.nextInt();
		sc.close();
		int size = (setN / 2);
		cnt = setN * setN - 1;

		visited(setN - 1, setN - 1, size);
		System.out.println(answer);
	}
}
