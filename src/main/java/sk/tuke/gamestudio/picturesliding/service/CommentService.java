package sk.tuke.gamestudio.picturesliding.service;

import sk.tuke.gamestudio.picturesliding.entity.Comment;

import java.util.List;

public interface CommentService {
    void addComment(Comment comment) throws CommentException;
    List<Comment> getComments(String game) throws CommentException;
    void reset() throws CommentException;
}
