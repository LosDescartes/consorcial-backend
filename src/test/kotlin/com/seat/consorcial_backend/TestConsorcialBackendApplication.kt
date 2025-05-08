package com.seat.consorcial_backend

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
	fromApplication<ConsorcialBackendApplication>().with(TestcontainersConfiguration::class).run(*args)
}
