//TIP Para <b>executar</b> o código, pressione <shortcut actionId="Run"/> ou
// clique no ícone <icon src="AllIcons.Actions.Execute"/> da guia.
fun main() {
    val name = "Kotlin"
    //TIP Pressione <shortcut actionId="ShowIntentionActions"/> com o cursor no texto realçado
    // para ver como IntelliJ IDEA sugere a correção.
    println("Hello, " + name + "!")

    for (i in 1..5) {
        //TIP Pressione <shortcut actionId="Debug"/> para iniciar a depuração do código. Definimos <icon src="AllIcons.Debugger.Db_set_breakpoint"/> ponto de interrupção
        // para você, mas você sempre pode adicionar mais pressionando <shortcut actionId="ToggleLineBreakpoint"/>.
        println("i = $i")
    }
}