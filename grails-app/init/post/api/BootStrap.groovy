package post.api

import org.grails.web.json.JSONElement

class BootStrap {
    PostService postService

    def init = { servletContext ->
        List<JSONElement> posts = postService.getAllPosts()
        if(posts) {
            List<JSONElement> filteredPosts = postService.filterPost(posts)
            postService.publishPosts(filteredPosts)
        }
    }
    def destroy = {
    }
}
