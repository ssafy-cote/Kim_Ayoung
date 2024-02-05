/*
1. 앞으로 뒤로만 움직일 경우의 수 찾기
2. 앞으로도 가고 뒤로도 가는 경우의 수 찾기 - 재귀 이용
	start 0, end 0 index로 재귀 시작
 	index 값이 array 범위를 넘어가면 return
  	start의 값과 end의 값이 같으면 end + 1로 재귀 
   	start의 array가 A이면 start + 1을 하여 재귀 -> A일 경우에는 해당 index까지 접근할 필요가 없음
    	end의 array가 A이면 end + 1 하여 재귀 -> A일 경우에는 해당 index까지 접근 불필요
     	start 쪽으로 두번 혹은 end쪽으로 두번 이동할 경우 두 경우 중 더 작은 경우를 move에 저장
*/
class Solution {
    static int move = Integer.MAX_VALUE;
    static char[] array;
    public void moveMin(int start, int end) {
		if(start == array.length || end == array.length) return;
		else if(start == end) {
			moveMin(start, end + 1);
		}
		else if(array[start] == 'A') {
			moveMin(start + 1, end);
		}
		else if( array[end] == 'A') {
			moveMin(start, end + 1);
		}
		else {
			move = Math.min(Math.min(start * 2 + array.length - end, start + (array.length - end) * 2), move);
			moveMin(start + 1, end);
		}
		
	}
    public int solution(String name) {
        int answer = 0;
        array = name.toCharArray();
        
        for(int i = 0 ; i < array.length; i++) {
			char c = array[i];
			answer += Math.min(c - 'A', 'Z' - c + 1);
		}
        
        moveMin(0, 0);
        // 뒤로 탐색
		for(int i = array.length - 1; i > 0; i--) {
			if(array[i] == 'A') continue;
			move = Math.min(move, i);
			break;
		}
		// 앞으로
		for(int i = 0; i < array.length; i++) {
			if(array[i] == 'A') continue;
			move = Math.min(move, array.length - i);
			break;
		}
		if(move == Integer.MAX_VALUE) move = 0;
        return answer + move;
    }
}
