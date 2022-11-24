package edu.iastate.cs228.hw3;

import java.util.AbstractSequentialList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Implementation of the list interface based on linked nodes
 * that store multiple items per node.  Rules for adding and removing
 * elements ensure that each node (except possibly the last one)
 * is at least half full.
 */
public class StoutList<E extends Comparable<? super E>> extends AbstractSequentialList<E>
{
  /**
   * Default number of elements that may be stored in each node.
   */
  private static final int DEFAULT_NODESIZE = 4;
  
  /**
   * Number of elements that can be stored in each node.
   */
  private final int nodeSize;
  
  /**
   * Dummy node for head.  It should be private but set to public here only  
   * for grading purpose.  In practice, you should always make the head of a 
   * linked list a private instance variable.  
   */
  public Node head;
  
  /**
   * Dummy node for tail.
   */
  private Node tail;
  
  /**
   * Number of elements in the list.
   */
  private int size;
  
  /**
   * Constructs an empty list with the default node size.
   */
  public StoutList()
  {
    this(DEFAULT_NODESIZE);
  }

  /**
   * Constructs an empty list with the given node size.
   * @param nodeSize number of elements that may be stored in each node, must be 
   *   an even number
   */
  public StoutList(int nodeSize)
  {
    if (nodeSize <= 0 || nodeSize % 2 != 0) throw new IllegalArgumentException();
    
    // dummy nodes
    head = new Node();
    tail = new Node();
    head.next = tail;
    tail.previous = head;
    this.nodeSize = nodeSize;
  }
  
  /**
   * Constructor for grading only.  Fully implemented. 
   * @param head
   * @param tail
   * @param nodeSize
   * @param size
   */
  public StoutList(Node head, Node tail, int nodeSize, int size)
  {
	  this.head = head; 
	  this.tail = tail; 
	  this.nodeSize = nodeSize; 
	  this.size = size; 
  }

  @Override
  public int size()
  {
    return size;
  }
  
  @Override
  public boolean add(E item)
  {
	//if the item is null
	  if (item == null) {
		  //throw a new NullPointerException()
		  throw new NullPointerException(); 
	  } 
	  
	  //if there aren't any nodes in the list
	  if(size == 0) {
	  	//create a node to put the item
		Node node = new Node();
		//connect it to the head and the tail pointers
		node.next = tail; 
		head.next = node; 
		node.previous = head;
		tail.previous = node; 
	  	//put the item into the node
		node.addItem(item);
		//increment size by one
		size++; 
	  }
	  
	  //else there is a node to put it in (put it in the last node)
	  else {
		//if the last node is not full
		if (tail.previous.count < nodeSize) {
		//put the item into that node at the next open position
			tail.previous.addItem(item);
			size++; 
		}
		
		//otherwise if the last node is full create a new node and put it into that
		else if(tail.previous.count == nodeSize) {
			//create a new node
			Node node = new Node();
			//put the item into the node
			node.addItem(item);
			//create a temp node to point to
			Node n = tail.previous;  
			//connect it to the tail pointer
			node.next = tail;
			n.next = node;
			node.previous = n;
			tail.previous = node;

			//increment size by one
			size++; 
		}
	  }

    return true;
  }

  @Override
  public void add(int pos, E item)
  {
	//if the list is empty
	  if (head.next == tail) {
		  //same procedure as the normal add
		  add(item); 
		  return; 
	  } 
  
	  //otherwise need to know the info about the node where you want to add it
	  //get the nodeInfo about the position 
	  NodeInfo nodeInfo = find(pos); 
	  Node n = nodeInfo.node; 
	  int off = nodeInfo.offset; 
	
	  //else if offset == 0
	  if (off == 0) {
		//if n has a predecessor which was fewer than M elements
		  if (n.previous.count < nodeSize && n.previous != head) {
			//put e in n's predecessor
			  n.previous.addItem(item);
			  size++; 
			  return; 
		  }

		//if n is the tail node and n's predecessor has M elements
		  if (n == tail && n.previous.count == nodeSize) {
			  add(item); 
		  }
	  }
	  //else if there is space in node n
	  if (n.count < nodeSize) 
		  //put e in node n at offset off
		  n.addItem(off, item);
		  
	  //perform a split operation
	  else {
		  Node sNode = new Node();
		  for (int i = 0; i < nodeSize/2; i++) {
			  sNode.addItem(n.data[nodeSize/2]);
			  n.removeItem(nodeSize/2);
		  }
		  
		  Node pNode = n.next; 
		  n.next = sNode; 
		  sNode.next = pNode;
		  pNode.previous = sNode; 
		  
		  if (off <= nodeSize / 2)
			  n.addItem(off, item); 
		  
		  if (off > nodeSize / 2)
			  sNode.addItem(off - nodeSize / 2, item);
		  
	  }
	size++; 
		
  }

  @Override
  public E remove(int pos)
  {
	  
	  //get the node info for the node you want to remove from
	  NodeInfo info = find(pos);
	  Node n = info.node;
	  int off = info.offset; 
	  E value = n.data[off]; 
	  
	  //if the node n containing X is the last node and has only one element 
	  if (n.next == tail && n.count == 1) {
		  //delete the node
		  Node temp = n.previous;
		  temp.next = n.next; 
		  n.next.previous = temp;
		  n = null; 
	  }
	  //otherwise, if n is the last node or if n has more than M/2 elements
	  else if ((n.next == tail && n.count >= 2) || n.count > nodeSize / 2) 
		  //remove x form n, shifting elements as necessary
		  n.removeItem(off);
	  
	  //otherwise, the node n must have at most M/2 element
	  else {
		  //look at its successor n
		  Node successor = n.next; 
		  n.removeItem(off);
		  
		  //perform a merge operation as follows
		  //if the successor node has more than M/2 elements
		  if (successor.count > nodeSize / 2) {
			  //move the first element from the successor to n
			  E temp = successor.data[0];
			  n.addItem(temp);
			  successor.removeItem(0); 
		  } 
		  //if the successor node has M/2 or fewer element
		  else if (successor.count <= nodeSize/2) {
			  //move all elements from the successor to n
			  for (E e : successor.data) {
				  if (e != null)
					  n.addItem(e); 
			  }
			  //delete the successor (full merge)
			  successor.next.previous = successor.previous;
			  successor.previous.next = successor.next; 
			  successor = null; 
		  }
	  }
	  
	size --; 
    return value;
  }

  /**
   * Sort all elements in the stout list in the NON-DECREASING order. You may do the following. 
   * Traverse the list and copy its elements into an array, deleting every visited node along 
   * the way.  Then, sort the array by calling the insertionSort() method.  (Note that sorting 
   * efficiency is not a concern for this project.)  Finally, copy all elements from the array 
   * back to the stout list, creating new nodes for storage. After sorting, all nodes but 
   * (possibly) the last one must be full of elements.  
   *  
   * Comparator<E> must have been implemented for calling insertionSort().    
   */
  public void sort()
  {
	  //make array to use to sort the data
	  E[] arr = (E[]) new Comparable[size];
	  //implement the comparator 
	  Comparator<E> c = new Comparator<E>() {

		@Override
		public int compare(E o1, E o2) {
			return o1.compareTo(o2);
		}
		  
	  };
	  
	   //make the array
	   arr = makeArray(); 
	  
	   //sort the data
	   insertionSort(arr, c); 
	   
	   //reset the size
	   size = 0; 
	   //put all of the elements back into the list
	   for (E e : arr) {
		   add(e); 
	   }
  }
  
  /**
   * Sort all elements in the stout list in the NON-INCREASING order. Call the bubbleSort()
   * method.  After sorting, all but (possibly) the last nodes must be filled with elements.  
   *  
   * Comparable<? super E> must be implemented for calling bubbleSort(). 
   */
  public void sortReverse() 
  {
	   //make array to use to sort the data
	   E[] arr = (E[]) new Comparable[size];
	  

	   //make the array
	   arr = makeArray();

	   //sort the data
	   bubbleSort(arr); 
	   
	   //reset the size
	   size = 0; 
	   //put all of the elements back into the list
	   for (E e : arr) {
		   add(e); 
	   }
	  
  }
  
  @Override
  public Iterator<E> iterator()
  {
    return new StoutListIterator();
  }

  @Override
  public ListIterator<E> listIterator()
  {
    return new StoutListIterator();
  }

  @Override
  public ListIterator<E> listIterator(int index)
  {
    return new StoutListIterator(index);
  }
  
  /**
   * Returns a string representation of this list showing
   * the internal structure of the nodes.
   */
  public String toStringInternal()
  {
    return toStringInternal(null);
  }

  /**
   * Returns a string representation of this list showing the internal
   * structure of the nodes and the position of the iterator.
   *
   * @param iter
   *            an iterator for this list
   */
  public String toStringInternal(ListIterator<E> iter) 
  {
      int count = 0;
      int position = -1;
      if (iter != null) {
          position = iter.nextIndex();
      }

      StringBuilder sb = new StringBuilder();
      sb.append('[');
      Node current = head.next;
      while (current != tail) {
          sb.append('(');
          E data = current.data[0];
          if (data == null) {
              sb.append("-");
          } else {
              if (position == count) {
                  sb.append("| ");
                  position = -1;
              }
              sb.append(data.toString());
              ++count;
          }

          for (int i = 1; i < nodeSize; ++i) {
             sb.append(", ");
              data = current.data[i];
              if (data == null) {
                  sb.append("-");
              } else {
                  if (position == count) {
                      sb.append("| ");
                      position = -1;
                  }
                  sb.append(data.toString());
                  ++count;

                  // iterator at end
                  if (position == size && count == size) {
                      sb.append(" |");
                      position = -1;
                  }
             }
          }
          sb.append(')');
          current = current.next;
          if (current != tail)
              sb.append(", ");
      }
      sb.append("]");
      return sb.toString();
  }


  /**
   * Node type for this list.  Each node holds a maximum
   * of nodeSize elements in an array.  Empty slots
   * are null.
   */
  private class Node
  {
    /**
     * Array of actual data elements.
     */
    // Unchecked warning unavoidable.
    public E[] data = (E[]) new Comparable[nodeSize];
    
    /**
     * Link to next node.
     */
    public Node next;
    
    /**
     * Link to previous node;
     */
    public Node previous;
    
    /**
     * Index of the next available offset in this node, also 
     * equal to the number of elements in this node.
     */
    public int count;

    /**
     * Adds an item to this node at the first available offset.
     * Precondition: count < nodeSize
     * @param item element to be added
     */
    void addItem(E item)
    {
      if (count >= nodeSize)
      {
        return;
      }
      data[count++] = item;
      //useful for debugging
      //      System.out.println("Added " + item.toString() + " at index " + count + " to node "  + Arrays.toString(data));
    }
  
    /**
     * Adds an item to this node at the indicated offset, shifting
     * elements to the right as necessary.
     * 
     * Precondition: count < nodeSize
     * @param offset array index at which to put the new element
     * @param item element to be added
     */
    void addItem(int offset, E item)
    {
      if (count >= nodeSize)
      {
    	  return;
      }
      for (int i = count - 1; i >= offset; --i)
      {
        data[i + 1] = data[i];
      }
      ++count;
      data[offset] = item;
      //useful for debugging 
//      System.out.println("Added " + item.toString() + " at index " + offset + " to node: "  + Arrays.toString(data));
    }

    /**
     * Deletes an element from this node at the indicated offset, 
     * shifting elements left as necessary.
     * Precondition: 0 <= offset < count
     * @param offset
     */
    void removeItem(int offset)
    {
      E item = data[offset];
      for (int i = offset + 1; i < nodeSize; ++i)
      {
        data[i - 1] = data[i];
      }
      data[count - 1] = null;
      --count;
    }   
  }
  
  /**
   * helper class
   * @author paige
   *
   */
  private class NodeInfo{
	  public Node node; 
	  public int offset; 
	  
	  /**
	   * constructor for NodeInfo
	   * @param node
	   * @param offset
	   */
	  public NodeInfo(Node node, int offset) {
		  this.node = node; 
		  this.offset = offset; 
	  }
	  

	  
  }
  
  /**
   * helper method to return the node and offset for the given logical index
   * @param pos the position of the node that info is wanted from
   * @return the NodeInfo of the given position 
   */
  private NodeInfo find(int pos) {
	  Node n = head.next;
	  int i = 0; 
	  //iterate through the nodes
	  while (n != tail) {
		  //if the index and the count of the node is less than the pos
		  if (i + n.count <= pos) {
			  //add the num of elements in the node to i
			  i += n.count; 
			  //move on to the next node
			  n = n.next; 
			  continue; 
		  }
		  
		  //when you break out of the loop (find the correct node)
		  NodeInfo info = new NodeInfo(n, pos - i); 
		  return info; 
	  }
	  //if it isn't found before the end of the list, return null
	  return null; 
  }
 
  private class StoutListIterator implements ListIterator<E>
  {
	// constants you possibly use ...   
	  
	// instance variables ... 
	/**
	 * the current position of the iterator
	 */
	int position; 
	
	/**
	 * array that holds all of the data from all of the nodes
	 */
	E[] data; 
	
	/**
	 * what the last action was (if it was next or previous)
	 */
	String nextOrPrevious; 
	  
    /**
     * Default constructor 
     */
    public StoutListIterator()
    { 
    	//set position to 0
    	position = -1; 
    	data = makeArray(); 
    }

    /**
     * Constructor finds node at a given position.
     * 
     * 
     * @param pos
     */
    public StoutListIterator(int pos)
    {
    	//set the position to pos
    	position = pos - 1; 
    	data = makeArray(); 
    }

    /**
     * Returns true if this list iterator has more
     * elements when traversing the list in the forward 
     * direction. 
     * 
     * (In other words, returns true if next()
     * would return an element rather than throwing an 
     * exception.)
     */
    @Override
    public boolean hasNext()
    {
    	//if the current position is less than the size
    	if(position <= size) {
    		return true; 
    	} else {
    		return false; 
    	}	
    }

    @Override
    public E next()
    {
    	nextOrPrevious = "next"; 
    	data = makeArray(); 
    	//if there is not a next
    	if (!hasNext())
    		//throw new NoSuchElementException
    		throw new NoSuchElementException();
    	
    	//otherwise there is a next
    	//move position forward one
    	position ++; 
    		 
    	//return the next item
    	E ret = data[position];
    	return ret;
    }

	@Override
	public boolean hasPrevious() {
		if (position > -1)
			return true; 
		else
			return false;
	}

	@Override
	public E previous() {
    	data = makeArray(); 
    	//if there is not a previous
    	if (!hasPrevious())
    		//throw new NoSuchElementException
    		throw new NoSuchElementException();
    	
    	//otherwise there is a previous
    	//move position back one
    	position --; 
    	nextOrPrevious = "previous"; 	 
    	//return the previous item
    	return data[position + 1];
	}

	@Override
	public int nextIndex() {
		//if the list iterator is at the end of the list
		if (position == size)
			//return the size
			return size; 
		//otherwise
		else
			//return the next index
			return position + 1; 
	}

	@Override
	public int previousIndex() {
		return position; 
	}

	@Override
	public void remove() {
		//if the last move was next
		if (nextOrPrevious.equals("next")) {
			//remove the element behind the position 
			StoutList.this.remove(position); 
			//re-make the array 
			data = makeArray();
			//move the position back
			position --; 
			//set the last move to previous 
			nextOrPrevious = "";
			//don't let the position go below 0 
			if (position < -1) {
				position = -1; 
			}
		//otherwise if the last move was previous 
		} else if (nextOrPrevious.equals("previous")) {
			//remove the item at the current position 
			StoutList.this.remove(position+1);
			//re-make the array
			data = makeArray();
			nextOrPrevious = ""; 
		} else {
			throw new IllegalStateException(); 
		}
	}

	@Override
	public void set(E e) { 
		//if the last move was next
		if (nextOrPrevious.equals("next")) {
			//set the one behind it
			//get the node that it is in 
			NodeInfo i = find(position); 
			//the node that the info is in
			Node n = i.node;
			//set the data of the node at the offset to the new element
			n.data[i.offset] = e; 
			//re-make the array list because it will be different now
			data = makeArray(); 
			//get rid of the next or previous because you can't call set twice
			nextOrPrevious = null; 
		} else if (nextOrPrevious.equals("previous")) {
			//set the one in front of it
			//get the node that it is in
			NodeInfo i = find(position + 1); 
			//the node that the info is in
			Node n = i.node; 
			//set the data of the node at the offset to the new element
			n.data[i.offset] = e; 
			//re-make the array list because it'll be different now
			data = makeArray(); 
		//otherwise next or previous have not been called
		} else {
			//throw illegal state exception
			throw new IllegalStateException(); 
		}
		
	}

	@Override
	public void add(E e) {
		StoutList.this.add(position + 1, e);
		position++;
		data = makeArray();
		nextOrPrevious = "next"; 	
	} 

    // Other methods you may want to add or override that could possibly facilitate 
    // other operations, for instance, addition, access to the previous element, etc.
    // 
    // ...
    // 
    
  }
  
  /**
   * helper method to make an array of the elements in all of the nodes
   * @return the array of the elements in all of the nodes
   */
  private E[] makeArray() {
	  E[] data = (E[]) new Comparable[size]; 
  	
  	//iterate through all of the nodes and add their data to data
  	
  	//temp node for iterating through the nodes (starting at the first)
  	Node temp = head.next; 
  	//while the node is not the tail
  	int i = 0; 
  	while(temp != tail) {
  		//take all of the data from the node and add it to all of the data
  		
  		for (E d : temp.data) {
  			if (d == null)
  				continue; 
  			data[i] = d;
  			i++;
  		}
  		//go to the next node
  		temp = temp.next;
  		
  	}	
  	return data; 
  }
  /**
   * Sort an array arr[] using the insertion sort algorithm in the NON-DECREASING order. 
   * @param arr   array storing elements from the list 
   * @param comp  comparator used in sorting 
   */
  private void insertionSort(E[] arr, Comparator<? super E> comp)
  {
	  for (int i = 1; i < size; ++i) {
		  E key = arr[i];
		  int j = i - 1; 
		  
		  while (j >= 0 && comp.compare(arr[j], key) > 0) {
			  arr[j+1] = arr[j];
			  j = j- 1;
		  }
		  arr[j+1] = key; 
	  }
  }
  
  /**
   * Sort arr[] using the bubble sort algorithm in the NON-INCREASING order. For a 
   * description of bubble sort please refer to Section 6.1 in the project description. 
   * You must use the compareTo() method from an implementation of the Comparable 
   * interface by the class E or ? super E. 
   * @param arr  array holding elements from the list
   */
  private void bubbleSort(E[] arr)
  {
	  boolean swapped = true; 
	  
	  while(swapped) {
		  swapped = false; 
		  for (int i = 0; i < arr.length - 1; i++) {
			  if (arr[i].compareTo(arr[i + 1]) < 0) {
				  swapped = true;
				  E temp = arr[i];
				  arr[i] = arr[i+1];
				  arr[i+1] = temp; 
			  }
		  }
	  }
  }


}