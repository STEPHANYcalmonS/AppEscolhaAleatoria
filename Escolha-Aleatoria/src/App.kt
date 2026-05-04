import javax.swing.*
import java.awt.*
import kotlin.math.cos
import kotlin.math.PI

class PainelCard : JPanel() {
    var escalaX = 1.0
    override fun paintComponent(g: Graphics) {
        val g2 = g as Graphics2D

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

        val largura = (width * escalaX).toInt()
        val altura = height

        val x = (width - largura) / 2
        val y = 0

        g2.color = Color(0, 0, 0, 40)
        g2.fillRoundRect(x + 5, y + 5, largura, altura - 10, 30, 30)

        g2.color = Color.WHITE
        g2.fillRoundRect(x, y, largura, altura - 10, 30, 30)

        super.paintComponent(g)
    }

    init {
        isOpaque = false
        layout = BorderLayout()
    }
}

class BotaoGradiente(texto: String) : JButton(texto) {

    override fun paintComponent(g: Graphics) {
        val g2 = g as Graphics2D

        val gradiente = GradientPaint(
            0f, 0f, Color(0x9B59B6),
            width.toFloat(), height.toFloat(), Color(0x8E44AD)
        )

        g2.paint = gradiente
        g2.fillRoundRect(0, 0, width, height, 20, 20)

        super.paintComponent(g)
    }

    init {
        isContentAreaFilled = false
        foreground = Color.WHITE
        font = Font("Segoe UI", Font.BOLD, 14)
        isFocusPainted = false
    }
}

class FundoAnimado : JPanel() {

    data class Bolha(var x: Int, var y: Int, var tamanho: Int, var velocidade: Int)

    private val bolhas = mutableListOf<Bolha>()

    init {
        repeat(15) {
            bolhas.add(
                Bolha(
                    x = (50..400).random(),
                    y = (0..500).random(),
                    tamanho = (30..100).random(),
                    velocidade = (1..3).random()
                )
            )
        }

        val timer = Timer(40) {
            moverBolhas()
            repaint()
        }
        timer.start()
    }

    private fun moverBolhas() {
        for (bolha in bolhas) {
            bolha.y -= bolha.velocidade

            // quando sai da tela, volta pra baixo
            if (bolha.y + bolha.tamanho < 0) {
                bolha.y = height + bolha.tamanho
                bolha.x = (0..width).random()
            }
        }
    }

    override fun paintComponent(g: Graphics) {
        val g2 = g as Graphics2D

        val gradiente = GradientPaint(
            0f, 0f, Color(0xF3EDE8),
            0f, height.toFloat(), Color(0xE8DFF5)
        )
        g2.paint = gradiente
        g2.fillRect(0, 0, width, height)

        g2.color = Color(255, 255, 255, 160)

        for (bolha in bolhas) {
            g2.fillOval(bolha.x, bolha.y, bolha.tamanho, bolha.tamanho)
        }
    }
}
class Titulo(val texto: String) : JPanel() {

    var animacao = 0.0

    init {
        isOpaque = false

        val timer = Timer(40) {
            animacao += 0.02
            repaint()
        }
        timer.start()
    }

    override fun paintComponent(g: Graphics) {
        val g2 = g as Graphics2D

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

        val fonte = Font("Segoe UI", Font.BOLD, 28)
        g2.font = fonte

        val metrics = g2.fontMetrics
        val larguraTexto = metrics.stringWidth(texto)

        val x = (width - larguraTexto) / 2
        val y = height / 2 + metrics.ascent / 2

        g2.color = Color(0, 0, 0, 40)
        g2.drawString(texto, x + 2, y + 2)

        val brilho = (Math.sin(animacao) + 1) / 2
        val cor = Color(
            (140 + 40 * brilho).toInt(),
            100,
            200
        )

        g2.color = cor
        g2.drawString(texto, x, y)
    }
}
fun main() {
    val perguntasDiversao = listOf(
        "Se você pudesse ter qualquer superpoder por um dia, qual seria e por quê?",
        "Se virasse cantor, qual estilo seria?",
        "Se os animais pudessem falar, qual seria o mais rude?",
        "Qual filme você assistiria mil vezes?",
        "Qual comida você nunca enjoa?",
        "Onde/ como seria sua viagem perfeita?",
        "se você fosse um personagem em um videogame, qual seria seu movimento especial?",
        "Qual desculpa você já deu pra não sair?",
        "Série que você indicaria pra todo mundo?"
    )

    val perguntasCriativas = listOf(
        "Invente um novo sabor de sorvete agora. O que tem nele?",
        "Invente um superpoder inútil",
        "Crie um nome de empresa futurista",
        "Como seria uma cidade em Marte?",
        "Projete a casa na árvore dos seus sonhos. Quais características ela terá?",
        "Crie um supervilão baseado no seu pior hábito. Qual é o nome dele?",
        "Se você escrevesse um livro sobre sua vida, qual seria o título?",
        "Invente uma criatura mágica",
        "Descreva um mundo com regras diferentes",
        "Crie uma música com um tema aleatório",
        "O que as pessoas não sabem sobre você?",
        "Como você quer ser lembrado?",
        "Ilha deserta: 3 coisas que levaria?"
    )

    var listaAtual = perguntasDiversao

    val janela = JFrame("App Aleatório")
    janela.setSize(450, 500)
    janela.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    janela.layout = BorderLayout()
    janela.contentPane = FundoAnimado()
    janela.layout = BorderLayout()

    val container = JPanel(GridBagLayout())
    container.isOpaque = false

    val card = PainelCard()
    card.preferredSize = Dimension(300, 180)

    val textoPergunta = JLabel("Clique em girar", SwingConstants.CENTER)
    textoPergunta.font = Font("Segoe UI", Font.BOLD, 16)

    card.add(textoPergunta, BorderLayout.CENTER)
    container.add(card)

    val titulo = Titulo("Uma Pergunta ao Acaso")
    titulo.preferredSize = Dimension(400, 70)

    val painelTopo = JPanel()
    painelTopo.isOpaque = false
    painelTopo.border = BorderFactory.createEmptyBorder(10, 0, 10, 0)

    val botaoDiversao = JButton("🎮 Diversão")
    val botaoCriativo = JButton("✨ Criativo")

    botaoDiversao.isFocusPainted = false
    botaoDiversao.border = BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(Color(0x8E44AD), 2, true),
        BorderFactory.createEmptyBorder(8, 16, 8, 16)
    )

    botaoCriativo.isFocusPainted = false
    botaoCriativo.border = BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(Color(0x8E44AD), 2, true),
        BorderFactory.createEmptyBorder(8, 16, 8, 16)
    )

    botaoDiversao.background = Color(0x8E44AD)
    botaoDiversao.foreground = Color.WHITE

    botaoCriativo.background = Color.LIGHT_GRAY
    botaoCriativo.foreground = Color.BLACK

    botaoDiversao.isFocusPainted = false
    botaoCriativo.isFocusPainted = false

    botaoDiversao.addActionListener {
        listaAtual = perguntasDiversao

        botaoDiversao.background = Color(0x8E44AD)
        botaoDiversao.foreground = Color.WHITE

        botaoCriativo.background = Color.LIGHT_GRAY
        botaoCriativo.foreground = Color.BLACK
    }

    botaoCriativo.addActionListener {
        listaAtual = perguntasCriativas

        botaoCriativo.background = Color(0x8E44AD)
        botaoCriativo.foreground = Color.WHITE

        botaoDiversao.background = Color.LIGHT_GRAY
        botaoDiversao.foreground = Color.BLACK
    }

    painelTopo.add(botaoDiversao)
    painelTopo.add(Box.createHorizontalStrut(30)) // espaço de 15px
    painelTopo.add(botaoCriativo)

    val botaoGirar = BotaoGradiente("Girar")

    botaoGirar.isFocusPainted = false
    botaoGirar.border = BorderFactory.createEmptyBorder(10, 30, 10, 30)
    botaoGirar.cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)

    val painelInferior = JPanel()
    painelInferior.isOpaque = false
    painelInferior.border = BorderFactory.createEmptyBorder(10, 0, 30, 0)
    painelInferior.add(botaoGirar)

    val topo = JPanel()
    topo.layout = BoxLayout(topo, BoxLayout.Y_AXIS)
    topo.isOpaque = false

    titulo.alignmentX = Component.CENTER_ALIGNMENT
    painelTopo.alignmentX = Component.CENTER_ALIGNMENT

    topo.add(Box.createVerticalStrut(10))
    topo.add(titulo)
    topo.add(Box.createVerticalStrut(10))
    topo.add(painelTopo)

    janela.add(topo, BorderLayout.NORTH)
    janela.add(container, BorderLayout.CENTER)
    janela.add(painelInferior, BorderLayout.SOUTH)

    botaoGirar.addActionListener {

        botaoGirar.isEnabled = false

        val temporizador = Timer(20, null)

        var progresso = 0.0
        var trocouTexto = false

        temporizador.addActionListener {

            progresso += 0.05

            val escala = kotlin.math.abs(cos(progresso * PI))

            card.escalaX = escala
            card.repaint()

            if (!trocouTexto && escala < 0.1) {
                textoPergunta.text =
                    "<html><div style='text-align:center;'>${listaAtual.random()}</div></html>"
                trocouTexto = true
            }

            if (progresso >= 1.0) {
                temporizador.stop()
                card.escalaX = 1.0
                card.repaint()
                botaoGirar.isEnabled = true
            }
        }

        temporizador.start()
    }

    janela.isVisible = true
}