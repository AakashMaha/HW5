package hw5;

public class Node {
	int num;
	static int sum = 0;
	Node next;

	Node(int number) {
		num = number;
		next = null;
	}

	static void findTotalRecursive(Node head) {
		if (head == null) {
			return; // END CASE
		}
		findTotalRecursive(head.next); // RECURSIVE CALL TO NEXT NODE

		sum = sum + head.num; // ADD ALL VALUES TOGETHER

	}

	static int findTotal(Node head) {
		sum = 0;
		findTotalRecursive(head);
		return sum; // RETURN SUM
	}

	// --------------------------------------------
	public static double findMean(int total, int size) {
		double mean = 0;
		mean = ((double) total) / ((double) size); // DIVIDE SUM OF VALUES BY NUMBER OF VALUES
		return mean;
	}
	// -----------------------------------------------

}
