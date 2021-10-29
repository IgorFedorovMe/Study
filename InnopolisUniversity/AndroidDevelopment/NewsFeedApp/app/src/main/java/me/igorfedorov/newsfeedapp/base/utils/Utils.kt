package me.igorfedorov.newsfeedapp.base.utils

import me.igorfedorov.newsfeedapp.base.functional.Either

inline fun <reified T> attempt(func: () -> T): Either<Throwable, T> = try {
    Either.Right(func.invoke())
} catch (e: Throwable) {
    Either.Left(e)
}