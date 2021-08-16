package post.api

import grails.gorm.transactions.Transactional
import org.springframework.beans.factory.annotation.Value

@Transactional
class SendMessageService {


    def jmsService

    @Value('${ellucian.queue_name}')
    String queueName

    def sendMessage(String message) {
        jmsService.send(queue: queueName, message)
    }
}
