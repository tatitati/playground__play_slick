package infrastructure.message

case class Message(
                 senderId: Long,
                 content: String,
                 id: Long = 0L
               )
