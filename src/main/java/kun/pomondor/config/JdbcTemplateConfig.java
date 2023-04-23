package kun.pomondor.config;

import kun.pomondor.repository.etc.food.comment.FoodCommentRepository;
import kun.pomondor.repository.etc.food.comment.FoodCommentRepositoryImpl;
import kun.pomondor.repository.etc.food.post.FoodPostRepository;
import kun.pomondor.repository.etc.food.post.FoodPostRepositoryImpl;
import kun.pomondor.repository.etc.food.score.ScoreRepository;
import kun.pomondor.repository.etc.food.score.ScoreRepositoryImpl;
import kun.pomondor.repository.friend.FriendRepository;
import kun.pomondor.repository.friend.FriendRepositoryImpl;
import kun.pomondor.repository.member.MemberRepository;
import kun.pomondor.repository.member.MemberRepositoryImpl;
import kun.pomondor.repository.time.TimeRepository;
import kun.pomondor.repository.time.TimeRepositoryImpl;
import kun.pomondor.service.etc.food.*;
import kun.pomondor.service.member.MemberService;
import kun.pomondor.service.member.MemberServiceImpl;
import kun.pomondor.service.time.TimeService;
import kun.pomondor.service.time.TimeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class JdbcTemplateConfig {

	private final DataSource dataSource;

	@Bean
	public MemberService memberService() {
		return new MemberServiceImpl(memberRepository());
	}

	@Bean
	public MemberRepository memberRepository() {
		return new MemberRepositoryImpl(dataSource);
	}

	@Bean
	public FriendRepository friendRepository() {
		return new FriendRepositoryImpl(dataSource);
	}

	@Bean
	public TimeRepository timeRepository() {
		return new TimeRepositoryImpl(dataSource);
	}

	@Bean
	public TimeService timeService() {
		return new TimeServiceImpl(timeRepository());
	}

	@Bean
	public FoodPostRepository foodPostRepository() {
		return new FoodPostRepositoryImpl(dataSource);
	}

	@Bean
	public FoodPostService foodPostService() {
		return new FoodPostServiceImpl(foodPostRepository());
	}

	@Bean
	public FoodCommentRepository foodCommentRepository() {
		return new FoodCommentRepositoryImpl(dataSource);
	}

	@Bean
	public FoodCommentService foodCommentService() {
		return new FoodCommentServiceImpl(foodCommentRepository());
	}

	@Bean
	public ScoreRepository scoreRepository() {
		return new ScoreRepositoryImpl(dataSource);
	}

	@Bean
	public ScoreService scoreService() {
		return new ScoreServiceImpl(scoreRepository());
	}
}

