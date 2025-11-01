public class QueueUsingArray {
    int front, rear, size;
    int capacity;
    int[] queue;

    public QueueUsingArray(int capacity) {
        this.capacity = capacity;
        queue = new int[capacity];
        front = 0;
        size = 0;
        rear = -1;
    }

    void enqueue(int item) {
        if (size == capacity) {
            System.out.println("Queue full");
            return;
        }
        rear = (rear + 1) % capacity;
        queue[rear] = item;
        size++;
    }

    int dequeue() {
        if (size == 0) throw new RuntimeException("Queue empty");
        int item = queue[front];
        front = (front + 1) % capacity;
        size--;
        return item;
    }

    void printQueue() {
        for (int i = 0; i < size; i++) {
            System.out.print(queue[(front + i) % capacity] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        QueueUsingArray q = new QueueUsingArray(5);
        q.enqueue(10);
        q.enqueue(20);
        q.enqueue(30);
        q.printQueue();
        q.dequeue();
        q.printQueue();
    }
}
