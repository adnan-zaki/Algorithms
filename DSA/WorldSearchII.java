import java.util.*;

public class WordSearchII {
    static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        String word;
    }

    static class Trie {
        TrieNode root = new TrieNode();
        void insert(String word) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                if (node.children[c - 'a'] == null) node.children[c - 'a'] = new TrieNode();
                node = node.children[c - 'a'];
            }
            node.word = word;
        }
    }

    public List<String> findWords(char[][] board, String[] words) {
        List<String> res = new ArrayList<>();
        Trie trie = new Trie();
        for (String w : words) trie.insert(w);
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[0].length; j++)
                dfs(board, i, j, trie.root, res);
        return res;
    }

    void dfs(char[][] board, int i, int j, TrieNode node, List<String> res) {
        char c = board[i][j];
        if (c == '#' || node.children[c - 'a'] == null) return;
        node = node.children[c - 'a'];
        if (node.word != null) {
            res.add(node.word);
            node.word = null;
        }
        board[i][j] = '#';
        if (i > 0) dfs(board, i - 1, j, node, res);
        if (j > 0) dfs(board, i, j - 1, node, res);
        if (i < board.length - 1) dfs(board, i + 1, j, node, res);
        if (j < board[0].length - 1) dfs(board, i, j + 1, node, res);
        board[i][j] = c;
    }

    public static void main(String[] args) {
        WordSearchII ws = new WordSearchII();
        char[][] board = {
            {'o','a','a','n'},
            {'e','t','a','e'},
            {'i','h','k','r'},
            {'i','f','l','v'}
        };
        String[] words = {"oath","pea","eat","rain"};
        System.out.println(ws.findWords(board, words));
    }
}
