package com.walvarado.unsplashtest.api

enum class RequestError(val statusCode: Int) {
    BAD_REQUEST(400) {
        override fun toString() = "Parámetro erroneos"
    },
    UNAUTHORIZED(401) {
        override fun toString() = "No autorizado"
    },
    FORBIDDEN(403) {
        override fun toString() = "No tienes permiso"
    },
    NOT_FOUND(404) {
        override fun toString() = "No encontrado"
    },
    INTERNAL_ERROR(500) {
        override fun toString() = "Error interno"
    },
    SERVICE_UNAVAILABLE(503) {
        override fun toString() = "Servicio no disponible"
    },
    UNKNOWN(0) {
        override fun toString() = "Error desconocido"
    };
    override fun toString(): String {
        return statusCode.toString()
    }
    companion object {
        private val VALUES = values()
        fun getByValue(value: Int) = VALUES.firstOrNull { it.statusCode == value }
    }
}