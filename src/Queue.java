// Java program for array implementation of queue

// A class to represent a queue
class Queue
{
    int front, rear, size;
    int  capacity;
    BTreeNode array[];

    public Queue(int capacity) {
        this.capacity = capacity;
        front = this.size = 0;
        rear = capacity - 1;
        array = new BTreeNode[this.capacity];

    }

    // Queue is full when size becomes equal to 
    // the capacity 
    boolean isFull()
    {  return (size == capacity);
    }

    // Queue is empty when size is 0
    boolean isEmpty()
    {  return (size == 0); }

    // Method to add an item to the queue. 
    // It changes rear and size
    void enqueue( BTreeNode item)
    {
        if (isFull())
            return;
        this.rear = (this.rear + 1)%this.capacity;
        this.array[this.rear] = item;
        this.size = this.size + 1;
        System.out.println(item+ " enqueued to queue");
    }

    // Method to remove an item from queue.  
    // It changes front and size
    public BTreeNode dequeue()
    {
        if (isEmpty())
            return null;

        BTreeNode item = this.array[this.front];
        this.front = (this.front + 1)%this.capacity;
        this.size = this.size - 1;
        return item;
    }

    // Method to get front of queue
    public BTreeNode front()
    {
        if (isEmpty())
            return null;

        return this.array[this.front];
    }

    // Method to get rear of queue
    public BTreeNode rear()
    {
        if (isEmpty())
            return null;

        return this.array[this.rear];
    }
}