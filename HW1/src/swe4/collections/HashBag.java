package swe4.collections;

import java.util.Iterator;

final class InvalidIteratorException extends RuntimeException {
    InvalidIteratorException(String message) {
        super(message);
    }
}

public class HashBag<T> implements Iterable<T> {

    private class List implements Iterable<T> {
        private class ListNode {
            public class Payload {
                public T data;
                public int count;

                Payload(T element) {
                    this(element, 1);
                }

                Payload(T element, int occurrence) {
                    data = element;
                    count = occurrence;
                }
            }

            private Payload item;
            private ListNode next;

            ListNode(T element) {
                this(element, 1);
            }

            ListNode(T element, int occurrence) {
                item = new Payload(element, occurrence);
            }

            public int add(int amount) {
                var temp = item.count;
                item.count += amount;
                return temp;
            }

            public int remove(int amount) {
                var temp = item.count;
                item.count -= amount;
                return temp;
            }

            public T getData() {
                return item.data;
            }

            public int getCount() {
                return item.count;
            }

            public ListNode getNext() {
                return next;
            }

            public void setNext(ListNode n) {
                next = n;
            }

            @Override
            public String toString() {
                return "[" + item.data + ", " + item.count + "]";
            }
        }

        private class ListIterator implements Iterator<ListNode> {
            private ListNode current;
            private int count;

            ListIterator(ListNode n) {
                current = n;
                count = 0;
            }

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public ListNode next() {
                if (!hasNext()) {
                    throw new InvalidIteratorException("Iterator at the end");
                }

                var temp = current;
                count++;
                if (count <= current.item.count) {
                    current = current.next;
                    count = 0;
                }
                return temp;
            }
        }

        ListNode head;

        List() {
            head = null;
        }

        @Override
        public Iterator<T> iterator() {
            return (Iterator<T>) new ListIterator(head);
        }

        public ListNode newNode(T element, int occurrences) {
            return new ListNode(element, occurrences);
        }

        public int add(T element, int occurrences) {
            if (head == null) {
                return append(element, occurrences);
            }

            int temp = 0;
            for (T node : this) {
                var n = (ListNode) node;
                if (n.item.data.equals(element)) {
                    temp = n.item.count;
                    n.item.count += occurrences;
                    break;
                }
            }

            return temp == 0 ? append(element, occurrences) : temp;
        }

        private int append(T element, int occurrences) {
            var temp = head;
            if (temp == null) {
                head = newNode(element, occurrences);
                return 0;
            }

            while (temp.next != null) {
                temp = temp.next;
            }

            temp.next = newNode(element, occurrences);
            return 0;
        }

        private int prepend(T element, int occurrences) {
            var temp = newNode(element, occurrences);
            temp.next = head;
            head = temp;
            return 0;
        }

        public int remove(T element, int occurrences) {
            int temp = add(element, -occurrences);
            if (temp - occurrences <= 0) {
                removeNode(element);
            }
            return temp;
        }

        public void removeNode(T element) {
            ListNode curr = head;
            ListNode prev = null;
            while (!curr.item.data.equals(element) && curr != null) {
                prev = curr;
                curr = curr.next;
            }

            if (prev == null) {
                head = head.next;
            } else {
                if (curr == null) {
                    prev.next = null;
                } else {
                    prev.next = curr.next;
                }
            }
        }

        public boolean contains(T element) {
            return count(element) > 0;
        }

        public int count(T element) {
            for (T node : this) {
                var n = (ListNode) node;
                if (n.item.data.equals(element)) {
                    return n.item.count;
                }
            }
            return 0;
        }

        public int totalEntries() {
            int total = 0;
            for (var node : this) {
                var n = (ListNode) node;
                total += n.item.count;
            }
            return total;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("List: {");

            for (T node : this) {
                sb.append(node.toString());
            }

            sb.append("} ");
            return sb.toString();
        }
    }

    private class HashBagIterator implements Iterator<T> {
        private final Object[] table;
        private int current = 0;
        private int count = 0;
        private int size = 0;
        private int max = 0;
        private Iterator<T> currListIterator;

        HashBagIterator(Object[] b, int s, int m) {
            table = b;
            max = m;
            size = s;
            current = 0;

            // Get first element in Bag
            getNextList(0);
        }

        private void getNextList(int offset) {
            for (int i = current + offset; i < max; i++) {
                if (table[i] != null) {
                    currListIterator = ((List) table[i]).iterator();
                    current = i;
                    break;
                }
            }
        }

        @Override
        public boolean hasNext() {
            if (count < size && size != 0) {
                return true;
            } else {
                if (currListIterator == null)
                    return false;
                else
                    return currListIterator.hasNext();
            }
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new InvalidIteratorException("Iterator already at the end!");
            } else {
                T v = currListIterator.next();

                if (!currListIterator.hasNext()) {
                    getNextList(1);
                }

                count++;
                return v;
            }
        }
    }

    private int capacity;
    private int size = 0;
    private float loadFactor;
    private Object[] table;

    HashBag() {
        this(11, 0.75f);
    }

    HashBag(int cap, float lF) {
        capacity = cap;
        loadFactor = lF;
        table = new Object[capacity];
    }

    public int add(T element) {
        return add(element, 1);
    }

    public int add(T element, int occurrences) {
        float newLoadFactor = (float) (size + 1) / capacity;
        if (newLoadFactor > loadFactor) {
            rehash();
        }

        int hash = element.hashCode() % capacity;
        if (table[hash] != null) {
            var list = (List) table[hash];
            return list.add(element, occurrences);
        } else {
            size++;
            List list = new List();
            table[hash] = list;
            return list.add(element, occurrences);
        }
    }

    public void clear() {
        for (int i = 0; i < capacity; i++) {
            if (table[i] != null) {
                size--;
            }

            table[i] = null;
            // thank you GC for the rest ^^
        }
    }

    public boolean contains(T element) {
        return count(element) > 0;
    }

    public int count(T element) {
        int hash = element.hashCode() % capacity;
        if (table[hash] != null) {
            var list = (List) table[hash];
            return list.count(element);
        } else {
            return 0;
        }
    }

    public float getLoadFactor() {
        return (float) size / capacity;
    }

    public Object[] elements() {
        Object[] items = new Object[size()];

        int count = 0;
        for (var item : this) {
            var node = (List.ListNode)item;
            items[count] = node.item.data;
            count++;
        }

        return items;
    }

    public boolean isEmpty() {
        return size <= 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new HashBagIterator(table, size, capacity);
    }

    public int remove(T element) {
        return remove(element, 1);
    }

    public int remove(T element, int occurrences) {
        int hash = element.hashCode() % capacity;
        if (table[hash] != null) {
            var list = (List) table[hash];
            var temp = list.remove(element, occurrences);
            if (temp - occurrences <= 0 && temp != 0)
                size--;
            return temp;
        } else {
            return 0;
        }
    }

    public void rehash() {
        var newBag = new HashBag<T>(capacity * 2, loadFactor);

        for (var elem : this) {
            var node = (List.ListNode)elem;
            newBag.add(node.item.data, node.item.count);
        }

        capacity = newBag.capacity;
        loadFactor = newBag.loadFactor;
        size = newBag.size;
        table = newBag.table;
    }

    public int size() {
        int total = 0;
        for (int i = 0; i < capacity; i++) {
            if (table[i] != null) {
                var list = (List) table[i];
                total += list.totalEntries();
            }
        }

        return total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("HashBag: {");
        for (int i = 0; i < capacity; i++) {
            if (table[i] != null) {
                var list = (List)table[i];
                sb.append(list.toString());
            }
        }

        sb.append("}");
        return sb.toString();
    }
}
