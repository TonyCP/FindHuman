package findHuman.client;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class clientView extends JFrame implements ActionListener {

    private static final Color CARD_TABLE_GREEN = new Color(37, 93, 54);
    private static final Color TEXT_COLOR = new Color(230, 230, 230);
    private static final Dimension FRAME_MINIMUM_DIMENSION = new Dimension(960, 600);
    private static final Float WELCOME_LABEL_SIZE = 24.0f;
    private client controller;
    private JPanel humanPanel;
    private guessingClientPanel guessPanel;
    private hidingClientPanel hidePanel;
    private JPanel scoreBox;
    private JLabel yourScore;
    private JLabel oppScore;
    private JLabel welcomeWaitingLabel;
    static JMenuBar mb;
    static JMenu menu;
    static JMenuItem quit;


    public clientView(client controller) {
        this.controller = controller;
        setupWindowListener(this.controller);
        setupFrame();
        setupActionListeners();
    }

    private void setupFrame() {
        setTitle("Find the Humans");
        mb = new JMenuBar();
        menu = new JMenu("File");
        quit = new JMenuItem("Quit");
        menu.add(quit);
        mb.add(menu);
        this.setJMenuBar(mb);
        this.setVisible(true);
        setMinimumSize(FRAME_MINIMUM_DIMENSION);
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new GridLayout());
        showChanges();
    }

    public void setupPanels() {
        GridBagConstraints constraints = new GridBagConstraints();

        // Scorebox //
        Border border = BorderFactory.createTitledBorder("SCORES: ");
        scoreBox = new JPanel();
        scoreBox.setBorder(border);
        yourScore = new JLabel();
        oppScore = new JLabel();
        scoreBox.add(yourScore);
        scoreBox.add(oppScore);
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(scoreBox, constraints);

        // Human Panel //
        humanPanel = new JPanel();
        humanPanel.setBackground(CARD_TABLE_GREEN);
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(humanPanel, constraints);
        showChanges();
    }

    public void addHumanPanel(String panelType) {
        if(panelType == "GUESS") {
            guessPanel = new guessingClientPanel(controller);
            this.humanPanel = guessPanel;
        }else if(panelType == "HIDE"){
            hidePanel = new hidingClientPanel(controller);
            this.humanPanel = hidePanel;
        }
        showChanges();
    }

    public void updateHumanPanel(){
        if(humanPanel instanceof guessingClientPanel){


        }else if(humanPanel instanceof hidingClientPanel){

        }
        showChanges();
    }

    // FIX
//    public void UpdateScores(int p1Score, int p2Score){
//        this.yourScore.setText(p1Score);
//        this.oppScore.setText(p2Score);
//    }

    private void showChanges() {
        revalidate();
        repaint();
        setVisible(true);
    }

    private void setupWindowListener(client controller) {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", null, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.YES_OPTION) {
                    controller.getClientModel().quitGame();
                    System.exit(0);
                }
            }
        });
    }

    private void setupActionListeners() {
        quit.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object target = e.getSource();
        if (target == quit) {
            controller.getClientModel().quitGame();
        }


    }
}
