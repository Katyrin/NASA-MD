package com.katyrin.nasa_md.ui.main.data

val fakeData: List<ListItem> = listOf(
    ListItem.NoteItem(
        header = "Убраться",
        description = "Нужно успеть до 18:00 сегодня либо завтра с 13:00 до 17:00, обязательно протереть окна",
        hasPortfolio = true,
    ),
    ListItem.ImportantItem(
        header = "Урок",
        description = "Не пропустить вечером урок и сделать домашнюю работу к этому уроку"
    ),
    ListItem.NoteItem(
        header = "Купить продукты",
        description = "Молоко, хлеб, чай, печенье, курица, рыба, мясо, порошок, мыло, овощи, фрукты, газета, лампоча, жвачка",
        hasPortfolio = true,
    ),
    ListItem.NoteItem(
        header = "Кино",
        description = "Сходить в кино на новый фильм",
        hasPortfolio = false,
    ),
    ListItem.NoteItem(
        header = "Футбол",
        description = "Во вторник с 20:00 до 22:00",
        hasPortfolio = false,
    ),
    ListItem.NoteItem(
        header = "Посылка",
        description = "Съездить получить посылку в пункт выдачи либо на Карла Маркса либо на Ленина работают с 10:00 до 20:00",
        hasPortfolio = true,
    ),
)