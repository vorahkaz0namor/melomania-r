package ru.netology

import java.util.*
import kotlin.math.roundToInt

const val SONG_COST = 5000.0
const val MAIN_DISCOUNT = 10000
const val GREAT_DISCOUNT = 0.05
const val REGULAR_USER_DISCOUNT = 0.01

fun main() {
    val sc = Scanner(System.`in`)
    var isRegularUser: Boolean
    var songsValue: Int

    mainMenu()
    while (true) {
        try {
            songsValue = sc.nextLine().toInt()
            if (songsValue < 0) throw InputMismatchException()
            break
        } catch (e: Exception) {
            print("Введите корректное значение\n>> ")
        }
    }

    if (songsValue == 0)
        println("""
            Очень жаль, что Вы ничего не выбрали сегодня... :-(
            Приходите к нам еще! ;-)
        """.trimIndent())
    else {
        do {
            print("Являетесь ли Вы нашим постоянным пользователем?\n(y - Да / n - Нет) >> ")
            val input = sc.nextLine()
            isRegularUser = input.equals("y")
        } while (!input.equals("y") && !input.equals("n"))
        sc.close()

        println("\n" + """
            Поздравляем! ?.-)
            Вы только что приобрели в нашем магазине $songsValue ${songEnding(songsValue)}
            на сумму ${purchaseCount(songsValue, isRegularUser)} рублей.
            Приятного прослушивания!
        """.trimIndent()
        )
    }
}

fun mainMenu() {
    print("""
        Добро пожаловать в онлайн-магазин "Меломания"!
        В нашем магазине для Вас действуют следующие условия:
        1. Стоимость одной песни - 50 рублей
        2. Скидка при покупке от 21 до 200 песен - 100 рублей
        3. Скидка при покупке свыше 200 песен - 5% от суммы покупки
        4. Если Вы являетесь нашим постоянным пользователем
           (т.е. приобретаете в нашем магазине песни ежемесячно),
           то для Вас будет действовать дополнительная скидка - 1% от суммы покупки
        Введите количество песен, которые Вы хотите приобрести (или 0 - для выхода):
        >> 
    """.trimIndent())
}

fun purchaseCount(songsValue: Int, isRegularUser: Boolean): Double {
    var purchase: Double = songsValue * SONG_COST
    when {
        songsValue in (21..200) -> purchase -= MAIN_DISCOUNT
        songsValue > 200 -> purchase *= (1 - GREAT_DISCOUNT)
    }
    if (isRegularUser)
        purchase *= (1 - REGULAR_USER_DISCOUNT)
    return ((purchase).roundToInt().toDouble() / 100)
}

fun songEnding(songsValue: Int): String {
    val ending = songsValue.toString()
    val singleChar = ending.substring(ending.length - 1).toInt()
    val pairChars = if (ending.length > 1)
                        ending.substring(ending.length - 2).toInt()
                    else 0
    return when {
               (singleChar == 1 &&
                pairChars != 11) -> "песню"
                (singleChar in 2..4 &&
                 pairChars !in 12..14) -> "песни"
                else -> "песен"
    }
}