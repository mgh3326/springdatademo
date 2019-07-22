package me.khmoon.demospringdata;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class CommentRepositoryTest {

  @Autowired
  CommentRepository commentRepository;

  @Test
  public void crud() throws ExecutionException, InterruptedException {
    this.createComment(100, "spring data jpa");
    this.createComment(55, "HIBERNATE SPRING");

    PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "likeCount"));
    ListenableFuture<List<Comment>> future = commentRepository.findByCommentContainsIgnoreCase("Spring", pageRequest);

    System.out.println("===============");
    System.out.println("is done?" + future.isDone());

    future.addCallback(new ListenableFutureCallback<List<Comment>>() {
      @Override
      public void onFailure(Throwable throwable) {
        System.out.println(throwable);
      }

      @Override
      public void onSuccess(List<Comment> comments) {
        System.out.println("===============");
        comments.forEach(System.out::println);
      }
    });

    List<Comment> comments = future.get();
    comments.forEach(System.out::println);
//    commentRepository.save(comment);
//
//    List<Comment> all = commentRepository.findAll();
//    assertThat(all.size()).isEqualTo(1);
//
//    long count = commentRepository.count();
//    assertThat(count).isEqualTo(1);
//
//    Optional<Comment> byId = commentRepository.findById(100l);
//    assertThat(byId).isEmpty();
//    Comment comment = byId.orElseThrow(IllegalAccessError::new);

  }

  private void createComment(int likeCount, String comment) {
    Comment newComment = new Comment();
    newComment.setComment(comment);
    newComment.setLikeCount(likeCount);
    commentRepository.save(newComment);
  }

}