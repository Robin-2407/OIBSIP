import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NumberGuessingGame extends JFrame {
    private int randomNumber;
    private int score;
    private int attempts;
    
    private int closeRange = 3;
    
    private JLabel rangeLabel;
    private JLabel scoreLabel;
    private JLabel resultLabel;
    private JTextField guessField;
    private JButton submitButton;
    
    public NumberGuessingGame() {
        setTitle("Number Guessing Game");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initUI();
    }
    
    private void initUI() {
        setLayout(new GridLayout(6, 1));
        
        rangeLabel = new JLabel("Enter the range of the number (e.g., 1-100):");
        add(rangeLabel);
        
        JTextField rangeField = new JTextField();
        add(rangeField);
        
        submitButton = new JButton("Start Game");
        add(submitButton);
        
        scoreLabel = new JLabel("Score: 0");
        add(scoreLabel);
        
        guessField = new JTextField();
        add(guessField);
        
        resultLabel = new JLabel("Guess a number!");
        add(resultLabel);
        
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (submitButton.getText().equals("Start Game")) {
                    String rangeText = rangeField.getText();
                    String[] rangeArray = rangeText.split("-");
                    int minRange = Integer.parseInt(rangeArray[0].trim());
                    int maxRange = Integer.parseInt(rangeArray[1].trim());
                    startNewGame(minRange, maxRange);
                    submitButton.setText("Submit Guess");
                    rangeField.setEnabled(false);
                } else {
                    processGuess();
                }
            }
        });
        
        guessField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processGuess();
            }
        });
    }
    
    private void startNewGame(int minRange, int maxRange) {
        randomNumber = (int) (Math.random() * (maxRange - minRange + 1)) + minRange;
        score = 100; // Set initial score
        attempts = 0;
        scoreLabel.setText("Score: " + score);
        resultLabel.setText("Guess a number!");
        guessField.setText("");
        guessField.setEnabled(true);
    }
    
    private void processGuess() {
        int guessedNumber = Integer.parseInt(guessField.getText());
        attempts++;

        if (guessedNumber == randomNumber) {
            resultLabel.setText("Congratulations! You guessed the number in " + attempts + " attempts.");
            guessField.setEnabled(false);
            submitButton.setEnabled(false);
        } else if (Math.abs(guessedNumber - randomNumber) <= closeRange) {
            resultLabel.setText("Close, but not quite. Keep guessing!");
            score -= 5;
        } else if (guessedNumber < randomNumber) {
            resultLabel.setText("Too low. Try again.");
            score -= 10;
        } else {
            resultLabel.setText("Too high. Try again.");
            score -= 10;
        }

        if (attempts >= 10) {
            resultLabel.setText("You've used all your attempts. The number was " + randomNumber);
            guessField.setEnabled(false);
            submitButton.setEnabled(false);
        }

        if (score < 0) {
            score = 0;
        }

        scoreLabel.setText("Score: " + score);
        guessField.setText("");
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new NumberGuessingGame().setVisible(true);
            }
        });
    }
}
