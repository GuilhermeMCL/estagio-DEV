import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;

public class SimulacaoElevador extends JFrame {
    private static final int TOTAL_ANDARES = 4;
    private int andarAtual = 0; // Andar atual do elevador (0 = Andar 1)
    private Queue<Integer> solicitacoes = new LinkedList<>(); // Fila de solicitações
    private JButton[] botoesAndar = new JButton[TOTAL_ANDARES];
    private JButton[][] botoesChamada = new JButton[TOTAL_ANDARES][2]; // Botões de chamada para cima e para baixo
    private JLabel labelElevador;
    private Timer temporizador;
    private JPanel painelIndicadorAndar; // Painel para indicar o andar atual

    public SimulacaoElevador() {
        setTitle("Simulação de Elevador");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Adicionando um JLabel para mostrar o status do elevador
        labelElevador = new JLabel("Elevador no andar: " + (andarAtual + 1), SwingConstants.CENTER);
        add(labelElevador, BorderLayout.NORTH);

        // Painel de andares
        JPanel painelAndar = new JPanel();
        painelAndar.setLayout(new GridLayout(TOTAL_ANDARES, 1));
        
        ActionListener ouvinteBotaoChamada = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton source = (JButton) e.getSource();
                int andar = (int) source.getClientProperty("andar");
                int direcao = (int) source.getClientProperty("direcao");
                int andarDestino = andar + direcao;
                if (andarDestino >= 0 && andarDestino < TOTAL_ANDARES) {
                    solicitacoes.add(andarDestino);
                    processarSolicitacoes();
                }
            }
        };

        for (int i = TOTAL_ANDARES - 1; i >= 0; i--) { // Ordem decrescente dos andares
            JPanel painelBotaoAndar = new JPanel();
            painelBotaoAndar.setLayout(new FlowLayout());

            // Botão para o andar
            botoesAndar[i] = new JButton("Andar " + (i + 1)); // Exibe Andar 1, 2, 3, 4
            final int andar = i; // Captura do andar atual
            botoesAndar[i].addActionListener(e -> {
                solicitacoes.add(andar);
                processarSolicitacoes();
            });
            painelBotaoAndar.add(botoesAndar[i]);

            // Botão de chamada para cima
            botoesChamada[i][0] = new JButton("Chamar para cima");
            botoesChamada[i][0].putClientProperty("andar", i);
            botoesChamada[i][0].putClientProperty("direcao", 1);
            botoesChamada[i][0].addActionListener(ouvinteBotaoChamada);
            painelBotaoAndar.add(botoesChamada[i][0]);

            // Botão de chamada para baixo
            botoesChamada[i][1] = new JButton("Chamar para baixo");
            botoesChamada[i][1].putClientProperty("andar", i);
            botoesChamada[i][1].putClientProperty("direcao", -1);
            botoesChamada[i][1].addActionListener(ouvinteBotaoChamada);
            painelBotaoAndar.add(botoesChamada[i][1]);

            // Desabilitar botão de chamada para baixo no andar 0
            if (i == 0) {
                botoesChamada[i][1].setEnabled(false);
            }

            // Desabilitar botão de chamada para cima no último andar
            if (i == TOTAL_ANDARES - 1) {
                botoesChamada[i][0].setEnabled(false);
            }

            painelAndar.add(painelBotaoAndar);
        }

        // Adicionando componentes à janela
        add(painelAndar, BorderLayout.SOUTH);

        // Painel de andares com indicação visual
        painelIndicadorAndar = new JPanel();
        painelIndicadorAndar.setLayout(new GridLayout(TOTAL_ANDARES, 1));
        for (int i = TOTAL_ANDARES - 1; i >= 0; i--) { // Ordem decrescente dos andares
            JLabel indicadorAndar = new JLabel("Andar " + (i + 1), SwingConstants.CENTER);
            indicadorAndar.setOpaque(true);
            indicadorAndar.setBackground(Color.LIGHT_GRAY); // Define a cor padrão
            indicadorAndar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            painelIndicadorAndar.add(indicadorAndar);
        }
        atualizarIndicadoresAndar(); // Atualiza a cor do andar atual
        add(painelIndicadorAndar, BorderLayout.EAST);

        setVisible(true);
    }

    private void processarSolicitacoes() {
        if (!solicitacoes.isEmpty()) {
            int proximoAndar = solicitacoes.poll();
            if (proximoAndar != andarAtual) {
                moverElevador(proximoAndar);
            } else {
                processarSolicitacoes(); // Processa a próxima solicitação se o andar atual for igual ao próximo andar
            }
        } else {
            moverElevador(0); // Retorna ao primeiro andar quando não houver solicitações
        }
    }

    private void moverElevador(int andarDestino) {
        if (andarDestino != andarAtual) {
            labelElevador.setText("Elevador se movendo para o andar: " + (andarDestino + 1));
            int passo = (andarDestino > andarAtual) ? 1 : -1; // Determina a direção do movimento
            temporizador = new Timer(500, new ActionListener() { // Aumentar o tempo para uma animação mais suave
                @Override
                public void actionPerformed(ActionEvent e) {
                    andarAtual += passo;
                    atualizarIndicadoresAndar();
                    labelElevador.setText("Elevador no andar: " + (andarAtual + 1)); // Atualiza o label com o andar atual
                    if (andarAtual == andarDestino) {
                        labelElevador.setText("Elevador chegou ao andar: " + (andarAtual + 1)); // Mensagem final
                        Toolkit.getDefaultToolkit().beep(); // Toca um som quando o elevador chega ao andar
                        temporizador.stop();
                        new Timer(5000, new ActionListener() { // Espera 5 segundos antes de processar a próxima solicitação
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                processarSolicitacoes();
                            }
                        }).start();
                    }
                }
            });
            temporizador.start();
        }
    }

    private void atualizarIndicadoresAndar() {
        for (int i = 0; i < TOTAL_ANDARES; i++) {
            JLabel indicadorAndar = (JLabel) painelIndicadorAndar.getComponent(TOTAL_ANDARES - 1 - i);
            indicadorAndar.setBackground(i == andarAtual ? Color.YELLOW : Color.LIGHT_GRAY);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SimulacaoElevador::new);
    }
}
