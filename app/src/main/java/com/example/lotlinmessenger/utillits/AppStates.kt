package com.example.lotlinmessenger.utillits

enum class AppStates(val state: String) {
    /* Класс перечисление состояний приложения*/

    ONLINE("онлайн"),
    OFFLINE("офлайн"),
    TYPING("печатает");

    companion object {
        fun updateState(appStates: AppStates) {
            /*Функция принимает состояние и записывает в базу данных*/
            REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID).child(CHILD_STATE)
                .setValue(appStates.state)
                .addOnSuccessListener { USER.state = appStates.state }
        }
    }
}