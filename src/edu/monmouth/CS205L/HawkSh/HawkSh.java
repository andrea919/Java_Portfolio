package edu.monmouth.CS205L.HawkSh;
import java.io.*;
import java.util.*;

/**
 * HawkSh.java — Command line interpreter.
 *
 * Parts of the Shell class and its logic have already been written for you.
 * You should NOT modify anything I have already written.
 * Your job is to implement whatever I didn't.
 * 
 * NOTES:
 * 1. Do not use throw anywhere in your code. The throws I wrote are OK.
 * 2. You may ONLY use queue and stack methods that we talked about in class
 *    to get data into and out of your data structures. For example, if
 *    somewhere in your program you have a queue backed by a linked list, you
 *    CANNOT use linked list methods to walk the list. You MUST use dequeue
 *    and enqueue to get data out (and potentially put it back in). You may
 *    use top() and first() however, those are OK. It will simply be marked
 *    wrong if you use non-stack and non-queue methods (e.g., walking a list).
 * 3. We didn't get time to talk about them in class, but you may use double
 *    ended queues (deques) if it better solves your problem somewhere.
 * 4. You must explain what your methods do in a comment. I have left
 *    TODO: Explain what your method does here.
 *    In the places where you need to explain what your method does.
 * 5. Anywhere else I say to leave me a comment and explain something, you
 *    must leave a comment there and explain the thing I want you to explain.
 * 6. Each methods tells you what it should return. Don't change that.
 * 7. You may add additional methods and inner variables, as you need.
 */
public class HawkSh {

    // In a comment here, explain why a circular queue is a good choice
    // (or not) to solve the problems it solves in your shell.
	
	/**
     * A Circular Queue is a good choice to solve this problem because:
     * Efficient memory: prevents the shell to consume too much memory.
     * Performance: While keeping a fixed size, there is no need to shift 
     * the elements but just the pointers.
     */
    static class CircularQueue {

        private static final int CAPACITY = 128;
        int sz, front, last = 0;
        private byte[] data = new byte[CAPACITY];
        
        
        /**
         * This method makes sure the array is not full.
         * If it is not, it calculates the last in queue and increment the size.
         * 
         * @return true if the byte was added, false if the buffer was already full.
         */
        public boolean enqueue(byte b) {
            if(isFull()) {
            		return false;
            }
            last = (front + sz)%CAPACITY;
            data[last] = b;
            sz++;
            return true;
        }

        /**
         * This method makes sure the array is not empty.
         * If it is not, data in the front are dequeued and saved into a tmp, 
         * the front increments by one and the size decrements.
         *
         * @return the unsigned byte value (0–255), or -1 if the buffer was empty.
         */
        public int dequeue() {
            if(isEmpty()) {
            		return -1;
            }
            byte tmp = data[front];
            front = (front+1)%CAPACITY;
            sz--;
            return Byte.toUnsignedInt(tmp);
        }

        /** @return true if the buffer contains no bytes. */
        public boolean isEmpty() {
        		return size() == 0;
        	}

        /** @return true if the buffer has no remaining capacity. */
        public boolean isFull() {
            return size()==CAPACITY;
        }

        /** @return the number of bytes currently stored in the buffer. */
        public int size() {
            return sz;
        }

        /** @return the current value of the tail index. */
        public int getTail() {
        		if (isEmpty()) return -1;
            return last;
        }

        /**
         * Load the bytes of a String into the queue one byte at a time
         * by calling enqueue() in a loop. Stop early if the buffer fills up.
         * If the string ends in a newline character ('\n'), drop the newline
         * character (don't enqueue it). Also don't enqueue any leading or
         * trailing whitespace characters. If there are whitespace characters
         * in the middle of the command, those do need to be enqueued.
         *
         * @return the number of bytes actually enqueued.
         */
        public int loadLine(String line) {
            String cleanLine = line.trim();
            
            int counter = 0;
            byte[] bytes = cleanLine.getBytes();
            for (byte b : bytes) {
            		if(enqueue(b)) {
            			counter++;
            		}else {
            			break;
            		}
            }
            return counter;
        }

        /**
         * Remove and return all bytes currently in the queue as a String,
         * by calling dequeue() in a loop until the queue is empty.
         *
         * @return a String built from the dequeued bytes, in order.
         */
        public String drain() {
        		StringBuilder sb = new StringBuilder();
        		while(!isEmpty()) {
        	   		char b = (char) dequeue(); // cast the int into char
        	   		sb.append(b);
        		}
        		return sb.toString();
        }
    }

    // In a comment here, explain why a stack is a good choice
    // (or not) to solve the problems it solves in your shell.
    
    /**
     * A Stack is a good choice because:
     * Commands Order: it displays the most recent command first.
     * Dynamic: the main difference from an array as overlying data 
     * structures is that the implemented singly linked list, lets 
     * the stack to grow as needed without any top limit. 
     */
    static class CommandStack {

        // --- Inner Node class — do not modify ---
        private static class Node {
            String data;
            Node   next;
            Node(String data, Node next) { this.data = data; this.next = next; }
        }
        
        Node head = null;
        int sz = 0;

        /**
         * This method checks if the stacks is empty:
         * If it is, the method creates a new head, 
         * If it is not, the method creates a new Node which will point to the head,
         * and becomes the head of the stack
         */
        public void push(String command) {
        		if(isEmpty()) {
        			head = new Node(command, null);
        		} else {
            		Node tmp = new Node(command, head);
            		head = tmp;
        		}
        		sz++;
        }

        /**
         * This method checks if the stack is empty:
         * If it is, the method returns null
         * If it is not, the method makes the next node the new head 
         * and it returns the old head's data stored in the tmp variable.
         *
         * @return the removed String, or null if the stack was empty.
         */
        public String pop() {
            if(isEmpty()) { return null;}
            String tmp = head.data;
            head = head.next;
            sz--;
            return tmp; 
        }

        /**
         * This method checks if the stack is empty:
         * If it is the method returns null, 
         * If it is not, the method returns the head's data.
         *
         * @return the top String, or null if the stack was empty.
         */
        public String top() {
        	if(isEmpty()) { return null;}
            return head.data;
        }

        /** @return true if the stack contains no items. */
        public boolean isEmpty() {
            return size()==0;
        }

        /** @return the number of items currently on the stack. */
        public int size() {
            return sz;
        }
    }

    // In a comment here, explain why a queue is a good choice
    // (or not) to solve the problems it solves in your shell.
    /**
     * The queue is a good choice because the order of the directories
     * in a path matters and the order hierarchy is important to 
     * execute a sequential search of a file in this case.
     * The Queue successfully meets this search requirement.
     * Adding or removing directories, both in the middle or 
     * even at the end of the linked list, is not a problem for
     * this data structure which does it with a O(1).
     */
    static class LinkedQueue {

        // --- Inner Node class — do not modify ---
        private static class Node {
            String data;
            Node   next;
            Node(String data) { this.data = data; this.next = null; }
        }
        
        Node head = null;
        Node tail = null;
        int sz = 0;

        /**
         * This method checks if the Queue is empty:
         * If it is the new node becomes head and tail,
         * If it is not, the new node becomes the new tail.
         */
        public void enqueue(String dir) {
        		Node tmp = new Node(dir);
        	
            if(isEmpty()) {
            		head = tmp;
            		tail = head;
            } else {
                tail.next = tmp;
                tail = tmp;
            }
            sz++;
        }

        /**
         * This method checks if the Queue is empty:
         * If it is, it returns null
         * If it is not, the new head is the element before the original head
         * and the old head data is returned as a String.
         *
         * @return the removed String, or null if the queue was empty.
         */
        public String dequeue() {
            if(isEmpty()) {
            		return null;
            }
            String data = head.data;
            head = head.next;
            
            if(head==null) { tail = null; sz=0;}
            
            sz--;
            return data;
        }

        /** @return true if the queue contains no items. */
        public boolean isEmpty() {
            return sz==0;
        }

        /** @return the number of items currently in the queue. */
        public int size() {
            return sz;
        }

        /**
         * Return a snapshot of all directories in front-to-back order
         * WITHOUT using direct node traversal.
         *
         * You may only use dequeue() and enqueue().
         *
         * After the loop the queue must be in exactly the same state
         * (same order, same size) as before the call.
         *
         * @return a String[] of all directories, front-to-back.
         */
        public String[] toArray() {
        		int originalSize = size();
            String[] array = new String[originalSize];
            for(int i=0; i<originalSize; i++) {
            		array[i] = dequeue();
            		enqueue(array[i]);
            }
            
            return array;
        }
    }

    // Main logic for the shell.
    static class Shell {

        private final LinkedQueue   pathQueue      = new LinkedQueue();
        private final CircularQueue inputQueue     = new CircularQueue();
        private final CommandStack  commandHistory = new CommandStack();

        // Don't change this: this concatenates each directory in pathQueue
        // with the command typed in, one at a time, and checks to see if
        // that results in an executable program, stopping if it does.
        private String resolveCommand(String token) {
            if (token.contains("/")) return token;
            for (String dir : pathQueue.toArray()) {
                File candidate = new File(dir, token);
                if (candidate.canExecute()) return candidate.getPath();
            }
            return null;
        }

        /**
         * This method prints the History of the commandHistory Stack
         * While the top of original Stack is not null, 
         * The method will iterate through the CommandStack, 
         * store the entire node in a new tmp Stack and print it out.
         * Additionally, the method will restore the original Stack, 
         * using the same process the other way around.
         */
        private void printHistory() {
            CommandStack tmpStack = new CommandStack();
            while(commandHistory.top() != null) {
            		tmpStack.push(commandHistory.top());
            		System.out.println(commandHistory.pop());
            }
            
            while(tmpStack.top() != null) {
            		commandHistory.push(tmpStack.pop());
            }
        }

        // Don't change this: it prints your path directories out in a way
        // that makes sense for the PATH environment variable.
        private void printPath() {
            String[] dirs = pathQueue.toArray();
            if (dirs.length == 0) {
                System.out.println("PATH: (empty)");
                return;
            }
            StringBuilder sb = new StringBuilder("PATH: ");
            for (int i = 0; i < dirs.length; i++) {
                if (i > 0) sb.append(':');
                sb.append(dirs[i]);
            }
            System.out.println(sb);
        }

        // Don't change this; it splits your command by whitespace into a
        // String[] so that the shell can execute the command.
        private void executeCommand(String rawCommand) {
            String[] parts = rawCommand.trim().split("\\s+");
            if (parts.length == 0 || parts[0].isEmpty()) return;
            String resolved = resolveCommand(parts[0]);
            if (resolved == null) {
                System.out.println("hawksh: " + parts[0] + ": command not found");
                return;
            }
            parts[0] = resolved;
            try {
                ProcessBuilder pb = new ProcessBuilder(parts);
                pb.inheritIO();
                pb.start().waitFor();
            } catch (IOException e) {
                System.out.println("hawksh: " + parts[0] + ": " + e.getMessage());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("hawksh: command interrupted");
            }
        }

        public void run() throws IOException {
            pathQueue.enqueue("/bin");
            pathQueue.enqueue("/usr/bin");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                System.out.print("$ ");
                System.out.flush();

                String line = reader.readLine();
                if (line == null) {
                    System.out.println("\nexit");
                    break;
                }

                int loaded = inputQueue.loadLine(line);
                if (loaded < line.getBytes().length) {
                    System.out.println("shell: input truncated to " + CircularQueue.CAPACITY + " bytes");
                }
                String command = inputQueue.drain().trim();

                if (command.isEmpty()) continue;
                
                String[] parts = command.split("\\s+");
                String commandBase = parts[0];

                // TODO: Implement the command dispatch.
                // You need the following built-in commands:
                switch(commandBase) {
                // 1. exit - exits the shell (quits the program, all done)
                		case "exit":
                			System.out.println("Goodbye!");
                			return;
                	// 2. history - prints, one command per line, all the
                //              previously executed commands.
                		case "history":
                			printHistory();
                			break;
                	// 3. last - prints the most recently executed command.
                		case "last":
                			System.out.println(commandHistory.top());
                			break;
                	// 4. where - prints the current index of the next available
                //            cell in your circular queue.
                		case "where":
                			System.out.println("Next available cell: "+ inputQueue.getTail());
                			break;
                	// 5. pathAdd <dir> - adds <dir> to your pathQueue, then
                //                    prints the new PATH.	
                		case "pathAdd":
                			if(parts.length > 1) {
                				pathQueue.enqueue(parts[1]);
                    			printPath();
                			} else {
                				System.out.println("Error: Please specify a directory to add.");
                			}
                			break;
                	// 6. pathRemove <dir> - removes <dir> from your pathQueue,
                //                       then prints the new PATH.
                		case "pathRemove":
                			if(parts.length > 1) {
                				String pathToRemove = parts[1];
                				int originalSize = pathQueue.size();
                				for (int i=0; i<originalSize; i++) {
                					String value = pathQueue.dequeue();
                					if(!value.equals(pathToRemove)) {
                						pathQueue.enqueue(value);
                					}
                				}
                				printPath();
                			} // if there are not enough parts
                			break;
                			
                	// 7. If it is none of the built-in commands, try to execute
                //    whatever the user typed in using executeCommand.
                		default:
                			executeCommand(command);
                			break;
           
                } // switch
                
                // After the command is run, built-in command or not, you need
                // to add the command to your commandHistory.
                commandHistory.push(command);
                
            } // while
        } // run method
    } // shell class

    public static void main(String[] args) throws IOException {
        new Shell().run();
    }
}
