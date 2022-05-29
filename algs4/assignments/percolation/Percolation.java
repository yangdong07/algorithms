import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF model;
    private WeightedQuickUnionUF model1;
    private int _n = 0;
    private int size = 0;      // total size
    private int numOpened = 0;   // current opened num;
    private boolean[] opened;    // opened status

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        _n = n;
        size = n * n + 1;
        model = new WeightedQuickUnionUF(size);
        model1 = new WeightedQuickUnionUF(size + 1);
        opened = new boolean[size + 1];
        opened[0] = true;
        opened[size] = true;
        for (int i = 1; i < size; ++i) {
            opened[i] = false;
        }
    }

    private int validRowCol(int row, int col) {
        if (row < 1 || row > _n || col < 1 || col > _n)
            throw new IllegalArgumentException();
        return (row - 1) * _n + col;
    }

    private void checkUnion(int idx, int another) {
        if (opened[another]) {
            model.union(idx, another);
            model1.union(idx, another);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int idx = validRowCol(row, col);
        if (isOpen(idx)) return;
        if (idx > _n) checkUnion(idx, idx - _n);
        else checkUnion(idx, 0);
        if (idx < size - _n) checkUnion(idx, idx + _n);
        else model1.union(idx, size);
        if (col > 1) checkUnion(idx, idx - 1);
        if (col < _n) checkUnion(idx, idx + 1);
        opened[idx] = true;
        numOpened++;
    }

    private boolean isOpen(int idx) {
        return opened[idx];
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return isOpen(validRowCol(row, col));
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int idx = validRowCol(row, col);
        return isOpen(idx) && (model.find(idx) == model.find(0));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOpened;
    }

    // does the system percolate?
    public boolean percolates() {
        return (model1.find(size) == model1.find(0));
    }

    // test client (optional)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        Percolation model = new Percolation(n);
        int[] perm = StdRandom.permutation(n * n);
        int i = 0;
        while (!model.percolates()) {
            int idx = perm[i];
            int row = idx / n + 1;
            int col = idx % n + 1;
            model.open(row, col);
            ++i;
        }
        System.out.println((double)i / (n * n));
    }
}
