import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RomanDecimalConverter extends JFrame {
    private JTextField inputField;
    private JTextField outputField;
    private JButton convertButton;
    private JComboBox<String> conversionType;

    public RomanDecimalConverter() {
        setTitle("Conversor de Números Romanos e Decimais");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        inputField = new JTextField(15);
        outputField = new JTextField(15);
        outputField.setEditable(false);
        convertButton = new JButton("Converter");

        String[] types = {"Romano para Decimal", "Decimal para Romano"};
        conversionType = new JComboBox<>(types);

        add(new JLabel("Entrada:"));
        add(inputField);
        add(conversionType);
        add(convertButton);
        add(new JLabel("Saída:"));
        add(outputField);

        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convert();
            }
        });

        inputField.addCaretListener(e -> convert());

        setVisible(true);
    }

    private void convert() {
        String input = inputField.getText().toUpperCase();
        String selectedType = (String) conversionType.getSelectedItem();
        if (selectedType.equals("Romano para Decimal")) {
            try {
                int decimalValue = romanToDecimal(input);
                outputField.setText(String.valueOf(decimalValue));
            } catch (IllegalArgumentException e) {
                outputField.setText("Erro: Símbolo inválido");
            }
        } else {
            try {
                int decimalValue = Integer.parseInt(input);
                String romanValue = decimalToRoman(decimalValue);
                outputField.setText(romanValue);
            } catch (NumberFormatException e) {
                outputField.setText("Erro: Número inválido");
            }
        }
    }

    private int romanToDecimal(String roman) {
        int total = 0;
        int prevValue = 0;

        for (int i = roman.length() - 1; i >= 0; i--) {
            char ch = roman.charAt(i);
            int value = getRomanValue(ch);

            if (value < 0) {
                throw new IllegalArgumentException("Símbolo inválido");
            }

            if (value < prevValue) {
                total -= value;
            } else {
                total += value;
            }
            prevValue = value;
        }
        return total;
    }

    private int getRomanValue(char ch) {
        switch (ch) {
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            case 'D': return 500;
            case 'M': return 1000;
            default: return -1; // Símbolo inválido
        }
    }

    private String decimalToRoman(int number) {
        if (number <= 0) {
            throw new IllegalArgumentException("Número deve ser maior que zero");
        }

        StringBuilder roman = new StringBuilder();
        String[] romanNumerals = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};

        for (int i = 0; i < values.length; i++) {
            while (number >= values[i]) {
                roman.append(romanNumerals[i]);
                number -= values[i];
            }
        }
        return roman.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RomanDecimalConverter::new);
    }
}