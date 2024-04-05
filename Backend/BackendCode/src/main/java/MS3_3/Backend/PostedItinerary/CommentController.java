package MS3_3.Backend.PostedItinerary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {
    @Autowired
    CommentRepository commentRepository;

    @PostMapping("/Comment")
    public PostedItinerary.Comment createPlace(@RequestBody PostedItinerary.Comment comment) {
        commentRepository.save(comment);
        return comment;
    }

    @DeleteMapping("/Comment/{commentID}")
    public String deletePlace(@PathVariable String commentID) {
        commentRepository.deleteByCommentID(commentID);
        return "Place with name " + commentID + " successfully deleted";
    }

    @PutMapping("/Comment/{commentID}")
    public PostedItinerary.Comment updatePlace(@PathVariable String commentID, @RequestBody PostedItinerary.Comment updatedComment) {
        PostedItinerary.Comment existingComment = commentRepository.findByCommentID(commentID);
        if (existingComment != null) {
            existingComment.setCreator(updatedComment.getCreator());
            existingComment.setItinerary(updatedComment.getItinerary());
            existingComment.setCommentText(updatedComment.getCommentText());
            existingComment.setCommentID(updatedComment.getCommentID());
            return existingComment;
        } else {
            return null;
        }
    }

    @GetMapping("/Comment/{uniqueCode}")
    public PostedItinerary.Comment getComment(@PathVariable String commentID) {
        return commentRepository.findByCommentID(commentID);
    }

    @GetMapping("/Comment/List")
    public List<PostedItinerary.Comment> getAllComments() {
        return commentRepository.findAll();
    }
}
