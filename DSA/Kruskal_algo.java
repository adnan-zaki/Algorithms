// Kruskal_algo.java
// Kruskal's Algorithm Project with Menu + Non-Graphical + Visual Animation

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.text.StyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;


class Edge {
    int src, dest, weight;
    boolean chosen = false;
    boolean rejected = false;

    Edge(int s, int d, int w) { src = s; dest = d; weight = w; }
}

public class Kruskal_algo extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JTextArea consoleOutput;

    // For non-graphical
    private int[][] cost;
    private int[] parent;
    private int n;

    // For graphical
    private java.util.List<Edge> edges;
    private int step = 0;
    private GraphPanel originalGraph;
    private MSTPanel mstGraph;
    private JTextArea edgeListArea;
    private int[] parentDSU;

    public Kruskal_algo() {
        setTitle("Kruskal's Algorithm Project");
        setSize(1100, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);


        // Intro screen
        JPanel introPanel = new JPanel() {
            protected void paintComponent(Graphics g ) {
                super.paintComponent(g);

                setBackground(new Color(235, 235, 235)); 

                g.setColor(Color.RED);
                
                g.setFont(new Font("Monospaced", Font.BOLD, 50));
                g.drawString("WELCOME TO MY PROJECT", 250, 100);


                g.setColor(Color.BLUE);
                g.setFont(new Font("Monospaced", Font.BOLD, 30));

                g.drawString("TOPIC : KRUSKAL ALGORITHM ", 300, 150);

                g.setColor(new Color(14, 39, 60)); // hex to rgb 0E273C
                g.setFont(new Font("Monospaced", Font.BOLD, 25));
                g.drawString("SUBMITTED TO : MS PRIYANKA GHAI", 320, 300);
                g.drawString("SUBMITTED BY : JADWINDER SINGH", 320, 340);

                // small hint
                g.setColor(Color.DARK_GRAY);
                g.setFont(new Font("Monospaced", Font.ITALIC, 18));
                g.drawString("Click anywhere to continue...", 380, 450);
                
            }
        };
        
        introPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(mainPanel, "menu");
            }
        });


        // Menu screen with buttons
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(new Color(235, 235, 235));
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS)); // vertical layout

        // Title label
        JLabel title = new JLabel("MENU", SwingConstants.CENTER);
        title.setFont(new Font("Monospaced", Font.BOLD, 36));
        title.setForeground(Color.RED);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(Box.createVerticalStrut(50)); // spacing
        menuPanel.add(title);
        menuPanel.add(Box.createVerticalStrut(40));

        // Buttons
        JButton introBtn = new JButton("1. INTRODUCTION OF KRUSKAL ALGORITHM");
        JButton nonGraphBtn = new JButton("2. NON-GRAPHICAL MODE");
        JButton graphBtn    = new JButton("3. GRAPHICAL MODE");
        JButton exitBtn     = new JButton("4. EXIT");

        // Style buttons
        Font btnFont = new Font("Monospaced", Font.PLAIN, 22);
        introBtn.setFont(btnFont);
        nonGraphBtn.setFont(btnFont);
        graphBtn.setFont(btnFont);
        exitBtn.setFont(btnFont);
        introBtn.setContentAreaFilled(true); // keeps the background color
        introBtn.setFocusPainted(false);   // remove focus border
        nonGraphBtn.setFocusPainted(false);   // remove focus border



        introBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        nonGraphBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        graphBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        menuPanel.add(introBtn);
        menuPanel.add(Box.createVerticalStrut(20));
        menuPanel.add(nonGraphBtn);
        menuPanel.add(Box.createVerticalStrut(20));
        menuPanel.add(graphBtn);
        menuPanel.add(Box.createVerticalStrut(20));
        menuPanel.add(exitBtn);

        // Button actions
        introBtn.addActionListener(e -> showIntroduction());
        nonGraphBtn.addActionListener(e -> runNonGraphical());
        graphBtn.addActionListener(e -> runGraphical());
        exitBtn.addActionListener(e -> System.exit(0));


        // Console Panel
        JPanel consolePanel = new JPanel(new BorderLayout());
        consoleOutput = new JTextArea();
        consoleOutput.setEditable(false);
        consolePanel.add(new JScrollPane(consoleOutput), BorderLayout.CENTER);

        // Add panels
        mainPanel.add(introPanel, "intro");
        mainPanel.add(menuPanel, "menu");
        mainPanel.add(consolePanel, "console");

        add(mainPanel);
        cardLayout.show(mainPanel, "intro");
    }


    private void showIntroduction() {
    JTextPane textPane = new JTextPane();
    textPane.setEditable(false);
    textPane.setFont(new Font("Monospaced", Font.PLAIN, 14));

    StyledDocument doc = textPane.getStyledDocument();

    // Bold style
    Style boldStyle = textPane.addStyle("Bold", null);
    StyleConstants.setBold(boldStyle, true);                 // bold
    StyleConstants.setForeground(boldStyle, new Color(20, 20, 20)); // very dark
    StyleConstants.setFontFamily(boldStyle, "Monospaced");   // heavier monospaced font


    // Normal style
    Style normalStyle = textPane.addStyle("Normal", null);

    try {
        doc.insertString(doc.getLength(), "Introduction:\n", boldStyle);
        doc.insertString(doc.getLength(), 
                "Kruskal's Algorithm is a Minimum Spanning Tree (MST) algorithm\n" +
                "that finds a subset of edges that forms a tree including every vertex,\n" +
                "where the total weight of all edges in the tree is minimized.\n\n", 
                normalStyle);

        doc.insertString(doc.getLength(), "Steps:\n", boldStyle);
        doc.insertString(doc.getLength(),
                "1. Sort all edges in increasing order of weight.\n" +
                "2. Pick the smallest edge. Check if it forms a cycle with the spanning tree formed so far.\n" +
                "3. If it doesn't form a cycle, include it in the MST.\n" +
                "4. Repeat step 2 until there are (V-1) edges in the MST.\n\n",
                normalStyle);

        doc.insertString(doc.getLength(), "Algorithm Kruskal(E, cost, n, t):\n", boldStyle);
        doc.insertString(doc.getLength(),
                "// E is the set of edges in G. G has n vertices, cost[u,v] is the \n" +
                "// cost of edge (u,v), t is the set of edges in the minimum-cost spanning tree.\n\n" +
                "{\n" +
                "    // Construct a heap out of the edge costs using Heapify;\n" +
                "    for i := 1 to n do parent[i] := -1; // Each vertex is in a different set\n" +
                "    i := 0; mincost := 0.0;\n" +
                "    while ((i < n-1) and (heap not empty)) do\n" +
                "    {\n" +
                "        // Delete a minimum cost edge (u,v) from the heap and reheapify using Adjust;\n" +
                "        j := Find(u); k := Find(v);\n" +
                "        if (j != k) then\n" +
                "        {\n" +
                "            i := i + 1;\n" +
                "            t[i,1] := u; t[i,2] := v;\n" +
                "            mincost := mincost + cost[u,v];\n" +
                "            Union(j,k);\n" +
                "        }\n" +
                "    }\n" +
                "    if (i = n-1) then write(No spanning tree);\n" +
                "    else return mincost;\n" +
                "}\n",
                normalStyle);

    } catch (Exception e) {
        e.printStackTrace();
    }

    textPane.setCaretPosition(0); //goes to up by default


    JScrollPane scrollPane = new JScrollPane(textPane);
    scrollPane.setPreferredSize(new Dimension(800, 500));

    JOptionPane.showMessageDialog(this, scrollPane, 
            "Introduction to Kruskal's Algorithm", JOptionPane.INFORMATION_MESSAGE);
}




     // ----------------- Non-graphical Kruskal -----------------
    private void runNonGraphical() {
    NonGraphPanel nonGraph = new NonGraphPanel(cardLayout, mainPanel);
    mainPanel.add(nonGraph, "nongraph");
    cardLayout.show(mainPanel, "nongraph");
}

    // ---------------- Non-Graphical Kruskal Panel ----------------
class NonGraphPanel extends JPanel {
    private JTextField vertexField;
    private JPanel matrixPanel;
    private JTextArea outputArea;
    private JTextField[][] matrixFields;
    private int n;
    private JPanel parentPanel; // reference to parent
    private CardLayout cardLayout; // reference to CardLayout

    NonGraphPanel(CardLayout cl, JPanel parent) {
        this.cardLayout = cl;
        this.parentPanel = parent;
        setLayout(new BorderLayout());


        // Main vertical panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel, BorderLayout.CENTER);

        // -------- Top buttons --------
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backBtn = new JButton("Back to Menu");
        JButton exitBtn = new JButton("Exit");
        buttonsPanel.add(backBtn);
        buttonsPanel.add(exitBtn);
        mainPanel.add(buttonsPanel);

        backBtn.addActionListener(e -> cardLayout.show(parentPanel, "menu"));
        exitBtn.addActionListener(e -> System.exit(0));

        // -------- Vertex input --------
        JPanel vertexPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        vertexPanel.add(new JLabel("Enter number of vertices:"));
        vertexField = new JTextField(5);
        JButton generateBtn = new JButton("Generate Matrix");
        vertexPanel.add(vertexField);
        vertexPanel.add(generateBtn);
        mainPanel.add(vertexPanel);

        // -------- Matrix panel --------
        matrixPanel = new JPanel();
        JScrollPane matrixScroll = new JScrollPane(matrixPanel);
        matrixScroll.setPreferredSize(new Dimension(600, 300));
        mainPanel.add(matrixScroll);

        // -------- Output + Run --------
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        JButton runBtn = new JButton("Run Kruskal");
        bottomPanel.add(runBtn, BorderLayout.NORTH);

        outputArea = new JTextArea(10, 50);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        outputArea.setEditable(false);
        bottomPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);
        mainPanel.add(bottomPanel);

        // -------- Generate matrix action --------
        generateBtn.addActionListener(e -> {
            try {
                n = Integer.parseInt(vertexField.getText());
                matrixPanel.removeAll();
                matrixPanel.setLayout(new GridLayout(n+1, n+1, 5, 5));
                matrixFields = new JTextField[n][n];

                matrixPanel.add(new JLabel(""));
                for (int j = 1; j <= n; j++) {
                    JLabel lbl = new JLabel("" + j, SwingConstants.CENTER);
                    lbl.setFont(new Font("Serif", Font.BOLD, 14));
                    matrixPanel.add(lbl);
                }

                for (int i = 0; i < n; i++) {
                    JLabel rowLbl = new JLabel("" + (i+1), SwingConstants.CENTER);
                    rowLbl.setFont(new Font("Serif", Font.BOLD, 14));
                    matrixPanel.add(rowLbl);

                    for (int j = 0; j < n; j++) {
                        JTextField tf = new JTextField("");
                        tf.setHorizontalAlignment(SwingConstants.CENTER);
                        matrixFields[i][j] = tf;
                        matrixPanel.add(tf);
                    }
                }

                matrixPanel.revalidate();
                matrixPanel.repaint();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Enter valid number of vertices!");
            }
        });

        // -------- Run Kruskal action --------
        runBtn.addActionListener(e -> runKruskal());
    }

    
    private void runKruskal() {
        if (matrixFields == null) return;

        int[][] cost = new int[n+1][n+1];
        int[] parent = new int[n+1];
        outputArea.setText("Running Kruskal's Algorithm...\n");

        // read matrix with validation
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            String text = matrixFields[i][j].getText().trim();

            // Check if field is empty
            if (text.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Please fill all values in the matrix before running Kruskal!", 
                    "Input Error", JOptionPane.ERROR_MESSAGE);
                return; // stop execution
            }

            

            try {
                int val = Integer.parseInt(text);
                cost[i+1][j+1] = (val == 0 ? 999 : val); // replace 0 with large value
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Invalid input at cell (" + (i+1) + "," + (j+1) + "). Please enter numbers only.", 
                    "Input Error", JOptionPane.ERROR_MESSAGE);
                return; // stop execution
            }
        }
    }



        //kruskal algorithm execution
        int ne = 1, mincost = 0;
        while (ne < n) {
            int min = 999, a = -1, b = -1, u = -1, v = -1;
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    if (cost[i][j] < min) {
                        min = cost[i][j];
                        a = u = i; b = v = j;
                    }
                }
            }
            while (parent[u] != 0) u = parent[u];
            while (parent[v] != 0) v = parent[v];
            if (u != v) {
                outputArea.append("Edge " + ne + " : ("+a+","+b+") cost="+min+"\n");
                parent[v] = u;
                mincost += min;
                ne++;

                // Highlight chosen edge
                Font boldFont = new Font("Monospaced", Font.BOLD, 14);

                matrixFields[a-1][b-1].setBackground(Color.GREEN);
                matrixFields[a-1][b-1].setFont(boldFont);
                matrixFields[a-1][b-1].setForeground(Color.BLACK);

                matrixFields[b-1][a-1].setBackground(Color.GREEN);
                matrixFields[b-1][a-1].setFont(boldFont);
                matrixFields[b-1][a-1].setForeground(Color.BLACK);
                cost[a][b] = cost[b][a] = 999;
        }
        outputArea.append("Minimum cost = " + mincost + "\n");
    }
}

}


    private int find(int i) {
        while (parent[i] != 0) i = parent[i];
        return i;
    }

    private boolean uni(int i, int j) {
        if (i != j) {
            parent[j] = i;
            return true;
        }
        return false;
    }

    // ----------------- Graphical Kruskal -----------------
    private void runGraphical() {

        // Stop old timer if running
        if (mstTimer != null && mstTimer.isRunning()) {
            mstTimer.stop();
        }


        edges = new ArrayList<>();
        // same sample edges from second.java
        edges.add(new Edge(1,6,10));
        edges.add(new Edge(3,4,12));
        edges.add(new Edge(2,7,14));
        edges.add(new Edge(4,7,18));
        edges.add(new Edge(5,7,24));
        edges.add(new Edge(4,5,22));
        edges.add(new Edge(2,3,16));
        edges.add(new Edge(1,2,28));
        edges.add(new Edge(5,6,25));

        Collections.sort(edges, Comparator.comparingInt(e -> e.weight));
        parentDSU = new int[8]; // 1-based reset DSU

        //reset state
        mstWeight = 0;   
        chosenWeights = new ArrayList<>();
        step = 0;
        
        JPanel graphsPanel = new JPanel(new GridLayout(1, 2));
        originalGraph = new GraphPanel(edges, false, "Original Graph");
        mstGraph = new MSTPanel(edges, "MST Construction");
        graphsPanel.add(originalGraph);
        graphsPanel.add(mstGraph);

        edgeListArea = new JTextArea(6, 40);
        edgeListArea.setEditable(false);
        edgeListArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(edgeListArea);

        // Buttons panel
        JPanel buttonsPanel = new JPanel();
        JButton backBtn = new JButton("Back to Menu");
        JButton exitBtn = new JButton("Exit");

        // Style buttons
        JButton[] btns = {backBtn, exitBtn};
        for (JButton b : btns) {
            b.setFont(new Font("Monospaced", Font.PLAIN, 12));
            b.setFocusPainted(false);
            b.setContentAreaFilled(true);
        }


        // Button actions
        backBtn.addActionListener(e -> {
            if (mstTimer != null) mstTimer.stop(); // stop timer when going back
            cardLayout.show(mainPanel, "menu");
        });
        exitBtn.addActionListener(e -> System.exit(0));

        buttonsPanel.add(backBtn);
        buttonsPanel.add(exitBtn);


        JPanel container = new JPanel(new BorderLayout());
        container.add(graphsPanel, BorderLayout.CENTER);
        container.add(scrollPane, BorderLayout.SOUTH);
        container.add(buttonsPanel, BorderLayout.NORTH); // top of edge list or bottom as needed

        // Remove old "graph" panel if it exists
        if (Arrays.asList(mainPanel.getComponents()).contains(container)) {
             mainPanel.remove(container);
        }
    

        mainPanel.add(container, "graph");
        cardLayout.show(mainPanel, "graph");

        // Timer to animate
        step = 0;
        mstTimer = new javax.swing.Timer(2500, e -> nextStep());
        mstTimer.start();
    }

        
    // Fields
    private javax.swing.Timer mstTimer;
    private int mstWeight;                          // total MST cost
    private java.util.List<Integer> chosenWeights;  // store weights of chosen edges
    
    // ----------------- Next Step (with MST sum) -----------------
    private void nextStep() {
    if (step >= edges.size()) {
        if (mstTimer != null) mstTimer.stop(); // stop timer

        // All edges processed -> final MST message
        if (!chosenWeights.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < chosenWeights.size(); i++) {
                sb.append(chosenWeights.get(i));
                if (i < chosenWeights.size() - 1) sb.append(" + ");
            }
            edgeListArea.append("\nGraph Completed!\n");
            edgeListArea.append("Minimum Weight (MST Cost) = " + sb + " = " + mstWeight + "\n");

            // Optional: also show in a popup
            JOptionPane.showMessageDialog(this,
                "Graph Completed!\nMinimum Weight (MST Cost) = " + sb + " = " + mstWeight,
                "Kruskal Result",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
        edgeListArea.setCaretPosition(edgeListArea.getDocument().getLength());
        return;
    }

    Edge edge = edges.get(step);
    int u = findDSU(edge.src), v = findDSU(edge.dest);

    if (u != v) {
        unionDSU(u, v);
        edge.chosen = true;
        mstWeight += edge.weight;              // add weight to total
        chosenWeights.add(edge.weight);        // record weight
        edgeListArea.append("Chosen   : (" + edge.src + "," + edge.dest + ") w=" + edge.weight + "\n");
    } else {
        edge.rejected = true;
        edgeListArea.append("Rejected : (" + edge.src + "," + edge.dest + ") w=" + edge.weight + "\n");
    }

    // Auto-scroll
    edgeListArea.setCaretPosition(edgeListArea.getDocument().getLength());

    // Repaint graphs
    originalGraph.repaint();
    mstGraph.repaint();

    step++;
}




    private int findDSU(int i) {
        if (parentDSU[i] == 0) return i;
        return parentDSU[i] = findDSU(parentDSU[i]);
    }

    private void unionDSU(int i, int j) { parentDSU[findDSU(i)] = findDSU(j); }

   
    class GraphPanel extends JPanel {
        java.util.List<Edge> edges;
        boolean onlyMST;
        String title;

        int[][] coords = {
            {0,0}, {150,80}, {350,80}, {500,180},
            {350,300}, {150,300}, {50,180}, {250,180}
        };

        GraphPanel(java.util.List<Edge> e, boolean mst, String title) {
            this.edges = e;
            this.onlyMST = mst;
            this.title = title;
            setPreferredSize(new Dimension(550, 550));
            setBackground(new Color(235, 235, 235)); ;
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


            g2.setColor(new Color( 14, 14, 82	));    
           


            g2.setFont(new Font("Arial", Font.BOLD, 18));
            g2.drawString(title, getWidth()/2 - 60, 25);
            g2.drawString(title, getWidth()/2 - 60, 25);

            g2.setFont(new Font("Arial", Font.PLAIN, 12));
            for (Edge ed : edges) {
                if (onlyMST && !ed.chosen) continue;
                int x1 = coords[ed.src][0], y1 = coords[ed.src][1];
                int x2 = coords[ed.dest][0], y2 = coords[ed.dest][1];

                if (ed.chosen){
                    g2.setColor(Color.GREEN);
                
                }

                else if (ed.rejected){
                    g2.setColor(Color.RED);
                }

                else{
                    g2.setColor(Color.LIGHT_GRAY);
                }

                g2.setStroke(new BasicStroke(ed.chosen ? 3 : 1));
                g2.drawLine(x1, y1, x2, y2);

                g2.setColor(Color.BLUE);
                g2.drawString(""+ed.weight, (x1+x2)/2, (y1+y2)/2);
            }

            for (int i=1; i<=7; i++) {
                int x = coords[i][0], y = coords[i][1];


                g.setColor(new Color(244, 50, 11));          // <-- correct usage


                g2.fillOval(x-20, y-20, 40, 40);
                g2.setColor(Color.BLACK);
                g2.setFont(new Font("Arial", Font.BOLD, 14));
                g2.drawString(""+i, x-5, y+5);
            }
        }
    }

    class MSTPanel extends GraphPanel {
        MSTPanel(java.util.List<Edge> e, String title) { super(e, true, title); }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Kruskal_algo().setVisible(true));
    }

}