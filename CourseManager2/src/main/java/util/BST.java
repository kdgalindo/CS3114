package util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BST<K extends Comparable<? super K>, V extends Comparable<? super V>>
	implements Iterable<V> {
	private BSTNode root;
	private int size;
	
	private class BSTNode {
		private K key;
		private V value;
		private BSTNode left;
		private BSTNode right;
		
		public BSTNode(K key, V val) {
			this(key, val, null, null);
		}
		
		public BSTNode(K key, V val, BSTNode lt, BSTNode rt) {
			this.key = key;
			this.value = val;
			this.left = lt;
			this.right = rt;
		}
	}
	
	public BST() {
		root = null;
		size = 0;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public int size() {
		return size;
	}
	
	public V find(K key) {
		return find(key, root);
	}
	
	private V find(K key, BSTNode sRoot) {
		if (sRoot == null) {
			return null;
		}
		
		int cmp = key.compareTo(sRoot.key);
		if (cmp < 0) {
			return find(key, sRoot.left);
		}
		else if (cmp > 0) {
			return find(key, sRoot.right);
		}
		else {
			return sRoot.value;
		}
	}
	
	public void insert(K key, V value) {
		root = insert(key, value, root);
		++size;
	}
	
	private BSTNode insert(K key, V value, BSTNode sRoot) {
		if (sRoot == null) {
			return new BSTNode(key, value);
		}
		
		int cmp = key.compareTo(sRoot.key);
		if (cmp < 0) {
			sRoot.left = insert(key, value, sRoot.left);
		}
		else {
			sRoot.right = insert(key, value, sRoot.right);
		}
		return sRoot;
	}
	
	public V remove(K key) {
		V oValue = find(key);
		if (oValue == null) {
			return null;
		}
		
		root = remove(key, root);
		--size;
		return oValue;
	}
	
	private BSTNode remove(K key, BSTNode sRoot) {
		if (sRoot == null) {
			return null;
		}
		
		int cmp = key.compareTo(sRoot.key);
		if (cmp < 0) {
			sRoot.left = remove(key, sRoot.left);
		}
		else if (cmp > 0) {
			sRoot.right = remove(key, sRoot.right);
		}
		else {
			if (sRoot.right == null) {
				return sRoot.left;
			}
			else if (sRoot.left == null) {
				return sRoot.right;
			}
			else {
				BSTNode tRoot = sRoot;
				sRoot = min(tRoot.right);
				sRoot.right = deleteMin(tRoot.right);
				sRoot.left = tRoot.left;
			}
		}
		return sRoot;
	}
	
    private BSTNode min(BSTNode sRoot) {
        if (sRoot.left == null) {
            return sRoot;
        }
        else {
        	return min(sRoot.left);
        }
    }
    
    private BSTNode deleteMin(BSTNode sRoot) {
        if (sRoot.left == null) {
            return sRoot.right;
        }
        sRoot.left = deleteMin(sRoot.left);
        return sRoot;
    }
	
	public V remove(K key, V value) {
		V oValue = find(key, value, root);
		if (oValue == null) {
			return null;
		}
		
		root = remove(key, value, root);
		--size;
		return oValue;
	}
	
	private V find(K key, V value, BSTNode sRoot) {
		if (sRoot == null) {
			return null;
		}
		
		int cmp = key.compareTo(sRoot.key);
		if (cmp < 0) {
			return find(key, value, sRoot.left);
		}
		else if (cmp > 0) {
			return find(key, value, sRoot.right);
		}
		else {
			cmp = value.compareTo(sRoot.value);
			if (cmp != 0) {
				return find(key, value, sRoot.right);
			}
			else {
				return sRoot.value;
			}
		}
	}
	
	private BSTNode remove(K key, V value, BSTNode sRoot) {
		if (sRoot == null) {
			return null;
		}
		
		int cmp = key.compareTo(sRoot.key);
		if (cmp < 0) {
			sRoot.left = remove(key, value, sRoot.left);
		}
		else if (cmp > 0) {
			sRoot.right = remove(key, value, sRoot.right);
		}
		else {
			cmp = value.compareTo(sRoot.value);
			if (cmp != 0) {
				sRoot.right = remove(key, value, sRoot.right);
			}
			else {
				if (sRoot.right == null) {
					return sRoot.left;
				}
				else if (sRoot.left == null) {
					return sRoot.right;
				}
				else {
					BSTNode tRoot = sRoot;
					sRoot = min(tRoot.right);
					sRoot.right = deleteMin(tRoot.right);
					sRoot.left = tRoot.left;
				}
			}
		}
		return sRoot;
	}
	
	public void clear() {
		root = null;
		size = 0;
	}
	
	public Iterable<V> findAll(K key) {
		Queue<V> queue = new LinkedList<V>();
		findAll(key, root, queue);
		return queue;
	}
	
	private void findAll(K key, BSTNode sRoot, Queue<V> queue) {
		if (sRoot == null) {
			return;
		}
		
		int cmp = key.compareTo(sRoot.key);
		if (cmp < 0) {
			findAll(key, sRoot.left, queue);
		}
		else if (cmp > 0) {
			findAll(key, sRoot.right, queue);
		}
		else {
			queue.add(sRoot.value);
			findAll(key, sRoot.right, queue);
		}
	}
	
	public Iterable<V> findRange(K lo, K hi) {
		Queue<V> queue = new LinkedList<V>();
		findRange(lo, hi, root, queue);
		return queue;
	}
	
	private void findRange(K lo, K hi, BSTNode sRoot, Queue<V> queue) {
		if (sRoot == null) {
			return;
		}
		
		int cmplo = lo.compareTo(sRoot.key);
		int cmphi = hi.compareTo(sRoot.key);
		if (cmplo < 0) {
			findRange(lo, hi, sRoot.left, queue);
		}
		if ((cmplo <= 0) && (cmphi >= 0)) {
			queue.add(sRoot.value);
		}
		if (cmphi > 0) {
			findRange(lo, hi, sRoot.right, queue);
		}
	}
	
    @Override
    public Iterator<V> iterator() {
        return new BSTIterator();
    }

    private class BSTIterator implements Iterator<V> {
        private Stack<BSTNode> stack;

        public BSTIterator() {
        	stack = new Stack<BSTNode>();
        	goLeftFrom(root);
        }

        private void goLeftFrom(BSTNode sRoot) {
            while (sRoot != null) {
                stack.push(sRoot);
                sRoot = sRoot.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public V next() {
            BSTNode current = stack.peek();
            stack.pop();
            if (current.right != null) {
                goLeftFrom(current.right);
            }
            return current.value;
        }
    }
}
