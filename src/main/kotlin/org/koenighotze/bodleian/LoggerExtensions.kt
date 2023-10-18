import org.slf4j.Logger

fun Logger.logException(message: String, e: Throwable) {
    this.debug(message, e)
    this.error("$message. Reason: ${e.message}")
}
