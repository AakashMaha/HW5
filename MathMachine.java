package hw5;

public class MathMachine {

	Node head;

	public MathMachine() {

	}

	public static MathMachine insert(MathMachine list, int num) {
		Node newNum = new Node(num);

		if (list.head == null) { //CASE: IF LIST IS EMPTY
			list.head = newNum;
		} else {
			Node old = list.head;
			while (old.next != null) {
				old = old.next;
			}
			old.next = newNum; //SCOOTS OLD VALUE OVER TO INSERT NEW VALUE
		}
		return list;
	}

	public static void printList(MathMachine list) {
		Node node = list.head;

		System.out.print("LinkedList: ");

		
		while (node != null) { //RUNS THROUGH ENTIRE LIST
			System.out.print(node.num + " "); //OUTPUT NODE

			// Go to next node
			node = node.next; //MOVE TO NEXT
		}
	}

}
