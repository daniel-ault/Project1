/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project1;

import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author Daniel
 */
public class BSTDictionary<K extends Comparable<K>, V> {
    
    private static class BSTNode<K, V> {
        private K key;
        private V value;
        private BSTNode<K, V> left, right;
        
        private BSTNode(K key, V value, BSTNode<K, V> left, BSTNode<K, V> right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }
        
        public String toString() {
            return key + " - " + value;
        }
        
    }// end class BSTNode<K, V>
    
    public static class DictEntry<K, V> {
        public K key;
        public V value;
        
        public DictEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
    
    private BSTNode<K, V> root;
    
    
    /**
     * Inserts the specified key-value pair into the dictionary. 
     * If the key already exists, this method should replace that key’s value 
     * with the new one.
     * @param key
     * @param value 
     */
    public void add(K key, V value) {
        //special case of adding to the root
        if (root == null)
            root = new BSTNode<K, V>(key, value, null, null);
        else add(key, value, root);
    }
    
    /*
     * helper method that recursively adds the key and value to a tree rooted
     * at where
     */
    private void add(K key, V value, BSTNode<K, V> where) {
        int compare = key.compareTo(where.key);
        
        if (compare == 0)           //key already exists
            where.value = value;    //so replace the old value with the new
        else if (compare<0 && where.left==null)     //give where a new left child
            where.left = new BSTNode<K, V>(key, value, null, null);
        else if (compare>0 && where.right==null)    //give where a new right child
            where.right = new BSTNode<K, V>(key, value, null, null);
        else if (compare<0)         //if where has a left child, recursively add to that child
            add(key, value, where.left);
        else if (compare>0)         //if where has a right child, recursively add to that child
            add(key, value, where.right);
    }// end add
    
    /**
     * 
     * @param key
     * @return  the value associated with the specified <code>key</code>,
     *          or <code>null</code> if the key does not exist.
     */
    public V getValue(K key) {
        return getValue(key, root);
    }
    
    
    /*
     *  Recursively searches the tree for the specified key, and returns the
     *  value associated with that key
     */
    private V getValue(K key, BSTNode<K, V> where) {
        if (where == null) {        //empty tree - nothing to search!
            return null;
        } 
        else {
            int compare = key.compareTo(where.key);
            
            if (compare == 0)       //key found!
                return where.value;
            else if (compare < 0)   //recursively search where's left subtree
                return getValue(key, where.left);
            else                    //recursively search where's right subtree
                return getValue(key, where.right);
        }// end if-else
    }//end getValue
    
    
    /**
     * 
     * @param key
     * @return  <code>true</code> if the specified key exists in the dictionary,
     *          or <code>false</code> if it does not.
     */
    public boolean contains(K key) {
        if (getValue(key) == null)
            return false;
        else
            return true;
    }
    
    
    
    //ADD iterator() METHOD THAT RETURNS AN ITERATOR OVER ELEMENTS IN DICTIONARY
    public Iterator<DictEntry<K, V>> iterator() {
        LinkedList<DictEntry<K, V>> list = new LinkedList<DictEntry<K, V>>();
        
        inOrderTraverse(list, root);
        
        return list.iterator();
    }
    
    private void/*LinkedList<BSTNode<K, V>>*/ inOrderTraverse(LinkedList<DictEntry<K, V>> list, BSTNode<K, V> where) {
        if (where != null) {
            inOrderTraverse(list, where.left);
            
            list.add(new DictEntry<K, V>(where.key, where.value));
            
            
            inOrderTraverse(list, where.right);
        }   
    }// end inOrderTraverse
    
    // Wrapper method for toString
    @Override
    public String toString() {
        return toString(root, "");
    }
    
    //Recursive implementation of toString. The indent parameter keeps track of
    //how many spaces to display before each line. Each recursive call of
    //toString lengthens this parameter by one.
    private String toString(BSTNode<K, V> where, String indent) {
        if (where == null)
            return indent + "null";
        else {
            String s = indent + where.key + " - " + where.value + "\n";
            s += toString(where.left, indent + " ") + "\n";
            s += toString(where.right, indent + " ");
            return s;
        }
    }
}
