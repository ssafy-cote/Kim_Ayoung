import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.StringTokenizer;

/*
 * 문제
 * 1. 나무 M미터 필요 
 * 2. 목재 절단기는 다음과 같이 작동
 * 	- 절단기에 높이 H 지정
 * 	- 높이가 H보다 큰 나무는 H위의 부분이 잘리고, 낮은 나무는 잘리지 않음
 * 3. 필요한 나무만틈만 집으로 가져가려고 한다. 
 * 4. 적어도 M미터의 나무를 집에 가져가기 위해서 절단기에 설정할 수 있는 높이의 최대값
 * 
 * 입력
 * 1. 나무의 수 N, 집으로 가져가려고 하는 나무의 길이 M
 * 	- N 백만이하, M 20억 이하
 * 2. 나무의 높이  십억 이하
 * 
 * 출력
 * 높이의 최댓값
 * 
 * 풀이 과정
 * 1. long을 사용해서 나무의 수와 나무의 길이를 입력을 받는다. 
 * 2. HashMap에 저장을 한다. key(길이), value(개수)
 * 3. dp를 이용한다. 2차원 dp // 목재 길이, 목재 절단기의 높이, trees의 개수
 * 4. 높이가 높은 나무부터 for문을 돌면서 배열에 다음 값을 저장한다. 
* 	- 해당 높이의 나무를 넣기 위한 절단기의 높이의 최대 값은 높이 - 1 --> dp[i][1] = 높이 - 1
*	- 현재 자를 수 있는 나무의 개수를 저장 --> dp[i][2] = dp[i - 1][2](이전에 사용한 나무의 개수 ) + 현재 높이의 나무의 개수
* 	- 현재 가지고 갈 수 있는 목재 길이를 저장 dp[i][0] = dp[i - 1][0](이전에 가질 수 있는 목재의 길이) + (이전의 절단기 높이 - 현재의 절단기 높이) * dp[i - 1][2](이전에 사용한 나무의 개수) + 현재 높이의 나무의 개수
* 5. 가지고 갈 수 있는 목재의 길이가 M보다 크거나 같으면 for문을 멈추고 다음을 실행
*	- for문이 한 번만 실행되었다면 dp[0][1] return
* 	- 한번 이상 실행이 되었다면 이전 idx를 가진 dp에서 절단기의 높이를 1씩 감소시키면서 목재의 길이가 M을 넘기는 순간 answer 출력
*/
public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int maxTreeLength = 0;
		
		HashMap<Integer, Integer> trees = new HashMap<>();
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++) {
			int treeLength =  Integer.parseInt(st.nextToken());
			if(trees.containsKey(treeLength)) trees.put(treeLength, trees.get(treeLength) + 1);
			else trees.put(treeLength, 1);
			
			if(maxTreeLength < treeLength) maxTreeLength = treeLength;
		}
		
		int[][] dp = new int[trees.size()][3]; // 목재 길이, 목재 절단기의 높이, trees의 개수
		
		List<Integer> treeLength = new ArrayList<>(trees.keySet());
		Collections.sort(treeLength, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				// TODO Auto-generated method stub
				return o2 - o1;
			}
		});
		
		int idx = 0;
		for(Integer key: treeLength) {
			if(idx == 0) {
				dp[idx][0] = trees.get(key);
				dp[idx][1] = key - 1;
				dp[idx][2] = trees.get(key);
			}
			else {
				dp[idx][1] =  key - 1;
				dp[idx][0] = dp[idx - 1][0] + dp[idx - 1][2] * (dp[idx - 1][1] - dp[idx][1]) + trees.get(key);
				dp[idx][2] = dp[idx - 1][2] + trees.get(key);
			}
			
			if(dp[idx][0] >= M) break;
			idx++;
		}
		
		if(idx != 0) {
			int answer = dp[idx - 1][1];
			int myTree = dp[idx - 1][0];
			
			while(myTree < M) {
				answer--;
				myTree += dp[idx - 1][2];
			}
			System.out.println(answer);
		}
		else {
			System.out.println(dp[idx][1]);
		}
	}

}
