package post.api

import grails.gorm.transactions.Transactional
import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse
import netscape.javascript.JSObject
import org.grails.web.json.JSONElement

@Transactional
class PostService {

    private static final POST_URL = "https://jsonplaceholder.typicode.com/posts"
    private static final POST_TO_FETCH = 25
    SendMessageService sendMessageService

    JSONElement getAllPosts() {
        RestBuilder builder = new RestBuilder()
        RestResponse response = builder.get(POST_URL)
        if(response.status == 200 && response.hasBody()){
            return response.json
        }
        return null;
    }

    List<JSONElement> filterPost(JSONElement allPosts){
        List<JSONElement> filteredPosts = (allPosts as List<JSONElement>).take(POST_TO_FETCH)
        return filteredPosts
    }

    def publishPosts(List<JSONElement> posts) {
        if(posts){
            def allThreads = []
            posts.each {JSONElement postInstance ->
                allThreads << Thread.start {
                    log.info( "Posting message: ${postInstance.toString()}")
                    sendMessageService.sendMessage(postInstance.toString())
                }
            }
        }
    }
}
